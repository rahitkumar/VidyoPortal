
/**
 * GroupAlreadyExistsFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class GroupAlreadyExistsFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977579L;
    
    private com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault faultMessage;

    
        public GroupAlreadyExistsFaultException() {
            super("GroupAlreadyExistsFaultException");
        }

        public GroupAlreadyExistsFaultException(java.lang.String s) {
           super(s);
        }

        public GroupAlreadyExistsFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public GroupAlreadyExistsFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.GroupAlreadyExistsFault getFaultMessage(){
       return faultMessage;
    }
}
    