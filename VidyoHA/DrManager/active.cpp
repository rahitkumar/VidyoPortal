#include "statemachine.h"
#include "log.h"

DR_STATES CActive::Handler(DR_EVENTS ev)
{
   LogDebug("Event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = ACTIVE;
   
   switch( ev ) {
      case MAINT:
         next_state = PRE_MAINTENANCE;
         break;
      case MAINT_END:  // this should not happen!
      case REPL_OK:    // this should not happen!
      case REPL_FAIL:  // this should not happen!
      case REPL_STOP:  // this should not happen!
      case PUBKEY_OK:  // this should not happen!
      case NONE:
      case MATCH_PUBIP:
         break;
      case NO_MATCH_PUBIP:
      case C_PUBIP:
      case NO_DNS:
         next_state = STARTUP;
         break;
   }

   LogDebug("Next State: %s", CStateMachine::GetInstance()->StateStr( next_state ));

   return CStateMachine::GetInstance()->CurrentState( next_state );
}

