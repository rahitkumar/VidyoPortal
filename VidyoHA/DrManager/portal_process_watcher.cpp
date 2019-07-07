#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include "log.h"
#include "statemachine.h"
#include "utils.h"
#include "param.h"
#include "mydefs.h"
#include "check_process_script.h"

//!\brief The portal_process_watcher thread monitor the mandatory process processes runnning on the ACTIVE node.
//! Check process every 5 secs. Do nothing if the node is not ACTIVE
//
void *portal_process_watcher(void *args)
{
   CParam *cP=CParam::GetInstance();
   const char *defaultdir="/tmp";
   int failed_ctr = 0;
   int MAX_FAILED_CTR=5;
   
   CStateMachine *cSM = CStateMachine::GetInstance();
   
   if ( cSM == NULL ) {
      LogError("Unable to get an instance of StateMachine!");
      exit(1);
   }

   while(1) {
      sleep(5);
      if ( cSM->CurrentState() != ACTIVE )
         continue;

      if ( check_portal_process() ) 
         failed_ctr=0;
      else{
         failed_ctr++;
         LogError("Warning! check_portal_process return error [%d/%d]", failed_ctr, MAX_FAILED_CTR);
      }
      if ( failed_ctr >= MAX_FAILED_CTR )
         LogError("Warning! Rebooting the box due to missing portal process");
   }

   return (void *)0;
}

