
/**
 * EndpointNotBoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.guest;

public class EndpointNotBoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504782303891L;
    
    private com.vidyo.portal.guest.EndpointNotBoundFault faultMessage;

    
        public EndpointNotBoundFaultException() {
            super("EndpointNotBoundFaultException");
        }

        public EndpointNotBoundFaultException(java.lang.String s) {
           super(s);
        }

        public EndpointNotBoundFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public EndpointNotBoundFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.guest.EndpointNotBoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.guest.EndpointNotBoundFault getFaultMessage(){
       return faultMessage;
    }
}
    