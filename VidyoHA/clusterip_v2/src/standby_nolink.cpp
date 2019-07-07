#include<stdio.h>
#include<unistd.h>
#include "statemachine.h"
#include "clusterip.h"
#include "miscutils.h"
#include "params.h"

HA_STATES CStandbyNoLink::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = STANDBY_NOLINK;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   CParams cp;
   HA_EVENTS hb_event = HB_S_NL;

   cp = cSM->GetParameters();

   if ( CStateMachine::GetInstance()->Promoted() ) {
      cmu->LogMsg(INFO, "CStandbyNoLink::Handler()->Demoting...");
      CStateMachine::GetInstance()->Promoted( false );
      Demote( true );
   }

   switch( p_event ) {
      case HB_REQ:
      case F_S_B:
      case NONE:
      case NPNG:
      case HB_A_L:
      case HB_A_NL:
      case HB_S_NL:
      case HB_S_L:
      case HB_NAK:
         break;
      case NHB:
         next_state = ACTIVE_NOLINK_NOSTANDBY;
         break;
      case PNG:
         next_state = STANDBY;
         break;
      case HB_ACK: // should not go here.
         break;
      case MAINT:
         next_state = MAINTENANCE;
         break;
      case HB_A2S:
         next_state = ACTIVE_NOLINK_STANDBY_NOLINK ;
         cmu->LogMsg(INFO, "CStandby::Handler()- Recv HB_A2S - Preparing to takeover...");
         sleep(1);
         ClearEventQueue();
         break;
   }

   HB_Sender( &cp, hb_event );

   return CStateMachine::GetInstance()->CurrentState( next_state );
}


