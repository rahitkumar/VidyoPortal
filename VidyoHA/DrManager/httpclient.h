#ifndef _HTTP_CLIENT_
#define _HTTP_CLIENT_

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
#include<Lmi/Os/LmiMutex.h>
#include<Lmi/ConferenceServer/Tls/LmiCsTlsTransport.h>
#include<pthread.h>

#include "httptransport.h"


class CHttpClient
{
public:
   CHttpClient( CHttpTransport *cht, LmiLogCategory logcategory);
   ~CHttpClient();

   LmiBool Initialize(const char *host, const unsigned int port, const LmiHttpClientCallback cb);
   LmiBool Connect();
   void Disconnect();
   LmiBool ResourcePath( const char *path );
   LmiBool Method( const char *method );
   LmiBool AddHeader( const char *name, const char *value );
   LmiBool SetBody( const char *body );
   LmiBool SendRequest( int timeout = 5 );
   LmiBool SendRequestAsync();
   int ContentLength();
   int StatusCode();
   char* GetContent();
   const char* GetHeader(const char *str);
   LmiAllocator* Alloc() const { return alloc; }
   
private:
   CHttpTransport *chttpTransport;
   LmiHttpClient httpClient;
   LmiTransport *transport;
   LmiAllocator* alloc;
   LmiString  data;
   LmiHttpClientCallback callback;
   LmiHttpConnectionHandle connectionHandle;
   LmiHttpRequest httpRequest;
   LmiHttpResponse httpResponse;
   LmiString httpHost;
   LmiInt httpPort;
   LmiString httpMethod;
   pthread_mutex_t httpMutex;
   pthread_cond_t httpMutexCond;
   LmiLogCategory log;

///Constructed flag
   LmiBool init_httpHost;
   LmiBool init_httpMethod;
   LmiBool init_httpClient;
   LmiBool init_httpRequest;
   LmiBool init_httpResponse;
};


#endif
