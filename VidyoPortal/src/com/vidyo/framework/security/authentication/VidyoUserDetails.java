package com.vidyo.framework.security.authentication;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

public class VidyoUserDetails extends User implements org.springframework.security.core.userdetails.UserDetails {

	protected static final Logger logger = LoggerFactory.getLogger(VidyoUserDetails.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3120073291744834826L;
	public static final int REFRESH_INTERVAL_MINUTES = 60;
	private String bak;
	private boolean allowedToParticipate;
	private int tenantID;
	private boolean importedUser = false;

	private int memberId;

	private String sak;

	private String sourceIP = "Unknown";

	private Date lastLDAPRefreshTime = null;
	private String lastModifiedDateExternal;
	private int langId;

	private String memberName;

	private String emailAddress;
	
	private String pak2;

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the lastRefreshTime
	 */
	public Date getLastRefreshTime() {
		return lastLDAPRefreshTime;
	}

	/**
	 * @param lastRefreshTime
	 *            the lastRefreshTime to set
	 */
	public void setLastRefreshTime(Date lastRefreshTime) {
		this.lastLDAPRefreshTime = lastRefreshTime;
	}

	/**
	 * @return the sak
	 */
	public String getSak() {
		return sak;
	}

	/**
	 * @param sak
	 *            the sak to set
	 */
	public void setSak(String sak) {
		this.sak = sak;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public String getBak() {
		return bak;
	}

	public boolean isAllowedToParticipate() {
		return allowedToParticipate;
	}

	public boolean isImportedUser() {
		return importedUser;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public VidyoUserDetails(String username, String password, String bak, String sak, boolean enabled,
			boolean allowedToParticipate, GrantedAuthority[] authorities, boolean importedUser, int tenantID,
			int memberId, int langId, String memberName, String emailAddress, String pak2,String lastModifiedDateExternal) throws IllegalArgumentException {
		super(username, password, enabled, true, true, true, Arrays.asList(authorities));
		this.bak = bak;
		this.allowedToParticipate = allowedToParticipate;
		this.importedUser = importedUser;
		this.tenantID = tenantID;
		this.memberId = memberId;
		this.sak = sak;
		this.langId = langId;
		this.memberName = memberName;
		this.emailAddress = emailAddress;
		this.pak2 = pak2;
		this.lastModifiedDateExternal = lastModifiedDateExternal;
		
	}

	public String getLastModifiedDateExternal() {
		return lastModifiedDateExternal;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof VidyoUserDetails)) {
			return false;
		}

		VidyoUserDetails otherUser = (VidyoUserDetails) obj;

		return (this.getUsername().equals(otherUser.getUsername()) && this.getTenantID() == otherUser.getTenantID());
	}

	public int hashCode() {
		return memberId % 10000;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.User#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the pak2
	 */
	public String getPak2() {
		return pak2;
	}

	/**
	 * @param pak2 the pak2 to set
	 */
	public void setPak2(String pak2) {
		this.pak2 = pak2;
	}
}