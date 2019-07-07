
/**
 * UserNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.ws.replay;

public class UserNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1327444964561L;
    
    private com.vidyo.ws.replay.UserNotFoundFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.ws.replay.UserNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.replay.UserNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    