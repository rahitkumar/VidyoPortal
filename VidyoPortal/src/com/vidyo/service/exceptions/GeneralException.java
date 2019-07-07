package com.vidyo.service.exceptions;

public class GeneralException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1801220767077023129L;

	public GeneralException() {
        super("Internal error");
    }

    /**
    * Create a new <code>GeneralException</code>
    * with the specified detail message and no root cause.
    * @param msg the detail message
    */
    public GeneralException(String msg) {
        super(msg);
    }

    /**
    * Create a new <code>GeneralException</code>
    * with the specified detail message and the given root cause.
    * @param msg the detail message
    * @param cause the root cause
    */
    public GeneralException(String msg, Throwable cause) {
        super(msg, cause);
    }

}