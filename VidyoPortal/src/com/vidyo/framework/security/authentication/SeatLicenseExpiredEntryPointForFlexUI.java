package com.vidyo.framework.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import com.vidyo.service.exceptions.SeatLicenseExpiredException;

public class SeatLicenseExpiredEntryPointForFlexUI extends org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	if(authException instanceof SeatLicenseExpiredException){
            response.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, authException.getMessage());
	    }
    	else{
    		/*
    		 * Removed Header WWW-Authenticate 
    		 */
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    	}
    }
}
