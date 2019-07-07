/**
 * 
 */
package com.vidyo.service.interportal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.IpcConfiguration;
import com.vidyo.db.InterPortalConferenceDao;

/**
 * @author Ganesh
 * 
 */
public class IpcConfigurationServiceImpl implements
		IpcConfigurationService {

	/**
	 * 
	 */
	protected final Logger logger = LoggerFactory.getLogger(IpcConfigurationServiceImpl.class.getName());

	/**
	 * 
	 */
	private InterPortalConferenceDao interPortalConferenceDao;

	/**
	 * 
	 * @param interPortalConference
	 * @return
	 */
	@Override
	public int saveIpcConfiguration(IpcConfiguration interPortalConference) {
		interPortalConferenceDao.saveIpcConfiguration(interPortalConference);
		return 0;
	}

	/**
	 * Returns the InterPortalConference Detail for a tenant
	 * 
	 * @param tenantId
	 * @return InterPortalConference
	 */
	@Override
	public IpcConfiguration getIpcConfiguration(int tenantId) {
		logger.debug("Entering getIpcConfiguration() of InterPortalConferenceServiceImpl");
		if (tenantId == 0) {
			return null;
		}
		IpcConfiguration interPortalConference = interPortalConferenceDao
				.getIpcConfiguration(tenantId);
		logger.debug("Exiting getIpcConfiguration() of InterPortalConferenceServiceImpl");
		return interPortalConference;
	}

	/**
	 * 
	 * @param tenantId
	 * @param inbound
	 * @param outbound
	 * @param inboundLines
	 * @param outboundLines
	 * @return
	 */
	@Override
	public int updateIpcConfiguration(int tenantId, int inbound, int outbound,
			int inboundLines, int outboundLines) {
		int updatedRowCount = interPortalConferenceDao.updateIpcConfiguration(
				tenantId, inbound, outbound, inboundLines, outboundLines);
		return updatedRowCount;
	}

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	@Override
	public int deleteIpcConfiguration(int tenantId) {
		int rc = interPortalConferenceDao.deleteIpcConfiguration(tenantId);
		return rc;
	}

	/**
	 * 
	 * @param allowed
	 * @return
	 */	
	@Override
	public int updateAllowBlockFlag(int allowed, int tenantId) {
		return interPortalConferenceDao.updateAllowBlockFlag(allowed, tenantId);
	}
	
	/**
	 * 
	 * @param tenantId
	 * @param remoteHost
	 * @return
	 */
	public String getHostnameMatch(int tenantId, String remoteHost) {
		return interPortalConferenceDao.getHostnameMatch(tenantId, remoteHost);
	}

	/**
	 * @param interPortalConferenceDao
	 *            the interPortalConferenceDao to set
	 */
	public void setInterPortalConferenceDao(
			InterPortalConferenceDao interPortalConferenceDao) {
		this.interPortalConferenceDao = interPortalConferenceDao;
	}

}
