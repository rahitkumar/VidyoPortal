
/**
 * EmailAddressNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.guest;

public class EmailAddressNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504782303870L;
    
    private com.vidyo.portal.guest.EmailAddressNotFoundFault faultMessage;

    
        public EmailAddressNotFoundFaultException() {
            super("EmailAddressNotFoundFaultException");
        }

        public EmailAddressNotFoundFaultException(java.lang.String s) {
           super(s);
        }

        public EmailAddressNotFoundFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public EmailAddressNotFoundFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.guest.EmailAddressNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.guest.EmailAddressNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    