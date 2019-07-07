
/**
 * AllLinesInUseFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.guest;

public class AllLinesInUseFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504782303911L;
    
    private com.vidyo.portal.guest.AllLinesInUseFault faultMessage;

    
        public AllLinesInUseFaultException() {
            super("AllLinesInUseFaultException");
        }

        public AllLinesInUseFaultException(java.lang.String s) {
           super(s);
        }

        public AllLinesInUseFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AllLinesInUseFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.guest.AllLinesInUseFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.guest.AllLinesInUseFault getFaultMessage(){
       return faultMessage;
    }
}
    