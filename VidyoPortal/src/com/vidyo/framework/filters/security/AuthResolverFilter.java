package com.vidyo.framework.filters.security;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.utils.UserAgentUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthResolverFilter extends GenericFilterBean implements InitializingBean, Ordered {

    private ISystemService systemService;
    private String samlUrl;

    public void setSamlUrl(String samlUrl) {
        this.samlUrl = samlUrl;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        String bak = ServletRequestUtils.getStringParameter(request, "bak", "");

	    Integer tenantId = TenantContext.getTenantId();
		
	    com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(tenantId).toAuthentication();
	    
        boolean isSamlAuth = authType instanceof SamlAuthentication;
        
        // Old VidyoRoom will have SWB in User-Agent. we don't authenticate VidyoRoom against SAML
		String userAgent = request.getHeader("User-Agent");
        boolean isVidyoRoom = UserAgentUtils.isVidyoRoom(userAgent);
        
        boolean isGuestLink = false;
        if(!isVidyoRoom) {
            String queryString = request.getQueryString();
            if(queryString != null && !queryString.isEmpty()) {
                isGuestLink = queryString.contains("roomdirect.html") ||
                        ((StringUtils.trimToEmpty(request.getRequestURI()).contains("/join/")));
            }
        }
        

        if (!isVidyoRoom && !isGuestLink && isSamlAuth && auth == null && bak.isEmpty()) {
            response.sendRedirect(samlUrl);
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
        return 1;
    }

}
