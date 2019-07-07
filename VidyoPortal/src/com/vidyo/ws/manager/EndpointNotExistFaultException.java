
/**
 * EndpointNotExistFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.manager;

public class EndpointNotExistFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395170283874L;
    
    private com.vidyo.ws.manager.EndpointNotExistFault faultMessage;

    
        public EndpointNotExistFaultException() {
            super("EndpointNotExistFaultException");
        }

        public EndpointNotExistFaultException(java.lang.String s) {
           super(s);
        }

        public EndpointNotExistFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public EndpointNotExistFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.manager.EndpointNotExistFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.manager.EndpointNotExistFault getFaultMessage(){
       return faultMessage;
    }
}
    