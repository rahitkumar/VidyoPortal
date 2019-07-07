package com.vidyo.service.conference;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.ExternalLink;
import com.vidyo.bo.Group;
import com.vidyo.bo.Guest;
import com.vidyo.bo.Invite;
import com.vidyo.bo.Member;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.Room;
import com.vidyo.bo.User;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.IConferenceDao;
import com.vidyo.db.IFederationDao;
import com.vidyo.service.ConferenceServiceImpl;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.CancelInvitationException;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.EndpointNotExistException;
import com.vidyo.service.exceptions.EndpointNotSupportedException;
import com.vidyo.service.exceptions.InviteConferenceException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.MakeCallException;
import com.vidyo.service.exceptions.NoVidyoFederationException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.StartRingException;
import com.vidyo.service.exceptions.StopRingException;
import com.vidyo.service.guest.GuestService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.ws.federation.DropRemoteConferenceRequest;
import com.vidyo.ws.federation.DropRemoteConferenceResponse;
import com.vidyo.ws.federation.GeneralFault;
import com.vidyo.ws.federation.GeneralFaultException;
import com.vidyo.ws.federation.InvalidArgumentFault;
import com.vidyo.ws.federation.InvalidArgumentFaultException;
import com.vidyo.ws.federation.VidyoFederationServiceStub;
import com.vidyo.ws.manager.EndpointNotExistFault;
import com.vidyo.ws.manager.EndpointNotExistFaultException;
import com.vidyo.ws.manager.NotLicensedFault;
import com.vidyo.ws.manager.NotLicensedFaultException;
import com.vidyo.ws.manager.RemoveExternalLinkRequest;
import com.vidyo.ws.manager.RemoveExternalLinkResponse;
import com.vidyo.ws.manager.ResourceNotAvailableFault;
import com.vidyo.ws.manager.ResourceNotAvailableFaultException;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

public class ConferenceServiceImplTest {
    @InjectMocks
    private IConferenceService conferenceService = new ConferenceServiceImpl();

    @Mock
    private IConferenceDao daoMock;
    @Mock
    private ComponentsService componentsServiceMock;
    @Mock
    private ISystemService systemMock;
    @Mock
    private IRoomService roomMock;
    @Mock
    private IGroupService groupMock;
    @Mock
    private IMemberService memberMock;
    @Mock
    private EndpointService endpointServiceMock;
    @Mock
    private IFederationDao federationDaoMock;
    @Mock
    private GuestService guestServiceMock;
    @Mock
    private TransactionService transactionServiceMock;
    @Mock
    private ITenantService tenantMock;
    @Mock
    private DropRemoteConferenceResponse dropRemoteConferenceResponseMock;
    @Mock
    private VidyoFederationServiceStub vidyoFederationServiceStubMock;
    @Mock
    private VidyoManagerServiceStub vidyoManagerServiceStubMock;
    @Mock
    private com.vidyo.ws.manager.Status_type0 statusType0Mock;
    @Mock
    private RemoveExternalLinkResponse removeExternalLinkResponseMock;


    @BeforeMethod(alwaysRun = true)
    public void injectDoubles() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     * String getVMConnectAddress() throws NoVidyoManagerException
     */
    @Test(groups = {"conferenceService"})
    public void getVMConnectAddressWithValidInput() throws NoVidyoManagerException {
        when(componentsServiceMock.getVidyoManagerConnectAddress()).thenReturn("connectAddress");
        when(systemMock.getTLSProxyConfiguration()).thenReturn(true);

        assertEquals(conferenceService.getVMConnectAddress(),"connectAddress&TlsProxy=true");
    }


    @Test(groups = {"conferenceService"})
    public void getVMConnectAddressWithtlsProxyNotEnabled() throws NoVidyoManagerException {
        when(componentsServiceMock.getVidyoManagerConnectAddress()).thenReturn("connectAddress");
        when(systemMock.getTLSProxyConfiguration()).thenReturn(false);

        assertEquals(conferenceService.getVMConnectAddress(),"connectAddress");
    }

    /**
     *
     * String createConferenceForFederation(String confName, Group room_group) throws NoVidyoManagerException,
     ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException
     */


    /**
     *
     * void addEndpointToConference(String uniqueCallID, Room room, Member member, String endpointType)
     */

    /**
     *
     * EndpointCallPref populateEndpointCallPref(EndpointCallPref endpoint, Room roomToJoin, Member joiningMember,
     String endpointType)
     */

    /**
     *
     * void addEndpointToConferenceForFederation(String confName, Group room_group, Member member,
     String endpointType) throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     ResourceNotAvailableException, EndpointNotExistException
     */

    /**
     *
     * void inviteRecorderToConference(Room room, Group room_group, Member member, String recorderGUID,
     String recorderName, int webcast) throws NoVidyoManagerException, ConferenceNotExistException,
     NotLicensedException, EndpointNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * void inviteEndpointToConference(String uniqueCallID, Room room, Group room_group, Member fromMember,
     Member toMember, String toEndpointType) throws NoVidyoManagerException, ConferenceNotExistException,
     NotLicensedException, EndpointNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * void cancelInviteEndpointToConference(Room room, Group room_group, Member fromMember, Member toMember)
     throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     EndpointNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * void removeEndpointFromConference(Room room, Member member, String endpointType)
     throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     ResourceNotAvailableException, EndpointNotExistException
     */

    /**
     *
     * void removeEndpointFromConference(Room room, Member member)
     */

    /**
     *
     * void removeEndpointFromConferenceForFederation(String name_of_conference, Member member)
     throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     ResourceNotAvailableException, EndpointNotExistException
     */

    /**
     *
     * int deleteConference(String confName) throws NoVidyoManagerException, ConferenceNotExistException,
     NotLicensedException, ResourceNotAvailableException
     */

    /**
     *
     * void disconnectAll(String confName) throws NoVidyoManagerException, ConferenceNotExistException,
     NotLicensedException, ResourceNotAvailableException
     */

    /**
     *
     * void startRing(Room room, Member fromMember, Member toMember)
     throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     EndpointNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * void stopRing(Room room, Member fromMember, Member toMember)
     throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     EndpointNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * String createP2PConference(Room fromRoom, Group room_group, Room toRoom) throws NoVidyoManagerException,
     ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException
     */

    /**
     *
     * void makeCall(String uniqueCallID, Room fromRoom, Group room_group, Room toRoom, Member fromMember,
     Member toMember, String fromEndpointType, String toEndpointType)
     throws NoVidyoManagerException, NotLicensedException, EndpointNotExistException,
     ConferenceNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * boolean isEndpointReady(String type, String guid)
     */

    /**
     *
     * void joinTheConference(Room roomToJoin, String virtualEndpointGUID, String displayName)
     throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException
     */

    /**
     *
     * oid joinTheConferenceInQueue(String virtualEndpointGUID)
     throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException
     */

    /**
     *
     * void leaveTheConference(int endpointID, int roomID, CallCompletionCode callCompletionCode)
     throws LeaveConferenceException
     */

    /**
     *
     * void leaveTheConference(String GUID, int roomID, CallCompletionCode callCompletionCode)
     throws LeaveConferenceException
     */

    /**
     *
     * void checkAndDeleteTheConference(Control conference) throws DeleteConferenceException
     */

    /**
     *
     * void dropRemoteConf(ExternalLink externalLink) throws ConferenceNotExistException, RemoteException
     */
    @Test(groups = {"conferenceService"},expectedExceptions = NullPointerException.class)
    public void dropRemoteConfWithValidInputAndRestIsEmpty() throws NoVidyoFederationException, RemoteException, ConferenceNotExistException, InvalidArgumentFaultException, GeneralFaultException {
        ExternalLink externalLink = new ExternalLink();

        String secure = "Y";
        String toTenantHost = "toTenantHost";
        String fromSystemID = "fromSystemID";
        String fromConferenceName = "fromConferenceName";
        String toSystemID = "toSystemID";
        String toConferenceName = "toConferenceName";

        externalLink.setSecure(secure);
        externalLink.setToTenantHost(toTenantHost);
        externalLink.setFromSystemID(fromSystemID);
        externalLink.setFromConferenceName(fromConferenceName);
        externalLink.setToSystemID(toSystemID);
        externalLink.setToConferenceName(toConferenceName);

        when(systemMock.getVidyoFederationServiceStubWithAUTH(toTenantHost,true)).thenReturn(vidyoFederationServiceStubMock);

        DropRemoteConferenceResponse resp = new DropRemoteConferenceResponse();
        when(vidyoFederationServiceStubMock.dropRemoteConference(any(DropRemoteConferenceRequest.class))).thenReturn(resp);

        ((ConferenceServiceImpl)conferenceService).dropRemoteConf(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = ConferenceNotExistException.class)
    public void dropRemoteConfWithValidInputAndNoVidyoFederationException() throws NoVidyoFederationException, RemoteException, ConferenceNotExistException, InvalidArgumentFaultException, GeneralFaultException {
        ExternalLink externalLink = new ExternalLink();

        String secure = "Y";
        String toTenantHost = "toTenantHost";
        String fromSystemID = "fromSystemID";
        String fromConferenceName = "fromConferenceName";
        String toSystemID = "toSystemID";
        String toConferenceName = "toConferenceName";

        externalLink.setSecure(secure);
        externalLink.setToTenantHost(toTenantHost);
        externalLink.setFromSystemID(fromSystemID);
        externalLink.setFromConferenceName(fromConferenceName);
        externalLink.setToSystemID(toSystemID);
        externalLink.setToConferenceName(toConferenceName);

        when(systemMock.getVidyoFederationServiceStubWithAUTH(toTenantHost,true)).thenThrow(NoVidyoFederationException.class);

        ((ConferenceServiceImpl)conferenceService).dropRemoteConf(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = ConferenceNotExistException.class)
    public void dropRemoteConfWithValidInputAndInvalidArgumentFaultException() throws NoVidyoFederationException, RemoteException, ConferenceNotExistException, InvalidArgumentFaultException, GeneralFaultException {
        ExternalLink externalLink = new ExternalLink();

        String secure = "Y";
        String toTenantHost = "toTenantHost";
        String fromSystemID = "fromSystemID";
        String fromConferenceName = "fromConferenceName";
        String toSystemID = "toSystemID";
        String toConferenceName = "toConferenceName";

        externalLink.setSecure(secure);
        externalLink.setToTenantHost(toTenantHost);
        externalLink.setFromSystemID(fromSystemID);
        externalLink.setFromConferenceName(fromConferenceName);
        externalLink.setToSystemID(toSystemID);
        externalLink.setToConferenceName(toConferenceName);

        InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException();
        InvalidArgumentFault msg = new InvalidArgumentFault();
        invalidArgumentFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoFederationServiceStubWithAUTH(toTenantHost,true)).thenReturn(vidyoFederationServiceStubMock);

        when(vidyoFederationServiceStubMock.dropRemoteConference(any(DropRemoteConferenceRequest.class))).thenThrow(invalidArgumentFaultException);

        ((ConferenceServiceImpl)conferenceService).dropRemoteConf(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = ConferenceNotExistException.class)
    public void dropRemoteConfWithValidInputAndInvalidGeneralFaultException() throws NoVidyoFederationException, RemoteException, ConferenceNotExistException, InvalidArgumentFaultException, GeneralFaultException {
        ExternalLink externalLink = new ExternalLink();

        String secure = "Y";
        String toTenantHost = "toTenantHost";
        String fromSystemID = "fromSystemID";
        String fromConferenceName = "fromConferenceName";
        String toSystemID = "toSystemID";
        String toConferenceName = "toConferenceName";

        externalLink.setSecure(secure);
        externalLink.setToTenantHost(toTenantHost);
        externalLink.setFromSystemID(fromSystemID);
        externalLink.setFromConferenceName(fromConferenceName);
        externalLink.setToSystemID(toSystemID);
        externalLink.setToConferenceName(toConferenceName);

        when(systemMock.getVidyoFederationServiceStubWithAUTH(toTenantHost,true)).thenReturn(vidyoFederationServiceStubMock);

        GeneralFaultException generalFaultException = new GeneralFaultException();
        GeneralFault msg = new GeneralFault();
        generalFaultException.setFaultMessage(msg);


        when(vidyoFederationServiceStubMock.dropRemoteConference(any(DropRemoteConferenceRequest.class))).thenThrow(generalFaultException);

        ((ConferenceServiceImpl)conferenceService).dropRemoteConf(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = RemoteException.class)
    public void dropRemoteConfWithValidInputAndRemoteException() throws NoVidyoFederationException, RemoteException, ConferenceNotExistException, InvalidArgumentFaultException, GeneralFaultException {
        ExternalLink externalLink = new ExternalLink();

        String secure = "Y";
        String toTenantHost = "toTenantHost";
        String fromSystemID = "fromSystemID";
        String fromConferenceName = "fromConferenceName";
        String toSystemID = "toSystemID";
        String toConferenceName = "toConferenceName";

        externalLink.setSecure(secure);
        externalLink.setToTenantHost(toTenantHost);
        externalLink.setFromSystemID(fromSystemID);
        externalLink.setFromConferenceName(fromConferenceName);
        externalLink.setToSystemID(toSystemID);
        externalLink.setToConferenceName(toConferenceName);

        when(systemMock.getVidyoFederationServiceStubWithAUTH(toTenantHost,true)).thenReturn(vidyoFederationServiceStubMock);


        when(vidyoFederationServiceStubMock.dropRemoteConference(any(DropRemoteConferenceRequest.class))).thenThrow(RemoteException.class);

        ((ConferenceServiceImpl)conferenceService).dropRemoteConf(externalLink);
    }

    /**
     *
     * void disconnectAll(Room roomToDisconnect) throws DisconnectAllException
     */

    /**
     *
     * String inviteToConference(Invite invite) throws OutOfPortsException, EndpointNotExistException,
     InviteConferenceException, EndpointNotSupportedException
     */
    @Test(groups = {"conferenceService"},expectedExceptions = NullPointerException.class)
    public void inviteToConferenceWithEmptyRoom() throws EndpointNotExistException, EndpointNotSupportedException, InviteConferenceException, OutOfPortsException {
        Invite invite = new Invite();
        when(roomMock.getRoomDetailsForConference(0)).thenReturn(null);

        conferenceService.inviteToConference(invite);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = InviteConferenceException.class)
    public void inviteToConferenceWithEmptyFromMember() throws EndpointNotExistException, EndpointNotSupportedException, InviteConferenceException, OutOfPortsException {
        Invite invite = new Invite();

        int roomID = 1;
        invite.setFromRoomID(roomID);
        int tenantID = 2;
        int groupID = 3;

        Room room = new Room();
        room.setTenantID(tenantID);
        room.setGroupID(groupID);

        Group room_group = new Group();

        Member fromMember = new Member();

        int memberID = 1;
        invite.setFromMemberID(memberID);

        when(roomMock.getRoomDetailsForConference(roomID)).thenReturn(room);
        when(groupMock.getGroup(groupID)).thenReturn(room_group);
        when(memberMock.getMember(memberID)).thenReturn(null);

        conferenceService.inviteToConference(invite);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = InviteConferenceException.class)
    public void inviteToConferenceWithFromMemberNotEmptyAndRoomIDNotEqualsToFromRoomIDAndThrowNullPointerException() throws EndpointNotExistException, EndpointNotSupportedException, InviteConferenceException, OutOfPortsException {
        Invite invite = new Invite();

        int roomID = 1;
        int fromRoomID  = 4;
        int tenantID = 2;
        int groupID = 3;
        int fromMemberID = 5;
        String roomExtNumber = "6";

        invite.setFromRoomID(fromRoomID);
        invite.setFromMemberID(fromMemberID);

        Room room = new Room();
        room.setTenantID(tenantID);
        room.setGroupID(groupID);

        Group room_group = new Group();

        Member fromMember = new Member();
        fromMember.setRoomID(roomID);


        Room inviterRoom = new Room();

        inviterRoom.setRoomExtNumber(roomExtNumber);

        when(roomMock.getRoomDetailsForConference(fromRoomID)).thenReturn(room);
        when(groupMock.getGroup(groupID)).thenReturn(room_group);
        when(memberMock.getMember(fromMemberID)).thenReturn(fromMember);
        when(roomMock.getRoom(roomID)).thenReturn(inviterRoom);
        when(memberMock.getMemberNoRoom(fromMemberID)).thenThrow(new NullPointerException());

        conferenceService.inviteToConference(invite);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = InviteConferenceException.class)
    public void inviteToConferenceWithFromMemberNotEmptyAndRoomIDNotEqualsToFromRoomIDAndToMemberIdIsNotZero() throws EndpointNotExistException, EndpointNotSupportedException, InviteConferenceException, OutOfPortsException {
        Invite invite = new Invite();

        int roomID = 1;
        int fromRoomID  = 4;
        int tenantID = 2;
        int groupID = 3;
        int fromMemberID = 5;
        String extNumber = "6";
        int roomTypeID = 7;
        String roomName = "roomName";
        String roomType = "roomType";
        int toMemberID = 8;

        invite.setFromRoomID(fromRoomID);
        invite.setFromMemberID(fromMemberID);
        invite.setToMemberID(toMemberID);

        Room room = new Room();
        room.setTenantID(tenantID);
        room.setGroupID(groupID);

        Group room_group = new Group();

        Member fromMember = new Member();
        fromMember.setRoomID(roomID);

        Room inviteRoom = new Room();
        inviteRoom.setRoomExtNumber(extNumber);
        inviteRoom.setRoomID(roomID);
        inviteRoom.setRoomTypeID(roomTypeID);
        inviteRoom.setRoomName(roomName);
        inviteRoom.setRoomType(roomType);

        when(roomMock.getRoomDetailsForConference(fromRoomID)).thenReturn(room);
        when(groupMock.getGroup(groupID)).thenReturn(room_group);
        when(memberMock.getMember(fromMemberID)).thenReturn(fromMember);
        when(roomMock.getRoom(fromRoomID)).thenReturn(inviteRoom);
        when(memberMock.getMemberNoRoom(fromMemberID)).thenThrow(new NullPointerException());
        when(memberMock.getMember(toMemberID)).thenReturn(null);

        conferenceService.inviteToConference(invite);

    }

    /**
     *
     * boolean canTenantUseGatewayforAddress(List<Tenant> canCallToTenant, String address, int tenantID)
     */

    /**
     *
     * String getVirtualGUIDForLegacyDevice(Member fromMember, Member toMember, Object arg, int tenantID)
     */

    /**
     *
     * String getVirtualGUIDForJoinToLegacyForCancel(Member fromMember, Member toMember, int tenantID)
     */

    /**
     *
     * String getVirtualGUIDForJoinToLegacy(Member fromMember, Member toMember, int tenantID)
     */

    /**
     *
     * String cancelInviteToConference(Invite invite) throws EndpointNotExistException, CancelInvitationException
     */
    @Test(groups = {"conferenceService"},expectedExceptions = NullPointerException.class)
    public void cancelInviteToConferenceWithInvalidInput() throws EndpointNotExistException, CancelInvitationException {
        Invite invite = new Invite();

        when(roomMock.getRoom(0)).thenReturn(null);

        conferenceService.cancelInviteToConference(invite);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = CancelInvitationException.class)
    public void cancelInviteToConferenceWithEmptyFromMember() throws EndpointNotExistException, CancelInvitationException {
        Invite invite = new Invite();
        int fromRoomId = 1;
        int groupId = 2;
        int fromMemberId = 3;
        invite.setFromRoomID(fromRoomId);
        invite.setFromMemberID(fromMemberId);

        Room room = new Room();
        room.setGroupID(groupId);

        Group room_group = new Group();

        Member fromMember = new Member();

        when(roomMock.getRoom(fromRoomId)).thenReturn(room);
        when(groupMock.getGroup(groupId)).thenReturn(room_group);
        when(memberMock.getMember(fromMemberId)).thenReturn(null);


        conferenceService.cancelInviteToConference(invite);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = CancelInvitationException.class)
    public void cancelInviteToConferenceWithToMemberIDNot0AndToMemberIsEmpty() throws EndpointNotExistException, CancelInvitationException {
        Invite invite = new Invite();
        int fromRoomId = 1;
        int groupId = 2;
        int fromMemberId = 3;
        int toMemberId = 4;
        invite.setFromRoomID(fromRoomId);
        invite.setFromMemberID(fromMemberId);
        invite.setToMemberID(toMemberId);

        Room room = new Room();
        room.setGroupID(groupId);

        Group room_group = new Group();

        Member fromMember = new Member();

        when(roomMock.getRoom(fromRoomId)).thenReturn(room);
        when(groupMock.getGroup(groupId)).thenReturn(room_group);
        when(memberMock.getMember(fromMemberId)).thenReturn(fromMember);
        when(memberMock.getMember(toMemberId)).thenReturn(null);

        conferenceService.cancelInviteToConference(invite);
    }

    /**
     *
     * void startRing(int fromMemberID, int toRoomID) throws StartRingException
     */
    /*@Test(groups = {"conferenceService"},expectedExceptions = StartRingException.class)
    public void startRingWithValidInputAndFromMemberIsEmpty() throws StartRingException {
        int fromMemberID = 1;
        int toRoomID = 2;

        when(memberMock.getMember(fromMemberID)).thenReturn(null);

        conferenceService.startRing(fromMemberID,toRoomID);
    }*/

    /*@Test(groups = {"conferenceService"},expectedExceptions = StartRingException.class)
    public void startRingWithValidInputAndToMemberIsEmpty() throws StartRingException {
        int fromMemberID = 1;
        int toRoomID = 2;

        Member fromMember = new Member();

        Room fromRoom = new Room();
        Room toRoom = new Room();
        Member toMember = new Member();

        int fromRoomID = 3;

        fromMember.setRoomID(fromRoomID);
        toMember.setRoomID(toRoomID);

        when(memberMock.getMember(fromMemberID)).thenReturn(fromMember);
        when(roomMock.getRoom(toRoomID)).thenReturn(toRoom);
        when(roomMock.getRoom(fromMemberID)).thenReturn(fromRoom);
        when(memberMock.getMember(toRoomID)).thenReturn(null);

        conferenceService.startRing(fromMemberID,toRoomID);
    }*/

    /**
     *
     * void stopRing(int fromMemberID, int toRoomID) throws StopRingException
     */
    /*@Test(groups = {"conferenceService"},expectedExceptions = StopRingException.class)
    public void stopRingWithValidInputAndToMemberIsEmpty() throws StopRingException {
        int fromMemberID = 1;
        int toRoomID = 2;

        when(memberMock.getMember(fromMemberID)).thenReturn(null);

        conferenceService.stopRing(fromMemberID,toRoomID);
    }*/

    /*@Test(groups = {"conferenceService"},expectedExceptions = StopRingException.class)
    public void stopRingWithValidInputAndFromMemberIsNotEmpty() throws StopRingException {
        int fromMemberID = 1;
        int toRoomID = 2;

        Member fromMember = new Member();

        Room fromRoom = new Room();
        Room toRoom = new Room();
        Member toMember = new Member();

        int fromRoomID = 3;

        fromMember.setRoomID(fromRoomID);
        toMember.setRoomID(toRoomID);

        when(memberMock.getMember(fromMemberID)).thenReturn(fromMember);
        when(roomMock.getRoom(toRoomID)).thenReturn(toRoom);
        when(roomMock.getRoom(fromMemberID)).thenReturn(fromRoom);
        when(memberMock.getMember(toRoomID)).thenReturn(null);

        conferenceService.stopRing(fromMemberID,toRoomID);
    }*/

    /**
     *
     *  void twoPartyConference(int fromMemberID, int toRoomID)
     */
    @Test(groups = {"conferenceService"},expectedExceptions = MakeCallException.class)
    public void twoPartyConferenceWithValidInputAndFromMemberIsEmpty() throws EndpointNotExistException, OutOfPortsException, MakeCallException {
        int fromMemberID = 1;
        int toRoomID = 2;

        when(memberMock.getMember(fromMemberID)).thenReturn(null);

        conferenceService.twoPartyConference(fromMemberID,toRoomID);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = MakeCallException.class)
    public void twoPartyConferenceWithValidInputAndtoMemberIsEmpty() throws EndpointNotExistException, OutOfPortsException, MakeCallException {
        int fromMemberID = 1;
        int toRoomID = 2;

        Member fromMember = new Member();
        int fromRoomID = 3;
        int groupID = 4;
        fromMember.setRoomID(fromRoomID);

        Room fromRoom = new Room();
        fromRoom.setGroupID(groupID);
        Group room_group = new Group();

        when(memberMock.getMember(fromMemberID)).thenReturn(fromMember);
        when(roomMock.getRoom(fromRoomID)).thenReturn(fromRoom);
        when(groupMock.getGroup(groupID)).thenReturn(room_group);

        Room toRoom = new Room();
        int toMemberId = 5;

        toRoom.setMemberID(toMemberId);
        when(roomMock.getRoom(toRoomID)).thenReturn(toRoom);
        when(memberMock.getMember(toMemberId)).thenReturn(null);

        conferenceService.twoPartyConference(fromMemberID,toRoomID);
    }

    /**
     *
     * void twoPartyConference(String virtualEndpointGUID, String nameExt, String tenant, String displayName)
     throws MakeCallException, OutOfPortsException
     */

    /**
     *
     * void twoPartyConferenceFromLegacyInQueue(String virtualEndpointGUID)
     throws MakeCallException, OutOfPortsException
     */

    /**
     *
     * void twoPartyConferenceToLegacyInQueue(String virtualEndpointGUID)
     throws MakeCallException, OutOfPortsException, EndpointNotExistException
     */

    /**
     *
     * void twoPartyConference(VirtualEndpoint ve, Room toRoom) throws MakeCallException, OutOfPortsException
     */

    /**
     *
     * void twoPartyConference(Invite invite)
     throws EndpointNotExistException, MakeCallException, OutOfPortsException
     */

    /**
     *
     * void recordTheConference(int memberID, int roomID, String recorderPrefix, int webcast)
     throws OutOfPortsException, JoinConferenceException
     */

    /**
     *
     * List<Control> getControl(int roomID, ControlFilter filter)
     */
    @Test(groups = {"conferenceService"})
    public void getControlWithValidInput(){
        int roomID = 1;
        ControlFilter filter = new ControlFilter();

        Control c1 = new Control();
        Control c2 = new Control();
        Control c3 = new Control();

        List<Control> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);

        List<Control> fedlist = new ArrayList<>();
        fedlist.add(c3);

        list.addAll(fedlist);

        when(daoMock.getControls(roomID,filter)).thenReturn(list);
        when(daoMock.getFederationControls(roomID,filter)).thenReturn(fedlist);

        assertEquals(conferenceService.getControl(roomID,filter).get(2),list.get(2));
    }

    @Test(groups = {"conferenceService"}, expectedExceptions = DataAccessException.class)
    public void getControlWithInvalidInput(){
        int roomID = 0;
        ControlFilter filter = new ControlFilter();

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getControls(roomID,filter)).thenThrow(dae);

        conferenceService.getControl(roomID,filter);
    }

    /**
     *
     * Control getControlForMember(int memberID, ControlFilter filter)
     */
    @Test(groups = {"conferenceService"})
    public void getControlForMemberWithValidInput(){
        int memberID = 1;
        ControlFilter filter = new ControlFilter();

        Control list = new Control();
        when(daoMock.getControlForMember(memberID,filter)).thenReturn(list);

        assertEquals(conferenceService.getControlForMember(memberID,filter),list);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getConferenceForMemberWithInvalidInput(){
        int memberID = 0;
        ControlFilter filter = new ControlFilter();

        DataAccessException dae = new DataAccessException("dae"){};

        when(daoMock.getControlForMember(memberID,filter)).thenThrow(dae);

        conferenceService.getControlForMember(memberID,filter);
    }

    /**
     *
     * Long getCountControl(int roomID)
     */
    @Test(groups = {"conferenceService"})
    public void getCountControlWithValidInput(){
        int roomID = 1;

        Long number = new Long(5);

        when(daoMock.getCountControls(roomID)).thenReturn(number);

        assertEquals(conferenceService.getCountControl(roomID),number);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getCountControlWithInvalidInput(){
        int roomID = 1;

        DataAccessException dae = new DataAccessException("dae") {};
        when(daoMock.getCountControls(roomID)).thenThrow(dae);

        conferenceService.getCountControl(roomID);
    }

    /**
     *
     * void updateEndpointAudio
     */

    /**
     *
     * void updateEndpointAudioForAll(int roomID, boolean mute)
     */

    /**
     *
     * void muteAudio(String GUID) throws Exception
     */

    /**
     *
     * void unmuteAudio(String GUID) throws Exception
     */

    /**
     *
     *  void lectureModeInform(int roomID, String GUID)
     */

    /**
     *
     * void lectureModeInformPostJoin(ConferenceRecord conferenceRecord)
     */

    /**
     *
     * boolean guidIsPresenting(String GUID, String participantId)
     */

    /**
     *
     * void stopVideo(String GUID) throws Exception
     */

    /**
     *
     * void startVideo(String GUID) throws Exception
     */

    /**
     *
     * void startVideoIfStopped(String newPresenterGUID) throws Exception
     */

    /**
     *
     * void silenceAudio(String GUID) throws Exception
     */

    /**
     *
     * void silenceVideo(String GUID) throws Exception
     */

    /**
     *
     * void handleQueuedCallAsync(String GUID, int newStatus, long newSequenceNum, int tenantID)
     */

    /**
     *
     * void updateEndpointStatus(String GUID, String status, String source, ConferenceInfo conferenceInfo,
     long newSequenceNum, String updateReason, String participantId)
     */

    /**
     *
     * void notifyPresenterDisconnect(String endpointGUID)
     */

    /**
     *
     * void checkAndDeleteConference(String endpointGUID, String endpointType)
     */

    /**
     *
     * void roundRobinGateways(String GUID)
     */

    /**
     *
     * void roundRobinRecorders(String GUID)
     */

    /**
     *
     * int getEndpointStatus(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getEndpointStatusWithValidInput(){
        int endpointID = 1;

        String GUID = new String();
        int status = 1;

        when(daoMock.getGUIDForEndpointID(endpointID,"D")).thenReturn(GUID);
        when(daoMock.getEndpointStatus(GUID)).thenReturn(status);

        assertEquals(conferenceService.getEndpointStatus(endpointID),status);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getEndpointStatusWithInvalidInput(){
        int endpointID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getGUIDForEndpointID(endpointID,"D")).thenThrow(dae);

        conferenceService.getEndpointStatus(endpointID);
    }

    /**
     *
     * int getEndpointStatus(String GUID)
     */
    @Test(groups = {"conferenceService"})
    public void getEndpointStatusWithValidGUID(){
        String GUID = "GUID";

        int status = 1;

        when(daoMock.getEndpointStatus(GUID)).thenReturn(status);

        assertEquals(conferenceService.getEndpointStatus(GUID),status);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getEndpointStatusWithInvalidGUID(){
        String GUID = "GUID";

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getEndpointStatus(GUID)).thenThrow(dae);

        conferenceService.getEndpointStatus(GUID);
    }

    /**
     *
     * String getEndpointIPaddress(String GUID)
     */
    @Test(groups = {"conferenceService"})
    public void getEndpointIPaddressWithValiGUID(){
        String GUID = "GUID";

        when(daoMock.getEndpointIPaddress(GUID)).thenReturn(GUID);

        assertEquals(conferenceService.getEndpointIPaddress(GUID),"GUID");
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getEndpointIPaddressWithInvalidGUID(){
        String GUID = null;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getEndpointIPaddress(GUID)).thenThrow(dae);

        conferenceService.getEndpointIPaddress(GUID);
    }

    /**
     *
     * List<Control> getParticipants(int roomID, ControlFilter filter)
     */
    @Test(groups = {"conferenceService"})
    public void getParticipantsWithValidInput(){
        int roomID = 1;
        ControlFilter filter = new ControlFilter();

        List<Control> list = new ArrayList<>();
        Control c1 = new Control();
        Control c2 = new Control();

        list.add(c1);
        list.add(c2);

        when(daoMock.getParticipants(roomID,filter)).thenReturn(list);

        assertEquals(conferenceService.getParticipants(roomID,filter).get(1),c2);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getParticipantsWithInvalidInput(){
        int roomID = 1;
        ControlFilter filter = new ControlFilter();

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getParticipants(roomID,filter)).thenThrow(dae);

        conferenceService.getParticipants(roomID,filter);
    }

    /**
     *
     * List<Entity> getParticipants(int roomID, EntityFilter filter, User user)
     */
    @Test(groups = {"conferenceService"})
    public void getParticipantsWithValidRoomIDAndValidEntityFilterAndValidUser(){
        int roomID = 1;
        EntityFilter filter = new EntityFilter();
        User user = new User();

        List<Entity> list = new ArrayList<>();
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        list.add(e1);
        list.add(e2);


        when(daoMock.getParticipants(roomID,filter,user)).thenReturn(list);

        assertEquals(conferenceService.getParticipants(roomID,filter,user).get(1),e2);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getParticipantsWithInvalidRoomIDAndInvalidEntityFilterAndInvalidUser(){
        int roomID = 1;
        EntityFilter filter = new EntityFilter();
        User user = new User();

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getParticipants(roomID,filter,user)).thenThrow(dae);

        conferenceService.getParticipants(roomID,filter,user);
    }

    /**
     *
     * List<Entity> getLectureModeParticipants(int roomID, EntityFilter filter)
     */
    @Test(groups = {"conferenceService"})
    public void getLectureModeParticipantsWithValidInput(){
        int roomID = 1;
        EntityFilter filter = new EntityFilter();

        List<Entity> list = new ArrayList<>();
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        list.add(e1);
        list.add(e2);

        when(daoMock.getLectureModeParticipants(roomID,filter)).thenReturn(list);

        assertEquals(conferenceService.getLectureModeParticipants(roomID,filter).get(1),e2);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getLectureModeParticipantsWithInvalidInput(){
        int roomID = 1;
        EntityFilter filter = new EntityFilter();

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getLectureModeParticipants(roomID,filter)).thenThrow(dae);

        conferenceService.getLectureModeParticipants(roomID,filter);
    }

    /**
     *
     * Long getCountParticipants(int roomID)
     */
    @Test(groups = {"conferenceService"})
    public void getCountParticipantsWithValidRoomID(){
        int roomID = 1;

        Long number = new Long(5);
        when(daoMock.getCountParticipants(roomID)).thenReturn(number);

        assertEquals(conferenceService.getCountParticipants(roomID),number);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getCountParticipantsWithInvalidInput(){
        int roomID = 1;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getCountParticipants(roomID)).thenThrow(dae);

        conferenceService.getCountParticipants(roomID);
    }

    /**
     *
     * int getEndpointIDForMemberID(int memberID)
     */
    @Test(groups = {"conferenceService"})
    public void getEndpointIDForMemberIDWithValidInput(){
        int memberID = 1;

        int rc = 5;

        when(daoMock.getEndpointIDForMemberID(memberID)).thenReturn(rc);

        assertEquals(daoMock.getEndpointIDForMemberID(memberID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getEndpointIDForMemberIDWithInvalidInput(){
        int memberID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getEndpointIDForMemberID(memberID)).thenThrow(dae);

        conferenceService.getEndpointIDForMemberID(memberID);
    }

    /**
     *
     * int getMemberIDForEndpointID(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getMemberIDForEndpointIDWithValidID(){
        int endpointID = 1;
        int rc = 1;

        when(daoMock.getMemberIDForEndpointID(endpointID)).thenReturn(rc);

        assertEquals(conferenceService.getMemberIDForEndpointID(endpointID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getMemberIDForEndpointIDWithInvalidID(){
        int endpointID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getMemberIDForEndpointID(endpointID)).thenThrow(dae);

        conferenceService.getMemberIDForEndpointID(endpointID);
    }

    /**
     *
     * String getMemberTypeForEndpointID(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getMemberTypeForEndpointIDWithValidInput(){
        int endpointID = 1;
        String rc = "rc";

        when(daoMock.getMemberTypeForEndpointID(endpointID)).thenReturn(rc);

        assertEquals(conferenceService.getMemberTypeForEndpointID(endpointID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getMemberTypeForEndpointIDWithInvalidID(){
        int endpoint = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getMemberTypeForEndpointID(endpoint)).thenThrow(dae);

        conferenceService.getMemberTypeForEndpointID(endpoint);
    }

    /**
     *
     * int getEndpointIDForGUID(String GUID, String endpointType)
     */
    @Test(groups = {"conferenceService"})
    public void getEndpointIDForGUIDWithValidInput(){
        String GUID = "GUID";
        String endpointType = "endpointType";

        int rc = 1;

        when(daoMock.getEndpointIDForGUID(GUID,endpointType)).thenReturn(rc);

        assertEquals(conferenceService.getEndpointIDForGUID(GUID,endpointType),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getEndpointIDForGUIDWithInvalidInput(){
        String GUID = "";
        String endpointType = "";

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getEndpointIDForGUID(GUID,endpointType)).thenThrow(dae);

        conferenceService.getEndpointIDForGUID(GUID,endpointType);
    }

    /**
     *
     * String getGUIDForEndpointID(int endpointID, String endpointType)
     */
    @Test(groups = {"conferenceService"})
    public void getGUIDForEndpointIDWithValidInput(){
        int endpointID = 1;
        String endpointType = "endpointType";

        String rc = "rc";

        when(daoMock.getGUIDForEndpointID(endpointID,endpointType)).thenReturn(rc);

        assertEquals(conferenceService.getGUIDForEndpointID(endpointID,endpointType),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getGUIDForEndpointIDWithInvalidInput(){
        int endpointID = 0;
        String endpointType = "";

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getGUIDForEndpointID(endpointID,endpointType)).thenThrow(dae);

        conferenceService.getGUIDForEndpointID(endpointID,endpointType);
    }

    /**
     *
     * int getRoomIDForEndpointID(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getRoomIDForEndpointIDWithValidInput(){
        int endpointID = 1;

        int rc = 1;

        when(daoMock.getRoomIDForEndpointID(endpointID)).thenReturn(rc);

        assertEquals(conferenceService.getRoomIDForEndpointID(endpointID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getRoomIDForEndpointIDWithInvalidInput(){
        int endpointID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getRoomIDForEndpointID(endpointID)).thenThrow(dae);

        conferenceService.getRoomIDForEndpointID(endpointID);
    }

    /**
     *
     * boolean isEndpointIDinRoomID(int endpointID, int roomID)
     */
    @Test(groups = {"conferenceService"})
    public void isEndpointIDinRoomIDWithValidInput(){
        int endpointID = 1;
        int roomID = 1;

        boolean rc = true;

        when(daoMock.isEndpointIDinRoomID(endpointID,roomID)).thenReturn(rc);

        assertEquals(conferenceService.isEndpointIDinRoomID(endpointID,roomID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void isEndpointIDinRoomIDWithInvalidInput() {
        int endpointID = 0;
        int roomID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.isEndpointIDinRoomID(endpointID,roomID)).thenThrow(dae);

        conferenceService.isEndpointIDinRoomID(endpointID,roomID);
    }

    /**
     *
     * String getGUIDForEndpointIdInConf(int endpointID, int roomID)
     */
    @Test(groups = {"conferenceService"})
    public void getGUIDForEndpointIdInConfWithValidInput(){
        int endpointID = 1;
        int roomID = 1;

        String guid = "guid";

        when(daoMock.getGUIDForEndpointIdInConf(endpointID,roomID)).thenReturn(guid);

        assertEquals(conferenceService.getGUIDForEndpointIdInConf(endpointID,roomID),guid);
    }

    @Test(groups = {"conferenceService"})
    public void getGUIDForEndpointIdInConfWithInvalidInput(){
        int endpoint = 0;
        int roomID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getGUIDForEndpointIdInConf(endpoint,roomID)).thenThrow(dae);

        assertEquals(conferenceService.getGUIDForEndpointIdInConf(endpoint,roomID),null);
    }

    /**
     *
     * void bindGuestToEndpoint(int guestID, String GUID)
     */

    /**
     *
     * void unbindUserFromEndpoint(String GUID, UserUnbindCode reasonCode)
     */

    /**
     *
     * void sendMessageToEndpoint(String endpointGUID, DataHandler content) throws Exception
     */

    /**
     *
     * void asyncSendMessageToEndpoint(String endpointGUID, DataHandler content) throws Exception
     */

    /**
     *
     * void getAffinities()
     */

    /**
     *
     * VirtualEndpoint getVirtualEndpointForEndpointID(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getVirtualEndpointForEndpointIDWithValidInput(){
        int endpointID  = 1;
        VirtualEndpoint rc = new VirtualEndpoint();

        when(daoMock.getVirtualEndpointForEndpointID(endpointID)).thenReturn(rc);

        assertEquals(conferenceService.getVirtualEndpointForEndpointID(endpointID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getVirtualEndpointForEndpointIDWithInvalidInput(){
        int endpointID  = 1;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getVirtualEndpointForEndpointID(endpointID)).thenThrow(dae);

        conferenceService.getVirtualEndpointForEndpointID(endpointID);
    }

    /**
     *
     * RecorderEndpoint getRecorderEndpointForEndpointID(int endpointID)
     */
    @Test(groups = {"conferenceService"})
    public void getRecorderEndpointForEndpointIDWithValidInput(){
        int endpointID = 1;

        RecorderEndpoint rc = new RecorderEndpoint();

        when(daoMock.getRecorderEndpointForEndpointID(endpointID)).thenReturn(rc);

        assertEquals(conferenceService.getRecorderEndpointForEndpointID(endpointID),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getRecorderEndpointForEndpointIDWithInvalidInput(){
        int endpointID = 0;

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getRecorderEndpointForEndpointID(endpointID)).thenThrow(dae);

        conferenceService.getRecorderEndpointForEndpointID(endpointID);
    }

    /**
     *
     * void checkBindUserChallengeResponse(String endpointGUID, String challengeResponse, long bindUserResponseID)
     */

    /**
     *
     * String getRoomNameForGUID(String guid)
     */
    @Test(groups = {"conferenceService"})
    public void getRoomNameForGUIDWithValidInputAndRoomNameNotNull(){
        String guid = "guid";

        String roomName = "roomName";

        when(daoMock.getRoomNameForGUID(guid)).thenReturn(roomName);

        assertEquals(conferenceService.getRoomNameForGUID(guid),roomName);
    }

    @Test(groups = {"conferenceService"})
    public void getRoomNameForGUIDWithValidInputAndRoomNameIsNull(){
        String guid = "guid";

        when(daoMock.getRoomNameForGUID(guid)).thenReturn(null);

        assertEquals(conferenceService.getRoomNameForGUID(guid),null);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getRoomNameForGUIDWithInvalidInput(){
        String guid = "";

        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.getRoomNameForGUID(guid)).thenThrow(dae);

        conferenceService.getRoomNameForGUID(guid);

    }

    /**
     *
     * void checkIfEndpointIsSupported(Room room, String guid) throws EndpointNotSupportedException
     */
//    @Test(groups = {"conferenceService"},expectedExceptions = LectureModeNotSupportedException.class)
//    public void checkIfEndpointSupportedWithValidInputAndThrowLectureModeNotSupportedException() throws EndpointNotSupportedException {
//        Room room = new Room();
//        String guid = "guid";
//
//        int tenantID = 2;
//        room.setTenantID(2);
//        room.setLectureMode(1);
//
//        TenantConfiguration tenantConfiguration = new TenantConfiguration();
//        when(tenantMock.getTenantConfiguration(tenantID)).thenReturn(tenantConfiguration);
//
//        EndpointFeatures endpointFeatures = new EndpointFeatures();
//        endpointFeatures.setLectureModeSupported(false);
//
//        when(endpointServiceMock.getEndpointFeaturesForGuid(guid)).thenReturn(endpointFeatures);
//
//        ((ConferenceServiceImpl)conferenceService).checkIfEndpointIsSupported(room,guid);
//
//    }

    /**
     *
     * void checkForAvailableLinesSystemLevel(String licenseVersion) throws OutOfPortsException
     */

    /**
     *
     * void checkRoleAndValidateFreePorts(Member fromMember, Member toMember) throws OutOfPortsException
     */

    /**
     *
     * boolean isRoomCapacityReached(Room roomToJoin)
     */

    /**
     *
     * String getLicenseVersion()
     */

    /**
     *
     * void disconnectMeFromConference(int memberID) throws LeaveConferenceException
     */

    /**
     *
     * void pauseRecording(String guid) throws Exception
     */

    /**
     *
     * void resumeRecording(String guid) throws Exception
     */

    /**
     *
     * void sendMessageToRecorder(MediaControlCommandType.Enum commandType, String guid)
     */

    /**
     *
     * boolean leaveTheConference(String GUID, CallCompletionCode callCompletionCode)
     throws LeaveConferenceException
     */

    /**
     *
     * boolean sendLeaveConfRequest(String GUID, String confName)
     throws NoVidyoManagerException, NotLicensedException, EndpointNotExistException,
     ConferenceNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * boolean isMemberPresentInRoom(int memberID, int roomID)
     */
    @Test(groups = {"conferenceService"})
    public void isMemberPresentInRoomWithValidInput(){
        int memberID = 1;
        int roomID = 1;

        when(daoMock.isMemberPresentInRoom(memberID,roomID)).thenReturn(true);

        assertEquals(conferenceService.isMemberPresentInRoom(memberID,roomID),true);
    }

    @Test(groups = {"conferenceService"})
    public void isMemberPresentInRoomWithInvalidInput(){
        int memberID = 1;
        int roomID = 1;

        when(daoMock.isMemberPresentInRoom(memberID,roomID)).thenReturn(false);

        assertEquals(conferenceService.isMemberPresentInRoom(memberID,roomID),false);
    }

    /**
     *
     * String inviteParticipantToConference(Room roomToJoin, Member inviter, Member invitee, String toEndpointType)
     throws OutOfPortsException, EndpointNotExistException, InviteConferenceException,
     EndpointNotSupportedException
     */

    /**
     *
     * String inviteParticipantToConferenceQueue(String virtualEndpointGUID) throws OutOfPortsException,
     EndpointNotExistException, InviteConferenceException, EndpointNotSupportedException
     */

    /**
     *
     * String inviteLegacyToConference(Room roomToJoin, Member inviter, Member invitee)
     throws InviteConferenceException, EndpointNotExistException, OutOfPortsException
     */

    /**
     *
     * void sendGwServiceEndpointMessageIfAny()
     */

    /**
     *
     * void addControlMeetingEndpoint(Room roomToJoin, String guid, String externalIP)
     throws InviteConferenceException
     */

    /**
     *
     * void addSpontaneousEndpointToConference(String confName, String endpointGUID, String externalIP)
     throws NoVidyoManagerException, ConferenceNotExistException
     */

    /**
     *
     * String createConference(String confName, int maxUsers) throws NoVidyoManagerException,
     ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException
     */

    /**
     *
     * static String createConferenceUniqueCallID()
     */

    /**
     *
     * boolean inviteEndpointToConference(String uniqueCallId, String confName, Room roomToJoin, Member inviter,
     Member invitee, String endpointType) throws NoVidyoManagerException, NotLicensedException,
     EndpointNotExistException, ConferenceNotExistException, ResourceNotAvailableException
     */

    /**
     *
     * void joinTheConference(Room roomToJoin, Member joiningMember)
     throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException
     */

    /**
     *
     * void addEndpointToConference(String uniqueCallId, String confName, Room roomToJoin, Member joiningMember,
     String endpointType) throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
     ResourceNotAvailableException, EndpointNotExistException
     */

    /**
     *
     * void postConferenceHandle(Room roomToJoin, Member joiningMember)
     */

    /**
     *
     * Long getCountParticipants(String conferenceName)
     */
    @Test(groups = {"conferenceService"})
    public void getCountParticipantsWithValidInput(){
        String conferenceName = "conferenceName";

        Long num = new Long(20);

        when(daoMock.getCountParticipants(conferenceName)).thenReturn(num);

        assertEquals(conferenceService.getCountParticipants(conferenceName),num);
    }

    @Test(groups = {"conferenceService"})
    public void getCountParticipantsWithInvalidConferenceName(){
        String conferenceName = null;

        Long num = new Long(0);

        when(daoMock.getCountParticipants(conferenceName)).thenReturn(num);

        assertEquals(conferenceService.getCountParticipants(conferenceName),num);
    }

    /**
     *
     * int removeExternalLink(ExternalLink externalLink) throws NoVidyoManagerException
     */
    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchRemoteException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(RemoteException.class);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchGeneralFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        com.vidyo.ws.manager.GeneralFaultException generalFaultException = new com.vidyo.ws.manager.GeneralFaultException();
        com.vidyo.ws.manager.GeneralFault msg = new com.vidyo.ws.manager.GeneralFault();
        generalFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(generalFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchInvalidArgumentException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        com.vidyo.ws.manager.InvalidArgumentFaultException invalidArgumentFaultException = new com.vidyo.ws.manager.InvalidArgumentFaultException();
        com.vidyo.ws.manager.InvalidArgumentFault msg = new com.vidyo.ws.manager.InvalidArgumentFault();
        invalidArgumentFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(invalidArgumentFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchNotLicensedFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        NotLicensedFaultException notLicensedFaultException = new NotLicensedFaultException();
        NotLicensedFault notLicensedFault = new NotLicensedFault();
        notLicensedFaultException.setFaultMessage(notLicensedFault);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(notLicensedFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchEndpointNotExistFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        EndpointNotExistFaultException endpointNotExistFaultException= new EndpointNotExistFaultException();
        EndpointNotExistFault endpointNotExistFault = new EndpointNotExistFault();
        endpointNotExistFaultException.setFaultMessage(endpointNotExistFault);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(endpointNotExistFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndCatchResourceNotAvailableFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        ResourceNotAvailableFaultException resourceNotAvailableFaultException = new ResourceNotAvailableFaultException();
        ResourceNotAvailableFault msg = new ResourceNotAvailableFault();
        resourceNotAvailableFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(resourceNotAvailableFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);
    }





    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchRemoteException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(RemoteException.class);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchGeneralFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        com.vidyo.ws.manager.GeneralFaultException generalFaultException = new com.vidyo.ws.manager.GeneralFaultException();
        com.vidyo.ws.manager.GeneralFault msg = new com.vidyo.ws.manager.GeneralFault();
        generalFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(generalFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }


    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchInvalidArgumentFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        com.vidyo.ws.manager.InvalidArgumentFaultException invalidArgumentFaultException = new com.vidyo.ws.manager.InvalidArgumentFaultException();
        com.vidyo.ws.manager.InvalidArgumentFault msg = new com.vidyo.ws.manager.InvalidArgumentFault();
        invalidArgumentFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(invalidArgumentFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchNotLicensedFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        NotLicensedFaultException notLicensedFaultException = new NotLicensedFaultException();
        NotLicensedFault notLicensedFault = new NotLicensedFault();
        notLicensedFaultException.setFaultMessage(notLicensedFault);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(notLicensedFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchEndpointNotExistFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        EndpointNotExistFaultException endpointNotExistFaultException = new EndpointNotExistFaultException();
        EndpointNotExistFault endpointNotExistFault = new EndpointNotExistFault();
        endpointNotExistFaultException.setFaultMessage(endpointNotExistFault);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(endpointNotExistFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }

    @Test(groups = {"conferenceService"},expectedExceptions = NoVidyoManagerException.class)
    public void removeExternalLinkWithValiInputAndMySystemIDEqualsFromSystemIDAndCatchResourceNotAvailableFaultException() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "mysystemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        ResourceNotAvailableFaultException resourceNotAvailableFaultException = new ResourceNotAvailableFaultException();
        ResourceNotAvailableFault msg = new ResourceNotAvailableFault();
        resourceNotAvailableFaultException.setFaultMessage(msg);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenThrow(resourceNotAvailableFaultException);

        ((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink);

    }

    @Test(groups = {"conferenceService"})
    public void removeExternalLinkWithValidInputAndRespStatusNotEqualsToStatusType() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        RemoveExternalLinkResponse resp = new RemoveExternalLinkResponse();
        com.vidyo.ws.manager.Status_type0 status_type0 = com.vidyo.ws.manager.Status_type0.OK;
        resp.setStatus(status_type0);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenReturn(removeExternalLinkResponseMock);

        when(removeExternalLinkResponseMock.getStatus()).thenReturn(statusType0Mock);
        when(statusType0Mock.getValue()).thenReturn("-1");

        assertEquals(((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink),-1);
    }

    @Test(groups = {"conferenceService"})
    public void removeExternalLinkWithValidInputAndRespStatusEqualsToStatusType() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        RemoveExternalLinkResponse resp = new RemoveExternalLinkResponse();
        com.vidyo.ws.manager.Status_type0 status_type0 = com.vidyo.ws.manager.Status_type0.OK;
        resp.setStatus(status_type0);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenReturn(resp);
        when(removeExternalLinkResponseMock.getStatus()).thenReturn(statusType0Mock);

        assertEquals(((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink),0);
    }

    @Test(groups = {"conferenceService"})
    public void removeExternalLinkWithValidInputAndRespIsNull() throws NoVidyoManagerException, RemoteException, com.vidyo.ws.manager.GeneralFaultException, com.vidyo.ws.manager.InvalidArgumentFaultException, NotLicensedFaultException, EndpointNotExistFaultException, ResourceNotAvailableFaultException {
        ExternalLink externalLink = new ExternalLink();
        externalLink.setFromSystemID("mysystemID");
        externalLink.setFromConferenceName("fromConferenceName");
        externalLink.setToConferenceName("toConferenceName");
        externalLink.setToSystemID("toSysemID");

        String mySystemID = "systemID";

        when(systemMock.getVidyoManagerID()).thenReturn(mySystemID);

        RemoveExternalLinkResponse resp = new RemoveExternalLinkResponse();
        com.vidyo.ws.manager.Status_type0 status_type0 = com.vidyo.ws.manager.Status_type0.OK;
        resp.setStatus(status_type0);

        when(systemMock.getVidyoManagerServiceStubWithAUTH()).thenReturn(vidyoManagerServiceStubMock);
        when(vidyoManagerServiceStubMock.removeExternalLink(any(RemoveExternalLinkRequest.class))).thenReturn(null);

        assertEquals(((ConferenceServiceImpl)conferenceService).removeExternalLink(externalLink),0);
    }
    /**
     *
     * ExternalLink getExternalLink(String toConfName)
     */
    @Test(groups = {"conferenceService"})
    public void getExternalLinkWithValidInput(){
        String toConfName = "toConfName";

        ExternalLink rc = new ExternalLink();
        when(federationDaoMock.getExternalLink(toConfName)).thenReturn(rc);

        assertEquals(((ConferenceServiceImpl)conferenceService).getExternalLink(toConfName),rc);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void getExternalLinkWithInvalidInput(){
        String toConfName = "toConfName";

        DataAccessException dae = new DataAccessException("dae") {};
        when(federationDaoMock.getExternalLink(toConfName)).thenThrow(dae);

        ((ConferenceServiceImpl)conferenceService).getExternalLink(toConfName);
    }

    /**
     *
     * int cleanGuestsAndEndpoints()
     */
    @Test(groups = {"conferenceService"})
    public void cleanGuestsAndEndpointsWithValidInput(){
        when(daoMock.cleanGuestsAndEndpoints()).thenReturn(5);

        assertEquals(conferenceService.cleanGuestsAndEndpoints(),5);
    }

    @Test(groups = {"conferenceService"},expectedExceptions = DataAccessException.class)
    public void cleanGuestsAndEndpointsWithInvalidInput(){
        DataAccessException dae = new DataAccessException("dae") {};

        when(daoMock.cleanGuestsAndEndpoints()).thenThrow(dae);

        (conferenceService).cleanGuestsAndEndpoints();
    }

    /**
     *
     * int cleanGuestsAndEndpoints()
     */

    /**
     *
     * void cleanConferenceForLinkedUser(String endpointGuid)
     */

    /**
     *
     * String getInfoForGateway(String fromExt, String toExt, String fromUsername)
     */

    /**
     *
     * DataHandler getPortalAppData(Room roomToJoin, Member inviter)
     */

    /**
     *
     * String generateConferenceName(Room room, String tenantUrl)
     */

    /**
     *
     * int recordCallDisconnectDetails(String tenantName, int tenantId, String endpointGUID,
     String uniqueCallId)
     */

    /**
     *
     * int addEndpointUnsupportedToTransactionHistory(Room room, String endpointGUID, String feature)
     */
    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithValidInputAndEndpointNotEmpty(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "D";
        String memberType = "G";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(guest);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithValidInputAndEndpointIsEmpty(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "D";
        String memberType = "G";

        room.setTenantID(tenantID);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(null);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(guest);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithValidInputWithEmptyGuest(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "D";
        String memberType = "G";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(null);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithValidInputWithMembertypeNotEqualsToGAndMembNotEmpty(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "D";
        String memberType = "NOTG";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(null);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithValidInputWithMembertypeNotEqualsToGAndMembIsEmpty(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "D";
        String memberType = "NOTG";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(null);
        when(memberMock.getMember(memberID)).thenReturn(null);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithEndpointTypeNotEqualsTOV(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "V";
        String memberType = "G";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(guest);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithEndpointTypeNotEqualsTOR(){
        Room room = new Room();
        String endpointGUID = "GUID";
        String feature = "feature";

        int memberID = 1;
        int tenantID = 2;
        String endpointType = "R";
        String memberType = "G";

        room.setTenantID(tenantID);

        Endpoint endpoint = new Endpoint();
        endpoint.setMemberID(memberID);
        endpoint.setEndpointType(endpointType);
        endpoint.setMemberType(memberType);

        when(endpointServiceMock.getEndpointDetail(endpointGUID)).thenReturn(endpoint);

        Guest guest = new Guest();

        Member member = new Member();

        when(guestServiceMock.getGuest(memberID,tenantID)).thenReturn(guest);
        when(memberMock.getMember(memberID)).thenReturn(member);
        when(transactionServiceMock.addTransactionHistoryWithUserLookup(any(TransactionHistory.class))).thenReturn(1);

        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),1);
    }

    @Test(groups = {"conferenceService"})
    public void addEndpointUnsupportedToTransactionHistoryWithInvalidInput(){
        Room room = new Room();
        String endpointGUID = "";
        String feature = "feature";


        assertEquals(conferenceService.addEndpointUnsupportedToTransactionHistory(room,endpointGUID,feature),0);
    }


    /**
     *
     * void disconnectEndpointFromConference(int endpointID, Room roomToDisconnect,
     CallCompletionCode callCompletionCode) throws LeaveConferenceException
     */
    @Test(groups = {"conferenceService"},expectedExceptions = LeaveConferenceException.class)
    public void disconnectEndpointFromConferenceWithValidInputAndGUIDIsEmpty() throws LeaveConferenceException {
        int endpointID = 1;
        Room roomToDisconnect = new Room();

        String endpointType = "D";
        when(daoMock.getGUIDForEndpointID(endpointID,endpointType)).thenReturn("");

        conferenceService.disconnectEndpointFromConference(endpointID,roomToDisconnect,null);
    }

    /**
     *
     * void disconnectEndpointFromConference(String GUID, Room roomToDisconnect,
     CallCompletionCode callCompletionCode) throws LeaveConferenceException
     */

    /**
     *
     * void disconnectAllConferencesForTenant(int tenantId) throws NoVidyoManagerException,
     ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException
     */

    /**
     *
     * void checkAndStopWaitingRoomIfNecessary(String joiningGUID)
     */

    /**
     *
     * LectureModeDao getLectureModeDao()
     */

    /**
     *
     * int clearStaleConferenceRecords()
     */
    @Test(groups = {"conferenceService"})
    public void clearStraleConferenceRecords(){
        when(daoMock.clearStaleConferenceRecords()).thenReturn(1);

        assertEquals(conferenceService.clearStaleConferenceRecords(),1);
    }

    /**
     *
     * void setComponentsService(ComponentsService componentsService)
     */

    /**
     *
     * boolean canControlConference(User user, Room room, String moderatorPIN)
     */

    /**
     *
     * void silenceSpeaker(List<String> guids, int flag, int roomId, boolean allEndpoints)
     */





















}
