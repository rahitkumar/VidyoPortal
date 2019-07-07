#include "statemachine.h"
#include "log.h"

DR_STATES CPreActive::Handler(DR_EVENTS ev)
{
   LogDebug("Event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = PRE_ACTIVE;
   
   switch( ev ) {
      case MAINT:
         next_state = PRE_MAINTENANCE;
         break;
      case MAINT_END:
         break;
      case REPL_OK:   // this should not happen.
      case REPL_FAIL: // this should not happen.
      case REPL_STOP: // this should not happen.
      case PUBKEY_OK: // this should not happen
      case NONE:
      case MATCH_PUBIP:
         break;
      case C_PUBIP:
      case NO_DNS:
      case NO_MATCH_PUBIP:
         next_state = STARTUP;
         break;
      case ACTIVE_READY:
         next_state = ACTIVE;
         break;
   }

   LogDebug("Next State: %s", CStateMachine::GetInstance()->StateStr( next_state ));

   return CStateMachine::GetInstance()->CurrentState( next_state );
}

