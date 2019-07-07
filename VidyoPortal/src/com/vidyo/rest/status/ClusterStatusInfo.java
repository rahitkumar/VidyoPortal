package com.vidyo.rest.status;

import java.util.Map;

import com.vidyo.rest.base.RestResponse;

public class ClusterStatusInfo extends RestResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String 	assignedNodeId;
	private String dataSyncIp;
	private Map<String, String> clusterMap;
	public Map<String, String> getClusterMap() {
		return clusterMap;
	}
	public void setClusterMap(Map<String, String> clusterMap) {
		this.clusterMap = clusterMap;
	}
	public String getAssignedNodeId() {
		return assignedNodeId;
	}
	public void setAssignedNodeId(String assignedNodeId) {
		this.assignedNodeId = assignedNodeId;
	}
	public String getDataSyncIp() {
		return dataSyncIp;
	}
	public void setDataSyncIp(String dataSyncIp) {
		this.dataSyncIp = dataSyncIp;
	}


//	public String nodeId;
//	public String nodeVersion;
//	public String status;
//	public String dataSyncIp; 
//	public String requiredKeys;
//	public String assignedNodeNumber;
//	public String command;
//	public String displayName;
//	public String publicIp; 
//	public String nativeEth0Ip; 
//	public String nativeEth1Ip;
//	public String getNodeId() {
//		return nodeId;
//	}
//	public void setNodeId(String nodeId) {
//		this.nodeId = nodeId;
//	}
//	public String getNodeVersion() {
//		return nodeVersion;
//	}
//	public void setNodeVersion(String nodeVersion) {
//		this.nodeVersion = nodeVersion;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public String getDataSyncIp() {
//		return dataSyncIp;
//	}
//	public void setDataSyncIp(String dataSyncIp) {
//		this.dataSyncIp = dataSyncIp;
//	}
//	public String getRequiredKeys() {
//		return requiredKeys;
//	}
//	public void setRequiredKeys(String requiredKeys) {
//		this.requiredKeys = requiredKeys;
//	}
//	public String getAssignedNodeNumber() {
//		return assignedNodeNumber;
//	}
//	public void setAssignedNodeNumber(String assignedNodeNumber) {
//		this.assignedNodeNumber = assignedNodeNumber;
//	}
//	public String getCommand() {
//		return command;
//	}
//	public void setCommand(String command) {
//		this.command = command;
//	}
//	public String getDisplayName() {
//		return displayName;
//	}
//	public void setDisplayName(String displayName) {
//		this.displayName = displayName;
//	}
//	public String getPublicIp() {
//		return publicIp;
//	}
//	public void setPublicIp(String publicIp) {
//		this.publicIp = publicIp;
//	}
//	public String getNativeEth0Ip() {
//		return nativeEth0Ip;
//	}
//	public void setNativeEth0Ip(String nativeEth0Ip) {
//		this.nativeEth0Ip = nativeEth0Ip;
//	}
//	public String getNativeEth1Ip() {
//		return nativeEth1Ip;
//	}
//	public void setNativeEth1Ip(String nativeEth1Ip) {
//		this.nativeEth1Ip = nativeEth1Ip;
//	} 
}
