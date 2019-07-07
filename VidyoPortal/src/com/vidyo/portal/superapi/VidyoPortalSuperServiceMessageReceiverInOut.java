
/**
 * VidyoPortalSuperServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.superapi;

        /**
        *  VidyoPortalSuperServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalSuperServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalSuperServiceSkeletonInterface skel = (VidyoPortalSuperServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("saveDb".equals(methodName)){
                
                com.vidyo.portal.superapi.SaveDBResponse saveDBResponse47 = null;
	                        com.vidyo.portal.superapi.SaveDBRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SaveDBRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SaveDBRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               saveDBResponse47 =
                                                   
                                                   
                                                         skel.saveDb(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), saveDBResponse47, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "saveDb"));
                                    } else 

            if("backupDb".equals(methodName)){
                
                com.vidyo.portal.superapi.BackupDbResponse backupDbResponse49 = null;
	                        com.vidyo.portal.superapi.BackupDbRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.BackupDbRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.BackupDbRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               backupDbResponse49 =
                                                   
                                                   
                                                         skel.backupDb(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), backupDbResponse49, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "backupDb"));
                                    } else 

            if("addClientVersion".equals(methodName)){
                
                com.vidyo.portal.superapi.AddClientVersionResponse addClientVersionResponse51 = null;
	                        com.vidyo.portal.superapi.AddClientVersionRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.AddClientVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.AddClientVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addClientVersionResponse51 =
                                                   
                                                   
                                                         skel.addClientVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addClientVersionResponse51, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "addClientVersion"));
                                    } else 

            if("getServiceComponentsData".equals(methodName)){
                
                com.vidyo.portal.superapi.GetServiceComponentsDataResponse getServiceComponentsDataResponse53 = null;
	                        com.vidyo.portal.superapi.GetServiceComponentsDataRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetServiceComponentsDataRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetServiceComponentsDataRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getServiceComponentsDataResponse53 =
                                                   
                                                   
                                                         skel.getServiceComponentsData(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getServiceComponentsDataResponse53, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getServiceComponentsData"));
                                    } else 

            if("getTenantDetails".equals(methodName)){
                
                com.vidyo.portal.superapi.GetTenantDetailsResponse getTenantDetailsResponse55 = null;
	                        com.vidyo.portal.superapi.GetTenantDetailsRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetTenantDetailsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetTenantDetailsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTenantDetailsResponse55 =
                                                   
                                                   
                                                         skel.getTenantDetails(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTenantDetailsResponse55, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getTenantDetails"));
                                    } else 

            if("getLicenseData".equals(methodName)){
                
                com.vidyo.portal.superapi.GetLicenseDataResponse getLicenseDataResponse57 = null;
	                        com.vidyo.portal.superapi.GetLicenseDataRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetLicenseDataRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetLicenseDataRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLicenseDataResponse57 =
                                                   
                                                   
                                                         skel.getLicenseData(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLicenseDataResponse57, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getLicenseData"));
                                    } else 

            if("deleteTenant".equals(methodName)){
                
                com.vidyo.portal.superapi.DeleteTenantResponse deleteTenantResponse59 = null;
	                        com.vidyo.portal.superapi.DeleteTenantRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.DeleteTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.DeleteTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteTenantResponse59 =
                                                   
                                                   
                                                         skel.deleteTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteTenantResponse59, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "deleteTenant"));
                                    } else 

            if("setCustomRole".equals(methodName)){
                
                com.vidyo.portal.superapi.SetCustomRoleResponse setCustomRoleResponse61 = null;
	                        com.vidyo.portal.superapi.SetCustomRoleRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SetCustomRoleRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SetCustomRoleRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setCustomRoleResponse61 =
                                                   
                                                   
                                                         skel.setCustomRole(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setCustomRoleResponse61, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "setCustomRole"));
                                    } else 

            if("setChatStateSuper".equals(methodName)){
                
                com.vidyo.portal.superapi.SetChatStateSuperResponse setChatStateSuperResponse63 = null;
	                        com.vidyo.portal.superapi.SetChatStateSuperRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SetChatStateSuperRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SetChatStateSuperRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setChatStateSuperResponse63 =
                                                   
                                                   
                                                         skel.setChatStateSuper(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setChatStateSuperResponse63, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "setChatStateSuper"));
                                    } else 

            if("getRouterPoolList".equals(methodName)){
                
                com.vidyo.portal.superapi.GetRouterPoolListResponse getRouterPoolListResponse65 = null;
	                        com.vidyo.portal.superapi.GetRouterPoolListRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetRouterPoolListRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetRouterPoolListRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRouterPoolListResponse65 =
                                                   
                                                   
                                                         skel.getRouterPoolList(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRouterPoolListResponse65, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getRouterPoolList"));
                                    } else 

            if("setIpcDomains".equals(methodName)){
                
                com.vidyo.portal.superapi.SetIpcDomainsResponse setIpcDomainsResponse67 = null;
	                        com.vidyo.portal.superapi.SetIpcDomainsRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SetIpcDomainsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SetIpcDomainsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setIpcDomainsResponse67 =
                                                   
                                                   
                                                         skel.setIpcDomains(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setIpcDomainsResponse67, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "setIpcDomains"));
                                    } else 

            if("setLoginAndWelcomeBanner".equals(methodName)){
                
                com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse setLoginAndWelcomeBannerResponse69 = null;
	                        com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setLoginAndWelcomeBannerResponse69 =
                                                   
                                                   
                                                         skel.setLoginAndWelcomeBanner(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setLoginAndWelcomeBannerResponse69, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "setLoginAndWelcomeBanner"));
                                    } else 

            if("listNetworkComponents".equals(methodName)){
                
                com.vidyo.portal.superapi.ListNetworkComponentsResponse listNetworkComponentsResponse71 = null;
	                        com.vidyo.portal.superapi.ListNetworkComponentsRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.ListNetworkComponentsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.ListNetworkComponentsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               listNetworkComponentsResponse71 =
                                                   
                                                   
                                                         skel.listNetworkComponents(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), listNetworkComponentsResponse71, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "listNetworkComponents"));
                                    } else 

            if("getIpcAccessControl".equals(methodName)){
                
                com.vidyo.portal.superapi.GetIpcAccessControlResponse getIpcAccessControlResponse73 = null;
	                        com.vidyo.portal.superapi.GetIpcAccessControlRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetIpcAccessControlRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetIpcAccessControlRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getIpcAccessControlResponse73 =
                                                   
                                                   
                                                         skel.getIpcAccessControl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getIpcAccessControlResponse73, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getIpcAccessControl"));
                                    } else 

            if("createTenant".equals(methodName)){
                
                com.vidyo.portal.superapi.CreateTenantResponse createTenantResponse75 = null;
	                        com.vidyo.portal.superapi.CreateTenantRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.CreateTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.CreateTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createTenantResponse75 =
                                                   
                                                   
                                                         skel.createTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createTenantResponse75, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "createTenant"));
                                    } else 

            if("listDb".equals(methodName)){
                
                com.vidyo.portal.superapi.ListDbResponse listDbResponse77 = null;
	                        com.vidyo.portal.superapi.ListDbRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.ListDbRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.ListDbRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               listDbResponse77 =
                                                   
                                                   
                                                         skel.listDb(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), listDbResponse77, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "listDb"));
                                    } else 

            if("getLocationTags".equals(methodName)){
                
                com.vidyo.portal.superapi.GetLocationTagsResponse getLocationTagsResponse79 = null;
	                        com.vidyo.portal.superapi.GetLocationTagsRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetLocationTagsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetLocationTagsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLocationTagsResponse79 =
                                                   
                                                   
                                                         skel.getLocationTags(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLocationTagsResponse79, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getLocationTags"));
                                    } else 

            if("deleteDb".equals(methodName)){
                
                com.vidyo.portal.superapi.DeleteDBResponse deleteDBResponse81 = null;
	                        com.vidyo.portal.superapi.DeleteDBRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.DeleteDBRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.DeleteDBRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteDBResponse81 =
                                                   
                                                   
                                                         skel.deleteDb(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteDBResponse81, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "deleteDb"));
                                    } else 

            if("getListOfTenants".equals(methodName)){
                
                com.vidyo.portal.superapi.ListTenantsResponse listTenantsResponse83 = null;
	                        com.vidyo.portal.superapi.ListTenantsRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.ListTenantsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.ListTenantsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               listTenantsResponse83 =
                                                   
                                                   
                                                         skel.getListOfTenants(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), listTenantsResponse83, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getListOfTenants"));
                                    } else 

            if("updateTenant".equals(methodName)){
                
                com.vidyo.portal.superapi.UpdateTenantResponse updateTenantResponse85 = null;
	                        com.vidyo.portal.superapi.UpdateTenantRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.UpdateTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.UpdateTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateTenantResponse85 =
                                                   
                                                   
                                                         skel.updateTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateTenantResponse85, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "updateTenant"));
                                    } else 

            if("getIpcDomainList".equals(methodName)){
                
                com.vidyo.portal.superapi.GetIpcDomainListResponse getIpcDomainListResponse87 = null;
	                        com.vidyo.portal.superapi.GetIpcDomainListRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetIpcDomainListRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetIpcDomainListRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getIpcDomainListResponse87 =
                                                   
                                                   
                                                         skel.getIpcDomainList(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getIpcDomainListResponse87, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getIpcDomainList"));
                                    } else 

            if("getChatStateSuper".equals(methodName)){
                
                com.vidyo.portal.superapi.GetChatStateSuperResponse getChatStateSuperResponse89 = null;
	                        com.vidyo.portal.superapi.GetChatStateSuperRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.GetChatStateSuperRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.GetChatStateSuperRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getChatStateSuperResponse89 =
                                                   
                                                   
                                                         skel.getChatStateSuper(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getChatStateSuperResponse89, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "getChatStateSuper"));
                                    } else 

            if("setIpcAccessControl".equals(methodName)){
                
                com.vidyo.portal.superapi.SetIpcAccessControlResponse setIpcAccessControlResponse91 = null;
	                        com.vidyo.portal.superapi.SetIpcAccessControlRequest wrappedParam =
                                                             (com.vidyo.portal.superapi.SetIpcAccessControlRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.superapi.SetIpcAccessControlRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setIpcAccessControlResponse91 =
                                                   
                                                   
                                                         skel.setIpcAccessControl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setIpcAccessControlResponse91, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                    "setIpcAccessControl"));
                                    
            } else 

                if("setLogAggregationServer".equals(methodName)){
                    
                    com.vidyo.portal.superapi.SetLogAggregationServerResponse setLogAggregationServerResponse121 = null;
    	                        com.vidyo.portal.superapi.SetLogAggregationServerRequest wrappedParam =
                                                                 (com.vidyo.portal.superapi.SetLogAggregationServerRequest)fromOM(
                                        msgContext.getEnvelope().getBody().getFirstElement(),
                                        com.vidyo.portal.superapi.SetLogAggregationServerRequest.class,
                                        getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                    
                                                   setLogAggregationServerResponse121 =
                                                       
                                                       
                                                             skel.setLogAggregationServer(wrappedParam)
                                                        ;
                                                
                                            envelope = toEnvelope(getSOAPFactory(msgContext), setLogAggregationServerResponse121, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                        "setLogAggregationServer"));
                } else 

                    if("setMobileLoginMode".equals(methodName)){
                        
                    	 com.vidyo.portal.superapi.SetMobileLoginModeResponse setMobileLoginModeResponse123 = null;
	                        com.vidyo.portal.superapi.SetMobileLoginModeRequest wrappedParam =
                                                          (com.vidyo.portal.superapi.SetMobileLoginModeRequest)fromOM(
                                 msgContext.getEnvelope().getBody().getFirstElement(),
                                 com.vidyo.portal.superapi.SetMobileLoginModeRequest.class,
                                 getEnvelopeNamespaces(msgContext.getEnvelope()));
                                             
                                            setMobileLoginModeResponse123 =
                                                
                                                
                                                      skel.setMobileLoginMode(wrappedParam)
                                                 ;
                                         
                                     envelope = toEnvelope(getSOAPFactory(msgContext), setMobileLoginModeResponse123, false, new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                 "setMobileLoginMode"));
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (NotLicensedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotLicensedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (BannerTextFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"BannerTextFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidArgumentFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidArgumentFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (MissingArgumentFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"MissingArgumentFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ExistingTenantFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ExistingTenantFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ExternalModeFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ExternalModeFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (GeneralFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GeneralFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (IncorrectIpcAccessLevelFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"IncorrectIpcAccessLevelFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NotAuthorizedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotAuthorizedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidTenantFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidTenantFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
        
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SaveDBRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SaveDBRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SaveDBResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SaveDBResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.BackupDbRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.BackupDbRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.BackupDbResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.BackupDbResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.AddClientVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.AddClientVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.AddClientVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.AddClientVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ExternalModeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ExternalModeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetServiceComponentsDataRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetServiceComponentsDataRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetServiceComponentsDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetServiceComponentsDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetTenantDetailsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetTenantDetailsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetTenantDetailsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetTenantDetailsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.InvalidTenantFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.InvalidTenantFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetLicenseDataRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetLicenseDataRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetLicenseDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetLicenseDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.NotLicensedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.NotLicensedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.NotAuthorizedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.NotAuthorizedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.DeleteTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.DeleteTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.DeleteTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.DeleteTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetCustomRoleRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetCustomRoleRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetCustomRoleResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetCustomRoleResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetChatStateSuperRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetChatStateSuperRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetChatStateSuperResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetChatStateSuperResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetRouterPoolListRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetRouterPoolListRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetRouterPoolListResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetRouterPoolListResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetIpcDomainsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetIpcDomainsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetIpcDomainsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetIpcDomainsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.BannerTextFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.BannerTextFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListNetworkComponentsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListNetworkComponentsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListNetworkComponentsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListNetworkComponentsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetIpcAccessControlRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetIpcAccessControlRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetIpcAccessControlResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetIpcAccessControlResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.CreateTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.CreateTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.CreateTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.CreateTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ExistingTenantFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ExistingTenantFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
            
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetLogAggregationServerRequest param, boolean optimizeContent)
                    throws org.apache.axis2.AxisFault {

                    
                                try{
                                     return param.getOMElement(com.vidyo.portal.superapi.SetLogAggregationServerRequest.MY_QNAME,
                                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                                } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                                }
                            

                    }
                
                    private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetLogAggregationServerResponse param, boolean optimizeContent)
                    throws org.apache.axis2.AxisFault {

                    
                                try{
                                     return param.getOMElement(com.vidyo.portal.superapi.SetLogAggregationServerResponse.MY_QNAME,
                                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                                } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                                }
                            

                    }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListDbRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListDbRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListDbResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListDbResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetLocationTagsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetLocationTagsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetLocationTagsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetLocationTagsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.DeleteDBRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.DeleteDBRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.DeleteDBResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.DeleteDBResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListTenantsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListTenantsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.ListTenantsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.ListTenantsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.UpdateTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.UpdateTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.UpdateTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.UpdateTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetIpcDomainListRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetIpcDomainListRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetIpcDomainListResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetIpcDomainListResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetChatStateSuperRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetChatStateSuperRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.GetChatStateSuperResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.GetChatStateSuperResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetIpcAccessControlRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetIpcAccessControlRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetIpcAccessControlResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.SetIpcAccessControlResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
            
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetMobileLoginModeRequest param, boolean optimizeContent)
                    throws org.apache.axis2.AxisFault {

                    
                                try{
                                     return param.getOMElement(com.vidyo.portal.superapi.SetMobileLoginModeRequest.MY_QNAME,
                                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                                } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                                }
                            

                    }
            
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.SetMobileLoginModeResponse param, boolean optimizeContent)
                    throws org.apache.axis2.AxisFault {

                    
                                try{
                                     return param.getOMElement(com.vidyo.portal.superapi.SetMobileLoginModeResponse.MY_QNAME,
                                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                                } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                                }
                            

                    }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.superapi.MissingArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.superapi.MissingArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SaveDBResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SaveDBResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SaveDBResponse wrapsaveDb(){
                                com.vidyo.portal.superapi.SaveDBResponse wrappedElement = new com.vidyo.portal.superapi.SaveDBResponse();
                                return wrappedElement;
                         }
                    
                         
                         private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetMobileLoginModeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                 throws org.apache.axis2.AxisFault{
                               try{
                                   org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                    
                                             emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetMobileLoginModeResponse.MY_QNAME,factory));
                                         

                                  return emptyEnvelope;
                             } catch(org.apache.axis2.databinding.ADBException e){
                                 throw org.apache.axis2.AxisFault.makeFault(e);
                             }
                             }
                             
                                  private com.vidyo.portal.superapi.SetMobileLoginModeResponse wrapsetMobileLoginMode(){
                                         com.vidyo.portal.superapi.SetMobileLoginModeResponse wrappedElement = new com.vidyo.portal.superapi.SetMobileLoginModeResponse();
                                         return wrappedElement;
                                  }
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.BackupDbResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.BackupDbResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.BackupDbResponse wrapbackupDb(){
                                com.vidyo.portal.superapi.BackupDbResponse wrappedElement = new com.vidyo.portal.superapi.BackupDbResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.AddClientVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.AddClientVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.AddClientVersionResponse wrapaddClientVersion(){
                                com.vidyo.portal.superapi.AddClientVersionResponse wrappedElement = new com.vidyo.portal.superapi.AddClientVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetServiceComponentsDataResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetServiceComponentsDataResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetServiceComponentsDataResponse wrapgetServiceComponentsData(){
                                com.vidyo.portal.superapi.GetServiceComponentsDataResponse wrappedElement = new com.vidyo.portal.superapi.GetServiceComponentsDataResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetTenantDetailsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetTenantDetailsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetTenantDetailsResponse wrapgetTenantDetails(){
                                com.vidyo.portal.superapi.GetTenantDetailsResponse wrappedElement = new com.vidyo.portal.superapi.GetTenantDetailsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetLicenseDataResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetLicenseDataResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetLicenseDataResponse wrapgetLicenseData(){
                                com.vidyo.portal.superapi.GetLicenseDataResponse wrappedElement = new com.vidyo.portal.superapi.GetLicenseDataResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.DeleteTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.DeleteTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.DeleteTenantResponse wrapdeleteTenant(){
                                com.vidyo.portal.superapi.DeleteTenantResponse wrappedElement = new com.vidyo.portal.superapi.DeleteTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetCustomRoleResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetCustomRoleResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SetCustomRoleResponse wrapsetCustomRole(){
                                com.vidyo.portal.superapi.SetCustomRoleResponse wrappedElement = new com.vidyo.portal.superapi.SetCustomRoleResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetChatStateSuperResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetChatStateSuperResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SetChatStateSuperResponse wrapsetChatStateSuper(){
                                com.vidyo.portal.superapi.SetChatStateSuperResponse wrappedElement = new com.vidyo.portal.superapi.SetChatStateSuperResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetRouterPoolListResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetRouterPoolListResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetRouterPoolListResponse wrapgetRouterPoolList(){
                                com.vidyo.portal.superapi.GetRouterPoolListResponse wrappedElement = new com.vidyo.portal.superapi.GetRouterPoolListResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetIpcDomainsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetIpcDomainsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SetIpcDomainsResponse wrapsetIpcDomains(){
                                com.vidyo.portal.superapi.SetIpcDomainsResponse wrappedElement = new com.vidyo.portal.superapi.SetIpcDomainsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse wrapsetLoginAndWelcomeBanner(){
                                com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse wrappedElement = new com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.ListNetworkComponentsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.ListNetworkComponentsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetLogAggregationServerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                            throws org.apache.axis2.AxisFault{
                          try{
                              org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                               
                                        emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetLogAggregationServerResponse.MY_QNAME,factory));
                                    

                             return emptyEnvelope;
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                        }
                        
                             private com.vidyo.portal.superapi.SetLogAggregationServerResponse wrapsetLogAggregationServer(){
                                    com.vidyo.portal.superapi.SetLogAggregationServerResponse wrappedElement = new com.vidyo.portal.superapi.SetLogAggregationServerResponse();
                                    return wrappedElement;
                             }
                    
                         private com.vidyo.portal.superapi.ListNetworkComponentsResponse wraplistNetworkComponents(){
                                com.vidyo.portal.superapi.ListNetworkComponentsResponse wrappedElement = new com.vidyo.portal.superapi.ListNetworkComponentsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetIpcAccessControlResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetIpcAccessControlResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetIpcAccessControlResponse wrapgetIpcAccessControl(){
                                com.vidyo.portal.superapi.GetIpcAccessControlResponse wrappedElement = new com.vidyo.portal.superapi.GetIpcAccessControlResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.CreateTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.CreateTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.CreateTenantResponse wrapcreateTenant(){
                                com.vidyo.portal.superapi.CreateTenantResponse wrappedElement = new com.vidyo.portal.superapi.CreateTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.ListDbResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.ListDbResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.ListDbResponse wraplistDb(){
                                com.vidyo.portal.superapi.ListDbResponse wrappedElement = new com.vidyo.portal.superapi.ListDbResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetLocationTagsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetLocationTagsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetLocationTagsResponse wrapgetLocationTags(){
                                com.vidyo.portal.superapi.GetLocationTagsResponse wrappedElement = new com.vidyo.portal.superapi.GetLocationTagsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.DeleteDBResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.DeleteDBResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.DeleteDBResponse wrapdeleteDb(){
                                com.vidyo.portal.superapi.DeleteDBResponse wrappedElement = new com.vidyo.portal.superapi.DeleteDBResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.ListTenantsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.ListTenantsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.ListTenantsResponse wrapgetListOfTenants(){
                                com.vidyo.portal.superapi.ListTenantsResponse wrappedElement = new com.vidyo.portal.superapi.ListTenantsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.UpdateTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.UpdateTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.UpdateTenantResponse wrapupdateTenant(){
                                com.vidyo.portal.superapi.UpdateTenantResponse wrappedElement = new com.vidyo.portal.superapi.UpdateTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetIpcDomainListResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetIpcDomainListResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetIpcDomainListResponse wrapgetIpcDomainList(){
                                com.vidyo.portal.superapi.GetIpcDomainListResponse wrappedElement = new com.vidyo.portal.superapi.GetIpcDomainListResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.GetChatStateSuperResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.GetChatStateSuperResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.GetChatStateSuperResponse wrapgetChatStateSuper(){
                                com.vidyo.portal.superapi.GetChatStateSuperResponse wrappedElement = new com.vidyo.portal.superapi.GetChatStateSuperResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.superapi.SetIpcAccessControlResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.superapi.SetIpcAccessControlResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.superapi.SetIpcAccessControlResponse wrapsetIpcAccessControl(){
                                com.vidyo.portal.superapi.SetIpcAccessControlResponse wrappedElement = new com.vidyo.portal.superapi.SetIpcAccessControlResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.vidyo.portal.superapi.SaveDBRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SaveDBRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SaveDBResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SaveDBResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.BackupDbRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.BackupDbRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.BackupDbResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.BackupDbResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.AddClientVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.AddClientVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.AddClientVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.AddClientVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ExternalModeFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ExternalModeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetServiceComponentsDataRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetServiceComponentsDataRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetServiceComponentsDataResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetServiceComponentsDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetTenantDetailsRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetTenantDetailsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetTenantDetailsResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetTenantDetailsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetLicenseDataRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetLicenseDataRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetLicenseDataResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetLicenseDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
                
                if (com.vidyo.portal.superapi.SetMobileLoginModeRequest.class.equals(type)){
                    
                    return com.vidyo.portal.superapi.SetMobileLoginModeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
             

                }
                
                if (com.vidyo.portal.superapi.SetMobileLoginModeResponse.class.equals(type)){
                    
                    return com.vidyo.portal.superapi.SetMobileLoginModeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
             

                }
           
                if (com.vidyo.portal.superapi.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.NotAuthorizedFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.NotAuthorizedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.DeleteTenantRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.DeleteTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.DeleteTenantResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.DeleteTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetCustomRoleRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetCustomRoleRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetCustomRoleResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetCustomRoleResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
                
                if (com.vidyo.portal.superapi.SetLogAggregationServerRequest.class.equals(type)){
                    
                    return com.vidyo.portal.superapi.SetLogAggregationServerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
             

		         }
		    
		         if (com.vidyo.portal.superapi.SetLogAggregationServerResponse.class.equals(type)){
		         
		                    return com.vidyo.portal.superapi.SetLogAggregationServerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
		             
		
		         }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetChatStateSuperRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetChatStateSuperRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetChatStateSuperResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetChatStateSuperResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetRouterPoolListRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetRouterPoolListRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetRouterPoolListResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetRouterPoolListResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetIpcDomainsRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetIpcDomainsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetIpcDomainsResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetIpcDomainsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.BannerTextFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.BannerTextFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListNetworkComponentsRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListNetworkComponentsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListNetworkComponentsResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListNetworkComponentsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetIpcAccessControlRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetIpcAccessControlRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetIpcAccessControlResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetIpcAccessControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.CreateTenantRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.CreateTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.CreateTenantResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.CreateTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ExistingTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ExistingTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListDbRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListDbRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListDbResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListDbResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetLocationTagsRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetLocationTagsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetLocationTagsResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetLocationTagsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.DeleteDBRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.DeleteDBRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.DeleteDBResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.DeleteDBResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListTenantsRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListTenantsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ListTenantsResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ListTenantsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.UpdateTenantRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.UpdateTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.UpdateTenantResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.UpdateTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.ExistingTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.ExistingTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidTenantFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidTenantFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetIpcDomainListRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetIpcDomainListRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetIpcDomainListResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetIpcDomainListResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.IncorrectIpcAccessLevelFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetChatStateSuperRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetChatStateSuperRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GetChatStateSuperResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GetChatStateSuperResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetIpcAccessControlRequest.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetIpcAccessControlRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.SetIpcAccessControlResponse.class.equals(type)){
                
                           return com.vidyo.portal.superapi.SetIpcAccessControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.MissingArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.MissingArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.superapi.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.superapi.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    