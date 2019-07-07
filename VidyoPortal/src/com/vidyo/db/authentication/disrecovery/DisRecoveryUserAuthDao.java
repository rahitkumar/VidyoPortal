/**
 * 
 */
package com.vidyo.db.authentication.disrecovery;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author ganesh
 *
 */
public interface DisRecoveryUserAuthDao {
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException;
}
