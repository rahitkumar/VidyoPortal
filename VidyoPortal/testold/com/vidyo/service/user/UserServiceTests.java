/**
 * 
 */
package com.vidyo.service.user;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Tenant;
import com.vidyo.db.IUserDao;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.UserServiceImpl;

/**
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserServiceTests {

	@SpringBeanByType
	private IUserService userService;

	private Mock<IUserDao> userDaoMock;

	private Mock<ITenantService> tenantServiceMock;
	
	private UserServiceImpl userServiceMock;

	@Before
	public void initialize() {
		((UserServiceImpl) userService).setDao(userDaoMock.getMock());
		((UserServiceImpl) userService).setTenantService(tenantServiceMock.getMock());
		userServiceMock = EasyMock.createMockBuilder(UserServiceImpl.class).addMockedMethod("generateBAKforMember", int.class).createMock();
		userServiceMock.setTenantService(tenantServiceMock.getMock());
		userServiceMock.setDao(userDaoMock.getMock());
	}

	/**
	 * Tests the valid case.
	 */
	@Test
	public void testGetOnetimeAccessUrlValidTenant() {
		Tenant tenant = new Tenant();
		tenant.setTenantURL("dummy.url.com");
		tenantServiceMock.returns(tenant).getTenant(1);
		userDaoMock.returns(1).saveBAKforMember(1, null);
		AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(1, 1);
		Assert.assertNotNull(accessKeyResponse);
		Assert.assertEquals("dummy.url.com", accessKeyResponse.getTenantUrl());
	}

	/**
	 * Test the failure case with invalid tenant
	 */
	@Test
	public void testGetOnetimeAccessUrlInvalidTenant() {
		tenantServiceMock.returns(null).getTenant(1);
		AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(1, 1);
		Assert.assertNotNull(accessKeyResponse);
		Assert.assertEquals(AccessKeyResponse.INVALID_TENANT, accessKeyResponse.getStatus());
	}

	/**
	 * Tests the encryption failure case
	 */
	@Test
	public void testGetOnetimeAccessUrlSaveFail() {
		Tenant tenant = new Tenant();
		tenant.setTenantURL("dummy.url.com");
		tenantServiceMock.returns(tenant).getTenant(1);
		userDaoMock.onceRaises(new RuntimeException()).saveBAKforMember(1, null);
		AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(1, 1);
		Assert.assertNotNull(accessKeyResponse);
		Assert.assertEquals(AccessKeyResponse.KEY_GEN_FAILED, accessKeyResponse.getStatus());
	}
	
	@Test
	public void testGetOnetimeAccessUrlNullKey() {
		Tenant tenant = new Tenant();
		tenant.setTenantURL("dummy.url.com");
		tenantServiceMock.returns(tenant).getTenant(1);
		EasyMock.expect(userServiceMock.generateBAKforMember(1)).andReturn(null).anyTimes();		
		EasyMock.replay(userServiceMock);
		AccessKeyResponse accessKeyResponse = userServiceMock.getOnetimeAccessUri(1, 1);
		Assert.assertNotNull(accessKeyResponse);
		Assert.assertEquals(AccessKeyResponse.KEY_GEN_FAILED, accessKeyResponse.getStatus());		
	}

}
