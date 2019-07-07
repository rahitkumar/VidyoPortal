package com.vidyo.framework.filters.security;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SamlAuthResolverFilter extends GenericFilterBean implements InitializingBean, Ordered {

    private ISystemService systemService;

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Integer tenantId = TenantContext.getTenantId();
        com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(tenantId).toAuthentication();
        boolean isSamlAuth = authType instanceof SamlAuthentication;

        if (!isSamlAuth) {
            String redirect = this.getServletContext().getContextPath();
            if(redirect.isEmpty()) {
            	redirect = "/";
            }
        	response.sendRedirect(redirect);
            return;
        }
		
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		
		if (fi.getResponse().isCommitted()) {
            return;
        }

        chain.doFilter(request, response);

	}
	
	@Override
	public int getOrder() {
        return 2;
    }

}
