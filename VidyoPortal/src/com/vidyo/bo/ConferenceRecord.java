package com.vidyo.bo;

import java.io.Serializable;
import java.util.Date;

public class ConferenceRecord implements Serializable {
    private String GUID;
    /**
     * @return the gUID
     */
    public String getGUID() {
        return GUID;
    }

    /**
     * @param gUID the gUID to set
     */
    public void setGUID(String gUID) {
        GUID = gUID;
    }

    private String UniqueCallID;
    private String conferenceName;
    private String conferenceType;
    private int roomID;
    private int endpointID;
    private String endpointType;
    private String endpointCaller;
    private int tenantID;
    private int audio;
    private int video;
    private int speaker;

    private Date createdTime;
    private Date modifiedTime;

    public String getUniqueCallID() {
        return UniqueCallID;
    }

    public void setUniqueCallID(String uniqueCallID) {
        UniqueCallID = uniqueCallID;
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

    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
    }

    public String getEndpointCaller() {
        return endpointCaller;
    }

    public void setEndpointCaller(String endpointCaller) {
        this.endpointCaller = endpointCaller;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getSpeaker() {
        return speaker;
    }

    public void setSpeaker(int speaker) {
        this.speaker = speaker;
    }
}