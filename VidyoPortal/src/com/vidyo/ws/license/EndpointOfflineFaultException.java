
/**
 * EndpointOfflineFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.license;

public class EndpointOfflineFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1375815333725L;
    
    private com.vidyo.ws.license.EndpointOfflineFault faultMessage;

    
        public EndpointOfflineFaultException() {
            super("EndpointOfflineFaultException");
        }

        public EndpointOfflineFaultException(java.lang.String s) {
           super(s);
        }

        public EndpointOfflineFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public EndpointOfflineFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.license.EndpointOfflineFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.license.EndpointOfflineFault getFaultMessage(){
       return faultMessage;
    }
}
    