/**
 * 
 */
package com.vidyo.utils;

import com.vidyo.service.room.ScheduledRoomResponse;

/**
 * Random unique number generator
 * 
 * @author Ganesh
 * 
 */
public interface Generator {

	/**
	 * 
	 * @return
	 */
	public String generate();
	
	/**
	 * 
	 * @param memberId
	 * @param randNumb
	 * @return
	 */
	public String generateSchRoomExtnWithPin(int memberId, int randNumb);
	
	/**
	 * 
	 * @return
	 */
	public int generateRandom();
	
	/**
	 * Decrypts the scheduled room extension & pin and returns back the original
	 * values as a delimited string
	 * 
	 * @param extn
	 * @param pin
	 * @return
	 */
	public ScheduledRoomResponse decryptShceduledRoom(String ext, String pin);
	
	/**
	 * 
	 * @param tenantId
	 * @param tenantPrefix
	 * @return
	 */

	public String generateRandomRoomExtn(int tenantId, String tenantPrefix);
}
