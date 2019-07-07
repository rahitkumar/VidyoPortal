package com.vidyo.db;

import com.vidyo.bo.*;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.service.room.request.RoomUpdateRequest;

import java.util.Date;
import java.util.List;

public interface IRoomDao {
    public List<Room> getRooms(int tenant, RoomFilter filter);

    public Long getCountRooms(int tenant, RoomFilter filter);

    public Room getRoom(int tenant, int roomID);

    public Room getRoom(int tenant, String nameExt);

    public Room getRoomByNameAndTenantId(int tenant, String name);

    public Room getRoomForKey(int tenant, String key);

    public Room getRoomForModeratorKey(int tenant, String key);
    
    public int getRoomIdForExternalRoomId(String externalRoomId);

    public int updateRoom(int tenant, int roomID, Room room);

    public int insertRoom(int tenant, Room room);

    public int deleteRoom(int tenant, int roomID);

    public int lockRoom(int tenant, int roomID);

    public int unlockRoom(int tenant, int roomID);

    public int muteRoom(int tenant, int roomID);

    public int unmuteRoom(int tenant, int roomID);

    public int silenceRoom(int tenant, int roomID);

    public int unsilenceRoom(int tenant, int roomID);

    public int muteRoomVideo(int tenant, int roomID);

    public int unmuteRoomVideo(int tenant, int roomID);

    public int silenceRoomVideo(int tenant, int roomID);

    public int unsilenceRoomVideo(int tenant, int roomID);

    public int resetRoomState(int roomID);

    public boolean isRoomExistForRoomName(int tenant, String roomName, int roomID);

    public boolean isRoomExistForRoomExtNumber(String roomExtNumber, int roomID);

    public boolean isExtensionExistForLegacyExtNumber(String roomExtNumber, int roomID);

    public List<Long> getRoomsForOwnerID(int tenant, int ownerID);

    public Room getPersonalRoomForOwnerID(int tenant, int ownerID);

    public Room getPersonalOrLegacyRoomForOwnerID(int tenant, int ownerID);

    //public List<Browse> getBrowse(int tenant, BrowseFilter filter, User user);

    //public Long getCountBrowse(int tenant, BrowseFilter filter, User user);

    public int updateRoomPIN(int tenant, Room room);

    public int updateRoomKey(int tenant, Room room);

    public int updateModeratorKey(int tenant, Room room);

    public List<Entity> getEntityByOwnerID(int tenant, EntityFilter filter, User user, int ownerID);

    public Long getCountEntityByOwnerID(int tenant, EntityFilter filter, User user, int ownerID);

    public List<Entity> getEntityByEmail(int tenant, EntityFilter filter, User user, String emailAddress);

    public Long getCountEntityByEmail(int tenant, EntityFilter filter, User user, String emailAddress);

    public List<Entity> getEntities(int tenant, EntityFilter filter, User user, boolean excludeRooms,
            String emailAddress, int ownerID);

    public Long getCountEntity(int tenant, EntityFilter filter, User user, boolean excludeRooms, String emailAddress,
            int ownerID);

    public Entity getOneEntity(int tenant, int entityID, User user);

    public List<Entity> getRoomEntitiesForOwnerID(int tenant, int ownerID);

    //public List<Integer> getEntityIDs(int tenant, EntityFilter filter, User user, boolean excludeRooms,
            //List<Integer> tenantIds, boolean showDisabledRooms);

    //public int getCountEntityIDs(int tenant, EntityFilter filter, User user, boolean excludeRooms,
            //List<Integer> tenantIds, boolean showDisabledRooms);

    public List<RoomProfile> getRoomProfiles();

    public RoomProfile getRoomProfile(int roomID);

    public int updateRoomProfile(int roomID, String profileName);

    public int removeRoomProfile(int roomID);

    /**
     * Get the list of Entities for the RoomIds
     *
     * @param roomIDs
     * @param tenant
     * @param user
     * @param filter
     * @return
     */
    public List<Entity> getEntities(List<Integer> roomIDs, int tenant, List<Integer> tenantIds, User user, EntityFilter filter);

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomID
     * @param tenantID
     * @return
     */
    public Room getRoomDetailsForConference(int roomID, int tenantID);

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
     * @param tenantID
     * @return
     */
    public Room getRoomDetailsForWebcast(int roomID, int tenantID);

    public int updateRoomModeratorPIN(int tenant, Room room);

    public int getBiggestRoomExtNumber(int tenant);

    /**
     * Deletes all the Scheduled Rooms
     *
     * @param prefix
     * @return
     */
    public int deleteScheduledRooms(String prefix);

    /**
     * Deletes the Scheduled Room by roomKey
     *
     * @param roomKey
     * @return
     */
    public int deleteScheduledRoomByRoomKey(String roomKey);

    /**
     * Updates the Room's last accessed time.
     *
     * @param roomId
     * @param lastAccessedTime
     * @return
     */
    public int updateLastAccessedTime(int roomId, Date lastAccessedTime);

    /**
     * Returns the room count by room ext number
     *
     * @param roomExtNumber
     * @return
     */
    public boolean isRoomExistForRoomExtNumber(String roomExtNumber);

    /**
     * Returns the count of rooms matching the prefix belonging to other
     * tenants.
     *
     * @param tenantId
     *            current tenant
     * @param prefix
     *            tenant prefix to be matched
     * @return count of matching rooms
     */
    public int getRoomsCountMatchingPrefix(int tenantId, String prefix);

    /**
     * Updates the Room Extension numbers which belong to the Tenant.<br>
     * The method identifies the order in which the rows have to be updated.<br>
     * Handling of all possible update cases have to considered as database<br>
     * rollback support in currently not there.
     *
     * @param newPrefix
     * @param tenantId
     * @param oldPrefix
     * @return
     */
    public int updateRoomExtensionPrefix(String newPrefix, int tenantId, String oldPrefix);

    /**
     * Updates the GroupId to Default GroupId for Rooms.
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupForRooms(RoomUpdateRequest roomUpdateRequest);

    /**
     * Deletes inactive scheduled rooms based on the inactivity limit.
     *
     * @param inactiveDays Days of Inactivity
     * @return count of deleted rooms
     */
    public int deleteInactiveSchRooms(int inactiveDays);

    /**
     * Updates the Room's group based on the member and room type.
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupByMember(RoomUpdateRequest roomUpdateRequest);

    /**
     * Returns the Accessible Room details for the user
     * @param roomId
     * @param canCallTenantIds
     * @return
     */
    public Room getAccessibleRoomDetails(int roomId, List<Integer> canCallTenantIds);

    /**
     * Returns RoomId, memberId, moderatorPin, roomType of the room.
     * @param roomId
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsForDisconnectParticipant(int roomId, int tenantId);

    public int getRoomIdByExt(int tenantId, String roomExt);

    public void setTenantRoomsLectureModeState(int tenantId, boolean flag);

    /**
     *
     * @param tenant
     * @param filter
     * @param user
     * @param tenantIds
     * @param showDisabledRooms
     * @return
     */
    public List<RoomIdSearchResult> searchRoomIds(int tenant,
            EntityFilter filter, User user, List<Integer> tenantIds,
            boolean showDisabledRooms);

    public int findRoomsCount(int thisUserMemberID, String query, String queryField, List<Integer> allowedTenantIds, List<Integer> roomTypes, boolean showDisabledRooms);

    public List<RoomMini> findRooms(int thisUserMemberID, String query, String queryField, List<Integer> allowedTenantIds, List<Integer> roomTypes, int start, int limit, String sortBy, String sortDir, boolean showDisabledRooms);

    /**
     *
     * @param roomID
     * @param allowedTenantIds
     * @return
     */
    public int getMemberIDForRoomID(int roomID, List<Integer> allowedTenantIds);

    /**
     *
     * @param tenant
     * @param ownerID
     * @return
     */
    public int getPublicRoomCountForOwnerID(int tenant, int ownerID);

    public int getPublicRoomCountForTenentID(int tenant);

    public long getPublicRoomCount();

    public int getDisplayNameCount(String displayName, int tenant);

    /**
     * @param displayName
     * @param roomId
     * @param tenant
     * @return
     */
    public int getDisplayNameCount(String displayName, int roomId, int tenant);

    public int deleteScheduledRoomsbyRecurring (int limit);

    public int updateDeleteOnScheduledRoom(int roomId);

    public int getDefaultTenantRoomsCountMatchingPrefix(String prefix);

    /**
     * Get the Entity Details with LDAP attributes
     * @param tenant
     * @param entityID
     * @param user
     * @return
     */
    public Entity getOneEntityDetails(int tenant, int entityID, User user);

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomNameExt
     * @param tenantID
     * @return
     */
    public Room getRoomDetailsForConference(String roomNameExt, int tenantId);

    /**
     * Returns the room details by roomKey and tenantId
     * @param roomKey
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsByRoomKey(String roomKey, int tenantId);

    public boolean isPublicRoomIdExistsForMemberId(int roomId, int memberId, int tenantId);

    public int updatePublicRoomDescription(int roomID, String roomDescription);

    /**
     * Overloaded API which silences the speaker device of the Room from Server side
     * which means Endpoints cannot unsilence their speaker.
     *
     * @param roomId
     * @param flag
     * @return
     */
    public int silenceSpeakerForRoomServer(int roomId, int flag);
    
    /**
     * Find the Room IDs with Room status and the number of participants in the conference
     * @param roomIds
     * @return
     */
    public List<Room> findRoomStatusAndNumOfParticipants(List<Integer> roomIds);

    public int getRoomStatus(int roomId, int groupId);

	public static final String INACCESSIBLE_ROOMS_FOR_USER = "SELECT DISTINCT(rug.room_id) FROM room_user_group rug "
			+ "LEFT JOIN member_user_group mug on rug.user_group_id=mug.user_group_id "
			+ "WHERE (mug.user_group_id is null OR mug.user_group_id <> :userId) "
			+ "AND rug.room_id not in (select DISTINCT(rug1.room_id) FROM room_user_group rug1 "
			+ "INNER JOIN member_user_group mug1 on rug1.user_group_id=mug1.user_group_id "
			+ "WHERE mug1.member_id=:userId)";
	
	/**
	 * Query to delete all rooms owned by a member
	 */
	public static final String DELETE_ALL_ROOMS_FOR_MEMBER = "DELETE FROM Room WHERE memberId =:memberId";
	
	/**
	 * Deletes all rooms owned by the Member
	 * @param memberId
	 * @return
	 */
	public int deleteRoomsByMember(int memberId);


	public int getRoomCountByExtnLength(int tenantId, int tenantPrefix);

  public void clearRoomCounterCache(int tenantId);
}