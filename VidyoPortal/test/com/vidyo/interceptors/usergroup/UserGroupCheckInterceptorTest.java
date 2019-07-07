package com.vidyo.interceptors.usergroup;

import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.db.repository.member.MemberRepository;
import com.vidyo.db.repository.room.RoomRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.portal.guest.GuestJoinConferenceRequest;
import com.vidyo.portal.guest.LinkEndpointToGuestRequest;
import com.vidyo.portal.guest.LogInAsGuestRequest;
import com.vidyo.portal.user.v1_1.*;
import com.vidyo.service.*;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.usergroup.UserGroupService;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.math.RandomUtils;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.Assert.*;

@PrepareForTest({TenantContext.class})
public class UserGroupCheckInterceptorTest extends PowerMockTestCase {

    private static final int TENANT_ID = 77;
    com.vidyo.portal.user.v1_1.EntityID entityID;
    com.vidyo.portal.admin.v1_1.EntityID adminEntityID;
    private IUserService userService;
    private ITenantService tenantService;
    private IRoomService roomService;
    private MemberRepository memberRepository;;
    private RoomRepository roomRepository;
    private UserGroupService userGroupService;
    private UserGroupCheckInterceptor userGroupCheckInterceptor;
    private MethodInvocation methodInvocation;
    private User user;

    private IMemberService memberService;

    @BeforeMethod
    private void setup(){
        {
            userGroupCheckInterceptor = new UserGroupCheckInterceptor();
            mockStatic(TenantContext.class);

            userService = mock(IUserService.class);
            tenantService = mock(ITenantService.class);
            roomService = mock(IRoomService.class);
            methodInvocation = mock(MethodInvocation.class);

            //Injecting the repository dependencies.
            memberRepository = mock(MemberRepository.class);
            roomRepository = mock(RoomRepository.class);

            userGroupCheckInterceptor.setUserService(userService);
            userGroupCheckInterceptor.setTenantService(tenantService);
            userGroupCheckInterceptor.setRoomService(roomService);
            entityID = mock(com.vidyo.portal.user.v1_1.EntityID.class);
            adminEntityID = mock(com.vidyo.portal.admin.v1_1.EntityID.class);
            userGroupService = mock(UserGroupService.class);
            memberService = mock(IMemberService.class);
            userGroupCheckInterceptor.setUserGroupService(userGroupService);
            userGroupCheckInterceptor.setMemberService(memberService);
            user = mock(User.class);
            when(TenantContext.getTenantId()).thenReturn(TENANT_ID);
        }
    }


    //test cases for User Service methods.

    @Test
    public void join_conference_user_service_invalid_roomId() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abc");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
        verify(userService, never()).getLoginUser();
    }

    @Test
    public void join_conference_user_service_invalid_roomId_test() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("23");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw InvalidArgumentFaultException.");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException);
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, never()).getTenantByURL(anyString());
        verify(userGroupService, times(1)).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_conference_user_service_null_scheduled_room_response() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);

        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(null);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, never()).getTenantByURL(anyString());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_conference_user_service_valid_room_extension() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
       ScheduledRoomResponse scheduledRoomResponse =  mock(ScheduledRoomResponse.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);

        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(scheduledRoomResponse);
        when(scheduledRoomResponse.getRoom()).thenReturn(null);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, never()).getTenantByURL(anyString());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_coference_user_service_invalid_room_name_extension() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dddsfds@");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);

        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(null);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, never()).getTenantByURL(anyString());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }
    @Test
    public void join_coference_user_service_valid_room_name_extension() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd@dsfds");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        when(tenantService.getTenantByURL(anyString())).thenReturn(null);

        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(null);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, times(1)).getTenantByURL(anyString());
        verify(roomService, never()).getRoomDetailsForConference(anyString(), anyInt());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_coference_user_service_valid_tenantUrl() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        Tenant tenant = mock(Tenant.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd@dsfds");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        when(tenantService.getTenantByURL(anyString())).thenReturn(tenant);
        when(tenant.getTenantID()).thenReturn(RandomUtils.nextInt());

        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(null);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(roomService, never()).validateScheduledRoom(anyString(), anyString(), anyInt());
        verify(tenantService, times(1)).getTenantByURL(anyString());
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_coference_user_service_valid_tenantUrl_and_invalid_room() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        Tenant tenant = mock(Tenant.class);
        com.vidyo.bo.Room room = mock(com.vidyo.bo.Room.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd@dsfds");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        when(tenantService.getTenantByURL(anyString())).thenReturn(tenant);
        when(tenant.getTenantID()).thenReturn(RandomUtils.nextInt());
        when(roomService.getRoomDetailsForConference(anyString(), anyInt())).thenReturn(room);
        when(room.getRoomID()).thenReturn(RandomUtils.nextInt());
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("this method is bound to throw invalidargumentfaultexception.");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.user.v1_1.InvalidArgumentFaultException  );
        }
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, times(1)).getTenantByURL(anyString());
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(userGroupService, times(1)).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_coference_user_service_valid_tenantUrl_and_valid_data() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        Tenant tenant = mock(Tenant.class);
        com.vidyo.bo.Room room = mock(com.vidyo.bo.Room.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd@dsfds");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true);
        when(tenantService.getTenantByURL(anyString())).thenReturn(tenant);
        when(tenant.getTenantID()).thenReturn(RandomUtils.nextInt());
        when(roomService.getRoomDetailsForConference(anyString(), anyInt())).thenReturn(room);
        when(room.getRoomID()).thenReturn(RandomUtils.nextInt());
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is not bound to throw invalidargumentfaultexception.");
        }
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, times(1)).getTenantByURL(anyString());
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(userGroupService, times(1)).canMemberAccessRoom(anyInt(), anyInt());
    }

    @Test
    public void join_coference_user_service_valid_tenantUrl_and_valid_room() throws Throwable {
        JoinConferenceRequest joinConferenceRequest = mock(JoinConferenceRequest.class);

        com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0 joinConferenceRequestChoiceType0 = mock(com.vidyo.portal.user.v1_1.JoinConferenceRequestChoice_type0.class);
        Tenant tenant = mock(Tenant.class);
        com.vidyo.bo.Room room = mock(com.vidyo.bo.Room.class);
        userGroupCheckInterceptor.setTenantService(tenantService);
        when(joinConferenceRequest.getJoinConferenceRequestChoice_type0()).thenReturn(joinConferenceRequestChoiceType0);
        when(joinConferenceRequestChoiceType0.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abcd");
        when(joinConferenceRequestChoiceType0.getExtension()).thenReturn("45dd@dsfds");
        when(userService.getLoginUser()).thenReturn(user);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{joinConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        when(tenantService.getTenantByURL(anyString())).thenReturn(tenant);
        when(tenant.getTenantID()).thenReturn(RandomUtils.nextInt());
        when(roomService.getRoomDetailsForConference(anyString(), anyInt())).thenReturn(null);
        when(roomService.validateScheduledRoom(anyString(),anyString(), anyInt())).thenReturn(null);
        when(room.getRoomID()).thenReturn(RandomUtils.nextInt());
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("this method is bound to throw invalidargumentfaultexception.");
        }
        verify(joinConferenceRequest, times(1)).getPIN();
        verify(tenantService, times(1)).getTenantByURL(anyString());
        verify(roomService, times(1)).getRoomDetailsForConference(anyString(), anyInt());
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }
    @Test
    public void disconnect_conference_all_request_invalid_roomid() throws Throwable{

        DisconnectConferenceAllRequest disconnectConferenceAllRequest = mock(DisconnectConferenceAllRequest.class);

        when(methodInvocation.getArguments()).thenReturn(new Object[]{disconnectConferenceAllRequest});
        when(disconnectConferenceAllRequest.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("abc");
            userGroupCheckInterceptor.invoke(methodInvocation);
        verify(userService, never()).getLoginUser();
    }

    @Test
    public void disconnect_conference_all_request_valid_roomId() throws Throwable {

        DisconnectConferenceAllRequest disconnectConferenceAllRequest = mock(DisconnectConferenceAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{disconnectConferenceAllRequest});
        when(disconnectConferenceAllRequest.getConferenceID()).thenReturn(entityID);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(45);
        when(entityID.getEntityID()).thenReturn("4");
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("should throw exception for valid data");
        } catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof InvalidArgumentFaultException);
        }
        verify(userService, times(1)).getLoginUser();
        verify(userGroupService, times(1)).canMemberAccessRoom(anyInt(),anyInt());
    }

    @Test
    public void disconnect_conference_all_request_valid_data() throws Throwable {
        DisconnectConferenceAllRequest disconnectConferenceAllRequest = mock(DisconnectConferenceAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{disconnectConferenceAllRequest});
        when(disconnectConferenceAllRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void leave_conference_all_request_valid_data() throws Throwable {
        LeaveConferenceRequest leaveConferenceRequest = mock(LeaveConferenceRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{leaveConferenceRequest});
        when(leaveConferenceRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_audio_request_valid_data() throws Throwable {
        MuteAudioRequest muteAudioRequest = mock(MuteAudioRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteAudioRequest});
        when(muteAudioRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_audio_client_all_request_valid_data() throws Throwable {
        MuteAudioClientAllRequest muteAudioClientAllRequest = mock(MuteAudioClientAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteAudioClientAllRequest});
        when(muteAudioClientAllRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_audio_server_all_request_valid_data() throws Throwable {
        MuteAudioServerAllRequest  muteAudioServerAllRequest = mock(MuteAudioServerAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteAudioServerAllRequest});
        when(muteAudioServerAllRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_video_client_all_request_valid_data() throws Throwable {
        MuteVideoClientAllRequest  muteVideoClientAllRequest = mock(MuteVideoClientAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteVideoClientAllRequest});
        when(muteVideoClientAllRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_video_server_all_request_valid_data() throws Throwable {
        MuteVideoServerAllRequest  muteVideoServerAllRequest = mock(MuteVideoServerAllRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteVideoServerAllRequest});
        when(muteVideoServerAllRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void pause_recording_request_valid_data() throws Throwable {
        PauseRecordingRequest pauseRecordingRequest = mock(PauseRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{pauseRecordingRequest});
        when(pauseRecordingRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void resume_recording_request_valid_data() throws Throwable {
        ResumeRecordingRequest resumeRecordingRequest = mock(ResumeRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{resumeRecordingRequest});
        when(resumeRecordingRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void start_lecture_mode_request_valid_data() throws Throwable {
        StartLectureModeRequest startLectureModeRequestObj = mock(StartLectureModeRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startLectureModeRequestObj});
        when(startLectureModeRequestObj.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void start_recording_request_with_valid_data() throws Throwable {
        StartRecordingRequest startRecordingRequest= mock(StartRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startRecordingRequest});
        when(startRecordingRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void start_video_request_valid_data() throws Throwable {
        StartVideoRequest startVideoRequest= mock(StartVideoRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startVideoRequest});
        when(startVideoRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_lecture_mode_request_valid_data() throws Throwable {
        StopLectureModeRequest stopLectureModeRequest = mock(StopLectureModeRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopLectureModeRequest});
        when(stopLectureModeRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_recording_valid_data() throws Throwable {
        StopRecordingRequest stopRecordingRequest= mock(StopRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopRecordingRequest});
        when(stopRecordingRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_video_request_valid_data() throws Throwable {
        StopVideoRequest stopVideoRequest= mock(StopVideoRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopVideoRequest});
        when(stopVideoRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void unmute_audio_request_valid_data() throws Throwable {
        UnmuteAudioRequest  unmuteAudioRequest = mock(UnmuteAudioRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{unmuteAudioRequest});
        when(unmuteAudioRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void set_presenter_request_valid_data() throws Throwable {
        SetPresenterRequest setPresenterRequest = mock(SetPresenterRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{setPresenterRequest});
        when(setPresenterRequest.getConferenceID()).thenReturn(entityID);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }


    // test cases for Admin
    @Test
    public void leave_conference_valid_data() throws Throwable {
        com.vidyo.portal.admin.v1_1.LeaveConferenceRequest leaveConferenceRequest = mock(com.vidyo.portal.admin.v1_1.LeaveConferenceRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{leaveConferenceRequest});
        when(leaveConferenceRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(45);
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void mute_audio_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.MuteAudioRequest muteAudioRequest = mock(com.vidyo.portal.admin.v1_1.MuteAudioRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{muteAudioRequest});
        when(muteAudioRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void pause_recording_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.PauseRecordingRequest pauseRecordingRequest = mock(com.vidyo.portal.admin.v1_1.PauseRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{pauseRecordingRequest});
        when(pauseRecordingRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void remove_presenter_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.RemovePresenterRequest removePresenterRequest = mock(com.vidyo.portal.admin.v1_1.RemovePresenterRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{removePresenterRequest});
        when(removePresenterRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void remove_room_pin_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest removeRoomPINRequest = mock(com.vidyo.portal.admin.v1_1.RemoveRoomPINRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{removeRoomPINRequest});
        when(removeRoomPINRequest.getRoomID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void set_presenter_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.SetPresenterRequest setPresenterRequest = mock(com.vidyo.portal.admin.v1_1.SetPresenterRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{setPresenterRequest});
        when(setPresenterRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void start_lecture_mode_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StartLectureModeRequest startLectureModeRequest = mock(com.vidyo.portal.admin.v1_1.StartLectureModeRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startLectureModeRequest});
        when(startLectureModeRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void start_recording_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StartRecordingRequest  startRecordingRequest = mock(com.vidyo.portal.admin.v1_1.StartRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startRecordingRequest});
        when(startRecordingRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }


    @Test
    public void start_video_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StartVideoRequest startVideoRequest = mock(com.vidyo.portal.admin.v1_1.StartVideoRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{startVideoRequest});
        when(startVideoRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_lecture_mode_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StopLectureModeRequest stopLectureModeRequest = mock(com.vidyo.portal.admin.v1_1.StopLectureModeRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopLectureModeRequest});
        when(stopLectureModeRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_recording_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StopRecordingRequest stopRecordingRequest = mock(com.vidyo.portal.admin.v1_1.StopRecordingRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopRecordingRequest});
        when(stopRecordingRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void stop_video_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.StopVideoRequest stopVideoRequest = mock(com.vidyo.portal.admin.v1_1.StopVideoRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{stopVideoRequest});
        when(stopVideoRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void transfer_participant_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.TransferParticipantRequest transferParticipantRequest = mock(com.vidyo.portal.admin.v1_1.TransferParticipantRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{transferParticipantRequest});
        when(transferParticipantRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void unmute_audio_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.UnmuteAudioRequest unmuteAudioRequest = mock(com.vidyo.portal.admin.v1_1.UnmuteAudioRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{unmuteAudioRequest});
        when(unmuteAudioRequest.getConferenceID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void update_room_request_test() throws Throwable {
        com.vidyo.portal.admin.v1_1.UpdateRoomRequest updateRoomRequest = mock(com.vidyo.portal.admin.v1_1.UpdateRoomRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{updateRoomRequest});
        when(updateRoomRequest.getRoomID()).thenReturn(adminEntityID);
        when(adminEntityID.getEntityID()).thenReturn(RandomUtils.nextInt());
        constructValidDataAndCallExecute(() -> userGroupCheckInterceptor.invoke(methodInvocation));
    }

    @Test
    public void guest_join_conference_test_with_invalid_guest() throws Throwable {
        GuestJoinConferenceRequest guestJoinConferenceRequest = mock(GuestJoinConferenceRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{guestJoinConferenceRequest});
        when(guestJoinConferenceRequest.getGuestID()).thenReturn(RandomUtils.nextInt());
        when(userService.getUserForGuestID(anyInt())).thenReturn(null);
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void guest_join_conference_test_with_invalid_room_id() throws Throwable {
        GuestJoinConferenceRequest guestJoinConferenceRequest = mock(GuestJoinConferenceRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{guestJoinConferenceRequest});
        when(guestJoinConferenceRequest.getGuestID()).thenReturn(RandomUtils.nextInt());

        when(userService.getUserForGuestID(anyInt())).thenReturn(user);
        when(user.getRoomID()).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(false);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw Exception");
        } catch (Exception exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.guest.InvalidArgumentFaultException);
        }
        verify(userGroupService, times(1)).canGuestAccessRoom(anyInt());
    }

    @Test
    public void guest_join_conference_test_with_valid_room_id() throws Throwable {
        GuestJoinConferenceRequest guestJoinConferenceRequest = mock(GuestJoinConferenceRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{guestJoinConferenceRequest});
        when(guestJoinConferenceRequest.getGuestID()).thenReturn(RandomUtils.nextInt());

        when(userService.getUserForGuestID(anyInt())).thenReturn(user);
        when(user.getRoomID()).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(true);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        } catch (Exception exception) {
            fail("This method is bound to throw Exception");
        }
        verify(userGroupService, times(1)).canGuestAccessRoom(anyInt());

    }

    @Test
    public void test_guest_link_endpoint_to_guest_request_invalid_endpointGUID() throws Throwable {
        IConferenceService conferenceService = mock(IConferenceService.class);
        LinkEndpointToGuestRequest linkEndpointToGuestRequest = mock(LinkEndpointToGuestRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{linkEndpointToGuestRequest});
        when(linkEndpointToGuestRequest.getEID()).thenReturn("");
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(linkEndpointToGuestRequest, never()).getGuestID();
        verify(conferenceService, never()).getEndpointIDForGUID(anyString(), anyString());
        verify(conferenceService, never()).getRoomIDForEndpointID(anyInt());
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_guest_link_endpoint_to_guest_request_valid_endpointGUID() throws Throwable {
        IConferenceService conferenceService = mock(IConferenceService.class);
        LinkEndpointToGuestRequest linkEndpointToGuestRequest = mock(LinkEndpointToGuestRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{linkEndpointToGuestRequest});
        when(linkEndpointToGuestRequest.getEID()).thenReturn("3456");
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(linkEndpointToGuestRequest, times(1)).getGuestID();
        verify(conferenceService, never()).getEndpointIDForGUID(anyString(), anyString());
        verify(conferenceService, never()).getRoomIDForEndpointID(anyInt());
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_guest_link_endpoint_to_guest_request_invalid_guest() throws Throwable {
        IConferenceService conferenceService = mock(IConferenceService.class);
        LinkEndpointToGuestRequest linkEndpointToGuestRequest = mock(LinkEndpointToGuestRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{linkEndpointToGuestRequest});
        when(linkEndpointToGuestRequest.getEID()).thenReturn("3456");
        when(linkEndpointToGuestRequest.getGuestID()).thenReturn(0);
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(linkEndpointToGuestRequest, times(1)).getGuestID();
        verify(conferenceService, never()).getEndpointIDForGUID(anyString(), anyString());
        verify(conferenceService, never()).getRoomIDForEndpointID(anyInt());
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_guest_link_endpoint_to_guest_request_valid_guest() throws Throwable {
        IConferenceService conferenceService = mock(IConferenceService.class);
        LinkEndpointToGuestRequest linkEndpointToGuestRequest = mock(LinkEndpointToGuestRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{linkEndpointToGuestRequest});
        when(linkEndpointToGuestRequest.getEID()).thenReturn("3456");
        when(linkEndpointToGuestRequest.getGuestID()).thenReturn(10);
        when(conferenceService.getEndpointIDForGUID(anyString(), anyString())).thenReturn(RandomUtils.nextInt());
        when(conferenceService.getRoomIDForEndpointID(anyInt())).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(false);
        userGroupCheckInterceptor.setConferenceService(conferenceService);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw exception");
        } catch (Exception exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.guest.InvalidArgumentFaultException);
        }
        verify(linkEndpointToGuestRequest, times(1)).getGuestID();
    }

    @Test
    public void test_guest_link_endpoint_to_guest_request_valid_data() throws Throwable {
        IConferenceService conferenceService = mock(IConferenceService.class);
        LinkEndpointToGuestRequest linkEndpointToGuestRequest = mock(LinkEndpointToGuestRequest.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{linkEndpointToGuestRequest});
        when(linkEndpointToGuestRequest.getEID()).thenReturn("3456");
        when(linkEndpointToGuestRequest.getGuestID()).thenReturn(10);
        when(conferenceService.getEndpointIDForGUID(anyString(), anyString())).thenReturn(RandomUtils.nextInt());
        when(conferenceService.getRoomIDForEndpointID(anyInt())).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(true);
        userGroupCheckInterceptor.setConferenceService(conferenceService);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        } catch (Exception exception) {
            fail("This method is bound to throw exception");
        }
        verify(linkEndpointToGuestRequest, times(1)).getGuestID();
    }

    @Test
    public void test_invalid_roomkey_login_as_guest() throws Throwable {
        LogInAsGuestRequest logInAsGuestRequest = mock(LogInAsGuestRequest.class);
        when(logInAsGuestRequest.getRoomKey()).thenReturn(null);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{logInAsGuestRequest});
        IRoomService mockRoomService = mock(IRoomService.class);
        userGroupCheckInterceptor.setRoomService(mockRoomService);
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(roomService, never()).getRoomDetailsByRoomKey(anyString(), anyInt());
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_empty_roomkey_login_as_guest() throws Throwable {
        LogInAsGuestRequest logInAsGuestRequest = mock(LogInAsGuestRequest.class);
        when(logInAsGuestRequest.getRoomKey()).thenReturn("invalid");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{logInAsGuestRequest});
        IRoomService mockRoomService = mock(IRoomService.class);
        userGroupCheckInterceptor.setRoomService(mockRoomService);
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(roomService, never()).getRoomDetailsByRoomKey(anyString(), anyInt());
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_valid_roomkey_login_as_guest() throws Throwable {
        LogInAsGuestRequest logInAsGuestRequest = mock(LogInAsGuestRequest.class);
        when(logInAsGuestRequest.getRoomKey()).thenReturn("45");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{logInAsGuestRequest});
        when(roomService.getRoomDetailsByRoomKey(anyString(), anyInt())).thenReturn(null);
        IRoomService mockRoomService = mock(IRoomService.class);
        userGroupCheckInterceptor.setRoomService(mockRoomService);
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(userGroupService, never()).canGuestAccessRoom(anyInt());
    }

    @Test
    public void test_valid_room_login_as_guest() throws Throwable {
        LogInAsGuestRequest logInAsGuestRequest = mock(LogInAsGuestRequest.class);
        when(logInAsGuestRequest.getRoomKey()).thenReturn("45");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{logInAsGuestRequest});
        com.vidyo.bo.Room mockRoom = mock(com.vidyo.bo.Room.class);
        when(roomService.getRoomDetailsByRoomKey(anyString(), anyInt())).thenReturn(mockRoom);
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(false);
        IRoomService mockRoomService = mock(IRoomService.class);
        userGroupCheckInterceptor.setRoomService(mockRoomService);
        userGroupCheckInterceptor.setRoomService(roomService);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This test is bound to throw exception");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.guest.InvalidArgumentFaultException);
        }
    }

    @Test
    public void test_valid_room_login_as_guest_success_case() throws Throwable {
        LogInAsGuestRequest logInAsGuestRequest = mock(LogInAsGuestRequest.class);
        when(logInAsGuestRequest.getRoomKey()).thenReturn("45");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{logInAsGuestRequest});
        com.vidyo.bo.Room mockRoom = mock(com.vidyo.bo.Room.class);
        when(roomService.getRoomDetailsByRoomKey(anyString(), anyInt())).thenReturn(mockRoom);
        when(userGroupService.canGuestAccessRoom(anyInt())).thenReturn(true);
        IRoomService mockRoomService = mock(IRoomService.class);
        userGroupCheckInterceptor.setRoomService(mockRoomService);
        userGroupCheckInterceptor.setRoomService(roomService);
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        } catch (Exception exception) {
            fail("This test is bound to throw exception");
        }
    }

    @Test
    public void test_invite_to_conference_request_user_service_cannot_access() throws Throwable {
        InviteToConferenceRequest inviteToConferenceRequest = mock(InviteToConferenceRequest.class);
        InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice_type0 = mock(InviteToConferenceRequestChoice_type0.class);

        when(inviteToConferenceRequest.getConferenceID()).thenReturn(entityID);
        when(inviteToConferenceRequestChoice_type0.getEntityID()).thenReturn(entityID);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice_type0);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false, false);
        when(entityID.getEntityID()).thenReturn("33434", "3455");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw InvalidArgumentFaultException");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof InvalidArgumentFaultException);
        }
    }

    @Test
    public void test_invite_to_conference_request_invitee_cannot_access() throws Throwable {
        InviteToConferenceRequest inviteToConferenceRequest = mock(InviteToConferenceRequest.class);
        InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice_type0 = mock(InviteToConferenceRequestChoice_type0.class);
        when(inviteToConferenceRequest.getConferenceID()).thenReturn(entityID);
        when(inviteToConferenceRequestChoice_type0.getEntityID()).thenReturn(entityID);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice_type0);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true, false);
        when(entityID.getEntityID()).thenReturn("33434", "3455");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        com.vidyo.bo.Member member = mock(com.vidyo.bo.Member.class);
        when(memberService.getInviteeDetails(anyInt())).thenReturn(member);
        when(member.getMemberID()).thenReturn(RandomUtils.nextInt());
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw InvalidArgumentFaultException");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof InvalidArgumentFaultException);
        }
    }

    @Test
    public void test_invite_to_conference_request_invitee_valid_data() throws Throwable {
        InviteToConferenceRequest inviteToConferenceRequest = mock(InviteToConferenceRequest.class);
        InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice_type0 = mock(InviteToConferenceRequestChoice_type0.class);

        when(inviteToConferenceRequest.getConferenceID()).thenReturn(entityID);
        when(inviteToConferenceRequestChoice_type0.getEntityID()).thenReturn(entityID);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice_type0);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true, true);
        when(entityID.getEntityID()).thenReturn("33434", "3455");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        com.vidyo.bo.Member member = mock(com.vidyo.bo.Member.class);
        when(memberService.getInviteeDetails(anyInt())).thenReturn(member);
        when(member.getMemberID()).thenReturn(RandomUtils.nextInt());
        try {
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("This method is not bound to throw any exception");
        }
    }

    @Test
    public void test_invite_to_conference_request_user_service_invalid_roomId() throws Throwable {
        InviteToConferenceRequest inviteToConferenceRequest = mock(InviteToConferenceRequest.class);
        InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice_type0 = mock(InviteToConferenceRequestChoice_type0.class);

        when(inviteToConferenceRequest.getConferenceID()).thenReturn(entityID);
        when(inviteToConferenceRequestChoice_type0.getEntityID()).thenReturn(entityID);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice_type0);
        when(entityID.getEntityID()).thenReturn("abcd", "df534df");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        userGroupCheckInterceptor.invoke(methodInvocation);
        verify(userGroupService, never()).canMemberAccessRoom(anyInt(), anyInt());
    }


    @Test
    public void test_invite_to_conference_admin_invalid_access_inviter() throws Throwable {
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequest inviteToConferenceRequest = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.class);
        when(inviteToConferenceRequest.getConferenceID()).thenReturn(adminEntityID);
        when(inviteToConferenceRequest.getConferenceID().getEntityID()).thenReturn(RandomUtils.nextInt());
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0.class);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice);
        when(inviteToConferenceRequestChoice.getEntityID()).thenReturn(adminEntityID);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(false);
        try{
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw InvalidArgumentFaultException.");
        }catch (Exception exception){
           assertNotNull(exception);
           assertTrue(exception instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException);
        }
    }

    @Test
    public void test_invite_to_conference_admin_invalid_access_invitee() throws Throwable {
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequest inviteToConferenceRequest = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.class);
        when(inviteToConferenceRequest.getConferenceID()).thenReturn(adminEntityID);
        when(inviteToConferenceRequest.getConferenceID().getEntityID()).thenReturn(RandomUtils.nextInt());
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0.class);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice);
        when(inviteToConferenceRequestChoice.getEntityID()).thenReturn(adminEntityID);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true,false);
        com.vidyo.bo.Member member = mock(com.vidyo.bo.Member.class);
        when(memberService.getInviteeDetails(anyInt())).thenReturn(member);
        when(member.getMemberID()).thenReturn(RandomUtils.nextInt());
        try{
            userGroupCheckInterceptor.invoke(methodInvocation);
            fail("This method is bound to throw InvalidArgumentFaultException.");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof com.vidyo.portal.admin.v1_1.InvalidArgumentFaultException);
        }
    }

    @Test
    public void test_invite_to_conference_admin_valid_access_invitee() throws Throwable {
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequest inviteToConferenceRequest = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequest.class);
        when(inviteToConferenceRequest.getConferenceID()).thenReturn(adminEntityID);
        when(inviteToConferenceRequest.getConferenceID().getEntityID()).thenReturn(RandomUtils.nextInt());
        com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0 inviteToConferenceRequestChoice = mock(com.vidyo.portal.admin.v1_1.InviteToConferenceRequestChoice_type0.class);
        when(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0()).thenReturn(inviteToConferenceRequestChoice);
        when(inviteToConferenceRequestChoice.getEntityID()).thenReturn(adminEntityID);
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(RandomUtils.nextInt());
        when(methodInvocation.getArguments()).thenReturn(new Object[]{inviteToConferenceRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true,true);
        com.vidyo.bo.Member member = mock(com.vidyo.bo.Member.class);
        when(memberService.getInviteeDetails(anyInt())).thenReturn(member);
        when(member.getMemberID()).thenReturn(RandomUtils.nextInt());
        try{
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("This method is not bound to throw InvalidArgumentFaultException.");
        }
    }

    @Test
    public void test_silence_speaker_server_request() throws Throwable {
        SilenceSpeakerServerRequest silenceSpeakerServerRequest = mock(SilenceSpeakerServerRequest.class);
        SilenceSpeakerServerType silenceSpeakerServerType = mock(SilenceSpeakerServerType.class);
        when(methodInvocation.getArguments()).thenReturn(new Object[]{silenceSpeakerServerRequest});
        when(silenceSpeakerServerRequest.getSilenceSpeakerServerRequest()).thenReturn(silenceSpeakerServerType);
        when(silenceSpeakerServerType.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("dsfdsfdsf");
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true);
        try{
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("This method is not bound to throw exception");
        }
    }

    @Test
    public void test_speaker_server_all_request()throws Throwable{
        SilenceSpeakerServerAllRequest silenceSpeakerServerAllRequest = mock(SilenceSpeakerServerAllRequest.class);
        SilenceSpeakerServerType silenceSpeakerServerType = mock(SilenceSpeakerServerType.class);

        when(silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest()).thenReturn(silenceSpeakerServerType);
        when(silenceSpeakerServerType.getConferenceID()).thenReturn(entityID);
        when(entityID.getEntityID()).thenReturn("valid-roomId");
        when(methodInvocation.getArguments()).thenReturn(new Object[]{silenceSpeakerServerAllRequest});
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true);

        try{
            userGroupCheckInterceptor.invoke(methodInvocation);
        }catch (Exception exception){
            fail("This method is not bound to throw exception.");
        }
    }

    private void constructValidDataAndCallExecute(Executor executor) throws Throwable {
        when(userService.getLoginUser()).thenReturn(user);
        when(user.getMemberID()).thenReturn(45);
        when(entityID.getEntityID()).thenReturn("4");
        when(userGroupService.canMemberAccessRoom(anyInt(), anyInt())).thenReturn(true);
        try {
            executor.execute();
        } catch (Exception exception){
            fail("should not throw exception for valid data");
        }
        verify(userService, times(1)).getLoginUser();
        verify(userGroupService, times(1)).canMemberAccessRoom(anyInt(),anyInt());
    }

    interface Executor{
        public void execute() throws Throwable;
    }
}
