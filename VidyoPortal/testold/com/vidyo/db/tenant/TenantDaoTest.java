/**
 * 
 */
package com.vidyo.db.tenant;

import static org.junit.Assert.*;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.bo.Tenant;
import com.vidyo.db.BaseDAOTest;
import com.vidyo.db.ITenantDao;
import com.vidyo.db.TenantDaoJdbcImpl;

/**
 * @author ganesh
 * 
 */
@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantDaoTest extends BaseDAOTest {

	private ITenantDao tenantDao;

	@Before
	public void initializeDao() {
		tenantDao = new TenantDaoJdbcImpl();
		((TenantDaoJdbcImpl) tenantDao).setDataSource(getDataSource());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidUrl() {
		String tenantUrl = "valid.tenant.com";
		Tenant tenant = tenantDao.getTenantByURL(tenantUrl);
		Assert.assertNotNull(tenant);
		Assert.assertEquals(tenantUrl, tenant.getTenantURL());
		Assert.assertEquals(191, tenant.getTenantID());
		Assert.assertEquals("301", tenant.getTenantPrefix());
		Assert.assertEquals(10, tenant.getInstalls());
		Assert.assertEquals(5, tenant.getSeats());
		Assert.assertEquals(30, tenant.getPorts());
		Assert.assertEquals(1, tenant.getGuestLogin());
		Assert.assertEquals(4, tenant.getExecutives());
		Assert.assertEquals(12, tenant.getPanoramas());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidUrl() {
		String tenantUrl = "invalid.tenant.com";
		Tenant tenant = null;
		Throwable t = null;
		try {
			tenant = tenantDao.getTenantByURL(tenantUrl);
		} catch (DataAccessException e) {
			t = e;
		}
		Assert.assertNull(tenant);
		Assert.assertTrue(t instanceof DataAccessException);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidName() {
		String tenantName = "MyTenant";
		Tenant tenant = tenantDao.getTenant(tenantName);
		Assert.assertNotNull(tenant);
		Assert.assertEquals(tenantName, tenant.getTenantName());
		Assert.assertEquals(191, tenant.getTenantID());
		Assert.assertEquals("301", tenant.getTenantPrefix());
		Assert.assertEquals(10, tenant.getInstalls());
		Assert.assertEquals(5, tenant.getSeats());
		Assert.assertEquals(30, tenant.getPorts());
		Assert.assertEquals(1, tenant.getGuestLogin());
		Assert.assertEquals(4, tenant.getExecutives());
		Assert.assertEquals(12, tenant.getPanoramas());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidName() {
		String tenantName = "InvalidTenant";
		Tenant tenant = null;
		Throwable t = null;
		try {
			tenant = tenantDao.getTenant(tenantName);
		} catch (DataAccessException e) {
			t = e;
		}
		Assert.assertNull(tenant);
		Assert.assertTrue(t instanceof DataAccessException);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidId() {
		int tenantId = 191;
		Tenant tenant = tenantDao.getTenant(tenantId);
		Assert.assertNotNull(tenant);
		Assert.assertEquals(tenantId, tenant.getTenantID());
		Assert.assertEquals("301", tenant.getTenantPrefix());
		Assert.assertEquals(10, tenant.getInstalls());
		Assert.assertEquals(5, tenant.getSeats());
		Assert.assertEquals(30, tenant.getPorts());
		Assert.assertEquals(1, tenant.getGuestLogin());
		Assert.assertEquals(4, tenant.getExecutives());
		Assert.assertEquals(12, tenant.getPanoramas());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidId() {
		int tenantId = 1001;
		Tenant tenant = null;
		Throwable t = null;
		try {
			tenant = tenantDao.getTenant(tenantId);
		} catch (DataAccessException e) {
			t = e;
		}
		Assert.assertNull(tenant);
		Assert.assertTrue(t instanceof DataAccessException);
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByValidReplayUrl() {
		String replayUrl = "replay.vidyo.com";
		Tenant tenant = tenantDao.getTenantByReplayURL(replayUrl);
		Assert.assertNotNull(tenant);
		Assert.assertEquals(replayUrl, tenant.getTenantReplayURL());
		Assert.assertEquals(191, tenant.getTenantID());
		Assert.assertEquals("301", tenant.getTenantPrefix());
		Assert.assertEquals(10, tenant.getInstalls());
		Assert.assertEquals(5, tenant.getSeats());
		Assert.assertEquals(30, tenant.getPorts());
		Assert.assertEquals(1, tenant.getGuestLogin());
		Assert.assertEquals(4, tenant.getExecutives());
		Assert.assertEquals(12, tenant.getPanoramas());
	}

	/**
	 * 
	 */
	@Test
	public void testGetTenantByInvalidReplayUrl() {
		String replayUrl = "invalidreplay.vidyo.com";
		Throwable t = null;
		Tenant tenant = null;
		try {
			tenant = tenantDao.getTenantByReplayURL(replayUrl);
		} catch (Exception e) {
			t = e;
		}
		Assert.assertNull(tenant);
		Assert.assertTrue(t instanceof DataAccessException);
	}
	
}
