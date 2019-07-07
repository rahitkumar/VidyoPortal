package com.vidyo.framework.filters.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionExpirationFilter extends GenericFilterBean implements InitializingBean, Ordered {

    private String redirectPage;
    private String expiredParam;

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public void setExpiredParam(String expiredParam) {
        this.expiredParam = expiredParam;
    }

    public void afterPropertiesSet() {
        Assert.notNull(redirectPage, "redirectPage must be specified");
        Assert.notNull(expiredParam, "expiredParam must be specified");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
    	FilterInvocation fi = new FilterInvocation(request, response, chain);

        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
            String ajax = request.getHeader("X-Requested-With");
            if (ajax != null && ajax.equalsIgnoreCase("XMLHttpRequest")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                String referer = request.getHeader("Referer");
                if (referer == null) {
                    response.sendRedirect(redirectPage);
                } else {
                    response.sendRedirect(redirectPage + expiredParam);    
                }
            }
        }

        if (fi.getResponse().isCommitted()) {
            return;
        }

        chain.doFilter(request, response);
    }

    public int getOrder() {
        return 250;
    }
}
