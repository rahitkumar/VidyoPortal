package com.vidyo.db.security.token;

import java.util.Date;

/**
 * The abstraction used by {@link PersistentTokenBasedRememberMeServices} to
 * store the persistent login tokens for a user.
 * 
 * @see JdbcTokenRepositoryImpl
 * @see InMemoryTokenRepositoryImpl
 * 
 * @author Luke Taylor
 * @version $Id: VidyoPersistentTokenRepository.java,v 1.1 2013/01/04 18:56:29
 *          ganesh Exp $
 * @since 2.0
 */
public interface VidyoPersistentTokenRepository {

	void createNewToken(VidyoPersistentRememberMeToken token);

	void updateToken(String series, Date lastUsed, int tenantId, String endpointGUID);

	VidyoPersistentRememberMeToken getTokenForSeries(String seriesId, int tenantId, String endpointGUID);

	/**
	 * Delete specific token/eid combination for the user.
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointGUID
	 */
	void removeUserTokens(String username, int tenantId, String endpointGUID);

	/**
	 * Deletes all tokens associated with the user.
	 * 
	 * @param username
	 * @param tenantId
	 */
	int removeUserTokens(String username, int tenantId);

	/**
	 * Returns the count of active tokens excluding the endpointid from which
	 * the request is made.
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 * @return
	 */
	public int getCountByUserEid(String username, int tenantId, String endpointId);

	/**
	 * Returns the count of active tokens for the user
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 * @return
	 */
	// public int getCountByUsername(String username, int tenantId);

	/**
	 * Deletes all auth tokens of the user except for the Eid.
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 */
	public int removeAllUserTokensExceptCurrentEndpoint(String username, int tenantId, String endpointId);

	/**
	 * Deletes tokens which are inactive for the specified time.
	 * 
	 * @param inactiveDaysCount
	 */
	public int removeUserTokens(int inactiveDaysCount);

	/**
	 * Delete all user tokens for a specific tenant.
	 * 
	 * @param tenantId
	 * @return
	 */
	public int removeUserTokensByTenant(int tenantId);

	/**
	 * 
	 * @param username
	 * @param series
	 * @param token
	 * @param tenantId
	 * @return
	 */
	public int removeCurrentToken(String username, String series, String token, int tenantId);
}
