
/**
 * InvalidArgumentFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.guest;

public class InvalidArgumentFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504782303883L;
    
    private com.vidyo.portal.guest.InvalidArgumentFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.portal.guest.InvalidArgumentFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.guest.InvalidArgumentFault getFaultMessage(){
       return faultMessage;
    }
}
    