package com.vidyo.framework.security.web;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ITenantService;

import org.apache.commons.codec.binary.Base64;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.security.token.VidyoPersistentRememberMeToken;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;

/**
 * {@link RememberMeServices} implementation based on Barry Jaspan's <a
 * href="http://jaspan.com/improved_persistent_login_cookie_best_practice"
 * >Improved Persistent Login Cookie Best Practice</a>.
 * 
 * There is a slight modification to the described approach, in that the
 * username is not stored as part of the cookie but obtained from the persistent
 * store via an implementation of {@link PersistentTokenRepository}. The latter
 * should place a unique constraint on the series identifier, so that it is
 * impossible for the same identifier to be allocated to two different users.
 * 
 * <p>
 * User management such as changing passwords, removing users and setting user
 * status should be combined with maintenance of the user's persistent tokens.
 * </p>
 * 
 * <p>
 * Note that while this class will use the date a token was created to check
 * whether a presented cookie is older than the configured
 * <tt>tokenValiditySeconds</tt> property and deny authentication in this case,
 * it will to delete such tokens from the storage. A suitable batch process
 * should be run periodically to remove expired tokens from the database.
 * </p>
 * 
 * @author Luke Taylor
 * @version $Id: PersistentTokenBasedRememberMeServices.java,v 1.8 2014/03/20
 *          16:10:23 vgoghari Exp $
 * @since 2.0
 */
public class PersistentTokenBasedRememberMeServices extends AbstractRememberMeServices {
	
	private VidyoPersistentTokenRepository tokenRepository = null;
	private SecureRandom random;

	public static final int DEFAULT_SERIES_LENGTH = 16;
	public static final int DEFAULT_TOKEN_LENGTH = 16;

	private int seriesLength = DEFAULT_SERIES_LENGTH;
	private int tokenLength = DEFAULT_TOKEN_LENGTH;
	
	private ITenantService tenantService;

	public PersistentTokenBasedRememberMeServices() throws Exception {
		random = SecureRandom.getInstance("SHA1PRNG");
	}

	/**
	 * Locates the presented cookie data in the token repository, using the
	 * series id. If the data compares successfully with that in the persistent
	 * store, a new token is generated and stored with the same series. The
	 * corresponding cookie value is set on the response.
	 * 
	 * @param cookieTokens
	 *            the series and token values
	 * 
	 * @throws RememberMeAuthenticationException
	 *             if there is no stored token corresponding to the submitted
	 *             cookie, or if the token in the persistent store has expired.
	 * @throws InvalidCookieException
	 *             if the cookie doesn't have two tokens as expected.
	 * @throws CookieTheftException
	 *             if a presented series value is found, but the stored token is
	 *             different from the one presented.
	 */
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Entering processAutoLoginCookie() of PersistentTokenBasedRememberMeServices");
		if (cookieTokens.length < 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
					+ Arrays.asList(cookieTokens) + "'");
		}
		final String eid = cookieTokens.length > 2 ? cookieTokens[0]:null;
		final String presentedSeries = cookieTokens.length > 2 ? cookieTokens[1]:cookieTokens[0];
		final String presentedToken = cookieTokens.length > 2 ? cookieTokens[2]:cookieTokens[1];

		// TODO - cache the tokens
		VidyoPersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries,
				TenantContext.getTenantId(), eid);

		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete all logins for this user
			// and throw an
			// exception to warn them.
			tokenRepository.removeUserTokens(token.getUsername(), TenantContext.getTenantId());
			throw new RememberMeAuthenticationException("Invalid Token");
		}
		
		// Get the session expiration period for the Tenant
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		long tokenExpPeriod = (long) tenantConfiguration.getSessionExpirationPeriod() * 60 * 60 * 1000;
		if (tenantConfiguration.getSessionExpirationPeriod() != 0) {
			if ((token.getCreationTime().getTime() + tokenExpPeriod) < System.currentTimeMillis()) {
				logger.error("Auth Token Expired - " + token.getUsername());
				throw new RememberMeAuthenticationException("Auth Token Expired");
			}

		}

		// Token also matches, so login is valid. Update the token value,
		// keeping the *same* series number.
		/*logger.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '"
					+ token.getSeries() + "'");

		VidyoPersistentRememberMeToken existingToken = new VidyoPersistentRememberMeToken(token.getUsername(),
				TenantContext.getTenantId(), token.getSeries(), token.getTokenValue(), new Date(),
				token.getCreationTime(), token.getValiditySecs(), token.getEndpointGUID());

		try {
			tokenRepository.updateToken(existingToken.getSeries(), new Date(), TenantContext.getTenantId(),
					token.getEndpointGUID());
			// addCookie(existingToken, request, response);
		} catch (DataAccessException e) {
			logger.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException("Autologin failed due to data access problem");
		}*/

		UserDetails user = null;
		try {
			user = getUserDetailsService().loadUserByUsername(token.getUsername());
		} catch (Exception e) {
            logger.warn("Exception from loadUserByUsername: ", e);
			logger.warn("Autologin failed due to user details issue " + token.getUsername());
			throw new RememberMeAuthenticationException("Autologin failed due to user details issue");
		}
		logger.debug("User successfully retrieved for Token based authentication {}", user);
		// Set a request attribute to indicate authentication was performed
		// using token
		request.setAttribute("AUTH_USING_TOKEN", true);
		request.setAttribute("ENDPOINT_ID", token.getEndpointGUID());
		request.setAttribute("TOKEN_PART", token.getTokenValue());
		request.setAttribute("SERIES_PART", token.getSeries());
		logger.debug("Exiting processAutoLoginCookie() of PersistentTokenBasedRememberMeServices");
		return user;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * Creates a new persistent login token with a new series number, stores the
	 * data in the persistent token repository and adds the corresponding cookie
	 * to the response.
	 * 
	 */
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();

		logger.debug("Creating new persistent login for user " + username);

		/*
		 * VidyoPersistentRememberMeToken persistentToken = new
		 * VidyoPersistentRememberMeToken(username, TenantContext.get(),
		 * generateSeriesData(), generateTokenData(), new Date(), new Date(),
		 * 1000); try { tokenRepository.createNewToken(persistentToken);
		 * addCookie(persistentToken, request, response); } catch
		 * (DataAccessException e) {
		 * logger.error("Failed to save persistent token ", e);
		 * 
		 * }
		 */
	}

	protected String generateSeriesData() {
		byte[] newSeries = new byte[seriesLength];
		random.nextBytes(newSeries);
		return new String(Base64.encodeBase64(newSeries));
	}

	protected String generateTokenData() {
		byte[] newToken = new byte[tokenLength];
		random.nextBytes(newToken);
		return new String(Base64.encodeBase64(newToken));
	}

	/*
	 * private void addCookie(VidyoPersistentRememberMeToken token,
	 * HttpServletRequest request, HttpServletResponse response) { setCookie(new
	 * String[] {token.getSeries(),
	 * token.getTokenValue()},getTokenValiditySeconds(), request, response); }
	 */

	public void setTokenRepository(VidyoPersistentTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	public void setSeriesLength(int seriesLength) {
		this.seriesLength = seriesLength;
	}

	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}
}
