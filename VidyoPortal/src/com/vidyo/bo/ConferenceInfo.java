package com.vidyo.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ConferenceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String ConferenceID;
	String VRID;
	String VRName;
	String GroupID;
	String GroupName;

	public String getConferenceID() {
		return ConferenceID;
	}

	public void setConferenceID(String conferenceID) {
		ConferenceID = conferenceID;
	}

	public String getVRID() {
		return VRID;
	}

	public void setVRID(String VRID) {
		this.VRID = VRID;
	}

	public String getVRName() {
		return VRName;
	}

	public void setVRName(String VRName) {
		this.VRName = VRName;
	}

	public String getGroupID() {
		return GroupID;
	}

	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	/**
     * 
     */
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.toString();
	}
}