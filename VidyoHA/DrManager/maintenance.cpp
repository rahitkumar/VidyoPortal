#include "statemachine.h"
#include "log.h"

DR_STATES CMaintenance::Handler(DR_EVENTS ev)
{
   LogDebug("CMaintenance::Handler(): event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = MAINTENANCE;
   
   switch( ev ) {
      case MAINT:
         break;
      case MAINT_END:
         next_state = STARTUP;
         break;
      case MATCH_PUBIP:
         break;
      case NO_MATCH_PUBIP:
         break;
      case C_PUBIP:
         break;
      case NO_DNS:
         break;
      case REPL_OK:
         break;
      case REPL_FAIL:
         break;
      case REPL_STOP:
         break;
      case PUBKEY_OK:
         break;
      case NONE:
         break;
   }

   return CStateMachine::GetInstance()->CurrentState( next_state );
}

