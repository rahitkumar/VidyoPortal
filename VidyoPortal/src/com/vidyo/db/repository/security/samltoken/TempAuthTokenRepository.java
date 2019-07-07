/**
 *
 */
package com.vidyo.db.repository.security.samltoken;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ganesh
 *
 */
public interface TempAuthTokenRepository extends JpaRepository<TempAuthToken, Integer> {

	/**
	 *
	 * @param token
	 * @return
	 */
	public TempAuthToken findOneByToken(String token);

}
