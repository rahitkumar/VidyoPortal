
/**
 * RoomNotFoundFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class RoomNotFoundFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977602L;
    
    private com.vidyo.portal.admin.v1_1.RoomNotFoundFault faultMessage;

    
        public RoomNotFoundFaultException() {
            super("RoomNotFoundFaultException");
        }

        public RoomNotFoundFaultException(java.lang.String s) {
           super(s);
        }

        public RoomNotFoundFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RoomNotFoundFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.RoomNotFoundFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.RoomNotFoundFault getFaultMessage(){
       return faultMessage;
    }
}
    