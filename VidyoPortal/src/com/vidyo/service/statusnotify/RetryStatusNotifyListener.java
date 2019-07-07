package com.vidyo.service.statusnotify;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.ExternalStatusNotification;
import com.vidyo.bo.statusnotify.NotificationInfo;

public class RetryStatusNotifyListener extends BaseStatusNotifyListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(RetryStatusNotifyListener.class.getName());

	public void onMessage(Message message) {
		try {
			NotificationInfo notificationInfo = (NotificationInfo) ((ActiveMQObjectMessage) message).getObject();

			if (logger.isDebugEnabled()) {
				logger.debug("Got retry message - {}, thread id - {}", notificationInfo.toString(),
						Thread.currentThread().getId());
			}

			CDRinfo2 info = notificationInfo.getUserNotification();
			// check send external notification via REST
			if ((info != null) && (notificationInfo.getExternalStatusNotificationUrl() != null)) {
				try {
					callExternalRestService(notificationInfo);
				} catch (Exception rce) {
					logger.error("error send retry nofitication to external server -> " + rce);
					storeNotificationToRetry(notificationInfo);
				}
			}
		} catch (JMSException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
	}
	
	protected void storeNotificationToRetry(NotificationInfo notificationInfo) {
		ExternalStatusNotification notification = new ExternalStatusNotification();
		notification.setTenantID(notificationInfo.getTenantId());
		notification.setStatus("Active");
		notification.setData(notificationInfo.getUserNotification().getExtData());
		notification.setDataType(notificationInfo.getUserNotification().getExtDataType());
		notification.setUrl(notificationInfo.getExternalStatusNotificationUrl());
		notification.setUsername(notificationInfo.getExternalUsername());
		notification.setPassword(notificationInfo.getExternalPassword());
		notification.setRetry(1); // retry if not success
		
		tenantNotificationsService.createNotification(notification);
	}
}
