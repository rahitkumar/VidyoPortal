package com.vidyo.bo;

import java.io.Serializable;

public class Guestconf implements Serializable {
    int groupID;
    int proxyID;
    int locationID;

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getProxyID() {
        return proxyID;
    }

    public void setProxyID(int proxyID) {
        this.proxyID = proxyID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}