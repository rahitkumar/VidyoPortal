/**
 * 
 */
package com.vidyo.portal.batch.jobs.licensecheck.tasklet;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.system.SystemService;

/**
 * Executes the task of sending System License and/or Seat License Expiry
 * notification to the Super Admin user.<br>
 * The schedule is configurable through the Quartz scheduler available in the
 * system.<br>
 * 
 * 
 * @author Ganesh
 * 
 */
public class LicenseExpiryReminderTasklet implements Tasklet, InitializingBean {

	/**
	 * 
	 */
	protected final Logger logger = LoggerFactory.getLogger(LicenseExpiryReminderTasklet.class);

	/**
	 * 
	 */
	private EmailService emailService;

	/**
     * 
     */
	private SystemService systemService;

	/**
     * 
     */
	private ITenantService tenantService;


	/**
     * 
     */
	private MemberService memberService;

	/**
     * 
     */
	private ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * 
	 */
	private LicensingService licensingService;

	/**
	 * 
	 */
	private static final long MILLI_SECONDS_PERDAY = (long) (24 * 3600 * 1000);

	/**
	 * Sends out reminder emails to super admin when the license is about to
	 * expire.<br>
	 * The frequency of the reminder mails is configured in config xml.
	 */
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
//		List<Integer> tenantIds = tenantService.getAllTenantIds();
//		if (tenantIds.isEmpty()) {
//			logger.error("No Tenants configured in the system. Terminating the License Expiry Reminder Job");
//			return null;
//		}

		// Call to load the License Data
//		LicenseFeatureData[] licenseFeatureDatas = getSystemLicenseFeatures(tenantIds.get(0));

//		if (licenseFeatureDatas == null) {
//			logger.error("Terminating Job - LicenseExpiryReminderTasklet - License data is null from VidyoManager");
//			return RepeatStatus.FINISHED;
//		}

		Configuration fromAddrConfig = systemService.getConfiguration("SuperEmailFrom");
		String fromAddr = null;

		if (fromAddrConfig != null
				&& fromAddrConfig.getConfigurationValue() != null
				&& fromAddrConfig.getConfigurationValue().trim().length() > 0) {
			fromAddr = fromAddrConfig.getConfigurationValue().trim();
		} else {
			fromAddr = "support@vidyo.com";
		}

		String toAddr = null;
		Configuration toAddrConfig = systemService.getConfiguration("SuperEmailTo");

		if (toAddrConfig != null
				&& toAddrConfig.getConfigurationValue() != null
				&& toAddrConfig.getConfigurationValue().trim().length() > 0) {
			toAddr = toAddrConfig.getConfigurationValue().trim();
		} else {
//			SystemLicenseFeature licenseeEmailFeature = getSystemLicenseFeature(
//					"LicenseeEmail", licenseFeatureDatas);
			SystemLicenseFeature licenseeEmailFeature = licensingService.getSystemLicenseFeature("LicenseeEmail");
			if (licenseeEmailFeature != null) {
				toAddr = licenseeEmailFeature.getLicensedValue();
			}
		}

//		sendSeatLicenseExpiryNotification(fromAddr, toAddr, tenantIds.get(0), licenseFeatureDatas);
//		sendSystemLicenseExpiryNotificaton(fromAddr, toAddr, tenantIds.get(0), licenseFeatureDatas);
		sendSeatLicenseExpiryNotification(fromAddr, toAddr);
		sendSystemLicenseExpiryNotificaton(fromAddr, toAddr);

		return RepeatStatus.FINISHED;
	}

	/**
	 * Utility method to get the License and a specific value
	 * 
	 * @param featureName
	 * @return
	 */
/*	private SystemLicenseFeature getSystemLicenseFeature(String featureName, LicenseFeatureData[] licenseFeatureDatas) {
		SystemLicenseFeature feature = null;
		if (licenseFeatureDatas != null && licenseFeatureDatas.length > 0) {
			for (LicenseFeatureData aData : licenseFeatureDatas) {
				String name = aData.getName();
				if (name.equalsIgnoreCase(featureName)) {
					String currentValue = aData.getCurrentValue();
					// In use value
					String maxValue = aData.getMaxValue(); // Licensed value

					feature = new SystemLicenseFeature(name, maxValue, currentValue);
					break;
				}
			}
		}

		return feature;
	}
*/
	/**
	 * Returns the System License
	 * 
	 * @return
	 */
/*	private LicenseFeatureData[] getSystemLicenseFeatures(int tenantId) {
		VidyoManagerServiceStub ws = null;
		try {
			// The 1st tenant id in the list will be Default Tenant
			ws = systemService.getVidyoManagerServiceStubWithAuth(tenantId);
		} catch (NoVidyoManagerException e) {
			logger.error("No VidyoManager associated with the Tenant Id {}", tenantId);
			return null;
		}
		GetLicenseDataResponse licRes = null;
		try {
			licRes = ws.getLicenseData(new GetLicenseDataRequest());
		} catch (Exception e) {
			logger.error("Failed to get system license from VidyoManager - ", e.getMessage());
			return null;
		}
		return licRes.getLicenseFeature();
	}
*/
	/**
	 * Utility to get System IP
	 * 
	 * @return
	 */
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
		} catch (Exception ex) {
			logger.error("Exception while getting local ip address " + ex.getMessage());
		}
		return ip;
	}

	/**
	 * 
	 * @param fromAddr
	 * @param toAddr
	 * @param tenantId
	 */
//	private void sendSystemLicenseExpiryNotificaton(String fromAddr, String toAddr, int tenantId,
//			LicenseFeatureData[] licenseFeatureDatas) {
	private void sendSystemLicenseExpiryNotificaton(String fromAddr, String toAddr) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		// assume it uses long format: "April 5, 2010"
		Date expiryDate = null;
//		SystemLicenseFeature expiryDateFeature = getSystemLicenseFeature("Expiry Date", licenseFeatureDatas);
		SystemLicenseFeature expiryDateFeature = licensingService.getSystemLicenseFeature("Expiry Date");
		if (expiryDateFeature == null) {
			logger.warn("Expiry Date Feature not available in License, not sending System License Expiry notification");
			return;
		}
		try {
			expiryDate = df.parse(expiryDateFeature.getLicensedValue());
		} catch (Exception pe) {
			logger.error("Error while parsing the Expiry Date for format 'Month DD YYYY' - "
					+ expiryDateFeature.getLicensedValue());
			expiryDate = null;
		}
		// try different format
		if (expiryDate == null) { // possible format: "022510" for Feb 25, 2010
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
			try {
				expiryDate = sdf.parse(expiryDateFeature.getLicensedValue());
			} catch (ParseException e) {
				logger.error("Error while parsing the Expiry Date for format 'MMddyy' - "
						+ expiryDateFeature.getLicensedValue());
			}
		}

		if (expiryDate == null) {
			logger.warn("Expiry Date License Feature not availble, not sending email notification - "
					+ expiryDateFeature.getLicensedValue());
			return;
		}

		Date today = new Date();
		long daysLeft = daysBetween(today, expiryDate);
		
		logger.debug("Days Left for License Expiry - {}", daysLeft);

		if ((daysLeft == 45) || (daysLeft == 30) || (daysLeft == 15)
				|| ((daysLeft < 8) && (daysLeft >= 0))) {
			// Check if email can be sent
//			if (!canSendNotification(licenseFeatureDatas)) {
//				return;
//			}
			if (!canSendNotification()) {
				return;
			}
			
			if(daysLeft == 0) {
				daysLeft = 1;
			}
			
			if (toAddr == null || toAddr.trim().length() <= 0) {
				logger.warn("Cannot send System License Expiry notification - No recepient email id available. Terminating the job.");
				return;
			}

			Locale loc = getSuperLocale();

			String subject = messageSource.getMessage("port.license.expiry.notification", null, "", loc);
			String ip = getLocalHostIP();
			String tenantUrl = getDefaultTenantUrl();
			String content = MessageFormat.format(
					messageSource.getMessage("port.license.about.to.expire.in.0.days", null, "", loc),
					daysLeft, ip + "(URL: " + tenantUrl + ")");
			try {
				emailService.sendEmailSynchronous(fromAddr, toAddr, subject, content);
			} catch (Exception e) {
				logger.error(
						"Error while sending System License Expiry Notification. Days Left - {}, toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
						daysLeft, toAddr, ip, tenantUrl);
				logger.error("Exception while sending email ", e);
				return;
			}
			logger.debug(
					"System License Expiry Notification Email was submitted to email service successfully. License is about to expire in days - {} , toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
					daysLeft, toAddr, ip, tenantUrl);
		}
	}

	/**
	 * 
	 * @param fromAddr
	 * @param toAddr
	 * @param tenantId
	 */
//	private void sendSeatLicenseExpiryNotification(String fromAddr, String toAddr, int tenantId,
//			LicenseFeatureData[] licenseFeatureDatas) {
	private void sendSeatLicenseExpiryNotification(String fromAddr, String toAddr) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		// assume it uses long format: "April 5, 2010"
		Date expiryDate = null;
//		SystemLicenseFeature expiryDateFeature = getSystemLicenseFeature("SeatExpiry", licenseFeatureDatas);
		SystemLicenseFeature expiryDateFeature = licensingService.getSystemLicenseFeature("SeatExpiry");
		if (expiryDateFeature == null) {
			logger.error("SeatExpiry Date Feature not available in License, not sending SeatExpiry Expiry notification");
			return;
		}
		try {
//			expiryDate = df.parse(getSystemLicenseFeature("SeatExpiry", licenseFeatureDatas).getLicensedValue());
			expiryDate = df.parse(expiryDateFeature.getLicensedValue());
		} catch (Exception pe) {
			logger.error("Error while parsing the Expiry Date for format 'Month DD YYYY' - "
					+ expiryDateFeature.getLicensedValue());
		}
		// try again
		if (expiryDate == null) { // possible format: "022510" for Feb 25, 2010
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
			try {
//				expiryDate = sdf.parse(this.getSystemLicenseFeature("SeatExpiry", licenseFeatureDatas)
//						.getLicensedValue());
				expiryDate = sdf.parse(expiryDateFeature.getLicensedValue());
			} catch (ParseException e) {
				logger.error("Error while parsing the Expiry Date for format 'MMddyy' - "
						+ expiryDateFeature.getLicensedValue());
			}
		}

		if (expiryDate == null) {
			logger.warn("SeatExpiry Date License Feature not availble, not sending email notification - "
					+ expiryDateFeature.getLicensedValue());
			return;
		}

		Date today = new Date();
		long daysLeft = daysBetween(today, expiryDate);
		
		logger.debug("Days Left for License Expiry - {}", daysLeft);

		if ((daysLeft == 45) || (daysLeft == 30) || (daysLeft == 15)
				|| ((daysLeft < 8) && (daysLeft >= 0))) {
			// Check if email can be sent
//			if (!canSendNotification(licenseFeatureDatas)) {
//				return;
//			}
			if (!canSendNotification()) {
				return;
			}

			if (toAddr == null || toAddr.trim().length() <= 0) {
				logger.warn("Cannot send Seat License Expiry notification - No recepient email id available. Terminating the job.");
				return;
			}
			
			if(daysLeft == 0) {
				daysLeft = 1;
			}

			Locale loc = getSuperLocale();

			String subject = messageSource.getMessage("seat.license.expiry.notification", null, "", loc);
			String ip = getLocalHostIP();
			String tenantUrl = getDefaultTenantUrl();
			String content = MessageFormat.format(
					messageSource.getMessage("seat.license.about.to.expire.in.0.days", null, "", loc), daysLeft,
					ip + "(URL: " + tenantUrl + ")");
			try {
				emailService.sendEmailSynchronous(fromAddr, toAddr, subject, content);
			} catch (Exception e) {
				logger.error(
						"Error while sending Seat License Expiry Notification. Days Left - {}, toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
						daysLeft, toAddr, ip, tenantUrl);
				logger.error("Exception while sending email ", e);
				return;
			}
			logger.debug(
					"Seat License Expiry Notification Email was submitted to email service successfully. License is about to expire in days - {} , toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
					daysLeft, toAddr, ip, tenantUrl);
		}
	}

	/**
	 * Returns if send notification feature value from License
	 * 
	 * @param licenseFeatureDatas
	 * @return
	 */
//	private boolean canSendNotification(LicenseFeatureData[] licenseFeatureDatas) {
	private boolean canSendNotification() {
		// Get the email notification license value
//		SystemLicenseFeature notificationFeature = getSystemLicenseFeature("SendExpirationNotification",
//				licenseFeatureDatas);
		SystemLicenseFeature notificationFeature = licensingService.getSystemLicenseFeature("SendExpirationNotification");
		if (notificationFeature == null) {
			logger.error("License does not allow sending expiry notification. Terminating the job.");
			return false;
		}
		String licensedVal = notificationFeature.getLicensedValue();

		boolean sendNotification = Boolean.valueOf(licensedVal);
		if (!sendNotification) {
			logger.error("License does not allow sending expiry notification. Terminating the job. Flag - "
					+ sendNotification);
		}
		return sendNotification;
	}

	/**
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private long daysBetween(Date d1, Date d2) {
		long days = (d2.getTime() - d1.getTime()) / MILLI_SECONDS_PERDAY;
		return days;
	}

	/**
	 * 
	 * @return
	 */
	private Locale getSuperLocale() {
		MemberFilter memberFilter = new MemberFilter();
		List<Member> superMembers = memberService.getSupers(memberFilter);
		
		// Set default 1
		int langId = 1;
		
		if(!superMembers.isEmpty()) {
			langId = superMembers.get(0).getLangID();
		}

		switch (langId) {
		case 1:
			return Locale.ENGLISH;
		case 2:
			return Locale.FRENCH;
		case 3:
			return Locale.JAPANESE;
		case 4:
			return Locale.SIMPLIFIED_CHINESE;
		case 5:
			return new Locale("es");
		case 6:
			return Locale.ITALIAN;
		case 7:
			return Locale.GERMAN;
		case 8:
			return Locale.KOREAN;
		case 9:
			return new Locale("pt");
		case 10:
			return Locale.ENGLISH;
		case 11:
			return new Locale("fi");
		case 12:
			return new Locale("pl");
		case 13:
			return Locale.TRADITIONAL_CHINESE;
		case 14:
			return new Locale("th");
		case 15:
			return new Locale("ru");
		case 16:
			return new Locale("tr");
		default:
			return Locale.ENGLISH;
		}
	}

	/**
	 * 
	 * @return
	 */
	private String getDefaultTenantUrl() {
		Tenant t = tenantService.getTenant(1); // get default tenant
		return t.getTenantURL();
	}

	/**
	 * Mere check to make sure that all properties are set
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(systemService, "SystemService cannot be null");
		Assert.notNull(tenantService, "TenantService cannot be null");
		Assert.notNull(memberService, "MemberService cannot be null");
		Assert.notNull(emailService, "mailSender cannot be null");
		Assert.notNull(messageSource, "ReloadableResourceBundleMessageSource cannot be null");
		Assert.notNull(licensingService, "LicensingService cannot be null");
	}

	/**
	 * @param emailService
	 *            the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}

}
