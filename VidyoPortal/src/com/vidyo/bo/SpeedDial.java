package com.vidyo.bo;

import java.io.Serializable;

public class SpeedDial implements Serializable {
    String name;
    int roomID;
    int memberID;
    int endpointID;
    int roomTypeID;
    String roomType;
    int roomLocked;
    int roomPinned;
    int roomEnabled;
    String roomName;
    String roomExtNumber;
    String memberStatus;
    String roomStatus;
    String dialIn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
    
    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomExtNumber() {
        return roomExtNumber;
    }

    public void setRoomExtNumber(String roomExtNumber) {
        this.roomExtNumber = roomExtNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomLocked() {
        return roomLocked;
    }

    public void setRoomLocked(int roomLocked) {
        this.roomLocked = roomLocked;
    }

    public int getRoomPinned() {
        return roomPinned;
    }

    public void setRoomPinned(int roomPinned) {
        this.roomPinned = roomPinned;
    }

    public int getRoomEnabled() {
        return roomEnabled;
    }

    public void setRoomEnabled(int roomEnabled) {
        this.roomEnabled = roomEnabled;
    }
    
    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
    }
}
