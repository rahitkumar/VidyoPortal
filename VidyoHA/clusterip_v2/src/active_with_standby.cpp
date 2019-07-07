#include<stdio.h>
#include "statemachine.h"
#include "clusterip.h"
#include "params.h"



HA_STATES CActiveWithStandby::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = ACTIVE_WITH_STANDBY;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CParams cp;
   HA_EVENTS hb_event = HB_A_L;

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
      case HB_A_NL: // split brain
      case HB_A_L:
          SplitBrain();
          break;
      case HB_NAK:  // this should not happen
      case HB_ACK:  // this should not happen
      case NONE:
      case PNG:
      case HB_S_L:
      case HB_A2S:
         break;
      case NPNG:
         next_state = ACTIVE_NOLINK_STANDBY_NOLINK;
         break;
      case HB_S_NL:
         next_state = ACTIVE_WITH_STANDBY_NOLINK;
         break;
      case NHB:
         next_state = ACTIVE_NOSTANDBY;
         break;
      case MAINT:
         Demote( true );
         next_state = MAINTENANCE;
         break;
      case F_S_B:  // Force Standby
         hb_event = HB_A2S;
         next_state = STANDBY;
         break;
      case HB_REQ:
         hb_event = HB_NAK;
         break;
   }

   HB_Sender( &cp, hb_event );

   return CStateMachine::GetInstance()->CurrentState( next_state );
}
