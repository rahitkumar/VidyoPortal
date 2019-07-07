/**
 *
 */
package com.vidyo.framework.filters.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class CorsFilter extends OncePerRequestFilter {

	protected static final String ORIGIN = "Origin";

	//VPTL-7694 - adding whitelisted origins configures from the xml.
	private List<String> allowedOrigins = new ArrayList<>();

	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getHeader(ORIGIN) != null) {
			String origin = request.getHeader(ORIGIN);
			logger.info("Request origin->" + origin);
			for(String allowedOrigin : allowedOrigins) {
				if(allowedOrigin.contains(origin)) {
					response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
					response.setHeader("Access-Control-Allow-Credentials", "false");
					response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, X-Requested-With, soapaction, authorization");
					response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
				}
			}
		}
		if (request.getMethod().equals("OPTIONS")) {
			try {
				response.getWriter().print("OK");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

}
