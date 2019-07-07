package com.vidyo.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Room;
import com.vidyo.bo.RoomFilter;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.room.RoomType;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.service.cache.CacheAdministrationService;
import com.vidyo.service.room.request.RoomUpdateRequest;
import com.vidyo.utils.room.RoomUtils;

public class RoomDaoJdbcImpl extends JdbcDaoSupport implements IRoomDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(RoomDaoJdbcImpl.class.getName());

    @Autowired
    private TenantConfigurationDao tenantConfigurationDao;


    @Autowired
    private ITenantDao tenantDao;

    @Autowired
    private CacheAdministrationService cacheAdministrationService;

    /**
     * Template for handling SQLs that support named params
     */
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    /**
     *
     * @param namedParamJdbcTemplate
     */
    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    public List<Room> getRooms(int tenant, RoomFilter filter) {

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        StringBuilder sqlstm = new StringBuilder(
                " SELECT"
                        + " r.roomID,"
                        + " g.groupName,"
                        + " r.displayName,"
                        + " r.roomName,"
                        + " r.roomExtNumber,"
                        + " r.roomDescription,"
                        + " r.roomPIN,"
                        + " r.roomModeratorPIN,"
                        + " r.roomLocked,"
                        + " r.roomKey,"
                        + " r.roomModeratorKey,"
                        + " r.roomEnabled,"
                        + " rt.roomType,"
                        + " m.username as ownerName,"
                        + " m.tenantID as tenantID"
                        /*
                         * Removing these columns as they are not needed by API but only UI
                        + " CASE WHEN c.roomID IS NOT NULL THEN 1 ELSE 0 END as roomStatus,"
                        + " CASE WHEN c.roomID IS NOT NULL THEN COUNT(*) ELSE 0 END as numParticipants"
                        */
                        + " FROM"
                        + " Room r"
                        + " INNER JOIN Member m ON (r.memberID=m.memberID AND m.tenantID = :tenantId AND m.allowedToParticipate=1)"
                        + " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID ");
        if (filter.getType() != null && !filter.getType().isEmpty() && !filter.getType().equalsIgnoreCase("ALL")) {
            sqlstm.append(" AND rt.roomType = :roomType");
            namedParamsMap.put("roomType", filter.getType().trim());
        } else {
            sqlstm.append(" AND r.roomTypeID IN (1,2)");
        }
        if (filter.getRoomStatus() != null) {
            if(filter.getRoomStatus().equalsIgnoreCase("Enabled")) {
                sqlstm.append(" AND r.roomEnabled = 1");
            } if(filter.getRoomStatus().equalsIgnoreCase("Disabled")) {
                sqlstm.append(" AND r.roomEnabled = 0");
            }
        }
        sqlstm.append(")");
        
        /*
         * Removing the Conferences table from join.
         * sqlstm.append(" INNER JOIN Groups g ON (r.groupID=g.groupID) LEFT JOIN Conferences c on c.roomID = r.roomID");
         */
        
        sqlstm.append(" INNER JOIN Groups g ON (r.groupID=g.groupID)");
        namedParamsMap.put("tenantId", tenant);
        String query = RoomUtils.buildSearchQuery(filter.getQuery(), filter.getExt(), filter.getDisplayName() != null ? filter.getDisplayName():filter.getRoomName());

        if (query != null && !query.isEmpty()) {
            sqlstm.append(" WHERE MATCH(roomName,roomExtNumber, displayName) AGAINST (:query in boolean mode)");
            namedParamsMap.put("query", query);
        }
        sqlstm.append(" GROUP BY r.roomID");
        if (!filter.getSort().equalsIgnoreCase("")) {
            sqlstm.append(" ORDER BY ")
            .append(filter.getSort()).append(" ").append(filter.getDir());
        }


        sqlstm.append(" LIMIT :start, :limit");
        namedParamsMap.put("start", filter.getStart());
        namedParamsMap.put("limit", filter.getLimit());
        return namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
    }

    public Long getCountRooms(int tenant, RoomFilter filter) {
        StringBuilder sqlstm = new StringBuilder(
                " SELECT count(*)"
                        + " FROM"
                        + " Room r"
                        + " INNER JOIN Member m ON (r.memberID=m.memberID AND m.tenantID = :tenantId AND m.allowedToParticipate=1)"
                        + " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID");
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter.getType() != null && !filter.getType().isEmpty() && !filter.getType().equalsIgnoreCase("ALL")) {
            sqlstm.append(" AND rt.roomType = :roomType");
            namedParamsMap.put("roomType", filter.getType().trim());
        } else {
            sqlstm.append(" AND r.roomTypeID IN (1,2)");
        }

        if (filter.getRoomStatus() != null) {
            if(filter.getRoomStatus().equalsIgnoreCase("Enabled")) {
                sqlstm.append(" AND r.roomEnabled = 1");
            } if(filter.getRoomStatus().equalsIgnoreCase("Disabled")) {
                sqlstm.append(" AND r.roomEnabled = 0");
            }
        }
        sqlstm.append(")");
        namedParamsMap.put("tenantId", tenant);
        String query = RoomUtils.buildSearchQuery(filter.getQuery(), filter.getExt(), filter.getDisplayName() != null ? filter.getDisplayName():filter.getRoomName());
        if (query != null && !query.isEmpty()) {
            sqlstm.append(" WHERE MATCH(roomName,roomExtNumber, displayName) AGAINST (:query in boolean mode)");
            namedParamsMap.put("query", query);
        }

        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }
    
    /**
     * Find the Room IDs with Room status and the number of participants in the conference
     * @param roomIds
     * @return
     */
    public List<Room> findRoomStatusAndNumOfParticipants(List<Integer> roomIds) {
    	logger.debug("findRoomStatusAndNumOfParticipants executing...");
    	if (roomIds == null || roomIds.size() == 0){
    		return null;
    	}
    	logger.debug("findRoomStatusAndNumOfParticipants has room ids = "+ roomIds);
    	Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        StringBuilder sqlstm = new StringBuilder(
                " SELECT" +
                "  c.roomID as roomID," +
                "  CASE WHEN c.roomID IS NOT NULL THEN 1 ELSE 0 END as roomStatus," +
                "  CASE WHEN c.roomID IS NOT NULL THEN COUNT(c.roomID) ELSE 0 END as numParticipants" +
                " FROM Conferences c WHERE c.roomID in (:roomIDs) GROUP BY c.roomID");

        namedParamsMap.put("roomIDs", roomIds);
        
        logger.debug("findRoomStatusAndNumOfParticipants returning results.");
        
        return namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Room.class));
    }

    public Room getRoom(int tenant, int roomID) {
        logger.debug("Get record from Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  r.roomTypeID," +
            "  r.memberID," +
            "  r.groupID," +
            "  r.profileID," +
            "  g.groupName," +
            "  g.allowRecording," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  r.roomDescription," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomModeratorPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomKey," +
            "  r.roomModeratorKey," +
            "  m.tenantID," +
            "  rt.roomType," +
            "  m.username as ownerName," +
            "  m.memberName as ownerDisplayName," +
            "  r.displayName," + // used to be m.memberName as displayName
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType AND c.endpointType != :endpointType) as numParticipants," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType AND c.endpointType != :endpointType) = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType AND c.endpointType != :endpointType) = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  r.roomMuted," +
            "  r.roomSilenced," +
            "  r.roomVideoMuted," +
            "  r.roomVideoSilenced," +
            "  r.lectureMode," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.allowedToParticipate=:allow)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " WHERE" +
            "  r.roomID =:roomId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("endpointType", "R");
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("roomId", roomID);

        return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
    }

    public Room getRoom(int tenant, String nameExt) {
        logger.debug("Get record from Room for tenant = " + tenant + " and name or extension = " + nameExt);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  r.roomTypeID," +
            "  r.memberID," +
            "  r.groupID," +
            "  r.profileID," +
            "  g.groupName," +
            "  g.allowRecording," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  r.roomDescription," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomModeratorPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomKey," +
            "  r.roomModeratorKey," +
            "  m.tenantID," +
            "  rt.roomType," +
            "  m.username as ownerName," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') as numParticipants," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  r.roomMuted," +
            "  r.roomSilenced," +
            "  r.roomVideoMuted," +
            "  r.roomVideoSilenced," +
            "  r.lectureMode," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " WHERE" +
            "  m.tenantID = :tenantID"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantID", tenant);
        sqlstm.append(" AND r.roomName = :roomName");
        namedParamsMap.put("roomName", nameExt);
        sqlstm.append(" OR r.roomExtNumber = :roomExtNumber"); // NOTE OR CLAUSE!!, will ignore tenantID
        namedParamsMap.put("roomExtNumber", nameExt);

        return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
    }

    public Room getRoomByNameAndTenantId(int tenant, String name) {
        logger.debug("Get record from Room for tenant = " + tenant + " and name = " + name);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  r.roomTypeID," +
            "  r.memberID," +
            "  r.groupID," +
            "  r.profileID," +
            "  g.groupName," +
            "  g.allowRecording," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  r.roomDescription," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomModeratorPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomKey," +
            "  r.roomModeratorKey," +
            "  m.tenantID," +
            "  rt.roomType," +
            "  m.username as ownerName," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') as numParticipants," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  r.roomMuted," +
            "  r.roomSilenced," +
            "  r.roomVideoMuted," +
            "  r.roomVideoSilenced," +
            "  r.lectureMode," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " WHERE" +
            "  m.tenantID = :tenantID"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantID", tenant);
        sqlstm.append(" AND (r.roomName = :roomName");
        namedParamsMap.put("roomName", name);
        sqlstm.append(" OR r.roomExtNumber = :roomExtNumber)");
        namedParamsMap.put("roomExtNumber", name);

        return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
    }

    public Room getRoomForKey(int tenant, String key) {
        Room rc = null;
        logger.debug("Get record from Room for tenant = " + tenant + " and key = " + key);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  r.roomTypeID," +
            "  r.memberID," +
            "  r.groupID," +
            "  r.profileID," +
            "  g.groupName," +
            "  g.allowRecording," +
            "  r.roomName," +
            "  r.displayName," +
            "  r.roomExtNumber," +
            "  r.roomDescription," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomModeratorPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomKey," +
            "  r.roomModeratorKey," +
            "  m.tenantID," +
            "  rt.roomType," +
            "  m.username as ownerName," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') as numParticipants," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  r.roomMuted," +
            "  r.roomSilenced," +
            "  r.roomVideoMuted," +
            "  r.roomVideoSilenced," +
            "  r.lectureMode," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " WHERE" +
            "  m.tenantID = ?" +
            " AND" +
            " r.roomKey COLLATE utf8_bin = ?"
        );

        try {
            rc = getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Room.class), tenant, key);
        } catch (Exception ignored) {
        }
        return rc;
    }

    public Room getRoomForModeratorKey(int tenant, String key) {
        Room rc = null;
        logger.debug("Get record from Room for tenant = " + tenant + " and key = " + key);

        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                        "  r.roomID," +
                        "  r.roomTypeID," +
                        "  r.memberID," +
                        "  r.groupID," +
                        "  r.profileID," +
                        "  g.groupName," +
                        "  g.allowRecording," +
                        "  r.roomName," +
                        "  r.displayName," +
                        "  r.roomExtNumber," +
                        "  r.roomDescription," +
                        "  r.roomPIN," +
                        "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
                        "  r.roomModeratorPIN," +
                        "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
                        "  r.roomLocked," +
                        "  r.roomEnabled," +
                        "  r.roomKey," +
                        "  r.roomModeratorKey," +
                        "  m.tenantID," +
                        "  rt.roomType," +
                        "  m.username as ownerName," +
                        "  t.tenantPrefix," +
                        "  t.tenantName," +
                        "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
                        " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') as numParticipants," +
                        "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
                        "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
                        "  r.roomMuted," +
                        "  r.roomSilenced," +
                        "  r.roomVideoMuted," +
                        "  r.roomVideoSilenced," +
                        "  r.lectureMode," +
                        "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
                        " FROM" +
                        "  Room r" +
                        " INNER JOIN Member m ON (r.memberID=m.memberID)" +
                        " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
                        " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
                        " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
                        " WHERE" +
                        "  m.tenantID = ?" +
                        " AND" +
                        "  r.roomModeratorKey = ?"
        );

        try {
            rc = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Room.class), tenant, key);
        } catch (Exception ignored) {
        }
        return rc;
    }

    public int updateRoom(int tenant, int roomID, Room room) { 	
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        if (room.getDisplayName() == null || room.getDisplayName().isEmpty()){
            room.setDisplayName(room.getRoomName());
        }

        room.setRoomID(roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomTypeID = :roomTypeID," +
            "  r.memberID = :memberID," +
            "  r.groupID = :groupID," +
            "  r.roomName = :roomName," +
            "  r.displayName = :displayName," +
            "  r.roomExtNumber = :roomExtNumber," +
            "  r.roomDescription = :roomDescription,"
        );

        if (room.getPinSetting() != null) {
            if (room.getPinSetting().equalsIgnoreCase("enter")) {
                sqlstm.append("  r.roomPIN = :roomPIN,");
            } else if (room.getPinSetting().equalsIgnoreCase("remove")) {
                sqlstm.append("  r.roomPIN = NULL,");
            } else if (room.getPinSetting().equalsIgnoreCase("leave")) {
                // do not touch the roomPIN field in this case
            }
        }

        if (room.getModeratorPinSetting() != null) {
            if (room.getModeratorPinSetting().equalsIgnoreCase("enter")) {
                sqlstm.append("  r.roomModeratorPIN = :roomModeratorPIN,");
            } else if (room.getModeratorPinSetting().equalsIgnoreCase("remove")) {
                sqlstm.append("  r.roomModeratorPIN = NULL,");
            } else if (room.getModeratorPinSetting().equalsIgnoreCase("leave")) {
                // do not touch the roomModeratorPIN field in this case
            }
        }

        sqlstm.append(
                "  r.roomLocked = :roomLocked," +
                        "  r.roomEnabled = :roomEnabled," +
                        "  r.roomKey = :roomKey" +
                        " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(room);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return roomID;
    }

    @Override
    public int updatePublicRoomDescription(int roomID, String roomDescription) {
        StringBuffer sqlstm = new StringBuffer();
        sqlstm.append(" UPDATE")
        .append("  Room r" )
        .append(" SET")
        .append("  r.roomDescription = :roomDescription")
        .append(" Where r.roomID = :roomID");

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("roomDescription", roomDescription)
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), paramSource);
    }
    
    public static final String GET_ROOM_ID_FOR_EXTERNAL_ROOM_ID = "SELECT roomID from Room WHERE externalRoomID = :externalRoomID";
    
    @Override
    public int getRoomIdForExternalRoomId(String externalRoomId) {
    	int roomID = 0;
    	Map<String, Object> params = new HashMap<>();
    	params.put("externalRoomID", externalRoomId);
    
    	try {
    		roomID = namedParamJdbcTemplate.queryForObject(GET_ROOM_ID_FOR_EXTERNAL_ROOM_ID, params, Integer.class);
    	} catch (DataAccessException dae) {
    		// info logging, most of the time we will get 0 results
    		logger.info("Exception (for externalRoomId: " + externalRoomId + ") getting room ID: " + dae.getMessage());
    	}
    	return roomID;
    }

    @Override
    public int insertRoom(int tenant, Room room) {

        // Check if display name is null or empty. If so, update it with room name.
        // (For backward compatibility support)
        if (room.getDisplayName() == null || room.getDisplayName().isEmpty()){
            room.setDisplayName(room.getRoomName());
        }

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Room")
            .usingGeneratedKeyColumns("roomID");

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(room);
        Number newID = insert.executeAndReturnKey(parameters);

        //increase the room Counter for extension length
        updateRoomCounter(tenant, room.getRoomExtNumber());

        logger.debug("New roomID = " + newID.intValue());

        return newID.intValue();
    }

    private void updateRoomCounter(int tenantId, String roomExtn) {
        TenantConfiguration tenantConfiguration = tenantConfigurationDao.getTenantConfiguration(tenantId);
        int length = tenantConfiguration.getExtnLength();
        //update the room count in the cache for only the room extension length equals to extnLength configured in TenantConfiguration.
        if ( ( roomExtn.length() - tenantDao.getTenant(tenantId).getTenantPrefix().length() )  == length ) {
            Integer count = getRoomCountByExtnLength(tenantId, roomExtn.length());
            //update the counter and set in cache.
            cacheAdministrationService.updateCacheByName("roomCountCache", tenantId, ++count);
        }
    }

    @Override
    public int getRoomCountByExtnLength(int tenantId, int tenantExtensionLength){
        Integer count = (Integer)cacheAdministrationService.getValueFromCache("roomCountCache", tenantId);
        if(count == null){
           count = getCountByExtensionLength(tenantId, tenantExtensionLength);
           //also update the cache so next time the counter will be from the cache.
           cacheAdministrationService.updateCacheByName("roomCountCache", tenantId, count);
        }
        return count;
    }

    @Override
    public void clearRoomCounterCache(int tenantId) {
        logger.info("Clearing the roomCounterCache for tenantId " + tenantId);
        cacheAdministrationService.clearCache("roomCountCache");
    }

    @Transactional
    public int deleteRoom(int tenant, int roomID) {
        logger.debug("Remove record from Room for roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Room" +
            " WHERE" +
            "  (roomID = ?)"
        );

        String detachGroupFromRoomQuery = "delete from room_user_group where room_id=?";
        int dereferenced = getJdbcTemplate().update(detachGroupFromRoomQuery, roomID);
        logger.info("Dereferenced all usergroups from RoomId :"+roomID+" No. of records deleted :" +dereferenced);
        int affected = getJdbcTemplate().update(sqlstm.toString(), roomID);
        logger.debug("Rows affected: " + affected);

        return roomID;
    }

    public int lockRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomLocked = :roomLocked" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomLocked", 1)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int unlockRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomLocked = :roomLocked" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomLocked", 0)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int muteRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomMuted = :roomMuted" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomMuted", 1)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int unmuteRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomMuted = :roomMuted" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomMuted", 0)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int silenceRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomSilenced = :roomSilenced" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomSilenced", 1)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int unsilenceRoom(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomSilenced = :roomSilenced" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("roomSilenced", 0)
            .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int muteRoomVideo(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                        "  Room r" +
                        " SET" +
                        "  r.roomVideoMuted = :roomVideoMuted" +
                        " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roomVideoMuted", 1)
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int unmuteRoomVideo(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                        "  Room r" +
                        " SET" +
                        "  r.roomVideoMuted = :roomVideoMuted" +
                        " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roomVideoMuted", 0)
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int silenceRoomVideo(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                        "  Room r" +
                        " SET" +
                        "  r.roomVideoSilenced = :roomVideoSilenced" +
                        " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roomVideoSilenced", 1)
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int unsilenceRoomVideo(int tenant, int roomID) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                        "  Room r" +
                        " SET" +
                        "  r.roomVideoSilenced = :roomVideoSilenced" +
                        " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roomVideoSilenced", 0)
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public int resetRoomState(int roomID) {
        logger.debug("Update record in Room for roomID = " + roomID);

        ;

        StringBuffer sqlstm = new StringBuffer(
                "UPDATE "+
                "  Room r, "+
                "  Member m, "+
                "  TenantConfiguration c "+
                " SET "+
                "  r.roomMuted = c.waitingRoomsEnabled, "+
                "  r.roomSilenced = 0," +
                "  r.roomVideoMuted = 0," +
                "  r.roomVideoSilenced = 0," +
                "  r.roomSpeakerMuted = 0," +
                "  r.lectureMode = c.waitingRoomsEnabled "+
                " WHERE "+
                "  r.roomID = :roomID "+
                "  AND r.memberID = m.memberID "+
                "  AND m.tenantID = c.tenantID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("roomID", roomID);

        return namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
    }

    public boolean isRoomExistForRoomName(int tenant, String roomName, int roomID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (m.tenantID = :tenantID and r.memberID = m.memberID)" +
            " WHERE" +
            "  r.roomName = :roomName"
        );

        if (roomID != 0) {
            sqlstm.append(" AND r.roomID <> ").append(String.valueOf(roomID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenant));
        params.put("roomName", roomName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isRoomExistForRoomExtNumber(String roomExtNumber, int roomID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Room r" +
            " WHERE" +
            " r.roomExtNumber = :roomExtNumber"
        );

        if (roomID != 0) {
            sqlstm.append(" AND r.roomID <> ").append(String.valueOf(roomID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("roomExtNumber", roomExtNumber);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isExtensionExistForLegacyExtNumber(String roomExtNumber, int roomID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Room r" +
            " WHERE" +
            "  r.roomExtNumber = :roomExtNumber"
        );

        if (roomID != 0) {
            sqlstm.append(" AND r.roomID <> ").append(String.valueOf(roomID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("roomExtNumber", roomExtNumber);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public List<Long> getRoomsForOwnerID(int tenant, int ownerID) {
        List<Long> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " WHERE" +
            "  m.tenantID = ?" +
            " AND" +
            "  r.memberID = ?"
        );

        rc = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }, tenant, ownerID
        );

        return rc;
    }

    public List<Entity> getRoomEntitiesForOwnerID(int tenant, int ownerID) {
        List<Entity> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  m.memberID," +
            "  m.username," +
            "  md.modeID," +
            "  md.modeName," +
            "  r.roomName," +
            "  CASE WHEN rt.roomTypeID = 1 OR rt.roomTypeID = 3 THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  r.roomDescription," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomLocked," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = ? THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  lg.langCode," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video," +
            "  'true' as canControlMeeting" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = ?)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=1 AND m.allowedToParticipate=1)" +
            " LEFT JOIN Endpoints e ON (e.memberID=m.memberID)" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) " +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN MemberMode md ON (md.modeID=m.modeID)" +
            " WHERE" +
            "  m.tenantID = ?" +
            " AND" +
            "  r.roomEnabled = 1" +
            " AND" +
            "  r.roomTypeID in (1,2)" +
            " AND" +
            "  r.memberID = ?"
        );

        rc = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Entity.class), ownerID, ownerID, tenant, ownerID);

        return rc;
    }

    public Room getPersonalRoomForOwnerID(int tenant, int ownerID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID = m.memberID AND r.roomTypeID = 1)" +
            " WHERE" +
            "  m.tenantID = :tenantId" +
            " AND" +
            "  r.memberID = :memberId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", ownerID);
        int roomID = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);

        return this.getRoom(tenant, roomID);
    }

    public Room getPersonalOrLegacyRoomForOwnerID(int tenant, int ownerID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID = m.memberID AND r.roomTypeID IN (:personal, :legacy))" +
            " WHERE" +
            "  m.tenantID = :tenantId" +
            " AND" +
            "  r.memberID = :memberId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", ownerID);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("legacy", 3);
        int roomID = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);

        return this.getRoom(tenant, roomID);
    }

    /*public List<Browse> getBrowse(int tenant, BrowseFilter filter, User user) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        List<Browse> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  m.memberID," +
            "  CASE WHEN rt.roomTypeID = 1 OR rt.roomTypeID = 3 THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn" +
            " FROM  " +
            "  Room r" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = :memberId)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=1 AND m.allowedToParticipate=1)" +
            " LEFT JOIN Endpoints e ON (e.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " WHERE" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", userID);
        if (filter != null) {
            //String query = filter.getNameext();
            String query = filter.getNameext().trim().replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                // filter for non-offline
                sqlstm.append(" AND e.status <> 0 ");
                sqlstm.append(" ORDER BY ").append("memberStatus = 1, ").append(filter.getSort()).append(" ").append(filter.getDir());
            } else {
                if (!query.contains(" ")) {
                    sqlstm.append(" AND (((m.memberName LIKE :memberName");
                    namedParamsMap.put("memberName", query+"%");
                } else {
                    query = query.replace(" ", ".*\\\\ ") ;
                    sqlstm.append(" AND (((m.memberName REGEXP '^").append(query).append("'");
                }
                sqlstm.append(") AND r.roomTypeID IN (1,3))");

                sqlstm.append(" OR r.roomName LIKE :nameExt ");
                sqlstm.append(" AND r.roomEnabled = 1");
                sqlstm.append(" OR r.roomExtNumber LIKE :nameExt )");
                namedParamsMap.put("nameExt", filter.getNameext()+"%");

                if (!filter.getType().equalsIgnoreCase("All")) {
                    sqlstm.append(" AND rt.roomType = :roomType");
                    namedParamsMap.put("roomType", filter.getType());
                }

                if (filter.getSort().equalsIgnoreCase("memberStatusText")) {
                    sqlstm.append(" ORDER BY ").append("memberStatus").append(" ").append(filter.getDir());
                } else {
                    sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
                }
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Browse.class));
        return rc;
    }

    public Long getCountBrowse(int tenant, BrowseFilter filter, User user) {

        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM  " +
            "  Room r" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = :memberId)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=1 AND m.allowedToParticipate=1)" +
            " LEFT JOIN Endpoints e ON (e.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " WHERE" +
            "  m.memberID =:memberId" +
            " OR" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)"
        );
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberId", userID);
        namedParamsMap.put("tenantId", tenant);

        if (filter != null) {
           // String query = filter.getNameext();
            String query = filter.getNameext().trim().replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                // filter for non-offline
                sqlstm.append(" AND e.status <> 0 )");
            } else {
                if (!query.contains(" ")) {
                    sqlstm.append(" AND (((m.memberName LIKE :memberName");
                    namedParamsMap.put("memberName", query+"%");
                } else {
                    query = query.replace(" ", ".*\\\\ ") ;
                    sqlstm.append(" AND (((m.memberName REGEXP '^").append(query).append("'");
                }
                sqlstm.append(") AND r.roomTypeID IN (1,3))");
                sqlstm.append(" OR r.roomName LIKE :nameExt");
                sqlstm.append(" AND r.roomEnabled = 1");
                sqlstm.append(" OR r.roomExtNumber LIKE :nameExt )");
                namedParamsMap.put("nameExt", filter.getNameext()+"%");
                if (!filter.getType().equalsIgnoreCase("All")) {
                    sqlstm.append(" AND rt.roomType = :roomType ");
                    namedParamsMap.put("roomType", filter.getType());
                }
            }
        }

        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }*/

    public int updateRoomPIN(int tenant, Room room) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + room.getRoomID());

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET"
        );
        if (room.getPinSetting().equalsIgnoreCase("enter")) {
            sqlstm.append("  r.roomPIN = :roomPIN");
        } else if (room.getPinSetting().equalsIgnoreCase("remove")) {
            sqlstm.append("  r.roomPIN = NULL");
        } else if (room.getPinSetting().equalsIgnoreCase("leave")) {
            // do not touch the roomPIN field in this case
            sqlstm.append("  r.roomPIN = r.roomPIN");
        }
        sqlstm.append(
                " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(room);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return room.getRoomID();
    }

    public int updateRoomKey(int tenant, Room room) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + room.getRoomID());

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET"
        );
        if (room.getRoomKey() == null) {
            sqlstm.append("  r.roomKey = NULL");
        } else {
            sqlstm.append("  r.roomKey = :roomKey");
        }
        sqlstm.append(
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(room);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return room.getRoomID();
    }

    public int updateModeratorKey(int tenant, Room room) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + room.getRoomID());

        StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                        "  Room r" +
                        " SET"
        );
        if (room.getRoomModeratorKey() == null) {
            sqlstm.append("  r.roomModeratorKey = NULL");
        } else {
            sqlstm.append("  r.roomModeratorKey = :roomModeratorKey");
        }
        sqlstm.append(
                " WHERE" +
                        "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(room);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return room.getRoomID();
    }

    public List<Entity> getEntityByOwnerID(int tenant, EntityFilter filter, User user, int ownerID) {
        //return getEntities(tenant, filter, user, false, null, ownerID);
        return getEntitiesByOwnerId(filter, ownerID, user);
    }

    public Long getCountEntityByOwnerID(int tenant, EntityFilter filter, User user, int ownerID) {
        //return getCountEntity(tenant, filter, user, false, null, ownerID);
        return new Long(getCountForSearchByEntityId(filter, ownerID, user));
    }

    public List<Entity> getEntityByEmail(int tenant, EntityFilter filter, User user, String emailAddress) {
        return getEntities(tenant, filter, user, false, emailAddress, 0);
    }

    public Long getCountEntityByEmail(int tenant, EntityFilter filter, User user, String emailAddress) {
        return getCountEntity(tenant, filter, user, false, emailAddress, 0);
    }

    public List<Entity> getEntities(int tenant, final EntityFilter filter, User user, boolean excludeRooms, String emailAddress, int ownerID) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        final List<Entity> entities;

        // Step 1 - get records from Room, Member and static reference tables - Groups, RoomType, Language, Tenants, MemberMode
        StringBuffer BASE_QUERY = new StringBuffer();

        BASE_QUERY.append(
            " SELECT * from ( SELECT " +
            "  r.roomID," +
            "  m.memberID," +
            "  m.username," +
            "  m.emailAddress, " +
            "  md.modeID," +
            "  md.modeName," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END, " +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE e.status END as memberStatus, " +
            "  r.roomname," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  r.roomDescription," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  CASE WHEN r.memberID =:memberId THEN r.roomKey ELSE NULL END AS roomKey," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner," +
            "  lg.langID," +
            "  lg.langCode," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  COALESCE(mt.tenantDialIn, '') as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Groups g ON g.groupID=r.groupID" +
            " INNER JOIN Member m ON (m.memberID=r.memberID AND m.active = :active AND m.allowedToParticipate = :allow)" +
            " INNER JOIN RoomType rt ON (rt.roomTypeID=r.roomTypeID"
        );

        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy OR r.roomTypeID = :public))");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :personal))");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :public))");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :legacy))");
            }
            // for * users with Online status
            if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                BASE_QUERY.append(" INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline)");
            } else {
                BASE_QUERY.append(" LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline)");
            }
        } else { // for searchByEntityID
            BASE_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
            BASE_QUERY.append(" LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline)");
        }

        BASE_QUERY.append(
            " INNER JOIN Language lg ON lg.langID=m.langID" +
            " INNER JOIN Tenant mt ON mt.tenantID=m.tenantID" +
            " INNER JOIN MemberMode md ON md.modeID=m.modeID" +
            " WHERE" +
            " m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)" +
            " AND r.roomEnabled = :roomEnabled"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberId", userID);
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        namedParamsMap.put("roomEnabled", 1);
        if (emailAddress != null) { // for search by email
            BASE_QUERY.append(" AND m.emailAddress = :emailAddress");
            namedParamsMap.put("emailAddress", emailAddress);
        }
        if (ownerID != 0) { // for search by ownerID
            BASE_QUERY.append(" AND r.memberID = :ownerId");
            namedParamsMap.put("ownerId", ownerID);
        }

        if (filter != null) {
            String query = filter.getQuery().trim().replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                if(excludeRooms){
                       BASE_QUERY.append(" AND r.roomTypeID <> :public) AS ALIASNAME");
                } else {
                    BASE_QUERY.append(") AS ALIASNAME");
                }

                BASE_QUERY.append(" WHERE roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
                namedParamsMap.put("userId", userID);

            } else {
                if (!query.contains(" ")) {
                    BASE_QUERY.append("  AND (m.memberName LIKE :memberName");
                    namedParamsMap.put("memberName", query+"%");
                    BASE_QUERY.append(" OR m.memberName LIKE :memberName1)");
                    namedParamsMap.put("memberName1", "% "+query+"%");
                } else {
                    query = query.replace(" ", ".*\\ ") ;
                    //BASE_QUERY.append(" AND (m.memberName REGEXP '^").append(query).append("')");
                    BASE_QUERY.append(" AND (m.memberName REGEXP CONCAT('^', :regExQuery)) ");
                    namedParamsMap.put("regExQuery", query);
                }

                if(!excludeRooms){
                    StringBuffer ROOM_NAME_QUERY = new StringBuffer("SELECT r.roomID, m.memberID, m.username, m.emailAddress, md.modeID, md.modeName, CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END" +
                            ", CASE WHEN e.status IS NULL THEN 0 ELSE e.status END as memberStatus, r.roomname, " +
                        "CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as " +
                        "name, r.roomExtNumber as ext,  r.roomDescription,  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned,  " +
                        "r.roomPIN, CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned, r.roomModeratorPIN, " +
                        "CASE WHEN r.memberID =:memberId THEN r.roomKey ELSE NULL END AS roomKey, " +
                        "r.roomLocked,  r.roomEnabled,  r.roomTypeID,  rt.roomType,  CASE WHEN r.memberID = :memberId " +
                        "THEN 1 ELSE 0 END as roomOwner, lg.langID, lg.langCode,  mt.tenantID,  mt.tenantName,  mt.tenantUrl,  COALESCE(mt.tenantDialIn, '') " +
                        "as dialIn,  g.roomMaxUsers,  g.userMaxBandWidthIn,  g.userMaxBandWidthOut,  g.allowRecording FROM  Room r INNER JOIN " +
                        "Groups g ON g.groupID=r.groupID INNER JOIN Member m ON (m.memberID=r.memberID AND m.active = :active AND m.allowedToParticipate = :allow) INNER JOIN RoomType rt ON rt.roomTypeID=r.roomTypeID ");
                        if (filter.getEntityType().equalsIgnoreCase("All")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy OR r.roomTypeID = :public)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :personal)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :public)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :legacy)");
                        }
                        if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                            ROOM_NAME_QUERY.append(" INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                        } else {
                            ROOM_NAME_QUERY.append(" LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                        }
                        ROOM_NAME_QUERY.append("INNER JOIN Language lg ON lg.langID=m.langID INNER JOIN Tenant mt ON mt.tenantID=m.tenantID INNER JOIN MemberMode md ON md.modeID=m.modeID " +
                        "WHERE  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId) AND r.roomEnabled = :roomEnabled");
                    if (emailAddress != null) { // for search by email
                        ROOM_NAME_QUERY.append(" AND m.emailAddress = :emailAddress");
                    }
                    if (ownerID != 0) { // for search by ownerID
                        ROOM_NAME_QUERY.append(" AND r.memberID = :ownerId");
                    }
                    ROOM_NAME_QUERY.append(" AND r.roomName LIKE :roomName");
                    namedParamsMap.put("roomName", query+"%");
                    if (!filter.getEntityType().equalsIgnoreCase("All")) {
                        ROOM_NAME_QUERY.append(" AND rt.roomType = :roomType");
                        namedParamsMap.put("roomType", filter.getEntityType());
                    }

                    BASE_QUERY.append(" UNION ").append(ROOM_NAME_QUERY);
                }

                StringBuffer ROOM_EXTN_QUERY = new StringBuffer("SELECT r.roomID, m.memberID, m.username, m.emailAddress, md.modeID, md.modeName, CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END" +
                            ", CASE WHEN e.status IS NULL THEN 0 ELSE e.status END as memberStatus, r.roomname, " +
                    "CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as " +
                    "name, r.roomExtNumber as ext,  r.roomDescription,  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned,  " +
                    "r.roomPIN, CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned, r.roomModeratorPIN, " +
                    "CASE WHEN r.memberID =:memberId THEN r.roomKey ELSE NULL END AS roomKey, " +
                    "r.roomLocked,  r.roomEnabled,  r.roomTypeID,  rt.roomType,  CASE WHEN r.memberID = :memberId " +
                    "THEN 1 ELSE 0 END as roomOwner, lg.langID, lg.langCode,  mt.tenantID,  mt.tenantName,  mt.tenantUrl,  COALESCE(mt.tenantDialIn, '') " +
                    "as dialIn,  g.roomMaxUsers,  g.userMaxBandWidthIn,  g.userMaxBandWidthOut,  g.allowRecording FROM  Room r INNER JOIN " +
                    "Groups g ON g.groupID=r.groupID INNER JOIN Member m ON (m.memberID=r.memberID AND m.active = :active AND m.allowedToParticipate = :allow) INNER JOIN RoomType rt ON rt.roomTypeID=r.roomTypeID ");
                    if (filter.getEntityType().equalsIgnoreCase("All")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy OR r.roomTypeID = :public)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :personal)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :public)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :legacy)");
                    }
                    if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                        ROOM_EXTN_QUERY.append(" INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                    } else {
                        ROOM_EXTN_QUERY.append(" LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                    }
                    ROOM_EXTN_QUERY.append("INNER JOIN Language lg ON lg.langID=m.langID INNER JOIN Tenant mt ON mt.tenantID=m.tenantID INNER JOIN MemberMode md ON md.modeID=m.modeID " +
                    "WHERE  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId) AND r.roomEnabled = :roomEnabled");
                if (emailAddress != null) { // for search by email
                    ROOM_EXTN_QUERY.append(" AND m.emailAddress = :emailAddress");
                }
                if (ownerID != 0) { // for search by ownerID
                    ROOM_EXTN_QUERY.append(" AND r.memberID = :ownerId");
                }
                ROOM_EXTN_QUERY.append(" AND r.roomExtNumber LIKE :roomExtNumber ");
                namedParamsMap.put("roomExtNumber", query+"%");
                if (!filter.getEntityType().equalsIgnoreCase("All")) {
                    ROOM_EXTN_QUERY.append(" AND rt.roomType = :roomType");
                    namedParamsMap.put("roomType", filter.getEntityType());
                }
                BASE_QUERY.append(" UNION ").append(ROOM_EXTN_QUERY).append(") AS ALIASNAME");

                BASE_QUERY.append(" WHERE roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
                namedParamsMap.put("userId", userID);

                if (!filter.getSortBy().equalsIgnoreCase("")) {
                    if (filter.getSortBy().equalsIgnoreCase("memberStatusText")) {
                        BASE_QUERY.append(" ORDER BY ").append("memberStatus").append(" ").append(filter.getDir());
                    } else {
                        BASE_QUERY.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
                    }
                }
            }
            BASE_QUERY.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        } else {
            BASE_QUERY.append(") AS ALIASNAME");

            BASE_QUERY.append(" WHERE roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
            namedParamsMap.put("userId", userID);
        }

        entities = namedParamJdbcTemplate.query(BASE_QUERY.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));

        // Step 2 - update results from step 1 to values from Endpoints table
        /*StringBuffer sqlstmStep2 = new StringBuffer(
            " SELECT" +
            "  COALESCE(e.endpointID, 0) as endpointID," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus" +
            " FROM" +
            "  Endpoints e" +
            " WHERE" +
            "  e.memberID=? " +
            " AND " +
            "  e.memberType = 'R'"
        );*/

        // Step 3 - update results from step 1 to values from SpeedDial table
        StringBuffer sqlstmStep3 = new StringBuffer(
            " SELECT" +
            "  COALESCE(sd.speedDialID, 0) as speedDialID" +
            " FROM" +
            "  SpeedDial sd" +
            " WHERE" +
            "  sd.roomID = ?" +
            " AND" +
            "  sd.memberID = ?"
        );
        final Iterator<Entity> it3 = entities.iterator();
        while (it3.hasNext()) {
            final Entity e = it3.next();
            try {
                getJdbcTemplate().queryForObject(sqlstmStep3.toString(),
                    new RowMapper<Object>() {
                        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                            e.setSpeedDialID(rs.getInt("speedDialID"));
                            return null;
                        }
                    }, e.getRoomID(), userID
                );
            } catch (Exception ignored){
                logger.error(ignored.getMessage());
            }
        }

        // Step 4 - update results from step 1 to values from Conferences table
        StringBuffer sqlstmStep4 = new StringBuffer(
            " SELECT" +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=? AND c.conferenceType='C' AND c.endpointType != 'R') = ? THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=? AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  COALESCE(c.audio, 0) as audio," +
            "  COALESCE(c.video, 0) as video" +
            " FROM" +
            "  Conferences c" +
            " WHERE" +
            "  c.roomID = ?"
        );
        final Iterator<Entity> it4 = entities.iterator();
        while (it4.hasNext()) {
            final Entity e = it4.next();
            try {
                getJdbcTemplate().queryForObject(sqlstmStep4.toString(),
                    new RowMapper<Object>() {
                        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                            e.setRoomStatus(rs.getInt("roomStatus"));
                            e.setAudio(rs.getInt("audio"));
                            e.setVideo(rs.getInt("video"));
                            return null;
                        }
                    }, e.getRoomID(), e.getRoomMaxUsers(), e.getRoomID(), e.getRoomID()
                );
            } catch (Exception ignored){
                logger.error(ignored.getMessage());
            }
        }

        return entities;
    }

    public Long getCountEntity(int tenant, EntityFilter filter, User user, boolean excludeRooms, String emailAddress, int ownerID) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        StringBuffer BASE_QUERY = new StringBuffer();

        BASE_QUERY.append(
            " SELECT COUNT(0) FROM (" +
            " SELECT " +
            "  r.roomID" +
            " FROM" +
            "  Room r" +
            "  INNER JOIN Groups g ON g.groupID=r.groupID" +
            "  INNER JOIN Member m ON (m.memberID=r.memberID AND m.active = :active AND m.allowedToParticipate = :allow)" +
            "  INNER JOIN RoomType rt ON (rt.roomTypeID=r.roomTypeID"
        );

        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy))");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :personal))");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :public))");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                BASE_QUERY.append(" AND (r.roomTypeID = :legacy))");
            }
            // for * users with Online status
            if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                BASE_QUERY.append(" INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline)");
            }
        } else { // for searchByEntityID
            BASE_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
        }

        BASE_QUERY.append(
            " INNER JOIN Language lg ON lg.langID=m.langID" +
            " INNER JOIN Tenant mt ON mt.tenantID=m.tenantID" +
            " INNER JOIN MemberMode md ON md.modeID=m.modeID" +
            " WHERE" +
            " m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)" +
            " AND r.roomEnabled = :roomEnabled"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberId", userID);
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        namedParamsMap.put("roomEnabled", 1);
        if (emailAddress != null) { // for search by email
            BASE_QUERY.append(" AND m.emailAddress = :emailAddress");
            namedParamsMap.put("emailAddress", emailAddress);
        }
        if (ownerID != 0) { // for search by ownerID
            BASE_QUERY.append(" AND r.memberID = :ownerId");
            namedParamsMap.put("ownerId", ownerID);
        }

        if (filter != null) {
            String query = filter.getQuery().trim().replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                if (excludeRooms) {
                    BASE_QUERY.append(" AND r.roomTypeID <> 2) AS ALIASNAME");
                } else {
                    BASE_QUERY.append(") AS ALIASNAME");
                }
            } else {
                if (!query.contains(" ")) {
                    BASE_QUERY.append("  AND ( m.memberName LIKE :memberName");
                    namedParamsMap.put("memberName", query+"%");
                    BASE_QUERY.append(" OR m.memberName LIKE :memberName1)");
                    namedParamsMap.put("memberName1", "% "+query+"%");
                } else {
                    query = query.replace(" ", ".*\\ ") ;
                    //BASE_QUERY.append(" AND (m.memberName REGEXP '^").append(query).append("')");
                    BASE_QUERY.append(" AND (m.memberName REGEXP CONCAT('^', :regExQuery)) ");
                    namedParamsMap.put("regExQuery", query);
                }

                if(!excludeRooms){
                    StringBuffer ROOM_NAME_QUERY = new StringBuffer("SELECT r.roomID FROM Room r INNER JOIN Groups g ON g.groupID=r.groupID " +
                        "INNER JOIN Member m ON (r.memberID=m.memberID AND m.active = :active AND m.allowedToParticipate = :allow) " +
                        "INNER JOIN RoomType rt ON rt.roomTypeID=r.roomTypeID ");
                        if (filter.getEntityType().equalsIgnoreCase("All")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy OR r.roomTypeID = :public)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :personal)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :public)");
                        } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                            ROOM_NAME_QUERY.append(" AND (r.roomTypeID = :legacy)");
                        }
                        if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                            ROOM_NAME_QUERY.append("INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                        }
                        ROOM_NAME_QUERY.append("INNER JOIN Language lg ON m.langID=lg.langID " +
                        "INNER JOIN Tenant mt ON mt.tenantID=m.tenantID " +
                        "WHERE  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId) " +
                        "AND r.roomEnabled = :roomEnabled ");
                    if (emailAddress != null) { // for search by email
                        ROOM_NAME_QUERY.append(" AND m.emailAddress = :emailAddress");
                    }
                    if (ownerID != 0) { // for search by ownerID
                        ROOM_NAME_QUERY.append(" AND r.memberID = :ownerId");
                    }
                    ROOM_NAME_QUERY.append(" AND r.roomName LIKE :roomName");
                    namedParamsMap.put("roomName", query+"%");
                    if (!filter.getEntityType().equalsIgnoreCase("All")) {
                        ROOM_NAME_QUERY.append(" AND rt.roomType = :roomType");
                        namedParamsMap.put("roomType", filter.getEntityType());
                    }

                    BASE_QUERY.append(" UNION ").append(ROOM_NAME_QUERY);
                }

                StringBuffer ROOM_EXTN_QUERY = new StringBuffer("SELECT r.roomID FROM Room r INNER JOIN Groups g ON g.groupID=r.groupID " +
                    "INNER JOIN Member m ON (r.memberID=m.memberID AND m.active = :active AND m.allowedToParticipate = :allow) " +
                    "INNER JOIN RoomType rt ON r.roomTypeID=rt.roomTypeID ");
                    if (filter.getEntityType().equalsIgnoreCase("All")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :legacy OR r.roomTypeID = :public)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :personal)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :public)");
                    } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                        ROOM_EXTN_QUERY.append(" AND (r.roomTypeID = :legacy)");
                    }
                    if (filter.getQuery().trim().equalsIgnoreCase("*")) {
                        ROOM_EXTN_QUERY.append("INNER JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = :memberType AND e.status != :offline) ");
                    }
                    ROOM_EXTN_QUERY.append("INNER JOIN Language lg ON m.langID=lg.langID " +
                    "INNER JOIN Tenant mt ON mt.tenantID=m.tenantID " +
                    "WHERE  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId) " +
                    "AND r.roomEnabled = :roomEnabled ");
                if (emailAddress != null) { // for search by email
                    ROOM_EXTN_QUERY.append(" AND m.emailAddress = :emailAddress");
                }
                if (ownerID != 0) { // for search by ownerID
                    ROOM_EXTN_QUERY.append(" AND r.memberID = :ownerId");
                }
                ROOM_EXTN_QUERY.append(" AND r.roomExtNumber LIKE :roomExtNumber ");
                namedParamsMap.put("roomExtNumber", query+"%");
                if (!filter.getEntityType().equalsIgnoreCase("All")) {
                    ROOM_EXTN_QUERY.append(" AND rt.roomType = :roomType");
                    namedParamsMap.put("roomType", filter.getEntityType());
                }
                BASE_QUERY.append(" UNION ").append(ROOM_EXTN_QUERY).append(") AS ALIASNAME");
            }
        } else {
            BASE_QUERY.append(") AS ALIASNAME");
        }

        BASE_QUERY.append(" WHERE roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", userID);

        List<Long> ll = namedParamJdbcTemplate.query(BASE_QUERY.toString(), namedParamsMap,
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Entity getOneEntity(int tenant, int entityID, User user) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  m.memberID," +
            "  m.username," +
            "  m.emailAddress," +
            "  md.modeID," +
            "  md.modeName," +
            "  r.roomname," +
            "  CASE WHEN rt.roomTypeID = 1 OR rt.roomTypeID = 3 THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  r.roomDescription as description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomEnabled," +
            "  r.roomLocked," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = ? THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  lg.langID," +
            "  lg.langCode," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video" +
            " FROM  " +
            "  Room r" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = ?)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=1 AND m.allowedToParticipate=1)" +
            " LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = 'R')" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) " +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN MemberMode md ON (md.modeID=m.modeID)" +
            " WHERE" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = ?)" +
            " AND" +
            "  r.roomID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Entity.class), userID, userID, tenant, entityID);
    }

    /**
     * Get the Entity Details with LDAP attributes
     * @param tenant
     * @param entityID
     * @param user
     * @return
     */
    public Entity getOneEntityDetails(int tenant, int entityID, User user) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  m.memberID," +
            "  m.username," +
            "  m.emailAddress," +
            "  md.modeID," +
            "  md.modeName," +
            "  r.roomname," +
            "  CASE WHEN rt.roomTypeID = 1 OR rt.roomTypeID = 3 THEN m.memberName ELSE r.displayName END as name," +
            "  r.roomExtNumber as ext," +
            "  r.roomDescription as description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomEnabled," +
            "  r.roomLocked," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = ? THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  lg.langID," +
            "  lg.langCode," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video," +
            "  m.phone1," +
            "  m.phone2," +
            "  m.phone3," +
            "  m.department," +
            "  m.title," +
            "  m.instantMessagerID," +
            "  m.location," +
            "  m.thumbnailUpdateTime" +
            " FROM  " +
            "  Room r" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = ?)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=1 AND m.allowedToParticipate=1)" +
            " LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType = 'R')" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) " +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN MemberMode md ON (md.modeID=m.modeID)" +
            " WHERE" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = ?)" +
            " AND" +
            "  r.roomID = ?"
        );
        Entity entity = null;

        try {
            entity = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Entity.class), userID, userID, tenant, entityID);
        } catch (EmptyResultDataAccessException e) {
            // entity does not exists so keeping entity as null
        }
        return entity;
    }

    public List<RoomIdSearchResult> searchRoomIds(int tenant,
            EntityFilter filter, User user, List<Integer> tenantIds,
            boolean showDisabledRooms) {
        List<RoomIdSearchResult> idSearchResults = new ArrayList<RoomIdSearchResult>();
        List<SqlParameter> paramList = new ArrayList<SqlParameter>();
        final String procedureCall = "{CALL SearchRoomIdsAndTotalCount(?,?,?,?,?,?,?,?,?,?,?)}";
        Map<String, Object> resultMap = getJdbcTemplate().call(
                new CallableStatementCreator() {
                    @Override
                    public CallableStatement createCallableStatement(
                            Connection connection) throws SQLException {
                        // Filter based on Room Types - 1. Personal, 2. Public, 3. Legacy, 4. Scheduled
                        int roomType = 4;
                        int includeRoomType = 0;
                        if (filter != null) {
                            if (filter.getEntityType().equalsIgnoreCase("All")) {
                                // Scheduled rooms are not searchable by default
                                roomType = 4;
                                includeRoomType = 0;
                            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                                roomType = 1;
                                includeRoomType = 1;
                            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                                roomType = 2;
                                includeRoomType = 1;
                            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                                roomType = 3;
                                includeRoomType = 1;
                            }
                        }
                        String queryInput = RoomUtils.buildSearchQuery(filter.getQuery());
                        StringBuilder tenantIdList = new StringBuilder();
                        for(Integer tenantId : tenantIds) {
                            tenantIdList.append(tenantId);
                            if(tenantIds.size() > 1) {
                                tenantIdList.append(",");
                            }
                        }
                        CallableStatement callableStatement = connection.prepareCall(procedureCall);
                        callableStatement.setInt(1, filter.getStart());
                        callableStatement.setInt(2, filter.getLimit());
                        callableStatement.setString(3, tenantIdList.toString());
                        callableStatement.setInt(4, roomType);
                        callableStatement.setInt(5, includeRoomType);
                        callableStatement.setInt(6, 1);
                        callableStatement.setInt(7, 1);
                        callableStatement.setInt(8, showDisabledRooms ? 0 : 1);
                        callableStatement.setString(9, queryInput);
                        callableStatement.setString(10, filter.getSortBy() + " " + filter.getDir());
                        callableStatement.setInt(11, user.getMemberID());
                        return callableStatement;
                    }
                }, paramList);
        List<LinkedCaseInsensitiveMap<Integer>> resultSet = (List<LinkedCaseInsensitiveMap<Integer>>) resultMap.get("#result-set-1");
        if(resultSet != null && resultSet instanceof List<?>) {
            for(LinkedCaseInsensitiveMap<Integer> map : resultSet) {
                RoomIdSearchResult roomIdSearchResult = new RoomIdSearchResult();
                if(map.get("roomID") != null) {
                    roomIdSearchResult.setRoomId(map.get("roomID").intValue());
                }
                idSearchResults.add(roomIdSearchResult);
            }
        }
        List<LinkedCaseInsensitiveMap<Long>> totalCountResultSet = (List<LinkedCaseInsensitiveMap<Long>>) resultMap.get("#result-set-2");
        if(totalCountResultSet != null && totalCountResultSet instanceof List<?>) {
            for(LinkedCaseInsensitiveMap<Long> map : totalCountResultSet) {
                if(map.get("totalCount") != null && idSearchResults.size() > 0) {
                    idSearchResults.get(0).setTotalCount(map.get("totalCount").intValue());
                }
            }
        }
        return idSearchResults;
    }

    @Override
    public int findRoomsCount(int thisUserMemberID, String query, String queryField,  List<Integer> allowedTenantIds,  List<Integer>  roomTypes, boolean showDisabledRooms) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Room r LEFT JOIN Member m ON r.memberID = m.memberID" +
                " WHERE r.roomTypeID IN (:roomTypes) AND ");
        namedParamsMap.put("tenantIds", allowedTenantIds);
        namedParamsMap.put("roomTypes", roomTypes);
        if (queryField.equals("memberName")) {
            sql.append(" MATCH(m.memberName) AGAINST(:memberName in boolean mode) " );
            namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(query));
        } else if (queryField.equals("roomName")){
            sql.append(" MATCH(r.roomName,r.roomExtNumber, displayName) AGAINST(:roomNameOrExt in boolean mode) ");
            namedParamsMap.put("roomNameOrExt", RoomUtils.buildSearchQuery(query));
        } else if (queryField.equals("memberID")) {
            sql.append(" r.memberID = :memberID ");
            namedParamsMap.put("memberID", Long.parseLong(query));
        }
        sql.append(" AND m.tenantID IN (:tenantIds)");
        if (!showDisabledRooms) {
            sql.append(" AND r.roomEnabled = 1");
        }
        sql.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", thisUserMemberID);
        int total = namedParamJdbcTemplate.queryForObject(sql.toString(), namedParamsMap, Integer.class);
        return total;
    }


    @Override
    public List<RoomMini> findRooms(int thisUserMemberID, String query, String queryField, List<Integer> allowedTenantIds, List<Integer> roomTypes, int start, int limit, String sortBy, String sortDir, boolean showDisabledRooms) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT r.roomID, r.roomName, r.displayName, r.roomExtNumber, r.roomDescription," +
                " m.memberID, m.memberName," +
                " r.roomTypeID, r.roomPIN, r.roomLocked, r.roomEnabled, COALESCE(s.memberID, 0) as myContact" +
                " FROM Room r LEFT JOIN Member m ON r.memberID = m.memberID " +
                " LEFT JOIN SpeedDial s ON (s.memberID = :thisUserMemberID AND r.roomID = s.roomID)" +
                " WHERE r.roomTypeID IN (:roomTypes) AND ");
        if (queryField.equals("memberName")) {
            sql.append(" MATCH(m.memberName) AGAINST(:memberName in boolean mode) " );
            namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(query));
        } else if (queryField.equals("roomName")) {
            sql.append(" MATCH(r.roomName,r.roomExtNumber,r.displayName) AGAINST(:roomNameOrExt in boolean mode) ");
            namedParamsMap.put("roomNameOrExt", RoomUtils.buildSearchQuery(query));
        } else if (queryField.equals("memberID")) {
            sql.append(" r.memberID = :memberID ");
            namedParamsMap.put("memberID", Long.parseLong(query));
        }

        sql.append(" AND m.tenantID IN (:tenantIds)");
        if (!showDisabledRooms) {
            sql.append(" AND r.roomEnabled = 1");
        }

        namedParamsMap.put("tenantIds", allowedTenantIds);
        namedParamsMap.put("roomTypes", roomTypes);
        namedParamsMap.put("thisUserMemberID", thisUserMemberID);

        sql.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", thisUserMemberID);

        if ("DESC".equals(sortDir)) {
            sql.append(" ORDER BY ").append(sortBy).append(" DESC LIMIT ").append(start).append(",").append(limit);
        } else {
            sql.append(" ORDER BY ").append(sortBy).append(" ASC LIMIT ").append(start).append(",").append(limit);
        }
        List<RoomMini> rooms = namedParamJdbcTemplate.query(sql.toString(), namedParamsMap,
                new RowMapper<RoomMini>() {
                    public RoomMini mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        RoomMini room = new RoomMini();
                        room.setRoomID(rs.getLong("roomID"));
                        room.setExtension(rs.getString("roomExtNumber"));
                        room.setName(rs.getString("roomName"));
                        room.setDisplayName(rs.getString("displayName"));
                        room.setOwnerID(rs.getLong("memberID"));
                        room.setOwnerName(rs.getString("memberName"));
                        room.setDescription(StringUtils.trimToEmpty(rs.getString("roomDescription")));
                        int roomType = rs.getInt("roomTypeID");
                        if (roomType == 1) {
                            room.setType("personal");
                        } else if (roomType == 2) {
                            room.setType("public");
                        } else if (roomType == 3) {
                            room.setType("legacy");
                        }
                        room.setPinned(StringUtils.isNotBlank(rs.getString("roomPIN")));
                        room.setLocked(rs.getInt("roomLocked") == 1);
                        room.setEnabled(rs.getInt("roomEnabled") == 1);
                        if (rs.getInt("myContact") == 0) {
                            room.setInContacts(false);
                        } else {
                            room.setInContacts(true);
                        }
                        return room;
                    }
                });
        return rooms;

    }

    @Override
    public int getMemberIDForRoomID(int roomID, List<Integer> allowedTenantIds) {
        String sql = "SELECT m.memberID FROM Room r" +
                " LEFT JOIN Member m ON r.memberID = m.memberID" +
                " WHERE r.roomID = :roomID AND m.tenantID IN (:tenantIds)";
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantIds", allowedTenantIds);
        namedParamsMap.put("roomID", roomID);
        int memberID = 0;
        try {
            memberID = namedParamJdbcTemplate.queryForObject(sql, namedParamsMap, Integer.class);
        } catch (DataAccessException dae) {
            // ignore if we don't find it
        }
        return memberID;
    }

    /*public List<RoomIdSearchResult> searchRoomIds(int tenant, EntityFilter filter, User user,
            List<Integer> tenantIds, boolean showDisabledRooms){
        String query = filter.getQuery().trim()
                .replaceAll("\\b\\s{2,}\\b", " ");
        StringBuilder searchRoomIds = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM (SELECT roomID, roomExtNumber, CASE WHEN roomTypeID = 1 OR roomTypeID = 3 THEN memberName ELSE roomName END as name "
            + "FROM ( SELECT roomID, roomTypeID, roomName, memberName, roomExtNumber FROM Member RIGHT JOIN Room USING(memberID)");
        if(query.equalsIgnoreCase("*")) {
            searchRoomIds.append(" INNER JOIN Endpoints USING(memberID) WHERE status <> 0 AND");
        } else {
            searchRoomIds.append(" WHERE");
        }
        searchRoomIds.append(" active =:active AND allowedToParticipate =:allowed AND tenantID IN (:tenantIds)");
        if(!query.equalsIgnoreCase("*")) {
            searchRoomIds.append(" AND MATCH(memberName) AGAINST(:name in boolean mode)");
        }
        searchRoomIds.append(" AND ");
        searchRoomIds = getRoomTypes(filter, searchRoomIds);
        searchRoomIds.append(" AND roomEnabled =:roomEnabled) as innq1");
        searchRoomIds.append(" UNION");
        searchRoomIds.append(" SELECT roomID, roomExtNumber, CASE WHEN roomTypeID = 1 OR roomTypeID = 3 THEN memberName ELSE roomName END as name FROM Room LEFT JOIN Member USING(memberID)");
        if(query.equalsIgnoreCase("*")) {
            searchRoomIds.append(" INNER JOIN Endpoints USING(memberID) WHERE status <> 0 AND");
        } else {
            searchRoomIds.append(" WHERE");
        }
        searchRoomIds = getRoomTypes(filter, searchRoomIds);
        if(showDisabledRooms) {
            searchRoomIds.append(" AND roomEnabled in(:roomEnabled)");
        } else {
            searchRoomIds.append(" AND roomEnabled =:roomEnabled");
        }
        searchRoomIds.append(" AND roomEnabled =:roomEnabled");
        if(!query.equalsIgnoreCase("*")) {
            searchRoomIds.append(" AND ");
            // If only characters, query only roomName
            if(StringUtils.isAlpha(query)) {
                searchRoomIds.append(" MATCH(roomName)");
            } else {
                // If alpha numeric use roomName and roomExtn
                searchRoomIds.append(" MATCH(roomName,roomExtNumber)");
            }
            searchRoomIds.append(" AGAINST(:name in boolean mode)");
        }
        searchRoomIds.append(" AND active =:active AND allowedToParticipate =:allowed AND tenantID IN (:tenantIds)) as res,");
        searchRoomIds.append(" (SELECT FOUND_ROWS() AS 'total') tot ORDER BY");
        if(filter.getSortBy().equalsIgnoreCase("ext")) {
            searchRoomIds.append(" roomExtNumber");
        } else if(filter.getSortBy().equalsIgnoreCase("roomID")) {
            searchRoomIds.append(" roomID");
        } else {
            searchRoomIds.append(" name");
        }
        searchRoomIds.append(" ").append(filter.getDir());
        searchRoomIds.append(" LIMIT :start, :limit");
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("start", filter.getStart());
        namedParamsMap.put("limit", filter.getLimit());
        namedParamsMap.put("memberId", user.getMemberID());
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allowed", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        if(query.contains(" ")) {
            String[] queryStrings = query.split(" ");
            namedParamsMap.put("name", "+"+queryStrings[0]+"*" + " +"+queryStrings[1]+"*");
        } else {
            namedParamsMap.put("name", "*"+query+"*");
        }

        if (showDisabledRooms) {
            ArrayList<Integer> disabledRoomValues = new ArrayList<Integer>(2);
            disabledRoomValues.add(1);
            disabledRoomValues.add(0);
            namedParamsMap.put("roomEnabled", disabledRoomValues);
        } else {
            namedParamsMap.put("roomEnabled", 1);
        }

        namedParamsMap.put("tenantIds", tenantIds);
        List<RoomIdSearchResult> roomIdSearchResults = getJdbcTemplate().query(searchRoomIds.toString(),
                new RowMapper<RoomIdSearchResult>() {
                    public RoomIdSearchResult mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        RoomIdSearchResult roomIdSearchResult = new RoomIdSearchResult();
                        roomIdSearchResult.setRoomId(rs.getInt("roomID"));
                        roomIdSearchResult.setTotalCount(rs.getInt("total"));
                        return roomIdSearchResult;
                    }
                }, namedParamsMap);
        return roomIdSearchResults;
    }*/

    public StringBuilder getRoomTypes(EntityFilter filter, StringBuilder searchRoomIds) {
        // Filter based on Room Types - 1. Personal, 2. Public, 3. Legacy, 4. Scheduled
        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                // Scheduled rooms are not searchable by default
                searchRoomIds.append(" roomTypeID != 4");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                searchRoomIds.append(" roomTypeID = 1");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                searchRoomIds.append(" roomTypeID = 2");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                searchRoomIds.append(" roomTypeID = 3");
            }
        } else {
            searchRoomIds.append(" roomTypeID != 4");
        }

        return searchRoomIds;

    }

    /*public List<Integer> getEntityIDs(int tenant, EntityFilter filter, User user,
            boolean excludeRooms, List<Integer> tenantIds, boolean showDisabledRooms) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }
        List<String> roomTypes = new ArrayList<String>(3);
        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                roomTypes.add("Personal");
                roomTypes.add("Public");
                roomTypes.add("Legacy");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                roomTypes.add("Personal");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                roomTypes.add("Public");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                roomTypes.add("Legacy");
            }

        } else { // for searchByEntityID
            // If no filter, search on all types by default
            roomTypes.add("Personal");
            roomTypes.add("Public");
            roomTypes.add("Legacy");
        }

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();

        StringBuffer GET_ALL_ENTITY_IDS = new StringBuffer("SELECT r.roomID, CASE WHEN r.roomTypeID = :personal OR r.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name FROM Room r, RoomType rt, Member m LEFT JOIN Endpoints e ON e.memberID=m.memberID AND e.memberType = :memberType AND e.status <> :offline WHERE rt.roomTypeID=r.roomTypeID AND rt.roomType in (:roomTypes) AND m.memberID=r.memberID AND m.active =:active AND m.allowedToParticipate =:allowed AND m.tenantID IN (:tenantIds) AND r.roomEnabled IN (:roomEnabled) ");
        StringBuffer GET_ONLINE_ENTITY_IDS = new StringBuffer("SELECT r.roomID, CASE WHEN r.roomTypeID = :personal OR r.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name FROM Room r, RoomType rt, Member m, Endpoints e WHERE rt.roomTypeID=r.roomTypeID AND rt.roomType in (:roomTypes) AND m.memberID=r.memberID AND m.active =:active AND m.allowedToParticipate =:allowed AND m.tenantID IN (:tenantIds) AND r.roomEnabled IN (:roomEnabled) AND e.memberID=m.memberID AND e.memberType = :memberType AND e.status <> :offline ");
        if (filter != null) {
            String query = filter.getQuery().trim()
                    .replaceAll("\\b\\s{2,}\\b", " ")
                    .replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
            if(query.equalsIgnoreCase("*")) {
                GET_ALL_ENTITY_IDS = GET_ONLINE_ENTITY_IDS;
                // Remove Public Room Type when searching online users with
                if (!roomTypes.isEmpty()) {
                    roomTypes.remove("Public");
                }

            } else {
                if (!query.contains(" ")) {
                    GET_ALL_ENTITY_IDS
                            .append("  AND ( m.memberName LIKE :memberFirstName OR m.memberName LIKE :memberLastName ");
                    if (!excludeRooms) {
                        // Include room name search
                        GET_ALL_ENTITY_IDS
                                .append(" OR r.roomName LIKE :memberFirstName ");
                    }
                    GET_ALL_ENTITY_IDS
                            .append("OR r.roomExtNumber LIKE :memberFirstName)");
                    namedParamsMap.put("memberFirstName", query + "%");
                    namedParamsMap.put("memberLastName", "% " + query + "%");
                } else {
                    // Exclude room extension from search if it the query has space
                    query = query.replace(" ", ".*\\ ");
                    GET_ALL_ENTITY_IDS.append(" AND (m.memberName REGEXP CONCAT('^', :regExQuery) ")
                            .append(" OR r.roomName REGEXP CONCAT('^', :regExQuery) ")
                            .append(")");
                    namedParamsMap.put("regExQuery", query);
                }
            }
        }
        GET_ALL_ENTITY_IDS.append(" ORDER by ");
        if(filter.getSortBy().equalsIgnoreCase("ext")) {
            GET_ALL_ENTITY_IDS.append("r.roomExtNumber");
        } else if(filter.getSortBy().equalsIgnoreCase("roomID")) {
            GET_ALL_ENTITY_IDS.append("r.roomID");
        } else {
            GET_ALL_ENTITY_IDS.append("name");
        }

        //TODO - memberStatus sorting - is it still applicable?

        GET_ALL_ENTITY_IDS.append(" ").append(filter.getDir());
        GET_ALL_ENTITY_IDS.append(" LIMIT :start, :limit");
        namedParamsMap.put("start", filter.getStart());
        namedParamsMap.put("limit", filter.getLimit());
        namedParamsMap.put("memberId", userID);
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allowed", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        ArrayList<Integer> disabledRoomValues = new ArrayList<Integer>();
        disabledRoomValues.add(1);
        if (showDisabledRooms) {
            disabledRoomValues.add(0);
        }
        namedParamsMap.put("roomEnabled", disabledRoomValues);
        namedParamsMap.put("roomTypes", roomTypes);
        namedParamsMap.put("tenantIds", tenantIds);
        return namedParamJdbcTemplate.query(GET_ALL_ENTITY_IDS.toString(), namedParamsMap,
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rs.getInt(1);
                    }
                });
    }*/

    protected int getOnlineEntitiesCount(int tenant, EntityFilter filter,
            User user, List<String> roomTypes, boolean excludeRooms,
            Map<String, Object> namedParamsMap, List<Integer> tenantIds) {
        // Remove Public Room Type when searching online users with
        if (!roomTypes.isEmpty()) {
            roomTypes.remove("Public");
        }
        StringBuffer COUNT_ONLINE_ENTITY_IDS = new StringBuffer("SELECT COUNT(r.roomID) FROM Room r, RoomType rt, Member m, Endpoints e WHERE rt.roomTypeID=r.roomTypeID AND rt.roomType in (:roomTypes) AND m.memberID=r.memberID AND m.active =:active AND m.allowedToParticipate =:allowed AND m.tenantID IN (:tenantIds) AND r.roomEnabled IN (:roomEnabled) AND e.memberID=m.memberID AND e.memberType = :memberType AND e.status <> :offline ");
        List<Integer> count = namedParamJdbcTemplate.query(
                COUNT_ONLINE_ENTITY_IDS.toString(), namedParamsMap,
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rs.getInt(1);
                    }
                });

        return count.size() > 0 ? count.get(0) : 0;
    }

    /*public int getCountEntityIDs(int tenant, EntityFilter filter, User user,
            boolean excludeRooms, List<Integer> tenantIds, boolean showDisabledRooms) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }
        List<String> roomTypes = new ArrayList<String>(3);
        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                roomTypes.add("Personal");
                roomTypes.add("Public");
                roomTypes.add("Legacy");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                roomTypes.add("Personal");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                roomTypes.add("Public");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                roomTypes.add("Legacy");
            }

        } else { // for searchByEntityID
            // If no filter, search on all types by default
            roomTypes.add("Personal");
            roomTypes.add("Public");
            roomTypes.add("Legacy");
        }

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberId", userID);
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allowed", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        ArrayList<Integer> disabledRoomValues = new ArrayList<Integer>();
        disabledRoomValues.add(1);
        if (showDisabledRooms) {
            disabledRoomValues.add(0);
        }
        namedParamsMap.put("roomEnabled", disabledRoomValues);
        namedParamsMap.put("roomTypes", roomTypes);
        namedParamsMap.put("tenantIds", tenantIds);

        int count = 0;
        if (filter != null && filter.getQuery().trim().equalsIgnoreCase("*")) {
            // return online ids
            count = getOnlineEntitiesCount(tenant, filter, user,
                    roomTypes, excludeRooms, namedParamsMap, tenantIds);
        } else if(filter == null || filter != null) {
            StringBuffer COUNT_ALL_ENTITY_IDS = new StringBuffer("SELECT COUNT(r.roomID) FROM Room r, RoomType rt, Member m LEFT JOIN Endpoints e ON e.memberID=m.memberID AND e.memberType = :memberType AND e.status <> :offline WHERE rt.roomTypeID=r.roomTypeID AND rt.roomType in (:roomTypes) AND m.memberID=r.memberID AND m.active =:active AND m.allowedToParticipate =:allowed AND m.tenantID IN (:tenantIds) AND r.roomEnabled IN (:roomEnabled) ");
            if (filter != null) {
                String query = filter.getQuery().trim()
                        .replaceAll("\\b\\s{2,}\\b", " ").replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
                if (!query.contains(" ")) {
                    COUNT_ALL_ENTITY_IDS
                            .append(" AND ( m.memberName LIKE :memberFirstName OR m.memberName LIKE :memberLastName ");
                    if (!excludeRooms) {
                        // Include room name search
                        COUNT_ALL_ENTITY_IDS.append(" OR r.roomName LIKE :memberFirstName ");
                    }
                    COUNT_ALL_ENTITY_IDS
                            .append("OR r.roomExtNumber LIKE :memberFirstName)");
                    namedParamsMap.put("memberFirstName", query + "%");
                    namedParamsMap.put("memberLastName", "% " + query + "%");
                } else {
                    // Exclude room extension from search if it the query has space
                    query = query.replace(" ", ".*\\ ");
                    COUNT_ALL_ENTITY_IDS.append(" AND (m.memberName REGEXP CONCAT('^', :regExQuery) ")
                            .append(" OR r.roomName REGEXP CONCAT('^', :regExQuery) ")
                            .append(")");
                    namedParamsMap.put("regExQuery", query);                    
                }
            }

            List<Integer> countLst = namedParamJdbcTemplate.query(
                    COUNT_ALL_ENTITY_IDS.toString(), namedParamsMap,
                    new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return rs.getInt(1);
                        }
                    });

            count = countLst.size() > 0 ? countLst.get(0) : 0;
        }


        return count;
    }*/

    public List<RoomProfile> getRoomProfiles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  *" +
            " FROM" +
            "  Profile"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(RoomProfile.class));
    }

    public RoomProfile getRoomProfile(int roomID) {
        RoomProfile rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  *" +
            " FROM" +
            "  Profile p" +
            " WHERE" +
            "  p.profileID = (SELECT profileID FROM Room WHERE roomID = :roomID)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);

        try {
            rc = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap,
                    BeanPropertyRowMapper.newInstance(RoomProfile.class));
        } catch (Exception ignored) {
        }
        return rc;
    }

    public int updateRoomProfile(int roomID, String profileName) {
        logger.debug("Update record in Room for roomID = " + roomID + " and profile = " + profileName);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.profileID = (SELECT profileID FROM Profile WHERE profileName = :profileName)" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);
        namedParamsMap.put("profileName", profileName);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParamsMap);
        logger.debug("Rows affected: " + affected);

        return roomID;
    }

    public int removeRoomProfile(int roomID) {
        logger.debug("Update record in Room for roomID = " + roomID + " to set profileID = 0");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.profileID = 0" +
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParamsMap);
        logger.debug("Rows affected: " + affected);

        return roomID;
    }

    /**
     * Get the list of Entities for the RoomIds
     *
     * @param roomIDs
     * @param tenant
     * @param user
     * @param filter
     * @return
     */
    public List<Entity> getEntities(List<Integer> roomIDs, int tenant, List<Integer> tenantIds, User user, EntityFilter filter) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT"
            + "  r.roomID,"
            + "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID,"
            + "  m.memberID,"
            + "  m.username,"
            + "  m.emailAddress, "
            + "  m.modeID,"
            + "  r.roomname,"
            + "  CASE WHEN r.roomTypeID = :personal OR r.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name,"
            + "  r.roomExtNumber as ext,"
            + "  r.roomDescription,"
            + "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned,"
            + "  r.roomPIN,"
            + "  r.roomModeratorPIN,"
            + "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned,"
            + "  CASE WHEN r.memberID =:memberId THEN r.roomKey ELSE NULL END AS roomKey,"
            + "  r.roomEnabled,"
            + "  r.roomLocked,"
            + "  r.roomTypeID,"
            + "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus,"
            + "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID,"
            + "  CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner,"
            + "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType AND c.endpointType != :endpointType) = g.roomMaxUsers THEN 2"
            + "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType AND c.endpointType != :endpointType) = 0 THEN 0 ELSE 1 END as roomStatus,"
            + "  lg.langID,"
            + "  lg.langCode,"
            + "  mt.tenantID,"
            + "  mt.tenantName,"
            + "  mt.tenantUrl,"
            + "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn,"
            + "  g.roomMaxUsers,"
            + "  g.userMaxBandWidthIn,"
            + "  g.userMaxBandWidthOut,"
            + "  g.allowRecording,"
            + "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio,"
            + "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video"
            + " FROM  "
            + "  Room r"
            + " INNER JOIN Groups g ON (g.groupID=r.groupID)"
            + " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID =:memberId)"
            + " INNER JOIN Member m ON (r.memberID=m.memberID AND m.active=:active AND m.allowedToParticipate=:allow)"
            + " LEFT JOIN Endpoints e ON (e.memberID=m.memberID AND e.memberType =:memberType)"
            + " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) "
            + " INNER JOIN Language lg ON (m.langID=lg.langID)"
            + " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)"
            + " WHERE"
            + " r.roomID in (:roomIds) and m.tenantID IN (:tenantIds)"
        );

        sqlstm.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("userId", user.getMemberID());

        /*if (filter != null && !filter.getSortBy().equalsIgnoreCase("")) {
            if (filter.getSortBy().equalsIgnoreCase("memberStatusText")) {
                sqlstm.append(" ORDER BY ").append("memberStatus").append(" ").append(filter.getDir());
            } else {
                sqlstm.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
            }
        }*/
        
        sqlstm.append(" ORDER BY FIELD(r.roomID, :roomIds) ");

        namedParamsMap.put("memberId", user.getMemberID());
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("endpointType", "R");
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        namedParamsMap.put("roomEnabled", 1);
        namedParamsMap.put("roomIds", roomIDs);
        namedParamsMap.put("tenantIds", tenantIds);

        return namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
                BeanPropertyRowMapper.newInstance(Entity.class));
    }

    private static final String GET_ROOM_DETAILS_FOR_CONF = "SELECT r.roomID, r.roomName, r.groupID, r.recurring, "
            + "r.roomModeratorPIN, r.roomLocked, r.roomEnabled, r.roomPIN, r.roomMuted, r.roomSilenced, r.roomVideoMuted, r.roomVideoSilenced, r.lectureMode, r.profileID, "
            + "r.roomKey, r.roomModeratorKey, r.roomSpeakerMuted, r.roomExtNumber, "
            + "rt.roomType, m.memberID, m.memberName as ownerName, m.tenantID, t.tenantName, g.roomMaxUsers, g.userMaxBandWidthIn, g.allowRecording, "
            + "g.userMaxBandWidthOut, count(c.conferenceID) as occupantsCount FROM Room r LEFT JOIN Conferences c ON r.roomID = c.roomID and c.endpointType !=:endpointType, "
            + "RoomType rt, Member m, Groups g, Tenant t WHERE r.memberID = m.memberID "
            + "AND r.roomID =:roomId AND r.roomTypeID = rt.roomTypeID AND m.tenantID =t.tenantId AND "
            + "r.groupID = g.groupID";

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomID
     * @param tenantID
     * @return
     */
    public Room getRoomDetailsForConference(int roomID, int tenantID) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomId", roomID);
        namedParamsMap.put("tenantId", tenantID);
        // Recorder Endpoint type is "R" and it is not counted as a particpant
        namedParamsMap.put("endpointType", "R");
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_FOR_CONF, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class)
                );
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result
        return rooms.get(0);
    }

    private static final String GET_ROOM_DETAILS_FOR_CONF_BY_GATEWAY = "SELECT r.roomID, r.roomName, r.roomExtNumber, r.roomLocked, r.recurring,"
            + "r.roomModeratorPIN, r.roomLocked, r.roomEnabled, roomPIN, r.roomMuted, r.roomSilenced, r.roomVideoMuted, r.roomVideoSilenced, r.lectureMode, r.profileID, "
            + "rt.roomType, m.memberID, m.memberName as ownerName, m.tenantID, g.roomMaxUsers, g.userMaxBandWidthIn, "
            + "g.userMaxBandWidthOut, t.tenantName FROM Room r, RoomType rt, Member m, Groups g, Tenant t WHERE r.memberID = m.memberID "
            + "AND r.roomTypeID = rt.roomTypeID AND m.tenantID =t.tenantId AND r.groupID = g.groupID "
            + "AND (r.roomExtNumber = :roomExt OR r.roomName = :roomExt) AND t.vidyoGatewayControllerDns = :vidyoGatewayControllerDns";

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomExt Room Extension or Room Name
     * @param vidyoGatewayControllerDns
     * @return
     */
    public Room getRoomDetailsForConference(String roomExt, String vidyoGatewayControllerDns) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomExt", roomExt);
        namedParamsMap.put("vidyoGatewayControllerDns", vidyoGatewayControllerDns);
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_FOR_CONF_BY_GATEWAY, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result
        return rooms.get(0);
    }

    private static final String GET_ROOM_DETAILS_FOR_WEBCAST = "SELECT m.username as ownerName, m.memberid, "
            + "t.tenantName, t.tenantID, r.roomName, rt.roomType, r.roompin FROM Room r, RoomType rt, Member m, Tenant t where "
            + "r.memberID = m.memberID AND r.roomTypeID = rt.roomTypeID AND m.tenantID = t.tenantID AND r.roomID = :roomId AND m.tenantID = :tenantId";

    /**
     * Returns the Room, Member, Tenant details needed for Webcast
     *
     * @param roomID
     * @param tenantID
     * @return
     */
    public Room getRoomDetailsForWebcast(int roomID, int tenantID) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomId", roomID);
        namedParamsMap.put("tenantId", tenantID);
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_FOR_WEBCAST, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result
        return rooms.get(0);
    }
    
    public int updateRoomModeratorPIN(int tenant, Room room) {
        logger.debug("Update record in Room for tenant = " + tenant + " and roomID = " + room.getRoomID());

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET"
        );
        if (room.getModeratorPinSetting().equalsIgnoreCase("enter")) {
            sqlstm.append("  r.roomModeratorPIN = :roomModeratorPIN");
        } else if (room.getModeratorPinSetting().equalsIgnoreCase("remove")) {
            sqlstm.append("  r.roomModeratorPIN = NULL");
        } else if (room.getModeratorPinSetting().equalsIgnoreCase("leave")) {
            // do not touch the roomModeratorPIN field in this case
            sqlstm.append("  r.roomModeratorPIN = r.roomModeratorPIN");
        }
        sqlstm.append(
            " WHERE" +
            "  (r.roomID = :roomID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(room);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return room.getRoomID();
    }

    public int getBiggestRoomExtNumber(int tenant) {
        if (logger.isDebugEnabled())
            logger.debug("Get biggest room extension for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomExtNumber" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON r.memberID = m.memberID" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " ORDER BY CAST(r.roomExtNumber AS UNSIGNED) DESC LIMIT 0,1"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenant));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        List<String> ls = namedParamJdbcTemplate.query(sqlstm.toString(),
                namedParameters,
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }
        );

        int biggestExtNumber = ls.size() > 0 ? Integer.valueOf(ls.get(0)) : 0;
        if (logger.isDebugEnabled())
            logger.debug("Biggest room extension for tenant = " + biggestExtNumber);

        return biggestExtNumber;

    }

    private static final String DELETE_SCHEDULED_ROOMS = "delete from Room where roomTypeID =:roomTypeId";

    private static final String DELETE_SCHEDULED_ROOM_BY_ROOM_KEY = "delete from Room where roomKey =:roomKey";

    /**
     * Deletes all the Scheduled Rooms
     *
     * @param prefix
     * @return
     */
    public int deleteScheduledRooms(String prefix) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomTypeId", 4);
        int deletedRowsCount = namedParamJdbcTemplate.update(DELETE_SCHEDULED_ROOMS, namedParamsMap);
        return deletedRowsCount;
    }

    /**
     * Deletes the Scheduled Room for the given key
     *
     * @param roomKey
     * @return
     */
    public int deleteScheduledRoomByRoomKey(String roomKey) {
        Map<String, Object> namedParamsMap = new HashMap<>();
        namedParamsMap.put("roomKey", roomKey);
        int deletedRowsCount = namedParamJdbcTemplate.update(DELETE_SCHEDULED_ROOM_BY_ROOM_KEY, namedParamsMap);
        return deletedRowsCount;
    }

    /**
     * Query to update the Room's last used time
     */
    private static final String UPDATE_ROOM_LAST_ACCESSED_TIME = "update Room set last_used =:lastAccessedTime where roomID =:roomId";

    /**
     * Updates the Room's last accessed time.
     *
     * @param roomId
     * @param lastAccessedTime
     * @return
     */
    @Override
    public int updateLastAccessedTime(int roomId, Date lastAccessedTime) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomId", roomId);
        namedParamsMap.put("lastAccessedTime", lastAccessedTime);
        int updatedRowsCount = namedParamJdbcTemplate.update(UPDATE_ROOM_LAST_ACCESSED_TIME, namedParamsMap);
        return updatedRowsCount;
    }

    /**
     * SQL to get room count by room extension number
     */
    private static final String COUNT_ROOM_BY_ROOM_EXTN = "SELECT COUNT(1) FROM Room r where r.roomExtNumber =:roomExtNumber";

    /**
     * Returns the room count by room ext number
     *
     * @param roomExtNumber
     * @return
     */
    public boolean isRoomExistForRoomExtNumber(String roomExtNumber) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("roomExtNumber", roomExtNumber);
        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(COUNT_ROOM_BY_ROOM_EXTN, namedParameters, Integer.class);
        return num > 0;
    }

    private static final String ROOMS_COUNT_MATCHING_PREFIX = "select count(1) FROM Room r, Member m where m.tenantID !=:tenantId and m.memberID = r.memberID and r.roomExtNumber like :prefixWildcard";

    /**
     * Checks if the prefix is matching any of the other Tenant's rooms.<br>
     * This is just a wild card match and not an exact match. This check is done
     * specifically to prevent any DB corruption due to lack of transactional
     * DB.
     *
     * @param tenantId
     *            current tenant
     * @param prefix
     *            tenant prefix to be matched
     * @return count of matching rooms
     */
    public int getRoomsCountMatchingPrefix(int tenantId, String prefix) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenantId);
        namedParamsMap.put("prefixWildcard", prefix + "%");
        return namedParamJdbcTemplate.queryForObject(ROOMS_COUNT_MATCHING_PREFIX, namedParamsMap, Integer.class);
    }

    /**
     * Query to update the Room Extensions in the descending order of their numerical value
     */
    private String UPDATE_TENANT_PREFIX_DESC = "UPDATE Room SET roomExtNumber = CONCAT(:newPrefix, SUBSTRING(roomExtNumber FROM (CHAR_LENGTH(:oldPrefix)+1))) WHERE roomTypeID !=:legacy AND roomExtNumber LIKE :oldPrefixWC AND memberID IN (SELECT memberID FROM Member WHERE tenantID =:tenantId) order by CAST(roomExtNumber AS DECIMAL) DESC";

    /**
     * Query to update the Room Extensions in the ascending order of their numerical value
     */
    private String UPDATE_TENANT_PREFIX_ASC = "UPDATE Room SET roomExtNumber = CONCAT(:newPrefix, SUBSTRING(roomExtNumber FROM (CHAR_LENGTH(:oldPrefix)+1))) WHERE roomTypeID !=:legacy AND roomExtNumber LIKE :oldPrefixWC AND memberID IN (SELECT memberID FROM Member WHERE tenantID =:tenantId) order by CAST(roomExtNumber AS DECIMAL) ASC";

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
    public int updateRoomExtensionPrefix(String newPrefix, int tenantId, String oldPrefix) {

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenantId);
        namedParamsMap.put("oldPrefix", oldPrefix);
        namedParamsMap.put("newPrefix", newPrefix);
        namedParamsMap.put("oldPrefixWC", oldPrefix + "%");
        namedParamsMap.put("legacy", 3);
        int updateCount = 0;

        if(newPrefix.length() > oldPrefix.length()) {
            // Update the rows in descending order of extension value
            updateCount = namedParamJdbcTemplate.update(UPDATE_TENANT_PREFIX_DESC, namedParamsMap);
        } else {
            // Update the rows in ascending order of extension value - if equal or lesser digits
            updateCount = namedParamJdbcTemplate.update(UPDATE_TENANT_PREFIX_ASC, namedParamsMap);
        }

        return updateCount;
    }

    /**
     *
     */
    public static final String UPDATE_GROUP_FOR_ROOMS = "update Room r, Groups g set r.groupId =:defaultGroupId where g.groupId = r.groupId and g.tenantId =:tenantId and r.groupId =:groupId";

    /**
     * Updates the GroupId to Default GroupId for Rooms.
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupForRooms(RoomUpdateRequest roomUpdateRequest) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(3);
        namedParamsMap.put("tenantId", roomUpdateRequest.getTenantId());
        namedParamsMap.put("defaultGroupId", roomUpdateRequest.getDefaultGroupId());
        namedParamsMap.put("groupId", roomUpdateRequest.getOldGroupId());
        int updateCount = namedParamJdbcTemplate.update(UPDATE_GROUP_FOR_ROOMS, namedParamsMap);
        return updateCount;
    }

    public static final String DELETE_INACTIVE_ROOMS = "delete from Room where roomTypeID =:typeId and DATEDIFF(now(), last_used) >:inactiveDays AND recurring = 0";

    /**
     * Deletes inactive scheduled rooms based on the inactivity limit.
     *
     * @param inactiveDays
     *            Days of Inactivity
     * @return count of deleted rooms
     */
    public int deleteInactiveSchRooms(int inactiveDays) {
        RoomType roomType = getRoomTypeDetails("Scheduled");
        if (roomType == null) {
            return 0;
        }
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(2);
        namedParamsMap.put("typeId", roomType.getRoomTypeId());
        namedParamsMap.put("inactiveDays", inactiveDays);
        return namedParamJdbcTemplate.update(DELETE_INACTIVE_ROOMS, namedParamsMap);
    }

    public int updateDeleteOnScheduledRoom(int roomId) {
        // UP date the delete on
        RoomType roomType = getRoomTypeDetails("Scheduled");
        if (roomType == null) {
            return 0;
        }
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(2);
        namedParamsMap.put("typeId", roomType.getRoomTypeId());
        namedParamsMap.put("roomId", roomId);
        String updateDeleteOn = "update Room set deleteon = DATE_ADD( DATE_FORMAT(last_used, '%Y-%m-%d %H:%i:%s'),INTERVAL recurring+1 DAY) WHERE"
                + " roomID = :roomId AND roomTypeID = :typeId AND recurring > 0";
        return namedParamJdbcTemplate.update(updateDeleteOn, namedParamsMap);

    }

    /**
     * Delete the records which are not last used based on the recurring value.
     * So if the now - recurring is still greater than last used value, the record should garbage collected.
     *
     * @param limit - batch size used for deleting records in batches
     *
     * @return - Records deleted
     */
    public int deleteScheduledRoomsbyRecurring (int limit){
        logger.debug("Inside DAO deleteScheduledRoomsbyRecurring...");
        int totalDeleted = 0;
        String deleteScheduledRoomsRecurring = "CALL deleteScheduledRoom(?)";
        List<SqlParameter> paramList = new ArrayList<SqlParameter>();
        Map<String, Object> resultMap = getJdbcTemplate().call(
                new CallableStatementCreator() {
                    @Override
                    public CallableStatement createCallableStatement(
                            Connection connection) throws SQLException {
                                CallableStatement callableStatement = connection.prepareCall(deleteScheduledRoomsRecurring);
                                callableStatement.setInt(1, limit);
                                return callableStatement;
                    }
                }, paramList);

        List<LinkedCaseInsensitiveMap<Long>> totalCountResultSet = (List<LinkedCaseInsensitiveMap<Long>>) resultMap.get("#result-set-1");
        if(totalCountResultSet != null && totalCountResultSet instanceof List<?>) {
            for(LinkedCaseInsensitiveMap<Long> map : totalCountResultSet) {
                if(map.get("TotalRecords") != null) {
                    totalDeleted = map.get("TotalRecords").intValue();
                }
            }
        }

        logger.debug("Finished DAO deleteScheduledRoomsbyRecurring...");
        return totalDeleted;
    }

    public static final String GET_ROOM_TYPE_DETAILS = "select roomTypeID as roomTypeId, roomType, roomTypeDescription as description from RoomType where roomType =:roomType";

    /**
     *
     * @param roomType
     * @return
     */
    private RoomType getRoomTypeDetails(String roomType) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(1);
        namedParamsMap.put("roomType", roomType);
        List<RoomType> roomTypes = namedParamJdbcTemplate.query(GET_ROOM_TYPE_DETAILS, namedParamsMap,
                BeanPropertyRowMapper.newInstance(RoomType.class));
        if(roomTypes.isEmpty()) {
            return null;
        }

        if(roomTypes.size() > 1) {
            logger.warn("Duplicate RoomTypes in the RoomType table");
        }
        return roomTypes.get(0);
    }

    /**
     *
     */
    public static final String UPDATE_GROUP_BY_MEMBER = "update Room r, RoomType rt set r.groupId =:newGroupId where r.memberId =:memberId and rt.roomType =:roomType and r.roomTypeID = rt.roomTypeID";

    /**
     * Updates the Room's group based on the member and room type.
     *
     * @param roomUpdateRequest
     * @return
     */
    public int updateGroupByMember(RoomUpdateRequest roomUpdateRequest) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(3);
        namedParamsMap.put("roomType", roomUpdateRequest.getRoomType());
        namedParamsMap.put("newGroupId", roomUpdateRequest.getNewGroupId());
        namedParamsMap.put("memberId", roomUpdateRequest.getMemberId());
        int updatedCount = namedParamJdbcTemplate.update(UPDATE_GROUP_BY_MEMBER, namedParamsMap);
        return updatedCount;
    }

    public static final String IS_ROOM_VALID = "select r.roomID, m.memberID from Room r, Member m, RoomType rt where r.roomID =:roomId and r.memberID = m.memberID and r.roomTypeID = rt.roomTypeID and rt.roomType in (:roomTypes) and m.tenantID in (:canCallTenantIds)";

    /**
     * Returns the Accessible Room details for the user
     *
     * @param roomId
     * @param canCallTenantIds
     * @return
     */
    @Override
    public Room getAccessibleRoomDetails(int roomId,
            List<Integer> canCallTenantIds) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>(3);
        namedParamsMap.put("roomId", roomId);
        namedParamsMap.put("canCallTenantIds", canCallTenantIds);

        List<String> roomTypes = new ArrayList<String>(3);
        roomTypes.add(RoomTypes.PERSONAL.getType());
        roomTypes.add(RoomTypes.PUBLIC.getType());
        roomTypes.add(RoomTypes.LEGACY.getType());
        namedParamsMap.put("roomTypes", roomTypes);

        List<Room> rooms = namedParamJdbcTemplate.query(IS_ROOM_VALID,
                namedParamsMap, new RoomMapper());
        if (rooms.isEmpty()) {
            return null;
        }
        return rooms.get(0);
    }

    public static final String COUNT_FOR_SEARCH_ONLINE_BY_MEMBERID = "select count(r.roomID) from Room r, Member m, RoomType rt, Endpoints e where m.memberID =:memberId and m.active =:active and m.allowedToParticipate =:allowed and m.memberId = e.memberId and e.memberType = :memberType and e.status != :offline and m.memberID = r.memberID and r.roomEnabled =:roomEnabled and r.roomTypeID = rt.roomTypeID and rt.roomType in (:roomTypes) ";

    public static final String COUNT_FOR_SEARCH_BY_MEMBERID = "select count(r.roomID) from Room r, RoomType rt, Member m left join Endpoints e on m.memberId = e.memberId and e.memberType = :memberType where m.memberID =:memberId and m.active =:active and m.allowedToParticipate =:allowed and m.memberID = r.memberID and r.roomEnabled =:roomEnabled and r.roomTypeID = rt.roomTypeID and rt.roomType in (:roomTypes) ";

    private int getCountForSearchByEntityId(EntityFilter filter, int ownerId, User loggedInUser) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        // Room Types validation
        List<String> roomTypes = new ArrayList<String>(3);
        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                roomTypes.add("Personal");
                roomTypes.add("Public");
                roomTypes.add("Legacy");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                roomTypes.add("Personal");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                roomTypes.add("Public");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                roomTypes.add("Legacy");
            }
            String query = filter.getQuery().trim()
                    .replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                // Count only online users - join with endpoints status != 0
                queryBuilder.append(COUNT_FOR_SEARCH_ONLINE_BY_MEMBERID);
            } else {
                queryBuilder.append(COUNT_FOR_SEARCH_BY_MEMBERID);
                if (!query.isEmpty() && !query.contains(" ")) {
                    queryBuilder
                            .append(" and (m.memberName like :memberName or m.memberName like :memberName1 or r.roomName like :memberName or r.roomExtNumber like :memberName)");
                    namedParamsMap.put("memberName", query + "%");
                    namedParamsMap.put("memberName1", "% " + query + "%");
                } else if (!query.isEmpty()) {
                    // Legacy devices containing space in name will be covered
                    // by member name column
                    query = query.replace(" ", ".*\\ ");
                    queryBuilder.append(" AND m.memberName REGEXP CONCAT('^', :regExpQuery) ");
                    namedParamsMap.put("regExQuery", query);                    
                    // Public and Personal rooms don't support space in room
                    // name
                }
            }

        } else {
            // If no filter, search on all types by default
            roomTypes.add("Personal");
            roomTypes.add("Public");
            roomTypes.add("Legacy");
            queryBuilder.append(COUNT_FOR_SEARCH_BY_MEMBERID);
        }

        queryBuilder.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", loggedInUser.getMemberID());

        namedParamsMap.put("memberId", ownerId);
        namedParamsMap.put("roomTypes", roomTypes);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allowed", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("offline", 0);
        namedParamsMap.put("roomEnabled", 1);
        int count = namedParamJdbcTemplate.queryForObject(
                queryBuilder.toString(), namedParamsMap, Integer.class);
        return count;
    }

    public static final String SEARCH_ALL_ENTITIES_BY_OWNER_ID = "SELECT "
            + " r.roomID, "
            + " CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID, "
            + " m.memberID, "
            + " m.username, "
            + " m.modeID, "
            + " m.emailAddress, "
            + " r.roomname, "
            + " CASE WHEN r.roomTypeID = :personal OR r.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name, "
            + " r.roomExtNumber as ext, "
            + " r.roomDescription, "
            + " CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned, "
            + " r.roomPIN, "
            + " r.roomModeratorPIN, "
            + " CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned, "
            + " CASE WHEN r.memberID = :memberId THEN r.roomKey ELSE NULL END AS roomKey, "
            + " r.roomEnabled, "
            + " r.roomLocked, "
            + " r.roomTypeID, "
            + " rt.roomType,"
            + " CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus, "
            + " CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID, "
            + " CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner, "
            + " CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID = r.roomID AND c.conferenceType = :confType AND c.endpointType != :endpointType) = g.roomMaxUsers THEN 2  "
            + " WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID = r.roomID AND c.conferenceType = :confType AND c.endpointType != :endpointType) = 0 THEN 0 ELSE 1 END as roomStatus, "
            + " lg.langID, "
            + " lg.langCode, "
            + " mt.tenantID, "
            + " mt.tenantName, "
            + " mt.tenantUrl, "
            + " CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn, "
            + " g.roomMaxUsers, "
            + " g.allowRecording, "
            + " CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio, "
            + " CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video "
            + " FROM "
            + " Room r LEFT JOIN SpeedDial sd ON r.roomID = sd.roomID AND sd.memberID =:memberId"
            + " , RoomType rt"
            + " , Member m LEFT JOIN Endpoints e ON e.memberID = m.memberID AND e.memberType = :memberType "
            + " LEFT JOIN Conferences c ON c.endpointID = e.endpointID "
            + " , Groups g  "
            + " , Language lg "
            + " , Tenant mt "
            + " WHERE "
            + " m.memberID =:memberId AND m.active = :active AND m.allowedToParticipate=:allowed and r.memberID = m.memberID "
            + " and r.roomTypeID = rt.roomTypeID and rt.roomType in (:roomTypes) and g.groupID = r.groupID and m.langID = lg.langID "
            + " and mt.tenantID=m.tenantID ";

    public static final String SEARCH_ONLINE_ENTITIES_BY_OWNER_ID = "SELECT "
            + " r.roomID, "
            + " CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID, "
            + " m.memberID, "
            + " m.username, "
            + " m.modeID, "
            + " m.emailAddress, "
            + " r.roomname, "
            + " CASE WHEN r.roomTypeID = :personal OR r.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name, "
            + " r.roomExtNumber as ext, "
            + " r.roomDescription, "
            + " CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned, "
            + " r.roomPIN, "
            + " r.roomModeratorPIN, "
            + " CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned, "
            + " CASE WHEN r.memberID = :memberId THEN r.roomKey ELSE NULL END AS roomKey, "
            + " r.roomEnabled, "
            + " r.roomLocked, "
            + " rt.roomType,"
            + " r.roomTypeID, "
            + " rt.roomType,"
            + " CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus, "
            + " CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID, "
            + " CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner, "
            + " CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID = r.roomID AND c.conferenceType = :confType AND c.endpointType != :endpointType) = g.roomMaxUsers THEN 2  "
            + " WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID = r.roomID AND c.conferenceType = :confType AND c.endpointType != :endpointType) = 0 THEN 0 ELSE 1 END as roomStatus, "
            + " lg.langID, "
            + " lg.langCode, "
            + " mt.tenantID, "
            + " mt.tenantName, "
            + " mt.tenantUrl, "
            + " CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn, "
            + " g.roomMaxUsers, "
            + " g.allowRecording, "
            + " CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio, "
            + " CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video "
            + " FROM "
            + "  Room r "
            + " , RoomType rt"
            + " , Member m  "
            + " , Groups g  "
            + " , Language lg  "
            + " , Endpoints e "
            + " , Tenant mt "
            + " LEFT JOIN SpeedDial sd ON (r.roomID = sd.roomID AND sd.memberID = :memberId)  "
            + " LEFT JOIN Conferences c ON (c.endpointID = e.endpointID) "
            + " WHERE "
            + " m.memberID =:memberID AND m.active = :active AND m.allowedToParticipate=:allowed and r.memberID = m.memberID "
            + " and e.memberID = m.memberID AND e.memberType = :memberType and r.roomTypeID = rt.roomTypeID and rt.roomType in (:roomTypes) "
            + " and g.groupID = r.groupID and m.langID = lg.langID "
            + " and mt.tenantID=m.tenantID ";

    private List<Entity> getEntitiesByOwnerId(EntityFilter filter, int ownerId, User loggedInUser) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        // Room Types validation
        List<String> roomTypes = new ArrayList<String>(3);
        if (filter != null) {
            if (filter.getEntityType().equalsIgnoreCase("All")) {
                roomTypes.add("Personal");
                roomTypes.add("Public");
                roomTypes.add("Legacy");
            } else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
                roomTypes.add("Personal");
            } else if (filter.getEntityType().equalsIgnoreCase("Public")) {
                roomTypes.add("Public");
            } else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
                roomTypes.add("Legacy");
            }
            String query = filter.getQuery().trim()
                    .replaceAll("\\b\\s{2,}\\b", " ");
            if (query.equalsIgnoreCase("*")) {
                // Count only online users - join with endpoints status != 0
                queryBuilder.append(SEARCH_ONLINE_ENTITIES_BY_OWNER_ID);
            } else {
                queryBuilder.append(SEARCH_ALL_ENTITIES_BY_OWNER_ID);
                if (!query.isEmpty() && !query.contains(" ")) {
                    queryBuilder
                            .append(" and (m.memberName like :memberName or m.memberName like :memberName1 or r.roomName like :memberName or r.roomExtNumber like :memberName)");
                    namedParamsMap.put("memberName", query + "%");
                    namedParamsMap.put("memberName1", "% " + query + "%");
                } else if (!query.isEmpty()) {
                    // Query contains space. do we need to search roomname and
                    // room extn?
                    query = query.replace(" ", ".*\\ ");
                    queryBuilder.append(" AND m.memberName REGEXP CONCAT('^', :regExpQuery) ");
                    namedParamsMap.put("regExQuery", query);
                }
            }

            queryBuilder.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
            namedParamsMap.put("userId", loggedInUser.getMemberID());

            if (!filter.getSortBy().equalsIgnoreCase("")) {
                if (filter.getSortBy().equalsIgnoreCase("memberStatusText")) {
                    queryBuilder.append(" ORDER BY ").append("memberStatus")
                            .append(" ").append(filter.getDir());
                } else {
                    queryBuilder.append(" ORDER BY ")
                            .append(filter.getSortBy()).append(" ")
                            .append(filter.getDir());
                }
            }
        } else {
            // If no filter, search on all types by default
            roomTypes.add("Personal");
            roomTypes.add("Public");
            roomTypes.add("Legacy");
            queryBuilder.append(SEARCH_ALL_ENTITIES_BY_OWNER_ID);

            queryBuilder.append(" AND r.roomId NOT IN (").append(INACCESSIBLE_ROOMS_FOR_USER).append(")");
            namedParamsMap.put("userId", loggedInUser.getMemberID());
        }

        namedParamsMap.put("memberId", ownerId);
        namedParamsMap.put("roomTypes", roomTypes);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allowed", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("endpointType", "R");
        namedParamsMap.put("offline", 0);
        namedParamsMap.put("roomEnabled", 1);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("confType", "C");
        return namedParamJdbcTemplate.query(queryBuilder.toString(),
                namedParamsMap,
                BeanPropertyRowMapper.newInstance(Entity.class));
    }

    private static final class RoomMapper implements RowMapper<Room> {

        @Override
        public Room mapRow(ResultSet resultset, int rowNum) throws SQLException {
            Room r = new Room();
            r.setRoomID(resultset.getInt("roomID"));
            r.setMemberID(resultset.getInt("memberID"));
            return r;
        }
    }

    private static final String GET_ROOM_DETAILS_FOR_DISCONNECT = "SELECT r.roomID, r.roomPIN, r.roomExtNumber, "
            + "r.roomModeratorPIN, r.roomName, rt.roomType, t.tenantName,"
            + "m.memberID "
            + "FROM Room r, RoomType rt,"
            + "Member m, Tenant t WHERE  r.roomID =:roomId AND r.memberID = m.memberID AND r.roomTypeID = rt.roomTypeID "
            + "AND m.tenantID =t.tenantId";

    /**
     * Returns RoomId, memberId, moderatorPin, roomType of the room.
     * @param roomId
     * @param tenantId
     * @return
     */
    public Room getRoomDetailsForDisconnectParticipant(int roomId, int tenantId) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomId", roomId);
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_FOR_DISCONNECT, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result
        return rooms.get(0);
    }

    public int getRoomIdByExt(int tenantId, String roomExt) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomExt", roomExt);

        StringBuilder sqlStr = new StringBuilder("SELECT roomID FROM Room r");
        if(tenantId != 0) {
            sqlStr.append(" INNER JOIN Member m ON (r.memberID = m.memberID)");
        }
        sqlStr.append(" WHERE r.roomExtNumber = :roomExt ");
        if(tenantId != 0) {
            sqlStr.append(" AND m.tenantID = :tenantID");
            namedParamsMap.put("tenantID", tenantId);
        }
        int roomId = namedParamJdbcTemplate.queryForObject(sqlStr.toString(), namedParamsMap, Integer.class);
        return roomId;
    }

    public static final String SET_ALL_ROOMS_LECTURE_MODE_STATE = "UPDATE Room r" +
            " JOIN Room mr ON mr.roomId = r.roomId" +
            " JOIN Member m ON mr.memberID = m.memberID" +
            " SET r.lectureMode = :lectureModeFlag, r.roomMuted = :lectureModeFlag" +
            " WHERE m.tenantID = :tenantId ";

    public void setTenantRoomsLectureModeState(int tenantId, boolean flag) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("lectureModeFlag", (flag ? 1 : 0));
        namedParamsMap.put("tenantId", tenantId);
        namedParamJdbcTemplate.update(SET_ALL_ROOMS_LECTURE_MODE_STATE, namedParamsMap);
    }

    public int getPublicRoomCountForOwnerID(int tenant, int ownerID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  count(*)" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID = m.memberID AND r.roomTypeID = 2)" +
            " WHERE" +
            "  m.tenantID = :tenantId" +
            " AND" +
            "  r.memberID = :memberId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", ownerID);
        int count = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);

        return count;
    }

    public int getPublicRoomCountForTenentID(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  count(*)" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID = m.memberID AND r.roomTypeID = 2)" +
            " WHERE" +
            "  m.tenantID = :tenantId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        int count = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);

        return count;
    }

    public long getPublicRoomCount() {
        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  count(*)" +
                " FROM" +
                "  Room r" +
                "  WHERE" +
                "  r.roomTypeID = 2"
            );


            int count = getJdbcTemplate().queryForObject(sqlstm.toString(), Integer.class);

            return count;
    }

    public static final String ROOM_EXISTS_BY_DISPLAYNAME = "SELECT EXISTS (SELECT 1 FROM Room r, Member m WHERE r.memberid = m.memberid AND MATCH(r.roomname, r.displayname, r.roomextnumber) AGAINST(:displayName in boolean mode) and m.tenantid =:tenantId)";
    
    public int getDisplayNameCount(String displayName, int tenant) {

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        //VPTL-8201 - Use the full text search for checking displayname
        namedParamsMap.put("displayName", "'\"+" + displayName + "\"'");
        namedParamsMap.put("tenantId", tenant);
        int count = namedParamJdbcTemplate.queryForObject(ROOM_EXISTS_BY_DISPLAYNAME, namedParamsMap, Integer.class);
        return count;
    }


    private static final String TENANT_ROOMS_COUNT_MATCHING_PREFIX = "select count(1) FROM Room r, Member m where m.tenantID =:tenantId and m.memberID = r.memberID and r.roomExtNumber like :prefixWildcard";

    /**
     * Checks if the prefix is matching any of the other Tenant's rooms.<br>
     * This is just a wild card match and not an exact match. This check is done
     * specifically to prevent any DB corruption due to lack of transactional
     * DB.
     *
     * @param tenantId
     *            current tenant
     * @param prefix
     *            tenant prefix to be matched
     * @return count of matching rooms
     */
    public int getDefaultTenantRoomsCountMatchingPrefix(String prefix) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", 1);
        namedParamsMap.put("prefixWildcard", prefix + "%");
        return namedParamJdbcTemplate.queryForObject(TENANT_ROOMS_COUNT_MATCHING_PREFIX, namedParamsMap, Integer.class);
    }

    /**
     * @param displayName
     * @param roomId
     * @param tenant
     * @return
     */
    public int getDisplayNameCount(String displayName, int roomId, int tenant){
        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  count(*)" +
                " FROM" +
                "  Room r" +
                " INNER JOIN Member m ON (r.memberID = m.memberID) " +
                "  WHERE" +
                "  r.displayName = :displayName AND r.roomId != :roomId AND m.tenantID = :tenantId"
            );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("displayName", displayName);
        namedParamsMap.put("roomId", roomId);
        namedParamsMap.put("tenantId", tenant);
        int count = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);
        return count;
    }

    private static final String GET_ROOM_DETAILS_FOR_GW_CONF = "SELECT * FROM (SELECT r.roomID, r.roomName, r.roomExtNumber, r.groupID, r.recurring, "
            + "r.roomModeratorPIN, r.roomLocked, r.roomEnabled, roomPIN, r.roomMuted, r.roomSilenced, r.roomVideoMuted, r.roomVideoSilenced, r.lectureMode, r.profileID, "
            + "rt.roomType, m.memberID, m.memberName as ownerName, m.tenantID, t.tenantName, g.roomMaxUsers, g.userMaxBandWidthIn, "
            + "g.userMaxBandWidthOut, count(c.conferenceID) as occupantsCount FROM Room r LEFT JOIN Conferences c ON r.roomID = c.roomID and c.endpointType !=:endpointType, "
            + "RoomType rt, Member m, Groups g, Tenant t WHERE r.memberID = m.memberID AND r.roomTypeID = rt.roomTypeID AND m.tenantID =t.tenantId AND r.groupID = g.groupID "
            + "AND m.tenantId =:tenantId and r.roomName =:roomNameExt UNION "
            + "SELECT r.roomID, r.roomName, r.roomExtNumber, r.groupID, r.recurring, "
            + "r.roomModeratorPIN, r.roomLocked, r.roomEnabled, roomPIN, r.roomMuted, r.roomSilenced, r.roomVideoMuted, r.roomVideoSilenced, r.lectureMode, r.profileID, "
            + "rt.roomType, m.memberID, m.memberName as ownerName, m.tenantID, t.tenantName, g.roomMaxUsers, g.userMaxBandWidthIn, "
            + "g.userMaxBandWidthOut, count(c.conferenceID) as occupantsCount FROM Room r LEFT JOIN Conferences c ON r.roomID = c.roomID and c.endpointType !=:endpointType, "
            + "RoomType rt, Member m, Groups g, Tenant t WHERE r.memberID = m.memberID AND r.roomTypeID = rt.roomTypeID AND m.tenantID =t.tenantId AND r.groupID = g.groupID "
            + "AND r.roomExtNumber =:roomNameExt) as res WHERE res.roomID IS NOT NULL";

    /**
     * Returns the Room, Member details needed for creating the Conference
     *
     * @param roomNameExt
     * @param tenantID
     * @return
     */
    @Override
    public Room getRoomDetailsForConference(String roomNameExt, int tenantId) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomNameExt", roomNameExt);
        namedParamsMap.put("tenantId", tenantId);
        // Recorder Endpoint type is "R" and it is not counted as a particpant
        namedParamsMap.put("endpointType", "R");
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_FOR_GW_CONF, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result
        return rooms.get(0);
    }

    private static final String GET_ROOM_DETAILS_BY_ROOM_KEY = "select r.roomID, r.roomName, r.roomKey, r.roomModeratorKey, r.roomPIN, r.displayName, r.roomDescription, r.roomExtNumber, r.roomEnabled, r.roomLocked, CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned, rt.roomType, r.groupID FROM Room r INNER JOIN Member m ON (r.memberID=m.memberID) INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID) where m.tenantId =:tenantId and r.roomKey =:roomKey";

    /**
     * Returns the roomId, roomKey and moderatorKey by roomKey and tenantId
     * @param roomKey
     * @param tenantId
     * @return
     */
    @Override
    public Room getRoomDetailsByRoomKey(String roomKey, int tenantId) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomKey", roomKey);
        namedParamsMap.put("tenantId", tenantId);
        List<Room> rooms = namedParamJdbcTemplate.query(
                GET_ROOM_DETAILS_BY_ROOM_KEY, namedParamsMap,
                BeanPropertyRowMapper.newInstance(Room.class));
        if (rooms.isEmpty()) {
            return null;
        }
        // Expect only one result as the room keys are unique
        return rooms.get(0);

    }

    private static final String PUBLIC_ROOM_ID_FOR_MEMBER_ID = "SELECT COUNT(*) FROM Room r, Member m WHERE r.roomID = :roomID AND roomTypeID = 2 AND r.memberId = :memberId AND m.tenantId =:tenantId AND m.memberId = r.memberId";
    @Override
    public boolean isPublicRoomIdExistsForMemberId(int roomId, int memberId, int tenantId) {
        int count = 0;
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("tenantId", tenantId)
                .addValue("memberId", memberId)
                .addValue("roomID", roomId);

        count = namedParamJdbcTemplate.queryForObject(PUBLIC_ROOM_ID_FOR_MEMBER_ID, paramSource, Integer.class);
        return count > 0;
    }

    /**
     *
     */
    public static final String SILENCE_SPEAKER_SERVER = "UPDATE Room r SET r.roomSpeakerMuted = :roomSpeakerMuted WHERE r.roomID = :roomId";

    /**
     * Silences the speaker device of the Room from Server
     * side which means Endpoints joining/joined this room cannot unsilence their speaker.
     *
     * @param roomId
     * @param flag
     * @return
     */
    @Override
    public int silenceSpeakerForRoomServer(int roomId, int flag) {
        int count = 0;
        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("roomId", roomId)
                .addValue("roomSpeakerMuted", flag);
        count = namedParamJdbcTemplate.update(SILENCE_SPEAKER_SERVER, paramSource);
        return count;
    }

	private static final String GET_ROOM_STATUS_BY_ROOM_ID_AND_GROUP_ID = "SELECT CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=:roomId AND c.conferenceType='C' AND c.endpointType != 'R') = g.roomMaxUsers THEN 2 WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=:roomId AND c.conferenceType='C' AND c.endpointType != 'R') = 0 THEN 0 ELSE 1 END as roomStatus from Groups g where g.groupId=:groupId";

	/**
	 * Returns the room status by roomId and groupId
	 *
	 * @param roomId
	 * @param groupId
	 * @return
	 */
	@Override
	public int getRoomStatus(int roomId, int groupId) {
		int roomStatus = 0;
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("roomId", roomId);
		namedParamsMap.put("groupId", groupId);
		roomStatus = namedParamJdbcTemplate.queryForObject(GET_ROOM_STATUS_BY_ROOM_ID_AND_GROUP_ID, namedParamsMap,
				Integer.class);
		return roomStatus;
	}

	/**
	 * Deletes all rooms owned by the Member
	 * @param memberId
	 * @return
	 */
	@Override
	public int deleteRoomsByMember(int memberId) {
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberId", memberId);
		return namedParamJdbcTemplate.update(DELETE_ALL_ROOMS_FOR_MEMBER, namedParamsMap);
	}

	private int getCountByExtensionLength(int tenantId, int tenantExtensionLength) {
	    String sqlstm = "select count(*) from Room r INNER JOIN Member m on r.memberId=m.memberId and m.tenantId=:tenantId and length(r.roomExtNumber)=:roomExtLength";
      Map<String, Object> namedParamsMap = new HashMap<String, Object>();
      namedParamsMap.put("tenantId", tenantId);
      namedParamsMap.put("roomExtLength", tenantExtensionLength);
      Integer li = namedParamJdbcTemplate.queryForObject(sqlstm, namedParamsMap, Integer.class);
      return li;
    }
}