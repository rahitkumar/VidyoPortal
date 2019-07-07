package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestResponse;

import java.io.Serializable;

public class JoinFromLegacyResponse extends RestResponse implements Serializable {
	private static final long serialVersionUID = 7748324534911877140L;

	private String joinStatus = "UNKNOWN";

	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
}
