/**
 *
 */
package com.vidyo.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.WildCardString;

/**
 * @author Ganesh
 *
 */
public class InterPortalConferenceDaoJdbcImpl extends
		NamedParameterJdbcDaoSupport implements InterPortalConferenceDao {

	/**
	 * Logger
	 */
	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(InterPortalConferenceDaoJdbcImpl.class.getName());

	/**
	 *
	 */
	protected WildCardString wildCardString = new WildCardString();

	/**
	 * Returns the InterPortalConference detail for a Tenant
	 *
	 * @param tenantID
	 *            the Id of the Tenant
	 * @return InterPortalConference data object
	 */
	@Override
	public IpcConfiguration getIpcConfiguration(int tenantId) {
		logger.debug("Entering getIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		String GET_IPC_DETAIL_BY_TENANT_ID = "select ipcID, tenantID, inbound, outbound, inboundLines, outboundLines, allowed from IpcConfiguration where tenantID =:tenantID";
		Map<String, Integer> namedParamsMap = new HashMap<String, Integer>();
		namedParamsMap.put("tenantID", tenantId);
		IpcConfiguration ipcDetail = (IpcConfiguration) getNamedParameterJdbcTemplate()
				.queryForObject(
						GET_IPC_DETAIL_BY_TENANT_ID,
						namedParamsMap,
						BeanPropertyRowMapper
								.newInstance(IpcConfiguration.class));
		logger.debug("Exiting getIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		return ipcDetail;
	}

	/**
	 * Save the InterportalConference configuration for a Tenant
	 *
	 * @param interPortalConference
	 *            the object holding the configuration details
	 */
	@Override
	public void saveIpcConfiguration(IpcConfiguration interPortalConference) {
		logger.debug("Entering saveIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");

		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("IpcConfiguration").usingGeneratedKeyColumns(
						"ipcID");

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				interPortalConference);

		Number newID = insert.executeAndReturnKey(parameters);

		logger.debug("Exiting saveIpcConfiguration() of InterPortalConferenceDaoJdbcImpl<<<<<<<");
	}

	/**
	 * Updates the Inter-Portal Conference configuration for a Tenant
	 *
	 * @param tenantId
	 *            the Id of the Tenant
	 * @param inbound
	 *            enable/disable inbound conference flag
	 * @param outbound
	 *            enable/disable outbound conference flag
	 * @param inboundLines
	 *            number of inbound lines
	 * @param outboundLines
	 *            number of outbound lines
	 * @return updated row count
	 */
	@Override
	public int updateIpcConfiguration(int tenantId, int inbound, int outbound,
			int inboundLines, int outboundLines) {
		logger.debug("Entering updateIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		StringBuffer sql = new StringBuffer(
				"UPDATE IpcConfiguration ipc set inbound =:inbound, outbound =:outbound");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("inbound", inbound);
		paramMap.put("outbound", outbound);
		if (tenantId != 0) {
			sql.append(" where tenantID =:tenantId");
			paramMap.put("tenantId", tenantId);
		}
		int rc = getNamedParameterJdbcTemplate().update(sql.toString(),
				paramMap);

		logger.debug("Exiting updateIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		return rc;
	}

	/**
	 * Deletes the Inter-Portal Conference configuration for a Tenant
	 *
	 * @param tenantId
	 *            the Id of the Tenant
	 */
	public int deleteIpcConfiguration(int tenantId) {
		logger.debug("Entering deleteIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		String DELETE_IPC_CONFIG = "DELETE FROM IpcConfiguration WHERE tenantID =:tenantId";
		SqlParameterSource paramSource = new MapSqlParameterSource("tenantId",
				tenantId);
		int rc = getNamedParameterJdbcTemplate().update(DELETE_IPC_CONFIG,
				paramSource);
		logger.debug("Exiting deleteIpcConfiguration() of InterPortalConferenceDaoJdbcImpl");
		return rc;
	}

	/**
	 * Updates the Allowed Flag for all the IPC Configuration entries
	 *
	 * @param allowed
	 *            Allowed/Blocked flag to be set
	 * @return updated row count
	 */
	public int updateAllowBlockFlag(int allowed, int tenantId) {
		logger.debug("Entering updateAllowBlockedFlag() of InterPortalConferenceDaoJdbcImpl");
		String UPDATE_ALLOWED_FLAG = "UPDATE IpcConfiguration set allowed =:allowed where tenantID =:tenantId";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("allowed", allowed);
		paramMap.put("tenantId", tenantId);
		logger.debug("Exiting updateAllowBlockedFlag() of InterPortalConferenceDaoJdbcImpl");
		return getNamedParameterJdbcTemplate().update(UPDATE_ALLOWED_FLAG,
				paramMap);
	}

	/**
	 * Query to get the hostnames configured with wildcard
	 */
	public static final String GET_WILDCARD_HOST_NAMES = "SELECT hostname FROM IpcConfiguration ipc, IpcTenantDomainList itdl WHERE ipc.tenantID =:tenantId and ipc.ipcID = itdl.ipcID and itdl.hostName like '%*%'";

	/**
	 * Query to get the hostnames matching with the wildcard using regex function of DB
	 */
	public static final String GET_REMOTE_HOST_MATCH = "SELECT itdl.hostname FROM IpcConfiguration ipc, IpcTenantDomainList itdl WHERE ipc.tenantID =:tenantId and ipc.ipcID = itdl.ipcID and :remoteHost REGEXP itdl.hostName";

	/**
	 *
	 * @param tenantId
	 * @param remoteHost
	 * @return
	 */
	@Override
	public String getHostnameMatch(int tenantId, String remoteHost) {
		logger.debug("Entering getHostnameMatch() of InterPortalConferenceDaoJdbcImpl");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("remoteHost", remoteHost);
		paramMap.put("tenantId", tenantId);
		List<String> hostnameMatches = getNamedParameterJdbcTemplate()
				.queryForList(GET_REMOTE_HOST_MATCH, paramMap, String.class);
		if (!hostnameMatches.isEmpty()) {
			logger.debug("Exiting getHostnameMatch() of InterPortalConferenceDaoJdbcImpl returning {}", hostnameMatches.get(0));
			return hostnameMatches.get(0);
		}
		// If the DB regex doesn't match, try using the wildcard match util
		hostnameMatches = getNamedParameterJdbcTemplate().queryForList(
				GET_WILDCARD_HOST_NAMES, paramMap, String.class);
		if (!hostnameMatches.isEmpty()) {
			for (String hostName : hostnameMatches) {
				WildCardString wildCardString = new WildCardString();
				boolean match = wildCardString.isStringMatching(remoteHost.trim(), hostName.trim());
				if (match) {
					logger.debug("Exiting getHostnameMatch() of InterPortalConferenceDaoJdbcImpl returning {}", hostName);
					return hostName;
				}
			}

		}
		logger.debug("Exiting getHostnameMatch() of InterPortalConferenceDaoJdbcImpl returning {}", "null");
		return null;
	}

}