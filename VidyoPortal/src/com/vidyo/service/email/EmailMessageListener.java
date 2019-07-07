package com.vidyo.service.email;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class EmailMessageListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(EmailMessageListener.class.getName());

	private EmailService emailService;

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void onMessage(Message message) {
		try {
			SimpleMailMessage m = (SimpleMailMessage)((ActiveMQObjectMessage) message).getObject();

			if (logger.isDebugEnabled()) {
				logger.debug("Got message - {}", m.toString());
			}

			this.emailService.sendEmailSynchronous(m);
		} catch (JMSException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
	}

}
