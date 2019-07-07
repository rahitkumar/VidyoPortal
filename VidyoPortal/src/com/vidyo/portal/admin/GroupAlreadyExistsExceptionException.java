
/**
 * GroupAlreadyExistsExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.admin;

public class GroupAlreadyExistsExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1326308883996L;
    
    private com.vidyo.portal.admin.GroupAlreadyExistsExceptionE faultMessage;

    
        public GroupAlreadyExistsExceptionException() {
            super("GroupAlreadyExistsExceptionException");
        }

        public GroupAlreadyExistsExceptionException(java.lang.String s) {
           super(s);
        }

        public GroupAlreadyExistsExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public GroupAlreadyExistsExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.GroupAlreadyExistsExceptionE msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.GroupAlreadyExistsExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    