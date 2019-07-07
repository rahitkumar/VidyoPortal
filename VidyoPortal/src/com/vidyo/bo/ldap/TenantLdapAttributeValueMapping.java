package com.vidyo.bo.ldap;

import java.io.Serializable;

public class TenantLdapAttributeValueMapping implements Serializable {

	private int valueID;
	private int mappingID;
	private String vidyoValueName;
	private String ldapValueName;

	public TenantLdapAttributeValueMapping () {
	}

	public TenantLdapAttributeValueMapping (int mappingID, String vidyoValueName) {
		this.mappingID = mappingID;
		this.vidyoValueName = vidyoValueName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof TenantLdapAttributeValueMapping)) {
			return false;
		}

		TenantLdapAttributeValueMapping vidyoValue = (TenantLdapAttributeValueMapping) o;

		return vidyoValueName.equals(vidyoValue.getVidyoValueName());
	}

	@Override
	public int hashCode() {
		int result = vidyoValueName.hashCode();
		result = 732 * result;
		return result;
	}

	public int getValueID() {
		return valueID;
	}

	public void setValueID(int valueID) {
		this.valueID = valueID;
	}

	public int getMappingID() {
		return mappingID;
	}

	public void setMappingID(int mappingID) {
		this.mappingID = mappingID;
	}

	public String getVidyoValueName() {
		return vidyoValueName;
	}

	public void setVidyoValueName(String vidyoValueName) {
		this.vidyoValueName = vidyoValueName;
	}

	public String getLdapValueName() {
		return ldapValueName;
	}

	public void setLdapValueName(String ldapValueName) {
		this.ldapValueName = ldapValueName;
	}
}