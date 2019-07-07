
/**
 * RoomDisabledFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.ws.desktop;

public class RoomDisabledFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1371569917407L;
    
    private com.vidyo.ws.desktop.RoomDisabledFault faultMessage;

    
        public RoomDisabledFaultException() {
            super("RoomDisabledFaultException");
        }

        public RoomDisabledFaultException(java.lang.String s) {
           super(s);
        }

        public RoomDisabledFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RoomDisabledFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.ws.desktop.RoomDisabledFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.ws.desktop.RoomDisabledFault getFaultMessage(){
       return faultMessage;
    }
}
    