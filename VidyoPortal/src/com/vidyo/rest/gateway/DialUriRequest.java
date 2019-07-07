package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DialUriRequest  extends RestRequest implements Serializable {

	private static final long serialVersionUID = -5488497272942413922L;

	@NotNull
	@Length(min=1)
	private String dialingUri;

	private String pin;

	@NotNull
	@Length(min=1)
	private String endpointGuid;

	@NotNull
	@Length(min=1)
	private String prefix;

	private String tenant;

	private String fromDisplayName = "Legacy Device";

	private String deviceModel;

	@NotNull
	private String endpointPublicIpAddress;

	public String getDialingUri() {
		return dialingUri;
	}

	public void setDialingUri(String dialingUri) {
		this.dialingUri = dialingUri;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEndpointGuid() {
		return endpointGuid;
	}

	public void setEndpointGuid(String endpointGuid) {
		this.endpointGuid = endpointGuid;
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

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getEndpointPublicIpAddress() {
		return endpointPublicIpAddress;
	}

	public void setEndpointPublicIpAddress(String endpointPublicIpAddress) {
		this.endpointPublicIpAddress = endpointPublicIpAddress;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String toString() {
		return "endpointGuid=[" + StringUtils.trimToEmpty(getEndpointGuid()) + "]" +
				",dialingUri=[" + StringUtils.trimToEmpty(getDialingUri()) + "]" +
				",prefix=[" + StringUtils.trimToEmpty(getPrefix()) + "]"+
				",fromDisplayName=[" + StringUtils.trimToEmpty(getFromDisplayName()) + "]" +
				",deviceModel=[" + StringUtils.trimToEmpty(getDeviceModel()) + "]" +
				",endpointPublicIpAddress=[" + StringUtils.trimToEmpty(getEndpointPublicIpAddress()) + "]" +
				",tenant=[" + StringUtils.trimToEmpty(getTenant()) + "]";
	}
}
