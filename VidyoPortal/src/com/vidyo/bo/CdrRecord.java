package com.vidyo.bo;

public class CdrRecord extends CDRinfo2 {
	private String CallID;
	private String JoinTime;
	private String LeaveTime;

	public static final String[] RECORD_HEADER =  new String[] {
				"CallID",
				"UniqueCallID",
				"ConferenceName",
				"TenantName",
				"ConferenceType",
				"EndpointType",
				"CallerID",
				"CallerName",
				"JoinTime",
				"LeaveTime",
				"CallState",
				"Direction",
				"RouterID",
				"GWID",
				"GWPrefix",
				"ReferenceNumber",
				"ApplicationName",
				"ApplicationVersion",
				"ApplicationOs",
				"DeviceModel",
				"EndpointPublicIPAddress",
				"AccessType",
			    "RoomType",
			    "RoomOwner",
				"CallCompletionCode",
				"Extension",
				"EndpointGUID"
	};

	@Override
	public String toString() {
		return getCallID()
				+ "," + getUniqueCallID()
				+ "," + getConferenceName()
				+ "," + getTenantName()
				+ "," + getConferenceType()
				+ "," + getEndpointType()
				+ "," + getCallerID()
				+ "," + getCallerName()
				+ "," + getJoinTime()
				+ "," + getLeaveTime()
				+ "," + getCallState()
				+ "," + getDirection()
				+ "," + getRouterID()
				+ "," + getGWID()
				+ "," + getGWPrefix()
				+ "," + getReferenceNumber()
				+ "," + getApplicationName()
				+ "," + getApplicationVersion()
				+ "," + getApplicationOs()
				+ "," + getDeviceModel()
				+ "," + getEndpointPublicIPAddress()
				+ "," + getAccessType()
				+ "," + getRoomType()
				+ "," + getRoomOwner()
				+ "," + getCallCompletionCode()
				+ "," + getExtension()
				+ "," + getEndpointGUID()
				+ "\n";
	}

	public String[] toArray() {
		return new String[] {
				getCallID(),
				getUniqueCallID(),
				csvCleanse(getConferenceName()),
				getTenantName(),
				getConferenceType(),
				getEndpointType(),
				csvCleanse(getCallerID()),
				csvCleanse(getCallerName()),
				getJoinTime(),
				getLeaveTime(),
				getCallState(),
				getDirection(),
				getRouterID(),
				csvCleanse(getGWID()),
				csvCleanse(getGWPrefix()),
				csvCleanse(getReferenceNumber()),
				csvCleanse(getApplicationName()),
				csvCleanse(getApplicationVersion()),
				csvCleanse(getApplicationOs()),
				csvCleanse(getDeviceModel()),
				csvCleanse(getEndpointPublicIPAddress()),
				getAccessType(),
				getRoomType(),
				csvCleanse(getRoomOwner()),
				getCallCompletionCode(),
				csvCleanse(getExtension()),
				csvCleanse(getEndpointGUID())
		};
	}

	private String csvCleanse(String input) {
		if (input != null) {
			return input.replace("\n", " ");
		}
		return "";
	}

	public String getCallID() {
		return CallID;
	}

	public void setCallID(String callID) {
		CallID = callID;
	}

	public String getJoinTime() {
		return JoinTime;
	}

	public void setJoinTime(String joinTime) {
		JoinTime = joinTime;
	}

	public String getLeaveTime() {
		return LeaveTime == null ? "" : LeaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		LeaveTime = leaveTime;
	}

}