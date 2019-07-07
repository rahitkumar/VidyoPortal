/**
 * 
 */
package com.vidyo.framework.filters.security;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Ganesh
 * 
 */
public class RedirectResponseWrapper extends HttpServletResponseWrapper {

	/**
	 * 
	 * @param httpServletResponse
	 */
	public RedirectResponseWrapper(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
	}

	/**
	 * 
	 * @return
	 */
	private String redirect;

	/**
	 * 
	 * @return
	 */
	public String getRedirect() {
		return redirect;
	}

	/**
	 * 
	 */
	public void sendRedirect(String string) throws IOException {
		this.redirect = string;
	}
}
