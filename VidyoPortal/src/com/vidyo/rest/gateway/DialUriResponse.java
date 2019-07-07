package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestResponse;

import java.io.Serializable;

public class DialUriResponse extends RestResponse implements Serializable {
	private static final long serialVersionUID = 4022531986719126014L;

	private String joinStatus = "OK";

	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}

}
