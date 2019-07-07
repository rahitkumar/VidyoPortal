package com.vidyo.bo.member;

import java.io.Serializable;

public class MemberKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5124311556725987234L;
	
	private int tenantID;
	private int memberID;
	private int roleID;
	
	public int getTenantID() {
		return tenantID;
	}
	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	

}
