/**
 *
 */
package com.vidyo.service.statusnotify;

import javax.jms.JMSException;
import javax.jms.Queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.ArgumentMatchers;
import org.unitils.mock.Mock;
import org.unitils.mock.core.proxy.ProxyInvocation;
import org.unitils.mock.mockbehavior.MockBehavior;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.StatusNotification;
import com.vidyo.bo.statusnotify.Alert;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.ISystemService;

/**
 * @author ganesh
 *
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StatusNotifySenderTests {

	/**
	 *
	 */
	@SpringBeanByType
	private StatusNotificationService statusNotificationService;

	/**
	 *
	 */
	private Mock<ISystemService> mockSystemService;

	/**
	 *
	 */
	private Mock<JmsTemplate> mockJmsTemplate;

	/**
	 *
	 */
	private Mock<Queue> mockQueue;

	/**
	 *
	 */
	@Before
	public void initialize() {
		((StatusNotificationServiceImpl) statusNotificationService)
				.setJmsTemplate(mockJmsTemplate.getMock());
		((StatusNotificationServiceImpl) statusNotificationService)
				.setStatusNotifyMQqueue(mockQueue.getMock());
		((StatusNotificationServiceImpl) statusNotificationService)
				.setSystemService(mockSystemService.getMock());
	}

	/**
	 * SendStatusNotification invocation returns if the notification is not
	 * enabled. If this method runs without errors, this test case is
	 * successful.
	 */
	@Test
	public void testSendMessageDisableNotification() {
		StatusNotification statusNotification = new StatusNotification();
		statusNotification.setFlag(false);
		mockSystemService.returns(statusNotification)
				.getStatusNotificationService();
		statusNotificationService.sendStatusNotificationToQueue(null);
	}

	/**
	 * Test case to send message if the notification is enabled
	 *
	 * @throws JmsException
	 * @throws JMSException
	 */
	@Test
	public void testSendMessageEnableNotification() throws JmsException,
			JMSException {
		StatusNotification statusNotification = new StatusNotification();
		statusNotification.setFlag(true);
		mockSystemService.returns(statusNotification)
				.getStatusNotificationService();
		mockJmsTemplate.performs(new MockBehavior() {
			@Override
			public Object execute(ProxyInvocation arg0) throws Throwable {
				return null;
			}
		}).send(ArgumentMatchers.any(String.class),
				ArgumentMatchers.any(MessageCreator.class));
		Alert alert = new Alert("guid", "Online", 0);
		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setAlert(alert);
		statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
	}
}
