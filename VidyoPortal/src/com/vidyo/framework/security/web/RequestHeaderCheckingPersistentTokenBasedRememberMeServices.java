/**
 * 
 */
package com.vidyo.framework.security.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

/**
 * @author Ganesh
 * 
 */
public class RequestHeaderCheckingPersistentTokenBasedRememberMeServices extends
		PersistentTokenBasedRememberMeServices {
	
	private String credentialsCharset = "UTF-8";

	/**
	 * Default Constructor
	 * 
	 * @throws Exception
	 */
	public RequestHeaderCheckingPersistentTokenBasedRememberMeServices()
			throws Exception {
		super();
	}
	
    public void setCredentialsCharset(String credentialsCharset) {
    	Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
		this.credentialsCharset = credentialsCharset;
	}
    
    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
		return credentialsCharset;
	} 	

	protected boolean rememberMeRequested(HttpServletRequest request,
			String parameter) {
		String value = request.getHeader(DEFAULT_PARAMETER);
		boolean val = value != null && Boolean.parseBoolean(value) ? Boolean
				.parseBoolean(value) : super.rememberMeRequested(request,
				parameter);
		return val;
	}

	protected String extractRememberMeCookie(HttpServletRequest request) {
		//String cookieValue = request
		//		.getHeader(SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		String header = request.getHeader("Authorization");
        if (logger.isDebugEnabled()) {
            logger.debug("Authorization header: " + header);
        }
        String token = null;
        if ((header != null) && header.startsWith("Basic ")) {
            byte[] base64Token = null;            
			try {
				base64Token = header.substring(6).getBytes("UTF-8");
				token = new String(base64Token);
			} catch (UnsupportedEncodingException e) {
				logger.error("Cannot decode the token - "+ base64Token + " decode base64 - "+ token);
				return null;
			}
        }
		return token;
	}

	/*protected void setCookie(String[] tokens, int maxAge,
			HttpServletRequest request, HttpServletResponse response) {
		// Do not set the cookie
		//String cookieValue = encodeCookie(tokens);
		//response.setHeader(SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, cookieValue);
	}*/

}
