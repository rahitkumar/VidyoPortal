package com.vidyo.bo;

import java.io.Serializable;

public class Group implements Serializable {
    int groupID;
    int tenantID;
    int routerID;
    int secondaryRouterID;
    String groupName;
    String groupDescription;
    int defaultFlag;
    int roomMaxUsers;
    int userMaxBandWidthIn;
    int userMaxBandWidthOut;
    String routerName;
    int routerActive;
    String secondaryRouterName;
    int secondaryRouterActive;
    int allowRecording;

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getRouterID() {
        return routerID;
    }

    public void setRouterID(int routerID) {
        this.routerID = routerID;
    }

    public int getSecondaryRouterID() {
        return secondaryRouterID;
    }

    public void setSecondaryRouterID(int secondaryRouterID) {
        this.secondaryRouterID = secondaryRouterID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public int getRoomMaxUsers() {
        return roomMaxUsers;
    }

    public void setRoomMaxUsers(int roomMaxUsers) {
        this.roomMaxUsers = roomMaxUsers;
    }

    public int getUserMaxBandWidthIn() {
        return userMaxBandWidthIn;
    }

    public void setUserMaxBandWidthIn(int userMaxBandWidthIn) {
        this.userMaxBandWidthIn = userMaxBandWidthIn;
    }

    public int getUserMaxBandWidthOut() {
        return userMaxBandWidthOut;
    }

    public void setUserMaxBandWidthOut(int userMaxBandWidthOut) {
        this.userMaxBandWidthOut = userMaxBandWidthOut;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public int getRouterActive() {
        return routerActive;
    }

    public void setRouterActive(int routerActive) {
        this.routerActive = routerActive;
    }

    public String getSecondaryRouterName() {
        return secondaryRouterName;
    }

    public void setSecondaryRouterName(String secondaryRouterName) {
        this.secondaryRouterName = secondaryRouterName;
    }

    public int getSecondaryRouterActive() {
        return secondaryRouterActive;
    }

    public void setSecondaryRouterActive(int secondaryRouterActive) {
        this.secondaryRouterActive = secondaryRouterActive;
    }

    public int getAllowRecording() {
        return allowRecording;
    }

    public void setAllowRecording(int allowRecording) {
        this.allowRecording = allowRecording;
    }
}
