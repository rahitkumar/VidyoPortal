package com.vidyo.portal.admin.v1_1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
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
import com.vidyo.bo.Member;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.User;
import com.vidyo.bo.Room;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IReplayService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

@PrepareForTest({ MessageContext.class, TenantContext.class })
public class VidyoPortalAdminServiceSkeletonTest extends PowerMockTestCase {

	private static final String ROOM_NAME_XSS = "<![CDATA[<img onerror=alert('XSS') src=a>]]>";

	@InjectMocks
	private VidyoPortalAdminServiceSkeleton vidyoPortalAdminServiceSkeleton;
	
	@Mock
	private LicensingService licensingService;

	@Mock
	private IUserService userService;
	
	@Mock
	private IMemberService memberService;
	
	@Mock
	private IRoomService roomService;
	
	@Mock
	private ISystemService systemService;
	
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
	
	//@Test
	public void testUpdatePublicRoomXssFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);

		Member member = new Member();
		member.setMemberID(3);
		member.setNeoRoomPermanentPairingDeviceUser(false);
		when(memberService.getMember(3)).thenReturn(member);

		UpdateRoomRequest updateRoomRequest = new UpdateRoomRequest();
		com.vidyo.portal.admin.v1_1.Room room = new com.vidyo.portal.admin.v1_1.Room();
		room.setName(ROOM_NAME_XSS);
		EntityID id = new EntityID();
		id.setEntityID(55);
		room.setRoomID(id);
		updateRoomRequest.setRoom(room);
		updateRoomRequest.setRoomID(id);
		
		boolean status = false;
		try {
			vidyoPortalAdminServiceSkeleton.updateRoom(updateRoomRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains(
						"Invalid Room Name - " + ROOM_NAME_XSS)) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
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
		id.setEntityID(55);
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).generateWebCastURL(any());

		CreateWebcastURLRequest createWebcastURLRequest = new CreateWebcastURLRequest();
		createWebcastURLRequest.setRoomID(id);
		
		vidyoPortalAdminServiceSkeleton.createWebcastURL(createWebcastURLRequest);
	}
	
	@Test
	public void testCreateWebcastURLFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID(55);
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
			vidyoPortalAdminServiceSkeleton.createWebcastURL(createWebcastURLRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) e;
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
		id.setEntityID(55);
		room.setRoomID(55);
		room.setTenantID(1);
		when(roomService.getRoomDetailsForConference(anyInt())).thenReturn(room);

		Member member = new Member();
		member.setMemberID(3);
		when(memberService.getMember(3)).thenReturn(member);
		doNothing().when(replayService).deleteWebCastURL(any());

		RemoveWebcastURLRequest removeWebcastURLRequest = new RemoveWebcastURLRequest();
		removeWebcastURLRequest.setRoomID(id);
		
		vidyoPortalAdminServiceSkeleton.removeWebcastURL(removeWebcastURLRequest);
	}
	
	@Test
	public void testRemoveWebcastURLFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID(55);
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
			vidyoPortalAdminServiceSkeleton.removeWebcastURL(removeWebcastURLRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) e;
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
		id.setEntityID(55);
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
		
		vidyoPortalAdminServiceSkeleton.createWebcastPIN(createWebcastPINRequest);
	}
	
	@Test
	public void testCreateWebcastPINFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID(55);
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
			vidyoPortalAdminServiceSkeleton.createWebcastPIN(createWebcastPINRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) e;
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
		id.setEntityID(55);
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
		
		vidyoPortalAdminServiceSkeleton.removeWebcastPIN(removeWebcastPINRequest);
	}
	
	@Test
	public void testRemoveWebcastPINFailed() throws Exception {
		User user = new User();
		user.setMemberID(3);
		when(userService.getLoginUser()).thenReturn(user);
		
		Room room = new Room();
		room.setDisplayName("test1");
		EntityID id = new EntityID();
		id.setEntityID(55);
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
			vidyoPortalAdminServiceSkeleton.removeWebcastPIN(removeWebcastPINRequest);
		} catch (Exception e) {
			if (e instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) {
				com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException ex = 
						(com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException) e;
				if (ex.getMessage().contains("You are not an owner of room")) {
					status = true;
				}
			}
		}

		Assert.assertTrue(status);
	}
}
