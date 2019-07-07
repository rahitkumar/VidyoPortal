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
public class UserDesktopAuthDaoJdbcImpl extends UserAuthDaoJdbcImpl {

	@Override
	public UserDetails loadUserByUsername(int tenantId, String username,
			Authentication authenticationConfig)
			throws UsernameNotFoundException, DataAccessException {		
		return super.loadUserByUsername(tenantId, username, authenticationConfig);
	}

	/**
	 * 
	 */
	@Override
	public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
		super.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
	}

	/**
	 * 
	 */
	@Override
	public void setUsersByUsernameQuery(String usersByUsernameQuery) {
		super.setUsersByUsernameQuery(usersByUsernameQuery);
	}

	/**
	 * 
	 */
	@Override
	protected void initDao() throws Exception {
		super.initDao();
	}

}
