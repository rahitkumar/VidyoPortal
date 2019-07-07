/**
 *
 */
package com.vidyo.service.statusnotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.statusnotify.NotificationInfo;

/**
 * @author ganesh
 *
 */
public class TcpNotificationServiceImpl implements EventsNotificationService {

	/**
	 *
	 */
	protected static final Logger logger = LoggerFactory.getLogger(TcpNotificationServiceImpl.class);

	/**
	 *
	 */
	private SimpleGateway simpleGateway;

	/**
	 *
	 */
	@Override
	public void sendNotification(NotificationInfo notificationInfo) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(notificationInfo);
		} catch (JsonProcessingException e) {
			logger.error("Error while creating json for notification", e);

		}
		simpleGateway.send(json);
	}

}
