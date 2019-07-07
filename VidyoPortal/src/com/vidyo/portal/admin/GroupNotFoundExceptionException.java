
/**
 * GroupNotFoundExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class GroupNotFoundExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308883986L;
    
    private com.vidyo.portal.admin.GroupNotFoundExceptionE faultMessage;

    
        public GroupNotFoundExceptionException() {
            super("GroupNotFoundExceptionException");
        }

        public GroupNotFoundExceptionException(java.lang.String s) {
           super(s);
        }

        public GroupNotFoundExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public GroupNotFoundExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.GroupNotFoundExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.GroupNotFoundExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    