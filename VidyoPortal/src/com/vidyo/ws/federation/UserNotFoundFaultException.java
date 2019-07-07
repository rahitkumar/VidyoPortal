
/**
 * UserNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.federation;

public class UserNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1364507615978L;
    
    private com.vidyo.ws.federation.UserNotFoundFault faultMessage;

    
        public UserNotFoundFaultException() {
            super("UserNotFoundFaultException");
        }

        public UserNotFoundFaultException(java.lang.String s) {
           super(s);
        }

        public UserNotFoundFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public UserNotFoundFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.federation.UserNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.federation.UserNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    