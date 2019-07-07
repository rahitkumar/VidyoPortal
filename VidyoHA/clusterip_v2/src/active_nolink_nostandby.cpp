#include<stdio.h>
#include "statemachine.h"
#include "clusterip.h"
#include "clusterinfo.h"

HA_STATES CActiveNoLinkNoStandby::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = ACTIVE_NOLINK_NOSTANDBY;
   CStateMachine *cSM = CStateMachine::GetInstance();
   CClusterInfo *cCI = CClusterInfo::GetInstance();

   CParams cp;
   HA_EVENTS hb_event = HB_A_NL;

   cp = cSM->GetParameters();

   if ( ! CStateMachine::GetInstance()->Promoted() ) {
      printf("promoting the node...\n");
      CStateMachine::GetInstance()->Promoted( true );
      Promote();
   }

   cCI->DBSyncStatus("");

   switch( p_event ) {
      case HB_A_L:  
      case HB_A_NL:
      case NONE:
      case NPNG:
      case NHB:
      case HB_REQ:
      case HB_A2S:
      case HB_ACK:  // this should not happen
         break;
      case PNG:
         next_state = REBOOT;
         break;
      case HB_S_L:
         next_state = REBOOT;
         break;
      case HB_S_NL:
         next_state = ACTIVE_NOLINK_STANDBY_NOLINK;
         break;
      case HB_NAK:
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


