/**
 * 
 */
package com.vidyo.framework.security.authentication.license;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.vidyo.framework.security.authentication.VidyoUserGuestDetails;

/**
 * @author ganesh
 *
 */
public class LicenseServiceAuthenticationProvider extends
		DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		VidyoUserGuestDetails localUserDetails = (VidyoUserGuestDetails) userDetails;
		String presentedPassword = authentication.getCredentials().toString();
		if (!getPasswordEncoder().matches(presentedPassword, localUserDetails.getPak())
				&& !getPasswordEncoder().matches(presentedPassword,localUserDetails.getPak2())) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}

	}
	
}
