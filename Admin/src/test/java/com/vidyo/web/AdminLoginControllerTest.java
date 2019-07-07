package com.vidyo.web;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import org.springframework.mock.web.MockHttpServletResponse;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;

public class AdminLoginControllerTest {

	private static final int REFERENCE_TENANT_ID = 532;

	@Mock
	protected ISystemService system;
	
	@Mock
	private CookieLocaleResolver lr;
	
	@InjectMocks
	private AdminLoginController adminLoginController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(REFERENCE_TENANT_ID);
		when(lr.resolveLocale(Mockito.any(HttpServletRequest.class))).thenReturn(Locale.ITALIAN);
	}

	@Test
	public void testChangeLanguageSuccess() throws Exception {
		MockHttpServletResponse response = new MockHttpServletResponse();
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("lang")).thenReturn("it");
		
		adminLoginController.getLoginHtml(request, response);
		
		Cookie cookie = response.getCookies()[0];
		assertArrayEquals(new String[] { cookie.getName() }, new String[] { "VidyoPortalAdminLanguage" });
		assertArrayEquals(new String[] { cookie.getValue() }, new String[] { "it" });
	}
}
