package com.vidyo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.util.ResourceBundle;

public class ExtensionExistException  extends AuthenticationException {

	static ResourceBundle rs;
	static {
		rs = ResourceBundle.getBundle("messages");
	}


	public ExtensionExistException() {
		super(rs.getString("duplicate.room.number"));
	}

	/**
	 * Create a new <code>ExtensionExistException</code>
	 * with the specified detail message and no root cause.
	 * @param msg the detail message
	 */
	public ExtensionExistException(String msg) {
		super(msg);
	}

	/**
	 * Create a new <code>ExtensionExistException</code>
	 * with the specified detail message and the given root cause.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public ExtensionExistException(String msg, Throwable cause) {
		super(msg, cause);
	}


}