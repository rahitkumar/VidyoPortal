package com.vidyo.portal.user.v1_1;

import static org.junit.Assert.*;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.vidyo.bo.User;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.interportal.IpcConfigurationService;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class VidyoPortalUserServiceSkeletonTest {
	
	@Autowired 
	MockHttpServletRequest request;
	
	@SpringBeanByType
	private VidyoPortalUserServiceSkeletonInterface vidyoPortalUserServiceEndPoint_v1_1;
	
	private Mock<ISystemService> mockSystemService;
	private Mock<ITenantService> mockTenantService;
	private Mock<IpcConfigurationService> mockIpcConfigurationService;
	private Mock<IUserService> mockUserService;
	private Mock<LicensingService> mockLicensingService;
	
	@Before
	public void initialize() {
		((VidyoPortalUserServiceSkeleton) vidyoPortalUserServiceEndPoint_v1_1).setSystemService(mockSystemService.getMock());
		((VidyoPortalUserServiceSkeleton) vidyoPortalUserServiceEndPoint_v1_1).setTenantService(mockTenantService.getMock());
		((VidyoPortalUserServiceSkeleton) vidyoPortalUserServiceEndPoint_v1_1).setIpcConfigurationService(mockIpcConfigurationService.getMock());
		((VidyoPortalUserServiceSkeleton) vidyoPortalUserServiceEndPoint_v1_1).setUserService(mockUserService.getMock());
		((VidyoPortalUserServiceSkeleton) vidyoPortalUserServiceEndPoint_v1_1).setLicensingService(mockLicensingService.getMock());
		
		TenantContext.setTenantId(1);
		 
		TransportHeaders headers = new TransportHeaders(request);
		headers.put("user-agent", "VidyoDesktop");
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		
		MessageContext.setCurrentMessageContext(messageContext);
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
			resp = vidyoPortalUserServiceEndPoint_v1_1.getPortalFeatures(null);
		} catch (NotLicensedFaultException | GeneralFaultException e) {
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
			resp = vidyoPortalUserServiceEndPoint_v1_1.getPortalFeatures(null);
		} catch (NotLicensedFaultException | GeneralFaultException e) {
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
			resp = vidyoPortalUserServiceEndPoint_v1_1.getPortalFeatures(null);
		} catch (NotLicensedFaultException | GeneralFaultException e) {
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
			resp = vidyoPortalUserServiceEndPoint_v1_1.getPortalFeatures(null);
		} catch (NotLicensedFaultException | GeneralFaultException e) {
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
	public void getVidyoReplayLibrarySuccess() {
		String url = "http://12.12.12.12";
		mockTenantService.returns(url).getTenantReplayURL(TenantContext.getTenantId());
		
		User loginUser = new User();
		loginUser.setMemberID(1);
		
		mockUserService.returns(loginUser).getLoginUser();
		
		String BAK = "123456789";
		
		mockUserService.returns(BAK).generateBAKforMember(loginUser.getMemberID());
		
		GetVidyoReplayLibraryResponse response = null;
		try {
			response = vidyoPortalUserServiceEndPoint_v1_1.getVidyoReplayLibrary(null);
		} catch (VidyoReplayNotAvailableFaultException
				| NotLicensedFaultException | GeneralFaultException e) {
			fail("Should not throw an exception");
		}
		
		String expectedUrl = url + "/replay/recordingUI.html"; 
		assertNotNull(response);
		assertEquals(expectedUrl, response.getVidyoReplayLibraryUrl());
		assertEquals(BAK, response.getAuthToken());
	}

	@Test
	public void getVidyoReplayLibraryFailNotLicensedFaultException() {
		TransportHeaders headers = new TransportHeaders(request);
		headers.put("user-agent", "");
		MessageContext messageContext = new MessageContext();
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, headers);
		
		MessageContext.setCurrentMessageContext(messageContext);
		
		mockLicensingService.returns(null).getSystemLicenseFeature("AllowUserAPIs");
		
		try {
			vidyoPortalUserServiceEndPoint_v1_1.getVidyoReplayLibrary(null);
			fail("Should throw a NotLicensedFaultException");
		} catch (NotLicensedFaultException e) {
			// OK
		} catch (VidyoReplayNotAvailableFaultException
				| GeneralFaultException e) {
			fail("Should throw a NotLicensedFaultException");
		}
	}
	
	@Test
	public void getVidyoReplayLibraryFailVidyoReplayNotAvailableFaultException() {

		mockTenantService.returns(null).getTenantReplayURL(TenantContext.getTenantId());
		
		User loginUser = new User();
		loginUser.setMemberID(1);
		
		mockUserService.returns(loginUser).getLoginUser();
		
		String BAK = "123456789";
		
		mockUserService.returns(BAK).generateBAKforMember(loginUser.getMemberID());
		
		try {
			vidyoPortalUserServiceEndPoint_v1_1.getVidyoReplayLibrary(null);
			fail("Should throw an VidyoReplayNotAvailableFaultException");
		} catch (VidyoReplayNotAvailableFaultException e) {
			// OK
		} catch (NotLicensedFaultException | GeneralFaultException e) {
			fail("Should throw a VidyoReplayNotAvailableFaultException");
		}
		
	}
}
