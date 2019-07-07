
/**
 * ResourceNotAvailableFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.manager;

public class ResourceNotAvailableFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1395170283887L;
    
    private com.vidyo.ws.manager.ResourceNotAvailableFault faultMessage;

    
        public ResourceNotAvailableFaultException() {
            super("ResourceNotAvailableFaultException");
        }

        public ResourceNotAvailableFaultException(java.lang.String s) {
           super(s);
        }

        public ResourceNotAvailableFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ResourceNotAvailableFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.manager.ResourceNotAvailableFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.manager.ResourceNotAvailableFault getFaultMessage(){
       return faultMessage;
    }
}
    