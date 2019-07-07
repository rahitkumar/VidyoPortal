/**
 * 
 */
package com.vidyo.portal.batch.jobs.guesteps.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.vidyo.service.IConferenceService;

/**
 * Deletes the Guest Endpoints from the Endpoints table in specific time
 * intervals.
 * 
 * @author Ganesh
 * 
 */
public class CleanGuestEndpointsTasklet implements Tasklet, InitializingBean {

	protected static final Logger logger = Logger.getLogger(CleanGuestEndpointsTasklet.class);

	private IConferenceService conferenceService;
	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(conferenceService, "ConferenceService cannot be null");
	}

	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {

		int deleted = conferenceService.cleanGuestsAndEndpoints();
		logger.debug("Deleted Guest Endpoints Count -> " + deleted);

		return RepeatStatus.FINISHED;
	}
}
