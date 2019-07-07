
/**
 * NotLicensedExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class NotLicensedExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308883910L;
    
    private com.vidyo.portal.admin.NotLicensedExceptionE faultMessage;

    
        public NotLicensedExceptionException() {
            super("NotLicensedExceptionException");
        }

        public NotLicensedExceptionException(java.lang.String s) {
           super(s);
        }

        public NotLicensedExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotLicensedExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.NotLicensedExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.NotLicensedExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    