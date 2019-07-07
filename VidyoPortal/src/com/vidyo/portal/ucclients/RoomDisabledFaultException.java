/**
 * RoomDisabledFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.vidyo.portal.ucclients;

public class RoomDisabledFaultException extends java.lang.Exception {

	private static final long serialVersionUID = 1317060674530L;

	private com.vidyo.portal.ucclients.RoomDisabledFault faultMessage;

	public RoomDisabledFaultException() {
		super("RoomDisabledFaultException");
	}

	public RoomDisabledFaultException(java.lang.String s) {
		super(s);
	}

	public RoomDisabledFaultException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public RoomDisabledFaultException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.vidyo.portal.ucclients.RoomDisabledFault msg) {
		faultMessage = msg;
	}

	public com.vidyo.portal.ucclients.RoomDisabledFault getFaultMessage() {
		return faultMessage;
	}
}
