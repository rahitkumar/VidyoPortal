
/**
 * ConferenceLockedFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.user;

public class ConferenceLockedFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1358373547512L;
    
    private com.vidyo.portal.user.ConferenceLockedFault faultMessage;

    
        public ConferenceLockedFaultException() {
            super("ConferenceLockedFaultException");
        }

        public ConferenceLockedFaultException(java.lang.String s) {
           super(s);
        }

        public ConferenceLockedFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ConferenceLockedFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.user.ConferenceLockedFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.user.ConferenceLockedFault getFaultMessage(){
       return faultMessage;
    }
}
    