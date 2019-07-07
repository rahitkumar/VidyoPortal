/**
 * SeatLicenseExpiredFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.ucclients;

public class SeatLicenseExpiredFaultException extends java.lang.Exception {

	private static final long serialVersionUID = 1317060674546L;

	private com.vidyo.portal.ucclients.SeatLicenseExpiredFault faultMessage;

	public SeatLicenseExpiredFaultException() {
		super("SeatLicenseExpiredFaultException");
	}

	public SeatLicenseExpiredFaultException(java.lang.String s) {
		super(s);
	}

	public SeatLicenseExpiredFaultException(java.lang.String s,
			java.lang.Throwable ex) {
		super(s, ex);
	}

	public SeatLicenseExpiredFaultException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(
			com.vidyo.portal.ucclients.SeatLicenseExpiredFault msg) {
		faultMessage = msg;
	}

	public com.vidyo.portal.ucclients.SeatLicenseExpiredFault getFaultMessage() {
		return faultMessage;
	}
}
