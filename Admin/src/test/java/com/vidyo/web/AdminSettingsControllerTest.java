package com.vidyo.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationServiceImpl;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;

public class AdminSettingsControllerTest {

	private static final String FIELDS = "fields";
	private static final String SUCCESS = "success";

	private static final String TYTO_USERNAME = "tytoUsername";
	private static final String TYTO_PASSWORD = "tytoPassword";
	private static final String TYTO_URL = "tytoUrl";
	private static final String NOTIFICATION_PASSWORD = "notificationPassword";
	private static final String NOTIFICATION_USER = "notificationUser";
	private static final String NOTIFICATION_URL = "notificationUrl";
	private static final String SHARED_SECRET = "sharedSecret";

	private static final String USER = "user";
	private static final String EMPTY_PASSWORD = "";
	private static final String URL = "url";
	private static final String SECRET = "secret";

	private static final String ENABLE_TYTO_CARE_INTEGRATION = "enableTytoCareIntegration";
	private static final String ENABLE_EPIC_INTEGRATION = "enableEpicIntegration";

	@InjectMocks
	private AdminSettingsController settingsController;

	@Mock
	private IUserService user;

	@Mock
	private ISystemService system;

	@Mock
	private ExtIntegrationServiceImpl extIntegrationService;

	@Mock
	private ITenantService tenantService;

	@Mock
	private IMemberService member;

	private ReloadableResourceBundleMessageSource ms;

	private Map<String, Object> getModel(ModelAndView result) {
		return (Map<String, Object>) result.getModel().get("model");
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
		ms = new ReloadableResourceBundleMessageSource();
		ms.setUseCodeAsDefaultMessage(true);
		ReflectionTestUtils.setField(settingsController, "ms", ms);
	}

	@Test
	public void testGetTytoCareIntegrationAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(true);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTytoIntegrationMode(1);
		tenantConfiguration.setTytoUrl(TYTO_URL);
		tenantConfiguration.setTytoUsername(TYTO_USERNAME);
		tenantConfiguration.setTytoPassword(TYTO_PASSWORD);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);

		ModelAndView result = settingsController.getTytoCareIntegrationAjax(request, response);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, getModel(result).get(ENABLE_TYTO_CARE_INTEGRATION));
		Assert.assertEquals(TYTO_URL, getModel(result).get(TYTO_URL));
		Assert.assertEquals(TYTO_USERNAME, getModel(result).get(TYTO_USERNAME));
		Assert.assertEquals(SystemServiceImpl.PASSWORD_UNCHANGED, getModel(result).get(TYTO_PASSWORD));	
	}

	@Test
	public void testGetEpicIntegrationAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isTenantEpicEnabled()).thenReturn(true);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(ExternalDataTypeEnum.EPIC.ordinal());
		tenantConfiguration.setExtIntegrationSharedSecret(SECRET);
		tenantConfiguration.setExternalNotificationUrl(URL);
		tenantConfiguration.setExternalUsername(USER);
		tenantConfiguration.setExternalPassword(EMPTY_PASSWORD);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);

		ModelAndView result = settingsController.getEpicIntegrationAjax(request, response);
		Assert.assertNotNull(result);
		
		Assert.assertEquals(1, getModel(result).get(ENABLE_EPIC_INTEGRATION));
		Assert.assertEquals(SECRET, getModel(result).get(SHARED_SECRET));
		Assert.assertEquals(URL, getModel(result).get(NOTIFICATION_URL));
		Assert.assertEquals(USER, getModel(result).get(NOTIFICATION_USER));
		Assert.assertEquals(EMPTY_PASSWORD, getModel(result).get(NOTIFICATION_PASSWORD));
	}

	@Test
	public void testFeaturesDisabled() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalEpicEnabled()).thenReturn(false);
		ModelAndView result = settingsController.saveEpicIntegrationAdminAjax(request, response);
		Assert.assertNull(result);

		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(false);
		result = settingsController.saveTytoCareIntegrationAdminAjax(request, response);
		Assert.assertNull(result);
	}

	@Test
	public void testSaveEpicIntegrationAdminAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalEpicEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_EPIC_INTEGRATION)).thenReturn("1");
		when(request.getParameter(SHARED_SECRET)).thenReturn(SECRET);
		when(request.getParameter(NOTIFICATION_URL)).thenReturn(URL);
		when(request.getParameter(NOTIFICATION_USER)).thenReturn(USER);
		when(request.getParameter(NOTIFICATION_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(1);

		ModelAndView result = settingsController.saveEpicIntegrationAdminAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
	}
	
	@Test
	public void testDisableEpicWithoutDeletePrevConfiguration() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalEpicEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_EPIC_INTEGRATION)).thenReturn("0");
		when(request.getParameter(SHARED_SECRET)).thenReturn("xxx");
		when(request.getParameter(NOTIFICATION_URL)).thenReturn("");
		when(request.getParameter(NOTIFICATION_USER)).thenReturn("");
		when(request.getParameter(NOTIFICATION_PASSWORD)).thenReturn("");
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(1);
		tenantConfiguration.setExtIntegrationSharedSecret(SECRET);
		tenantConfiguration.setExternalNotificationUrl(URL);
		tenantConfiguration.setExternalUsername(USER);
		tenantConfiguration.setExternalPassword(EMPTY_PASSWORD);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(1);

		ModelAndView result = settingsController.saveEpicIntegrationAdminAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
		
		Assert.assertEquals(0, tenantConfiguration.getExternalIntegrationMode());
		Assert.assertEquals(SECRET, tenantConfiguration.getExtIntegrationSharedSecret());
		Assert.assertEquals(URL, tenantConfiguration.getExternalNotificationUrl());
		Assert.assertEquals(USER, tenantConfiguration.getExternalUsername());
		Assert.assertEquals(EMPTY_PASSWORD, tenantConfiguration.getExternalPassword());
	}

	@Test
	public void testSaveTytoCareIntegrationAdminAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("1");
		when(request.getParameter(TYTO_URL)).thenReturn(URL);
		when(request.getParameter(TYTO_USERNAME)).thenReturn(USER);
		when(request.getParameter(TYTO_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(1);

		ModelAndView result = settingsController.saveTytoCareIntegrationAdminAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
	}
	
	@Test
	public void testDisableTytoCareWithoutDeletePrevConfiguration() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("0");
		when(request.getParameter(TYTO_URL)).thenReturn("");
		when(request.getParameter(TYTO_USERNAME)).thenReturn("");
		when(request.getParameter(TYTO_PASSWORD)).thenReturn("");
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setTytoIntegrationMode(1);
		tenantConfiguration.setTytoUrl(URL);
		tenantConfiguration.setTytoUsername(USER);
		tenantConfiguration.setTytoPassword(EMPTY_PASSWORD);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(1);

		ModelAndView result = settingsController.saveTytoCareIntegrationAdminAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
		
		Assert.assertEquals(0, tenantConfiguration.getTytoIntegrationMode());
		Assert.assertEquals(URL, tenantConfiguration.getTytoUrl());
		Assert.assertEquals(USER, tenantConfiguration.getTytoUsername());
		Assert.assertEquals(EMPTY_PASSWORD, tenantConfiguration.getTytoPassword());
	}

	@Test
	public void testSaveEpicIntegrationAdminAjaxFailed() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalEpicEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_EPIC_INTEGRATION)).thenReturn("1");
		when(request.getParameter(SHARED_SECRET)).thenReturn(SECRET);
		when(request.getParameter(NOTIFICATION_URL)).thenReturn(URL);
		when(request.getParameter(NOTIFICATION_USER)).thenReturn(USER);
		when(request.getParameter(NOTIFICATION_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(0);

		ModelAndView result = settingsController.saveEpicIntegrationAdminAjax(request, response);
		Assert.assertEquals(false, getModel(result).get(SUCCESS));
		Assert.assertNotNull(getModel(result).get(FIELDS));
	}

	@Test
	public void testSaveTytoCareIntegrationAdminAjaxFailed() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("1");
		when(request.getParameter(TYTO_URL)).thenReturn(URL);
		when(request.getParameter(TYTO_USERNAME)).thenReturn(USER);
		when(request.getParameter(TYTO_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);
		when(tenantService.updateTenantConfiguration(any())).thenReturn(0);

		ModelAndView result = settingsController.saveTytoCareIntegrationAdminAjax(request, response);
		Assert.assertEquals(false, getModel(result).get(SUCCESS));
		Assert.assertNotNull(getModel(result).get(FIELDS));
	}
	
	/*@Test
	public void testTytoCareConnectionFailed() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("1");
		when(request.getParameter(TYTO_URL)).thenReturn("https://app-stage1.tytocare.com");
		when(request.getParameter(TYTO_USERNAME)).thenReturn(USER);
		when(request.getParameter(TYTO_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		when(system.restTytoCareAuthentication(any(), any(), any())).thenReturn(false);
		
		ModelAndView result = settingsController.testTytoCareConnection(request, response);
		Assert.assertEquals(false, getModel(result).get(SUCCESS));
		Assert.assertNotNull(getModel(result).get(FIELDS));
	}*/
	
	/*@Test
	public void testTytoCareConnectionSuccess() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);
		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("1");
		when(request.getParameter(TYTO_URL)).thenReturn("https://app-stage1.tytocare.com");
		when(request.getParameter(TYTO_USERNAME)).thenReturn(USER);
		when(request.getParameter(TYTO_PASSWORD)).thenReturn(EMPTY_PASSWORD);
		when(system.restTytoCareAuthentication(any(), any(), any())).thenReturn(true);
		
		ModelAndView result = settingsController.testTytoCareConnection(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
		Assert.assertNull(getModel(result).get(FIELDS));
	}*/
}
