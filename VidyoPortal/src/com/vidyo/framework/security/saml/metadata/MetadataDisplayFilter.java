package com.vidyo.framework.security.saml.metadata;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.web.FilterInvocation;

import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;

/**
 * This filter extends MetadataDisplayFilter. We have to override this one since
 * we wanted to change the default download file name
 * 
 */
public class MetadataDisplayFilter extends org.springframework.security.saml.metadata.MetadataDisplayFilter {

	/**
	 * Class logger.
	 */
	protected final static Logger log = LoggerFactory.getLogger(MetadataDisplayFilter.class);
	private ISystemService systemService;

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	protected ITenantService tenantService;

	public ITenantService getTenantService() {
		return tenantService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * The filter attempts to generate application metadata (if configured so)
	 * and in case the call is made to the expected URL the metadata value is
	 * displayed and no further filters are invoked. Otherwise filter chain
	 * invocation continues.
	 *
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws javax.servlet.ServletException
	 *             error
	 * @throws java.io.IOException
	 *             io error
	 */
	@Override
	protected void processMetadataDisplay(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {

			Integer tenantId = TenantContext.getTenantId();
			com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(tenantId)
					.toAuthentication();
			boolean isSamlAuth = authType instanceof SamlAuthentication;

			if (!isSamlAuth) {
				logger.warn("This tenant is not saml enabled");
				response.sendError(404, "Not Found");
			}else{
			Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
			String fileName = "sp_metadata.xml";
			if (!(tenant == null || tenant.getTenantName() == null)) {
				fileName = tenant.getTenantName() + "_sp_metadata.xml";
			}
			SAMLMessageContext context = contextProvider.getLocalEntity(request, response);
			String entityId = context.getLocalEntityId();
			response.setContentType("application/samlmetadata+xml"); 
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			displayMetadata(entityId, response.getWriter());
			}
		} catch (MetadataProviderException e) {
			throw new ServletException("Error initializing metadata", e);
		}
	}

}