/**
 *
 */
package com.vidyo.service.statusnotify;

import com.vidyo.bo.statusnotify.NotificationInfo;

/**
 * @author ganesh
 *
 */
public interface EventsNotificationService {

	public void sendNotification(NotificationInfo notificationInfo);

}
