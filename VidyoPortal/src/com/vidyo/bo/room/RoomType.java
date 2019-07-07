/**
 * 
 */
package com.vidyo.bo.room;

import java.io.Serializable;

/**
 * @author ganesh
 *
 */
public class RoomType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int roomTypeId;
	
	private String roomType;
	
	private String description;

	/**
	 * @return the roomTypeId
	 */
	public int getRoomTypeId() {
		return roomTypeId;
	}

	/**
	 * @param roomTypeId the roomTypeId to set
	 */
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
