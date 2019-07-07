package com.vidyo.bo.authentication;

import java.io.Serializable;

public class SamlRelayStateParam implements Serializable {

	private static final long serialVersionUID = 1700445004412715743L;

	private String mode;
	private String roomKey;
	private String directDial;

	/**
	 *
	 */
	private String token;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDirectDial() {
		return directDial;
	}

	public void setDirectDial(String directDial) {
		this.directDial = directDial;
	}
}