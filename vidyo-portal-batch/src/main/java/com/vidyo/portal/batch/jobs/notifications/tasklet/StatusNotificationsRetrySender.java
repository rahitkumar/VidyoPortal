package com.vidyo.portal.batch.jobs.notifications.tasklet;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestClientException;

import com.vidyo.bo.ExternalStatusNotification;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.statusnotify.BaseStatusNotifyListener;

public class StatusNotificationsRetrySender extends BaseStatusNotifyListener implements Tasklet {

	protected static final Logger logger = Logger.getLogger(StatusNotificationsRetrySender.class);

	private int chunkSize;
	
	private int pageNumber = 0;

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		PageRequest page = new PageRequest(pageNumber, chunkSize);
		List<ExternalStatusNotification> notications = tenantNotificationsService.getActiveNotifications(page);

		for (ExternalStatusNotification notification : notications) {
			
			try {
				NotificationInfo info = tenantNotificationsService.convertNotification(notification);
			
				callExternalRestService(info);
				tenantNotificationsService.markCompleted(notification);
			} catch (RestClientException e) {
				logger.error("Rest call exception ", e);
			} catch (Exception e) {
				logger.error("Critical error ", e);
			}
		}

		if (notications.size() < chunkSize) { // check is the latest page
			pageNumber = 0; // reset
			return RepeatStatus.FINISHED;
		} else {
			pageNumber++;
			return RepeatStatus.CONTINUABLE; // continue processing
		}
	}
}
