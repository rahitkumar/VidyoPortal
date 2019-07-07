package com.vidyo.service.email;

import com.vidyo.bo.email.SmtpConfig;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    public SmtpConfig getSuperSmtpConfig();
    public void setSuperSmtpConfig(SmtpConfig config);

    public void sendEmailSynchronous(String from, String to, String subject, String body);
    public void sendEmailSynchronous(SimpleMailMessage message);

    public void sendTestEmail(String from, String to, SmtpConfig config);

	// Email Message to MQ
	public void sendEmailAsynchronous(SimpleMailMessage a);
}