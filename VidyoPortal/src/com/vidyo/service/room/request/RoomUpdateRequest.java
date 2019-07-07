/**
 * 
 */
package com.vidyo.service.room.request;

/**
 * @author Ganesh
 *
 */
public class RoomUpdateRequest {
	
	/**
	 * 
	 */
	private int oldGroupId;
	
	/**
	 * 
	 */
	private int defaultGroupId;
	
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
	private String roomType;
	
	/**
	 * 
	 */
	private int newGroupId;

	/**
	 * @return the oldGroupId
	 */
	public int getOldGroupId() {
		return oldGroupId;
	}

	/**
	 * @param oldGroupId the oldGroupId to set
	 */
	public void setOldGroupId(int oldGroupId) {
		this.oldGroupId = oldGroupId;
	}
	
	/**
	 * @return the defaultGroupId
	 */
	public int getDefaultGroupId() {
		return defaultGroupId;
	}

	/**
	 * @param defaultGroupId the defaultGroupId to set
	 */
	public void setDefaultGroupId(int defaultGroupId) {
		this.defaultGroupId = defaultGroupId;
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
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the newGroupId
	 */
	public int getNewGroupId() {
		return newGroupId;
	}

	/**
	 * @param newGroupId the newGroupId to set
	 */
	public void setNewGroupId(int newGroupId) {
		this.newGroupId = newGroupId;
	}

}
