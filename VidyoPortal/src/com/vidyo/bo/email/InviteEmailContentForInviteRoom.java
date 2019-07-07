package com.vidyo.bo.email;

import java.util.Locale;

import com.vidyo.bo.Room;

public class InviteEmailContentForInviteRoom {
	private Room room;
	private String tenantDialIn;
	private boolean guestsAllowed;
	private String tenantUrl;
	private String gateWayDns;
	private Locale locale;
	private boolean overrideScheduledRoomProperties;
	private String transportName;
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getTenantDialIn() {
		return tenantDialIn;
	}
	public void setTenantDialIn(String tenantDialIn) {
		this.tenantDialIn = tenantDialIn;
	}
	public boolean isGuestsAllowed() {
		return guestsAllowed;
	}
	public void setGuestsAllowed(boolean guestsAllowed) {
		this.guestsAllowed = guestsAllowed;
	}
	public String getTenantUrl() {
		return tenantUrl;
	}
	public void setTenantUrl(String tenantUrl) {
		this.tenantUrl = tenantUrl;
	}
	public String getGateWayDns() {
		return gateWayDns;
	}
	public void setGateWayDns(String gateWayDns) {
		this.gateWayDns = gateWayDns;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public boolean isOverrideScheduledRoomProperties() {
		return overrideScheduledRoomProperties;
	}
	public void setOverrideScheduledRoomProperties(boolean overrideScheduledRoomProperties) {
		this.overrideScheduledRoomProperties = overrideScheduledRoomProperties;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	
}
