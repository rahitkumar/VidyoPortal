/**
 * 
 */
package com.vidyo.service.conference.response;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.bo.availabledevice.AvailableDevice;
import com.vidyo.framework.service.BaseServiceResponse;


public class DeviceManagementResponse extends BaseServiceResponse {

	/**
	 * 
	 */
	public static final int INVALID_ENDPOINTGUID = 6201;
		
	/**
	 * 
	 */
	public static final int CANNOT_ACCESS_TO_ENDPOINT = 6202;
	
	
	/**
	 * 
	 */
	public static final int REGISTER_DEVICE_FAILED = 6203;
	
	/**
	 * 
	 */
	public static final int RETRIEVE_AVAILABLE_DEVICE_LIST_FAILED = 6204;
	
	/**
	 * 
	 */
	public static final int GET_AVAILABLE_DEVICE_FAILED = 6205;
	
	/**
	 * 
	 */
	public static final int INVALID_ROOM_KEY = 6206;
	
	/**
	 * 
	 */
	public static final int INVALID_AVAILABLE_DEVICE_ID = 6207;
	
	/**
	 * 
	 */
	public static final int BRING_DEVICE_TO_CONFERENCE_FAILED = 6208;
	

	private List<AvailableDevice> availableDevices = new ArrayList<AvailableDevice>();
	
	private AvailableDevice availableDevice = null;
	
	public void addAvailableDevices(List<AvailableDevice> availableDevices) {
		this.availableDevices.addAll(availableDevices);
	}
	
	public List<AvailableDevice> retriveAvailableDevices() {
		return availableDevices;
	}
	
	public void setAvailableDevice(AvailableDevice availableDevice) {
		this.availableDevice = availableDevice;
	}
	
	public AvailableDevice getAvailableDevice() {
		return availableDevice;
	}

}
