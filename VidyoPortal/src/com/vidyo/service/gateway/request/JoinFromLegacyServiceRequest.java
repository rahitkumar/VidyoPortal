package com.vidyo.service.gateway.request;

public class JoinFromLegacyServiceRequest {
	private String gatewayUserAccount;
	private String gwEndpointGuid;
	private String fromDisplayName;
	private String dialString;
	private String pin;
	private String prefix;
	private String tenantName;
	private boolean directCallFlag;
	private String deviceModel;
	private String endpointPublicIpAddress;

	public String getGatewayUserAccount() {
		return gatewayUserAccount;
	}

	public void setGatewayUserAccount(String gatewayUserAccount) {
		this.gatewayUserAccount = gatewayUserAccount;
	}

	public String getGwEndpointGuid() {
		return gwEndpointGuid;
	}

	public void setGwEndpointGuid(String gwEndpointGuid) {
		this.gwEndpointGuid = gwEndpointGuid;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUserTenantName() {
		return tenantName;
	}

	public void setUserTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public boolean isDirectCallFlag() {
		return directCallFlag;
	}

	public void setDirectCallFlag(boolean directCallFlag) {
		this.directCallFlag = directCallFlag;
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
