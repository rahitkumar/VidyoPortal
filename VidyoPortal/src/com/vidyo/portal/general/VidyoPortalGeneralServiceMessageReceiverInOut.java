
/**
 * VidyoPortalGeneralServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.portal.general;

        /**
        *  VidyoPortalGeneralServiceMessageReceiverInOut message receiver
        */

        public class VidyoPortalGeneralServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        VidyoPortalGeneralServiceSkeletonInterface skel = (VidyoPortalGeneralServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("getTransactionStatus".equals(methodName)){
                
                com.vidyo.portal.general.TransactionStatusResponse transactionStatusResponse19 = null;
	                        com.vidyo.portal.general.TransactionStatusRequest wrappedParam =
                                                             (com.vidyo.portal.general.TransactionStatusRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.general.TransactionStatusRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               transactionStatusResponse19 =
                                                   
                                                   
                                                         skel.getTransactionStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), transactionStatusResponse19, false, new javax.xml.namespace.QName("http://portal.vidyo.com/general",
                                                    "getTransactionStatus"));
                                    } else 

            if("getLogInType".equals(methodName)){
                
                com.vidyo.portal.general.LogInTypeResponse logInTypeResponse21 = null;
	                        com.vidyo.portal.general.LogInTypeRequest wrappedParam =
                                                             (com.vidyo.portal.general.LogInTypeRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.general.LogInTypeRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               logInTypeResponse21 =
                                                   
                                                   
                                                         skel.getLogInType(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), logInTypeResponse21, false, new javax.xml.namespace.QName("http://portal.vidyo.com/general",
                                                    "getLogInType"));
                                    } else 

            if("webRTCLoginIsEnabled".equals(methodName)){
                
                com.vidyo.portal.general.WebRTCLoginIsEnabledResponse webRTCLoginIsEnabledResponse23 = null;
	                        com.vidyo.portal.general.WebRTCLoginIsEnabledRequest wrappedParam =
                                                             (com.vidyo.portal.general.WebRTCLoginIsEnabledRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.vidyo.portal.general.WebRTCLoginIsEnabledRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               webRTCLoginIsEnabledResponse23 =
                                                   
                                                   
                                                         skel.webRTCLoginIsEnabled(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), webRTCLoginIsEnabledResponse23, false, new javax.xml.namespace.QName("http://portal.vidyo.com/general",
                                                    "webRTCLoginIsEnabled"));
                                    
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
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.TransactionStatusRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.TransactionStatusRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.TransactionStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.TransactionStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.LogInTypeRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.LogInTypeRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.LogInTypeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.LogInTypeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.WebRTCLoginIsEnabledRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.WebRTCLoginIsEnabledRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.portal.general.WebRTCLoginIsEnabledResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.portal.general.WebRTCLoginIsEnabledResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.general.TransactionStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.general.TransactionStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.general.TransactionStatusResponse wrapgetTransactionStatus(){
                                com.vidyo.portal.general.TransactionStatusResponse wrappedElement = new com.vidyo.portal.general.TransactionStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.general.LogInTypeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.general.LogInTypeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.general.LogInTypeResponse wrapgetLogInType(){
                                com.vidyo.portal.general.LogInTypeResponse wrappedElement = new com.vidyo.portal.general.LogInTypeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.portal.general.WebRTCLoginIsEnabledResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.portal.general.WebRTCLoginIsEnabledResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.vidyo.portal.general.WebRTCLoginIsEnabledResponse wrapwebRTCLoginIsEnabled(){
                                com.vidyo.portal.general.WebRTCLoginIsEnabledResponse wrappedElement = new com.vidyo.portal.general.WebRTCLoginIsEnabledResponse();
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
        
                if (com.vidyo.portal.general.TransactionStatusRequest.class.equals(type)){
                
                           return com.vidyo.portal.general.TransactionStatusRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.TransactionStatusResponse.class.equals(type)){
                
                           return com.vidyo.portal.general.TransactionStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.general.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.general.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.LogInTypeRequest.class.equals(type)){
                
                           return com.vidyo.portal.general.LogInTypeRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.LogInTypeResponse.class.equals(type)){
                
                           return com.vidyo.portal.general.LogInTypeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.portal.general.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.general.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.WebRTCLoginIsEnabledRequest.class.equals(type)){
                
                           return com.vidyo.portal.general.WebRTCLoginIsEnabledRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.WebRTCLoginIsEnabledResponse.class.equals(type)){
                
                           return com.vidyo.portal.general.WebRTCLoginIsEnabledResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.portal.general.GeneralFault.class.equals(type)){
                
                           return com.vidyo.portal.general.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    