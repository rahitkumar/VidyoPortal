/**
 *
 */
package com.vidyo.service.statusnotify;

import com.vidyo.bo.statusnotify.NotificationInfo;

/**
 * @author ganesh
 *
 */
public interface EventsDispatcher {

	public void sendNotification(NotificationInfo notificationInfo);

}
