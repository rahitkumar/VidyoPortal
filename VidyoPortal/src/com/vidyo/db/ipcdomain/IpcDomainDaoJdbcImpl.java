/**
 * 
 */
package com.vidyo.db.ipcdomain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.vidyo.bo.WildCardString;
import com.vidyo.bo.ipcdomain.IpcDomain;

/**
 * @author Ganesh
 * 
 */
public class IpcDomainDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements IpcDomainDao {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(IpcDomainDaoJdbcImpl.class.getName());

	/**
	 * 
	 */
	protected WildCardString wildCardString = new WildCardString();

	/**
	 * Returns all the available Domain/Address List
	 * 
	 * @return List of IpcDomain data objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IpcDomain> getPortalIpcDomains() {
		logger.debug("Entering getPortalIpcDomains() of IpcDomainDaoJdbcImpl");
		final String GET_ALL_IPC_DOMAIN_LIST = "SELECT domainID, domainName from IpcPortalDomainList";
		List<IpcDomain> portalIpcDomains = getNamedParameterJdbcTemplate()
				.query(GET_ALL_IPC_DOMAIN_LIST,
						new HashMap<String, String>(), new IpcDomainRowMapper());
		logger.debug("Exiting getPortalIpcDomains() of IpcDomainDaoJdbcImpl");
		return portalIpcDomains;
	}

	/**
	 * Update the Allowed/Blocked flag for the Domain/Address list
	 * 
	 * @param allowBlockFlag
	 *            Flag to be updated
	 * @return updated Row Count
	 */
	@Override
	public int updateWhiteListFlag(int allowBlockFlag) {
		logger.debug("Entering updateWhiteListFlag() of IpcDomainDaoJdbcImpl");
		String UPDATE_WHITE_LIST_FLAG = "UPDATE IpcPortalDomainList set whiteList =:allowBlockFlag";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("allowBlockFlag", allowBlockFlag);
		logger.debug("Exiting updateWhiteListFlag() of IpcDomainDaoJdbcImpl");
		return getNamedParameterJdbcTemplate().update(UPDATE_WHITE_LIST_FLAG,
				paramMap);
	}

	/**
	 * Save the newly added Domains/Address list
	 * 
	 * @param domainNames
	 *            Domain/Address list to be saved
	 * @param whiteListFlag
	 *            Flag indicating Allowed/Blocked
	 */
	@Override
	public void addDomains(String[] domainNames, int whiteListFlag) {
		logger.debug("Entering addDomains() of IpcDomainDaoJdbcImpl");
		StringBuilder insertSql = new StringBuilder(
				"INSERT INTO IpcPortalDomainList (domainName) values");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < domainNames.length; i++) {
			insertSql.append("(:domainName" + i + ")");
			if (i >= 0 && i < domainNames.length - 1) {
				insertSql.append(",");
			}
			paramMap.put("domainName" + i, domainNames[i]);
		}
		getNamedParameterJdbcTemplate().update(insertSql.toString(), paramMap);
		logger.debug("Exiting addDomains() of IpcDomainDaoJdbcImpl");
	}

	/**
	 * Delete the Domain/Address list by Ids
	 * 
	 * @param ids
	 *            Domain/Address Ids to be deleted
	 * @return deleted row count
	 */
	@Override
	public int deleteIpcDomains(List<Integer> ids) {
		logger.debug("Entering deleteIpcDomains() of IpcDomainDaoJdbcImpl");
		StringBuilder deleteSql = new StringBuilder(
				"DELETE FROM IpcPortalDomainList where domainID in (");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < ids.size(); i++) {
			deleteSql.append(ids.get(i));
			if (i < (ids.size() - 1)) {
				deleteSql.append(",");
			}
			paramMap.put("id" + i, ids.get(i));
		}
		deleteSql.append(")");
		logger.debug("Exiting deleteIpcDomains() of IpcDomainDaoJdbcImpl");
		return getNamedParameterJdbcTemplate().update(deleteSql.toString(),
				paramMap);
	}
	
	/**
	 * Delete the Domain/Address list by domain names
	 * 
	 * @param domainNames
	 *            domain names to be deleted
	 * @return deleted row count
	 */
	@Override
	public int deleteIpcDomainsByName(List<String> domainNames) {
		logger.debug("Entering deleteIpcDomainsByName() of IpcDomainDaoJdbcImpl");
		StringBuilder deleteSql = new StringBuilder(
				"DELETE FROM IpcPortalDomainList where domainName in (");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < domainNames.size(); i++) {
			deleteSql.append("'" + domainNames.get(i) + "'");
			if (i < (domainNames.size() - 1)) {
				deleteSql.append(",");
			}
		}
		deleteSql.append(")");
		logger.debug("Exiting deleteIpcDomainsByName() of IpcDomainDaoJdbcImpl");
		return getNamedParameterJdbcTemplate().update(deleteSql.toString(), paramMap);
	}

	/**
	 * Returns the matching hostname if the Portal Domain list matches one
	 * 
	 * @param remoteHost
	 *            Hostname to be matched
	 * @return IpcDomain data object
	 */
	@Override
	public String getHostnameMatch(String remoteHost) {
		logger.debug("Entering getHostnameMatch() of IpcDomainDaoJdbcImpl");
		String MATCH_REMOTE_HOST_NAME = "SELECT domainName FROM IpcPortalDomainList WHERE :remoteHost REGEXP domainName";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("remoteHost", remoteHost);
		List<String> ipcPortalDomains = getNamedParameterJdbcTemplate()
				.queryForList(MATCH_REMOTE_HOST_NAME, paramMap, String.class);
		logger.debug("Exiting getHostnameMatch() of IpcDomainDaoJdbcImpl");
		if (!ipcPortalDomains.isEmpty()) {
			return ipcPortalDomains.get(0);
		}
		// If the DB regex doesn't match, try using the wildcard match util
		String GET_WILDCARD_HOST_NAMES = "SELECT domainName FROM IpcPortalDomainList WHERE domainName like '%*%'";
		ipcPortalDomains = getNamedParameterJdbcTemplate().queryForList(
				GET_WILDCARD_HOST_NAMES, paramMap, String.class);
		if (!ipcPortalDomains.isEmpty()) {
			for (String hostName : ipcPortalDomains) {
				WildCardString wildCardString = new WildCardString();
				boolean match = wildCardString.isStringMatching(remoteHost.trim(), hostName.trim());
				if (match) {
					return hostName;
				}
			}
		}
		return null;
	}

}