/**
 * 
 */
package com.vidyo.service.room;

import com.vidyo.framework.service.BaseServiceResponse;

/**
 * Response object holding the invite details.
 * 
 * @author ganesh
 *
 */
public class RoomAccessDetailsResponse extends BaseServiceResponse{
	
	/**
	 * 
	 */
	public static final int NO_ROOM_KEY = 8001;
	
	/**
	 * RoomKey for constructing Room Url
	 */
	private String roomKey;
	
	/**
	 * 
	 */
	private String roomPin;
	
	/**
	 * 
	 */
	private String extension;
	
	/**
	 * 
	 */
	private String tenantUrl;

	/**
	 * @return the roomKey
	 */
	public String getRoomKey() {
		return roomKey;
	}

	/**
	 * @param roomKey the roomKey to set
	 */
	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	/**
	 * @return the roomPin
	 */
	public String getRoomPin() {
		return roomPin;
	}

	/**
	 * @param roomPin the roomPin to set
	 */
	public void setRoomPin(String roomPin) {
		this.roomPin = roomPin;
	}

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
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
}
