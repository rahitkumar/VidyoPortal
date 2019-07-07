package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.User;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.db.user.UserRowMapper;

import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.validateExtDataType;

public class UserDaoJdbcImpl extends JdbcDaoSupport implements IUserDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(UserDaoJdbcImpl.class.getName());

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

    public User getUserByUsername(int tenant, String username) {
        logger.debug("Get record from Member for tenant = " + tenant + " and username = " + username);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  m.memberID as memberID," +
            "  m.tenantID as tenantID," +
            "  CASE WHEN r.roomID IS NULL THEN 0 ELSE r.roomID END as roomID," +
            "  CASE WHEN r.roomEnabled IS NULL THEN 0 ELSE r.roomEnabled END as roomEnabled," +
            "  m.username as username," +
            "  m.memberName as memberName," +
            "  m.emailAddress as emailAddress," +
            "  m.langID as langID," +
            "  m.allowedToParticipate as allowedToParticipate," +
            "  'R' as userType" +
            " FROM" +
            "  Member m" +
            " LEFT JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=?)" +
            " WHERE" +
            "  (m.tenantID = ?)" +
            " AND" +
            "  (m.username = ?)" +
            " UNION " +
            " SELECT " +
            "  g.guestID as memberID," +
            "  g.tenantID as tenantID," +
            "  '0' as roomID," +
            "  '0' as roomEnabled," +
            "  g.username as username," +
            "  g.guestName as memberName," +
            "  '' as emailAddress," +
            "  '1' as allowedToParticipate," +
            "  '1' as langID," +
            "  'G' as userType" +
            " FROM" +
            "  Guests g" +
            " WHERE" +
            "  (g.tenantID = ?)" +
            " AND" +
            "  (g.username = ?)"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(User.class), 1, tenant, username, tenant, username);
    }

    public Language getUserLang(int tenant, String username) {
        logger.debug("Get record from Member for tenant = " + tenant + " and username = " + username);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.langID," +
            "  l.langCode," +
            "  l.langName" +
            " FROM" +
            "  Member m" +
            " INNER JOIN Language l ON m.langID = l.langID" +
            " WHERE" +
            "  (m.tenantID = ?)" +
            " AND" +
            "  (m.username = ?)"
        );

        Language lang = getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Language.class), tenant, username);

        if(lang.getLangName().equalsIgnoreCase("System Language")){
        	sqlstm = new StringBuffer(
                " SELECT " +
                "  l.langID, " +
                "  c.configurationValue AS 'langCode'," +
                "  l.langName " +
                " FROM " +
                "  Language l, " +
                "  Configuration c " +
                " WHERE " +
                "    l.langName = 'System Language' " +
                " AND  c.configurationName = 'SystemLanguage' " +
                " AND  c.tenantID = ?"
            );

        	lang = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Language.class), tenant);
        }

        return lang;
    }

    public int linkEndpointToUser(int memberID, String GUID, String endpointUploadType) {
        int rc = 0;
        logger.debug("Associate in Endpoints for GUID = " + GUID + " and memberID = " + memberID);

        // remove memberID associated to other GUID
        StringBuffer removeSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  memberID = :noMemberID," +
            "  authorized = :notAuthorized" +
            " WHERE" +
            "  memberID = :memberID" +
            " AND" +
            "  endpointGUID <> :GUID" +
            " AND" +
            "  memberType = :memberType"
        );

        SqlParameterSource removeParameters = new MapSqlParameterSource()
        		.addValue("noMemberID", 0)
        		.addValue("notAuthorized", 0)
                .addValue("memberID", memberID)
                .addValue("GUID", GUID)
                .addValue("memberType", "R");

        int affected = namedParamJdbcTemplate.update(removeSqlstm.toString(), removeParameters);
        logger.debug("Rows affected for Endpoints: " + affected);

        //Update the Endoint

        StringBuffer updateSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  memberID = :memberID," +
            "  memberType = :memberType," +
            "  authorized = :notAuthorized" +
            " WHERE" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("memberType", "R")
                .addValue("GUID", GUID)
                .addValue("notAuthorized", 0);

        int updated = namedParamJdbcTemplate.update(updateSqlstm.toString(), namedParameters);
        logger.debug("Rows affected for Endpoints: " + updated);

        if(updated == 0) {
    		try { // try to insert if duplicate exception then update
                // create a new associated record for memberID and GUID and status "Offline" if not presen
                SqlParameterSource insertParameters = new MapSqlParameterSource()
                        .addValue("memberID", memberID)
                        .addValue("memberType", "R")
                        .addValue("endpointGUID", GUID)
                        .addValue("authorized", 0)
    					.addValue("sequenceNum", 0)
                        .addValue("status", 0)
                        .addValue("consumesLine", 1) // offline by default
                        .addValue("endpointUploadType", endpointUploadType)
                        .addValue("lectureModeSupport", 0);
                KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
                namedParamJdbcTemplate.update(INSERT_INTO_ENDPOINTS, insertParameters, generatedKeyHolder);
                logger.debug("New endpointID = " + generatedKeyHolder.getKey().intValue());

                rc = generatedKeyHolder.getKey().intValue();
    		} catch (Exception e) {
                // update old associated record for memberID and GUID and status "Offline" if not present

                StringBuffer updateSqlstm1 = new StringBuffer(
                    " UPDATE" +
                    "  Endpoints" +
                    " SET" +
                    "  memberID = :memberID," +
                    "  memberType = :memberType," +
                    "  authorized = :notAuthorized" +
                    " WHERE" +
                    "  endpointGUID = :GUID"
                );

                namedParameters = new MapSqlParameterSource()
                        .addValue("memberID", memberID)
                        .addValue("memberType", "R")
                        .addValue("GUID", GUID)
                        .addValue("notAuthorized", 0);

                updated = namedParamJdbcTemplate.update(updateSqlstm1.toString(), namedParameters);
                logger.debug("Rows affected for Endpoints: " + updated);

                rc = updated;
                logger.warn("Duplicate GUID LinkEndpointToUser ->"+GUID);
            }
        }

		return rc;
    }

        private Long getCountForGUID(String GUID) {
            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  Endpoints" +
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

    public int unlinkEndpointFromUser(int memberID, String GUID) {
        logger.debug("Disconnect in Endpoints for GUID = " + GUID + " and memberID = " + memberID);

        // remove memberID associated to other GUID
        StringBuffer removeSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  memberID = 0," +
            "  authorized = 0" +
            " WHERE" +
            "  memberID = :memberID" +
            " AND" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource removeParameters = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("GUID", GUID);

        int affected = namedParamJdbcTemplate.update(removeSqlstm.toString(), removeParameters);
        logger.debug("Rows affected for Endpoints: " + affected);

        return affected;
    }

    public int updateEndpointIPaddress(String GUID, String ipAddress) {
        logger.debug("Update ipAddress in Endpoints for GUID = " + GUID + " and ipAddress = " + ipAddress);

        // remove memberID associated to other GUID

        StringBuffer removeSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  ipAddress = :ipAddress" +
            " WHERE" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource removeParameters = new MapSqlParameterSource()
                .addValue("ipAddress", ipAddress)
                .addValue("GUID", GUID);

        int affected = namedParamJdbcTemplate.update(removeSqlstm.toString(), removeParameters);
        logger.debug("Rows affected for Endpoints: " + affected);

        return affected;
    }
    
	public int updateEndpointExternalData(String GUID, int extDataTypeRaw, String extData) {
        int extDataType = validateExtDataType(extDataTypeRaw);
        logger.debug("Update external data in Endpoints for GUID = " + GUID + " and extDataType = " + extDataType +
        		",extDataTypeRaw: " + extDataTypeRaw + " and extData = " + extData);

        // remove memberID associated to other GUID

        StringBuffer removeSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            " extData = :extData ," +
            " extDataType = :extDataType" +
            " WHERE" +
            "  endpointGUID = :GUID"
        );

        SqlParameterSource removeParameters = new MapSqlParameterSource()
                .addValue("extData", extData)
                .addValue("extDataType", extDataType)
                .addValue("GUID", GUID);

        int affected = namedParamJdbcTemplate.update(removeSqlstm.toString(), removeParameters);
        logger.debug("Rows affected for Endpoints: " + affected);

        return affected;
	}

	private String cleanForCDR(String str, int maxSize) {
		String cleanString = StringUtils.trimToEmpty(str);
		if (cleanString.length() > maxSize) {
			return cleanString.substring(0, maxSize);
		}
		return cleanString;
	}
	
	public static final String UPDATE_ENDPOINT_CDR_INFO = "UPDATE Endpoints SET "
			+ "applicationName = :applicationName , applicationVersion = :applicationVersion , "
			+ "applicationOs = :applicationOs , deviceModel = :deviceModel , "
			+ "endpointPublicIPAddress = :endpointPublicIPAddress ,  "
			+ "extData = :extData , "
			+ "extDataType = :extDataType "
			+ "WHERE endpointGUID = :GUID";

	public int updateEndpointCDRInfo(String GUID, CDRinfo2 cdrinfo) {
		logger.debug("Update CDR info in Endpoints for GUID = " + GUID);

		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("applicationName", cleanForCDR(cdrinfo.getApplicationName(),24))
				.addValue("applicationVersion", cleanForCDR(cdrinfo.getApplicationVersion(),32))
				.addValue("applicationOs", cleanForCDR(cdrinfo.getApplicationOs(),24))
				.addValue("deviceModel", cleanForCDR(cdrinfo.getDeviceModel(),50))
				.addValue("endpointPublicIPAddress", cleanForCDR(cdrinfo.getEndpointPublicIPAddress(),48))
				.addValue("extData", cdrinfo.getExtData())
				.addValue("extDataType", cdrinfo.getExtDataType())
				.addValue("GUID", GUID);

		int affected = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_CDR_INFO, sqlParameterSource);
		logger.debug("Rows affected for Endpoints: " + affected);

		return affected;
	}
	
	public int updateEndpointReferenceNumber(String GUID, String referenceNumber) {
		logger.debug("Update CDR info in Endpoints for GUID = " + GUID);

		StringBuffer updateSQL = new StringBuffer(
				" UPDATE" +
						"  Endpoints" +
						" SET" +
						"  referenceNumber = :referenceNumber" +
						" WHERE" +
						"  endpointGUID = :GUID"
		);

		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("referenceNumber", cleanForCDR(referenceNumber, 64))
				.addValue("GUID", GUID);

		int affected = namedParamJdbcTemplate.update(updateSQL.toString(), sqlParameterSource);
		logger.debug("Rows affected for Endpoints: " + affected);

		return affected;
	}

    public boolean isMemberLinkedToEndpoint(int memberID, String GUID) {
        logger.debug("Get record from Endpoints for memberID = " + memberID + " and GUID = " + GUID);

        StringBuffer chk_sqlstm = new StringBuffer(
            " SELECT" +
            "  memberID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " AND" +
            "  memberType = ?"
        );

        List<Integer> lli = getJdbcTemplate().query(chk_sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, GUID, "R"
        );

        int linked_memberID = lli.size() > 0 ? lli.get(0) : 0;

        return linked_memberID != 0;
    }

    public int getMyStatus(int memberID, String GUID) {
        logger.debug("Get record from Endpoints for memberID = " + memberID);

        StringBuffer chk_sqlstm = new StringBuffer(
            " SELECT" +
            "  memberID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  endpointGUID = ?" +
            " AND" +
            "  memberType = ?"
        );

        List<Integer> lli = getJdbcTemplate().query(chk_sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, GUID, "R"
        );

        int linked_memberID = lli.size() > 0 ? lli.get(0) : 0;

        if (linked_memberID == 0) { // means this memberID is linked to different endpointGUID
            return -1; // on this status do logout
        }

        if (linked_memberID == memberID) {
            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END as status" +
                " FROM" +
                "  Endpoints e" +
                " WHERE" +
                "  e.memberID = ?" +
                " AND" +
                "  e.memberType = 'R'"
            );

            List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, memberID
            );

            return li.size() > 0 ? li.get(0) : 0;
        } else {
            return -1; // on this status do logout
        }
    }

    public String getLinkedEndpointGUID(int memberID) {
        logger.debug("Get record from Endpoints for memberID = " + memberID);

        StringBuffer chk_sqlstm = new StringBuffer(
            " SELECT" +
            "  endpointGUID" +
            " FROM" +
            "  Endpoints" +
            " WHERE" +
            "  memberID = ?" +
            " AND" +
            "  memberType = ?"
        );

        List<String> lls = getJdbcTemplate().query(chk_sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, memberID, "R"
        );

        return lls.size() > 0 ? lls.get(0) : "";
    }

    public int addGuestUser(int tenant, String guestName, String username) {
        logger.debug("Add record into Guests for tenant = " + tenant + " and guestName = " + guestName + " and username = " + username);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
			.withTableName("Guests")
			.usingGeneratedKeyColumns("guestID");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenant));
        params.put("guestName", guestName);
        params.put("username", username);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);
        Number newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New guestID = " + newID.intValue());

        return newID.intValue();
    }

	public int addGuestUser(int tenant, String guestName, String username, int roomID) {
		logger.debug("Add record into Guests for tenant = " + tenant + " and guestName = " + guestName + " and username = " + username + " and roomID = " + roomID);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
			.withTableName("Guests")
			.usingGeneratedKeyColumns("guestID");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tenantID", String.valueOf(tenant));
		params.put("guestName", guestName);
		params.put("username", username);
		params.put("roomID", String.valueOf(roomID));

		SqlParameterSource namedParameters = new MapSqlParameterSource(params);
		Number newID = insert.executeAndReturnKey(namedParameters);

		logger.debug("New guestID = " + newID.intValue());

		return newID.intValue();
	}

    public User getUserForGuestID(int guestID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  guestID as memberID," +
            "  tenantID as tenantID," +
            "  guestName as memberName," +
            "  '1' as allowedToParticipate," +
            "  username," +
            "  COALESCE(roomID, 0) as roomID," +
            "  '0' as roomEnabled," +
            "  '1' as langID," +
            "  'G' as userType," +
            "  pak, pak2" +
            " FROM" +
            "  Guests" +
            " WHERE" +
            "  guestID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(User.class), guestID);
    }

    public int linkEndpointToGuest(int tenant, int guestID, String GUID) {
        logger.debug("Associate in Endpoints for GUID = " + GUID + " and guestID = " + guestID);

        // remove memberID associated to other GUID
        StringBuffer removeSqlstm = new StringBuffer(
            " UPDATE" +
            "  Endpoints" +
            " SET" +
            "  memberID = 0," +
            "  authorized = 0" +
            " WHERE" +
            "  memberID = :guestID" +
            " AND" +
            "  endpointGUID <> :GUID" +
            " AND" +
            "  memberType = :memberType"
        );

        SqlParameterSource removeParameters = new MapSqlParameterSource()
                .addValue("guestID", guestID)
                .addValue("GUID", GUID)
                .addValue("memberType", "G");

        int affected = namedParamJdbcTemplate.update(removeSqlstm.toString(), removeParameters);
        logger.debug("Rows affected for Endpoints: " + affected);

        Long count = getCountForGUID(GUID);
        if (count == 0l) {
            // create a new associated record for memberID and GUID and status "Offline" if not present
            SimpleJdbcInsert insertTemplate = new SimpleJdbcInsert(this.getDataSource())
                    .withTableName("Endpoints")
                    .usingGeneratedKeyColumns("endpointID");

            SqlParameterSource insertParameters = new MapSqlParameterSource()
                    .addValue("memberID", guestID)
                    .addValue("memberType", "G")
                    .addValue("endpointGUID", GUID)
                    .addValue("authorized", 0)
					.addValue("sequenceNum", 0)
                    .addValue("status", 0) //offline by default
                    .addValue("consumesLine", 1)
                    .addValue("lectureModeSupport", 0);
            Number newID = insertTemplate.executeAndReturnKey(insertParameters);
            logger.debug("New endpointID = " + newID.intValue());

            return newID.intValue();
        } else {
            // update old associated record for memberID and GUID and status "Offline" if not present

            StringBuffer updateSqlstm = new StringBuffer(
                " UPDATE" +
                "  Endpoints" +
                " SET" +
                "  memberID = :memberID," +
                "  memberType = :memberType" +
                " WHERE" +
                "  endpointGUID = :GUID"
            );

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("memberID", guestID)
                    .addValue("memberType", "G")
                    .addValue("GUID", GUID);

            int updated = namedParamJdbcTemplate.update(updateSqlstm.toString(), namedParameters);
            logger.debug("Rows affected for Endpoints: " + updated);

            return updated;
        }
    }

    public int savePAKforMember(int memberID, String PAK, String pak2) {
        logger.debug("Update Portal Access Key in Member for pak = " + PAK + " and memberID = " + memberID);

        StringBuffer pakSqlstm = new StringBuffer(
            " UPDATE" +
            "  Member" +
            " SET" +
            "  pak = :pak, pak2 =:pak2 "+
            " WHERE" +
            "  memberID = :memberID"
        );

        SqlParameterSource pakParameters = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("pak", PAK).addValue("pak2", pak2);

        int affected = namedParamJdbcTemplate.update(pakSqlstm.toString(), pakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int savePAKforGuest(int guestID, String PAK, String pak2) {
        logger.debug("Update Portal Access Key in Guests for pak = " + PAK + " and guestID = " + guestID);

        StringBuffer pakSqlstm = new StringBuffer(
            " UPDATE" +
            "  Guests" +
            " SET" +
            "  pak = :pak , pak2 =:pak2 " +
            " WHERE" +
            "  guestID = :guestID"
        );

        SqlParameterSource pakParameters = new MapSqlParameterSource()
                .addValue("guestID", guestID)
                .addValue("pak", PAK).addValue("pak2", pak2);;

        int affected = namedParamJdbcTemplate.update(pakSqlstm.toString(), pakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }


    public static final String GET_PAK_PAK2_FOR_MEMBER = "SELECT pak, pak2 from Member where memberID = ?";

	public PortalAccessKeys getPAKforMember(int memberID) {
		PortalAccessKeys portalAccessKeys = getJdbcTemplate()
				.queryForObject(
						GET_PAK_PAK2_FOR_MEMBER,
						BeanPropertyRowMapper
								.newInstance(PortalAccessKeys.class), memberID);

		return portalAccessKeys;
	}

	public static final String GET_PAK_PAK2_FOR_GUEST = "SELECT pak, pak2 from Guests where guestID = ?";

    public PortalAccessKeys getPAKforGuest(int guestID) {
		PortalAccessKeys portalAccessKeys = getJdbcTemplate()
				.queryForObject(
						GET_PAK_PAK2_FOR_GUEST,
						BeanPropertyRowMapper
								.newInstance(PortalAccessKeys.class), guestID);

		return portalAccessKeys;
    }

    public int getMemberIDForPAK(String PAK) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  memberID" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  pak = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, PAK
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public int saveBAKforMember(int memberID, String BAK) {
        logger.debug("Update Browser Access Key in Member for bak = " + BAK + " and memberID = " + memberID);

        StringBuffer bakSqlstm = new StringBuffer(
            " UPDATE" +
            "  Member" +
            " SET" +
            "  bak = :bak, bak_creation_time =:creationTime" +
            " WHERE" +
            "  memberID = :memberID"
        );

        SqlParameterSource bakParameters = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("bak", BAK)
                .addValue("creationTime", Calendar.getInstance().getTime());

        int affected = namedParamJdbcTemplate.update(bakSqlstm.toString(), bakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public String getBAKforMember(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  bak" +
            " FROM" +
            "  Member" +
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

    public int getMemberIDForBAK(String BAK) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  memberID" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  bak = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, BAK
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public int saveSAKforMember(int memberID, String SAK, long bindUserRequestID) {
        logger.debug("Update Service Access Key in Member for sak = " + SAK + " and memberID = " + memberID + " and bindUserRequestID = " + bindUserRequestID);

        StringBuffer sakSqlstm = new StringBuffer(
            " UPDATE" +
            "  Member" +
            " SET" +
            "  sak = :sak," +
            "  bindUserRequestID = :bindUserRequestID" +
            " WHERE" +
            "  memberID = :memberID"
        );

        SqlParameterSource sakParameters = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("sak", SAK)
                .addValue("bindUserRequestID", bindUserRequestID);

        int affected = namedParamJdbcTemplate.update(sakSqlstm.toString(), sakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public String getSAKforMember(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  sak" +
            " FROM" +
            "  Member" +
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

    public long getBindUserRequestIDforMember(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  bindUserRequestID" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  memberID = ?"
        );

        List<Long> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, memberID
        );

        return ls.size() > 0 ? ls.get(0) : 0l;
    }

    public int saveSAKforGuest(int guestID, String SAK, long bindUserRequestID) {
        logger.debug("Update Service Access Key in Guests for sak = " + SAK + " and guestID = " + guestID + " and bindUserRequestID = " + bindUserRequestID);

        StringBuffer sakSqlstm = new StringBuffer(
            " UPDATE" +
            "  Guests" +
            " SET" +
            "  sak = :sak," +
            "  bindUserRequestID = :bindUserRequestID" +
            " WHERE" +
            "  guestID = :guestID"
        );

        SqlParameterSource sakParameters = new MapSqlParameterSource()
                .addValue("guestID", guestID)
                .addValue("sak", SAK)
                .addValue("bindUserRequestID", bindUserRequestID);

        int affected = namedParamJdbcTemplate.update(sakSqlstm.toString(), sakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public String getSAKforGuest(int guestID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  sak" +
            " FROM" +
            "  Guests" +
            " WHERE" +
            "  guestID = ?"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, guestID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

    public long getBindUserRequestIDforGuest(int guestID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  bindUserRequestID" +
            " FROM" +
            "  Guests" +
            " WHERE" +
            "  guestID = ?"
        );

        List<Long> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, guestID
        );

        return ls.size() > 0 ? ls.get(0) : 0l;
    }

    public static final String EID_EXISTS_QUERY = "SELECT SUM(cnt) from( SELECT COUNT(0) as cnt FROM ClientInstallations WHERE EID =:eid UNION ALL SELECT COUNT(0) as cnt FROM ClientInstallations2 WHERE EID =:eid ) as aliasname";

	public boolean isUserLicenseRegistered(String EID) {
		logger.debug("Get count of registered EID from ClientInstallations/ClientInstallations2 for EID = " + EID);


		/*StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  COUNT(0)" +
			" FROM" +
			"  ClientInstallations" +
			" WHERE" +
			"  EID = ?" +
			" UNION ALL" +
			" SELECT" +
			"  COUNT(0)" +
			" FROM" +
			"  ClientInstallations2" +
			" WHERE" +
			"  EID = ?"
		);*/
		Map<String, String> args = new HashMap<String, String>();
		args.put("eid", EID);
        int count = namedParamJdbcTemplate.queryForObject(EID_EXISTS_QUERY, args, Integer.class);
		logger.debug("Found " + count + " registered EID");

		return (count > 0);
	}

    public int registerLicenseForUser(String userName, String tenantName, String EID, String ipAddress, String hostname) {
		logger.debug("Add record into ClientInstallations for EID = " + EID);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
						.withTableName("ClientInstallations");

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("userName", userName)
				.addValue("tenantName", tenantName)
				.addValue("EID", EID)
				.addValue("ipAddress", ipAddress)
				.addValue("hostname", hostname);

		int affected = insert.execute(namedParameters);
		logger.debug("Rows affected: " + affected);

        return affected;
    }

	public int registerLicenseForUser2(String userName, String displayName, String tenantName, String EID, String ipAddress, String hostname) {
		logger.debug("Add record into ClientInstallations2 for EID = " + EID);

		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
						.withTableName("ClientInstallations2");

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("userName", userName)
				.addValue("displayName", displayName)
				.addValue("tenantName", tenantName)
				.addValue("EID", EID)
				.addValue("ipAddress", ipAddress)
				.addValue("hostname", hostname);

		int affected = insert.execute(namedParameters);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

    public int updateRegisterLicenseForGuest(String userName, String guestName, Room room) {
        logger.debug("Update ClientInstallations in case og Guest's registration for userName = " + userName);

        StringBuffer sakSqlstm = new StringBuffer(
            " UPDATE" +
            "  ClientInstallations" +
            " SET" +
            "  userName = :userNameNew," +
            "  roomName = :roomName," +
            "  roomOwner = :roomOwner" +
            " WHERE" +
            "  userName = :userName"
        );

        SqlParameterSource sakParameters = new MapSqlParameterSource()
                .addValue("userNameNew", "Guest(" + guestName + ")")
                .addValue("roomName", room.getRoomName())
                .addValue("roomOwner", room.getOwnerName())
                .addValue("userName", "Guest(" + userName + ")");

        int affected = namedParamJdbcTemplate.update(sakSqlstm.toString(), sakParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

	public int updateRegisterLicenseForGuest2(String userName, String guestName, Room room) {
		logger.debug("Update ClientInstallations2 in case og Guest's registration for userName = " + userName);

		StringBuffer sakSqlstm = new StringBuffer(
			" UPDATE" +
			"  ClientInstallations2" +
			" SET" +
			"  userName = :userNameNew," +
			"  displayName = :displayName," +
			"  roomName = :roomName," +
			"  roomOwner = :roomOwner" +
			" WHERE" +
			"  userName = :userName"
		);

		SqlParameterSource sakParameters = new MapSqlParameterSource()
				.addValue("userNameNew", "Guest")
				.addValue("displayName", guestName)
				.addValue("roomName", room.getRoomName())
				.addValue("roomOwner", room.getOwnerName())
				.addValue("userName", userName);

		int affected = namedParamJdbcTemplate.update(sakSqlstm.toString(), sakParameters);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

    public int getNumOfConsumedLicenses(String tenantName) {
       logger.debug("Get count of registered EID from ClientInstallations for tenant = " + tenantName);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  ClientInstallations" +
            " WHERE" +
            "  tenantName = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, tenantName
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

	public int getNumOfConsumedLicenses2(String tenantName) {
	   logger.debug("Get count of registered EID from ClientInstallations2 for tenant = " + tenantName);

		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  COUNT(0)" +
			" FROM" +
			"  ClientInstallations2" +
			" WHERE" +
			"  tenantName = ?"
		);

		List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				}, tenantName
		);

		return li.size() > 0 ? li.get(0) : 0;
	}

	/**
	 * SQL to update the authroized flag and consumes line flag
	 */
	private static final String UPDATE_ENDPOINT_FLAG = "update Endpoints set authorized =:flag, consumesLine =:consumesLine where endpointGUID =:GUID";

	public int setAuthorizedFlagForEndpoint(Endpoint endpoint) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("flag", endpoint.getAuthorized())
				.addValue("GUID", endpoint.getGuid()).addValue("consumesLine", endpoint.getConsumesLine());

		int updated = namedParamJdbcTemplate.update(UPDATE_ENDPOINT_FLAG, namedParameters);
		logger.debug("Rows affected for Endpoints: " + updated);

		return updated;

	}

    private static final String USER_FOR_BIND_CHALLENGE = "SELECT m.sak, m.bindUserRequestID, mr.roleName as userRole FROM Member m, MemberRole mr WHERE m.memberID =:memberID and m.roleID = mr.roleID";

	/**
	 * Returns the SAK and BindUserRequestID for bind user challenge
	 *
	 * @param memberID
	 * @return
	 */
	public User getUserForBindChallengeResponse(int memberID) {
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberID", memberID);
		List<User> users = namedParamJdbcTemplate.query(
				USER_FOR_BIND_CHALLENGE, namedParamsMap, new UserRowMapper());

		if (users.isEmpty()) {
			return null;
		}

		return users.get(0);
	}

	/**
	 *
	 */
	private static final String GET_MEMBER_FOR_BAK = "select memberID, username, bak, bak_creation_time from Member where bak =:bak";

	/**
	 * Returns Member, browser access key(bak) and bak creation time based on
	 * the browser access key (bak)
	 *
	 * @param Bak
	 * @return
	 */
	public List<Member> getMemberForBak(String bak) {

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("bak", bak);
		List<Member> members = namedParamJdbcTemplate.query(GET_MEMBER_FOR_BAK, paramsMap, new BeanPropertyRowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNumber) throws SQLException {
				Member member = new Member();
				member.setMemberID(rs.getInt("memberID"));
				member.setBakCreationTime(rs.getTimestamp("bak_creation_time"));
				member.setUsername(rs.getString("username"));
				return member;
			}

		});

		return members;
	}

	/**
	 * Empty method to clear userDetailCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [MemberDaoJdbcImpl] else cache wont get cleared.
	 *
	 * @param tenantId
	 * @param userName
	 * @return
	 */
	@TriggersRemove(cacheName = { "userDataCache" }, keyGenerator = @KeyGenerator(name = "StringCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateMember(int tenantId, String userName) {
		return 0;
	}

	public static final String DELETE_PAK = "UPDATE Member set pak = NULL where memberId = ? and tenantId = ?";

	public static final String DELETE_PAK2 = "UPDATE Member set pak2 = NULL where memberId = ? and tenantId = ?";

	/**
	 * Deletes pak value of the Member
	 *
	 * @param tenantId
	 * @param memberId
	 * @return
	 */
	@Override
	public int deletePak(int tenantId, int memberId) {
		int updateCount = getJdbcTemplate().update(DELETE_PAK, memberId,
				tenantId);
		return updateCount;
	}

	/**
	 * Deletes pak2 value of the Member
	 *
	 * @param tenantId
	 * @param memberId
	 * @return
	 */
	@Override
	public int deletePak2(int tenantId, int memberId) {
		int updateCount = getJdbcTemplate().update(DELETE_PAK2, memberId,
				tenantId);
		return updateCount;
	}

    /**
     * Fetch the Admin User for the given tenant.
     * @return
     */
	@Override
    public List<User> getUserByRole(int tenantId, int roleId){
        Map<String, Integer> paramsMap = new HashMap<>();
        paramsMap.put("tenantId", tenantId);
        paramsMap.put("roleId", roleId);
        String sqlQuery="select username, memberID, tenantID,memberName, roleID from Member where tenantID = :tenantId and roleId=:roleId";
        List<User> users = namedParamJdbcTemplate.query(sqlQuery, paramsMap, new BeanPropertyRowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setMemberID(rs.getInt("memberID"));
                user.setTenantID(rs.getInt("tenantID"));
                user.setMemberName(rs.getString("memberName"));
                user.setUserRole(Integer.toString(rs.getInt("roleID")));
                return user;
            }
        });
	    return users;
    }

}