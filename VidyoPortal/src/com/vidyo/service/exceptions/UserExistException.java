package com.vidyo.service.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.util.ResourceBundle;

public class UserExistException extends AuthenticationException {

	static ResourceBundle rs;
	static {
		rs = ResourceBundle.getBundle("messages");
	}


	public UserExistException() {
		super(rs.getString("duplicate.user.name"));
	}

	/**
	 * Create a new <code>UserExistException</code>
	 * with the specified detail message and no root cause.
	 * @param msg the detail message
	 */
	public UserExistException(String msg) {
		super(msg);
	}

	/**
	 * Create a new <code>UserExistException</code>
	 * with the specified detail message and the given root cause.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public UserExistException(String msg, Throwable cause) {
		super(msg, cause);
	}

}