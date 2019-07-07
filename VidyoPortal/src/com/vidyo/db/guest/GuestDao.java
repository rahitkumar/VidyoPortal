/**
 * 
 */
package com.vidyo.db.guest;

import com.vidyo.bo.Guest;

/**
 * @author ganesh
 *
 */
public interface GuestDao {
	
	/**
	 * 
	 * @param id
	 * @param tenantId
	 * @return
	 */
	public Guest getGuest(int id, int tenantId);
	
	/**
	 * 
	 * @param id
	 * @param tenantId
	 * @return
	 */
	public int deletePak(int id, int tenantId);
	
	/**
	 * 
	 * @param id
	 * @param tenantId
	 * @return
	 */
	public int deletePak2(int id, int tenantId);

}
