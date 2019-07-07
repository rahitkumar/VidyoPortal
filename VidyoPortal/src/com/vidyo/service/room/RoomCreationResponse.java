/**
 * 
 */
package com.vidyo.service.room;

import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.framework.service.BaseServiceResponse;

/**
 * Contains response codes and messages from Room Service while creating Room.
 * This is currently used for Scheduled Room purpose but can be extended to
 * other room types creation.
 * 
 * @author Ganesh
 * 
 */
public class RoomCreationResponse extends BaseServiceResponse {
	
	/**
	 * 
	 */
	public static final int SYSTEM_SCHEDULED_ROOM_FEATURE_DISABLED = 5001;
	
	/**
	 * 
	 */
	public static final int TENANT_SCHEDULED_ROOM_FEATURE_DISABLED = 5002;
	
	/**
	 * 
	 */
	public static final int SCHEDULED_ROOM_CREATION_FAILED = 5002;	
	
	/**
	 * 
	 */
	public static final int SYSTEM_PUBLIC_ROOM_FEATURE_DISABLED = 5003;
	
	/**
	 * 
	 */
	public static final int TENANT_PUBLIC_ROOM_FEATURE_DISABLED = 5004;
	
	/**
	 * 
	 */
	public static final int PUBLIC_ROOM_CREATION_FAILED = 5004;	

	/**
	 * 
	 */
	private Room room;

	/**
	 * 
	 */
	private String extensionValue;
	
	/**
	 * 
	 */
	private Tenant tenant;

	/**
	 * @return the tenant
	 */
	public Tenant getTenant() {
		return tenant;
	}

	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	/**
	 * @return the extensionValue
	 */
	public String getExtensionValue() {
		return extensionValue;
	}

	/**
	 * @param extensionValue
	 *            the extensionValue to set
	 */
	public void setExtensionValue(String extensionValue) {
		this.extensionValue = extensionValue;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

}
