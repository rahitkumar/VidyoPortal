package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

import org.springframework.security.core.AuthenticationException;

public class SeatLicenseExpiredException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public SeatLicenseExpiredException() {
        super(rs.getString("seat.license.expired"));
    }

    /**
    * Create a new <code>CancelInvitationException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public SeatLicenseExpiredException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>CancelInvitationException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public SeatLicenseExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}