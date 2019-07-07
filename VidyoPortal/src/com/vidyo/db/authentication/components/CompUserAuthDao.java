/**
 * 
 */
package com.vidyo.db.authentication.components;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author ganesh	
 *
 */
public interface CompUserAuthDao {

	/**
	 * 
	 * @param compType
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(String compType, String username)
			throws UsernameNotFoundException;
}
