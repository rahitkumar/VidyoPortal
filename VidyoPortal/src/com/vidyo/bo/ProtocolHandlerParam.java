package com.vidyo.bo;

import java.io.Serializable;

public class ProtocolHandlerParam implements Serializable {
	
	private static final long serialVersionUID = -312344830469114888L;
	private String vmAddr;
	private String tlsProxy;
	private String username;
	private String accessKey;
	private String proxyAddr;
	private String portalURL;
	private String loctag;
	private String portalVersion;
	private int minPinLen;
	private int maxPinLen;
	private String vrProxyConfig;
	private int minMediaPort;
	private int maxMediaPort;
	private String endpointExternalIPAddress;
	private String roomKey;

	
	
	public String getPortalURL() {
		return portalURL;
	}
	public void setPortalURL(String portalURL) {
		this.portalURL = portalURL;
	}
	public String getVmAddr() {
		return vmAddr;
	}
	public void setVmAddr(String vmAddr) {
		this.vmAddr = vmAddr;
	}
	public String getTlsProxy() {
		return tlsProxy;
	}
	public void setTlsProxy(String tlsProxy) {
		this.tlsProxy = tlsProxy;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getProxyAddr() {
		return proxyAddr;
	}
	public void setProxyAddr(String proxyAddr) {
		this.proxyAddr = proxyAddr;
	}

	public String getLoctag() {
		return loctag;
	}
	public void setLoctag(String loctag) {
		this.loctag = loctag;
	}
	public String getPortalVersion() {
		return portalVersion;
	}
	public void setPortalVersion(String portalVersion) {
		this.portalVersion = portalVersion;
	}
	public int getMinPinLen() {
		return minPinLen;
	}
	public void setMinPinLen(int i) {
		this.minPinLen = i;
	}
	public int getMaxPinLen() {
		return maxPinLen;
	}
	public void setMaxPinLen(int pinMax) {
		this.maxPinLen = pinMax;
	}
	public String getVrProxyConfig() {
		return vrProxyConfig;
	}
	public void setVrProxyConfig(String vrProxyConfig) {
		this.vrProxyConfig = vrProxyConfig;
	}
	public int getMinMediaPort() {
		return minMediaPort;
	}
	public void setMinMediaPort(int i) {
		this.minMediaPort = i;
	}
	public int getMaxMediaPort() {
		return maxMediaPort;
	}
	public void setMaxMediaPort(int maxMediaPort) {
		this.maxMediaPort = maxMediaPort;
	}
	public String getEndpointExternalIPAddress() {
		return endpointExternalIPAddress;
	}
	public void setEndpointExternalIPAddress(String endpointExternalIPAddress) {
		this.endpointExternalIPAddress = endpointExternalIPAddress;
	}
	

	@Override
	public String toString() {
		return "PortalHandlerConf [vmAddr=" + vmAddr + ", tlsProxy=" + tlsProxy + ", username=" + username
				+ ", accessKey=" + accessKey + ", proxyAddr=" + proxyAddr + ", portalURL=" + portalURL + ", loctag="
				+ loctag + ", portalVersion=" + portalVersion + ", minPinLen=" + minPinLen + ", maxPinLen=" + maxPinLen
				+ ", vrProxyConfig=" + vrProxyConfig + ", minMediaPort=" + minMediaPort + ", maxMediaPort="
				+ maxMediaPort + ",endpointExternalIPAddress="+endpointExternalIPAddress+"]";
	}
	public String getRoomKey() {
		return roomKey;
	}
	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}
}