#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<clusterip.h>
#include "statemachine.h"
#include "miscutils.h"

//!\brief Reboot Handler, this is the initial state when the box comes up.
//!       Returns the next state
HA_STATES CReboot::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = REBOOT;
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   CStateMachine *cSM = CStateMachine::GetInstance();

   cmu->LogMsg(WARNING, "CReboot::Handler(): State: %s Previous: %s, Event: %s  Previous: %s\n",
         cSM->CurrentStateStr(), cSM->PreviousStateStr(),
         cSM->CurrentEventStr(), cSM->PreviousEventStr());

   cmu->LogMsg(WARNING, "CReboot::Handler(): SYSTEM REBOOTING !!!");
   sleep(1);
   system("/sbin/reboot");
   sleep(1);
   exit(0);

   /// should not go here... 
   
   switch( p_event ) {
      case NHB:
      case HB_A2S:
      case MAINT:
      case F_S_B:
      case NONE:
      case HB_S_L:
      case HB_S_NL:
      case HB_NAK:
      case HB_ACK:
         break;
      case HB_A_L:
      case HB_A_NL:
         StartAsStandby(); // block access to portal
         next_state = STANDBY_NOLINK;
         break;
      case NPNG:
         next_state = NEGOTIATION_NOLINK;
         break;
      case HB_REQ:
      case PNG:
         next_state = NEGOTIATION;
         break;
   }

   return CStateMachine::GetInstance()->CurrentState( next_state );
}


