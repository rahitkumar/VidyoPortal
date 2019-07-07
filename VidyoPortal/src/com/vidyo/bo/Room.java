package com.vidyo.bo;

import com.vidyo.bo.usergroup.UserGroup;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "Room")
public class Room implements Serializable {

    @Id
    private int roomID;
    private int roomTypeID;
    @JoinColumn(insertable = false,updatable = false)
    private int memberID;
    @Transient
    private String ownerName; // from Member table
    private int groupID;
    @Transient
    private String groupName; // from Groups table
    private String roomName;
    private String roomExtNumber;
    private String roomDescription;
    private String externalRoomID;
    @Transient
    private String roomType;
    private int roomEnabled;
    private int roomMuted;
    private int roomLocked;
    @Transient
    private String pinSetting;
    String roomPIN;
    @Transient
    private int roomPinned;
    private String roomKey;
    @Transient
    private int tenantID;
    @Transient
    private String roomURL;
    @Transient
    private String tenantPrefix;
    @Transient
    private String tenantName;
    private String displayName; // now means room display name, used to mean member displayName
    @Transient
    private String ownerDisplayName; // introduce new variable for member displayname
    @Transient
    private int numParticipants;
    @Transient
    private int roomStatus;
    @Transient
    private String dialIn;

    @Transient
    private int allowRecording;
    @Transient
    private String webCastURL;
    @Transient
    private String webCastPIN;
    @Transient
    private int webCastPinned;
    @Transient
    private int allowWebcasting;

    private int roomSilenced;
    @Transient
    private int replayErrorCode;

    private int profileID;
    @Transient
    private String moderatorPinSetting;
    private String roomModeratorPIN;
    @Transient
    private int roomModeratorPinned;
    private String roomModeratorKey;
    @Transient
    private int importedUsed;
    @Transient
    private int roomMaxUsers;

    @JoinColumn(name = "memberID", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    /**
     *
     */
    @Transient
    private int userMaxBandWidthIn;

    /**
     *
     */
    @Transient
    private int userMaxBandWidthOut;

    /**
     * Scheduled Room Recurring meeting flag
     */
    private int recurring;

    /**
     *
     */
    @Transient
    private String schRoomConfName;

    private int roomVideoMuted = 0;
    private int roomVideoSilenced = 0;
    @Transient
    private int occupantsCount;

    private Integer lectureMode = 0; // can be null

    private Date deleteon;

    /**
     *
     */
    private int roomSpeakerMuted;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.EAGER)
    private Set<UserGroup> userGroups;


    /**
     * @return the schRoomConfName
     */
    public String getSchRoomConfName() {
        return schRoomConfName;
    }

    /**
     * @param schRoomConfName the schRoomConfName to set
     */
    public void setSchRoomConfName(String schRoomConfName) {
        this.schRoomConfName = schRoomConfName;
    }

    /**
     * @return the recurring
     */
    public int getRecurring() {
        return recurring;
    }

    /**
     * @param recurring the recurring to set
     */
    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }

    /**
     * @return the roomMaxUsers
     */
    public int getRoomMaxUsers() {
        return roomMaxUsers;
    }

    /**
     * @param roomMaxUsers the roomMaxUsers to set
     */
    public void setRoomMaxUsers(int roomMaxUsers) {
        this.roomMaxUsers = roomMaxUsers;
    }

    public int getReplayErrorCode() {
        return replayErrorCode;
    }

    public void setReplayErrorCode(int replayErrorCode) {
        this.replayErrorCode = replayErrorCode;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }


    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomEnabled() {
        return roomEnabled;
    }

    public void setRoomEnabled(int roomEnabled) {
        this.roomEnabled = roomEnabled;
    }

    public int getRoomLocked() {
        return roomLocked;
    }

    public void setRoomLocked(int roomLocked) {
        this.roomLocked = roomLocked;
    }

    public int getRoomMuted() {
        return roomMuted;
    }

    public void setRoomMuted(int roomMuted) {
        this.roomMuted = roomMuted;
    }

    public String getPinSetting() {
        return pinSetting;
    }

    public void setPinSetting(String pinSetting) {
        this.pinSetting = pinSetting;
    }

    public String getRoomPIN() {
        return roomPIN;
    }

    public void setRoomPIN(String roomPIN) {
        this.roomPIN = roomPIN;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getRoomURL() {
        return roomURL;
    }

    public void setRoomURL(String roomURL) {
        this.roomURL = roomURL;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getRoomPinned() {
        return roomPinned;
    }

    public void setRoomPinned(int roomPinned) {
        this.roomPinned = roomPinned;
    }

    public String getTenantPrefix() {
        return tenantPrefix;
    }

    public void setTenantPrefix(String tenantPrefix) {
        this.tenantPrefix = tenantPrefix;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getDialIn() {
        return dialIn;
    }

    public void setDialIn(String dialIn) {
        this.dialIn = dialIn;
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

    public int getRoomSilenced() {
        return roomSilenced;
    }

    public void setRoomSilenced(int roomSilenced) {
        this.roomSilenced = roomSilenced;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

	public String getExternalRoomID() {
		return externalRoomID;
	}

	public void setExternalRoomID(String externalRoomID) {
		this.externalRoomID = externalRoomID;
	}

	public String getModeratorPinSetting() {
        return moderatorPinSetting;
    }

    public void setModeratorPinSetting(String moderatorPinSetting) {
        this.moderatorPinSetting = moderatorPinSetting;
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

    /**
     * @return the userMaxBandWidthIn
     */
    public int getUserMaxBandWidthIn() {
        return userMaxBandWidthIn;
    }

    /**
     * @param userMaxBandWidthIn the userMaxBandWidthIn to set
     */
    public void setUserMaxBandWidthIn(int userMaxBandWidthIn) {
        this.userMaxBandWidthIn = userMaxBandWidthIn;
    }

    /**
     * @return the userMaxBandWidthOut
     */
    public int getUserMaxBandWidthOut() {
        return userMaxBandWidthOut;
    }

    /**
     * @param userMaxBandWidthOut the userMaxBandWidthOut to set
     */
    public void setUserMaxBandWidthOut(int userMaxBandWidthOut) {
        this.userMaxBandWidthOut = userMaxBandWidthOut;
    }

    public int getImportedUsed() {
        return importedUsed;
    }

    public void setImportedUsed(int importedUsed) {
        this.importedUsed = importedUsed;
    }

    public String getRoomModeratorKey() {
        return this.roomModeratorKey;
    }

    public void setRoomModeratorKey(String key) {
        this.roomModeratorKey = key;
    }

    public int getRoomVideoMuted() {
        return roomVideoMuted;
    }

    public void setRoomVideoMuted(int roomVideoMuted) {
        this.roomVideoMuted = roomVideoMuted;
    }

    public int getRoomVideoSilenced() {
        return roomVideoSilenced;
    }

    public void setRoomVideoSilenced(int roomVideoSilenced) {
        this.roomVideoSilenced = roomVideoSilenced;
    }

    /**
     * @return the occupantsCount
     */
    public int getOccupantsCount() {
        return occupantsCount;
    }

    /**
     * @param occupantsCount the occupantsCount to set
     */
    public void setOccupantsCount(int occupantsCount) {
        this.occupantsCount = occupantsCount;
    }


    public Integer getLectureMode() {
        if (lectureMode == null) {
            this.lectureMode = 0;
        }
        return lectureMode;
    }

    public void setLectureMode(Integer lectureMode) {
        if (lectureMode == null) {
            this.lectureMode = 0;
        } else {
            this.lectureMode = lectureMode;
        }
    }

    public Date getDeleteon() {
        return deleteon;
    }

    public void setDeleteon(Date deleteon) {
        this.deleteon = deleteon;
    }

    public int getRoomSpeakerMuted() {
        return roomSpeakerMuted;
    }

    public void setRoomSpeakerMuted(int roomSpeakerMuted) {
        this.roomSpeakerMuted = roomSpeakerMuted;
    }

    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroup) { this.userGroups = userGroup; }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(roomID).toHashCode();
    }

    /**
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            final Room otherMember = (Room) obj;
            return new EqualsBuilder().append(roomID, otherMember.roomID).isEquals();
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}