#include<stdio.h>
#include "statemachine.h"

//!\brief CMaintenance Handler.
//!       Returns the next state
HA_STATES CMaintenance::Handler( HA_EVENTS p_event )
{
   HA_STATES next_state = MAINTENANCE;
   
  
   printf("Maintenance state: Do Nothing....\n");
   // clusterip will be stopped by the ha_portal.sh script...

   return CStateMachine::GetInstance()->CurrentState( next_state );
}


