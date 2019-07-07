package com.vidyo.service.statusnotify;

import com.vidyo.bo.statusnotify.NotificationInfo;

public interface StatusNotificationService {

	/**
	 * Sends the status notification to external server.
	 * Typically app developer will not call this method. It'll be invoked by JMS listener.
	 *
	 * @param a
	 */
	public void sendStatusNotificationToExtServer(NotificationInfo notificationInfo);

	/**
	 * Puts the status notification message on to the queue.
	 * App developer should invoke this method to push the status notification to external server async.
	 *
	 * @param a
	 */
	public void sendStatusNotificationToQueue(NotificationInfo notificationInfo);

}