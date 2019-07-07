
/**
 * VidyoPortalUserServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.user.v1_1;
    /**
     *  VidyoPortalUserServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalUserServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param inviteToConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.InviteToConferenceResponse inviteToConference
                (
                  com.vidyo.portal.user.v1_1.InviteToConferenceRequest inviteToConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchMembersRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchMembersResponse searchMembers
                (
                  com.vidyo.portal.user.v1_1.SearchMembersRequest searchMembersRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param raiseHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RaiseHandResponse raiseHand
                (
                  com.vidyo.portal.user.v1_1.RaiseHandRequest raiseHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchResponse search
                (
                  com.vidyo.portal.user.v1_1.SearchRequest searchRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createPublicRoomRequest
             * @throws PublicRoomCreationFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws NotAllowedToCreateFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreatePublicRoomResponse createPublicRoom
                (
                  com.vidyo.portal.user.v1_1.CreatePublicRoomRequest createPublicRoomRequest
                 )
            throws PublicRoomCreationFaultException,InvalidArgumentFaultException,NotAllowedToCreateFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteVideoServerAllRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MuteVideoServerAllResponse muteVideoServerAll
                (
                  com.vidyo.portal.user.v1_1.MuteVideoServerAllRequest muteVideoServerAllRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StopRecordingResponse stopRecording
                (
                  com.vidyo.portal.user.v1_1.StopRecordingRequest stopRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unmuteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UnmuteAudioResponse unmuteAudio
                (
                  com.vidyo.portal.user.v1_1.UnmuteAudioRequest unmuteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws ScheduledRoomCreationFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateScheduledRoomResponse createScheduledRoom
                (
                  com.vidyo.portal.user.v1_1.CreateScheduledRoomRequest createScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,ScheduledRoomCreationFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLectureModeParticipantsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetLectureModeParticipantsResponse getLectureModeParticipants
                (
                  com.vidyo.portal.user.v1_1.GetLectureModeParticipantsRequest getLectureModeParticipantsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param portalPrefixRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
             * @throws PrefixNotConfiguredFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.PortalPrefixResponse getPortalPrefix
                (
                  com.vidyo.portal.user.v1_1.PortalPrefixRequest portalPrefixRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException,PrefixNotConfiguredFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetWebcastURLResponse getWebcastURL
                (
                  com.vidyo.portal.user.v1_1.GetWebcastURLRequest getWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPINLengthRangeRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetPINLengthRangeResponse getPINLengthRange
                (
                  com.vidyo.portal.user.v1_1.GetPINLengthRangeRequest getPINLengthRangeRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRecordingProfilesRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetRecordingProfilesResponse getRecordingProfiles
                (
                  com.vidyo.portal.user.v1_1.GetRecordingProfilesRequest getRecordingProfilesRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateRoomResponse createRoom
                (
                  com.vidyo.portal.user.v1_1.CreateRoomRequest createRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removePresenterRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemovePresenterResponse removePresenter
                (
                  com.vidyo.portal.user.v1_1.RemovePresenterRequest removePresenterRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchMyContactsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchMyContactsResponse searchMyContacts
                (
                  com.vidyo.portal.user.v1_1.SearchMyContactsRequest searchMyContactsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param lockRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LockRoomResponse lockRoom
                (
                  com.vidyo.portal.user.v1_1.LockRoomRequest lockRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param myAccountRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MyAccountResponse myAccount
                (
                  com.vidyo.portal.user.v1_1.MyAccountRequest myAccountRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param dismissAllRaisedHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DismissAllRaisedHandResponse dismissAllRaisedHand
                (
                  com.vidyo.portal.user.v1_1.DismissAllRaisedHandRequest dismissAllRaisedHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param roomAccessOptionsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RoomAccessOptionsResponse getRoomAccessOptions
                (
                  com.vidyo.portal.user.v1_1.RoomAccessOptionsRequest roomAccessOptionsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteVideoClientAllRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MuteVideoClientAllResponse muteVideoClientAll
                (
                  com.vidyo.portal.user.v1_1.MuteVideoClientAllRequest muteVideoClientAllRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param resumeRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.ResumeRecordingResponse resumeRecording
                (
                  com.vidyo.portal.user.v1_1.ResumeRecordingRequest resumeRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomProfilesRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetRoomProfilesResponse getRoomProfiles
                (
                  com.vidyo.portal.user.v1_1.GetRoomProfilesRequest getRoomProfilesRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DeleteRoomResponse deleteRoom
                (
                  com.vidyo.portal.user.v1_1.DeleteRoomRequest deleteRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getEntityByRoomKeyRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetEntityByRoomKeyResponse getEntityByRoomKey
                (
                  com.vidyo.portal.user.v1_1.GetEntityByRoomKeyRequest getEntityByRoomKeyRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.ParticipantStatusResponse muteAudioLocal
                (
                  com.vidyo.portal.user.v1_1.MuteAudioRequest muteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveRoomPINResponse removeRoomPIN
                (
                  com.vidyo.portal.user.v1_1.RemoveRoomPINRequest removeRoomPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchRoomsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchRoomsResponse searchRooms
                (
                  com.vidyo.portal.user.v1_1.SearchRoomsRequest searchRoomsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param cancelOutboundCallRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CancelOutboundCallResponse cancelOutboundCall
                (
                  com.vidyo.portal.user.v1_1.CancelOutboundCallRequest cancelOutboundCallRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param dismissRaisedHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DismissRaisedHandResponse dismissRaisedHand
                (
                  com.vidyo.portal.user.v1_1.DismissRaisedHandRequest dismissRaisedHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param joinConferenceRequest
             * @throws WrongPINFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.JoinConferenceResponse joinConference
                (
                  com.vidyo.portal.user.v1_1.JoinConferenceRequest joinConferenceRequest
                 )
            throws WrongPINFaultException,InvalidArgumentFaultException,GeneralFaultException,ConferenceLockedFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getModeratorURLWithTokenRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse getModeratorURLWithToken
                (
                  com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest getModeratorURLWithTokenRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param linkEndpointRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws AccessRestrictedFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LinkEndpointResponse linkEndpoint
                (
                  com.vidyo.portal.user.v1_1.LinkEndpointRequest linkEndpointRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,AccessRestrictedFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserAccountTypeRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetUserAccountTypeResponse getUserAccountType
                (
                  com.vidyo.portal.user.v1_1.GetUserAccountTypeRequest getUserAccountTypeRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createWebcastPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateWebcastPINResponse createWebcastPIN
                (
                  com.vidyo.portal.user.v1_1.CreateWebcastPINRequest createWebcastPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setMemberModeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SetMemberModeResponse setMemberMode
                (
                  com.vidyo.portal.user.v1_1.SetMemberModeRequest setMemberModeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createTestcallRoomRequest
             * @throws TestcallRoomCreationFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateTestcallRoomResponse createTestcallRoom
                (
                  com.vidyo.portal.user.v1_1.CreateTestcallRoomRequest createTestcallRoomRequest
                 )
            throws TestcallRoomCreationFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DeleteScheduledRoomResponse deleteScheduledRoom
                (
                  com.vidyo.portal.user.v1_1.DeleteScheduledRoomRequest deleteScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param leaveConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LeaveConferenceResponse leaveConference
                (
                  com.vidyo.portal.user.v1_1.LeaveConferenceRequest leaveConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unraiseHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UnraiseHandResponse unraiseHand
                (
                  com.vidyo.portal.user.v1_1.UnraiseHandRequest unraiseHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StopVideoResponse stopVideo
                (
                  com.vidyo.portal.user.v1_1.StopVideoRequest stopVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param myEndpointStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MyEndpointStatusResponse myEndpointStatus
                (
                  com.vidyo.portal.user.v1_1.MyEndpointStatusRequest myEndpointStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getEntityByEntityIDRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetEntityByEntityIDResponse getEntityByEntityID
                (
                  com.vidyo.portal.user.v1_1.GetEntityByEntityIDRequest getEntityByEntityIDRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateRoomPINResponse createRoomPIN
                (
                  com.vidyo.portal.user.v1_1.CreateRoomPINRequest createRoomPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getConferenceIDRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
             * @throws InPointToPointCallFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetConferenceIDResponse getConferenceID
                (
                  com.vidyo.portal.user.v1_1.GetConferenceIDRequest getConferenceIDRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException,InPointToPointCallFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logoutAllOtherSessionsRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsResponse logoutAllOtherSessions
                (
                  com.vidyo.portal.user.v1_1.LogoutAllOtherSessionsRequest logoutAllOtherSessionsRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param onetimeAccessRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.OnetimeAccessResponse getOnetimeAccessUrl
                (
                  com.vidyo.portal.user.v1_1.OnetimeAccessRequest onetimeAccessRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateWebcastURLResponse createWebcastURL
                (
                  com.vidyo.portal.user.v1_1.CreateWebcastURLRequest createWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetRoomProfileResponse getRoomProfile
                (
                  com.vidyo.portal.user.v1_1.GetRoomProfileRequest getRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeModeratorPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveModeratorPINResponse removeModeratorPIN
                (
                  com.vidyo.portal.user.v1_1.RemoveModeratorPINRequest removeModeratorPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param silenceSpeakerServerRequest
             * @throws InvalidConferenceFaultException : 
             * @throws InvalidParticipantFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SilenceSpeakerServerResponse silenceSpeakerServer
                (
                  com.vidyo.portal.user.v1_1.SilenceSpeakerServerRequest silenceSpeakerServerRequest
                 )
            throws InvalidConferenceFaultException,InvalidParticipantFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLoginAndWelcomeBannerRequest
             * @throws GeneralFaultException : 
             * @throws FeatureNotAvailableFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerResponse getLoginAndWelcomeBanner
                (
                  com.vidyo.portal.user.v1_1.GetLoginAndWelcomeBannerRequest getLoginAndWelcomeBannerRequest
                 )
            throws GeneralFaultException,FeatureNotAvailableFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateRoomURLResponse createRoomURL
                (
                  com.vidyo.portal.user.v1_1.CreateRoomURLRequest createRoomURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getChangePasswordHtmlUrlWithTokenRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenResponse getChangePasswordHtmlUrlWithToken
                (
                  com.vidyo.portal.user.v1_1.GetChangePasswordHtmlUrlWithTokenRequest getChangePasswordHtmlUrlWithTokenRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updatePublicRoomDescriptionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws PublicRoomDescUpdationFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse updatePublicRoomDescription
                (
                  com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest updatePublicRoomDescriptionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,PublicRoomDescUpdationFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalFeaturesRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetPortalFeaturesResponse getPortalFeatures
                (
                  com.vidyo.portal.user.v1_1.GetPortalFeaturesRequest getPortalFeaturesRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startLectureModeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StartLectureModeResponse startLectureMode
                (
                  com.vidyo.portal.user.v1_1.StartLectureModeRequest startLectureModeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateAuthTokenRequest
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointNotBoundFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse generateAuthToken
                (
                  com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest generateAuthTokenRequest
                 )
            throws InvalidArgumentFaultException,EndpointNotBoundFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetPortalVersionResponse getPortalVersion
                (
                  com.vidyo.portal.user.v1_1.GetPortalVersionRequest getPortalVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getParticipantsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetParticipantsResponse getParticipants
                (
                  com.vidyo.portal.user.v1_1.GetParticipantsRequest getParticipantsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addToMyContactsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.AddToMyContactsResponse addToMyContacts
                (
                  com.vidyo.portal.user.v1_1.AddToMyContactsRequest addToMyContactsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updatePasswordRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UpdatePasswordResponse updatePassword
                (
                  com.vidyo.portal.user.v1_1.UpdatePasswordRequest updatePasswordRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param disconnectConferenceAllRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DisconnectConferenceAllResponse disconnectConferenceAll
                (
                  com.vidyo.portal.user.v1_1.DisconnectConferenceAllRequest disconnectConferenceAllRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setEndpointDetailsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointNotBoundFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse setEndpointDetails
                (
                  com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest setEndpointDetailsRequest
                 )
            throws InvalidArgumentFaultException,EndpointNotBoundFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.ParticipantStatusResponse muteVideoLocal
                (
                  com.vidyo.portal.user.v1_1.MuteVideoRequest muteVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioServerAllRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MuteAudioServerAllResponse muteAudioServerAll
                (
                  com.vidyo.portal.user.v1_1.MuteAudioServerAllRequest muteAudioServerAllRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLogAggregationServerRequest
             * @throws LogAggregationDisabledFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetLogAggregationServerResponse getLogAggregationServer
                (
                  com.vidyo.portal.user.v1_1.GetLogAggregationServerRequest getLogAggregationServerRequest
                 )
            throws LogAggregationDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveRoomURLResponse removeRoomURL
                (
                  com.vidyo.portal.user.v1_1.RemoveRoomURLRequest removeRoomURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateLanguageRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UpdateLanguageResponse updateLanguage
                (
                  com.vidyo.portal.user.v1_1.UpdateLanguageRequest updateLanguageRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logOutRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LogOutResponse logOut
                (
                  com.vidyo.portal.user.v1_1.LogOutRequest logOutRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getEntityDetailsByEntityIDRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse getEntityDetailsByEntityID
                (
                  com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest getEntityDetailsByEntityIDRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unlockRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.UnlockRoomResponse unlockRoom
                (
                  com.vidyo.portal.user.v1_1.UnlockRoomRequest unlockRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeModeratorURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse removeModeratorURL
                (
                  com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest removeModeratorURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeWebcastPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveWebcastPINResponse removeWebcastPIN
                (
                  com.vidyo.portal.user.v1_1.RemoveWebcastPINRequest removeWebcastPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveRoomProfileResponse removeRoomProfile
                (
                  com.vidyo.portal.user.v1_1.RemoveRoomProfileRequest removeRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param whatIsMyIPAddressRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.WhatIsMyIPAddressResponse whatIsMyIPAddress
                (
                  com.vidyo.portal.user.v1_1.WhatIsMyIPAddressRequest whatIsMyIPAddressRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param joinIPCConferenceRequest
             * @throws WrongPINFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.JoinIPCConferenceResponse joinIPCConference
                (
                  com.vidyo.portal.user.v1_1.JoinIPCConferenceRequest joinIPCConferenceRequest
                 )
            throws WrongPINFaultException,InvalidArgumentFaultException,GeneralFaultException,ConferenceLockedFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioClientAllRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MuteAudioClientAllResponse muteAudioClientAll
                (
                  com.vidyo.portal.user.v1_1.MuteAudioClientAllRequest muteAudioClientAllRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StartVideoResponse startVideo
                (
                  com.vidyo.portal.user.v1_1.StartVideoRequest startVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchByEntityIDRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchByEntityIDResponse searchByEntityID
                (
                  com.vidyo.portal.user.v1_1.SearchByEntityIDRequest searchByEntityIDRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getModeratorURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetModeratorURLResponse getModeratorURL
                (
                  com.vidyo.portal.user.v1_1.GetModeratorURLRequest getModeratorURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getVidyoReplayLibraryRequest
             * @throws VidyoReplayNotAvailableFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryResponse getVidyoReplayLibrary
                (
                  com.vidyo.portal.user.v1_1.GetVidyoReplayLibraryRequest getVidyoReplayLibraryRequest
                 )
            throws VidyoReplayNotAvailableFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param silenceSpeakerServerAllRequest
             * @throws InvalidConferenceFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllResponse silenceSpeakerServerAll
                (
                  com.vidyo.portal.user.v1_1.SilenceSpeakerServerAllRequest silenceSpeakerServerAllRequest
                 )
            throws InvalidConferenceFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createModeratorPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws InvalidModeratorPINFormatFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateModeratorPINResponse createModeratorPIN
                (
                  com.vidyo.portal.user.v1_1.CreateModeratorPINRequest createModeratorPINRequest
                 )
            throws InvalidArgumentFaultException,InvalidModeratorPINFormatFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserNameRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetUserNameResponse getUserName
                (
                  com.vidyo.portal.user.v1_1.GetUserNameRequest getUserNameRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param pauseRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.PauseRecordingResponse pauseRecording
                (
                  com.vidyo.portal.user.v1_1.PauseRecordingRequest pauseRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveWebcastURLResponse removeWebcastURL
                (
                  com.vidyo.portal.user.v1_1.RemoveWebcastURLRequest removeWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param logInRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.LogInResponse logIn
                (
                  com.vidyo.portal.user.v1_1.LogInRequest logInRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setPresenterRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SetPresenterResponse setPresenter
                (
                  com.vidyo.portal.user.v1_1.SetPresenterRequest setPresenterRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchByEmailRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SearchByEmailResponse searchByEmail
                (
                  com.vidyo.portal.user.v1_1.SearchByEmailRequest searchByEmailRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setThumbnailPhotoRequest
             * @throws FileTooLargeFaultException : 
             * @throws NotAllowedThumbnailPhotoFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse setThumbnailPhoto
                (
                  com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest setThumbnailPhotoRequest
                 )
            throws FileTooLargeFaultException,NotAllowedThumbnailPhotoFaultException,InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param directCallRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.DirectCallResponse directCall
                (
                  com.vidyo.portal.user.v1_1.DirectCallRequest directCallRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StartRecordingResponse startRecording
                (
                  com.vidyo.portal.user.v1_1.StartRecordingRequest startRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ResourceNotAvailableFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getActiveSessionsRequest
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetActiveSessionsResponse getActiveSessions
                (
                  com.vidyo.portal.user.v1_1.GetActiveSessionsRequest getActiveSessionsRequest
                 )
            throws GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopLectureModeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.StopLectureModeResponse stopLectureMode
                (
                  com.vidyo.portal.user.v1_1.StopLectureModeRequest stopLectureModeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.SetRoomProfileResponse setRoomProfile
                (
                  com.vidyo.portal.user.v1_1.SetRoomProfileRequest setRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getInviteContentRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.GetInviteContentResponse getInviteContent
                (
                  com.vidyo.portal.user.v1_1.GetInviteContentRequest getInviteContentRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioRequest1
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.MuteAudioResponse muteAudio
                (
                  com.vidyo.portal.user.v1_1.MuteAudioRequest muteAudioRequest1
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeFromMyContactsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws SeatLicenseExpiredFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.RemoveFromMyContactsResponse removeFromMyContacts
                (
                  com.vidyo.portal.user.v1_1.RemoveFromMyContactsRequest removeFromMyContactsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,SeatLicenseExpiredFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createModeratorURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ControlMeetingFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.user.v1_1.CreateModeratorURLResponse createModeratorURL
                (
                  com.vidyo.portal.user.v1_1.CreateModeratorURLRequest createModeratorURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException,NotLicensedFaultException;
        
         }
    