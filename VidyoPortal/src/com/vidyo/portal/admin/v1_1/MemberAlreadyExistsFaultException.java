
/**
 * MemberAlreadyExistsFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class MemberAlreadyExistsFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977583L;
    
    private com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault faultMessage;

    
        public MemberAlreadyExistsFaultException() {
            super("MemberAlreadyExistsFaultException");
        }

        public MemberAlreadyExistsFaultException(java.lang.String s) {
           super(s);
        }

        public MemberAlreadyExistsFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public MemberAlreadyExistsFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.MemberAlreadyExistsFault getFaultMessage(){
       return faultMessage;
    }
}
    