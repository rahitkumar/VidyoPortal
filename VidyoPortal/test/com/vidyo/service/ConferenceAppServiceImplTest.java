package com.vidyo.service;

import com.vidyo.bo.Guest;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.ConferenceAppServiceImpl;
import com.vidyo.service.conference.request.GuestJoinConfRequest;
import com.vidyo.service.conference.request.InviteToConferenceRequest;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.conference.request.LeaveConferenceRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.conference.response.LeaveConferenceResponse;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.system.SystemService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;

/**
 * Created by fbao on 7/26/2017.
 */
public class ConferenceAppServiceImplTest {
    @InjectMocks
    private ConferenceAppService conferenceAppService = new ConferenceAppServiceImpl();

    @Mock
    private SystemService systemServiceMock;
    @Mock
    private IRoomService roomServiceMock;
    @Mock
    private ITenantService tenantServiceMock;
    @Mock
    private IMemberService memberServiceMock;
    @Mock
    private IFederationConferenceService federationConferenceServiceMock;
    @Mock
    private IConferenceService conferenceServiceMock;
    @Mock
    private IUserService userServiceMock;

    @Mock
    private ScheduledRoomResponse scheduledRoomResponseMock;

    @BeforeMethod(alwaysRun = true)
    public void injectDoubles() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     *
     * JoinConferenceResponse joinInterPortalRoomConference(JoinConferenceRequest joinConferenceRequest)
     */

    /**
     *
     * JoinConferenceResponse joinConferenceInRoom(JoinConferenceRequest joinConferenceRequest)
     */
    @Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndJoiningMemberIsNull(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        joinConferenceRequest.setJoiningMemberId(joiningMemberID);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.INVALID_MEMBER);
    }

    @Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndJoiningMemberIsNotNullAndRoomToJoinIsNull(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        joinConferenceRequest.setJoiningMemberId(joiningMemberID);

        int roomId = 2;
        joinConferenceRequest.setRoomId(roomId);

        Member joiningMember = new Member();
        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.INVALID_ROOM);
    }

    @Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduleAndTenantIsNull(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        joinConferenceRequest.setJoiningMemberId(joiningMemberID);

        int roomId = 2;

        joinConferenceRequest.setRoomId(roomId);

        Member joiningMember = new Member();

        int toJoinTenantID = 3;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.INVALID_ROOM);
    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduleAndStatusNotEqualsToSUCCESS(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        joinConferenceRequest.setJoiningMemberId(joiningMemberID);

        int roomId = 2;

        joinConferenceRequest.setRoomId(roomId);

        Member joiningMember = new Member();

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(toJoinMemberID);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(5);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(toJoinMemberID,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.INVALID_ROOM);

    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduleAndCanCallTenantIsFalse(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        joinConferenceRequest.setJoiningMemberId(joiningMemberID);

        int roomId = 2;
        int tenantId = 5;

        joinConferenceRequest.setRoomId(roomId);

        Member joiningMember = new Member();
        joiningMember.setTenantID(tenantId);

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(toJoinMemberID);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(0);

        List<Tenant> allowedTenants = new ArrayList<>();
        Tenant tenant1 = new Tenant();
        tenant1.setTenantID(tenantId);
        allowedTenants.add(tenant1);

        when(tenantServiceMock.canCallToTenants(tenantId)).thenReturn(allowedTenants);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(toJoinMemberID,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.INVALID_ROOM);

    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduledAndDisableRoomCheck(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        int roomId = 2;
        int tenantId = 5;
        String schRoomConfName = "schRoomConName";

        joinConferenceRequest.setJoiningMemberId(joiningMemberID);
        joinConferenceRequest.setRoomId(roomId);
        joinConferenceRequest.setSchRoomConfName(schRoomConfName);

        Member joiningMember = new Member();
        joiningMember.setTenantID(tenantId);

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(joiningMemberID);
        roomToJoin.setRoomLocked(1);
        roomToJoin.setRoomEnabled(-1);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(0);

        List<Tenant> allowedTenants = new ArrayList<>();
        Tenant tenant1 = new Tenant();
        tenant1.setTenantID(toJoinTenantID);
        allowedTenants.add(tenant1);

        when(tenantServiceMock.canCallToTenants(tenantId)).thenReturn(allowedTenants);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(joiningMemberID,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.ROOM_DISABLED);
    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduledAndRoomLocked(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        int roomId = 2;
        int tenantId = 5;
        String schRoomConfName = "schRoomConName";

        joinConferenceRequest.setJoiningMemberId(joiningMemberID);
        joinConferenceRequest.setRoomId(roomId);
        joinConferenceRequest.setSchRoomConfName(schRoomConfName);

        Member joiningMember = new Member();
        joiningMember.setTenantID(tenantId);

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        String roomPin = "5";

        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(toJoinMemberID);
        roomToJoin.setRoomLocked(1);
        roomToJoin.setRoomPIN(roomPin);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(0);

        List<Tenant> allowedTenants = new ArrayList<>();
        Tenant tenant1 = new Tenant();
        tenant1.setTenantID(toJoinTenantID);
        allowedTenants.add(tenant1);

        when(tenantServiceMock.canCallToTenants(tenantId)).thenReturn(allowedTenants);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(toJoinMemberID,Integer.parseInt(roomPin))).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.ROOM_LOCKED);
    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduledAndWrongPin(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        int roomId = 2;
        int tenantId = 5;
        String schRoomConfName = "schRoomConName";
        String roomPin = "6";

        joinConferenceRequest.setJoiningMemberId(joiningMemberID);
        joinConferenceRequest.setRoomId(roomId);
        joinConferenceRequest.setSchRoomConfName(schRoomConfName);
        joinConferenceRequest.setCheckPin(true);

        Member joiningMember = new Member();
        joiningMember.setTenantID(tenantId);

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(toJoinMemberID);
        roomToJoin.setRoomLocked(0);
        roomToJoin.setRoomEnabled(1);
        roomToJoin.setRoomPIN(roomPin);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(0);

        List<Tenant> allowedTenants = new ArrayList<>();
        Tenant tenant1 = new Tenant();
        tenant1.setTenantID(toJoinTenantID);
        allowedTenants.add(tenant1);

        when(tenantServiceMock.canCallToTenants(tenantId)).thenReturn(allowedTenants);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(toJoinMemberID,6)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.WRONG_PIN);
    }

    //@Test(groups = {"conferenceAppService"})
    public void JoinConferenceResponseWithValidInputAndRoomTypeEqualsShceduledAndRoomCapacityReached(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int joiningMemberID = 1;
        int roomId = 2;
        int tenantId = 5;
        String schRoomConfName = "schRoomConName";
        String roomPin = "6";

        joinConferenceRequest.setJoiningMemberId(joiningMemberID);
        joinConferenceRequest.setRoomId(roomId);
        joinConferenceRequest.setSchRoomConfName(schRoomConfName);
        joinConferenceRequest.setCheckPin(true);
        joinConferenceRequest.setPin(roomPin);

        Member joiningMember = new Member();
        joiningMember.setTenantID(tenantId);

        int toJoinTenantID = 3;
        int toJoinMemberID = 4;
        int occupantsCount = 7;
        int roomMaxUsers = 7;
        Room roomToJoin = new Room();
        roomToJoin.setRoomID(roomId);
        roomToJoin.setRoomType("scheduled");
        roomToJoin.setTenantID(toJoinTenantID);
        roomToJoin.setMemberID(toJoinMemberID);
        roomToJoin.setRoomLocked(0);
        roomToJoin.setRoomEnabled(1);
        roomToJoin.setRoomPIN(roomPin);
        roomToJoin.setOccupantsCount(occupantsCount);
        roomToJoin.setRoomMaxUsers(roomMaxUsers);

        Tenant tenant = new Tenant();
        tenant.setScheduledRoomEnabled(1);

        Long scheduledRoomPin = new Long(6);
        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(0);
        scheduledRoomResponse.setPin(scheduledRoomPin);

        List<Tenant> allowedTenants = new ArrayList<>();
        Tenant tenant1 = new Tenant();
        tenant1.setTenantID(toJoinTenantID);
        allowedTenants.add(tenant1);

        when(tenantServiceMock.canCallToTenants(tenantId)).thenReturn(allowedTenants);

        when(memberServiceMock.getMember(joiningMemberID)).thenReturn(joiningMember);
        when(roomServiceMock.getRoomDetailsForConference(roomId)).thenReturn(roomToJoin);
        when(tenantServiceMock.getTenant(toJoinTenantID)).thenReturn(tenant);
        when(roomServiceMock.generateSchRoomExtPin(toJoinMemberID,6)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceInRoom(joinConferenceRequest).getStatus(), JoinConferenceResponse.ROOM_CAPACITY_REACHED);
    }

    /**
     *
     * JoinConferenceResponse joinRemoteConference(JoinConferenceRequest joinConferenceRequest)
     */

    /**
     *
     * JoinConferenceResponse joinConferenceInScheduledRoom(JoinConferenceRequest joinConferenceRequest)
     */

    /**
     *
     * ScheduledRoomResponse validateScheduledRoom(String roomExt, String pin)
     */
    @Test(groups = {"conferenceAppService"})
    public void validateScheduledRoomWithValidInputAndRoomExtStartsWithNotEmptyPrefix(){
        String roomExt = "schRoomPrefixRoomExt";
        String pin = "pin";

        String schRoomPrefix= "schRoomPrefix";
        int status = 1;

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(status);
        scheduledRoomResponse.setSchRoomPrefix(schRoomPrefix);

        when(roomServiceMock.validateScheduledRoom(roomExt,pin,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.validateScheduledRoom(roomExt,pin).getStatus(),ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN);

    }

    @Test(groups = {"conferenceAppService"})
    public void validateScheduledRoomWithValidInputAndRoomExtNotStartsWithPrefix(){
        String roomExt = "roomExt";
        String pin = "pin";

        String schRoomPrefix= "schRoomPrefix";
        int status = 1;

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(status);
        scheduledRoomResponse.setSchRoomPrefix(schRoomPrefix);

        when(roomServiceMock.validateScheduledRoom(roomExt,pin,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.validateScheduledRoom(roomExt,pin),scheduledRoomResponse);

    }

    @Test(groups = {"conferenceAppService"})
    public void validateScheduledRoomWithValidInputAndStatusEqualsToZero(){
        String roomExt = "roomExt";
        String pin = "pin";

        String schRoomPrefix= "schRoomPrefix";
        int status = 0;

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(status);
        scheduledRoomResponse.setSchRoomPrefix(schRoomPrefix);

        when(roomServiceMock.validateScheduledRoom(roomExt,pin,0)).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.validateScheduledRoom(roomExt,pin),scheduledRoomResponse);
    }

    /**
     *
     * JoinConferenceResponse joinConferenceAsGuest(GuestJoinConfRequest guestJoinConfRequest)
     */
    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndGuestNotAllowed(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        boolean isGuestAllowed = false;
        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.GUESTS_NOT_ALLOWED);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndInvalidGuest(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        guestJoinConfRequest.setGuestId(guestId);

        boolean isGuestAllowed = true;
        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);

        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.INVALID_GUEST);
    }

    @Test(groups = {"conferenceAppService"},expectedExceptions = NullPointerException.class)
    public void joinConferenceAsGuestWithValidInvalidInput(){
        GuestJoinConfRequest guestJoinConfRequest = null;

        boolean isGuestAllowed = true;
        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.INVALID_GUEST);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndInvalidRoom(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);

        boolean isGuestAllowed = true;

        User guestUser = new User();

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.INVALID_ROOM);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomIdLessThanZero(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = -1;
        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);

        boolean isGuestAllowed = true;

        User guestUser = new User();

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.INVALID_ROOM);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomToJoinTenantIDNotEqualasToGusetJoinReq(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);


        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 4;
        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.INVALID_ROOM);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndAndEndPointIsNull(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        User guestUser = new User();
        Room roomToJoin = new Room();
        Guest guest = new Guest();

        roomToJoin.setTenantID(roomToJoinTenantId);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.GUESTS_NOT_LINKED);
    }

    //@Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypeIsScheduledAndStatusIsSuccessAndPinIsNotNull(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "Scheduled";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(pin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(ScheduledRoomResponse.SUCCESS);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(pin))).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypeIsScheduledAndStatusIsNotSuccessAndPinIsNotNull(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "Scheduled";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(500);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypeIsScheduledAndRoomTypeNotEqualsToScheduledAndPinIsNotNull(){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(500);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponse);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithNullScheduledRoomResponse (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
        scheduledRoomResponse.setStatus(500);

        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponse);

        when(roomServiceMock.validateScheduledRoom(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithFailureScheduledResponse (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);


        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponseMock);

        when(roomServiceMock.validateScheduledRoom(null, "5", 2)).thenReturn(scheduledRoomResponseMock);
        when(scheduledRoomResponseMock.getStatus()).thenReturn(ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithNullRoomInsScheduledRoomResponse (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);


        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponseMock);

        when(roomServiceMock.validateScheduledRoom(null, "5", 2)).thenReturn(scheduledRoomResponseMock);
        when(scheduledRoomResponseMock.getStatus()).thenReturn(ScheduledRoomResponse.SUCCESS);
        when(scheduledRoomResponseMock.getRoom()).thenReturn(null);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }

    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithValidRoomNullRoomPin (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);


        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponseMock);

        when(roomServiceMock.validateScheduledRoom(null, "5", 2)).thenReturn(scheduledRoomResponseMock);
        when(scheduledRoomResponseMock.getStatus()).thenReturn(ScheduledRoomResponse.SUCCESS);
        when(scheduledRoomResponseMock.getRoom()).thenReturn(new Room());

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }


    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithValidRoomInvalidRoomPin (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        Room room = new Room();
        room.setRoomPIN("2345");


        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponseMock);

        when(roomServiceMock.validateScheduledRoom(null, "5", 2)).thenReturn(scheduledRoomResponseMock);
        when(scheduledRoomResponseMock.getStatus()).thenReturn(ScheduledRoomResponse.SUCCESS);
        when(scheduledRoomResponseMock.getRoom()).thenReturn(room);

        assertEquals(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }


    @Test(groups = {"conferenceAppService"})
    public void joinConferenceAsGuestWithValidInputAndRoomTypwithValidRoomValidRoomPin (){
        GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();

        int guestId = 1;
        int roomId = 2;
        int guestJoinConfRequestTenantId = 2;
        int memberId = 4;
        String pin = "10";

        guestJoinConfRequest.setGuestId(guestId);
        guestJoinConfRequest.setRoomId(roomId);
        guestJoinConfRequest.setTenantId(guestJoinConfRequestTenantId);
        guestJoinConfRequest.setPin(pin);

        boolean isGuestAllowed = true;

        int roomToJoinTenantId = 2;
        String responsePin = "5";

        User guestUser = new User();
        Room roomToJoin = new Room();

        roomToJoin.setTenantID(roomToJoinTenantId);

        String endPointGUID = "endPointGUID";
        String roomToJoinRoomType = "roomType";

        roomToJoin.setRoomType(roomToJoinRoomType);
        roomToJoin.setRoomPIN(responsePin);
        roomToJoin.setMemberID(memberId);

        Guest guest = new Guest();
        guest.setEndpointGUID(endPointGUID);

        Room room = new Room();
        room.setRoomPIN("10");


        when(roomServiceMock.areGuestAllowedToThisRoom()).thenReturn(isGuestAllowed);
        when(userServiceMock.getUserForGuestID(guestId)).thenReturn(guestUser);
        when(roomServiceMock.getRoom(roomId)).thenReturn(roomToJoin);
        when(memberServiceMock.getGuest(guestId)).thenReturn(guest);
        when(roomServiceMock.generateSchRoomExtPin(memberId,Integer.parseInt(responsePin))).thenReturn(scheduledRoomResponseMock);

        when(roomServiceMock.validateScheduledRoom(null, "5", 2)).thenReturn(scheduledRoomResponseMock);
        when(scheduledRoomResponseMock.getStatus()).thenReturn(ScheduledRoomResponse.SUCCESS);
        when(scheduledRoomResponseMock.getRoom()).thenReturn(room);

        assertNotSame(conferenceAppService.joinConferenceAsGuest(guestJoinConfRequest).getStatus(),JoinConferenceResponse.WRONG_PIN);
    }




    /**
     *
     * JoinConferenceResponse joinConference(JoinConferenceRequest joinConferenceRequest)
     */
    @Test(groups = {"conferenceAppService"})
    public void joinConferenceWithValidInputAndStatusIsInvalidScheduleRoom(){
        JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();

        int roomId = -1;
        String roomNameExt = "roomNameExt@";

        joinConferenceRequest.setRoomId(roomId);
        joinConferenceRequest.setRoomNameExt(roomNameExt);

        assertEquals(conferenceAppService.joinConference(joinConferenceRequest).getStatus(),ScheduledRoomResponse.INVALID_SCHEDULED_ROOM);
    }

    /**
     *
     *  JoinConferenceResponse inviteParticipantToConference(InviteToConferenceRequest inviteToConferenceRequest)
     */
    @Test(groups = {"conferenceAppService"})
    public void inviteParticipantToConferenceWithValidInputAndStatusIsParams_Not_Present(){
        InviteToConferenceRequest inviteToConferenceRequest = new InviteToConferenceRequest();

        int inviteeId = -1;

        inviteToConferenceRequest.setInviteeId(inviteeId);

        assertEquals(conferenceAppService.inviteParticipantToConference(inviteToConferenceRequest).getStatus(),JoinConferenceResponse.REQUIRED_PARAMS_NOT_PRESENT);
    }

    /**
     *
     * JoinConferenceResponse validateRoom(Room roomToJoin, String inputModeratorPin)
     */

    /**
     *
     * boolean canMemberControlRoom(int controllingMemberID, int roomID, int roomOwnerID, String roomModeratorPin, String inputModeratorPin, String userRole)
     */

    /**
     *
     * LeaveConferenceResponse leaveConference (LeaveConferenceRequest leaveConferenceRequest)
     */
    @Test(groups = {"conferenceAppService"})
    public void leaveConferenceWithValidInputAndStatusIsInvalidRoom(){
        LeaveConferenceResponse leaveConferenceResponse = new LeaveConferenceResponse();

        LeaveConferenceRequest leaveConferenceRequest = new LeaveConferenceRequest();
        int roomId = 1;
        int tenantContexId = 2;

        leaveConferenceRequest.setRoomId(roomId);

        TenantContext.setTenantId(tenantContexId);

        Room roomToDisconnect = new Room();

        when(roomServiceMock.getRoomDetailsForDisconnectParticipant(roomId,tenantContexId)).thenReturn(null);

        assertEquals(conferenceAppService.leaveConference(leaveConferenceRequest).getStatus(),JoinConferenceResponse.INVALID_ROOM);
    }
}
