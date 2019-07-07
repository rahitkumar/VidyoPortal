package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class JoinFromLegacyRequest extends RestRequest implements Serializable  {

	private static final long serialVersionUID = 3873441528832217043L;

	private String prefix;

	@NotNull
	@Length(min=1)
	private String fromDisplayName = "Legacy Device";

	@NotNull
	@Length(min=1)
	private String dialString;

	private String pin;

	private String tenant;

	@NotNull
	private boolean directCallFlag = false;

	@NotNull
	@Length(min=1)
	private String endpointGuid;

	private String deviceModel;

	@NotNull
	private String endpointPublicIpAddress;

	public String getEndpointPublicIpAddress() {
		return endpointPublicIpAddress;
	}

	public void setEndpointPublicIpAddress(String endpointPublicIpAddress) {
		this.endpointPublicIpAddress = endpointPublicIpAddress;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFromDisplayName() {
		return fromDisplayName;
	}

	public void setFromDisplayName(String fromDisplayName) {
		this.fromDisplayName = fromDisplayName;
	}

	public String getDialString() {
		return dialString;
	}

	public void setDialString(String dialString) {
		this.dialString = dialString;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public boolean isDirectCallFlag() {
		return directCallFlag;
	}

	public void setDirectCallFlag(boolean directCallFlag) {
		this.directCallFlag = directCallFlag;
	}

	public String getEndpointGuid() {
		return endpointGuid;
	}

	public void setEndpointGuid(String endpointGuid) {
		this.endpointGuid = endpointGuid;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String toString() {
		return "endpointGuid=[" + StringUtils.trimToEmpty(getEndpointGuid()) + "]" +
				",fromDisplayName=[" + StringUtils.trimToEmpty(getFromDisplayName()) + "]" +
				",dialString=[" + StringUtils.trimToEmpty(getDialString()) + "]" +
				",tenant=[" + StringUtils.trimToEmpty(getTenant()) + "]" +
				",directCallFlag=[" + (isDirectCallFlag() ? "true" : "false") + "]" +
				",deviceModel=[" + StringUtils.trimToEmpty(getDeviceModel()) + "]" +
				",endpointPublicIpAddress=[" + StringUtils.trimToEmpty(getEndpointPublicIpAddress()) + "]";
	}
}
