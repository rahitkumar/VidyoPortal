#include "statemachine.h"
#include "log.h"
#include "pre_active_script.h"

DR_STATES CStandby::Handler(DR_EVENTS ev)
{
   LogDebug("Event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = STANDBY;
   
   switch( ev ) {
      case MAINT:
         next_state = PRE_MAINTENANCE;
         break;
      case MAINT_END:
      case NO_MATCH_PUBIP:
      case REPL_OK:
      case PUBKEY_OK:
      case NONE:
         break;
      case MATCH_PUBIP:
         next_state = PRE_ACTIVE;
         LogInfo("DEBUG (STANDBY) BEFORE RUNNING PRE_ACTIVE_SCRIPT");

         if ( start_preactive_script() ) {
            LogInfo("pre_active script successfully executed...");
         }else {
            LogError("failed to run pre_active script!!!");
         }

         break;
      case C_PUBIP:
      case NO_DNS:
         next_state = STARTUP;
         break;
         break;
      case REPL_FAIL:
      case REPL_STOP:
         next_state = PRE_STANDBY;
         break;
   }

   LogDebug("Next State: %s", CStateMachine::GetInstance()->StateStr( next_state ));

   return CStateMachine::GetInstance()->CurrentState( next_state );
}

