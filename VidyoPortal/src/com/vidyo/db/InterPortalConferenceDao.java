/**
 * 
 */
package com.vidyo.db;

import com.vidyo.bo.IpcConfiguration;

/**
 * @author Ganesh
 * 
 */
public interface InterPortalConferenceDao {

	/**
	 * Returns the InterPortalConference detail for a Tenant
	 * 
	 * @param tenantID
	 *            the Id of the Tenant
	 * @return InterPortalConference data object
	 */
	public IpcConfiguration getIpcConfiguration(int tenantId);

	/**
	 * Save the InterportalConference configuration for a Tenant
	 * 
	 * @param interPortalConference
	 *            the object holding the configuration details
	 */
	public void saveIpcConfiguration(IpcConfiguration interPortalConference);

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
	public int updateIpcConfiguration(int tenantId, int inbound, int outbound,
			int inboundLines, int outboundLines);

	/**
	 * Deletes the Inter-Portal Conference configuration for a Tenant
	 * 
	 * @param tenantId
	 *            the Id of the Tenant
	 */
	public int deleteIpcConfiguration(int tenantId);

	/**
	 * Updates the Allowed Flag for all the IPC Configuration entries
	 * 
	 * @param allowed
	 *            allowed/blocked flag to be set
	 * @return updated row count
	 */
	public int updateAllowBlockFlag(int allowed, int tenantId);

	/**
	 * 
	 * @param tenantId
	 * @param remoteHost
	 * @return
	 */
	public String getHostnameMatch(int tenantId, String remoteHost);

}
