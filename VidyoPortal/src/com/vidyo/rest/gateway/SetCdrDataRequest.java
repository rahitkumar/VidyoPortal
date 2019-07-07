package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestRequest;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SetCdrDataRequest  extends RestRequest implements Serializable {

	private static final long serialVersionUID = -99720273331372065L;
	@NotNull
	private String endpointGuid;
	private String deviceModel;
	@NotNull
	private String endpointPublicIpAddress;

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

	public String getEndpointPublicIpAddress() {
		return endpointPublicIpAddress;
	}

	public void setEndpointPublicIpAddress(String endpointPublicIpAddress) {
		this.endpointPublicIpAddress = endpointPublicIpAddress;
	}

	public String toString() {
		return "endpointGuid=[" + StringUtils.trimToEmpty(getEndpointGuid()) + "]" +
				",deviceModel=[" + StringUtils.trimToEmpty(getDeviceModel()) + "]" +
				",endpointPublicIpAddress=[" + StringUtils.trimToEmpty(getEndpointPublicIpAddress()) + "]";
	}
}
