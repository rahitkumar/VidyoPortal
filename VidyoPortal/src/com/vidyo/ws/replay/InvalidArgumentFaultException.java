
/**
 * InvalidArgumentFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.ws.replay;

public class InvalidArgumentFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1327444964548L;
    
    private com.vidyo.ws.replay.InvalidArgumentFault faultMessage;

    
        public InvalidArgumentFaultException() {
            super("InvalidArgumentFaultException");
        }

        public InvalidArgumentFaultException(java.lang.String s) {
           super(s);
        }

        public InvalidArgumentFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public InvalidArgumentFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.replay.InvalidArgumentFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.replay.InvalidArgumentFault getFaultMessage(){
       return faultMessage;
    }
}
    