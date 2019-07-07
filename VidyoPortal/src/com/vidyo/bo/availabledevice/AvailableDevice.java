/**
 * 
 */
package com.vidyo.bo.availabledevice;

import java.io.Serializable;

/**
 * 
 *
 */
public class AvailableDevice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int availableDeviceID;
	
	private String endpointGUID;
	
	private String endpointDisplayName;
	
	private int endpointID;
	
	private int memberID;

	public int getEndpointID() {
		return endpointID;
	}

	public void setEndpointID(int endpointID) {
		this.endpointID = endpointID;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public int getAvailableDeviceID() {
		return availableDeviceID;
	}

	public void setAvailableDeviceID(int availableDeviceID) {
		this.availableDeviceID = availableDeviceID;
	}

	public String getEndpointGUID() {
		return endpointGUID;
	}

	public void setEndpointGUID(String endpointGUID) {
		this.endpointGUID = endpointGUID;
	}

	public String getEndpointDisplayName() {
		return endpointDisplayName;
	}

	public void setEndpointDisplayName(String endpointDisplayName) {
		this.endpointDisplayName = endpointDisplayName;
	}
}
