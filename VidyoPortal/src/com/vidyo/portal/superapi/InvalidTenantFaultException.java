
/**
 * InvalidTenantFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class InvalidTenantFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962730224L;
    
    private com.vidyo.portal.superapi.InvalidTenantFault faultMessage;

    
        public InvalidTenantFaultException() {
            super("InvalidTenantFaultException");
        }

        public InvalidTenantFaultException(java.lang.String s) {
           super(s);
        }

        public InvalidTenantFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public InvalidTenantFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.InvalidTenantFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.InvalidTenantFault getFaultMessage(){
       return faultMessage;
    }
}
    