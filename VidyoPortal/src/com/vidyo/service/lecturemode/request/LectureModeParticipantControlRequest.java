package com.vidyo.service.lecturemode.request;

public class LectureModeParticipantControlRequest extends LectureModeControlRequest {

	private int endpointID;
	private String endpointType;

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
}
