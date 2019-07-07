package com.vidyo.service.gateway.request;

public class SetCdrDataServiceRequest {
	private String endpointGuid;
	private String deviceModel;
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

}
