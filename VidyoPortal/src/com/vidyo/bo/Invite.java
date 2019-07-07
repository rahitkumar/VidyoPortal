package com.vidyo.bo;

import java.io.Serializable;

public class Invite implements Serializable {
    int fromMemberID;
    int fromRoomID;
    int toEndpointID;
    int toMemberID;
    String search;
    Object payload;
    private String callFromIdentifier;

    public int getFromMemberID() {
        return fromMemberID;
    }

    public void setFromMemberID(int fromMemberID) {
        this.fromMemberID = fromMemberID;
    }

    public int getFromRoomID() {
        return fromRoomID;
    }

    public void setFromRoomID(int fromRoomID) {
        this.fromRoomID = fromRoomID;
    }

    public int getToEndpointID() {
        return toEndpointID;
    }

    public void setToEndpointID(int toEndpointID) {
        this.toEndpointID = toEndpointID;
    }

    public int getToMemberID() {
        return toMemberID;
    }

    public void setToMemberID(int toMemberID) {
        this.toMemberID = toMemberID;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

	/**
	 * @return the callfromIdentifier
	 */
	public String getCallFromIdentifier() {
		return callFromIdentifier;
	}

	/**
	 * @param callfromIdentifier the callfromIdentifier to set
	 */
	public void setCallFromIdentifier(String callFromIdentifier) {
		this.callFromIdentifier = callFromIdentifier;
	}
}