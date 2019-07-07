package com.vidyo.rest.controllers.admin;

import java.io.Serializable;
import java.util.List;

import com.vidyo.bo.MemberRoles;

public class WSRestWrapper implements  Serializable {

	private static final long serialVersionUID = -8840362496520648208L;
	private String restUrl;
	private boolean isRestAuthConfigured;
	private 	List<MemberRoles>  assignedRoles;	

	
	public boolean isRestAuthConfigured() {
		return isRestAuthConfigured;
	}

	public void setRestAuthConfigured(boolean isRestAuthConfigured) {
		this.isRestAuthConfigured = isRestAuthConfigured;
	}

	public List<MemberRoles> getAssignedRoles() {
		return assignedRoles;
	}

	public void setAssignedRoles(List<MemberRoles> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}

	public String getRestUrl() {
			return restUrl;
	}

	public void setRestUrl(String restUrl) {
			this.restUrl = restUrl;
	}

    
}
