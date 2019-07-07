/**
 * 
 */
package com.vidyo.service.conference.response;

import com.vidyo.framework.service.BaseServiceResponse;

/**
 * @author Ganesh
 *
 */
public class JoinConferenceResponse extends BaseServiceResponse {
	
	/**
	 * 
	 */
	public static final int INVALID_ROOM = 6001;
	
	/**
	 * 
	 */
	public static final int WRONG_PIN = 6002;
	
	/**
	 * 
	 */
	public static final int REMOTE_JOIN_CONF_FAILED = 6003;
	
	/**
	 * 
	 */
	public static final int RESOURCE_NOT_AVAILABLE_FOR_CONF = 6004;

	/**
	 * 
	 */
	public static final int OUT_OF_PORTS_ERROR = 6005;

	/**
	 * 
	 */
	public static final int JOIN_CONF_FAILED = 6006;

	/**
	 * 
	 */
	public static final int REMOTE_USER_NOT_FOUND = 6007;
	
	/**
	 * 
	 */
	public static final int REMOTE_CONF_NOT_ALLOWED = 6008;
	
	/**
	 * 
	 */
	public static final int ROOM_LOCKED = 6009;
	
	/**
	 * 
	 */
	public static final int INVALID_MEMBER = 6010;
	
	/**
	 * 
	 */
	public static final int ROOM_DISABLED = 6011;
	
	/**
	 * 
	 */
	public static final int INVALID_GUEST = 6012;	

	/**
	 * 
	 */
	public static final int GUESTS_NOT_ALLOWED = 6013;
	
	/**
	 * 
	 */
	public static final int GUESTS_NOT_LINKED = 6014;
	
	/**
	 * 
	 */
	public static final int ROOM_CAPACITY_REACHED = 6015;
	
	/**
	 * 
	 */
	public static final int REQUIRED_PARAMS_NOT_PRESENT = 6016;
	
	/**
	 * 
	 */
	public static final int CANNOT_CONTROL_ROOM = 6017;
	
	/**
	 * 
	 */
	public static final int INVITED_MEMBER_NOT_ONLINE = 6018;	

}
