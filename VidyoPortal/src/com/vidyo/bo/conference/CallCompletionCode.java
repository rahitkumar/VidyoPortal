package com.vidyo.bo.conference;

/**
 *  0 is the default status, and it is updated to:
 *  1 or 3,  based on info from vidyomanager status updates
 *  2, based on info from portal actions such as disconnect and disconnect all
 *
 *  0 – Call completion reason not available
 *  1 – User disconnected the call
 *  2 – Call disconnected by  admin/Operator/Room Owner/Moderator
 *  3 – Call disconnected due to NW failure, unresponsive clients
 */

public enum CallCompletionCode {
	UNKNOWN("0"),
	BY_SELF("1"),
	BY_SOMEONE_ELSE("2"),
	ERROR("3");

	private String code = "0";

	CallCompletionCode(String code) {
		this.code = code;
	}

	public String toString() {
		return this.code;
	}
}
