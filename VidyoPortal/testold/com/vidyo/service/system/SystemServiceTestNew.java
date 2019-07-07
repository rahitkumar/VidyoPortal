/**
 * 
 */
package com.vidyo.service.system;

import junit.framework.Assert;

import org.apache.axis2.AxisFault;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Service;
import com.vidyo.db.ISystemDao;
import com.vidyo.framework.propertyconfig.OpenPropertyPlaceholderConfigurer;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

/**
 * Unit test class to test SystemService Implementation
 * 
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class SystemServiceTestNew {

	@SpringBeanByType
	private SystemService systemService;

	private Mock<ISystemDao> mockSystemDao;

	private Mock<OpenPropertyPlaceholderConfigurer> propertyPlaceholderConfigurerMock;

	@Before
	public void initialize() {
		((com.vidyo.service.system.SystemServiceImpl) systemService)
				.setSystemDao(mockSystemDao.getMock());
		((com.vidyo.service.system.SystemServiceImpl) systemService)
				.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());

	}

	/**
	 * TestCase for getting VidyoManager service url
	 * 
	 * @throws NoVidyoManagerException
	 * @throws AxisFault
	 */
	@Test
	public void testGetVidyoManagerServiceStubWithAuth() throws NoVidyoManagerException, AxisFault {
		Service service = new Service();
		service.setUrl("http://127.0.01:17995");
		mockSystemDao.returns(service).getVidyoManagerService(1);
		VidyoManagerServiceStub serviceStub = systemService.getVidyoManagerServiceStubWithAuth(1);
		Assert.assertNotNull("Service Cannot be null", serviceStub);
		// Assert.assertEquals("https://127.0.0.1:17995/services/VidyoManagerService",
		// service1.getUrl());
	}

}
