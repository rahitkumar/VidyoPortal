package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.service.exceptions.*;

import javax.activation.DataHandler;

import java.util.List;

public interface IConferenceService {
    public String getVMConnectAddress() throws NoVidyoManagerException;
    public void joinTheConference(Room roomToJoin, String virtualEndpointGUID, String displayName) throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException;
    public void leaveTheConference(int endpointID, int roomID, CallCompletionCode callCompletionCode) throws LeaveConferenceException;
    public void leaveTheConference(String endpointGUID, int roomID, CallCompletionCode callCompletionCode) throws LeaveConferenceException;
    public String inviteToConference(Invite invite) throws OutOfPortsException, EndpointNotExistException, InviteConferenceException, EndpointNotSupportedException;
    public String cancelInviteToConference(Invite invite) throws EndpointNotExistException, CancelInvitationException;
    //public void startRing(int fromMemberID, int toRoomID) throws StartRingException;
    //public void stopRing(int fromMemberID, int toRoomID) throws StopRingException;
    public void twoPartyConference(int fromMemberID, int toRoomID) throws EndpointNotExistException, MakeCallException, OutOfPortsException;
    public void twoPartyConference(String virtualEndpointGUID, String nameExt, String tenant, String displayName) throws MakeCallException, OutOfPortsException;
    public void twoPartyConference(Invite invite) throws EndpointNotExistException, MakeCallException, OutOfPortsException;
    public void recordTheConference(int memberID, int roomID, String recorderPrefix, int webcast) throws OutOfPortsException, JoinConferenceException;
    public void disconnectAll(Room roomToDisconnect)throws DisconnectAllException;

    public List<Control> getControl(int roomID, ControlFilter filter);
    public Control getControlForMember(int memberID, ControlFilter filter);
    public Long getCountControl(int roomID);

    public void updateEndpointAudio(String GUID, boolean mute);
    public void updateEndpointAudioForAll(int roomID, boolean mute);

    public void muteAudio(String endpointGUID) throws Exception;
    public void unmuteAudio(String endpointGUID) throws Exception;
    public void stopVideo(String endpointGUID) throws Exception;
    public void startVideo(String endpointGUID) throws Exception;
    public void silenceAudio(String endpointGUID) throws Exception;
    public void silenceVideo(String endpointGUID) throws Exception;

    /**
     * Updates Endpoint Status in the Portal based on the notification from
     * VidyoManager. Important: There are AOP interceptors working on this
     * method flow. Please refer to the spring interceptors xml before changing
     * any business logic in this method
     *
     * @param GUID
     * @param status
     * @param source
     * @param conferenceInfo
     * @param newSequenceNum
     * @param updateReason
     * @param participantId
     */
    public void updateEndpointStatus(String GUID, String status, String source, ConferenceInfo conferenceInfo, long newSequenceNum, String updateReason, String participantId);

    public int getEndpointStatus(int endpointID);
    public int getEndpointStatus(String GUID);
    public String getEndpointIPaddress(String GUID);

    public List<Control> getParticipants(int roomID, ControlFilter filter);
    public List<Entity> getParticipants(int roomID, EntityFilter filter, User user);
    public List<Entity> getLectureModeParticipants(int roomID, EntityFilter filter);
    public Long getCountParticipants(int roomID);
    public int getEndpointIDForMemberID(int memberID);
    public int getMemberIDForEndpointID(int endpointID);
    public String getMemberTypeForEndpointID(int endpointID);

    public int getEndpointIDForGUID(String GUID, String endpointType);
    public String getGUIDForEndpointID(int endpointID, String endpointType);
    public int getRoomIDForEndpointID(int endpointID);
    public boolean isEndpointIDinRoomID(int endpointID, int roomID);

    /**
     * Sends Challenge to the Endpoint through VidyoManager. IMPORTANT: There
     * are AOP interceptors working on this method flow. Please refer to the
     * spring interceptors xml before changing any business logic in this method.
     *
     * @param memberID
     * @param GUID
     */
    //public void bindUserToEndpoint(int memberID, String GUID);

    /**
     * Sends Challenge to the Endpoint through VidyoManager. IMPORTANT: There
     * are AOP interceptors working on this method flow. Please refer to the
     * spring interceptors xml before changing any business logic in this method.
     *
     * @param guestID
     * @param GUID
     */
    public void bindGuestToEndpoint(int guestID, String GUID);
    public void unbindUserFromEndpoint(String GUID, UserUnbindCode reasonCode);
    public void sendMessageToEndpoint(String endpointGUID, DataHandler content) throws Exception;
    public void getAffinities();
    public VirtualEndpoint getVirtualEndpointForEndpointID(int endpointID);
    public RecorderEndpoint getRecorderEndpointForEndpointID(int endpointID);

    public void checkBindUserChallengeResponse(String endpointGUID, String challengeResponse, long bindUserResponseID);

    public String getRoomNameForGUID(String guid);

    public void disconnectMeFromConference(int memberID)
            throws LeaveConferenceException;

    /*public String inviteToConference(int fromMemberID, int toMemberID,
            int toEndpointID) throws OutOfPortsException,
            EndpointNotExistException, InviteConferenceException;*/

    /**
     * Pauses Recording of the Conference. Sends the Vcap message to the
     * recorder.
     *
     * @param GUID
     * @return
     * @throws Exception
     */
    public void pauseRecording(String GUID) throws Exception;

    /**
     * Resumes the recording of the Conference. Sends the Vcap message to the
     * recorder.
     *
     * @param GUID
     * @return
     * @throws Exception
     */
    public void resumeRecording(String GUID) throws Exception;

    /**
     *
     * @param GUID
     * @return
     * @throws LeaveConferenceException
     */
    public boolean leaveTheConference(String GUID, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException;

    /**
     * Checks if the Member has joined the specified Room.
     *
     * @param memberID
     * @param roomID
     * @return
     */
    public boolean isMemberPresentInRoom(int memberID, int roomID);

    /**
     * Creates the Conference and invites the Member to the Conference.
     *
     * @param roomToJoin
     * @param fromMemberId
     * @param inviteeEntity
     * @return
     * @throws OutOfPortsException
     * @throws EndpointNotExistException
     * @throws InviteConferenceException
     */
    public String inviteParticipantToConference(Room roomToJoin,
            Member inviter, Member invitee, String toEndpointType) throws OutOfPortsException,
            EndpointNotExistException, InviteConferenceException, EndpointNotSupportedException;

    /**
     * Creates the Conference and adds the Member to the Conference
     *
     * @param roomToJoin
     * @param joiningMember
     * @throws OutOfPortsException
     * @throws JoinConferenceException
     */
    public void joinTheConference(Room roomToJoin, Member joiningMember)
            throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException;

    /**
     *
     * @param roomToJoin
     * @param inviter
     * @param invitee
     * @return
     * @throws InviteConferenceException
     */
    public String inviteLegacyToConference(Room roomToJoin, Member inviter,
            Member invitee) throws InviteConferenceException,
            EndpointNotExistException, OutOfPortsException;

    /**
     * Returns the count of the records in Conferences, ConferenceRecord,
     * ExternalLinks table based on Conference Name
     *
     * @param conferenceName
     * @return
     */
    public Long getCountParticipants(String conferenceName);

    /**
     * Deletes Guest Endpoints after the call is complete
     * @return
     */
    public int cleanGuestsAndEndpoints();

    /**
     * Cleans Conference and Conference Record for the user while logging in.
     *
     * @param endpointGuid
     * @return
     */
    public void cleanConferenceForLinkedUser(String endpointGuid);

    /**
     *
     * @param endpointID
     * @param roomToDisconnect
     * @param callCompletionCode
     */
    public void disconnectEndpointFromConference(int endpointID,
            Room roomToDisconnect, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException;

    public void disconnectEndpointFromConference(String endpointGUID,
                                                 Room roomToDisconnect, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException;

    public int addEndpointUnsupportedToTransactionHistory(Room room, String endpointGUID, String feature);

    public void disconnectAllConferencesForTenant(int tenantId) throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException;

    public int clearStaleConferenceRecords();

    public void startVideoIfStopped(String newPresenterGUID) throws Exception;

    /**
     * Returns the GUID for the EndpointId/Room Id combination
     * @param endpointID
     * @param roomID
     * @return
     */
    public String getGUIDForEndpointIdInConf(int endpointID, int roomID);

    /**
     * Sends VCAP message for silencing endpoint's speaker.
     *
     * @param guid
     * @throws Exception
     */
    public void silenceSpeaker(List<String> guids, int flag, int roomId, boolean allEndpoints);
    
    /**
     * Utility method to create the conference name based on the Room Type
     * @TODO Cannot make it static utility without further re-factoring - 09/16/2018
     * @param room
     * @param tenantUrl
     * @return
     */
    public String generateConferenceName(Room room, String tenantUrl);
}
