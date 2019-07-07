package com.vidyo.portal.user.v1_1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Banners;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Member;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Room;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IReplayService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.ws.configuration.NetworkElementType;

@PrepareForTest({ MessageContext.class, TenantContext.class })
public class VidyoPortalUserServiceSkeletonTest extends PowerMockTestCase {

	@InjectMocks
	private VidyoPortalUserServiceSkeleton vidyoPortalUserServiceSkeleton;

	@Mock
	private LicensingService licensingService;

	@Mock
	private IUserService userService;

	@Mock
	private IConferenceService conferenceService;

	@Mock
	private IServiceService serviceService;

	@Mock
	private ISystemService systemService;

	@Mock
	private ComponentsService componentService;

	@Mock
	private IMemberService memberService;

	@Mock
	private IRoomService roomService;
	
	@Mock
	private ITenantService tenantService;
	
	@Mock
	private IpcConfigurationService ipcConfigurationService;
	
	@Mock
	private ExtIntegrationService extIntegrationService;
	
	@Mock
	private IReplayService replayService;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);

		PowerMockito.mockStatic(MessageContext.class);
		MessageContext messageContext = setupMessageContext();
		PowerMockito.when(MessageContext.getCurrentMessageContext()).thenReturn(messageContext);

		PowerMockito.mockStatic(TenantContext.class);
		PowerMockito.when(TenantContext.getTenantId()).thenReturn(1);

		when(licensingService.getSystemLicenseFeature("AllowUserAPIs"))
				.thenReturn(new SystemLicenseFeature("AllowUserAPIs", "true", "true"));
	}

	private MessageContext setupMessageContext() {
		MessageContext messageContext = new MessageContext();
		TransportHeaders transportHeaders = mock(TransportHeaders.class);
		when(transportHeaders.get("user-agent")).thenReturn(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, transportHeaders);
		messageContext.setProperty(MessageContext.REMOTE_ADDR, "127.0.0.1");
		return messageContext;
	}
	
	private PortalFeature_type0 getPortalFeatureByName(PortalFeature_type0[] features, String name) {
		for (PortalFeature_type0 feature : features) {
			if (feature.getFeature().getPortalFeatureName().equals(name)) {
				return feature;
			}
		}
		return null;
	}

	private void setCommonMockBehavioursForLogIn() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		when(userService.generatePAKforMember(3)).thenReturn(new PortalAccessKeys());

		when(conferenceService.getVMConnectAddress()).thenReturn("127.0.0.1");

		when(serviceService.getProxyCSVList(3)).thenReturn(null);
		when(serviceService.getLocationTagForMember(3)).thenReturn(null);

		when(systemService.getAdminEndpointSettings(1)).thenReturn(new EndpointSettings());
		when(systemService.getMinPINLengthForTenant(1)).thenReturn(2);
	}

	private void verifyCommonMockBehavioursForLogIn() throws Exception {
		verify(userService, times(3)).getLoginUser();
		verify(userService, times(1)).generatePAKforMember(3);
		verify(conferenceService, times(1)).getVMConnectAddress();
		verify(serviceService, times(1)).getProxyCSVList(3);
		verify(serviceService, times(1)).getLocationTagForMember(3);
		verify(systemService, times(1)).getAdminEndpointSettings(1);
		verify(systemService, times(1)).getMinPINLengthForTenant(1);
	}

	@Test
	public void testLogInWithReturnServiceAddressFlag() throws Exception {
		setCommonMockBehavioursForLogIn();

		Component registrationService = new Component();
		registrationService.setMgmtUrl("http://localhost:8090/");
		List<Component> registrationServiceComponents = new ArrayList<Component>();
		registrationServiceComponents.add(registrationService);
		when(componentService.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue()))
				.thenReturn(registrationServiceComponents);

		Component pairingService = new Component();
		pairingService.setMgmtUrl("http://localhost:8092/");
		List<Component> pairingServiceComponents = new ArrayList<Component>();
		pairingServiceComponents.add(pairingService);
		when(componentService.getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue()))
				.thenReturn(pairingServiceComponents);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnServiceAddress(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(response.getRegistrationService().toString(), "http://localhost:8090/");
		Assert.assertEquals(response.getPairingService().toString(), "http://localhost:8092/");

		verifyCommonMockBehavioursForLogIn();
		verify(componentService, times(1))
				.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue());
		verify(componentService, times(1)).getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue());
	}

	@Test
	public void testLogInWithReturnServiceAddressFlagBeforeServiceConfiguration() throws Exception {
		setCommonMockBehavioursForLogIn();

		when(componentService.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue()))
				.thenReturn(null);
		when(componentService.getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue()))
				.thenReturn(null);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnServiceAddress(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getRegistrationService());
		Assert.assertNull(response.getPairingService());

		verifyCommonMockBehavioursForLogIn();
		verify(componentService, times(1))
				.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue());
		verify(componentService, times(1)).getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue());
	}

	@Test
	public void testLogInWithoutReturnServiceAddressFlag() throws Exception {
		setCommonMockBehavioursForLogIn();

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnServiceAddress(false);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getRegistrationService());
		Assert.assertNull(response.getPairingService());

		verifyCommonMockBehavioursForLogIn();
	}

	@Test
	public void testLogInWithReturnServiceAddressFlagThrowingExceptionForRegistrationService() throws Exception {
		boolean status = false;

		setCommonMockBehavioursForLogIn();

		when(componentService.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue()))
				.thenThrow(new RuntimeException());

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnServiceAddress(true);
		try {
			vidyoPortalUserServiceSkeleton.logIn(logInRequest);
		} catch (Exception e) {
			if (e instanceof GeneralFaultException
					&& ((GeneralFaultException) e).getMessage().contains("System Error - Unable to process request")) {
				status = true;
			}
		}

		Assert.assertTrue(status);

		verifyCommonMockBehavioursForLogIn();
		verify(componentService, times(1))
				.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue());
	}

	@Test
	public void testLogInWithReturnServiceAddressFlagThrowingExceptionForPairingService() throws Exception {
		boolean status = false;

		setCommonMockBehavioursForLogIn();

		when(componentService.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue()))
				.thenReturn(null);
		when(componentService.getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue()))
				.thenThrow(new RuntimeException());

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnServiceAddress(true);
		try {
			vidyoPortalUserServiceSkeleton.logIn(logInRequest);
		} catch (Exception e) {
			if (e instanceof GeneralFaultException
					&& ((GeneralFaultException) e).getMessage().contains("System Error - Unable to process request")) {
				status = true;
			}
		}

		Assert.assertTrue(status);

		verifyCommonMockBehavioursForLogIn();
		verify(componentService, times(1))
				.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue());
		verify(componentService, times(1)).getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue());
	}

	@Test
	public void testLogInWithReturnUserRoleFlag() throws Exception {
		setCommonMockBehavioursForLogIn();

		Member member = new Member();
		member.setMemberID(3);
		member.setRoleName("Normal");
		when(memberService.getMember(3)).thenReturn(member);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnUserRole(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(response.getUserRole(), "Normal");

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnUserRoleFlagWithGetMemberThrowingException() throws Exception {
		boolean status = false;

		setCommonMockBehavioursForLogIn();

		when(memberService.getMember(3)).thenThrow(new RuntimeException());

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnUserRole(true);
		try {
			vidyoPortalUserServiceSkeleton.logIn(logInRequest);
		} catch (Exception e) {
			if (e instanceof GeneralFaultException
					&& ((GeneralFaultException) e).getMessage().contains("System Error - Unable to process request")) {
				status = true;
			}
		}

		Assert.assertTrue(status);

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnUserRoleFlagWithInvalidMemberId() throws Exception {
		setCommonMockBehavioursForLogIn();

		when(memberService.getMember(3)).thenReturn(null);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnUserRole(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getUserRole());

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnUserRoleFlagWithNonexistentMemberRole() throws Exception {
		setCommonMockBehavioursForLogIn();

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnUserRole(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getUserRole());

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnNeoRoomPermanentPairingDeviceUserAttributeFlag() throws Exception {
		setCommonMockBehavioursForLogIn();

		Member member = new Member();
		member.setMemberID(3);
		member.setNeoRoomPermanentPairingDeviceUser(true);
		when(memberService.getMember(3)).thenReturn(member);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnNeoRoomPermanentPairingDeviceUserAttribute(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(response.getNeoRoomPermanentPairingDeviceUser(), true);

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnNeoRoomPermanentPairingDeviceUserAttributeFlagWithValueFalse() throws Exception {
		setCommonMockBehavioursForLogIn();

		Member member = new Member();
		member.setMemberID(3);
		member.setNeoRoomPermanentPairingDeviceUser(false);
		when(memberService.getMember(3)).thenReturn(member);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnNeoRoomPermanentPairingDeviceUserAttribute(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertEquals(response.getNeoRoomPermanentPairingDeviceUser(), false);

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnNeoRoomPermanentPairingDeviceUserAttributeFlagWithGetMemberThrowingException()
			throws Exception {
		boolean status = false;

		setCommonMockBehavioursForLogIn();

		when(memberService.getMember(3)).thenThrow(new RuntimeException());

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnNeoRoomPermanentPairingDeviceUserAttribute(true);
		try {
			vidyoPortalUserServiceSkeleton.logIn(logInRequest);
		} catch (Exception e) {
			if (e instanceof GeneralFaultException
					&& ((GeneralFaultException) e).getMessage().contains("System Error - Unable to process request")) {
				status = true;
			}
		}

		Assert.assertTrue(status);

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
	}

	@Test
	public void testLogInWithReturnNeoRoomPermanentPairingDeviceUserAttributeFlagWithInvalidMemberId()
			throws Exception {
		setCommonMockBehavioursForLogIn();

		when(memberService.getMember(3)).thenReturn(null);

		LogInRequest logInRequest = new LogInRequest();
		logInRequest.setReturnNeoRoomPermanentPairingDeviceUserAttribute(true);
		LogInResponse response = vidyoPortalUserServiceSkeleton.logIn(logInRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getUserRole());

		verifyCommonMockBehavioursForLogIn();
		verify(memberService, times(1)).getMember(3);
		Assert.assertEquals(response.getNeoRoomPermanentPairingDeviceUser(), false);
	}

	@Test
	public void testCreateScheduledRoom() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);

		Member member = new Member();
		member.setMemberID(3);
		member.setNeoRoomPermanentPairingDeviceUser(false);
		when(memberService.getMember(3)).thenReturn(member);
		when(memberService.getPersonalRoomId(3)).thenReturn(4);

		RoomCreationResponse roomCreationResponse = new RoomCreationResponse();
		roomCreationResponse.setRoom(new Room());
		roomCreationResponse.setTenant(new Tenant());
		roomCreationResponse.setStatus(0);
		when(roomService.createScheduledRoom(any(SchRoomCreationRequest.class))).thenReturn(roomCreationResponse);
		when(roomService.getRoomURL(any(ISystemService.class), anyString(), anyString(), anyString()))
				.thenReturn("http://localhost/roomURL");

		when(systemService.getTenantInvitationEmailContent()).thenReturn("Email Content");
		when(systemService.constructEmailInviteSubjectForInviteRoom(Locale.getDefault()))
				.thenReturn("Email Invite Subject");

		CreateScheduledRoomRequest createScheduledRoomRequest = new CreateScheduledRoomRequest();
		createScheduledRoomRequest.setReturnRoomDetails(true);
		CreateScheduledRoomResponse response = vidyoPortalUserServiceSkeleton
				.createScheduledRoom(createScheduledRoomRequest);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getRoom());

		verify(userService, times(1)).getLoginUser();
		verify(memberService, times(1)).getMember(3);
		verify(memberService, times(1)).getPersonalRoomId(3);
		verify(roomService, times(1)).createScheduledRoom(any(SchRoomCreationRequest.class));
		verify(roomService, times(1)).getRoomURL(systemService, null, null, null);
		verify(systemService, times(1)).getTenantInvitationEmailContent();
		verify(systemService, times(1)).constructEmailInviteSubjectForInviteRoom(Locale.getDefault());
	}

	@Test
	public void testCreateScheduledRoomWithFlagNotSet() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);

		Member member = new Member();
		member.setMemberID(3);
		member.setNeoRoomPermanentPairingDeviceUser(false);
		when(memberService.getMember(3)).thenReturn(member);

		RoomCreationResponse roomCreationResponse = new RoomCreationResponse();
		roomCreationResponse.setRoom(new Room());
		roomCreationResponse.setTenant(new Tenant());
		roomCreationResponse.setStatus(0);
		when(roomService.createScheduledRoom(any(SchRoomCreationRequest.class))).thenReturn(roomCreationResponse);
		when(roomService.getRoomURL(any(ISystemService.class), anyString(), anyString(), anyString()))
				.thenReturn("http://localhost/roomURL");

		when(systemService.getTenantInvitationEmailContent()).thenReturn("Email Content");
		when(systemService.constructEmailInviteSubjectForInviteRoom(Locale.getDefault()))
				.thenReturn("Email Invite Subject");

		CreateScheduledRoomRequest createScheduledRoomRequest = new CreateScheduledRoomRequest();
		createScheduledRoomRequest.setReturnRoomDetails(false);
		CreateScheduledRoomResponse response = vidyoPortalUserServiceSkeleton
				.createScheduledRoom(createScheduledRoomRequest);

		Assert.assertNotNull(response);
		Assert.assertNull(response.getRoom());

		verify(userService, times(1)).getLoginUser();
		verify(memberService, times(1)).getMember(3);
		verify(roomService, times(1)).createScheduledRoom(any(SchRoomCreationRequest.class));
		verify(roomService, times(1)).getRoomURL(systemService, null, null, null);
		verify(systemService, times(1)).getTenantInvitationEmailContent();
		verify(systemService, times(1)).constructEmailInviteSubjectForInviteRoom(Locale.getDefault());
	}
	
	@Test
	public void testPortalFeatures() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);

		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(false);
		when(tenantService.getTenant(any())).thenReturn(new Tenant());
		when(ipcConfigurationService.getIpcConfiguration(anyInt())).thenReturn(new IpcConfiguration());
		when(systemService.getBannersInfo()).thenReturn(new Banners());
		when(systemService.getPortalChat()).thenReturn(new PortalChat());  
		when(systemService.getAuthenticationConfig(anyInt())).thenReturn(new AuthenticationConfig()) ; 

		GetPortalFeaturesRequest portalFeaturesRequest = new GetPortalFeaturesRequest();
		GetPortalFeaturesResponse response = vidyoPortalUserServiceSkeleton
				.getPortalFeatures(portalFeaturesRequest);

		Assert.assertNotNull(response);
		PortalFeature_type0 feature = getPortalFeatureByName(response.getPortalFeature(), "TytoCare");
		Assert.assertNotNull(feature);
		Assert.assertEquals(false, feature.getEnable());
	}
	
	@Test
	public void testCreateWebcastURLSuccess() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		room.setMemberID(3);
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).generateWebCastURL(any());

		CreateWebcastURLRequest createWebcastURLRequest = new CreateWebcastURLRequest();
		createWebcastURLRequest.setRoomID(id);
		
		vidyoPortalUserServiceSkeleton.createWebcastURL(createWebcastURLRequest);
	}
	
	@Test
	public void testCreateWebcastURLFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);

		CreateWebcastURLRequest createWebcastURLRequest = new CreateWebcastURLRequest();
		createWebcastURLRequest.setRoomID(id);
		
		boolean status = false;
		try {
			vidyoPortalUserServiceSkeleton.createWebcastURL(createWebcastURLRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.user.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains("You are not an owner of room")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
	}
	
	@Test
	public void testRemoveWebcastURLSuccess() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		room.setMemberID(3);
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).deleteWebCastURL(any());

		RemoveWebcastURLRequest removeWebcastURLRequest = new RemoveWebcastURLRequest();
		removeWebcastURLRequest.setRoomID(id);
		
		vidyoPortalUserServiceSkeleton.removeWebcastURL(removeWebcastURLRequest);
	}
	
	@Test
	public void testRemoveWebcastURLFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);

		RemoveWebcastURLRequest removeWebcastURLRequest = new RemoveWebcastURLRequest();
		removeWebcastURLRequest.setRoomID(id);
		
		boolean status = false;
		try {
			vidyoPortalUserServiceSkeleton.removeWebcastURL(removeWebcastURLRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.user.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains("You are not an owner of room")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
	}
	
	@Test
	public void testCreateWebcastPINSuccess() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		room.setMemberID(3);
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).updateWebCastPIN(any(), any());
		when(systemService.isPINLengthAcceptable(anyInt(), any())).thenReturn(true);

		CreateWebcastPINRequest createWebcastPINRequest = new CreateWebcastPINRequest();
		createWebcastPINRequest.setRoomID(id);
		
		vidyoPortalUserServiceSkeleton.createWebcastPIN(createWebcastPINRequest);
	}
	
	@Test
	public void testCreateWebcastPINFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		
		when(systemService.isPINLengthAcceptable(anyInt(), any())).thenReturn(true);

		CreateWebcastPINRequest createWebcastPINRequest = new CreateWebcastPINRequest();
		createWebcastPINRequest.setRoomID(id);
		
		boolean status = false;
		try {
			vidyoPortalUserServiceSkeleton.createWebcastPIN(createWebcastPINRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.user.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains("You are not an owner of room")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
	}
	
	@Test
	public void testRemoveWebcastPINSuccess() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		room.setMemberID(3);
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).updateWebCastPIN(any(), any());
		when(systemService.isPINLengthAcceptable(anyInt(), any())).thenReturn(true);

		RemoveWebcastPINRequest removeWebcastPINRequest = new RemoveWebcastPINRequest();
		removeWebcastPINRequest.setRoomID(id);
		
		vidyoPortalUserServiceSkeleton.removeWebcastPIN(removeWebcastPINRequest);
	}
	
	@Test
	public void testRemoveWebcastPINFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID("55");
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		
		when(systemService.isPINLengthAcceptable(anyInt(), any())).thenReturn(true);

		RemoveWebcastPINRequest removeWebcastPINRequest = new RemoveWebcastPINRequest();
		removeWebcastPINRequest.setRoomID(id);
		
		boolean status = false;
		try {
			vidyoPortalUserServiceSkeleton.removeWebcastPIN(removeWebcastPINRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.user.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.user.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains("You are not an owner of room")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
	}
}
