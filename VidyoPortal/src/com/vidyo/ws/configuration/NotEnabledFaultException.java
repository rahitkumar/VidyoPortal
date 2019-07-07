
/**
 * NotEnabledFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.configuration;

public class NotEnabledFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1496905469333L;
    
    private com.vidyo.ws.configuration.NotEnabledFault faultMessage;

    
        public NotEnabledFaultException() {
            super("NotEnabledFaultException");
        }

        public NotEnabledFaultException(java.lang.String s) {
           super(s);
        }

        public NotEnabledFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotEnabledFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.configuration.NotEnabledFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.configuration.NotEnabledFault getFaultMessage(){
       return faultMessage;
    }
}
    