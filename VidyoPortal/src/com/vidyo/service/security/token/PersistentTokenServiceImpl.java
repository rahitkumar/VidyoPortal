/**
 *
 */
package com.vidyo.service.security.token;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.db.IUserDao;
import com.vidyo.db.security.token.VidyoPersistentRememberMeToken;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;
import com.vidyo.framework.security.utils.TokenGenerator;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;

/**
 * @author ganesh
 *
 */
@Service("PersistentTokenService")
public class PersistentTokenServiceImpl implements PersistentTokenService {

	/**
	 *
	 */
	protected static final Logger logger = LoggerFactory.getLogger(PersistentTokenServiceImpl.class);

	/**
	 * EndpointService
	 */
	private EndpointService endpointService;

	/**
	 *
	 */
	private VidyoPersistentTokenRepository persistentTokenRepository;

	/**
	 *
	 */
	private TokenGenerator tokenGenerator;

	/**
	 *
	 */
	private IUserDao userDao;

	/**
	 * @param tokenGenerator
	 *            the tokenGenerator to set
	 */
	public void setTokenGenerator(TokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}

	/**
	 * @param endpointService
	 *            the endpointService to set
	 */
	public void setEndpointService(EndpointService endpointService) {
		this.endpointService = endpointService;
	}

	/**
	 * @param persistentTokenRepository the persistentTokenRepository to set
	 */
	public void setPersistentTokenRepository(VidyoPersistentTokenRepository persistentTokenRepository) {
		this.persistentTokenRepository = persistentTokenRepository;
	}

	/**
	 * Creates a new persistent token for the User with a validaitiy time.
	 *
	 * @param tokenCreationRequest
	 * @return
	 */
	public TokenCreationResponse createPersistentToken(TokenCreationRequest tokenCreationRequest) {
		TokenCreationResponse tokenCreationResponse = new TokenCreationResponse();
		// Check endpoint only if it is present if request
		if (StringUtils.isNotBlank(tokenCreationRequest.getEndpointId()) ) {
			// Validate if EndpointId is bound to User
			Endpoint endpoint = endpointService.getEndpointDetails(tokenCreationRequest.getEndpointId());

			if (endpoint == null || endpoint.getMemberID() != tokenCreationRequest.getMemberId()) {
				tokenCreationResponse.setStatus(TokenCreationResponse.ENDPOINT_NOT_BOUND_TO_USER);
				tokenCreationResponse.setMessageId("endpoint.not.bound.to.user");
				return tokenCreationResponse;
			}
		}
		// Delete any old tokens if present for the guid and user combination
		// By default it is true. Only specific requests which doesn't need old tokens be deleted will set it to false
		if(tokenCreationRequest.isDeleteOldTokens()) {
			persistentTokenRepository.removeUserTokens(tokenCreationRequest.getUsername(),
					tokenCreationRequest.getTenantId(), tokenCreationRequest.getEndpointId());
		}

		VidyoPersistentRememberMeToken persistentToken = new VidyoPersistentRememberMeToken(
				tokenCreationRequest.getUsername(), tokenCreationRequest.getTenantId(),
				tokenGenerator.generateSeriesData(), tokenGenerator.generateTokenData(), null, null,
				tokenCreationRequest.getValidityTime(), tokenCreationRequest.getEndpointId());

		try {
			persistentTokenRepository.createNewToken(persistentToken);
		} catch (DataAccessException e) {
			logger.error("Failed to save persistent token", e.getMessage());
			tokenCreationResponse.setStatus(TokenCreationResponse.HTTP_INTERNAL_ERROR);
			tokenCreationResponse.setMessageId("internal.server.error");
			return tokenCreationResponse;
		}

		String token = tokenGenerator.encodeToken(new String[] { persistentToken.getSeries(),
				persistentToken.getTokenValue() });
		tokenCreationResponse.setToken(token);
		tokenCreationResponse.setStatus(TokenCreationResponse.SUCCESS);
		//userDao.deletePak2(tokenCreationRequest.getTenantId(), tokenCreationRequest.getMemberId());
		return tokenCreationResponse;

	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
