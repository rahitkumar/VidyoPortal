/**
 * 
 */
package com.vidyo.service.conference.request;

import com.vidyo.bo.Member;

/**
 * @author Ganesh
 *
 */
public class JoinConferenceRequest {
	
	/**
	 * 
	 */
	private int joiningMemberId;
	
	/**
	 * 
	 */
	private Member joiningMember;
	
	/**
	 * @return the joiningMember
	 */
	public Member getJoiningMember() {
		return joiningMember;
	}

	/**
	 * @param joiningMember the joiningMember to set
	 */
	public void setJoiningMember(Member joiningMember) {
		this.joiningMember = joiningMember;
	}
	
	/**
	 * @return the joiningMemberId
	 */
	public int getJoiningMemberId() {
		return joiningMemberId;
	}

	/**
	 * @param joiningMemberId the joiningMemberId to set
	 */
	public void setJoiningMemberId(int joiningMemberId) {
		this.joiningMemberId = joiningMemberId;
	}

	/**
	 * 
	 */
	private int roomId;
	
	/**
	 * 
	 */
	private String pin;
	
	/**
	 * 
	 */
	private String roomNameExt;
	
	/**
	 * 
	 */
	private String tenantUrl;
	
	/**
	 * @return the tenantUrl
	 */
	public String getTenantUrl() {
		return tenantUrl;
	}

	/**
	 * @param tenantUrl the tenantUrl to set
	 */
	public void setTenantUrl(String tenantUrl) {
		this.tenantUrl = tenantUrl;
	}

	/**
	 * @return the roomNameExt
	 */
	public String getRoomNameExt() {
		return roomNameExt;
	}

	/**
	 * @param roomNameExt the roomNameExt to set
	 */
	public void setRoomNameExt(String roomNameExt) {
		this.roomNameExt = roomNameExt;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * 
	 */
	private String schRoomConfName;
	
	/**
	 * 
	 */
	private boolean checkCrossTenant = true;
	
	/**
	 * 
	 */
	private boolean checkPin = true;

	/**
	 * @return the checkPin
	 */
	public boolean isCheckPin() {
		return checkPin;
	}

	/**
	 * @param checkPin the checkPin to set
	 */
	public void setCheckPin(boolean checkPin) {
		this.checkPin = checkPin;
	}

	/**
	 * @return the checkCrossTenant
	 */
	public boolean isCheckCrossTenant() {
		return checkCrossTenant;
	}

	/**
	 * @param checkCrossTenant the checkCrossTenant to set
	 */
	public void setCheckCrossTenant(boolean checkCrossTenant) {
		this.checkCrossTenant = checkCrossTenant;
	}

	/**
	 * @return the schRoomConfName
	 */
	public String getSchRoomConfName() {
		return schRoomConfName;
	}

	/**
	 * @param schRoomConfName the schRoomConfName to set
	 */
	public void setSchRoomConfName(String schRoomConfName) {
		this.schRoomConfName = schRoomConfName;
	}

	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
		

}
