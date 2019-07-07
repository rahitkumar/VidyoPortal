/**
 * 
 */
package com.vidyo.db.ipcdomain;

import java.util.List;

import com.vidyo.bo.ipcdomain.IpcDomain;

/**
 * @author Ganesh
 * 
 */
public interface IpcDomainDao {

	/**
	 * Returns all the available Domain/Address List
	 * 
	 * @return List of IpcDomain data objects
	 */
	public List<IpcDomain> getPortalIpcDomains();

	/**
	 * Update the Allowed/Blocked flag for the Domain/Address list
	 * 
	 * @param allowBlockFlag
	 *            Flag to be updated
	 * @return updated Row Count
	 */
	public int updateWhiteListFlag(int allowBlockFlag);

	/**
	 * Save the newly added Domains/Address list
	 * 
	 * @param domainNames
	 *            Domain/Address list to be saved
	 * @param whiteListFlag
	 *            Flag indicating Allowed/Blocked
	 */
	public void addDomains(String[] domainNames, int whiteListFlag);

	/**
	 * Delete the Domain/Address list by Ids
	 * 
	 * @param ids
	 *            Domain/Address Ids to be deleted
	 * @return deleted row count
	 */
	public int deleteIpcDomains(List<Integer> ids);
	
	/**
	 * Delete the Domain/Address list by domain names
	 * 
	 * @param domainNames
	 *            domain names to be deleted
	 * @return deleted row count
	 */
	public int deleteIpcDomainsByName(List<String> domainNames);
	
	/**
	 * Returns the matching hostname if the Portal Domain list matches one
	 * 
	 * @param remoteHost
	 *            Hostname to be matched
	 * @return IpcDomain data object
	 */
	public String getHostnameMatch(String remoteHost);
}
