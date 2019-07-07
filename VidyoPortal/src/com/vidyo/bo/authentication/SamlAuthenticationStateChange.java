package com.vidyo.bo.authentication;

import java.io.Serializable;

public class SamlAuthenticationStateChange implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6691827183984274925L;
	
	private int tenantID;
	private SamlAuthenticationState samlAuthenticationState;
	private SamlAuthentication samlAuthentication;
	private String oldSpEntityId = null;
	private String oldIdpEntityId = null;
	
	public SamlAuthenticationState getSamlAuthenticationState() {
		return samlAuthenticationState;
	}

	public void setSamlAuthenticationState(SamlAuthenticationState samlAuthenticationState) {
		this.samlAuthenticationState = samlAuthenticationState;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public SamlAuthentication getSamlAuthentication() {
		return samlAuthentication;
	}

	public void setSamlAuthentication(SamlAuthentication samlAuthentication) {
		this.samlAuthentication = samlAuthentication;
	}

	public String getOldSpEntityId() {
		return oldSpEntityId;
	}

	public void setOldSpEntityId(String oldSpEntityId) {
		this.oldSpEntityId = oldSpEntityId;
	}

	public String getOldIdpEntityId() {
		return oldIdpEntityId;
	}

	public void setOldIdpEntityId(String oldIdpEntityId) {
		this.oldIdpEntityId = oldIdpEntityId;
	}
	
}
