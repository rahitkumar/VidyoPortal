
/**
 * NotAuthorizedFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class NotAuthorizedFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962730167L;
    
    private com.vidyo.portal.superapi.NotAuthorizedFault faultMessage;

    
        public NotAuthorizedFaultException() {
            super("NotAuthorizedFaultException");
        }

        public NotAuthorizedFaultException(java.lang.String s) {
           super(s);
        }

        public NotAuthorizedFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotAuthorizedFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.NotAuthorizedFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.NotAuthorizedFault getFaultMessage(){
       return faultMessage;
    }
}
    