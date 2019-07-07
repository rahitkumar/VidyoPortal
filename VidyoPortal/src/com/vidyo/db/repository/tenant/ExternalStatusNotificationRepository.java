package com.vidyo.db.repository.tenant;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.bo.ExternalStatusNotification;

@Repository
@Transactional
public interface ExternalStatusNotificationRepository extends JpaRepository<ExternalStatusNotification, Integer> {

	@Query("FROM ExternalStatusNotification t WHERE t.retry = 1 AND t.status <> 'Completed' AND t.creationTime > :date")
	List<ExternalStatusNotification> findActiveNotifications(Pageable page, @Param("date") Date date);
	
	@Modifying
	@Query("UPDATE ExternalStatusNotification t SET t.status = 'Completed' where t.notificationId in :ids")
	void markCompletedByIds(@Param("ids") List<Integer> ids);
	
	@Modifying
	@Query("UPDATE ExternalStatusNotification t SET t.status = 'Completed' where t.notificationId = :id")
	void markCompletedById(@Param("id") Integer id);
}
