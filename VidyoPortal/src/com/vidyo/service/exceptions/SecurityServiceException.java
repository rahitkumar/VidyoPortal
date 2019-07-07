package com.vidyo.service.exceptions;

public class SecurityServiceException extends Exception {
	public SecurityServiceException(String msg) {
		super(msg);
	}

	public SecurityServiceException(String msg, Exception e) {
		super(msg, e);
	}
}
