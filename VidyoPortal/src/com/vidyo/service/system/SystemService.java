/**
 * 
 */
package com.vidyo.service.system;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Service;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

/**
 * @author Ganesh
 * 
 */
public interface SystemService {

	/**
	 * 
	 * @return
	 * @throws NoVidyoManagerException
	 */
	public VidyoManagerServiceStub getVidyoManagerServiceStubWithAuth(
			int tenantID) throws NoVidyoManagerException;

	/**
	 * 
	 * @return
	 */
	public Service getVidyoManagerService(int tenantID);
	
	/**
	 * 
	 * @param configName
	 * @return
	 */
	public Configuration getConfiguration(String configName);

	/**
	 * Returns true if FIPS mode is enabled
	 * 
	 * @return
	 */
	public boolean isFipsModeEnabled();

	/**
	 * Utility method to clear vidyo manager cache. Implementation will specify which cache
	 * has to be cleared.
	 * 
	 */
	public void clearVidyoManagerCache();
	
	/**
	 * 
	 * @return
	 */
	public int updateVidyoManagerCredentials();
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public int updateComponentsEncryption(boolean value);
}
