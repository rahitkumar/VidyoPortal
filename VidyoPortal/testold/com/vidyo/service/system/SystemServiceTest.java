/**
 * 
 */
package com.vidyo.service.system;

import java.io.IOException;

import junit.framework.Assert;

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
import com.vidyo.service.ISystemService;

/**
 * Unit test class to test SystemService Implementation
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class SystemServiceTest {

	@SpringBeanByType
	private ISystemService systemService;

	private Mock<ISystemDao> mockSystemDao;

	private Mock<OpenPropertyPlaceholderConfigurer> propertyPlaceholderConfigurerMock;

	@Before
	public void initialize() {
		((com.vidyo.service.SystemServiceImpl) systemService)
				.setDao(mockSystemDao.getMock());
		((com.vidyo.service.SystemServiceImpl) systemService)
				.setPropertyPlaceholderConfigurer(propertyPlaceholderConfigurerMock
						.getMock());
	}

	/**
	 * TestCase for getting desktop guide location
	 * @throws IOException
	 */
	@Test
	public void testGetDeskGuideLocation() throws IOException {
		systemService.getConfigValue(0, "deskguidelocation_en");
		systemService.getConfigValue(0, "PortalVersion");
		propertyPlaceholderConfigurerMock
				.returns("VidyoPortal_and_Vidyo_Desktop_User_Guide")
				.getMergedProperties().getProperty("desk", null);
		propertyPlaceholderConfigurerMock
				.returns("http://www.vidyo.com/documents/support/")
				.getMergedProperties().getProperty("baseUrl", null);

		propertyPlaceholderConfigurerMock.returns(".pdf").getMergedProperties()
				.getProperty("guide_extn", null);
		try {
			String val = systemService.getGuideLocation("fr", "desk");
			Assert.assertNotNull("GuideLocation Cannot be null", val);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TestCase for getting admin guide location
	 * @throws IOException
	 */
	@Test
	public void testGetAdminGuideLocation() throws IOException {
		systemService.getConfigValue(0, "adminguidelocation_en");
		systemService.getConfigValue(0, "PortalVersion");
		propertyPlaceholderConfigurerMock
				.returns("VidyoConferencing_Administrators_Guide")
				.getMergedProperties().getProperty("admin", null);
		propertyPlaceholderConfigurerMock
				.returns("http://www.vidyo.com/documents/support/")
				.getMergedProperties().getProperty("baseUrl", null);

		propertyPlaceholderConfigurerMock.returns(".pdf").getMergedProperties()
				.getProperty("guide_extn", null);
		try {
			String val = systemService.getGuideLocation("fr", "admin");
			Assert.assertNotNull("GuideLocation Cannot be null", val);
			Assert.assertEquals("http://www.vidyo.com/documents/support/v2.2.1/FR/VidyoConferencing_Administrators_Guide_FR.pdf", val);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TestCase for getting VidyoManager service url
	 */
	@Test
	public void testGetVidyoManagerService() {
		//((com.vidyo.service.SystemServiceImpl)systemService).setTenant(new TenantHolder());
		com.vidyo.bo.Service service = new Service();
		service.setUrl("https://127.0.0.1:17995");
		mockSystemDao.returns(service).getVidyoManagerService(1);
		Service service1 = systemService.getVidyoManagerService();
		Assert.assertNotNull("Service Cannot be null", service1);
		Assert.assertNotNull("URL Cannot be null", service1.getUrl());
		Assert.assertEquals("https://127.0.0.1:17995/services/VidyoManagerService", service1.getUrl());
	}
	
}
