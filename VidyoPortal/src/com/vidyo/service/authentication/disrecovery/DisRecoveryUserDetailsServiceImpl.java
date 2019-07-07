/**
 * 
 */
package com.vidyo.service.authentication.disrecovery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vidyo.db.authentication.disrecovery.DisRecoveryUserAuthDao;

/**
 * @author ganesh
 *
 */
public class DisRecoveryUserDetailsServiceImpl implements UserDetailsService {

	/**
	 * 
	 */
	private DisRecoveryUserAuthDao disRecoveryUserAuthDao;

	/**
	 * @param disRecoveryUserAuthDao
	 */
	public DisRecoveryUserDetailsServiceImpl(DisRecoveryUserAuthDao disRecoveryUserAuthDao) {
		this.disRecoveryUserAuthDao = disRecoveryUserAuthDao;
	}

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return disRecoveryUserAuthDao.loadUserByUsername(username);
	}

}
