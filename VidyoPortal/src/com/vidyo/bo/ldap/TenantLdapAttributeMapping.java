package com.vidyo.bo.ldap;

import java.io.Serializable;

public class TenantLdapAttributeMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3441503187440535228L;
	
	private int mappingID;
	private int tenantID;
	private String vidyoAttributeName;
	private String ldapAttributeName;
	private String defaultAttributeValue;

	public int getMappingID() {
		return mappingID;
	}

	public void setMappingID(int mappingID) {
		this.mappingID = mappingID;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public String getVidyoAttributeName() {
		return vidyoAttributeName;
	}

	public void setVidyoAttributeName(String vidyoAttributeName) {
		this.vidyoAttributeName = vidyoAttributeName;
	}

	public String getLdapAttributeName() {
		return ldapAttributeName;
	}

	public void setLdapAttributeName(String ldapAttributeName) {
		this.ldapAttributeName = ldapAttributeName;
	}

	public String getDefaultAttributeValue() {
		return defaultAttributeValue;
	}

	public void setDefaultAttributeValue(String defaultAttributeValue) {
		this.defaultAttributeValue = defaultAttributeValue;
	}
}