/**
 *
 */
package com.vidyo.service.statusnotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;

/**
 * @author ganesh
 *
 */
public class FailedNotificationHandler {

	protected static final Logger logger = LoggerFactory.getLogger(EchoService.class);

	public void handleFailedNotification(Message<MessageHandlingException> message) {
		String payLoad = (String) message.getPayload().getFailedMessage().getPayload();
		logger.warn("payLoad ->" + payLoad);
	}

}
