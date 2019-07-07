package com.vidyo.db;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.utils.PortalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CDRCollection2DaoJdbcImpl extends NamedParameterJdbcDaoSupport implements ICDRCollection2Dao {

    protected final Logger logger = LoggerFactory.getLogger(CDRCollection2DaoJdbcImpl.class.getName());

	private static final String CONFERENCES_RECORD_QUERY =  "SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   m.username" +
			"  ELSE" +
			"   'Guest'" +
			"  END as CallerID," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   m.memberName" +
			"  ELSE" +
			"   CONCAT_WS('-', g.guestName, g.username)" +
			"  END as CallerName," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   tm.tenantName" +
			"  ELSE" +
			"   tg.tenantName" +
			"  END as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  WHEN c.conferenceType = 'F' THEN" +
			"   'IC'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   CASE WHEN m.roleID = 4 THEN" + // VidyoRoom
			"    'R'" +
			"   WHEN m.roleID = 6 THEN" + // Legacy
			"    'L'" +
			"   ELSE" +
			"    'D'" +
			"   END" +
			"  ELSE" +
			"   'G'" +
			"  END as EndpointType," +

			"  c.endpointCaller as Caller," +

			"  CASE WHEN c.endpointCaller = 'Y' THEN" +
			"   'O'" +
			"  ELSE" +
			"   'I'" +
			"  END as Direction," +

			"  NULL as GWID," +
			"  NULL as GWPrefix," +

			"  e.referenceNumber as referenceNumber, " +
			"  e.applicationName as applicationName, " +
			"  e.applicationVersion as applicationVersion, " +
			"  e.applicationOs as applicationOs, " +
			"  e.deviceModel as deviceModel, " +
			"  e.endpointPublicIPAddress as endpointPublicIPAddress, " +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   CASE WHEN m.roleID = 6 THEN" +
			"    'L'" +
			"   ELSE" +
			"    'U'" +
			"   END" +
			"  ELSE" +
			"   'G'" +
			"  END as AccessType," +

			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" mr.username as RoomOwner," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   rm.roomExtNumber" +
			"  ELSE" +
			"   NULL" +
			"  END as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +


			" FROM" +
			"  Endpoints e" +
			" INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = 'R'" +
			" LEFT JOIN Tenant tm ON tm.tenantID = m.tenantID AND e.memberType = 'R'" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member mr ON mr.memberID = r.memberID" +
			" LEFT JOIN Room rm ON rm.memberID = m.memberID AND rm.roomTypeID = 1" +
			" LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = 'G'" +
			" LEFT JOIN Tenant tg ON tg.tenantID = g.tenantID AND e.memberType = 'G'" +
			" WHERE" +
			"  e.endpointGUID = :guid" +

			" UNION" +

			" SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  e.displayExt as CallerID," +
			"  e.displayName as CallerName," +

			"  t.tenantName as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  'L' as EndpointType," +  // Legacy
			"  c.endpointCaller as Caller," +

			"  CASE WHEN e.direction = 0 THEN" +
			"   'I'" +
			"  ELSE" +
			"   'O'" +
			"  END as Direction," +

			"  e.gatewayID as GWID," +
			"  e.prefix as GWPrefix," +

			"  '' as referenceNumber, " +
			"  'VidyoGateway' as applicationName, " +
			"  '' as applicationVersion, " +
			"  '' as applicationOs, " +
			"  e.deviceModel as deviceModel, " +
			"  e.endpointPublicIPAddress as endpointPublicIPAddress,  " +

			" 'L' as AccessType," +
			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" m.username as RoomOwner," +
            " NULL as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +
			" FROM" +
			"  VirtualEndpoints e" +
			" INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member m ON m.memberID = r.memberID" +
			" LEFT JOIN Tenant t ON t.tenantID = e.tenantID" +
			" WHERE" +
			"  e.endpointGUID = :guid" +

			" UNION" +

			" SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  e.prefix as CallerID," +
			"  e.description as CallerName," +

			"  t.tenantName as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  'C' as EndpointType," + // Call Recorded via VidyoReplay/Recorder
			"  c.endpointCaller as Caller," +

			"  'O' as Direction," +

			"  NULL as GWID," +
			"  NULL as GWPrefix," +

			"  '' as referenceNumber, " +
	        "  'VidyoReplay' as applicationName, " +
			"  '' as applicationVersion, " +
			"  '' as applicationOs, " +
			"  '' as deviceModel, " +
			"  '' as endpointPublicIPAddress,  " +

			" 'R' as AccessType," +
			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" m.username as RoomOwner," +
            " NULL as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +

			" FROM" +
			"  RecorderEndpoints e" +
			" INNER JOIN ConferenceRecord c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member m ON m.memberID = r.memberID" +
			" LEFT JOIN Tenant t ON t.tenantID = c.tenantID" +
			" WHERE" +
			"  e.endpointGUID = :guid";

	private static final String CONFERENCES_QUERY = "SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   m.username" +
			"  ELSE" +
			"   'Guest'" +
			"  END as CallerID," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   m.memberName" +
			"  ELSE" +
			"   CONCAT_WS('-', g.guestName, g.username)" +
			"  END as CallerName," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   tm.tenantName" +
			"  ELSE" +
			"   tg.tenantName" +
			"  END as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  WHEN c.conferenceType = 'F' THEN" +
			"   'IC'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   CASE WHEN m.roleID = 4 THEN" + // VidyoRoom
			"    'R'" +
			"   WHEN m.roleID = 6 THEN" + // Legacy
			"    'L'" +
			"   ELSE" +
			"    'D'" +
			"   END" +
			"  ELSE" +
			"   'G'" +
			"  END as EndpointType," +

			"  c.endpointCaller as Caller," +

			"  CASE WHEN c.endpointCaller = 'Y' THEN" +
			"   'O'" +
			"  ELSE" +
			"   'I'" +
			"  END as Direction," +

			"  NULL as GWID," +
			"  NULL as GWPrefix," +

			"  e.referenceNumber as referenceNumber, " +
			"  e.applicationName as applicationName, " +
			"  e.applicationVersion as applicationVersion, " +
			"  e.applicationOs as applicationOs, " +
			"  e.deviceModel as deviceModel, " +
			"  e.endpointPublicIPAddress as endpointPublicIPAddress, " +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   CASE WHEN m.roleID = 6 THEN" +
			"    'L'" +
			"   ELSE" +
			"    'U'" +
			"   END" +
			"  ELSE" +
			"   'G'" +
			"  END as AccessType," +

			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" mr.username as RoomOwner," +

			"  CASE WHEN e.memberType = 'R' THEN" +
			"   rm.roomExtNumber" +
			"  ELSE" +
			"   NULL" +
			"  END as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +

			" FROM" +
			"  Endpoints e" +
			" INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = 'R'" +
			" LEFT JOIN Tenant tm ON tm.tenantID = m.tenantID AND e.memberType = 'R'" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member mr ON mr.memberID = r.memberID" +
			" LEFT JOIN Room rm ON rm.memberID = m.memberID AND rm.roomTypeID = 1" +
			" LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = 'G'" +
			" LEFT JOIN Tenant tg ON tg.tenantID = g.tenantID AND e.memberType = 'G'" +
			" WHERE" +
			"  e.endpointGUID = :guid" +

			" UNION" +

			" SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  e.displayExt as CallerID," +
			"  e.displayName as CallerName," +

			"  t.tenantName as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  'L' as EndpointType," +  // Legacy
			"  c.endpointCaller as Caller," +

			"  CASE WHEN e.direction = 0 THEN" +
			"   'I'" +
			"  ELSE" +
			"   'O'" +
			"  END as Direction," +

			"  e.gatewayID as GWID," +
			"  e.prefix as GWPrefix," +

			"  '' as referenceNumber, " +
			"  'VidyoGateway' as applicationName, " +
			"  '' as applicationVersion, " +
			"  '' as applicationOs, " +
			"  e.deviceModel as deviceModel, " +
			"  e.endpointPublicIPAddress as endpointPublicIPAddress,  " +

			" 'L' as AccessType," +
			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" m.username as RoomOwner," +
			" NULL as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +

			" FROM" +
			"  VirtualEndpoints e" +
			" INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member m ON m.memberID = r.memberID" +
			" LEFT JOIN Tenant t ON t.tenantID = e.tenantID" +
			" WHERE" +
			"  e.endpointGUID = :guid" +

			" UNION" +

			" SELECT" +
			"  c.UniqueCallID as UniqueCallID," +
			"  c.conferenceName as ConferenceName," +

			"  e.prefix as CallerID," +
			"  e.description as CallerName," +

			"  t.tenantName as TenantName," +

			"  CASE WHEN c.conferenceType = 'P' THEN" +
			"   'D'" +
			"  ELSE" +
			"   'C'" +
			"  END as ConferenceType," +

			"  'C' as EndpointType," + // Call Recorded via VidyoReplay/Recorder
			"  c.endpointCaller as Caller," +

			"  'O' as Direction," +

			"  NULL as GWID," +
			"  NULL as GWPrefix," +

			"  '' as referenceNumber, " +
			"  'VidyoReplay' as applicationName, " +
			"  '' as applicationVersion, " +
			"  '' as applicationOs, " +
			"  '' as deviceModel, " +
			"  '' as endpointPublicIPAddress,  " +

			" 'R' as AccessType," +
			"  CASE WHEN r.roomTypeID = 1 THEN 'M' " +
			"       WHEN r.roomTypeID = 2 THEN 'P' " +
			"       WHEN r.roomTypeID = 3 THEN 'L' " +
			"       WHEN r.roomTypeID = 4 THEN 'S' " +
			"  ELSE '-' " +
			"  END as RoomType," +

			" m.username as RoomOwner," +
			" NULL as Extension, " +
			" c.roomID, "+
			" c.endpointID as participantId, " +
			" c.audio as audioState, " +
			" c.video as videoState " +

			" FROM" +
			"  RecorderEndpoints e" +
			" INNER JOIN Conferences c ON e.endpointID = c.endpointID" +
			" LEFT JOIN Room r ON r.roomID = c.roomID" +
			" LEFT JOIN Member m ON m.memberID = r.memberID" +
			" LEFT JOIN Tenant t ON t.tenantID = c.tenantID" +
			" WHERE" +
			"  e.endpointGUID = :guid";

	private static final String UPDATE_CONFERENCE_COMPLETION_CODE = "UPDATE ConferenceCall2" +
			" SET CallCompletionCode = :callCompletionCode " +
			" WHERE UniqueCallID = :uniqueCallID AND CallCompletionCode = '0' AND LeaveTime IS NULL" ;

	private static final String UPDATE_ENDPOINT_COMPLETION_CODE =  "UPDATE ConferenceCall2" +
			" SET CallCompletionCode = :callCompletionCode " +
			" WHERE UniqueCallID = :uniqueCallID AND " +
			" EndpointGUID = :endpointGUID";

    public CDRinfo2 getCDRFromConferenceRecordTable(String GUID) {
        CDRinfo2 info = null;

        try {
			SqlParameterSource namedParameters = new MapSqlParameterSource("guid", GUID);
            info = getNamedParameterJdbcTemplate().queryForObject(
					CONFERENCES_RECORD_QUERY,
					namedParameters,BeanPropertyRowMapper.newInstance(CDRinfo2.class)
					);
			if (info != null) {
				info.setEndpointGUID(GUID);
			}
            logger.info("Looking in ConferenceRecord for endpoint GUID - " + GUID + " / FOUND");
        } catch (DataAccessException e) {
            logger.debug("Looking in ConferenceRecord for endpoint GUID - " + GUID + " / NOT FOUND / " + e.getMessage());
		}

        return info;
    }

	public CDRinfo2 getCDRFromConferencesTable(String GUID) {
		CDRinfo2 info = null;

		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource("guid", GUID);
			info = getNamedParameterJdbcTemplate().queryForObject(
					CONFERENCES_QUERY,
					namedParameters,
					BeanPropertyRowMapper.newInstance(CDRinfo2.class)
			);
			if (info != null) {
				info.setEndpointGUID(GUID);
			}
            logger.info("Looking in Conferences for endpoint GUID - " + GUID + " / FOUND");
		} catch (DataAccessException e) {
            logger.debug("Looking in Conferences for endpoint GUID - " + GUID + " / NOT FOUND / " + e.getMessage());
		}

		return info;
	}
	
	public void insertCDRtoConferenceCall2 (CDRinfo2 info) {
		int affected = updateCDRinConferenceCall2(info);

		if (affected == 0) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("UniqueCallID", info.getUniqueCallID());
			params.put("ConferenceName", info.getConferenceName());
			params.put("TenantName", info.getTenantName());
			params.put("ConferenceType", info.getConferenceType());
			params.put("EndpointType", info.getEndpointType());
			params.put("CallerID", info.getCallerID());
			params.put("CallerName", info.getCallerName());

			params.put("JoinTime", new Date());
			params.put("LeaveTime", null);
			params.put("CallState", info.getCallState());

			params.put("Direction", info.getDirection());
			params.put("RouterID", info.getRouterID());
			params.put("GWID", info.getGWID());
			params.put("GWPrefix", info.getGWPrefix());

			params.put("ReferenceNumber", info.getReferenceNumber());
			params.put("ApplicationName", info.getApplicationName());
			params.put("ApplicationVersion", info.getApplicationVersion());
			params.put("ApplicationOS", info.getApplicationOs());
			params.put("DeviceModel", info.getDeviceModel());
			params.put("EndpointPublicIPAddress", info.getEndpointPublicIPAddress());
			params.put("AccessType", info.getAccessType());
			params.put("RoomType", info.getRoomType());
			params.put("RoomOwner", info.getRoomOwner());
			params.put("CallCompletionCode", '0');
			params.put("Extension", info.getExtension());
			params.put("EndpointGUID", info.getEndpointGUID());

			int id = getNamedParameterJdbcTemplate().update(INSERT_INTO_CDR_QUERY, params);

			if (logger.isDebugEnabled()) {
				logger.debug("New CallID: " + id + " / GUID: " + info.getEndpointGUID() +
                        " / CallState: " + info.getCallState() );
			}
		}
    }

    public int updateCDRinConferenceCall2 (CDRinfo2 info) {

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ConferenceCall2" +
            " SET" +
			"  RouterID = :RouterID," +
            "  CallState = :CallState," +
            "  DeviceModel = :DeviceModel," +
            "  EndpointPublicIPAddress = :EndpointPublicIPAddress" +
            " WHERE" +
            "  ConferenceName = :ConferenceName" +
            " AND" +
            "  EndpointGUID = :EndpointGUID" +
            " AND" +
            "  UniqueCallID = :UniqueCallID" +
			" AND" +
			"  CallState != 'COMPLETED'"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("ConferenceName", info.getConferenceName())
            .addValue("EndpointGUID", info.getEndpointGUID())
			.addValue("RouterID", info.getRouterID())
            .addValue("CallState", info.getCallState())
            .addValue("DeviceModel", info.getDeviceModel()) // may be set by gw after call has started
            .addValue("EndpointPublicIPAddress", info.getEndpointPublicIPAddress()) // may be set by gw after call has started
			.addValue("UniqueCallID", info.getUniqueCallID());

        int updatedRows = 0;
        try {
            updatedRows =  getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
            logger.debug ("Updated CDR, GUID: " + info.getEndpointGUID() + " / CallerState: " + info.getCallState() + " UniqueCallID: " + info.getUniqueCallID() + " updatedRows: " + updatedRows);
        } catch (DataAccessException e) {
            logger.debug ("Existing CDR not found for update. GUID: " + info.getEndpointGUID() + " / CallerState: " + info.getCallState());
        }
        return updatedRows;
    }

	public final static String COMPELETE_CDR = " UPDATE" +
			"  ConferenceCall2" +
			" SET" +
			"  LeaveTime = :Time," +
			"  CallState = :CallState," +
            "  DeviceModel = :DeviceModel," +
            "  EndpointPublicIPAddress = :EndpointPublicIPAddress" +
			" WHERE" +
			"  ConferenceName = :ConferenceName" +
			" AND" +
            "  EndpointGUID = :EndpointGUID" +
			" AND" +
			"  UniqueCallID = :UniqueCallID" +
			" AND" +
			"  CallState != 'COMPLETED'";

	public final static String COMPELETE_CDR_WITH_REASON = " UPDATE" +
			"  ConferenceCall2" +
			" SET" +
			"  LeaveTime = :Time," +
			"  CallState = :CallState, " +
			"  CallCompletionCode = :CallCompletionCode, " +
            "  DeviceModel = :DeviceModel," +
            "  EndpointPublicIPAddress = :EndpointPublicIPAddress" +
			" WHERE" +
			"  ConferenceName = :ConferenceName" +
			" AND" +
            "  EndpointGUID = :EndpointGUID" +
			" AND" +
			"  UniqueCallID = :UniqueCallID" +
			" AND" +
			"  CallState != 'COMPLETED'";

	public void completeCDRinConferenceCall2 (CDRinfo2 info) {
		String sql = COMPELETE_CDR;

		MapSqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("ConferenceName", info.getConferenceName())
				.addValue("EndpointGUID", info.getEndpointGUID())
				.addValue("CallState", info.getCallState())
				.addValue("UniqueCallID", info.getUniqueCallID())
                .addValue("DeviceModel", info.getDeviceModel()) // may be set by gw after call has started
                .addValue("EndpointPublicIPAddress", info.getEndpointPublicIPAddress()) // may be set by gw after call has started
				.addValue("Time", new Date());


		//  note: in some cases, completion codes are also set beforehand via
		//   updateConferenceCallCompletionCode()
		//     or
		//   updateEndpointCallCompletionCode() below
		if (!CallCompletionCode.UNKNOWN.toString().equals(info.getCallCompletionCode())) {

			sql = COMPELETE_CDR_WITH_REASON;

			updateParameters.addValue("CallCompletionCode", info.getCallCompletionCode());
            logger.debug("Attempt to complete CDR with completion code");
		} else {
            logger.debug("Attempt to complete CDR without completion code");
        }

        try {
		    int rowsUpdated = getNamedParameterJdbcTemplate().update(sql, updateParameters);
            logger.debug("Completed CDR for GUID: " + info.getEndpointGUID() + " / CallState: " + info.getCallState() +
                    " / rowsUpdated: " + rowsUpdated);
        } catch (DataAccessException e) {
            logger.error("Could not complete CDR / " + e.getMessage(), e);
        }
	}

	/**
	 * Inserts the Server Restart event in to the CDR2 table
	 *
	 * @param info
	 */
	public void createServerRestartRecord(CDRinfo2 info) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("UniqueCallID", PortalUtils.generateUniqueCallID());
		params.put("ConferenceName", info.getConferenceName());
		params.put("TenantName", info.getTenantName());
		params.put("ConferenceType", info.getConferenceType());
		params.put("EndpointType", info.getEndpointType());
		params.put("CallerID", info.getCallerID());
		params.put("CallerName", info.getCallerName());
		Date date = Calendar.getInstance().getTime();
		params.put("JoinTime", date);
		params.put("LeaveTime", date);
		params.put("CallState", info.getCallState());

		params.put("Direction", info.getDirection());
		params.put("RouterID", info.getRouterID());
		params.put("GWID", info.getGWID());
		params.put("GWPrefix", info.getGWPrefix());

		params.put("ReferenceNumber", info.getReferenceNumber());
		params.put("ApplicationName", info.getApplicationName());
		params.put("ApplicationVersion", info.getApplicationVersion());
		params.put("ApplicationOS", info.getApplicationOs());
		params.put("DeviceModel", info.getDeviceModel());
		params.put("EndpointPublicIPAddress", info.getEndpointPublicIPAddress());
		params.put("AccessType", info.getAccessType());
		params.put("RoomType", info.getRoomType());
		params.put("RoomOwner", info.getRoomOwner());
		params.put("CallCompletionCode", info.getCallCompletionCode());
		params.put("Extension", info.getExtension());
		params.put("EndpointGUID", info.getEndpointGUID());

		getNamedParameterJdbcTemplate().update(INSERT_INTO_CDR_QUERY, params);
	}

	/**
	 * SQL to get the total CDR count
	 */
	private static final String GET_TOTAL_CDR_COUNT = "SELECT COUNT(CallID) from ConferenceCall2";

	/**
	 * Returns the total cdr(v2) count
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
	private static final String DELETE_CDR = "DELETE FROM ConferenceCall2 ORDER BY CallID ASC LIMIT :limit";

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


	public int updateConferenceCallCompletionCode(String uniqueCallId, CallCompletionCode code) {
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("uniqueCallID", uniqueCallId)
				.addValue("callCompletionCode", code.toString());
		int rowsUpdated = 0;

        try {
            rowsUpdated = getNamedParameterJdbcTemplate().update(UPDATE_CONFERENCE_COMPLETION_CODE, updateParameters);
        } catch (DataAccessException e) {
            logger.error("Failed to update conference completion code, uniqueCallId: " + uniqueCallId, e);
        }

        return rowsUpdated;
	}

	public int updateEndpointCallCompletionCode(String uniqueCallId, String endpointGUID, CallCompletionCode code) {
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("uniqueCallID", uniqueCallId)
				.addValue("endpointGUID", endpointGUID)
				.addValue("callCompletionCode", code.toString());

        int rowsUpdated = 0;

        try {
		    rowsUpdated =  getNamedParameterJdbcTemplate().update(UPDATE_ENDPOINT_COMPLETION_CODE, updateParameters);
        } catch (DataAccessException e) {
            logger.error("Failed to update endpoint completion code, GUID: " + endpointGUID, e);
        }

        return rowsUpdated;
    }

}