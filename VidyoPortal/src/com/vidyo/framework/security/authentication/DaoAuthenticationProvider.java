package com.vidyo.framework.security.authentication;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.naming.directory.DirContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.CACAuthentication;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.WsAuthentication;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.db.IMemberDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.service.ISystemService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.ws.authentication.AuthenticationRequest;
import com.vidyo.ws.authentication.AuthenticationResponse;
import com.vidyo.ws.authentication.AuthenticationServiceStub;

public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	protected static final Logger logger = LoggerFactory.getLogger(DaoAuthenticationProvider.class);

	private PasswordEncoder passwordEncoder;
	


	private UserDetailsService userDetailsService;

	private boolean includeDetailsObject = true;

	private ISystemService system;

	private List<String> ldapImportRoles = new ArrayList<>();
	
	private static final String UPDATE_PASSWORD_SCHEME = "update Member set password = ? where memberId = ?";
	
	/**
	 * JdbcTemplate is injected directly so that none of the
	 * other classed will have access to update password scheme
	 */
	private JdbcTemplate jdbcTemplate;
	
	private IMemberDao memberDao;

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setLdapImportRoles(String ldapImportRoles) {
		if (ldapImportRoles != null && !ldapImportRoles.isEmpty()) {
			String[] roles = ldapImportRoles.split(",");
			this.ldapImportRoles.addAll(Arrays.asList(roles));
		}
	}

	// ~ Methods
	// ========================================================================================================

	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;

		VidyoUserDetails localUserDetails = (VidyoUserDetails) userDetails;

		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		if (presentedPassword.equalsIgnoreCase("")) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		boolean needToAuth = false;

		List<MemberRoles> authForMemberRoles = this.system.getToRoles();
		List<String> authForRoles = new LinkedList<String>();
		for (MemberRoles r : authForMemberRoles) {
			authForRoles.add("ROLE_" + r.getRoleName().toUpperCase());
		}

		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		for (GrantedAuthority role : roles) {
			if (authForRoles.contains(role.getAuthority())) {
				needToAuth = true;
				break;
			}
		}

		// Does not have access to TenantId through any means - so using the
		// thread local to get the TenantId
		AuthenticationConfig authConfig= this.system.getAuthenticationConfig(TenantContext.getTenantId());
		Authentication auth = authConfig.toAuthentication();
		// VidyoDesktop will use BAK as a password

        if (passwordEncoder.matches(presentedPassword, localUserDetails.getBak())
                || passwordEncoder.matches(presentedPassword, localUserDetails.getSak())
                ||
                (localUserDetails.getPak2() != null && localUserDetails.getPak2().equalsIgnoreCase(presentedPassword))) {
            needToAuth = false;
            return;
        }
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		String requestPath = request.getRequestURL().toString();
		boolean desktopServiceInvocation = requestPath
				.contains("VidyoDesktopService");

		if (desktopServiceInvocation
				&& !passwordEncoder.matches(presentedPassword, localUserDetails.getSak())) {
			// If desktop service invocation, verify only within the local DB
			logger.warn("Invalid SAK from Endpoint {}, expected {}",
					presentedPassword, localUserDetails.getSak());
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}

		if (needToAuth && auth instanceof WsAuthentication) { // Authentication
																// using Web
																// Service
																// provided by
																// third party
			AuthenticationServiceStub authenticationService = null;
			try {
				authenticationService = this.system
						.getAuthenticationServiceStubWithAUTH((WsAuthentication) auth);

				AuthenticationRequest req = new AuthenticationRequest();
				req.setUsername(localUserDetails.getUsername());
				req.setPassword(presentedPassword);

				AuthenticationResponse resp = authenticationService.authentication(req);
				if (resp != null && !resp.getPassed()) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
			} catch (Exception e) {
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			} finally {
				if (authenticationService != null) {
					try {
						authenticationService.cleanup();
					} catch (AxisFault af) {
						// ignore
					}
				}
			}
			// If the webservices password comparison is successful update to new
			// Password Scheme
			if (localUserDetails.getPassword() != null && !localUserDetails.getPassword().contains(":")) {
				try {
					updatePasswordScheme(
							PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)),
							localUserDetails);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error(
							"Error while generating password hash (ws) - password is not migrated to new scheme",
							e);
				}
			}			

		}
		else if (needToAuth && auth instanceof WsRestAuthentication) {			
			try {
				//this should be only called from vidyoportalroot
				if(!system.restAuthenticationService((WsRestAuthentication)auth,localUserDetails.getUsername(),presentedPassword)){
				
						throw new BadCredentialsException(messages.getMessage(
								"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
					}
			}
					
		 catch (Exception e) {
				logger.error("Error occurred while validating user against rest api",e);
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
			if (localUserDetails.getPassword() != null && !localUserDetails.getPassword().contains(":")) {
				try {
					updatePasswordScheme(
							PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)),
							localUserDetails);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error(
							"Error while generating password hash (ws) - password is not migrated to new scheme",
							e);
				}
			}		
		}

		else if (needToAuth && auth instanceof LdapAuthentication) {
			LdapAuthentication ldapAuth = (LdapAuthentication) auth;
			DirContext ctx = null;
			DirContext userctx = null;
			try {
				// Some LDAP implementation allows null or empty password
				if (presentedPassword == null || presentedPassword.isEmpty()) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
				LdapContextSource contextSource = new LdapContextSource();
				contextSource.setUrl(ldapAuth.getLdapurl());
				contextSource.setUserDn(ldapAuth.getLdapusername());
				contextSource.setPassword(ldapAuth.getLdappassword());
				contextSource.setAnonymousReadOnly(true);
				contextSource.setPooled(false);
				contextSource.setCacheEnvironmentProperties(false);
				contextSource.afterPropertiesSet();
				ctx = contextSource.getContext(ldapAuth.getLdapusername(),
						ldapAuth.getLdappassword());

				String dn = SystemServiceImpl.getDnForUser(localUserDetails.getUsername(), ldapAuth,
						ctx);
				// Empty or null dn if the user is not in LDAP
				if (dn == null || dn.trim().isEmpty()) {
					throw new BadCredentialsException("LDAP didn't return the user dn -"
							+ localUserDetails.getUsername() + " TenantId -" + TenantContext.getTenantId());
				}
				userctx = contextSource.getContext(dn, presentedPassword);

			} catch (Exception e) {
				logger.error("Addition Auth check Exception from LDAP: {}", e.getMessage());
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			} finally {
				LdapUtils.closeContext(ctx);
				LdapUtils.closeContext(userctx);
			}
			// If the ldap password comparison is successful update to new
			// Password Scheme
			if (localUserDetails.getPassword() != null && !localUserDetails.getPassword().contains(":")) {
				try {
					updatePasswordScheme(
							PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)),
							localUserDetails);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error(
							"Error while generating password hash (ldap) - password is not migrated to new scheme",
							e);
				}
			}

		} // if it is cac authenticated and ldap enabled in the cac authentication section
		else if (needToAuth && auth instanceof CACAuthentication && "1".equalsIgnoreCase(authConfig.getCacldapflag())) {
			CACAuthentication ldapAuth = (CACAuthentication) auth;
			DirContext ctx = null;
			DirContext userctx = null;
			try {
				// Some LDAP implementation allows null or empty password
				if (presentedPassword == null || presentedPassword.isEmpty()) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
				LdapContextSource contextSource = new LdapContextSource();
				contextSource.setUrl(ldapAuth.getLdapurl());
				contextSource.setUserDn(ldapAuth.getLdapusername());
				contextSource.setPassword(ldapAuth.getLdappassword());
				contextSource.setAnonymousReadOnly(true);
				contextSource.setPooled(false);
				contextSource.setCacheEnvironmentProperties(false);
				contextSource.afterPropertiesSet();
				ctx = contextSource.getContext(ldapAuth.getLdapusername(),
						ldapAuth.getLdappassword());

				String dn = SystemServiceImpl.getDnForUserForCAC(localUserDetails.getUsername(), ldapAuth,
						ctx);
				// Empty or null dn if the user is not in LDAP
				if (dn == null || dn.trim().isEmpty()) {
					throw new BadCredentialsException("LDAP didn't return the user dn -"
							+ localUserDetails.getUsername() + " TenantId -" + TenantContext.getTenantId());
				}
				userctx = contextSource.getContext(dn, presentedPassword);

			} catch (Exception e) {
				logger.error("Addition Auth check Exception from LDAP: {}", e.getMessage());
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			} finally {
				LdapUtils.closeContext(ctx);
				LdapUtils.closeContext(userctx);
			}
			// If the ldap password comparison is successful update to new
			// Password Scheme
			if (localUserDetails.getPassword() != null && !localUserDetails.getPassword().contains(":")) {
				try {
					updatePasswordScheme(
							PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)),
							localUserDetails);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error(
							"Error while generating password hash (ldap) - password is not migrated to new scheme",
							e);
				}
			}

		}else {
			logger.debug("user-" + localUserDetails + "- presented passwd-"
					+ presentedPassword);

			// The password is in new scheme, so compare using the password hash
			// utility
			if (localUserDetails.getPassword().contains(":")) {
				try {
					if (!PasswordHash.validatePassword(presentedPassword,
							localUserDetails.getPassword())) {
						throw new BadCredentialsException(
								messages.getMessage(
										"AbstractUserDetailsAuthenticationProvider.badCredentials",
										"Bad credentials"));
					}
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new BadCredentialsException(
							messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.badCredentials",
									"Bad credentials"));
				}
			} else {
				// Old password scheme check
				if (!passwordEncoder
						.matches(presentedPassword, localUserDetails.getPassword())) {
					throw new BadCredentialsException(
							messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.badCredentials",
									"Bad credentials"));
				}
				// If password comparison is successful update to new Password Scheme
				updatePasswordScheme(presentedPassword, localUserDetails);
			}
		}
	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}
	
	/**
	 * 
	 * @param presentedPassword
	 * @param userDetails
	 */
	public void updatePasswordScheme(String presentedPassword, VidyoUserDetails userDetails) {
		try {
			String newPassword = PasswordHash.createHash(presentedPassword);
			int updateCount = jdbcTemplate.update(UPDATE_PASSWORD_SCHEME, new Object[] {newPassword, userDetails.getMemberId()});
			memberDao.updateMember(TenantContext.getTenantId(), userDetails.getUsername() != null ? userDetails
					.getUsername().toLowerCase(Locale.ENGLISH) : null);						
			logger.error("Update Count for Password Scheme Change {}", updateCount);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error("Updating to new password scheme failed ", e);
		}		
	}

	/**
	 * 
	 */
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser = null;
		loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		if (loadedUser == null) {
			throw new UsernameNotFoundException("UserNotFound through any Authentication Mechanism");
		}
		return loadedUser;
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate
	 * default.
	 * 
	 * @param passwordEncoder
	 *            The passwordEncoder to use
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return super.supports(authentication);
	}

	protected PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	protected boolean isIncludeDetailsObject() {
		return includeDetailsObject;
	}

	/**
	 * Determines whether the UserDetails will be included in the
	 * <tt>extraInformation</tt> field of a thrown BadCredentialsException.
	 * Defaults to true, but can be set to false if the exception will be used
	 * with a remoting protocol, for example.
	 * 
	 * @param includeDetailsObject

	 */
	public void setIncludeDetailsObject(boolean includeDetailsObject) {
		this.includeDetailsObject = includeDetailsObject;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param memberDao the memberDao to set
	 */
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

}