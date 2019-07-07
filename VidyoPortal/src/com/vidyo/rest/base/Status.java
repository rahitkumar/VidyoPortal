package com.vidyo.rest.base;

import java.io.Serializable;

public class Status implements Serializable {

	private static final long serialVersionUID = 3359536764790510874L;

	private String code = "200";
	private String message = "OK";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
