
/**
 * PublicRoomCreationFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class PublicRoomCreationFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977574L;
    
    private com.vidyo.portal.admin.v1_1.PublicRoomCreationFault faultMessage;

    
        public PublicRoomCreationFaultException() {
            super("PublicRoomCreationFaultException");
        }

        public PublicRoomCreationFaultException(java.lang.String s) {
           super(s);
        }

        public PublicRoomCreationFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public PublicRoomCreationFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.PublicRoomCreationFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.PublicRoomCreationFault getFaultMessage(){
       return faultMessage;
    }
}
    