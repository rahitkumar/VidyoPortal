/**
 * 
 */
package com.vidyo.service.room;

/**
 * @author ysakurikar
 *
 */
public class PublicRoomCreateRequest {
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
	private String displayName;
	
	/**
	 * 
	 */
	private String roomName;
	
	/**
	 * 
	 */
	private boolean pinRequired;
	
	/**
	 * 
	 */
	private boolean inMyContacts;
	
	/**
	 * 
	 */
	private boolean locked;
	
	private String PIN;
	
	private String description;
	
	
	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public boolean isPinRequired() {
		return pinRequired;
	}

	public void setPinRequired(boolean pinRequired) {
		this.pinRequired = pinRequired;
	}

	public boolean isInMyContacts() {
		return inMyContacts;
	}

	public void setInMyContacts(boolean inMyContacts) {
		this.inMyContacts = inMyContacts;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
