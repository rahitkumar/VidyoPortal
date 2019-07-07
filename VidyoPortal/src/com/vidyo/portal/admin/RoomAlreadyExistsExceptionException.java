
/**
 * RoomAlreadyExistsExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class RoomAlreadyExistsExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308884007L;
    
    private com.vidyo.portal.admin.RoomAlreadyExistsExceptionE faultMessage;

    
        public RoomAlreadyExistsExceptionException() {
            super("RoomAlreadyExistsExceptionException");
        }

        public RoomAlreadyExistsExceptionException(java.lang.String s) {
           super(s);
        }

        public RoomAlreadyExistsExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RoomAlreadyExistsExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.RoomAlreadyExistsExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.RoomAlreadyExistsExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    