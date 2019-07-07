/**
 * 
 */
package com.vidyo.db.ipc;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.db.BaseDAOTest;
import com.vidyo.db.InterPortalConferenceDao;
import com.vidyo.db.InterPortalConferenceDaoJdbcImpl;

/**
 * @author ganesh
 *
 */
@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class InterPortalConferenceDaoTest extends BaseDAOTest {

	/**
	 * 
	 */
	private InterPortalConferenceDao interPortalConferenceDao;

	@Before
	public void initializeDao() {
		interPortalConferenceDao = new InterPortalConferenceDaoJdbcImpl();
		((InterPortalConferenceDaoJdbcImpl) interPortalConferenceDao).setDataSource(getDataSource());
	}
	
	/**
	 * Validates null return for Tenant with no hostnames configured. <br>
	 * This test case is to check if the Tenant with zero blocked list returns null.
	 */
	@Test
	public void testGetNoHostnameMatch() {
		String hostnameMatch = interPortalConferenceDao.getHostnameMatch(1, "main.vidyo.com");
		Assert.assertNull(hostnameMatch);
	}
	
	@Test
	public void testGetHostnameMatch() {
		String hostnameMatch = interPortalConferenceDao.getHostnameMatch(2, "main.vidyo.com");
		Assert.assertNotNull(hostnameMatch);
		Assert.assertEquals("*.vidyo.com", hostnameMatch);
	}

}
