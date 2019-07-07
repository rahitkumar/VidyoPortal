
/**
 * MissingArgumentFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class MissingArgumentFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962730016L;
    
    private com.vidyo.portal.superapi.MissingArgumentFault faultMessage;

    
        public MissingArgumentFaultException() {
            super("MissingArgumentFaultException");
        }

        public MissingArgumentFaultException(java.lang.String s) {
           super(s);
        }

        public MissingArgumentFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public MissingArgumentFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.MissingArgumentFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.MissingArgumentFault getFaultMessage(){
       return faultMessage;
    }
}
    