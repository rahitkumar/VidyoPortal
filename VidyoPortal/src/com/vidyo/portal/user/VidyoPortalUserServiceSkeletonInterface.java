
/**
 * VidyoPortalUserServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.user;
    /**
     *  VidyoPortalUserServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalUserServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param joinIPCConferenceRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws WrongPinFaultException : 
         */

        
                public com.vidyo.portal.user.JoinIPCConferenceResponse joinIPCConference
                (
                  com.vidyo.portal.user.JoinIPCConferenceRequest joinIPCConferenceRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ConferenceLockedFaultException,SeatLicenseExpiredFaultException,WrongPinFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchByEmailRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.SearchByEmailResponse searchByEmail
                (
                  com.vidyo.portal.user.SearchByEmailRequest searchByEmailRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomURLRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.CreateRoomURLResponse createRoomURL
                (
                  com.vidyo.portal.user.CreateRoomURLRequest createRoomURLRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unmuteAudioRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.UnmuteAudioResponse unmuteAudio
                (
                  com.vidyo.portal.user.UnmuteAudioRequest unmuteAudioRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setMemberModeRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.SetMemberModeResponse setMemberMode
                (
                  com.vidyo.portal.user.SetMemberModeRequest setMemberModeRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.SearchResponse search
                (
                  com.vidyo.portal.user.SearchRequest searchRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.CreateRoomResponse createRoom
                (
                  com.vidyo.portal.user.CreateRoomRequest createRoomRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserNameRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.user.GetUserNameResponse getUserName
                (
                  com.vidyo.portal.user.GetUserNameRequest getUserNameRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param inviteToConferenceRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.InviteToConferenceResponse inviteToConference
                (
                  com.vidyo.portal.user.InviteToConferenceRequest inviteToConferenceRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomPINRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.CreateRoomPINResponse createRoomPIN
                (
                  com.vidyo.portal.user.CreateRoomPINRequest createRoomPINRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logInRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.LogInResponse logIn
                (
                  com.vidyo.portal.user.LogInRequest logInRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.MuteAudioResponse muteAudio
                (
                  com.vidyo.portal.user.MuteAudioRequest muteAudioRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteRoomRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.DeleteRoomResponse deleteRoom
                (
                  com.vidyo.portal.user.DeleteRoomRequest deleteRoomRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalVersionRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.GetPortalVersionResponse getPortalVersion
                (
                  com.vidyo.portal.user.GetPortalVersionRequest getPortalVersionRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unlockRoomRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.UnlockRoomResponse unlockRoom
                (
                  com.vidyo.portal.user.UnlockRoomRequest unlockRoomRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchMyContactsRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.SearchMyContactsResponse searchMyContacts
                (
                  com.vidyo.portal.user.SearchMyContactsRequest searchMyContactsRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param lockRoomRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.LockRoomResponse lockRoom
                (
                  com.vidyo.portal.user.LockRoomRequest lockRoomRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updatePasswordRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.UpdatePasswordResponse updatePassword
                (
                  com.vidyo.portal.user.UpdatePasswordRequest updatePasswordRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateLanguageRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.UpdateLanguageResponse updateLanguage
                (
                  com.vidyo.portal.user.UpdateLanguageRequest updateLanguageRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param directCallRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.DirectCallResponse directCall
                (
                  com.vidyo.portal.user.DirectCallRequest directCallRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getEntityByRoomKeyRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.user.GetEntityByRoomKeyResponse getEntityByRoomKey
                (
                  com.vidyo.portal.user.GetEntityByRoomKeyRequest getEntityByRoomKeyRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param linkEndpointRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.LinkEndpointResponse linkEndpoint
                (
                  com.vidyo.portal.user.LinkEndpointRequest linkEndpointRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param leaveConferenceRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.LeaveConferenceResponse leaveConference
                (
                  com.vidyo.portal.user.LeaveConferenceRequest leaveConferenceRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomURLRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.RemoveRoomURLResponse removeRoomURL
                (
                  com.vidyo.portal.user.RemoveRoomURLRequest removeRoomURLRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param myEndpointStatusRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.MyEndpointStatusResponse myEndpointStatus
                (
                  com.vidyo.portal.user.MyEndpointStatusRequest myEndpointStatusRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getInviteContentRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.user.GetInviteContentResponse getInviteContent
                (
                  com.vidyo.portal.user.GetInviteContentRequest getInviteContentRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param myAccountRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.MyAccountResponse myAccount
                (
                  com.vidyo.portal.user.MyAccountRequest myAccountRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getEntityByEntityIDRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.GetEntityByEntityIDResponse getEntityByEntityID
                (
                  com.vidyo.portal.user.GetEntityByEntityIDRequest getEntityByEntityIDRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getParticipantsRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.GetParticipantsResponse getParticipants
                (
                  com.vidyo.portal.user.GetParticipantsRequest getParticipantsRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchByEntityIDRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.SearchByEntityIDResponse searchByEntityID
                (
                  com.vidyo.portal.user.SearchByEntityIDRequest searchByEntityIDRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomPINRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.RemoveRoomPINResponse removeRoomPIN
                (
                  com.vidyo.portal.user.RemoveRoomPINRequest removeRoomPINRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startVideoRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.StartVideoResponse startVideo
                (
                  com.vidyo.portal.user.StartVideoRequest startVideoRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopVideoRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.StopVideoResponse stopVideo
                (
                  com.vidyo.portal.user.StopVideoRequest stopVideoRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addToMyContactsRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.AddToMyContactsResponse addToMyContacts
                (
                  com.vidyo.portal.user.AddToMyContactsRequest addToMyContactsRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeFromMyContactsRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.RemoveFromMyContactsResponse removeFromMyContacts
                (
                  com.vidyo.portal.user.RemoveFromMyContactsRequest removeFromMyContactsRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logOutRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
         */

        
                public com.vidyo.portal.user.LogOutResponse logOut
                (
                  com.vidyo.portal.user.LogOutRequest logOutRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param joinConferenceRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws WrongPinFaultException : 
         */

        
                public com.vidyo.portal.user.JoinConferenceResponse joinConference
                (
                  com.vidyo.portal.user.JoinConferenceRequest joinConferenceRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ConferenceLockedFaultException,SeatLicenseExpiredFaultException,WrongPinFaultException;
        
         }
    