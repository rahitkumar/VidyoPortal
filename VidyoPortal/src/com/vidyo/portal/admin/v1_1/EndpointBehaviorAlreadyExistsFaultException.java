
/**
 * EndpointBehaviorAlreadyExistsFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class EndpointBehaviorAlreadyExistsFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977592L;
    
    private com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault faultMessage;

    
        public EndpointBehaviorAlreadyExistsFaultException() {
            super("EndpointBehaviorAlreadyExistsFaultException");
        }

        public EndpointBehaviorAlreadyExistsFaultException(java.lang.String s) {
           super(s);
        }

        public EndpointBehaviorAlreadyExistsFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public EndpointBehaviorAlreadyExistsFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.EndpointBehaviorAlreadyExistsFault getFaultMessage(){
       return faultMessage;
    }
}
    