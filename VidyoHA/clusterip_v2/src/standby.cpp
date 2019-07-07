#include<stdio.h>
#include<unistd.h>
#include "statemachine.h"
#include "clusterip.h"
#include "miscutils.h"
#include "params.h"

HA_STATES CStandby::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = STANDBY;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CParams cp;
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   HA_EVENTS hb_event = HB_S_L;

   cp = cSM->GetParameters();

   if ( CStateMachine::GetInstance()->Promoted() ) {
      cmu->LogMsg(INFO, "CStandby::Handler()->Demoting...");
      CStateMachine::GetInstance()->Promoted( false );
      Demote( true );
   }

   switch( p_event ) {
      case F_S_B:
      case HB_REQ:
      case NONE:
      case PNG:
      case HB_A_L:
      case HB_S_L:
      case HB_S_NL:
      case HB_A_NL:
      case HB_NAK:
      case HB_ACK:
         break;
      case NPNG:
         next_state = STANDBY_NOLINK;
         break;
      case NHB:
         next_state = ACTIVE_NOSTANDBY;
         break;
      case MAINT:
         next_state = MAINTENANCE;
         break;
      case HB_A2S:
         next_state = ACTIVE_WITH_STANDBY;
         cmu->LogMsg(INFO, "CStandby::Handler()- Recv HB_A2S - Preparing to takeover..."); 
         sleep(1);
         ClearEventQueue();
         break;
   }

   HB_Sender( &cp, hb_event );

   return CStateMachine::GetInstance()->CurrentState( next_state );
}


