/**
 * GeneralFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.ucclients;

public class GeneralFaultException extends java.lang.Exception {

	private static final long serialVersionUID = 1317060674515L;

	private com.vidyo.portal.ucclients.GeneralFault faultMessage;

	public GeneralFaultException() {
		super("GeneralFaultException");
	}

	public GeneralFaultException(java.lang.String s) {
		super(s);
	}

	public GeneralFaultException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public GeneralFaultException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.vidyo.portal.ucclients.GeneralFault msg) {
		faultMessage = msg;
	}

	public com.vidyo.portal.ucclients.GeneralFault getFaultMessage() {
		return faultMessage;
	}
}
