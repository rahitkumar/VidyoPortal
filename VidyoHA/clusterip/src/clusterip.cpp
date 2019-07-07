////////////////////////////////////////////////////////////////////////////////
// Filename: clusterip.cpp
// Description:
// Author: Eric Fernandez
// Date Created: 02-06-2012
// Modification History
//    02-06-2012 - Initial Coding (Eric)
////////////////////////////////////////////////////////////////////////////////

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>
#include<string.h>
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

#include"params.h"
#include"clusterip.h"
#include"clusterinfo.h"
#include"reqmsg.h"
#include"pinger.h"
#include"statemachine.h"
#include"miscutils.h"
#include"cluster_signals.h"
#include"checksum.h"

int CMiscUtils::RunLevel=1;
CMiscUtils cmu;

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
const char *version = "1.0.1";
char LISTEN_PORT[8];
int MAX_NEGOTIATE=5;

bool PEER_HAS_CLUSTER_IP=false;

// Initialize the mutex on CStateMachine object. 
pthread_mutex_t CStateMachine::sm_mutex = PTHREAD_MUTEX_INITIALIZER;

pthread_t sender_thread, optional_sender_thread, receiver_thread, pinger_thread, admin_thread, hb_watcher_thread, checksum_thread ;

pthread_attr_t attr;
pthread_mutex_t memory_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t csum_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t  csum_mutex_cond = PTHREAD_COND_INITIALIZER;

CClusterInfo clusterInfo( memory_mutex );
time_t starttime;
bool NewChecksumAvailable = false;


void ProcessRecvData(unsigned char *buffer, const int len, CParams *cp, const char *srcip, CStateMachine csm);


int main(int argc, char **argv)
{
   CParams cParams(progname, version);

   cParams.ParseArgs( argc, argv );

   CMiscUtils::RunLevel = cParams.LogLevel();

   time(&starttime);

   printf("dst-ip [%s]\n", cParams.PeerIP());
   printf("dst-port [%s]\n", cParams.Port());
   printf("my-ip[%s]\n", cParams.MyIP());
   printf("cluster-ip[%s]\n", cParams.ClusterIP());

   InitSignals();
   if ( cParams.Daemonize() ) {
      printf("Daemonizing...\n");
      cmu.Daemonize();
   }
 
   pthread_attr_init(&attr);
   clusterInfo.Initialize( cParams.MyIP(), cParams.ClusterIP(), cParams.Preferred() );
   
   InitializeEventHandler( cParams );
   if ( ! SetDBVersion() ) { // defined inside the checksum.cpp
      cmu.LogMsg("Warning!!! Failed to set the DB Version...");
      clusterInfo.MyDBVersion("UNKNOWN");
   }

   StartClientServerThread(cParams);

   if ( pthread_create(&hb_watcher_thread, &attr, hb_watcher, (void *)&cParams) != 0 ) {
      perror("pthread_create():hb_watcher");
      exit(1);
   }

   if ( pthread_create(&pinger_thread, &attr, pinger, (void *)&cParams) != 0 ) {
      perror("pthread_create():pinger");
      exit(1);
   }
   
   if ( pthread_create(&admin_thread, &attr, admin, (void *)&cParams) != 0 ) {
      perror("pthread_create():admin");
      exit(1);
   }

   if ( pthread_create(&checksum_thread, &attr, checksum, (void *)&cParams) != 0 ) {
      perror("pthread_create():checksum");
      exit(1);
   }

   printf("done...\n");
   pthread_join( receiver_thread, NULL);
}

void InitializeEventHandler(CParams cp)
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

void StartClientServerThread(CParams cParams)
{
   if ( strlen( cParams.PeerETH1IP() ) ) {
      if ( pthread_create(&optional_sender_thread, &attr, OptionalClient, (void *)&cParams) != 0 ) {
         perror("pthread_create():Client");
         exit(1);
      }
   }

   if ( pthread_create(&sender_thread, &attr, Client, (void *)&cParams) != 0 ) {
      perror("pthread_create():Client");
      exit(1);
   }

   if ( pthread_create(&receiver_thread, &attr, Server, (void *)&cParams) != 0 ) {
      perror("pthread_create(): Server");
      exit(1);
   }
}


int CreateUDPClientSocket(char *hostip, unsigned int port)
{
   int sock;

   if ( (sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ){
      perror("socket");
      return -1;
   }
   return sock;
}

int CreateUDPServerSocket()
{
   int sock;
   int aiErr;
   char bind_ip[64];
   struct addrinfo *ai;
   struct addrinfo *aiHead;
   struct addrinfo hints;
   //struct sockaddr_in m_addr;

   memset( &hints, 0, sizeof(hints) );
   hints.ai_flags = AI_PASSIVE;
   hints.ai_family = AF_UNSPEC;
   hints.ai_socktype = SOCK_DGRAM;
   hints.ai_protocol = IPPROTO_UDP;

   // use :: if my_ip is IPv6 otherwise use 0.0.0.0

   memset(bind_ip, 0, sizeof(bind_ip));

   if ( IsIPV6( clusterInfo.MyIP() ) )
      strcpy(bind_ip, "::");
   else
      strcpy(bind_ip, "0.0.0.0");

   if ( (aiErr = getaddrinfo(bind_ip, LISTEN_PORT, &hints, &aiHead)) != 0 ) {
      fprintf(stderr, "Error: %s\n", gai_strerror( aiErr ) );
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
      fprintf(stderr, "no valid socket..\n");
      return -1;
   }

   if (bind(sock, ai->ai_addr, ai->ai_addrlen) <  0) {
      perror("bind...");
      return -1;
   }

   return sock;
}
//! \brief OptionalClient thread. 
//! Sends heartbeat/status to the remote node using the ETH1 interface.
//! Note: This thread will run only when ETH1 is enabled.
void *OptionalClient(void *args)
{
   CParams *cp = (CParams *)args;
   CReqMsg crm;
   UCHAR buffer[512];

   crm.IP( cp->MyETH1IP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( clusterInfo.MyDBVersion() );

   cmu.LogMsg(INFO, "OptionalClient(): DB Version[%s]", clusterInfo.MyDBVersion() );

   while(1) {
      if ( clusterInfo.MyRole() == ACTIVE || clusterInfo.MyRole() == STANDBY ) {
         crm.Assemble( buffer, 1, clusterInfo.MyRole(), OPTIONAL_STATUS, clusterInfo.MyBackupChecksum() );

         UDPSend(cp, buffer, strlen((char *)buffer), true);
      }
      sleep(5);
   }
}

//! \brief Client thread
//! Sends heartbeat/status to the remote node. Acknowledgment will be received from the Server.
void *Client(void *args)
{
   CParams *cp = (CParams *)args;
   CReqMsg crm;

   crm.IP( cp->MyIP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( clusterInfo.MyDBVersion() );

   cmu.LogMsg(INFO, "Client(): DB Version[%s]", clusterInfo.MyDBVersion() );
   CStateMachine csm(cp, &crm);

   while(1) {
      clusterInfo.SetClientHB();
      csm.ProcessStateMachine( clusterInfo.State() );
      if ( clusterInfo.State() == NEGOTIATION_STATE )
         sleep(1);
      else
         sleep( cp->Interval() );
   }
}

//! \brief Server thread.
//! This thread is responsible for updating the status of the shared memory between two threads.
void *Server(void *args)
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
   time_t now, uptime;
   
   //printf("Server...\n");
   //printf("MyIP = %u\n", cp->MyIP());
   memset(LISTEN_PORT, 0, sizeof(LISTEN_PORT));
   strcpy(LISTEN_PORT,  cp->Port());
   crm.IP( cp->MyIP() );
   crm.Priority( cp->Preferred() ? 'P': 'N' );
   crm.DBVersion( clusterInfo.MyDBVersion() );


   cmu.LogMsg(INFO, "Server(): DB Version[%s]", clusterInfo.MyDBVersion() );
   CStateMachine csm(cp, &crm);

   //printf("Server...SERVER\n");
   if ( ( sd = CreateUDPServerSocket() ) < 0 ) {
      perror("CreateUDPServer()");
      pthread_exit( NULL );
   }

   // get the current socket settings.
   opt = fcntl( sd, F_GETFL );
   
   // set the socket to non-blocking...
   fcntl( sd, F_SETFL, opt | O_NONBLOCK );

   while(1){
      //if ( clusterInfo.MyStatus() == OFFLINE_STATUS  || clusterInfo.MyStatus() == MAINTENANCE_STATUS ) {
          //printf("**********Server: Do nothing...\n");
      //    sleep(1);
      //    continue;
     // }

      clusterInfo.SetServerHB();
      FD_ZERO(&rfds);
      FD_SET(sd, &rfds);

      tv.tv_sec = 2;
      tv.tv_usec = 0;

      retval = select( sd + 1, &rfds, NULL, NULL, &tv);
      if ( retval == 0 ){
         //printf("**********Server---- select timeout()...\n");
         //clusterInfo.DumpClusterData(stdout);
         continue;
      } else if ( retval < 0 ) {
         if ( errno == EINTR )
            continue;
         else {
            //TODO: do we really want to continue or restart ????
            perror("select()");
            continue;
         }
      }

      if ( FD_ISSET( sd, &rfds ) ) {
         len=sizeof(src_addr);
         nread = recvfrom( sd, buffer, MAX_DATA_SIZE - 1, 0, (struct sockaddr *)&src_addr, (socklen_t *)&len);

         if ( nread < 0 ) {
            cmu.LogMsg(WARNING, "recvfrom() returns 0");
            if ( errno == EINTR )
               continue;
         }else if ( nread == 0 ) {
            cmu.LogMsg(INFO, "recvfrom() returns 0");
            continue;
         }else {
            if ( clusterInfo.MyStatus() == OFFLINE_STATUS  || clusterInfo.MyStatus() == MAINTENANCE_STATUS ) {
                // read and discard the data... if our status is in maintenenance... otherwise recv data will
                // be queued...
                continue;
            }

            if ( getnameinfo((struct sockaddr *)&src_addr, len, s_ip, sizeof(s_ip), NULL, 0, 
                              NI_NUMERICHOST | NI_DGRAM) ){
               perror("getnameinfo()");
               continue;
            }

            cmu.LogMsg(DEBUG, "****Server(): Recv: %d bytes from %s\n", nread, s_ip);
            ProcessRecvData(buffer, nread, cp, s_ip, csm);
            time(&now);
            uptime =  now - starttime;
         }
      }
   }
}

void ProcessRecvData(unsigned char *buffer, const int len, CParams *cp, const char *srcip, CStateMachine csm)
{
   if ( cp->Encrypt() ) {  // buffer is encrypted...
      // TODO: provide a better encryption...
      ////*** decrypt the data....
      unsigned char temp_buffer[2048]; // this will temporary hold the clear text.
      int temp_buffer_len;

      cmu.XOR( buffer, len, temp_buffer, &temp_buffer_len );  
      memcpy( buffer, temp_buffer, temp_buffer_len );
      cmu.LogMsg(DEBUG, "(decrypted-recv)buffer[%s] len [%d][%d]", buffer, len, temp_buffer_len );
   }  

   
   if ( clusterInfo.UpdateClusterInfo( (char *)buffer, len ) ) { //** discard the data if failed to update the cluster information.
      //printf("ClusterData has been updated successfully...\n");
      //clusterInfo.DumpClusterData( stdout );

      //printf("Recv: [%s]%d bytes\n", buffer,  len);
      cmu.LogMsg(DEBUG, "ClusterIP: %s, s_addr: %s\n", cp->ClusterIP(), srcip);

      if ( !strcmp(srcip, cp->PeerETH1IP() ) ) {
      }else{
         ///*** check if the source IP of the data is the cluster ip. 
         if ( !strcmp(srcip, cp->ClusterIP()) ) {
            PEER_HAS_CLUSTER_IP=true;
         }else{
            PEER_HAS_CLUSTER_IP=false;
         }
      }

      csm.ProcessStateMachine( clusterInfo.State(), clusterInfo.PeerAction() );
   }else
      cmu.LogMsg(WARNING, "Invalid Data received! Cluster Data not updated...");
}

//! \brief Send UDP Message to the peer. 
//! Note: if eth1_enabled is true then always use the peer's eth1 for the
//! destination IP.  eth1_enabled is false by default.
int UDPSend( CParams *cp, UCHAR *buffer, const int len, bool eth1_enabled )
{
   int sd=0;
   char dest_ip[64];
//   unsigned int n_ip;
   int aiErr;
   struct addrinfo *ai;
   struct addrinfo *aiHead;
   struct addrinfo hints;

//   struct sockaddr_in m_addr;
   int nbytes;

   memset( &hints, 0, sizeof(hints) );
   hints.ai_family = PF_UNSPEC;
   hints.ai_socktype = SOCK_DGRAM;
   hints.ai_protocol = IPPROTO_UDP;

   if ( eth1_enabled ) {
         strncpy(dest_ip, cp->PeerETH1IP(), sizeof(dest_ip));
   }else {
      if ( clusterInfo.MyRole() == ACTIVE )
         PEER_HAS_CLUSTER_IP = false;

      if ( PEER_HAS_CLUSTER_IP )
         strncpy(dest_ip, cp->ClusterIP(), sizeof(dest_ip));
      else
         strncpy(dest_ip, cp->PeerIP(), sizeof(dest_ip));
   }



   aiErr = getaddrinfo(dest_ip, cp->Port(), &hints, &aiHead);

   if ( aiErr != 0 ) {
      cmu.LogMsg(DEBUG, "Error!!! getaddrinfo(): %s\n", gai_strerror( aiErr ));
      return -1;
   }

   ai = aiHead;
   while( ai ){
      if ((sd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol)) > 0 ) {
         printf("valid socket found: %d\n", sd);
         break;
      }else {
         perror("continue");
      }

      ai = ai->ai_next;
   }

   freeaddrinfo( aiHead );


//   n_ip =  cp->PeerIP();
//   n_ip = m_addr.sin_addr.s_addr;
//   inet_ntop(AF_INET, (void *)&n_ip, s_ip, INET_ADDRSTRLEN);

   cmu.LogMsg(DEBUG, "(UdpSend())Host: %s:%s[%s]\n", dest_ip, cp->Port(), (char *)buffer );

   if ( cp->Encrypt() ) {  // buffer is encrypted...
      // TODO: provide a better encryption...
      ////*** encrypt the data....
      unsigned char temp_buffer[2048]; // this will hold the encrypted text.
      int temp_buffer_len;

      cmu.XOR( buffer, len, temp_buffer, &temp_buffer_len );  // simple encryption...

      //cmu.LogMsg(DEBUG, "(clear-send)buffer[%s] len [%d][%d]", buffer, len, temp_buffer_len );

      if ( ( nbytes = sendto( sd, temp_buffer, temp_buffer_len, 0, ai->ai_addr, ai->ai_addrlen) ) < 0 ){
         perror("sendto()");
      }
   } else {
      if ( ( nbytes = sendto( sd, buffer, len, 0, ai->ai_addr, ai->ai_addrlen) ) < 0 ){
         perror("sendto()");
      }
   }

   printf("(UdpSend()) Sent Data: %d bytes\n", nbytes  );

   close(sd);
   return nbytes;
}


void *hb_watcher(void *args)
{
   CParams *cp = (CParams *)args;
   int expiry;
   char my_status, my_role, peer_status;
   
   while(1){
      clusterInfo.SetHBWatcherHB(); 
      my_status = clusterInfo.MyStatus();
      my_role = clusterInfo.MyRole();
      peer_status = clusterInfo.PeerStatus();

      //printf("*********  hb_watcher: my_status: %c,  my_role: %c,  peer_status: %c\n",
      //   my_status, my_role, peer_status); 

      if ( my_status == MAINTENANCE_STATUS || peer_status == OFFLINE_STATUS ) {
         sleep(1);
         continue;
      }
      // if the next hop is accessible then proceed... otherwise do nothing...
      expiry = cp->Timeout() - clusterInfo.LastACK();
      if ( expiry < 0 ) {
         // if we are standby and we are not getting ACK from the active then take-over.
         clusterInfo.PeerStatus( OFFLINE_STATUS );
         clusterInfo.PeerRole( UNKNOWN );
         if ( clusterInfo.MyRole() == STANDBY ) {
            printf("hb_watcher: Promoting to ACTIVE...\n");
            clusterInfo.MyRole( ACTIVE );
            clusterInfo.State( STANDBY_TO_ACTIVE );
            //Promote();
         }
      }

      //printf(" >>>>>>>>>>>>>>>>>    hb_watcher: expiry = %d <<<<<<<<<<<<<<<<<<<<<\n", expiry);

      if ( expiry > 0 )
         sleep(expiry);
      else
         sleep(2);
   }
}

 
void Promote()
{
   int ret;
   char cmd[256];

   sprintf(cmd, "%s >/dev/null 2>&1", PROMOTE_EVENT_SCRIPT);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu.LogErr("Failed to run promte script...\n");
   }
}


void SplitBrain()
{
   int ret;
   char cmd[256];
   
   sprintf(cmd, "%s >/dev/null 2>&1", SPLIT_BRAIN_EVENT_SCRIPT);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu.LogErr("Failed to run split brain script...\n");
   }
}

void Demote( bool self_demote )
{
   int ret;
   char cmd[256];
   char args[64];

   memset(args, 0, sizeof(args));
   if ( self_demote )
      strcpy(args, "MAINTENANCE");
   
   sprintf(cmd, "%s %s >/dev/null 2>&1", DEMOTE_EVENT_SCRIPT, args);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu.LogErr("Failed to run demote script...\n");
   }
}

void StartAsStandby()
{
   int ret;
   char cmd[256];

   sprintf(cmd, "%s >/dev/null 2>&1", START_STANDBY_SCRIPT);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu.LogErr("Failed to run standby script...\n");
   }
}


void ResetRebootCounter()
{
   int ret;
   char cmd[256];

   sprintf(cmd, "%s >/dev/null 2>&1", RESET_REBOOT_COUNTER_SCRIPT);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      cmu.LogErr("Failed to run reset_reboot_counter script...\n");
   }
}


void ReadDBCheckSum()
{

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

