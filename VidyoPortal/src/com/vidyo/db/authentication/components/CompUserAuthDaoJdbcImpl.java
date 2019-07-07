/**
 * 
 */
package com.vidyo.db.authentication.components;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.vidyo.framework.security.utils.VidyoUtil;

/**
 * AuthDao implementation for components connecting to Portal.
 * 
 * @author ganesh
 *
 */
public class CompUserAuthDaoJdbcImpl extends JdbcDaoImpl implements
		CompUserAuthDao {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(CompUserAuthDaoJdbcImpl.class);

	private boolean usernameBasedPrimaryKey = true;

	/**
	 * Overridden to decrypt the password in DB
	 */
	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (!usernameBasedPrimaryKey) {
			returnUsername = username;
		}
		logger.debug("user:" + returnUsername + " password from DB:"
				+ userFromUserQuery.getPassword());
		String password = null;
		password = VidyoUtil.decrypt(userFromUserQuery.getPassword());
		if (StringUtils.isEmpty(password)) {
			throw new RuntimeException(
					"Failed to decrypt the password value, throwing exception to avoid caching");
		}
		return new User(returnUsername, password,
				userFromUserQuery.isEnabled(), true, true, true,
				combinedAuthorities);
	}

	@Override
	@Cacheable(cacheName = "componentsUserDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public UserDetails loadUserByUsername(String compType, String username)
			throws UsernameNotFoundException {
		return loadUserByUsername(username);

	}

}
