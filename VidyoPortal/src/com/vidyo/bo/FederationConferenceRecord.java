package com.vidyo.bo;

import java.io.Serializable;

public class FederationConferenceRecord implements Serializable {
    String conferenceName;
    String conferenceType;
    int endpointID;
    String endpointGUID;
    String endpointType;
    String endpointCaller;
    String userNameAtTenant;
    String displayName;
    String extension;
    String dialIn;
    int audio;
    int video;

    String toTenantHost;

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEndpointCaller() {
        return endpointCaller;
    }

    public void setEndpointCaller(String endpointCaller) {
        this.endpointCaller = endpointCaller;
    }

    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
    }

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUserNameAtTenant() {
        return userNameAtTenant;
    }

    public void setUserNameAtTenant(String userNameAtTenant) {
        this.userNameAtTenant = userNameAtTenant;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public String getToTenantHost() {
        return toTenantHost;
    }

    public void setToTenantHost(String toTenantHost) {
        this.toTenantHost = toTenantHost;
    }
}