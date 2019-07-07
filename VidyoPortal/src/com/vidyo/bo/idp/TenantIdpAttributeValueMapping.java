package com.vidyo.bo.idp;

import java.io.Serializable;

public class TenantIdpAttributeValueMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2224204907689854570L;
	
	private int valueID;
	private int mappingID;
	private String vidyoValueName;
	private String idpValueName;

	public TenantIdpAttributeValueMapping () {
	}

	public TenantIdpAttributeValueMapping (int mappingID, String vidyoValueName) {
		this.mappingID = mappingID;
		this.vidyoValueName = vidyoValueName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof TenantIdpAttributeValueMapping)) {
			return false;
		}

		TenantIdpAttributeValueMapping vidyoValue = (TenantIdpAttributeValueMapping) o;

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

	public String getIdpValueName() {
		return idpValueName;
	}

	public void setIdpValueName(String idpValueName) {
		this.idpValueName = idpValueName;
	}
}