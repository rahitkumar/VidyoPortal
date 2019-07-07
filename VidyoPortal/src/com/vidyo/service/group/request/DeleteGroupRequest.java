/**
 * 
 */
package com.vidyo.service.group.request;

/**
 * @author Ganesh
 *
 */
public class DeleteGroupRequest {
	
	/**
	 * 
	 */
	private int tenantId;
	
	/**
	 * 
	 */
	private int groupId;

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

}
