#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<sys/stat.h>
#include<unistd.h>
#include<stdio.h>
#include<stdarg.h>
#include <stdlib.h>
#include <getopt.h>

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

#include "httpclient.h"
#include "httptransport.h"

#define DBG(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
fprintf(stdout, "%s(%d):%s: %s\n",__FILE__,__LINE__,__func__, __buf__); fflush(stdout); }

#define ERR(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
fprintf(stderr, "%s(%d):%s: %s\n",__FILE__,__LINE__,__func__, __buf__); fflush(stderr); }

struct ReqDataStruct{
   int nodeId;
   int httpsPort;
   char nodeVersion[64];
   char displayName[256];
   char activeNodeIp[256];
   char dataSyncIp[256];
   char publicIp[256];
   char publicFqdn[256];
   char nativeEth0Ip[64];
   char nativeEth1Ip[64];
   char adcip[256];
   char authKey[256];
   char replStatus[256];
};

bool secured = false;
int  http_port = 80;
char domain_cert[256];
char domain_pkey[256];
char ca_certs[256];
int reg_timeout = 5;

bool debug = false;
const char *progname = "drreg";
const char *version = "1.0.0";
LmiAllocator* alloc = LmiMallocAllocatorGetDefault();

void help();
char *GenerateJsonMsg(ReqDataStruct *rds);
bool ProcessJsonResponse(const char *resp);
void ProcessHttpResponse(LmiHttpClient *httpClient, LmiHttpRequest *request, LmiHttpResponse *response, LmiHttpConnectionHandle connectionHandle, LmiVoidPtr userData);
void Initialize();
void parseArgs(const int argc, char **argv, ReqDataStruct **rds);
void sighandler(int signo);
void init_signals();

int main(int argc, char **argv)
{
   char *requestData = NULL;
   CHttpTransport httpTransport;
   ReqDataStruct *req;

   parseArgs(argc, argv, &req);

   Initialize();

   init_signals();
   alarm(reg_timeout + 1);

   if ( secured ) {
      if ( !httpTransport.Initialize( LMI_TRUE, domain_cert, domain_pkey, ca_certs, LMI_FALSE) ) {
         ERR("Failed to initialize secured httptransport!\n");
         exit(1);
      }
   }else {
      if ( !httpTransport.Initialize() ) {
         ERR("Failed to initialize httptransport!\n");
         exit(1);
      }
   }


   requestData = GenerateJsonMsg( req );

   CHttpClient httpClient(&httpTransport, (LmiLogCategory )NULL);
   if ( httpClient.Initialize(req->activeNodeIp, http_port, ProcessHttpResponse) == LMI_FALSE ) {
      ERR("Failed to initialize httpClient\n");
      free( requestData );
      exit(1);
   } 

   if (debug) DBG("About to connect...");
   httpClient.Method("POST");
   if ( httpClient.Connect() == LMI_FALSE ) {
         ERR("httpClient.Connect(): Failed\n");
         free( requestData );
         exit(1);
   }
   if (debug) DBG("After to connect...");

   httpClient.ResourcePath("VidyoPortalStatusService/clusterRegistration.json");
   httpClient.AddHeader( "User-Agent", "drreg 1.0");
   httpClient.AddHeader( "Accept", "*/*");
   httpClient.AddHeader( "Content-Type", "application/json");
   httpClient.AddHeader( "Connection", "Keep-Alive");
   if ( strlen(req->authKey) > 0 ) {
      char *auth=NULL;
      if ( asprintf(&auth, "Basic %s", req->authKey) < 0 ) {
         ERR("failed to allocation string for authentication!");
         exit(1);
      }
      httpClient.AddHeader( "Authorization", auth);
      free(auth);
      
   }
   httpClient.SetBody( requestData );
   free( requestData );
   if (debug) DBG("Sending Request Data...");
   if ( !httpClient.SendRequest( 5 ) ){ // default is 3 seconds 
         ERR("Failed to send request data!");
         exit(1);
   }


   if (debug) DBG("httpResponse Len: %d\n", httpClient.ContentLength());
   if (debug) DBG("StatusCode: %d\n", httpClient.StatusCode());

   if ( httpClient.StatusCode() == 200 ) {
      char *ptr = NULL;
      ptr = httpClient.GetContent();
	   if ( ptr ) {
	      ProcessJsonResponse(ptr);
	      free( ptr );
	   }
      // teardown http connection
      httpClient.Disconnect();
   }else {
      printf("STATUSCODE=%d\n", httpClient.StatusCode() );
   }

   alarm(0);
}

char *GenerateJsonMsg(ReqDataStruct *rds)
{
   LmiJson json;
   LmiString jsonStr;
   char *jsonCStr=NULL;

   if ( LmiJsonConstruct(&json, "{}", alloc) == NULL ) {
      ERR("failed to construct json object!\n");
   }

   LmiStringConstructDefault(&jsonStr, alloc);

   if (debug) DBG("Generating Json Message...\n");

   LmiJsonObjectAddInt64(&json, "nodeId", rds->nodeId);
   LmiJsonObjectAddStrStr(&json, "nodeVersion", rds->nodeVersion);
   LmiJsonObjectAddStrStr(&json, "displayName", rds->displayName);
   LmiJsonObjectAddStrStr(&json, "publicIp", rds->publicIp);
   LmiJsonObjectAddStrStr(&json, "nativeEth0Ip", rds->nativeEth0Ip);
   LmiJsonObjectAddStrStr(&json, "nativeEth1Ip", rds->nativeEth1Ip);
   LmiJsonObjectAddStrStr(&json, "replStatus", rds->replStatus);

   LmiJsonSerialize(&json, &jsonStr);

   LmiJsonDestruct( &json );
   
   jsonCStr = strdup(LmiStringCStr(&jsonStr));

   LmiStringDestruct( &jsonStr );

   return jsonCStr;
}

void ProcessHttpResponse(LmiHttpClient *httpClient, LmiHttpRequest *request, LmiHttpResponse *response, LmiHttpConnectionHandle connectionHandle, LmiVoidPtr userData) 
{
   if (debug) printf("callback started...\n");
   pthread_cond_t *pcond = (pthread_cond_t *)userData;
   pthread_cond_signal( pcond );
   //printf("CallBack-> Location: [%s]\n", LmiHttpResponseGetHeaderStr(response, "Location"));

   if (debug) printf("callback executed.\n");
}

bool ProcessJsonResponse(const char *resp)
{
   LmiJson jsonResp;
   LmiAllocator*   alloc = LmiMallocAllocatorGetDefault();
   int statCode;

   if (debug) DBG("JSON RESP: [%s]\n", resp);
   if ( LmiJsonConstruct(&jsonResp, resp, alloc) == NULL ) {
      ERR("Unable to constuct jsonResp object!");
      return false;
   }

   const LmiJson *jsonStatus = LmiJsonObjectGetValueStr(&jsonResp, "status");
   const LmiJson *jsonActiveConf = LmiJsonObjectGetValueStr(&jsonResp, "clusterMap");

   if ( jsonStatus == NULL ) {
      ERR("Failed to get jsonStatus");
      return false;
   }

   statCode = atoi(LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonStatus, "code") )) );
   if (debug) DBG("Status Code: [%d]\n", statCode);
   if (debug) DBG("Status Message: [%s]\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonStatus, "message") )) );


   printf("STATUSCODE=%d\n", statCode );
   if ( statCode != 200 )
      return false; 

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "error") )
      printf("ERROR=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "error") )));


   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nodeId") )
      printf("NODEID=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nodeId") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nodeVersion") )
      printf("NODEVERSION=%s\n",  LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nodeVersion") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "displayName") ) 
      printf("DISPLAYNAME=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "displayName") )) );

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth0Ip") )
      printf("NATIVEETH0IP=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth0Ip") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth1Ip") )
      printf("NATIVEETH1IP=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "nativeEth1Ip") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "dataSyncIp") )
      printf("DATASYNCIP=%s\n",  LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "dataSyncIp") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "publicIp") )
      printf("PUBLICIP=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "publicIp") )));

   if ( LmiJsonObjectGetValueStr(jsonActiveConf, "dbVersion") )
      printf("DBVERSION=%s\n", LmiStringCStr(LmiJsonString(  LmiJsonObjectGetValueStr(jsonActiveConf, "dbVersion") )));


   return true;
}


void Initialize()
{
   if ( LmiUtilsInitialize() == LMI_FALSE ) {
      printf("LmiUtilsInitialize(): failed\n");
      exit(1);
   }

   LmiOsInitialize();
   if(LmiSecurityInitialize() == LMI_FALSE) {
      printf("LmiSecurityInitialize: Failed\n");
      exit(1);
   }

   LmiWebInitialize();
   LmiCsTlsInitialize();

   if(LmiCsOpenSslSetFipsMode(LMI_TRUE) == LMI_TRUE) {
      if (debug) printf("FIPS OpenSSL version %s self-test - SUCCESS\n", LmiCsOpenSslGetVersion());
   } else {
      if (debug) printf("FIPS OpenSSL version %s self-test - FAIL\n", LmiCsOpenSslGetVersion());
      exit(1);
   }
}

void parseArgs(const int argc, char **argv, ReqDataStruct **prds)
{
   ReqDataStruct rds;

   int c;
   int optind = 0;

   static struct option long_options[] = {
      {"active-node-ip", 1, 0, 'A' },
      {"auth-key", 1, 0, 'a' },
      {"node-id", 1, 0, 'n' },
      {"node-version", 1, 0, 'N' },
      {"display-name", 1, 0, 'd' },
      {"public-ip", 1, 0, 'p' },
      {"adc-ip", 1, 0, 'c' },
      {"eth0-ip", 1, 0, 'i' },
      {"eth1-ip", 1, 0, 'I' },
      {"http-port", 1, 0, 'P' },
      {"ca-cert", 1, 0, 'C' },
      {"server-cert", 1, 0, 'S' },
      {"server-pkey", 1, 0, 'K' },
      {"repl-status", 1, 0, 'r' },
      {"timeout", 1, 0, 't' },
      {"secured", 0, 0, 's' },
      {"version", 0, 0, 'v' },
      {"help", 0, 0, 'h' },
      {"debug", 0, 0, 'D' },
      { 0, 0, 0, 0}
   };

   memset(domain_cert, 0, sizeof(domain_cert));
   memset(domain_pkey, 0, sizeof(domain_pkey));
   memset(ca_certs, 0, sizeof(ca_certs));

   strcpy(domain_cert, "/opt/vidyo/etc/ssl/certs/domain-server.crt");
   strcpy(domain_pkey, "/opt/vidyo/etc/ssl/private/domain-server.key");
   strcpy(ca_certs, "/opt/vidyo/etc/ssl/certs/cacert.root");

   memset(&rds, 0, sizeof(rds));
   while(1) {
      if ( (c = getopt_long(argc, argv, "P:K:C:S:a:c:A:n:N:d:p:i:I:r:t:vhDs",  long_options, &optind )) < 0 )
         break;

      switch(c) {
         case 'n':
            rds.nodeId = atoi(optarg);
            break;
         case 'N':
            strncpy(rds.nodeVersion, optarg, sizeof(rds.nodeVersion));
            break;
         case 'd':
            strncpy(rds.displayName, optarg, sizeof(rds.displayName));
            break;
         case 'a':
            strncpy(rds.authKey, optarg, sizeof(rds.authKey));
            break;
         case 'A':
            strncpy(rds.activeNodeIp, optarg, sizeof(rds.activeNodeIp));
            break;
         case 'p':
            strncpy(rds.publicIp, optarg, sizeof(rds.publicIp));
            break;
         case 'c':
            strncpy(rds.adcip, optarg, sizeof(rds.adcip));
            break;
         case 'i':
            strncpy(rds.nativeEth0Ip, optarg, sizeof(rds.nativeEth0Ip));
            break;
         case 'I':
            strncpy(rds.nativeEth1Ip, optarg, sizeof(rds.nativeEth1Ip));
            break;
         case 'r':
            strncpy(rds.replStatus, optarg, sizeof(rds.replStatus));
            break;
         case 'P':
            http_port=atoi(optarg);
            break;
         case 't':
            reg_timeout=atoi(optarg);
            break;
         case 'C':
            strncpy(ca_certs, optarg, sizeof(ca_certs));
            break;
         case 'K':
            strncpy(domain_pkey, optarg, sizeof(domain_pkey));
            break;
         case 'S':
            strncpy(domain_cert, optarg, sizeof(domain_cert));
            break;
         case 's':
            secured = true;
            break;
         case 'v':
            printf("%s version: %s (%s)\n", progname, version, LmiCsOpenSslGetVersion());
            exit(0);
            break;
         case 'h':
            help();
            break;
         case 'D':
            debug = true;
            break;
      }
   }

   if (debug) printf("nodeId [%d]\n", rds.nodeId);
   if (debug) printf("nodeVersion [%s]\n", rds.nodeVersion);
   if (debug) printf("displayName [%s]\n", rds.displayName);
   if (debug) printf("publicIp [%s]\n", rds.publicIp);
   if (debug) printf("nativeEth0Ip [%s]\n", rds.nativeEth0Ip);

   if ( rds.nodeId < 0 ||
        strlen(rds.nodeVersion) == 0 ||
        strlen(rds.displayName) == 0 ||
        strlen(rds.publicIp) == 0 ||
        strlen(rds.activeNodeIp) == 0 ||
        strlen(rds.nativeEth0Ip) == 0 ) {
      help();
   }
   

   /*
   rds.nodeId = 15;
   strcpy(rds.nodeVersion, "3.4.5.010");
   strcpy(rds.displayName, "Node 1");
   strcpy(rds.dataSyncIp, "172.16.1.2");
   strcpy(rds.publicIp, "67.67.1.2");
   strcpy(rds.nativeEth0Ip, "127.0.0.1");
   strcpy(rds.nativeEth1Ip, "127.1.1.0");
   */

   *prds = (ReqDataStruct *)malloc(sizeof(rds));
   memcpy( *prds, &rds, sizeof(rds));
}

void help()
{
   printf("Usage: %s [OPTION]\n", progname);
   printf("Send registration information to the active side\n\n");
   printf("Option(s):\n");
   printf(" -a, --auth-key       : Authorization key for basic authentication.\n");
   printf(" -n, --node-id        : The site ID to be used during registration.\n");
   printf(" -N, --node-version   : The portal version of the site.\n");
   printf(" -d, --display-name   : The site name to be displayed on the status page.\n");
   printf(" -A, --active-node-ip : Public IP of the Active node.\n");
   printf("                        Registration will go to this IP\n");
   printf(" -p, --public-ip      : Server public Ip\n");
   printf(" -c, --adc-ip         : ADC(Application Delivery Controller) IP\n");
   printf(" -i, --eth0-ip        : native ETH0 IP\n");
   printf(" -I, --eth1-ip        : native ETH1 IP (optional)\n");
   printf(" -r, --repl-status    : Replication status (colon separated fields)\n"); 
   printf(" -s, --secured        : Use TLS connection\n");
   printf(" -P, --http-port      : HTTP port\n");
   printf(" -t, --timeout        : Registration timeout in seconds. (Default 5)\n");
   printf(" -C, --ca-cert        : Trusted root certificates. (Default - /opt/vidyo/etc/ssl/certs/cacert.root)\n");
   printf(" -S, --server-cert    : Domain server certficate. (Default - /opt/vidyo/etc/ssl/certs/domain-server.crt)\n");
   printf(" -K, --server-pkey    : Domain server private key. (Default - /opt/vidyo/etc/ssl/private/domain-server.key)\n");
   
   printf(" -D, --debug          : Print debug message\n");
   printf(" -v, --version        : Display version\n");
   printf(" -h, --help           : Display help\n\n");
   exit(0);
}


void init_signals()
{
   struct sigaction sa;

   sa.sa_handler = sighandler;
   sigaction(SIGALRM, &sa, NULL); 
}

void sighandler(int signo)
{
   switch(signo) {
   case SIGALRM:
      ERR("Timout Error! Aborting");
      exit(1);   
     break;
   default:
     ERR("Caught signal: %d\n", signo);
   }
}
