
/**
 * VidyoPortalGuestServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.guest;
    /**
     *  VidyoPortalGuestServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalGuestServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Get the current active client version
                                    * @param clientVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.ClientVersionResponse getClientVersion
                (
                  com.vidyo.portal.guest.ClientVersionRequest clientVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param recoverPasswordRequest
             * @throws EmailAddressNotFoundFaultException : 
             * @throws NotificationEmailNotConfiguredFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.RecoverPasswordResponse recoverPassword
                (
                  com.vidyo.portal.guest.RecoverPasswordRequest recoverPasswordRequest
                 )
            throws EmailAddressNotFoundFaultException,NotificationEmailNotConfiguredFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param raiseHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.RaiseHandResponse raiseHand
                (
                  com.vidyo.portal.guest.RaiseHandRequest raiseHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param guestJoinConferenceRequest
             * @throws RoomIsFullFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws WrongPinFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws AllLinesInUseFaultException : 
         */

        
                public com.vidyo.portal.guest.GuestJoinConferenceResponse guestJoinConference
                (
                  com.vidyo.portal.guest.GuestJoinConferenceRequest guestJoinConferenceRequest
                 )
            throws RoomIsFullFaultException,InvalidArgumentFaultException,WrongPinFaultException,GeneralFaultException,ResourceNotAvailableFaultException,ConferenceLockedFaultException,AllLinesInUseFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unraiseHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.UnraiseHandResponse unraiseHand
                (
                  com.vidyo.portal.guest.UnraiseHandRequest unraiseHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param linkEndpointToGuestRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws AccessRestrictedFaultException : 
         */

        
                public com.vidyo.portal.guest.LinkEndpointToGuestResponse linkEndpointToGuest
                (
                  com.vidyo.portal.guest.LinkEndpointToGuestRequest linkEndpointToGuestRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,AccessRestrictedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomDetailsByRoomKeyRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
         */

        
                public com.vidyo.portal.guest.GetRoomDetailsByRoomKeyResponse getRoomDetailsByRoomKey
                (
                  com.vidyo.portal.guest.GetRoomDetailsByRoomKeyRequest getRoomDetailsByRoomKeyRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ResourceNotAvailableFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalFeaturesRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.GetPortalFeaturesResponse getPortalFeatures
                (
                  com.vidyo.portal.guest.GetPortalFeaturesRequest getPortalFeaturesRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalVersionRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.GetPortalVersionResponse getPortalVersion
                (
                  com.vidyo.portal.guest.GetPortalVersionRequest getPortalVersionRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param whatIsMyIPAddressRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.WhatIsMyIPAddressResponse whatIsMyIPAddress
                (
                  com.vidyo.portal.guest.WhatIsMyIPAddressRequest whatIsMyIPAddressRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logInAsGuestRequest
             * @throws RoomIsFullFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
         */

        
                public com.vidyo.portal.guest.LogInAsGuestResponse logInAsGuest
                (
                  com.vidyo.portal.guest.LogInAsGuestRequest logInAsGuestRequest
                 )
            throws RoomIsFullFaultException,InvalidArgumentFaultException,GeneralFaultException,ResourceNotAvailableFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createTestcallRoomRequest
             * @throws TestcallRoomCreationFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.CreateTestcallRoomResponse createTestcallRoom
                (
                  com.vidyo.portal.guest.CreateTestcallRoomRequest createTestcallRoomRequest
                 )
            throws TestcallRoomCreationFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setEndpointDetailsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointNotBoundFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.guest.SetEndpointDetailsResponse setEndpointDetails
                (
                  com.vidyo.portal.guest.SetEndpointDetailsRequest setEndpointDetailsRequest
                 )
            throws InvalidArgumentFaultException,EndpointNotBoundFaultException,GeneralFaultException;
        
         }
    