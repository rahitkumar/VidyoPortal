package com.vidyo.bo;

import java.io.Serializable;

public class Control implements Serializable {
    private Integer roomID = 0;
	private int endpointID;
	private String endpointGUID;
	private String endpointType;
	private String name;
	private String ext;
	private int video = 1;
	private int audio = 1;
	private int connect = 1;
	private String conferenceName;
	private String conferenceType;
	private String tenantName;
	private String dialIn;
	private int webcast = 0;
	private String vrID;
	private String vrName;
	private String groupID;
	private String groupName;
	private Integer presenter = 0; // can be null
	private Integer handRaised = 0; // can be null

	long numOfParticipants = 0l;

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        if (roomID == null) {
            roomID = 0;
        }
        this.roomID = roomID;
    }

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
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

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
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

    public int getWebcast() {
        return webcast;
    }

    public void setWebcast(int webcast) {
        this.webcast = webcast;
    }

    public String getVrID() {
        return vrID;
    }

    public void setVrID(String vrID) {
        this.vrID = vrID;
    }

    public String getVrName() {
        return vrName;
    }

    public void setVrName(String vrName) {
        this.vrName = vrName;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

	public long getNumOfParticipants() {
		return numOfParticipants;
	}

	public void setNumOfParticipants(long numOfParticipants) {
		this.numOfParticipants = numOfParticipants;
	}

	public Integer getPresenter() {
		if (presenter == null) {
			this.presenter = 0;
		}
		return presenter;
	}

	public void setPresenter(Integer presenter) {
		if (presenter == null) {
			this.presenter = 0;
		} else {
			this.presenter = presenter;
		}
	}

	public Integer getHandRaised() {
		if (handRaised == null) {
			this.handRaised = 0;
		}
		return handRaised;
	}

	public void setHandRaised(Integer handRaised) {
		if (handRaised == null) {
			this.handRaised = 0;
		} else {
			this.handRaised = handRaised;
		}
	}
}