/**
 * StatusNotificationServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */
package com.vidyo.ws.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StatusNotificationServiceSkeleton java skeleton for the axisService
 */
public class StatusNotificationServiceSkeleton implements StatusNotificationServiceSkeletonInterface {

	protected final Logger logger = LoggerFactory.getLogger(StatusNotificationServiceSkeleton.class.getName());

	/**
	 * Auto generated method signature
	 * 
	 * @param notifyUserStatus0
	 */
	public void notifyUserStatus(com.vidyo.ws.notification.NotifyUserStatus notifyUserStatus0) {
		if (logger.isDebugEnabled()) {
			logger.error("Status Notification on Server Side:");
			logger.error("Room ->" + notifyUserStatus0.getUserStatus().getRoomName());
			logger.error("Tenant->" + notifyUserStatus0.getUserStatus().getTenant());
			logger.error("User->" + notifyUserStatus0.getUserStatus().getUsername());
			logger.error("MemberStatus->" + notifyUserStatus0.getUserStatus().getMemberStatus());
			logger.error("===");
		}
	}

}
