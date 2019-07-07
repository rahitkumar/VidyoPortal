package com.vidyo.service.statusnotify;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.ISystemService;

public class StatusNotifyListener extends BaseStatusNotifyListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(StatusNotifyListener.class.getName());

	protected Queue retryStatusNotifyMQqueue;

	protected JmsTemplate jmsTemplate;

	protected ISystemService systemService;

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setRetryStatusNotifyMQqueue(Queue retryStatusNotifyMQqueue) {
		this.retryStatusNotifyMQqueue = retryStatusNotifyMQqueue;
	}

	public Queue getRetryStatusNotifyMQqueue() {
		return retryStatusNotifyMQqueue;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * 
	 */
	public void onMessage(Message message) {
		try {
			NotificationInfo notificationInfo = (NotificationInfo) ((ActiveMQObjectMessage) message).getObject();

			if (logger.isDebugEnabled()) {
				logger.debug("Got message - {}, thread id - {}", notificationInfo.toString(), 
						Thread.currentThread().getId());
			}

			CDRinfo2 info = notificationInfo.getUserNotification();
			// Send Epic REST notification only if the External URL and extData
			// are present.
			if ((info != null && StringUtils.isNotBlank(info.getExtData()))
					&& (notificationInfo.getExternalStatusNotificationUrl() != null)) {

				// Always have the retry flag as true, sucess flag as false
				RetryResult result = new RetryResult(true, "", false);
				
				try {
					result = callExternalRestService(notificationInfo);
					
					if (!result.isSuccess() && !result.isRetry()) {
						storeFailedNotification(notificationInfo, result);
					}
				} catch (Exception rce) {
					logger.error("error send nofitication to external server -> ", rce);
					// If there is a runtime exception/error, store the failed message with retry
					// flag as '1' (true), because all retries throw RestClientException
					result.setMessage(rce.getMessage());
					storeFailedNotification(notificationInfo, result);
				}
			}

			// Send the notifications other than External notification
			try {
				getStatusNotificationService().sendStatusNotificationToExtServer(notificationInfo);
			} catch (Exception e) {
				logger.error("Cannot call sendStatusNotificationToExtServer method ", e);
			}
		} catch (JMSException e) {
				logger.error("JMS Exception while processing message ->", e);
		}
	}

	/**
	 * Only the errors
	 */
	@Override
	protected RetryResult isRetryRequired(String content) {
		RetryResult result = new RetryResult(true, "", false); // retry by default

		// NO-RETRY errors - These errors should not be re-tried.
		if (StringUtils.isNotBlank(content) && ((content.indexOf("NO-CONFERENCE-ID") > -1)
				|| (content.indexOf("NO-USER-ID") > -1) || (content.indexOf("NO-USER-ID-Type") > -1)
				|| (content.indexOf("NO-VENDOR-NAME") > -1) || (content.indexOf("NO-CONNECTION-STATUS") > -1)
				|| (content.indexOf("INVALID-CONFERENCE-ID") > -1) || (content.indexOf("INVALID-USER-ID-TYPE") > -1)
				|| (content.indexOf("INVALID-USER-ID") > -1) || (content.indexOf("INVALID-CONNECTION-STATUS") > -1))) {

			String error = "Reason Not Available";
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode node = objectMapper.readTree(content);
				error = node.get("ExceptionMessage") != null ? node.get("ExceptionMessage").asText() : "Reason Not Available";
			} catch (Exception e) {
				logger.error("error parsing json response", e);
				error = "Parsing response failed";
			}
			// Do not retries for these error messages as they will never succeed
			result = new RetryResult(false, error, false); 
		}

		if (StringUtils.isNotBlank(content) && content.indexOf("LOCK-FAILED") > -1) {
			result = new RetryResult(true, "LOCK-FAILED", false); // retry only for this error as this a temporary error scenario
		}

		return result;
	}
}
