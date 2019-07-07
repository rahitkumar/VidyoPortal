
/**
 * ExistingTenantFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class ExistingTenantFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962730048L;
    
    private com.vidyo.portal.superapi.ExistingTenantFault faultMessage;

    
        public ExistingTenantFaultException() {
            super("ExistingTenantFaultException");
        }

        public ExistingTenantFaultException(java.lang.String s) {
           super(s);
        }

        public ExistingTenantFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExistingTenantFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.ExistingTenantFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.ExistingTenantFault getFaultMessage(){
       return faultMessage;
    }
}
    