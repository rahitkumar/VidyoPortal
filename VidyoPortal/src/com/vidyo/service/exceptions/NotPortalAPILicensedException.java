package com.vidyo.service.exceptions;

import java.util.ResourceBundle;

import org.springframework.security.core.AuthenticationException;

public class NotPortalAPILicensedException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ResourceBundle rs;
    static {
        rs = ResourceBundle.getBundle("messages");
    }


    public NotPortalAPILicensedException() {
        super(rs.getString("portal.api.license.missing"));
    }

    /**
    * Create a new <code>NotAdminAPILicensedException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public NotPortalAPILicensedException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>NotAdminAPILicensedException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public NotPortalAPILicensedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}