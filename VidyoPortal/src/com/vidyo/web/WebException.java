package com.vidyo.web;

/**
 * 
 * @author Hojin
 * 
 * This can be used for all controller level common exception
 * 
 */
public class WebException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which just sends out the Error Message and suppresses the
	 * Exception Trace
	 * 
	 * @param message
	 */
	public WebException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public WebException(String message, Throwable t) {
		super(message, t);
	}

}
