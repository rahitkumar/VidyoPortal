package com.vidyo.portal.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Banners;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ILoginService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.exceptions.EmailAddressNotFoundException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.NotificationEmailAddressNotConfiguredException;
import com.vidyo.service.interportal.IpcConfigurationService;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class VidyoPortalGuestServiceSkeletonTest {
	
	@SpringBeanByType
	private VidyoPortalGuestServiceSkeletonInterface vidyoPortalGuestServiceEndPoint;
	
	private Mock<ISystemService> mockSystemService;
	private Mock<ITenantService> mockTenantService;
	private Mock<IpcConfigurationService> mockIpcConfigurationService;
	private Mock<ILoginService> mockLoginService;
	
	@Before
	public void initialize() {
		((VidyoPortalGuestServiceSkeleton) vidyoPortalGuestServiceEndPoint).setSystem(mockSystemService.getMock());
		((VidyoPortalGuestServiceSkeleton) vidyoPortalGuestServiceEndPoint).setTenant(mockTenantService.getMock());
		((VidyoPortalGuestServiceSkeleton) vidyoPortalGuestServiceEndPoint).setIpcConfigurationService(mockIpcConfigurationService.getMock());
		((VidyoPortalGuestServiceSkeleton) vidyoPortalGuestServiceEndPoint).setLoginService(mockLoginService.getMock());
		
		TenantContext.setTenantId(1);
	}
	
	@Test
	public void getPortalFeaturesEndpointChatFeature1() {
		Banners banners = new Banners();
		banners.setShowLoginBanner(true);
		banners.setShowWelcomeBanner(true);
		
		mockSystemService.returns(null).getConfiguration("SCHEDULED_ROOM_PREFIX");
		mockSystemService.returns(true).getTLSProxyConfiguration();
		mockSystemService.returns(banners).getBannersInfo();
		
		
		
		int tenantID = TenantContext.getTenantId();
		Tenant tenant = new Tenant();
		tenant.setTenantID(tenantID);
		
		mockTenantService.returns(tenant).getTenant(tenantID);
		mockTenantService.returns(true).isTenantNotAllowingGuests();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(tenantID);
		tenantConfiguration.setEndpointPrivateChat(1);
		tenantConfiguration.setEndpointPublicChat(1);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(tenantID);
		
		IpcConfiguration interPortalConference = new IpcConfiguration();
		interPortalConference.setOutbound(1);
		interPortalConference.setInbound(1);
		mockIpcConfigurationService.returns(interPortalConference).getIpcConfiguration(tenantID);
		
		GetPortalFeaturesResponse resp = null;
		try {
			resp = vidyoPortalGuestServiceEndPoint.getPortalFeatures(null);
		} catch (GeneralFaultException e) {
			fail("getPortalFeatures() should not throw an exception.") ;
		}
		
		PortalFeature_type0 [] features = resp.getPortalFeature();
		
		assertNotNull(features);
		assertNotEquals(0, features.length);
		
		boolean endpointPrivateChatFound = false;
		boolean endpointPublicChatFound = false;
		
		for(PortalFeature_type0 feature : features) {
			if("EndpointPrivateChat".equals(feature.getFeature().getPortalFeatureName())){
				assertTrue(feature.getEnable());
				endpointPrivateChatFound = true;
			}
			
			if("EndpointPublicChat".equals(feature.getFeature().getPortalFeatureName())){
				assertTrue(feature.getEnable());
				endpointPublicChatFound = true;
			}
		}
		
		assertTrue(endpointPrivateChatFound && endpointPublicChatFound);
	}
	
	@Test
	public void getPortalFeaturesEndpointChatFeature2() {
		Banners banners = new Banners();
		banners.setShowLoginBanner(true);
		banners.setShowWelcomeBanner(true);
		
		mockSystemService.returns(null).getConfiguration("SCHEDULED_ROOM_PREFIX");
		mockSystemService.returns(true).getTLSProxyConfiguration();
		mockSystemService.returns(banners).getBannersInfo();
		
		
		
		int tenantID = TenantContext.getTenantId();
		Tenant tenant = new Tenant();
		tenant.setTenantID(tenantID);
		
		mockTenantService.returns(tenant).getTenant(tenantID);
		mockTenantService.returns(true).isTenantNotAllowingGuests();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(tenantID);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(1);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(tenantID);
		
		IpcConfiguration interPortalConference = new IpcConfiguration();
		interPortalConference.setOutbound(1);
		interPortalConference.setInbound(1);
		mockIpcConfigurationService.returns(interPortalConference).getIpcConfiguration(tenantID);
		
		GetPortalFeaturesResponse resp = null;
		try {
			resp = vidyoPortalGuestServiceEndPoint.getPortalFeatures(null);
		} catch (GeneralFaultException e) {
			fail("getPortalFeatures() should not throw an exception.") ;
		}
		
		PortalFeature_type0 [] features = resp.getPortalFeature();
		
		assertNotNull(features);
		assertNotEquals(0, features.length);
		
		boolean endpointPrivateChatFound = false;
		boolean endpointPublicChatFound = false;
		
		for(PortalFeature_type0 feature : features) {
			if("EndpointPrivateChat".equals(feature.getFeature().getPortalFeatureName())){
				assertFalse(feature.getEnable());
				endpointPrivateChatFound = true;
			}
			
			if("EndpointPublicChat".equals(feature.getFeature().getPortalFeatureName())){
				assertTrue(feature.getEnable());
				endpointPublicChatFound = true;
			}
		}
		
		assertTrue(endpointPrivateChatFound && endpointPublicChatFound);
	}
	
	@Test
	public void getPortalFeaturesEndpointChatFeature3() {
		Banners banners = new Banners();
		banners.setShowLoginBanner(true);
		banners.setShowWelcomeBanner(true);
		
		mockSystemService.returns(null).getConfiguration("SCHEDULED_ROOM_PREFIX");
		mockSystemService.returns(true).getTLSProxyConfiguration();
		mockSystemService.returns(banners).getBannersInfo();
		
		
		
		int tenantID = TenantContext.getTenantId();
		Tenant tenant = new Tenant();
		tenant.setTenantID(tenantID);
		
		mockTenantService.returns(tenant).getTenant(tenantID);
		mockTenantService.returns(true).isTenantNotAllowingGuests();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(tenantID);
		tenantConfiguration.setEndpointPrivateChat(1);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(tenantID);
		
		IpcConfiguration interPortalConference = new IpcConfiguration();
		interPortalConference.setOutbound(1);
		interPortalConference.setInbound(1);
		mockIpcConfigurationService.returns(interPortalConference).getIpcConfiguration(tenantID);
		
		GetPortalFeaturesResponse resp = null;
		try {
			resp = vidyoPortalGuestServiceEndPoint.getPortalFeatures(null);
		} catch (GeneralFaultException e) {
			fail("getPortalFeatures() should not throw an exception.") ;
		}
		
		PortalFeature_type0 [] features = resp.getPortalFeature();
		
		assertNotNull(features);
		assertNotEquals(0, features.length);
		
		boolean endpointPrivateChatFound = false;
		boolean endpointPublicChatFound = false;
		
		for(PortalFeature_type0 feature : features) {
			if("EndpointPrivateChat".equals(feature.getFeature().getPortalFeatureName())){
				assertTrue(feature.getEnable());
				endpointPrivateChatFound = true;
			}
			
			if("EndpointPublicChat".equals(feature.getFeature().getPortalFeatureName())){
				assertFalse(feature.getEnable());
				endpointPublicChatFound = true;
			}
		}
		
		assertTrue(endpointPrivateChatFound && endpointPublicChatFound);
	}
	
	@Test
	public void getPortalFeaturesEndpointChatFeature4() {
		Banners banners = new Banners();
		banners.setShowLoginBanner(true);
		banners.setShowWelcomeBanner(true);
		
		mockSystemService.returns(null).getConfiguration("SCHEDULED_ROOM_PREFIX");
		mockSystemService.returns(true).getTLSProxyConfiguration();
		mockSystemService.returns(banners).getBannersInfo();
		
		
		
		int tenantID = TenantContext.getTenantId();
		Tenant tenant = new Tenant();
		tenant.setTenantID(tenantID);
		
		mockTenantService.returns(tenant).getTenant(tenantID);
		mockTenantService.returns(true).isTenantNotAllowingGuests();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(tenantID);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(tenantID);
		
		IpcConfiguration interPortalConference = new IpcConfiguration();
		interPortalConference.setOutbound(1);
		interPortalConference.setInbound(1);
		mockIpcConfigurationService.returns(interPortalConference).getIpcConfiguration(tenantID);
		
		GetPortalFeaturesResponse resp = null;
		try {
			resp = vidyoPortalGuestServiceEndPoint.getPortalFeatures(null);
		} catch (GeneralFaultException e) {
			fail("getPortalFeatures() should not throw an exception.") ;
		}
		
		PortalFeature_type0 [] features = resp.getPortalFeature();
		
		assertNotNull(features);
		assertNotEquals(0, features.length);
		
		boolean endpointPrivateChatFound = false;
		boolean endpointPublicChatFound = false;
		
		for(PortalFeature_type0 feature : features) {
			if("EndpointPrivateChat".equals(feature.getFeature().getPortalFeatureName())){
				assertFalse(feature.getEnable());
				endpointPrivateChatFound = true;
			}
			
			if("EndpointPublicChat".equals(feature.getFeature().getPortalFeatureName())){
				assertFalse(feature.getEnable());
				endpointPublicChatFound = true;
			}
		}
		
		assertTrue(endpointPrivateChatFound && endpointPublicChatFound);
	}
	
	@Test
	public void recoverPasswordSuccess() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setScheme("http");
		request.setContextPath("/");
		request.addHeader("host", "kuku.kuk.com");

		TransportHeaders headers = new TransportHeaders(request);
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		messageContext.setProperty("transport.http.servletRequest", request);
		
		MessageContext.setCurrentMessageContext(messageContext);
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com";
		RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest();
		recoverPasswordRequest.setEmailAddress(forgotPasswordEmail);
		
		RecoverPasswordResponse resp = null;
		
		try {
			resp = vidyoPortalGuestServiceEndPoint.recoverPassword(recoverPasswordRequest);
		} catch (GeneralFaultException | EmailAddressNotFoundFaultException
				| NotificationEmailNotConfiguredFaultException e) {
			fail("Should not fail");
		}
		
		assertNotNull(resp);
		assertEquals(OK_type0.OK, resp.getOK());
		try {
			mockLoginService.assertInvoked().forgotAPIPassword(1, forgotPasswordEmail, "http", "kuku.kuk.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException
				| EmailAddressNotFoundException | GeneralException e) {
			fail("Should not fail");
		}
		
	}
	
	@Test
	public void recoverPasswordFailNotificationEmailNotConfiguredFaultException() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setScheme("http");
		request.setContextPath("/");
		request.addHeader("host", "kuku.kuk.com");

		TransportHeaders headers = new TransportHeaders(request);
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		messageContext.setProperty("transport.http.servletRequest", request);
		
		MessageContext.setCurrentMessageContext(messageContext);
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com";
		RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest();
		recoverPasswordRequest.setEmailAddress(forgotPasswordEmail);
		
		try {
			mockLoginService.raises(NotificationEmailAddressNotConfiguredException.class).forgotAPIPassword(1, forgotPasswordEmail, "http", "kuku.kuk.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException
				| EmailAddressNotFoundException | GeneralException e1) {
			fail();
		}
		
		try {
			vidyoPortalGuestServiceEndPoint.recoverPassword(recoverPasswordRequest);
		} catch (NotificationEmailNotConfiguredFaultException e) {
			// OK
		} catch (GeneralFaultException | EmailAddressNotFoundFaultException e) {
			fail("Should not fail");
		}
		
	}
	
	@Test
	public void recoverPasswordFailEmailAddressNotFoundFaultException() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setScheme("http");
		request.setContextPath("/");
		request.addHeader("host", "kuku.kuk.com");

		TransportHeaders headers = new TransportHeaders(request);
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		messageContext.setProperty("transport.http.servletRequest", request);
		
		MessageContext.setCurrentMessageContext(messageContext);
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com";
		RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest();
		recoverPasswordRequest.setEmailAddress(forgotPasswordEmail);
		
		try {
			mockLoginService.raises(EmailAddressNotFoundException.class).forgotAPIPassword(1, forgotPasswordEmail, "http", "kuku.kuk.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException
				| EmailAddressNotFoundException | GeneralException e1) {
			fail();
		}
		
		try {
			vidyoPortalGuestServiceEndPoint.recoverPassword(recoverPasswordRequest);
		} catch (EmailAddressNotFoundFaultException e) {
			// OK
		} catch (GeneralFaultException | NotificationEmailNotConfiguredFaultException e) {
			fail("Should not fail");
		}
		
	}
	
	@Test
	public void recoverPasswordFailGeneralFaultException() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		request.setScheme("http");
		request.setContextPath("/");
		request.addHeader("host", "kuku.kuk.com");

		TransportHeaders headers = new TransportHeaders(request);
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		messageContext.setProperty("transport.http.servletRequest", request);
		
		MessageContext.setCurrentMessageContext(messageContext);
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com";
		RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest();
		recoverPasswordRequest.setEmailAddress(forgotPasswordEmail);
		
		try {
			mockLoginService.raises(GeneralException.class).forgotAPIPassword(1, forgotPasswordEmail, "http", "kuku.kuk.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException
				| EmailAddressNotFoundException | GeneralException e1) {
			fail();
		}
		
		try {
			vidyoPortalGuestServiceEndPoint.recoverPassword(recoverPasswordRequest);
		} catch (GeneralFaultException e) {
			// OK
		} catch (EmailAddressNotFoundFaultException | NotificationEmailNotConfiguredFaultException e) {
			fail("Should not fail");
		}
		
	}
}
