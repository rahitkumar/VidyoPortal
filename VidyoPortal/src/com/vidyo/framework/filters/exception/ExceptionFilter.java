/**
 * 
 */
package com.vidyo.framework.filters.exception;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Ganesh
 * 
 */
public class ExceptionFilter implements Filter {


	/** Logger for this class and subclasses */
	protected final static Logger logger = LoggerFactory.getLogger(ExceptionFilter.class.getName());

	@Override
	public void destroy() {
		// leave it blank, no implementation

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				logger.error(" Cause: " + (cause.getMessage() != null ? cause.getMessage() : "no message"));
			}
			ServletException servletException = new ServletException(e);
			throw servletException;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// nothing to initialize as of now

	}

}
