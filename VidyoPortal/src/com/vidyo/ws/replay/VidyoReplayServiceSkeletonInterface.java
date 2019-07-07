
/**
 * VidyoReplayServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
    package com.vidyo.ws.replay;
    /**
     *  VidyoReplayServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoReplayServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Retrive user details
                                    * @param getUserByUsernameRequest
             * @throws InvalidArgumentFaultException : 
             * @throws UserNotFoundFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.replay.GetUserByUsernameResponse getUserByUsername
                (
                  com.vidyo.ws.replay.GetUserByUsernameRequest getUserByUsernameRequest
                 )
            throws InvalidArgumentFaultException,UserNotFoundFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive tenant name
                                    * @param getTenantByHostRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws TenantNotFoundFaultException : 
         */

        
                public com.vidyo.ws.replay.GetTenantByHostResponse getTenantByHost
                (
                  com.vidyo.ws.replay.GetTenantByHostRequest getTenantByHostRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,TenantNotFoundFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive tenant's Terms Of Service content
                                    * @param getTermsOfServiceForTenantRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws TenantNotFoundFaultException : 
         */

        
                public com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse getTermsOfServiceForTenant
                (
                  com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest getTermsOfServiceForTenantRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,TenantNotFoundFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive tenant's Contact Us content
                                    * @param getContactUsForTenantRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws TenantNotFoundFaultException : 
         */

        
                public com.vidyo.ws.replay.GetContactUsForTenantResponse getContactUsForTenant
                (
                  com.vidyo.ws.replay.GetContactUsForTenantRequest getContactUsForTenantRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,TenantNotFoundFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive tenant's Logo as binary stream (or Default)
                                    * @param getLogoForTenantRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws TenantNotFoundFaultException : 
         */

        
                public com.vidyo.ws.replay.GetLogoForTenantResponse getLogoForTenant
                (
                  com.vidyo.ws.replay.GetLogoForTenantRequest getLogoForTenantRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,TenantNotFoundFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive user details
                                    * @param getUserByTokenRequest
             * @throws InvalidArgumentFaultException : 
             * @throws UserNotFoundFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.replay.GetUserByTokenResponse getUserByToken
                (
                  com.vidyo.ws.replay.GetUserByTokenRequest getUserByTokenRequest
                 )
            throws InvalidArgumentFaultException,UserNotFoundFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrive tenant's About Us content
                                    * @param getAboutUsForTenantRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws TenantNotFoundFaultException : 
         */

        
                public com.vidyo.ws.replay.GetAboutUsForTenantResponse getAboutUsForTenant
                (
                  com.vidyo.ws.replay.GetAboutUsForTenantRequest getAboutUsForTenantRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,TenantNotFoundFaultException;
        
         }
    