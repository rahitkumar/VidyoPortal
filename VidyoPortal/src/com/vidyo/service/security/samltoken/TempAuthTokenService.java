/**
 *
 */
package com.vidyo.service.security.samltoken;

import com.vidyo.db.repository.security.samltoken.TempAuthToken;

/**
 * @author ganesh
 *
 */
public interface TempAuthTokenService {

	/**
	 * Saves the token in to the SAML token table with no user information
	 *
	 * @param token
	 * @return
	 */
	public TempAuthToken saveToken(String token);

	/**
	 * Associates the token returned from relay state to the authorized user. If
	 * the token returned from the relay state is tampered with, then the
	 * association won't happen.
	 *
	 * @param token
	 * @param memberId
	 * @return
	 */
	public TempAuthToken updateTokenWithUser(String token, int memberId, String wsAuthToken);

	/**
	 * Delete the temporary token by the value
	 *
	 * @param token
	 * @return
	 */
	public int deleteToken(String token);

	/**
	 * Returns the token details with associated member id.
	 * @param token
	 */
	public TempAuthToken getTokenDetails(String token);

}
