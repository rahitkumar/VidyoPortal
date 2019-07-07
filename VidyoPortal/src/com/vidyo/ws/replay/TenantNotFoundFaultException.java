
/**
 * TenantNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.ws.replay;

public class TenantNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1327444964587L;
    
    private com.vidyo.ws.replay.TenantNotFoundFault faultMessage;

    
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
    

    public void setFaultMessage(com.vidyo.ws.replay.TenantNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.replay.TenantNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    