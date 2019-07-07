package com.vidyo.framework.security.authentication;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class VidyoPortalUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        setAuthenticationSuccessHandler(successHandler);
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        setAuthenticationFailureHandler(failureHandler);
    }
}
