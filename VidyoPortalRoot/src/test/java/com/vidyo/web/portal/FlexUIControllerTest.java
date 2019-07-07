package com.vidyo.web.portal;

import static org.mockito.Mockito.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ExtIntegrationServiceImpl;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.PortalService;
import com.vidyo.service.TestCallService;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.transaction.TransactionService;

public class FlexUIControllerTest {

	@InjectMocks
	private FlexUIController flexUIController;

	@Mock
	private IUserService userService;
	
	@Mock
	private IConferenceService conference;
	
	@InjectMocks
	private ExtIntegrationServiceImpl extIntegrationService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private IRoomService roomService;

	@Mock
	private ITenantService tenantService;

	@Mock
	private ISystemService systemService;
	
	@Mock
	private TestCallService testCallService;
	
	@Mock
	private PortalService portalService;
	
	@Mock
	private TransactionService transactionService;
	
	@Mock
	private EndpointUploadService endpoint;	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
		flexUIController.setExtIntegrationService(extIntegrationService);
		flexUIController.setConferenceService(conference);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetControlMeetingHtmlBAKWorkflows() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("bak")).thenReturn("jdsfhsdkjfsdfS");
		when(request.getParameter("key")).thenReturn("someroomkey");
		when(request.getHeader("host")).thenReturn("somehost");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));

		Member member = new Member();
		member.setMemberID(4);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS", MemberBAKType.MorderatorURL)).thenReturn(member);
		doNothing().when(userService).deleteMemberBAK("jdsfhsdkjfsdfS");
		when(userService.generateBAKforMember(4)).thenReturn("kjdshfkjhsdkfh");
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));

		Room room = new Room();
		room.setRoomID(5);
		room.setMemberID(4);
		room.setRoomName("Room1");
		room.setDisplayName("Room1");
		room.setRoomType("Scheduled");
		room.setRoomPIN("1234");
		room.setLectureMode(0);
		room.setTenantID(3);
		when(roomService.getRoomForModeratorKey("someroomkey")).thenReturn(room);

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		scheduledRoomResponse.setStatus(0);
		when(roomService.generateSchRoomExtPin(4, 1234)).thenReturn(scheduledRoomResponse);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setWaitingRoomsEnabled(1);
		tenantConfiguration.setLectureModeAllowed(1);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);

		when(systemService.getCustomizedImageLogoName()).thenReturn(null);
		when(systemService.getCustomizedSuperPortalImageLogoName()).thenReturn("mylogoname");

		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		ModelAndView result = flexUIController.getControlMeetingHtml(request, response);

		Assert.assertNotNull(result);
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("authenticated"), "true");
		verify(request, times(1)).getParameter("bak");
		verify(request, times(2)).getParameter("key");
		verify(request, times(1)).getHeader("host");
		verify(request, times(1)).getSession(true);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS", MemberBAKType.MorderatorURL);
		verify(userService, times(1)).deleteMemberBAK("jdsfhsdkjfsdfS");
		verify(userService, times(1)).generateBAKforMember(4);
		verify(userService, times(1)).getLoginUser();
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(roomService, times(1)).getRoomForModeratorKey("someroomkey");
		// TODO verify(roomService, times(1)).generateSchRoomExtPin(4, 1234);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(systemService, times(1)).getCustomizedImageLogoName();
		verify(systemService, times(1)).getCustomizedSuperPortalImageLogoName();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowWithEpicDisabled() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("extData")).thenReturn("rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		when(request.getParameter("extDataType")).thenReturn("1");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(0);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("0"); // disabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("0"); // disabled
		when(systemService.getConfigValue(TenantContext.getTenantId(), "SharedSecret")).thenReturn("T1h9e8c2o3r4r9e6");
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNull(result);
		verify(response, times(1)).sendError(404, "epicnotenabled");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowWithExternalDataInvalid() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("extData")).thenReturn("rsz76/DemxxCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxpp5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		when(request.getParameter("extDataType")).thenReturn("1");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(1);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("1"); // enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("1"); // enabled
		when(systemService.getConfigValue(TenantContext.getTenantId(), "SharedSecret")).thenReturn("T1h9e8c2o3r4r9e6");
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNull(result);
		verify(response, times(1)).sendError(404, "externaldatainvalid");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowWithExternalDataTypeInvalid() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("extData")).thenReturn("rsz76/DemxxCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxpp5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		when(request.getParameter("extDataType")).thenReturn("7");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(1);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("1"); // enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("1"); // enabled
		when(systemService.getConfigValue(TenantContext.getTenantId(), "SharedSecret")).thenReturn("T1h9e8c2o3r4r9e6");
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNull(result);
		verify(response, times(1)).sendError(404, "externaldatatypeinvalid");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowCreateInvalidRoom() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("extData")).thenReturn("rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		when(request.getParameter("extDataType")).thenReturn("1");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExtIntegrationSharedSecret("T1h9e8c2o3r4r9e6");
		tenantConfiguration.setExternalIntegrationMode(1);
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(0);
		
		// couldn't create room - throw exception
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenThrow(new ScheduledRoomCreationException());
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("1"); // enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("1"); // enabled
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNull(result);
		verify(response, times(1)).sendError(404, "invalidroom");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowOneUser() throws Exception {
		final String EXT_DATA = "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==";
		final String ROOM_KEY = "xcsdffg";
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("extData")).thenReturn(EXT_DATA);
		when(request.getParameter("extDataType")).thenReturn("1");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));
		
		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(1);
		tenantConfiguration.setExtIntegrationSharedSecret("T1h9e8c2o3r4r9e6");
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(0);
		when(roomService.areGuestAllowedToThisRoom()).thenReturn(true);
		
		ScheduledRoomResponse roomResp = new ScheduledRoomResponse();
		roomResp.setStatus(0);
		Room room = new Room();
		room.setRoomID(7);
		room.setRoomKey(ROOM_KEY);
		roomResp.setRoom(room);
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenReturn(roomResp);
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("1"); // enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("1"); // enabled
		
		when(endpoint.getActiveEndpointForType(request)).thenReturn(null);
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNotNull(result);
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("extData"), EXT_DATA);
		Assert.assertEquals(modelMap.get("extDataType"), "1");
		Assert.assertEquals(modelMap.get("dispName"), "Hayden Kelly");
		Assert.assertEquals(modelMap.get("key"), ROOM_KEY);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEpicRoomJoinWorkflowTwoUsers() throws Exception {
		final String EXT_DATA = "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+d" + 
				"rhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7" + 
				"GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==";
		final String ROOM_KEY = "xcsdffg";
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletResponse response2 = mock(HttpServletResponse.class);
		when(request.getParameter("extData")).thenReturn(EXT_DATA);
		when(request.getParameter("extDataType")).thenReturn("1");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));
		
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));
		
		SecurityContextHolder.setContext(mock(SecurityContext.class));
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExternalIntegrationMode(1);
		tenantConfiguration.setExtIntegrationSharedSecret("T1h9e8c2o3r4r9e6");
		when(tenantService.getTenantConfiguration(anyInt())).thenReturn(tenantConfiguration);
		when(tenantService.getTenant(anyInt())).thenReturn(new Tenant());
		when(transactionService.addTransactionHistoryWithUserLookup(any())).thenReturn(0);
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(0);
		when(roomService.areGuestAllowedToThisRoom()).thenReturn(true);
		
		ScheduledRoomResponse roomResp = new ScheduledRoomResponse();
		roomResp.setStatus(0);
		Room room = new Room();
		room.setRoomID(7);
		room.setRoomKey(ROOM_KEY);
		roomResp.setRoom(room);
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenReturn(roomResp);
		
		Configuration epicConfig = new Configuration();
		epicConfig.setConfigurationValue("1"); // enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(epicConfig);
		when(systemService.getConfigValue(TenantContext.getTenantId(), "EnableEpic")).thenReturn("1"); // enabled
		
		when(endpoint.getActiveEndpointForType(request)).thenReturn(null);
		
		ModelAndView result = flexUIController.getFlexHtml(request, response);
		Assert.assertNotNull(result);
		
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(room.getRoomID());
		when(roomService.getRoom(anyInt())).thenReturn(room);
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenReturn(null);
		result = flexUIController.getFlexHtml(request, response2);
		Assert.assertNotNull(result);
		
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("extData"), EXT_DATA);
		Assert.assertEquals(modelMap.get("extDataType"), "1");
		Assert.assertEquals(modelMap.get("dispName"), "Hayden Kelly");
		Assert.assertEquals(modelMap.get("key"), ROOM_KEY);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetControlMeetingHtmlBAKWorkflowsWithEncodedBAK() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("bak")).thenReturn("jdsfhsdkjfsdfS%3D%3D");
		when(request.getParameter("key")).thenReturn("someroomkey");
		when(request.getHeader("host")).thenReturn("somehost");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));

		Member member = new Member();
		member.setMemberID(4);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS%3D%3d", MemberBAKType.MorderatorURL)).thenReturn(null);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS==", MemberBAKType.MorderatorURL)).thenReturn(member);
		doNothing().when(userService).deleteMemberBAK("jdsfhsdkjfsdfS==");
		when(userService.generateBAKforMember(4)).thenReturn("kjdshfkjhsdkfh");
		User user = new User();
		user.setTenantID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));

		Room room = new Room();
		room.setRoomID(5);
		room.setMemberID(4);
		room.setRoomName("Room1");
		room.setDisplayName("Room1");
		room.setRoomType("Scheduled");
		room.setRoomPIN("1234");
		room.setLectureMode(0);
		room.setTenantID(3);
		when(roomService.getRoomForModeratorKey("someroomkey")).thenReturn(room);

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		scheduledRoomResponse.setStatus(0);
		when(roomService.generateSchRoomExtPin(4, 1234)).thenReturn(scheduledRoomResponse);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setWaitingRoomsEnabled(1);
		tenantConfiguration.setLectureModeAllowed(1);
		when(tenantService.getTenantConfiguration(3)).thenReturn(tenantConfiguration);

		when(systemService.getCustomizedImageLogoName()).thenReturn(null);
		when(systemService.getCustomizedSuperPortalImageLogoName()).thenReturn("mylogoname");

		when(tenantService.getTenant(3)).thenReturn(new Tenant());
		ModelAndView result = flexUIController.getControlMeetingHtml(request, response);

		Assert.assertNotNull(result);
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("authenticated"), "true");
		verify(request, times(1)).getParameter("bak");
		verify(request, times(2)).getParameter("key");
		verify(request, times(1)).getHeader("host");
		verify(request, times(1)).getSession(true);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS%3D%3D", MemberBAKType.MorderatorURL);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS==", MemberBAKType.MorderatorURL);
		verify(userService, times(1)).deleteMemberBAK("jdsfhsdkjfsdfS==");
		verify(userService, times(1)).generateBAKforMember(4);
		verify(userService, times(1)).getLoginUser();
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(roomService, times(1)).getRoomForModeratorKey("someroomkey");
		// TODO verify(roomService, times(1)).generateSchRoomExtPin(4, 1234);
		verify(tenantService, times(1)).getTenantConfiguration(3);
		verify(systemService, times(1)).getCustomizedImageLogoName();
		verify(systemService, times(1)).getCustomizedSuperPortalImageLogoName();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetChangePasswordHtmlBAKWorkflows() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("bak")).thenReturn("jdsfhsdkjfsdfS");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));

		Member member = new Member();
		member.setMemberID(4);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS", MemberBAKType.ChangePassword)).thenReturn(member);
		doNothing().when(userService).deleteMemberBAK("jdsfhsdkjfsdfS");
		when(userService.generateBAKforMember(4)).thenReturn("kjdshfkjhsdkfh");

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));

		when(systemService.getCustomizedImageLogoName()).thenReturn(null);
		when(systemService.getCustomizedSuperPortalImageLogoName()).thenReturn("mylogoname");

		ModelAndView result = flexUIController.getChangePasswordHtml(request, response);

		Assert.assertNotNull(result);
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("authenticated"), "true");
		verify(request, times(1)).getParameter("bak");
		verify(request, times(1)).getSession(true);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS", MemberBAKType.ChangePassword);
		verify(userService, times(1)).deleteMemberBAK("jdsfhsdkjfsdfS");
		verify(userService, times(1)).generateBAKforMember(4);
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(systemService, times(1)).getCustomizedImageLogoName();
		verify(systemService, times(1)).getCustomizedSuperPortalImageLogoName();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetChangePasswordHtmlBAKWorkflowsWithEncodedBAK() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("bak")).thenReturn("jdsfhsdkjfsdfS%3D%3D");
		when(request.getSession(true)).thenReturn(mock(HttpSession.class));

		Member member = new Member();
		member.setMemberID(4);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS%3D%3D", MemberBAKType.ChangePassword)).thenReturn(null);
		when(userService.getMemberForBak("jdsfhsdkjfsdfS==", MemberBAKType.ChangePassword)).thenReturn(member);
		doNothing().when(userService).deleteMemberBAK("jdsfhsdkjfsdfS==");
		when(userService.generateBAKforMember(4)).thenReturn("kjdshfkjhsdkfh");

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mock(Authentication.class));

		SecurityContextHolder.setContext(mock(SecurityContext.class));

		when(systemService.getCustomizedImageLogoName()).thenReturn(null);
		when(systemService.getCustomizedSuperPortalImageLogoName()).thenReturn("mylogoname");

		ModelAndView result = flexUIController.getChangePasswordHtml(request, response);

		Assert.assertNotNull(result);
		Map<String, Object> modelMap = (Map<String, Object>) result.getModelMap().get("model");
		Assert.assertEquals(modelMap.get("authenticated"), "true");
		verify(request, times(1)).getParameter("bak");
		verify(request, times(1)).getSession(true);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS%3D%3D", MemberBAKType.ChangePassword);
		verify(userService, times(1)).getMemberForBak("jdsfhsdkjfsdfS==", MemberBAKType.ChangePassword);
		verify(userService, times(1)).deleteMemberBAK("jdsfhsdkjfsdfS==");
		verify(userService, times(1)).generateBAKforMember(4);
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(systemService, times(1)).getCustomizedImageLogoName();
		verify(systemService, times(1)).getCustomizedSuperPortalImageLogoName();
	}
}
