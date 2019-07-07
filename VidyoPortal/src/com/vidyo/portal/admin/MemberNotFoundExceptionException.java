
/**
 * MemberNotFoundExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class MemberNotFoundExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308883975L;
    
    private com.vidyo.portal.admin.MemberNotFoundExceptionE faultMessage;

    
        public MemberNotFoundExceptionException() {
            super("MemberNotFoundExceptionException");
        }

        public MemberNotFoundExceptionException(java.lang.String s) {
           super(s);
        }

        public MemberNotFoundExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public MemberNotFoundExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.MemberNotFoundExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.MemberNotFoundExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    