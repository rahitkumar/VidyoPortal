/**
 * 
 */
package com.vidyo.portal.batch.jobs.scheduledroom.tasklet;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.room.RoomType;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;

/**
 * This job deletes the Scheduled Rooms from the Room table if they are not used
 * for the last 'N' number of days.<br>
 * The number of days is a configurable parameter.
 * 
 * @author Ganesh
 * 
 */
public class ScheduledRoomCleanupTasklet implements Tasklet {

	/**
	 * Logger
	 */
	protected static final Logger logger = Logger
			.getLogger(ScheduledRoomCleanupTasklet.class);

	/**
	 * 
	 */
	private IRoomService roomService;

	/**
	 * 
	 */
	private ISystemService systemService;

	/**
	 * 
	 */
	private static final String SCHEDULED_ROOM_INACTIVE_LIMIT = "SCHEDULED_ROOM_INACTIVE_LIMIT";
	
	/**
	 * 
	 */
	private static final String SCHEDULED_ROOM_BATCH_LIMIT = "SCHEDULED_ROOM_BATCH_LIMIT";

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param roomService
	 *            the roomService to set
	 */
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	/**
	 * 
	 */
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
		// Step 1 - Get the Scheduled Room Prefix marked for deletion
		Configuration configuration = systemService.getConfiguration(SCHEDULED_ROOM_INACTIVE_LIMIT);

		// Check the configuration value
		if (configuration == null || configuration.getConfigurationValue() == null || configuration.getConfigurationValue().trim().isEmpty()) {
			logger.warn("Scheduled Room Inactive limit is not configured, terminating job execution");
			return RepeatStatus.FINISHED;
		}
		
		int inactiveDays = 0;
		
		try {
			inactiveDays = Integer.parseInt(configuration.getConfigurationValue().trim());
		} catch(Exception e) {
			logger.error("Scheduled Room Inactivity limit is not valid value"+ configuration.getConfigurationValue().trim());
			inactiveDays = 0;
		}
		
		if(inactiveDays != 0) {
			// Continue with the clean up of scheduled rooms based on the config value.
			int count = roomService.deleteInactiveSchRooms(inactiveDays);
			logger.debug("Number of Inactive Scheduled rooms deleted - " + count);
		}
		
		// Step 1 - All the scheduled rooms should be deleted from Room where the recurring period + the creation time has crossed today's limit
		//          as it means the scheduled room is no longer needed and can be cleaned up
		logger.info("Starting ScheduledRoomCleanupTasklet - > To garbage collect the scheduled rooms");

		configuration = systemService.getConfiguration(SCHEDULED_ROOM_BATCH_LIMIT);
		
		int batchSize = 0;
		
		try {
			batchSize = Integer.parseInt(configuration.getConfigurationValue().trim());
		} catch(Exception e) {
			logger.error("Scheduled Room batchSize limit is not valid value. Setting batchSize to default 500");
			batchSize = 500;
		}
		// At this time calling roomservice to cleanup the records.

		int totalDeleted = roomService.deleteScheduledRoomsbyRecurring(batchSize);
		
		logger.info("Completed ScheduledRoomCleanupTasklet -> garbage collection for the scheduled rooms. Number of Scheduled rooms deleted - " + totalDeleted);
		
		return RepeatStatus.FINISHED;
	}

}
