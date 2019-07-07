
/**
 * VidyoPortalAdminServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
    package com.vidyo.portal.admin;
    /**
     *  VidyoPortalAdminServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalAdminServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param getLicenseDataRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetLicenseDataResponse getLicenseData
                (
                  com.vidyo.portal.admin.GetLicenseDataRequest getLicenseDataRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.RemoveRoomPINResponse removeRoomPIN
                (
                  com.vidyo.portal.admin.RemoveRoomPINRequest removeRoomPINRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetRoomsResponse getRooms
                (
                  com.vidyo.portal.admin.GetRoomsRequest getRoomsRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateGroupRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws GroupNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.UpdateGroupResponse updateGroup
                (
                  com.vidyo.portal.admin.UpdateGroupRequest updateGroupRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,GroupNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.GetRoomResponse getRoom
                (
                  com.vidyo.portal.admin.GetRoomRequest getRoomRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,RoomNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.DeleteRoomResponse deleteRoom
                (
                  com.vidyo.portal.admin.DeleteRoomRequest deleteRoomRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,RoomNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param muteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.MuteAudioResponse muteAudio
                (
                  com.vidyo.portal.admin.MuteAudioRequest muteAudioRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getParticipantsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetParticipantsResponse getParticipants
                (
                  com.vidyo.portal.admin.GetParticipantsRequest getParticipantsRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param unmuteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.UnmuteAudioResponse unmuteAudio
                (
                  com.vidyo.portal.admin.UnmuteAudioRequest unmuteAudioRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getGroupsRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetGroupsResponse getGroups
                (
                  com.vidyo.portal.admin.GetGroupsRequest getGroupsRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws MemberAlreadyExistsExceptionException : 
         */

        
                public com.vidyo.portal.admin.AddMemberResponse addMember
                (
                  com.vidyo.portal.admin.AddMemberRequest addMemberRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,MemberAlreadyExistsExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param startVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.StartVideoResponse startVideo
                (
                  com.vidyo.portal.admin.StartVideoRequest startVideoRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomPINRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.CreateRoomPINResponse createRoomPIN
                (
                  com.vidyo.portal.admin.CreateRoomPINRequest createRoomPINRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteGroupRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws GroupNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.DeleteGroupResponse deleteGroup
                (
                  com.vidyo.portal.admin.DeleteGroupRequest deleteGroupRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,GroupNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param removeRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.RemoveRoomURLResponse removeRoomURL
                (
                  com.vidyo.portal.admin.RemoveRoomURLRequest removeRoomURLRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws RoomAlreadyExistsExceptionException : 
         */

        
                public com.vidyo.portal.admin.AddRoomResponse addRoom
                (
                  com.vidyo.portal.admin.AddRoomRequest addRoomRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,RoomAlreadyExistsExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.UpdateMemberResponse updateMember
                (
                  com.vidyo.portal.admin.UpdateMemberRequest updateMemberRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,MemberNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deleteMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.DeleteMemberResponse deleteMember
                (
                  com.vidyo.portal.admin.DeleteMemberRequest deleteMemberRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,MemberNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param inviteToConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.InviteToConferenceResponse inviteToConference
                (
                  com.vidyo.portal.admin.InviteToConferenceRequest inviteToConferenceRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getMembersRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetMembersResponse getMembers
                (
                  com.vidyo.portal.admin.GetMembersRequest getMembersRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param addGroupRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws GroupAlreadyExistsExceptionException : 
         */

        
                public com.vidyo.portal.admin.AddGroupResponse addGroup
                (
                  com.vidyo.portal.admin.AddGroupRequest addGroupRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,GroupAlreadyExistsExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getGroupRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws GroupNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.GetGroupResponse getGroup
                (
                  com.vidyo.portal.admin.GetGroupRequest getGroupRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,GroupNotFoundExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getPortalVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.GetPortalVersionResponse getPortalVersion
                (
                  com.vidyo.portal.admin.GetPortalVersionRequest getPortalVersionRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateRoomRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws RoomNotFoundExceptionException : 
             * @throws RoomAlreadyExistsExceptionException : 
         */

        
                public com.vidyo.portal.admin.UpdateRoomResponse updateRoom
                (
                  com.vidyo.portal.admin.UpdateRoomRequest updateRoomRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,RoomNotFoundExceptionException,RoomAlreadyExistsExceptionException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createRoomURLRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.CreateRoomURLResponse createRoomURL
                (
                  com.vidyo.portal.admin.CreateRoomURLRequest createRoomURLRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param stopVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.StopVideoResponse stopVideo
                (
                  com.vidyo.portal.admin.StopVideoRequest stopVideoRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param leaveConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.admin.LeaveConferenceResponse leaveConference
                (
                  com.vidyo.portal.admin.LeaveConferenceRequest leaveConferenceRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getMemberRequest
             * @throws InvalidArgumentFaultException : 
             * @throws NotLicensedExceptionException : 
             * @throws GeneralFaultException : 
             * @throws MemberNotFoundExceptionException : 
         */

        
                public com.vidyo.portal.admin.GetMemberResponse getMember
                (
                  com.vidyo.portal.admin.GetMemberRequest getMemberRequest
                 )
            throws InvalidArgumentFaultException,NotLicensedExceptionException,GeneralFaultException,MemberNotFoundExceptionException;
        
         }
    