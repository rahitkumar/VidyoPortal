
/**
 * VidyoPortalAdminServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.admin.v1_1;
    /**
     *  VidyoPortalAdminServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalAdminServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveRoomURLResponse removeRoomURL
                (
                  com.vidyo.portal.admin.v1_1.RemoveRoomURLRequest removeRoomURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param dismissAllRaisedHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DismissAllRaisedHandResponse dismissAllRaisedHand
                (
                  com.vidyo.portal.admin.v1_1.DismissAllRaisedHandRequest dismissAllRaisedHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getParticipantsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetParticipantsResponse getParticipants
                (
                  com.vidyo.portal.admin.v1_1.GetParticipantsRequest getParticipantsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetPortalVersionResponse getPortalVersion
                (
                  com.vidyo.portal.admin.v1_1.GetPortalVersionRequest getPortalVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws InvalidModeratorPINFormatFaultException : 
             * @throws NotLicensedFaultException : 
             * @throws ScheduledRoomCreationFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateScheduledRoomResponse createScheduledRoom
                (
                  com.vidyo.portal.admin.v1_1.CreateScheduledRoomRequest createScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,InvalidModeratorPINFormatFaultException,NotLicensedFaultException,ScheduledRoomCreationFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetWebcastURLResponse getWebcastURL
                (
                  com.vidyo.portal.admin.v1_1.GetWebcastURLRequest getWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLectureModeParticipantsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsResponse getLectureModeParticipants
                (
                  com.vidyo.portal.admin.v1_1.GetLectureModeParticipantsRequest getLectureModeParticipantsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param enableScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.EnableScheduledRoomResponse enableScheduledRoom
                (
                  com.vidyo.portal.admin.v1_1.EnableScheduledRoomRequest enableScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addMemberRequest
             * @throws MemberAlreadyExistsFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.AddMemberResponse addMember
                (
                  com.vidyo.portal.admin.v1_1.AddMemberRequest addMemberRequest
                 )
            throws MemberAlreadyExistsFaultException,InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRecordingProfilesRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetRecordingProfilesResponse getRecordingProfiles
                (
                  com.vidyo.portal.admin.v1_1.GetRecordingProfilesRequest getRecordingProfilesRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removePresenterRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemovePresenterResponse removePresenter
                (
                  com.vidyo.portal.admin.v1_1.RemovePresenterRequest removePresenterRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getTenantDetailsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetTenantDetailsResponse getTenantDetails
                (
                  com.vidyo.portal.admin.v1_1.GetTenantDetailsRequest getTenantDetailsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchMembersRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SearchMembersResponse searchMembers
                (
                  com.vidyo.portal.admin.v1_1.SearchMembersRequest searchMembersRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetRoomResponse getRoom
                (
                  com.vidyo.portal.admin.v1_1.GetRoomRequest getRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
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

        
                public com.vidyo.portal.admin.v1_1.CreatePublicRoomResponse createPublicRoom
                (
                  com.vidyo.portal.admin.v1_1.CreatePublicRoomRequest createPublicRoomRequest
                 )
            throws PublicRoomCreationFaultException,InvalidArgumentFaultException,NotAllowedToCreateFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * Allows for adding an additional client version at the super level. This new client version will be seen by all tenants. The new client version will not be activated.
                                    * @param addClientVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ExternalModeFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.AddClientVersionResponse addClientVersion
                (
                  com.vidyo.portal.admin.v1_1.AddClientVersionRequest addClientVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ExternalModeFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param transferParticipantRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.TransferParticipantResponse transferParticipant
                (
                  com.vidyo.portal.admin.v1_1.TransferParticipantRequest transferParticipantRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateRoomURLResponse createRoomURL
                (
                  com.vidyo.portal.admin.v1_1.CreateRoomURLRequest createRoomURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StopRecordingResponse stopRecording
                (
                  com.vidyo.portal.admin.v1_1.StopRecordingRequest stopRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unmuteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.UnmuteAudioResponse unmuteAudio
                (
                  com.vidyo.portal.admin.v1_1.UnmuteAudioRequest unmuteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param roomIsEnabledRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RoomIsEnabledResponse roomIsEnabled
                (
                  com.vidyo.portal.admin.v1_1.RoomIsEnabledRequest roomIsEnabledRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addGroupRequest
             * @throws GroupAlreadyExistsFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.AddGroupResponse addGroup
                (
                  com.vidyo.portal.admin.v1_1.AddGroupRequest addGroupRequest
                 )
            throws GroupAlreadyExistsFaultException,InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * For deleting end point behavior at tenant level
                                    * @param deleteEndpointBehaviorRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NoEndpointBehaviorExistsFaultException : 
             * @throws GeneralFaultException : 
             * @throws EndpointBehaviorDisabledFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorResponse deleteEndpointBehavior
                (
                  com.vidyo.portal.admin.v1_1.DeleteEndpointBehaviorRequest deleteEndpointBehaviorRequest
                 )
            throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,GeneralFaultException,EndpointBehaviorDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetRoomsResponse getRooms
                (
                  com.vidyo.portal.admin.v1_1.GetRoomsRequest getRoomsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteGroupRequest
             * @throws GroupNotFoundFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DeleteGroupResponse deleteGroup
                (
                  com.vidyo.portal.admin.v1_1.DeleteGroupRequest deleteGroupRequest
                 )
            throws GroupNotFoundFaultException,InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startLectureModeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StartLectureModeResponse startLectureMode
                (
                  com.vidyo.portal.admin.v1_1.StartLectureModeRequest startLectureModeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateWebcastURLResponse createWebcastURL
                (
                  com.vidyo.portal.admin.v1_1.CreateWebcastURLRequest createWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param inviteToConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.InviteToConferenceResponse inviteToConference
                (
                  com.vidyo.portal.admin.v1_1.InviteToConferenceRequest inviteToConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeModeratorPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveModeratorPINResponse removeModeratorPIN
                (
                  com.vidyo.portal.admin.v1_1.RemoveModeratorPINRequest removeModeratorPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetRoomProfileResponse getRoomProfile
                (
                  com.vidyo.portal.admin.v1_1.GetRoomProfileRequest getRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLicenseDataRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetLicenseDataResponse getLicenseData
                (
                  com.vidyo.portal.admin.v1_1.GetLicenseDataRequest getLicenseDataRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setTenantRoomAttributesRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesResponse setTenantRoomAttributes
                (
                  com.vidyo.portal.admin.v1_1.SetTenantRoomAttributesRequest setTenantRoomAttributesRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateGroupRequest
             * @throws GroupNotFoundFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.UpdateGroupResponse updateGroup
                (
                  com.vidyo.portal.admin.v1_1.UpdateGroupRequest updateGroupRequest
                 )
            throws GroupNotFoundFaultException,InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param scheduledRoomEnabledRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledResponse isScheduledRoomEnabled
                (
                  com.vidyo.portal.admin.v1_1.ScheduledRoomEnabledRequest scheduledRoomEnabledRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetMemberResponse getMember
                (
                  com.vidyo.portal.admin.v1_1.GetMemberRequest getMemberRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,MemberNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StartRecordingResponse startRecording
                (
                  com.vidyo.portal.admin.v1_1.StartRecordingRequest startRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ResourceNotAvailableFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getGroupRequest
             * @throws GroupNotFoundFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetGroupResponse getGroup
                (
                  com.vidyo.portal.admin.v1_1.GetGroupRequest getGroupRequest
                 )
            throws GroupNotFoundFaultException,InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param leaveConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.LeaveConferenceResponse leaveConference
                (
                  com.vidyo.portal.admin.v1_1.LeaveConferenceRequest leaveConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopLectureModeRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StopLectureModeResponse stopLectureMode
                (
                  com.vidyo.portal.admin.v1_1.StopLectureModeRequest stopLectureModeRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SetRoomProfileResponse setRoomProfile
                (
                  com.vidyo.portal.admin.v1_1.SetRoomProfileRequest setRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.MuteAudioResponse muteAudio
                (
                  com.vidyo.portal.admin.v1_1.MuteAudioRequest muteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getMembersRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetMembersResponse getMembers
                (
                  com.vidyo.portal.admin.v1_1.GetMembersRequest getMembersRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StopVideoResponse stopVideo
                (
                  com.vidyo.portal.admin.v1_1.StopVideoRequest stopVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DeleteMemberResponse deleteMember
                (
                  com.vidyo.portal.admin.v1_1.DeleteMemberRequest deleteMemberRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,MemberNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * For updating end point behavior at tenant level
                                    * @param updateEndpointBehaviorRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NoEndpointBehaviorExistsFaultException : 
             * @throws GeneralFaultException : 
             * @throws EndpointBehaviorDisabledFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorResponse updateEndpointBehavior
                (
                  com.vidyo.portal.admin.v1_1.UpdateEndpointBehaviorRequest updateEndpointBehaviorRequest
                 )
            throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,GeneralFaultException,EndpointBehaviorDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateRoomPINResponse createRoomPIN
                (
                  com.vidyo.portal.admin.v1_1.CreateRoomPINRequest createRoomPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getConferenceIDRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
             * @throws InPointToPointCallFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetConferenceIDResponse getConferenceID
                (
                  com.vidyo.portal.admin.v1_1.GetConferenceIDRequest getConferenceIDRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException,InPointToPointCallFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param pauseRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.PauseRecordingResponse pauseRecording
                (
                  com.vidyo.portal.admin.v1_1.PauseRecordingRequest pauseRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeWebcastURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveWebcastURLResponse removeWebcastURL
                (
                  com.vidyo.portal.admin.v1_1.RemoveWebcastURLRequest removeWebcastURLRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.UpdateMemberResponse updateMember
                (
                  com.vidyo.portal.admin.v1_1.UpdateMemberRequest updateMemberRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,MemberNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createWebcastPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateWebcastPINResponse createWebcastPIN
                (
                  com.vidyo.portal.admin.v1_1.CreateWebcastPINRequest createWebcastPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DeleteScheduledRoomResponse deleteScheduledRoom
                (
                  com.vidyo.portal.admin.v1_1.DeleteScheduledRoomRequest deleteScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setPresenterRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SetPresenterResponse setPresenter
                (
                  com.vidyo.portal.admin.v1_1.SetPresenterRequest setPresenterRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param enableRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.EnableRoomResponse enableRoom
                (
                  com.vidyo.portal.admin.v1_1.EnableRoomRequest enableRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setChatStateAdminRequest
             * @throws ChatNotAvailableInSuperFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SetChatStateAdminResponse setChatStateAdmin
                (
                  com.vidyo.portal.admin.v1_1.SetChatStateAdminRequest setChatStateAdminRequest
                 )
            throws ChatNotAvailableInSuperFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveRoomPINResponse removeRoomPIN
                (
                  com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest removeRoomPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addRoomRequest
             * @throws RoomAlreadyExistsFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws InvalidModeratorPINFormatFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.AddRoomResponse addRoom
                (
                  com.vidyo.portal.admin.v1_1.AddRoomRequest addRoomRequest
                 )
            throws RoomAlreadyExistsFaultException,InvalidArgumentFaultException,GeneralFaultException,InvalidModeratorPINFormatFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createModeratorPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws InvalidModeratorPINFormatFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateModeratorPINResponse createModeratorPIN
                (
                  com.vidyo.portal.admin.v1_1.CreateModeratorPINRequest createModeratorPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,InvalidModeratorPINFormatFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchRoomsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SearchRoomsResponse searchRooms
                (
                  com.vidyo.portal.admin.v1_1.SearchRoomsRequest searchRoomsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param cancelOutboundCallRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CancelOutboundCallResponse cancelOutboundCall
                (
                  com.vidyo.portal.admin.v1_1.CancelOutboundCallRequest cancelOutboundCallRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getGroupsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetGroupsResponse getGroups
                (
                  com.vidyo.portal.admin.v1_1.GetGroupsRequest getGroupsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateRoomRequest
             * @throws RoomAlreadyExistsFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws InvalidModeratorPINFormatFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.UpdateRoomResponse updateRoom
                (
                  com.vidyo.portal.admin.v1_1.UpdateRoomRequest updateRoomRequest
                 )
            throws RoomAlreadyExistsFaultException,InvalidArgumentFaultException,GeneralFaultException,InvalidModeratorPINFormatFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param disableScheduledRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DisableScheduledRoomResponse disableScheduledRoom
                (
                  com.vidyo.portal.admin.v1_1.DisableScheduledRoomRequest disableScheduledRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param dismissRaisedHandRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DismissRaisedHandResponse dismissRaisedHand
                (
                  com.vidyo.portal.admin.v1_1.DismissRaisedHandRequest dismissRaisedHandRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLocationTagsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetLocationTagsResponse getLocationTags
                (
                  com.vidyo.portal.admin.v1_1.GetLocationTagsRequest getLocationTagsRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * For adding new end point behavior at tenant level
                                    * @param createEndpointBehaviorRequest
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointBehaviorAlreadyExistsFaultException : 
             * @throws GeneralFaultException : 
             * @throws EndpointBehaviorDisabledFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorResponse createEndpointBehavior
                (
                  com.vidyo.portal.admin.v1_1.CreateEndpointBehaviorRequest createEndpointBehaviorRequest
                 )
            throws InvalidArgumentFaultException,EndpointBehaviorAlreadyExistsFaultException,GeneralFaultException,EndpointBehaviorDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeWebcastPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveWebcastPINResponse removeWebcastPIN
                (
                  com.vidyo.portal.admin.v1_1.RemoveWebcastPINRequest removeWebcastPINRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomProfileRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.RemoveRoomProfileResponse removeRoomProfile
                (
                  com.vidyo.portal.admin.v1_1.RemoveRoomProfileRequest removeRoomProfileRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param scheduledRoomIsEnabledRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledResponse scheduledRoomIsEnabled
                (
                  com.vidyo.portal.admin.v1_1.ScheduledRoomIsEnabledRequest scheduledRoomIsEnabledRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param resumeRecordingRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.ResumeRecordingResponse resumeRecording
                (
                  com.vidyo.portal.admin.v1_1.ResumeRecordingRequest resumeRecordingRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.StartVideoResponse startVideo
                (
                  com.vidyo.portal.admin.v1_1.StartVideoRequest startVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * To Fetch end point behavior at tenant level
                                    * @param getEndpointBehaviorRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NoEndpointBehaviorExistsFaultException : 
             * @throws GeneralFaultException : 
             * @throws EndpointBehaviorDisabledFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetEndpointBehaviorResponse getEndpointBehavior
                (
                  com.vidyo.portal.admin.v1_1.GetEndpointBehaviorRequest getEndpointBehaviorRequest
                 )
            throws InvalidArgumentFaultException,NoEndpointBehaviorExistsFaultException,GeneralFaultException,EndpointBehaviorDisabledFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.DeleteRoomResponse deleteRoom
                (
                  com.vidyo.portal.admin.v1_1.DeleteRoomRequest deleteRoomRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,RoomNotFoundFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomProfilesRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.GetRoomProfilesResponse getRoomProfiles
                (
                  com.vidyo.portal.admin.v1_1.GetRoomProfilesRequest getRoomProfilesRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param setLayoutRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.portal.admin.v1_1.SetLayoutResponse setLayout
                (
                  com.vidyo.portal.admin.v1_1.SetLayoutRequest setLayoutRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
        
         }
    