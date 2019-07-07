#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<signal.h>

#include"cluster_signals.h"
#include"clusterip.h"

void InitSignals()
{
   struct sigaction sa;
   sa.sa_handler = SigHandler;
   
   sigaction(SIGINT, &sa, NULL);
   sigaction(SIGTERM, &sa, NULL);
   sigaction(SIGSEGV, &sa, NULL);
   sigaction(SIGHUP, &sa, NULL);
   sigaction(SIGABRT, &sa, NULL);
   sigaction(SIGALRM, &sa, NULL);
   sigaction(SIGQUIT, &sa, NULL);
  
   //TODO: SIGUSR1 and SIGUSR2
}


void SigHandler( int s )
{
   switch(s){
   case SIGINT:  printf("SIGINT received!\n"); break;
   case SIGTERM:  printf("SIGTERM received!\n"); break;
   case SIGHUP:  printf("SIGHUP received!\n"); break;
   case SIGABRT:  printf("SIGABRT received!\n"); break;
   case SIGALRM:  printf("SIGALRM received!\n"); break;
   case SIGQUIT:  printf("SIGQUIT received!\n"); break;
   }

   Demote( true );

   exit(0);
}
