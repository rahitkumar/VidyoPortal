package com.vidyo.framework.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;

public class DaoAuthenticationProviderWithLicenseCheck extends DaoAuthenticationProvider {

    //~ Instance fields ================================================================================================

    
    //~ Methods ========================================================================================================

    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {
	    super.additionalAuthenticationChecks(userDetails, authentication);
    	
    	//filter out members who are not allowed to participate in actual vidyo communication
        VidyoUserDetails localUserDetails = (VidyoUserDetails)userDetails;

        if (!localUserDetails.isAllowedToParticipate()) {
	        throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }
}