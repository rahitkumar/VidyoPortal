#include<stdio.h>
#include<sys/types.h>
#include <sys/wait.h>
#include<sys/stat.h>
#include<unistd.h>
#include<Lmi/Utils/LmiMisc.h>
#include<Lmi/Security/LmiSecurity.h>
#include<Lmi/Security/LmiSecureRandom.h>
#include<Lmi/Utils/LmiUtils.h>
#include<Lmi/Utils/LmiString.h>
#include<Lmi/Utils/LmiTypes.h>
#include<Lmi/Os/LmiOs.h>
#include<Lmi/Utils/LmiString.h>
#include<Lmi/Os/LmiLog.h>
#include<Lmi/Os/LmiFileLogListener.h>
#include<LmiConfigAutoconf.h>
#include<Lmi/Os/LmiMallocAllocator.h>
#include<Lmi/Web/LmiWeb.h>
#include<Lmi/Transport/LmiTransport.h>
#include<Lmi/Web/LmiHttpClient.h>
#include<Lmi/Transport/LmiSocketTimerLoop.h>
#include<Lmi/Transport/LmiTcpTransport.h>
#include<Lmi/Transport/LmiTransportAddress.h>
#include<Lmi/Web/LmiHttpRequest.h>
#include<Lmi/Web/LmiHttpResponse.h>
#include<Lmi/Web/LmiJson.h>
#include<Lmi/ConferenceServer/Utils/LmiCsUtils.h>
#include<Lmi/ConferenceServer/Tls/LmiCsTlsTransport.h>
#include<Lmi/ConferenceServer/Utils/LmiCsOpenSsl.h>
#include<queue>

#include "httpclient.h"
#include "httptransport.h"
#include "log.h"
#include "statemachine.h"
#include "dns_watcher.h"
#include "config.h"
#include "param.h"
#include "utils.h"
#include "register.h"
#include "eventhandler.h"
#include "admin.h"
#include "portal_process_watcher.h"

/////////////////// static functions /////////////////////////////
static void Initialize(int argc, char **argv);
static void LoadConfig();
static void process_wait_status(int status);
static void InitializeSignals();
static void sighandler(int sig);
static void *SighandlerThread(void *args);
#if 0
void *event_sim( void *args );
#endif

//////////////////////////////////////////////////////////////////

CStateMachine *CStateMachine::pInstance = NULL;
CConfig *CConfig::pInstance = NULL;
CParam *CParam::pInstance = NULL;

const char *progname = "drmgr";
const char *version = "1.0.0";

bool register_thread_running = false;

// Global set of  signals for DR
sigset_t dr_signals;


int main(int argc, char **argv)
{
   pthread_t dns_watcher_thread;
   pthread_t register_thread;
   pthread_t event_handler_thread;
   pthread_t admin_thread;
   pthread_t sighandler_thread;
   pthread_t process_watcher_thread;
   pthread_attr_t attr;
   //int status, pid;

   Initialize(argc, argv);
   LoadConfig();
   InitializeSignals();

   CStateMachine *cSM=CStateMachine::GetInstance();

   if ( CParam::GetInstance()->Daemonize() ) {
      printf("Running as daemon...\n");
      Daemonize();
   }

   pthread_attr_init(&attr);

   pthread_create(&dns_watcher_thread, &attr, dns_watcher, (void *)NULL );
   pthread_create(&event_handler_thread, &attr, event_handler, (void *)NULL);
   pthread_create(&admin_thread, &attr, admin, (void *)NULL);
   pthread_create(&sighandler_thread, &attr, SighandlerThread, (void *)NULL);
   pthread_create(&process_watcher_thread, &attr, portal_process_watcher, (void *)NULL);

   sleep(3); // let's give sometime for other thread to determine whether we are the ACTIVE node or not.
   while(1) {
      if ( ! register_thread_running ) {
         if ( cSM != NULL ) {
            if ( cSM->CurrentState() == PRE_ACTIVE || cSM->CurrentState() == ACTIVE ) {
	      // do nothing
            }else{
               printf("creating register thread...\n");
               pthread_create(&register_thread, &attr, Register, (void *)NULL);
            }
         }else
         printf("CSM is null!\n");
      }

      sleep(2);
   }

}

static void Initialize(int argc, char **argv)
{
   CParam *cParam = CParam::Initialize();

   CLog *cLog = CLog::Initialize( "drmgr", LOG_LOCAL3 );

   if ( cParam == NULL ) {
      fprintf(stderr, "Failed to initialize CParam!!!\n");
      exit(1);
   }

   if ( cLog == NULL ) {
      fprintf(stderr, "Failed to initialize CLog!!!\n");
      exit(1);
   }
    
   // make sure we remove any previous lock file...
   unlockfile( DR_STATUS_LOCK );

   cParam->Version( version );
   cParam->ParseArgs(argc, argv);
   // check if the require argument is being provided from the command line otherwise exit
#if 0
   if ( strlen( cParam->NodeId() ) == 0 ) 
      Failed("Invalid argument: Node ID is missing");

   if ( strlen( cParam->NodeVersion() ) == 0 ) 
      Failed("Invalid argument: Node Version is missing");

   if ( strlen( cParam->DisplayName() ) == 0 )
      Failed("Invalid argument: Node Name is missing");

   if ( strlen( cParam->PublicIp() ) == 0 ) 
      Failed("Invalid argument: Public IP is missing");

   if ( strlen( cParam->Eth0Ip() ) == 0 ) 
      Failed("Invalid argument: eth0 IP is missing");
#endif

   if ( cParam->EnableLogInfo() )
      cLog->EnableInfo();
   if ( cParam->EnableLogWarning() )
      cLog->EnableWarn();
   if ( cParam->EnableLogDebug() )
      cLog->EnableDebug();

   cLog->EnableError();

   LogInfo("Started\n");

   if ( LmiUtilsInitialize() == LMI_FALSE ) {
      printf("LmiUtilsInitialize(): failed\n");
      exit(1);
   }

   LmiOsInitialize();
   if(LmiSecurityInitialize() == LMI_FALSE) {
      printf("LmiSecurityInitialize: Failed\n");
      exit(1);
   }

   LmiCsTlsInitialize();

   if(LmiCsOpenSslSetFipsMode(LMI_TRUE) == LMI_TRUE) {
      printf("FIPS OpenSSL version %s self-test - SUCCESS\n", LmiCsOpenSslGetVersion());
   } else {
      printf("FIPS OpenSSL version %s self-test - FAIL\n", LmiCsOpenSslGetVersion());
      exit(1);
   }

   CStateMachine *cSM = CStateMachine::Initialize();
   cSM->InitializeStates();
   cSM->PushEventToQueue( NONE );
}


static void *SighandlerThread(void *args)
{
   int sig;
   int s;

   while(1) {
      s = sigwait(&dr_signals, &sig);
      if (s != 0){
         DBG("sigwait returns %d, errno: %d", s, errno);
         Logger("sigwait returns %d, errno: %d", s, errno);
         continue;
      }
      sighandler(sig);
   }

   return (void *)0;
}

static void sighandler(int sig)
{
   int pid;
   int status;

   switch(sig) {
      case SIGCHLD:
         DBG("SIGCHLD received.\n", sig);
         Logger("SIGCHLD received.\n", sig);
         DBG("SIGCHLD received.(After)\n", sig);
         if ((pid = waitpid(-1, &status, WNOHANG)) > 0 )
            process_wait_status(status);
         else
            if ( errno != ECHILD )
               Logger("Warning! waitpid returns: %d, errno: %d", pid, errno);
         break;
      case SIGUSR1:
         DBG("SIGUSR1 received");
         Logger("SIGUSR1 received");
         break;
      case SIGUSR2:
         DBG("SIGUSR1 received");
         Logger("SIGUSR1 received");
         break;
      case SIGPIPE:
         DBG("SIGPIPE received");
         Logger("SIGPIPE received");
         break;
      default:
         DBG("Signal(%d) received.\n", sig);
         Logger("Signal(%d) received.\n", sig);
   }
}

static void InitializeSignals()
{
   struct sigaction sa;

   sigemptyset(&dr_signals);
   sigaddset(&dr_signals, SIGCHLD);
   sigaddset(&dr_signals, SIGUSR1);
   sigaddset(&dr_signals, SIGUSR2);
   sigaddset(&dr_signals, SIGPIPE);

   if ( pthread_sigmask(SIG_BLOCK, &dr_signals, NULL) != 0 ) {
      LogError("failed to set sigmask - SIG_BLOCK");
      return;
   }

#if 0
   sa.sa_flags = SA_NODEFER; //|SA_NOCLDWAIT;
   sigemptyset(&sa.sa_mask);
   sa.sa_handler = sighandler;
   if (sigaction(SIGCHLD, &sa, NULL) == -1){
      LogError("failed to set sighandler for SIGCHLD");
      return;
   }

   if (sigaction(SIGUSR1, &sa, NULL) == -1){
      LogError("failed to set sighandler for SIGUSR1");
      return;
   }

   if (sigaction(SIGUSR2, &sa, NULL) == -1){
      LogError("failed to set sighandler for SIGUSR2");
      return;
   }

   if (sigaction(SIGPIPE, &sa, NULL) == -1){
      LogError("failed to set sighandler for SIGUSR2");
      return;
   }

#endif

}


static void LoadConfig()
{
   CConfig *cConfig = CConfig::Initialize();

   if ( ! cConfig->Read( CParam::GetInstance()->LocalConfig() ) ) {
      LogError("Unable to open local node configuration!");
      printf("Unable to open local node configuration!\n");
      exit(1);
   }

   LogInfo("NODEID=[%s]", cConfig->NamVal("NODEID"));
   LogInfo("PUBLICIP=[%s]", cConfig->NamVal("PUBLICIP"));
   LogInfo("DISPLAYNAME=[%s]", cConfig->NamVal("DISPLAYNAME"));
   LogInfo("NATIVEETH0IP=[%s]", cConfig->NamVal("NATIVEETH0IP"));
   LogInfo("NATIVEETH1IP=[%s]", cConfig->NamVal("NATIVEETH1IP"));
   LogInfo("PUBLICFQDN=[%s]", cConfig->NamVal("PUBLICFQDN"));
   LogInfo("ADCADDRESS=[%s]", cConfig->NamVal("ADCADDRESS"));

}


#if 0
void *event_sim( void *args )
{
   printf("event_simulator: started\n"); 
   while(1) {
      FILE *fp;
      char buffer[128];
      if ((fp = fopen("/tmp/event", "r")) != NULL ) {
         memset(buffer, 0, sizeof(buffer));
         fgets(buffer, sizeof(buffer), fp); 
         printf("buffer: %d\n", atoi(buffer));
         fclose(fp);
         CStateMachine::GetInstance()->PushEventToQueue( (DR_EVENTS) atoi(buffer) );
         unlink("/tmp/event");
      }
      
      usleep(500000);
   }

   return (void *)0;
}
#endif

static void process_wait_status(int status)
{
   DBG("before WIFEXITED\n");
   if ( WIFEXITED(status) ){
      Logger("child exit successfully: %d\n", WEXITSTATUS(status) );
      printf("child exit successfully: %d\n", WEXITSTATUS(status) );
   }else {
      LogError("child exit with error!!!\n");
      printf("child exit with error!!!\n");
   }
   if ( WIFSIGNALED(status) ){
      Logger("child process terminated by the signal, %d\n", WTERMSIG(status) );
      printf("child process terminated by the signal, %d\n", WTERMSIG(status) );
   }
   DBG("AFTER WIFEXITED\n");
}
  
