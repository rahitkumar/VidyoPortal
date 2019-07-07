package com.vidyo.web.portal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.Attribute;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.PortalService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.PortalUtils;

public class ProtocolHandlerTest {

	private static final int REFERENCE_TENANT_ID = 10;
	private static final String REFERENCE_DIRECT_DIAL = "134";

	@InjectMocks
	private FlexUIController flexUIController;

	@Mock
	private IUserService userService;

	@Mock
	private IConferenceService conference;

	@Mock
	private ISystemService system;

	@Mock
	private IMemberService memberService;

	@Mock
	private IRoomService roomService;

	@Mock
	private EndpointUploadService endpoint;

	@Mock
	private ITenantService tenantService;

	@Mock
	private PortalService portalService;
	
	@Mock
	private IServiceService service;

	@Mock
	private TransactionService transactionService;

	private MockMvc mockMvc;
	
	private ReloadableResourceBundleMessageSource ms;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(flexUIController).build();
		TenantContext.setTenantId(REFERENCE_TENANT_ID);
		ms = new ReloadableResourceBundleMessageSource();
		ms.setUseCodeAsDefaultMessage(true);
		ReflectionTestUtils.setField(flexUIController, "ms", ms);
	}

	@Test
	public void testJoinLinkWithDirectDialSuccess() throws Exception {
		initMocksForDirectDial();
		
		MvcResult result = mockMvc.perform(get("/join/?directDial=" + REFERENCE_DIRECT_DIAL)).andExpect(status().isOk())
				.andReturn();
		String view = result.getModelAndView().getViewName();
		assertEquals(REFERENCE_DIRECT_DIAL, view.substring(view.indexOf("directDial") + 11));

		// flex.html
		result = mockMvc.perform(get("/flex.html?roomdirect.html&key=&directDial=" + REFERENCE_DIRECT_DIAL))
				.andExpect(status().isOk()).andReturn();
		assertEquals("vidyo", getModel(result).get("appProtocol"));
		assertEquals("sdfghh", getModel(result).get("key"));
		assertEquals(REFERENCE_DIRECT_DIAL, getModel(result).get("directDial"));
	}

	@Test
	public void testJoinLinkWithoutDirectDialFail() throws Exception {
		MvcResult result = mockMvc.perform(get("/flex.html?roomdirect.html&key=")).andExpect(status().isNotFound()).andReturn();
		assertEquals("externaldatatypeinvalid", result.getResponse().getErrorMessage());

		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(1);
		
		result = mockMvc.perform(get("/flex.html?roomdirect.html&key=sdfghh")).andExpect(status().isNotFound()).andReturn();
		assertEquals("invalidroom", result.getResponse().getErrorMessage());
	}

	@Test
	public void testJoinLinkWithDirectDialFail() throws Exception {
		initMocksForDirectDial();
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(1);
		
		mockMvc.perform(get("/flex.html?roomdirect.html&key=&directDial=test"))
				.andExpect(status().isNotFound()).andReturn();
		
		mockMvc.perform(get("/flex.html?roomdirect.html&key=&directDial="))
		.andExpect(status().isNotFound()).andReturn();
		
		mockMvc.perform(get("/flex.html?roomdirect.html&key=&directDial=1234"))
				.andExpect(status().isOk()).andReturn();
	}
	
	//@Test
	public void testSamlIndexHtmlDirectDialSuccess() throws Exception {
		initMocksForDirectDial();
		initMocksForDirectDialSaml();
		
		class TestNameId extends org.opensaml.saml2.core.impl.NameIDImpl {
			public TestNameId() {
				super("tt", "pp", "xx");
			}
		}
		class TestAssertion extends org.opensaml.saml2.core.impl.AssertionImpl {
			public TestAssertion() {
				super("tt", "pp", "xx");
			}
		}
		class TestSAMLAuthenticationToken extends AbstractAuthenticationToken {
			
			private SAMLCredential credentials;
			
			public TestSAMLAuthenticationToken(SAMLCredential credentials) {
				super(null);
				this.credentials = credentials;
			}
			
			public Object getCredentials() {
				return this.credentials;
			}
			
			public Object getPrincipal() {
		        return null;
		    }
		}
		
		String relay = PortalUtils.encodeBase64("{ \"directDial\": \"" + REFERENCE_DIRECT_DIAL + "\" }");
		SAMLCredential credentials = new SAMLCredential(new TestNameId(), new TestAssertion(), null, relay, new ArrayList<Attribute>(), null, null);
		org.springframework.security.core.Authentication authentication = new TestSAMLAuthenticationToken(credentials);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		MvcResult result = mockMvc.perform(get("/samlindex.html/?directDial=" + REFERENCE_DIRECT_DIAL)).andExpect(status().isOk())
				.andReturn();
		assertEquals("saml_neo_index_html", result.getModelAndView().getViewName());
		assertEquals(REFERENCE_DIRECT_DIAL, getModel(result).get("directDial"));
		assertNull(getModel(result).get("roomKey"));
	}
	
	//@Test
	public void testSamlIndexHtmlDirectDialNotFound() throws Exception {
		initMocksForDirectDial();
		initMocksForDirectDialSaml();
		
		MvcResult result = mockMvc.perform(get("/samlindex.html")).andExpect(status().isOk())
				.andReturn();
		assertEquals("saml_neo_index_html", result.getModelAndView().getViewName());
		assertNull(getModel(result).get("roomKey")); 
	}

	private Map getModel(MvcResult result) {
		return (Map) result.getModelAndView().getModelMap().get("model");
	}

	private void initMocksForDirectDialSaml() {
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(endpoint.isUploadModeExternal(anyInt())).thenReturn(true);
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		when(userService.generatePAKforMember(anyInt())).thenReturn(new PortalAccessKeys());
		when(service.getProxyCSVList(anyInt())).thenReturn("1");
		when(system.getAdminEndpointSettings(anyInt())).thenReturn(new EndpointSettings());
	}
	
	private void initMocksForDirectDial() {
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(system.getCustomizedImageLogoName()).thenReturn("test");
		Member member = new Member();
		member.setActive(1);
		member.setMemberName("Test name");
		when(memberService.getMember(anyInt())).thenReturn(member);
		Room room = new Room();
		room.setRoomKey("sdfghh");
		room.setMember(member);
		when(roomService.getRoom(anyInt())).thenReturn(room);
		when(roomService.areGuestAllowedToThisRoom()).thenReturn(true);
		EndpointUpload upload = new EndpointUpload();
		upload.setEndpointUploadFile("test.txt");
		upload.setEndpointUploadVersion("1.0");
		when(endpoint.getActiveEndpointForType(any(HttpServletRequest.class))).thenReturn(upload);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(new TenantConfiguration());
		when(portalService.getEncodedPortalFeatures()).thenReturn("");
	}
}
