package com.vidyo.service.email;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.email.SmtpConfig;
import com.vidyo.db.ISystemDao;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.service.SystemServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import java.util.Properties;

public class EmailServiceImpl implements EmailService {

	protected final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class.getName());

    public static final String DEFAULT_SMTP_ENCODING = "UTF-8";

    private ISystemDao systemDao;
	private JmsTemplate jmsTemplate;
	private Queue emailMessageMQqueue;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setEmailMessageMQqueue(Queue emailMessageMQqueue) {
		this.emailMessageMQqueue = emailMessageMQqueue;
	}

    public void setSystemDao(ISystemDao systemDao) {
        this.systemDao = systemDao;
    }

    public void setSuperSmtpConfig(SmtpConfig config) {
        setSmtpConfig(0, config);
    }

    public SmtpConfig getSuperSmtpConfig() {
        return getSmtpConfig(0);
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    public void sendEmailSynchronous(String from, String to, String subject, String body) {
        sendEmail(0, from, to, subject, body);
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    public void sendEmailSynchronous(SimpleMailMessage message) {
        sendEmail(0, message);
    }

    private void setSmtpConfig(int tenantId, SmtpConfig config) {
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_HOST, config.getHost());
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_PORT, ""+config.getPort());
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_USERNAME, config.getUsername());
        if (!SystemServiceImpl.PASSWORD_UNCHANGED.equals(config.getPassword())) {
            systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_PASSWORD, VidyoUtil.encrypt(config.getPassword()));
        }
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_SECURITY_TYPE, config.getSecurityType());
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_TRUST_ALL_CERTS, (config.isTrustAllCerts() ? "1" : "0"));
        systemDao.updateConfiguration(tenantId, SmtpConfig.SMTP_EMAILS_ON, (config.isEmailsOn() ? "on" : "off"));
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    private void sendEmail(int tenantId, String from, String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        sendEmail(tenantId, message);
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    private void sendEmail(int tenantId, SimpleMailMessage message) {
        SmtpConfig config = getSmtpConfig(tenantId);
        sendEmail(config, message);
    }

    private SmtpConfig getSmtpConfig(int tenantId) {

        String smtpHost = getConfigValue(tenantId, SmtpConfig.SMTP_HOST);
        String smtpPort = getConfigValue(tenantId, SmtpConfig.SMTP_PORT);
        String smtpUsername = getConfigValue(tenantId, SmtpConfig.SMTP_USERNAME);
        String smtpPassword = getConfigValue(tenantId, SmtpConfig.SMTP_PASSWORD);
        String smtpSecure = getConfigValue(tenantId, SmtpConfig.SMTP_SECURITY_TYPE);
        String smtpTrustAllCerts = getConfigValue(tenantId, SmtpConfig.SMTP_TRUST_ALL_CERTS);
        String smtpEmailsOn =  getConfigValue(tenantId, SmtpConfig.SMTP_EMAILS_ON);

        SmtpConfig config = new SmtpConfig();
        if (!StringUtils.isBlank(smtpHost)) {
            config.setHost(smtpHost);
        }
        if (!StringUtils.isBlank(smtpPort)) {
            config.setPort(Integer.parseInt(smtpPort));
        }
        if (!StringUtils.isBlank(smtpUsername)) {
            config.setUsername(smtpUsername);
        }
        if (!StringUtils.isBlank(smtpPassword)) {
            config.setPassword(VidyoUtil.decrypt(smtpPassword));
        }
        if (!StringUtils.isBlank(smtpSecure)) {
            config.setSecurityType(smtpSecure);
        }
        if (!StringUtils.isBlank(smtpTrustAllCerts)) {
            if ("1".equals(smtpTrustAllCerts)) {
                config.setTrustAllCerts(true);
            }
        }
        if (!StringUtils.isBlank(smtpEmailsOn)) { // if not in db, defaults are used, see SmtpConfig
            if ("off".equalsIgnoreCase(smtpEmailsOn)) {
                config.setEmailsOn(false);
            } else {
                config.setEmailsOn(true);
            }
        }

        return config;
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    public void sendTestEmail(String from, String to, SmtpConfig config) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Test Message");
        message.setText("This is a test message (security: " + config.getSecurityType() + ") to verify SMTP server configuration.");
        sendEmail(config, message);
    }

	/**
	 * This method may throw runtime exceptions.
	 */
    private void sendEmail(SmtpConfig config, SimpleMailMessage message) {

        if (!config.isEmailsOn()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Email Notifications OFF: not sending email");
            }
            return;
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setDefaultEncoding(EmailServiceImpl.DEFAULT_SMTP_ENCODING);

        if (StringUtils.isBlank(config.getHost())) {
            mailSender.setHost(SmtpConfig.DEFAULT_SMTP_HOST);
        } else {
            mailSender.setHost(config.getHost());
        }

        if (StringUtils.isBlank(""+config.getPort())) {
            mailSender.setPort(JavaMailSenderImpl.DEFAULT_PORT);
        } else {
            mailSender.setPort(config.getPort());
        }

        Properties secureProps = new Properties();

        if (SmtpConfig.SMTP_SECURITY_TYPE_STARTTLS.equals(config.getSecurityType())) { // ESMTP
            mailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
            secureProps.setProperty("mail.smtp.starttls.enable","true");
            secureProps.setProperty("mail.smtp.starttls.required", "true");
            if (!StringUtils.isBlank(config.getUsername())) {
                secureProps.setProperty("mail.smtp.auth", "true");
            }
            if (config.isTrustAllCerts()) {
                secureProps.setProperty("mail.smtp.socketFactory.class", "com.vidyo.service.email.AlwaysTrustSSLSocketFactory");
            }
        } else if (SmtpConfig.SMTP_SECURITY_TYPE_SSL_TLS.equals(config.getSecurityType())) { // SSL
            mailSender.setProtocol("smtps");
            secureProps.setProperty("mail.transport.protocol", "smtps");
            if (config.isTrustAllCerts()) {
                secureProps.setProperty("mail.smtps.socketFactory.class", "com.vidyo.service.email.AlwaysTrustSSLSocketFactory");
            } else {
                secureProps.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }

            if (!StringUtils.isBlank(config.getUsername())) {
                secureProps.setProperty("mail.smtps.auth", "true");
            }
        } else { // SMTP
            mailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
            if (!StringUtils.isBlank(config.getUsername())) {
                secureProps.setProperty("mail.smtp.auth", "true");
            }
        }

        mailSender.setJavaMailProperties(secureProps);

        if (!StringUtils.isBlank(config.getUsername())) {
            mailSender.setUsername(config.getUsername());
        }

        if (!StringUtils.isBlank(config.getPassword())) {
            mailSender.setPassword(config.getPassword());
        }

        mailSender.send(message);
    }

	@Override
	public void sendEmailAsynchronous(final SimpleMailMessage m) {
		if (logger.isDebugEnabled()) {
			logger.debug("Sending Email Message to MQ {}", m.toString());
		}

        String smtpEmailsOn =  getConfigValue(0, SmtpConfig.SMTP_EMAILS_ON);
         if (!StringUtils.isBlank(smtpEmailsOn)) {
            if ("off".equalsIgnoreCase(smtpEmailsOn)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Email Notifications OFF: not sending email");
                }
                return;
            }
         }
		try {
			jmsTemplate.send(
					this.emailMessageMQqueue.getQueueName(),
					new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							return session.createObjectMessage(m);
						}
					}
			);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Cannot send Email Message to MQ {}", e.getMessage());
			}
		}
	}
	
	private String getConfigValue(int tenantID, String configurationName){
		Configuration config = systemDao.getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();
		
	}

}