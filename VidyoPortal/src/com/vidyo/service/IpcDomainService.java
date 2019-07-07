/**
 * 
 */
package com.vidyo.service;

import java.util.List;

import com.vidyo.bo.ipcdomain.IpcDomain;

/**
 * @author Ganesh
 * 
 */
public interface IpcDomainService {

	/**
	 * 
	 * @return
	 */
	public List<IpcDomain> getPortalIpcDomains();

	/**
	 * 
	 * @param allowBlockFlag
	 * @return
	 */
	public int updateWhiteListFlag(int allowBlockFlag);

	/**
	 * 
	 * @param domainNames
	 * @param whiteListFlag
	 */
	public void addDomains(String[] domainNames, int whiteListFlag);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteIpcDomains(String[] ids);
	
	/**
	 * 
	 * @param domainNames
	 * @return
	 */
	public int deleteIpcDomainsByName(String[] domainNames);
	
	/**
	 * 
	 * @param remoteHost
	 * @return
	 */
	public String getHostnameMatch(String remoteHost);
}
