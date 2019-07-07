package com.vidyo.bo.email;


import com.vidyo.utils.VendorUtils;

public class SmtpConfig {

    public static final String DEFAULT_SMTP_HOST = "localhost";
    public static final int DEFAULT_SMTP_PORT = 25;

    public static final String SMTP_HOST = "smtpHost";
    public static final String SMTP_PORT = "smtpPort";
    public static final String SMTP_SECURITY_TYPE = "smtpSecure";
    public static final String SMTP_USERNAME = "smtpUsername";
    public static final String SMTP_PASSWORD = "smtpPassword";

    public static final String SMTP_SECURITY_TYPE_NONE = "NONE";
    public static final String SMTP_SECURITY_TYPE_STARTTLS = "STARTTLS";
    public static final String SMTP_SECURITY_TYPE_SSL_TLS = "SSL_TLS";

    public static final String SMTP_TRUST_ALL_CERTS = "smtpSslTrustAll";
    public static final String SMTP_EMAILS_ON = "MAIL_NOTIFICATIONS"; // make config name same as in DISA 56D branch

    private String host = SmtpConfig.DEFAULT_SMTP_HOST;
    private int port = SmtpConfig.DEFAULT_SMTP_PORT;
    private String securityType = SmtpConfig.SMTP_SECURITY_TYPE_NONE;
    private String username = "";
    private String password = "";
    private boolean trustAllCerts = false;
    private boolean emailsOn = true; // default

    public SmtpConfig() {
        // override default for DISA, no emails by default
        if (VendorUtils.isDISA()) {
            setEmailsOn(false);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        if (SmtpConfig.SMTP_SECURITY_TYPE_STARTTLS.equals(securityType) ||
                SmtpConfig.SMTP_SECURITY_TYPE_SSL_TLS.equals(securityType)) {
            this.securityType = securityType;
        } else {
            this.securityType = SmtpConfig.SMTP_SECURITY_TYPE_NONE;
        }
    }


    public boolean isTrustAllCerts() {
        return trustAllCerts;
    }

    public void setTrustAllCerts(boolean trustAllCerts) {
        this.trustAllCerts = trustAllCerts;
    }

    public boolean isEmailsOn() {
        return emailsOn;
    }

    public void setEmailsOn(boolean emailsOn) {
        this.emailsOn = emailsOn;
    }


}
