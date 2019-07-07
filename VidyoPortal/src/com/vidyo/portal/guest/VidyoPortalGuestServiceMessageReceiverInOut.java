
/**
 * VidyoPortalGuestServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.guest;

        /**
        *  VidyoPortalGuestServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalGuestServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalGuestServiceSkeletonInterface skel = (VidyoPortalGuestServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("getClientVersion".equals(methodName)){
                
                com.vidyo.portal.guest.ClientVersionResponse clientVersionResponse79 = null;
	                        com.vidyo.portal.guest.ClientVersionRequest wrappedParam =
                                                             (com.vidyo.portal.guest.ClientVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.ClientVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               clientVersionResponse79 =
                                                   
                                                   
                                                         skel.getClientVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), clientVersionResponse79, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "getClientVersion"));
                                    } else 

            if("recoverPassword".equals(methodName)){
                
                com.vidyo.portal.guest.RecoverPasswordResponse recoverPasswordResponse81 = null;
	                        com.vidyo.portal.guest.RecoverPasswordRequest wrappedParam =
                                                             (com.vidyo.portal.guest.RecoverPasswordRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.RecoverPasswordRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               recoverPasswordResponse81 =
                                                   
                                                   
                                                         skel.recoverPassword(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), recoverPasswordResponse81, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "recoverPassword"));
                                    } else 

            if("raiseHand".equals(methodName)){
                
                com.vidyo.portal.guest.RaiseHandResponse raiseHandResponse83 = null;
	                        com.vidyo.portal.guest.RaiseHandRequest wrappedParam =
                                                             (com.vidyo.portal.guest.RaiseHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.RaiseHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               raiseHandResponse83 =
                                                   
                                                   
                                                         skel.raiseHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), raiseHandResponse83, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "raiseHand"));
                                    } else 

            if("guestJoinConference".equals(methodName)){
                
                com.vidyo.portal.guest.GuestJoinConferenceResponse guestJoinConferenceResponse85 = null;
	                        com.vidyo.portal.guest.GuestJoinConferenceRequest wrappedParam =
                                                             (com.vidyo.portal.guest.GuestJoinConferenceRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.GuestJoinConferenceRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               guestJoinConferenceResponse85 =
                                                   
                                                   
                                                         skel.guestJoinConference(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), guestJoinConferenceResponse85, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "guestJoinConference"));
                                    } else 

            if("unraiseHand".equals(methodName)){
                
                com.vidyo.portal.guest.UnraiseHandResponse unraiseHandResponse87 = null;
	                        com.vidyo.portal.guest.UnraiseHandRequest wrappedParam =
                                                             (com.vidyo.portal.guest.UnraiseHandRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.UnraiseHandRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unraiseHandResponse87 =
                                                   
                                                   
                                                         skel.unraiseHand(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unraiseHandResponse87, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "unraiseHand"));
                                    } else 

            if("linkEndpointToGuest".equals(methodName)){
                
                com.vidyo.portal.guest.LinkEndpointToGuestResponse linkEndpointToGuestResponse89 = null;
	                        com.vidyo.portal.guest.LinkEndpointToGuestRequest wrappedParam =
                                                             (com.vidyo.portal.guest.LinkEndpointToGuestRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.LinkEndpointToGuestRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               linkEndpointToGuestResponse89 =
                                                   
                                                   
                                                         skel.linkEndpointToGuest(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), linkEndpointToGuestResponse89, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "linkEndpointToGuest"));
                                    } else 

            if("getRoomDetailsByRoomKey".equals(methodName)){
                
                com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse getRoomDetailsByRoomKeyResponse91 = null;
	                        com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest wrappedParam =
                                                             (com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getRoomDetailsByRoomKeyResponse91 =
                                                   
                                                   
                                                         skel.getRoomDetailsByRoomKey(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getRoomDetailsByRoomKeyResponse91, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "getRoomDetailsByRoomKey"));
                                    } else 

            if("getPortalFeatures".equals(methodName)){
                
                com.vidyo.portal.guest.GetPortalFeaturesResponse getPortalFeaturesResponse93 = null;
	                        com.vidyo.portal.guest.GetPortalFeaturesRequest wrappedParam =
                                                             (com.vidyo.portal.guest.GetPortalFeaturesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.GetPortalFeaturesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalFeaturesResponse93 =
                                                   
                                                   
                                                         skel.getPortalFeatures(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalFeaturesResponse93, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "getPortalFeatures"));
                                    } else 

            if("getPortalVersion".equals(methodName)){
                
                com.vidyo.portal.guest.GetPortalVersionResponse getPortalVersionResponse95 = null;
	                        com.vidyo.portal.guest.GetPortalVersionRequest wrappedParam =
                                                             (com.vidyo.portal.guest.GetPortalVersionRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.GetPortalVersionRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPortalVersionResponse95 =
                                                   
                                                   
                                                         skel.getPortalVersion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPortalVersionResponse95, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "getPortalVersion"));
                                    } else 

            if("whatIsMyIPAddress".equals(methodName)){
                
                com.vidyo.portal.guest.WhatIsMyIPAddressResponse whatIsMyIPAddressResponse97 = null;
	                        com.vidyo.portal.guest.WhatIsMyIPAddressRequest wrappedParam =
                                                             (com.vidyo.portal.guest.WhatIsMyIPAddressRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.WhatIsMyIPAddressRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               whatIsMyIPAddressResponse97 =
                                                   
                                                   
                                                         skel.whatIsMyIPAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), whatIsMyIPAddressResponse97, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "whatIsMyIPAddress"));
                                    } else 

            if("logInAsGuest".equals(methodName)){
                
                com.vidyo.portal.guest.LogInAsGuestResponse logInAsGuestResponse99 = null;
	                        com.vidyo.portal.guest.LogInAsGuestRequest wrappedParam =
                                                             (com.vidyo.portal.guest.LogInAsGuestRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.LogInAsGuestRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logInAsGuestResponse99 =
                                                   
                                                   
                                                         skel.logInAsGuest(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logInAsGuestResponse99, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "logInAsGuest"));
                                    } else 

            if("createTestcallRoom".equals(methodName)){
                
                com.vidyo.portal.guest.CreateTestcallRoomResponse createTestcallRoomResponse101 = null;
	                        com.vidyo.portal.guest.CreateTestcallRoomRequest wrappedParam =
                                                             (com.vidyo.portal.guest.CreateTestcallRoomRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.CreateTestcallRoomRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createTestcallRoomResponse101 =
                                                   
                                                   
                                                         skel.createTestcallRoom(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createTestcallRoomResponse101, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "createTestcallRoom"));
                                    } else 

            if("setEndpointDetails".equals(methodName)){
                
                com.vidyo.portal.guest.SetEndpointDetailsResponse setEndpointDetailsResponse103 = null;
	                        com.vidyo.portal.guest.SetEndpointDetailsRequest wrappedParam =
                                                             (com.vidyo.portal.guest.SetEndpointDetailsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.guest.SetEndpointDetailsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setEndpointDetailsResponse103 =
                                                   
                                                   
                                                         skel.setEndpointDetails(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setEndpointDetailsResponse103, false, new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                    "setEndpointDetails"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (RoomIsFullFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"RoomIsFullFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (EmailAddressNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"EmailAddressNotFoundFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NotificationEmailNotConfiguredFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotificationEmailNotConfiguredFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (TestcallRoomCreationFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"TestcallRoomCreationFault");
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
         catch (WrongPinFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"WrongPinFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (EndpointNotBoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"EndpointNotBoundFault");
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
         catch (ResourceNotAvailableFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ResourceNotAvailableFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ConferenceLockedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ConferenceLockedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (AccessRestrictedFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"AccessRestrictedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (AllLinesInUseFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"AllLinesInUseFault");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.ClientVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.ClientVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.ClientVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.ClientVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.RecoverPasswordRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.RecoverPasswordRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.RecoverPasswordResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.RecoverPasswordResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.EmailAddressNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.EmailAddressNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.NotificationEmailNotConfiguredFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.NotificationEmailNotConfiguredFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.RaiseHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.RaiseHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.RaiseHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.RaiseHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GuestJoinConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GuestJoinConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GuestJoinConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GuestJoinConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.RoomIsFullFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.RoomIsFullFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.WrongPinFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.WrongPinFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.ResourceNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.ResourceNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.ConferenceLockedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.ConferenceLockedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.AllLinesInUseFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.AllLinesInUseFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.UnraiseHandRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.UnraiseHandRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.UnraiseHandResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.UnraiseHandResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.LinkEndpointToGuestRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.LinkEndpointToGuestRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.LinkEndpointToGuestResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.LinkEndpointToGuestResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.AccessRestrictedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.AccessRestrictedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetPortalFeaturesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetPortalFeaturesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetPortalFeaturesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetPortalFeaturesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetPortalVersionRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetPortalVersionRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.GetPortalVersionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.GetPortalVersionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.WhatIsMyIPAddressRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.WhatIsMyIPAddressRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.WhatIsMyIPAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.WhatIsMyIPAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.LogInAsGuestRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.LogInAsGuestRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.LogInAsGuestResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.LogInAsGuestResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.CreateTestcallRoomRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.CreateTestcallRoomRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.CreateTestcallRoomResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.CreateTestcallRoomResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.TestcallRoomCreationFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.TestcallRoomCreationFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.SetEndpointDetailsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.SetEndpointDetailsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.SetEndpointDetailsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.SetEndpointDetailsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.guest.EndpointNotBoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.guest.EndpointNotBoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.ClientVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.ClientVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.ClientVersionResponse wrapgetClientVersion(){
                                com.vidyo.portal.guest.ClientVersionResponse wrappedElement = new com.vidyo.portal.guest.ClientVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.RecoverPasswordResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.RecoverPasswordResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.RecoverPasswordResponse wraprecoverPassword(){
                                com.vidyo.portal.guest.RecoverPasswordResponse wrappedElement = new com.vidyo.portal.guest.RecoverPasswordResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.RaiseHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.RaiseHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.RaiseHandResponse wrapraiseHand(){
                                com.vidyo.portal.guest.RaiseHandResponse wrappedElement = new com.vidyo.portal.guest.RaiseHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.GuestJoinConferenceResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.GuestJoinConferenceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.GuestJoinConferenceResponse wrapguestJoinConference(){
                                com.vidyo.portal.guest.GuestJoinConferenceResponse wrappedElement = new com.vidyo.portal.guest.GuestJoinConferenceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.LinkEndpointToGuestResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.LinkEndpointToGuestResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.LinkEndpointToGuestResponse wraplinkEndpointToGuest(){
                                com.vidyo.portal.guest.LinkEndpointToGuestResponse wrappedElement = new com.vidyo.portal.guest.LinkEndpointToGuestResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.UnraiseHandResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.UnraiseHandResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.UnraiseHandResponse wrapunraiseHand(){
                                com.vidyo.portal.guest.UnraiseHandResponse wrappedElement = new com.vidyo.portal.guest.UnraiseHandResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse wrapgetRoomDetailsByRoomKey(){
                                com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse wrappedElement = new com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.GetPortalFeaturesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.GetPortalFeaturesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.GetPortalFeaturesResponse wrapgetPortalFeatures(){
                                com.vidyo.portal.guest.GetPortalFeaturesResponse wrappedElement = new com.vidyo.portal.guest.GetPortalFeaturesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.GetPortalVersionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.GetPortalVersionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.GetPortalVersionResponse wrapgetPortalVersion(){
                                com.vidyo.portal.guest.GetPortalVersionResponse wrappedElement = new com.vidyo.portal.guest.GetPortalVersionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.WhatIsMyIPAddressResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.WhatIsMyIPAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.WhatIsMyIPAddressResponse wrapwhatIsMyIPAddress(){
                                com.vidyo.portal.guest.WhatIsMyIPAddressResponse wrappedElement = new com.vidyo.portal.guest.WhatIsMyIPAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.LogInAsGuestResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.LogInAsGuestResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.LogInAsGuestResponse wraplogInAsGuest(){
                                com.vidyo.portal.guest.LogInAsGuestResponse wrappedElement = new com.vidyo.portal.guest.LogInAsGuestResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.CreateTestcallRoomResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.CreateTestcallRoomResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.CreateTestcallRoomResponse wrapcreateTestcallRoom(){
                                com.vidyo.portal.guest.CreateTestcallRoomResponse wrappedElement = new com.vidyo.portal.guest.CreateTestcallRoomResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.guest.SetEndpointDetailsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.guest.SetEndpointDetailsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.guest.SetEndpointDetailsResponse wrapsetEndpointDetails(){
                                com.vidyo.portal.guest.SetEndpointDetailsResponse wrappedElement = new com.vidyo.portal.guest.SetEndpointDetailsResponse();
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
        
                if (com.vidyo.portal.guest.ClientVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.ClientVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.ClientVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.ClientVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RecoverPasswordRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.RecoverPasswordRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RecoverPasswordResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.RecoverPasswordResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.EmailAddressNotFoundFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.EmailAddressNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.NotificationEmailNotConfiguredFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.NotificationEmailNotConfiguredFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RaiseHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.RaiseHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RaiseHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.RaiseHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GuestJoinConferenceRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.GuestJoinConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GuestJoinConferenceResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.GuestJoinConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RoomIsFullFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.RoomIsFullFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.WrongPinFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.WrongPinFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.ConferenceLockedFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.ConferenceLockedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.AllLinesInUseFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.AllLinesInUseFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.UnraiseHandRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.UnraiseHandRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.UnraiseHandResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.UnraiseHandResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.LinkEndpointToGuestRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.LinkEndpointToGuestRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.LinkEndpointToGuestResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.LinkEndpointToGuestResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.AccessRestrictedFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.AccessRestrictedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetPortalFeaturesRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetPortalFeaturesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetPortalFeaturesResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetPortalFeaturesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetPortalVersionRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetPortalVersionRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GetPortalVersionResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.GetPortalVersionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.WhatIsMyIPAddressRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.WhatIsMyIPAddressRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.WhatIsMyIPAddressResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.WhatIsMyIPAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.LogInAsGuestRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.LogInAsGuestRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.LogInAsGuestResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.LogInAsGuestResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.RoomIsFullFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.RoomIsFullFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.CreateTestcallRoomRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.CreateTestcallRoomRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.CreateTestcallRoomResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.CreateTestcallRoomResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.TestcallRoomCreationFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.TestcallRoomCreationFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.SetEndpointDetailsRequest.class.equals(type)){
                
                           return com.vidyo.portal.guest.SetEndpointDetailsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.SetEndpointDetailsResponse.class.equals(type)){
                
                           return com.vidyo.portal.guest.SetEndpointDetailsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.EndpointNotBoundFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.EndpointNotBoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.guest.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.guest.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    