
/**
 * NotAllowedThumbnailPhotoFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.vidyo.portal.user.v1_1;

public class NotAllowedThumbnailPhotoFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1504103137503L;
    
    private com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault faultMessage;

    
        public NotAllowedThumbnailPhotoFaultException() {
            super("NotAllowedThumbnailPhotoFaultException");
        }

        public NotAllowedThumbnailPhotoFaultException(java.lang.String s) {
           super(s);
        }

        public NotAllowedThumbnailPhotoFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public NotAllowedThumbnailPhotoFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault msg){
       faultMessage = msg;
    }
    
    public com.vidyo.portal.user.v1_1.NotAllowedThumbnailPhotoFault getFaultMessage(){
       return faultMessage;
    }
}
    