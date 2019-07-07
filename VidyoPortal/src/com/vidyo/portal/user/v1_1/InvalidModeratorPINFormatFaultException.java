
/**
 * InvalidModeratorPINFormatFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.user.v1_1;

public class InvalidModeratorPINFormatFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504103137506L;
    
    private com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault faultMessage;

    
        public InvalidModeratorPINFormatFaultException() {
            super("InvalidModeratorPINFormatFaultException");
        }

        public InvalidModeratorPINFormatFaultException(java.lang.String s) {
           super(s);
        }

        public InvalidModeratorPINFormatFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public InvalidModeratorPINFormatFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.user.v1_1.InvalidModeratorPINFormatFault getFaultMessage(){
       return faultMessage;
    }
}
    