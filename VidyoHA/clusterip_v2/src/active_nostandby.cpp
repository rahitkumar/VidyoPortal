#include<stdio.h>
#include "statemachine.h"
#include "clusterip.h"
#include "clusterinfo.h"
#include "params.h"


HA_STATES CActiveNoStandby::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = ACTIVE_NOSTANDBY;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CClusterInfo *cCI = CClusterInfo::GetInstance();

   CParams cp;
   HA_EVENTS hb_event = HB_A_L;

   cp = cSM->GetParameters();

   if ( ! CStateMachine::GetInstance()->Promoted() ) {
      printf("promoting the node...\n");
      CStateMachine::GetInstance()->Promoted( true );
      Promote();
   }

   cCI->DBSyncStatus("");

   switch( p_event ) {
      case HB_A_L: // Split-brain
      case HB_A_NL:  
         //SplitBrain();
         break;
      case NONE:
      case PNG:
      case NHB:
      case HB_ACK:
      case HB_A2S:
         break;
      case HB_REQ:
         hb_event = HB_NAK;
         break;
      case NPNG:
         next_state = ACTIVE_NOLINK_NOSTANDBY;
         break;
      case HB_S_L:
         next_state = ACTIVE_WITH_STANDBY;
         break;
      case HB_S_NL:
         next_state = ACTIVE_WITH_STANDBY_NOLINK;
         break;
      case HB_NAK:  // should not go here.
         break;
      case MAINT:
         Demote( true );
         next_state = MAINTENANCE;
         break;
      case F_S_B:  // Force Standby
         break;
   }

   HB_Sender( &cp, hb_event );
   return CStateMachine::GetInstance()->CurrentState( next_state );
}


