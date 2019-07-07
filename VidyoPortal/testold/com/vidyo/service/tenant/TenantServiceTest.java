/**
 * 
 */
package com.vidyo.service.tenant;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Tenant;
import com.vidyo.db.ITenantDao;
import com.vidyo.service.ITenantService;
import com.vidyo.service.TenantServiceImpl;

/**
 * @author ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantServiceTest {

	/**
	 * 
	 */
	@SpringBeanByType
	private ITenantService tenantService;

	/**
	 * 
	 */
	private Mock<ITenantDao> mockTenantDao;

	/**
	 * 
	 */
	@Before
	public void initialize() {
		((TenantServiceImpl) tenantService).setDao(mockTenantDao.getMock());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidId() {
		Tenant tenant = new Tenant();
		tenant.setTenantID(191);
		mockTenantDao.returns(tenant).getTenant(191);
		Tenant tenantVal = tenantService.getTenant(191);
		Assert.assertNotNull(tenantVal);
		Assert.assertEquals(191, tenantVal.getTenantID());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidId() {
		mockTenantDao.raises(new IncorrectResultSizeDataAccessException(1))
				.getTenant(10001);
		Tenant tenantVal = null;
		tenantVal = tenantService.getTenant(10001);
		Assert.assertNull(tenantVal);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidName() {
		Tenant testTenant = new Tenant();
		testTenant.setTenantID(191);
		testTenant.setTenantName("MyTenant");
		mockTenantDao.returns(testTenant).getTenant("MyTenant");
		Tenant actualTenant = tenantService.getTenant("MyTenant");
		Assert.assertNotNull(actualTenant);
		Assert.assertEquals(testTenant.getTenantID(),
				actualTenant.getTenantID());
		Assert.assertEquals(testTenant.getTenantName(),
				actualTenant.getTenantName());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidName() {
		mockTenantDao.raises(new IncorrectResultSizeDataAccessException(1))
				.getTenant("InvalidTenantName");
		Tenant tenantVal = tenantService.getTenant("InvalidTenantName");
		Assert.assertNull(tenantVal);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidUrl() {
		Tenant testTenant = new Tenant();
		testTenant.setTenantID(191);
		testTenant.setTenantURL("valid.tenant.com");
		mockTenantDao.returns(testTenant).getTenant("valid.tenant.com");
		Tenant actualTenant = tenantService.getTenant("valid.tenant.com");
		Assert.assertNotNull(actualTenant);
		Assert.assertEquals(testTenant.getTenantID(),
				actualTenant.getTenantID());
		Assert.assertEquals(testTenant.getTenantURL(),
				actualTenant.getTenantURL());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidUrl() {
		mockTenantDao.raises(new IncorrectResultSizeDataAccessException(1))
				.getTenantByURL("invalid.tenant.com");
		Tenant tenantVal = tenantService.getTenantByURL("invalid.tenant.com");
		Assert.assertNull(tenantVal);
	}

}
