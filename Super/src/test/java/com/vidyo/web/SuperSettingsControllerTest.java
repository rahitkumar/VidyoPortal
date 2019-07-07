package com.vidyo.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationServiceImpl;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class SuperSettingsControllerTest {

	private static final String ENABLE_EPIC_INTEGRATION = "enableEpicIntegration";
	private static final String ENABLE_TYTO_CARE_INTEGRATION = "enableTytoCareIntegration";
	
	private static final String SUCCESS = "success";
	
	@InjectMocks
	private SuperSettingsController settingsController;
	
	@Mock
	private ExtIntegrationServiceImpl extIntegrationService;

	@Mock
	private ITenantService tenantService;
	
	@Mock
	private ISystemService system;
	
	
	private Map<String, Object> getModel(ModelAndView result) {
		return (Map<String, Object>) result.getModel().get("model");
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
	}
	
	@Test
	public void testGetTytoCareIntegrationAttributeHtml() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(extIntegrationService.isGlobalTytoCareEnabled()).thenReturn(true);

		ModelAndView result = settingsController.getTytoCareIntegrationAttributeHtml(request, response);
		Assert.assertNotNull(result);
		Assert.assertEquals(true, getModel(result).get(ENABLE_TYTO_CARE_INTEGRATION));
	}
	
	@Test
	public void testGetEpicIntegrationAttributeHtml() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		when(extIntegrationService.isGlobalEpicEnabled()).thenReturn(true);

		ModelAndView result = settingsController.getEpicIntegrationAttributeHtml(request, response);
		Assert.assertNotNull(result);
		Assert.assertEquals(true, getModel(result).get(ENABLE_EPIC_INTEGRATION));
	}
	
	@Test
	public void testSaveEnableEpicIntegrationAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(request.getParameter(ENABLE_EPIC_INTEGRATION)).thenReturn("1");
		when(system.updateSystemConfig(any(), any())).thenReturn(1);

		ModelAndView result = settingsController.saveEnableEpicIntegrationAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
	}

	@Test
	public void testSaveEnableTytoCareIntegrationAjax() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(request.getParameter(ENABLE_TYTO_CARE_INTEGRATION)).thenReturn("1");
		when(system.updateSystemConfig(any(), any())).thenReturn(1);

		ModelAndView result = settingsController.saveEnableTytoCareIntegrationAjax(request, response);
		Assert.assertEquals(true, getModel(result).get(SUCCESS));
	}
}
