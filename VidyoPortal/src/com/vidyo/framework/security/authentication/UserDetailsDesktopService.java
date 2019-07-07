package com.vidyo.framework.security.authentication;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.authentication.Authentication;
import com.vidyo.db.authentication.UserAuthDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;

public class UserDetailsDesktopService extends UserDetailsService {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(UserDetailsDesktopService.class);

	/**
	 * 
	 */
	private ISystemService system;

	/**
	 * 
	 */
	private UserAuthDao userAuthDao;

	/**
	 * @param userAuthDao
	 *            the userAuthDao to set
	 */
	public void setUserAuthDao(UserAuthDao userAuthDao) {
		this.userAuthDao = userAuthDao;
	}

	/**
	 * 
	 */
	public void setSystem(ISystemService system) {
		this.system = system;
	}

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Entering loadUserByUsername(String username) of UserDetailsDesktopService");
		Authentication authConfig = system.getAuthenticationConfig(TenantContext.getTenantId()).toAuthentication();

		UserDetails userDetails = userAuthDao.loadUserByUsername(TenantContext.getTenantId(), username, authConfig);

		if (userDetails != null && userDetails instanceof VidyoUserDetails) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			((VidyoUserDetails) userDetails).setSourceIP(request.getRemoteAddr());
		}

		logger.debug("Exiting loadUserByUsername(String username) of UserDetailsDesktopService");
		return userDetails;
	}

}