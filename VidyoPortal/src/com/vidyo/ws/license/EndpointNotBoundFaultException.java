
/**
 * EndpointNotBoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.license;

public class EndpointNotBoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1375815333707L;
    
    private com.vidyo.ws.license.EndpointNotBoundFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.ws.license.EndpointNotBoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.license.EndpointNotBoundFault getFaultMessage(){
       return faultMessage;
    }
}
    