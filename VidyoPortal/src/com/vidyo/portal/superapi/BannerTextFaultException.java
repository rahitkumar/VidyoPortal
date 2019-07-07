
/**
 * BannerTextFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.superapi;

public class BannerTextFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1482962729955L;
    
    private com.vidyo.portal.superapi.BannerTextFault faultMessage;

    
        public BannerTextFaultException() {
            super("BannerTextFaultException");
        }

        public BannerTextFaultException(java.lang.String s) {
           super(s);
        }

        public BannerTextFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public BannerTextFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.superapi.BannerTextFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.superapi.BannerTextFault getFaultMessage(){
       return faultMessage;
    }
}
    