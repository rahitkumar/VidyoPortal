package com.vidyo.bo;

import java.io.Serializable;

public class Browse implements Serializable {
    int roomID;
    int endpointID;
    int memberID;
    String name;
    String ext;
    int roomLocked;
    int roomPinned;
    int roomEnabled;    
    int memberStatus;
    int roomStatus;
    int speedDialID;
    int roomTypeID;
    String roomType;
    int roomOwner;
    int tenantID;
    String tenantName;
    String dialIn;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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
    
    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    public int getSpeedDialID() {
        return speedDialID;
    }

    public void setSpeedDialID(int speedDialID) {
        this.speedDialID = speedDialID;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(int roomOwner) {
        this.roomOwner = roomOwner;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
    }
}
