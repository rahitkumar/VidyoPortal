package com.vidyo.service.gateway.response;

public class GetVMConnectInfoServiceResponse extends BaseGatewayServiceResponse {

	public static final int VIDYO_MANAGER_UNAVAILABLE = 5000;

	private String vmConnect;

	public String getVmConnect() {
		return vmConnect;
	}

	public void setVmConnect(String vmConnect) {
		this.vmConnect = vmConnect;
	}
}
