#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<signal.h>

#include"cluster_signals.h"
#include"clusterip.h"
#include"miscutils.h"

extern int global_sd;
extern int admin_sd;

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
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   switch(s){
   case SIGINT:  cmu->LogMsg(WARNING, "SigHandler(): SIGINT received!"); break;
   case SIGTERM:  cmu->LogMsg("SigHandler(): SIGTERM received!"); break;
   case SIGHUP:  cmu->LogMsg("SigHandler(): SIGHUP received!"); break;
   case SIGABRT:  cmu->LogMsg("SigHandler(): SIGABRT received!"); break;
   case SIGALRM:  cmu->LogMsg("SigHandler(): SIGALRM received!"); break;
   case SIGQUIT:  cmu->LogMsg("SigHandler(): SIGQUIT received!"); break;
   default:
         cmu->LogMsg("SigHandler(): received signal %d", s); break;
   }

   cmu->LogMsg("SigHandler(): Closing socket %d %dn", global_sd, admin_sd);
   close(global_sd);
   close(admin_sd);
   Demote( true );

   exit(0);
}
