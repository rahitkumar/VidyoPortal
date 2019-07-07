package com.vidyo.service.gateway.response;

import com.vidyo.framework.service.BaseServiceResponse;

public class JoinFromLegacyServiceResponse extends BaseGatewayServiceResponse {

	public static final int INVALID_TO_NAME_EXT = 5001;
	public static final int ROOM_LOCKED = 5002;
	public static final int ROOM_DISABLED = 5003;
	public static final int ROOM_PIN_REQUIRED = 5004;
	public static final int ROOM_PIN_INVALID = 5005;
	public static final int FAILED_TO_CONNECT_CALL = 5006;
    public static final int ROOM_FULL = 5007;
    public static final int CANNOT_CALL_TENANT = 5008;
	public static final int NO_LINES_AVAILABLE = 5009;

	public static final int P2P_OFFLINE = 6000;
	public static final int P2P_BUSY = 6001;

    private String joinStatus = "NONE";

	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
}
