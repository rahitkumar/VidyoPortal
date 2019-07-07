/**
 * 
 */
package com.vidyo.service.security.token.request;

/**
 * @author ganesh
 * 
 */
public class TokenCreationRequest {

	/**
	 * 
	 */
	private String username;

	/**
	 * 
	 */
	private int memberId;

	/**
	 * 
	 */
	private String endpointId;

	/**
	 * 
	 */
	private int tenantId;
	
	/**
	 * Validity time in seconds
	 */
	private int validityTime;
	
	/**
	 * 
	 */
	private boolean deleteOldTokens;

	/**
	 * @return the validityTime
	 */
	public int getValidityTime() {
		return validityTime;
	}

	/**
	 * @param validityTime the validityTime to set
	 */
	public void setValidityTime(int validityTime) {
		this.validityTime = validityTime;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the memberId
	 */
	public int getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the endpointId
	 */
	public String getEndpointId() {
		return endpointId;
	}

	/**
	 * @param endpointId
	 *            the endpointId to set
	 */
	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	/**
	 * @return the tenantId
	 */
	public int getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isDeleteOldTokens() {
		return deleteOldTokens;
	}

	public void setDeleteOldTokens(boolean deleteOldTokens) {
		this.deleteOldTokens = deleteOldTokens;
	}

}
