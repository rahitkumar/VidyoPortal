/**
 * 
 */
package com.vidyo.db.authentication;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vidyo.bo.authentication.Authentication;

/**
 * @author ganesh
 * 
 */
public interface UserAuthDao {

	/**
	 * 
	 * @param tenantId
	 * @param username
	 * @param authenticationConfig
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(int tenantId, String username,
			Authentication authenticationConfig)
			throws UsernameNotFoundException, DataAccessException;

}
