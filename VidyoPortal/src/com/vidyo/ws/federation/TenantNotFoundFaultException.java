
/**
 * TenantNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.federation;

public class TenantNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1364507615993L;
    
    private com.vidyo.ws.federation.TenantNotFoundFault faultMessage;

    
        public TenantNotFoundFaultException() {
            super("TenantNotFoundFaultException");
        }

        public TenantNotFoundFaultException(java.lang.String s) {
           super(s);
        }

        public TenantNotFoundFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public TenantNotFoundFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.federation.TenantNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.federation.TenantNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    