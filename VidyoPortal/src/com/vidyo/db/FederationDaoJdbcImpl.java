package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.ExternalLink;
import com.vidyo.bo.Federation;
import com.vidyo.bo.FederationConferenceRecord;
import com.vidyo.db.externallink.ExternalLinkRowMapper;

public class FederationDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements IFederationDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(FederationDaoJdbcImpl.class.getName());

	public String updateToSystemIpc(Federation federation,
			ExternalLink externalLink) {
		String requestId = externalLink.getRequestID();
		synchronized (this) {
			ExternalLink currentExternalLink = getExternalLinkByUniqueKey(
					externalLink.getFromSystemID(),
					externalLink.getFromConferenceName(),
					externalLink.getToSystemID(),
					externalLink.getToConferenceName());

			if (currentExternalLink == null) {
				SimpleJdbcInsert insertExternalLinks = new SimpleJdbcInsert(
						this.getDataSource()).withTableName("ExternalLinks")
						.usingGeneratedKeyColumns("externalLinkID");
				Date date = Calendar.getInstance().getTime();
				externalLink.setCreationTime(date);
				externalLink.setModificationTime(date);

				SqlParameterSource parametersForExternalLinks = new BeanPropertySqlParameterSource(
						externalLink);

				Number newID = insertExternalLinks
						.executeAndReturnKey(parametersForExternalLinks);
				federation.setExternalLinkID(newID.intValue());
			} else {
				federation.setExternalLinkID(currentExternalLink
						.getExternalLinkID());
				requestId = currentExternalLink.getRequestID();
			}
		}

		return requestId;
	}

	public boolean updateFromSystemIpc(Federation federation,
			ExternalLink externalLink) {

		boolean extLinkCreated = false;
		synchronized (this) {
			ExternalLink currentExternalLink = getExternalLinkByUniqueKey(
					externalLink.getFromSystemID(),
					externalLink.getFromConferenceName(),
					externalLink.getToSystemID(),
					externalLink.getToConferenceName());

			if (currentExternalLink == null) {
				SimpleJdbcInsert insertExternalLinks = new SimpleJdbcInsert(
						this.getDataSource()).withTableName("ExternalLinks")
						.usingGeneratedKeyColumns("externalLinkID");
				Date date = Calendar.getInstance().getTime();
				externalLink.setCreationTime(date);
				externalLink.setModificationTime(date);
				SqlParameterSource parametersForExternalLinks = new BeanPropertySqlParameterSource(
						externalLink);

				Number newID = insertExternalLinks
						.executeAndReturnKey(parametersForExternalLinks);
				federation.setExternalLinkID(newID.intValue());
				extLinkCreated = true;
			} else {
				federation.setExternalLinkID(currentExternalLink
						.getExternalLinkID());
			}

		}

		return extLinkCreated;
	}

    public int deleteFederation(ExternalLink externalLink) {
        logger.debug("Remove record from Federations for externalLinkID = " + externalLink.getExternalLinkID());

        StringBuffer sqlstmFederations = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Federations" +
            " WHERE" +
            "  externalLinkID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstmFederations.toString(), externalLink.getExternalLinkID());
        logger.debug("Rows affected: " + affected);

        StringBuffer sqlstmExternalLinks = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  ExternalLinks" +
            " WHERE" +
            "  externalLinkID = ?"
        );

        affected = getJdbcTemplate().update(sqlstmExternalLinks.toString(), externalLink.getExternalLinkID());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public ExternalLink getExternalLinkByRequestID(String requestID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            "  el.requestID = ?"
        );

        ExternalLink rc = null;
        try {
            rc = getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(ExternalLink.class), requestID);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        return rc;
    }

    public ExternalLink getExternalLink(String requestID, String fromConfName, String toConfName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            "  el.requestID =:requestId AND el.fromConferenceName =:fromConfName AND el.toConferenceName =:toConfName "
        );

        ExternalLink rc = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("requestId", requestID);
        params.put("fromConfName", fromConfName);
        params.put("toConfName", toConfName);

        rc = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), params,
                BeanPropertyRowMapper.newInstance(ExternalLink.class));

        return rc;
    }

    public ExternalLink getExternalLinkByRemoteMedia(String remoteMediaAddress, String remoteMediaAdditionalInfo) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            "  el.remoteMediaAddress = ?" +
            " AND" +
            " el.remoteMediaAdditionalInfo = ?"
        );

        ExternalLink rc = null;
        try {
            rc = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(ExternalLink.class), remoteMediaAddress, remoteMediaAdditionalInfo);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        return rc;
    }


    public ExternalLink getExternalLinkByUniqueKey(String fromSystemID, String fromConferenceName, String toSystemID, String toConferenceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            "  el.fromSystemID = :fromSystemID" +
            " AND" +
            "  el.toSystemID = :toSystemID" +
            " AND" +
            "  el.fromConferenceName = :fromConferenceName" +
            " AND" +
            "  el.toConferenceName = :toConferenceName"
        );

        SqlParameterSource selectParameters = new MapSqlParameterSource()
            .addValue("fromSystemID", fromSystemID)
            .addValue("toSystemID", toSystemID)
            .addValue("fromConferenceName", fromConferenceName)
            .addValue("toConferenceName", toConferenceName);

        ExternalLink rc = null;
        try {
            rc = (ExternalLink) getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), selectParameters,
                    BeanPropertyRowMapper.newInstance(ExternalLink.class));
        } catch (DataAccessException ignored) {
            if (logger.isDebugEnabled())
	            logger.debug("ExternalLink record not found - will insert it!");
        }
        return rc;
    }

    public ExternalLink getExternalLinkForUpdateStatus(String remoteSystemID, String fromConferenceName, String toConferenceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status, el.secure" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE " +
            " ( el.fromSystemID = :fromSystemID" +
            " OR" +
            "  el.toSystemID = :toSystemID )" +
            " AND" +
            "  el.fromConferenceName = :fromConferenceName" +
            " AND" +
            "  el.toConferenceName = :toConferenceName"
            //" ORDER BY el.externalLinkID DESC LIMIT 1" // get the latest record
        );

        SqlParameterSource selectParameters = new MapSqlParameterSource()
            .addValue("fromSystemID", remoteSystemID)
            .addValue("toSystemID", remoteSystemID)
            .addValue("fromConferenceName", fromConferenceName)
            .addValue("toConferenceName", toConferenceName);

        ExternalLink rc = null;
        try {
            rc = (ExternalLink) getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), selectParameters,
                    BeanPropertyRowMapper.newInstance(ExternalLink.class));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return rc;
    }

    public ExternalLink getExternalLinkByConferenceName(String conferenceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            "  el.fromConferenceName = :fromConferenceName" +
            " AND" +
            "  el.toConferenceName = :toConferenceName"
        );

        SqlParameterSource selectParameters = new MapSqlParameterSource()
            .addValue("fromConferenceName", conferenceName)
            .addValue("toConferenceName", conferenceName);

        ExternalLink rc = null;
        try {
            rc = (ExternalLink) getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), selectParameters,
                    BeanPropertyRowMapper.newInstance(ExternalLink.class));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return rc;
    }

    public int updateExternalLinkForMediaInfo(int externalLinkID, String requestID, String mediaAddress, String mediaAdditionalInfo) {
        // regular endpoint
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ExternalLinks" +
            " SET" +
            //"  requestID = :requestID," +
            "  remoteMediaAddress = :mediaAddress," +
            "  remoteMediaAdditionalInfo = :mediaAdditionalInfo," +
            "  modificationTime = :modificationTime" +
            " WHERE" +
            "  externalLinkID = :externalLinkID"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            //.addValue("requestID", requestID)
            .addValue("mediaAddress", mediaAddress)
            .addValue("mediaAdditionalInfo", mediaAdditionalInfo)
            .addValue("externalLinkID", externalLinkID)
            .addValue("modificationTime", Calendar.getInstance().getTime());

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int updateExternalLinkForLocalMediaInfo(int externalLinkID, String mediaAddress, String mediaAdditionalInfo) {
        // regular endpoint
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ExternalLinks" +
            " SET" +
            "  localMediaAddress = :mediaAddress," +
            "  localMediaAdditionalInfo = :mediaAdditionalInfo," +
            "  modificationTime = :modificationTime" +
            " WHERE" +
            "  externalLinkID = :externalLinkID"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("mediaAddress", mediaAddress)
            .addValue("mediaAdditionalInfo", mediaAdditionalInfo)
            .addValue("externalLinkID", externalLinkID)
            .addValue("modificationTime", Calendar.getInstance().getTime());

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int updateExternalLinkForStatus(int externalLinkID, int status) {
        // regular endpoint
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  ExternalLinks" +
            " SET" +
            "  status = :status, modificationTime =:modificationTime" +
            " WHERE" +
            "  externalLinkID = :externalLinkID"
        );

        SqlParameterSource updateParameters = new MapSqlParameterSource()
            .addValue("status", status)
            .addValue("externalLinkID", externalLinkID)
            .addValue("modificationTime", Calendar.getInstance().getTime());

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), updateParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public List<Control> getParticipantsForFederation(String conferenceName, ControlFilter filter) {
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
            " as webcast" +
            " " +
            " FROM  Conferences c" +
            " LEFT JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType='D')" +
            " LEFT JOIN VirtualEndpoints ve ON (ve.endpointID=c.endpointID AND c.endpointType='V')" +
            " LEFT JOIN RecorderEndpoints re ON (re.endpointID=c.endpointID AND c.endpointType='R')" +
            " LEFT JOIN Member m ON (m.memberID=e.memberID AND e.memberType='R')" +
            " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
            " LEFT JOIN Room r ON (m.memberID=r.memberID AND r.roomTypeID=1)" +
            " LEFT JOIN Tenant t ON (t.tenantID=c.tenantID)" +
            " " +
            " WHERE c.conferenceName = :conferenceName" +
            "  AND" +
            "  c.conferenceType = 'F'"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("conferenceName", conferenceName);

        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Control.class));
        return rc;
    }

    public int insertFederationConferenceRecord(FederationConferenceRecord federationConferenceRecord) {
        if (isFederationConferenceRecordExist(federationConferenceRecord)) {
            StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                "  FederationConferences" +
                " SET" +
                "  conferenceName   = :conferenceName," +
                "  conferenceType   = :conferenceType," +
                "  endpointID       = :endpointID," +
                "  endpointGUID     = :endpointGUID," +
                "  endpointType     = :endpointType," +
                "  endpointCaller   = :endpointCaller," +
                "  userNameAtTenant = :userNameAtTenant," +
                "  displayName      = :displayName," +
                "  extension        = :extension," +
                "  dialIn           = :dialIn," +
                "  video            = :video," +
                "  audio            = :audio" +
                " WHERE" +
                "  endpointGUID = :endpointGUID"
            );

            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(federationConferenceRecord);

            int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
            logger.debug("Rows affected: " + affected);

            return affected;
        } else {
            SimpleJdbcInsert insertExternalLinks = new SimpleJdbcInsert(this.getDataSource())
                .withTableName("FederationConferences")
                .usingGeneratedKeyColumns("conferenceID");

            SqlParameterSource parametersForExternalLinks = new BeanPropertySqlParameterSource(federationConferenceRecord);

            Number newID = insertExternalLinks.executeAndReturnKey(parametersForExternalLinks);
            return newID.intValue();
        }
    }

    public boolean isFederationConferenceRecordExist(FederationConferenceRecord federationConferenceRecord) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  FederationConferences" +
            " WHERE" +
            "  endpointGUID = :endpointGUID"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("endpointGUID", federationConferenceRecord.getEndpointGUID());

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public int deleteFederationConferenceRecord(String endpointGUID) {
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  FederationConferences" +
            " WHERE" +
            "  endpointGUID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), endpointGUID);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int deleteAllFederationConferenceRecords(String conferenceName) {
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  FederationConferences" +
            " WHERE" +
            "  conferenceName = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), conferenceName);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public FederationConferenceRecord getFederationConferenceRecord(String endpointGUID) {
        FederationConferenceRecord rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.conferenceName," +
            "  c.conferenceType," +
            "  e.endpointID," +
            "  e.endpointGUID," +
            "  c.endpointType," +
            "  c.endpointCaller," +
            "  CONCAT(m.username, '@', t.tenantUrl) as userNameAtTenant," +
            "  m.memberName as displayName," +
            "  r.roomExtNumber as extension," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            "  c.video," +
            "  c.audio," +
            "  el.toTenantHost" +
            " FROM" +
            "  Endpoints e" +
            "  JOIN Conferences c ON (c.endpointID=e.endpointID)" +
            "  JOIN Member m ON (m.memberID=e.memberID)" +
            "  JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=:personal)" +
            "  JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            "  JOIN ExternalLinks el ON (el.toConferenceName=c.conferenceName)" +
            " WHERE" +
            "  e.endpointGUID = :endpointGUID"
        );
        Map<String, Object> namedParams = new HashMap<String, Object>();
        namedParams.put("personal", 1);
        namedParams.put("endpointGUID", endpointGUID);

        rc = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParams,
                BeanPropertyRowMapper.newInstance(FederationConferenceRecord.class));
        return rc;
    }

    public String getFederationEndpointGUID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  fc.endpointGUID" +
            " FROM" +
            "  FederationConferences fc" +
            " WHERE" +
            "  fc.endpointID = ?"
        );

        List<String> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            },
            endpointID
        );

        return ll.size() > 0 ? ll.get(0) : "";
    }

    public String getFederationConferenceName(int roomID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT CONCAT(r.roomName, '_', t.tenantName)" +
            "  FROM Room r" +
            "  JOIN Member m ON (r.memberID=m.memberID)" +
            "  JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            "  WHERE r.roomID = ?"
        );

        List<String> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            },
            roomID
        );

        return ll.size() > 0 ? ll.get(0) : "";
    }

    public String getFederationFromTenantHostForEndpointID(int endpointID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  el.fromTenantHost" +
            " FROM" +
            "  FederationConferences fc" +
            "  JOIN ExternalLinks el ON (el.fromConferenceName=fc.conferenceName)" +
            " WHERE" +
            "  fc.endpointID = ?"
        );

        List<String> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            },
            endpointID
        );

        return ll.size() > 0 ? ll.get(0) : "";
    }

    public ExternalLink getExternalLink(String toConfName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  el.externalLinkID," +
            "  el.requestID," +
            "  el.fromSystemID," +
            "  el.fromTenantHost," +
            "  el.fromConferenceName," +
            "  el.toSystemID," +
            "  el.toTenantHost," +
            "  el.toConferenceName," +
            "  el.localMediaAddress," +
            "  el.localMediaAdditionalInfo," +
            "  el.remoteMediaAddress," +
            "  el.remoteMediaAdditionalInfo," +
            "  el.status," +
            "  el.secure" +
            " FROM" +
            "  ExternalLinks el" +
            " WHERE" +
            " el.toConferenceName =:toConfName "
        );

        ExternalLink rc = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("toConfName", toConfName);

        rc = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), params,
                BeanPropertyRowMapper.newInstance(ExternalLink.class));

        return rc;
    }


	private static final String UPDATE_UNIQUE_CALL_ID = "UPDATE ExternalLinks SET uniqueCallID =:uniqueCallId, modificationTime =:modificationTime WHERE toSystemID =:toSystemID AND toConferenceName =:toConferenceName";

	/**
	 *
	 * @param toSystemId
	 * @param toConfName
	 * @param uniqueCallId
	 * @return
	 */
	public int updateExternalLinkUniqueId(String toSystemId, String toConfName,
			String uniqueCallId) {
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("uniqueCallId", uniqueCallId)
				.addValue("toSystemID", toSystemId)
				.addValue("toConferenceName", toConfName)
				.addValue("modificationTime", Calendar.getInstance().getTime());

		int affected = getNamedParameterJdbcTemplate().update(UPDATE_UNIQUE_CALL_ID,
				updateParameters);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

	/**
	 * SQL to get the links with specific status for more than specified time
	 */
	private static final String GET_DISCONNECTED_EXTERNAL_LINKS = "SELECT externalLinkID, fromSystemID, toSystemID, fromConferenceName, toConferenceName, status, fromTenantHost from ExternalLinks WHERE status !=:status AND TIMESTAMPDIFF(MINUTE, modificationTime, CURRENT_TIMESTAMP) >:timeLapsed";

	/**
	 * Returns the list of ExternalLinks remaining with specific status for a
	 * specific period of time
	 *
	 * @param timeLapsed
	 * @return
	 */
	public List<ExternalLink> getDisconnectedExternalLinks(int status,
			int timeLapsed) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timeLapsed", timeLapsed);
		paramMap.put("status", status);

		@SuppressWarnings("unchecked")
		List<ExternalLink> externalLinks = getNamedParameterJdbcTemplate()
				.query(GET_DISCONNECTED_EXTERNAL_LINKS, paramMap,
						new ExternalLinkRowMapper());
		return externalLinks;
	}

	private static final String GET_COUNT_EXT_LINKS_BY_TO_CONF_NAME = "SELECT COUNT(0) FROM ExternalLinks WHERE toConferenceName =:toConfName";

	/**
	 * Returns ExternalLinks count by toConferenceName
	 *
	 * @param toConfName
	 * @return
	 */
	public int getExtLinksCountByToConfName(String toConfName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("toConfName", toConfName);
		int count = getNamedParameterJdbcTemplate().queryForObject(
				GET_COUNT_EXT_LINKS_BY_TO_CONF_NAME, paramMap, Integer.class);
		return count;

	}

}
