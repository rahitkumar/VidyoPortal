package com.vidyo.db;

import java.sql.Timestamp;
import java.util.List;

import com.vidyo.bo.availabledevice.AvailableDevice;

public interface IAvailableDeviceDao {
	/**
	 * Add Endpoint to AvailableDevice table if endpoint does not exist in the table, otherwise updates the row 
	 * with provided availabilityTime
	 * @param endpointGUID endpointGUID which should be added or updated
	 * @param availabilityTime adding timestamp
	 */
	
	public void addAvailableDevice(String endpointGUID, Timestamp availabilityTime);
	/**
	 * Checks if endpoint exists in [availabilityTime - timeRange, availabilityTime] range 
	 * @param endpointGUID endpointGUID which is being checked
	 * @param availabilityTime Upper bounder of checking time range
	 * @param timeRange checking time range in seconds
	 * @return <code>true</code> if endpoint exist in the time range, <code>false</code> - otherwise 
	 */
	public boolean isAvailableDeviceExist(String endpointGUID, Timestamp availabilityTime, int timeRange);
	
	/**
	 * Gets the list of EndpointAvailabilitys available in the time range for the tenant 
	 * @param tenantID
	 * @param availabilityTime Upper bounder of checking time range
	 * @param timeRange checking time range in seconds
	 * @return the list of EndpointAvailabilitys available in the time range for the tenant
	 */
	public List<AvailableDevice> getAvailableDeviceList(int tenantID, Timestamp availabilityTime, int timeRange);
	
	/**
	 * Gets AvailableDevice.
	 * @param tenantID
	 * @param availableDeviceID availableDeviceID available in the time range for tenant
	 * @param availabilityTime Upper bounder of checking time range
	 * @param timeRange checking time range in seconds
	 * @return AvailableDevice if exists, otherwise <code>null</code>
	 */
	public AvailableDevice getAvailableDevice(int tenantID, int availableDeviceID, Timestamp availabilityTime, int timeRange);
}
