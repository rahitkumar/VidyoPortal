#include<stdio.h>
#include <string.h>
#include<time.h>
#include "statemachine.h"
#include "clusterip.h"
#include "clusterinfo.h"
#include "miscutils.h"
#include "params.h"

HA_STATES CNegotiation::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = NEGOTIATION;
   CStateMachine *cSM = CStateMachine::GetInstance(); 
   CClusterInfo  *cCI = CClusterInfo::GetInstance();
   CMiscUtils    *cmu = CMiscUtils::GetInstance();
   CParams cp;
   time_t now;
   pthread_mutex_t sm_mutex;

   int negctr = cCI->NegotiateCounter() + 1;

   cp = cSM->GetParameters();
   sm_mutex = cSM->GetMutex();

   pthread_mutex_lock( &sm_mutex );

   switch( p_event ) {
      case HB_A2S:
      case MAINT:
      case F_S_B:
      case NONE:
      case PNG:
         /// do the negotiation... 
         if ( m_last_negotiate_time >  0 ) {
            time(&now);
            if ( (now - m_last_negotiate_time) < 2 ) {  // negotiate every 2 seconds only
               break;
            }
         }

         HB_Sender( &cp, HB_REQ );

         time( &m_last_negotiate_time );

         if ( m_max_negotiate-- <= 0 ){
            cmu->LogMsg(WARNING, "Max negotiation has been reached!\n"); 
            next_state =  ACTIVE_NOSTANDBY;
         }else
            cmu->LogMsg(INFO, "m_max_negotiate: %d\n", m_max_negotiate);
         
         break;
      case HB_REQ:
           if ( ( cp.Preferred() && cCI->PeerPriority() != 'P' ) || cCI->MyRole() == ACTIVE_ROLE ) {
              // if we are the preferred node and peer is not then reject the request...
              cmu->LogMsg( INFO, "NegotiationState(): NAK'ing Peer. My Role: %s", cCI->StrRole(cCI->MyRole()));
              cCI->MyLastRole( cCI->MyRole() );
              cCI->MyRole( ACTIVE_ROLE );
              cCI->NegotiateCounter(0);
              cCI->State( ACTIVE_STATE );
              next_state = ACTIVE_WITH_STANDBY;
              HB_Sender( &cp, HB_NAK );
           }else{ // let's check if the peer is the preferred...
              if ( cCI->PeerPriority() == 'P' && !cp.Preferred() ) {
                 next_state = STANDBY; 
                 HB_Sender( &cp, HB_ACK );
              }else {
                 cmu->LogMsg(WARNING, "Warning!!! Both nodes are non-preferred... Calculating the IP Address...\n");
                 cmu->LogMsg(WARNING, "Warning!!! [%s] [%s]", cCI->MyIP(), cCI->PeerIP());
                 if ( strcmp( cCI->MyIP(), cCI->PeerIP() ) > 0 ){
                    cmu->LogMsg(INFO, "This node has higher priority...");
                    cCI->MyLastRole( cCI->MyRole() );
                    cCI->MyRole( ACTIVE_ROLE );
                    cCI->NegotiateCounter(0);
                    cCI->State( ACTIVE_STATE );
                    HB_Sender( &cp, HB_NAK );
                    next_state = ACTIVE_WITH_STANDBY; 
                 }else{
                    cmu->LogMsg(INFO, "Peer Has priority...\n");
                    cCI->MyRole( STANDBY_ROLE );
                    cCI->NegotiateCounter(0);
                    next_state = STANDBY; 
                    cCI->State( STANDBY_STATE );
                    HB_Sender( &cp, HB_ACK );
                 }
              }
           }

         break;
      case NPNG:
         next_state = NEGOTIATION_NOLINK;
         break;
      case NHB:
         next_state = ACTIVE_NOSTANDBY;
         break;
      case HB_A_L:
      case HB_A_NL:
      case HB_NAK:
         next_state = STANDBY;
         break;
      case HB_S_L:
      case HB_S_NL:
         next_state = NEGOTIATION;
         break;
      case HB_ACK:
         next_state = ACTIVE_WITH_STANDBY;
         break;
   }

   if ( next_state == STANDBY || next_state == STANDBY_NOLINK )
      StartAsStandby(); // block access to portal


   pthread_mutex_unlock( &sm_mutex );
   return CStateMachine::GetInstance()->CurrentState( next_state );
}


