#ifndef _HTTP_TRANSPORT_
#define _HTTP_TRANSPORT_

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
#include <Lmi/ConferenceServer/Utils/LmiCsUtils.h>
#include<Lmi/ConferenceServer/Tls/LmiCsTlsTransport.h>
#include <Lmi/ConferenceServer/Utils/LmiCsOpenSsl.h>


class CHttpTransport
{
public:
   CHttpTransport();
   ~CHttpTransport();
   LmiBool Initialize(
                   const LmiBool securedFlag = LMI_FALSE,
                   const char *cert = NULL,
                   const char *privkey = NULL,
                   const char *cacert = NULL,
                   const LmiBool hostnameCheck = LMI_FALSE);
   LmiTransportAddress GetTransportAddress() const;
   LmiTransport* GetTransport() const;
   LmiBool Secured() const;
private:
   LmiBool secured;
   LmiTransport *transport;
   LmiTcpTransport tcpTransport;
   LmiSocketTimerLoop stl;
   LmiAllocator* alloc;
   LmiThreadPriority threadPriority;
   LmiCsTlsTransport tlsTransport;
   LmiTransportAddress transportAddress;

   LmiBool init_stl;
   LmiBool init_tcpTransport;
   LmiBool init_tlsTransport;
   LmiBool init_transportAddress;
};


#endif
