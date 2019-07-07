package com.vidyo.db.security.token;

import java.util.Date;

/**
 * @author Luke Taylor
 * @version $Id: PersistentRememberMeToken.java 2239 2007-11-10 15:42:21Z luke_t
 *          $
 */
public class VidyoPersistentRememberMeToken {
	private String username;
	private String series;
	private String tokenValue;
	private Date date;
	private int tenantId;
	private Date creationTime;
	private int validitySecs;
	private String endpointGUID;

	public VidyoPersistentRememberMeToken(String username, int tenantId,
			String series, String tokenValue, Date date, Date creationTime,
			int validitySecs, String endpoinGUID) {
		this.username = username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
		this.tenantId = tenantId;
		this.creationTime = creationTime;
		this.validitySecs = validitySecs;
		this.endpointGUID = endpoinGUID;
	}

	/**
	 * @return the endpointGUID
	 */
	public String getEndpointGUID() {
		return endpointGUID;
	}

	/**
	 * @param endpointGUID the endpointGUID to set
	 */
	public void setEndpointGUID(String endpointGUID) {
		this.endpointGUID = endpointGUID;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the validitySecs
	 */
	public int getValiditySecs() {
		return validitySecs;
	}

	/**
	 * @param validitySecs
	 *            the validitySecs to set
	 */
	public void setValiditySecs(int validitySecs) {
		this.validitySecs = validitySecs;
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

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public Date getDate() {
		return date;
	}
}
