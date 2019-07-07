/**
 * 
 */
package com.vidyo.service.interportal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.ipcdomain.IpcDomain;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IpcDomainService;

/**
 * @author Ganesh
 * 
 */
public class InterPortalConferenceServiceImpl implements
		InterPortalConferenceService {

	/**
	 * Logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(InterPortalConferenceServiceImpl.class.getName());

	/**
	 * IpcConfigurationService which gives the configuration details for a
	 * Tenant
	 */
	private IpcConfigurationService ipcConfigurationService;

	/**
	 * SystemService which provides the Poratl Level configuration details
	 */
	private ISystemService systemService;

	/**
	 * 
	 */
	private IpcDomainService ipcDomainService;

	/**
	 * 
	 * @param tenantId
	 * @param hostName
	 * @return
	 */
	public boolean isInterPortalConferencingAllowed(int tenantId,
			String hostName, boolean outbound) {
		logger.debug("Entering isInterPortalConferencingAllowed() of FederationConferenceServiceImpl");
		// Get the IpcConfiguration for the Tenant
		IpcConfiguration ipcConfiguration = ipcConfigurationService
				.getIpcConfiguration(tenantId);
		
		if(ipcConfiguration == null) {
			return false;
		}

		// Super or Admin Managed, Check if In-bound/Out-bound is enabled based on the flag
		int allowConf = 0;
		if(outbound) {
			allowConf = ipcConfiguration.getOutbound();
		} else {
			allowConf = ipcConfiguration.getInbound();
		}
		
		// Block the call, if outbound/inbound is not enabled
		if (allowConf == 0) {
			logger.error("IPC outbound/inbound is not enabled for the Tenant -"
					+ tenantId);
			return false;
		}

		// Get the IPC access control details
		boolean isIpcSuperManaged = systemService.isIpcSuperManaged();

		// Case1: Super Managed IPC
		if (isIpcSuperManaged) {
			//Get the allow/blocked from the Configuration table
			int allowBlockFlag = 0;
			Configuration config = systemService
					.getConfiguration("ipcAllowDomainFlag");

			if (config != null && config.getConfigurationValue() != null) {
				allowBlockFlag = Integer
						.valueOf(config.getConfigurationValue());

			}
						
			/*
			 * If Super Managed, match the remote host with the domain names in
			 * the IpcPortalDomainList
			 */

			String ipcPortalDomain = ipcDomainService
					.getHostnameMatch(hostName);

			/*
			 * No match in the database, a)If Blocked list, allow this call b)If
			 * Allowed list, block this call
			 */
			if (ipcPortalDomain == null || ipcPortalDomain == "") {
				if (logger.isDebugEnabled()) {
					logger.debug("No match for the hostname -" + hostName
							+ " Allow/Block Flag -"
							+ ipcConfiguration.getAllowed());
				}
				return (allowBlockFlag == 0);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Match for the hostname -" + hostName
							+ " Allow/Block Flag -"
							+ ipcConfiguration.getAllowed() + " DB Match -"
							+ ipcPortalDomain);
				}
				return (allowBlockFlag == 1);
			}

		} else {
			// Case2: Admin managed IPC
			if (logger.isDebugEnabled()) {
				logger.debug("Admin Managed IPC for Tenant -" + tenantId
						+ " RemoteHostName -" + hostName);
			}
			String hostNameMatch = ipcConfigurationService.getHostnameMatch(
					tenantId, hostName);
			if (hostNameMatch == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("No match for the hostname -" + hostName
							+ " Allow/Block Flag -"
							+ ipcConfiguration.getAllowed());
				}
				return (ipcConfiguration.getAllowed() == 0);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Match for the hostname -" + hostName
							+ " Allow/Block Flag -"
							+ ipcConfiguration.getAllowed() + " DB Match -"
							+ hostNameMatch);
				}
				return (ipcConfiguration.getAllowed() == 1);
			}

		}
	}

	/**
	 * @param ipcConfigurationService
	 *            the ipcConfigurationService to set
	 */
	public void setIpcConfigurationService(
			IpcConfigurationService ipcConfigurationService) {
		this.ipcConfigurationService = ipcConfigurationService;
	}

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param ipcDomainService
	 *            the ipcDomainService to set
	 */
	public void setIpcDomainService(IpcDomainService ipcDomainService) {
		this.ipcDomainService = ipcDomainService;
	}

}
