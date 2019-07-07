package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vidyo.bo.*;

import com.vidyo.service.LicensingServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.framework.security.utils.VidyoUtil;

public class SystemDaoJdbcImpl extends JdbcDaoSupport implements ISystemDao {

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

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(SystemDaoJdbcImpl.class.getName());

    private final static String INSERT_CONFIGURATION_VALUE = "INSERT INTO Configuration ("
			+ " tenantID, configurationName, configurationValue, configDate)"
			+ " VALUES (:tenantID, :configName, :configValue, :date)";

	private final static String DELETE_CONFIGURATION_BY_NAME = "DELETE FROM Configuration WHERE"
			+ " configurationName =? AND"
			+ " tenantID = ?";

	private final static String UPDATE_CONFIGURATION_BY_NAME = " UPDATE Configuration SET "
			+ " configurationValue = ?, updateTime = ? WHERE"
			+ " configurationName = ? AND"
			+ " tenantID = ?";

	private final static String REPLACE_CONFIGURATION_VALUE_BY_NAME_AND_VALUE = " UPDATE Configuration SET "
			+ " configurationValue = ?, updateTime = ? WHERE"
			+ " configurationName = ? AND"
			+ " configurationValue = ? AND"
			+ " tenantID = ?";

	private final static String UPDATE_CONFIGURATIONS = " UPDATE Configuration SET "
			+ " configurationValue = ?, updateTime = ? WHERE"
			+ " configurationName = ?";

	private final static String GET_CONFIGURATION_BY_TENANT_AND_NAME = "SELECT * FROM Configuration WHERE"
			+ " configurationName = ? AND"
			+ " tenantID = ?";

	private final static String GET_CONFIGURATIONS_BY_TENANT_AND_CONFIG_NAMES = "SELECT * FROM Configuration WHERE"
			+ " tenantID = :tenantID AND"
			+ " configurationName in (:configNames)";

	private final static String INSERT_CONFIGURATION = "INSERT INTO Configuration ("
			+ " tenantID, configurationName, configurationValue, configDate)"
			+ " VALUES (?, ?, ?, ?)";

    /*
    * END BANNERS section
     */
    public List<Language> getLanguages() {
    	return getLanguages(false, -1);
    }

    public Long getCountLanguages() {
    	return getCountLanguages(false);
    }

    public List<Language> getLanguages(Boolean displaySystemLanguage, int tenantId) {
        logger.debug("Get records from Language");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  langID," +
            "  langCode," +
            "  langName" +
            " FROM" +
            "  Language"
        );

        if(!displaySystemLanguage){
        	sqlstm.append(
                " WHERE" +
                "  langName NOT IN ('System Language')"
        	);

            return getJdbcTemplate().query(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Language.class));
        }
        else{
        	sqlstm.append(
                " UNION" +
                " SELECT" +
                "  l.langID," +
                "  c.configurationValue AS 'langCode'," +
                "  l.langName" +
                " FROM" +
                "  Language l," +
                "  Configuration c" +
                " WHERE" +
                "    l.langName = 'System Language'" +
                " AND  c.configurationName = 'SystemLanguage'" +
                " AND  c.tenantID = ?"
        	);

            return getJdbcTemplate().query(sqlstm.toString(),
            		BeanPropertyRowMapper.newInstance(Language.class), tenantId);
        }
    }

    public Long getCountLanguages(Boolean displaySystemLanguage) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Language"
        );

        if(!displaySystemLanguage)
        	sqlstm.append(
        			" WHERE" +
        			"  langName NOT IN ('System Language')"
        	);

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Language getSystemLang(int tenant) {
        logger.debug("Get SystemLanguage from Configuration");
        String configValue = "";
        Configuration config = getConfiguration(tenant, "SystemLanguage");
		if (config != null && !StringUtils.isBlank(config.getConfigurationValue())){
			configValue = config.getConfigurationValue();
		}

        if (configValue == null || configValue.trim().length() == 0) {
            // by default
            Language lang = new Language();
            lang.setLangCode("en");
            lang.setLangID(1);
            lang.setLangName("English");

            return lang;
        } else {
            StringBuffer sqlstm_get = new StringBuffer(
                " SELECT" +
                "  langID," +
                "  langCode," +
                "  langName" +
                " FROM" +
                "  Language" +
                " WHERE langCode = ?" +
                " AND" +
                " langName NOT IN ('System Language')"
            );

            return getJdbcTemplate().queryForObject(sqlstm_get.toString(),
                    BeanPropertyRowMapper.newInstance(Language.class), configValue);
        }
    }

    public int saveSystemLang( int tenant, String langCode) {
	    return updateConfiguration(tenant, "SystemLanguage", langCode);
    }

    public int getLangIDforLangCode(String langCode) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  langID," +
            "  langCode," +
            "  langName" +
            " FROM" +
            "  Language" +
            " WHERE" +
            "  langCode = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, langCode
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

	@Cacheable(cacheName = "vidyoManagerServiceCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	@TriggersRemove(cacheName = "componentsUserDataCache", removeAll = true)
	public Service getVidyoManagerService(int tenant) {
		logger.debug("Executing getVidyoManagerService to retrieve vmuser and password");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleName", "VidyoManager");
        params.put("tenantId", tenant);
        StringBuffer sqlstm = new StringBuffer(
                " SELECT " +
                " CONCAT(FQDN, ':', SOAP_PORT) as url," +
                " username as user," +
                " password as password" +
                " FROM" +
                " vidyo_manager_config vmc, components comp, components_type ct" +
                " WHERE" +
                " vmc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and" +
                " vmc.comp_id in (SELECT serviceID FROM TenantXservice WHERE tenantID = ?) LIMIT 0, 1"
            );
        Service service = getJdbcTemplate().queryForObject(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Service.class), "VidyoManager", tenant);
        String password = VidyoUtil.decrypt(service.getPassword());
        service.setPassword(password);
        Configuration config = getConfiguration(0, "COMPONENTS_ENCRYPTION");
        if(config != null && config.getConfigurationValue() != null && Boolean.valueOf(config.getConfigurationValue())) {
        	//enable https in URL
        	service.setUrl("https://" + service.getUrl());
        }
        logger.debug("Service object found in db {}", service);
        return service;
    }

    public boolean assignLocationToGroups(int locationID, String groupIDs) {
        logger.debug("Update Member's locationID by specified groups");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
	        "   Member m"+
	        "   LEFT JOIN Room r"+
	        "   ON m.memberID=r.memberID" +
            " SET m.locationID = ?"+
            " WHERE"+
	        "   r.roomTypeID=1"+
            "  AND"+
            "   r.groupID IN ( "+groupIDs+" )"
        );
        try {
            int affected = getJdbcTemplate().update(sqlstm.toString(), locationID);
            logger.debug("Rows affected: " + affected);
            return true;
        }
        catch(Exception anyEx) {
            logger.warn(anyEx.getMessage());
            return false;
        }
    }

    public CdrAccess readCDR(String ip, String cdrEnabled, String cdrFormat, String cdrAllowDeny, String cdrAccessPassword, String cdrAllowToDelete) {
		CdrAccess ca = new CdrAccess();

		ca.setEnabled(Integer.parseInt(cdrEnabled));
		ca.setFormat(cdrFormat);
		ca.setAllowdeny(Integer.parseInt(cdrAllowDeny));
		ca.setId("cdraccess");
		ca.setPassword(cdrAccessPassword);
		ca.setIp(ip);
		ca.setAllowdelete(Integer.parseInt(cdrAllowToDelete));

		/*if (!ip.equalsIgnoreCase("")) {
			logger.debug("User 'vidyo' calling: SHOW GRANTS FOR cdraccess@'"+ip+"'");
			StringBuffer sqlstm = new StringBuffer(
				" SHOW GRANTS FOR cdraccess@'" + ip + "';"
			);

			List<String> ls = null;
			try {
				ls = getJdbcTemplate().query(sqlstm.toString(),
					new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString(1);
						}
					}
				);
			} catch (Exception e){
				logger.error("readCDR(): SHOW GRANTS failed: "+e.getMessage());
			}

			if (ls != null) {
				for (String grantLine : ls) {
					if (grantLine.contains("ConferenceCall")) {
						if (grantLine.toUpperCase().contains("DELETE")) {
							ca.setAllowdelete(1);
							break;
						}
					}
				}
			}
		}*/
        return ca;
    }

	public boolean grantCDR(CdrAccess ca){
		try {
			ShellExecutor.execute("sudo -n /opt/vidyo/bin/firewallSettings.sh enableCdr " + ca.getIp());
		} catch (ShellExecutorException e) {
			logger.error("Error while enabling CDR access", e);
			return false;
		}
		
		//Remove existing CDR access
        denyCDR(ca.getIp());

        /*StringBuffer sqlstm3;
		StringBuffer sqlstm4;
		StringBuffer sqlstm5;

        if (ca.getAllowdelete() == 1) {
			sqlstm3 = new StringBuffer("GRANT SELECT,DELETE ON portal2.ConferenceCall2 TO "+ca.getId()+"@'"+ca.getIp()+"' IDENTIFIED BY ?;");
        } else {
			sqlstm3 = new StringBuffer("GRANT SELECT ON portal2.ConferenceCall2 TO "+ca.getId()+"@'"+ca.getIp()+"' IDENTIFIED BY ?;");
        }
        sqlstm4 = new StringBuffer(" GRANT SELECT ON portal2.ClientInstallations TO "+ca.getId()+"@'"+ca.getIp()+"' IDENTIFIED BY ?;");
		sqlstm5 = new StringBuffer(" GRANT SELECT ON portal2.ClientInstallations2 TO "+ca.getId()+"@'"+ca.getIp()+"' IDENTIFIED BY ?;");

        try {
			getJdbcTemplate().update(sqlstm3.toString(), ca.getPassword());
			getJdbcTemplate().update(sqlstm4.toString(), ca.getPassword());
			getJdbcTemplate().update(sqlstm5.toString(), ca.getPassword());
            getJdbcTemplate().update("FLUSH PRIVILEGES;");

        } catch(Exception e){
            logger.error("grantCDR(): GRANT ...; is failed: "+e.getMessage());
            return false;
        }*/
		// Invoke the sudo script to handle the cdraccess user
		try {
			ShellExecutor
					.execute(
							new String[] { "sudo", "-n", "/opt/vidyo/bin/cdr_user.sh", "CREATE", "cdraccess",
									ca.getIp(), (ca.getAllowdelete() == 1 ? "Y" : "N") },
							Arrays.asList(ca.getPassword()));
		} catch (ShellExecutorException e) {
			logger.error("Error while enabling CDR access", e);
			return false;
		}

        return true;
    }

	public boolean denyCDR(String cdrAccessFromHost) {
		/*try {
			StringBuffer sqlstm = new StringBuffer(" DROP USER cdraccess@'" + cdrAccessFromHost + "';");
			getJdbcTemplate().update(sqlstm.toString());
			getJdbcTemplate().update("FLUSH PRIVILEGES;");
		} catch (Exception e) {
			logger.error("denyCDR(): DROP USER is failed:" + e.getMessage());
			return true; // ignore this error - maybe specified user does not exist
		}*/
		// Remove the existing cdraccess user
		try {
			ShellExecutor.execute("sudo -n /opt/vidyo/bin/cdr_user.sh DROP cdraccess " + cdrAccessFromHost);
		} catch (ShellExecutorException e) {
			logger.error("Error while revoking CDR access, may be user does not exist", e);
			return false;
		}
		return true;
	}

	public int purgeCDR(CdrFilter cf) {
		StringBuffer sqlstm = new StringBuffer(
			" DELETE" +
			" FROM" +
			"  ConferenceCall2" +
			" WHERE" +
			"  1"
		);

		if (cf.getOneall().equalsIgnoreCase("one")) {
			sqlstm.append(" AND TenantName in (select TenantName from Tenant where tenantID = :TenantName)");
		}

		if (cf.getDateperiod().equalsIgnoreCase("range")) {
			sqlstm.append(" AND JoinTime BETWEEN :StartDate AND :EndDate");
		}


		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("TenantName", cf.getTenantName());
		namedParamsMap.put("StartDate", cf.getStartdate());
		namedParamsMap.put("EndDate", cf.getEnddate());

		int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParamsMap);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

    public List<CdrRecord> exportCDRBatched(CdrFilter cf, int limit, int offset) {

        logger.error("called exportCDRBatched, limit:" + limit + ", offset: " + offset);

        StringBuffer sqlstm = new StringBuffer(
                        "SELECT " +
                                "IFNULL(CallID, '') as CallID, " +
                                "IFNULL(UniqueCallID, '') as UniqueCallID, " +
                                "IFNULL(ConferenceName,'') as ConferenceName, " +
                                "IFNULL(TenantName,'') as TenantName, " +
                                "IFNULL(ConferenceType,'') as ConferenceType, " +
                                "IFNULL(EndpointType,'') as EndpointType, " +
                                "IFNULL(CallerID,'') as CallerID, " +
                                "IFNULL(CallerName,'') as CallerName, " +
                                "IFNULL(JoinTime,'') as JoinTime, " +
                                "IFNULL(LeaveTime,'') as LeaveTime, " +
                                "IFNULL(CallState,'') as CallState, " +
                                "IFNULL(Direction,'') as Direction, " +
                                "IFNULL(RouterID,'') as RouterID, " +
                                "IFNULL(GWID,'') as GWID, " +
                                "IFNULL(GWPrefix,'') as GWPrefix, " +
                                "IFNULL(ReferenceNumber,'') as ReferenceNumber, " +
                                "IFNULL(ApplicationName,'') as ApplicationName, " +
                                "IFNULL(ApplicationVersion,'') as ApplicationVersion, " +
                                "IFNULL(ApplicationOS,'') as ApplicationOS, " +
                                "IFNULL(DeviceModel,'') as DeviceModel, " +
                                "IFNULL(EndpointPublicIPAddress, '') as EndpointPublicIPAddress, " +
                                "IFNULL(AccessType,'') as AccessType, " +
                                "IFNULL(RoomType,'') as RoomType, " +
                                "IFNULL(RoomOwner,'') as RoomOwner, " +
                                "IFNULL(CallCompletionCode,'') as CallCompletionCode, " +
                                "IFNULL(Extension,'') as Extension, " +
                                "IFNULL(EndpointGUID,'') as EndpointGUID " +
                                "FROM ConferenceCall2 " +
                                "WHERE ");


        //MySQLCodec mySQLCodec = new MySQLCodec(MySQLCodec.Mode.STANDARD);
        Map<String, Object> paramMap = new HashMap<>();

        if (cf.getOneall().equalsIgnoreCase("one")) {
            //String tenantNameEnc = ESAPI.encoder().encodeForSQL(mySQLCodec, cf.getTenantName());
            sqlstm.append(" TenantName =:tenantName AND ");
            paramMap.put("tenantName", cf.getTenantName());
        }

        if (cf.getDateperiod().equalsIgnoreCase("range")) {
            sqlstm.append(" `JoinTime` BETWEEN :startDate AND :endDate ");
            paramMap.put("startDate", cf.getStartdate());
            paramMap.put("endDate", cf.getEnddate());
        }
        sqlstm.append(" LIMIT :limit  OFFSET :offset");
        paramMap.put("offset", offset);
        paramMap.put("limit", limit);
        return namedParamJdbcTemplate.query(sqlstm.toString(), paramMap, BeanPropertyRowMapper.newInstance(CdrRecord.class));
    }

	public int getExportCDRCount(CdrFilter cf) {

		StringBuffer sqlstm = new StringBuffer(
				"SELECT COUNT(*) " +
						"FROM ConferenceCall2 " +
						"WHERE ");
		Map<String, Object> paramMap = new HashMap<>();
		if (cf.getOneall().equalsIgnoreCase("one")) {
            sqlstm.append("TenantName =:tenantName AND ");
            paramMap.put("tenantName", cf.getTenantName());
		}

		if (cf.getDateperiod().equalsIgnoreCase("range")) {
            sqlstm.append(" `JoinTime` BETWEEN :startDate AND :endDate ");
            paramMap.put("startDate", cf.getStartdate());
            paramMap.put("endDate", cf.getEnddate());
		}

		return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), paramMap, Integer.class);

	}

    // TODO: Create ClientInstalations2Dao and move this method their
    public long getTotalInstallsInUse(String cdrFormat) {
        logger.debug("Get Total Installs");

		StringBuffer sqlstm = new StringBuffer(
			" SELECT SUM(num) FROM (" +
			" SELECT  COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations ci" +
			" UNION ALL" +
			" SELECT COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations2 ci2" +
			" ) as num"
		);

		logger.debug("getCountAllInstalls. SQL - " + sqlstm.toString());

		List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
			new RowMapper<Long>() {
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			}
		);

		return ll.size() > 0 ? ll.get(0) : 0l;
    }

    // TODO: Move IMemberDao this method
    public long getTotalSeatsInUse() {
         logger.debug("Get Total Seats");
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            " (m.roleID = 1 OR m.roleID = 2 OR m.roleID = 3)" +
            " AND" +
            " m.active = 1" +
            " AND" +
            " m.allowedToParticipate = 1"
                /* Currently counting Admin, operator, and Normal Users */
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    @Override
    public long getTotalPortsInUse(String licVersion) {
        StringBuffer sqlstm = new StringBuffer();

        if (licVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)) {
            sqlstm.append(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  Conferences c" +
                " WHERE" +
                "  c.conferenceType = ?"
            );
            logger.debug("getCountPorts. SQL - " + sqlstm.toString());

            List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                },
                "C"
            );

            return ll.size() > 0 ? ll.get(0) : 0l;
        } else if (licVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_21)) {
            sqlstm.append(
                "  SELECT COUNT(0) as num" +
                "  FROM Conferences c" +
                "  INNER JOIN Endpoints e ON (e.endpointID = c.endpointID AND c.endpointType = :endpointType and e.consumesLine = :consumesLine)" +
                "  WHERE" +
				"   c.conferenceType IN (:conferenceType)"
            );
            logger.debug("getCountPorts. SQL - " + sqlstm.toString());

            Map<String, Object> params = new HashMap<>();
            params.put("endpointType", "D");
            params.put("consumesLine", 1);
            List<String> confTypes = new ArrayList<>(3);
            confTypes.add("C");
            confTypes.add("P");
            confTypes.add("F");
            params.put("conferenceType", confTypes);

            List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), params,
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
            );

            return ll.size() > 0 ? ll.get(0) : 0l;
        } else if (licVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) {
            sqlstm.append(
                    "SELECT (SELECT COUNT(*) FROM Conferences c) - " +
                            " (SELECT COUNT(*) FROM Conferences c, Endpoints e " +
                            " WHERE e.endpointID = c.endpointID and e.consumesLine = 0)"
            );
            logger.debug("getCountPorts. SQL - " + sqlstm.toString());

            long count = getJdbcTemplate().queryForObject(sqlstm.toString(), Long.class);
            return count;
        }

        return 0;
    }

    public List<MemberRoles> getFromRoles(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName NOT IN ('Super', 'Legacy')"
        );
        sqlstm.append(" AND mr.roleID NOT IN (SELECT roleID FROM TenantXauthRole WHERE tenantID = ?)");

        return getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(MemberRoles.class), tenant);
    }

    @Cacheable(cacheName="tenantConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public List<MemberRoles> getToRoles(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName NOT IN (:roles)"
        );
        sqlstm.append(" AND mr.roleID IN (SELECT roleID FROM TenantXauthRole WHERE tenantID =:tenantId)");
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> roles = new ArrayList<String>(2);
        roles.add("Super");
        roles.add("Legacy");
        params.put("roles", roles);
        params.put("tenantId", tenant);

        return namedParamJdbcTemplate.query(sqlstm.toString(), params, BeanPropertyRowMapper.newInstance(MemberRoles.class));
    }

    @TriggersRemove(cacheName={"tenantConfigCache", "tenantGuestConfigCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int insertTenantXauthRole( int tenantID, int roleID) {
        logger.debug("Add record into TenantXauthRole for tenantID = " + tenantID + " and roleID = " + roleID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("TenantXauthRole");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("roleID", roleID);

        int affected = insert.execute(namedParameters);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    @TriggersRemove(cacheName={"tenantConfigCache", "tenantGuestConfigCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteTenantXauthRole(int tenantID) {
        logger.debug("Remove record from TenantXauthRole for tenantID = " + tenantID);

        StringBuffer sqlstmCallFrom = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXauthRole" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affectedCallFrom = getJdbcTemplate().update(sqlstmCallFrom.toString(), tenantID);
        logger.debug("Rows affected: " + affectedCallFrom);

        return tenantID;
    }

    public List<Control> getCurrentTenantCalls(int tenantID, ControlFilter filter) {
        List<Control> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            " COALESCE(" +
            "  CASE WHEN c.endpointType = 'D' THEN" +
            "   CASE WHEN e.memberType = 'R' THEN" +
            "    r.roomID" +
            "   ELSE" +
            "    0" +
            "   END" +
            "  WHEN c.endpointType = 'V' THEN" +
            "   CASE WHEN ve.entityID IS NOT NULL THEN" +
            "    ve.entityID" +
            "   ELSE" +
            "    0" +
            "   END" +
			"  WHEN c.endpointType = 'R' THEN" +
			"   CASE WHEN re.entityID IS NOT NULL THEN" +
			"    re.entityID" +
			"   ELSE" +
			"    0" +
			"   END" +
            "  END" +
            ", 0)" +
            " as roomID," +
            " c.endpointID," +
            " c.endpointType," +
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
            "  c.audio," +
            "  c.video," +
            "  CONCAT(CONCAT(c.conferenceName, ': '), (SELECT COUNT(innerc.conferenceName) FROM Conferences innerc WHERE innerc.conferenceName = c.conferenceName)) as conferenceName," +
            "  c.conferenceType," +
            "  t.tenantName," +
            "  c.VRID," +
            "  c.VRName," +
            "  c.GroupID," +
            "  c.GroupName" +
            " FROM  Conferences c" +
            " INNER JOIN Tenant t ON (t.tenantID=c.tenantID)" +
            " LEFT JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType='D')" +
            " LEFT JOIN VirtualEndpoints ve ON (ve.endpointID=c.endpointID AND c.endpointType='V')" +
			" LEFT JOIN RecorderEndpoints re ON (re.endpointID=c.endpointID AND c.endpointType='R')" +
            " LEFT JOIN Member m ON (m.memberID=e.memberID AND e.memberType='R')" +
            " LEFT JOIN Guests g ON (g.guestID=e.memberID AND e.memberType='G')" +
            " LEFT JOIN Room r ON (m.memberID=r.memberID AND r.roomTypeID=1)" +
            " " +
            " WHERE" +
            "  c.tenantID = :tenantID"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantID", tenantID);

        if (filter != null) {
        	if (filter.getProperty() != null) {
        		if(!ControlFilter.sortList.contains(filter.getProperty())) {
        			filter.setProperty("conferenceName");
        		}
        		sqlstm.append(" GROUP BY ").append(filter.getProperty());
        		if (filter.getDirection() != null){
        			if(!ControlFilter.controlFilterDirs.contains(filter.getDirection())) {
        				filter.setDirection("ASC");
        			}
        			sqlstm.append(" ").append(filter.getDirection());
        		} else {
        			sqlstm.append(" ASC ");
        		}
        	}
        	if ( filter.getSort() != null && filter.getSort().trim().length() > 0) {
        		if(!ControlFilter.sortList.contains(filter.getProperty())) {
        			filter.setSort("conferenceName");
        		}
        		sqlstm.append(" ORDER BY ").append(filter.getSort());
        	} else {
        		sqlstm.append(" ORDER BY ").append("conferenceName");
        	}

            if (filter.getDir() != null && filter.getDir().trim().length() > 0) {
            	sqlstm.append(" ").append(filter.getDir());
            } else {
            	sqlstm.append(" ").append("ASC");
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Control.class));
        return rc;
    }

    public Long getCountCurrentTenantCalls(int tenantID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Conferences c" +
            " INNER JOIN Tenant t ON (t.tenantID=c.tenantID)" +
            " WHERE" +
            "  c.tenantID = ?"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, tenantID
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public List<Control> getCurrentCalls(ControlFilter filter) {
        List<Control> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            " c.endpointID," +
            " c.endpointType," +
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
            "   'Legacy'" +
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
            "  c.audio," +
            "  c.video," +
            "  CONCAT(CONCAT(c.conferenceName, ': '), (SELECT COUNT(innerc.conferenceName) FROM Conferences innerc WHERE innerc.conferenceName = c.conferenceName)) AS conferenceName," +
            "  c.conferenceType," +
            "  t.tenantName," +
            "  c.VRID," +
            "  c.VRName," +
            "  c.GroupID," +
            "  c.GroupName" +
            " FROM  Conferences c" +
            " LEFT JOIN Endpoints e ON e.endpointID=c.endpointID AND c.endpointType='D'" +
            " LEFT JOIN VirtualEndpoints ve ON ve.endpointID = c.endpointID AND c.endpointType= 'V'" +
			" LEFT JOIN RecorderEndpoints re ON re.endpointID = c.endpointID AND c.endpointType = 'R'" +
            " LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = 'R'" +
            " LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = 'G'" +
            " LEFT JOIN Tenant t ON t.tenantID = c.tenantID" +
            " LEFT JOIN Room r ON m.memberID = r.memberID AND r.roomTypeID = 1 "
        );

		Map<String, Object> namedParamsMap = new HashMap<String, Object>();

		String sortColumn = filter.getSort();
		if (!ControlFilter.sortList.contains(sortColumn)) {
			sortColumn = "conferenceName";
		}

		if (filter != null) {
			if (sortColumn.equalsIgnoreCase("ext")) {
				sortColumn = sortColumn + "+0";
			}
			if (filter.getProperty() != null) {
	        		if(!ControlFilter.sortList.contains(filter.getProperty())) {
	        			filter.setProperty("conferenceName");
	        		}
				sqlstm.append(" GROUP BY ").append(filter.getProperty());
				if (filter.getDirection() != null) {
	        			if(!ControlFilter.controlFilterDirs.contains(filter.getDirection())) {
	        				filter.setDirection("ASC");
	        			}
					sqlstm.append(" ").append(filter.getDirection());
				} else {
					sqlstm.append(" ASC ");
				}
			}

			sqlstm.append(" ORDER BY ").append(sortColumn);

			sqlstm.append(" ").append(filter.getDir());

			sqlstm.append(" LIMIT :start, :limit");
			namedParamsMap.put("start", filter.getStart());
			namedParamsMap.put("limit", filter.getLimit());
		}

		rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
				BeanPropertyRowMapper.newInstance(Control.class));
		return rc;
    }

    public Long getCountCurrentCalls() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Conferences c" +
            " INNER JOIN Tenant t ON (t.tenantID=c.tenantID)"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    private static final String VIDYO_REPLAY_SERVICE = "SELECT comp.id as serviceID, ct.id as roleID, comp.name as serviceName, vrc.username as user, vrc.password as password, "
    		+ "t.tenantReplayUrl AS 'url' FROM vidyo_replay_config vrc, components comp, components_type ct, Tenant t WHERE comp.comp_type_id = ct.id AND ct.name = 'VidyoReplay' "
    		+ "AND comp.id IN (SELECT x.serviceID FROM TenantXservice x WHERE x.tenantID = ?) AND vrc.comp_id = comp.id AND t.tenantID = ? ";

    public Service getVidyoReplayService(int tenant) {
        try {
            Service service = getJdbcTemplate().queryForObject(VIDYO_REPLAY_SERVICE, BeanPropertyRowMapper.newInstance(Service.class), tenant, tenant);
            service.setPassword(VidyoUtil.decrypt(service.getPassword()));
            return service;
        } catch (Exception e) {
            return null;
        }
    }

    /*public List<RecorderEndpoint> getAvailableRecorders(int tenant, RecorderEndpointFilter filter) {
        List<RecorderEndpoint> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  endpointID," +
            "  serviceID," +
            "  recID," +
            "  description," +
            "  prefix," +
            "  endpointGUID," +
            "  status," +
            "  updateTime" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  serviceID IN (SELECT x.serviceID FROM TenantXservice x WHERE x.tenantID = ?)" +
            " AND" +
            "  status = 1" // "Online" status
        );

        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT ").append(filter.getStart()).append(", ").append(filter.getLimit());
        }

        rc = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(RecorderEndpoint.class), tenant);
        return rc;
    }

    public Long getCountAvailableRecorders(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  serviceID IN (SELECT x.serviceID FROM TenantXservice x WHERE x.tenantID = ?)" +
            " AND" +
            "  status = 1"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, tenant
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }*/

	public List<RecorderPrefix> getAvailableRecorderPrefixes(int tenant, RecorderEndpointFilter filter) {
		List<RecorderPrefix> rc;
		StringBuffer sqlstm = new StringBuffer(
				" SELECT DISTINCT" +
						"  description," +
						"  prefix" +
						" FROM" +
						"  RecorderEndpoints" +
						" WHERE" +
						"  serviceID IN (SELECT x.serviceID FROM TenantXservice x WHERE x.tenantID = ?)" +
						" AND" +
						"  status = 1" // "Online" status
		);

		if (filter != null) {
			sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
			sqlstm.append(" LIMIT ").append(filter.getStart()).append(", ").append(filter.getLimit());
		}

		rc = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(RecorderPrefix.class), tenant);
		return rc;
	}

	public Long getCountAvailableRecorderPrefixes(int tenant) {
		StringBuffer sqlstm = new StringBuffer(
				" SELECT count(DISTINCT  description, prefix)" +
						" FROM" +
						"  RecorderEndpoints" +
						" WHERE" +
						"  serviceID IN (SELECT x.serviceID FROM TenantXservice x WHERE x.tenantID = ?)" +
						" AND" +
						"  status = 1" // "Online" status
		);

		List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
				new RowMapper<Long>() {
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				}, tenant
		);

		return ll.size() > 0 ? ll.get(0) : 0l;
	}

	/**
	 * Saves the HotStandby Security key in the respective table
	 *
	 * @param key
	 * @return
	 */
	public int saveHotStandbySecurityKey(String key) {
		String INSERT_SEC_KEY = "INSERT INTO HotStandbySecKeys (pub_key, creation_date) values(:key, :creation_date)";
		SqlParameterSource paramSource = new MapSqlParameterSource().addValue(
				"key", key).addValue("creation_date", Calendar.getInstance().getTime());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		namedParamJdbcTemplate.update(INSERT_SEC_KEY, paramSource, generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	private static final String UPDATE_VIDYOMANAGER_CREDENTIALS = "update vidyo_manager_config vm, components c, components_type ct SET vm.username =:userId, vm.password =:password WHERE vm.comp_id = c.id and c.comp_type_id = ct.id and ct.name =:roleName";

	/**
	 * Updates the credentials of a service based on rolename
	 *
	 * @param userId
	 * @param password
	 * @param roleName
	 * @return
	 */
	@TriggersRemove(cacheName = { "vidyoManagerIdCache",
			"vmConnectAddressCache",
			"vidyoManagerServiceCache", "vidyoManagerServiceStubCache",
			"licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public int updateServiceCredentials(String userId, String password, String roleName) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("password", VidyoUtil.encrypt(password));
		paramsMap.put("roleName", roleName);
		int updateCount = namedParamJdbcTemplate.update(UPDATE_VIDYOMANAGER_CREDENTIALS, paramsMap);
		return updateCount;
	}

	/**
	 * Empty implementation which clears cache [VidyoManagerData, LicenseData,
	 * VMID] through annotation.
	 *
	 * @param tenantId
	 */
	@TriggersRemove(cacheName = { "vidyoManagerIdCache",
			"vmConnectAddressCache",
			"vidyoManagerServiceCache", "vidyoManagerServiceStubCache",
			"licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public void clearVidyoManagerAndLicenseCache(int tenantId) {
		// Empty implementation, just has to be proxied through AOP.
	}

    @Override
	public int insertConfiguration(int tenantID, String configName, String configValue) {
		SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("configName", configName)
				.addValue("configValue", configValue)
				.addValue("date", Calendar.getInstance().getTime());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		namedParamJdbcTemplate.update(INSERT_CONFIGURATION_VALUE, paramSource,
				generatedKeyHolder);

		return generatedKeyHolder.getKey().intValue();
	}

	@Override
	@TriggersRemove(cacheName = { "configurationCache"},
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public int deleteConfiguration(@PartialCacheKey int tenantID, @PartialCacheKey String configurationName) {
		int count = getJdbcTemplate().update(DELETE_CONFIGURATION_BY_NAME, configurationName, tenantID);
		return count;
	}

	@Override
	@TriggersRemove(cacheName = { "configurationCache"},
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public int updateConfiguration(@PartialCacheKey int tenantID,@PartialCacheKey String configName, String configValue) {

		int updatedCount = getJdbcTemplate().update(UPDATE_CONFIGURATION_BY_NAME, configValue, Calendar.getInstance().getTime(), configName, tenantID);

		if (updatedCount == 0) {
			updatedCount= insertConfiguration(tenantID, configName, configValue);
		}

		logger.debug("Exiting updateTenantConfiguration() of TenantConfigurationDaoJdbcImpl");

		return updatedCount;
	}

	@Override
	@TriggersRemove(cacheName = { "configurationCache"},
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public int updateConfigurations(@PartialCacheKey int tenantID, @PartialCacheKey String configName, String configValue) {
		int updatedCount = getJdbcTemplate().update(UPDATE_CONFIGURATIONS, configValue, Calendar.getInstance().getTime(), configName);

		logger.debug("Exiting updateTenantConfiguration() of TenantConfigurationDaoJdbcImpl");

		return updatedCount;
	}

	@Override
	@TriggersRemove(cacheName = { "configurationCache"},
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public int replaceConfigurationValue(@PartialCacheKey int tenantID, @PartialCacheKey String configName, String oldConfigValue, String newConfigValue){

		int updatedCount = getJdbcTemplate().update(REPLACE_CONFIGURATION_VALUE_BY_NAME_AND_VALUE,
				newConfigValue, Calendar.getInstance().getTime(), configName, oldConfigValue, tenantID);

		logger.debug("Exiting updateTenantConfiguration() of TenantConfigurationDaoJdbcImpl");

		return updatedCount;
	}

	@Override
	@Cacheable (cacheName = "configurationCache",
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public Configuration getConfiguration( int tenantID, String configurationName) {
		Configuration configuration = null;
		try {
			configuration = getJdbcTemplate().queryForObject(GET_CONFIGURATION_BY_TENANT_AND_NAME,
					BeanPropertyRowMapper.newInstance(Configuration.class), configurationName, tenantID);

		} catch (EmptyResultDataAccessException ex){
			// There is no matching configuration.
		}

		logger.debug("Exiting getConfiguration() by config name and tenant id of ConfigurationDaoJdbcImpl");
		return configuration;
	}

	@Override
	public List<Configuration> getConfigurationsByTenantAndConfigNames(int tenantID, List<String> configNames) {
		List<Configuration> configurations = null;
		if (CollectionUtils.isEmpty(configNames)) {
			return configurations;
		}

		SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("configNames", configNames);
		try {
			configurations = namedParamJdbcTemplate.query(GET_CONFIGURATIONS_BY_TENANT_AND_CONFIG_NAMES, paramSource,
					BeanPropertyRowMapper.newInstance(Configuration.class));
		} catch (EmptyResultDataAccessException ex) {
			// There is no matching configuration.
		}

		logger.debug("Exiting getConfigurationsByTenantAndConfigNames() of ConfigurationDaoJdbcImpl");
		return configurations;
	}

	@Override
	@TriggersRemove(cacheName = { "configurationCache" },
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public void clearConfigurationCache(int tenantId, String configName) {
		// Do nothing.
	}

	@Override
	public int updateConfigurations(int tenantID, List<Configuration> configurations) {
		if(CollectionUtils.isEmpty(configurations)) {
			return 0;
		}

		Date updateTime = Calendar.getInstance().getTime();
		List<Object[]> parameters = new ArrayList<>();
		// For the update call, the parameters need to be in the order :
		// { ConfigValue, UpdateTime, ConfigName, TenantId }
		for (Configuration configuration : configurations) {
			parameters.add(new Object[] { configuration.getConfigurationValue(), updateTime,
					configuration.getConfigurationName(), tenantID });
		}

		int[] updatedCounts = getJdbcTemplate().batchUpdate(UPDATE_CONFIGURATION_BY_NAME, parameters);
		int updatedCount = 0;

		List<Object[]> insertParameters = new ArrayList<>();
		for (int i = 0; i < updatedCounts.length; i++) {
			if (updatedCounts[i] != 0) {
				updatedCount++;
				continue;
			}

			Object[] configParams = parameters.get(i);
			// For the insert call, the parameters need to be in the order :
			// { TenantId, ConfigName, ConfigValue, UpdateTime }
			insertParameters.add(new Object[] { configParams[3], configParams[2], configParams[0], configParams[1] });
		}

		int[] insertCounts = getJdbcTemplate().batchUpdate(INSERT_CONFIGURATION, insertParameters);
		for (int count : insertCounts) {
			updatedCount += count;
		}

		logger.debug("Exiting updateConfigurations() of TenantConfigurationDaoJdbcImpl");

		return updatedCount;
	}

}