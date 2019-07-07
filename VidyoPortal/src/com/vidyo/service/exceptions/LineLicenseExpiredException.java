package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

import org.springframework.security.core.AuthenticationException;

public class LineLicenseExpiredException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public LineLicenseExpiredException() {
        super(rs.getString("line.license.expired"));
    }

    /**
    * Create a new <code>LineLicenseExpiredException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public LineLicenseExpiredException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>LineLicenseExpiredException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public LineLicenseExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}