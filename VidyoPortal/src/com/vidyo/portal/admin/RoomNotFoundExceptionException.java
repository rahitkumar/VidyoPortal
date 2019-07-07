
/**
 * RoomNotFoundExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class RoomNotFoundExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308883965L;
    
    private com.vidyo.portal.admin.RoomNotFoundExceptionE faultMessage;

    
        public RoomNotFoundExceptionException() {
            super("RoomNotFoundExceptionException");
        }

        public RoomNotFoundExceptionException(java.lang.String s) {
           super(s);
        }

        public RoomNotFoundExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RoomNotFoundExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.RoomNotFoundExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.RoomNotFoundExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    