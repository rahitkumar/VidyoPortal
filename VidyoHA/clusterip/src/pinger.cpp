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
#include"pinger.h"

extern CClusterInfo clusterInfo;

extern char TIMEOUT_EVENT_SCRIPT[256];

//! \brief Pinger thread. This thread is responsible for checking the connection to the cloud.
//! 
void *pinger(void *args)
{
   CParams *cp = (CParams *)args;
   CMiscUtils cmu;

   char ping_cmd[128];
   int exit_code;
   time_t pingok, now;
   bool alerted = false;

   memset( TIMEOUT_EVENT_SCRIPT, 0, sizeof(TIMEOUT_EVENT_SCRIPT));

   // TODO: use ping6 if NexHopIP() is ipv6.
   if ( IsIPV6(cp->NextHopIP()) )
      snprintf(ping_cmd, 128,"ping6 -c1 -w3 %s > /dev/null 2>&1", cp->NextHopIP());
   else
      snprintf(ping_cmd, 128,"ping -c1 -w3 %s > /dev/null 2>&1", cp->NextHopIP());

   cmu.LogMsg(DEBUG, "pinger(): %s", ping_cmd);
   time( &pingok );

   while(1){
      clusterInfo.SetPingerHB(); // update the hearbeat time
      if ( clusterInfo.State() == MAINTENANCE_STATE || clusterInfo.MyStatus() == MAINTENANCE_STATUS ) {
         //printf("********pinger do nothing...\n");
         sleep(1);
         continue;
      }
      //printf("%s\n", ping_cmd);
      exit_code = system( ping_cmd );
      //cmu.LogMsg(DEBUG, "pinger(): exit_code:%d [%s]", exit_code, ping_cmd);
      //printf("exit_code: %d\n", exit_code);
      if ( exit_code == 0 ){
         if ( clusterInfo.MyStatus() != ONLINE_STATUS ){
            clusterInfo.ResetLastACK();
            clusterInfo.MyStatus( ONLINE_STATUS );
         }
         time( &pingok );
         clusterInfo.PingOK();
         alerted = false;
      }
      else{
         // next hop is unreachable...
         cmu.LogMsg(WARNING, "pinger(): next hop [%s]  is unreachable", cp->NextHopIP());
         time( &now );
         if ( (now - pingok) >= cp->Timeout() ){
            if ( ! alerted ) {
               alerted = true;
               // reach the maximum timeout allowed. Set the node status to OFFLINE and node ROLE to UNKNOWN
               clusterInfo.MyStatus( OFFLINE_STATUS );
               clusterInfo.MyRole( UNKNOWN );
               clusterInfo.State( UNKNOWN_STATE );
               cmu.LogMsg(WARNING, "pinger(): maximum timeout has been reached! Demoting...");
               Demote( false ); // parameter should be true if user wants to put the node to standby...
            }
         }
         sleep( cp->PingInterval() );
         continue;
      }
      sleep( cp->PingInterval() );
   }
}
