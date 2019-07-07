package com.vidyo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.ExternalStatusNotification;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.db.repository.tenant.ExternalStatusNotificationRepository;
import com.vidyo.framework.security.utils.VidyoUtil;

import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.validateExtDataType;

@Service
public class ExternalStatusNotificationServiceImpl implements ExternalStatusNotificationService {

	protected final Logger logger = LoggerFactory.getLogger(ExternalStatusNotificationServiceImpl.class.getName());
	
	@Autowired
	private ExternalStatusNotificationRepository externalStatusNotificationRepository;
	
	@Autowired
	private EntityManagerFactory emf;

	@Override
	@Transactional
	public List<ExternalStatusNotification> getActiveNotifications(Pageable page) {
		Date date = DateUtils.addDays(Calendar.getInstance().getTime(), -7);
		return externalStatusNotificationRepository.findActiveNotifications(page, date);
	}
	
	@Override
	@Transactional
	public void markCompleted(ExternalStatusNotification notification) {
		externalStatusNotificationRepository.markCompletedById(notification.getNotificationId());
	}
	
	@Override
	@Transactional
	public void markCompleted(List<ExternalStatusNotification> notifications) {
		List<Integer> ids = new ArrayList<Integer>();
		for (ExternalStatusNotification notification : notifications) {
			ids.add(notification.getNotificationId());
		}
		
		externalStatusNotificationRepository.markCompletedByIds(ids);
	}
	
	@Override
	public void createNotification(ExternalStatusNotification notification) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {           
        	tx.begin(); 
        	entityManager.persist(notification);
        	tx.commit();
        	entityManager.close();
        } catch (Exception e) {
        	logger.error("Failed TenantNotificationsServiceImpl.createNotification", e);          
        	tx.rollback();
        }
	}
	
	@Override
	public NotificationInfo convertNotification(ExternalStatusNotification notificationInfo) {
		NotificationInfo notification = new NotificationInfo();
		notification.setTenantId(notificationInfo.getTenantID());

		CDRinfo2 info = new CDRinfo2();
		info.setExtData(notificationInfo.getData());
		info.setExtDataType(
				validateExtDataType(
						notificationInfo.getDataType()));
		notification.setUserNotification(info);

		notification.setExternalStatusNotificationUrl(notificationInfo.getUrl());
		notification.setExternalUsername(notificationInfo.getUsername());
		if (StringUtils.isNotBlank(notificationInfo.getPassword())) {
			notification.setExternalPassword(notificationInfo.getPassword());
		} else {
			notification.setExternalPassword("");
		}
		String password = notificationInfo.getPassword();
		if ((password != null) && !password.isEmpty()) {
			password = VidyoUtil.decrypt(password);
		} else {
			password = "";
		}
		notification.setPlainTextExternalPassword(password);

		return notification;
	}
}
