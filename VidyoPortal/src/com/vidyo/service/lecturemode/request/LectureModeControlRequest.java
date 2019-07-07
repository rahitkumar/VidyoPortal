package com.vidyo.service.lecturemode.request;

public class  LectureModeControlRequest {

	private int memberID;
	private int tenantID;
	private int roomID;
	private String moderatorPIN;

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getModeratorPIN() {
		return moderatorPIN;
	}

	public void setModeratorPIN(String moderatorPIN) {
		this.moderatorPIN = moderatorPIN;
	}

}
