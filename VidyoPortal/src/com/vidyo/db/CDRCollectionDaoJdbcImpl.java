package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.vidyo.bo.CDRinfo;

public class CDRCollectionDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements ICDRCollectionDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(CDRCollectionDaoJdbcImpl.class.getName());

    public CDRinfo getCDRinfo(String GUID) {
        logger.debug("Collect info for CDR for endpoint - " + GUID);

        CDRinfo info = null;

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   m.username" +
            "  ELSE" +
            "   CONCAT_WS('-', 'Guest', g.guestName, g.username)" +
            "  END as CallerName," +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   tm.tenantName" +
            "  ELSE" +
            "   tg.tenantName" +
            "  END as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  Endpoints e" +
            " INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = 'R'" +
            " LEFT JOIN Tenant tm ON tm.tenantID = m.tenantID AND e.memberType = 'R'" +
            " LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = 'G'" +
            " LEFT JOIN Tenant tg ON tg.tenantID = g.tenantID AND e.memberType = 'G'" +
            " WHERE" +
            "  e.endpointGUID = ?" +

            " UNION" +

            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  CONCAT_WS('-', e.prefix, e.displayName) as CallerName," +
            //"  e.displayName as CallerName," +
            "  t.tenantName as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  VirtualEndpoints e" +
            " INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Tenant t ON t.tenantID = e.tenantID" +
            " WHERE" +
            "  e.endpointGUID = ?" +

            " UNION" +

            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  e.description as CallerName," +
            "  t.tenantName as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  RecorderEndpoints e" +
            " INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Tenant t ON t.tenantID = 1" +
            " WHERE" +
            "  e.endpointGUID = ?"
        );

        try {
            info = getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(CDRinfo.class), GUID, GUID, GUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return info;
    }

    public void insertCDRtoConferenceCall (CDRinfo info) {
        logger.debug("Add record into ConferenceCall");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("ConferenceCall")
            .usingGeneratedKeyColumns("CallID");


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ConferenceName", info.getConferenceName());
        params.put("CallerName", info.getCallerName());
        params.put("TenantName", info.getTenantName());
        params.put("JoinTime", new Date());
        params.put("LeaveTime", null);
        params.put("CallState", info.getCallState());

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);
        Number newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New CallID = " + newID.intValue());
    }

    public void updateCDRinConferenceCall (CDRinfo info) {
        logger.debug("Update records in ConferenceCall");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ConferenceCall" +
            " SET" +
            "  LeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
            "  ConferenceName = :ConferenceName" +
            " AND" +
            "  CallerName = :CallerName" +
            " AND" +
            "  TenantName = :TenantName" +
            " AND" +
            "  CallState = 'IN PROGRESS'"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("ConferenceName", info.getConferenceName())
            .addValue("CallerName", info.getCallerName())
            .addValue("TenantName", info.getTenantName())
            .addValue("CallState", info.getCallState())
            .addValue("Time", new Date());

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }


    public void insertCDRtoPointToPointCall (CDRinfo info) {
        logger.debug("Add record into PointToPointCall");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  CallID" +
            " FROM" +
            "  PointToPointCall" +
            " WHERE" +
            "  ConferenceName = :confName" +
            " AND" +
            "  CallState NOT IN (:callStates)"
        );
        Map<String, Object> namedParams = new HashMap<String, Object>();
        namedParams.put("confName", info.getConferenceName());
        List<String> callStates = new ArrayList<String>();
        callStates.add("NO ANSWER");
        callStates.add("REJECTED");
        callStates.add("COMPLETED");
        callStates.add("CANCELED");
        callStates.add("SERVER RESTART");
        namedParams.put("callStates", callStates);

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, namedParams
        );

        int CallID = li.size() > 0 ? li.get(0) : 0;

        if (CallID == 0) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                .withTableName("PointToPointCall")
                .usingGeneratedKeyColumns("CallID");

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("ConferenceName", info.getConferenceName());
            if (info.getCaller().equalsIgnoreCase("Y")) {
                params.put("CallerName", info.getCallerName());
                params.put("CallerTenantName", info.getTenantName());
                params.put("CallerJoinTime", new Date());
                params.put("CallerLeaveTime", null);
            } else {
                params.put("CalleeName", info.getCallerName());
                params.put("CalleeTenantName", info.getTenantName());
                params.put("CalleeJoinTime", new Date());
                params.put("CalleeLeaveTime", null);
            }
            params.put("CallState", info.getCallState());

            SqlParameterSource namedParameters = new MapSqlParameterSource(params);
            Number newID = insert.executeAndReturnKey(namedParameters);

            logger.debug("New CallID = " + newID.intValue());
        } else {

            StringBuffer update = new StringBuffer(
                " UPDATE" +
                "  PointToPointCall" +
                " SET"
            );

            if (info.getCaller().equalsIgnoreCase("Y")) {
                update.append(
                "  CallerName = :CallerName," +
                "  CallerTenantName = :TenantName," +
                "  CallerJoinTime = :Time," +
                "  CallState = :CallState"
                );
            } else {
                update.append(
                "  CalleeName = :CallerName," +
                "  CalleeTenantName = :TenantName," +
                "  CalleeJoinTime = :Time," +
                "  CallState = :CallState"
                );
            }

            update.append(
                " WHERE" +
                "  ConferenceName = :ConferenceName" +
                " AND" +
                "  CallID = :CallID"
            );

            SqlParameterSource updateParameters = new MapSqlParameterSource()
                .addValue("ConferenceName", info.getConferenceName())
                .addValue("CallerName", info.getCallerName())
                .addValue("TenantName", info.getTenantName())
                .addValue("Time", new Date())
                .addValue("CallState", info.getCallState())
                .addValue("CallID", CallID);

            int affected = getNamedParameterJdbcTemplate().update(update.toString(), updateParameters);
            logger.debug("Rows affected: " + affected);
        }
    }

    public void updateCDRinPointToPointCall (CDRinfo info) {
        logger.debug("Update records in PointToPointCall");

        StringBuffer update = new StringBuffer(
            " UPDATE" +
            "  PointToPointCall" +
            " SET"
        );

        if (info.getCaller().equalsIgnoreCase("Y")) {
            update.append(
            "  CallerLeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
            "  ConferenceName = :ConferenceName" +
            " AND" +
            "  CallerName = :CallerName" +
            " AND" +
            "  CallerTenantName = :TenantName" +
            " AND" +
            "  CallerLeaveTime IS NULL"
            );
        } else {
            update.append(
            "  CalleeLeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
            "  ConferenceName = :ConferenceName" +
            " AND" +
            "  CalleeName = :CallerName" +
            " AND" +
            "  CalleeTenantName = :TenantName" +
            " AND" +
            "  CalleeLeaveTime IS NULL"
            );
        }

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("ConferenceName", info.getConferenceName())
            .addValue("CallerName", info.getCallerName())
            .addValue("TenantName", info.getTenantName())
            .addValue("Time", new Date())
            .addValue("CallState", info.getCallState());

        int affected = getNamedParameterJdbcTemplate().update(update.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }

    public void updateCDRinPointToPointCallForCallee (CDRinfo info) {
        logger.debug("Update records in PointToPointCall");

        StringBuffer update = new StringBuffer(
            " UPDATE" +
            "  PointToPointCall" +
            " SET" +
            "  CallerLeaveTime = :Time," +
            "  CalleeLeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
            "  ConferenceName = :ConferenceName" +
            " AND" +
            "  CallState = 'RINGING'" +
            " AND" +
            "  CalleeName = :CallerName" +
            " AND" +
            "  CalleeTenantName = :TenantName"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
                .addValue("ConferenceName", info.getConferenceName())
                .addValue("CallerName", info.getCallerName())
                .addValue("TenantName", info.getTenantName())
                .addValue("Time", new Date())
                .addValue("CallState", info.getCallState());

        int affected = getNamedParameterJdbcTemplate().update(update.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }

    public void updateCDRinProgressConferenceCall() {
        logger.debug("Update records in ConferenceCall");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ConferenceCall" +
            " SET" +
            "  LeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
			"  CallState IN ('IN PROGRESS','RINGING','ALERTING')"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("Time", new Date())
            .addValue("CallState", "SERVER RESTART");

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }

    public void updateCDRinProgressP2PCall() {
        logger.debug("Update records in PointToPointCall");

        StringBuffer update = new StringBuffer(
            " UPDATE" +
            "  PointToPointCall" +
            " SET" +
            "  CallerLeaveTime = :Time," +
            "  CalleeLeaveTime = :Time," +
            "  CallState = :CallState" +
            " WHERE" +
			"  CallState IN ('IN PROGRESS','RINGING','ALERTING')"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("Time", new Date())
            .addValue("CallState", "SERVER RESTART");

        int affected = getNamedParameterJdbcTemplate().update(update.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);
    }

	public void updateCDRinProgressConferenceCall2() {
		logger.debug("Update records in ConferenceCall2");

		StringBuffer sqlstm = new StringBuffer(
			" UPDATE" +
			"  ConferenceCall2" +
			" SET" +
			"  LeaveTime = :Time," +
			"  CallState = :CallState" +
			" WHERE" +
			"  CallState IN ('IN PROGRESS','RINGING','ALERTING')"
		);

		SqlParameterSource updateParameters = new MapSqlParameterSource()
			.addValue("Time", new Date())
			.addValue("CallState", "SERVER RESTART");

		int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
		logger.debug("Rows affected: " + affected);
	}

    public int cleanConferences() {
        logger.debug("Remove all record from Conferences on start VM");

        // Clean up the table
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Conferences"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        // reset counter
        /*sqlstm = new StringBuffer(
            "ALTER TABLE Conferences AUTO_INCREMENT=1"
        );

        affected = getJdbcTemplate().update(sqlstm.toString());*/
        logger.debug("Rows affected: " + affected);

        // Clean up the table
        sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  ConferenceRecord"
        );

        affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int resetVirtualEndpoints() {
        logger.debug("Set status of all Virtual Endpoints to 'Offline'");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  VirtualEndpoints" +
            " SET" +
            "  status = 0,"+
            " sequenceNum = 0"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int resetEndpoints() {
        logger.debug("Set status of all Endpoints to 'Offline'");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  status = 0, "  +
            "  authorized = 0," +
            "  sequenceNum = 0"
		);

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int resetRecorderEndpoints() {
        logger.debug("Set status of all Recorder Endpoints to 'Offline'");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  RecorderEndpoints" +
            " SET" +
            "  status = 0," +
            " sequenceNum = 0"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int cleanFederations() {
        logger.debug("Remove all record from Federations on start VM");

        // Clean up the table
        StringBuffer sqlstmFederations = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Federations"
        );

        int affected = getJdbcTemplate().update(sqlstmFederations.toString());
        logger.debug("Rows affected: " + affected);

        StringBuffer sqlstmExternalLinks = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  ExternalLinks"
        );

        affected = getJdbcTemplate().update(sqlstmExternalLinks.toString());
        logger.debug("Rows affected: " + affected);

        StringBuffer sqlstmFederationConferences = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  FederationConferences"
        );

        affected = getJdbcTemplate().update(sqlstmFederationConferences.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    /**
     * Returns the CDR Info from the Conferences Table
     * @param GUID
     * @return
     */
    public CDRinfo getCDRinfoFromConference(String GUID) {
        logger.debug("Collect info for CDR for endpoint from Conferences Table {} ", GUID);

        CDRinfo info = null;

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   m.username" +
            "  ELSE" +
            "   CONCAT_WS('-', 'Guest', g.guestName, g.username)" +
            "  END as CallerName," +
            "  CASE WHEN e.memberType = 'R' THEN" +
            "   tm.tenantName" +
            "  ELSE" +
            "   tg.tenantName" +
            "  END as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  Endpoints e" +
            " INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = 'R'" +
            " LEFT JOIN Tenant tm ON tm.tenantID = m.tenantID AND e.memberType = 'R'" +
            " LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = 'G'" +
            " LEFT JOIN Tenant tg ON tg.tenantID = g.tenantID AND e.memberType = 'G'" +
            " WHERE" +
            "  e.endpointGUID = ?" +

            " UNION" +

            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  CONCAT_WS('-', e.prefix, e.displayName) as CallerName," +
            //"  e.displayName as CallerName," +
            "  t.tenantName as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  VirtualEndpoints e" +
            " INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Tenant t ON t.tenantID = e.tenantID" +
            " WHERE" +
            "  e.endpointGUID = ?" +

            " UNION" +

            " SELECT" +
            "  c.conferenceName as ConferenceName," +
            "  e.description as CallerName," +
            "  t.tenantName as TenantName," +
            "  c.conferenceType as ConferenceType," +
            "  c.endpointType as EndpointType," +
            "  c.endpointCaller as Caller" +
            " FROM" +
            "  RecorderEndpoints e" +
            " INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
            " LEFT JOIN Tenant t ON t.tenantID = 1" +
            " WHERE" +
            "  e.endpointGUID = ?"
        );

        try {
            info = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(CDRinfo.class), GUID, GUID, GUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return info;
    }

	/**
	 * SQL to get the total CDR count
	 */
	private static final String GET_TOTAL_CDR_COUNT = "SELECT COUNT(CallID) from ConferenceCall";

	/**
	 * Returns the total cdr(v1) count
	 *
	 * @return total count
	 */
	@Override
	public int getTotalCdrCount() {
		return getJdbcTemplate().queryForObject(GET_TOTAL_CDR_COUNT, Integer.class);
	}

	/**
	 * SQL to delete the oldest n number of records from CDR table
	 */
	private static final String DELETE_CDR = "DELETE FROM ConferenceCall ORDER BY CallID ASC LIMIT :limit";

	/**
	 * Deletes the oldest CDR records based on the limit passed. The limit
	 * presents the number of records to be deleted.
	 *
	 *
	 * @param limit
	 * @return
	 */
	@Override
	public int deleteCdr(int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit", limit);
		int deleteCount = getNamedParameterJdbcTemplate().update(DELETE_CDR, params);
		return deleteCount;
	}
}