
/**
 * VidyoPortalCACServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.cac;
    /**
     *  VidyoPortalCACServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalCACServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateAuthTokenRequest
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointNotBoundFaultException : 
             * @throws GeneralFaultException : 
         * @throws SeatLicenseExpiredFaultException 
         * @throws NotLicensedFaultException 
         */

        
                public com.vidyo.ws.cac.GenerateAuthTokenResponse generateAuthToken
                (
                  com.vidyo.ws.cac.GenerateAuthTokenRequest generateAuthTokenRequest
                 )
            throws InvalidArgumentFaultException,EndpointNotBoundFaultException,GeneralFaultException, NotLicensedFaultException, SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param linkEndpointRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws AccessRestrictedFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.ws.cac.LinkEndpointResponse linkEndpoint
                (
                  com.vidyo.ws.cac.LinkEndpointRequest linkEndpointRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,AccessRestrictedFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserNameRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.ws.cac.GetUserNameResponse getUserName
                (
                  com.vidyo.ws.cac.GetUserNameRequest getUserNameRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logInRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.ws.cac.LogInResponse logIn
                (
                  com.vidyo.ws.cac.LogInRequest logInRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         }
    