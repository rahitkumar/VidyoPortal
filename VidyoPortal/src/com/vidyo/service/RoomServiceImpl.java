package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.db.IRoomDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.room.PublicRoomCreateRequest;
import com.vidyo.service.room.RoomAccessDetailsResponse;
import com.vidyo.service.room.RoomControlResponse;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.RoomUpdateResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.room.request.RoomUpdateRequest;
import com.vidyo.service.statusnotify.StatusNotificationService;
import com.vidyo.service.system.SystemService;
import com.vidyo.service.utils.UtilService;
import com.vidyo.utils.Generator;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.utils.VendorUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomServiceImpl implements IRoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    public static final String SCHEDULED_ROOM_PREFIX = "SCHEDULED_ROOM_PREFIX";

    private IRoomDao dao;
    private ITenantService tenantService;

    private Generator generator;

    /**
     *
     */
    private SystemService systemService;

    private ISystemService systemServiceConfig;

    private UtilService utilService;

    private MemberService memberService;

    @Autowired
    private StatusNotificationService statusNotificationService;

    /**
     * @param memberService the memberService to set
     */
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * @param systemService the systemService to set
     */
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public ISystemService getSystemServiceConfig() {
        return systemServiceConfig;
    }

    public void setSystemServiceConfig(ISystemService systemServiceConfig) {
        this.systemServiceConfig = systemServiceConfig;
    }

    /**
     * @param generator the generator to set
     */
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public void setDao(IRoomDao dao) {
        this.dao = dao;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }

    public List<Room> getRooms(RoomFilter filter) {
        List<Room> list = this.dao.getRooms(TenantContext.getTenantId(), filter);
        return list;
    }

    public Long getCountRooms(RoomFilter filter) {
        Long number = this.dao.getCountRooms(TenantContext.getTenantId(), filter);
        return number;
    }

    public Room getRoom(int roomID) {
    	logger.info("TenantContext ->" + TenantContext.getTenantId());
        Room room = this.dao.getRoom(TenantContext.getTenantId(), roomID);
        return room;
    }

    public Room getRoom(String nameExt) {
        Room room = this.dao.getRoom(TenantContext.getTenantId(), nameExt);
        return room;
    }

    public Room getRoom(String nameExt, String tenant) {
        Room room = null;

        if(tenant == null || tenant.isEmpty()) {
            room = this.dao.getRoom(1, nameExt);
        } else {
            Tenant t = this.tenantService.getTenant(tenant);
            room = this.dao.getRoom(t.getTenantID(), nameExt);
        }
        return room;
    }

    public Room getRoomByNameAndTenantName(String nameExt, String tenantName) {
        Tenant t = this.tenantService.getTenant(tenantName);
        Room room = null;
        try {
            room = this.dao.getRoomByNameAndTenantId(t.getTenantID(), nameExt);
        } catch (Exception e) {
            logger.error("Room not found for room name or ext - {} , TenantId - {}", nameExt, t.getTenantID());
        }
        return room;
    }

    public Room getRoomForKey(String key) {
        Room room = this.dao.getRoomForKey(TenantContext.getTenantId(), key);
        return room;
    }

    public Room getRoomForModeratorKey(String key) {
        Room room = this.dao.getRoomForModeratorKey(TenantContext.getTenantId(), key);
        return room;
    }
    
    public int getRoomIdForExternalRoomId(String externalRoomId) {
    	int rc = this.dao.getRoomIdForExternalRoomId(externalRoomId);
        return rc;
    }

    public int updateRoom(int roomID, Room room) {
        int rc = this.dao.updateRoom(TenantContext.getTenantId(), roomID, room);
        room.setRoomID(roomID);
        sendStatusNotification(room);
        return rc;
    }

    @Override
    public int updatePublicRoomDescription(int roomID, String roomDescription) {
        int rc = this.dao.updatePublicRoomDescription(roomID, roomDescription);
        return rc;
    }

    public int insertRoom(Room room) {
        // Check if the Member belongs to the same Tenant
        Member member = memberService.getMember(TenantContext.getTenantId(), room.getMemberID());
        if(member == null) {
            return 0;
        }
        room.setRoomKey(SecureRandomUtils.generateRoomKey(getRoomKeyLength()));

        // set room attributes based on tenant settings
        TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(TenantContext.getTenantId());
        room.setRoomMuted(tenantConfiguration.getWaitingRoomsEnabled());
        room.setLectureMode(tenantConfiguration.getWaitingRoomsEnabled());

        int rc = this.dao.insertRoom(TenantContext.getTenantId(), room);
        // Send notification
        if(rc > 0) {
        	room.setRoomID(rc);
        	sendStatusNotification(room);
        }
        return rc;
    }

    protected void sendStatusNotification(Room room) {
    	NotificationInfo notificationInfo = new NotificationInfo();
    	notificationInfo.setRoomNotification(room);
    	statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
    }

    @Transactional
    public int deleteRoom(int roomID) {
        int rc = this.dao.deleteRoom(TenantContext.getTenantId(), roomID);
        Room deletedRoom = new Room();
        deletedRoom.setRoomID(roomID);
        sendStatusNotification(deletedRoom);
        return rc;
    }

    public int lockRoom(int roomID) {
        int rc = this.dao.lockRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int unlockRoom(int roomID) {
        int rc = this.dao.unlockRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int muteRoom(int roomID) {
        int rc = this.dao.muteRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int unmuteRoom(int roomID) {
        int rc = this.dao.unmuteRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int silenceRoom(int roomID) {
        int rc = this.dao.silenceRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int unsilenceRoom(int roomID) {
        int rc = this.dao.unsilenceRoom(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int muteRoomVideo(int roomID) {
        int rc = this.dao.muteRoomVideo(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int unmuteRoomVideo(int roomID) {
        int rc = this.dao.unmuteRoomVideo(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int silenceRoomVideo(int roomID) {
        int rc = this.dao.silenceRoomVideo(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int unsilenceRoomVideo(int roomID) {
        int rc = this.dao.unsilenceRoomVideo(TenantContext.getTenantId(), roomID);
        return rc;
    }

    public int resetRoomState(int roomID) {
        int rc = this.dao.resetRoomState(roomID);
        return rc;
    }

    public boolean isRoomExistForRoomName(String roomName, int roomID) {
        boolean rc = this.dao.isRoomExistForRoomName(TenantContext.getTenantId(), roomName, roomID);
        return rc;
    }

    public boolean isRoomExistForRoomExtNumber(String roomExtNumber, int roomID) {
        boolean rc = this.dao.isRoomExistForRoomExtNumber(roomExtNumber, roomID);
        return rc;
    }

    public boolean isExtensionExistForLegacyExtNumber(String roomExtNumber, int roomID) {
        boolean rc = this.dao.isExtensionExistForLegacyExtNumber(roomExtNumber, roomID);
        return rc;
    }
    
    public List<Long> getRoomsForOwnerID(int ownerID) {
        List<Long> rc = this.dao.getRoomsForOwnerID(TenantContext.getTenantId(), ownerID);
        return rc;
    }

    public Room getPersonalRoomForOwnerID(int ownerID) {
        Room rc = this.dao.getPersonalRoomForOwnerID(TenantContext.getTenantId(), ownerID);
        return rc;
    }

    public Room getPersonalOrLegacyRoomForOwnerID(int tenant, int ownerID) {
        Room rc = null;
        try {
            rc = this.dao.getPersonalOrLegacyRoomForOwnerID(tenant, ownerID);
        } catch (Exception e) {
            logger.error("Personal or Legacy Room for tenant = " + tenant + " ownerID = " + ownerID + " is not found");
        }

        return rc;
    }

    /*public List<Browse> getBrowse(BrowseFilter filter, User user) {
        List<Browse> list = this.dao.getBrowse(TenantContext.getTenantId(), filter, user);
        return list;
    }

    public Long getCountBrowse(BrowseFilter filter, User user) {
        Long number = this.dao.getCountBrowse(TenantContext.getTenantId(), filter, user);
        return number;
    }*/

    public int updateRoomPIN(Room room) {
        int rc = this.dao.updateRoomPIN(TenantContext.getTenantId(), room);
        return rc;
    }

    public int generateRoomKey(Room room) {
        room.setRoomKey(SecureRandomUtils.generateRoomKey(getRoomKeyLength()));
        int rc = this.dao.updateRoomKey(TenantContext.getTenantId(), room);
        return rc;
    }

    public int removeRoomKey(Room room) {
        room.setRoomKey(null);
        int rc = this.dao.updateRoomKey(TenantContext.getTenantId(), room);
        return rc;
    }

    public int generateModeratorKey(Room room) {
        room.setRoomModeratorKey(SecureRandomUtils.generateRoomKey(getRoomKeyLength())); // java secure random implementation
        int rc = this.dao.updateModeratorKey(TenantContext.getTenantId(), room);
        return rc;
    }

    public int removeModeratorKey(Room room) {
        room.setRoomModeratorKey(null);
        int rc = this.dao.updateModeratorKey(TenantContext.getTenantId(), room);
        return rc;
    }

    public List<Entity> getEntity(EntityFilter filter, User user) {
        return getEntity(filter, user, false);
    }

    public Long getCountEntity(EntityFilter filter, User user) {
        return getCountEntity(filter, user, false);
    }

    public List<Entity> getEntityByOwnerID(EntityFilter filter, User user, int ownerID) {
        List<Entity> list = this.dao.getEntityByOwnerID(TenantContext.getTenantId(), filter, user, ownerID);
        return list;
    }

    public Long getCountEntityByOwnerID(EntityFilter filter, User user, int ownerID) {
        Long number = this.dao.getCountEntityByOwnerID(TenantContext.getTenantId(), filter, user, ownerID);
        return number;
    }

    public List<Entity> getEntityByEmail(EntityFilter filter, User user, String emailAddress) {
        List<Entity> list = this.dao.getEntityByEmail(TenantContext.getTenantId(), filter, user, emailAddress);
        return list;
    }

    public Long getCountEntityByEmail(EntityFilter filter, User user, String emailAddress) {
        Long number = this.dao.getCountEntityByEmail(TenantContext.getTenantId(), filter, user, emailAddress);
        return number;
    }

    public List<Entity> getEntity(EntityFilter filter, User user, boolean excludeRooms) {
        List<Entity> list = this.dao.getEntities(TenantContext.getTenantId(), filter, user, excludeRooms, null, 0);
        return list;
    }

    public Long getCountEntity(EntityFilter filter, User user, boolean excludeRooms) {
        Long number = this.dao.getCountEntity(TenantContext.getTenantId(), filter, user, excludeRooms, null, 0);
        return number;
    }

    public List<Entity> getRoomEntities(int ownerID) {
        List<Entity> list = this.dao.getRoomEntitiesForOwnerID(TenantContext.getTenantId(), ownerID);
        for(Entity entity : list) {
            if(entity.getRoomType().equals("Scheduled")) {
                entity = overrideScheduledRoomProperties(entity);
            }
        }
        return list;
    }

    public String getTenantPrefix() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getTenantPrefix();
    }

    public String getTenantDialIn() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getTenantDialIn();
    }

    public boolean areGuestAllowedToThisRoom() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return 0 != rc.getGuestLogin();
    }

    public Entity getOneEntity(int entityID, User user) {
        Entity entity = this.dao.getOneEntity(TenantContext.getTenantId(), entityID, user);
        if (entity.getRoomType().equalsIgnoreCase("Scheduled")) {
            // Override the room name/extn/displayname with ScheduledRoom extension
            entity = overrideScheduledRoomProperties(entity);
        }
        return entity;
    }

    /*public List<Integer> getEntityIDs(EntityFilter filter, User user) {
        return getEntityIDs(filter, user, false);
    }*/

    /*public int getCountEntityIDs(EntityFilter filter, User user) {
        return getCountEntityIDs(filter, user, false);
    }*/

    /*public List<Integer> getEntityIDs(EntityFilter filter, User user, boolean excludeRooms) {
        List<Integer> tenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        boolean showDisabledRooms = systemServiceConfig.isShowDisabledRooms();
        List<Integer> list = this.dao.getEntityIDs(TenantContext.getTenantId(), filter, user, excludeRooms, tenantIds, showDisabledRooms);
        return list;
    }*/

    public List<RoomIdSearchResult> searchRoomIds(EntityFilter filter, User user) {
        List<Integer> tenantIds = tenantService
                .canCallToTenantIds(TenantContext.getTenantId());
        boolean showDisabledRooms = systemServiceConfig.isShowDisabledRooms();
        List<RoomIdSearchResult> roomIdSearchResults = dao.searchRoomIds(
                TenantContext.getTenantId(), filter, user, tenantIds,
                showDisabledRooms);
        return roomIdSearchResults;
    }



    @Override
    public int findRoomsCount(int thisUserMemberID, String query, String queryField, List<Integer> roomTypes) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        boolean showDisabledRooms = systemServiceConfig.isShowDisabledRooms();
        return dao.findRoomsCount(thisUserMemberID, query, queryField, allowedTenantIds, roomTypes, showDisabledRooms);
    }

    @Override
    public List<RoomMini> findRooms(int thisUserMemberID, String query, String queryField, List<Integer> roomTypes, int start, int limit, String sortBy, String sortDir) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        boolean showDisabledRooms = systemServiceConfig.isShowDisabledRooms();
        return dao.findRooms(thisUserMemberID, query, queryField, allowedTenantIds, roomTypes, start, limit, sortBy, sortDir, showDisabledRooms);
    }

    @Override
    public int getMemberIDForRoomIDIfAllowed(int roomID) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        return dao.getMemberIDForRoomID(roomID, allowedTenantIds);
    }

    /*public int getCountEntityIDs(EntityFilter filter, User user, boolean excludeRooms) {
        List<Integer> tenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        boolean showDisabledRooms = systemServiceConfig.isShowDisabledRooms();
        int number = this.dao.getCountEntityIDs(TenantContext.getTenantId(), filter, user, excludeRooms, tenantIds, showDisabledRooms);
        return number;
    }*/

    public List<RoomProfile> getRoomProfiles() {
        List<RoomProfile> list = this.dao.getRoomProfiles();
        return list;
    }

    public RoomProfile getRoomProfile(int roomID) {
        RoomProfile rc = this.dao.getRoomProfile(roomID);
        return rc;
    }

    public int updateRoomProfile(int roomID, String profileName) {
        int rc = this.dao.updateRoomProfile(roomID, profileName);
        return rc;
    }

    public int removeRoomProfile(int roomID) {
        int rc = this.dao.removeRoomProfile(roomID);
        return rc;
    }

    /**
     * Get the list of Entities for multiple RoomIds
     *
     * @param roomIDs
     * @param user
     * @param filter
     * @return
     */
    public List<Entity> getEntities(List<Integer> roomIDs, User user, EntityFilter filter) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        List<Entity> entities = dao.getEntities(roomIDs, TenantContext.getTenantId(), allowedTenantIds, user, filter);
        for (Entity entity : entities) {
            entity.setName(entity.getName());
            switch (entity.getRoomTypeID()) {
            case 1:
                entity.setRoomType("Personal");
                break;
            case 2:
                entity.setRoomType("Public");
                break;

            case 3:
                entity.setRoomType("Legacy");
                break;

            case 4:
                entity.setRoomType("Scheduled");
                // Scheduled Room - Override room name/extn
                entity = overrideScheduledRoomProperties(entity);
                break;
            }
            switch (entity.getModeID()) {
            case 1:
                entity.setModeName("Available");
                break;
            case 2:
                entity.setModeName("Away");
                break;

            case 3:
                entity.setModeName("DoNotDisturb");
                break;

            default:
                entity.setModeName("Available");
                break;
            }

        }
        return entities;
    }

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomID
     * @return
     */
    @Override
    public Room getRoomDetailsForConference(int roomID) {
        Room room = null;
        try {
            room = dao.getRoomDetailsForConference(roomID, TenantContext.getTenantId());
        } catch (Exception dae) {
            logger.warn("Room does not exist {}", roomID);
        }
        return room;
    }

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomExt Room Extension or Room Name
     * @param vidyoGatewayControllerDns
     * @return
     */
    @Override
    public Room getRoomDetailsForConference(String roomExt, String vidyoGatewayControllerDns) {
        Room room = dao.getRoomDetailsForConference(roomExt, vidyoGatewayControllerDns);
        return room;
    }

    /**
     * Returns the Room, Member details needed for creating the Conference
     * by roomExtn (across all tenants) or roomName on the specific tenant
     *
     * @param roomNameExt Room Extension or Room Name
     * @param tenantId
     * @return a <code>Room</code> if one matches the search criteria
     */
    @Override
    public Room getRoomDetailsForConference(String roomNameExt, int tenantId) {
        Room room = dao.getRoomDetailsForConference(roomNameExt, tenantId);
        return room;
    }

    /**
     * Returns the Room, Member, Tenant details needed for Webcast
     *
     * @param roomID
     * @return
     */
    @Override
    public Room getRoomDetailsForWebcast(int roomID) {
        Room room = dao.getRoomDetailsForWebcast(roomID, TenantContext.getTenantId());
        return room;
    }


    public int updateRoomModeratorPIN(Room room) {
        int rc = this.dao.updateRoomModeratorPIN(TenantContext.getTenantId(), room);
        return rc;
    }

    /**
     * Returns the Room based on TenantId and Room Name/Extension
     * @param nameExt
     * @param tenantId
     * @return
     */
    public Room getRoom(String nameExt, int tenantId) {
        Room room = null;
        try {
            room = this.dao.getRoom(tenantId, nameExt);
        } catch (Exception e) {
            logger.error("Room not found for Name/Ext - "+ nameExt + " in Tenant - "+ tenantId);
        }
        return room;
    }

    public String generateRoomExt(String tenantPrefix) {
        boolean roomExist = true;
        int count = 0;
        String roomExtn = null;
        // Member is not inserted at this point, so get the max memberId from
        // the Database
        int memberId = memberService.getCountAllSeats().intValue();
        while (roomExist && count++ < 10) {
            int randNumb = generator.generateRandom();
            roomExtn = tenantPrefix + generator.generateRandomRoomExtn(TenantContext.getTenantId(), null);
            // Check if Room Extension exists
            roomExist = dao.isRoomExistForRoomExtNumber(roomExtn);
            if (roomExist) {
                roomExtn = null;
                continue;
            }
        }
        if (roomExtn == null) {
            logger.error("Room Extension number cannot be generated for max count {}, tenantPrefix {}", memberId,
                    tenantPrefix);
            throw new RuntimeException("Room Extension number cannot be generated");
        }
        return roomExtn;
    }

    /**
     * Deletes all the Scheduled Rooms
     *
     * @param prefix
     * @return
     */
    public int deleteScheduledRooms(String prefix) {
        int deletedCount = dao.deleteScheduledRooms(prefix);
        return deletedCount;
    }

    /**
     * Deletes the Scheduled Room based on the roomKey.
     *
     * @param roomKey
     * @return
     */
    public int deleteScheduledRoomByRoomKey(String roomKey) {
        int deletedCount = dao.deleteScheduledRoomByRoomKey(roomKey);
        return deletedCount;
    }

    /**
     * Creates a Scheduled Room for the user and returns the Room details.
     *
     * @param tenantId
     *            Tenant to which the user belongs
     * @param memberId
     *            User requesting creation of Scheduled Room
     * @param groupId
     *            Group to which the user belongs
     * @param memberName
     *            Name of the User
     * @param recurring
     *            flag indicating the recurrence of the meeting
     * @return details of the room created wrapped in service response
     * @See {@link RoomCreationResponse}
     */
    public RoomCreationResponse createScheduledRoom(SchRoomCreationRequest schRoomCreationRequest) {
        //TODO - properties file message
        RoomCreationResponse roomCreationResponse = new RoomCreationResponse();
        Configuration scheduledRoomconfig = systemService.getConfiguration("SCHEDULED_ROOM_PREFIX");
        // Return error if scheduled room is not enabled at the system level
        if (scheduledRoomconfig == null
                || scheduledRoomconfig.getConfigurationValue() == null
                || scheduledRoomconfig.getConfigurationValue().trim().length() == 0) {
            roomCreationResponse.setStatus(RoomCreationResponse.SYSTEM_SCHEDULED_ROOM_FEATURE_DISABLED);
            roomCreationResponse.setMessage("Scheduled Room Feature not available");
            logger.warn("Scheduled room feature is disabled at the system level");
            return roomCreationResponse;
        }

        // Return error if scheduled room is disabled at tenant level
        Tenant tenant = tenantService.getTenant(schRoomCreationRequest.getTenantId());
        if (tenant == null || tenant.getScheduledRoomEnabled() != 1) {
            roomCreationResponse.setStatus(RoomCreationResponse.TENANT_SCHEDULED_ROOM_FEATURE_DISABLED);
            roomCreationResponse.setMessage("Scheduled Room Feature not available");
            logger.warn("Scheduled room feature is disabled at the Tenant level, {}", schRoomCreationRequest.getTenantId());
            return roomCreationResponse;
        }

        String roomExtn = null;
        boolean roomExist = true;
        String roomPIN = String.valueOf(generator.generateRandom());
        int count = 0;
        boolean roomCreated = false;
        while (roomExist && count < 10) {
            if (tenant.getTenantPrefix() != null ) {
                roomExtn = tenant.getTenantPrefix() + generator.generateRandomRoomExtn(tenant.getTenantID(),  tenant.getTenantPrefix());
            } else {
                roomExtn = generator.generateRandomRoomExtn(tenant.getTenantID(), null);
            }
            // Check if Room Extension exists
            roomExist = dao.isRoomExistForRoomExtNumber(roomExtn);
            // If extn is available, created room or continue the loop
            if (!roomExist) {
                // Save Room
                Room room = new Room();
                // Get the RoomTypeID
                room.setRoomType("Scheduled");
                room.setRoomTypeID(4);
                room.setMemberID(schRoomCreationRequest.getMemberId());
                room.setGroupID(schRoomCreationRequest.getGroupId());
                room.setProfileID(0);
                room.setRoomName(roomExtn);
                room.setRoomExtNumber(roomExtn);
                room.setRecurring(schRoomCreationRequest.getRecurring());
                if(schRoomCreationRequest.isPinRequired()) {
                    room.setRoomPIN(String.valueOf(roomPIN));
                }
                if(schRoomCreationRequest.getExternalRoomId() != null) {
                	room.setExternalRoomID(schRoomCreationRequest.getExternalRoomId());
                }
                room.setRoomKey(SecureRandomUtils.generateRoomKey(getRoomKeyLength()));
                room.setRoomDescription(schRoomCreationRequest.getMemberName() + "-" + "ScheduledRoom");
                room.setRoomEnabled(1);

                if (schRoomCreationRequest.getRecurring() > 0) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date()); // Now use today date.
                    c.add(Calendar.DATE, schRoomCreationRequest.getRecurring()+1); // Adding 5 days
                    Date dt2 = c.getTime();
                    room.setDeleteon(dt2);
                }
                int roomId = 0;
                try {
                    if (VendorUtils.isRoomsLockedByDefault()) {
                        room.setRoomLocked(1);
                    }
                    // set room attributes based on tenant settings
                    TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(schRoomCreationRequest.getTenantId());
                    room.setRoomMuted(tenantConfiguration.getWaitingRoomsEnabled());
                    room.setLectureMode(tenantConfiguration.getWaitingRoomsEnabled());

                    roomId = dao.insertRoom(schRoomCreationRequest.getTenantId(), room);
                } catch (Exception e) {
                    logger.error("Room extension not availble, continue generating the next random number - {}",
                            roomExtn);
                    roomExist = true;
                    count++;
                    continue;
                }
                room.setRoomID(roomId);
                roomCreationResponse.setExtensionValue(roomExtn);
                roomCreationResponse.setRoom(room);
                roomCreationResponse.setTenant(tenant);
                roomCreated = true;
            }
            count++;
        }

        if (!roomCreated) {
            // Scheduled Room creation failed due to duplicates
            logger.error("Scheduled Room Creation Failed after attempts -" + count);
            roomCreationResponse.setStatus(RoomCreationResponse.SCHEDULED_ROOM_CREATION_FAILED);
            roomCreationResponse.setMessage("Scheduled Room Creation Failed");
        }

        return roomCreationResponse;
    }

    /**
     * Decrypts the scheduled room extension & pin and validates the room
     * existence
     *
     * @param extn
     * @param pin
     * @return
     */
    public ScheduledRoomResponse validateScheduledRoom(String roomExt, String pin, int tenantId) {
        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        logger.debug("Entered into validateScduledRoom " + roomExt + " pin " + pin + "tenantId " + tenantId);
        Room room = null;
        //check for exact match of roomExtension and pin from the DB.
        try {
            room = dao.getRoomDetailsForConference(roomExt, tenantId);
        } catch (Exception e) {
            logger.error("Could not find the exact roomExtension match. fallback to older implementation. "+roomExt);
        }
        scheduledRoomResponse.setRoom(room);
        if (room != null) {
            scheduledRoomResponse.setStatus(0);
            if(pin != null && !pin.isEmpty()) {
                // Set the user entered pin back, as it has been validated
                try {
                    scheduledRoomResponse.setPin(Integer.valueOf(pin));
                } catch (NumberFormatException nfe) {
                    scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_PIN_EXTN);
                }
            }
            return scheduledRoomResponse;
        }
        // Check if Scheduled Room is enabled at system level
        Configuration config = systemService.getConfiguration(SCHEDULED_ROOM_PREFIX);
        // Return error if scheduled room is disabled at system level or pin is
        // invalid
        if (config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()) {
            scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
            scheduledRoomResponse.setMessage("invalid.room");
            scheduledRoomResponse.setDisplayMessage("Invalid Room");
            scheduledRoomResponse.setMessageId("Room");
            scheduledRoomResponse.setSchRoomPrefix(""); // set empty prefix
            return scheduledRoomResponse;
        }

        String schRoomPrefix = config.getConfigurationValue().trim();

        scheduledRoomResponse.setSchRoomPrefix(schRoomPrefix);

        if ((roomExt != null && roomExt.length() < schRoomPrefix.trim().length())) {
            scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
            scheduledRoomResponse.setMessage("invalid.room");
            scheduledRoomResponse.setDisplayMessage("Invalid Room");
            scheduledRoomResponse.setMessageId("Room");
            return scheduledRoomResponse;
        }

        // Room Extension input length should be greater than scheduled room
        // prefix value
        int endIndex = schRoomPrefix.length();
        String roomExtPrefix = roomExt.substring(0, endIndex);

        if (!roomExtPrefix.equalsIgnoreCase(schRoomPrefix)) {
            scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
            scheduledRoomResponse.setMessage("invalid.room");
            scheduledRoomResponse.setMessageId("Room");
            scheduledRoomResponse.setDisplayMessage("Invalid Room");
            return scheduledRoomResponse;
        }

        roomExtPrefix = roomExt.substring(0, endIndex);

        // Split the prefix, extension and pin values
        String extn = roomExt.substring(schRoomPrefix.length());
        logger.debug("extn -> {}", extn);
        ScheduledRoomResponse response = generator.decryptShceduledRoom(extn, pin);
        response.setSchRoomPrefix(schRoomPrefix);
        // Return error if the decryption fails
        if(response.getStatus() != 0) {
            return response;
        }
        String origExtnValue = null;
        if(response.getPin() != 0) {
            // Concat all the values together as a string
            origExtnValue = schRoomPrefix + response.getRoomExtn() + response.getPin();
        } else {
            origExtnValue = schRoomPrefix + response.getRoomExtn();
        }


        try {
            room = dao.getRoomDetailsForConference(origExtnValue, tenantId);
        } catch (Exception e) {
            logger.error("Room not found with extension - {}", origExtnValue);
            response.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
            return response;
        }
        response.setRoom(room);
        if (room != null) {
            response.setStatus(0);
            if(pin != null && !pin.isEmpty()) {
                // Set the user entered pin back, as it has been validated
                try {
                    response.setPin(Integer.valueOf(pin));
                } catch (NumberFormatException nfe) {
                    response.setStatus(ScheduledRoomResponse.INVALID_PIN_EXTN);
                }
            }
        } else {
            response.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
        }
        return response;
    }

    public ScheduledRoomResponse generateSchRoomExtPin(int memberId, int randNumb) {
        String encryptedExtPin = generator.generateSchRoomExtnWithPin(memberId, randNumb);
        String[] extnPin = encryptedExtPin.split("-");
        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        if(extnPin.length != 2) {
            scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
            return scheduledRoomResponse;
        }
        String extn = null;
        Configuration configuration = systemService.getConfiguration("SCHEDULED_ROOM_PREFIX");
        if(configuration != null && configuration.getConfigurationValue() != null) {
            extn = configuration.getConfigurationValue();
        }
        extn = (extn == null) ? extnPin[0] : (extn + extnPin[0]);
        scheduledRoomResponse.setRoomExtn(extn);
        scheduledRoomResponse.setPin(Integer.valueOf(extnPin[1]));
        return scheduledRoomResponse;
    }

    @Override
    public RoomControlResponse lockRoom(int loggedMemberID, int lockRoomID, String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering lockRoom() of RoomServiceImpl");

        RoomControlResponse response = new RoomControlResponse();

        Room lockRoom = null;
        try {
            lockRoom = getRoom(lockRoomID);
        } catch (Exception e) {
            logger.error("Invalid lockRoom", e.getMessage());

            response.setStatus(RoomControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidLockRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting lockRoom() of RoomServiceImpl");

            return response;
        }
        if(lockRoom == null) {
            logger.error("Invalid lockRoom");

            response.setStatus(RoomControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidLockRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting lockRoom() of RoomServiceImpl");

            return response;
        }

        if(utilService.canMemberControlRoom(loggedMemberID, lockRoom, moderatorPIN)) {
            try {
                lockRoom(lockRoomID);
            } catch (Exception e) {
                logger.error("LockRoomFailed", e.getMessage());

                response.setStatus(RoomControlResponse.LOCK_ROOM_FAILED);
                response.setMessageId("LockRoomFailed");
                response.setMessage("lock.room.failed");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(RoomControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting lockRoom() of RoomServiceImpl");

        return response;
    }

    @Override
    public RoomControlResponse unlockRoom(int loggedMemberID, int unlockRoomID, String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering unlockRoom() of RoomServiceImpl");

        RoomControlResponse response = new RoomControlResponse();

        Room unlockRoom = null;
        try {
            unlockRoom = getRoom(unlockRoomID);
        } catch (Exception e) {
            logger.error("Invalid unlockRoom", e.getMessage());

            response.setStatus(RoomControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnlockRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unlockRoom() of RoomServiceImpl");

            return response;
        }
        if(unlockRoom == null) {
            logger.error("Invalid unlockRoom");

            response.setStatus(RoomControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnlockRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unlockRoom() of RoomServiceImpl");

            return response;
        }

        if(utilService.canMemberControlRoom(loggedMemberID, unlockRoom, moderatorPIN)) {
            try {
                unlockRoom(unlockRoomID);
            } catch (Exception e) {
                logger.error("UnlockRoomFailed", e.getMessage());

                response.setStatus(RoomControlResponse.LOCK_ROOM_FAILED);
                response.setMessageId("UnlockRoomFailed");
                response.setMessage("unlock.room.failed");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(RoomControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting unlockRoom() of RoomServiceImpl");

        return response;
    }

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
    public RoomUpdateResponse updateRoomPrefix(String oldPrefix, int tenantId, String newPrefix) {

        Tenant tenant = tenantService.getTenant(tenantId);
        RoomUpdateResponse roomUpdateResponse = new RoomUpdateResponse();

        // Validation to check if the parameters are correct
        if (!oldPrefix.equalsIgnoreCase(tenant.getTenantPrefix())) {
            logger.error("Prefix don't match - existing tenant prefix {}, input old tenant prefix -{}",
                    tenant.getTenantPrefix(), oldPrefix);
            roomUpdateResponse.setStatus(RoomUpdateResponse.INPUT_TENANT_PREFIX_DOESNT_MATCH);
            return roomUpdateResponse;
        }

        int count = 0;
        try {
            count = dao.updateRoomExtensionPrefix(newPrefix, tenantId, oldPrefix);
        } catch (DataAccessException dae) {
            // Unexpected database error - log it and return error
            logger.error("Database Exception happened during room extension prefix upate ", dae);
            roomUpdateResponse.setStatus(RoomUpdateResponse.ROOM_PREFIX_UPDATE_FAILED);
        }

        if (count <= 0) {
            logger.error("No rows updated newPrefix - {}, - oldPrefix - {}, tenantId -{}", newPrefix, oldPrefix, tenantId);
            roomUpdateResponse.setStatus(RoomUpdateResponse.ROOM_PREFIX_UPDATE_FAILED);
        }

        return roomUpdateResponse;

    }

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
    public int isPrefixExisting(String tenantPrefix, int tenantId) {
        int count = dao.getRoomsCountMatchingPrefix(tenantId, tenantPrefix);
        return count;
    }

    /**
     * Updates the GroupId to Default GroupId for Rooms.
     *
     * @param roomUpdateRequest
     * @return
     */
    public RoomUpdateResponse updateGroupForRooms(RoomUpdateRequest roomUpdateRequest) {
        RoomUpdateResponse roomUpdateResponse = new RoomUpdateResponse();
        try {
            int updateCount = dao.updateGroupForRooms(roomUpdateRequest);
        } catch (Exception e) {
            logger.error("Exception while updating groupId for Rooms. This should never happen. ", e.getMessage());
            roomUpdateResponse.setStatus(RoomUpdateResponse.HTTP_INTERNAL_ERROR);
            roomUpdateResponse.setMessage("update.failed");
            return roomUpdateResponse;
        }

        roomUpdateResponse.setStatus(RoomUpdateResponse.SUCCESS);
        return roomUpdateResponse;
    }

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
    @Override
    public RoomAccessDetailsResponse getRoomAccessOptions(int memberId, int roomId, int tenantId) {
        logger.debug("Entering getInviteContentForRoom(memberId, roomId, tenantId) of RoomService");
        Room room = null;
        try {
            room = dao.getRoom(tenantId, roomId);
        } catch (Exception e) {
            logger.info("Room does not exist {}", roomId);
        }
        RoomAccessDetailsResponse accessOptionsResponse = new RoomAccessDetailsResponse();
        // Validate room owner
        if (room == null || room.getMemberID() != memberId) {
            logger.warn("Shot Invite API - Unauthorized access of Room {} by Member {} in Tenant {}", roomId, memberId,
                    tenantId);
            accessOptionsResponse.setStatus(JoinConferenceResponse.INVALID_ROOM);
            accessOptionsResponse.setMessage("invalid.room");
            accessOptionsResponse.setMessageId("Room");
            return accessOptionsResponse;
        }

        // Normal room and scheduled room without PIN
        String roomKey = room.getRoomKey();
        String pin = room.getRoomPIN();
        String extension = room.getRoomExtNumber();

        // Scheduled room case with PIN
        if (room.getRoomType().equalsIgnoreCase("Scheduled") && room.getRoomPIN() != null) {
            ScheduledRoomResponse scheduledRoomResponse = generateSchRoomExtPin(room.getMemberID(),
                    Integer.parseInt(room.getRoomPIN()));
            if (scheduledRoomResponse.getStatus() != ScheduledRoomResponse.SUCCESS) {
                accessOptionsResponse.setStatus(scheduledRoomResponse.getStatus());
                accessOptionsResponse.setMessage(scheduledRoomResponse.getMessage());
                accessOptionsResponse.setMessageId(scheduledRoomResponse.getMessageId());
                return accessOptionsResponse;
            }
            extension = scheduledRoomResponse.getRoomExtn();
            pin = String.valueOf(scheduledRoomResponse.getPin());
        }

        accessOptionsResponse.setRoomKey(roomKey);
        accessOptionsResponse.setExtension(extension);
        accessOptionsResponse.setRoomPin(pin);

        Tenant tenant = tenantService.getTenant(tenantId);
        if (tenant != null) {
            // This condition should never fail
            accessOptionsResponse.setTenantUrl(tenant.getTenantURL());
        }

        logger.debug("Exiting getInviteContentForRoom(memberId, roomId, tenantId) of RoomService");
        return accessOptionsResponse;
    }

    /**
     * Deletes inactive scheduled rooms based on the inactivity limit.
     *
     * @param inactiveDays Days of Inactivity
     * @return count of deleted rooms
     */
    public int deleteInactiveSchRooms(int inactiveDays) {
        return dao.deleteInactiveSchRooms(inactiveDays);
    }

    /**
     * Delete scheduled rooms based on recurring value and last used
     * @param limit
     * @return count of deleted rooms
     */
    public int deleteScheduledRoomsbyRecurring (int limit){
        logger.debug("Inside deleteScheduledRoomsbyRecurring...Limit = "+ limit);
        return dao.deleteScheduledRoomsbyRecurring(limit);
    }

    /**
     * Utility to override the scheduled room specific properties
     *
     * @param entity
     * @return
     */
	public Entity overrideScheduledRoomProperties(Entity entity) {
		//Starting 18.1.0 Scheduled Room has the Tenant Prefix instead of the Dedicated prefix
		//Check if the room extension number does not start with tenant prefix to apply the below logic
		Tenant roomOwnerTenant = tenantService.getTenant(entity.getTenantID());
		if (entity.getRoomPIN() != null && (entity.getExt() != null && !entity.getExt().startsWith(roomOwnerTenant.getTenantPrefix()))) {
			ScheduledRoomResponse scheduledRoomResponse = generateSchRoomExtPin(entity.getMemberID(),
					Integer.valueOf(entity.getRoomPIN().trim()));
			if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
				entity.setRoomName(scheduledRoomResponse.getRoomExtn());
				entity.setName(scheduledRoomResponse.getRoomExtn());
				entity.setExt(scheduledRoomResponse.getRoomExtn());
				entity.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
			}
		}
		return entity;
	}
	
    /**
     * Utility method to override scheduled room properties.
     *
     * @param room
     * @return
     */
    public Room overrideScheduledRoomProperties(Room room) {
		//Starting 18.1.0 Scheduled Room has the Tenant Prefix instead of the Dedicated prefix
		//Check if the room extension number does not start with tenant prefix to apply the below logic
    	Tenant roomOwnerTenant = tenantService.getTenant(room.getTenantID());
        if (room.getRoomPIN() != null && (room.getRoomExtNumber() != null && !room.getRoomExtNumber().startsWith(roomOwnerTenant.getTenantPrefix()))) {
            ScheduledRoomResponse scheduledRoomResponse = generateSchRoomExtPin(room.getMemberID(),
                    Integer.valueOf(room.getRoomPIN().trim()));
            if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
                room.setRoomName(scheduledRoomResponse.getRoomExtn());
                room.setRoomExtNumber(scheduledRoomResponse.getRoomExtn());
                room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
            }
        }
        return room;
    }


    /**
     * Updates the room's group for the Member based on room type
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupByMember(RoomUpdateRequest roomUpdateRequest) {
        int updateCount = 0;
        try {
            updateCount = dao.updateGroupByMember(roomUpdateRequest);
        } catch (DataAccessException dae) {
            logger.error("DataAccessException while updating the room's group by member {}", dae.getMessage());
        }

        return updateCount;
    }

    /**
     * Returns the Accessible Room details for the user
     * @param roomId
     * @param canCallTenantIds
     * @return
     */
    @Override
    public Room getAccessibleRoomDetails(int roomId, List<Integer> canCallTenantIds) {
        Room room = null;
        try {
            room = dao.getAccessibleRoomDetails(roomId, canCallTenantIds);
        } catch(DataAccessException dae) {
            logger.warn("Error while excuting getAccessibleRoomDetails query - ", dae.getMessage());
        }
        return room;
    }

    /**
     * Returns RoomId, memberId, moderatorPin, roomType of the room.
     *
     * @param roomId
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsForDisconnectParticipant(int roomId, int tenantId) {
        Room room = null;
        try {
            room = dao.getRoomDetailsForDisconnectParticipant(roomId, tenantId);
        } catch (DataAccessException dae) {
            logger.warn(
                    "Error while excuting getRoomDetailsForDisconnectParticipant query - ",
                    dae.getMessage());
        }
        return room;
    }

    /**
     * Returns RoomId based on roomExt. If <code>tenantId == 0</code> the search will done against all rooms, otherwise against rooms
     * which member is equal to tenantId.
     * @param tenantId
     * @param roomExt
     * @return
     */
    public int getRoomIdByExt(int tenantId, String roomExt, String pin) {
        int roomId = 0;

        try {
            roomId = dao.getRoomIdByExt(tenantId, roomExt);
        } catch (Exception dae) {
            logger.warn("Error while excuting getRoomIdByExt query - ", dae.getMessage());
        }

        if(roomId > 0) {
            return roomId;
        }

        // Try checking for scheduled room
        ScheduledRoomResponse scheduledRoomResponse = null;
        scheduledRoomResponse = validateScheduledRoom(roomExt, pin, tenantId);

        if(scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
            roomId = scheduledRoomResponse.getRoom().getRoomID();
        } else {
            roomId = scheduledRoomResponse.getStatus() * -1;
        }

        return roomId;
    }

    /**
     * Check the tenant configuration setting to confirm if the users are allowed to create the public room
     * @param tenantId
     * @return
     */
    public int getUserCreatePublicRoomAllowed(int tenantId) {
        TenantConfiguration tc = tenantService.getTenantConfiguration(tenantId);
        if (tc.getCreatePublicRoomEnable() > 0){
            return tc.getMaxCreatePublicRoomUser();
        }

        return 0;
    }

    /**
     * Check the total public rooms created by the user
     * @param tenantId
     * @param ownerId
     * @return
     */
    public int getUserCreatePublicRoomCount(int tenantId, int ownerId) {
        int roomExists = this.dao.getPublicRoomCountForOwnerID(tenantId, ownerId);

        return roomExists;
    }

    public long getPublicRoomCountForTenentID(int tenantId) {
        //copied from the below method.getTenantCreatePublicRoomRemainCount
        long roomCount = this.dao.getPublicRoomCountForTenentID(tenantId);

        return roomCount;
    }

    /**
     *
     * @param tenantId
     * @return
     */
    public long getTenantCreatePublicRoomRemainCount(int tenantId) {
        long roomCount = this.dao.getPublicRoomCountForTenentID(tenantId);

        TenantConfiguration tc = tenantService.getTenantConfiguration(tenantId);
        roomCount = tc.getMaxCreatePublicRoomTenant() - roomCount;

        return roomCount;
    }

    public long getSystemCreatePublicRoomRemainCount() {

        Configuration config = systemService.getConfiguration("MAX_CREATE_PUBLIC_ROOM");

        long totalRooms = this.dao.getPublicRoomCount();
        totalRooms = Long.parseLong(config.getConfigurationValue()) - totalRooms;

        return totalRooms;
    }

    public int getDisplayNameCount(String displayName, int tenant) {
        int displayNameCount = this.dao.getDisplayNameCount(displayName, tenant);

        return displayNameCount;
    }

    /**
     * Create the public room as well as generate new extn and add to it.
     * The extension length will be used from system level settings.
     * @param publicRoomCreateRequest
     * @return
     */
    @Transactional
    public RoomCreationResponse createPublicRoom(PublicRoomCreateRequest publicRoomCreateRequest) {
        RoomCreationResponse roomCreationResponse = new RoomCreationResponse();
        Configuration publicRoomconfig = systemService.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");

        Configuration roomExtnLength = systemService.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH");

        // Return error if public room is not enabled at the system level
        if (publicRoomconfig == null
                || publicRoomconfig.getConfigurationValue() == null
                || publicRoomconfig.getConfigurationValue().trim().length() == 0) {
            roomCreationResponse.setStatus(RoomCreationResponse.SYSTEM_PUBLIC_ROOM_FEATURE_DISABLED);
            roomCreationResponse.setMessage("Public Room Feature not available");
            logger.warn("Public room feature is disabled at the system level");
            return roomCreationResponse;
        }

        // Return error if public room is disabled at tenant level
        TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(publicRoomCreateRequest.getTenantId());
        if (tenantConfiguration == null || tenantConfiguration.getCreatePublicRoomEnable() != 1) {
            roomCreationResponse.setStatus(RoomCreationResponse.TENANT_PUBLIC_ROOM_FEATURE_DISABLED);
            roomCreationResponse.setMessage("Public Room Feature not available");
            logger.warn("Public room feature is disabled at the Tenant level, {}", publicRoomCreateRequest.getTenantId());
            return roomCreationResponse;
        }

        Tenant tenant = tenantService.getTenant(publicRoomCreateRequest.getTenantId());
        String roomExtn = null;
        boolean roomExist = true;
        int count = 0;
        boolean roomCreated = false;
        while (roomExist && count < 10) {

            if (tenant.getTenantPrefix() != null ) {
                roomExtn = tenant.getTenantPrefix() + generator.generateRandomRoomExtn(tenant.getTenantID(),  tenant.getTenantPrefix());
            } else {
                roomExtn = generator.generateRandomRoomExtn(tenant.getTenantID(), null);
            }

            // Check if Room Extension exists
            roomExist = dao.isRoomExistForRoomExtNumber(roomExtn);
            // If extn is available, created room or continue the loop
            if (!roomExist) {
                // Save Room
                Room room = new Room();
                // Get the RoomTypeID
                room.setRoomType("Public");
                room.setRoomTypeID(2);
                room.setMemberID(publicRoomCreateRequest.getMemberId());
                room.setGroupID(publicRoomCreateRequest.getGroupId());
                room.setProfileID(0);
                room.setRoomName(publicRoomCreateRequest.getRoomName());

                room.setDisplayName(publicRoomCreateRequest.getDisplayName());
                room.setRoomExtNumber(roomExtn);

                if(publicRoomCreateRequest.isPinRequired()) {
                    room.setRoomPIN(publicRoomCreateRequest.getPIN());
                }
                room.setRoomKey(SecureRandomUtils.generateRoomKey(getRoomKeyLength()));
                if (publicRoomCreateRequest.getDescription() == null ) {
                    room.setRoomDescription(publicRoomCreateRequest.getMemberName() + "-" + "PublicRoom");
                } else {
                    room.setRoomDescription(publicRoomCreateRequest.getDescription());
                }
                room.setRoomEnabled(1);
                int roomId = 0;
                try {
                    if (!VendorUtils.isRoomsLockedByDefault() && publicRoomCreateRequest.isLocked()) {
                        room.setRoomLocked(1);
                    } else if (VendorUtils.isRoomsLockedByDefault()) {
                        room.setRoomLocked(1);
                    } else {
                        room.setRoomLocked(0);
                    }
                    // set room attributes based on tenant settings
                    room.setRoomMuted(tenantConfiguration.getWaitingRoomsEnabled());
                    room.setLectureMode(tenantConfiguration.getWaitingRoomsEnabled());

                    roomId = dao.insertRoom(publicRoomCreateRequest.getTenantId(), room);
                } catch (Exception e) {
                    logger.error("Room extension not availble, continue generating the next random number - {}",
                            roomExtn);
                    roomExist = true;
                    count++;
                    continue;
                }

                roomCreationResponse.setExtensionValue(roomExtn);

                room.setRoomID(roomId);
                roomCreationResponse.setRoom(room);
                roomCreationResponse.setTenant(tenant);
                roomCreated = true;
            }
            count++;
        }

        if (!roomCreated) {
            // Scheduled Room creation failed due to duplicates
            logger.error("Public Room Creation Failed after attempts -" + count);
            roomCreationResponse.setStatus(RoomCreationResponse.PUBLIC_ROOM_CREATION_FAILED);
            roomCreationResponse.setMessage("Public Room Creation Failed");
        }

        return roomCreationResponse;
    }

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
    @Override
    public int isPrefixExistingInDefaultTenant(String tenantPrefix) {
        int count = dao.getDefaultTenantRoomsCountMatchingPrefix(tenantPrefix);
        return count;
    }

    /**
     * Get the Entity Details with LDAP attributes
     * @param entityID
     * @param user
     * @return
     */
    @Override
    public Entity getOneEntityDetails(int entityID, User user) {
        Entity entity = this.dao.getOneEntityDetails(TenantContext.getTenantId(), entityID, user);
        if (entity.getRoomType().equalsIgnoreCase("Scheduled")) {
            // Override the room name/extn/displayname with ScheduledRoom extension
            entity = overrideScheduledRoomProperties(entity);
        }
        return entity;
    }

    /**
     * @param displayName
     * @param roomId
     * @param tenant
     * @return
     */
    @Override
    public int getDisplayNameCount(String displayName, int roomId, int tenant){
        int displayNameCount = this.dao.getDisplayNameCount(displayName, roomId, tenant);

        return displayNameCount;
    }

    /**
     * Returns the roomId, roomKey and moderatorKey by roomKey and tenantId
     * @param roomKey
     * @param tenantId
     * @return
     */
    @Override
    public Room getRoomDetailsByRoomKey(String roomKey, int tenantId) {
        Room room = null;
        try {
            room = dao.getRoomDetailsByRoomKey(roomKey, tenantId);
        } catch (Exception e) {
            logger.error("Error while retrieving room by room Key" + roomKey);
        }
        return room;
    }


    @Override
    /**
     * Get the Room URL based on the format set by super
     * @param system
     * @param scheme
     * @param host
     * @param roomKey
     * @return
     */
    public String getRoomURL(ISystemService system, String scheme, String host, String roomKey){
        StringBuilder roomURL = new StringBuilder();
        if (StringUtils.isNotBlank(scheme))
            roomURL.append(scheme);
        if (StringUtils.isNotBlank(host))
            roomURL.append("://").append(host);
        Configuration roomLinkFormatConfig = system.getConfiguration("ROOM_LINK_FORMAT");
        String joinURL = "/flex.html?roomdirect.html&key=";
        if (roomLinkFormatConfig != null && !StringUtils.isEmpty(roomLinkFormatConfig.getConfigurationValue())) {
            if (roomLinkFormatConfig.getConfigurationValue().equalsIgnoreCase("join") ){
                joinURL = "/join/";
            }
        }
        roomURL.append(joinURL).append(roomKey);
        return roomURL.toString();
    }

    /**
     * Get the Room Key length set by super
     * @return
     */
    private int getRoomKeyLength(){
        Configuration roomKeyConfig = systemService.getConfiguration("ROOM_KEY_LENGTH");

        if (roomKeyConfig == null || StringUtils.isBlank(roomKeyConfig.getConfigurationValue())){
            return 10;
        }

        return Integer.parseInt(roomKeyConfig.getConfigurationValue());
    }

    @Override
    public boolean isPublicRoomIdExistsForMemberId(int roomId, int memberId, int tenantId){
        return dao.isPublicRoomIdExistsForMemberId(roomId, memberId, tenantId);
    }

    /**
     * Overloaded API which silences the speaker of the Room from Server side
     * which means Endpoints cannot unsilence the speaker.
     *
     * @param roomId
     * @param flag
     * @return
     */
    @Override
    public int silenceSpeakerForRoomServer(int roomId, int flag) {
        return dao.silenceSpeakerForRoomServer(roomId, flag);
    }
    
    /**
     * Get the Room IDs with Room status and the number of participants in the conference
     * @param roomIds
     * @return
     */
    @Override
    public List<Room> getRoomStatusAndNumOfParticipants(List<Room> roomsToCheck) {
    	
    	logger.debug("getRoomStatusAndNumOfParticipants executing....");
    	if (roomsToCheck == null || roomsToCheck.size() == 0)
    		return roomsToCheck;
    	
    	logger.debug("roomsToCheck = "+ roomsToCheck.toString());
    	
    	//Get the room ids list created for the roomsToCheck
    	List<Integer> roomIds = roomsToCheck.stream().map(Room::getRoomID).collect(Collectors.toList());
    	
    	// Use the room ids list to get the conference details for those rooms
    	List<Room> conferenceStatusRooms = dao.findRoomStatusAndNumOfParticipants(roomIds);

    	// If there are any rooms found in conference, get the number of participants 
    	// and its status updated back in the original list 
    	if (conferenceStatusRooms != null){
        	logger.debug("conferenceStatusRooms = "+ conferenceStatusRooms.toString());
    		conferenceStatusRooms.stream().forEach(confRoom -> { 
    			Optional<Room> roomResulted = roomsToCheck.stream().filter(r -> r.getRoomID() == confRoom.getRoomID()).findFirst();
    			if (roomResulted != null) {
	    			Room r = roomResulted.get();
	    			r.setRoomStatus(confRoom.getRoomStatus());
	    			r.setNumParticipants(confRoom.getNumParticipants());
    			}
    		});
    	}
    	
    	logger.debug("getRoomStatusAndNumOfParticipants returning list.");
    	
    	// Return back the updated Rooms list with the number of participants and room status 
    	
    	return roomsToCheck;
    }

    public int getRoomStatus(int roomId, int groupId) {
        return this.dao.getRoomStatus(roomId, groupId);
    }

	@Override
	public int deleteRoomsByMember(int memberId) {
		return dao.deleteRoomsByMember(memberId);
	}

}