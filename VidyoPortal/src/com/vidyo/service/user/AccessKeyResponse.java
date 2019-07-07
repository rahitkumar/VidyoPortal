/**
 * 
 */
package com.vidyo.service.user;

import com.vidyo.framework.service.BaseServiceResponse;

/**
 * @author Ganesh
 * 
 */
public class AccessKeyResponse extends BaseServiceResponse {
	
	/**
	 * 
	 */
	public static final int INVALID_TENANT = 1001;
	
	/**
	 * 
	 */
	public static final int KEY_GEN_FAILED = 1002; 	

	/**
	 * Hostname - tenant url
	 */
	private String tenantUrl;

	/**
	 * Access Key
	 */
	private String accessKey;
	
	/**
	 * 
	 */
	private String portalPath;

	/**
	 * @return the tenantUrl
	 */
	public String getTenantUrl() {
		return tenantUrl;
	}

	/**
	 * @return the portalPath
	 */
	public String getPortalPath() {
		return portalPath;
	}

	/**
	 * @param portalPath the portalPath to set
	 */
	public void setPortalPath(String portalPath) {
		this.portalPath = portalPath;
	}

	/**
	 * @param tenantUrl
	 *            the tenantUrl to set
	 */
	public void setTenantUrl(String tenantUrl) {
		this.tenantUrl = tenantUrl;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey
	 *            the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
