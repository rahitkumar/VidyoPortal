/**
 * 
 */
package com.vidyo.interceptors.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vidyo.service.SecurityServiceImpl;

/**
 * Interceptor which decides whether to allow secuirty/maintenance calls or not. 
 * 
 * @author ganesh
 *
 */
public class SecurityAccessInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(SecurityAccessInterceptor.class);

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME
				+ "private/PRIVILEGED_MODE");
		boolean privMode = privModeFileHandle.exists();
		if (!privMode) {
			logger.warn("Security Settings modifications attempted in Non-Privileged Mode "					
					+ " Request -" + request.getRequestURI());
		}
		return privMode;
	}

}
