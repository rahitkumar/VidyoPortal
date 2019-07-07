#include "statemachine.h"
#include "log.h"
#include "pre_active_script.h"

DR_STATES CPreStandby::Handler(DR_EVENTS ev)
{
   LogDebug("Event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = PRE_STANDBY;
   
   switch( ev ) {
      case MAINT:
         next_state = PRE_MAINTENANCE;
         break;
      case MATCH_PUBIP:
         LogInfo("DEBUG (PreStandby) BEFORE RUNNING PRE_ACTIVE_SCRIPT");

         if ( start_preactive_script() ) {
            LogInfo("pre_active script successfully executed...");
         }else {
            LogError("failed to run pre_active script!!!");
         }

         next_state = PRE_ACTIVE;
         break;
      case MAINT_END: /// this should not happen
      case NO_MATCH_PUBIP:
      case REPL_FAIL:
      case REPL_STOP:
      case PUBKEY_OK:
      case NONE:
         break;
      case C_PUBIP:
      case NO_DNS:
         next_state = STARTUP;
         break;
      case REPL_OK:
         next_state = STANDBY;
         break;
   }

   LogDebug("Next State: %s", CStateMachine::GetInstance()->StateStr( next_state ));

   return CStateMachine::GetInstance()->CurrentState( next_state );
}

