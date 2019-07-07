package com.vidyo.framework.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

public class VidyoPortalAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	protected static final Logger logger = LoggerFactory.getLogger(VidyoPortalAuthenticationSuccessHandler.class.getName());
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
		
		String session_lang = ServletRequestUtils.getStringParameter(request, "session_lang", "");
        if (session_lang.equalsIgnoreCase("on")) {
            HttpSession sess = request.getSession();
            sess.setAttribute("session_lang", "on");
        }
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if (savedRequest == null || request.getRequestURL().indexOf("ajax") > 0) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
