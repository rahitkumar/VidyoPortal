/**
 * 
 */
package com.vidyo.service.security.token.response;

import com.vidyo.framework.service.BaseServiceResponse;

/**
 * @author ganesh
 *
 */
public class TokenCreationResponse extends BaseServiceResponse {
	
	/**
	 * 
	 */
	public static final int ENDPOINT_NOT_BOUND_TO_USER = 9001;
	
	/**
	 * 
	 */
	private String token;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
