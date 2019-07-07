package com.vidyo.service;

import java.util.List;
import java.util.Map;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.service.license.response.LicenseResponse;

public interface LicensingService {
    public boolean setSystemLicense(String base64Content);
    public boolean setVMLicense(String base64Content);
    public List<SystemLicenseFeature> getTenantLicense(int tenantId);
    public SystemLicenseFeature getSystemLicenseFeature(String featureName);
    public Map<String, String> getLicenseFeatureDataFromFile(String sysFileName);
    public LicenseResponse testVMKeyToken(String user, String pwd);
    public boolean seatLicenseExpired();
    public boolean lineLicenseExpired();
    public long getAllowedSeats();
    public long getAllowedExecutives();
    public long getAllowedPanoramas();

    public String checkLicenseToBeUploaded(Map<String, String> licenseDataMap);

    public boolean isPortalAPIAccessEnabled();
    
	/**
	 * Validates the license file signature uploaded by endpoint.<br>
	 * Returns the response with status code and consumes line flag.
	 * 
	 * @param file
	 *            file name with absolute path
	 * @return license response with status code and consumes line flag
	 */
	public LicenseResponse validateLicenseFileSignature(String file);
	
	/**
	 * Returns the license details for the entire system.
	 * 
	 * @return list of system license features
	 */
	public List<SystemLicenseFeature> getSystemLicenseDetails();
	
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
