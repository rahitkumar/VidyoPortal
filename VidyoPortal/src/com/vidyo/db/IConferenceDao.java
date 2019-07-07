package com.vidyo.db;

import com.vidyo.bo.*;
import com.vidyo.bo.conference.Conference;
import com.vidyo.bo.gateway.GatewayPrefix;

import java.util.List;

public interface IConferenceDao {
	
	public static final String INSERT_INTO_CONF_QUERY = "INSERT INTO Conferences (conferenceName, conferenceType, roomID, GUID, endpointID, "
			+ "endpointType, endpointCaller, tenantID, audio, video, speaker, uniqueCallID, createdTime) "
			+ "VALUES(:conferenceName, :conferenceType, :roomID, :GUID, :endpointID, :endpointType, :endpointCaller, :tenantID, :audio, :video, :speaker, :uniqueCallID, :createdTime)";
	
	public static final String DELETE_FROM_CONF_RECORD = "DELETE FROM ConferenceRecord WHERE GUID = ?";
	
    public void pointCutOnUpdateStatus(String GUID, String status, int oldStatus, int newStatus, ConferenceInfo conferenceInfo, String endpointType);
    public void pointCutOnUpdateStatusForFederation(String GUID, String status, int old_status, int new_status, ConferenceInfo conferenceInfo);

    public List<Control> getControls(int roomID, ControlFilter filter);
    public List<Control> getFederationControls(int roomID, ControlFilter filter);
    public Control getControlForMember(int memberID, ControlFilter filter);
    public Long getCountControls(int roomID);
    /**
     * Returns true if exception while inserting Endpoint row
     * @param GUID
     * @param status
     * @param newSequenceNum
     * @param endpointType
     * @return boolean
     */
    public boolean updateEndpointStatus(String GUID, int status, long newSequenceNum, String endpointType);
    public boolean updateVirtualEndpointStatus(String GUID, int status, long newSequenceNum);
    public int getEndpointStatus(String GUID);
    public String getEndpointType(String GUID);
    public String getEndpointIPaddress(String GUID);

    public void moveEndpointToConference(String GUID, ConferenceInfo conferenceInfo, String participantId);
    public int addEndpointToConferenceRecord(String uniqueCallID, String conferenceName, String conferenceType, Room room, String GUID, String endpointType, String endpointCaller);
    public int addEndpointToConferenceRecordQueue(String uniqueCallID, String conferenceName, String conferenceType, Room room, String GUID, String endpointType, String endpointCaller);
    public int getTenantIDForGUID(String GUID, String endpointType);

    public Control removeEndpointFromConference(String GUID, String endpointType);
    public void removeConferenceRecord(String conferenceName);
    public int getEndpointIDForGUID(String GUID, String endpointType);
    public int updateVirtualEndpointInfo(String GUID, String displayName, String displayExt, int entityID);
    public String getGUIDForEndpointID(int endpointID, String endpointType);
    public boolean isEndpointIDinRoomID(int endpointID, int roomID);
    public String getGUIDForMemberID(int memberID);
    public GatewayPrefix getGatewayPrefixForCall(String toExt, int tenantID);
    public String getVirtualGUIDForRingingCall(int gwServiceId, String toExt,int tenantID, int roomID);
    public String getVirtualGUIDForLegacyDevice(Member member, Object arg, int tenantID);
    public boolean canTenantUseGatewayforAddress(List<Tenant> canCallToTenant, String address, int tenantID);

    public List<Control> getParticipants(int roomID, ControlFilter filter);
    public List<Entity> getParticipants(int roomID, EntityFilter filter, User user);
    public List<Entity> getLectureModeParticipants(int roomID, EntityFilter filter);
    public Long getCountParticipants(int roomID);
    public Long getCountParticipants(String conferenceName);
    public Conference getUniqueIDofConference(String conferenceName);

    public int getEndpointIDForMemberID(int memberID);
    public int getMemberIDForEndpointID(int endpointID);
    public String getMemberTypeForEndpointID(int endpointID);
    public int getRoomIDForEndpointID(int endpointID);
    public int getRoomIDForEndpointGUIDConferenceRecord(String endpointGUID);
    public ConferenceRecord getConferenceRecordForEndpointGUID(String endpointGUID);
    public String getConferenceTypeForEndpointID(int endpointID);
    public int getRoomIDForEndpointIDRoundRobin(int endpointID);
    public String getConferenceTypeForEndpointIDRoundRobin(int endpointID);

    public void addRouter(String routerName, String description);
    public void resetRouters();
    public VirtualEndpoint getVirtualEndpointForEndpointID(int endpointID);
    public RecorderEndpoint getRecorderEndpointForEndpointID(int endpointID);

    public void updateEndpointAudio(String GUID, int flag);
    public void updateEndpointAudioForAll(int roomID, int flag);
    public void updateEndpointVideo(String GUID, int flag);
    public int cleanGuestsAndEndpoints();

    //public boolean canTenantUseRecorderforPrefix(List<Tenant> canCallToTenant, String prefix);
    public RecorderEndpoint getRecorderGUIDForPrefix(String prefix, Member member, int webcast, Object arg);
    public int updateRecorderEndpointInfo(String GUID, int entityID, int webcast);

    public String getRoomNameForGUID(String guid);
    public String getRoomProfile(int profileID);
    public Control getConferenceNameFromConferenceRecord(String GUID, String endpointType);

    /**
     *
     * @param guid
     * @return
     */
    public String getConfNameByGuid(String guid);
    public String getUniqueCallIDByGuid(String guid);

    /**
     * Checks if the Member has joined the specified Room.
     * @param memberID
     * @param roomID
     * @return
     */
    public boolean isMemberPresentInRoom(int memberID, int roomID);

    /**
     * Cleans the Conferences table during link Endpoint
     * call.
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConference(String endpointGuid);

    /**
     * Cleans the ConferenceRecord table during link Endpoint
     * call.
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConferenceRecord(String endpointGuid);

    /**
     * Cleans the ConferenceRecord table by guid and confname.<br>
     * This is used when the two party conference call fails<br>
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConferenceRecord(String endpointGuid, String confName);

    public String getPresenterInRoom(int roomID);

    public String getParticipantIdForGUID(String GUID);

    public int getRoomIDByPresenterGuid(String guid);

    public boolean isHandRaised(String GUID);

    public void unlockRoomIfRoomOwnerJoined(String guid);

    public void lockOwnerRoomsIfRoomOwnerLeaving(String guid);
    
    public int getRoomIdForConference(int conferenceId);

    public List<String> getConferencesForTenant(int tenantId);

    public int clearStaleConferenceRecords();

    public int getRoomIDForWaitingRoomStop(String guid);

    public boolean isVideoStopped(String endpointGUID);

    /**
     * Returns the GUID for the EndpointId/Room Id combination
     * @param endpointID
     * @param roomID
     * @return
     */
    public String getGUIDForEndpointIdInConf(int endpointID, int roomID);

    /**
     * Updates the flag for Endpoint's speaker.
     * @param guid
     * @param flag
     * @return
     */
    public int updateEndpointSpeakerStatus(String guid, int flag);

    /**
     * Updates the speaker status for all Endpoints in the room.
     * @param roomId
     * @param flag
     * @return
     */
    public int updateEndpointSpeakerStatusAll(int roomId, int flag);
}