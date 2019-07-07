/**
 * 
 */
package com.vidyo.framework.service;

/**
 * Base Class for all service responses. Contains a statuc code and brief
 * message specific to the operation.
 * 
 * @author Ganesh
 * 
 */
public class BaseServiceResponse {
	
	/**
	 * 
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 
	 */
	public static final int HTTP_SUCCESS = 200;
	
	/**
	 * 
	 */
	public static final int HTTP_UNAUTHORIZED = 401;
	
	/**
	 * 
	 */
	public static final int HTTP_INTERNAL_ERROR = 500;	
	
	/**
	 * 
	 */
	private int status;

	/**
	 * 
	 */
	private String message;
	
	/**
	 * 
	 */
	private String messageId;
	
	/**
	 * Display error message for webservices
	 */
	private String displayMessage;

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the displayMessage
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}

	/**
	 * @param displayMessage the displayMessage to set
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

}
