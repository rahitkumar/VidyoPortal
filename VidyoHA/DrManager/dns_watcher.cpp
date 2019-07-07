#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include "log.h"
#include "statemachine.h"
#include "utils.h"
#include "param.h"
#include "mydefs.h"
#include "cluster_fqdn_ip.h"

static bool ip_match( char *cluster_ip );

//!\brief The dns_watcher thread is responsible for checking whether the ADC (F5/Radware) 
//! is resolving the CLUSTER_FQDN to our public IP Address.
void *dns_watcher(void *args)
{
   CParam *cP=CParam::GetInstance();
   const char *defaultdir="/tmp";
   char prev_cluster_ip[MAX_SIZE_IP];
   char cluster_ip[MAX_SIZE_IP];
   DR_STATES  prev_state;
   int change_state_threshold = 5;
   int change_state_ctr = change_state_threshold ;
   
   CStateMachine *cSM = CStateMachine::GetInstance();
   
   if ( cSM == NULL ) {
      LogError("Unable to get an instance of StateMachine!");
      exit(1);
   }

   memset( prev_cluster_ip, 0, sizeof(prev_cluster_ip));   
   while(1) {
      memset(cluster_ip, 0, sizeof(cluster_ip));
      if ( ip_match( cluster_ip ) ) {
         printf("***** IP ADDRESS MATCH *****\n");
         if ( cSM->CurrentState() == ACTIVE )
            cSM->PushEventToQueue( MATCH_PUBIP );
         else {
            if ( change_state_ctr >= change_state_threshold ) {
               Logger("Warning! Generating MATCH_PUBIP event!");
               cSM->PushEventToQueue( MATCH_PUBIP );
               change_state_ctr = 0;
            }else{
               change_state_ctr++;
               Logger("Warning! About to change state [%d/%d]", 
                     change_state_ctr, change_state_threshold);
            }
         }
         
      }else {
         if ( memcmp( cluster_ip, prev_cluster_ip, sizeof(cluster_ip)) == 0 ) {
            if ( cSM->CurrentState() != ACTIVE ){
               cSM->PushEventToQueue( NO_MATCH_PUBIP );
               printf(">>>>>>>>>>>>> IP ADDRESS DO NOT MATCH *****\n");
            }else {
               if ( change_state_ctr >= change_state_threshold ) {
                  Logger("Warning! Generating NO_MATCH_PUBIP event!");
                  cSM->PushEventToQueue( NO_MATCH_PUBIP );
                  change_state_ctr = 0;
               }else{
                  change_state_ctr++;
               }
            }
         }else {
            if ( change_state_ctr >= change_state_threshold ) {
               memset( prev_cluster_ip, 0, sizeof(prev_cluster_ip));   
               memcpy( prev_cluster_ip, cluster_ip, sizeof(cluster_ip));
               cSM->PushEventToQueue( C_PUBIP );
               change_state_ctr=0;
            }else {
               change_state_ctr++;
               Logger("Warning! Detecting change in PUBLIC IP [%d/%d]", 
                     change_state_ctr, change_state_threshold);
               printf(">>>>>>>>>>>>> CHANGE PUBLIC IP ADDRESS*****\n");
            }
         }
      }

      sleep( cP->DnsCheckInterval() );
   }
   return (void *)0;
}


//!\brief Returns true if CLUSTER_FQDN resolves the IP Address to our public IP.
//! cluster_ip stores the IP address of the CLUSTER_FQDN.
static bool ip_match( char *cluster_ip )
{
   CParam *cP=CParam::GetInstance();
   char IP[MAX_SIZE_IP];
   int nread;

   memset(IP, 0, sizeof(IP) );
   get_cluster_fqdns_ip( IP );
   //strcpy(IP, "172.0.0.1");

   printf("IP: [%s] PublicIP: [%s]", IP, cP->PublicIp());

   strncpy(cluster_ip, IP, MAX_SIZE_IP);
   if ( strcmp(cP->PublicIp(), IP) == 0 )
      return true;
   else
      return false;
}

