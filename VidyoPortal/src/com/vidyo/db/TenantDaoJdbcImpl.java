package com.vidyo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.Location;
import com.vidyo.bo.Router;
import com.vidyo.bo.Service;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantFilter;
import com.vidyo.bo.TenantIpc;
import com.vidyo.components.ComponentType;
import com.vidyo.service.LicensingServiceImpl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TenantDaoJdbcImpl extends JdbcDaoSupport implements ITenantDao {
	
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
    protected final Logger logger = LoggerFactory.getLogger(TenantDaoJdbcImpl.class.getName());

    private int getTenantConfigurationValue(int tenantID, String configurationName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  c.configurationValue" +
            " FROM" +
            "  Configuration c" +
            " WHERE" +
            "  c.configurationName = ?" +
            " AND " +
            "  c.tenantID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, configurationName, tenantID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    private int getOtherTenantsConfigurationValue(int tenantID, String configurationName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            " CASE WHEN SUM(c.configurationValue) IS NULL THEN 0 " +
            " ELSE SUM(c.configurationValue) END as num " +
            " FROM  " +
            "  Configuration c " +
            " WHERE  " +
            "  c.configurationName = ?" +
            " AND   " +
            "  c.tenantID <> ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, configurationName, tenantID
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    private int setTenantConfigurationValue(int tenantID, String configurationName, String configurationValue) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  configurationID" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  configurationName = ?" +
            " AND" +
            "  tenantID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, configurationName, tenantID
        );

        int configurationID = li.size() > 0 ? li.get(0) : 0;

        if (configurationID == 0) {
            logger.debug("Add record into Configuration->" + configurationName + " for tenant = " + tenantID);

            SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                .withTableName("Configuration")
                .usingGeneratedKeyColumns("configurationID");

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("configurationName", configurationName)
                .addValue("configurationValue", configurationValue);

            Number newID = insert.executeAndReturnKey(namedParameters);

            logger.debug("New configurationID = " + newID.intValue());

            return newID.intValue();
        } else {
            logger.debug("Update record in Configuration->" + configurationName + " for tenant = " + tenantID + " and configurationID = " + configurationID);

            StringBuffer sqlstm_upd = new StringBuffer(
                " UPDATE" +
                "  Configuration" +
                " SET" +
                "  configurationValue = :configurationValue" +
                " WHERE" +
                "  configurationName = :configurationName" +
                " AND" +
                "  tenantID = :tenantID"
            );

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("configurationName", configurationName)
                .addValue("configurationValue", configurationValue);

            int affected = namedParamJdbcTemplate.update(sqlstm_upd.toString(), namedParameters);
            logger.debug("Rows affected: " + affected);

            return configurationID;
        }
    }

    public List<Tenant> getTenants(TenantFilter filter) {
        List<Tenant> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID," +
            "  tenantName," +
            "  tenantURL," +
            "  tenantPrefix," +
            "  tenantDialIn," +
            "  tenantReplayURL," +
            "  description," +
            "  vidyoGatewayControllerDns," +
            "  mobileLogin," +
            "  scheduledRoomEnabled"+
            " FROM" +
            "  Tenant WHERE tenantId != 0 "
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();

        if (filter != null) {
            sqlstm.append(" AND tenantName LIKE :tenantName");
            namedParamsMap.put("tenantName", filter.getTenantName()+"%");
            sqlstm.append(" AND tenantURL LIKE :tenantURL ");
            namedParamsMap.put("tenantURL", filter.getTenantURL()+"%");
            // SQL injection fix for sort and dir
            if(filter.getSort() != null && filter.getDir() != null) {
            		sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Tenant.class));
        return rc;
    }

    public Long getCountTenants(TenantFilter filter) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
            sqlstm.append(" WHERE tenantName LIKE :tenantName");
            namedParamsMap.put("tenantName", filter.getTenantName()+"%");
            sqlstm.append(" AND tenantURL LIKE :tenantURL ");
            namedParamsMap.put("tenantURL", filter.getTenantURL()+"%");
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
	 * Query to get Tenant details
	 */
	// It's fine to do an outer join as the IPc entry for tenant will always be
	// there.
	protected static final String GET_TENANT_BY_ID = "SELECT tena.tenantID, tenantName, tenantURL, tenantPrefix, tenantDialIn, "
			+ "tenantReplayURL, description, mobileLogin, ipc.inbound, ipc.outbound, vidyoGatewayControllerDns, scheduledRoomEnabled, tenantWebRTCURL FROM Tenant tena "
			+ "LEFT OUTER JOIN IpcConfiguration ipc ON tena.tenantID = ipc.tenantID WHERE  tena.tenantID = ?";

	/**
	 * Returns Tenant by tenant id
	 *
	 */
	@Override
	@Cacheable(cacheName = "tenantDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public Tenant getTenant(int tenantID) {
		logger.debug("Entering getTenant() by tenant id of TenantDao {}"
				+ tenantID);
		Tenant tenant = getJdbcTemplate().queryForObject(
				GET_TENANT_BY_ID,
				BeanPropertyRowMapper.newInstance(Tenant.class),
				tenantID);
		if (tenant != null) {
			tenant = getTenantConfigurations(tenant);
		}
		logger.debug("Exiting getTenant() by tenant id of TenantDao");
		return tenant;
	}

	/**
	 *
	 */
	protected static final String GET_TENANT_BY_NAME = "SELECT tenantID, tenantName, tenantURL, tenantPrefix, tenantDialIn, tenantReplayURL, description, vidyoGatewayControllerDns, scheduledRoomEnabled, tenantWebRTCURL FROM Tenant WHERE tenantName = ?";

	/**
	 *
	 */
	@Override
	@Cacheable(cacheName = "tenantDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public Tenant getTenant(String tenantName) {
		logger.debug("Entering getTenant() by tenantName of TenantDao {}"
				+ tenantName);
		Tenant tenant = getJdbcTemplate().queryForObject(
				GET_TENANT_BY_NAME,
				BeanPropertyRowMapper.newInstance(Tenant.class),
				tenantName);
		if (tenant != null) {
			tenant = getTenantConfigurations(tenant);
		}
		logger.debug("Entering getTenant() by tenantName of TenantDao");
		return tenant;
	}

	/**
	 * Query to get Tenant by TenantURL
	 */
	protected static final String GET_TENANT_BY_URL = "SELECT tenantID, tenantName, tenantURL, tenantPrefix, tenantDialIn, tenantReplayURL, description, vidyoGatewayControllerDns, scheduledRoomEnabled, tenantWebRTCURL FROM Tenant WHERE tenantURL = ?";

	/**
     *
     */
	@Override
	@Cacheable(cacheName = "tenantDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public Tenant getTenantByURL(String url) {
		logger.debug("Entering getTenantByURL() of TenantDao {}", url);
		Tenant tenant = null;

		tenant = getJdbcTemplate().queryForObject(GET_TENANT_BY_URL,
				BeanPropertyRowMapper.newInstance(Tenant.class),
				url);

		if (tenant != null) {
			tenant = getTenantConfigurations(tenant);
		}
		logger.debug("Exiting getTenantByURL() of TenantDao {}", url);
		return tenant;
	}

	/**
	 * Utility to populate tenant configurations in to the Tenant object
	 *
	 * @param tenant
	 * @return
	 */
	// TODO - this code has to be refactored to make single DB calls instead of
	// each for each config value
	protected Tenant getTenantConfigurations(Tenant tenant) {
		logger.debug("Entering getTenantConfigurations() of TenantDao");
		tenant.setInstalls(getInstalls(tenant.getTenantID()));
		tenant.setSeats(getSeats(tenant.getTenantID()));
		tenant.setPorts(getPorts(tenant.getTenantID()));
		tenant.setGuestLogin(getGuestLogin(tenant.getTenantID()));
		tenant.setExecutives(getExecutives(tenant.getTenantID()));
		tenant.setPanoramas(getPanoramas(tenant.getTenantID()));
		logger.debug("Exiting getTenantConfigurations() of TenantDao");
		return tenant;
	}

	/**
     *
     */
	protected static final String GET_TENANT_BY_REPLAY_URL = "SELECT tenantID, tenantName, tenantURL, tenantPrefix, tenantDialIn, tenantReplayURL, description, vidyoGatewayControllerDns, scheduledRoomEnabled FROM Tenant WHERE " +
            " tenantReplayUrl = ? OR " +
            " tenantReplayUrl = CONCAT('http://', ?) OR " +
            " tenantReplayUrl = CONCAT('https://', ?)";

	/**
     *
     */
	@Override
	@Cacheable(cacheName = "tenantDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public Tenant getTenantByReplayURL(String url) {
		logger.debug("Entering getTenantByReplayURL() of TenantDao {}" + url);
		Tenant tenant = getJdbcTemplate().queryForObject(
				GET_TENANT_BY_REPLAY_URL,
				BeanPropertyRowMapper.newInstance(Tenant.class),
				url, url, url);
		if(tenant != null) {
			tenant = getTenantConfigurations(tenant);
		}
		logger.debug("Exiting getTenantByReplayURL() of TenantDao");
		return tenant;
	}

    public List<Tenant> getFromTenants(int tenantID) {
        List<Tenant> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID," +
            "  tenantName," +
            "  tenantURL," +
            "  tenantPrefix," +
            "  tenantDialIn," +
            "  tenantReplayURL," +
            "  description," +
            "  vidyoGatewayControllerDns," +
            "  scheduledRoomEnabled"+
            " FROM" +
            "  Tenant" +
            " WHERE"
        );
        sqlstm.append(" tenantID not in (?, 0)");
        sqlstm.append(" AND tenantID NOT IN (SELECT callTo FROM TenantXcall WHERE tenantID = ? AND callTo <> ?)");
        sqlstm.append(" ORDER BY tenantName;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Tenant.class), tenantID, tenantID, tenantID);
        return rc;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Tenant> getToTenants(int tenantID) {
        List<Tenant> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID," +
            "  tenantName," +
            "  tenantURL," +
            "  tenantPrefix," +
            "  tenantDialIn," +
            "  tenantReplayURL," +
            "  description," +
            "  vidyoGatewayControllerDns," +
            "  scheduledRoomEnabled"+
            " FROM" +
            "  Tenant" +
            " WHERE"
        );
        sqlstm.append(" tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ? AND callTo <> ?)");
        sqlstm.append(" ORDER BY tenantName;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Tenant.class), tenantID, tenantID);
        return rc;
    }

    public List<Router> getFromRouters(int tenantID) {
        List<Router> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  routerID," +
            "  routerName," +
            "  description," +
            "  active" +
            " FROM" +
            "  Routers" +
            " WHERE"
        );
        sqlstm.append(" routerID NOT IN (SELECT routerID FROM TenantXrouter WHERE tenantID = ?)");
        sqlstm.append(" ORDER BY routerName;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Router.class), tenantID);
        return rc;
    }

    public List<Router> getToRouters(int tenantID) {
        List<Router> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  routerID," +
            "  routerName," +
            "  description," +
            "  active" +
            " FROM" +
            "  Routers" +
            " WHERE"
        );
        sqlstm.append(" routerID IN (SELECT routerID FROM TenantXrouter WHERE tenantID = ?)");
        sqlstm.append(" ORDER BY routerName;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Router.class), tenantID);
        return rc;
    }

    public List<Service> getFromVMs(int tenantID) {
        return getFromServices(tenantID, "VidyoManager", true);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Service> getToVMs(int tenantID) {
        return getToServices(tenantID, "VidyoManager");
    }

    public List<Service> getFromVPs(int tenantID) {
        return getFromServices(tenantID, "VidyoRouter", true);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Service> getToVPs(int tenantID) {
        return getToServices(tenantID, "VidyoRouter");
    }

    public List<Service> getFromVGs(int tenantID) {
        return getFromServices(tenantID, "VidyoGateway", false);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Service> getToVGs(int tenantID) {
        return getToServices(tenantID, "VidyoGateway");
    }

    @TriggersRemove(cacheName={"tenantConfigCache", "tenantGuestConfigCache", "tenantDataCache", "tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int updateTenant(@PartialCacheKey int tenantID, Tenant tenant) {
        logger.debug("Update record in Tenant for tenantID = " + tenantID);

        tenant.setTenantID(tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Tenant" +
            " SET" +
            "  tenantID = :tenantID," +
            "  tenantName = :tenantName," +
            "  tenantURL = :tenantURL," +
            "  tenantPrefix = :tenantPrefix," +
            "  tenantDialIn = :tenantDialIn," +
            "  tenantReplayURL = :tenantReplayURL," +
            "  tenantWebRTCURL = :tenantWebRTCURL," +
            "  description = :description," +
            "  mobileLogin =:mobileLogin," +
            "  vidyoGatewayControllerDns = :vidyoGatewayControllerDns," +
            " scheduledRoomEnabled =:scheduledRoomEnabled " +
            " WHERE" +
            "  tenantID = :tenantID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(tenant);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int insertTenant(Tenant tenant) {
        logger.debug("Add record into Tenant");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Tenant")
                        .usingGeneratedKeyColumns("tenantID");

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(tenant);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New tenantID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName={"tenantConfigCache", "tenantGuestConfigCache", "tenantDataCache", "tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteTenant(int tenantID) {
        logger.debug("Remove record from Tenant for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Tenant" +
            " WHERE" +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int insertTenantXCall(@ PartialCacheKey int fromTenantID, int toTenantID) {
        logger.debug("Add record into TenantXcall for tenantID = " + fromTenantID + " and CallTo = " + toTenantID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("TenantXcall");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", fromTenantID)
                .addValue("callTo", toTenantID);

        int affected = insert.execute(namedParameters);
        logger.debug("Rows affected: " + affected);

        return fromTenantID;
    }
    
    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertTenantXCall(@ PartialCacheKey int fromTenantID, Set<Integer> canCallTenantIds) {
        logger.debug("Add record into TenantXcall for tenantID = " + fromTenantID + " and CallTo = " + canCallTenantIds);
        String query = "INSERT INTO TenantXcall(tenantID, callTo) values (?, ?)";
        List<Integer> callCallTenantList = new ArrayList<>(canCallTenantIds);
        getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, fromTenantID);
				ps.setInt(2, callCallTenantList.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return callCallTenantList.size();
			}
		});

    }
    
    public static final String DELETE_CALL_TO_TENANT_IDS = "DELETE FROM TenantXcall WHERE tenantID =:tenantId AND callTo in (:removedTenants)";
    
	/**
	 * Deletes can call to tenant ids
	 * @param fromTenantID
	 * @param removedTenants
	 * @return
	 */
    @Override
    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteTenantXCall(@ PartialCacheKey int fromTenantID, Set<Integer> removedTenants) {
    		Map<String, Object> paramMap = new HashMap<>();
    		paramMap.put("tenantId", fromTenantID);
    		paramMap.put("removedTenants", removedTenants);
    		return namedParamJdbcTemplate.update(DELETE_CALL_TO_TENANT_IDS, paramMap);
    }
    
    public static final String DELETE_TENANT_SERVICE_MAPPING = "DELETE FROM TenantXservice WHERE tenantID =:tenantId AND serviceID in (:removedServiceIds)";
    
	/**
	 * Deletes Tenant and Services mapping
	 * @param fromTenantID
	 * @param removedServiceIds
	 * @return
	 */
    @Override
    public int deleteTenantXServiceMapping(@ PartialCacheKey int tenantId, Set<Integer> removedServiceIds) {
    		Map<String, Object> paramMap = new HashMap<>();
    		paramMap.put("tenantId", tenantId);
    		paramMap.put("removedServiceIds", removedServiceIds);
    		return namedParamJdbcTemplate.update(DELETE_TENANT_SERVICE_MAPPING, paramMap);
    }

    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteTenantXCall(int tenantID) {
        logger.debug("Remove record from TenantXcall for tenantID = " + tenantID);

        StringBuffer sqlstmCallFrom = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXcall" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affectedCallFrom = getJdbcTemplate().update(sqlstmCallFrom.toString(), tenantID);
        logger.debug("Rows affected: " + affectedCallFrom);

        return tenantID;
    }
    
    
    public static final String DELETE_TENANT_MAPPINGS_EXCEPT_SELF = "DELETE FROM TenantXcall WHERE tenantID = ? and callTo != ?";
    
    /**
     * Deletes all TenantXcall amppings except self mapping
     * @param tenantID
     * @return
     */
	@TriggersRemove(cacheName = {
			"tenantXCallCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int deleteTenantXCallMappingExceptSelf(int tenantID) {
		logger.debug("Remove record from TenantXcall for tenantID = " + tenantID);
		int affectedCallFrom = getJdbcTemplate().update(DELETE_TENANT_MAPPINGS_EXCEPT_SELF, tenantID, tenantID);
		logger.debug("Rows affected: " + affectedCallFrom);
		return affectedCallFrom;
	}

    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteAllTenantXCall(int tenantID) {
        logger.debug("Remove record from TenantXcall for tenantID = " + tenantID);

        StringBuffer sqlstmCallFrom = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXcall" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affectedCallFrom = getJdbcTemplate().update(sqlstmCallFrom.toString(), tenantID);
        logger.debug("Rows affected: " + affectedCallFrom);

        StringBuffer sqlstmCallTo = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXcall" +
            " WHERE " +
            "  callTo = ?"
        );

        int affectedCallTo = getJdbcTemplate().update(sqlstmCallTo.toString(), tenantID);
        logger.debug("Rows affected: " + affectedCallTo);

        return tenantID;
    }

    public int insertTenantXrouter(int tenantID, int routerID) {
        logger.debug("Add record into TenantXrouter for tenantID = " + tenantID + " and routerID = " + routerID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("TenantXrouter");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("routerID", routerID);

        int affected = insert.execute(namedParameters);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int deleteTenantXrouter(int tenantID) {
        logger.debug("Remove record from TenantXrouter for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXrouter" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int insertTenantXservice(int tenantID, int serviceID) {
        logger.debug("Add record into TenantXservice for tenantID = " + tenantID + " and serviceID = " + serviceID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("TenantXservice");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("serviceID", serviceID);

        int affected = insert.execute(namedParameters);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int deleteTenantXservice(int tenantID) {
        logger.debug("Remove record from TenantXservice for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXservice" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }
    
    

    public int deleteTenantXserviceNonVM(int tenantID) {
    	logger.debug("Remove record from TenantXservice for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXservice" +
            " WHERE " +
            "  tenantID = ? AND serviceID not in (select cmp.id from components cmp, components_type cmpt where COMP_TYPE_ID = cmpt.id and cmpt.name = 'VidyoManager')"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int getInstalls(int tenantID) {
        return getTenantConfigurationValue(tenantID, "NumInstalls");
    }

    public int getOtherTenantsInstalls(int tenantID) {
        return getOtherTenantsConfigurationValue(tenantID, "NumInstalls");
    }

    public int setInstalls(Tenant tenant, int tenantID) {
        return setTenantConfigurationValue(tenantID, "NumInstalls", String.valueOf(tenant.getInstalls()));
    }

    public int getSeats(int tenantID) {
        return getTenantConfigurationValue(tenantID, "NumSeats");
    }

    public int getOtherTenantsSeats(int tenantID) {
        return getOtherTenantsConfigurationValue(tenantID, "NumSeats");
    }

    public int setSeats(Tenant tenant, int tenantID) {
        return setTenantConfigurationValue(tenantID, "NumSeats", String.valueOf(tenant.getSeats()));
    }

    public int getPorts(int tenantID) {
        return getTenantConfigurationValue(tenantID, "NumPorts");
    }

    public int getOtherTenantsPorts(int tenantID) {
        return getOtherTenantsConfigurationValue(tenantID, "NumPorts");
    }

    public int getUsedNumOfPorts(int tenantID, String lic_version) {
        StringBuffer sqlstm = new StringBuffer();

	    List<Integer> li;

	    if (lic_version.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)) {
            sqlstm.append(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  Conferences c" +
                " WHERE" +
                "  c.tenantID = ?" +
                " AND" +
                "  c.conferenceType = 'C'"
            );

		    li = getJdbcTemplate().query(sqlstm.toString(),
				    new RowMapper<Integer>() {
					    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						    return rs.getInt(1);
					    }
				    },
				    tenantID
		    );
            return li.size() > 0 ? li.get(0) : 0;
	    } else if (lic_version.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_21)) {
	    	sqlstm.append(
    			    "  SELECT  COUNT(0) as cnt" +
    			    "  FROM  Conferences c" +
    			    "  JOIN Endpoints e ON (e.endpointID=c.endpointID AND c.endpointType= :endpointType)" +
    			    "  WHERE  c.tenantID = :tenantID" +
    			    "  AND  c.conferenceType IN (:conferenceTypes)" +
    			    "  AND e.consumesLine = :consumesLine"
            );
            Map<String, Object> namedParams = new HashMap<String, Object>();
            namedParams.put("endpointType", "D");
            namedParams.put("tenantID", tenantID);

            List<String> conferenceTypes = new ArrayList<>();
            conferenceTypes.add("C");
            conferenceTypes.add("P");
            conferenceTypes.add("F");
            namedParams.put("conferenceTypes", conferenceTypes);

            namedParams.put("consumesLine", 1);

		    li = namedParamJdbcTemplate.query(sqlstm.toString(), namedParams,
				    new RowMapper<Integer>() {
					    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						    return rs.getInt(1);
					    }
				    }
		    );
            return li.size() > 0 ? li.get(0) : 0;
	    } else if (lic_version.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) {
            sqlstm.append(
                    "SELECT " +
                            "(SELECT COUNT(*) FROM Conferences c WHERE c.tenantID = ?) - " +
                            "(SELECT COUNT(*) FROM Conferences c, Endpoints e WHERE c.tenantID = ? AND e.endpointID = c.endpointID and e.consumesLine = 0)"
            );
            logger.debug("getCountPorts. SQL - " + sqlstm.toString());

            int count = getJdbcTemplate().queryForObject(sqlstm.toString(), Integer.class, tenantID, tenantID);
            return count;
        }

        return 0;
    }

    public int setPorts(Tenant tenant, int tenantID) {
        return setTenantConfigurationValue(tenantID, "NumPorts", String.valueOf(tenant.getPorts()));
    }

    public int getGuestLogin(int tenantID) {
        return getTenantConfigurationValue(tenantID, "GuestLogin");
    }

    public int setGuestLogin(Tenant tenant, int tenantID) {
        return setTenantConfigurationValue(tenantID, "GuestLogin", String.valueOf(tenant.getGuestLogin()));
    }

    public int deleteGroupsForTenant(int tenantID) {
        logger.debug("Remove records from Groups for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Groups" +
            " WHERE" +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }
    
    protected static final String DELETE_MEMBER_BAK_BY_TENANT_ID = "DELETE mb FROM member_bak mb, Member mem where mem.memberid = mb.member_id and mem.tenantId = ?";

    @Transactional
    public int deleteMembersForTenant(int tenantID) {
        logger.debug("Remove records from Member for tenantID = " + tenantID);
        String DETACH_MEMBER_FROM_USER_GROUPS = "DELETE FROM member_user_group where member_id in (SELECT memberId FROM Member WHERE tenantID=?)";
        int memberIds = getJdbcTemplate().update(DETACH_MEMBER_FROM_USER_GROUPS,  tenantID);
        logger.debug("Number of records deleted from member_user_groups is " + memberIds);
        int deletedMemberBakCount = getJdbcTemplate().update(DELETE_MEMBER_BAK_BY_TENANT_ID, tenantID);
        logger.debug("Number of records deleted from member_bak is " + deletedMemberBakCount);
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }
    
    @Transactional
    public int deleteEndpointsForTenant(int tenantID) {
        logger.debug("Remove records from Endpoint for tenantID = " + tenantID);
        StringBuffer sqlstm = new StringBuffer(
            " DELETE FROM Endpoints WHERE memberID IN (SELECT memberId FROM Member WHERE tenantID=?)"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    @Transactional
    public int deleteUserGroupsForTenant(int tenantID){
        logger.debug("Remove user groups attached to tenant = " + tenantID);
        String DELETE_USER_GROUPS = "DELETE FROM user_group WHERE tenant_id=?";
        int userGroupsDeleteCount = getJdbcTemplate().update(DELETE_USER_GROUPS, tenantID);
        logger.error("Number of records deleted from user_group is " + userGroupsDeleteCount);
        return tenantID;

    }


    @Transactional
    public int deleteRoomsForTenant(int tenantID) {
        logger.debug("Remove records from Room for tenantID = " + tenantID);
        String DETACH_ROOMS_FROM_USER_GROUPS = "DELETE FROM room_user_group where room_id in (SELECT roomId FROM Room r INNER JOIN Member m ON m.memberID = r.memberID WHERE m.tenantID = ?)";
        int roomIds = getJdbcTemplate().update(DETACH_ROOMS_FROM_USER_GROUPS, tenantID);
        logger.debug("Number of records deleted from room_user_groups is " + roomIds);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            "  r" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON m.memberID=r.memberID" +
            " WHERE" +
            "  m.tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int deleteConfigurationForTenant(int tenantID) {
        logger.debug("Remove records from Configuration for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int deleteServicesForTenant(int tenantID) {
        logger.debug("Remove records from TenantXservice for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXservice" +
            " WHERE" +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public List<Tenant> canCallToTenants(int tenantID) {
        List<Tenant> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID," +
            "  tenantName," +
            "  tenantURL," +
            "  tenantPrefix," +
            "  description," +
            "  vidyoGatewayControllerDns" +
            " FROM" +
            "  Tenant" +
            " WHERE"
        );
        sqlstm.append(" tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = ?)");
        sqlstm.append(" ORDER BY tenantName;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Tenant.class), tenantID);
        return rc;
    }

    public int getTenantIDforVirtualEndpoint(String GUID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID" +
            " FROM" +
            "  VirtualEndpoints" +
            " WHERE" +
            "  endpointGUID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }, GUID
        );

        return li.size() > 0 ? li.get(0) : 1; // Default tenant by default
    }


	@TriggersRemove(cacheName = { "tenantConfigCache",
			"tenantGuestConfigCache", "tenantDataCache" }, removeAll = true)
    public int setRegularLicense(int installs, int seats, int ports, int executives) {
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  configurationName = 'NumInstalls'"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  configurationName = 'NumSeats'"
        );

        affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  configurationName = 'NumPorts'"
        );

        affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  configurationName = 'NumExecutives'"
        );

        affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Configuration")
            .usingGeneratedKeyColumns("configurationID");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("tenantID", 1)
            .addValue("configurationName", "NumInstalls")
            .addValue("configurationValue", installs);

        Number newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New configurationID = " + newID.intValue());


        insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Configuration")
            .usingGeneratedKeyColumns("configurationID");

        namedParameters = new MapSqlParameterSource()
            .addValue("tenantID", 1)
            .addValue("configurationName", "NumSeats")
            .addValue("configurationValue", seats);

        newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New configurationID = " + newID.intValue());

        insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Configuration")
            .usingGeneratedKeyColumns("configurationID");

        namedParameters = new MapSqlParameterSource()
            .addValue("tenantID", 1)
            .addValue("configurationName", "NumPorts")
            .addValue("configurationValue", ports);

        newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New configurationID = " + newID.intValue());

        insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Configuration")
            .usingGeneratedKeyColumns("configurationID");

        namedParameters = new MapSqlParameterSource()
            .addValue("tenantID", 1)
            .addValue("configurationName", "NumExecutives")
            .addValue("configurationValue", executives);

        newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New configurationID = " + newID.intValue());

        return 1;
    }

    public void updateTenantOfVirtualEndpoints(int serviceID, int tenantID) {
        logger.debug("Update record in VirtualEndpoints->tenantID for service = " + serviceID);

        StringBuffer sqlstm_upd = new StringBuffer(
            " UPDATE" +
            "  VirtualEndpoints" +
            " SET" +
            "  tenantID = :tenantID" +
            " WHERE" +
            "  serviceID = :serviceID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("tenantID", tenantID)
            .addValue("serviceID", serviceID);

        int affected = namedParamJdbcTemplate.update(sqlstm_upd.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);
    }

    public void updateTenantOfGatewayPrefixes(int serviceID, int tenantID) {
        logger.debug("Update record in GatewayPrefixes->tenantID for service = " + serviceID);

        StringBuffer sqlstm_upd = new StringBuffer(
                " UPDATE" +
                        "  GatewayPrefixes" +
                        " SET" +
                        "  tenantID = :tenantID" +
                        " WHERE" +
                        "  serviceID = :serviceID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("serviceID", serviceID);

        int affected = namedParamJdbcTemplate.update(sqlstm_upd.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);
    }

    public boolean isTenantExistForTenantName(String tenantName, int tenantID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant t" +
            " WHERE" +
            "  t.tenantName = :tenantName"
        );

        if (tenantID != 0) {
            sqlstm.append(" AND t.tenantID <> ").append(String.valueOf(tenantID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantName", tenantName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isTenantUrlExistForTenantUrl(String tenantUrl, int tenantID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant t" +
            " WHERE" +
            "  t.tenantUrl = :tenantUrl"
        );

        if (tenantID != 0) {
            sqlstm.append(" AND t.tenantID <> ").append(String.valueOf(tenantID));
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("tenantUrl", tenantUrl);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isTenantReplayUrlExistForTenantReplayUrl(String tenantReplayUrl, int tenantID) {
        if (tenantReplayUrl == null) {
            return false;
        }
        tenantReplayUrl = tenantReplayUrl.trim();
        if ("".equals(tenantReplayUrl)) {
            return false;
        }

        if (tenantReplayUrl.startsWith("https://")) {
            tenantReplayUrl = tenantReplayUrl.replace("https://", "");
        }
        if (tenantReplayUrl.startsWith("http://")) {
            tenantReplayUrl = tenantReplayUrl.replace("http://", "");
        }

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant t" +
            " WHERE" +
            "  ( t.tenantReplayUrl = :tenantReplayUrl OR" +
            "    t.tenantReplayUrl = CONCAT('http://', :tenantReplayUrl) OR " +
            "    t.tenantReplayUrl = CONCAT('https://', :tenantReplayUrl) ) "
        );

        if (tenantID != 0) {
            sqlstm.append(" AND t.tenantID <> ").append(String.valueOf(tenantID));
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("tenantReplayUrl", tenantReplayUrl);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isTenantPrefixExistForTenantPrefix(String tenantPrefix, int tenantID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant t" +
            " WHERE" +
            "  t.tenantPrefix = :tenantPrefix"
        );

        if (tenantID != 0) {
            sqlstm.append(" AND t.tenantID <> ").append(String.valueOf(tenantID));
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("tenantPrefix", tenantPrefix);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isTenantPrefixExistForTenantPrefixLike(String tenantPrefix) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Tenant t" +
            " WHERE" +
            "  t.tenantPrefix like :tenantPrefix"
        );

        Map<String, String> params = new HashMap<String, String>();
        params.put("tenantPrefix", tenantPrefix+"%");

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean hasTenantReplayComponent(int tenantID) {
        List<Service> l = getToServices(tenantID, "VidyoReplay");
        return l.size() > 0;
    }

    public List<Service> getFromRecs(int tenantID) {
        return getFromServices(tenantID, "VidyoRecorder", true);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Service> getToRecs(int tenantID) {
        return getToServices(tenantID, "VidyoRecorder");
    }

    public List<Service> getFromReplays(int tenantID) {
        return getFromServices(tenantID, "VidyoReplay", true);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Service> getToReplays(int tenantID) {
        return getToServices(tenantID, "VidyoReplay");
    }

        private List<Service> getFromServices(int tenantID, String serviceRole, boolean shared) {
            List<Service> rc;
            StringBuffer sqlstm = new StringBuffer(
                " SELECT " +
                "  s.ID as serviceID," +
                "  sr.ID as roleID," +
                "  sr.NAME as roleName," +
                "  s.NAME as serviceName" +
                " FROM" +
                "  components s" +
                " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME = ?)" +
                " WHERE"
            );

            if (shared) {
                sqlstm.append(" s.ID NOT IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)");
            } else {
                sqlstm.append(" s.ID NOT IN (SELECT serviceID FROM TenantXservice)");
            }

            sqlstm.append(" ORDER BY serviceName;");

            if (shared) {
                rc = getJdbcTemplate().query(sqlstm.toString(),
                        BeanPropertyRowMapper.newInstance(Service.class), serviceRole, tenantID);
            } else {
                rc = getJdbcTemplate().query(sqlstm.toString(),
                        BeanPropertyRowMapper.newInstance(Service.class), serviceRole);
            }

            return rc;
        }

        private List<Service> getToServices(int tenantID, String serviceRole) {
            List<Service> rc;
            StringBuffer sqlstm = new StringBuffer(
                " SELECT " +
                " s.ID as serviceID," +
                " sr.ID as roleID," +
                " sr.NAME as roleName," +
                " s.NAME as serviceName" +
                " FROM" +
                " components s" +
                " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME = ?)" +
                " WHERE"
            );
            sqlstm.append(" s.ID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)");
            sqlstm.append(" ORDER BY serviceName;");

            rc = getJdbcTemplate().query(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Service.class), serviceRole, tenantID);
            return rc;
        }

        public boolean isTenantAuthenticatedWithLDAP(int tenant){

            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  Configuration c" +
                " WHERE" +
                "  c.configurationValue = 1" +
                " AND" +
                "  c.configurationName = 'LDAPAuthenticationFlag'" +
                " AND" +
                "  c.tenantID = :tenant"
            );

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("tenant", String.valueOf(tenant));

            SqlParameterSource namedParameters = new MapSqlParameterSource(params);

            int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
            return num > 0;
        }

    public List<Location> getFromLocations(int tenantID) {
        List<Location> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  locationID," +
            "  locationTag" +
            " FROM" +
            "  Locations" +
            " WHERE"
        );
        sqlstm.append(" locationID NOT IN (SELECT locationID FROM TenantXlocation WHERE tenantID = ?)");
        sqlstm.append(" ORDER BY locationTag;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Location.class), tenantID);
        return rc;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public List<Location> getToLocations(int tenantID) {
        List<Location> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  locationID," +
            "  locationTag" +
            " FROM" +
            "  Locations" +
            " WHERE"
        );
        sqlstm.append(" locationID IN (SELECT locationID FROM TenantXlocation WHERE tenantID = ?)");
        sqlstm.append(" ORDER BY locationTag;");

        rc = getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Location.class), tenantID);
        return rc;
    }
    public boolean isLocationExistTenantLevel(int tenantId,int locationId) {

        StringBuffer sqlstm = new StringBuffer("SELECT"
        		+ " COUNT(0) FROM TenantXlocation tl WHERE ");
        
        		
        sqlstm.append("tl.locationID=:locationID and tl.tenantID=:tenantID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("locationID", locationId);
        params.put("tenantID", tenantId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

 
    
        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);

        return num>0;
    }
    public boolean isVidyoProxyExistTenantLevel(int tenantId,int serviceId) {

        StringBuffer sqlstm = new StringBuffer("SELECT"
        		+ " COUNT(0) FROM TenantXservice ts WHERE ");
        
        		
        sqlstm.append("ts.serviceID=:serviceID and ts.tenantID=:tenantID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("serviceID", serviceId);
        params.put("tenantID", tenantId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

 
    
        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);

        return num>0;
    }

    public int insertTenantXlocation(int tenantID, int locationID) {
        logger.debug("Add record into TenantXlocation for tenantID = " + tenantID + " and locationID = " + locationID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("TenantXlocation");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("tenantID", tenantID)
                .addValue("locationID", locationID);

        int affected = insert.execute(namedParameters);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int deleteTenantXlocation(int tenantID) {
        logger.debug("Remove record from TenantXlocation for tenantID = " + tenantID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  TenantXlocation" +
            " WHERE " +
            "  tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        logger.debug("Rows affected: " + affected);

        return tenantID;
    }

    public int getExecutives(int tenantID) {
        return getTenantConfigurationValue(tenantID, "NumExecutives");
    }

    public int getOtherTenantsExecutives(int tenantID) {
        return getOtherTenantsConfigurationValue(tenantID, "NumExecutives");
    }

    public int setExecutives(Tenant tenant, int tenantID) {
        return setTenantConfigurationValue(tenantID, "NumExecutives", String.valueOf(tenant.getExecutives()));
    }

	public int getPanoramas(int tenantID) {
		return getTenantConfigurationValue(tenantID, "NumPanoramas");
	}

	public int getOtherTenantsPanoramas(int tenantID) {
		return getOtherTenantsConfigurationValue(tenantID, "NumPanoramas");
	}

	public int setPanoramas(Tenant tenant, int tenantID) {
		return setTenantConfigurationValue(tenantID, "NumPanoramas", String.valueOf(tenant.getPanoramas()));
	}

	@Cacheable(cacheName="tenantGuestConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public boolean isTenantNotAllowingGuests(int tenantID){

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Configuration" +
            " WHERE" +
            "  tenantID = :tenantID" +
            " AND" +
            "  configurationName = 'GuestLogin'" +
            " AND" +
            "  configurationValue = 0"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenantID));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

	public boolean isIpcSuperManaged() {
		logger.debug("Entering isIpcSuperManaged() of TenantDaoJdbcImpl");
		String sql = "select superManaged from Tenant limit :limit";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit", 1);
		int superManaged = namedParamJdbcTemplate.queryForObject(sql, params, Integer.class);
		logger.debug("Exiting isIpcSuperManaged() of TenantDaoJdbcImpl");
		return (superManaged == 1 ? true : false);
	}

	public int updateIpcAdmin(int ipcAdmin) {
		logger.debug("Entering updateIpcAdmin() of TenantDaoJdbcImpl");
		String sql = "UPDATE Tenant set superManaged =:ipcAdmin";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ipcAdmin", ipcAdmin);
		int updatedCount = namedParamJdbcTemplate.update(sql, params);
		logger.debug("Exiting updateIpcAdmin() of TenantDaoJdbcImpl");
		return updatedCount;
	}

	public List<TenantIpc> getIpcDetails(int tenantID) {
		logger.debug("Entering getIpcDetails() of TenantDaoJdbcImpl");
		String GET_IPC_DETAILS_BY_TENANT_ID = "SELECT ten.tenantID as tenantID, "
				+ "ipc.ipcID as ipcID, ipc.inbound as inbound, ipc.outbound as outbound, ipc.allowed as allowed, "
				+ "ipcw.hostname as hostname, ipcw.ID as ipcWhiteListId from Tenant ten, "
				+ "IpcConfiguration ipc left outer join IpcTenantDomainList ipcw on ipc.ipcID = ipcw.ipcID "
				+ "where ten.tenantID = :tenantID and ten.tenantID = ipc.tenantID";
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("tenantID", tenantID);
		List<TenantIpc> tenantIpcDetails = namedParamJdbcTemplate.query(
				GET_IPC_DETAILS_BY_TENANT_ID, params, new TenantIpcRowMapper());
		logger.debug("Exiting getIpcDetails() of TenantDaoJdbcImpl");
		return tenantIpcDetails;
	}

	public int updateWhiteListFlag(int tenantId, int flag) {
		logger.debug("Entering updateWhiteListFlag() of TenantDaoJdbcImpl");
		String UPDATE_IPC_WHITELIST_FLAG_BY_TENANT_ID = "UPDATE IpcTenantDomainList"
				+ " ipcw, IpcConfiguration ipc set ipcw.whitelist =:flag where ipcw.ipcID = ipc.ipcID "
				+ "and ipc.tenantID =:tenantID";
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("tenantID", tenantId);
		params.put("flag", flag);
		logger.debug("Exiting updateWhiteListFlag() of TenantDaoJdbcImpl");
		return namedParamJdbcTemplate.update(
				UPDATE_IPC_WHITELIST_FLAG_BY_TENANT_ID, params);
	}

	public int deleteTenantIpcDomains(int tenantId, List<Integer> deletedIds) {
		logger.debug("Entering deleteTenantIpcDomains() of TenantDaoJdbcImpl");
		String DELETE_IPC_DOMAINS_BY_TENANT_ID = "DELETE ipcw FROM IpcTenantDomainList ipcw,"
				+ " IpcConfiguration ipc where ipcw.ID in (:deletedIds) "
				+ "and ipcw.ipcID = ipc.ipcID and ipc.tenantID =:tenantID; ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantID", tenantId);
		params.put("deletedIds", deletedIds);
		logger.debug("Exiting deleteTenantIpcDomains() of TenantDaoJdbcImpl");
		return namedParamJdbcTemplate.update(DELETE_IPC_DOMAINS_BY_TENANT_ID,
				params);
	}

	public void addTenantIpcDomains(int ipcId, List<String> ipcDomains, int flag) {
		logger.debug("Entering addTenantIpcDomains() of TenantDaoJdbcImpl");
		StringBuilder insertSql = new StringBuilder(
				"INSERT into IpcTenantDomainList (ipcID,hostname) VALUES ");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < ipcDomains.size(); i++) {
			insertSql.append("(:ipcId" + i + "," + ":hostName" + i + ")");
			if (i >= 0 && i < ipcDomains.size() - 1) {
				insertSql.append(",");
			}
			paramMap.put("ipcId" + i, ipcId);
			paramMap.put("hostName" + i, ipcDomains.get(i));
		}
		namedParamJdbcTemplate.update(insertSql.toString(), paramMap);
		logger.debug("Exiting addTenantIpcDomains() of TenantDaoJdbcImpl");
	}

	/**
	 * Update mobile access configuration for all Tenants.
	 * @param mobileAccess
	 * @return
	 */
	@TriggersRemove(cacheName = { "tenantDataCache" }, removeAll = true)
	public int updateTenantMobileAccess(int mobileAccess) {
		logger.debug("Entering updateTenantMobileAccess() of TenantDaoJdbcImpl {}", mobileAccess);
		String sql = "UPDATE Tenant set mobileLogin =:mobileAccess";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileAccess", mobileAccess);
		int updatedCount = namedParamJdbcTemplate.update(sql, params);
		logger.debug("Exiting updateTenantMobileAccess() of TenantDaoJdbcImpl");
		return updatedCount;
	}

	public void updateTenantInClientInstallations(String newTenantName, String oldTenantName) {
		logger.debug("Update record in ClientInstallations old tanantName = " + oldTenantName + " new tenantName = " + newTenantName);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
			.addValue("newTenantName", newTenantName)
			.addValue("oldTenantName", oldTenantName);

		StringBuffer sqlstm_upd = new StringBuffer(
			" UPDATE" +
			"  ClientInstallations" +
			" SET" +
			"  tenantName = :newTenantName" +
			" WHERE" +
			"  tenantName = :oldTenantName"
		);

		int affected = namedParamJdbcTemplate.update(sqlstm_upd.toString(), namedParameters);
		logger.debug("Rows affected: " + affected);

		StringBuffer sqlstm_upd2 = new StringBuffer(
			" UPDATE" +
			"  ClientInstallations2" +
			" SET" +
			"  tenantName = :newTenantName" +
			" WHERE" +
			"  tenantName = :oldTenantName"
		);

		affected = namedParamJdbcTemplate.update(sqlstm_upd2.toString(), namedParameters);
		logger.debug("Rows affected: " + affected);

		StringBuffer sqlstm_upd3 = new StringBuffer(
				" UPDATE" +
				"  ConferenceCall2" +
				" SET" +
				"  TenantName = :newTenantName" +
				" WHERE" +
				"  TenantName = :oldTenantName"
			);
		affected = namedParamJdbcTemplate.update(sqlstm_upd3.toString(), namedParameters);
		logger.debug("Rows affected: " + affected);

	}

	/**
	 *
	 */
	public List<Integer> getMobileAccessDetail() {
		String GET_MOBILE_ACCESS_DETAIL = "SELECT mobileLogin FROM Tenant where tenantID <> 0 GROUP BY mobileLogin";
		Map<String, Object> paramMap = null;
		List<Integer> mobileAcessDetail = namedParamJdbcTemplate.query(
				GET_MOBILE_ACCESS_DETAIL, paramMap,
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt(1);
					}
				});

		return mobileAcessDetail;
	}

	private static final String GET_ALL_TENANT_IDS = "SELECT distinct tenantID from Tenant where tenantId != 0";

	/**
	 * Returns the list of all TenantIds except Super Tenant with Id zero
	 *
	 * @return
	 */
	public List<Integer> getAllTenantIds() {
		return getJdbcTemplate().query(GET_ALL_TENANT_IDS,
				new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt(1);
			}
		});
	}

	public boolean isVidyoGatewayControllerDnsExist(String vidyoGatewayControllerDns, int tenantID) {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering updateTenantMobileAccess() of TenantDaoJdbcImpl vidyoGatewayControllerDns = " + vidyoGatewayControllerDns);
		}
		final StringBuilder GET_VIDYOGATEWAY_CONTROLLER_DNS = new StringBuilder(
			"SELECT vidyoGatewayControllerDns from Tenant WHERE vidyoGatewayControllerDns = :vidyoGatewayControllerDns");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vidyoGatewayControllerDns", vidyoGatewayControllerDns);

		if(tenantID > 0) {
			GET_VIDYOGATEWAY_CONTROLLER_DNS.append(" AND tenantID <> :tenantID");
			params.put("tenantID", tenantID);
		}


		List<String> vidyoGatewayControllerDnsList = namedParamJdbcTemplate.query(GET_VIDYOGATEWAY_CONTROLLER_DNS.toString(), params,
				BeanPropertyRowMapper.newInstance(String.class));

		if(logger.isDebugEnabled()) {
			logger.debug("Exiting updateTenantMobileAccess() of TenantDaoJdbcImpl");
		}

		return vidyoGatewayControllerDnsList.size() > 0;
	}

	private static final String DISABLE_SCHEDULED_ROOM = "UPDATE Tenant set scheduledRoomEnabled =:schRoomDisabled where tenantID =:tenantId";

	/**
	 * Enables/Disables Tenant's Scheduled Room Feature
	 *
	 * @param schRoomAccess
	 * @param tenantId
	 * @return
	 */
	@TriggersRemove(cacheName = { "tenantConfigCache", "tenantXCallCache", "tenantDataCache", "tenantGuestConfigCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantScheduledRoomFeature(int schRoomDisabled,
			@PartialCacheKey int tenantId) {
		logger.debug("Entering updateTenantScheduledRoomFeature() of TenantDaoJdbcImpl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schRoomDisabled", schRoomDisabled);
		params.put("tenantId", tenantId);
		int updatedCount = namedParamJdbcTemplate.update(DISABLE_SCHEDULED_ROOM, params);
		logger.debug("Exiting updateTenantScheduledRoomFeature() of TenantDaoJdbcImpl");
		return updatedCount;
	}

	private static final String GET_CALL_TO_TENANT_IDS = "select t.callTo from TenantXcall t where t.tenantID = ?";

	/**
	 * Returns only the TenantIds which can be called
	 *
	 * @param tenantID
	 * @return
	 */
	@Cacheable(cacheName = "tenantXCallCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public List<Integer> canCallToTenantIds(int tenantId) {
		logger.debug("Entering canCallToTenantIds() of TenantDaoJdbcImpl");
		logger.debug("Exiting canCallToTenantIds() of TenantDaoJdbcImpl");
		return getJdbcTemplate().query(GET_CALL_TO_TENANT_IDS, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int row) throws SQLException {
				return Integer.valueOf(rs.getInt(1));
			}
		}, tenantId);
	}


	@Override
	public int updateMembersForTenant(int tenantID, String ids, boolean isProxy) {
        logger.debug("Get records from Member for tenantID = " + tenantID);
        int affected = 0;

        if (ids != null && ids.length() > 0)  {
        	String[] str = ids.split(",");

        	StringBuffer sqlstm = new StringBuffer(
	            " UPDATE Member SET ");
	        if (isProxy ){
	        	sqlstm.append("proxyID = 0 ");
	        } else {
	        	sqlstm.append("locationID = 1 ");
	        }
	        sqlstm.append(" WHERE tenantID = ? ");

	        if (isProxy){
	        	sqlstm.append(" AND (proxyID NOT IN (");
	        	sqlstm = StringToIntAppender(sqlstm, str);

	    		sqlstm.append(" )  AND proxyID IS NOT NULL)");
	        } else {
	        	sqlstm.append(" AND (locationID NOT IN (");
	        	sqlstm = StringToIntAppender(sqlstm, str);
	    		sqlstm.append(")  AND locationID IS NOT NULL)");
	        }

	        affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);

        }
        logger.debug("Rows affected: " + affected);


        return affected;
    }

	private StringBuffer StringToIntAppender (StringBuffer strBuffer, String[] str) {
		if (strBuffer != null) {

			for(int i=0; i < str.length; i++){
    			if (i < str.length-1) {
    				strBuffer.append(Integer.parseInt(str[i])).append(", ");
    			} else {
    				strBuffer.append(Integer.parseInt(str[i]));
    			}
    		}
		}

		return strBuffer;
	}

	@Override
	public int updateGuetsForTenant(int tenantID, String ids, boolean isProxy) {
		logger.debug("Get records from Member for tenantID = " + tenantID);
        int affected = 0;

        if (ids != null && ids.length() > 0)  {
        	String[] str = ids.split(",");

        	StringBuffer sqlstm = new StringBuffer(
	            " UPDATE Configuration SET ");
	        if (isProxy ){
	        	sqlstm.append("configurationValue = 0 ");
	        } else {
	        	sqlstm.append("configurationValue = 1 ");
	        }
	        sqlstm.append(" WHERE tenantID = ? ");

	        if (isProxy){
	        	sqlstm.append(" AND configurationName = 'GuestProxyID' AND configurationValue NOT IN (");
	        	sqlstm = StringToIntAppender(sqlstm, str);

	    		sqlstm.append(" ) ");
	        } else {
	        	sqlstm.append(" AND configurationName = 'GuestLocationID' AND configurationValue NOT IN (");
	        	sqlstm = StringToIntAppender(sqlstm, str);
	    		sqlstm.append(") ");
	        }

	        affected = getJdbcTemplate().update(sqlstm.toString(), tenantID);
        }
        logger.debug("Rows affected: " + affected);


        return affected;
	}

	/**
	 * Empty method to trigger cache removal.
	 *
	 * @param tenantName
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "tenantDataCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantByName(String tenantName) {
		return 0;
	}

	/**
	 * Empty method to trigger cache removal.
	 *
	 * @param tenantUrl
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "tenantDataCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantByUrl(String tenantUrl) {
		return 0;
	}

	/**
	 * Empty method to trigger cache removal.
	 *
	 * @param replayUrl
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "tenantDataCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantByReplayUrl(String replayUrl) {
		return 0;
	}

	/**
	 * Empty method to trigger cache removal.
	 *
	 * @param replayUrl
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "tenantDataCache", "tenantXCallCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantById(int tenantId) {
		return 0;
	}

	@Override
	@TriggersRemove(cacheName = { "tenantConfigCache", "tenantXCallCache", "tenantDataCache", "tenantGuestConfigCache" },
		keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator",
			properties = @Property(name = "includeMethod", value = "false")))
	public void clearTenantCache(int tenantId) {
		// Do nothing.
	}
	
    public static final String INSERT_TENANT_SERVICE_MAPPING = "INSERT INTO TenantXservice(tenantID, serviceID) values (?, ?)";

    /**
     * 
     */
    @TriggersRemove(cacheName={"tenantXCallCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertTenantXServiceMapping(@PartialCacheKey int tenantId, Set<Integer> serviceIds) {
        logger.debug("Add record into TenantXservice for tenantID = " + tenantId + " and ServiceIds = " + serviceIds);
        List<Integer> serviceList = new ArrayList<>(serviceIds);
        getJdbcTemplate().batchUpdate(INSERT_TENANT_SERVICE_MAPPING, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, tenantId);
				ps.setInt(2, serviceList.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return serviceList.size();
			}
		});

    }
    
    public static final String DELETE_TENANT_SERVICE_MAPPINGS_BY_TYPE = "DELETE txs FROM TenantXservice txs, components c, components_type ct WHERE txs.tenantID =:tenantId AND txs.serviceID = c.id and c.comp_type_id = ct.id and ct.name =:componentType";
    
    /**
     * Deletes a specific service mapping for a Tenant
     * @param tenantId
     * @param componentType
     * @return
     */
    @Override
	public int deleteTenantServiceMappingByType(@PartialCacheKey int tenantId, ComponentType componentType) {
		logger.debug("Delete records from TenantXservice for tenantID = " + tenantId + " and Comp type = "
				+ componentType.name());
		Map<String, Object> namedParamsMap = new HashMap<>();
		namedParamsMap.put("tenantId", tenantId);
		namedParamsMap.put("componentType", componentType.name());
		return namedParamJdbcTemplate.update(DELETE_TENANT_SERVICE_MAPPINGS_BY_TYPE, namedParamsMap);
	}
    
    public static final String DELETE_TENANT_LOCATION_MAPPING = "DELETE FROM TenantXlocation WHERE tenantID =:tenantId and locationId in (:locationIds)";
    
	/**
	 * Deletes Tenant Location mapping by tenantId and LocationIds
	 * @param tenantID
	 * @param locationIds
	 * @return
	 */    
    @Override
    public int deleteTenantXlocation(int tenantID, Set<Integer> locationIds) {
        logger.debug("Remove record from TenantXlocation for tenantID = " + tenantID + " LocationIds " + locationIds);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tenantId", tenantID);
        paramMap.put("locationIds", locationIds);
        int affected = namedParamJdbcTemplate.update(DELETE_TENANT_LOCATION_MAPPING, paramMap);
        logger.debug("Rows affected: " + affected);
        return affected;
    }
 
    public static final String INSERT_TENANT_LOCATION_MAPPING = "INSERT INTO TenantXlocation(tenantID, locationID) values (?, ?)";

    /**
     * Batch inserts Tenant Location mapping
     * @param tenantId
     * @param locationIds
     */
    @Override
    public void insertTenantXlocations(int tenantId, Set<Integer> locationIds) {
        logger.debug("Add record into TenantXlocation for tenantID = " + tenantId + " and locationIds = " + locationIds);
        List<Integer> locationIdList = new ArrayList<>(locationIds);
        getJdbcTemplate().batchUpdate(INSERT_TENANT_LOCATION_MAPPING, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, tenantId);
				ps.setInt(2, locationIdList.get(i));
			}
			@Override
			public int getBatchSize() {
				return locationIds.size();
			}
		});       
    }
    
    public static final String DEFAULT_PROXY_ID_FOR_MEMBER = "UPDATE Member SET proxyID =:defaultProxyId WHERE tenantID =:tenantId and proxyID in (:proxyIds)";
    
    /**
     * Updates the Proxy to default for the Proxies removed
     * @param tenantId
     * @param proxyIds
     * @return
     */
	@Override
	public int updateProxyIdToDefaultForMembers(int tenantId, Set<Integer> proxyIds) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defaultProxyId", 0);
		paramMap.put("tenantId", tenantId);
		paramMap.put("proxyIds", proxyIds); // Removed Ids
		return namedParamJdbcTemplate.update(DEFAULT_PROXY_ID_FOR_MEMBER, paramMap);
    }
	
	public static final String UPDATE_GUEST_PROXY_CONFIG = "UPDATE Configuration SET configurationValue =:defaultProxyId WHERE tenantID =:tenantId AND configurationName = 'GuestProxyID' AND configurationValue in (:proxyIds)";
	
	/**
	 * Updates the Guest Proxy Id configuration for the Tenant
	 * @param tenantId
	 * @param proxyIds
	 * @return
	 */
	@Override
	public int updateProxyConfigForGuests(int tenantId, Set<Integer> proxyIds) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defaultProxyId", 0);
		paramMap.put("tenantId", tenantId);
		paramMap.put("proxyIds", proxyIds); // Removed Ids
		return namedParamJdbcTemplate.update(UPDATE_GUEST_PROXY_CONFIG, paramMap);
	}
	
    public static final String DEFAULT_LOCATION_ID_FOR_MEMBER = "UPDATE Member SET proxyID =:defaultLocationId WHERE tenantID =:tenantId and locationID in (:locationIds)";
    
    /**
     * Updates the Proxy to default for the Proxies removed
     * @param tenantId
     * @param proxyIds
     * @return
     */
	@Override
	public int updateLocationIdToDefaultForMembers(int tenantId, Set<Integer> locationIds) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defaultLocationId", 1);
		paramMap.put("tenantId", tenantId);
		paramMap.put("locationIds", locationIds); // Removed Ids
		return namedParamJdbcTemplate.update(DEFAULT_LOCATION_ID_FOR_MEMBER, paramMap);
    }
	
	public static final String UPDATE_GUEST_LOCATION_CONFIG = "UPDATE Configuration SET configurationValue =:defaultProxyId WHERE tenantID =:tenantId AND configurationName = 'GuestLocationID' AND configurationValue in (:locationIds)";
	
	/**
	 * Updates the Guest Location Id configuration for the Tenant
	 * @param tenantId
	 * @param proxyIds
	 * @return
	 */
	@Override
	public int updateLocationConfigForGuests(int tenantId, Set<Integer> locationIds) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defaultProxyId", 0);
		paramMap.put("tenantId", tenantId);
		paramMap.put("locationIds", locationIds); // Removed Ids
		return namedParamJdbcTemplate.update(UPDATE_GUEST_LOCATION_CONFIG, paramMap);
	}

}