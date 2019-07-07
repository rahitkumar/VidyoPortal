package com.vidyo.framework.filters.tenant;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ITenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.FilterInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.vidyo.bo.Tenant;

import java.io.IOException;

public class TenantSelectionFilter extends GenericFilterBean implements InitializingBean, Ordered {
	protected static final Logger logger = LoggerFactory.getLogger(TenantSelectionFilter.class);

    private ITenantService tenantService;

    public void setTenant(ITenantService tenant) {
        this.tenantService = tenant;
    }

    public void afterPropertiesSet() {
        Assert.notNull(tenantService, "tenant must be specified");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

    	try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			
			FilterInvocation fi = new FilterInvocation(request, response, chain);

			String host = request.getHeader("host");

			// strip port # from host header, if any
			if (host != null && host.contains(":")) {
				host = host.substring(0, host.indexOf(":"));
			}

			// determine tenant based on hostname
			Tenant tenant = null;
			if (host != null && !host.equalsIgnoreCase("localhost")) {
				try {
					tenant = this.tenantService.getTenantByURL(host);
				} catch (Exception e) {
					logger.info("Tenant by URL returned error {}", e.getMessage());
				}
			}

			// if tenant is null, set tenantId to 1 as default value in the thread local
			if (tenant == null) {
				TenantContext.setTenantId(1);
			} else {
				TenantContext.setTenantId(tenant.getTenantID());
			}

			if (fi.getResponse().isCommitted()) {
			    return;
			}

			chain.doFilter(request, response);
		} finally {
			// remove the tenantId once the response returns - this is to
			// prevent the tenantId from getting recycled because of thread pooling
			TenantContext.clearTenantId();
		}
    }

    public int getOrder() {
        return 50;
    }

}
