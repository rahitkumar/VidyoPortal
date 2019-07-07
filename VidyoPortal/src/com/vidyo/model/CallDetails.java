package com.vidyo.model;

public class CallDetails {

    private String name;
    private String roomKey;
    private String portal;

    //Default constructor.
    public CallDetails() {
    }

    public CallDetails(String name, String roomKey, String portal) {
        this.name = name;
        this.roomKey = roomKey;
        this.portal = portal;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }


    @Override
    public String toString() {
        return "CallDetails{" +
                "name='" + name + '\'' +
                ", roomKey='" + roomKey + '\'' +
                ", portal='" + portal + '\'' +
                '}';
    }
}
