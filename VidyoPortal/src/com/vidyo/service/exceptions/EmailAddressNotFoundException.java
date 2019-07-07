package com.vidyo.service.exceptions;

public class EmailAddressNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 379491905277352720L;

    public EmailAddressNotFoundException() {
        super("Email address is not found");
    }

    /**
    * Create a new <code>EmailAddressNotFoundException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public EmailAddressNotFoundException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>EmailAddressNotFoundException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public EmailAddressNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}