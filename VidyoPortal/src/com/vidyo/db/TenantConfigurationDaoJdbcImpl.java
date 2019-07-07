package com.vidyo.db;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.TenantConfiguration;

public class TenantConfigurationDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements TenantConfigurationDao {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(TenantConfigurationDaoJdbcImpl.class.getName());

	@Override
	public int insertTenantConfiguration(TenantConfiguration tenantConfiguration) {
		logger.debug("Add record into TenantConfiguration");
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource()).withTableName("TenantConfiguration");

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(tenantConfiguration);
		int count = insert.execute(parameters);

		return count;
	}

	private static final String DELETE_TENANT_CONFIGURATION = " DELETE" + " FROM" + "  TenantConfiguration" + " WHERE"
			+ "  tenantID = :tenantID";

	@TriggersRemove(cacheName = {
			"tenantConfigurationCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	@Override
	public int deleteTenantConfiguration(int tenantID) {
		logger.debug("Remove record from TenantConfiguration for tenantID = " + tenantID);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(DELETE_TENANT_CONFIGURATION, params);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

	private final static String GET_TENANT_CONFIGURATION_BY_ID = "SELECT *" + " FROM TenantConfiguration "
			+ " WHERE tenantID = :tenantID";

	@Override
	@Cacheable(cacheName = "tenantConfigurationCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public TenantConfiguration getTenantConfiguration(int tenantID) {
		logger.debug("Entering getTenantConfiguration() by tenant id of TenantConfigurationDaoJdbcImpl {}" + tenantID);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantID", tenantID);
		TenantConfiguration tenantConfiguration = getNamedParameterJdbcTemplate().queryForObject(
				GET_TENANT_CONFIGURATION_BY_ID, params, BeanPropertyRowMapper.newInstance(TenantConfiguration.class));

		logger.debug("Exiting getTenantConfiguration() by tenant id of TenantConfigurationDaoJdbcImpl");
		return tenantConfiguration;
	}

	private final static String UPDATE_TENANT_CONFIGURATION = "UPDATE TenantConfiguration SET"
			+ " endpointPrivateChat = :endpointPrivateChat," + " endpointPublicChat = :endpointPublicChat,"
			+ " zincEnabled = :zincEnabled," + " zincUrl = :zincUrl," + " lectureModeAllowed = :lectureModeAllowed, "
			+ " waitingRoomsEnabled = :waitingRoomsEnabled, " + " waitUntilOwnerJoins = :waitUntilOwnerJoins, "
			+ " lectureModeStrict = :lectureModeStrict, " + " minPINLength = :minPINLength, "
			+ " createPublicRoomEnable = :createPublicRoomEnable, "
			+ " maxCreatePublicRoomUser = :maxCreatePublicRoomUser,"
			+ " maxCreatePublicRoomTenant = :maxCreatePublicRoomTenant, " + " minMediaPort = :minMediaPort, "
			+ " maxMediaPort = :maxMediaPort, " + " alwaysUseProxy = :alwaysUseProxy, " + " userImage = :userImage, "
			+ " uploadUserImage = :uploadUserImage, " + " vidyoNeoWebRTCGuestEnabled = :vidyoNeoWebRTCGuestEnabled, "
			+ " vidyoNeoWebRTCUserEnabled = :vidyoNeoWebRTCUserEnabled, " + " logAggregation = :logAggregation, "
			+ " endpointBehavior = :endpointBehavior, " + " personalRoom= :personalRoom, " + " extnLength=:extnLength, "
			+ " roomCountThreshold=:roomCountThreshold, " + " endpointUploadMode = :endpointUploadMode, "
			+ " testCall = :testCall, " + " mobileProtocol = :mobileProtocol, "
			+ " androidPackageName =:androidPackageName, " + " iOSAppId = :iOSAppId, " + " iOSAppLink = :iOSAppLink, "
			+ " iOSBundleId = :iOSBundleId, " + " androidAppLink = :androidAppLink, "
			+ " extIntegrationSharedSecret = :extIntegrationSharedSecret, "
			+ " externalIntegrationMode = :externalIntegrationMode, "
			+ " externalNotificationUrl = :externalNotificationUrl, " + " externalUsername = :externalUsername, "
			+ " externalPassword = :externalPassword, " + " desktopProtocol = :desktopProtocol,"
			+ " vidyoNotificationUrl = :vidyoNotificationUrl, " + " vidyoUsername = :vidyoUsername, "
			+ " vidyoPassword = :vidyoPassword, " + " pp = :pp, " + " ppVersion = :ppVersion, "
			+ " tc = :tc, " + " tcVersion = :tcVersion, " + " tytoIntegrationMode = :tytoIntegrationMode, " + " tytoUrl = :tytoUrl, "
			+ " tytoUsername = :tytoUsername, " + " tytoPassword = :tytoPassword " + " WHERE tenantID = :tenantId";

	/**
	 * Updates Tenant Configurations
	 * 
	 * @param tenantId
	 * @param tenantConfiguration
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = {
			"tenantConfigurationCache" }, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantConfiguration(@PartialCacheKey int tenantId, TenantConfiguration tenantConfiguration) {

		logger.debug("Entering updateTenantConfiguration() of TenantConfigurationDaoJdbcImpl");

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(tenantConfiguration);

		int updatedCount = getNamedParameterJdbcTemplate().update(UPDATE_TENANT_CONFIGURATION, parameters);

		if (updatedCount == 0) {
			updatedCount = insertTenantConfiguration(tenantConfiguration);
		}

		logger.debug("Exiting updateTenantConfiguration() of TenantConfigurationDaoJdbcImpl");

		return updatedCount;
	}

	private static final String UPDATE_SESSION_EXP_PERIOD_BY_ID = "UPDATE TenantConfiguration set sessionExpirationPeriod =:sessionExpirationPeriod where tenantID =:tenantID";
	private static final String UPDATE_TENANTCONFIGURATION_PUBLICROOMSETTINGS_RESET = "UPDATE TenantConfiguration set createPublicRoomEnable =:createPublicRoomEnable ,maxCreatePublicRoomUser =:maxCreatePublicRoomUser ";
	private static final String UPDATE_TENANTCONFIGURATION_PUBLICROOMSETTINGS_MAXRESET = "UPDATE TenantConfiguration set maxCreatePublicRoomUser =:maxCreatePublicRoomUser where maxCreatePublicRoomUser >:maxCreatePublicRoomUser";
	private static final String UPDATE_TENANTCONFIGURATION_USERATTR_USERIMAGE_UPLOAD_RESET = "UPDATE TenantConfiguration set uploadUserImage =:uploadUserImage";
	private static final String UPDATE_TENANTCONFIGURATION_USER_ATTRIBUTES_RESET = "UPDATE TenantConfiguration set userImage =:userImage,uploadUserImage =:uploadUserImage";

	private static final String UPDATE_SESSION_EXP_PERIOD_BY_VAL = "UPDATE TenantConfiguration set sessionExpirationPeriod =:sessionExpirationPeriod where sessionExpirationPeriod >:sessionExpirationPeriod";

	/**
	 * Updates Tenant's Session Expiration Configuration
	 *
	 * @param tenantId
	 * @param sessionExpPeriod
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateSessionExpConfig(@PartialCacheKey int tenantId, int sessionExpPeriod) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantID", tenantId);
		paramMap.put("sessionExpirationPeriod", sessionExpPeriod);
		if (tenantId == 0) {
			// Update all the tenants whose session exp value is more than the
			// new config value
			return getNamedParameterJdbcTemplate().update(UPDATE_SESSION_EXP_PERIOD_BY_VAL, paramMap);
		} else {
			return getNamedParameterJdbcTemplate().update(UPDATE_SESSION_EXP_PERIOD_BY_ID, paramMap);
		}

	}

	@Override
	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantCnfUserAttributes(String userImage, String uploadUserImage) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userImage", userImage);
		paramMap.put("uploadUserImage", uploadUserImage);

		return getNamedParameterJdbcTemplate().update(UPDATE_TENANTCONFIGURATION_USER_ATTRIBUTES_RESET, paramMap);
	}

	@Override
	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantCnfUserAttrUsrUploadUsage(String uploadUserImage) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uploadUserImage", uploadUserImage);

		return getNamedParameterJdbcTemplate().update(UPDATE_TENANTCONFIGURATION_USERATTR_USERIMAGE_UPLOAD_RESET,
				paramMap);
	}

	@Override
	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantCnfPblcRoomSettings(int createPublicRoomEnable, int maxCreatePublicRoomUser) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createPublicRoomEnable", createPublicRoomEnable);
		paramMap.put("maxCreatePublicRoomUser", maxCreatePublicRoomUser);

		return getNamedParameterJdbcTemplate().update(UPDATE_TENANTCONFIGURATION_PUBLICROOMSETTINGS_RESET, paramMap);
	}

	@Override
	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantCnfPblcRoomSettingsResetMax(@PartialCacheKey int maxCreatePublicRoomUser) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("maxCreatePublicRoomUser", maxCreatePublicRoomUser);

		return getNamedParameterJdbcTemplate().update(UPDATE_TENANTCONFIGURATION_PUBLICROOMSETTINGS_MAXRESET, paramMap);
	}

	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantAllLogAggregation(int logAggregation) {
		HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("logAggregation", logAggregation);
		return this.getNamedParameterJdbcTemplate()
				.update("UPDATE TenantConfiguration set logAggregation =:logAggregation", paramMap);
	}

	@TriggersRemove(cacheName = { "tenantConfigurationCache" }, removeAll = true)
	public int updateTenantAllCustomRole(int customRoleFlag) {
		HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("endpointBehavior", customRoleFlag);
		return this.getNamedParameterJdbcTemplate()
				.update("UPDATE TenantConfiguration set endpointBehavior =:endpointBehavior", paramMap);
	}
}