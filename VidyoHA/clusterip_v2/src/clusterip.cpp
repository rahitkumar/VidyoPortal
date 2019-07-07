/////////////////////////////////////////////////////////////////////////
// Filename: clusterip.cpp
// Description:  
// Date Created: 04/01/2013
// Modification History:
//    04-01-2013 - Initial coding(Eric)
////////////////////////////////////////////////////////////////////////

#include<queue>
#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#include<pthread.h>
#include<string.h>
#include<time.h>

#include<fcntl.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<netinet/tcp.h>
#include<netdb.h>
#include<errno.h>

#include<sys/socket.h>
#include<sys/types.h>
#include<sys/time.h>
#include<sys/select.h>


#include"clusterip.h"
#include"clusterinfo.h"
#include"statemachine.h"
#include"miscutils.h"
#include"reqmsg.h"
#include"params.h"
#include"link_watcher.h"
#include"dbversion.h"
#include"cluster_signals.h"

////////////////////////////////////////////
// Initialize the singleton class
////////////////////////////////////////////
CStateMachine *CStateMachine::pInstance = NULL;
CMiscUtils *CMiscUtils::pInstance = NULL;
CClusterInfo *CClusterInfo::pInstance = NULL;

//////////////////////////////////
// stl container for events
//////////////////////////////////
queue<HA_EVENTS> event_queue;

/////////////////////
// thread attributes
/////////////////////
pthread_attr_t attr;

///////////////////////
// mutex declaration
///////////////////////
pthread_mutex_t event_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t csum_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t memory_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t udpsend_mutex = PTHREAD_MUTEX_INITIALIZER;


////////////////////////
// conditional mutex
////////////////////////
pthread_cond_t  event_mutex_cond = PTHREAD_COND_INITIALIZER;
pthread_cond_t csum_mutex_cond = PTHREAD_COND_INITIALIZER;

//////////////////////////
// thread identifiers
//////////////////////////
pthread_t hb_watcher_thread; // thread that monitors the last HB received from peer.
pthread_t hb_receiver_thread; // thread that receives a HB from a UDP packet.
pthread_t link_watcher_thread; // thread that continuously monitors the connection to the cloud.
pthread_t admin_thread; // thread that accept request from cip_admin program.
pthread_t secondary_heartbeat_thread; // thread for sending HB on eth1 port
pthread_t thread_watcher_thread; // extra thread to monitor other threads.

/////// BASH helper for external events
const char *TIMEOUT_EVENT="timeout_event.sh";
const char *PROMOTE_EVENT="promote_event.sh";
const char *DEMOTE_EVENT="demote_event.sh";
const char *START_STANDBY="start_standby.sh";
const char *SPLIT_BRAIN_EVENT="split_brain_event.sh";
const char *RESET_REBOOT_COUNTER="reset_reboot_ctr.sh";

char TIMEOUT_EVENT_SCRIPT[256];
char PROMOTE_EVENT_SCRIPT[256];
char DEMOTE_EVENT_SCRIPT[256];
char SPLIT_BRAIN_EVENT_SCRIPT[256];
char START_STANDBY_SCRIPT[256];
char RESET_REBOOT_COUNTER_SCRIPT[256];

const int MAX_DATA_SIZE=512;
const char *progname = "clusterip";
const char *version = "2.0.0";
time_t starttime;
static int split_brain_ctr=0;
static bool demote_in_progress = false;


char LISTEN_PORT[8];

bool PEER_HAS_CLUSTER_IP=false;
int global_sd=0;
int admin_sd=0;

#ifdef GLOBAL_UDP_CLIENT
int udpclient_sd0=0; // for primary heartbeat.
int udpclient_sd1=0; // for secondaryheartbeat.
int AI_FAMILY;
static int CreateUDPClientSocket( CParams *cp, bool eth1_enabled = false );
void InitializeUDPClientSocket(CParams cp);
#endif

int main( int argc, char **argv)
{
   // initialze the StateMachine object. Note: this is a singleton class.
   CStateMachine *cSM = CStateMachine::Initialize();
   CClusterInfo *cCI = CClusterInfo::Initialize();
   CMiscUtils *cmu = CMiscUtils::Initialize();


   CParams cParams(progname, version);

   cmu->OpenLog("clusterip");

   cParams.ParseArgs( argc, argv );
   CMiscUtils::GetInstance()->RunLevel( cParams.LogLevel() );


   time( &starttime );

   InitSignals();

   // Daemonize before we start the threads
   if ( cParams.Daemonize() ) {
      printf("Daemonizing...\n");
      CMiscUtils::GetInstance()->Daemonize();
   }

   pthread_attr_init(&attr);

   // initialize the internal memory of the state machine object
   cSM->InitializeStates();
   cSM->SetParameters( cParams );

#ifdef GLOBAL_UDP_CLIENT
   InitializeUDPClientSocket( cParams );
#endif

   // initilize the internal memory of the cluster info object
   cCI->InitClusterData( memory_mutex, cParams.MyIP(), cParams.ClusterIP(), cParams.Preferred() );

   InitializeEventHandlerScripts( cParams );

   if ( ! SetDBVersion() ) { // defined inside the checksum.cpp
      cmu->LogMsg("Warning!!! Failed to set the DB Version...");
      cCI->MyDBVersion("UNKNOWN");
   }

   if ( pthread_create(&admin_thread, &attr, admin, (void *)&cParams) != 0 ) {
      perror("pthread_create():admin");
      exit(1);
   }

   if ( pthread_create(&hb_watcher_thread, &attr, hb_watcher, (void *)&cParams) != 0 ) {
      perror("pthread_create():hb_watcher");
      exit(1);
   }

   if ( pthread_create(&link_watcher_thread, &attr, LinkWatcher, (void *)&cParams) != 0 ) {
      perror("pthread_create(): Server");
      exit(1);
   }

   if ( pthread_create(&hb_receiver_thread, &attr, HB_Receiver, (void *)&cParams) != 0 ) {
      perror("pthread_create(): Server");
      exit(1);
   }

   if ( pthread_create(&thread_watcher_thread, &attr, thread_watcher, (void *)&cParams) != 0 ) {
      perror("pthread_create():thread_watcher");
      exit(1);
   }

   if ( strlen( cParams.PeerETH1IP() ) ) {
      if ( pthread_create(&secondary_heartbeat_thread, &attr, SecondaryHeartbeat, (void *)&cParams) != 0 ) {
         perror("pthread_create():Client");
         exit(1);
      }
   }

   EventHandler();
}

void InitializeEventHandlerScripts(CParams cp)
{
   snprintf(TIMEOUT_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), TIMEOUT_EVENT);
   snprintf(PROMOTE_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), PROMOTE_EVENT);
   snprintf(DEMOTE_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), DEMOTE_EVENT);
   snprintf(SPLIT_BRAIN_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), SPLIT_BRAIN_EVENT);
   snprintf(START_STANDBY_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), START_STANDBY);
   snprintf(RESET_REBOOT_COUNTER_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), RESET_REBOOT_COUNTER);

   // TODO: make sure this event script is available otherwise do not start...

   printf("Timeout Event: %s\n", TIMEOUT_EVENT_SCRIPT);
   printf("Promote Event: %s\n", PROMOTE_EVENT_SCRIPT);
   printf("Demote Event: %s\n", DEMOTE_EVENT_SCRIPT);
}

void ClearEventQueue()
{
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   HA_EVENTS event;

   cmu->LogMsg(INFO, "About to clear the event Q");

   pthread_mutex_lock ( &event_mutex );
   while( ! event_queue.empty() ) {
      event = event_queue.front();
      event_queue.pop();
      cmu->LogMsg(INFO, "Clearing event %d", (int)event);
   }
   
   pthread_mutex_unlock ( &event_mutex );
}


int EventHandler()
{
   CStateMachine *cSM = CStateMachine::GetInstance();
   HA_EVENTS event;

   while(1){
      pthread_mutex_lock ( &event_mutex );
      while( event_queue.empty() ) 
         pthread_cond_wait( &event_mutex_cond, &event_mutex);

      if ( event_queue.empty() ) {
         printf("Q is empty...\n");
         pthread_mutex_unlock ( &event_mutex );
         continue;
      }

      event = event_queue.front();
      event_queue.pop();

      pthread_mutex_unlock ( &event_mutex );
 
      cSM->CurrentEvent( event );
      cSM->ProcessEvents();
   }
   return 1;
}

//! \brief Push events to a message queue. This will synchronize threads from inserting events to the queue.
//! A signal will be sent to the thread that is waiting for an event.
void PushEventsToQueue(HA_EVENTS ev)
{
   pthread_mutex_lock( &event_mutex );
   event_queue.push( ev );
   pthread_cond_signal( &event_mutex_cond );
   pthread_mutex_unlock( &event_mutex );
}

int CreateUDPServerSocket()
{
   int sock;
   int aiErr;
   CClusterInfo *cCI = CClusterInfo::GetInstance();
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   char bind_ip[64];
   struct addrinfo *ai;
   struct addrinfo *aiHead;
   struct addrinfo hints;
   //struct sockaddr_in m_addr;
   char temp[256];

   memset(temp, 0, sizeof(temp));
   memset( &hints, 0, sizeof(hints) );
   hints.ai_flags = AI_PASSIVE;
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_DGRAM;
   hints.ai_protocol = IPPROTO_UDP;

   // use :: if my_ip is IPv6 otherwise use 0.0.0.0

   memset(bind_ip, 0, sizeof(bind_ip));

   // TODO
   if ( IsIPV6( cCI->MyIP() ) )
      strcpy(bind_ip, "::");
   else
      strcpy(bind_ip, "0.0.0.0");

   if ( (aiErr = getaddrinfo(bind_ip, LISTEN_PORT, &hints, &aiHead)) != 0 ) {
      cmu->LogErr("getaddressinfo(): %s",  gai_strerror( aiErr ) );
      return -1;
   }

   ai = aiHead;
   while( ai ){
      sock = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
      if ( sock > 0 ) {
         printf("valid socket found: %d\n", sock);
         break;
      }else {
         perror("continue");
      }

      ai = ai->ai_next;
   }

   if ( ai == NULL ) { /// no valid connection
      cmu->LogErr("CreateUDPServerSocket(): no valid socket..");
      return -1;
   }

   if (bind(sock, ai->ai_addr, ai->ai_addrlen) <  0) {
      cmu->LogErr("CreateUDPServerSocket(): bind failed!!!");
      return -1;
   }

   return sock;
}

//! \brief Returns the HB event that we are sending via ETH1. Events might be different per state.
HA_EVENTS SecondaryHBEvent()
{
   HA_EVENTS event=NONE;
   CStateMachine *cSM = CStateMachine::GetInstance();

   switch( cSM->CurrentState() ) {
      case REBOOT:
      case NEGOTIATION:
      case NEGOTIATION_NOLINK:
      case STANDBY:
      case STANDBY_NOLINK:
      case MAINTENANCE:
         break;
      case ACTIVE_NOLINK_NOSTANDBY:
      case ACTIVE_NOLINK_STANDBY_NOLINK:
         event = HB2_A_NL;
         break;
      case ACTIVE_WITH_STANDBY_NOLINK:
      case ACTIVE_WITH_STANDBY:
      case ACTIVE_NOSTANDBY:
         event = HB2_A_L;
         break;
   }

   return event;
}

//! \brief SecondaryHeartbeat thread.
//! Sends heartbeat/status to the remote node using the ETH1 interface.
//! Note: This thread will run only when ETH1 is enabled.
void *SecondaryHeartbeat(void *args)
{
   CParams *cp = (CParams *)args;
   CReqMsg crm;
   UCHAR buffer[1024];
   HA_EVENTS event;
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CClusterInfo *cCI=CClusterInfo::GetInstance();

   crm.IP( cp->MyETH1IP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( cCI->MyDBVersion() );

   cmu->LogMsg(INFO, "SecondaryHeartbeat(): DB Version[%s]", cCI->MyDBVersion() );

   while(1) {
      event = SecondaryHBEvent();
      if ( event != NONE ){
         cmu->LogMsg(DEBUG, "SecondaryHeartbeat(): Sending HB:[%d]", event );
         crm.Assemble( buffer, 1, cCI->MyRole(), event, ( cCI->MyRole() == ACTIVE_ROLE ? NULL : cCI->DBSyncStatus() ));

         UDPSend(cp, buffer, strlen((char *)buffer), true);
      }
      sleep(5);
   }
   return (void*)0;
}

void SplitBrain()
{
   int ret;
   char cmd[256];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   if ( split_brain_ctr < 2 ) {
      cmu->LogMsg(WARNING, "Split brain occur [%d]", split_brain_ctr);
      split_brain_ctr++;
      return;
   }

   sprintf(cmd, "%s >/dev/null 2>&1", SPLIT_BRAIN_EVENT_SCRIPT);

   ret=system(cmd);
   if (  ret != 0 ) {
      cmu->LogErr("Failed to run split brain script...\n");
   }
}


void ResetRebootCounter()
{
   int ret;
   char cmd[256];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   sprintf(cmd, "%s >/dev/null 2>&1", RESET_REBOOT_COUNTER_SCRIPT);

   ret=system(cmd);
   if (  ret != 0 ) {
      cmu->LogErr("Failed to run reset_reboot_counter script...\n");
   }
}

void Promote()
{
   int ret;
   char cmd[256];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   sprintf(cmd, "%s >/dev/null 2>&1", PROMOTE_EVENT_SCRIPT);

   ret=system(cmd);
   if (  ret != 0 ) {
      cmu->LogErr("Failed to run promte script...\n");
   }
   split_brain_ctr = 0;
}


void Demote( bool self_demote )
{
   int ret;
   char cmd[256];
   char args[64];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   memset(args, 0, sizeof(args));
   if ( self_demote )
      strcpy(args, "MAINTENANCE");

   sprintf(cmd, "%s %s >/dev/null 2>&1", DEMOTE_EVENT_SCRIPT, args);

   demote_in_progress = true;
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu->LogErr("Failed to run demote script...\n");
   }
   split_brain_ctr = 0;
   demote_in_progress = false;
}

void StartAsStandby()
{
   int ret;
   char cmd[256];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   sprintf(cmd, "%s >/dev/null 2>&1", START_STANDBY_SCRIPT);

   ret=system(cmd);
   if (  ret != 0 ) {
      cmu->LogErr("Failed to run standby script...\n");
   }
}


bool IsIPV6(const char *ip)
{
   bool ipv6=false;
   int ctr=0;

   while( *ip != (char )NULL ){
      if ( *ip == ':' )
         ctr++;
      if ( ctr > 1 ){
         ipv6=true;
         break;
      }
      ip++;
   }
   return ipv6;
}

//! \brief HB_Receiver thread. This thread is responsible for processing the heartbeat data received from the peer.
void *HB_Receiver(void *args)
{
   CParams *cp = (CParams *)args;
   int sd;
   int nread;
   struct sockaddr_storage src_addr;
   unsigned char buffer[MAX_DATA_SIZE];
   char s_ip[64];
   int len;
   fd_set rfds;
   int retval;
   struct timeval tv;
   int  opt;
   CReqMsg crm;
   time_t now; //, uptime;
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CClusterInfo *cCI=CClusterInfo::GetInstance();

   //printf("Server...\n");
   //printf("MyIP = %u\n", cp->MyIP());
   //printf("Port = %s\n", cp->Port());
   memset(LISTEN_PORT, 0, sizeof(LISTEN_PORT));
   strcpy(LISTEN_PORT,  cp->Port());
   crm.IP( cp->MyIP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( cCI->MyDBVersion() );

   //cmu.LogMsg(INFO, "Server(): DB Version[%s]", clusterInfo.MyDBVersion() );
   //CStateMachine csm(cp, &crm);

   while( ( sd = CreateUDPServerSocket() ) < 0 ) {
      if ( sd < 0 ) {
         cmu->LogErr("HB_Receiver: failed to create UDP Server...");
         //pthread_exit( NULL );
      }
      sleep(2);
   }

   global_sd = sd;

   // get the current socket settings.
   opt = fcntl( sd, F_GETFL );

   // set the socket to non-blocking...
   fcntl( sd, F_SETFL, opt | O_NONBLOCK );

   while(1){
      cCI->SetServerHB();

      FD_ZERO(&rfds);
      FD_SET(sd, &rfds);

      tv.tv_sec = 3;
      tv.tv_usec = 0;

      retval = select( sd + 1, &rfds, NULL, NULL, &tv);
      if ( global_sd == 0 ) {
         cmu->LogMsg(WARNING, "HB_Receiver() Global socket was closed");
         break;
      }
      if ( retval == 0 ){
         PushEventsToQueue( NONE );
         //printf("**********Server---- select timeout()...\n");
         //clusterInfo.DumpClusterData(stdout);
         continue;
      } else if ( retval < 0 ) {
         if ( errno == EINTR )
            continue;
         else {
            //TODO: do we really want to continue or restart ????
            cmu->LogErr("HB_Receiver: select() failed!!!");
            continue;
         }
      }

      if ( FD_ISSET( sd, &rfds ) ) {
         len=sizeof(src_addr);
         nread = recvfrom( sd, buffer, MAX_DATA_SIZE - 1, 0, (struct sockaddr *)&src_addr, (socklen_t *)&len);

         if ( nread < 0 ) {
            cmu->LogMsg(WARNING, "recvfrom() returns < 0");
            if ( errno == EINTR )
               continue;
         }else if ( nread == 0 ) {
            cmu->LogMsg(INFO, "recvfrom() returns 0");
            continue;
         }else {

            if ( getnameinfo((struct sockaddr *)&src_addr, len, s_ip, sizeof(s_ip), NULL, 0,
                              NI_NUMERICHOST | NI_DGRAM) ){
               perror("getnameinfo()");
               continue;
            }

            cmu->LogMsg(DEBUG, "****Server(): Recv: %d bytes from %s\n", nread, s_ip);
            ProcessRecvData(buffer, nread, cp, s_ip);
            cmu->LogMsg(DEBUG, "ProcessRecvData Done!");
            time(&now);
            //uptime =  now - starttime;
         }
      }
   }

   return (void*)0;
}

//! \brief Returns true if the event is a regular heartbeat
bool IsEventHB(const HA_EVENTS event)
{
   bool b=false;

   switch( event ) {
      case NONE:
      case NPNG:
      case PNG:
      case NHB:
      case HB_NAK:
      case HB_ACK:
      case HB_A2S:
      case HB_REQ:
      case MAINT:
      case F_S_B:
      case HB2_A_L:
      case HB2_A_NL:
      // do nothing... make the compiler happy 
      break;
      case HB_A_L:
      case HB_S_L:
      case HB_S_NL:
      case HB_A_NL:
         b = true;
         break;
   }

   return b;
}

int HB_Sender( CParams *cp, HA_EVENTS event )
{
   // populate the CReqMsg with the parameters that will not change while the program is running
   CReqMsg crm;
   UCHAR buffer[MAX_MSG_SIZE];
   CClusterInfo *cCI=CClusterInfo::GetInstance();
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   int nBytes;
   char temp_status[256];

   cCI->SetClientHB(); //update the heartbeat on clusterinfo for this thread (udp client or HB_Sender). 

   //  Send heartbeat atleast every 3 seconds only by default.
   if ( IsEventHB( event ) && cCI->LastHBSent() < cp->Interval() ) {
      return 1;
   }

   cCI->UpdateLastHBSent();

   crm.IP( cp->MyIP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( cCI->MyDBVersion() );

   memset(temp_status, 0, sizeof(temp_status));
   strncpy(temp_status,  cCI->DBSyncStatus(), sizeof(temp_status) - 1 );
   crm.Assemble(buffer, cCI->MySeqno()+1, cCI->MyRole(), event, ( cCI->MyRole() == ACTIVE_ROLE ? NULL : temp_status ) );


   cmu->LogMsg(DEBUG, "****** DBSyncStatus=[%s]\n", cCI->DBSyncStatus() );

   cmu->LogMsg(DEBUG, "HB_Sender->buffer[%s][%d]\n", buffer, strlen((char *)buffer));
   cCI->MySeqno( cCI->MySeqno() + 1 );

   nBytes = UDPSend( cp,  buffer, strlen((char *)buffer) ); 

   return nBytes;
}


void ProcessRecvData(unsigned char *buffer, const int len, CParams *cp, const char *srcip)
{
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   CClusterInfo *cCI = CClusterInfo::GetInstance();

   if ( cp->Encrypt() ) {  // buffer is encrypted...
      // TODO: provide a better encryption...
      ////*** decrypt the data....
      unsigned char temp_buffer[2048]; // this will temporary hold the clear text.
      int temp_buffer_len;

      cmu->XOR( buffer, len, temp_buffer, &temp_buffer_len );
      temp_buffer[temp_buffer_len] = 0;
      memset( buffer, 0, sizeof(temp_buffer));
      memcpy( buffer, temp_buffer, temp_buffer_len );
      cmu->LogMsg(DEBUG, "(decrypted-recv)buffer[%s] len [%d][%d]", buffer, len, temp_buffer_len );
   }


   if ( cCI->UpdateClusterInfo( (char *)buffer, len ) ) { //** discard the data if failed to update the cluster information.
      //printf("ClusterData has been updated successfully...\n");
      //clusterInfo.DumpClusterData( stdout );

      //printf("Recv: [%s]%d bytes\n", buffer,  len);
      cmu->LogMsg(DEBUG, "ClusterIP: %s, s_addr: %s\n", cp->ClusterIP(), srcip);

      if ( !strcmp(srcip, cp->PeerETH1IP() ) ) {
      }else{
         ///*** check if the source IP of the data is the cluster ip.
         if ( !strcmp(srcip, cp->ClusterIP()) ) {
            PEER_HAS_CLUSTER_IP=true;
         }else{
            PEER_HAS_CLUSTER_IP=false;
         }
      }

      PushEventsToQueue( cCI->Events() );
   }else
      cmu->LogMsg(WARNING, "Invalid Data received! Cluster Data not updated...");
}

void *thread_watcher(void *args)
{
   //CParams *cp = (CParams *)args;
   CClusterInfo *cCI = CClusterInfo::GetInstance();
   CMiscUtils *cmu = CMiscUtils::GetInstance();

   while(1) {
      if ( cCI->LastHBWatcherHB() > 30 )
         cmu->LogMsg(WARNING, "No heartbeat from thread HB_WATCHER...");

      if ( cCI->LastPingerHB() > 30 )
         cmu->LogMsg(WARNING, "No heartbeat from thread LinkTest...");

      if ( cCI->LastServerHB() > 30 )
         cmu->LogMsg(WARNING, "No heartbeat from HB_RECEIVER...");

      if ( cCI->LastClientHB() > 30 )
         cmu->LogMsg(WARNING, "No heartbeat from UDPSender...");

      sleep(5);
   }

   return (void*)0;
}

void *hb_watcher(void *args)
{
   CParams *cp = (CParams *)args;
   CClusterInfo *cCI = CClusterInfo::GetInstance();
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   int expiry;

   while(1){
      cCI->SetHBWatcherHB();
      if ( demote_in_progress ) {
         cmu->LogMsg(INFO, "demote event in progress...");
         
         sleep(1);
      }
      expiry = cp->Timeout() - cCI->PeerLastHB();
      //printf("hb_watcher: expiry = %d, timeout: %d,  lastHB: %d\n", expiry, cp->Timeout(), (int)cCI->PeerLastHB());
      if ( expiry <= 0 ) {
         PushEventsToQueue( NHB );
      }
      sleep(2);
   }

   return (void*)0;
}


#ifdef GLOBAL_UDP_CLIENT 
//! \brief Send UDP Message to the peer.
//! Note: if eth1_enabled is true then always use the peer's eth1 for the
//! destination IP.  eth1_enabled is false by default.
int UDPSend( CParams *cp, UCHAR *buffer, const int len, bool eth1_enabled )
{
   int sd;
   char dest_ip[64];
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   //CClusterInfo *cCI=CClusterInfo::GetInstance();
   char errstr[246];
   struct sockaddr dest_addr;

   int nbytes;

   pthread_mutex_lock ( &udpsend_mutex );

   memset(errstr, 0, sizeof(errstr));

   if ( eth1_enabled ) {
      sd = udpclient_sd1;
      strncpy(dest_ip, cp->PeerETH1IP(), sizeof(dest_ip));
   } else {
      sd = udpclient_sd0;
      strncpy(dest_ip, cp->PeerIP(), sizeof(dest_ip));
   }


   memset(&dest_addr, 0, sizeof(dest_addr));
   if ( AI_FAMILY == AF_INET6 ) {
      struct sockaddr_in6 dest_sock6;
      memset(&dest_sock6, 0, sizeof(dest_sock6));

      if ( inet_pton( AF_INET6, dest_ip, (void *)&dest_sock6.sin6_addr) < 0 ) 
         cmu->LogMsg(WARNING, "AF_INET6: Failed on inet_pton [%s]\n", dest_ip );

      dest_sock6.sin6_family = AF_INET6;
      dest_sock6.sin6_port = htons( atoi( cp->Port() ) );
      memcpy(&dest_addr, (struct sockaddr *)&dest_sock6, sizeof(dest_addr));

   } else {
      struct sockaddr_in dest_sock4;

      //if ( inet_pton( ai->ai_family, cp->MyIP(), &sock4.sin_addr) < 0 ) 
      if ( inet_pton( AF_INET, dest_ip, &dest_sock4.sin_addr) < 0 ) 
         cmu->LogMsg(WARNING, "AF_INET: Failed on inet_pton [%s]\n", dest_ip);

      dest_sock4.sin_family = AF_INET;
      dest_sock4.sin_port = htons(  atoi( cp->Port() ) );
      memcpy(&dest_addr, (struct sockaddr *)&dest_sock4, sizeof(dest_addr));
   }

   if ( cp->Encrypt() ) {  // buffer is encrypted...
      // TODO: provide a better encryption...
      ////*** encrypt the data....
      unsigned char temp_buffer[2048]; // this will hold the encrypted text.
      int temp_buffer_len;

      cmu->XOR( buffer, len, temp_buffer, &temp_buffer_len );  // simple encryption...

      //cmu->LogMsg(DEBUG, "(clear-send)buffer[%s] len [%d][%d]", buffer, len, temp_buffer_len );

      if ( ( nbytes = sendto( sd, temp_buffer, temp_buffer_len, 0,  &dest_addr, sizeof(dest_addr) ) ) < 0 ){
         perror("sendto()");
         cmu->LogMsg(WARNING, "failed to send data! (%s)\n", strerror_r(errno, errstr, sizeof(errstr)));
      }
   } else {
      //cmu->LogMsg(DEBUG, "(clear-send)buffer[%s] len [%d], sd=%d", buffer, len, sd );

      if ( ( nbytes = sendto( sd, buffer, len, 0,  &dest_addr, sizeof(dest_addr) ) ) < 0 ){
         cmu->LogMsg(WARNING, "failed to send data! (%s)\n", strerror_r(errno, errstr, sizeof(errstr)));
      }
   }

   //printf("(UdpSend()) Sent Data: %d bytes\n", nbytes  );
   cmu->LogMsg(DEBUG, "(UdpSend()) Sent Data: %d bytes\n", nbytes  );
   pthread_mutex_unlock ( &udpsend_mutex );

   return nbytes;
}
void InitializeUDPClientSocket(CParams cp)
{
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   cmu->LogMsg("Initializing UDP client sockets...\n");

   if ((udpclient_sd0 = CreateUDPClientSocket( &cp )) <= 0 ) {
      cmu->LogMsg(WARNING, "failed to create udpclient_sd0!\n");
      exit(1); 
   }

   if ( strlen( cp.PeerETH1IP() ) ) {
      if (( udpclient_sd1 = CreateUDPClientSocket( &cp, true )) <= 0 ) {
         cmu->LogMsg(WARNING, "failed to create udpclient_sd1!\n");
         exit(1); 
      }
   }
}

static int CreateUDPClientSocket( CParams *cp, bool eth1_enabled )
{
   int sd=0;
   char dest_ip[64];
   char src_ip[64];
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CClusterInfo *cCI=CClusterInfo::GetInstance();
   int aiErr;
   struct addrinfo *ai;
   struct addrinfo *aiHead;
   struct addrinfo hints;
   char errstr[246];

   memset(errstr, 0, sizeof(errstr));
   memset( &hints, 0, sizeof(hints) );
   hints.ai_family = PF_UNSPEC;
   hints.ai_socktype = SOCK_DGRAM;
   hints.ai_protocol = IPPROTO_UDP;


   memset( dest_ip, 0, sizeof(dest_ip) );
   memset( src_ip, 0, sizeof(src_ip) );

   if ( eth1_enabled ) {
      strncpy(dest_ip, cp->PeerETH1IP(), sizeof(dest_ip));
      strncpy(src_ip, cp->MyETH1IP(), sizeof(src_ip));
   }else {
      if ( cCI->MyRole() == ACTIVE_ROLE )
         PEER_HAS_CLUSTER_IP = false;

      strncpy(dest_ip, cp->PeerIP(), sizeof(dest_ip));
      strncpy(src_ip, cp->MyIP(), sizeof(src_ip));
   }

   cmu->LogMsg("Creating UDP socket to %s:%s\n", dest_ip, cp->Port());
   aiErr = getaddrinfo(dest_ip, cp->Port(), &hints, &aiHead);

   if ( aiErr != 0 ) {
      cmu->LogMsg(WARNING, "Error!!! getaddrinfo(): %s\n", gai_strerror( aiErr ));
      return -1;
   }

   ai = aiHead;
   while( ai ){
      if ((sd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol)) > 0 ) {
         break;
      }else {
         perror("continue");
      }

      ai = ai->ai_next;
      close(sd);
   }


   AI_FAMILY = ai->ai_family;
   int ret=0;
   if ( ai->ai_family == AF_INET6 ) {
      struct sockaddr_in6 sock6;
      memset(&sock6, 0, sizeof(sock6));
      inet_pton( ai->ai_family, src_ip, (void *)&sock6.sin6_addr);
      sock6.sin6_family = AF_INET6;
      sock6.sin6_port = htons(0);
      if ( (ret=bind(sd, (struct sockaddr *)&sock6, sizeof(sock6))) <  0 ) {
         cmu->LogMsg(WARNING, "IPv6 failed to bind to [%s] (%d) (%s)\n", cp->MyIP(), ret, errstr);
         sd = 0;
      }
   } else {
      struct sockaddr_in sock4;

      if ( inet_pton( AF_INET, src_ip, &sock4.sin_addr) < 0 ) 
         cmu->LogMsg(WARNING, "Failed on inet_pton [%s]\n", cp->MyIP());

      sock4.sin_family = AF_INET;
      sock4.sin_port = htons(0);
      if ( (ret=bind(sd, (struct sockaddr *)&sock4, sizeof(sock4))) <  0 ) {
         cmu->LogMsg(WARNING, "IPv4 failed to bind to [%s][%d] (%s)\n", cp->MyIP(), errno,
            strerror_r(errno, errstr, sizeof(errstr) - 1));
         sd = 0;
      }
   } 

   freeaddrinfo( aiHead );
     
   cmu->LogMsg("UDPClientSocket has been created. Socket ID: %d\n", sd);

   return sd;
}

#else

int UDPSend( CParams *cp, UCHAR *buffer, const int len, bool eth1_enabled )
{
   int sd=0;
   char dest_ip[64];
   char src_ip[64];
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CClusterInfo *cCI=CClusterInfo::GetInstance();
   int aiErr;
   struct addrinfo *ai;
   struct addrinfo *aiHead;
   struct addrinfo hints;
   char errstr[246];

   int nbytes;

   memset(errstr, 0, sizeof(errstr));
   memset(&hints, 0, sizeof(hints));
   memset(src_ip, 0, sizeof(src_ip));
   memset(dest_ip, 0, sizeof(dest_ip));

   hints.ai_family = PF_UNSPEC;
   hints.ai_socktype = SOCK_DGRAM;
   hints.ai_protocol = IPPROTO_UDP;

   if ( eth1_enabled ) {
      strncpy(dest_ip, cp->PeerETH1IP(), sizeof(dest_ip));
      strncpy(src_ip, cp->MyETH1IP(), sizeof(src_ip));
   }else {
      if ( cCI->MyRole() == ACTIVE_ROLE )
         PEER_HAS_CLUSTER_IP = false;

      //if ( PEER_HAS_CLUSTER_IP )
      //   strncpy(dest_ip, cp->ClusterIP(), sizeof(dest_ip));
      //else
      strncpy(dest_ip, cp->PeerIP(), sizeof(dest_ip));
      strncpy(src_ip, cp->MyIP(), sizeof(src_ip));
   }


   aiErr = getaddrinfo(dest_ip, cp->Port(), &hints, &aiHead);

   if ( aiErr != 0 ) {
      cmu->LogMsg(DEBUG, "Error!!! getaddrinfo(): %s\n", gai_strerror( aiErr ));
      return -1;
   }

   ai = aiHead;
   while( ai ){
      if ((sd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol)) > 0 ) {
         //printf("valid socket found: %d\n", sd);
         break;
      }else {
         perror("continue");
      }

      ai = ai->ai_next;
      close(sd);
   }

   cmu->LogMsg(DEBUG, "(UdpSend())Host: %s:%s[%s]\n", dest_ip, cp->Port(), (char *)buffer );

   int ret=0;
   if ( ai->ai_family == AF_INET6 ) {
      struct sockaddr_in6 sock6;
      memset(&sock6, 0, sizeof(sock6));

      if ( inet_pton( AF_INET6, src_ip, &sock6.sin6_addr) < 0 ) 
         cmu->LogMsg(WARNING, "Failed on inet_pton [%s]\n", cp->MyIP());

      sock6.sin6_family = AF_INET6;
      sock6.sin6_port = htons(0);

      if ( (ret=bind(sd, (struct sockaddr *)&sock6, sizeof(sock6))) <  0 ) {
         cmu->LogMsg(WARNING, "IPv6 failed to bind to [%s] (%d) (%s)\n", 
            cp->MyIP(), 
            ret,
            strerror_r(errno, errstr, sizeof(errstr) - 1) );
      }
   } else {
      //char str[INET_ADDRSTRLEN];
      struct sockaddr_in sock4;

      //if ( inet_pton( ai->ai_family, cp->MyIP(), &sock4.sin_addr) < 0 ) 
      if ( inet_pton( AF_INET, src_ip, &sock4.sin_addr) < 0 ) 
         cmu->LogMsg(WARNING, "Failed on inet_pton [%s]\n", cp->MyIP());

      sock4.sin_family = AF_INET;
      sock4.sin_port = htons(0);
      if ( (ret=bind(sd, (struct sockaddr *)&sock4, sizeof(sock4))) <  0 ) {
         cmu->LogMsg(WARNING, "IPv4 failed to bind to [%s][%d] (%s)\n", 
            cp->MyIP(), errno, 
            strerror_r(errno, errstr, sizeof(errstr) - 1) );
      }
   } 

   freeaddrinfo( aiHead );
     
   if ( cp->Encrypt() ) {  // buffer is encrypted...
      // TODO: provide a better encryption...
      ////*** encrypt the data....
      unsigned char temp_buffer[2048]; // this will hold the encrypted text.
      int temp_buffer_len;

      cmu->XOR( buffer, len, temp_buffer, &temp_buffer_len );  // simple encryption...

      //cmu->LogMsg(DEBUG, "(clear-send)buffer[%s] len [%d][%d]", buffer, len, temp_buffer_len );

      if ( ( nbytes = sendto( sd, temp_buffer, temp_buffer_len, 0,  ai->ai_addr, ai->ai_addrlen ) ) < 0 ){
         perror("sendto()");
         cmu->LogMsg(WARNING, "failed to send data! (%s)\n", strerror_r(errno, errstr, sizeof(errstr)));
      }
   } else {
      //cmu->LogMsg(DEBUG, "(clear-send)buffer[%s] len [%d], sd=%d", buffer, len, sd );

      if ( ( nbytes = sendto( sd, buffer, len, 0,  ai->ai_addr, ai->ai_addrlen) ) < 0 ){
         cmu->LogMsg(WARNING, "failed to send data! (%s)\n", strerror_r(errno, errstr, sizeof(errstr)));
      }
   }

   //printf("(UdpSend()) Sent Data: %d bytes\n", nbytes  );
   cmu->LogMsg(DEBUG, "(UdpSend()) Sent Data: %d bytes\n", nbytes  );

   close(sd);
   return nbytes;
}

#endif
