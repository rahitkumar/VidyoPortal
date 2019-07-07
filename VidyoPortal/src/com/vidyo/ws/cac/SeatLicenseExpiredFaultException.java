
/**
 * SeatLicenseExpiredFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.cac;

public class SeatLicenseExpiredFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1470968343944L;
    
    private com.vidyo.ws.cac.SeatLicenseExpiredFault faultMessage;

    
        public SeatLicenseExpiredFaultException() {
            super("SeatLicenseExpiredFaultException");
        }

        public SeatLicenseExpiredFaultException(java.lang.String s) {
           super(s);
        }

        public SeatLicenseExpiredFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public SeatLicenseExpiredFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.cac.SeatLicenseExpiredFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.cac.SeatLicenseExpiredFault getFaultMessage(){
       return faultMessage;
    }
}
    