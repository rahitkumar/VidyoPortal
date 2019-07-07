
/**
 * VidyoPortalServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.ws.portal;

        /**
        *  VidyoPortalServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalServiceSkeletonInterface skel = (VidyoPortalServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("updateExternalLinkStatus".equals(methodName)){
                
                com.vidyo.ws.portal.UpdateExternalLinkStatusResponse updateExternalLinkStatusResponse13 = null;
	                        com.vidyo.ws.portal.UpdateExternalLinkStatusRequest wrappedParam =
                                                             (com.vidyo.ws.portal.UpdateExternalLinkStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.UpdateExternalLinkStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateExternalLinkStatusResponse13 =
                                                   
                                                   
                                                         skel.updateExternalLinkStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateExternalLinkStatusResponse13, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "updateExternalLinkStatus"));
                                    } else 

            if("startVidyoManager".equals(methodName)){
                
                com.vidyo.ws.portal.StartVidyoManagerResponse startVidyoManagerResponse15 = null;
	                        com.vidyo.ws.portal.StartVidyoManagerRequest wrappedParam =
                                                             (com.vidyo.ws.portal.StartVidyoManagerRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.StartVidyoManagerRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startVidyoManagerResponse15 =
                                                   
                                                   
                                                         skel.startVidyoManager(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startVidyoManagerResponse15, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "startVidyoManager"));
                                    } else 

            if("addSpontaneousEndpoint".equals(methodName)){
                
                com.vidyo.ws.portal.AddSpontaneousEndpointResponse addSpontaneousEndpointResponse17 = null;
	                        com.vidyo.ws.portal.AddSpontaneousEndpointRequest wrappedParam =
                                                             (com.vidyo.ws.portal.AddSpontaneousEndpointRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.AddSpontaneousEndpointRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               addSpontaneousEndpointResponse17 =
                                                   
                                                   
                                                         skel.addSpontaneousEndpoint(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), addSpontaneousEndpointResponse17, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "addSpontaneousEndpoint"));
                                    } else 

            if("infoFromEndpoint".equals(methodName)){
                
                com.vidyo.ws.portal.InfoFromEndpointResponse infoFromEndpointResponse19 = null;
	                        com.vidyo.ws.portal.InfoFromEndpointRequest wrappedParam =
                                                             (com.vidyo.ws.portal.InfoFromEndpointRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.InfoFromEndpointRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               infoFromEndpointResponse19 =
                                                   
                                                   
                                                         skel.infoFromEndpoint(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), infoFromEndpointResponse19, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "infoFromEndpoint"));
                                    } else 

            if("updateRouterEndpointStatus".equals(methodName)){
                
                com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse updateRouterEndpointStatusResponse21 = null;
	                        com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest wrappedParam =
                                                             (com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateRouterEndpointStatusResponse21 =
                                                   
                                                   
                                                         skel.updateRouterEndpointStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateRouterEndpointStatusResponse21, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "updateRouterEndpointStatus"));
                                    } else 

            if("updateEndpointStatus".equals(methodName)){
                
                com.vidyo.ws.portal.UpdateEndpointStatusResponse updateEndpointStatusResponse23 = null;
	                        com.vidyo.ws.portal.UpdateEndpointStatusRequest wrappedParam =
                                                             (com.vidyo.ws.portal.UpdateEndpointStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.ws.portal.UpdateEndpointStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateEndpointStatusResponse23 =
                                                   
                                                   
                                                         skel.updateEndpointStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateEndpointStatusResponse23, false, new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                    "updateEndpointStatus"));
                                    
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
         catch (GeneralFaultException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"GeneralFault");
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateExternalLinkStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateExternalLinkStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateExternalLinkStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateExternalLinkStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.StartVidyoManagerRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.StartVidyoManagerRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.StartVidyoManagerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.StartVidyoManagerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.AddSpontaneousEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.AddSpontaneousEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.AddSpontaneousEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.AddSpontaneousEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.InfoFromEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.InfoFromEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.InfoFromEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.InfoFromEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateEndpointStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateEndpointStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.portal.UpdateEndpointStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.portal.UpdateEndpointStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.UpdateExternalLinkStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.UpdateExternalLinkStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.UpdateExternalLinkStatusResponse wrapupdateExternalLinkStatus(){
                                com.vidyo.ws.portal.UpdateExternalLinkStatusResponse wrappedElement = new com.vidyo.ws.portal.UpdateExternalLinkStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.StartVidyoManagerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.StartVidyoManagerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.StartVidyoManagerResponse wrapstartVidyoManager(){
                                com.vidyo.ws.portal.StartVidyoManagerResponse wrappedElement = new com.vidyo.ws.portal.StartVidyoManagerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.AddSpontaneousEndpointResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.AddSpontaneousEndpointResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.AddSpontaneousEndpointResponse wrapaddSpontaneousEndpoint(){
                                com.vidyo.ws.portal.AddSpontaneousEndpointResponse wrappedElement = new com.vidyo.ws.portal.AddSpontaneousEndpointResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.InfoFromEndpointResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.InfoFromEndpointResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.InfoFromEndpointResponse wrapinfoFromEndpoint(){
                                com.vidyo.ws.portal.InfoFromEndpointResponse wrappedElement = new com.vidyo.ws.portal.InfoFromEndpointResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse wrapupdateRouterEndpointStatus(){
                                com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse wrappedElement = new com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.portal.UpdateEndpointStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.portal.UpdateEndpointStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.ws.portal.UpdateEndpointStatusResponse wrapupdateEndpointStatus(){
                                com.vidyo.ws.portal.UpdateEndpointStatusResponse wrappedElement = new com.vidyo.ws.portal.UpdateEndpointStatusResponse();
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
        
                if (com.vidyo.ws.portal.UpdateExternalLinkStatusRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateExternalLinkStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.UpdateExternalLinkStatusResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateExternalLinkStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.StartVidyoManagerRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.StartVidyoManagerRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.StartVidyoManagerResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.StartVidyoManagerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.AddSpontaneousEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.AddSpontaneousEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.AddSpontaneousEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.AddSpontaneousEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InfoFromEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.InfoFromEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InfoFromEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.InfoFromEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.UpdateEndpointStatusRequest.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateEndpointStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.UpdateEndpointStatusResponse.class.equals(type)){
                
                           return com.vidyo.ws.portal.UpdateEndpointStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.portal.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.portal.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    