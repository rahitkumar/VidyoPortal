package com.vidyo.framework.filters.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClickjackFilter implements Filter {
	
	private String mode = "DENY";

	private String csp = "frame-ancestors 'self'"; //https://jira.vidyo.com/browse/VPTL-7291 - Add CSP in the header.

	@Override
	public void destroy() {

	}

	/**
     * Add X-FRAME-OPTIONS response header to tell IE8 (and any other browsers who
     * decide to implement) not to display this content in a frame. For details, please
     * refer to http://blogs.msdn.com/sdl/archive/2009/02/05/clickjacking-defense-in-ie8.aspx.
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
        res.addHeader("X-FRAME-OPTIONS", mode);
        // https://jira.vidyo.com/browse/VPTL-7291 - add the CSP in the header
        res.addHeader("Content-Security-Policy", csp );
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configMode = filterConfig.getInitParameter("mode");
        if ( configMode != null ) {
            mode = configMode;
        }
	}

}
