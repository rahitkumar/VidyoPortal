package com.vidyo.framework.executors.exception;

public class ShellExecutorException extends Exception {

	private static final long serialVersionUID = -4117781976022829549L;

	public ShellExecutorException(String msg) {
		super(msg);
	}
	
	public ShellExecutorException(String msg, Exception e) {
		super(msg, e);
	}
	
}
