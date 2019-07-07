/**
 * 
 */
package com.vidyo.framework.service;

/**
 * 
 * @author Ganesh
 * 
 */
public class ServiceException extends RuntimeException {

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
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public ServiceException(String message, Throwable t) {
		super(message, t);
	}

}
