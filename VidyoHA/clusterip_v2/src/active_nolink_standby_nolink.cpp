#include<stdio.h>
#include "statemachine.h"
#include "clusterip.h"

HA_STATES CActiveNoLinkStandbyNoLink::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = ACTIVE_NOLINK_STANDBY_NOLINK;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CParams cp;
   HA_EVENTS hb_event = HB_A_NL;

   cp = cSM->GetParameters();

   if ( ! CStateMachine::GetInstance()->Promoted() ) {
      if ( p_event == HB_NAK ) {  // we're about to become active
         next_state = ACTIVE_WITH_STANDBY_NOLINK;
         return CStateMachine::GetInstance()->CurrentState( next_state );
      }

      CStateMachine::GetInstance()->Promoted( true );
      Promote();
   }

   switch( p_event ) {
      case HB_A_L:  // this should not happen
      case HB_A_NL: // this should not happen
         SplitBrain();
         break;
      case NONE:
      case NPNG:
      case HB_REQ:
      case HB_A2S:
      case HB_NAK:  // this should not happen
      case HB_ACK:  // this should not happen 
      case HB_S_NL:
         break;
      case NHB:
         next_state = ACTIVE_NOLINK_NOSTANDBY;
         break;
      case PNG:
         next_state = ACTIVE_WITH_STANDBY_NOLINK;
         break;
      case HB_S_L:
         next_state = REBOOT;
         break;
      case MAINT:
         Demote( true );
         next_state = MAINTENANCE;
         break;
      case F_S_B:  // Force Standby
         next_state = STANDBY_NOLINK;
         hb_event = HB_A2S;
         break;
   }

   HB_Sender( &cp, hb_event );

   return CStateMachine::GetInstance()->CurrentState( next_state );
}


