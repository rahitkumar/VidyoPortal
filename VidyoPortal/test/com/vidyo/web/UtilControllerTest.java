package com.vidyo.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;

public class UtilControllerTest {
	
	private static class DummyUtilController extends UtilController {		

		@Override
		protected String getViewName() { return "test";
		}

		@Override
		protected void complementHomeModel(HttpServletRequest request, HttpServletResponse response,
				Map<String, Object> model) throws Exception {			
			return;			
		}

		@Override
		protected String languagePropertyName() {
			return "test";
		}		
	}

	@Mock
	private IUserService user;

	@Mock
	protected ISystemService system;

	@Mock
	private CookieLocaleResolver lr;
	
	@InjectMocks
	private DummyUtilController dummyUtilController;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(lr.resolveLocale(Mockito.any(HttpServletRequest.class))).thenReturn(Locale.ENGLISH);

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testEscappingHtmlAddress() throws Exception {

		HttpServletRequest r = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		when(r.getSession()).thenReturn(session);
		when(session.getAttribute(Mockito.eq("session_lang"))).thenReturn("session_lang");
		when(r.getHeader("X-FORWARDED-FOR")).thenReturn("<script>");
		ModelAndView result = dummyUtilController.getHomeHtml(r, mock(HttpServletResponse.class));

		assertEquals("&lt;script&gt;", ((Map<String, String>) result.getModelMap().get("model")).get("ipAddress"));
	}

}
