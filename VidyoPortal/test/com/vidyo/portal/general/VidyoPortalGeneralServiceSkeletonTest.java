package com.vidyo.portal.general;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.TransportHeaders;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.security.samltoken.TempAuthTokenService;

@PrepareForTest({ MessageContext.class, TenantContext.class })
public class VidyoPortalGeneralServiceSkeletonTest extends PowerMockTestCase {

	@InjectMocks
	private VidyoPortalGeneralServiceSkeleton vidyoPortalGeneralServiceSkeleton;
	
	@Mock
	private ISystemService system;
	
	@Mock
	private ITenantService tenant;
	
	@Mock
	private LicensingService licensingService;
	
	@Mock
	private TempAuthTokenService tempAuthTokenService;
	
	private MessageContext setupMessageContext() {
		MessageContext messageContext = new MessageContext();
		TransportHeaders transportHeaders = mock(TransportHeaders.class);
		when(transportHeaders.get("user-agent")).thenReturn(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, transportHeaders);
		messageContext.setProperty(MessageContext.REMOTE_ADDR, "127.0.0.1");
		messageContext.setProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST, new MockHttpServletRequest());
		return messageContext;
	}
	
	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);

		PowerMockito.mockStatic(MessageContext.class);
		MessageContext messageContext = setupMessageContext();
		PowerMockito.when(MessageContext.getCurrentMessageContext()).thenReturn(messageContext);

		PowerMockito.mockStatic(TenantContext.class);
		PowerMockito.when(TenantContext.getTenantId()).thenReturn(1);

		when(licensingService.getSystemLicenseFeature("AllowUserAPIs"))
				.thenReturn(new SystemLicenseFeature("AllowUserAPIs", "true", "true"));
	}
	
	private void setTenantConfigurationMockForLogInType() {
		TenantConfiguration configuration = new TenantConfiguration();
		configuration.setTenantId(1);
		configuration.setPp("http://test1.com/pp");
		configuration.setPpVersion(2);
		configuration.setTc("http://test2.com/tc");
		configuration.setTcVersion(3);
		
		when(tenant.getTenantConfiguration(1)).thenReturn(configuration);
	}
	
	private void setSystemConfigurationMockForLogInType() {
		Configuration ppConfig = new Configuration();
		ppConfig.setConfigurationName(SystemConfigurationEnum.P_P.name());
		ppConfig.setConfigurationValue("http://pp.com");
		when(system.getConfiguration(SystemConfigurationEnum.P_P.name())).thenReturn(ppConfig);
		
		Configuration ppVersionConfig = new Configuration();
		ppVersionConfig.setConfigurationName(SystemConfigurationEnum.P_P_VERSION.name());
		ppVersionConfig.setConfigurationValue("4");
		when(system.getConfiguration(SystemConfigurationEnum.P_P_VERSION.name())).thenReturn(ppVersionConfig);
		
		Configuration tcConfig = new Configuration();
		tcConfig.setConfigurationName(SystemConfigurationEnum.T_C.name());
		tcConfig.setConfigurationValue("http://tc.com");
		when(system.getConfiguration(SystemConfigurationEnum.T_C.name())).thenReturn(tcConfig);
		
		Configuration tcVersionConfig = new Configuration();
		tcVersionConfig.setConfigurationName(SystemConfigurationEnum.T_C_VERSION.name());
		tcVersionConfig.setConfigurationValue("5");
		when(system.getConfiguration(SystemConfigurationEnum.T_C_VERSION.name())).thenReturn(tcVersionConfig);
	}
	
	private void setCommonMockBehavioursForLogInType() throws Exception {
		AuthenticationConfig authConfig = new AuthenticationConfig();
		authConfig.setSamlflag(true);
		when(system.getAuthenticationConfig(1)).thenReturn(authConfig);
		
		when(tempAuthTokenService.saveToken(anyString())).thenReturn(null);
	}

	@Test
	public void testLogInTypeWithoutPolicyDetails() throws Exception {
		setCommonMockBehavioursForLogInType();
		
		setTenantConfigurationMockForLogInType();
		
		setSystemConfigurationMockForLogInType();

		LogInTypeRequest logInRequest = new LogInTypeRequest();
		logInRequest.setSamlToken(true);
		logInRequest.setReturnPolicyDetails(false); // disable
		
		LogInTypeResponse response = vidyoPortalGeneralServiceSkeleton.getLogInType(logInRequest);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSamlToken());
		Assert.assertNotNull(response.getUrl());
		Assert.assertNull(response.getTermsAndConditions());
		Assert.assertNull(response.getPrivacyPolicy());
	}
	
	@Test
	public void testLogInTypeForTenantConfiguration() throws Exception {
		setCommonMockBehavioursForLogInType();
		
		setTenantConfigurationMockForLogInType();
		
		setSystemConfigurationMockForLogInType();

		LogInTypeRequest logInRequest = new LogInTypeRequest();
		logInRequest.setSamlToken(true);
		logInRequest.setReturnPolicyDetails(true); // enable
		
		LogInTypeResponse response = vidyoPortalGeneralServiceSkeleton.getLogInType(logInRequest);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSamlToken());
		Assert.assertNotNull(response.getUrl());
		Assert.assertNotNull(response.getTermsAndConditions());
		Assert.assertNotNull(response.getPrivacyPolicy());
		
		Assert.assertEquals(response.getTermsAndConditions().getTcURI().toString(), "http://test2.com/tc");
		Assert.assertEquals(response.getTermsAndConditions().getVersion(), "3");
		Assert.assertEquals(response.getPrivacyPolicy().getPpURI().toString(), "http://test1.com/pp");
		Assert.assertEquals(response.getPrivacyPolicy().getVersion(), "2");
	}
	
	@Test
	public void testLogInTypeForTenantConfigurationWithIncorrectUrls() throws Exception {
		setCommonMockBehavioursForLogInType();
		
		TenantConfiguration configuration = new TenantConfiguration();
		configuration.setTenantId(1);
		configuration.setPp("ppp:\\test1.com/pp");
		configuration.setPpVersion(2);
		configuration.setTc("ppp:\\test2.com/tc");
		configuration.setTcVersion(3);
		when(tenant.getTenantConfiguration(1)).thenReturn(configuration);
		
		setSystemConfigurationMockForLogInType();

		LogInTypeRequest logInRequest = new LogInTypeRequest();
		logInRequest.setSamlToken(true);
		logInRequest.setReturnPolicyDetails(true); // enable
		
		LogInTypeResponse response = vidyoPortalGeneralServiceSkeleton.getLogInType(logInRequest);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSamlToken());
		Assert.assertNotNull(response.getUrl());
		Assert.assertNotNull(response.getTermsAndConditions());
		Assert.assertNotNull(response.getPrivacyPolicy());
		
		Assert.assertNull(response.getTermsAndConditions().getTcURI());
		Assert.assertNull(response.getTermsAndConditions().getVersion());
		Assert.assertNull(response.getPrivacyPolicy().getPpURI());
		Assert.assertNull(response.getPrivacyPolicy().getVersion());
	}
	
	@Test
	public void testLogInTypeForSystemConfiguration() throws Exception {
		setCommonMockBehavioursForLogInType();
		
		TenantConfiguration configuration = new TenantConfiguration();
		configuration.setTenantId(1);
		when(tenant.getTenantConfiguration(1)).thenReturn(configuration);
		
		setSystemConfigurationMockForLogInType();

		LogInTypeRequest logInRequest = new LogInTypeRequest();
		logInRequest.setSamlToken(true);
		logInRequest.setReturnPolicyDetails(true); // enable
		
		LogInTypeResponse response = vidyoPortalGeneralServiceSkeleton.getLogInType(logInRequest);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSamlToken());
		Assert.assertNotNull(response.getUrl());
		Assert.assertNotNull(response.getTermsAndConditions());
		Assert.assertNotNull(response.getPrivacyPolicy());
		
		Assert.assertEquals(response.getTermsAndConditions().getTcURI().toString(), "http://tc.com");
		Assert.assertEquals(response.getTermsAndConditions().getVersion(), "5");
		Assert.assertEquals(response.getPrivacyPolicy().getPpURI().toString(), "http://pp.com");
		Assert.assertEquals(response.getPrivacyPolicy().getVersion(), "4");
	}
}
