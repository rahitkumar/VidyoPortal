/**
 * 
 */
package com.vidyo.service.member.response;

import com.vidyo.framework.service.BaseServiceResponse;


public class MemberManagementResponse extends BaseServiceResponse {

	/**
	 * 
	 */
	public static final int DUPLICATE_USER_NAME = 9001;
		
	/**
	 * 
	 */
	public static final int DUPLICATE_ROOM_NUMBER = 9002;
	
	/**
	 * 
	 */
	public static final int INVALID_USERNAME_LENGTH = 9003;
	
	/**
	 * 
	 */
	public static final int INVALID_USERNAME_MATCH = 9004;
	
	/**
	 * 
	 */
	public static final int INVALID_EMAIL_MATCH = 9005;
	
	/**
	 * 
	 */
	public static final int SEAT_LICENSE_EXPIRED = 9006;
	
	/**
	 * 
	 */
	public static final int CANNOT_ADD_MEMBER_SEAT_LICENSE_EXPIRED = 9007;
	
	/**
	 * 
	 */
	public static final int USER_CREATED_BUT_EMAIL_SEND_FAILED = 9008;

	public static final int PASSWORD_ENCODING_FAILED = 9009;
	
	public static final int INVALID_LOCATION_TAG = 9010;
	
	public static final int INVALID_GROUP_NAME = 9011;
	
	public static final int INVALID_ROLE_NAME = 9012;
	
	public static final int INVALID_PROXY_NAME = 9013;
	
	public static final int CANNOT_ADD_MEMBER_EXECUTIVE_LICENSE_EXPIRED = 9014;
	
	public static final int CANNOT_ADD_MEMBER_PANORAMA_LICENSE_EXPIRED = 9015;
	
	public static final int EXTN_MATCHES_SCHEDULEROOM_PREFIX = 9016;
	
	public static final int LINE_LICENSE_EXPIRED = 9006;

    public static final int PASSWORD_DOES_NOT_MEET_REQUIREMENTS = -1;
    public static final int PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED = -2;
    public static final int PASSWORD_ENCODING_EXCEPTION = -3;
	

}
