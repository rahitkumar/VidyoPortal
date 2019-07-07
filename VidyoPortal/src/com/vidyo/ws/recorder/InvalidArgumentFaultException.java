
/**
 * InvalidArgumentFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.ws.recorder;

public class InvalidArgumentFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1308341816696L;
    
    private com.vidyo.ws.recorder.InvalidArgumentFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.ws.recorder.InvalidArgumentFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.recorder.InvalidArgumentFault getFaultMessage(){
       return faultMessage;
    }
}
    