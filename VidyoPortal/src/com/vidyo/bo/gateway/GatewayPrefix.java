package com.vidyo.bo.gateway;

public class GatewayPrefix {

	private String prefixID;
	private int serviceID;
	private String gatewayID;
	private String prefix;
	private int direction; // fromLegacy = 0, toLegacy = 1
	private int tenantID;

	public String getPrefixID() {
		return prefixID;
	}

	public void setPrefixID(String prefixID) {
		this.prefixID = prefixID;
	}

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public String getGatewayID() {
		return gatewayID;
	}

	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}


}
