#include "statemachine.h"
#include "log.h"
#include "pre_active_script.h"
#include "pre_standby_script.h"

DR_STATES CStartup::Handler(DR_EVENTS ev)
{
   printf("CStartup::Handler()\n");

   LogDebug("Event: %s", CStateMachine::GetInstance()->EventStr(ev));

   DR_STATES next_state = STARTUP;
   
   CStateMachine::GetInstance()->SSHKeyGenerated( false );
   
   switch( ev ) {
      case MAINT:
         next_state = PRE_MAINTENANCE;
         break;
      case NO_DNS:
      case C_PUBIP:
      case REPL_OK:
      case REPL_FAIL:
      case MAINT_END:
      case REPL_STOP:
      case PUBKEY_OK:
      case NONE:
         break;
      case MATCH_PUBIP:
         next_state = PRE_ACTIVE;
         if ( start_preactive_script() ) {
            LogInfo("pre_active script successfully executed...");
         }else {
            LogError("failed to run pre_active script!!!");
         }
         break;
      case NO_MATCH_PUBIP:
         next_state = PRE_STANDBY;
         if ( start_prestandby_script() ) {
            LogInfo("pre_standby script successfully executed...");
         }else {
            LogError("failed to run pre_standby script!!!");
         }
         CStateMachine::GetInstance()->SSHKeyGenerated( true );

         break;
   }

   LogDebug("Next State: %s", CStateMachine::GetInstance()->StateStr( next_state ));
   return CStateMachine::GetInstance()->CurrentState( next_state );
}

