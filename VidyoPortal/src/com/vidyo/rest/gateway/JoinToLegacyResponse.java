package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestResponse;

import java.io.Serializable;

public class JoinToLegacyResponse extends RestResponse implements Serializable {

	private static final long serialVersionUID = 7585092726699607178L;

	private String endpointGuid;

	public String getEndpointGuid() {
		return endpointGuid;
	}

	public void setEndpointGuid(String endpointGuid) {
		this.endpointGuid = endpointGuid;
	}

}
