/**
 * 
 */
package com.vidyo.db.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.framework.security.authentication.VidyoUserDetails;

/**
 * @author ganesh
 * 
 */
public class UserAuthDaoJdbcImpl extends JdbcDaoSupport implements UserAuthDao {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(UserAuthDaoJdbcImpl.class);

	/**
	 * 
	 */
	private String authoritiesByUsernameQuery;

	/**
     * 
     */
	private String usersByUsernameQuery;

	/**
	 * 
	 */
	private MappingSqlQuery<GrantedAuthority> authoritiesByUsernameMapping;

	/**
     * 
     */
	private MappingSqlQuery<UserDetails> usersByUsernameMapping;

	/**
	 * @param authoritiesByUsernameQuery
	 *            the authoritiesByUsernameQuery to set
	 */
	public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
		this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
	}

	/**
	 * @param usersByUsernameQuery
	 *            the usersByUsernameQuery to set
	 */
	public void setUsersByUsernameQuery(String usersByUsernameQuery) {
		this.usersByUsernameQuery = usersByUsernameQuery;
	}

	/**
	 * 
	 */
	@Override
	protected void initDao() throws Exception {
		usersByUsernameMapping = new UsersByUsernameMapping(getDataSource());
		authoritiesByUsernameMapping = new AuthoritiesByUsernameMapping(getDataSource());
	}

	/**
	 * 
	 * @param username
	 * @param tenantId
	 * @param authenticationConfig
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	@Override
	@Cacheable(cacheName = "userDataCache", keyGenerator = @KeyGenerator(name = "StringCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public UserDetails loadUserByUsername(@PartialCacheKey int tenantId, @PartialCacheKey String username,
			Authentication authenticationConfig) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Entering loadUserByUsername() of UserAuthDaoJdbcImpl " + tenantId + " username " + username);
		try {
			UserDetails superAdmin = loadSuperUserByUsername(username, authenticationConfig);
			Collection<? extends GrantedAuthority> roles = superAdmin.getAuthorities();
			for (GrantedAuthority role : roles) {
				if (role.getAuthority().equalsIgnoreCase("ROLE_SUPER")) {
					return superAdmin;
				}
			}
		} catch (Exception ignored) {
		}

		// regular user
		List<UserDetails> users = loadUsersByUsername(username, tenantId, authenticationConfig);

		if (users == null || users.size() == 0) {
			return null;
		}
		// contains no GrantedAuthority[]
		VidyoUserDetails user = (VidyoUserDetails) users.get(0);

		boolean needToGetAutorities = false;
		Collection<GrantedAuthority> roles = user.getAuthorities();
		for (GrantedAuthority role : roles) {
			if (role.getAuthority().equalsIgnoreCase("ROLE_HOLDER")) {
				// fake role - need to update
				needToGetAutorities = true;
				break;
			}
		}

		GrantedAuthority[] arrayAuths;
		if (needToGetAutorities) {
			Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

			dbAuthsSet.addAll(loadUserAuthorities(user.getUsername(), tenantId));

			List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

			// Add custom authorities empty method invocation removed

			if (dbAuths.size() == 0) {
				throw new UsernameNotFoundException("User" + username + "has no GrantedAuthority");
			}

			arrayAuths = (GrantedAuthority[]) dbAuths.toArray(new GrantedAuthority[dbAuths.size()]);

			return new VidyoUserDetails(username, user.getPassword(), user.getBak(), user.getSak(), user.isEnabled(),
					user.isAllowedToParticipate(), arrayAuths, user.isImportedUser(), user.getTenantID(),
					user.getMemberId(), user.getLangId(), user.getMemberName(), user.getEmailAddress(), user.getPak2(),user.getLastModifiedDateExternal());
		} else {
			arrayAuths = user.getAuthorities().toArray(new GrantedAuthority[user.getAuthorities().size()]);
			return new VidyoUserDetails(username, user.getPassword(), user.getBak(), user.getSak(), user.isEnabled(),
					user.isAllowedToParticipate(), arrayAuths, user.isImportedUser(), user.getTenantID(),
					user.getMemberId(), user.getLangId(), user.getMemberName(), user.getEmailAddress(), user.getPak2(),user.getLastModifiedDateExternal());
		}
	}

	/**
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	private UserDetails loadSuperUserByUsername(String username, Authentication auth) throws UsernameNotFoundException,
			DataAccessException {
		// Super admin is a one records under Default tenant (tenantID = 1)
		List<UserDetails> users = loadUsersByUsername(username, 1, auth);

		if (users.size() == 0) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}

		VidyoUserDetails user = (VidyoUserDetails) users.get(0);

		boolean needToGetAutorities = false;
		Collection<GrantedAuthority> roles = user.getAuthorities();
		for (GrantedAuthority role : roles) {
			if (role.getAuthority().equalsIgnoreCase("ROLE_HOLDER")) {
				// fake role - need to update
				needToGetAutorities = true;
				break;
			}
		}

		// TODO - check this logic
		GrantedAuthority[] arrayAuths;
		if (needToGetAutorities) {
			Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

			dbAuthsSet.addAll(loadUserAuthorities(user.getUsername(), 1));

			List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
			// Add custom authorities empty method invocation removed
			// addCustomAuthorities(user.getUsername(), dbAuths);

			if (dbAuths.size() == 0) {
				throw new UsernameNotFoundException("User" + username + " has no GrantedAuthority");
			}

			arrayAuths = (GrantedAuthority[]) dbAuths.toArray(new GrantedAuthority[dbAuths.size()]);

			return new VidyoUserDetails(username, user.getPassword(), user.getBak(), user.getSak(), user.isEnabled(),
					user.isAllowedToParticipate(), arrayAuths, user.isImportedUser(), user.getTenantID(),
					user.getMemberId(), user.getLangId(), user.getMemberName(), user.getEmailAddress(), user.getPak2(),user.getLastModifiedDateExternal());
		} else {
			arrayAuths = user.getAuthorities().toArray(new GrantedAuthority[user.getAuthorities().size()]);
			return new VidyoUserDetails(username, user.getPassword(), user.getBak(), user.getSak(), user.isEnabled(),
					user.isAllowedToParticipate(), arrayAuths, user.isImportedUser(), user.getTenantID(),
					user.getMemberId(), user.getLangId(), user.getMemberName(), user.getEmailAddress(), user.getPak2(),user.getLastModifiedDateExternal());
		}
	}

	/**
	 * 
	 * @param username
	 * @param tenantID
	 * @param auth
	 * @return
	 */
	protected List<UserDetails> loadUsersByUsername(String username, int tenantID, Authentication auth) {
		List<UserDetails> users = null;
		Object[] params = { username, String.valueOf(tenantID) };
		users = usersByUsernameMapping.execute(params);

		// TODO - check this logic
		VidyoUserDetails user = null;
		if (users.size() > 0) {
			user = (VidyoUserDetails) users.get(0);
		}

		if (users == null || users.size() == 0) {
			return null;
		}

		return users;
	}

	/**
	 * 
	 * @param attributes
	 * @return
	 * @throws NamingException
	 */
	/*
	 * private Object mapFromAttributes(int tenantID, Attributes attributes)
	 * throws NamingException { // will not do a attribute mapping now - just
	 * checking if user exists // and his role String userType = "Normal";
	 * List<TenantLdapAttributeMapping> attributeMappings =
	 * tenantLdapAttributes.getTenantLdapAttributeMapping(tenantID); for
	 * (TenantLdapAttributeMapping mapping : attributeMappings) { if
	 * (mapping.getVidyoAttributeName().equalsIgnoreCase("UserType")) { userType
	 * = this.attributesMapper.getLdapAttributeValue( attributes, mapping); } }
	 * return convertUserTypeToRole(userType); }
	 */

	/**
	 * 
	 * @param userType
	 * @return
	 */
	/*
	 * private String convertUserTypeToRole(String userType) { String rc =
	 * "ROLE_NORMAL"; if (userType.equalsIgnoreCase("Admin")) { rc =
	 * "ROLE_ADMIN"; } if (userType.equalsIgnoreCase("Operator")) { rc =
	 * "ROLE_OPERATOR"; } if (userType.equalsIgnoreCase("Normal")) { rc =
	 * "ROLE_NORMAL"; } if (userType.equalsIgnoreCase("VidyoRoom")) { rc =
	 * "ROLE_VIDYOROOM"; } if (userType.equalsIgnoreCase("Super")) { rc =
	 * "ROLE_SUPER"; } if (userType.equalsIgnoreCase("Executive")) { rc =
	 * "ROLE_EXECUTIVE"; } if (userType.equalsIgnoreCase("VidyoPanorama")) { rc
	 * = "ROLE_VIDYOPANORAMA"; } return rc; }
	 */

	/**
	 * 
	 * @param username
	 * @param tenantID
	 * @return
	 */
	protected List<GrantedAuthority> loadUserAuthorities(String username, int tenantID) {
		List<GrantedAuthority> authorities = null;
		Object[] params = { username, String.valueOf(tenantID) };
		authorities = authoritiesByUsernameMapping.execute(params);
		return authorities;
	}

	/**
	 * Query object to look up a user.
	 */
	private class UsersByUsernameMapping extends MappingSqlQuery<UserDetails> {
		protected UsersByUsernameMapping(DataSource ds) {
			super(ds, usersByUsernameQuery);
			SqlParameter[] types = new SqlParameter[] { new SqlParameter(Types.VARCHAR),
					new SqlParameter(Types.INTEGER) };
			setParameters(types);
			compile();
		}

		protected UserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
			String username = rs.getString(1);
			String password = rs.getString(2);
			String bak = rs.getString(3);
			String sak = rs.getString(4);
			boolean enabled = rs.getBoolean(5);
			boolean allowedToParticipate = rs.getBoolean(6);
			boolean importedUser = rs.getBoolean(7);
			int tenantID = rs.getInt(8);
			int memberId = rs.getInt(9);
			int langId = rs.getInt(10);
			String memberName = rs.getString(11);
			String emailAddress = rs.getString(12);
			String pak2 = rs.getString(13);
			String lastModifiedDateExternal = rs.getString(14);
			 
			UserDetails user = new VidyoUserDetails(username, password, bak, sak, enabled, allowedToParticipate,
					new GrantedAuthority[] { new SimpleGrantedAuthority("ROLE_HOLDER") }, importedUser, tenantID,
					memberId, langId, memberName, emailAddress, pak2,lastModifiedDateExternal);
			return user;
		}
	}

	/**
	 * Query object to look up a user's authorities.
	 */
	private class AuthoritiesByUsernameMapping extends MappingSqlQuery<GrantedAuthority> {
		protected AuthoritiesByUsernameMapping(DataSource ds) {
			super(ds, authoritiesByUsernameQuery);
			SqlParameter[] types = new SqlParameter[] { new SqlParameter(Types.VARCHAR),
					new SqlParameter(Types.INTEGER) };
			setParameters(types);
			compile();
		}

		protected GrantedAuthority mapRow(ResultSet rs, int rownum) throws SQLException {
			String roleName = rs.getString(2);
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
			return authority;
		}
	}

}
