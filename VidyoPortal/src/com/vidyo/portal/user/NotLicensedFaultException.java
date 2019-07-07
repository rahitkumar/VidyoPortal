
/**
 * NotLicensedFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.user;

public class NotLicensedFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1358373547489L;
    
    private com.vidyo.portal.user.NotLicensedFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.portal.user.NotLicensedFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.user.NotLicensedFault getFaultMessage(){
       return faultMessage;
    }
}
    