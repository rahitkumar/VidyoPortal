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

#include"params.h"
#include"miscutils.h"
#include"clusterip.h"
#include"clusterinfo.h"
#include"link_watcher.h"


extern char TIMEOUT_EVENT_SCRIPT[256];

//! \brief LinkWatcher thread. This thread is responsible for checking the connection to the cloud.
//! 
void *LinkWatcher(void *args)
{
   CParams *cp = (CParams *)args;
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   CClusterInfo *cCI = CClusterInfo::GetInstance();

   char ping_cmd[128];
   int exit_code;
   time_t pingok, now;
   bool png_alert = false;
   bool npng_alert = false;

   memset( TIMEOUT_EVENT_SCRIPT, 0, sizeof(TIMEOUT_EVENT_SCRIPT));

   // TODO: use ping6 if NexHopIP() is ipv6.
   if ( IsIPV6(cp->NextHopIP()) )
      snprintf(ping_cmd, 128,"ping6 -c1 -w3 %s > /dev/null 2>&1", cp->NextHopIP());
   else
      snprintf(ping_cmd, 128,"ping -c1 -w3 %s > /dev/null 2>&1", cp->NextHopIP());

   //cmu->LogMsg(DEBUG, "LinkWatcher(): %s", ping_cmd);
   time( &pingok );

   while(1){
      cCI->SetPingerHB(); // update the hearbeat time

      //printf("%s\n", ping_cmd);
      exit_code = system( ping_cmd );
      //cmu.LogMsg(DEBUG, "pinger(): exit_code:%d [%s]", exit_code, ping_cmd);
      //printf("exit_code: %d\n", exit_code);
    
      if ( exit_code == 0 ) {
         time( &pingok );
         cCI->PingOK();
         if ( !png_alert ){
            PushEventsToQueue( PNG );
            png_alert = true;
            npng_alert = false;
         }
      }else {
         time( &now );
         cmu->LogErr("LinkTest failed: %d, timeout: %d\n", now-pingok, cp->Timeout() );
         if ( (now - pingok) >= cp->Timeout() ){
            if ( !npng_alert ) {
               npng_alert = true;
               png_alert = false;
               PushEventsToQueue( NPNG );
               cmu->LogErr("LinkTest failed: Sending NHB event to event Q!");
            }
         }
      }

      sleep( cp->PingInterval() );
   }

   return (void *)0;
}
