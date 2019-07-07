/**
 * 
 */
package com.vidyo.framework.security.authentication.components;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vidyo.db.authentication.components.CompUserAuthDao;
import com.vidyo.framework.security.authentication.UserDetailsService;

/**
 * @author ganesh
 *
 */
public class CompUserDetailsService extends UserDetailsService {
	
	/**
	 * 
	 */
	private String compType;

	/**
	 * 
	 */
	private CompUserAuthDao compUserAuthDao;

	/**
	 * 
	 * @param compUserAuthDao
	 */
	public void setCompUserAuthDao(CompUserAuthDao compUserAuthDao) {
		this.compUserAuthDao = compUserAuthDao;

	}

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.debug("Invoking loadUserByUsername() of CompUserDetailsService");
		UserDetails userDetails = compUserAuthDao.loadUserByUsername(compType,
				username);
		logger.debug("Hashcode {}, UserDetails {} {}", userDetails.hashCode(),
				userDetails.getUsername(), userDetails.getPassword());
		logger.debug("After invoking loadUserByUsername() of CompUserDetailsService");
		User user = null;
		if (userDetails != null) {
			user = new User(userDetails.getUsername(),
					userDetails.getPassword(), userDetails.getAuthorities());
		}

		return user;
	}

	/**
	 * @param compType the compType to set
	 */
	public void setCompType(String compType) {
		this.compType = compType;
	}

}
