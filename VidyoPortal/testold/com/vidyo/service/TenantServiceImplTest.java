package com.vidyo.service;

import static org.junit.Assert.*;
import static org.unitils.mock.ArgumentMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Group;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Member;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.EndpointUploadDao;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IRoomDao;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.ITenantDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.service.interportal.IpcConfigurationService;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantServiceImplTest {
	
	@SpringBeanByType
	private ITenantService tenantService;
	
	private Mock<IMemberService> mockMemberService;
	private Mock<IpcConfigurationService> mockIpcConfigurationService;
	private Mock<ISystemService> mockSystemService;
	
	private Mock<ITenantDao> mockTenantDao;
	private Mock<TenantConfigurationDao> mockTenantConfigurationDao;
	private Mock<IGroupDao> mockGroupDao;
	private Mock<ISystemDao> mockSystemDao;
	private Mock<IMemberDao> mockMemberDao;
	private Mock<IRoomDao> mockRoomDao;
	private Mock<EndpointUploadDao> mockEndpointUploadDao;
	
	@Before
	public void initialize() {
		((TenantServiceImpl) tenantService).setDao(mockTenantDao.getMock());
		((TenantServiceImpl) tenantService).setTenantConfigurationDao(mockTenantConfigurationDao.getMock());
		((TenantServiceImpl) tenantService).setGroupDao(mockGroupDao.getMock());
		((TenantServiceImpl) tenantService).setSystemDao(mockSystemDao.getMock());
		((TenantServiceImpl) tenantService).setRoomDao(mockRoomDao.getMock());
		((TenantServiceImpl) tenantService).setEndpointUploadDao(mockEndpointUploadDao.getMock());
		
		((TenantServiceImpl) tenantService).setMemberService(mockMemberService.getMock());
		((TenantServiceImpl) tenantService).setIpcConfigurationService(mockIpcConfigurationService.getMock());
		((TenantServiceImpl) tenantService).setSystemService(mockSystemService.getMock());
	}
	
	@Test
	public void updateEndpointChatsStatusesSuccess() {
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(1);
		tenantConfiguration.setEndpointPublicChat(1);
		
		mockTenantConfigurationDao.returns(tenantConfiguration).getTenantConfiguration(1);
		mockTenantConfigurationDao.returns(1).updateTenantConfiguration(1, tenantConfiguration);
		
		int count = tenantService.updateEndpointChatsStatuses(1, 1, 1);
		
		assertEquals(1, count);
		
		mockTenantConfigurationDao.assertInvoked().updateTenantConfiguration(1, tenantConfiguration);
		
	}
	
	@Test
	public void updateEndpointChatsStatusesFail() {
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTenantId(1);
		tenantConfiguration.setEndpointPrivateChat(1);
		tenantConfiguration.setEndpointPublicChat(1);
		
		mockTenantConfigurationDao.returns(tenantConfiguration).getTenantConfiguration(1);
		mockTenantConfigurationDao.raises(CannotGetJdbcConnectionException.class).updateTenantConfiguration(1, tenantConfiguration);
		
		int count = tenantService.updateEndpointChatsStatuses(1, 1, 1);
		
		assertEquals(0, count);
		
		mockTenantConfigurationDao.assertInvoked().updateTenantConfiguration(1, tenantConfiguration);
		
	}
	
	@Test
	public void insertTenantSuccess1() {
		Member adminMember = new Member();
		adminMember.setMemberID(0);
		adminMember.setPassword("password");
		
		Tenant tenant = new Tenant();
		tenant.setTenantID(1);
		
		mockTenantDao.returns(1).insertTenant(tenant);
		mockGroupDao.returns(1).insertGroup(1, any(Group.class));
		
		try {
			mockMemberService.returns(true).isValidMemberPassword(adminMember.getPassword(), adminMember.getMemberID());
		} catch (Exception e) {
			// this case should not happen
			fail("Password validation does not pass");
		}
		
		mockSystemService.returns(true).isVidyoWebAvailable();
		mockSystemService.returns(true).isVidyoWebEnabledBySuper();
		
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(true);
		portalChat.setDefaultPublicChatEnabled(true);
		portalChat.setDefaultPrivateChatEnabled(true);
		mockSystemService.returns(portalChat).getPortalChat();

		int tenId = 0;
		try {
			tenId = tenantService.insertTenant(tenant, adminMember);
		} catch (Exception e) {
			// this case should not happen
			fail("insertTenant fails.");
		}
		
		assertEquals(1, tenId);
		

		mockTenantDao.assertInvoked().insertTenant(tenant);
		mockTenantConfigurationDao.assertInvoked().insertTenantConfiguration(any(TenantConfiguration.class));
		mockGroupDao.assertInvoked().insertGroup(1, any(Group.class));
		//mockSystemDao.assertInvoked().saveGuestConfGroupID(1, 1);
		mockMemberDao.assertInvoked().insertMember(1, any(Member.class));
		mockRoomDao.assertInvoked().insertRoom(1, any(Room.class));
		mockEndpointUploadDao.assertInvoked().copyEndpointUploadsFromDefaultTenantToNew(1);
		mockTenantDao.assertInvoked().deleteTenantXCall(1);
		mockTenantDao.assertInvoked().insertTenantXCall(1, 1);
		mockTenantDao.assertInvoked().deleteTenantXrouter(1);
		mockTenantDao.assertInvoked().deleteTenantXservice(1);
		//mockSystemDao.assertInvoked().saveGuestConfProxyID(1, 0);
		mockTenantDao.assertInvoked().deleteTenantXlocation(1);
		mockTenantDao.assertInvoked().setInstalls(tenant, 1);
		mockTenantDao.assertInvoked().setSeats(tenant, 1);
		mockTenantDao.assertInvoked().setPorts(tenant, 1);
		mockTenantDao.assertInvoked().setGuestLogin(tenant, 1);
		mockTenantDao.assertInvoked().setExecutives(tenant, 1);
		mockTenantDao.assertInvoked().setPanoramas(tenant, 1);
		mockIpcConfigurationService.assertInvoked().saveIpcConfiguration(any(IpcConfiguration.class));
		mockSystemService.assertInvoked().saveVidyoWebEnabledForAdmin(1, true);
	}
	
	@Test
	public void deleteTenantSuccess() {
		Tenant tenant = new Tenant();
		tenant.setTenantID(1);
		tenant.setTenantName("tenantName");
		tenant.setTenantURL("url");
		
		mockTenantDao.returns(tenant).getTenant(1);
		mockTenantDao.returns(1).deleteTenant(1);
		
		int count = tenantService.deleteTenant(1);
		
		assertEquals(1, count);
		
		mockTenantDao.assertInvoked().getTenant(1);
		mockTenantDao.assertInvoked().updateTenantById(tenant.getTenantID());
		mockTenantDao.assertInvoked().updateTenantByName(tenant.getTenantName());
		mockTenantDao.assertInvoked().updateTenantByUrl(tenant.getTenantURL());
		
		mockTenantDao.assertInvoked().deleteRoomsForTenant(1);
		mockTenantDao.assertInvoked().deleteMembersForTenant(1);
		mockTenantDao.assertInvoked().deleteGroupsForTenant(1);
		mockTenantDao.assertInvoked().deleteConfigurationForTenant(1);
		mockTenantDao.assertInvoked().deleteServicesForTenant(1);
		
		mockTenantConfigurationDao.assertInvoked().deleteTenantConfiguration(1);
		
		mockTenantDao.assertInvoked().deleteTenant(1);
		mockTenantDao.assertInvoked().deleteAllTenantXCall(1);
		mockTenantDao.assertInvoked().deleteTenantXrouter(1);
		mockIpcConfigurationService.assertInvoked().deleteIpcConfiguration(1);
	}

}
