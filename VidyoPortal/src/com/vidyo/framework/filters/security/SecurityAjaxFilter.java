/**
 * 
 */
package com.vidyo.framework.filters.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * This Filter will idenitify the type of request [Ajax/Form Submission]
 * and send xml repsonse or redirect based on the type of the request.
 * @author Ganesh
 *
 */
public class SecurityAjaxFilter extends OncePerRequestFilter {

	/**
	 *
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// If its normal form submit request, proceed with the filter chain
		if (!isAjaxRequest(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		RedirectResponseWrapper redirectResponseWrapper = new RedirectResponseWrapper(
				response);

		filterChain.doFilter(request, redirectResponseWrapper);

		if (redirectResponseWrapper.getRedirect() != null) {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=utf-8");

			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			String status = "200";
			String redirectURL = redirectResponseWrapper.getRedirect();
			StringBuffer content = new StringBuffer();
			if (!redirectURL.contains("login_error")) {
                preventSessionFixation(request);
				content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
				content.append("<LoginResponse>\n");
				content.append("<status>").append(status).append("</status>\n");
				content.append("<message>" + "success" + "</message>\n");
                content.append("<role>" +  getUserRole(request) +"</role>\n");
				content.append("</LoginResponse>");
				//content.append("url:");
				//content.append(redirectURL);
			} else {
				status = "401";
				content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
				content.append("<LoginResponse>\n");
				if (((AuthenticationException) request
						.getSession()
						.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
						.getMessage().contains("Bad credentials")) {
					status = "401";
				} else {
					status = "1401";
				}
				content.append("<status>").append(status).append("</status>\n");
				content.append("<message>").append(((AuthenticationException) request
						.getSession()
						.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
						.getMessage()).append("</message>\n");
				content.append("</LoginResponse>");
			}
			response.getOutputStream().write(
					content.toString().getBytes("UTF-8"));
		}

	}

    public void preventSessionFixation(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        HttpSession existingSession = request.getSession(false);
        if (existingSession == null) {
            return;
        }

        Enumeration<String> attributeNames = existingSession.getAttributeNames();
        Map<String, Object> attributes = new HashMap<String, Object>();

        if (attributeNames != null) {
            String key = "";
            while (attributeNames.hasMoreElements()) {
                key = (String) attributeNames.nextElement();
                attributes.put(key, existingSession.getAttribute(key));
            }
        }

        existingSession.invalidate();
        HttpSession newSession = request.getSession(true);

        if (attributes.size() > 0 ) {
            for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
                newSession.setAttribute(attribute.getKey(), attribute.getValue());
            }
        }
    }

    public String getUserRole(HttpServletRequest request) {
        String rc="";
        HttpSession session = request.getSession();
        if (session != null) {
            SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (securityContext != null) {
                Authentication auth = securityContext.getAuthentication();
                if (auth != null) {
                    Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
                    for (GrantedAuthority role : roles) {
                        rc = role.getAuthority(); // hope user has only one role. in case of multiple - return last one
                    }
                }
            }
        }
        return rc;
    }

	/**
	 * Returns true if the request is Ajax
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").equalsIgnoreCase(
						"XMLHttpRequest");
	}
}