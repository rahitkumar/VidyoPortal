package com.vidyo.bo.profile;

import java.io.Serializable;

public class RoomProfile implements Serializable {
	int profileID;
	String profileName;
	String profileDescription;
	String profileXML;

	public int getProfileID() {
		return profileID;
	}

	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileDescription() {
		return profileDescription;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}

	public String getProfileXML() {
		return profileXML;
	}

	public void setProfileXML(String profileXML) {
		this.profileXML = profileXML;
	}
}