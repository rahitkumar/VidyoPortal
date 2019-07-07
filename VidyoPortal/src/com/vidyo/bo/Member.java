package com.vidyo.bo;

import com.vidyo.bo.usergroup.UserGroup;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * This Entity class is being reftrofitted to work with JPA Repository.
 * Note - Since the Member Table contains nullable columns for integer tyoes, the following properties have been made transient
 *
 * 	roleID
 * 	profileID
 * 	langID
 * 	memberCreated
 * 	proxyID
 * 	locationID
 * 	modeID
 *
 * Care whould be taken when performing the operations like save,fetch on this Entity.
 */
@Entity
@Table(name="Member")
public class Member implements Serializable {

	@Id
	private int memberID;
	@Transient
	private int roleID;
	@Transient
	private String roleName; // from MemberRole table
	private int tenantID;
	@Transient
	private int groupID;
	@Transient
	private String groupName; // from Groups table
	@Transient
	private int langID;
	@Transient
	private String langCode; // from Language table
	@Transient
	private int profileID;
	private String username;
	private String password; // SHA1 encoded
	private String memberName;
	private int active;
	private int allowedToParticipate;
	private String emailAddress;
	@Transient
	private int memberCreated;
	private String location;
	private String description;
	private boolean neoRoomPermanentPairingDeviceUser = false;

	// info about Personal Room
	@Transient
	private int roomID;
	@Transient
	private int roomTypeID;
	@Transient
	private String roomType;
	@Transient
	private String roomName;
	@Transient
	private String roomExtNumber;
	// info about Personal Room
	@Transient
	private String endpointGUID;
	private String defaultEndpointGUID;
	@Transient
	private String tenantPrefix;
	@Transient
	private String tenantName;
	@Transient
	private int proxyID;
	@Transient
	private String proxyName; // from Service table
	@Transient
	private String dialIn;
	@Transient
	private int locationID = 1;
	@Transient
	private String locationTag; // from Locations table
	@Transient
	private int modeID;

	@Transient
	private byte[] thumbNailImage;

	@Transient
	private int endpointId;
	private int importedUsed;

	@Column(name = "bak_creation_time")
	private Date bakCreationTime;

	private int loginFailureCount;

	private Date creationTime;
	@Transient
	private Date modificationTime;
	private String phone1;
	private String phone2;
	private String phone3;
	private String department;
	private String title;
	private String instantMessagerID;

	private Date thumbnailUpdateTime;

	//only for ldap and saml
	private String lastModifiedDateExternal;

	@ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
	private Set<UserGroup> userGroups;

	@Transient
	private boolean userGroupsUpdated;

	@Transient
	private Set<String> userGroupsFromAuthProvider;
	
	/**
	 * Customized from identifier - for legacy calls
	 */
	@Transient
	private String callFromIdentifier;

	public String getLastModifiedDateExternal() {
		return lastModifiedDateExternal;
	}

	public void setLastModifiedDateExternal(String lastModifiedDateExternal) {
		this.lastModifiedDateExternal = lastModifiedDateExternal;
	}

	private boolean userImageUploaded ;

	//1 is the default value ,as userImageUpload is allowed by default.Admin can disable it later
	private int userImageAllowed=1;
	
	/**
	 * External Data set in the Endpoints table
	 */
	@Transient
	private String extData;

	/**
	 * @return the extData
	 */
	public String getExtData() {
		return extData;
	}

	/**
	 * @param extData the extData to set
	 */
	public void setExtData(String extData) {
		this.extData = extData;
	}

	public int getUserImageAllowed() {
		return userImageAllowed;
	}

	public void setUserImageAllowed(int userImageAllowed) {
		this.userImageAllowed = userImageAllowed;
	}

	public boolean isUserImageUploaded() {
		return userImageUploaded;
	}

	public void setUserImageUploaded(boolean userImageUploaded) {
		this.userImageUploaded = userImageUploaded;
	}

	public String getPhone1() {
		if(StringUtils.isNotBlank(phone1) && "=-+@".indexOf(phone1.charAt(0)) >= 0 ) {
			phone1 = "'" + phone1;
		}
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		if(StringUtils.isNotBlank(phone2) && "=-+@".indexOf(phone2.charAt(0)) >= 0 ) {
			phone2 = "'" + phone2;
		}
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		if(StringUtils.isNotBlank(phone3) && "=-+@".indexOf(phone3.charAt(0)) >= 0 ) {
			phone3 = "'" + phone3;
		}
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getDepartment() {
		if(StringUtils.isNotBlank(department) && "=-+@".indexOf(department.charAt(0)) >= 0 ) {
			department = "'" + department;
		}
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		if(StringUtils.isNotBlank(title) && "=-+@".indexOf(title.charAt(0)) >= 0 ) {
			title = "'" + title;
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstantMessagerID() {
		if(StringUtils.isNotBlank(instantMessagerID) && "=-+@".indexOf(instantMessagerID.charAt(0)) >= 0 ) {
			instantMessagerID = "'" + instantMessagerID;
		}
		return instantMessagerID;
	}

	public void setInstantMessagerID(String instantMessagerID) {
		this.instantMessagerID = instantMessagerID;
	}



	/**
	 * @return the bakCreationTime
	 */
	public Date getBakCreationTime() {
		return bakCreationTime;
	}

	/**
	 * @param bakCreationTime
	 *            the bakCreationTime to set
	 */
	public void setBakCreationTime(Date bakCreationTime) {
		this.bakCreationTime = bakCreationTime;
	}

	/**
	 * @return the endpointId
	 */
	public int getEndpointId() {
		return endpointId;
	}

	/**
	 * @param endpointId
	 *            the endpointId to set
	 */
	public void setEndpointId(int endpointId) {
		this.endpointId = endpointId;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		if(StringUtils.isNotBlank(roleName) && "=-+@".indexOf(roleName.charAt(0)) >= 0 ) {
			roleName = "'" + roleName;
		}
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		if(StringUtils.isNotBlank(groupName) && "=-+@".indexOf(groupName.charAt(0)) >= 0 ) {
			groupName = "'" + groupName;
		}
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getLangID() {
		return langID;
	}

	public void setLangID(int langID) {
		this.langID = langID;
	}

	public String getLangCode() {
		if(StringUtils.isNotBlank(langCode) && "=-+@".indexOf(langCode.charAt(0)) >= 0 ) {
			langCode = "'" + langCode;
		}
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public int getProfileID() {
		return profileID;
	}

	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}

	public String getUsername() {
		if(StringUtils.isNotBlank(username) && "=-+@".indexOf(username.charAt(0)) >= 0 ) {
			username = "'" + username;
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if(StringUtils.isNotBlank(password) && "=-+@".indexOf(password.charAt(0)) >= 0 ) {
			password = "'" + password;
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberName() {
		if(StringUtils.isNotBlank(memberName) && "=-+@".indexOf(memberName.charAt(0)) >= 0 ) {
			memberName = "'" + memberName;
		}
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getAllowedToParticipate() {
		return allowedToParticipate;
	}

	public void setAllowedToParticipate(int allowedToParticipate) {
		this.allowedToParticipate = allowedToParticipate;
	}

	public byte[] getThumbNailImage() {
		return thumbNailImage;
	}

	public void setThumbNailImage(byte[] thumbNailImage) {
		this.thumbNailImage = thumbNailImage;
	}


	public String getEnable() {
		if (this.getActive() == 1) {
			return "on";
		} else {
			return null;
		}
	}

	public void setEnable(String enable) {
		if (enable != null) {
			if (enable.equalsIgnoreCase("on")) {
				this.setActive(1);
			} else {
				this.setActive(0);
			}
		} else {
			this.setActive(0);
		}
	}

	public String getAllowedToParticipateHtml() {
		if (this.getAllowedToParticipate() == 1) {
			return "on";
		} else {
			return null;
		}
	}

	public void setAllowedToParticipateHtml(String allowedToParticipate) {
		if (allowedToParticipate != null) {
			if (allowedToParticipate.equalsIgnoreCase("on")) {
				this.setAllowedToParticipate(1);
			} else {
				this.setAllowedToParticipate(0);
			}
		} else {
			this.setAllowedToParticipate(0);
		}
	}

	public String getEmailAddress() {
		if(StringUtils.isNotBlank(emailAddress) && "=-+@".indexOf(emailAddress.charAt(0)) >= 0 ) {
			emailAddress = "'" + emailAddress;
		}
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getMemberCreated() {
		return memberCreated;
	}

	public void setMemberCreated(int memberCreated) {
		this.memberCreated = memberCreated;
	}

	public String getLocation() {
		if(StringUtils.isNotBlank(location) && "=-+@".indexOf(location.charAt(0)) >= 0 ) {
			location = "'" + location;
		}
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		if(StringUtils.isNotBlank(description)&& "=-+@".indexOf(description.charAt(0)) >= 0 ) {
			description = "'" + description;
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNeoRoomPermanentPairingDeviceUser() {
		return neoRoomPermanentPairingDeviceUser;
	}

	public void setNeoRoomPermanentPairingDeviceUser(boolean neoRoomPermanentPairingDeviceUser) {
		this.neoRoomPermanentPairingDeviceUser = neoRoomPermanentPairingDeviceUser;
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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomExtNumber() {
		if(StringUtils.isNotBlank(roomExtNumber) && "=-+@".indexOf(roomExtNumber.charAt(0)) >= 0 ) {
			roomExtNumber = "'" + roomExtNumber;
		}
		return roomExtNumber;
	}

	public void setRoomExtNumber(String roomExtNumber) {
		this.roomExtNumber = roomExtNumber;
	}

	public String getEndpointGUID() {
		return endpointGUID;
	}

	public void setEndpointGUID(String endpointGUID) {
		this.endpointGUID = endpointGUID;
	}

	public String getDefaultEndpointGUID() {
		return defaultEndpointGUID;
	}

	public void setDefaultEndpointGUID(String defaultEndpointGUID) {
		this.defaultEndpointGUID = defaultEndpointGUID;
	}

	public boolean isHomemachine() {
		return this.defaultEndpointGUID != null && this.endpointGUID != null
				&& this.defaultEndpointGUID.equalsIgnoreCase(this.endpointGUID);
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

	public int getProxyID() {
		return proxyID;
	}

	public void setProxyID(int proxyID) {
		this.proxyID = proxyID;
	}

	public String getProxyName() {
		if(StringUtils.isNotBlank(proxyName) && "=-+@".indexOf(proxyName.charAt(0)) >= 0 ) {
			proxyName = "'" + proxyName;
		}
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getDialIn() {
		return dialIn;
	}

	public void setDialIn(String dialIn) {
		this.dialIn = dialIn;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getLocationTag() {
		if(StringUtils.isNotBlank(locationTag) && "=-+@".indexOf(locationTag.charAt(0)) >= 0 ) {
			locationTag = "'" + locationTag;
		}
		return locationTag;
	}

	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	public int getModeID() {
		return modeID;
	}

	public void setModeID(int modeID) {
		this.modeID = modeID;
	}

	public int getImportedUsed() {
		return importedUsed;
	}

	public void setImportedUsed(int importedUsed) {
		this.importedUsed = importedUsed;
	}

	public int getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Set<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroup) { this.userGroups = userGroup; }


	/*
	 * Export Users into .CSV file - password not included String userType =
	 * reader.get(0); //reader.get("UserType"); String username = reader.get(1);
	 * // reader.get("Username"); String password = reader.get(2); //
	 * reader.get("Password"); String name = reader.get(3); //
	 * reader.get("Fullname"); String email = reader.get(4); //
	 * reader.get("Email"); String ext = reader.get(5); //
	 * reader.get("Extension"); String groupName = reader.get(6); //
	 * reader.get("Group"); String lang = reader.get(7); //
	 * reader.get("Language"); String desc = reader.get(8); //
	 * reader.get("Description"); String proxyName = reader.get(9); //
	 * reader.get("Proxy"); String locationTag = reader.get(10); //
	 * reader.get("LocationTag");
	 */
	@Override
	public String toString() {
		return getRoleName() + "," + getUsername() + "," + "" // getPassword()
				+ "," + getMemberName() + "," + getEmailAddress() + "," + getRoomExtNumber() + "," + getGroupName()
				+ "," + getLangCode() + "," + getDescription() + "," + getProxyName() + "," + getLocationTag() + "\n";
	}

	/*
	 * Export Users into .VIDYO file - password included String userType =
	 * reader.get(0); //reader.get("UserType"); String username = reader.get(1);
	 * // reader.get("Username"); String password = reader.get(2); //
	 * reader.get("Password"); String name = reader.get(3); //
	 * reader.get("Fullname"); String email = reader.get(4); //
	 * reader.get("Email"); String ext = reader.get(5); //
	 * reader.get("Extension"); String groupName = reader.get(6); //
	 * reader.get("Group"); String lang = reader.get(7); //
	 * reader.get("Language"); String desc = reader.get(8); //
	 * reader.get("Description"); String proxyName = reader.get(9); //
	 * reader.get("Proxy"); String locationTag = reader.get(10); //
	 * reader.get("LocationTag");
	 */
	public String toVidyoString() {
		return getRoleName() + "," + getUsername() + "," + getPassword() + "," + getMemberName() + ","
				+ getEmailAddress() + "," + getRoomExtNumber() + "," + getGroupName() + "," + getLangCode() + ","
				+ getDescription() + "," + getProxyName() + "," + getLocationTag() + "," + getPhone1() + ","
				+ getPhone2() + "," + getPhone3() + "," + getDepartment() + "," + getTitle() + ","
				+ getInstantMessagerID() + "," + getLocation() + "\n";
	}

	public String[] toArray() {
		return new String[] { getRoleName(), getUsername(), getPassword(), getMemberName(), getEmailAddress(),
				getRoomExtNumber(), getGroupName(), getLangCode(), getDescription(), getProxyName(), getLocationTag(),
				getPhone1(), getPhone2(), getPhone3(), getDepartment(), getTitle(), getInstantMessagerID(), getLocation()};
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}

	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public Date getThumbnailUpdateTime() {
		return thumbnailUpdateTime;
	}

	public void setThumbnailUpdateTime(Date thumbnailUpdateTime) {
		this.thumbnailUpdateTime = thumbnailUpdateTime;
	}

	public boolean isUserGroupsUpdated() {
		return userGroupsUpdated;
	}

	public void setUserGroupsUpdated(boolean userGroupsUpdated) {
		this.userGroupsUpdated = userGroupsUpdated;
	}

	public Set<String> getUserGroupsFromAuthProvider() {
		return userGroupsFromAuthProvider;
	}

	public void setUserGroupsFromAuthProvider(Set<String> userGroupsFromAuthProvider) {
		this.userGroupsFromAuthProvider = userGroupsFromAuthProvider;
	}


	/**
	 *
    */
	@Override
   public int hashCode() {
	  return new HashCodeBuilder().append(memberID).toHashCode();
	}

    /**
    *
    */
	@Override
    public boolean equals(Object obj) {
		if (obj instanceof Member) {
			final Member otherMember = (Member) obj;
			return new EqualsBuilder().append(memberID, otherMember.memberID).isEquals();
		} else {
			return false;
		}
	}

	/**
	 * @return the callFromIdentifier
	 */
	public String getCallFromIdentifier() {
		return callFromIdentifier;
	}

	/**
	 * @param callFromIdentifier the callFromIdentifier to set
	 */
	public void setCallFromIdentifier(String callFromIdentifier) {
		this.callFromIdentifier = callFromIdentifier;
	}

}