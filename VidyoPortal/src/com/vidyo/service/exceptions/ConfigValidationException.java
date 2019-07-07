package com.vidyo.service.exceptions;

import java.util.List;

public class ConfigValidationException extends RuntimeException {

	private static final long serialVersionUID = 7808757551974985610L;

	private List<String> validationErrors;

	public ConfigValidationException() {
		super("Config validation exception");
	}

	public ConfigValidationException(String msg) {
		super(msg);
	}

	public ConfigValidationException(List<String> msgs) {
		super(msgs.toString());
		validationErrors = msgs;
	}

	public ConfigValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ConfigValidationException(List<String> msgs, Throwable cause) {
		super(msgs.toString(), cause);
		validationErrors = msgs;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}
}
