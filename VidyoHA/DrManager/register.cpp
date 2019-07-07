#include <stdio.h>
#include<sys/stat.h>
#include<sys/types.h>
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
#include<Lmi/Web/LmiJson.h>

#include "register.h"
#include "param.h"
#include "mydefs.h"
#include "log.h"
#include "httptransport.h"
#include "httpclient.h"
#include "utils.h"
#include "statemachine.h"
#include "cluster_fqdn_ip.h"
#include "config.h"
#include "pre_standby_script.h"

extern bool register_thread_running;

static bool  GenerateJsonStaticData(LmiJson *json);
static char *GenerateJsonMsg(const LmiJson *jsonStaticData, const int includeSshKeys);
static char *ReadPublicKeys();
static bool ProcessJsonResponse(const char *resp, int *sshKeysRequired);
static void ProcessHttpResponse(LmiHttpClient *httpClient, LmiHttpRequest *request, LmiHttpResponse *response, LmiHttpConnectionHandle connectionHandle, LmiVoidPtr userData);
static void CondSleep( int delay );

void *Register(void *args)
{
   CParam *cP=CParam::GetInstance();
   CStateMachine *cSM=CStateMachine::GetInstance();
   LmiAllocator* alloc = LmiMallocAllocatorGetDefault();
   LmiJson jsonStaticData;
   char *requestData = NULL;
   CHttpTransport httpTransport;
   int sshPubKeys=1;
   char cluster_ip[MAX_SIZE_IP];

   register_thread_running = true;

   unlockfile( ACTIVE_CONF_LOCK );
   LogInfo("Register thread will start in 5 seconds...");
   sleep(5); //let's use 5 secs for now...
   LogInfo("Register thread started");

   if ( cSM == NULL ) {
      LogError("Failed to get instance of state machine!");
      exit(1);
   }
  
   if ( mkdir(ACTIVE_CONF_DIR, 0700 ) < 0 ) {
      if ( errno == EEXIST ) 
         printf("%s already exist...\n", ACTIVE_CONF_DIR );
      else{
         LogError("Failed to create" ACTIVE_CONF_DIR);
         exit(1);
      }
   }else
      printf("directory created successfully...\n");

   //if ( !httpTransport.Initialize( LMI_TRUE, "./cert.crt", "./pkey.key", "./cacert.crt", LMI_FALSE) ) {
   if ( !httpTransport.Initialize() ) {
      LogError("Failed to initialize httptransport!\n");
      exit(1);
   }

   if ( LmiJsonConstruct(&jsonStaticData, "{}", alloc) == NULL ) {
      LogError("failed to construct json object!\n");
      return (void *)0;
   }

   if ( !GenerateJsonStaticData(&jsonStaticData) ) {
      LogError("Falied to generate jsonStaticData!");
      return (void*)0;
   }

   while(1) {
      if ( cSM->CurrentState() == PRE_ACTIVE || cSM->CurrentState() == ACTIVE ) {
         LogInfo("Current state is: %s, register thread about to exit...", cSM->StateStr(  cSM->CurrentState() ) ); 
         break;
      } 
      sleep(1);

      if ( cSM->SSHKeyGenerated() ) {
         cSM->SSHKeyGenerated( false );
         if ( !start_ssh_keygen() ) {
            LogError("Failed to generate ssh keys!!!");
            sshPubKeys = 0;
         }else{
            sshPubKeys = 1;
            Logger("SSH key generated successfully...");
         }
      }

      DBG("[%lu]Registering NodeId: %s, sshPubKeys: %d\n", pthread_self(), cP->NodeId(), sshPubKeys);
      // if sshPubKeys = 1, then GenerateJsonMsg should include the SSH keys to the request data
      requestData = GenerateJsonMsg(&jsonStaticData, sshPubKeys);
      if ( requestData == NULL ) {
         LogError("Failed to generate requestData!!!");
         continue;
      }else {
         DBG("RequestData: %s\n", requestData);
      }

      CHttpClient httpClient(&httpTransport, (LmiLogCategory )NULL);
      LogDebug("httpClient.Initialize");
      memset(cluster_ip, 0, sizeof(cluster_ip));
      get_cluster_fqdns_ip( cluster_ip );
      // TODO: make http port configurable
      DBG("CLUSTER IP: [%s]\n", cluster_ip);
      LogInfo("CLUSTER IP: [%s]", cluster_ip);
      if ( httpClient.Initialize(cluster_ip, 80, ProcessHttpResponse) == LMI_FALSE ) {
         LogError("Failed to initialize httpClient\n");
         free( requestData );
         continue;
      } else {
	 //printf("HTTP Client successfully initialize!\n");
      }

      LogDebug("About to connect...");
      httpClient.Method("POST");
      if ( httpClient.Connect() == LMI_FALSE ) {
         LogError("httpClient.Connect(): Failed\n");
         free( requestData );
         sleep(2);
         continue;
      }
      LogDebug("After to connect...");

      httpClient.ResourcePath("VidyoPortalStatusService/clusterRegistration.json");
      httpClient.AddHeader( "User-Agent", "drmgr 1.0");
      httpClient.AddHeader( "Accept", "*/*");
      httpClient.AddHeader( "Content-Type", "application/json");
      httpClient.AddHeader( "Connection", "Keep-Alive");
      httpClient.SetBody( requestData );
      free( requestData );
      Logger("Sending Request Data...");
      if ( !httpClient.SendRequest( cP->RegTimeout() ) ) { // default is 3 seconds 
         LogError("Failed to send request data!");
         continue;
      }

      LogDebug("httpResponse Len: %d\n", httpClient.ContentLength());
      LogDebug("StatusCode: %d\n", httpClient.StatusCode());
      if ( httpClient.StatusCode() == 200 ) {
         char *ptr = NULL;
         ptr = httpClient.GetContent();
	      if ( ptr ) { 
            if ( !lockfile(ACTIVE_CONF_LOCK, 3 ))  { // try to acquire lock and set timeout to 3 seconds.
               /// timeout occur..
               LogError("Unable to acquire lock!");
               continue;
            }
	         ProcessJsonResponse(ptr, &sshPubKeys);
            unlockfile( ACTIVE_CONF_LOCK );
	         free( ptr );
	      }
      }
      // teardown http connection
      httpClient.Disconnect();

      //DBG("SLEEP = %d\n", cP->RegInterval());
      CondSleep( cP->RegInterval() );
   }

   register_thread_running = false;

   return (void *)0;
}


static char *GenerateJsonMsg(const LmiJson *jsonStaticData, const int includeSshKeys)
{
   CStateMachine *cSM=CStateMachine::GetInstance();
   LmiAllocator* alloc = LmiMallocAllocatorGetDefault();
   LmiJson jsonData;
   LmiString jsonStr;
   char *keys=NULL;
   char *jsonCStr=NULL;

   if ( cSM == NULL ) {
      LogError("unable to get instance of the state machine!");
      return NULL;
   }

   LmiStringConstructDefault(&jsonStr, alloc);

   LogDebug("Generating Json Message...\n");

   LmiJsonConstructCopy(&jsonData, jsonStaticData);

   if ( includeSshKeys ) {
      if ( cSM->CurrentState() == PRE_STANDBY || cSM->CurrentState() == STANDBY ) {
         if ( ( keys = ReadPublicKeys() ) ) {
            DBG("key [%s]\n", keys);
            LmiJsonObjectAddStrStr(&jsonData, "keys", keys);
            free(keys);
         }
      }else {
         Logger("Skip reading ssh keys. Node is not pre_standby!!!");
         DBG("Skip reading ssh keys. Node is not pre_standby!!!");
      }
   }

   LmiJsonObjectAddStrStr(&jsonData, "nodeState", cSM->CurrentStateStr());

   LmiJsonSerialize(&jsonData, &jsonStr);

   LmiJsonDestruct( &jsonData );
   
   jsonCStr = strdup(LmiStringCStr(&jsonStr));

   LmiStringDestruct( &jsonStr );

   return jsonCStr;
}

static bool  GenerateJsonStaticData(LmiJson *json)
{
   CParam *cP=CParam::GetInstance();

#if 0

   LmiJsonObjectAddStrStr(json, "nodeId", cP->NodeId());
   LmiJsonObjectAddStrStr(json, "nodeVersion", cP->NodeVersion());
   LmiJsonObjectAddStrStr(json, "displayName", cP->DisplayName());
   LmiJsonObjectAddStrStr(json, "publicIp", cP->PublicIp());
   LmiJsonObjectAddStrStr(json, "nativeEth0Ip", cP->Eth0Ip());
   LmiJsonObjectAddStrStr(json, "nativeEth1Ip", cP->Eth1Ip());
#endif

   CConfig *cConfig = CConfig::GetInstance();
   if ( cConfig == NULL )
      return false;

   LmiJsonObjectAddStrStr(json, "nodeId", (cConfig->NamVal("NODEID") ? cConfig->NamVal("NODEID") : "" ) );
   LmiJsonObjectAddStrStr(json, "nodeVersion", (cP->NodeVersion() ? cP->NodeVersion() : "" ) );
   LmiJsonObjectAddStrStr(json, "displayName", ( cConfig->NamVal("DISPLAYNAME") ? cConfig->NamVal("DISPLAYNAME") : "" ) );
   LmiJsonObjectAddStrStr(json, "publicIp", (cConfig->NamVal("PUBLICIP") ? cConfig->NamVal("PUBLICIP") : "" ) );
   LmiJsonObjectAddStrStr(json, "nativeEth0Ip", (cConfig->NamVal("NATIVEETH0IP") ? cConfig->NamVal("NATIVEETH0IP") : "" ) );
   LmiJsonObjectAddStrStr(json, "nativeEth1Ip", (cConfig->NamVal("NATIVEETH1IP") ? cConfig->NamVal("NATIVEETH1IP") : "" ) );

   return true;
}


//! \brief Runs the script that will pack and tar the ssh public keys
//!  of dbsync and root user. Returns a pointer to the alloocated memory
//! that will contain the ssh keys.  
//! Note: Caller must ensure to free up the memory allocated by this function.
static char *ReadPublicKeys()
{
   CParam *cP=CParam::GetInstance();
   FILE *pp; 
   char command[128];
   char keys[10240];
   int nread;

   memset(command, 0, sizeof(command));
   memset(keys, 0, sizeof(keys));

   snprintf(command, SIZE(command), "%s/pack_keys_tar.sh", cP->BinDir());
   
   if ( ( pp =  popen(command, "r") ) == NULL )
      return NULL;
    
   nread=fread(keys, 1, SIZE(keys), pp);

   pclose(pp);

   if ( nread > 0 ) 
      DBG("keys = [%d][%s]\n", nread, keys);
 
   if (nread <= 0 )
      return (char *)NULL;
   else 
      return strdup(keys);
}

static void ProcessHttpResponse(LmiHttpClient *httpClient, LmiHttpRequest *request, LmiHttpResponse *response, LmiHttpConnectionHandle connectionHandle, LmiVoidPtr userData) 
{
   printf("callback started...\n");
   pthread_cond_t *pcond = (pthread_cond_t *)userData;
   pthread_cond_signal( pcond );
   //printf("CallBack-> Location: [%s]\n", LmiHttpResponseGetHeaderStr(response, "Location"));

   printf("callback executed.\n");
}

static bool ProcessJsonResponse(const char *resp, int *sshKeysRequired)
{
   LmiJson jsonResp;
   LmiAllocator*   alloc = LmiMallocAllocatorGetDefault();
   // active node's configuration 
   const char *nodeId = NULL;
   const char *nodeVersion = NULL;
   const char *displayName = NULL;
   const char *nativeEth0Ip = NULL;
   const char *nativeEth1Ip = NULL;
   const char *dataSyncIp = NULL;
   const char *publicIp = NULL;
   const char *assignedNodeNumber = NULL;
   const char *requiredKeys = NULL;
   FILE *fp;
   int status_code;

   DBG("JSON RESP: [%s]\n", resp);
   if ( LmiJsonConstruct(&jsonResp, resp, alloc) == NULL ) {
      LogError("Unable to constuct jsonResp object!");
      return false;
   }

   const LmiJson *jsonStatus = LmiJsonObjectGetValueStr(&jsonResp, "status");
   const LmiJson *jsonActiveConf = LmiJsonObjectGetValueStr(&jsonResp, "clusterMap");

   if ( jsonStatus == NULL ) {
      LogError("Failed to get jsonStatus");
      return false;
   }

   DBG("Status Code: [%s]\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonStatus, "code") )) );
   DBG("Status Message: [%s]\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonStatus, "message") )) );


   //if ( !lockfile(ACTIVE_CONF_LOCK, 3 ))  { // try to acquire lock and set timeout to 3 seconds.
   /// timeout occur..
   //   LogError("Unable to acquire lock!");
   //   return false;
   //}

   status_code = atoi(LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonStatus, "code") )) );
   if ( status_code != 200 )
      return false;

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nodeId") )
      nodeId = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nodeId") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nodeVersion") )
      nodeVersion = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nodeVersion") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "displayName") )
      displayName = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "displayName") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth0Ip") )
      nativeEth0Ip = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth0Ip") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth1Ip") )
      nativeEth1Ip = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth1Ip") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "dataSyncIp") )
      dataSyncIp = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "dataSyncIp") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "publicIp") )
      publicIp = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "publicIp") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "assignedNodeId") )
      assignedNodeNumber = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "assignedNodeNumber") ));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "requiredKeys") ) {
      requiredKeys = LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "requiredKeys") ));
      printf("Required Keys = %s\n", requiredKeys );
      if ( !strcasecmp(requiredKeys, "YES") )
         *sshKeysRequired = 1;
      else
         *sshKeysRequired = 0;
   }else{
      *sshKeysRequired = 0;  // ACTIVE
   }

   if ( (fp = fopen(ACTIVE_CONF, "w")) == NULL ) {
      LogError("failed to open" ACTIVE_CONF);
      return false;
   }
   
   if ( nodeId )
      fprintf(fp, "NODEID=%s\n", nodeId );
   if ( nodeVersion )
      fprintf(fp, "NODEVERSION=%s\n", nodeVersion );
   if ( displayName )
      fprintf(fp, "DISPLAYNAME=%s\n", displayName );
   if ( nativeEth0Ip )
      fprintf(fp, "NATIVEETH0IP=%s\n", nativeEth0Ip );
   if ( nativeEth1Ip )
      fprintf(fp, "NATIVEETH1IP=%s\n", nativeEth1Ip );
   if ( dataSyncIp )
      fprintf(fp, "DATASYNCIP=%s\n", dataSyncIp );
   if ( publicIp )
      fprintf(fp, "PUBLICIP=%s\n", publicIp );
   if ( assignedNodeNumber )
      fprintf(fp, "ASSIGNEDNODEID=%s\n", assignedNodeNumber );

   fflush(fp);
   fclose(fp);

   //if ( !unlockfile(ACTIVE_CONF_LOCK ) ) { // try to acquire lock and set timeout to 3 seconds.
   //   LogError("failed to unlock file!");
   //   return false;
   //}

   return true;
}

static void CondSleep( int delay )
{
   CStateMachine *cSM=CStateMachine::GetInstance();

   while ( delay-- >= 0 ) {
      sleep(1);
      if ( cSM->CurrentState() != cSM->PreviousState() ) 
         break;
   }
}
