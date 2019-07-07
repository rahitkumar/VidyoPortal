package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestRequest;

import java.io.Serializable;

public class JoinToLegacyRequest extends RestRequest implements Serializable {
	private static final long serialVersionUID = -8165073700030311023L;

	private String token;
	private String prefix;
	private String fromUserName;
	private String fromExtNumber;
	private String toExtension;
	private String endpointGuid;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromExtNumber() {
		return fromExtNumber;
	}

	public void setFromExtNumber(String fromExtNumber) {
		this.fromExtNumber = fromExtNumber;
	}

	public String getToExtension() {
		return toExtension;
	}

	public void setToExtension(String toExtension) {
		this.toExtension = toExtension;
	}

	public String getEndpointGuid() {
		return endpointGuid;
	}

	public void setEndpointGuid(String endpointGuid) {
		this.endpointGuid = endpointGuid;
	}
}
