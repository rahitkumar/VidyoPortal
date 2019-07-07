package com.vidyo.service.lecturemode.request;


public class GuestHandUpdateRequest extends HandUpdateRequest {
	private int guestID;
	private String username;

	public int getGuestID() {
		return guestID;
	}

	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
