#include<pthread.h>
#include<string.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/socket.h>
#include<sys/un.h>
#include<sys/types.h>
#include<unistd.h>
#include<string.h>

#include"statemachine.h"
#include"clusterip.h"
#include"clusterinfo.h"
#include"miscutils.h"

extern pthread_mutex_t csum_mutex;
extern pthread_cond_t csum_mutex_cond;


void admin_menu( int s );
int LocalServerSocket( const char * );
extern int global_sd;
extern int admin_sd;

void *admin(void *args)
{
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CParams *cp = (CParams *)args;
   int sd;
   int new_sd;


   printf("Admin thread started...\n");
   if ( (sd = LocalServerSocket( cp->AdminSocket() ) ) < 0 ) {
      cmu->LogErr("Failed to create local socket for admin thread!!!");
      exit(1);
   }

   admin_sd = sd;
 
   while(1){
      printf("Waiting for new connection...\n");
      if ( ( new_sd = accept(sd, NULL, NULL) ) < 0 ) {
         if ( errno == EINTR )
            continue;

         cmu->LogErr("admin_thread: Failed to accept new connection!!!");
         // close sd and reopen
         close( sd );
         if ( (sd = LocalServerSocket( cp->AdminSocket() ) ) < 0 ) {
            cmu->LogErr("Failed to create local socket for admin thread!!!");
            exit(1);
         }
         continue;
      }

      printf("Received new socket connection...\n");
      admin_menu( new_sd );

      close(new_sd);

   }
   return (void *)NULL;
}

void admin_menu( int new_sd )
{
   unsigned char buf[1024];
   bool loop = true;
   int len;
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   CClusterInfo *cCI = CClusterInfo::GetInstance();
   CStateMachine *cSM = CStateMachine::GetInstance();
   
   while( loop ) {
      memset(buf, 0, sizeof(buf));
      len = recv(new_sd, buf, sizeof(buf)-1, 0);

      //cmu->LogMsg(INFO,"cip_admin: [%s] [%d]", buf, len);

      if ( len <= 0 ) 
         break;


      if ( !strncmp((char *)buf, "CINFO-C", 7 ) ) {
          cCI->UpdateLastSyncTime();
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-P", 7 ) ) {
          cCI->ClusterDataToSocket( new_sd );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-W", 7 ) ) {
          cCI->ClusterDataToSocketWide( new_sd );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-S", 7 ) ) {  // ** set node to maintenance
          if ( cCI->MyStatus() == MAINTENANCE_STATUS )
             break;

          cCI->MyStatus( MAINTENANCE_STATUS );
          cCI->State( MAINTENANCE_STATE );

          close(global_sd);
          global_sd = 0;
          printf("about to go maintenance...\n");
          PushEventsToQueue( MAINT );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-O", 7 ) ) {  // ** remove node from maintenance
          if ( cCI->MyStatus() == MAINTENANCE_STATUS ) {
             cCI->Reset();
             cCI->State( ONLINE_STATE );
          }
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-C", 7 ) ) {  // ** send DB checksum to peer
          pthread_cond_signal( &csum_mutex_cond );
          if ( cCI->MyStatus() == MAINTENANCE_STATUS ) {
          }
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-F", 7 ) ) {  // ** force ACTIVE node to go STANDBY
          switch( cSM->CurrentState() ) {
             case ACTIVE_NOLINK_STANDBY_NOLINK:
             case ACTIVE_WITH_STANDBY:
             case ACTIVE_WITH_STANDBY_NOLINK:
                PushEventsToQueue( F_S_B );
                cmu->LogMsg( INFO, "Force STANDBY initiated by the user...");
                break;
             default:
                cmu->LogMsg( INFO, "Force STANDBY is not allowed at this time...");
          } 

          break;
      }

      cmu->LogMsg(DEBUG, "Admin: REPL_STATUS [%s] %d\n", (char *)buf, strlen((char *)buf));
      if ( !strncmp((char *)buf, "REPL_STATUS-", 12 ) ) { 
         char sync_status[64];
         memset(sync_status, 0, sizeof(sync_status));
         memcpy(sync_status, buf + 12, sizeof(sync_status)-1);

         cmu->LogMsg(DEBUG, "Admin: REPL_STATUS ====> [%s] %d\n", (char *)buf, strlen((char *)buf));
         cCI->DBSyncStatus(sync_status);
      }

      //cmu->LogMsg(DEBUG, "[%s] %d\n", (char *)buf, strlen((char *)buf));

      if ( !strcmp((char *)buf, "CEVENT-1" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( NPNG );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-2" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( PNG );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-3" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( NHB );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-4" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_A_L );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-5" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_S_L );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-6" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_S_NL );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-7" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_A_NL );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-8" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_NAK );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-9" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_ACK );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-10" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_REQ );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-11" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( HB_A2S );
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-12" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( MAINT );
         close(global_sd);
         global_sd = 0;
         break;
      }

      if ( !strcmp((char *)buf, "CEVENT-13" ) ) { 
         cCI->UpdatePeerLastHB();
         PushEventsToQueue( F_S_B );
         break;
      }

      if ( strlen((char *)buf) > 0 )
         break;
   }
}


int LocalServerSocket( const char *namedsocket )
{
   struct sockaddr_un address;
   int svr_sd;

   svr_sd = socket(PF_UNIX, SOCK_STREAM, 0);
   if ( svr_sd < 0 ) {
      perror("socket!");
      exit(1);
   }

   unlink( namedsocket );

   memset(&address, 0, sizeof(struct sockaddr_un));
   address.sun_family = AF_UNIX;
   strncpy(address.sun_path, namedsocket, sizeof(address.sun_path)-1);

   if ( bind(svr_sd, (struct sockaddr *)&address, sizeof(struct sockaddr_un)) < 0 ) {
      perror("bind!");
      return -1;
   }

   if ( listen(svr_sd, 5) < 0 ) {
      perror("listen");
      return -1;
   }

   return svr_sd;
}
