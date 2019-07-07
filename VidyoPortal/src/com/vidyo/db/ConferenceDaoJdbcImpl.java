package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.vidyo.bo.ConferenceInfo;
import com.vidyo.bo.ConferenceRecord;
import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Member;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.conference.Conference;
import com.vidyo.bo.gateway.GatewayPrefix;

public class ConferenceDaoJdbcImpl extends JdbcDaoSupport implements IConferenceDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(ConferenceDaoJdbcImpl.class.getName());

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

    protected final HashMap<String, ArrayList<String>> usedGW = new HashMap<String, ArrayList<String>>();

    public void pointCutOnUpdateStatus(String GUID, String status, int oldStatus, int newStatus, ConferenceInfo conferenceInfo, String endpointType) {
        // Dummy method for AOP pointcut - CDR collection, etc.
    }

    public void pointCutOnUpdateStatusForFederation(String GUID, String status, int old_status, int new_status, ConferenceInfo conferenceInfo) {
        // Dummy method for AOP pointcut - Federation.
    }

    public List<Control> getControls(int roomID, ControlFilter filter) {
        List<Control> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            " CASE WHEN c.endpointType = 'D' THEN" +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   r.roomID" +
            "  ELSE" +
            "   0" +
            "  END" +
            " WHEN c.endpointType = 'V' THEN" +
            "  CASE WHEN ve.entityID IS NOT NULL THEN" +
            "   ve.entityID" +
            "  ELSE" +
            "   0" +
            "  END" +
            " WHEN c.endpointType = 'R' THEN" +
            "  CASE WHEN re.entityID IS NOT NULL THEN" +
            "   re.entityID" +
            "  ELSE" +
            "   0" +
            "  END" +
            " END" +
            " as roomID," +
            " " +
            " c.endpointID," +
            " c.endpointType," +
            " " +
            " CASE WHEN c.endpointType = 'D' THEN" +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   m.memberName" +
            "  ELSE" +
            "   g.guestName" +
            "  END" +
            " WHEN c.endpointType = 'V' THEN" +
            "  ve.`displayName`" +
            " WHEN c.endpointType = 'R' THEN" +
            "  re.description" +
            " END" +
            " as name," +
            " " +
            " CASE WHEN c.endpointType = 'D' THEN" +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   r.roomExtNumber" +
            "  ELSE" +
            "   'Guest'" +
            "  END" +
            " WHEN c.endpointType = 'V' THEN" +
            "   ve.`displayExt`" +
            " WHEN c.endpointType = 'R' THEN" +
            "   'Recorder'" +
            " END" +
            " as ext," +
            " " +
            " CASE WHEN c.endpointType = 'D' THEN" +
            "   e.endpointGUID" +
            " WHEN c.endpointType = 'V' THEN" +
            "   ve.endpointGUID" +
            " WHEN c.endpointType = 'R' THEN" +
            "   re.endpointGUID" +
            " END" +
            " as endpointGUID," +
            " " +
            "  c.audio," +
            "  c.video," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " " +
            " CASE WHEN c.endpointType = 'D' THEN" +
            "   0" +
            " WHEN c.endpointType = 'V' THEN" +
            "   0" +
            " WHEN c.endpointType = 'R' THEN" +
            "   re.webcast" +
            " END" +
            " as webcast," +
            " " +
            " c.createdTime as joinTime, " +
            " c.presenter as presenter, " +
            " c.handRaised as handRaised " +
            " FROM  Conferences c" +
            " LEFT JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType='D')" +
            " LEFT JOIN VirtualEndpoints ve ON (ve.endpointID=c.endpointID AND c.endpointType='V')" +
            " LEFT JOIN RecorderEndpoints re ON (re.endpointID=c.endpointID AND c.endpointType='R')" +
            " LEFT JOIN Member m ON (m.memberID=e.memberID AND e.memberType='R')" +
            " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
            " LEFT JOIN Room r ON (m.memberID=r.memberID AND r.roomTypeID=1)" +
            " LEFT JOIN Tenant t ON (t.tenantID=c.tenantID)" +
            " " +
            " WHERE c.roomID = :roomID" +
            "  AND" +
            "  c.conferenceType = 'C'"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);

        if (filter != null) {
            sqlstm.append(" ORDER BY coalesce(presenter, 0) DESC, coalesce(handRaised,0) DESC, handRaisedTime ASC, ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Control.class));
        return rc;
    }

    public List<Control> getFederationControls(int roomID, ControlFilter filter) {
        List<Control> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  0 as roomID," +
            "  fc.endpointID," +
            "  fc.conferenceType as endpointType," + // This is a trick - F - means federated endpoint
            "  fc.displayName as name," +
            "  fc.extension as ext," +
            "  fc.endpointGUID," +
            "  fc.audio," +
            "  fc.video," +
            "  fc.dialIn," +
            "  0 as webcast" +
            " FROM" +
            "  FederationConferences fc" +
            " WHERE " +
            "  fc.conferenceName = " +
            "     (SELECT " +
            "      CONCAT(r.roomName, '_', t.tenantName) " +
            "     FROM Room r " +
            "      JOIN Member m ON (r.memberID=m.memberID) " +
            "      JOIN Tenant t ON (t.tenantID=m.tenantID) " +
            "     WHERE r.roomID = :roomID)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);

        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Control.class));
        return rc;
    }

    public Control getControlForMember(int memberID, ControlFilter filter) {
        Control rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "	c.conferenceType, " +
            "	c.roomID" +
            " FROM" +
            "	Conferences c " +
            " INNER JOIN Endpoints e ON c.endpointID = e.endpointID AND e.memberType = :memberType" +
            " INNER JOIN Member m ON e.memberID = m.memberID" +
            " WHERE" +
            "	e.memberID = :memberID"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberID", memberID);
        namedParamsMap.put("memberType", "R");
        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        try {
        rc = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Control.class));
        } catch (Exception e) {

        }

        return rc;
    }

    public Long getCountControls(int roomID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  SUM(c) " +
            " FROM" +
            " (" +
            "   SELECT COUNT(0) as c" +
            "   FROM  Conferences c WHERE c.roomID = ? AND  c.conferenceType = 'C'" +
            "   UNION" +
            "   SELECT COUNT(0) as c" +
            "   FROM FederationConferences fc" +
            "   WHERE fc.conferenceName = (" +
            "    SELECT CONCAT(r.roomName, '_', t.tenantName) FROM Room r JOIN Member m ON (r.memberID=m.memberID) JOIN Tenant t ON (t.tenantID=m.tenantID) WHERE r.roomID = ?" +
            "   )" +
            " ) as num"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, roomID, roomID
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public static final String UPDATE_VIRTUAL_ENDPOINT_STATUS = " UPDATE" +
            "  VirtualEndpoints e" +
            " SET" +
            "  e.status = :status," +
            "  e.updateTime = CURRENT_TIMESTAMP," +
            "  e.sequenceNum = :sequenceNum" +
            " WHERE" +
            "  e.endpointGUID = :GUID" +
            " AND" +
            "  e.sequenceNum <= :sequenceNum";

    public boolean updateVirtualEndpointStatus(String GUID, int status, long newSequenceNum) {
        Assert.notNull(GUID, "A GUID must be set");
        boolean updated = false;

        Long virtualcount = getCountForVirtualGUID(GUID);
        if (virtualcount != 0l) {
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("status", status)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("GUID", GUID);

            int affected = namedParamJdbcTemplate.update(UPDATE_VIRTUAL_ENDPOINT_STATUS, namedParameters);
            logger.debug("Rows affected for VirtualEndpoints: " + affected);

            updated = true;
        }

        return updated;
    }

    /**
     * Returns true if exception while inserting Endpoint row
     * @param GUID
     * @param status
     * @param newSequenceNum
     * @param endpointType
     * @return boolean
     */
    public boolean updateEndpointStatus(String GUID, int status, long newSequenceNum, String endpointType) {
        Assert.notNull(GUID, "A GUID must be set");

        /*if (status == 0 || status == 1) {
            int affected = getJdbcTemplate().update(
                " DELETE" +
                " FROM" +
                "  ConferenceRecord" +
                " WHERE" +
                "  GUID = ?", GUID);
            if (affected == 1) {
                logger.debug("Delete records in ConferenceRecord for GUID: " + GUID);
            }
        }*/

        logger.debug("Update status = '" + status + "' of endpoint in Endpoints for GUID = " + GUID);

        boolean updated = false;

        // recorder endpoints
        if(endpointType.equalsIgnoreCase("R")) {
            Long recordercount = getCountForRecorderGUID(GUID);
            if (recordercount != 0l) { // endpoint must be present in table (by VidyoRecorderService.registerRecorderEndpoint)

                StringBuffer sqlstm = new StringBuffer(
                    " UPDATE" +
                    "  RecorderEndpoints e" +
                    " SET" +
                    "  e.status = :status," +
                    "  e.updateTime = CURRENT_TIMESTAMP," +
                    "  e.sequenceNum = :sequenceNum" +
                    " WHERE" +
                    "  e.endpointGUID = :GUID" +
                    " AND" +
                    "  e.sequenceNum <= :sequenceNum"
                );

                SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("status", status)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("GUID", GUID);

                int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
                logger.debug("Rows affected for RecorderEndpoints: " + affected);

                updated = true;
            }
        }

        // virtual endpoints
        if(endpointType.equalsIgnoreCase("V")) {
            Long virtualcount = getCountForVirtualGUID(GUID);
            if (virtualcount != 0l) { // endpoint must be present in table (by VidyoGatewayService.registerVirtualEndpoint)

                StringBuffer sqlstm = new StringBuffer(
                    " UPDATE" +
                    "  VirtualEndpoints e" +
                    " SET" +
                    "  e.status = :status," +
                    "  e.updateTime = CURRENT_TIMESTAMP," +
                    "  e.sequenceNum = :sequenceNum" +
                    " WHERE" +
                    "  e.endpointGUID = :GUID" +
                    " AND" +
                    "  e.sequenceNum <= :sequenceNum"
                );

                SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("status", status)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("GUID", GUID);

                int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
                logger.debug("Rows affected for VirtualEndpoints: " + affected);

                updated = true;
            }
        }

        // endpoints
        boolean insertException = false;

        if (!updated) {
            //First update

            StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                "  Endpoints e" +
                " SET" +
                "  e.status = :status," +
                "  e.updateTime = CURRENT_TIMESTAMP," +
                "  e.sequenceNum = :sequenceNum" +
                " WHERE" +
                "  e.endpointGUID = :GUID" +
                " AND" +
                "  e.sequenceNum <= :sequenceNum"
            );

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("status", status)
                .addValue("sequenceNum", newSequenceNum)
                .addValue("GUID", GUID);

            int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
            logger.debug("Rows affected for Endpoints: " + affected);
            try {
                if(affected == 0) {
                    SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                    .withTableName("Endpoints")
                    .usingGeneratedKeyColumns("endpointID");

                SqlParameterSource insertNamedParameters = new MapSqlParameterSource()
                    .addValue("memberID", 0)
                    .addValue("memberType", "R")
                    .addValue("endpointGUID", GUID)
                    .addValue("authorized", 0)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("status", status) // set to offline by default
                    .addValue("consumesLine", 1)
                    .addValue("lectureModeSupport", 0);

                Number newID = insert.executeAndReturnKey(insertNamedParameters);
                logger.debug("New endpointID = " + newID.intValue());
                }
            } catch (Exception e) {
                //logger.warn("Duplicate exists for GUID ->"+GUID);
                //Update the record again with the status

                StringBuffer sqlstm1 = new StringBuffer(
                    " UPDATE" +
                    "  Endpoints e" +
                    " SET" +
                    "  e.status = :status," +
                    "  e.updateTime = CURRENT_TIMESTAMP," +
                    "  e.sequenceNum = :sequenceNum" +
                    " WHERE" +
                    "  e.endpointGUID = :GUID" +
                    " AND" +
                    "  e.sequenceNum <= :sequenceNum"
                );

                SqlParameterSource updateNamedParameters = new MapSqlParameterSource()
                    .addValue("status", status)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("GUID", GUID);

                affected = namedParamJdbcTemplate.update(sqlstm1.toString(), updateNamedParameters);
                logger.debug("Rows affected for 2nd Update for Endpoints: " + affected);
                //Return to caller for retrieving memberID
                insertException = true;
                logger.warn("Duplicate GUID UpdateEndpointStatus ->"+GUID);
            }
        }

        // Special case - GUID + linked user in user's conference convert "Busy" to "BusyInOwnRoom"
        if (status == 2) {
            if (isMemberInOwnPersonalRoom(GUID)) { // update one more time
                status = 9;

                StringBuffer sqlstm = new StringBuffer(
                    " UPDATE" +
                    "  Endpoints e" +
                    " SET" +
                    "  e.status = :status," +
                    "  e.updateTime = CURRENT_TIMESTAMP," +
                    "  e.sequenceNum = :sequenceNum" +
                    " WHERE" +
                    "  e.endpointGUID = :GUID" +
                    " AND" +
                    "  e.sequenceNum <= :sequenceNum"
                );

                SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("status", status)
                    .addValue("sequenceNum", newSequenceNum)
                    .addValue("GUID", GUID);

                int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
                logger.debug("Rows affected for Endpoints: " + affected);
            }
        }
        return insertException;
    }

    private Long getCountForVirtualGUID(String GUID) {
            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  VirtualEndpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );

            List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }, GUID
            );

            return ll.size() > 0 ? ll.get(0) : 0l;
        }

        private Long getCountForRecorderGUID(String GUID) {
            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  RecorderEndpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );

            List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }, GUID
            );

            return ll.size() > 0 ? ll.get(0) : 0l;
        }


        private static String SELECT_CALLID_FOR_GUID = "SELECT UniqueCallID FROM ConferenceRecord WHERE GUID = ?";
        private static String DELETE_CONFERENCE_RECORD_FOR_GUID = "DELETE FROM ConferenceRecord WHERE GUID = ?";
        
        private static String INSERT_INTO_CONF_RECORD_QUERY = "INSERT INTO ConferenceRecord (GUID, UniqueCallID, conferenceName, conferenceType, roomID, endpointID, endpointType, endpointCaller, tenantID, audio, video, speaker, conferenceCreated) "
        		+ "VALUES(:GUID, :UniqueCallID, :conferenceName, :conferenceType, :roomID, :endpointID, :endpointType, :endpointCaller, :tenantID, :audio, :video, :speaker, :conferenceCreated)";

        private int addEndpointToConferenceRecord(String uniqueCallID, String conferenceName, String conferenceType, Room room, String GUID, String endpointType, String endpointCaller, boolean queue) {
            logger.debug("Add record into ConferenceRecord for uniqueCallID = " + uniqueCallID + ", roomID = " + room.getRoomID() + " and endpointGUID = " + GUID + " and endpointType = " + endpointType);

            if (conferenceName.length() > 200) {
                conferenceName = conferenceName.substring(0, 200);
                logger.warn("Chopping conferenceName since too long, new name: " + conferenceName);
            }

            String dbCallID = null;
            try {
                dbCallID = getJdbcTemplate().queryForObject(SELECT_CALLID_FOR_GUID, String.class, GUID);
            } catch (DataAccessException dae) {
                // not found
            }

            if (dbCallID != null) {
                if (!dbCallID.equals(uniqueCallID)) {
                    // if endpoint in different call than expected, delete the record
                    int rowsDeleted = getJdbcTemplate().update(DELETE_CONFERENCE_RECORD_FOR_GUID, GUID);
                    logger.warn("Deleted ConferenceRecord (rows deleted: "+  rowsDeleted + ") for same GUID but different call, GUID: " + GUID);
                    logger.warn(" uniqueCallID: [" + uniqueCallID + "] / in db: [" + dbCallID + "]");
                } else {
                    // if endpoint in same call as expected, no need to create a record
                    logger.debug("ConferenceRecord exists already for GUID: " + GUID + ", returning");
                    return 1;
                }
            }

            // if no record for endpoint, create one
            int affected = 0;
            Integer endpointID = getEndpointIDForGUID(GUID, endpointType);
            if (endpointID != 0) {
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("GUID", GUID);
                params.put("UniqueCallID", uniqueCallID);
                params.put("conferenceName", conferenceName);
                params.put("conferenceType", conferenceType);
                params.put("roomID", room.getRoomID());
                params.put("endpointID", endpointID);
                params.put("endpointType", endpointType);
                params.put("endpointCaller", endpointCaller);
                if(endpointType.equalsIgnoreCase("R")) {
                    params.put("tenantID", room.getTenantID());
                } else {
                    params.put("tenantID", getTenantIDForGUID(GUID, endpointType));
                }
                if (room.getRoomMuted() == 0) {
                    params.put("audio", 1);
                } else {
                    params.put("audio", 0);
                }

                if (room.getRoomVideoMuted() == 0) {
                    params.put("video", 1);
                } else {
                    params.put("video", 0);
                }
                params.put("speaker", room.getRoomSpeakerMuted() ^ 1); // reverse the value from RoomSpeakerMuted
                params.put("conferenceCreated", queue ? 0 : 1);
                affected = namedParamJdbcTemplate.update(INSERT_INTO_CONF_RECORD_QUERY, params);

                logger.debug("Insert ConferenceRecord, rows affected: " + affected + ", for GUID: " + GUID);

                return affected;
            } else {
                return endpointID;
            }
        }



    public int addEndpointToConferenceRecord(String uniqueCallID, String conferenceName, String conferenceType, Room room, String GUID, String endpointType, String endpointCaller) {
        return addEndpointToConferenceRecord(uniqueCallID, conferenceName, conferenceType, room, GUID, endpointType, endpointCaller, false);
    }

    public int addEndpointToConferenceRecordQueue(String uniqueCallID, String conferenceName, String conferenceType, Room room, String GUID, String endpointType, String endpointCaller) {
        return addEndpointToConferenceRecord(uniqueCallID, conferenceName, conferenceType, room, GUID, endpointType, endpointCaller, true);
    }

    /**
     * This method will return tenantID for endpointType=="D" or endpointType=="V"
     */
    @Override
    public int getTenantIDForGUID(String GUID, String endpointType) {

        if (endpointType.equalsIgnoreCase("R")) {
            return 0;
        }

        StringBuffer sqlstm = new StringBuffer();

        int retVal = 0;
        if (endpointType.equalsIgnoreCase("D")) { // Endpoint
            sqlstm = new StringBuffer(
                    "SELECT " +
                    "    CASE WHEN e.memberType = 'R' THEN " +
                    "        m.tenantID " +
                    "    ELSE " +
                    "        g.tenantID " +
                    "    END " +
                    "    as tenantID    " +
                    " FROM  Endpoints e  " +
                    " LEFT JOIN Member m ON (e.memberID=m.memberID AND e.memberType='R')" +
                    " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
                    " WHERE e.endpointGUID = ?"
               );
        }

        if (endpointType.equalsIgnoreCase("V")) { // VirtualEndpoint
            sqlstm.append(
                " SELECT" +
                "  tenantID" +
                " FROM" +
                "  VirtualEndpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );
        }

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                    new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs.getInt(1);
                        }
                    }, GUID
                );
        retVal = li.size() > 0 ? li.get(0) : 0;

        return retVal;
    }

    private static final String UPDATE_CONFERENCES =  " UPDATE" +
            "  Conferences" +
            " SET" +
            "  VRID = :VRID," +
            "  VRName = :VRName," +
            "  GroupID = :GroupID," +
            "  GroupName = :GroupName" +
            " WHERE" +
            "  conferenceID = :conferenceID";

    private static final String UPDATE_CONFERENCES_PARTICIPANTID =  " UPDATE" +
            "  Conferences" +
            " SET" +
            "  VRID = :VRID," +
            "  VRName = :VRName," +
            "  GroupID = :GroupID," +
            "  GroupName = :GroupName," +
            "  participantID = :participantID" +
            " WHERE" +
            "  conferenceID = :conferenceID";

    public void moveEndpointToConference(String GUID, ConferenceInfo conferenceInfo, String participantID) {
        logger.debug("Copy record from ConferenceRecord to Conferences for endpointGUID = " + GUID);

        ConferenceRecord record = null;
        StringBuffer select = new StringBuffer(
            " SELECT  " +
            "  GUID, UniqueCallID, conferenceName,  conferenceType,  roomID,  endpointID,  endpointType,  endpointCaller,  tenantID,  audio,  video, speaker" +
            " FROM" +
            "  ConferenceRecord" +
            " WHERE" +
            "  GUID = ?"
        );

        try {
            record = getJdbcTemplate().queryForObject(select.toString(),
                    BeanPropertyRowMapper.newInstance(ConferenceRecord.class), GUID);
        } catch (Exception ignored) {
            logger.error("Select failed for ConferenceRecord: ", ignored);
        }

        int conferenceID = 0;
		if (record != null) {
			record.setCreatedTime(new Date());
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(record);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParamJdbcTemplate.update(INSERT_INTO_CONF_QUERY, parameters, keyHolder);
			logger.debug("Insert copy to Conferences: " + GUID);
			conferenceID = keyHolder.getKey().intValue();
			logger.debug("New conferenceID = " + conferenceID);
			getJdbcTemplate().update(DELETE_FROM_CONF_RECORD, GUID);
			logger.debug("Deleted ConferenceRecord after copy: " + GUID);
		}

        if (conferenceID != 0 && conferenceInfo != null) {
            String update = UPDATE_CONFERENCES;

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("VRID", conferenceInfo.getVRID())
                .addValue("VRName", conferenceInfo.getVRName())
                .addValue("GroupID", conferenceInfo.getGroupID())
                .addValue("GroupName", conferenceInfo.getGroupName())
                .addValue("conferenceID", conferenceID);

            // update participantID when available ONLY
            if (!StringUtils.isBlank(participantID)) {
                update = UPDATE_CONFERENCES_PARTICIPANTID;
                ((MapSqlParameterSource) namedParameters).addValue("participantID", participantID);
            }

            int affected = namedParamJdbcTemplate.update(update, namedParameters);
            logger.debug("Rows affected for update to Conferences: " + affected);
        }
    }

    /**
     * Deletes the conference records from Conferences and ConferenceRecord tables.
     *
     * @param guid
     */
    //TODO - Remove Endpoint Type
    public Control removeEndpointFromConference(String GUID, String endpointType /*EndpointType not used - remove later*/) {
        logger.debug("Remove record from Conferences for endpointGUID = " + GUID + "  and endpointType = '" + endpointType + "' and return conference name");

        Control conf = null;

        StringBuffer sqlstm_select = new StringBuffer(
            " SELECT" +
            "  conferenceName," +
            "  conferenceType," +
            "  roomID" +
            " FROM" +
            "  Conferences" +
            " WHERE" +
            "  GUID = ?" +
            " UNION" +
            " SELECT" +
            "  conferenceName," +
            "  conferenceType," +
            "  roomID" +
            " FROM" +
            "  ConferenceRecord" +
            " WHERE" +
            "  GUID = ?"
        );

        List<Control> ls = getJdbcTemplate().query(sqlstm_select.toString(),
            new RowMapper<Control>() {
                public Control mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Control c = new Control();
                    c.setConferenceName(rs.getString("conferenceName"));
                    c.setConferenceType(rs.getString("conferenceType"));
                    c.setRoomID(rs.getInt("roomID"));
                    return c;
                }
            },
            GUID,
            GUID
        );

        if (ls.size() > 0) {
            conf = ls.get(0);
        }

        synchronized (this) {
            int affected = getJdbcTemplate().update(
                " DELETE c" +
                " FROM" +
                "  Conferences c" +
                " WHERE" +
                "  c.GUID = ?", GUID
            );
            if (affected == 0) {
                affected = getJdbcTemplate().update(
                    " DELETE cr" +
                    " FROM" +
                    "  ConferenceRecord cr" +
                    " WHERE" +
                    "  cr.GUID = ?", GUID
                );
            }
            if (logger.isDebugEnabled())
                logger.debug("Rows affected: " + affected);
        }

        if (conf != null && !conf.getConferenceName().equalsIgnoreCase("")) {
            conf.setNumOfParticipants(getCountParticipants(conf.getConferenceName()));
        }

        return conf;
    }

    private static final String DELETE_CONF_RECORD_BY_CONF_NAME = "DELETE FROM ConferenceRecord WHERE conferenceName = ?";

    public void removeConferenceRecord(String conferenceName) {
        logger.debug("Remove record from Conferences for conference name = " + conferenceName);

        StringBuffer sqlstm_delete = new StringBuffer();

        sqlstm_delete.append(
            " DELETE" +
            " FROM" +
            "  Conferences" +
            " WHERE" +
            "  conferenceName = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm_delete.toString(), conferenceName);
        logger.debug("Remove record from Conferences for conference name - Rows affected: " + affected);
        affected = getJdbcTemplate().update(DELETE_CONF_RECORD_BY_CONF_NAME, conferenceName);
        logger.debug("Remove record from ConferenceRecord for conference name - Rows affected: " + affected);
    }

    public int getEndpointIDForGUID(String GUID, String endpointType) {
        StringBuffer sqlstm = new StringBuffer();

        if (endpointType.equalsIgnoreCase("D")) { // Endpoint
            sqlstm.append(
                " SELECT" +
                "  endpointID" +
                " FROM" +
                "  Endpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );
        }

        if (endpointType.equalsIgnoreCase("V")) { // VirtualEndpoint
            sqlstm.append(
                " SELECT" +
                "  endpointID" +
                " FROM" +
                "  VirtualEndpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );
        }

        if (endpointType.equalsIgnoreCase("R")) { // RecorderEndpoint
            sqlstm.append(
                " SELECT" +
                "  endpointID" +
                " FROM" +
                "  RecorderEndpoints" +
                " WHERE" +
                "  endpointGUID = ?"
            );
        }

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, GUID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public String getGUIDForEndpointID(int endpointID, String endpointType) {
        StringBuffer sqlstm = new StringBuffer();

        if (endpointType.equalsIgnoreCase("D")) { // Endpoint
            sqlstm.append(
                " SELECT" +
                "  endpointGUID" +
                " FROM" +
                "  Endpoints" +
                " WHERE" +
                "  endpointID = ?"
            );
        }

        if (endpointType.equalsIgnoreCase("V")) { // VirtualEndpoint
            sqlstm.append(
                " SELECT" +
                "  endpointGUID" +
                " FROM" +
                "  VirtualEndpoints" +
                " WHERE" +
                "  endpointID = ?"
            );
        }

        if (endpointType.equalsIgnoreCase("R")) { // RecorderEndpoint
            sqlstm.append(
                " SELECT" +
                "  endpointGUID" +
                " FROM" +
                "  RecorderEndpoints" +
                " WHERE" +
                "  endpointID = ?"
            );
        }

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, endpointID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    public boolean isEndpointIDinRoomID(int endpointID, int roomID) {
        logger.debug("Check if Endpoint = " + endpointID + " in conference in Room for roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Conferences" +
            " WHERE" +
            "  endpointID = ?" +
            " AND" +
            "  roomID = ?"
        );

        int num = getJdbcTemplate().queryForObject(sqlstm.toString(), Integer.class, endpointID, roomID);
        return num > 0;
    }

    /**
     * Returns the GUID for the EndpointId/Room Id combination
     * @param endpointID
     * @param roomID
     * @return
     */
    public String getGUIDForEndpointIdInConf(int endpointID, int roomID) {
        logger.debug("get guid if EndpointId = " + endpointID + " is in room = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  guid" +
            " FROM" +
            "  Conferences" +
            " WHERE" +
            "  endpointID = ?" +
            " AND" +
            "  roomID = ?"
        );

        String guid = getJdbcTemplate().queryForObject(sqlstm.toString(), String.class, endpointID, roomID);
        return guid;
    }

    public int getMemberIDForEndpointID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  memberID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, endpointID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public String getMemberTypeForEndpointID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  memberType" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, endpointID
        );

        return ls.size() > 0 ? ls.get(0) : "D";
    }

    public int getEndpointStatus(String GUID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  status" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " UNION" +
            " SELECT" +
            "  status" +
            " FROM" +
            "  VirtualEndpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " UNION" +
            " SELECT" +
            "  status" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  endpointGUID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, GUID, GUID, GUID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public String getEndpointType(String GUID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  'D' as endpointType" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " UNION" +
            " SELECT" +
            "  'V' as endpointType" +
            " FROM" +
            "  VirtualEndpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " UNION" +
            " SELECT" +
            "  'R' as endpointType" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  endpointGUID = ?"
        );

        List<String> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, GUID, GUID, GUID
        );

        return li.size() > 0 ? li.get(0) : "D";
    }

    public String getEndpointIPaddress(String GUID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  CASE WHEN ipAddress IS NULL THEN '' ELSE ipAddress END as ipAddress" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointGUID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, GUID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    public int getRoomIDForEndpointID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.roomID" +
            " FROM" +
            "  Conferences c" +
            " WHERE c.endpointID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, endpointID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public static final String GET_CONFERENCE_RECORD_FOR_GUID = "SELECT * from ConferenceRecord c WHERE c.GUID = :guid";

    public ConferenceRecord getConferenceRecordForEndpointGUID(String endpointGUID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("guid", endpointGUID);

        List<ConferenceRecord> confRecords = namedParamJdbcTemplate.query(
                GET_CONFERENCE_RECORD_FOR_GUID, namedParameters, new BeanPropertyRowMapper<ConferenceRecord>(ConferenceRecord.class));

        if (confRecords.isEmpty()) {
            logger.warn("Expected, but none found, conference record for GUID: " + endpointGUID);
            return null;
        }

        if (confRecords.size() > 1) {
            logger.warn("Incorrect Conference Records ->" + confRecords);
        }

        logger.info("Found conference record for GUID: " + endpointGUID);
        return confRecords.get(0);
    }

    public int getRoomIDForEndpointGUIDConferenceRecord(String endpointGUID) {
        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                        "  c.roomID" +
                        " FROM" +
                        "  ConferenceRecord c" +
                        " WHERE c.GUID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, endpointGUID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public String getConferenceTypeForEndpointID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.conferenceType" +
            " FROM" +
            "  Conferences c" +
            " WHERE c.endpointID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, endpointID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    /*
    * THIS FUNCTION WILL USED ONLY FOR ROUND-ROBIN ALGORITHM
    * DO NOT USED FOR OTHER FUNCTIONALITIES
    */
    public int getRoomIDForEndpointIDRoundRobin(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.roomID" +
            " FROM" +
            "  ConferenceRecord c" +
            " WHERE c.endpointID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, endpointID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    /*
    * THIS FUNCTION WILL USED ONLY FOR ROUND-ROBIN ALGORITHM
    * DO NOT USED FOR OTHER FUNCTIONALITIES
    */
    public String getConferenceTypeForEndpointIDRoundRobin(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.conferenceType" +
            " FROM" +
            "  ConferenceRecord c" +
            " WHERE c.endpointID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, endpointID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    public String getGUIDForMemberID(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  endpointGUID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  memberID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, memberID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    public boolean isMemberInOwnPersonalRoom(String GUID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            " CASE WHEN r.roomID = c.roomID THEN 'true' ELSE 'false' END" +
            " FROM" +
            "  Conferences c" +
            "  INNER JOIN Endpoints e ON (c.endpointID = e.endpointID)" +
            "  INNER JOIN Room r ON (e.memberID = r.memberID AND r.`roomTypeID` = 1)" +
            " WHERE" +
            "  e.endpointGUID = ?" +
            " AND" +
            "  c.conferenceType = 'C'"
        );

        List<Boolean> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Boolean>() {
                public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getBoolean(1);
                }
            }, GUID
        );

        return ls.size() > 0 ? ls.get(0) : false;
    }

    public List<Control> getParticipants(int roomID, ControlFilter filter) {
        List<Control> list = getControls(roomID, filter);

        if (filter != null) {
            filter.setSort("name"); // TODO: federation can only sort by name for now
        }
        List<Control> fedlist = getFederationControls(roomID, filter);
        list.addAll(fedlist);

        return list;
    }

    public Long getCountParticipants(int roomID) {
        return getCountControls(roomID);
    }

    public Long getCountParticipants(String conferenceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  SUM(c) " +
            " FROM" +
            " (" +
            "   SELECT COUNT(0) as c" +
            "   FROM  Conferences c WHERE c.conferenceName =:confName AND  c.conferenceType IN (:confTypes)" +
            "   UNION ALL" +
            "   SELECT COUNT(0) as c" +
            "   FROM ConferenceRecord cr" +
            "   WHERE cr.conferenceName =:confName" +
            "   UNION ALL SELECT COUNT(0) as c FROM ExternalLinks el where el.toConferenceName =:confName" +
            " ) as num");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("confName", conferenceName);
        List<String> confTypes = new ArrayList<String>();
        confTypes.add("C");
        confTypes.add("F");
        params.put("confTypes", confTypes);
        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), params,
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Conference getUniqueIDofConference(String conferenceName) {

        StringBuffer sqlstm_select = new StringBuffer(
                " SELECT" +
                "  uniqueCallID, conferenceName, 1 as conferenceCreated" +
                " FROM" +
                "  Conferences" +
                " WHERE" +
                "  conferenceName =:confName" +
                " UNION" +
                " SELECT" +
                "  uniqueCallID, conferenceName, conferenceCreated" +
                " FROM" +
                "  ConferenceRecord" +
                " WHERE" +
                " conferenceName =:confName" +
                " UNION SELECT uniqueCallID, toConferenceName, 1 as conferenceCreated FROM ExternalLinks WHERE toConferenceName =:confName AND uniqueCallID is not null"
            );

        Map<String, String> params = new HashMap<String, String>();
        params.put("confName", conferenceName);

        List<Conference> ls = namedParamJdbcTemplate.query(sqlstm_select.toString(), params, BeanPropertyRowMapper.newInstance(Conference.class));
        return ls.size() > 0 ? ls.get(0) : null;
    }

    public List<Entity> getParticipants(int roomID, EntityFilter filter, User user) {
        int userID = 0;
        if (user != null) {
            userID = user.getMemberID();
        }

        List<Entity> rc;
        StringBuffer sqlstm = new StringBuffer(
             "SELECT " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "            r.roomID " +
             "        ELSE " +
             "            0 " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        CASE WHEN ve.entityID IS NOT NULL THEN " +
             "            ve.entityID " +
             "        ELSE " +
             "            0 " +
             "        END " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        CASE WHEN re.entityID IS NOT NULL THEN " +
             "            re.entityID " +
             "        ELSE " +
             "            0 " +
             "        END " +
             "    END " +
             "    as roomID, " +
             " " +
             " c.endpointID, " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "        m.memberID " +
             "        ELSE " +
             "            0 " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        0 " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        0 " +
             "    END " +
             "    as memberID, " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "            m.memberName " +
             "        ELSE " +
             "            g.guestName " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        ve.displayName " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        re.description " +
             "    END " +
             "    as name, " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "            r.roomExtNumber " +
             "        ELSE " +
             "            'Guest' " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        ve.`displayExt` " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        'Recorder' " +
             "    END " +
             "    as ext, " +
             "  " +
             " r.roomDescription,   " +
             " " +
             " CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned,   " +
             " " +
             " r.roomPIN,   " +
             " r.roomModeratorPIN, " +
             " r.roomKey,   " +
             " CASE WHEN r.roomLocked IS NULL THEN 0 ELSE 1 END as roomLocked,   " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "        r.roomTypeID " +
             "        ELSE " +
             "            0 " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        3 " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        0 " +
             "    END " +
             "    as roomTypeID,   " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        rt.roomType " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        'Legacy' " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        'Recorder' " +
             "    END " +
             "    as roomType,    " +
             " " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        ve.status " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        re.status " +
             "    END " +
             "    as memberStatus,    " +
             "  " +
             " CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID,   " +
             " CASE WHEN r.memberID = :memberID THEN 1 ELSE 0 END as roomOwner,   " +
             " CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE (c.roomID=r.roomID)) = gr.roomMaxUsers THEN 2        " +
             "      WHEN (SELECT COUNT(0) FROM Conferences c WHERE (c.roomID=r.roomID)) = 0 THEN 0 ELSE 1 END as roomStatus,   " +
             "              " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "        mt.tenantID " +
             "        ELSE " +
             "            gt.tenantID " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        vt.tenantID " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        COALESCE(rect.tenantID, 1) " +
             "    END " +
             "    as tenantID,    " +
             "               " +
             "    CASE WHEN c.endpointType = 'D' THEN " +
             "        CASE WHEN e.memberType = 'R' THEN " +
             "        mt.tenantName " +
             "        ELSE " +
             "            gt.tenantName " +
             "        END " +
             "    WHEN c.endpointType = 'V' THEN " +
             "        vt.tenantName " +
             "    WHEN c.endpointType = 'R' THEN " +
             "        COALESCE(rect.tenantName, 'Default') " +
             "    END " +
             "    as tenantName,    " +
             "  " +
             " c.audio,   " +
             " c.video,   " +
             " " +
             " CASE WHEN e.memberType = 'R' THEN " +
             "    CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END " +
             " ELSE " +
             "    CASE WHEN gt.tenantDialIn IS NULL THEN '' ELSE gt.tenantDialIn END " +
             " END " +
             "  as dialIn, " +
             " CASE WHEN c.endpointType = 'D' THEN" +
             "   0" +
             " WHEN c.endpointType = 'V' THEN" +
             "   0" +
             " WHEN c.endpointType = 'R' THEN" +
             "   re.webcast" +
             " END" +
             " as webcast," +
             " m.emailAddress" +
             " FROM  Conferences c  " +
             " LEFT JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType='D')" +
             " LEFT JOIN VirtualEndpoints ve ON (ve.endpointID=c.endpointID AND c.endpointType='V')" +
             " LEFT JOIN RecorderEndpoints re ON (re.endpointID=c.endpointID AND c.endpointType='R')" +
             " LEFT JOIN Member m ON (e.memberID=m.memberID AND e.memberType='R')" +
             " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
             " LEFT JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=1)" +
             " LEFT JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
             " LEFT JOIN Groups gr ON (gr.groupID=r.groupID)" +
             " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = :memberID)" +
             " LEFT JOIN Tenant mt ON (mt.tenantID=m.tenantID AND e.memberType='R')" +
             " LEFT JOIN Tenant gt ON (gt.tenantID=g.tenantID AND e.memberType='G')" +
             " LEFT JOIN Tenant vt ON (vt.tenantID=ve.tenantID)" +
             " LEFT JOIN Tenant rect ON (rect.tenantID=m.tenantID)" +
             " WHERE c.roomID = :roomID" +
             " AND  c.conferenceType = 'C'"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);
        namedParamsMap.put("memberID", userID);
        if (filter != null) {
            if(!StringUtils.isEmpty(filter.getQuery())) {
                sqlstm.append(" AND (" +
                        " m.memberName LIKE :memberName " +
                        " OR r.roomName LIKE :roomName" +
                        " OR g.guestName LIKE :guestName" +
                        " OR ve.displayName LIKE :displayName " +
                        " OR re.description LIKE :description" +
                        " OR r.roomExtNumber LIKE :roomExtNumber" +
                        ")");
                namedParamsMap.put("memberName", filter.getQuery()+"%");
                namedParamsMap.put("roomName", filter.getQuery()+"%");
                namedParamsMap.put("guestName", filter.getQuery()+"%");
                namedParamsMap.put("displayName", filter.getQuery()+"%");
                namedParamsMap.put("description", filter.getQuery()+"%");
                namedParamsMap.put("roomExtNumber", filter.getQuery()+"%");
            }

            if (!filter.getEntityType().equalsIgnoreCase("All")) {
                sqlstm.append(" AND rt.roomType = :roomType");
                namedParamsMap.put("roomType", filter.getEntityType());
            }
            if (!filter.getSortBy().equalsIgnoreCase("")) {
                if (filter.getSortBy().equalsIgnoreCase("memberStatusText")) {
                    sqlstm.append(" ORDER BY ").append("memberStatus").append(" ").append(filter.getDir());
                } else {
                    sqlstm.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
                }
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
        return rc;
    }

    public List<Entity> getLectureModeParticipants(int roomID, EntityFilter filter) {

        List<Entity> rc;
        StringBuffer sqlstm = new StringBuffer(
                "SELECT " +
                        "    CASE WHEN c.endpointType = 'D' THEN " +
                        "        CASE WHEN e.memberType = 'R' THEN " +
                        "            r.roomID " +
                        "        ELSE " +
                        "            0 " +
                        "        END " +
                        "    WHEN c.endpointType = 'V' THEN " +
                        "        CASE WHEN ve.entityID IS NOT NULL THEN " +
                        "            ve.entityID " +
                        "        ELSE " +
                        "            0 " +
                        "        END " +
                        "    WHEN c.endpointType = 'R' THEN " +
                        "        CASE WHEN re.entityID IS NOT NULL THEN " +
                        "            re.entityID " +
                        "        ELSE " +
                        "            0 " +
                        "        END " +
                        "    END " +
                        "    as roomID, " +
                        " " +
                        " c.endpointID, " +
                        " " +
                        "    CASE WHEN c.endpointType = 'D' THEN " +
                        "        CASE WHEN e.memberType = 'R' THEN " +
                        "            m.memberName " +
                        "        ELSE " +
                        "            g.guestName " +
                        "        END " +
                        "    WHEN c.endpointType = 'V' THEN " +
                        "        ve.displayName " +
                        "    WHEN c.endpointType = 'R' THEN " +
                        "        re.description " +
                        "    END " +
                        "    as name, " +
                        " " +
                        "    CASE WHEN c.endpointType = 'D' THEN " +
                        "        CASE WHEN e.memberType = 'R' THEN " +
                        "            r.roomExtNumber " +
                        "        ELSE " +
                        "            'Guest' " +
                        "        END " +
                        "    WHEN c.endpointType = 'V' THEN " +
                        "        ve.`displayExt` " +
                        "    WHEN c.endpointType = 'R' THEN " +
                        "        'Recorder' " +
                        "    END " +
                        "    as ext, " +
                        " " +
                        " " +
                        "    CASE WHEN c.endpointType = 'D' THEN " +
                        "        rt.roomType " +
                        "    WHEN c.endpointType = 'V' THEN " +
                        "        'Legacy' " +
                        "    WHEN c.endpointType = 'R' THEN " +
                        "        'Recorder' " +
                        "    END " +
                        "    as roomType,    " +
                        " " +
                        " c.audio,   " +
                        " c.video,   " +
                        " " +
                        " CASE WHEN c.endpointType = 'D' THEN" +
                        "   0" +
                        " WHEN c.endpointType = 'V' THEN" +
                        "   0" +
                        " WHEN c.endpointType = 'R' THEN" +
                        "   re.webcast" +
                        " END" +
                        " as webcast, " +
                        " c.handRaised as handRaised, " +
                        " c.handRaisedTime as handRaisedTime, " +
                        " c.presenter as presenter " +
                        " FROM  Conferences c  " +
                        " LEFT JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType='D')" +
                        " LEFT JOIN VirtualEndpoints ve ON (ve.endpointID=c.endpointID AND c.endpointType='V')" +
                        " LEFT JOIN RecorderEndpoints re ON (re.endpointID=c.endpointID AND c.endpointType='R')" +
                        " LEFT JOIN Member m ON (e.memberID=m.memberID AND e.memberType='R')" +
                        " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
                        " LEFT JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=1)" +
                        " LEFT JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
                        " WHERE c.roomID = :roomID" +
                        " AND  c.conferenceType = 'C'"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("roomID", roomID);
        if (filter != null) {
            sqlstm.append(" AND (" +
                    " m.memberName LIKE :memberName " +
                    " OR r.roomName LIKE :roomName" +
                    " OR g.guestName LIKE :guestName" +
                    " OR ve.displayName LIKE :displayName " +
                    " OR re.description LIKE :description" +
                    " OR r.roomExtNumber LIKE :roomExtNumber" +
                    ")");
            namedParamsMap.put("memberName", filter.getQuery()+"%");
            namedParamsMap.put("roomName", filter.getQuery()+"%");
            namedParamsMap.put("guestName", filter.getQuery()+"%");
            namedParamsMap.put("displayName", filter.getQuery()+"%");
            namedParamsMap.put("description", filter.getQuery()+"%");
            namedParamsMap.put("roomExtNumber", filter.getQuery()+"%");

            if (!filter.getEntityType().equalsIgnoreCase("All")) {
                sqlstm.append(" AND rt.roomType = :roomType");
                namedParamsMap.put("roomType", filter.getEntityType());
            }
            if (!filter.getSortBy().equalsIgnoreCase("")) {
                if (filter.getSortBy().equalsIgnoreCase("memberStatusText")) {
                    sqlstm.append(" ORDER BY ").append("memberStatus").append(" ").append(filter.getDir());
                } else {
                    sqlstm.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
                }
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
        return rc;
    }

    public int getEndpointIDForMemberID(int memberID) {
        logger.debug("Get endpointID from Endpoints for linked memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  endpointID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  memberID = ?" +
            " AND" +
            "  memberType = 'R'"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, memberID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public void addRouter(String routerName, String description) {
        logger.debug("Update and add record in Routers for routerName = " + routerName);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Routers" +
            " SET" +
            "  routerName = :routerName," +
            "  description = :description," +
            "  active = 1" +
            " WHERE" +
            "  routerName = :routerName"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("routerName", routerName)
            .addValue("description", description);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        if (affected == 0) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                .withTableName("Routers")
                .usingGeneratedKeyColumns("routerID");

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("routerName", routerName);
            params.put("description", description);
            params.put("active", 1);

            SqlParameterSource insertParameters = new MapSqlParameterSource(params);
            Number newID = insert.executeAndReturnKey(insertParameters);

            logger.debug("New routerID = " + newID.intValue());
        }
    }

    public void resetRouters() {
        logger.debug("Update records in Routers for set active = 0 (except 'Default')");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Routers" +
            " SET" +
            "  active = :active" +
            " WHERE" +
            "  routerName != 'Default'"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("active", 0);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }

    public int updateVirtualEndpointInfo(String GUID, String displayName, String displayExt, int entityID) {
        logger.debug("Update records in VirtualEndpoints for set displayName = " + displayName + " and displayExt = " + displayExt + " and entityID = " + entityID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  VirtualEndpoints" +
            " SET" +
            "  displayName = :displayName," +
            "  displayExt = :displayExt," +
            "  entityID = :entityID" +
            " WHERE" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("displayName", displayName)
            .addValue("displayExt", displayExt)
            .addValue("entityID", entityID)
            .addValue("GUID", GUID);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public final static String GET_GATEWAYPREFIX_BY_PREFIX = "SELECT prefixID, serviceID, gatewayID, prefix, direction " +
            " FROM GatewayPrefixes  WHERE direction=1 and (SELECT locate(prefix, :toExt) = 1) " +
            " AND tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = :tenantID) order by length(prefix) desc limit 1";

    public final static String GET_GATEWAYPREFIX_DEFAULT = "SELECT prefixID, serviceID, gatewayID, prefix, direction " +
            " FROM GatewayPrefixes  WHERE direction=1 and upper(prefix) = 'DEFAULT'" +
            " AND tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = :tenantID)";

    public GatewayPrefix getGatewayPrefixForCall(String toExt, int tenantID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID )
                .addValue("toExt", toExt);

        GatewayPrefix gwPrefix = null;

        List<GatewayPrefix> prefixes = namedParamJdbcTemplate.query(GET_GATEWAYPREFIX_BY_PREFIX, namedParameters,
                new BeanPropertyRowMapper<GatewayPrefix>(GatewayPrefix.class));

        if (prefixes != null && prefixes.size() > 0) {
            return prefixes.get(0);
        } else {
            prefixes = namedParamJdbcTemplate.query(GET_GATEWAYPREFIX_DEFAULT, namedParameters,
                    new BeanPropertyRowMapper<GatewayPrefix>(GatewayPrefix.class));
            if (prefixes != null && prefixes.size() > 0) {
                return prefixes.get(0);
            }
        }

        return gwPrefix;
    }

    public final static String GET_GUID_FOR_RINGING = "SELECT ve.endpointGUID " +
            " FROM VirtualEndpoints ve, ConferenceRecord cr " +
            " WHERE " +
            " ve.serviceID = :serviceID AND ve.tenantID = :tenantID " +
            " AND ve.displayExt = :displayExt AND ve.direction = 1 " + // can only cancel outgoing calls
            " AND ve.status IN (3, 12) " + // Ringing, WaitJoinConfirm
            " AND cr.GUID = ve.endpointGUID AND cr.roomID = :roomID";

    public String getVirtualGUIDForRingingCall(int gwServiceId, String toExt,int tenantID, int roomID) {

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("serviceID", gwServiceId)
                .addValue("tenantID", tenantID )
                .addValue("displayExt", toExt)
                .addValue("roomID", roomID);

        try {
            String GUID =  namedParamJdbcTemplate.queryForObject(GET_GUID_FOR_RINGING, namedParameters, String.class);
            logger.warn("VirtualGUID IN USE: " + GUID + ", for roomID: " + roomID);
            return GUID;
        } catch (Exception e) {
            // it's acceptable if result not found
            logger.info("VirtualGUID NOT IN USE for call to " + toExt + " for roomID: " + roomID);
            logger.info("Data exception: " + e.getMessage());
        }

        return null;
    }

    public synchronized String getVirtualGUIDForLegacyDevice(Member member, Object arg, int tenantID) {
        String rc = "";
        VirtualEndpoint ve = null;
        String legacy = member.getRoomExtNumber();

        if (arg != null) {
            if(arg instanceof VirtualEndpoint) {
                VirtualEndpoint previous = (VirtualEndpoint)arg;

                ArrayList<String> usedGateWayIDs = this.usedGW.get(legacy);
                if (usedGateWayIDs == null) {
                    usedGateWayIDs = new ArrayList<String>();
                }

                if (!usedGateWayIDs.contains(previous.getGatewayID())) {
                    usedGateWayIDs.add(previous.getGatewayID());
                }

                this.usedGW.put(legacy, usedGateWayIDs);
                ve = selectVirtualEndpointForPrefix(member.getRoomExtNumber(), tenantID, usedGateWayIDs);
            }
        } else {
            ve = selectVirtualEndpointForPrefix(member.getRoomExtNumber(), tenantID);
        }

        if (ve == null) {
            this.usedGW.remove(legacy);
            if (selectVirtualEndpointForBusyPrefix(member.getRoomExtNumber(), tenantID) != null) {
                return rc;
            } else {
                ve = selectVirtualEndpointForDefaultPrefix(tenantID);
            }
        }

        if (ve != null) {
            updateVirtualEndpointInfo(ve.getEndpointGUID(), member.getMemberName(), member.getRoomExtNumber(), member.getRoomID());
            rc = ve.getEndpointGUID();
        }

        return rc;
    }

    public boolean canTenantUseGatewayforAddress(List<Tenant> canCallToTenant, String address, int tenantID) {
        boolean rc = false;
        VirtualEndpoint ve = selectVirtualEndpointForPrefix(address, tenantID);

        if(ve == null){
            ve = selectVirtualEndpointForDefaultPrefix(tenantID);
        }

        if (ve != null) {
            for (Tenant tenant : canCallToTenant) {
                if (ve.getTenantID() == tenant.getTenantID()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }

    private VirtualEndpoint selectVirtualEndpointForPrefix(String prefix, int tenantID) {
        logger.debug("Get record from VirtualEndpoints for registered prefix = " + prefix);

        VirtualEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.displayExt," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.tenantID," +
            "  t.tenantName," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " INNER JOIN Tenant t ON (t.tenantID = ve.tenantID)" +
            " WHERE" +
            "  ve.direction = 1" + // toLegacy
            " AND" +
            "  ve.status = 1" + // Online
            " AND" +
            "  (ve.displayName IS NULL OR ve.displayName = '')" +
            " AND" +
            "  (ve.displayExt IS NULL OR ve.displayExt = '')" +
            " AND" +
            "  t.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ?)" +
            " ORDER BY ve.updateTime ASC, ve.endpointID ASC, ve.prefix DESC"
        );

        List<VirtualEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(VirtualEndpoint.class), tenantID);

        for (VirtualEndpoint virtualEndpoint : lprefix) {
            String registered_prefix = virtualEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = virtualEndpoint;
                break;
            }
        }

        return rc;
    }

    private VirtualEndpoint selectVirtualEndpointForPrefix(String prefix, int tenantID, ArrayList<String> gateways) {
        logger.debug("Get record from VirtualEndpoints for registered prefix = " + prefix + " and not for gatewayID = " + gateways.toString());

        VirtualEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.displayExt," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.tenantID," +
            "  t.tenantName," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " INNER JOIN Tenant t ON t.tenantID = ve.tenantID" +
            " WHERE" +
            "  ve.direction = 1" + // toLegacy
            " AND" +
            "  ve.status = 1" + // Online
            " AND" +
            "  (ve.displayName IS NULL OR ve.displayName = '')" +
            " AND" +
            "  (ve.displayExt IS NULL OR ve.displayExt = '')" +
            " AND" +
            "  t.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ?)" +
            " AND" +
            "  ve.gatewayID NOT IN ("
        );

        for (String gw : gateways) {
            sqlstm.append("'").append(gw).append("'").append(",");
        }
        sqlstm.deleteCharAt(sqlstm.lastIndexOf(","));

        sqlstm.append(") ORDER BY ve.updateTime ASC, ve.endpointID ASC, ve.prefix DESC");

        List<VirtualEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(VirtualEndpoint.class), tenantID);

        for (VirtualEndpoint virtualEndpoint : lprefix) {
            String registered_prefix = virtualEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = virtualEndpoint;
                break;
            }
        }

        return rc;
    }

    private VirtualEndpoint selectVirtualEndpointForDefaultPrefix(int tenantID) {
        logger.debug("Get record from VirtualEndpoints for registered default prefix");

        VirtualEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.displayExt," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.tenantID," +
            "  t.tenantName," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " INNER JOIN Tenant t ON t.tenantID = ve.tenantID" +
            " WHERE" +
            "  ve.direction = 1" + // toLegacy
            " AND" +
            "  ve.status = 1" + // Online
            " AND" +
            "  (ve.displayName IS NULL OR ve.displayName = '')" +
            " AND" +
            "  (ve.displayExt IS NULL OR ve.displayExt = '')" +
            " AND" +
            "  t.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ?)" +
            " ORDER BY ve.updateTime ASC, ve.endpointID ASC, ve.prefix DESC"
        );

        List<VirtualEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(VirtualEndpoint.class), tenantID);

        for (VirtualEndpoint virtualEndpoint : lprefix) {
            String registered_prefix = virtualEndpoint.getPrefix();

            if ("default".equalsIgnoreCase(registered_prefix)) {
                rc = virtualEndpoint;
                break;
            }
        }

        return rc;
    }

    private VirtualEndpoint selectVirtualEndpointForBusyPrefix(String prefix, int tenantID) {
        logger.debug("Get record from VirtualEndpoints for registered busy prefix = " + prefix);

        VirtualEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.displayExt," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.tenantID," +
            "  t.tenantName," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " INNER JOIN Tenant t ON t.tenantID = ve.tenantID" +
            " WHERE" +
            "  ve.direction = 1" + // toLegacy
            " AND" +
            "  (ve.status <> 1 AND ve.status <> 0)" + // Anything other then Online and Offline
            " AND" +
            "  (ve.displayName IS NULL OR ve.displayName = '')" +
            " AND" +
            "  (ve.displayExt IS NULL OR ve.displayExt = '')" +
            " AND" +
            "  t.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ?)" +
            " ORDER BY ve.updateTime ASC, ve.endpointID ASC, ve.prefix DESC"
        );

        List<VirtualEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(VirtualEndpoint.class), tenantID);

        for (VirtualEndpoint virtualEndpoint : lprefix) {
            String registered_prefix = virtualEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = virtualEndpoint;
                break;
            }
        }

        return rc;
    }

    public VirtualEndpoint getVirtualEndpointForEndpointID(int endpointID) {
        logger.debug("Get record from VirtualEndpoints for endpointID = " + endpointID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.displayExt," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.tenantID," +
            "  t.tenantName," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " INNER JOIN Tenant t ON t.tenantID = ve.tenantID" +
            " WHERE" +
            "  ve.endpointID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(VirtualEndpoint.class), endpointID);
    }

    public static final String UPDATE_ENDPOINT_AUDIO_FOR_ALL_C = " UPDATE Conferences c SET c.audio = :flag  WHERE c.roomID = :roomID";
    public static final String UPDATE_ENDPOINT_AUDIO_FOR_ALL_CR = " UPDATE ConferenceRecord c SET c.audio = :flag  WHERE c.roomID = :roomID";

    public void updateEndpointAudioForAll(int roomID, int flag) {
        logger.debug("Update records in Conferences for RoomID = " + roomID + " and set audio to = " + flag);

        SqlParameterSource updateParameters = new MapSqlParameterSource()
                .addValue("roomID", roomID)
                .addValue("flag", flag);

        int affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_AUDIO_FOR_ALL_CR, updateParameters);
        logger.debug("Rows affected CR: " + affected);
        affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_AUDIO_FOR_ALL_C, updateParameters);
        logger.debug("Rows affected C: " + affected);

    }


    public static final String UPDATE_ENDPOINT_AUDIO_FOR_GUID_C = "UPDATE Conferences c SET c.audio = :flag  WHERE c.GUID = :GUID";
    public static final String UPDATE_ENDPOINT_AUDIO_FOR_GUID_CR = "UPDATE ConferenceRecord c SET c.audio = :flag  WHERE c.GUID = :GUID";

    public void updateEndpointAudio(String GUID, int flag) {
        logger.debug("Update records in Conferences for Endpoint = " + GUID + " and set audio to = " + flag);

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("GUID", GUID)
            .addValue("flag", flag);

        int affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_AUDIO_FOR_GUID_CR, updateParameters);
        logger.debug("updateEndpointAudio Rows affected CR: " + affected + ", for GUID: " + GUID);
        affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_AUDIO_FOR_GUID_C, updateParameters);
        logger.debug("updateEndpointAudio Rows affected C: " + affected + ", for GUID: " + GUID);

    }

    public static final String UPDATE_ENDPOINT_VIDEO_FOR_GUID_C = "UPDATE Conferences c SET c.video = :flag  WHERE c.GUID = :GUID";
    public static final String UPDATE_ENDPOINT_VIDEO_FOR_GUID_CR = "UPDATE ConferenceRecord c SET c.video = :flag  WHERE c.GUID = :GUID";

    public void updateEndpointVideo(String GUID, int flag) {
        logger.debug("Update records in Conferences for Endpoint = " + GUID + " and set video to = " + flag);

        SqlParameterSource updateParameters = new MapSqlParameterSource()
                .addValue("GUID", GUID)
                .addValue("flag", flag);

        int affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_VIDEO_FOR_GUID_CR, updateParameters);
        logger.debug("Rows affected CR: " + affected);
        affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_VIDEO_FOR_GUID_C, updateParameters);
        logger.debug("Rows affected C: " + affected);
    }

    public int cleanGuestsAndEndpoints() {
        logger.debug("Remove record from Endpoints where memberID = 0 and in status = 0 more then 5 min");

        // Regular users
        StringBuffer sqlstmRegular = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  memberID = 0" +
            " AND" +
            "  status = 0" +
            " AND" +
            "  TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5 "
        );

        int affected = getJdbcTemplate().update(sqlstmRegular.toString());
        logger.debug("Rows affected: " + affected);

        logger.debug("Remove record from Guests where guestsID not listed in Endpoints");

        // Guests ghosts
        StringBuffer sqlstmGuestGhosts = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Guests" +
            " WHERE" +
            "  guestID NOT IN (SELECT memberID FROM Endpoints WHERE memberType = 'G')" +
            " AND" +
            "  TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5 "
        );

        affected = getJdbcTemplate().update(sqlstmGuestGhosts.toString());
        logger.debug("Rows affected: " + affected);

        // Other Guests ghosts
        StringBuffer sqlstmOtherGuestGhosts = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  memberType = 'G'" +
            " AND" +
            "  status = 0" +
            " AND" +
            "  TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5 "
        );

        affected = getJdbcTemplate().update(sqlstmOtherGuestGhosts.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }


/*
    public boolean canTenantUseRecorderforPrefix(List<Tenant> canCallToTenant, String prefix) {
        boolean rc = false;
        RecorderEndpoint ve = selectRecorderEndpointForPrefix(prefix);

        if(ve == null){
            ve = selectRecorderEndpointForPrefix(prefix);
        }

        if (ve != null) {
            for (Tenant tenant : canCallToTenant) {
                if (ve.getTenantID() == tenant.getTenantID()) {
                    rc = true;
                    break;
                }
            }
        }
        return rc;
    }
*/

    public synchronized RecorderEndpoint getRecorderGUIDForPrefix(String prefix, Member member, int webcast, Object arg) {
        RecorderEndpoint re = null;

        if (arg != null) {
            if(arg instanceof RecorderEndpoint) {
                RecorderEndpoint previous = (RecorderEndpoint)arg;
                re = selectRecorderEndpointForPrefix(prefix, previous, member.getTenantID());
            }
        } else {
            re = selectRecorderEndpointForPrefix(prefix, member.getTenantID());
        }

        if (re == null) {
            if (selectRecorderEndpointForBusyPrefix(prefix, member.getTenantID()) != null) {
                return re;
            } else {
                re = selectRecorderEndpointForDefaultPrefix(member.getTenantID());
            }
        }

        if (re != null) {
            updateRecorderEndpointInfo(re.getEndpointGUID(), member.getMemberID(), webcast);
        }

        return re;
    }

    private RecorderEndpoint selectRecorderEndpointForPrefix(String prefix, int tenantID) {
        logger.debug("Get record from RecorderEndpoints for registered prefix = " + prefix + " for tenantID = " + tenantID);

        RecorderEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  re.endpointID," +
            "  re.serviceID," +
            "  re.recID," +
            "  re.prefix," +
            "  re.description," +
            "  COALESCE(re.entityID, 0) as entityID," +
            "  re.endpointGUID," +
            "  re.status," +
            "  re.updateTime" +
            " FROM" +
            "  RecorderEndpoints re" +
            " WHERE" +
            "  re.status = 1" + // Online
            " AND" +
            "  (re.entityID IS NULL OR re.entityID = 0)" +
            " AND" +
            "  re.serviceID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)" +
            " ORDER BY re.updateTime ASC, re.endpointID ASC, re.prefix DESC"
        );

        List<RecorderEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), tenantID);

        for (RecorderEndpoint recorderEndpoint : lprefix) {
            String registered_prefix = recorderEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = recorderEndpoint;
                break;
            }
        }

        return rc;
    }

    private RecorderEndpoint selectRecorderEndpointForPrefix(String prefix, RecorderEndpoint previous, int tenantID) {
        logger.debug("Get record from RecorderEndpoints for registered prefix = " + prefix + " and not for recID = " + previous.getRecID() + " for tenantID = " + tenantID);

        RecorderEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  re.endpointID," +
            "  re.serviceID," +
            "  re.recID," +
            "  re.prefix," +
            "  re.description," +
            "  COALESCE(re.entityID, 0) as entityID," +
            "  re.endpointGUID," +
            "  re.status," +
            "  re.updateTime" +
            " FROM" +
            "  RecorderEndpoints re" +
            " WHERE" +
            "  re.status = 1" + // Online
            " AND" +
            "  (re.entityID IS NULL OR re.entityID = 0)" +
            " AND" +
            "  re.recID <> ?" +
            " AND" +
            "  re.serviceID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)" +
            " ORDER BY re.updateTime ASC, re.endpointID ASC, re.prefix DESC"
        );

        List<RecorderEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), previous.getRecID(), tenantID);

        for (RecorderEndpoint recorderEndpoint : lprefix) {
            String registered_prefix = recorderEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = recorderEndpoint;
                break;
            }
        }

        return rc;
    }

    private RecorderEndpoint selectRecorderEndpointForDefaultPrefix(int tenantID) {
        logger.debug("Get record from RecorderEndpoints for registered default prefix");

        RecorderEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  re.endpointID," +
            "  re.serviceID," +
            "  re.recID," +
            "  re.prefix," +
            "  re.description," +
            "  COALESCE(re.entityID, 0) as entityID," +
            "  re.endpointGUID," +
            "  re.status," +
            "  re.updateTime" +
            " FROM" +
            "  RecorderEndpoints re" +
            " WHERE" +
            "  re.status = 1" + // Online
            " AND" +
            "  (re.entityID IS NULL OR re.entityID = 0)" +
            " AND" +
            "  re.serviceID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)" +
            " ORDER BY re.updateTime ASC, re.endpointID ASC, re.prefix DESC"
        );

        List<RecorderEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), tenantID);

        for (RecorderEndpoint recorderEndpoint : lprefix) {
            String registered_prefix = recorderEndpoint.getPrefix();

            if ("default".equalsIgnoreCase(registered_prefix)) {
                rc = recorderEndpoint;
                break;
            }
        }

        return rc;
    }

    private RecorderEndpoint selectRecorderEndpointForBusyPrefix(String prefix, int tenantID) {
        logger.debug("Get record from RecorderEndpoints for registered busy prefix = " + prefix);

        RecorderEndpoint rc = null;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  re.endpointID," +
            "  re.serviceID," +
            "  re.recID," +
            "  re.prefix," +
            "  re.description," +
            "  COALESCE(re.entityID, 0) as entityID," +
            "  re.endpointGUID," +
            "  re.status," +
            "  re.updateTime" +
            " FROM" +
            "  RecorderEndpoints re" +
            " WHERE" +
            "  (re.status <> 1 AND re.status <> 0)" + // Anything other then Online and Offline
            " AND" +
            "  (re.entityID IS NULL OR re.entityID = 0)" +
            " AND" +
            "  re.serviceID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)" +
            " ORDER BY re.updateTime ASC, re.endpointID ASC, re.prefix DESC"
        );

        List<RecorderEndpoint> lprefix = getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), tenantID);

        for (RecorderEndpoint recorderEndpoint : lprefix) {
            String registered_prefix = recorderEndpoint.getPrefix();

            if (prefix.startsWith(registered_prefix)) {
                rc = recorderEndpoint;
                break;
            }
        }

        return rc;
    }

    public int updateRecorderEndpointInfo(String GUID, int entityID, int webcast) {
        logger.debug("Update records in RecorderEndpoints for set entityID = " + entityID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  RecorderEndpoints" +
            " SET" +
            "  entityID = :entityID," +
            "  webcast = :webcast" +
            " WHERE" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("entityID", entityID)
            .addValue("webcast", webcast)
            .addValue("GUID", GUID);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public RecorderEndpoint getRecorderEndpointForEndpointID(int endpointID) {
        logger.debug("Get record from RecorderEndpoints for endpointID = " + endpointID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  re.endpointID," +
            "  re.serviceID," +
            "  re.recID," +
            "  re.prefix," +
            "  re.description," +
            "  COALESCE(re.entityID, 0) as entityID," +
            "  re.endpointGUID," +
            "  re.status," +
            "  re.updateTime" +
            " FROM" +
            "  RecorderEndpoints re" +
            " WHERE" +
            "  re.endpointID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), endpointID);
    }

    public String getRoomNameForGUID(String guid) {
        logger.debug("Entering getRoomNameForGUID() of ConferenceDaoJdbcImpl");
        String GET_ROOM_NAME_BY_GUID = "SELECT ro.roomName FROM ConferenceRecord cr, Room ro WHERE cr.GUID =:guid and cr.roomID = ro.roomID";
        Map<String, String> params = new HashMap<String, String>();
        params.put("guid", guid);
        List<String> roomNames = namedParamJdbcTemplate.query(
                GET_ROOM_NAME_BY_GUID, params, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rs.getString("roomName");
                    }

                });
        logger.debug("Exiting getRoomNameForGUID() of ConferenceDaoJdbcImpl");
        return roomNames.size() > 0 ? roomNames.get(0) : null;
    }

    public String getRoomProfile(int profileID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  profileXML" +
            " FROM" +
            "  Profile" +
            " WHERE" +
            "  profileID = :profileID"
        );

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("profileID", profileID);

        List<String> xmlList = namedParamJdbcTemplate.query(sqlstm.toString(), params,
            new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("profileXML");
                }
            });

        return xmlList.size() > 0 ? xmlList.get(0) : null;
    }

    public Control getConferenceNameFromConferenceRecord(String GUID, String endpointType) {
        logger.debug("Get record from ConferenceRecord for endpointGUID = " + GUID + "  and endpointType = '" + endpointType + "' and return conference name");

        Control conf = null;

        StringBuffer sqlstm_select = new StringBuffer(
            " SELECT  " +
            "  conferenceName," +
            "  conferenceType," +
            "  roomID" +
            " FROM" +
            "  ConferenceRecord" +
            " WHERE" +
            "  GUID = ?"
        );

        List<Control> ls = getJdbcTemplate().query(sqlstm_select.toString(),
                new RowMapper<Control>() {
                    public Control mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Control c = new Control();
                        c.setConferenceName(rs.getString("conferenceName"));
                        c.setConferenceType(rs.getString("conferenceType"));
                        c.setRoomID(rs.getInt("roomID"));
                        return c;
                    }
                }, GUID
        );

        if (ls.size() > 0) {
            conf = ls.get(0);
        }

        return conf;
    }

    private static final String CONF_NAME_BY_GUID = "SELECT conferenceName FROM ConferenceRecord where GUID =:guid UNION SELECT conferenceName FROM Conferences WHERE GUID =:guid";

    /**
     * Returns the ConferenceName associated with the GUID
     *
     * @param guid
     * @return
     */
    public String getConfNameByGuid(String guid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("guid", guid);
        List<ConferenceRecord> confRecords = namedParamJdbcTemplate.query(
                CONF_NAME_BY_GUID, params,
                BeanPropertyRowMapper
                        .newInstance(ConferenceRecord.class));
        if (confRecords.isEmpty()) {
            return null;
        }
        if (confRecords.size() > 1) {
            logger.warn("Incorrect Conference Records ->" + confRecords);
        }
        return confRecords.get(0).getConferenceName();
    }

    private static final String CONF_UNIQUEID_BY_GUID = "SELECT UniqueCallID FROM ConferenceRecord where GUID =:guid UNION SELECT UniqueCallID FROM Conferences WHERE GUID =:guid";

    /**
     * Returns the ConferenceName associated with the GUID
     *
     * @param guid
     * @return
     */
    public String getUniqueCallIDByGuid(String guid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("guid", guid);
        List<ConferenceRecord> confRecords = namedParamJdbcTemplate.query(
                CONF_UNIQUEID_BY_GUID, params,
                BeanPropertyRowMapper
                        .newInstance(ConferenceRecord.class));
        if (confRecords.isEmpty()) {
            return null;
        }
        if (confRecords.size() > 1) {
            logger.warn("Incorrect Conference Records ->" + confRecords);
        }
        return confRecords.get(0).getUniqueCallID();
    }

    /**
     * Query to check if the memberid & roomid are in a conference
     */
    private static final String IS_MEMBER_IN_ROOM = "SELECT count(1) FROM Conferences c, Endpoints e where e.memberID =:memberId and e.endpointID = c.endpointID and c.roomID =:roomId";

    /**
     * Checks if the Member has joined the specified Room.
     *
     * @param memberID
     * @param roomID
     * @return
     */
    public boolean isMemberPresentInRoom(int memberID, int roomID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberID);
        params.put("roomId", roomID);
        int count = namedParamJdbcTemplate.queryForObject(IS_MEMBER_IN_ROOM, params, Integer.class);
        return count > 0;
    }

    private static final String DELETE_CONFERENCES_BY_GUID = "DELETE c FROM Conferences c  WHERE c.GUID = ?";

    /**
     * Cleans the Conferences table during link Endpoint
     * call.
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConference(String endpointGuid) {
        logger.debug("Delete record from Conferences for endpointGUID -"
                + endpointGuid);

        int affected = getJdbcTemplate().update(
                DELETE_CONFERENCES_BY_GUID, endpointGuid);

        return affected;

    }

    private static final String DELETE_CONFERENCE_REC_BY_GUID = "DELETE cr FROM ConferenceRecord cr  WHERE cr.GUID = ?";

    /**
     * Cleans the ConferenceRecord table during link Endpoint
     * call.
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConferenceRecord(String endpointGuid) {
        logger.debug("Delete record from ConferenceRecord for endpointGUID -"
                + endpointGuid);
        int affected = getJdbcTemplate().update(
                DELETE_CONFERENCE_REC_BY_GUID, endpointGuid);
        return affected;
    }

    /**
     * SQL to clean up conference record by guid and confname
     */
    private static final String DELETE_CONF_REC_BY_GUID_CONFNAME = "DELETE cr FROM ConferenceRecord cr WHERE cr.GUID = ? and cr.conferenceName = ?";

    /**
     * Cleans the ConferenceRecord table by guid and confname.<br>
     * This is used when the two party conference call fails<br>
     *
     * @param endpointGuid
     * @return
     */
    public int cleanupConferenceRecord(String endpointGuid, String confName) {
        logger.debug("Delete record from ConferenceRecord for endpointGUID - {}, conf name - {}", endpointGuid, confName);
        int affected = getJdbcTemplate().update(DELETE_CONF_REC_BY_GUID_CONFNAME, endpointGuid, confName);
        return affected;
    }

    public static final String GET_PRESENTER_IN_ROOM = "SELECT participantID from Conferences WHERE" +
            " presenter = 1 AND roomID = :roomID";
    @Override
    public String getPresenterInRoom(int roomID) {
        String participantID =  null;
        Map<String, Object> params = new HashMap<>();
        params.put("roomID", roomID);
        try {
            participantID = (String) namedParamJdbcTemplate.queryForObject(GET_PRESENTER_IN_ROOM, params, String.class);
        } catch (DataAccessException dae) {
            // no presenter found
        }
        return participantID;
    }

    public static final String GET_PARTICIPANT_ID_FOR_GUID = "SELECT participantID from Conferences WHERE GUID = :guid";

    public String getParticipantIdForGUID(String GUID) {
        String participantID =  null;
        Map<String, Object> params = new HashMap<>();
        params.put("guid", GUID);
        try {
            participantID = (String) namedParamJdbcTemplate.queryForObject(GET_PARTICIPANT_ID_FOR_GUID, params, String.class);
        } catch (DataAccessException dae) {
            // no presenter found
        }
        return participantID;
    }

    public static final String GET_ROOM_BY_PRESENTER = "SELECT roomID from Conferences WHERE" +
            " presenter = 1 AND GUID = :guid";

    @Override
    public int getRoomIDByPresenterGuid(String guid) {
        if (guid == null) {
            return 0;
        }
        int roomID =  0;
        Map<String, Object> params = new HashMap<>();
        params.put("guid", guid);
        try {
            roomID = namedParamJdbcTemplate.queryForObject(GET_ROOM_BY_PRESENTER, params, Integer.class);
        } catch (DataAccessException dae) {
            // no presenter found
        }
        return roomID;
    }

    public static final String IS_HAND_RAISED = "SELECT COUNT(0) FROM Conferences c WHERE GUID = :guid AND c.handRaised = 1";
    @Override
    public boolean isHandRaised(String endpointGUID) {
        Map<String, Object> params = new HashMap<>();
        params.put("guid", endpointGUID);
        int count = namedParamJdbcTemplate.queryForObject(IS_HAND_RAISED, params, Integer.class);
        return count == 1;
    }

    public static final String IS_VIDEO_STOPPED = "SELECT COUNT(0) FROM Conferences c WHERE GUID = :guid AND c.video = 0";
    @Override
    public boolean isVideoStopped(String endpointGUID) {
        Map<String, Object> params = new HashMap<>();
        params.put("guid", endpointGUID);
        int count = namedParamJdbcTemplate.queryForObject(IS_VIDEO_STOPPED, params, Integer.class);
        return count == 1;
    }

    public static final String UNLOCK_ROOM_IF_ROOM_OWNER_JOINED = "update Room set roomLocked = 0  where roomID = (select roomID from " +
    "    (select c.roomID from Conferences c, Endpoints e, Room r " +
    "     where c.GUID = :guid and c.GUID = e.endpointGUID and " +
    "     e.memberID = r.memberID and e.memberType = 'R' and c.roomID = r.roomID) as ownerRoomID)";

    public void unlockRoomIfRoomOwnerJoined(String guid) {
        Map<String, Object> params = new HashMap<>();
        params.put("guid", guid);
        try {
            getJdbcTemplate().update(UNLOCK_ROOM_IF_ROOM_OWNER_JOINED, params);
        } catch (DataAccessException dae) {
            logger.warn("Exception unlocking room because room owner joined: " + dae.getMessage());
        }
    }

    public static final String LOCK_OWNER_ROOM_IF_ROOM_OWNER_LEAVING =  "update Room set roomLocked = 1  where roomID = (select roomID from " +
            "    (select c.roomID from Conferences c, Endpoints e, Room r " +
            "     where c.GUID = :guid and c.GUID = e.endpointGUID and " +
            "     e.memberID = r.memberID and e.memberType = 'R' and c.roomID = r.roomID) as ownerRoomID)";

    public void lockOwnerRoomsIfRoomOwnerLeaving(String guid) {
        Map<String, Object> params = new HashMap<>();
        params.put("guid", guid);
        try {
            namedParamJdbcTemplate.update(LOCK_OWNER_ROOM_IF_ROOM_OWNER_LEAVING, params);
        } catch (DataAccessException dae) {
            logger.warn("Exception locking owner room because room owner left: " + dae.getMessage());
        }

    }
    
    public static final String GET_ROOM_ID_FOR_CONFERENCE = "SELECT roomID from Conferences WHERE conferenceID = :conferenceID";
    
    @Override
    public int getRoomIdForConference(int conferenceId) {
    	int roomID = 0;
    	Map<String, Object> params = new HashMap<>();
    	params.put("conferenceID", conferenceId);
    
    	try {
    		roomID = namedParamJdbcTemplate.queryForObject(GET_ROOM_ID_FOR_CONFERENCE, params, Integer.class);
    	} catch (DataAccessException dae) {
    		// info logging, most of the time we will get 0 results
    		logger.info("Exception (for conferenceId: " + conferenceId + ") getting room ID: " + dae.getMessage());
    	}
    	return roomID;
    }

    public static final String GET_CONFERENCES_FOR_TENANT = "SELECT conferenceName from Conferences WHERE tenantID = :tenantId";

    @Override
    public List<String> getConferencesForTenant(int tenantId) {

        List<String> conferences = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        try {
            List<Map<String, Object>> rows = namedParamJdbcTemplate.queryForList(GET_CONFERENCES_FOR_TENANT, params);
            for (Map<String, Object> row : rows) {
                conferences.add((String) row.get("conferenceName"));
            }
        } catch (DataAccessException dae) {
            logger.warn("Exception getting conferences for tenant: " + tenantId + ", exception: " + dae.getMessage());
        }
        return conferences;

    }

    public static final String CLEAR_STALE_CONFERENCE_RECORDS = "DELETE FROM ConferenceRecord " +
            " WHERE conferenceCreated = 0 AND TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5";

    public int clearStaleConferenceRecords() {
        logger.debug("Remove record from ConferenceRecord with conferenceCreated=0 more than 5 minutes");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        int affected = namedParamJdbcTemplate.update(CLEAR_STALE_CONFERENCE_RECORDS, paramMap);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public static final String WAITING_ROOM_STOP_CHECK = "SELECT r.roomId " +
            " FROM Room r, Conferences c, Member m, Endpoints e, TenantConfiguration tc " +
            " WHERE c.GUID = :guid AND c.roomID = r.roomID " +
            " AND r.lectureMode = 1 AND m.memberID = r.memberID AND e.memberID = m.memberID " +
            " AND e.endpointGUID = c.GUID AND m.tenantID = tc.tenantID AND tc.waitingRoomsEnabled = 1 " +
            " AND tc.waitUntilOwnerJoins = 1 AND c.conferenceType <> 'P'";

    /**
     * we try to do everything in one query to make it quick
     *  nb: query also checks if it is room owner (endpoint memberid = room memberid)
     * returns room id if waiting room needs to be stopped
     * otherwise returns 0
     */

    public int getRoomIDForWaitingRoomStop(String guid) {
        int roomID = 0;
        Map<String, Object> params = new HashMap<>();
        params.put("guid", guid);
        try {
            roomID = namedParamJdbcTemplate.queryForObject(WAITING_ROOM_STOP_CHECK, params, Integer.class);
        } catch (DataAccessException dae) {
            // info logging, most of the time we will get 0 results
            logger.info("Exception (for guid: " + guid + ") getting room ID for waiting room stop: " + dae.getMessage());
        }
        return roomID;
    }

    public static final String UPDATE_ENDPOINT_SPEAKER_STATUS_GUID_C = "UPDATE Conferences c SET c.speaker = :flag  WHERE c.guid = :guid";

    public static final String UPDATE_ENDPOINT_SPEAKER_STATUS_GUID_CR = "UPDATE ConferenceRecord c SET c.speaker = :flag  WHERE c.GUID = :guid";

    public static final String UPDATE_ALL_ENDPOINT_SPEAKER_STATUS_C = "UPDATE Conferences c SET c.speaker = :flag  WHERE c.roomid = :roomid";

    public static final String UPDATE_ALL_ENDPOINT_SPEAKER_STATUS_CR = "UPDATE ConferenceRecord c SET c.speaker = :flag  WHERE c.roomid = :roomid";

    /**
     * Updates the flag for Endpoint's speaker.
     *
     * @param guid
     * @param flag
     * @return
     */
    @Override
    public int updateEndpointSpeakerStatus(String guid, int flag) {
        logger.debug("Update records in Conferences for Endpoint = " + guid + " and set speaker to = " + flag);

        SqlParameterSource updateParameters = new MapSqlParameterSource().addValue("guid", guid).addValue("flag", flag);

        int affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_SPEAKER_STATUS_GUID_CR, updateParameters);
        logger.debug("updateEndpointSpeakerStatus Rows affected ConferenceRecord: " + affected + ", for GUID: " + guid);
        affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_SPEAKER_STATUS_GUID_C, updateParameters);
        logger.debug("updateEndpointSpeakerStatus Rows affected Conferences: " + affected + ", for GUID: " + guid);

        return affected;
    }

    /**
     * Updates the speaker status for all Endpoints in the room.
     * @param roomId
     * @param flag
     * @return
     */
    @Override
    public int updateEndpointSpeakerStatusAll(int roomId, int flag) {
        logger.debug("Update records in Conferences for room = " + roomId + " and set speaker to = " + flag);

        SqlParameterSource updateParameters = new MapSqlParameterSource().addValue("roomid", roomId).addValue("flag",
                flag);

        int affected = namedParamJdbcTemplate.update(UPDATE_ALL_ENDPOINT_SPEAKER_STATUS_CR, updateParameters);
        logger.debug("updateEndpointSpeakerStatusAll Rows affected in ConferenceRecord: " + affected + ", for room: " + roomId);
        affected = namedParamJdbcTemplate.update(UPDATE_ALL_ENDPOINT_SPEAKER_STATUS_C, updateParameters);
        logger.debug("updateEndpointSpeakerStatusAll Rows affected in Conferences: " + affected + ", for room: " + roomId);

        return affected;
    }
}