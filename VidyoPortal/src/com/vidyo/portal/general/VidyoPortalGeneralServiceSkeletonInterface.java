
/**
 * VidyoPortalGeneralServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.general;
    /**
     *  VidyoPortalGeneralServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalGeneralServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param transactionStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.general.TransactionStatusResponse getTransactionStatus
                (
                  com.vidyo.portal.general.TransactionStatusRequest transactionStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logInTypeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.general.LogInTypeResponse getLogInType
                (
                  com.vidyo.portal.general.LogInTypeRequest logInTypeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param webRTCLoginIsEnabledRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.general.WebRTCLoginIsEnabledResponse webRTCLoginIsEnabled
                (
                  com.vidyo.portal.general.WebRTCLoginIsEnabledRequest webRTCLoginIsEnabledRequest
                 )
            throws GeneralFaultException;
        
         }
    