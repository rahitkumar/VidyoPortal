/**
 * 
 */
package com.vidyo.service.license;

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
import com.vidyo.db.license.LicenseDao;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.LicensingServiceImpl;
import com.vidyo.service.TenantServiceImpl;

/**
 * @author ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
//@RunWith(UnitilsJUnit4TestClassRunner.class)
public class LicensingServiceTests {

	/**
	 * 
	 */
	@SpringBeanByType
	private LicensingService licensingService;

	/**
	 * 
	 */
	private Mock<LicenseDao> mockLicenseDao;

	/**
	 * 
	 */
	@Before
	public void initialize() {
		((LicensingServiceImpl) licensingService).setLicenseDao(mockLicenseDao.getMock());
	}


}
