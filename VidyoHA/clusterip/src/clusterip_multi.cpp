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

/////// BASH helper for external events
const char *TIMEOUT_EVENT="timeout_event.sh";
const char *PROMOTE_EVENT="promote_event.sh";
const char *DEMOTE_EVENT="demote_event.sh";

char TIMEOUT_EVENT_SCRIPT[256];
char PROMOTE_EVENT_SCRIPT[256];
char DEMOTE_EVENT_SCRIPT[256];


const int MAX_DATA_SIZE=512;
const char *progname = "clusterip";
const char *version = "1.0.0";
int LISTEN_PORT=8888;
int MAX_NEGOTIATE=5;

bool PEER_HAS_CLUSTER_IP=false;


pthread_t sender_thread, receiver_thread, pinger_thread, admin_thread, hb_watcher_thread;
pthread_attr_t attr;
pthread_mutex_t memory_mutex = PTHREAD_MUTEX_INITIALIZER;

CClusterInfo clusterInfo( memory_mutex );
time_t starttime;


int main(int argc, char **argv)
{
   CParams cParams(progname, version);
   cParams.ParseArgs( argc, argv );

   time(&starttime);

   printf("dst-ip [%d]\n", cParams.DestIP());
   printf("dst-port [%d]\n", cParams.DestPort());

   pthread_attr_init(&attr);
   clusterInfo.Initialize( cParams.MyIP() );
   
   InitializeEventHandler( cParams );

   StartClientServerThread(cParams);

   if ( pthread_create(&hb_watcher_thread, &attr, hb_watcher, (void *)&cParams) != 0 ) {
      perror("pthread_create():hb_watcher");
      exit(1);
   }

   if ( pthread_create(&pinger_thread, &attr, pinger, (void *)&cParams) != 0 ) {
      perror("pthread_create():pinger");
      exit(1);
   }
   
   printf("done...\n");
   pthread_join( sender_thread, NULL);
   pthread_join( receiver_thread, NULL);
}

void InitializeEventHandler(CParams cp)
{
   snprintf(TIMEOUT_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), TIMEOUT_EVENT);
   snprintf(PROMOTE_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), PROMOTE_EVENT);
   snprintf(DEMOTE_EVENT_SCRIPT, 256, "%s/bin/%s", cp.Basedir(), DEMOTE_EVENT);

   // TODO: make this event script is available otherwise do not start...

   printf("Timeout Event: %s\n", TIMEOUT_EVENT_SCRIPT);
   printf("Promote Event: %s\n", PROMOTE_EVENT_SCRIPT);
   printf("Demote Event: %s\n", DEMOTE_EVENT_SCRIPT);
}

void StartClientServerThread(CParams cParams)
{
   if ( pthread_create(&sender_thread, &attr, Client, (void *)&cParams) != 0 ) {
      perror("pthread_create():Client");
      exit(1);
   }

   if ( pthread_create(&receiver_thread, &attr, Server, (void *)&cParams) != 0 ) {
      perror("pthread_create(): Server");
      exit(1);
   }
}


int CreateUDPClientSocket()
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
   struct sockaddr_in m_addr;

   m_addr.sin_family = AF_INET;
   m_addr.sin_addr.s_addr = INADDR_ANY;
   m_addr.sin_port = htons( LISTEN_PORT ); 

   if ( (sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ){
      perror("socket");
      return -1;
   }

   if ( (bind(sock, (struct sockaddr*)&m_addr, sizeof(m_addr))) < 0 ) {
      perror("bind...");
      return -1;
   }

   return sock;
}



//! \brief Server thread.
//! This thread is responsible for updating the status of the shared memory between two threads.
void *Server(void *args)
{
   CParams *cp = (CParams *)args;
   int sd;
   int nread;
   struct sockaddr_in src_addr;
   char buffer[MAX_DATA_SIZE];
   char s_ip[64];
   int len;
   fd_set rfds;
   int retval;
   struct timeval tv;
   int  opt;
   CReqMsg crm;
   time_t now, uptime;
   char action;
   
   //printf("Server...\n");
   //printf("MyIP = %u\n", cp->MyIP());
   crm.IP( cp->MyIP() );

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
      FD_ZERO(&rfds);
      FD_SET(sd, &rfds);

      tv.tv_sec = 5;
      tv.tv_usec = 0;

      retval = select( sd + 1, &rfds, NULL, NULL, &tv);
      if ( retval == 0 ){
         clusterInfo.DumpClusterData(stdout);
         continue;
      }
      else if ( retval < 0 ) {
         if ( errno == EINTR )
            continue;
         else {
            perror("select()");
            continue;
         }
      }

      if ( FD_ISSET( sd, &rfds ) ) { 
         nread = recvfrom( sd, buffer, MAX_DATA_SIZE - 1, 0, (struct sockaddr *)&src_addr, (socklen_t *)&len);
         if ( nread < 0 ) {
            printf("nread < 0 \n");
            if ( errno == EINTR )
               continue;
         }else if ( nread == 0 ) {
            continue;
         }else {
            time(&now);
            uptime =  now - starttime;
            inet_ntop(AF_INET, (void *)&src_addr.sin_addr.s_addr, s_ip, INET_ADDRSTRLEN);
            printf("Recv: [%s]%d bytes from %s\n", buffer,  nread, s_ip);
            ///*** check if the source IP of the data is the cluster ip. 

            if ( clusterInfo.UpdateClusterInfo( buffer, nread ) ) {
               printf("ClusterData has been updated successfully...\n");
               clusterInfo.DumpClusterData( stdout );
               printf("Recv: [%s]%d bytes from %s\n", buffer,  nread, s_ip);
               printf("ClusterIP: %u, s_addr: %u\n", cp->ClusterIP(), src_addr.sin_addr.s_addr );

               if ( src_addr.sin_addr.s_addr == cp->ClusterIP() ) {
                  PEER_HAS_CLUSTER_IP=true;
               }else{
                  PEER_HAS_CLUSTER_IP=false;
               }

               switch( clusterInfo.PeerAction() ) {
               case NEGOTIATE:
                  action = ( Negotiate() ? ACK2NEG : NAK );
                  crm.Assemble( buffer, clusterInfo.MyRole(), action, uptime);
                  UDPSend(cp, buffer, strlen(buffer));
                  break;

               case ACK2NEG:
                  if ( clusterInfo.MyRole() == MASTER )
                     continue;
                  printf("********BECOMING MASTER...\n");
                  //** peer ACKnowledge us to become master...
                  clusterInfo.MyLastRole( clusterInfo.MyRole() );
                  clusterInfo.MyRole( MASTER );
                  clusterInfo.NegotiateCounter(0);
                  clusterInfo.DumpClusterData( stdout );
                  Promote();
                  printf("********END BECOMING MASTER...\n");
                  break;

               case HEARTBEAT:
                  if ( clusterInfo.MyStatus() == OFFLINE_STATUS ) {
                     printf("****IM OFFLINE... NOT RESPONDING TO HeartBeat!!!\n");
                     break;
                  }
                  //*** my_role and peer_role is the same then go back to negotiation.
                  //***
                  if ( clusterInfo.PeerStatus() == ONLINE_STATUS ) {
                     printf("HEARTBEAT    PeerStatus == ONLINE_STATUS [%c:%c]\n",
                         clusterInfo.MyRole(), clusterInfo.PeerRole() );
                     if ( clusterInfo.MyRole() != UNKNOWN &&clusterInfo.PeerRole() == clusterInfo.MyRole() ){
                        clusterInfo.MyRole( UNKNOWN );
                        printf("HEARTBEAT    Setting MyRole = UNKNOWN\n");
                     }
                  }else {
                     printf("HEARTBEAT    PeerStatus != ONLINE_STATUS\n" );
                     //crm.Assemble( buffer, clusterInfo.MyRole(), ACK2HB, uptime);
                     //UDPSend(cp, buffer, strlen(buffer));
                  }

                     crm.Assemble( buffer, clusterInfo.MyRole(), ACK2HB, uptime);
                     UDPSend(cp, buffer, strlen(buffer));

                  break;
               case ACK2HB:
                  clusterInfo.PeerStatus( ONLINE_STATUS );
                  break;

               case NAK:
                  clusterInfo.MyRole( SLAVE );
                  clusterInfo.NegotiateCounter(0);
                  break;
               }
            }else
               printf("Cluster Data not updated...\n");
         }
      }
   }
}


//! \brief Handle Negotiate action.
//! Use this function if client is requesting to become MASTER.  If we are the 'MASTER' then
//! reject the request...
//! TODO: include uptime in deciding who is the master.
bool Negotiate()
{
   char my_last_action = clusterInfo.MyLastAction();
   bool b = false;
   //check our status:

   if ( clusterInfo.MyStatus() == OFFLINE_STATUS )
      return true;

   printf("*************************NEGOTIATE ***************************\n");
   if ( clusterInfo.MyRole() == MASTER ) {
      return false;
   }else {
      if ( my_last_action == NEGOTIATE ) {

         printf("Last Action is negotiate: %u : %u\n", clusterInfo.MyIP(), clusterInfo.PeerIP() ); 
         // both NODEs requesting to be a master... 
         // whoever has the higher IP address will become master...
         if ( clusterInfo.MyIP() > clusterInfo.PeerIP() ) 
            b =  false;
         else
            b =  true;
      }else if ( my_last_action == HEARTBEAT ) {
            b = true;
      }
   }

   return b;
}

//! \brief Client thread
//! Sends heartbeat/status to the remote node. Acknowledgment will be received from the Server.
void *Client(void *args)
{
   int sd;
   CParams *cp = (CParams *)args;
   char s_ip[64];
   unsigned int n_ip;
   struct sockaddr_in m_addr;
   int nbytes;
   CReqMsg crm;
   char buffer[256];
   time_t now, uptime;
   int SLEEP=1;
   int negctr;
  
   n_ip =  cp->DestIP();
   inet_ntop(AF_INET, (void *)&n_ip, s_ip, INET_ADDRSTRLEN);
   printf("Host: %s:%d\n", s_ip, cp->DestPort() );

   m_addr.sin_family = AF_INET;
   m_addr.sin_addr.s_addr = cp->DestIP();
   m_addr.sin_port = htons( cp->DestPort() );

   if ( (sd  = CreateUDPClientSocket()) < 0 ){
      perror("CreateUDPClientSocket()");
      pthread_exit( NULL );
   }
  
   crm.IP( cp->MyIP() );

   n_ip =  cp->MyIP();
   inet_ntop(AF_INET, (void *)&n_ip, s_ip, INET_ADDRSTRLEN);
   printf("MyIP: %s\n", s_ip );

   while(1){
      if ( clusterInfo.MyStatus() == OFFLINE_STATUS ) {
          sleep(1);
          continue;
      }
      memset(buffer, 0, sizeof(buffer));
      time(&now);
      uptime =  now - starttime;
      switch( clusterInfo.MyRole() ) {
      case 'U': // unknown
         printf("UNKNOWN current_role: %c  %c\n", clusterInfo.MyRole(), clusterInfo.MyLastRole());
         negctr = clusterInfo.NegotiateCounter() + 1;
         clusterInfo.NegotiateCounter( negctr );
         if ( negctr > MAX_NEGOTIATE ) {
            // we reach the max negotiation. Let's assume master!!!
            clusterInfo.MyRole( MASTER );
            clusterInfo.NegotiateCounter(0);
            Promote();
            continue; 
         }
         clusterInfo.MyLastAction( NEGOTIATE );
         crm.Assemble( buffer, 'U', NEGOTIATE, uptime);
         SLEEP=2;
         break; 
      case 'S': //slave
         printf("SLAVE current_role: %c  %c:  last_ack: %d\n", clusterInfo.MyRole(), clusterInfo.MyLastRole(), 
                   (int)clusterInfo.LastACK());
         if ( clusterInfo.LastACK() > cp->Timeout() ) {
            if ( clusterInfo.MyStatus() == OFFLINE_STATUS ) 
               printf("NO ACK FROM MASTER... MY STATUS IS OFFLINE TOO\n");
            else{
               clusterInfo.MyLastRole( clusterInfo.MyRole() );
               clusterInfo.MyRole( MASTER );
               clusterInfo.PeerRole( UNKNOWN );
               clusterInfo.PeerStatus( OFFLINE_STATUS );
               printf("Not getting heartbeat from MASTER!!! Promoting...\n");
               Promote();
            }

            continue;
         }
         if ( clusterInfo.MyRole() != clusterInfo.MyLastRole() ) {
            printf("********** DEMOTED *********\n");
         }else{
            clusterInfo.MyLastRole( clusterInfo.MyRole() );
         }
         crm.Assemble( buffer, SLAVE, HEARTBEAT, uptime);
         SLEEP=4;
      break;
      case 'M': //slave
         printf("[%lu]MASTER current_role: %c  %c\n", pthread_self(), clusterInfo.MyRole(), clusterInfo.MyLastRole());
         if ( clusterInfo.MyRole() != clusterInfo.MyLastRole() ) {
            printf("********** PROMOTED *********\n");
        
         }else{
            clusterInfo.MyLastRole( clusterInfo.MyRole() );
         }
         crm.Assemble( buffer, MASTER, HEARTBEAT, uptime);
         SLEEP=4;
         break;
      }


      //if ( ( nbytes = sendto( sd, buffer, strlen(buffer), 0, 
      //                        (struct sockaddr *)&m_addr, sizeof(m_addr)) ) < 0 ){
      //   perror("sendto()");
      // }

      if ( UDPSend( cp, buffer, strlen(buffer)) < 0 ){
         perror("UDPSend");
      }

      //printf("Client...[%lu] %d bytes sent...\n", pthread_self(), nbytes);
      sleep( SLEEP );
   }
}

int UDPSend( CParams *cp, char *buffer, const int len)
{
   int sd;
   char s_ip[64];
   unsigned int n_ip;
   struct sockaddr_in m_addr;
   int nbytes;
  

   m_addr.sin_family = AF_INET;
   m_addr.sin_port = htons( cp->DestPort() );

   if ( PEER_HAS_CLUSTER_IP )
      m_addr.sin_addr.s_addr = cp->ClusterIP();
   else
      m_addr.sin_addr.s_addr = cp->DestIP();

//   n_ip =  cp->DestIP();
   n_ip = m_addr.sin_addr.s_addr;
   inet_ntop(AF_INET, (void *)&n_ip, s_ip, INET_ADDRSTRLEN);
   printf("(UdpSend())Host: %s:%d\n", s_ip, cp->DestPort() );

   if ( (sd  = CreateUDPClientSocket()) < 0 ){
      perror("CreateUDPClientSocket()");
      return sd;
   }

   if ( ( nbytes = sendto( sd, buffer, len, 0, (struct sockaddr *)&m_addr, sizeof(m_addr)) ) < 0 ){
      perror("sendto()");
   }

   close(sd);
   return nbytes;
}


void *hb_watcher(void *args)
{
   CParams *cp = (CParams *)args;
   int expiry;
   
   while(1){
      if ( clusterInfo.PeerStatus() == OFFLINE_STATUS ) {
         sleep(1);
         continue;
      }
      // if the next hop is accessible then proceed... otherwise do nothing...
      expiry = cp->Timeout() - clusterInfo.LastACK();
      if ( expiry < 0 ) {
         // if we are slave and we are not getting ACK from the master then take-over.
         clusterInfo.PeerStatus( OFFLINE_STATUS );
         if ( clusterInfo.MyRole() == SLAVE ) {
            printf("hb_watcher: Promoting to MASTER...\n");
            clusterInfo.MyRole( MASTER );
            Promote();
         }
      }

      printf(" >>>>>>>>>>>>>>>>>    hb_watcher: expiry = %d <<<<<<<<<<<<<<<<<<<<<\n", expiry);

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
      printf("Failed to run promte script...\n");
   }
}


void Demote( bool self_demote )
{
   int ret;
   char cmd[256];
   char args[64];

   memset(args, 0, sizeof(args));
   if ( self_demote )
      strcpy(args, "STANDBY");
   
   sprintf(cmd, "%s %s >/dev/null 2>&1", DEMOTE_EVENT_SCRIPT, args);
 
   ret=system(cmd);
   if (  ret != 0 ) {
      printf("Failed to run demote script...\n");
   }
}
