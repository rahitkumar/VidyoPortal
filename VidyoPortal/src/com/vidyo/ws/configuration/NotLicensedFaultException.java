
/**
 * NotLicensedFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.configuration;

public class NotLicensedFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1496905469363L;
    
    private com.vidyo.ws.configuration.NotLicensedFault faultMessage;

    
        public NotLicensedFaultException() {
            super("NotLicensedFaultException");
        }

        public NotLicensedFaultException(java.lang.String s) {
           super(s);
        }

        public NotLicensedFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotLicensedFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.configuration.NotLicensedFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.configuration.NotLicensedFault getFaultMessage(){
       return faultMessage;
    }
}
    