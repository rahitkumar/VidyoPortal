/**
 * 
 */
package com.vidyo.service.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.vidyo.bo.Guest;
import com.vidyo.db.guest.GuestDao;

/**
 * @author ganesh
 * 
 */
public class GuestServiceImpl implements GuestService {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(GuestServiceImpl.class);

	/**
	 * 
	 */
	private GuestDao guestDao;

	/**
	 * 
	 */
	@Override
	public Guest getGuest(int id, int tenantId) {
		return guestDao.getGuest(id, tenantId);
	}

	/**
	 * 
	 * @param guestDao
	 */
	public void setGuestDao(GuestDao guestDao) {
		this.guestDao = guestDao;
	}

	/**
	 * 
	 * @param id
	 * @param tenantId
	 * @return
	 */
	@Override
	public int deletePak(int id, int tenantId) {
		int updateCount = 0;
		try {
			updateCount = guestDao.deletePak(id, tenantId);
		} catch (DataAccessException dae) {
			logger.error("Error while updating pak to null in Guests");
		}
		return updateCount;
	}

	/**
	 * 
	 * @param id
	 * @param tenantId
	 * @return
	 */
	@Override
	public int deletePak2(int id, int tenantId) {
		int updateCount = 0;
		try {
			updateCount = guestDao.deletePak2(id, tenantId);
		} catch (DataAccessException dae) {
			logger.error("Error while updating pak2 to null in Guests");
		}
		return updateCount;
	}

}
