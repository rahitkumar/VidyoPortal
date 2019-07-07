package com.vidyo.bo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class CDRinfo2 implements Serializable {

	private String UniqueCallID;
	private String ConferenceName;
	private String TenantName;
	private String ConferenceType;
	private String EndpointType;
	private String CallerID;
	private String CallerName;
	private String CallState;
	private String Direction;
	private String RouterID;
	private String GWID;
	private String GWPrefix;
	// CDRv3
	private String referenceNumber;
	private String applicationName;
	private String applicationVersion;
	private String deviceModel;
	private String endpointPublicIPAddress = "";
	private String accessType = "";
	private String roomType = "";
	private String roomOwner = "";
	private String applicationOs;
	private String callCompletionCode = "0";
	private String extension;
	private String endpointGUID = "";
	private int participantId;
	private int roomID;
	private int audioState;
	private int videoState;
	
	/**
	 * Data coming in from external integration
	 */
	private String extData;
	
	/**
	 * External Integration Type
	 * 1 -> EPIC
	 */
	private int extDataType;


	/**
	 * @return the audioState
	 */
	public int getAudioState() {
		return audioState;
	}

	/**
	 * @param audioState the audioState to set
	 */
	public void setAudioState(int audioState) {
		this.audioState = audioState;
	}

	/**
	 * @return the videoState
	 */
	public int getVideoState() {
		return videoState;
	}

	/**
	 * @param videoState the videoState to set
	 */
	public void setVideoState(int videoState) {
		this.videoState = videoState;
	}

	/**
	 * @return the roomID
	 */
	public int getRoomID() {
		return roomID;
	}

	/**
	 * @param roomID the roomID to set
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return the participantId
	 */
	public int getParticipantId() {
		return participantId;
	}

	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

	public String getCallerID() {
		return CallerID;
	}

	public void setCallerID(String callerID) {
		CallerID = callerID;
	}

	public String getCallerName() {
		return CallerName;
	}

	public void setCallerName(String callerName) {
		CallerName = callerName;
	}

	public String getCallState() {
		return CallState;
	}

	public void setCallState(String callState) {
		CallState = callState;
	}

	public String getConferenceName() {
		return ConferenceName;
	}

	public void setConferenceName(String conferenceName) {
		ConferenceName = conferenceName;
	}

	public String getConferenceType() {
		return ConferenceType;
	}

	public void setConferenceType(String conferenceType) {
		ConferenceType = conferenceType;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getEndpointType() {
		return EndpointType;
	}

	public void setEndpointType(String endpointType) {
		EndpointType = endpointType;
	}

	public String getGWID() {
		return GWID;
	}

	public void setGWID(String GWID) {
		this.GWID = GWID;
	}

	public String getGWPrefix() {
		return GWPrefix;
	}

	public void setGWPrefix(String GWPrefix) {
		this.GWPrefix = GWPrefix;
	}

	public String getRouterID() {
		return RouterID;
	}

	public void setRouterID(String routerID) {
		RouterID = routerID;
	}

	public String getTenantName() {
		return TenantName;
	}

	public void setTenantName(String tenantName) {
		TenantName = tenantName;
	}

	public String getUniqueCallID() {
		return UniqueCallID;
	}

	public void setUniqueCallID(String uniqueCallID) {
		UniqueCallID = uniqueCallID;
	}

	// CDRv3 fields

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getEndpointPublicIPAddress() {
		return endpointPublicIPAddress;
	}

	public void setEndpointPublicIPAddress(String endpointPublicIPAddress) {
		this.endpointPublicIPAddress = StringUtils.trimToEmpty(endpointPublicIPAddress); // not null
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = StringUtils.trimToEmpty(accessType); // not null
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = StringUtils.trimToEmpty(roomType); // not null
	}

	public String getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(String roomOwner) {
		this.roomOwner = StringUtils.trimToEmpty(roomOwner); // not null
	}

	public String getApplicationOs() {
		return applicationOs;
	}

	public void setApplicationOs(String applicationOs) {
		this.applicationOs = applicationOs;
	}

	public String getCallCompletionCode() {
		return callCompletionCode;
	}

	public void setCallCompletionCode(String callCompletionCode) {
		this.callCompletionCode = StringUtils.trimToEmpty(callCompletionCode); // not null
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String ext) {
		this.extension = ext;
	}

	public String getEndpointGUID() {
		return endpointGUID;
	}

	public void setEndpointGUID(String endpointGUID) {
		this.endpointGUID = StringUtils.trimToEmpty(endpointGUID); // not null;
	}

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

	/**
	 * @return the extDataType
	 */
	public int getExtDataType() {
		return extDataType;
	}

	/**
	 * @param extDataType the extDataType to set
	 */
	public void setExtDataType(int extDataType) {
		this.extDataType = extDataType;
	}

}