
/**
 * VidyoDesktopServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.desktop;
    /**
     *  VidyoDesktopServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoDesktopServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param createMyRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.CreateMyRoomURLResponse createMyRoomURL
                (
                  com.vidyo.ws.desktop.CreateMyRoomURLRequest createMyRoomURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startMyConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomDisabledFaultException : 
         */

        
                public com.vidyo.ws.desktop.StartMyConferenceResponse startMyConference
                (
                  com.vidyo.ws.desktop.StartMyConferenceRequest startMyConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param joinConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.JoinConferenceResponse joinConference
                (
                  com.vidyo.ws.desktop.JoinConferenceRequest joinConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateAuthTokenRequest
             * @throws EndpointNotBoundFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.GenerateAuthTokenResponse generateAuthToken
                (
                  com.vidyo.ws.desktop.GenerateAuthTokenRequest generateAuthTokenRequest
                 )
            throws EndpointNotBoundFaultException,InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param inviteToConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.InviteToConferenceResponse inviteToConference
                (
                  com.vidyo.ws.desktop.InviteToConferenceRequest inviteToConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserDataRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.GetUserDataResponse getUserData
                (
                  com.vidyo.ws.desktop.GetUserDataRequest getUserDataRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.GetUserStatusResponse getUserStatus
                (
                  com.vidyo.ws.desktop.GetUserStatusRequest getUserStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * get Browser Access Key
                                    * @param browserAccessKeyRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.desktop.BrowserAccessKeyResponse getBrowserAccessKey
                (
                  com.vidyo.ws.desktop.BrowserAccessKeyRequest browserAccessKeyRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         }
    