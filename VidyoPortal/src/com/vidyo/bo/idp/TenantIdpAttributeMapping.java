package com.vidyo.bo.idp;

import java.io.Serializable;

public class TenantIdpAttributeMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7915969251597118366L;
	
	private int mappingID;
	private int tenantID;
	private String vidyoAttributeName;
	private String idpAttributeName;
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

	public String getIdpAttributeName() {
		return idpAttributeName;
	}

	public void setIdpAttributeName(String idpAttributeName) {
		this.idpAttributeName = idpAttributeName;
	}

	public String getDefaultAttributeValue() {
		return defaultAttributeValue;
	}

	public void setDefaultAttributeValue(String defaultAttributeValue) {
		this.defaultAttributeValue = defaultAttributeValue;
	}
}