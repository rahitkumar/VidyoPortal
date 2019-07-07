package com.vidyo.service.statusnotify;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.StatusNotification;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.eventsnotify.EventsNotificationServers;
import com.vidyo.bo.statusnotify.Alert;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.bo.statusnotify.StatusNotificationInfo;
import com.vidyo.db.statusnotify.IStatusNotificationDao;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.email.EmailService;
import com.vidyo.ws.notification.MemberStatus_type1;
import com.vidyo.ws.notification.NotifyUserStatus;
import com.vidyo.ws.notification.StatusNotificationServiceStub;
import com.vidyo.ws.notification.UserStatus_type0;

public class StatusNotificationServiceImpl implements StatusNotificationService {

	protected final Logger logger = LoggerFactory.getLogger(StatusNotificationServiceImpl.class.getName());

	// will send out emails once per 100 unsuccesful status notification sending
	private static final int MAX_NUM_ERRORS = 100;
	private final Semaphore errorCounter = new Semaphore(MAX_NUM_ERRORS, true);

	private IStatusNotificationDao statusNotificationDao;
	private ISystemService systemService;
	private IConferenceService conferenceService;
	private EmailService emailService;
	private LicensingService licensingService;
	private ITenantService tenantService;
	private JmsTemplate jmsTemplate;
	private Queue statusNotifyMQqueue;
	private ThreadPoolTaskExecutor notificationTaskExecutor;
	private SimpleGateway tcpGateway;

	private AtomicLong sequence = new AtomicLong(System.currentTimeMillis());

	@Autowired
	private IMemberService memberService;

	public void setStatusNotificationDao(IStatusNotificationDao statusNotificationDao) {
		this.statusNotificationDao = statusNotificationDao;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}

	public void setTenantService(ITenantService tservice) {
		this.tenantService = tservice;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setStatusNotifyMQqueue(Queue statusNotifyMQqueue) {
		this.statusNotifyMQqueue = statusNotifyMQqueue;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public void sendStatusNotificationToExtServer(NotificationInfo notificationInfo) {
		logger.debug("Sending Status Notification to the customer's server {}", notificationInfo);
		logger.debug("Status Notification Settings " + systemService.getEventNotificationServers().getEventsNotificationEnabled());
		// UserNotification or RoomNotification property would be non-null only for TCP streaming notifications
		if (systemService.getEventNotificationServers().getEventsNotificationEnabled().equalsIgnoreCase("on")
				&& (notificationInfo.getUserNotification() != null || notificationInfo.getRoomNotification() != null)) {
			notificationInfo.setSequenceNum(sequence.incrementAndGet());
			logger.debug("Entering About to send message to tcp servers"
					+ systemService.getEventNotificationServers().getEventsNotificationEnabled());
			// Execute the notification in a separate thread
			Future<NotificationInfo> future = null;
			try {
				future = notificationTaskExecutor.submit(new AsyncEventsNotificationTask(notificationInfo));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception whiel invoking async task", e);
			}
			if (future != null && future.isDone()) {
				logger.debug("AsyncNotificationTask is complete " + notificationInfo);
			} else {
				logger.debug("Future is not complete" + future);
			}
		}
		// Alert property would be non-null only for SOAP notifications
		if(notificationInfo.getAlert() != null) {
			Alert alert = notificationInfo.getAlert();
			String status = alert.getStatus();
			//If status notification state is "WaitJoinConfirm" discard the message
			if(status != null && status.equalsIgnoreCase("waitjoinconfirm")) {
				logger.info("Discarding the WaitJoinConfirm message as it is not supported by receiving side" + notificationInfo);
				return;
			}
			StatusNotificationServiceStub notificationService = null;
			try {
				com.vidyo.bo.StatusNotification service = this.systemService.getStatusNotificationService();
				// If global or per tenant status notify server is enabled send the notification
				if (service.isFlag() || StringUtils.isNotBlank(notificationInfo.getVidyoStatusNotificationUrl())) {
					StatusNotificationInfo statusNotificationInfo = this.statusNotificationDao.getInfoForStatusNotification(alert
							.getGUID());
					if (statusNotificationInfo.getUserType().equalsIgnoreCase("M") // member
							|| statusNotificationInfo.getUserType().equalsIgnoreCase("G") // guest
							|| statusNotificationInfo.getUserType().equalsIgnoreCase("L") // legacy
							|| statusNotificationInfo.getUserType().equalsIgnoreCase("R")) { // recorder

						/*
						 * If the memberId is non zero, get the username using
						 * memberId. This memberId would be set only in certain
						 * notification flows.
						 */
						if (alert.getMemberId() != 0 && statusNotificationInfo.getUserType().equalsIgnoreCase("M")) {
							/*
							 * MemberId is needed for offline scenarios specifically as
							 * the unlink might have removed the memberId from the
							 * Endpoint
							 */
							String username = memberService.getUsername(alert.getMemberId());
							if (username != null) {
								statusNotificationInfo.setUserName(username);
							}
							// Race condition fix - set the tenant name in the queued message itself
							if(statusNotificationInfo.getTenantName() == null && alert.getTenantName() != null) {
								statusNotificationInfo.setTenantName(alert.getTenantName());
							}
						}

						if (statusNotificationInfo.getUserName() != null && !statusNotificationInfo.getUserName().equalsIgnoreCase("") 
								&& statusNotificationInfo.getTenantName() != null) {
							if(service.isFlag()) {
								notificationService = this.systemService
										.getStatusNotificationServiceStubWithAUTH(service);
								//logger.debug("Sending global status notification" + notificationService._getServiceClient().getTargetEPR().getAddress());
								status = sendNotification(alert, status, notificationService, statusNotificationInfo); 
							}
							// Individual Tenant Status Notification
							if (StringUtils.isNotBlank(notificationInfo.getVidyoStatusNotificationUrl())) {
								com.vidyo.bo.StatusNotification individualService = new com.vidyo.bo.StatusNotification();
								individualService.setFlag(true);
								individualService.setUrl(notificationInfo.getVidyoStatusNotificationUrl());
								individualService.setUsername(notificationInfo.getVidyoUsername());
								individualService.setPassword(notificationInfo.getPlainTextVidyoPassword());
								notificationService = this.systemService
										.getStatusNotificationServiceStubWithAUTH(individualService);
								
								status = sendNotification(alert, status, notificationService, statusNotificationInfo);
								//logger.debug("Sending individual status notification" + notificationService._getServiceClient().getTargetEPR().getAddress());
								if (logger.isDebugEnabled()) {
									logger.debug("SendStatusNotification per tenant completed successfully {}, {}", status,
											statusNotificationInfo.getUserName());
								}
							} else {
								logger.warn("Dropping Status Notification to the individual tenant's server {}", notificationInfo);
							}	
							if (logger.isDebugEnabled()) {
								logger.debug("SendStatusNotification completed successfully {}, {}", status,
										statusNotificationInfo.getUserName());
							}
						} else {
							logger.warn("Dropping Status Notification to the customer's server {}", notificationInfo);
						}
					}
				}
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("Error while sending Status Notification to the Server - {}\n{}", alert.toString(),
							e);
				}

				try {
					this.errorCounter.acquire();
				} catch (InterruptedException interruptedException) {
					logger.error("Error while acquiring error counter ", interruptedException);
				}

				if (this.errorCounter.availablePermits() == 0) {
					// reset counter
					this.errorCounter.release(MAX_NUM_ERRORS);

					StatusNotificationInfo statusNotificationInfo = statusNotificationDao.getInfoForStatusNotification(alert
							.getGUID());
					Tenant tenant = tenantService.getTenant(statusNotificationInfo.getTenantName());
					String tenantURL = "";
					if (tenant != null) {
						tenantURL = tenant.getTenantURL();
					}
					String ipAddress = getLocalHostIP();

					String errorMsg = "Dear Administrator,\n"
							+ "\n"
							+ "You are receiving this email to inform you that the User Status Notification using Web Service is not working properly. The server might be down or not reachable.\n"
							+ "\n"
							+ "Please check your settings under Super Portal -> Maintenance -> Status Notify.\n"
							+ "\n"
							+ "If you need assistance or have additional questions, please feel free to do one of the following:\n"
							+ "\n"
							+ " - Vidyo Resellers and End Users with \"Plus\" coverage: please contact the Vidyo Customer Service team at support@vidyo.com\n"
							+ "\n"
							+ " - Vidyo End Users without \"Plus\" coverage: please contact your authorized Vidyo Reseller\n"
							+ "\n" + "Portal IP Address: " + ipAddress + "; URL: " + tenantURL + "\n" + "\n"
							+ "Thank you,\n" + "Vidyo";
					String fromAddr = this.systemService.getSuperNotificationFromEmailAddress();
					if (fromAddr == null) {
						fromAddr = "support@vidyo.com";
					}
					String toAddr = this.systemService.getSuperNotificationToEmailAddress();
					if (toAddr == null || toAddr.trim().length() == 0) {
						toAddr = this.licensingService.getSystemLicenseFeature("LicenseeEmail").getLicensedValue();
					}

					emailService.sendEmailSynchronous(fromAddr, toAddr,
							"User Status Notification Server on the VidyoPortal is Not Reachable", errorMsg);
				}
			} finally {
				if (notificationService != null) {
					try {
						notificationService.cleanup();
					} catch (AxisFault af) {
						// ignore
					}
				}
			}
		}
	}

	private String sendNotification(Alert alert, String status, StatusNotificationServiceStub notificationService,
			StatusNotificationInfo statusNotificationInfo) throws RemoteException {
		
		UserStatus_type0 param = new UserStatus_type0();
		param.setUsername(statusNotificationInfo.getUserName());
		param.setTenant(statusNotificationInfo.getTenantName());

		status = prepareStatus(alert, status, param);

		NotifyUserStatus req = new NotifyUserStatus();
		req.setUserStatus(param);

		notificationService.notifyUserStatus(req);
		return status;
	}

	private String prepareStatus(Alert alert, String status, UserStatus_type0 param) {
		String result = status;
		
		// 2 new statuses used in Legacy or Recorder is not
		// supported by StatusNotificationService!
		// convert it to "Offline"
		if (status.equalsIgnoreCase("RingFailed") || status.equalsIgnoreCase("JoinFailed")) {
			result = "Offline";
		}

		param.setMemberStatus(MemberStatus_type1.Factory.fromValue(result));

		// If the status is busy, get the RoomName
		if (result.toLowerCase().contains("busy")) {
			param.setRoomName(alert.getConfName());
		}
		
		return result;
	}

	private String getLocalHostIP() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface etc = (NetworkInterface) interfaces.nextElement();
				if (etc.isLoopback())
					continue;
				if (etc.getName().equals("eth0")) {
					Enumeration<InetAddress> ips = etc.getInetAddresses();
					while (ips.hasMoreElements()) {
						InetAddress thisComputer = (InetAddress) ips.nextElement();
						if (thisComputer instanceof Inet4Address) {
							byte[] address = thisComputer.getAddress();
							for (int i = 0; i < address.length; i++) {
								int unsignedByte = address[i] < 0 ? address[i] + 256 : address[i];
								ip += unsignedByte;
								if (i < (address.length - 1))
									ip += ".";
							}
							return ip;
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
		return ip;
	}

	/**
	 * Puts the status notification message on to the queue
	 *
	 * @param a
	 */
	public void sendStatusNotificationToQueue(final NotificationInfo notificationInfo) {
		boolean isExternalPerTenantNotificationEnabled = false;
		boolean isVidyoPerTenantNotificationEnabled = false;
		String statusNotificationUrl = notificationInfo.getExternalStatusNotificationUrl();
		if ((statusNotificationUrl != null) && !statusNotificationUrl.isEmpty()) {
			isExternalPerTenantNotificationEnabled = true;
		}
		String vidyoStatusNotificationUrl = notificationInfo.getVidyoStatusNotificationUrl();
		if ((vidyoStatusNotificationUrl != null) && !vidyoStatusNotificationUrl.isEmpty()) {
			isVidyoPerTenantNotificationEnabled = true;
		}
		
		StatusNotification service = systemService.getStatusNotificationService();
		EventsNotificationServers eventsNotificationServers = systemService.getEventNotificationServers();
		// If status notification not enabled and events notification is disabled, don't put the message on queue
		logger.info("values " + !service.isFlag() + eventsNotificationServers.getEventsNotificationEnabled().equalsIgnoreCase("off")
				+ !isExternalPerTenantNotificationEnabled + !isVidyoPerTenantNotificationEnabled);
		if (!service.isFlag() && eventsNotificationServers.getEventsNotificationEnabled().equalsIgnoreCase("off")
				&& !isExternalPerTenantNotificationEnabled && !isVidyoPerTenantNotificationEnabled) {
			logger.debug("None of the status notification mechanism is enabled. Not sending message to the Queue" + notificationInfo);
			return;
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Sending Status Notification to the Queue {}", notificationInfo.toString());
		}
		notificationInfo.setQueueTimestamp(System.nanoTime());
		try {
			jmsTemplate.send(statusNotifyMQqueue.getQueueName(), new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(notificationInfo);
				}
			});
		} catch (Exception e) {
			logger.error("Cannot send message to MQ {}", e.getMessage(), e);
		}
	}

	public void setNotificationTaskExecutor(ThreadPoolTaskExecutor notificationTaskExecutor) {
		this.notificationTaskExecutor = notificationTaskExecutor;
	}

	private class AsyncEventsNotificationTask implements Callable<NotificationInfo> {

		private NotificationInfo notificationInfo;


		public AsyncEventsNotificationTask(NotificationInfo notificationInfo) {
			super();
			this.notificationInfo = notificationInfo;
		}

		@Override
		public NotificationInfo call() throws Exception {
			logger.debug("Entering call");
			ObjectMapper mapper = new ObjectMapper();
			notificationInfo.setWireTimestamp(System.nanoTime());
			String notificationJson = mapper.writeValueAsString(notificationInfo);
			String retVal = tcpGateway.send(notificationJson);
			logger.debug("retVal" + retVal);
			return null;
		}

	}

	public void setTcpGateway(SimpleGateway tcpGateway) {
		this.tcpGateway = tcpGateway;
	}

}