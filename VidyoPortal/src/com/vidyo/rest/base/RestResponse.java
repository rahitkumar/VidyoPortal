package com.vidyo.rest.base;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {
	private static final long serialVersionUID = 831204385170781116L;

	private Status status = new Status();
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatusCode(String code) {
		this.status.setCode(code);
	}

	public void setStatusMessage(String message){
		this.status.setMessage(message);
	}

	public void setStatus(String code, String message) {
		this.setStatusCode(code);
		this.setStatusMessage(message);
	}


}
