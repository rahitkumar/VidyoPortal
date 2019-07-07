#include "httptransport.h"
#include "log.h"

//!\brief Default CHttpTransport constructor
CHttpTransport::CHttpTransport()
{
   LogDebug("Default CHttpTransport Constructor\n");
   secured = LMI_FALSE;
   alloc = LmiMallocAllocatorGetDefault();
   threadPriority = (LmiThreadGetPriorityMin() + LmiThreadGetPriorityMax()) / 2;
   init_stl = LMI_FALSE;
   init_tcpTransport = LMI_FALSE;
   init_tlsTransport = LMI_FALSE;
   init_transportAddress = LMI_FALSE;
}

//!\brief Default CHttpTransport destructor
CHttpTransport::~CHttpTransport()
{
   LogDebug("Default CHttpTransport Destructor\n");
   if ( init_transportAddress )
      LmiTransportAddressDestruct( &transportAddress );
   if ( init_tlsTransport )
      LmiCsTlsTransportDestruct( &tlsTransport );
   if ( init_tcpTransport )
      LmiTcpTransportDestruct( &tcpTransport );
   if ( init_stl )
      LmiSocketTimerLoopDestruct( &stl );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//!\brief Initialize HTTP transport. Return true on success otherwise returns false.
// securedFlag = set to true for HTTPS connection. (Default false).
// cert - filename containing the certificate. 
// privkey - filename containing the private key.
// cacert - filename containing the list of trusted root certificate.
// hostnameCheck - set to tur if hostname should match with the subject or SAN of the httpServer certificate. (Default false)
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
LmiBool CHttpTransport::Initialize(
                   const LmiBool securedFlag, 
                   const char *cert, 
                   const char *privkey,
                   const char *cacert,
                   const LmiBool hostnameCheck)
{
   secured = securedFlag;

   if (!LmiSocketTimerLoopConstruct(&stl, "HttpTranport", threadPriority, alloc)) {
      LogError("Failed to construct LmiSocketTimerLoopConstruct\n");
      return LMI_FALSE;
   }
   init_stl = LMI_TRUE;

   printf("LmiSocketTimerLoopConstruct: Success\n");

   if (!LmiTcpTransportConstruct(&tcpTransport, &stl.se, alloc) ) {
      LogError("Failed to construct LmiTcpTransport\n");
      return LMI_FALSE;
   }
   init_tcpTransport = LMI_TRUE;

   if (( transport = LmiTcpTransportGetTransport(&tcpTransport)) == NULL ) {
      LogError("Failed to get tcp trasnport object\n");
      return LMI_FALSE;
   }

   if ( securedFlag ) {
      if ( LmiCsTlsTransportConstruct(&tlsTransport, "TLS", transport, LmiSocketTimerLoopGetTimerManager(&stl), alloc) == NULL ) {
         LogError("failed to construct tlsTransport\n");
         return LMI_FALSE;
      }
      init_tlsTransport = LMI_TRUE;

      if (( transport = LmiCsTlsTransportGetTransport(&tlsTransport)) == NULL ) {
         LogError("Failed to get tcp trasnport object\n");
         return LMI_FALSE;
      }

      if ( LmiTransportAddressConstructDefault(&transportAddress, transport ) == NULL ) {
         LogError("failed to construct TransportAddress...\n");
         return LMI_FALSE;
      }

      init_transportAddress = LMI_TRUE;
      LmiString lmiCert;
      LmiString lmiPrivKey;
      LmiString lmiCaCert;

      LmiStringConstructCStr(&lmiCert, cert, alloc);
      LmiStringConstructCStr(&lmiPrivKey, privkey, alloc);
      LmiStringConstructCStr(&lmiCaCert, cacert, alloc);

      if ( LmiCsTlsTransportAddressSetTlsInfo(&transportAddress, &lmiCert, &lmiPrivKey, NULL, &lmiCaCert, LMI_FALSE, LMI_TRUE) == LMI_FALSE ) {
         LogError("Failed to set TLS info...\n");
         LmiStringDestruct( &lmiCert );
         LmiStringDestruct( &lmiPrivKey );
         LmiStringDestruct( &lmiCaCert );
         return LMI_FALSE;
      }

      LmiStringDestruct( &lmiCert );
      LmiStringDestruct( &lmiPrivKey );
      LmiStringDestruct( &lmiCaCert );

      if ( hostnameCheck ) {
         LmiCsTlsTransportAddressEnableVerifyHostName(&transportAddress);
      }
   }else {
      LmiTransportAddressConstructDefault(&transportAddress, transport);
   }

   return LMI_TRUE;
}

//! \brief Returns the transport address object.
LmiTransportAddress CHttpTransport::GetTransportAddress() const
{
   return transportAddress;
}

//! \brief Returns the transport object.
LmiTransport* CHttpTransport::GetTransport() const
{
   return transport;
}

//! \brief Returns true is https is enabled otherwise false.
LmiBool CHttpTransport::Secured() const
{
   return secured;
}
