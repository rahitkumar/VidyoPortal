/**
 * 
 */
package com.vidyo.interceptors.conference;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.Room;
import com.vidyo.db.IRoomDao;

/**
 * @author Ganesh
 * 
 */
public class RoomUsageInterceptor implements MethodInterceptor {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(RoomUsageInterceptor.class);

	/**
	 * 
	 */
	private IRoomDao roomDao;

	/**
	 * @param roomDao
	 *            the roomDao to set
	 */
	public void setRoomDao(IRoomDao roomDao) {
		this.roomDao = roomDao;
	}

	/**
	 * Intercepts the addToConferenceRecord API and updates the room last
	 * accessed timestamp.
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		int roomId = 0;
		try {
			for (Object obj : invocation.getArguments()) {
				if (obj instanceof Room) {
					// Update the room last accessed time
					roomId = ((Room) obj).getRoomID();
					roomDao.updateLastAccessedTime(roomId, new Date());
					if (((Room) obj).getRoomType().equalsIgnoreCase("Scheduled")) {
						// After successful join to the room, check if it is a schedule room
						// If this is schedule Room then update the deleteon based on last_used.
						roomDao.updateDeleteOnScheduledRoom(roomId);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Cannot update Room's last used timestamp roomId - {}", roomId);
			logger.error("Error while updating room", e.getMessage());
		}
		return invocation.proceed();
	}

}
