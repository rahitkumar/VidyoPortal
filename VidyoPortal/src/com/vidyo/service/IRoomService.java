package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.service.room.PublicRoomCreateRequest;
import com.vidyo.service.room.RoomAccessDetailsResponse;
import com.vidyo.service.room.RoomControlResponse;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.RoomUpdateResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.room.request.RoomUpdateRequest;

import java.util.List;

public interface IRoomService {
    public List<Room> getRooms(RoomFilter filter);
    public Long getCountRooms(RoomFilter filter);
    public Room getRoom(int roomID);
    public Room getRoom(String nameExt);
    public Room getRoom(String nameExt, String tenant);
    public Room getRoomByNameAndTenantName(String nameExt, String url);
    public Room getRoomForKey(String key);
    public Room getRoomForModeratorKey(String key);
    public int getRoomIdForExternalRoomId(String externalRoomId);

    public int updateRoom(int roomID, Room room);
    public int insertRoom(Room room);
    public int deleteRoom(int roomID);

    public int lockRoom(int roomID);
    public int unlockRoom(int roomID);

    public int muteRoom(int roomID);
    public int unmuteRoom(int roomID);
    public int silenceRoom(int roomID);
    public int unsilenceRoom(int roomID);

    public int muteRoomVideo(int roomID);
    public int unmuteRoomVideo(int roomID);
    public int silenceRoomVideo(int roomID);
    public int unsilenceRoomVideo(int roomID);

    public int resetRoomState(int roomID);

    public boolean isRoomExistForRoomName(String roomName, int roomID);
    public boolean isRoomExistForRoomExtNumber(String roomExtNumber, int roomID);
    public boolean isExtensionExistForLegacyExtNumber(String roomExtNumber, int roomID);
    public List<Long> getRoomsForOwnerID(int ownerID);
    public Room getPersonalRoomForOwnerID(int ownerID);
    public Room getPersonalOrLegacyRoomForOwnerID(int tenant, int ownerID);
    //public List<Browse> getBrowse(BrowseFilter filter, User user);
    //public Long getCountBrowse(BrowseFilter filter, User user);

    public int updateRoomPIN(Room room);
    public int generateRoomKey(Room room);
    public int removeRoomKey(Room room);

    public int generateModeratorKey(Room room);
    public int removeModeratorKey(Room room);

    public List<Entity> getEntity(EntityFilter filter, User user);
    public Long getCountEntity(EntityFilter filter, User user);
    public List<Entity> getEntityByEmail(EntityFilter filter, User user, String emailAddress);
    public Long getCountEntityByEmail(EntityFilter filter, User user, String emailAddress);
    public List<Entity> getEntityByOwnerID(EntityFilter filter, User user, int ownerID);
    public Long getCountEntityByOwnerID(EntityFilter filter, User user, int ownerID);

    public List<Entity> getEntity(EntityFilter filter, User user, boolean excludeRooms);
    public List<Entity> getRoomEntities(int ownerID);
    public Long getCountEntity(EntityFilter filter, User user, boolean excludeRooms);

    public String getTenantPrefix();
    public String getTenantDialIn();
    public boolean areGuestAllowedToThisRoom();

    public Entity getOneEntity(int entityID, User user);

    //public List<Integer> getEntityIDs(EntityFilter filter, User user);
    //public int getCountEntityIDs(EntityFilter filter, User user);
    //public List<Integer> getEntityIDs(EntityFilter filter, User user, boolean excludeRooms);
    //public int getCountEntityIDs(EntityFilter filter, User user, boolean excludeRooms);

    public List<RoomProfile> getRoomProfiles();
    public RoomProfile getRoomProfile(int roomID);
    public int updateRoomProfile(int roomID, String profileName);
    public int removeRoomProfile(int roomID);


    /**
     * Get the list of Entities for multiple RoomIds
     *
     * @param roomIDs
     * @param user
     * @param filter
     * @return
     */
    public List<Entity> getEntities(List<Integer> roomIDs, User user, EntityFilter filter);

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomID
     * @return
     */
    public Room getRoomDetailsForConference(int roomID);

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomExt
     * @param vidyoGatewayControllerDns
     * @return
     */
    public Room getRoomDetailsForConference(String roomExt, String vidyoGatewayControllerDns);

    /**
     * Returns the Room, Member, Tenant details needed for Webcast
     *
     * @param roomID
     * @return
     */
    public Room getRoomDetailsForWebcast(int roomID);

    public int updateRoomModeratorPIN(Room room);

    /**
     * Returns the Room based on TenantId and Room Name/Extension
     * @param nameExt
     * @param tenantId
     * @return
     */
    public Room getRoom(String nameExt, int tenantId);

    public String generateRoomExt(String tenantPrefix);

    /**
     * Deletes all the Scheduled Rooms
     *
     * @param prefix
     * @return
     */
    public int deleteScheduledRooms(String prefix);

    /**
     * Deletes Scheduled Room for the given roomKey
     *
     * @param roomKey
     * @return
     */
    public int deleteScheduledRoomByRoomKey(String roomKey);

    /**
     * Creates a Scheduled Room for the user and returns the Room details.
     *
     * @param tenantId
     *            Tenant to which the user belongs to
     * @param memberId
     *            User requesting creation of Scheduled Room
     * @param groupId
     *            Group to which the user belongs to
     * @param memberName
     *            Name of the User
     * @param recurring
     *            flag indicating the recurrence of the meeting
     * @return details of the room created wrapped in service response
     * @See {@link RoomCreationResponse}
     */
    public RoomCreationResponse createScheduledRoom(SchRoomCreationRequest schRoomCreationRequest);

    /**
     * Decrypts the scheduled room extension & pin and validates the room
     * existence
     *
     * @param extn
     * @param pin
     * @return
     */
    public ScheduledRoomResponse validateScheduledRoom(String extn, String pin, int tenantId);

    /**
     *
     * @param memberId
     * @param randNumb
     * @return
     */
    public ScheduledRoomResponse generateSchRoomExtPin(int memberId, int randNumb);

    public RoomControlResponse lockRoom(int loggedMemberID, int lockRoomID, String moderatorPIN);

    public RoomControlResponse unlockRoom(int loggedMemberID, int unlockRoomID, String moderatorPIN);

    /**
     * Updates the Room Extension numbers which belong to the Tenant.<br>
     * The method identifies the order in which the rows have to be updated.<br>
     * Handling of all possible update cases have to considered as database<br>
     * rollback support in currently not there.
     *
     * @param oldPrefix
     *            existing prefix of the Tenant
     * @param tenantId
     *            rooms belonging to the tenant
     * @param newPrefix
     *            new prefix value to be updated
     * @return response object containing status code and message
     */
    public RoomUpdateResponse updateRoomPrefix(String oldPrefix, int tenantId, String newPrefix);

    /**
     * Checks if the prefix is matching any of the other Tenant's rooms.<br>
     * This is just a wild card match and not an exact match. This check is done
     * specifically to prevent any DB corruption due to lack of transactional
     * DB.
     *
     * @param tenantPrefix
     * @param tenantId
     * @return
     */
    public int isPrefixExisting(String tenantPrefix, int tenantId);

    /**
     * Updates the GroupId to Default GroupId for Rooms.
     *
     * @param roomUpdateRequest
     * @return
     */
    public RoomUpdateResponse updateGroupForRooms(RoomUpdateRequest roomUpdateRequest);

    /**
     * Returns the Invitation content for the room. Also does room owner validation.
     *
     * @param memberId
     *            member requesting the invite content
     * @param roomId
     *            Room identifier for which invite is generated
     * @param tenantId
     *            tenant to which room and member belong
     *
     * @return inviteContentResponse response object holding the invite details
     */
    public RoomAccessDetailsResponse getRoomAccessOptions(int memberId, int roomId, int tenantId);

    /**
     * Deletes inactive scheduled rooms based on the inactivity limit.
     *
     * @param inactiveDays Days of Inactivity
     * @return count of deleted rooms
     */
    public int deleteInactiveSchRooms(int inactiveDays);

    /**
     * Updates the room's group for the Member based on room type
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupByMember(RoomUpdateRequest roomUpdateRequest);

    /**
     * Utility to override the scheduled room specific properties
     *
     * @param entity
     * @return
     */
    public Entity overrideScheduledRoomProperties(Entity entity);

    /**
     * Utility method to override scheduled room properties.
     *
     * @param room
     * @return
     */
    public Room overrideScheduledRoomProperties(Room room);

    /**
     * Returns the Accessible Room details for the user
     * @param roomId
     * @param canCallTenantIds
     * @return
     */
    public Room getAccessibleRoomDetails(int roomId, List<Integer> canCallTenantIds);

    /**
     * Returns RoomId, memberId, moderatorPin, roomType of the room.
     *
     * @param roomId
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsForDisconnectParticipant(int roomId, int tenantId);

    public int getRoomIdByExt(int tenantId, String roomExt, String pin);

    /**
     *
     * @param filter
     * @param user
     * @return
     */
    public List<RoomIdSearchResult> searchRoomIds(EntityFilter filter, User user);

    public int findRoomsCount(int thisUserMemberID, String query, String queryField, List<Integer> roomTypes);

    public List<RoomMini> findRooms(int thisUserMemberID, String query, String queryField, List<Integer> roomTypes, int start, int limit, String sortBy, String sortDir);

    public int getMemberIDForRoomIDIfAllowed(int roomID);

    /**
     * @param tenantId
     * @return
     */
    public int getUserCreatePublicRoomAllowed(int tenantId);

    /**
     * @param tenantId
     * @param ownerId
     * @return
     */
    public int getUserCreatePublicRoomCount(int tenantId, int ownerId);

    /**
     * @param tenantId
     * @return
     */
    public long getTenantCreatePublicRoomRemainCount(int tenantId);
    public long getPublicRoomCountForTenentID(int tenantId);


    public long getSystemCreatePublicRoomRemainCount();

    /**
     * @param displayName
     * @param tenant
     * @return
     */
    public int getDisplayNameCount(String displayName, int tenant);

    /**
     * @param displayName
     * @param roomId
     * @param tenant
     * @return
     */
    public int getDisplayNameCount(String displayName, int roomId, int tenant);

    /**
     * @param publicRoomCreateRequest
     * @return
     */
    public RoomCreationResponse createPublicRoom(PublicRoomCreateRequest publicRoomCreateRequest);

    public int deleteScheduledRoomsbyRecurring (int limit);

    /**
     * Checks if the prefix is matching any of the other Tenant's rooms.<br>
     * This is just a wild card match and not an exact match. This check is done
     * specifically to prevent any DB corruption due to lack of transactional
     * DB.
     *
     * @param tenantPrefix
     * @param tenantId
     * @return
     */
    public int isPrefixExistingInDefaultTenant(String tenantPrefix);

    /**
     * Get the Entity Details with LDAP attributes
     * @param entityID
     * @param user
     * @return
     */
    public Entity getOneEntityDetails(int entityID, User user);

    /**
     * Returns the Room, Member details needed for creating the Conference
     * by roomExtn (across all tenants) or roomName on the specific tenant
     *
     * @param roomNameExt Room Extension or Room Name
     * @param tenantId
     * @return a <code>Room</code> if one matches the search criteria
     */
    public Room getRoomDetailsForConference(String roomNameExt, int tenantId);

    /**
     * Returns the roomId, roomKey and moderatorKey by roomKey and tenantId
     * @param roomKey
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsByRoomKey(String roomKey, int tenantId);

    /**
     *
     * @param system
     * @param scheme
     * @param host
     * @param roomKey
     * @return
     */
    public String getRoomURL(ISystemService system, String scheme, String host, String roomKey);
    public boolean isPublicRoomIdExistsForMemberId(int roomId, int memberId, int tenantId);
    public int updatePublicRoomDescription(int roomID, String roomDescription);

    /**
     * Overloaded API which silences the speaker of the Room from Server side
     * which means Endpoints cannot unsilence the speaker.
     *
     * @param roomId
     * @param flag
     * @return
     */
    public int silenceSpeakerForRoomServer(int roomId, int flag);
    
    /**
     * Get the Room IDs with Room status and the number of participants in the conference
     * @param roomsToCheck
     * @return
     */
	public List<Room> getRoomStatusAndNumOfParticipants(List<Room> roomsToCheck);

	public int getRoomStatus(int roomId, int groupId);
	
	/**
	 * Delete all rooms owned by the Member.
	 * This API should be invoked by the delete member work flow.
	 * @param memberId
	 * @return
	 */
	public int deleteRoomsByMember(int memberId);

}