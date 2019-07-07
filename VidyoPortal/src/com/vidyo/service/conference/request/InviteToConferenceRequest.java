/**
 * 
 */
package com.vidyo.service.conference.request;

/**
 * @author Ganesh
 * 
 */
public class InviteToConferenceRequest {

	/**
	 * MemberId of the Inviter
	 */
	private int inviterId;

	/**
	 * RoomId/EntityId of the Invitee. 
	 * Invitee's id can only be the roomId of the user not memberId.
	 */
	private int inviteeId;
	
	/**
	 * Legacy name
	 */
	private String legacy;

	/**
	 * Room to join
	 */
	private int roomId;
	
	/**
	 * 
	 */
	private String moderatorPin;
	
	/**
	 * Caller Identifier String - Only used for custom implementations
	 */
	private String callFromIdentifier;

	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId
	 *            the roomId to set
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	/**
	 * @return the inviterId
	 */
	public int getInviterId() {
		return inviterId;
	}

	/**
	 * @param inviterId the inviterId to set
	 */
	public void setInviterId(int inviterId) {
		this.inviterId = inviterId;
	}

	/**
	 * @return the inviteeId
	 */
	public int getInviteeId() {
		return inviteeId;
	}

	/**
	 * @param inviteeId the inviteeId to set
	 */
	public void setInviteeId(int inviteeId) {
		this.inviteeId = inviteeId;
	}

	/**
	 * @return the legacy
	 */
	public String getLegacy() {
		return legacy;
	}

	/**
	 * @param legacy the legacy to set
	 */
	public void setLegacy(String legacy) {
		this.legacy = legacy;
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

	/**
	 * @return the callFromIdentifier
	 */
	public String getCallFromIdentifier() {
		return callFromIdentifier;
	}

	/**
	 * @param callFromIdentifier the callFromIdentifier to set
	 */
	public void setCallFromIdentifier(String callFromIdentifier) {
		this.callFromIdentifier = callFromIdentifier;
	}

}
