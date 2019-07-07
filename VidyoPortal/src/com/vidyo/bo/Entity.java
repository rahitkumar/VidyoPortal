package com.vidyo.bo;

import java.io.Serializable;
import java.util.Date;

public class Entity implements Serializable {
    private int roomID;
	private int endpointID = 0;
	private int memberID;
	private String name;
	private String username;
	private String roomName;
	private String dialIn;
	private String ext;
	private String description;
	private int roomLocked;
	private int roomPinned;
	private int memberStatus = 0;
	private int roomStatus;
	private int roomEnabled;
	private int speedDialID = 0;
	private int roomTypeID;
	private String roomType;
	private int roomOwner = 0;
	private String roomKey;
	private String roomPIN;
	private String langCode;
	private String langName;
	private String endpointGUID;
	private int tenantID;
	private String tenantName;
	private int roomMaxUsers;
	private int userMaxBandWidthIn;
	private int userMaxBandWidthOut;
	private int video = 1;
	private int audio = 1;
	private int connect = 1;
	private int allowRecording;

	private String webCastURL;
	private String webCastPIN;
	private int webCastPinned;
	private int allowWebcasting = 0;
	private int modeID = 1;
	private String modeName;
	private String tenantUrl;

	private String conferenceType;

	private String roomModeratorPIN;
	private int roomModeratorPinned;

	private boolean canControlMeeting = false;

    private int webcast;
    
    private String emailAddress;
    
    private int langID;

	private Integer handRaised;
	private Date handRaisedTime;
	private Integer presenter;
	
	private String phone1;
	private String phone2;
	private String phone3;
	private String department;
	private String title;
	private String instantMessagerID;
	private String location;
	
	private String displayName;
	
	private Date thumbnailUpdateTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Entity)) {
			return false;
		}

		Entity entity = (Entity) o;

		return ext.equals(entity.ext) && roomName.equals(entity.roomName);
	}

	@Override
	public int hashCode() {
		int result = roomName.hashCode();
		result = 31 * result + ext.hashCode();
		return result;
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

	public int getParticipantID() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
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

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getRoomEnabled() {
        return roomEnabled;
    }

    public void setRoomEnabled(int roomEnabled) {
        this.roomEnabled = roomEnabled;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getRoomPIN() {
        return roomPIN;
    }

    public void setRoomPIN(String roomPIN) {
        this.roomPIN = roomPIN;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
    
    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
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

    public int getAllowRecording() {
        return allowRecording;
    }

    public void setAllowRecording(int allowRecording) {
        this.allowRecording = allowRecording;
    }

    public String getWebCastURL() {
        return webCastURL;
    }

    public void setWebCastURL(String webCastURL) {
        this.webCastURL = webCastURL;
    }

    public String getWebCastPIN() {
        return webCastPIN;
    }

    public void setWebCastPIN(String webCastPIN) {
        this.webCastPIN = webCastPIN;
    }

    public int getWebCastPinned() {
        return webCastPinned;
    }

    public void setWebCastPinned(int webCastPinned) {
        this.webCastPinned = webCastPinned;
    }

    public int getAllowWebcasting() {
        return allowWebcasting;
    }

    public void setAllowWebcasting(int allowWebcasting) {
        this.allowWebcasting = allowWebcasting;
    }

    public int getModeID() {
        return modeID;
    }

    public void setModeID(int modeID) {
        this.modeID = modeID;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getTenantUrl() {
        return tenantUrl;
    }

    public void setTenantUrl(String tenantUrl) {
        this.tenantUrl = tenantUrl;
    }

	/**
	 * @return the webcast
	 */
	public int getWebcast() {
		return webcast;
	}

	/**
	 * @param webcast the webcast to set
	 */
	public void setWebcast(int webcast) {
		this.webcast = webcast;
	}

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

	public String getRoomModeratorPIN() {
		return roomModeratorPIN;
	}

	public void setRoomModeratorPIN(String roomModeratorPIN) {
		this.roomModeratorPIN = roomModeratorPIN;
	}

	public int getRoomModeratorPinned() {
		return roomModeratorPinned;
	}

	public void setRoomModeratorPinned(int roomModeratorPinned) {
		this.roomModeratorPinned = roomModeratorPinned;
	}

	public boolean isCanControlMeeting() {
		return canControlMeeting;
	}

	public void setCanControlMeeting(boolean canControlMeeting) {
		this.canControlMeeting = canControlMeeting;
	}

	public Integer getHandRaised() {
		if (this.handRaised == null) {
			this.handRaised = 0;
		}
		return this.handRaised;
	}

	public void setHandRaised(Integer handRaised) {
		if (handRaised == null) {
			this.handRaised = 0;
		} else {
			this.handRaised = handRaised;
		}
	}

	public Date getHandRaisedTime() {
		return handRaisedTime;
	}

	public void setHandRaisedTime(Date handRaisedTime) {
		this.handRaisedTime = handRaisedTime;
	}

	public Integer getPresenter() {
		if (this.presenter == null) {
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
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getLangID() {
		return langID;
	}

	public void setLangID(int langID) {
		this.langID = langID;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstantMessagerID() {
		return instantMessagerID;
	}

	public void setInstantMessagerID(String instantMessagerID) {
		this.instantMessagerID = instantMessagerID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getThumbnailUpdateTime() {
		return thumbnailUpdateTime;
	}

	public void setThumbnailUpdateTime(Date thumbnailUpdateTime) {
		this.thumbnailUpdateTime = thumbnailUpdateTime;
	}	

	
	
}