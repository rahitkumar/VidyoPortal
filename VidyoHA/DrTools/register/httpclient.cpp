#include "httpclient.h"

#include "log.h"

//!\brief Construct a CHttpClient object.
//! cht - a transnport plugin the will be use for http connection.
CHttpClient::CHttpClient( CHttpTransport *cht, LmiLogCategory logcategory) : chttpTransport(cht), log(logcategory)
{
   LogDebug("Default CHttpClient Constructor\n");
   alloc = LmiMallocAllocatorGetDefault();
   init_httpHost = LMI_FALSE;
   init_httpMethod = LMI_FALSE;
   init_httpClient = LMI_FALSE;
   init_httpRequest = LMI_FALSE;
   init_httpResponse = LMI_FALSE;
   pthread_mutex_init(&httpMutex, NULL);
   pthread_cond_init(&httpMutexCond, NULL);
}

//!\bried Defaulr CHttpClient destructor
CHttpClient::~CHttpClient()
{
   LogDebug("Default CHttpClient Destructor\n");
   pthread_mutex_destroy(&httpMutex);
   pthread_cond_destroy(&httpMutexCond);
   if ( init_httpHost ) {
      LogDebug("Destructing httpHost...\n");
      LmiStringDestruct(&httpHost);
   }
   if ( init_httpMethod ) {
      LogDebug("Destructing httpMethod...\n");
      LmiStringDestruct(&httpMethod);
   }
   if ( init_httpClient ) {
      LogDebug("Destructing httpClient...\n");
      LmiHttpClientDestruct(&httpClient);
   }
   if ( init_httpRequest ) {
      LogDebug("Destructing httpRequest...\n");
      LmiHttpRequestDestruct(&httpRequest);
   }
   if ( init_httpResponse ) {
      LogDebug("Destructing httpResponse...\n");
      LmiHttpResponseDestruct(&httpResponse);
   }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//!\brief Initialize HTTP client. Return true on success otherwise returns false.
// host - IP address or FQDN of the HTTP Server.
// port - Port number
// cb   - Http callback function.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

LmiBool CHttpClient::Initialize(const char *host, const unsigned int port, const LmiHttpClientCallback cb)
{
   //httpMutex = PTHREAD_MUTEX_INITIALIZER;
   //httpMutexCond = PTHREAD_COND_INITIALIZER;
   //pthread_mutex_init(&httpMutex, NULL);
   //pthread_cond_init(&httpMutexCond, NULL);

   if ( LmiStringConstructCStr(&httpHost,  host, alloc) == NULL ) {
      return LMI_FALSE;
   }
   init_httpHost = LMI_TRUE;

   if ( LmiStringConstructCStr(&httpMethod, "GET", alloc) == NULL ) {
      return LMI_FALSE;
   }
   init_httpMethod = LMI_TRUE;
   
   if ( LmiMutexConstruct(&httpMutex) == NULL ) {
      return LMI_FALSE;
   }
   httpPort = port;

   LmiTransportAddress lta = chttpTransport->GetTransportAddress();

   if ( LmiHttpClientConstruct( &httpClient, chttpTransport->GetTransport(), &lta, cb, &httpMutexCond, alloc ) == NULL ) {
      LogError("failed to construct httpClient!\n");
      return LMI_FALSE;
   }
   
   init_httpClient = LMI_TRUE;

   return LMI_TRUE;
}

//!\brief Initiate http connection to the HTTP Server.
LmiBool CHttpClient::Connect()
{
   return LmiHttpClientCreateConnection(&httpClient, &httpHost, httpPort, &connectionHandle);
}

//!\bried Disconnect http connection
void CHttpClient::Disconnect()
{
   LmiHttpClientDestroyConnection(&httpClient, connectionHandle);
}

//!\brief Set the HTTP metthod. (GET, PUT, CONNECT)
LmiBool CHttpClient::Method( const char *method )
{
   if ( LmiStringAssignCStr( &httpMethod, method ) == NULL )
      return LMI_FALSE;
   else
      return LMI_TRUE;
}

//!\brief Set the Resource path of the URI
LmiBool CHttpClient::ResourcePath( const char *path )
{
   char *ptr;
   LmiBool ret = LMI_TRUE;

   if ( httpPort == 443 ) {
      if ( asprintf(&ptr, "%s://%s/%s", (chttpTransport->Secured() ? "https" : "http"), LmiStringCStr(&httpHost), path) < 0 )
         return LMI_FALSE;
   } else {
      if ( asprintf(&ptr, "%s://%s:%d/%s", (chttpTransport->Secured() ? "https" : "http"), LmiStringCStr(&httpHost), httpPort, path) < 0 )
         return LMI_FALSE;
   }

   if (!LmiHttpRequestConstructUri( &httpRequest, ptr, LmiStringCStr(&httpMethod), alloc) ) {
      LogError("LmiHttpRequestConstruct failed\n");
      ret = LMI_FALSE;
   }
   init_httpRequest = LMI_TRUE;
   free(ptr);
   return ret;
}

//!\brief Insert HTTP header to the http request
//! name - valid http header
//! value - header value
LmiBool CHttpClient::AddHeader( const char *name, const char *value )
{
   if ( LmiHttpRequestAddHeader( &httpRequest, name, value ) == NULL )
      return LMI_FALSE;
   else
      return LMI_TRUE;
}

LmiBool CHttpClient::SetBody(const char *body)
{
   LmiString data;
   char length[64];

   if ( LmiStringConstructCStr(&data, body, alloc) == NULL )
      return LMI_FALSE;

   if ( LmiHttpRequestSetData(&httpRequest, &data) == NULL ){
      LmiStringDestruct(&data);
      return LMI_FALSE;
   }
   sprintf(length, "%d", strlen(body)); 
   AddHeader("Content-Length", length);

   LmiStringDestruct(&data);
   return LMI_TRUE;
}

//!\brief Send HTTP Request. This function will block until response is received or timeout occurs.
//! Default timeout is 5 seconds.
LmiBool CHttpClient::SendRequest( int timeout )
{
   LmiBool status = LMI_TRUE;
   if ( !LmiHttpResponseConstruct(&httpResponse, alloc) ) {
      LogError("LmiHttpResponseConstruct: failed!\n");
      return LMI_FALSE;
   }

   init_httpResponse = LMI_TRUE;

   if ( LmiHttpClientSendRequest(&httpClient, connectionHandle, &httpRequest, &httpResponse) == LMI_FALSE ) {
      LogError("LmiHttpClientSendRequest: Failed\n");
      return LMI_FALSE;
   }
  
   struct timespec tv;
   int ret;
   tv.tv_sec = time(NULL) + timeout;
   tv.tv_nsec = 0;
   pthread_mutex_lock(&httpMutex);
   LogDebug("waiting for response...\n");
   //pthread_cond_wait(&httpMutexCond, &httpMutex);
   ret = pthread_cond_timedwait(&httpMutexCond, &httpMutex, &tv);
   if ( ret != 0 ) { 
      //perror("pthread_cond_timedwait");
      if ( ret == ETIMEDOUT ) {
         LogError("SendRequest(): timeout");
      } else if ( ret == EINVAL ) {
        LogError("SendRequest(): EINVAL");
      } else if ( ret == EPERM ) {
        LogError("SendRequest(): EPERM");
      }else {
        LogError("SendRequest(): unknown error occured");
      }
      status = LMI_FALSE;
   }
   pthread_mutex_unlock( &httpMutex );

   return status;
}

//!\brief Send HTTP Request asynchronously. This function returns immediately.
LmiBool CHttpClient::SendRequestAsync()
{
   LmiBool status = LMI_TRUE;
   if ( !LmiHttpResponseConstruct(&httpResponse, alloc) ) {
      LogError("LmiHttpResponseConstruct: failed!\n");
      return LMI_FALSE;
   }

   init_httpResponse = LMI_TRUE;

   if ( LmiHttpClientSendRequest(&httpClient, connectionHandle, &httpRequest, &httpResponse) == LMI_FALSE ) {
      LogError("LmiHttpClientSendRequest: Failed\n");
      return LMI_FALSE;
   }
  
   return status;
}

//!\brief Returns the length of the response from HTTP server.
int CHttpClient::ContentLength()
{
   return LmiHttpResponseGetContentLength(&httpResponse);
}

//!\brief Returns the http status code.
int CHttpClient::StatusCode()
{
   return LmiHttpResponseGetStatusCode(&httpResponse);
}

//!\brief Returns the content of the http response from the HTTP Server.
char* CHttpClient::GetContent()
{
   char *resp=(char *)malloc( ContentLength() + 1 );
   memset(resp, 0, ContentLength() + 1 );
   memcpy(resp,  LmiHttpResponseGetContent(&httpResponse), ContentLength());
   //printf("CHttpClient::GetContent(): [%s]\n", LmiHttpResponseGetContent(&httpResponse));
   //printf("Resp: [%s]\n", resp);
   //return LmiHttpResponseGetContent(&httpResponse);
   return resp;
}

//!\brief Returns the value of the http header specified by str.
const char* CHttpClient::GetHeader(const char *str)
{
   return LmiHttpResponseGetHeaderStr(&httpResponse, str);
}
