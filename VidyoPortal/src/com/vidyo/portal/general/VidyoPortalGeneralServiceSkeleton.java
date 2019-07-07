
/**
 * VidyoPortalGeneralServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.vidyo.portal.general;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.AuthenticationType;
import com.vidyo.db.repository.security.samltoken.TempAuthToken;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.security.samltoken.TempAuthTokenService;
import com.vidyo.utils.SecureRandomUtils;

/**
 * VidyoPortalGeneralServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalGeneralServiceSkeleton implements VidyoPortalGeneralServiceSkeletonInterface {

	protected final Logger logger = LoggerFactory.getLogger(VidyoPortalGeneralServiceSkeleton.class.getName());
	
	private ISystemService system;

	private ITenantService tenant;

	@Autowired
	private TempAuthTokenService tempAuthTokenService;

	/**
	 * @param tempAuthTokenService
	 *            the tempAuthTokenService to set
	 */
	public void setTempAuthTokenService(TempAuthTokenService tempAuthTokenService) {
		this.tempAuthTokenService = tempAuthTokenService;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setTenant(ITenantService tenant) {
		this.tenant = tenant;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param logInTypeRequest0
	 * @return logInTypeResponse1
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */

	public com.vidyo.portal.general.LogInTypeResponse getLogInType(
			com.vidyo.portal.general.LogInTypeRequest logInTypeRequest0)
			throws InvalidArgumentFaultException, GeneralFaultException {

		MessageContext context = MessageContext.getCurrentMessageContext();

		HttpServletRequest request = ((HttpServletRequest) context.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST));

		LogInTypeResponse response = new LogInTypeResponse();
		Integer tenantId = TenantContext.getTenantId();
		AuthenticationConfig authConfig = this.system.getAuthenticationConfig(tenantId);
		Authentication auth = authConfig.toAuthentication();
		AuthenticationType authType = auth.getAuthenticationType();

		StringBuffer loginURL = new StringBuffer();
		loginURL.append(request.getScheme()).append("://").append(request.getServerName());

		if (authType == AuthenticationType.SAML_BROWSER) {
			response.setLogInType(LogInType_type0.SAML_BROWSER);
			loginURL.append("/saml/login/");

			// VPTL-7657 - check if the request contains saml in the request
			// parameter
			if (logInTypeRequest0 != null && logInTypeRequest0.getSamlToken()) {
				// then generate a unique ID and return to the client
				String token = SecureRandomUtils.generateRoomKey(64);
				// Send the generated token as part of the response
				// TODO - Need to persist the token in the database.
				loginURL.append("?token=" + token);
				response.setSamlToken(token);
				tempAuthTokenService.saveToken(token);
			}
		} else if (authType == AuthenticationType.CAC) {
			response.setLogInType(LogInType_type0.CAC);
			// no special link, portal will handle it automatically
		} else {
			response.setLogInType(LogInType_type0.PORTAL);
			loginURL.append("/flex.html");
		}

		try {
			response.setUrl(new URI(loginURL.toString()));
		} catch (URI.MalformedURIException e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Internal Server Error");
			GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
			exception.setFaultMessage(fault);
			throw exception;
		}
		
		if (!logInTypeRequest0.getReturnPolicyDetails()) return response; // no need to return TC and privacy Policy
		
		TenantConfiguration tenantConfiguration = tenant.getTenantConfiguration(TenantContext.getTenantId());
		if (org.apache.commons.lang.StringUtils.isNotBlank(tenantConfiguration.getPp())) { // tenant
			PrivacyPolicy_type0 privacyPolicy = new PrivacyPolicy_type0();
			
			try {
				privacyPolicy.setPpURI(new URI(tenantConfiguration.getPp()));
				privacyPolicy.setVersion(String.valueOf(tenantConfiguration.getPpVersion()));
			} catch (URI.MalformedURIException e) {
				logger.error("Error", e);
			}
			
			response.setPrivacyPolicy(privacyPolicy);
		} else { // system
			Configuration ppConfig = this.system.getConfiguration(SystemConfigurationEnum.P_P.name());
			if ((ppConfig != null) && org.apache.commons.lang.StringUtils.isNotBlank(ppConfig.getConfigurationValue())) {
				PrivacyPolicy_type0 privacyPolicy = new PrivacyPolicy_type0();
				
				try {
					privacyPolicy.setPpURI(new URI(ppConfig.getConfigurationValue()));
					
					Configuration ppVersionConfig = this.system.getConfiguration(SystemConfigurationEnum.P_P_VERSION.name());
					if ((ppVersionConfig != null) && org.apache.commons.lang.StringUtils.isNotBlank(ppVersionConfig.getConfigurationValue())) {
						privacyPolicy.setVersion(ppVersionConfig.getConfigurationValue());
					}
				} catch (URI.MalformedURIException e) {
					logger.error("Error", e);
				}

				response.setPrivacyPolicy(privacyPolicy);
			} 
		}
		
		if (org.apache.commons.lang.StringUtils.isNotBlank(tenantConfiguration.getTc())) { // tenant
			TermsAndConditions_type0 terms = new TermsAndConditions_type0();
			
			try {
				terms.setTcURI(new URI(tenantConfiguration.getTc()));
				terms.setVersion(String.valueOf(tenantConfiguration.getTcVersion()));
			} catch (URI.MalformedURIException e) {
				logger.error("Error", e);
			}
			
			response.setTermsAndConditions(terms);
		} else { // system
			Configuration tcConfig = this.system.getConfiguration(SystemConfigurationEnum.T_C.name());
			if ((tcConfig != null) && org.apache.commons.lang.StringUtils.isNotBlank(tcConfig.getConfigurationValue())) {
				TermsAndConditions_type0 terms = new TermsAndConditions_type0();
				
				try {
					terms.setTcURI(new URI(tcConfig.getConfigurationValue()));
					
					Configuration tcVersionConfig = this.system.getConfiguration(SystemConfigurationEnum.T_C_VERSION.name());
					if ((tcVersionConfig != null) && org.apache.commons.lang.StringUtils.isNotBlank(tcVersionConfig.getConfigurationValue())) {
						terms.setVersion(tcVersionConfig.getConfigurationValue());
					}
				} catch (URI.MalformedURIException e) {
					logger.error("Error", e);
				}

				response.setTermsAndConditions(terms);
			} 
		}

		return response;
	}

	@Override
	public WebRTCLoginIsEnabledResponse webRTCLoginIsEnabled(WebRTCLoginIsEnabledRequest webRTCLoginIsEnabledRequest)
			throws GeneralFaultException {
		boolean webRTCForUsers = false;
		Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
		if (vidyoNeoWebRTUserConfiguration != null
				&& StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
				&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
			TenantConfiguration tenantConfiguration = tenant.getTenantConfiguration(TenantContext.getTenantId());
			webRTCForUsers = tenantConfiguration.getVidyoNeoWebRTCUserEnabled() == 1;
		}

		WebRTCLoginIsEnabledResponse response = new WebRTCLoginIsEnabledResponse();
		Tenant t = tenant.getTenant(TenantContext.getTenantId());
		String webrtcURL = t.getTenantWebRTCURL();

		if (webRTCForUsers && StringUtils.isNotBlank(webrtcURL)) {
			response.setWebRTCLoginEnabled(true);
		} else {
			response.setWebRTCLoginEnabled(false);
		}
		return response;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param transactionStatusRequest
	 * @throws GeneralFaultException
	 *             :
	 */
	public com.vidyo.portal.general.TransactionStatusResponse getTransactionStatus(
			com.vidyo.portal.general.TransactionStatusRequest transactionStatusRequest)
			throws GeneralFaultException, InvalidArgumentFaultException {

		if (StringUtils.isBlank(transactionStatusRequest.getToken())) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Token Input");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Token Input");
			exception.setFaultMessage(fault);
			throw exception;
		}

		TempAuthToken tempAuthToken = tempAuthTokenService.getTokenDetails(transactionStatusRequest.getToken());

		if (tempAuthToken == null) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("Invalid Token");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Token");
			exception.setFaultMessage(fault);
			throw exception;
		}

		TransactionStatusResponse response = new TransactionStatusResponse();
		if (tempAuthToken.getMemberId() == null || tempAuthToken.getMemberId() <= 0) {
			response.setTransactionStatus(TransactionStatus_type1.PENDING);
		} else {
			response.setAuthToken(tempAuthToken.getAuthToken());
			response.setTransactionStatus(TransactionStatus_type1.SUCCESS);
		}

		return response;
	}

}
