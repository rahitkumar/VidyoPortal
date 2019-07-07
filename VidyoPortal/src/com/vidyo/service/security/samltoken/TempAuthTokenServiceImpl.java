/**
 *
 */
package com.vidyo.service.security.samltoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.db.repository.security.samltoken.TempAuthToken;
import com.vidyo.db.repository.security.samltoken.TempAuthTokenRepository;

/**
 * @author ganesh
 *
 */
@Service("TempAuthTokenService")
public class TempAuthTokenServiceImpl implements TempAuthTokenService {

	/**
	 *
	 */
	@Autowired
	private TempAuthTokenRepository tempAuthTokenRepository;

	/**
	 * @param tempAuthTokenRepository
	 *            the tempAuthTokenRepository to set
	 */
	public void setTempAuthTokenRepository(TempAuthTokenRepository tempAuthTokenRepository) {
		this.tempAuthTokenRepository = tempAuthTokenRepository;
	}

	/**
	 * Saves the token in to the SAML token table with no user information
	 *
	 * @param token
	 * @return
	 */
	@Override
	public TempAuthToken saveToken(String token) {
		TempAuthToken tempAuthToken = new TempAuthToken();
		tempAuthToken.setToken(token);
		return tempAuthTokenRepository.save(tempAuthToken);
	}

	/**
	 * Associates the token returned from relay state to the authorized user. If
	 * the token returned from the relay state is tampered with, then the
	 * association won't happen.
	 *
	 * @param token
	 * @param memberId
	 * @return
	 */
	@Override
	public TempAuthToken updateTokenWithUser(String token, int memberId, String wsAuthToken) {
		TempAuthToken tempAuthToken = tempAuthTokenRepository.findOneByToken(token);
		if (tempAuthToken != null) {
			tempAuthToken.setMemberId(memberId);
			tempAuthToken.setAuthToken(wsAuthToken);
		}
		return tempAuthTokenRepository.save(tempAuthToken);
	}

	/**
	 * Delete the temporary token by the value.
	 *
	 * @param token
	 * @return
	 */
	@Override
	public int deleteToken(String token) {

		return 0;
	}

	/**
	 * Returns the token details with associated member id.
	 *
	 * @param token
	 */
	@Override
	public TempAuthToken getTokenDetails(String token) {
		TempAuthToken tempAuthToken = tempAuthTokenRepository.findOneByToken(token);
		return tempAuthToken;
	}

}
