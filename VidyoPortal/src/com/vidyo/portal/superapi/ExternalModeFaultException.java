
/**
 * ExternalModeFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class ExternalModeFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962730081L;
    
    private com.vidyo.portal.superapi.ExternalModeFault faultMessage;

    
        public ExternalModeFaultException() {
            super("ExternalModeFaultException");
        }

        public ExternalModeFaultException(java.lang.String s) {
           super(s);
        }

        public ExternalModeFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExternalModeFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.ExternalModeFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.ExternalModeFault getFaultMessage(){
       return faultMessage;
    }
}
    