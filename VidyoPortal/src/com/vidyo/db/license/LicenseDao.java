/**
 * 
 */
package com.vidyo.db.license;

import java.util.Map;

import com.vidyo.ws.manager.LicenseFeatureData;

/**
 * @author ganesh
 *
 */
public interface LicenseDao {

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public Map<String, String> getLicenseFeatures();
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Map<String, String> getLicenseDataFromFile(String fileName);
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean applySystemLicense(String fileName);
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean applyVMLicense(String fileName);

	/**
	 * Clears the System and VM license from the portal
	 * @return boolean indicates success if true
	 */
	public boolean clearLicense();
	
	/**
	 * Reverts back to old license.
	 * @return
	 */
	public boolean revertLicense();

	public boolean removeLicenseCacheData();
	

}
