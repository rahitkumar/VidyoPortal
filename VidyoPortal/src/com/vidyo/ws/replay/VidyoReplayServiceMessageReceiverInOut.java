
/**
 * VidyoReplayServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
        package com.vidyo.ws.replay;

        /**
        *  VidyoReplayServiceMessageReceiverInOut message receiver
        */

        public class VidyoReplayServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoReplayServiceSkeletonInterface skel = (VidyoReplayServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("getUserByUsername".equals(methodName)){
                
                com.vidyo.ws.replay.GetUserByUsernameResponse getUserByUsernameResponse15 = null;
	                        com.vidyo.ws.replay.GetUserByUsernameRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetUserByUsernameRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetUserByUsernameRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserByUsernameResponse15 =
                                                   
                                                   
                                                         skel.getUserByUsername(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserByUsernameResponse15, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getUserByUsername"));
                                    } else 

            if("getTenantByHost".equals(methodName)){
                
                com.vidyo.ws.replay.GetTenantByHostResponse getTenantByHostResponse17 = null;
	                        com.vidyo.ws.replay.GetTenantByHostRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetTenantByHostRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetTenantByHostRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTenantByHostResponse17 =
                                                   
                                                   
                                                         skel.getTenantByHost(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTenantByHostResponse17, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getTenantByHost"));
                                    } else 

            if("getTermsOfServiceForTenant".equals(methodName)){
                
                com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse getTermsOfServiceForTenantResponse19 = null;
	                        com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTermsOfServiceForTenantResponse19 =
                                                   
                                                   
                                                         skel.getTermsOfServiceForTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTermsOfServiceForTenantResponse19, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getTermsOfServiceForTenant"));
                                    } else 

            if("getContactUsForTenant".equals(methodName)){
                
                com.vidyo.ws.replay.GetContactUsForTenantResponse getContactUsForTenantResponse21 = null;
	                        com.vidyo.ws.replay.GetContactUsForTenantRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetContactUsForTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetContactUsForTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getContactUsForTenantResponse21 =
                                                   
                                                   
                                                         skel.getContactUsForTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getContactUsForTenantResponse21, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getContactUsForTenant"));
                                    } else 

            if("getLogoForTenant".equals(methodName)){
                
                com.vidyo.ws.replay.GetLogoForTenantResponse getLogoForTenantResponse23 = null;
	                        com.vidyo.ws.replay.GetLogoForTenantRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetLogoForTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetLogoForTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getLogoForTenantResponse23 =
                                                   
                                                   
                                                         skel.getLogoForTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getLogoForTenantResponse23, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getLogoForTenant"));
                                    } else 

            if("getUserByToken".equals(methodName)){
                
                com.vidyo.ws.replay.GetUserByTokenResponse getUserByTokenResponse25 = null;
	                        com.vidyo.ws.replay.GetUserByTokenRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetUserByTokenRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetUserByTokenRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserByTokenResponse25 =
                                                   
                                                   
                                                         skel.getUserByToken(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserByTokenResponse25, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getUserByToken"));
                                    } else 

            if("getAboutUsForTenant".equals(methodName)){
                
                com.vidyo.ws.replay.GetAboutUsForTenantResponse getAboutUsForTenantResponse27 = null;
	                        com.vidyo.ws.replay.GetAboutUsForTenantRequest wrappedParam =
                                                             (com.vidyo.ws.replay.GetAboutUsForTenantRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.replay.GetAboutUsForTenantRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getAboutUsForTenantResponse27 =
                                                   
                                                   
                                                         skel.getAboutUsForTenant(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getAboutUsForTenantResponse27, false, new javax.xml.namespace.QName("http://ws.vidyo.com/replay",
                                                    "getAboutUsForTenant"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (InvalidArgumentFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidArgumentFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UserNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UserNotFoundFault");
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
         catch (TenantNotFoundFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"TenantNotFoundFault");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetUserByUsernameRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetUserByUsernameRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetUserByUsernameResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetUserByUsernameResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.UserNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.UserNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetTenantByHostRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetTenantByHostRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetTenantByHostResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetTenantByHostResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.TenantNotFoundFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.TenantNotFoundFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetContactUsForTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetContactUsForTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetContactUsForTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetContactUsForTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetLogoForTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetLogoForTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetLogoForTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetLogoForTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetUserByTokenRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetUserByTokenRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetUserByTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetUserByTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetAboutUsForTenantRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetAboutUsForTenantRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.replay.GetAboutUsForTenantResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.replay.GetAboutUsForTenantResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetUserByUsernameResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetUserByUsernameResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetUserByUsernameResponse wrapgetUserByUsername(){
                                com.vidyo.ws.replay.GetUserByUsernameResponse wrappedElement = new com.vidyo.ws.replay.GetUserByUsernameResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetTenantByHostResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetTenantByHostResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetTenantByHostResponse wrapgetTenantByHost(){
                                com.vidyo.ws.replay.GetTenantByHostResponse wrappedElement = new com.vidyo.ws.replay.GetTenantByHostResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse wrapgetTermsOfServiceForTenant(){
                                com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse wrappedElement = new com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetContactUsForTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetContactUsForTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetContactUsForTenantResponse wrapgetContactUsForTenant(){
                                com.vidyo.ws.replay.GetContactUsForTenantResponse wrappedElement = new com.vidyo.ws.replay.GetContactUsForTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetLogoForTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetLogoForTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetLogoForTenantResponse wrapgetLogoForTenant(){
                                com.vidyo.ws.replay.GetLogoForTenantResponse wrappedElement = new com.vidyo.ws.replay.GetLogoForTenantResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetUserByTokenResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetUserByTokenResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetUserByTokenResponse wrapgetUserByToken(){
                                com.vidyo.ws.replay.GetUserByTokenResponse wrappedElement = new com.vidyo.ws.replay.GetUserByTokenResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.replay.GetAboutUsForTenantResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.replay.GetAboutUsForTenantResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.replay.GetAboutUsForTenantResponse wrapgetAboutUsForTenant(){
                                com.vidyo.ws.replay.GetAboutUsForTenantResponse wrappedElement = new com.vidyo.ws.replay.GetAboutUsForTenantResponse();
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
        
                if (com.vidyo.ws.replay.GetUserByUsernameRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetUserByUsernameRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetUserByUsernameResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetUserByUsernameResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.UserNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.UserNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetTenantByHostRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetTenantByHostRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetTenantByHostResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetTenantByHostResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.TenantNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.TenantNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.TenantNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.TenantNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetContactUsForTenantRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetContactUsForTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetContactUsForTenantResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetContactUsForTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.TenantNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.TenantNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetLogoForTenantRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetLogoForTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetLogoForTenantResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetLogoForTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.TenantNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.TenantNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetUserByTokenRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetUserByTokenRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetUserByTokenResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetUserByTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.UserNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.UserNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetAboutUsForTenantRequest.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetAboutUsForTenantRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GetAboutUsForTenantResponse.class.equals(type)){
                
                           return com.vidyo.ws.replay.GetAboutUsForTenantResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.replay.TenantNotFoundFault.class.equals(type)){
                
                           return com.vidyo.ws.replay.TenantNotFoundFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    