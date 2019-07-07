/**
 * 
 */
package com.vidyo.service.security.token;

import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;

/**
 * @author ganesh
 * 
 */
public interface PersistentTokenService {

	/**
	 * Creates a new persistent token for the User with a validaitiy time.
	 * 
	 * @param tokenCreationRequest
	 * @return
	 */
	public TokenCreationResponse createPersistentToken(TokenCreationRequest tokenCreationRequest);

}
