package com.vidyo.portal.admin.v1_1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.unitils.mock.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.ServiceFilter;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VidyoPortalAdminServiceSkeletonTest {
	
	@SpringBeanByType
	private VidyoPortalAdminServiceSkeletonInterface vidyoPortalAdminServiceEndPoint_v1_1;
	
	private Mock<IUserService> mockUserService;
	private Mock<ITenantService> mockTenantService;
	private Mock<ISystemService> mockSystemService;
	private Mock<IServiceService> mockServicemService;
	
	
	@Before
	public void initialize() {
		((VidyoPortalAdminServiceSkeleton) vidyoPortalAdminServiceEndPoint_v1_1).setUserService(mockUserService.getMock());
		((VidyoPortalAdminServiceSkeleton) vidyoPortalAdminServiceEndPoint_v1_1).setTenantService(mockTenantService.getMock());
		((VidyoPortalAdminServiceSkeleton) vidyoPortalAdminServiceEndPoint_v1_1).setSystemService(mockSystemService.getMock());
		((VidyoPortalAdminServiceSkeleton) vidyoPortalAdminServiceEndPoint_v1_1).setServiceService(mockServicemService.getMock());
		TenantContext.setTenantId(1);
	}
	@BeforeClass
	public static void onlyOnce() {
		
		TenantContext.setTenantId(1);
	}
	
	@Test
	public void setChatStateAdminFailedChatIsNotAvailable() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(false).isChatAvailable();
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPrivateChatState(true);
		
		try {
			vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
			fail("This case shoud throw GeneralFaultException");
		} catch(ChatNotAvailableInSuperFaultException cnaisEx) {
			// It is OK
		} catch(Exception ex) {
			fail("This case should throw only ChatNotAvailableInSuperFaultException");
		}		
	}
	
	@Test
	public void setChatStateAdminFailedToUpdateChatStatuses() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPrivateChatState(true);
		
		mockTenantService.returns(0).updateEndpointChatsStatuses(1, 1, 0);
		
		try {
			vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
			fail("This case shoud throw GeneralFaultException");
		} catch(GeneralFaultException gfEx) {
			// It is OK
		} catch(Exception ex) {
			fail("This case should throw only GeneralFaultException");
		}
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertInvoked().getTenantConfiguration(1);
		mockTenantService.assertInvoked().updateEndpointChatsStatuses(1, 1, 0);
	}
	
	@Test
	public void setChatStateAdminSuccess1() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPrivateChatState(true);
		
		mockTenantService.returns(1).updateEndpointChatsStatuses(1, 1, 0);
		
		SetChatStateAdminResponse setChatStateAdminResponse = null;
		try {
			setChatStateAdminResponse = vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
		} catch (NotLicensedFaultException | ChatNotAvailableInSuperFaultException | GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateAdminResponse);
		assertEquals(OK_type0.OK, setChatStateAdminResponse.getOK());
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertInvoked().getTenantConfiguration(1);
		mockTenantService.assertInvoked().updateEndpointChatsStatuses(1, 1, 0);
	}

	@Test
	public void setChatStateAdminSuccess2() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPrivateChatState(false);
		
		mockTenantService.returns(1).updateEndpointChatsStatuses(1, 0, 0);
		
		SetChatStateAdminResponse setChatStateAdminResponse = null;
		try {
			setChatStateAdminResponse = vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
		} catch (NotLicensedFaultException | ChatNotAvailableInSuperFaultException | GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateAdminResponse);
		assertEquals(OK_type0.OK, setChatStateAdminResponse.getOK());
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertInvoked().getTenantConfiguration(1);
		mockTenantService.assertInvoked().updateEndpointChatsStatuses(1, 0, 0);
	}
	
	@Test
	public void setChatStateAdminSuccess3() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPublicChatState(true);
		
		mockTenantService.returns(1).updateEndpointChatsStatuses(1, 0, 1);
		
		SetChatStateAdminResponse setChatStateAdminResponse = null;
		try {
			setChatStateAdminResponse = vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
		} catch (NotLicensedFaultException | ChatNotAvailableInSuperFaultException | GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateAdminResponse);
		assertEquals(OK_type0.OK, setChatStateAdminResponse.getOK());
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertInvoked().getTenantConfiguration(1);
		mockTenantService.assertInvoked().updateEndpointChatsStatuses(1, 0, 1);
	}

	@Test
	public void setChatStateAdminSuccess4() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(0);
		tenantConfiguration.setEndpointPublicChat(0);
		
		mockTenantService.returns(tenantConfiguration).getTenantConfiguration(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		setChatStateAdminRequest.setSetPublicChatState(false);
		
		mockTenantService.returns(1).updateEndpointChatsStatuses(1, 0, 0);
		
		SetChatStateAdminResponse setChatStateAdminResponse = null;
		try {
			setChatStateAdminResponse = vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
		} catch (NotLicensedFaultException | ChatNotAvailableInSuperFaultException | GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateAdminResponse);
		assertEquals(OK_type0.OK, setChatStateAdminResponse.getOK());
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertInvoked().getTenantConfiguration(1);
		mockTenantService.assertInvoked().updateEndpointChatsStatuses(1, 0, 0);
	}
	
	@Test
	public void setChatStateAdminSuccess5() {
		User user = new User();
		user.setTenantID(1);
		user.setUsername("admin");
		
		mockUserService.returns(user).getLoginUser();
		
		mockSystemService.returns(true).isChatAvailable();
		
		Tenant tenant = new Tenant();
		tenant.setTenantID(1);
		
		SetChatStateAdminRequest setChatStateAdminRequest = new SetChatStateAdminRequest();
		
		SetChatStateAdminResponse setChatStateAdminResponse = null;
		try {
			setChatStateAdminResponse = vidyoPortalAdminServiceEndPoint_v1_1.setChatStateAdmin(setChatStateAdminRequest);
		} catch (NotLicensedFaultException | ChatNotAvailableInSuperFaultException | GeneralFaultException e) {
		
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateAdminResponse);
		assertEquals(OK_type0.OK, setChatStateAdminResponse.getOK());
		
		mockUserService.assertInvoked().getLoginUser();
		mockTenantService.assertNotInvoked().getTenant(anyInt());
		mockTenantService.assertNotInvoked().getTenantConfiguration(anyInt());
		mockTenantService.assertNotInvoked().updateEndpointChatsStatuses(1, 0, anyInt());
	}
	@Test
	public void getLocationTags() {
	
		List<Location>locationTags=null;
		int tenantId=TenantContext.getTenantId();
	
		mockServicemService.returns(locationTags).getSelectedLocationTags(new ServiceFilter(),1);
		GetLocationTagsRequest req = new GetLocationTagsRequest();
		GetLocationTagsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getLocationTags(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
				fail("This case should not throw any Exception");
		}
		
		assertNotNull(resp);
	//	assertEquals(OK_type0.OK, resp.getTotal());
		mockServicemService.assertNotInvoked().getSelectedLocationTags(new ServiceFilter(),1);

	}
	@Test
	public void getLocationTags_1() {
	
		List<Location>locationTags=null;
	mockServicemService.returns(locationTags).getSelectedLocationTags(new ServiceFilter(),1);
		GetLocationTagsRequest req = new GetLocationTagsRequest();
		GetLocationTagsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getLocationTags(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
			
			assertTrue(true);
		}

	mockServicemService.assertNotInvoked().getSelectedLocationTags(new ServiceFilter(),1);

	}
	@Test
	public void getLocationTags_filters() {
	
		List<Location>locationTags=null;
		int tenantId=TenantContext.getTenantId();
		Filter_type0 param =new 	Filter_type0();
		param.setDir(new SortDir("1",true));
		param.setLimit(5);
		param.setQuery("danb");
		param.setSortBy("locationTag");
		param.setStart(0);
		
		
		mockServicemService.returns(locationTags).getSelectedLocationTags(new ServiceFilter(),1);
		GetLocationTagsRequest req = new GetLocationTagsRequest();
		req.setFilter(param);
		GetLocationTagsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getLocationTags(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
				fail("This case should not throw any Exception");
		}
		
		assertNotNull(resp);
	//	assertEquals(OK_type0.OK, resp.getTotal());
	
		mockServicemService.assertNotInvoked().getSelectedLocationTags(new ServiceFilter(),1);


	}
	@Test
	public void getLocationTags_filters_2() {
	
		List<Location>locationTags=null;
		int tenantId=TenantContext.getTenantId();
			Filter_type0 param =new 	Filter_type0();
		param.setDir(new SortDir("1",true));
		param.setLimit(5);
		param.setQuery("danb");
		param.setSortBy("locationTag");
		param.setStart(1);
		
			mockServicemService.returns(locationTags).getSelectedLocationTags(new ServiceFilter(),1);
		GetLocationTagsRequest req = new GetLocationTagsRequest();
		req.setFilter(param);
		GetLocationTagsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getLocationTags(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
				fail("This case should not throw any Exception");
		}
		
		assertNotNull(resp);
	//	assertEquals(OK_type0.OK, resp.getTotal());
		mockServicemService.assertNotInvoked().getSelectedLocationTags(new ServiceFilter(),1);


	}
	@Test
	public void getLocationTags_filters_3() {
	
		Location loc=new Location();
		loc.setLocationTag("Test");
		List<Location>locationTags=new ArrayList<Location>();
		locationTags.add(loc);
			Filter_type0 param =new 	Filter_type0();
		param.setDir(new SortDir("1",true));
		param.setLimit(5);
		param.setQuery("danb");
		param.setSortBy("locationTag");
		param.setStart(1);
		ServiceFilter serviceFilter=new ServiceFilter();
			mockServicemService.returns(locationTags).getSelectedLocationTags(serviceFilter,1);
		GetLocationTagsRequest req = new GetLocationTagsRequest();
		req.setFilter(param);
		GetLocationTagsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getLocationTags(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
				fail("This case should not throw any Exception");
		}
		
		assertNotNull(resp);
	//	assertEquals(OK_type0.OK, resp.getTotal());
	


	}
	@Test
	public void getTenantDetails() {
	
		Tenant tenant=new Tenant();
		tenant.setTenantName("Ten");
		tenant.setTenantPrefix("RE");
		ServiceFilter serviceFilter=new ServiceFilter();
		int id=0;
		mockTenantService.returns(tenant).getTenant(1);
		GetTenantDetailsRequest req = new GetTenantDetailsRequest();
		
		GetTenantDetailsResponse resp=null;
		try {
			resp=vidyoPortalAdminServiceEndPoint_v1_1.getTenantDetails(req);
		} catch (NotLicensedFaultException | InvalidArgumentFaultException | GeneralFaultException e) {
				fail("This case should not throw any Exception");
		}
		
		assertNotNull(resp);
	//	assertEquals(OK_type0.OK, resp.getTotal());
	


	}
}
