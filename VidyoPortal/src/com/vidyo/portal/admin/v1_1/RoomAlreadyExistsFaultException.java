
/**
 * RoomAlreadyExistsFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class RoomAlreadyExistsFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977570L;
    
    private com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault faultMessage;

    
        public RoomAlreadyExistsFaultException() {
            super("RoomAlreadyExistsFaultException");
        }

        public RoomAlreadyExistsFaultException(java.lang.String s) {
           super(s);
        }

        public RoomAlreadyExistsFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RoomAlreadyExistsFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.RoomAlreadyExistsFault getFaultMessage(){
       return faultMessage;
    }
}
    