
/**
 * ConferenceNotExistFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.manager;

public class ConferenceNotExistFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395170283901L;
    
    private com.vidyo.ws.manager.ConferenceNotExistFault faultMessage;

    
        public ConferenceNotExistFaultException() {
            super("ConferenceNotExistFaultException");
        }

        public ConferenceNotExistFaultException(java.lang.String s) {
           super(s);
        }

        public ConferenceNotExistFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ConferenceNotExistFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.manager.ConferenceNotExistFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.manager.ConferenceNotExistFault getFaultMessage(){
       return faultMessage;
    }
}
    