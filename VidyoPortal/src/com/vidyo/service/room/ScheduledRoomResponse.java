/**
 * 
 */
package com.vidyo.service.room;

import com.vidyo.bo.Room;
import com.vidyo.framework.service.BaseServiceResponse;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class ScheduledRoomResponse extends BaseServiceResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5682833305049048131L;
	
	/**
	 * 
	 */
	public static final int INVALID_PIN_EXTN = 4001;
	
	/**
	 * 
	 */
	public static final int INVALID_SCHEDULED_ROOM = 4002;
	
	/**
	 * 
	 */
	public static final int WRONG_SCHEDULED_ROOM_PIN = 4003;

	/**
	 * 
	 */
	private String roomExtn;
	
	/**
	 * 
	 */
	private long pin;
	
	/**
	 * 
	 */
	private Room room;
	
	/**
	 * 
	 */
	private String schRoomPrefix;

	/**
	 *
	 */
	private int memberId;

	/**
	 *
	 */
	private String memberName;

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * @return the memberId
	 */
	public int getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the room to set
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the room to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the roomExtn
	 */
	public String getRoomExtn() {
		return roomExtn;
	}

	/**
	 * @param roomExtn the roomExtn to set
	 */
	public void setRoomExtn(String roomExtn) {
		this.roomExtn = roomExtn;
	}

	/**
	 * @return the pin
	 */
	public long getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(long pin) {
		this.pin = pin;
	}

	/**
	 * @return the schRoomPrefix
	 */
	public String getSchRoomPrefix() {
		return schRoomPrefix;
	}

	/**
	 * @param schRoomPrefix the schRoomPrefix to set
	 */
	public void setSchRoomPrefix(String schRoomPrefix) {
		this.schRoomPrefix = schRoomPrefix;
	}

}
