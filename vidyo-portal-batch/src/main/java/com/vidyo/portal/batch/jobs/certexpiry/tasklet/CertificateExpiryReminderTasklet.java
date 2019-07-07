/**
 * 
 */
package com.vidyo.portal.batch.jobs.certexpiry.tasklet;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.MessageFormat;
import java.util.Arrays;
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
import com.vidyo.bo.Tenant;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.service.ITenantService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.system.SystemService;

/**
 * Executes the task of sending Certificate Expiry notification<br>
 * to the Super Admin user.<br>
 * The schedule is configurable through the Quartz scheduler available in the
 * system.<br>
 * 
 * 
 * @author Ganesh
 * 
 */
public class CertificateExpiryReminderTasklet implements Tasklet, InitializingBean {

	/**
	 * 
	 */
	protected final Logger logger = LoggerFactory.getLogger(CertificateExpiryReminderTasklet.class);

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
	 * Sends out reminder emails to super admin when the certificate is about to
	 * expire.<br>
	 * The frequency of the reminder mails is configured in config xml.
	 */
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		List<Integer> tenantIds = tenantService.getAllTenantIds();
		if (tenantIds.isEmpty()) {
			logger.error("No Tenants configured in the system. Terminating the License Expiry Reminder Job");
			return RepeatStatus.FINISHED;
		}

		// Invoke the Script Utility to get the Cert expiry details
		ShellCapture output = null;
		try {
			output = ShellExecutor.execute("/opt/vidyo/bin/get_cert_expiry.sh");
		} catch (Exception e) {
			logger.error("Error while getting the certificate expiry date", e.getMessage());
			return RepeatStatus.FINISHED;
		}

		if (output == null || output.getExitCode() != 0) {
			logger.error("Failed to get the certificate expiry details, terminating the job");
			return RepeatStatus.FINISHED;
		}

		int daysLeft = getCertValidityDaysCount(output);

		logger.debug("Certificate Validity Days - {}", daysLeft);
		
		sendCertExpiryNotificaton(tenantIds.get(0), daysLeft);

		return RepeatStatus.FINISHED;
	}

	/**
	 * 
	 * @return
	 */
	private int getCertValidityDaysCount(ShellCapture output) {				
		// Only process if exit code is zero
		List<String> outPutLines = output.getStdOutLines();

		String[] validDays = null;
		for (String outVal : outPutLines) {
			if (outVal.contains("VALID_DAYS")) {
				validDays = outVal.split("=");
			}
		}

		if (validDays == null || validDays.length != 2) {
			logger.error("Failed to get the certificate expiry details, terminating the job - {}",
					Arrays.toString(validDays));
			return -1;
		}
		int daysLeft = 0;

		try {
			daysLeft = Integer.parseInt(validDays[1]);
		} catch (Exception e) {
			logger.error("Certificate Expiry date is not valid, terminating the job - {}", validDays[1]);
			return -1;
		}

		return daysLeft;

	}

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
	private void sendCertExpiryNotificaton(int tenantId, int daysLeft) {
		logger.debug("Days Left for Certificate Expiry - {}", daysLeft);

		// VP-5444 : provide certificate expiration alert 60 days before expiration
                
		if ((daysLeft == 60) || (daysLeft == 45) || (daysLeft == 30) || 
		    (daysLeft == 15) || ((daysLeft < 8) && (daysLeft >= 0))) {

			String toAddr = null;
			Configuration toAddrConfig = systemService.getConfiguration("SuperEmailTo");

			if (toAddrConfig != null && toAddrConfig.getConfigurationValue() != null
					&& toAddrConfig.getConfigurationValue().trim().length() > 0) {
				toAddr = toAddrConfig.getConfigurationValue().trim();
			}

			if (toAddr == null || toAddr.trim().length() <= 0) {
				logger.warn("Cannot send Certificate Expiry notification - No recepient email id available. Terminating the job.");
				return;
			}

			Configuration fromAddrConfig = systemService.getConfiguration("SuperEmailFrom");
			String fromAddr = null;

			if (fromAddrConfig != null && fromAddrConfig.getConfigurationValue() != null
					&& fromAddrConfig.getConfigurationValue().trim().length() > 0) {
				fromAddr = fromAddrConfig.getConfigurationValue().trim();
			} else {
				fromAddr = "support@vidyo.com";
			}

			Locale loc = getSuperLocale();

			String subject = messageSource.getMessage("cert.license.expiry.notification", null, "", loc);
			String ip = getLocalHostIP();
			String tenantUrl = getDefaultTenantUrl(tenantId);
			String content = MessageFormat.format(
					messageSource.getMessage("cert.license.about.to.expire.in.0.days", null, "", loc), daysLeft, ip
							+ "(URL: " + tenantUrl + ")");
			try {
				emailService.sendEmailSynchronous(fromAddr, toAddr, subject, content);
			} catch (Exception e) {
				logger.error(
						"Error while sending System License Expiry Notification. Days Left - {}, toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
						daysLeft, toAddr, ip, tenantUrl);
				logger.error("Exception while sending email ", e.getMessage());
				return;
			}
			logger.debug(
					"Certificate Expiry Notification Email was submitted to email service successfully. Certificate is about to expire in days - {} , toEmailId - {} , IPAddress - {}, Default Tenant URL - {}",
					daysLeft, toAddr, ip, tenantUrl);
		}
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
	private String getDefaultTenantUrl(int tenantId) {
		Tenant t = tenantService.getTenant(tenantId); // get default tenant
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

}
