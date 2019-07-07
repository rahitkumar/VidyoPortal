package com.vidyo.service.lecturemode.request;

public abstract class HandUpdateRequest {

	private boolean handRaised;

	public boolean isHandRaised() {
		return handRaised;
	}

	public void setHandRaised(boolean handRaised) {
		this.handRaised = handRaised;
	}
}
