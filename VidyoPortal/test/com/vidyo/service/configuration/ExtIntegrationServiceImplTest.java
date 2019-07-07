package com.vidyo.service.configuration;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationServiceImpl;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;

public class ExtIntegrationServiceImplTest {

	@InjectMocks
	private ExtIntegrationServiceImpl extIntegrationServiceImpl;
	
	@Mock
	private ITenantService tenantService;

	@Mock
	private ISystemService systemService;
	
	@Mock
	private IRoomService roomService;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
	}
	
	@Test
	public void testGlobalEpicDisabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("0");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		Assert.assertFalse(extIntegrationServiceImpl.isGlobalEpicEnabled());
	}
	
	@Test
	public void testGlobalEpicEnabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		Assert.assertTrue(extIntegrationServiceImpl.isGlobalEpicEnabled());
	}
	
	@Test
	public void testGlobalTytoCareDisabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("0");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		Assert.assertFalse(extIntegrationServiceImpl.isGlobalTytoCareEnabled());
	}
	
	@Test
	public void testGlobalTytoCareEnabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		Assert.assertTrue(extIntegrationServiceImpl.isGlobalTytoCareEnabled());
	}
	
	@Test
	public void testTenantEpicDisabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(ExternalDataTypeEnum.VIDYO.ordinal());
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		Assert.assertFalse(extIntegrationServiceImpl.isTenantEpicEnabled());
	}
	
	@Test
	public void testTenantEpicEnabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.EPIC_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(ExternalDataTypeEnum.EPIC.ordinal());
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		Assert.assertTrue(extIntegrationServiceImpl.isTenantEpicEnabled());
	}
	
	@Test
	public void testTenantTytoCareDisabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTytoIntegrationMode(ExternalDataTypeEnum.VIDYO.ordinal());
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		Assert.assertFalse(extIntegrationServiceImpl.isTenantTytoCareEnabled());
	}
	
	@Test
	public void testTenantTytoCareEnabled() {
		Configuration conf = new Configuration();
		conf.setConfigurationName(SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name());
		conf.setConfigurationValue("1");
		when(systemService.getConfiguration(
				SystemConfigurationEnum.TYTOCARE_INTEGRATION_SUPPORT.name())).thenReturn(conf);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTytoIntegrationMode(1);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		Assert.assertTrue(extIntegrationServiceImpl.isTenantTytoCareEnabled());
	}
}
