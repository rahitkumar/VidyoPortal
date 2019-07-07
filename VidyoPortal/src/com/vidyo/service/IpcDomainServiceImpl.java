/**
 * 
 */
package com.vidyo.service;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.bo.ipcdomain.IpcDomain;
import com.vidyo.db.ipcdomain.IpcDomainDao;

/**
 * @author Ganesh
 * 
 */
public class IpcDomainServiceImpl implements IpcDomainService {

	/**
	 * 
	 */
	private IpcDomainDao ipcDomainDao;

	/**
	 * 
	 * @return
	 */
	@Override
	public List<IpcDomain> getPortalIpcDomains() {
		List<IpcDomain> ipcPortalDomains = ipcDomainDao.getPortalIpcDomains();
		return ipcPortalDomains;
	}

	/**
	 * 
	 * @param allowBlockFlag
	 * @return
	 */
	public int updateWhiteListFlag(int allowBlockFlag) {
		return ipcDomainDao.updateWhiteListFlag(allowBlockFlag);
	}

	/**
	 * 
	 * @param domainNames
	 * @param whiteListFlag
	 */
	public void addDomains(String[] domainNames, int whiteListFlag) {
		ipcDomainDao.addDomains(domainNames, whiteListFlag);
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteIpcDomains(String[] ids) {
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			list.add(Integer.valueOf(id));
		}
		return ipcDomainDao.deleteIpcDomains(list);
	}
	
	/**
	 * 
	 * @param domainNames
	 * @return
	 */
	public int deleteIpcDomainsByName(String[] domainNames) {
		int returnCount = 0;
		if(domainNames != null && domainNames.length > 0) {
			List<String> domainNameList = new ArrayList<String>();
			for(String domainName : domainNames) {
				domainNameList.add(domainName);
			}
			returnCount = ipcDomainDao.deleteIpcDomainsByName(domainNameList);
		}
		return returnCount;
	}

	/**
	 * 
	 * @param remoteHost
	 * @return
	 */	
	@Override
	public String getHostnameMatch(String remoteHost) {
		return ipcDomainDao.getHostnameMatch(remoteHost);
	}

	/**
	 * @param ipcDomainDao
	 *            the ipcDomainDao to set
	 */
	public void setIpcDomainDao(IpcDomainDao ipcDomainDao) {
		this.ipcDomainDao = ipcDomainDao;
	}

}
