#include<pthread.h>
#include<string.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/socket.h>
#include<sys/un.h>
#include<sys/types.h>
#include<unistd.h>
#include<string.h>

#include"log.h"
#include"param.h"
#include"admin.h"
#include"statemachine.h"
#include"mydefs.h"


static void admin_menu( int new_sd, DR_EVENTS *ev );
static int LocalServerSocket( const char * );
static void AdminGetCurrentState(int sd);

void *admin(void *args)
{
   CParam *cp=CParam::GetInstance();
   int sd;
   int new_sd;
   DR_EVENTS last_event=NONE;

   Logger("Admin thread started...\n");

   if ( (sd = LocalServerSocket( cp->AdminSocket() ) ) < 0 ) {
      LogError("Failed to create local socket for admin thread!!!");
      exit(1);
   }
 
   while(1){
      //printf("Waiting for new connection...\n");
      if ( ( new_sd = accept(sd, NULL, NULL) ) < 0 ) {
         if ( errno == EINTR )
            continue;

         LogError("admin_thread: Failed to accept new connection!!!");
         // close sd and reopen
         close( sd );
         if ( (sd = LocalServerSocket( cp->AdminSocket() ) ) < 0 ) {
            LogError("Failed to create local socket for admin thread!!!");
            exit(1);
         }
         continue;
      }

      LogDebug("Received new socket connection...\n");
      admin_menu( new_sd, &last_event );

      close(new_sd);

   }
   return (void *)NULL;
}

static void admin_menu( int new_sd, DR_EVENTS *ev )
{
   CStateMachine *cSM=CStateMachine::GetInstance();
   //unsigned char buf[1024];
   char buf[1024];
   bool loop = true;
   int len;
   
   LogDebug("start of admin_menu");

   while( loop ) {
      memset(buf, 0, sizeof(buf));
      len = recv(new_sd, buf, sizeof(buf)-1, 0);

//LogDebug("adminthread: [%s] [%d]", buf, len);
      LogDebug("adminthread: [%s] [%d]", buf, len);

      if ( len <= 0 ) 
         break;

      if ( !strncmp((char *)buf, "DREVENT-1", len ) ) {
         if ( *ev != MAINT ) Logger("admin: push event: MAINT");
         cSM->PushEventToQueue( MAINT );
         *ev = MAINT;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-2", len ) ) {
         if ( *ev != MAINT_END ) Logger("admin: push event: MAINT_END");
         cSM->PushEventToQueue( MAINT_END );
         *ev = MAINT_END;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-3", len ) ) {
         if ( *ev != MATCH_PUBIP ) Logger("admin: push event: MATCH_PUBIP");
         cSM->PushEventToQueue( MATCH_PUBIP );
         *ev = MATCH_PUBIP;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-4", len ) ) {
         if ( *ev != NO_MATCH_PUBIP ) Logger("admin: push event: NO_MATCH_PUBIP");
         cSM->PushEventToQueue( NO_MATCH_PUBIP );
         *ev = NO_MATCH_PUBIP;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-5", len ) ) {
         if ( *ev != C_PUBIP ) Logger("admin: push event: C_PUBIP");
         cSM->PushEventToQueue( C_PUBIP );
         *ev = C_PUBIP;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-6", len ) ) {
         if ( *ev != NO_DNS ) Logger("admin: push event: NO_DNS");
         cSM->PushEventToQueue( NO_DNS );
         *ev = NO_DNS;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-7", len ) ) {
         if ( *ev != REPL_OK ) Logger("admin: push event: REPL_OK");
         cSM->PushEventToQueue( REPL_OK );
         *ev = REPL_OK;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-8", len ) ) {
         if ( *ev != REPL_FAIL ) Logger("admin: push event: REPL_FAIL");
         cSM->PushEventToQueue( REPL_FAIL );
         *ev = REPL_FAIL;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-9", len ) ) {
         if ( *ev != REPL_STOP ) Logger("admin: push event: REPL_STOP");
         cSM->PushEventToQueue( REPL_STOP );
         *ev = REPL_STOP;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-10", len ) ) {
         if ( *ev != PUBKEY_OK ) Logger("admin: push event: PUBKEY_OK");
         cSM->PushEventToQueue( PUBKEY_OK );
         *ev = PUBKEY_OK;
         break;
      } else if ( !strncmp((char *)buf, "DREVENT-11", len ) ) {
         if ( *ev != ACTIVE_READY ) Logger("admin: push event: ACTIVE_READY");
         cSM->PushEventToQueue( ACTIVE_READY );
         *ev = ACTIVE_READY;
         break;
      } else if ( !strncmp((char *)buf, "DRCMD-S", 7 ) ) {
         LogDebug("getting current state");
         AdminGetCurrentState(new_sd);
         break;
      }  
   }
}

static void AdminGetCurrentState(int sd)
{
   CStateMachine *cSM=CStateMachine::GetInstance();
   char curstate[64];
   int ret;

   memset( curstate, 0, sizeof(curstate) );
   snprintf( curstate, SIZE(curstate), "STATE=%s\n", cSM->CurrentStateStr() );

   if ((ret = send(sd, curstate, strlen(curstate), 0)) < 0  ) {
      LogError("Failed to send data the dr_admin!");
   }
}


static int LocalServerSocket( const char *namedsocket )
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
