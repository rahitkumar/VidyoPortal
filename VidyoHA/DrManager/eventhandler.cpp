#include <stdio.h>
#include<sys/stat.h>
#include<sys/types.h>
#include"log.h"
#include"statemachine.h"


void *event_handler( void *args )
{
   CStateMachine *cSM=CStateMachine::GetInstance();

   cSM->EventHandler();

   return (void *)0;
}
