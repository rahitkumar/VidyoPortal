
/**
 * GeneralFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.configuration;

public class GeneralFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1496905469349L;
    
    private com.vidyo.ws.configuration.GeneralFault faultMessage;

    
        public GeneralFaultException() {
            super("GeneralFaultException");
        }

        public GeneralFaultException(java.lang.String s) {
           super(s);
        }

        public GeneralFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public GeneralFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.configuration.GeneralFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.configuration.GeneralFault getFaultMessage(){
       return faultMessage;
    }
}
    