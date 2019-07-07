package com.vidyo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vidyo.bo.ExternalStatusNotification;
import com.vidyo.bo.statusnotify.NotificationInfo;

public interface ExternalStatusNotificationService {

	List<ExternalStatusNotification> getActiveNotifications(Pageable page);

	void createNotification(ExternalStatusNotification notification);

	void markCompleted(ExternalStatusNotification notification);
	
	void markCompleted(List<ExternalStatusNotification> notifications);
	
	NotificationInfo convertNotification(ExternalStatusNotification notificationInfo);
}
