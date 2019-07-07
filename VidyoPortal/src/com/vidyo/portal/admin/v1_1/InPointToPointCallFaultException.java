
/**
 * InPointToPointCallFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.admin.v1_1;

public class InPointToPointCallFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1497867977618L;
    
    private com.vidyo.portal.admin.v1_1.InPointToPointCallFault faultMessage;

    
        public InPointToPointCallFaultException() {
            super("InPointToPointCallFaultException");
        }

        public InPointToPointCallFaultException(java.lang.String s) {
           super(s);
        }

        public InPointToPointCallFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public InPointToPointCallFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.admin.v1_1.InPointToPointCallFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.admin.v1_1.InPointToPointCallFault getFaultMessage(){
       return faultMessage;
    }
}
    