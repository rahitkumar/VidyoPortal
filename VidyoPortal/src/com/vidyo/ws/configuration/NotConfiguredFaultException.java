
/**
 * NotConfiguredFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.configuration;

public class NotConfiguredFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1496905469339L;
    
    private com.vidyo.ws.configuration.NotConfiguredFault faultMessage;

    
        public NotConfiguredFaultException() {
            super("NotConfiguredFaultException");
        }

        public NotConfiguredFaultException(java.lang.String s) {
           super(s);
        }

        public NotConfiguredFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotConfiguredFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.configuration.NotConfiguredFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.configuration.NotConfiguredFault getFaultMessage(){
       return faultMessage;
    }
}
    