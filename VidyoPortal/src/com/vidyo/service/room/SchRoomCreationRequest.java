/**
 * 
 */
package com.vidyo.service.room;

/**
 * @author Ganesh
 *
 */
public class SchRoomCreationRequest {
	
	/**
	 * 
	 */
	private int tenantId;
	
	/**
	 * 
	 */
	private int memberId;
	
	/**
	 * 
	 */
	private int groupId;
	
	/**
	 * 
	 */
	private String memberName;
	
	/**
	 * 
	 */
	private int recurring;
	
	/**
	 * 
	 */
	private boolean pinRequired;
	
	private String externalRoomId;

	public String getExternalRoomId() {
		return externalRoomId;
	}

	public void setExternalRoomId(String externalRoomId) {
		this.externalRoomId = externalRoomId;
	}

	/**
	 * @return the tenantId
	 */
	public int getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the memberId
	 */
	public int getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the recurring
	 */
	public int getRecurring() {
		return recurring;
	}

	/**
	 * @param recurring the recurring to set
	 */
	public void setRecurring(int recurring) {
		this.recurring = recurring;
	}

	/**
	 * @return the pinRequired
	 */
	public boolean isPinRequired() {
		return pinRequired;
	}

	/**
	 * @param pinRequired the pinRequired to set
	 */
	public void setPinRequired(boolean pinRequired) {
		this.pinRequired = pinRequired;
	}

}
