package com.vidyo.service.exceptions;

public class NotificationEmailAddressNotConfiguredException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 379491905277352720L;

    public NotificationEmailAddressNotConfiguredException() {
        super("Notification email address is not configured");
    }

    /**
    * Create a new <code>NotificationEmailAddressNotConfiguredException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NotificationEmailAddressNotConfiguredException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NotificationEmailAddressNotConfiguredException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NotificationEmailAddressNotConfiguredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}