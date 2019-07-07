/**
 * 
 */
package com.vidyo.service.interportal;

import com.vidyo.bo.IpcConfiguration;

/**
 * @author Ganesh
 * 
 */
public interface IpcConfigurationService {

	/**
	 * 
	 * @param interPortalConference
	 * @return
	 */
	public int saveIpcConfiguration(IpcConfiguration interPortalConference);

	/**
	 * 
	 * @param tenantID
	 * @return
	 */
	public IpcConfiguration getIpcConfiguration(int tenantId);

	/**
	 * 
	 * @param tenantId
	 * @param inbound
	 * @param outbound
	 * @param inboundLines
	 * @param outboundLines
	 * @return
	 */
	public int updateIpcConfiguration(int tenantId, int inbound, int outbound,
			int inboundLines, int outboundLines);
	
	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public int deleteIpcConfiguration(int tenantId);
	
	/**
	 * 
	 * @param allowed
	 * @return
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
