/**
 * 
 */
package com.vidyo.service.conference.request;

/**
 * @author ganesh
 * 
 */
public class LeaveConferenceRequest {

	/**
	 * RoomId/ConfId - Room in which Conference is taking place
	 */
	private int roomId;

	/**
	 * EndpointId to be removed from the Room
	 */
	private int endpointId;
	
	/**
	 * Moderator PIN for the room
	 */
	private String moderatorPin;

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

	/**
	 * @return the endpointId
	 */
	public int getEndpointId() {
		return endpointId;
	}

	/**
	 * @param endpointId the endpointId to set
	 */
	public void setEndpointId(int endpointId) {
		this.endpointId = endpointId;
	}

	/**
	 * @return the moderatorPin
	 */
	public String getModeratorPin() {
		return moderatorPin;
	}

	/**
	 * @param moderatorPin the moderatorPin to set
	 */
	public void setModeratorPin(String moderatorPin) {
		this.moderatorPin = moderatorPin;
	}

}
