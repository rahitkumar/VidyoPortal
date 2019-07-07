package com.vidyo.service;

import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.validateExtDataType;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.types.Duration;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.ConferenceInfo;
import com.vidyo.bo.ConferenceRecord;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.ExternalLink;
import com.vidyo.bo.Group;
import com.vidyo.bo.Guest;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.Invite;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.Room;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.UserUnbindCode;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.conference.Conference;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointMessage;
import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.bo.statusnotify.Alert;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.IConferenceDao;
import com.vidyo.db.IFederationDao;
import com.vidyo.db.endpoints.EndpointFeatures;
import com.vidyo.db.lecturemode.LectureModeDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.rest.clients.gateway.GatewayVcapClient;
import com.vidyo.roomProfile.ProfileDocument;
import com.vidyo.roomProfile.RoomProfileType;
import com.vidyo.service.bindendpoint.BindEndpointService;
import com.vidyo.service.cdrcollection.CdrCollectionService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.CancelInvitationException;
import com.vidyo.service.exceptions.CannotCallTenantInviteException;
import com.vidyo.service.exceptions.CannotCallTenantJoinException;
import com.vidyo.service.exceptions.CannotCallTenantP2PException;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.DeleteConferenceException;
import com.vidyo.service.exceptions.DisconnectAllException;
import com.vidyo.service.exceptions.EndpointNotExistException;
import com.vidyo.service.exceptions.EndpointNotSupportedException;
import com.vidyo.service.exceptions.InviteConferenceException;
import com.vidyo.service.exceptions.JoinConferenceException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.LectureModeNotSupportedException;
import com.vidyo.service.exceptions.MakeCallException;
import com.vidyo.service.exceptions.NoVidyoFederationException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NotLicensedException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.ResourceNotAvailableException;
import com.vidyo.service.exceptions.RoomFullJoinException;
import com.vidyo.service.guest.GuestService;
import com.vidyo.service.lecturemode.LectureModeServiceImpl;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.statusnotify.StatusNotificationService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.service.utils.AESEncryptDecryptUtil;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.vcap20.BindUserRequest;
import com.vidyo.vcap20.BirrateLimitSet;
import com.vidyo.vcap20.BitrateLimitType;
import com.vidyo.vcap20.DSCPValueSet;
import com.vidyo.vcap20.JoinToLegacyRequest;
import com.vidyo.vcap20.MediaChannelType;
import com.vidyo.vcap20.MediaControlCommandType;
import com.vidyo.vcap20.MediaControlRequest;
import com.vidyo.vcap20.MediaSourceType;
import com.vidyo.vcap20.MediaType;
import com.vidyo.vcap20.Message;
import com.vidyo.vcap20.RequestMessage;
import com.vidyo.vcap20.RootDocument;
import com.vidyo.vcap20.UnbindUserRequest;
import com.vidyo.vcap20.UserProfileType;
import com.vidyo.ws.federation.DropRemoteConferenceRequest;
import com.vidyo.ws.federation.DropRemoteConferenceResponse;
import com.vidyo.ws.federation.VidyoFederationServiceStub;
import com.vidyo.ws.manager.AddEndpointRequest;
import com.vidyo.ws.manager.AddEndpointResponse;
import com.vidyo.ws.manager.AddSpontaneousEndpointRequest;
import com.vidyo.ws.manager.AddSpontaneousEndpointResponse;
import com.vidyo.ws.manager.CancelInviteEndpointRequest;
import com.vidyo.ws.manager.CancelInviteEndpointResponse;
import com.vidyo.ws.manager.ClassOfConference_type0;
import com.vidyo.ws.manager.Clearance_type0;
import com.vidyo.ws.manager.ClientType_type0;
import com.vidyo.ws.manager.ConferenceID_type0;
import com.vidyo.ws.manager.ConferenceNotExistFaultException;
import com.vidyo.ws.manager.CreateConferenceRequest;
import com.vidyo.ws.manager.CreateConferenceResponse;
import com.vidyo.ws.manager.DeleteConferenceRequest;
import com.vidyo.ws.manager.DeleteConferenceResponse;
import com.vidyo.ws.manager.DisconnectAllRequest;
import com.vidyo.ws.manager.DisconnectAllResponse;
import com.vidyo.ws.manager.EndpointCallPref;
import com.vidyo.ws.manager.EndpointGUID_type0;
import com.vidyo.ws.manager.EndpointNotExistFaultException;
import com.vidyo.ws.manager.GeneralFaultException;
import com.vidyo.ws.manager.GetGroupsRequest;
import com.vidyo.ws.manager.GetGroupsResponse;
import com.vidyo.ws.manager.InfoForEndpointRequest;
import com.vidyo.ws.manager.InfoForEndpointResponse;
import com.vidyo.ws.manager.InvalidArgumentFaultException;
import com.vidyo.ws.manager.InviteEndpointRequest;
import com.vidyo.ws.manager.InviteEndpointResponse;
import com.vidyo.ws.manager.MakeCallRequest;
import com.vidyo.ws.manager.MakeCallResponse;
import com.vidyo.ws.manager.NEGroupType;
import com.vidyo.ws.manager.NotLicensedFaultException;
import com.vidyo.ws.manager.RemoveEndpointRequest;
import com.vidyo.ws.manager.RemoveEndpointResponse;
import com.vidyo.ws.manager.RemoveExternalLinkRequest;
import com.vidyo.ws.manager.RemoveExternalLinkResponse;
import com.vidyo.ws.manager.ResourceNotAvailableFaultException;
import com.vidyo.ws.manager.SpontaneousEndpointInfo;
import com.vidyo.ws.manager.Status_type0;
import com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

public class ConferenceServiceImpl implements IConferenceService {

    private static final ThreadLocal<EndpointMessage> gwServiceEndpointMessageHolder = new ThreadLocal<>();

    private final static String MEMBER_ROLE_EXECUTIVE = "Executive";
    private final static String MEMBER_ROLE_VIDYO_ROOM = "VidyoRoom";
    private final static String MEMBER_ROLE_LEGACY = "Legacy";
    private final static String MEMBER_ROLE_VIDEO_PANORAMA = "VidyoPanorama";

    private static List<String> excludedRolesv20_21 = new ArrayList<String>();
    static {
        excludedRolesv20_21.add(MEMBER_ROLE_EXECUTIVE);
        excludedRolesv20_21.add(MEMBER_ROLE_VIDYO_ROOM);
        excludedRolesv20_21.add(MEMBER_ROLE_LEGACY);
        excludedRolesv20_21.add(MEMBER_ROLE_VIDEO_PANORAMA);
    }
    private static List<String> excludedRolesv22 = new ArrayList<String>();
    static {
        excludedRolesv22.add(MEMBER_ROLE_EXECUTIVE);
    }

    /** Logger for this class and subclasses */
    protected static final Logger logger = LoggerFactory.getLogger(ConferenceServiceImpl.class.getName());

    protected IConferenceDao dao;
    protected IRoomService room;
    protected IMemberService member;
    protected IGroupService group;
    // TODO - To be deprecated after moving to new system service
    protected ISystemService system;
    protected IUserService user;
    protected ITenantService tenant;
    private LicensingService licensingService;
    private EndpointService endpointService;
    private IFederationDao federationDao;
    private CdrCollectionService cdrCollectionService;
    private BindEndpointService bindEndpointService;

    private StatusNotificationService statusNotificationService;

    private TransactionService transactionService;

    private GuestService guestService;

    private GatewayVcapClient gatewayVcapClient;
    private IServiceService servicesService;

    private LectureModeDao lectureModeDao;

    private ThreadPoolTaskExecutor gwQueuedCallsTaskExecutor;

    @Autowired
    private ComponentsService componentsService;

    public void setServicesService(IServiceService servicesService) {
        this.servicesService = servicesService;
    }

    public void setGatewayVcapClient(GatewayVcapClient gatewayVcapClient) {
        this.gatewayVcapClient = gatewayVcapClient;
    }

    public void setGuestService(GuestService guestService) {
        this.guestService = guestService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setCdrCollectionService(CdrCollectionService cdrCollectionService) {
        this.cdrCollectionService = cdrCollectionService;
    }

    public void setFederationDao(IFederationDao federationDao) {
        this.federationDao = federationDao;
    }

    public IFederationDao getFederationDao() {
        return federationDao;
    }

    /**
     * @param endpointService
     *            the endpointService to set
     */
    public void setEndpointService(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    public void setDao(IConferenceDao dao) {
        this.dao = dao;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setMember(IMemberService member) {
        this.member = member;
    }

    public void setGroup(IGroupService group) {
        this.group = group;
    }

    public void setSystem(ISystemService system) {
        this.system = system;
    }

    public void setUser(IUserService user) {
        this.user = user;
    }

    public void setTenant(ITenantService tenant) {
        this.tenant = tenant;
    }

    public void setLicensingService(LicensingService licensingService) {
        this.licensingService = licensingService;
    }

    public void setBindEndpointService(BindEndpointService bindEndpointService) {
        this.bindEndpointService = bindEndpointService;
    }

    /**
     * @param statusNotificationService
     *            the statusNotificationService to set
     */
    public void setStatusNotificationService(StatusNotificationService statusNotificationService) {
        this.statusNotificationService = statusNotificationService;
    }

    public void setLectureModeDao(LectureModeDao lectureModeDao) {
        this.lectureModeDao = lectureModeDao;
    }

    public void setGwQueuedCallsTaskExecutor(ThreadPoolTaskExecutor gwQueuedCallsTaskExecutor) {
        this.gwQueuedCallsTaskExecutor = gwQueuedCallsTaskExecutor;
    }

    public static int convertStatus(String status) {
        int int_status = 0;

        if (status.equalsIgnoreCase("Offline")) {
            int_status = 0;
        } else if (status.equalsIgnoreCase("Online")) {
            int_status = 1;
        } else if (status.equalsIgnoreCase("Busy")) {
            int_status = 2;
        } else if (status.equalsIgnoreCase("Ringing")) {
            int_status = 3;
        } else if (status.equalsIgnoreCase("RingAccepted")) {
            int_status = 4;
        } else if (status.equalsIgnoreCase("RingRejected")) {
            int_status = 5;
        } else if (status.equalsIgnoreCase("RingNoAnswer")) {
            int_status = 6;
        } else if (status.equalsIgnoreCase("Alerting")) {
            int_status = 7;
        } else if (status.equalsIgnoreCase("AlertCancelled")) {
            int_status = 8;
        } else if (status.equalsIgnoreCase("BusyInOwnRoom")) {
            int_status = 9;
        } else if (status.equalsIgnoreCase("RingFailed")) {
            int_status = 10;
        } else if (status.equalsIgnoreCase("JoinFailed")) {
            int_status = 11;
        } else if (status.equalsIgnoreCase("WaitJoinConfirm")) {
            int_status = 12;
        }

        return int_status;
    }

    /**
     *
     */
    @Override
    public String getVMConnectAddress() throws NoVidyoManagerException {
        String connectAddr = componentsService.getVidyoManagerConnectAddress();
        boolean tlsProxyEnabled = this.system.getTLSProxyConfiguration();
        if (tlsProxyEnabled) {
            connectAddr = connectAddr.concat("&TlsProxy=" + tlsProxyEnabled);
        }
        return connectAddr;
    }

    protected String createConferenceForFederation(String confName, Group room_group) throws NoVidyoManagerException,
            ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException {
        logger.debug("Entering createConferenceForFederation confName - {} ", confName);
        logger.debug("Name of conference - " + confName);

        // Do not send WS call to VM in case of conference exist in Portal DB
        Conference conference = this.dao.getUniqueIDofConference(confName);
        String uniqueCallID = null;
        if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
            // nothing from before, therefore need to create conference
        } else {
            return conference.getUniqueCallId();
        }

        // Create conference
        CreateConferenceRequest create_conf_req = new CreateConferenceRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        create_conf_req.setConferenceID(confID);

        // Max Users limit received from the host portal response
        create_conf_req.setNumParticipants(new BigInteger(String.valueOf(room_group.getRoomMaxUsers())));
        create_conf_req.setClassOfConference(ClassOfConference_type0.VGA);

        Duration one_hour_period = new Duration();
        one_hour_period.parseTime("1H0M0S");
        create_conf_req.setDuration(one_hour_period);

        /*
         * if (room_group.getRouterActive() == 1) { if
         * (!room_group.getRouterName().equalsIgnoreCase("Default")) {
         * create_conf_req.addGroupId(room_group.getRouterName()); } } if
         * (room_group.getSecondaryRouterActive() == 1) { if
         * (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
         * create_conf_req.addGroupId(room_group.getSecondaryRouterName()); } }
         */
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        try {
            CreateConferenceResponse create_conf_resp = stub.createConference(create_conf_req);

            if (create_conf_resp != null && create_conf_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // success
                uniqueCallID = createConferenceUniqueCallID();
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (InvalidArgumentFaultException e) {
            try { // hack for VM - if cannot create a conference for given
                    // parameters - delete it!!!
                this.deleteConference(confName);
            } catch (Exception ignored) {
            }
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
        return uniqueCallID;
    }

    private void addEndpointToConference(String uniqueCallID, Room room, Member member, String endpointType)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        // Add Endpoint to conference
        AddEndpointRequest add_ep_req = new AddEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        add_ep_req.setConferenceID(confID);
        add_ep_req.setDeleteConference(false);

        EndpointCallPref endpoint = new EndpointCallPref();

        endpoint.setUser(member.getMemberName());

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(member.getEndpointGUID());
        endpoint.setEndpointGUID(guid);

        // Handle for Virtual and Recorder Endpoints
        if (member.getEndpointId() <= 0) {
            int endpointId = dao.getEndpointIDForGUID(member.getEndpointGUID(), endpointType);
            member.setEndpointId(endpointId);
        }

        endpoint = populateEndpointCallPref(endpoint, room, member, endpointType);

        // Bandwidth is a minimum between room's group and member's group

        endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        endpoint.setMaxUplinkBw(BigInteger.ZERO);

        // We dont use GroupId, let it be default
        endpoint.addGroupId("Default");

        if ("D".equalsIgnoreCase(endpointType)) {
            endpoint.setApplicationData(getPortalAppData(room, null));
        }
        add_ep_req.setEndpoint(endpoint);

        try {
            logger.info("endpoint online, inviting: " + member.getEndpointGUID());

            // Hold conference record untill status of endpoint will be "Busy"
            this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "C", room, member.getEndpointGUID(),
                    endpointType, "Y");

            AddEndpointResponse add_user_resp = stub.addEndpoint(add_ep_req);

            if (add_user_resp != null && add_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {

            }

        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (ConferenceNotExistFaultException e) {
            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private EndpointCallPref populateEndpointCallPref(EndpointCallPref endpoint, Room roomToJoin, Member joiningMember,
            String endpointType) {

        if (joiningMember.getRoleName() != null) {
            if (joiningMember.getRoleName().equalsIgnoreCase("Guest")) {
                endpoint.setClientType(ClientType_type0.Guest);
            } else if (joiningMember.getRoleName().equalsIgnoreCase("Recorder")) {
                endpoint.setClientType(ClientType_type0.Recorder);
            } else if (joiningMember.getRoleName().equalsIgnoreCase("Legacy")) {
                endpoint.setClientType(ClientType_type0.Legacy);
            } else {
                endpoint.setClientType(ClientType_type0.None);
            }

        } else {
            endpoint.setClientType(ClientType_type0.None);
        }

        if (roomToJoin.getMemberID() == joiningMember.getMemberID()) {
            endpoint.setClearance(Clearance_type0.Owner);
        } else if (joiningMember.getRoleName() != null && joiningMember.getRoleName().equalsIgnoreCase("admin")) {
            endpoint.setClearance(Clearance_type0.Admin);
        } else if (joiningMember.getRoleName() != null && joiningMember.getRoleName().equalsIgnoreCase("operator")) {
            endpoint.setClearance(Clearance_type0.Operator);
        } else {
            endpoint.setClearance(Clearance_type0.None);
        }

        if (joiningMember.getEndpointId() <= 0) {
            int endpointId = dao.getEndpointIDForGUID(joiningMember.getEndpointGUID(), endpointType);
            joiningMember.setEndpointId(endpointId);
        }
        // EntityId is the personal room id of the Member
        // Format - SID:participantId:entityId
        endpoint.setUserId("SID:" + String.valueOf(joiningMember.getEndpointId()) + ":" + joiningMember.getRoomID());
        return endpoint;
    }

    protected void addEndpointToConferenceForFederation(String confName, Group room_group, Member member,
            String endpointType) throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {
        logger.debug(
                "Entering addEndpointToConferenceForFederation confName - {}, member - {}, GUID - {}, endpointType - {}",
                confName, member.getMemberName(), member.getEndpointGUID(), endpointType);
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        logger.debug("Name of conference - " + confName);

        // Add Endpoint to conference
        AddEndpointRequest add_ep_req = new AddEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        add_ep_req.setConferenceID(confID);
        add_ep_req.setDeleteConference(false);

        EndpointCallPref endpoint = new EndpointCallPref();

        endpoint.setUser(member.getMemberName());

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(member.getEndpointGUID());
        endpoint.setEndpointGUID(guid);

        // Bandwidth is a minimum between room's group and member's group
        endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        if ("D".equalsIgnoreCase(endpointType)) {
            endpoint.setApplicationData(getPortalAppData(null, null));
        }
        add_ep_req.setEndpoint(endpoint);

        try {
            AddEndpointResponse add_user_resp = stub.addEndpoint(add_ep_req);

            if (add_user_resp != null && add_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Hold conference record untill status of endpoint will be
                // "Busy"
                Room room = new Room();
                room.setRoomID(0);
                room.setTenantID(0);
                Conference conference = this.dao.getUniqueIDofConference(confName);
                String uniqueCallID = null;
                if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
                    uniqueCallID = createConferenceUniqueCallID();
                } else {
                    uniqueCallID = conference.getUniqueCallId();
                }
                this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "F", room, member.getEndpointGUID(),
                        endpointType, "Y");
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (ConferenceNotExistFaultException e) {
            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private void inviteRecorderToConference(Room room, Group room_group, Member member, String recorderGUID,
            String recorderName, int webcast) throws NoVidyoManagerException, ConferenceNotExistException,
            NotLicensedException, EndpointNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        InviteEndpointRequest invite_endpoint_req = new InviteEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        // conference room is fromRoomID
        invite_endpoint_req.setConferenceID(confID);

        // Set FromUser Endpoint (who is sent invitation)
        EndpointCallPref from_endpoint = new EndpointCallPref();
        from_endpoint.setUser(member.getMemberName());

        String fromEndpointGUID = member.getEndpointGUID();
        if (fromEndpointGUID == null) {
            fromEndpointGUID = "0"; // invite from admin
        }
        EndpointGUID_type0 from_guid = new EndpointGUID_type0();
        from_guid.setEndpointGUID_type0(fromEndpointGUID);
        from_endpoint.setEndpointGUID(from_guid);

        // Bandwidth is a minimum between room's group and member's group
        from_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        from_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        invite_endpoint_req.setFromUser(from_endpoint);

        // Set Second Endpoint (who will received invitation)
        EndpointCallPref to_endpoint = new EndpointCallPref();
        to_endpoint.setUser(recorderName);

        EndpointGUID_type0 to_guid = new EndpointGUID_type0();
        to_guid.setEndpointGUID_type0(recorderGUID);
        to_endpoint.setEndpointGUID(to_guid);

        // Bandwidth is equals to FromUser
        to_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        to_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        // TODO VERY IMPORTANT - CONT CREATE XML BY STRING MANIPULATION.
        // TODO - this logic has to be changed later - parsing the conference
        // name to arrive at room name - scheduled
        // room name fix for recorder
        String roomName = room.getRoomName();
        if (room.getRoomType().equalsIgnoreCase("Scheduled") && confName.contains("@")) {
            roomName = confName.substring(0, confName.indexOf("@"));
        }

        // Pass info for Recorder in ApplicationData
        StringBuffer infoForReplay = new StringBuffer();
        infoForReplay.append("<info>");
        infoForReplay.append("<tenantName>").append(member.getTenantName()).append("</tenantName>");
        infoForReplay.append("<userName>").append(member.getUsername()).append("</userName>");
        infoForReplay.append("<displayName>").append(member.getMemberName()).append("</displayName>");
        infoForReplay.append("<roomName>").append(roomName).append("</roomName>");
        infoForReplay.append("<roomOwnerName>").append(room.getOwnerName()).append("</roomOwnerName>");
        infoForReplay.append("<roomOwnerDisplayName>").append(room.getOwnerDisplayName())
                .append("</roomOwnerDisplayName>");
        infoForReplay.append("<roomTenantName>").append(room.getTenantName()).append("</roomTenantName>");
        if (webcast == 1) {
            infoForReplay.append("<webcast>").append("true").append("</webcast>");
        } else {
            infoForReplay.append("<webcast>").append("false").append("</webcast>");
        }
        infoForReplay.append("</info>");

        DataHandler appData = new DataHandler(infoForReplay.toString(), "text/plain; charset=UTF-8");
        to_endpoint.setApplicationData(appData);

        // populate EndpointCallPref for recirder
        to_endpoint.setClientType(ClientType_type0.Recorder);
        to_endpoint.setClearance(Clearance_type0.None);
        int endpointId = dao.getEndpointIDForGUID(recorderGUID, "R");
        if (endpointId > 0) {
            // EntityId is the personal room id of the Member
            to_endpoint.setUserId("SID:" + String.valueOf(endpointId) + ":0");
        }

        invite_endpoint_req.setToEPInfo(to_endpoint);

        invite_endpoint_req.setAlertCaller(false); // ToDo set Alert Caller to
                                                    // true if ready in VD
        invite_endpoint_req.setRingLengthSeconds(new BigInteger("30"));

        try {
            // Hold conference record untill status of endpoint will be "Busy"
            String uniqueCallID = "";
            Conference conference = this.dao.getUniqueIDofConference(confName);
            if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
                logger.error("Not possible to invite a recorder to a conference that does not exist.");
            } else {
                uniqueCallID = conference.getUniqueCallId();
            }
            this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "C", room, recorderGUID, "R", "N");

            InviteEndpointResponse invite_endpoint_resp = stub.inviteEndpoint(invite_endpoint_req);

            if (invite_endpoint_resp != null && invite_endpoint_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Hold conference record untill status of endpoint will be
                // "Busy"
                // String uniqueCallID =
                // this.dao.getUniqueIDofConference(name_of_conference);
                // this.dao.addEndpointToConferenceRecord(uniqueCallID,
                // name_of_conference, "C", room, recorderGUID, "R", "N");
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            // clean records in Portal DB - ConferenceRecord
            this.dao.cleanupConferenceRecord(recorderGUID, confName);
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private void inviteEndpointToConference(String uniqueCallID, Room room, Group room_group, Member fromMember,
            Member toMember, String toEndpointType) throws NoVidyoManagerException, ConferenceNotExistException,
            NotLicensedException, EndpointNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        InviteEndpointRequest invite_endpoint_req = new InviteEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        // conference room is fromRoomID
        invite_endpoint_req.setConferenceID(confID);

        // Set FromUser Endpoint (who is sent invitation)
        EndpointCallPref from_endpoint = new EndpointCallPref();

        if (fromMember.getRoomTypeID() == 2) {
            from_endpoint.setUser(fromMember.getRoomName());
        } else {
            from_endpoint.setUser(fromMember.getMemberName());
        }

        String fromEndpointGUID = fromMember.getEndpointGUID();
        if (fromEndpointGUID == null) {
            fromEndpointGUID = "0"; // invite from admin
        }
        EndpointGUID_type0 from_guid = new EndpointGUID_type0();
        from_guid.setEndpointGUID_type0(fromEndpointGUID);
        from_endpoint.setEndpointGUID(from_guid);

        from_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        from_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        invite_endpoint_req.setFromUser(from_endpoint);

        // Set Second Endpoint (who will received invitation)
        EndpointCallPref to_endpoint = new EndpointCallPref();
        to_endpoint.setUser(toMember.getMemberName());

        EndpointGUID_type0 to_guid = new EndpointGUID_type0();
        to_guid.setEndpointGUID_type0(toMember.getEndpointGUID());
        to_endpoint.setEndpointGUID(to_guid);

        // Bandwidth is equals to FromUser
        to_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        to_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        if ("V".equalsIgnoreCase(toEndpointType)) {
            // Pass info for VidyoGateway in ApplicationData
            StringBuffer infoForGW = new StringBuffer();
            infoForGW.append("<info>");
            infoForGW.append("<toExtension>").append(toMember.getRoomExtNumber()).append("</toExtension>");
            infoForGW.append("<fromExtNumber>").append(fromMember.getRoomExtNumber()).append("</fromExtNumber>");
            if (fromMember.getRoomTypeID() == 2) {
                infoForGW.append("<fromUserName>").append(fromMember.getRoomName()).append("</fromUserName>");
            } else {
                infoForGW.append("<fromUserName>").append(fromMember.getMemberName()).append("</fromUserName>");
            }
            infoForGW.append("</info>");

            DataHandler appData = new DataHandler(infoForGW.toString(), "text/plain; charset=UTF-8");
            to_endpoint.setApplicationData(appData);
        } else if ("D".equalsIgnoreCase(toEndpointType)) {
            DataHandler appData = getPortalAppData(room, fromMember);
            to_endpoint.setApplicationData(appData);
        }
        ;

        to_endpoint = populateEndpointCallPref(to_endpoint, room, toMember, toEndpointType);

        invite_endpoint_req.setToEPInfo(to_endpoint);

        invite_endpoint_req.setAlertCaller(false); // ToDo set Alert Caller to
                                                    // true if ready in VD
        invite_endpoint_req.setRingLengthSeconds(new BigInteger("30"));

        try {
            // to avoid race condition - let's create a record before we send
            // command to VM
            this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "C", room, toMember.getEndpointGUID(),
                    toEndpointType, "N");

            logger.info("endpoint online, inviting: " + toMember.getEndpointGUID());
            InviteEndpointResponse invite_endpoint_resp = stub.inviteEndpoint(invite_endpoint_req);

            if (invite_endpoint_resp != null && invite_endpoint_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Hold conference record untill status of endpoint will be
                // "Busy"
                // this.dao.addEndpointToConferenceRecord(uniqueCallID,
                // name_of_conference, "C", room, toMember.getEndpointGUID(),
                // toEndpointType, "N");
            }

        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            // clean records in Portal DB - ConferenceRecord
            this.dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private void cancelInviteEndpointToConference(Room room, Group room_group, Member fromMember, Member toMember)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            EndpointNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        CancelInviteEndpointRequest cancel_invite_endpoint_req = new CancelInviteEndpointRequest();

        // conference room is fromRoomID
        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        cancel_invite_endpoint_req.setConferenceID(confID);

        // Set FromUser Endpoint (who is sent invitation)
        EndpointCallPref from_endpoint = new EndpointCallPref();
        from_endpoint.setUser(fromMember.getMemberName());

        EndpointGUID_type0 from_guid = new EndpointGUID_type0();
        from_guid.setEndpointGUID_type0(fromMember.getEndpointGUID());
        from_endpoint.setEndpointGUID(from_guid);

        from_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        from_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        cancel_invite_endpoint_req.setFromUser(from_endpoint);

        // Set Second Endpoint (who will received invitation)
        EndpointCallPref to_endpoint = new EndpointCallPref();
        to_endpoint.setUser(toMember.getMemberName());

        EndpointGUID_type0 to_guid = new EndpointGUID_type0();
        to_guid.setEndpointGUID_type0(toMember.getEndpointGUID());
        to_endpoint.setEndpointGUID(to_guid);

        // Bandwidth is equals to FromUser
        to_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        to_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        // Pass info for Legacy Device in ApplicationData
        DataHandler appData = new DataHandler(toMember.getRoomExtNumber(), "text/plain; charset=UTF-8");
        to_endpoint.setApplicationData(appData);

        cancel_invite_endpoint_req.setToEPInfo(to_endpoint);

        try {
            CancelInviteEndpointResponse cancel_invite_endpoint_resp = stub
                    .cancelInviteEndpoint(cancel_invite_endpoint_req);

            if (cancel_invite_endpoint_resp != null
                    && cancel_invite_endpoint_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Maybe need to do something
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private void removeEndpointFromConference(Room room, Member member, String endpointType)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        // Remove Endpoint from conference
        RemoveEndpointRequest remove_ep_req = new RemoveEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        remove_ep_req.setConferenceID(confID);

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(member.getEndpointGUID());
        remove_ep_req.setEndpointGUID(guid);

        try {
            RemoveEndpointResponse remove_user_resp = stub.removeEndpoint(remove_ep_req);

            if (remove_user_resp != null && remove_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Will remove in updateEndpointStatus after collect CDR
                // Delete record from Conferences table
                // this.dao.removeEndpointFromConference(member.getEndpointGUID(),
                // endpointType);
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    // copy of above, removing unused endpointType
    private void removeEndpointFromConference(Room room, Member member)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        // Remove Endpoint from conference
        RemoveEndpointRequest remove_ep_req = new RemoveEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        remove_ep_req.setConferenceID(confID);

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(member.getEndpointGUID());
        remove_ep_req.setEndpointGUID(guid);

        try {
            RemoveEndpointResponse remove_user_resp = stub.removeEndpoint(remove_ep_req);

            if (remove_user_resp != null && remove_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Will remove in updateEndpointStatus after collect CDR
                // Delete record from Conferences table
                // this.dao.removeEndpointFromConference(member.getEndpointGUID(),
                // endpointType);
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    protected void removeEndpointFromConferenceForFederation(String name_of_conference, Member member)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        logger.debug("Name of conference - " + name_of_conference);

        // Remove Endpoint from conference
        RemoveEndpointRequest remove_ep_req = new RemoveEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(name_of_conference);
        remove_ep_req.setConferenceID(confID);

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(member.getEndpointGUID());
        remove_ep_req.setEndpointGUID(guid);

        try {
            RemoveEndpointResponse remove_user_resp = stub.removeEndpoint(remove_ep_req);

            if (remove_user_resp != null && remove_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Delete record from Conferences table
                // Will remove in updateEndpointStatus after collect CDR
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("VM return response - '" + e.getMessage() + "'. Portal assumes that GUID-" + guid
                        + " not in conference (self disconnected)!");
            }
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    protected int deleteConference(String confName) throws NoVidyoManagerException, ConferenceNotExistException,
            NotLicensedException, ResourceNotAvailableException {
        // Status code - success {0}, failure {-1}
        int status = 0;
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        logger.debug("Name of conference to delete from VM - " + confName);

        // Delete the conference
        DeleteConferenceRequest delete_cf_req = new DeleteConferenceRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        delete_cf_req.setConferenceID(confID);

        try {
            DeleteConferenceResponse delete_cf_resp = stub.deleteConference(delete_cf_req);

            if (delete_cf_resp != null && delete_cf_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Maybe need to do something
                status = 0;
            } else {
                status = -1;
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
        return status;
    }

    private void disconnectAll(String confName) throws NoVidyoManagerException, ConferenceNotExistException,
            NotLicensedException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        logger.debug("Name of conference - " + confName);

        // Disconnect all from the conference
        DisconnectAllRequest disconnect_all_req = new DisconnectAllRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        disconnect_all_req.setConferenceID(confID);

        try {
            DisconnectAllResponse disconnect_all_resp = stub.disconnectAll(disconnect_all_req);

            if (disconnect_all_resp != null && disconnect_all_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Maybe need to do something
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    /*private void startRing(Room room, Member fromMember, Member toMember)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            EndpointNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        // Start ringing
        StartRingRequest start_ring_req = new StartRingRequest();

        // conference room is fromRoomID
        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        start_ring_req.setConferenceID(confID);

        // GUID is toEndpointID
        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(toMember.getEndpointGUID());
        start_ring_req.setEndpointGUID(guid);

        // Display name of fromUser
        start_ring_req.setFromUser(fromMember.getMemberName());

        // Pass info for Legacy Device in ApplicationData
        DataHandler appData = new DataHandler(toMember.getRoomExtNumber(), "text/plain; charset=UTF-8");
        start_ring_req.setApplicationData(appData);

        try {
            StartRingResponse start_ring_resp = stub.startRing(start_ring_req);

            if (start_ring_resp != null && start_ring_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Maybe need to do something
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }*/

    /*private void stopRing(Room room, Member fromMember, Member toMember)
            throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            EndpointNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        logger.debug("Name of conference - " + confName);

        // Start ringing
        StopRingRequest stop_ring_req = new StopRingRequest();

        // conference room is fromRoomID
        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        stop_ring_req.setConferenceID(confID);

        // GUID is toEndpointID
        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(toMember.getEndpointGUID());
        stop_ring_req.setEndpointGUID(guid);

        // Display name of fromUser
        stop_ring_req.setFromUser(fromMember.getMemberName());

        try {
            StopRingResponse stop_ring_resp = stub.stopRing(stop_ring_req);

            if (stop_ring_resp != null && stop_ring_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Maybe need to do something
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }*/

    private String createP2PConference(Room fromRoom, Group room_group, Room toRoom) throws NoVidyoManagerException,
            ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException {
        String uniqueCallID = "";
        String confName = fromRoom.getRoomName() + "@" + fromRoom.getTenantName() + "_" + toRoom.getRoomName() + "@"
                + toRoom.getTenantName();

        if (confName.length() > 200) {
            confName = confName.substring(0, 200);
        }

        // Do not send WS call to VM in case of conference exist in Portal DB
        Conference conference = this.dao.getUniqueIDofConference(confName);
        if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
            // nothing found
        } else {
            if (conference.isConferenceCreatedInVidyoManager()) {
                return conference.getUniqueCallId();
            }
        }

        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        logger.debug("Name of conference - " + confName);

        // Create conference
        CreateConferenceRequest create_conf_req = new CreateConferenceRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        create_conf_req.setConferenceID(confID);

        create_conf_req.setNumParticipants(new BigInteger(String.valueOf("2"))); // 2
                                                                                    // participants
        create_conf_req.setClassOfConference(ClassOfConference_type0.VGA);

        Duration one_hour_period = new Duration();
        one_hour_period.parseTime("1H0M0S");
        create_conf_req.setDuration(one_hour_period);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                create_conf_req.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                create_conf_req.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        try {
            CreateConferenceResponse create_conf_resp = stub.createConference(create_conf_req);

            if (create_conf_resp != null && create_conf_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // success
                if (conference != null && !conference.isConferenceCreatedInVidyoManager()) {
                    uniqueCallID = conference.getUniqueCallId();
                } else {
                    uniqueCallID = createConferenceUniqueCallID();
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }

        return uniqueCallID;
    }

    private void makeCall(String uniqueCallID, Room fromRoom, Group room_group, Room toRoom, Member fromMember,
            Member toMember, String fromEndpointType, String toEndpointType)
            throws NoVidyoManagerException, NotLicensedException, EndpointNotExistException,
            ConferenceNotExistException, ResourceNotAvailableException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        String confName = fromRoom.getRoomName() + "@" + fromRoom.getTenantName() + "_" + toRoom.getRoomName() + "@"
                + toRoom.getTenantName();

        logger.debug("Name of conference - " + confName);

        if (confName.length() > 200) {
            confName = confName.substring(0, 200);
        }

        MakeCallRequest make_call_req = new MakeCallRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        make_call_req.setConferenceID(confID);

        // Add First Endpoint to conference
        EndpointCallPref from_endpoint = new EndpointCallPref();
        from_endpoint.setUser(fromMember.getMemberName());

        EndpointGUID_type0 from_guid = new EndpointGUID_type0();
        from_guid.setEndpointGUID_type0(fromMember.getEndpointGUID());
        from_endpoint.setEndpointGUID(from_guid);

        // Bandwidth is a minimum between room's group and member's group
        from_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        from_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                from_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }

        from_endpoint = populateEndpointCallPref(from_endpoint, fromRoom, fromMember, fromEndpointType);

        make_call_req.setFromEPInfo(from_endpoint);

        // Add Second Endpoint to conference
        EndpointCallPref to_endpoint = new EndpointCallPref();
        to_endpoint.setUser(toMember.getMemberName());

        EndpointGUID_type0 to_guid = new EndpointGUID_type0();
        to_guid.setEndpointGUID_type0(toMember.getEndpointGUID());
        to_endpoint.setEndpointGUID(to_guid);

        // Bandwidth is a minimum between room's group and member's group
        to_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        to_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        if (room_group.getRouterActive() == 1) {
            if (!room_group.getRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getRouterName());
            }
        }
        if (room_group.getSecondaryRouterActive() == 1) {
            if (!room_group.getSecondaryRouterName().equalsIgnoreCase("Default")) {
                to_endpoint.addGroupId(room_group.getSecondaryRouterName());
            }
        }
        if ("V".equalsIgnoreCase(toEndpointType)) {
            // Pass info for VidyoGateway in ApplicationData
            StringBuffer infoForGW = new StringBuffer();
            infoForGW.append("<info>");
            infoForGW.append("<toExtension>").append(toMember.getRoomExtNumber()).append("</toExtension>");
            infoForGW.append("<fromExtNumber>").append(fromMember.getRoomExtNumber()).append("</fromExtNumber>");
            if (fromMember.getRoomTypeID() == 2) {
                infoForGW.append("<fromUserName>").append(fromMember.getRoomName()).append("</fromUserName>");
            } else {
                infoForGW.append("<fromUserName>").append(fromMember.getMemberName()).append("</fromUserName>");
            }
            infoForGW.append("</info>");

            DataHandler appData = new DataHandler(infoForGW.toString(), "text/plain; charset=UTF-8");
            to_endpoint.setApplicationData(appData);
        } else if ("D".equalsIgnoreCase(toEndpointType)) {
            to_endpoint.setApplicationData(getPortalAppData(null, fromMember));
        }

        to_endpoint = populateEndpointCallPref(to_endpoint, fromRoom, toMember, toEndpointType);

        make_call_req.setToEPInfo(to_endpoint);

        MakeCallResponse make_call_resp = null;
        try {
            // Hold conference record untill status of endpoint will be "Busy"\
            if ("V".equals(fromEndpointType)) { // from legacy to portal call
                this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "P", toRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "P", toRoom, toMember.getEndpointGUID(),
                        toEndpointType, "N");
            } else {
                this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "P", fromRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecord(uniqueCallID, confName, "P", fromRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            }

            logger.info("endpoints for p2p online, inviting from: " + fromMember.getEndpointGUID());
            logger.info("endpoints for p2p online, inviting to: " + toMember.getEndpointGUID());

            make_call_resp = stub.makeCall(make_call_req);

            if (make_call_resp != null && make_call_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // be aware to finish DB inserting before call "makeCall" - race
                // condition:
                // portal got updateStatus before DB insert work done
            }

        } catch (RemoteException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) { // VM return GUID in
                                                        // ErrorMessage !!!
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }

            // clean records for this conference in Portal DB
            this.dao.removeConferenceRecord(confName);

            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);
            throw new NoVidyoManagerException();
        } catch (DataAccessException dae) {
            logger.error(dae.getMessage());

            if (fromEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(fromMember.getEndpointGUID(), "", "", 0);
            }
            if (toEndpointType.equalsIgnoreCase("V")) {
                this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
            }
            // Error when doing make call, clean by endpoint guid & conf name
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), confName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), confName);

            // also clean up reverse attempt if any, as that would cause a
            // DataAccessException for insert
            String reverseConfName = toRoom.getRoomName() + "@" + toRoom.getTenantName() + "_" + fromRoom.getRoomName()
                    + "@" + fromRoom.getTenantName();
            if (reverseConfName.length() > 200) {
                reverseConfName = reverseConfName.substring(0, 200);
            }
            dao.cleanupConferenceRecord(fromMember.getEndpointGUID(), reverseConfName);
            dao.cleanupConferenceRecord(toMember.getEndpointGUID(), reverseConfName);

            // p2p conference, so no need to check for other participants before
            // deleting conf on VM
            try {
                this.deleteConference(confName);
            } catch (Exception e) {
                logger.error("Failed to delete existing conf on VM: " + confName);
            }
            try {
                this.deleteConference(reverseConfName);
            } catch (Exception e) {
                logger.error("Failed to delete possibly existing conf on VM: " + reverseConfName);
            }

            throw new ResourceNotAvailableException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    private boolean isEndpointReady(String type, String guid) {
        // virtual endpoints may not be online yet, so check
        if (this.servicesService.isUseNewGatewayServiceInterface() && "V".equals(type)) {
            return (this.getEndpointStatus(guid) == 1); // online
        }
        return true;
    }

    public void joinTheConference(Room roomToJoin, String virtualEndpointGUID, String displayName)
            throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException {
        // check free ports
        int tenantID = roomToJoin.getTenantID();
        int vgTenantID = this.tenant.getTenantIDforVirtualEndpoint(virtualEndpointGUID);
        List<Tenant> canCallToTenants = this.tenant.canCallToTenants(vgTenantID);
        boolean canCall = false;
        for (Tenant to_tenant : canCallToTenants) {
            if (tenantID == to_tenant.getTenantID()) {
                canCall = true;
                break;
            }
        }
        if (!canCall) {
            throw new CannotCallTenantJoinException();
        }

        // Call to get group is not needed as the room has the max users info
        // Group group = this.group.getGroup(roomToJoin.getGroupID());
        // Commenting out below update since the update is already done
        this.dao.updateVirtualEndpointInfo(virtualEndpointGUID, displayName, "", 0);

        // Convert VirtualEndpoint into fake Member object
        Member member = new Member();
        member.setEndpointGUID(virtualEndpointGUID);
        member.setMemberName(displayName);
        member.setRoleName("Legacy");
        member.setTenantID(roomToJoin.getTenantID());

        checkRoleAndValidateFreePorts(member);

        if (isRoomCapacityReached(roomToJoin)) {
            String errMsg = "Room capacity is reached.";
            logger.error(errMsg);
            throw new RoomFullJoinException(errMsg);
        }

        checkIfEndpointIsSupported(roomToJoin, virtualEndpointGUID);

        String uniqueCallID;

        String tenantUrl = this.tenant.getTenant(roomToJoin.getTenantName()).getTenantURL();
        String confName = generateConferenceName(roomToJoin, tenantUrl);

        String endpointType = "V";

        if (!this.isEndpointReady(endpointType, member.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallID = createConferenceUniqueCallID();
            logger.info("endpoint not online, queueing: " + member.getEndpointGUID());
            this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "C", roomToJoin,
                    member.getEndpointGUID(), endpointType, "Y");
        } else {

            // STEP 1 - create a conference
            try {

                uniqueCallID = createConference(confName, roomToJoin.getRoomMaxUsers());

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            }

            // STEP 2 - add endpoint to the conference
            try {

                addEndpointToConference(uniqueCallID, roomToJoin, member, endpointType);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException();
            } catch (EndpointNotExistException e) {
                logger.error(e.getMessage());
                // special case - VirtualEndpoints gone but Online in DB
                String vGUID = e.getMessage();
                updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
                throw new JoinConferenceException();
            }

            postConferenceHandle(roomToJoin, member);
        }

    }

    private void joinTheConferenceInQueue(String virtualEndpointGUID)
            throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException {

        logger.info(" handling queued CONF FROM LEGACY call: " + virtualEndpointGUID);
        VirtualEndpoint ve = this.endpointService.getVirtualEndpointForEndpointGUID(virtualEndpointGUID);
        String displayName = ve.getDisplayName();

        int roomId = this.dao.getRoomIDForEndpointGUIDConferenceRecord(virtualEndpointGUID);

        Room roomToJoin = this.room.getRoomDetailsForConference(roomId);

        joinTheConference(roomToJoin, virtualEndpointGUID, displayName);

    }

    public void leaveTheConference(int endpointID, int roomID, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException {
        Room room = this.room.getRoom(roomID);

        String endpointType = "D";
        String GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
        if (GUID == null || GUID.trim().equalsIgnoreCase("")) {
            endpointType = "V";
            GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
            if (GUID == null || GUID.trim().equalsIgnoreCase("")) {
                endpointType = "R";
                GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
            }
        }

        // Convert into fake Member object
        Member member = new Member();
        member.setEndpointGUID(GUID);
        String uniqueCallID = null;
        try {
            removeEndpointFromConference(room, member, endpointType);
            uniqueCallID = dao.getUniqueCallIDByGuid(GUID);
            cdrCollectionService.updateEndpointCallCompletionCode(uniqueCallID, GUID, callCompletionCode);
        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new LeaveConferenceException();
        }
        recordCallDisconnectDetails(room.getTenantName(), tenant.getTenant(room.getTenantName()).getTenantID(), GUID,
                uniqueCallID);
    }

    public void leaveTheConference(String GUID, int roomID, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException {
        Room room = this.room.getRoom(roomID);

        String endpointType = this.dao.getEndpointType(GUID);
        // Convert into fake Member object
        Member member = new Member();
        member.setEndpointGUID(GUID);
        String uniqueCallID = null;
        try {
            removeEndpointFromConference(room, member, endpointType);
            uniqueCallID = dao.getUniqueCallIDByGuid(GUID);
            cdrCollectionService.updateEndpointCallCompletionCode(uniqueCallID, GUID, callCompletionCode);
        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new LeaveConferenceException();
        }
        // Don't log the call disconnect for Recorders.
        if (!endpointType.equals("R")) {
            recordCallDisconnectDetails(room.getTenantName(), tenant.getTenant(room.getTenantName()).getTenantID(),
                    GUID, uniqueCallID);
        }
    }

    protected void checkAndDeleteTheConference(Control conference) throws DeleteConferenceException {

        logger.debug("Entering checkAndDeleteTheConference(). ConfName = " + conference.getConferenceName()
                + " ; roomID = " + conference.getRoomID());
        long count = conference.getNumOfParticipants();

        if (count == 1 && conference.getConferenceType().equalsIgnoreCase("F")) {
            logger.debug(
                    "checkAndDeleteTheConference(). count == 1 && conference.getConferenceType().equalsIgnoreCase(\"F\")");
            ExternalLink externalLink = getExternalLink(conference.getConferenceName());
            if (externalLink != null) {
                // Delete the table entries
                federationDao.deleteFederation(externalLink);
                try {
                    removeExternalLink(externalLink);
                } catch (NoVidyoManagerException e) {
                    logger.error(e.getMessage());
                }
                try {
                    // This happens only from Remote to Host
                    dropRemoteConf(externalLink);
                } catch (Exception e) {
                    logger.error("Exception while cleanup", e.getMessage());
                }
            }
            // Update the count to zero once done with clean up for deleting the
            // conference
            count = 0;

        }

        if (count == 0l) { // No Endpoints in conference
            logger.debug("checkAndDeleteTheConference(). count == 0l");
            // Step 1 - update room settings
            if (conference.getRoomID() != 0) {
                this.room.resetRoomState(conference.getRoomID());
            }
            // Step 2 - delete conference on VidyoRouter
            try {
                logger.debug("checkAndDeleteTheConference(). delete conference on VidyoRouter. confName = "
                        + conference.getConferenceName());
                deleteConference(conference.getConferenceName());

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new DeleteConferenceException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new DeleteConferenceException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new DeleteConferenceException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new DeleteConferenceException();
            }
        }

        logger.debug("Exiting checkAndDeleteTheConference(). ConfName = " + conference.getConferenceName()
                + " ; roomID = " + conference.getRoomID());
    }

    public void dropRemoteConf(ExternalLink externalLink) throws ConferenceNotExistException, RemoteException {
        VidyoFederationServiceStub stub = null;
        try {
            if (externalLink.getSecure() == null) {
                logger.error("Secure flag should not be null in ExternalLink -->" + externalLink);
            }
            stub = this.system.getVidyoFederationServiceStubWithAUTH(externalLink.getToTenantHost(),
                    externalLink.getSecure().equalsIgnoreCase("Y"));

            DropRemoteConferenceRequest req = new DropRemoteConferenceRequest();

            req.setFromSystemID(externalLink.getFromSystemID());
            req.setFromConferenceName(externalLink.getFromConferenceName());
            req.setToSystemID(externalLink.getToSystemID());
            req.setToConferenceName(externalLink.getToConferenceName());

            DropRemoteConferenceResponse resp = stub.dropRemoteConference(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {

                }
            }

        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new ConferenceNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new ConferenceNotExistException(e.getFaultMessage().getErrorMessage());
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void disconnectAll(Room roomToDisconnect) throws DisconnectAllException {
        Tenant tenant = this.tenant.getTenant(roomToDisconnect.getTenantID());

        String tenantURL = tenant.getTenantURL();

        this.room.resetRoomState(roomToDisconnect.getRoomID());

        String confName = generateConferenceName(roomToDisconnect, tenantURL);
        String uniqueCallID = null;
        try {
            disconnectAll(confName);
            Conference conference = this.dao.getUniqueIDofConference(confName);
            if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
                logger.error("No conference found to disconnectALl.");
            } else {
                uniqueCallID = conference.getUniqueCallId();
            }

            cdrCollectionService.updateConferenceCallCompletionCode(uniqueCallID, CallCompletionCode.BY_SOMEONE_ELSE);
        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new DisconnectAllException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new DisconnectAllException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new DisconnectAllException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new DisconnectAllException();
        }
        recordCallDisconnectDetails(tenant.getTenantName(), tenant.getTenantID(), null, uniqueCallID);
        // Delete conference to free up the stuck conference
        /*
         * int status = 0; try { status = deleteConference(confName); } catch
         * (NoVidyoManagerException e) { logger.warn(
         * "Delete Conference Failed - NoVidyoManagerException {}", confName); }
         * catch (ConferenceNotExistException e) { logger.warn(
         * "Delete Conference Failed - ConferenceNotExistException {}",
         * confName); } catch (NotLicensedException e) {
         * logger.warn("Delete Conference Failed - NotLicensedException {}",
         * confName); } catch (ResourceNotAvailableException e) { logger.warn(
         * "Delete Conference Failed - ResourceNotAvailableException {}",
         * confName); }
         *
         * if (status != 0) { logger.error(
         * "Error while deleting conference from VidyoManager after DisconnectAll for Conference {}"
         * , confName); }
         */
    }

    public String inviteToConference(Invite invite) throws OutOfPortsException, EndpointNotExistException,
            InviteConferenceException, EndpointNotSupportedException {
        Room room = this.room.getRoomDetailsForConference(invite.getFromRoomID());
        int tenantID = room.getTenantID();

        Group room_group = this.group.getGroup(room.getGroupID());
        Member fromMember = null;
        try {
            fromMember = this.member.getMember(invite.getFromMemberID());

            if (fromMember != null) {
                if (fromMember.getRoomID() != invite.getFromRoomID()) { // not
                                                                        // invited
                                                                        // from
                                                                        // member's
                                                                        // private
                                                                        // room
                                                                        // -
                                                                        // must
                                                                        // be
                                                                        // from
                                                                        // public
                                                                        // room
                                                                        // tied
                                                                        // to
                                                                        // member
                    Room inviteRoom = this.room.getRoom(invite.getFromRoomID());

                    fromMember.setRoomExtNumber(inviteRoom.getRoomExtNumber());
                    fromMember.setRoomID(inviteRoom.getRoomID());
                    fromMember.setRoomTypeID(inviteRoom.getRoomTypeID());
                    fromMember.setRoomName(inviteRoom.getRoomName());
                    fromMember.setRoomType(inviteRoom.getRoomType());
                }
            } else { // force to call member getMemberNoRoom()
                throw new Exception();
            }
        } catch (Exception e) {
            try {
                fromMember = this.member.getMemberNoRoom(invite.getFromMemberID());
            } catch (Exception ex) {
                fromMember = null;
            }
        }
        if (fromMember == null) {
            String errMsg = "inviteToConference failed. Incorrect fromMemberId = " + invite.getFromMemberID();
            logger.error(errMsg);
            throw new InviteConferenceException(errMsg);
        }

        Member toMember = new Member();
        int toMemberID = invite.getToMemberID();
        if (toMemberID == 0) { // just use what user type in input box
            toMember.setMemberName(invite.getSearch());
            toMember.setRoomExtNumber(invite.getSearch());
            toMember.setRoleName("Legacy");
            toMember.setTenantID(tenantID);
        } else {
            toMember = this.member.getMember(toMemberID);
            if (toMember == null) {
                String errMsg = "inviteToConference failed. Incorrect toMemberId = " + toMemberID;
                logger.error(errMsg);
                throw new InviteConferenceException(errMsg);
            }
        }

        checkRoleAndValidateFreePorts(toMember);

        if (isRoomCapacityReached(room)) {
            String errMsg = "Room capacity is reached.";
            logger.error(errMsg);
            throw new InviteConferenceException(errMsg);
        }
        
        //Set the call from identifier if present - for customization of SIP FROM header
        fromMember.setCallFromIdentifier(invite.getCallFromIdentifier());

        String toGUID;
        String toEndpointType;
        if (invite.getToEndpointID() == 0) { // Legacy Device
            toEndpointType = "V";

            // check if this tenant can use VidyoGateway for the prefix
            List<Tenant> canCallToTenants = this.tenant.canCallToTenants(tenantID);
            boolean can_call_flag = this.canTenantUseGatewayforAddress(canCallToTenants, toMember.getRoomExtNumber(),
                    tenantID);
            if (can_call_flag) {
                toGUID = this.getVirtualGUIDForLegacyDevice(fromMember, toMember, invite.getPayload(), tenantID);
            } else {
                throw new CannotCallTenantInviteException();
            }
        } else {
            toEndpointType = "D";
            toGUID = this.dao.getGUIDForEndpointID(invite.getToEndpointID(), toEndpointType);
        }

        if (toGUID.equalsIgnoreCase("")) {
            throw new EndpointNotExistException();
        }

        checkIfEndpointIsSupported(room, toGUID);

        toMember.setEndpointGUID(toGUID);

        String uniqueCallID;

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        if (!this.isEndpointReady(toEndpointType, toMember.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallID = createConferenceUniqueCallID();
            logger.info("endpoint not online, queueing: " + toMember.getEndpointGUID());
            this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "C", room, toMember.getEndpointGUID(),
                    toEndpointType, "N");
        } else {

            // STEP 1 - create a conference
            try {

                uniqueCallID = createConference(confName, room_group.getRoomMaxUsers());

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            }

            // STEP 2 - invite to a conference
            if("V".equalsIgnoreCase(toEndpointType) && StringUtils.isNotBlank(invite.getCallFromIdentifier())) {
            		//If legacy and callFromIdentifier is customized, set it in the fromMember
            		fromMember.setCallFromIdentifier(invite.getCallFromIdentifier());
            		fromMember.setRoomExtNumber(invite.getCallFromIdentifier());
            }
            
            try {

                inviteEndpointToConference(uniqueCallID, room, room_group, fromMember, toMember, toEndpointType);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());

                if (toEndpointType.equalsIgnoreCase("V")) {
                    this.dao.updateVirtualEndpointInfo(toMember.getEndpointGUID(), "", "", 0);
                }

                throw new InviteConferenceException();
            } catch (EndpointNotExistException e) {
                logger.error(e.getMessage());
                // special case - VirtualEndpoints gone but Online in DB
                String vGUID = e.getMessage();
                updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
                throw new InviteConferenceException();
            }

            postConferenceHandle(room, toMember);
        }

        sendGwServiceEndpointMessageIfAny();

        return toGUID;
    }

    private boolean canTenantUseGatewayforAddress(List<Tenant> canCallToTenant, String address, int tenantID) {
        if (this.servicesService.isUseNewGatewayServiceInterface()) {
            return null != this.dao.getGatewayPrefixForCall(address, tenantID);
        } else {
            return this.dao.canTenantUseGatewayforAddress(canCallToTenant, address, tenantID);
        }
    }

    private String getVirtualGUIDForLegacyDevice(Member fromMember, Member toMember, Object arg, int tenantID) {
        if (this.servicesService.isUseNewGatewayServiceInterface()) {
            if (arg != null && arg instanceof VirtualEndpoint) {
                VirtualEndpoint ve = (VirtualEndpoint) arg;
                return ve.getEndpointGUID();
            }
            return getVirtualGUIDForJoinToLegacy(fromMember, toMember, tenantID);
        } else {
            return this.dao.getVirtualGUIDForLegacyDevice(toMember, arg, tenantID);
        }
    }

    private String getVirtualGUIDForJoinToLegacyForCancel(Member fromMember, Member toMember, int tenantID) {

        GatewayPrefix gwPrefix = this.dao.getGatewayPrefixForCall(toMember.getRoomExtNumber(), tenantID);
        if (gwPrefix != null) {
            int gwServiceId = gwPrefix.getServiceID();

            String ringingGuid = this.dao.getVirtualGUIDForRingingCall(gwServiceId, toMember.getRoomExtNumber(),
                    tenantID, fromMember.getRoomID());
            if (!StringUtils.isBlank(ringingGuid)) {
                return ringingGuid;
            }
        }

        return "";
    }

    private String getVirtualGUIDForJoinToLegacy(Member fromMember, Member toMember, int tenantID) {
        String virtualEndpointGUID = "";

        // from GatewayPrefixes, determine which gateway to call, based on
        // prefix that matches
        GatewayPrefix gwPrefix = this.dao.getGatewayPrefixForCall(toMember.getRoomExtNumber(), tenantID);
        if (gwPrefix != null) {
            int gwServiceId = gwPrefix.getServiceID();

            // already calling use case: check if GUID ringing
            String ringingGuid = this.dao.getVirtualGUIDForRingingCall(gwServiceId, toMember.getRoomExtNumber(),
                    tenantID, fromMember.getRoomID());
            if (!StringUtils.isBlank(ringingGuid)) {
                return ringingGuid;
            }

            virtualEndpointGUID = this.gatewayVcapClient.getVirtualGUIDForJoinToLegacy(gwPrefix, tenantID, toMember);

            com.vidyo.bo.Service gwService = this.servicesService.getVG(gwServiceId);
            String serviceEndpointGuid = gwService.getServiceEndpointGuid();

            if (!StringUtils.isBlank(serviceEndpointGuid)) {

                // send vcap message
                RootDocument doc = RootDocument.Factory.newInstance();
                Message message = doc.addNewRoot();
                RequestMessage requestMessage = message.addNewRequest();
                long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
                requestMessage.setRequestID(requestID);

                Tenant tenantObj = tenant.getTenant(tenantID);

                JoinToLegacyRequest joinToLegacyRequest = requestMessage.addNewJoinToLegacy();
                joinToLegacyRequest.setToken(gwService.getToken());
                joinToLegacyRequest.setPrefix(gwPrefix.getPrefix());
                joinToLegacyRequest.setFromUserName(fromMember.getMemberName());
                if (tenantObj.getVidyoGatewayControllerDns() != null
                        && !tenantObj.getVidyoGatewayControllerDns().isEmpty()) {
                    joinToLegacyRequest.setFromExtNumber(fromMember.getRoomExtNumber() + "," + fromMember.getRoomName()
                            + "@" + tenantObj.getVidyoGatewayControllerDns());
                } else {
                    joinToLegacyRequest.setFromExtNumber(fromMember.getRoomExtNumber());
                }
                // Override the CallFromIdentifier if specified
                if(StringUtils.isNotBlank(fromMember.getCallFromIdentifier())) {
                		joinToLegacyRequest.setFromExtNumber(fromMember.getCallFromIdentifier());
                }
                joinToLegacyRequest.setToExtension(toMember.getRoomExtNumber());
                joinToLegacyRequest.setEndpointGuid(virtualEndpointGUID);

                XmlOptions opts = new XmlOptions();
                opts.setCharacterEncoding("UTF-8");

                String info = doc.xmlText(opts);

                DataHandler joinToLegacyContent = new DataHandler(info, "text/plain; charset=UTF-8");
                EndpointMessage endpointMessage = new EndpointMessage();
                endpointMessage.setServiceEndpointGUID(serviceEndpointGuid);
                endpointMessage.setJoinToLegacyContent(joinToLegacyContent);

                this.gwServiceEndpointMessageHolder.set(endpointMessage);
                logger.info("Saving VCAP message for later, VE: " + virtualEndpointGUID);

                return virtualEndpointGUID;
            } else {
                logger.error("No service endpointGuid found for gw serviceID: " + gwServiceId);
                return "";
            }
        }

        return "";
    }

    public String cancelInviteToConference(Invite invite) throws EndpointNotExistException, CancelInvitationException {
        Room room = this.room.getRoom(invite.getFromRoomID());
        Group room_group = this.group.getGroup(room.getGroupID());

        Member fromMember = this.member.getMember(invite.getFromMemberID());
        if (fromMember == null) {
            String errMsg = "cancelInviteToConference failed. fromMmember does not exist for memberId = "
                    + invite.getFromMemberID();
            logger.error(errMsg);
            throw new CancelInvitationException(errMsg);
        }

        Member toMember = null;

        int toMemberID = invite.getToMemberID();
        if (toMemberID == 0) { // just use what user type in input box
            toMember = new Member();
            toMember.setMemberName(invite.getSearch());
            toMember.setRoomExtNumber(invite.getSearch());
        } else {
            toMember = this.member.getMember(toMemberID);
            if (toMember == null) {
                String errMsg = "cancelInviteToConference failed. toMmember does not exist for memberId = "
                        + toMemberID;
                logger.error(errMsg);
                throw new CancelInvitationException(errMsg);
            }
        }

        String toGUID;
        if (invite.getToEndpointID() == 0) { // Legacy Device
            toGUID = this.getVirtualGUIDForJoinToLegacyForCancel(fromMember, toMember, room.getTenantID());
        } else {
            toGUID = this.dao.getGUIDForEndpointID(invite.getToEndpointID(), "D");
        }

        if (StringUtils.isBlank(toGUID)) {
            String errMsg = "No ringing call found to cancel";
            logger.error(errMsg);
            throw new EndpointNotExistException(errMsg);
        }
        toMember.setEndpointGUID(toGUID);

        try {

            cancelInviteEndpointToConference(room, room_group, fromMember, toMember);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new CancelInvitationException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new CancelInvitationException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new CancelInvitationException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new CancelInvitationException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new CancelInvitationException();
        }

        return toGUID;
    }

    /*public void startRing(int fromMemberID, int toRoomID) throws StartRingException {
        Member fromMember = this.member.getMember(fromMemberID);
        if (fromMember == null) {
            String errMsg = "startRing failed. Incorect fromMemberID : " + fromMemberID;
            logger.error(errMsg);
            throw new StartRingException();
        }
        Room fromRoom = this.room.getRoom(fromMember.getRoomID());
        Room toRoom = this.room.getRoom(toRoomID);
        Member toMember = this.member.getMember(toRoom.getMemberID());
        if (toMember == null) {
            String errMsg = "startRing failed. Incorect toMemberID : " + toRoom.getMemberID();
            logger.error(errMsg);
            throw new StartRingException(errMsg);
        }

        try {

            startRing(fromRoom, fromMember, toMember);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new StartRingException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new StartRingException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new StartRingException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new StartRingException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new StartRingException();
        }
    }*/

    /*public void stopRing(int fromMemberID, int toRoomID) throws StopRingException {
        Member fromMember = this.member.getMember(fromMemberID);
        if (fromMember == null) {
            String errMsg = "stopRing failed. Incorect fromMemberID : " + fromMemberID;
            logger.error(errMsg);
            throw new StopRingException(errMsg);
        }
        Room fromRoom = this.room.getRoom(fromMember.getRoomID());
        Room toRoom = this.room.getRoom(toRoomID);
        Member toMember = this.member.getMember(toRoom.getMemberID());
        if (toMember == null) {
            String errMsg = "stopRing failed. Incorect toMemberID : " + toRoom.getMemberID();
            logger.error(errMsg);
            throw new StopRingException(errMsg);
        }

        try {

            stopRing(fromRoom, fromMember, toMember);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new StopRingException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new StopRingException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new StopRingException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new StopRingException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new StopRingException();
        }
    }*/

    public void twoPartyConference(int fromMemberID, int toRoomID)
            throws EndpointNotExistException, MakeCallException, OutOfPortsException {

        Member fromMember = this.member.getMember(fromMemberID);
        if (fromMember == null) {
            String errMsg = "twoPartyConference failed. Incorect fromMemberID : " + fromMemberID;
            logger.error(errMsg);
            throw new MakeCallException(errMsg);
        }
        Room fromRoom = this.room.getRoom(fromMember.getRoomID());
        Group room_group = this.group.getGroup(fromRoom.getGroupID());
        String fromEndpointType = "D";

        Room toRoom = this.room.getRoom(toRoomID);
        Member toMember = this.member.getMember(toRoom.getMemberID());
        if (toMember == null) {
            String errMsg = "twoPartyConference failed. Incorect toMemberID : " + toRoom.getMemberID();
            logger.error(errMsg);
            throw new MakeCallException(errMsg);
        }

        String toGUID;
        String toEndpointType = "";
        if (toRoom.getRoomTypeID() == 3) { // Legacy Device
            // check if this tenant can use VidyoGateway for the prefix
            List<Tenant> canCallToTenants = this.tenant.canCallToTenants(fromMember.getTenantID());
            boolean can_call_flag = this.canTenantUseGatewayforAddress(canCallToTenants, toMember.getRoomExtNumber(),
                    fromMember.getTenantID());
            if (can_call_flag) {
                toGUID = this.getVirtualGUIDForLegacyDevice(fromMember, toMember, null, fromMember.getTenantID());
                toEndpointType = "V";
            } else {
                throw new CannotCallTenantP2PException();
            }
        } else {
            toGUID = this.dao.getGUIDForMemberID(toRoom.getMemberID());
            toEndpointType = "D";
        }

        if (toGUID.equalsIgnoreCase("")) {
            throw new MakeCallException();
        }
        toMember.setEndpointGUID(toGUID);

        checkRoleAndValidateFreePorts(fromMember, toMember);

        String uniqueCallID;
        String confName = fromRoom.getRoomName() + "@" + fromRoom.getTenantName() + "_" + toRoom.getRoomName() + "@"
                + toRoom.getTenantName();

        if (!isEndpointReady(fromEndpointType, fromMember.getEndpointGUID())
                || !isEndpointReady(toEndpointType, toMember.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallID = createConferenceUniqueCallID();
            logger.info("one or both endpoints for p2p not online, queuing invite from: " + fromMember.getEndpointGUID()
                    + ", to: " + toMember.getEndpointGUID());

            if ("V".equals(fromEndpointType)) { // from legacy to portal call
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            } else {
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            }
        } else {

            // STEP 1 - create a P2P conference
            try {

                uniqueCallID = createP2PConference(fromRoom, room_group, toRoom);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException(e.getMessage());
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException(e.getMessage());
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException(e.getMessage());
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException(e.getMessage());
            }

            // STEP 2 - make a call for 2 endpoints
            try {

                makeCall(uniqueCallID, fromRoom, room_group, toRoom, fromMember, toMember, fromEndpointType,
                        toEndpointType);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (EndpointNotExistException e) {
                logger.error(e.getMessage());
                // special case - VirtualEndpoints gone but Online in DB
                String vGUID = e.getMessage();
                updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
                throw new MakeCallException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            }
        }

        sendGwServiceEndpointMessageIfAny();

    }

    public void twoPartyConference(String virtualEndpointGUID, String nameExt, String tenant, String displayName)
            throws MakeCallException, OutOfPortsException {
        this.dao.updateVirtualEndpointInfo(virtualEndpointGUID, displayName, displayName, 0); // displayExt
                                                                                                // =
                                                                                                // displayName

        VirtualEndpoint ve = this.endpointService.getVirtualEndpointForEndpointGUID(virtualEndpointGUID);
        Room toRoom = this.room.getRoom(nameExt, tenant);

        twoPartyConference(ve, toRoom);
    }

    private void twoPartyConferenceFromLegacyInQueue(String virtualEndpointGUID)
            throws MakeCallException, OutOfPortsException {
        logger.info(" handling queued P2P FROM LEGACY call: " + virtualEndpointGUID);
        VirtualEndpoint ve = this.endpointService.getVirtualEndpointForEndpointGUID(virtualEndpointGUID);

        int toRoomId = this.dao.getRoomIDForEndpointGUIDConferenceRecord(virtualEndpointGUID);
        Room toRoom = this.room.getRoomDetailsForConference(toRoomId); // from
                                                                        // legacy
                                                                        // stores
                                                                        // toRoomId
        twoPartyConference(ve, toRoom);
    }

    private void twoPartyConferenceToLegacyInQueue(String virtualEndpointGUID)
            throws MakeCallException, OutOfPortsException, EndpointNotExistException {
        logger.info(" handling queued P2P TO LEGACY call: " + virtualEndpointGUID);
        VirtualEndpoint ve = this.endpointService.getVirtualEndpointForEndpointGUID(virtualEndpointGUID);
        int fromRoomId = this.dao.getRoomIDForEndpointGUIDConferenceRecord(virtualEndpointGUID); // to
                                                                                                    // legacy
                                                                                                    // stores
                                                                                                    // fromRoomId

        Room fromRoom = this.room.getRoomDetailsForConference(fromRoomId);
        Invite invite = new Invite();
        if (ve.getEntityID() == 0) {
            invite.setFromMemberID(fromRoom.getMemberID());
            invite.setFromRoomID(fromRoomId);
            invite.setToMemberID(0);
            invite.setToEndpointID(0);
            invite.setSearch(ve.getDisplayName());
        } else {
            // we have a legacy member entity
            Room legacyRoom = this.room.getRoom(ve.getEntityID());
            invite.setFromMemberID(fromRoom.getMemberID());
            invite.setFromRoomID(fromRoomId);
            invite.setToMemberID(legacyRoom.getMemberID());
            invite.setToEndpointID(0);
            invite.setSearch(ve.getDisplayName());
        }
        invite.setPayload(ve);
        twoPartyConference(invite); // toRoom faked
    }

    private void twoPartyConference(VirtualEndpoint ve, Room toRoom) throws MakeCallException, OutOfPortsException {
        // fake Room
        Room fromRoom = new Room();
        fromRoom.setRoomName(ve.getDisplayName());
        fromRoom.setTenantName(ve.getTenantName());

        // fake Member
        Member fromMember = new Member();
        fromMember.setEndpointGUID(ve.getEndpointGUID());
        fromMember.setMemberName(ve.getDisplayName());
        fromMember.setTenantName(ve.getTenantName());
        fromMember.setRoleName("Legacy");

        String fromEndpointType = "V";

        int tenantID = toRoom.getTenantID();
        Group room_group = this.group.getGroup(toRoom.getGroupID());
        Member toMember = this.member.getMember(toRoom.getMemberID());
        if (toMember == null) {
            String errMsg = "twoPartyConference failed. Incorrect toMemberID : " + toRoom.getMemberID();
            logger.error(errMsg);
            throw new MakeCallException(errMsg);
        }

        String toEndpointType = "D";

        List<Tenant> canCallToTenants = this.tenant.canCallToTenants(ve.getTenantID());
        boolean can_call_flag = false;
        for (Tenant to_tenant : canCallToTenants) {
            if (tenantID == to_tenant.getTenantID()) {
                can_call_flag = true;
                break;
            }
        }
        if (!can_call_flag) {
            throw new CannotCallTenantP2PException();
        }

        checkRoleAndValidateFreePorts(fromMember, toMember);

        String uniqueCallID;
        String confName = fromRoom.getRoomName() + "@" + fromRoom.getTenantName() + "_" + toRoom.getRoomName() + "@"
                + toRoom.getTenantName();

        if (!isEndpointReady(fromEndpointType, fromMember.getEndpointGUID())
                || !isEndpointReady(toEndpointType, toMember.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallID = createConferenceUniqueCallID();
            logger.info("one or both endpoints for p2p not online, queuing invite from: " + fromMember.getEndpointGUID()
                    + ", to: " + toMember.getEndpointGUID());

            if ("V".equals(fromEndpointType)) { // from legacy to portal call
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            } else {
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            }
        } else {
            // STEP 1 - create a P2P conference
            try {

                uniqueCallID = createP2PConference(fromRoom, room_group, toRoom);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            }

            // STEP 2 - make a call for 2 endpoints
            try {

                makeCall(uniqueCallID, fromRoom, room_group, toRoom, fromMember, toMember, fromEndpointType,
                        toEndpointType);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (EndpointNotExistException e) {
                logger.error(e.getMessage());
                // special case - VirtualEndpoints gone but Online in DB
                String vGUID = e.getMessage();
                updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
                throw new MakeCallException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            }
        }
    }

    public void twoPartyConference(Invite invite)
            throws EndpointNotExistException, MakeCallException, OutOfPortsException {
        Room fromRoom = this.room.getRoom(invite.getFromRoomID());

        Member fromMember = this.member.getMember(invite.getFromMemberID());
        if (fromMember == null) {
            String errMsg = "twoPartyConference failed. Incorect fromMemberID : " + invite.getFromMemberID();
            logger.error(errMsg);
            throw new MakeCallException(errMsg);
        }
        Group room_group = this.group.getGroup(fromRoom.getGroupID());
        String fromEndpointType = "D";

        // fake Room
        Room toRoom = new Room();
        // fake Member
        Member toMember = new Member();
        int toMemberID = invite.getToMemberID();
        if (toMemberID == 0) { // just use what user type in input box
            toMember.setMemberName(invite.getSearch());
            toMember.setRoomExtNumber(invite.getSearch());
            toMember.setRoleName("Legacy");
            toMember.setTenantID(fromRoom.getTenantID());
            toRoom.setRoomName(invite.getSearch());
        } else {
            toMember = this.member.getMember(toMemberID);
            if (toMember == null) {
                String errMsg = "twoPartyConference failed. Incorect toMemberID : " + toMemberID;
                logger.error(errMsg);
                throw new MakeCallException(errMsg);
            }
            toRoom = this.room.getRoom(toMember.getRoomID());
        }

        checkRoleAndValidateFreePorts(fromMember, toMember);

        String toGUID;
        String toEndpointType;
        if (invite.getToEndpointID() == 0) { // Legacy Device
            toEndpointType = "V";

            // check if this tenant can use VidyoGateway for the prefix
            List<Tenant> canCallToTenants = this.tenant.canCallToTenants(fromMember.getTenantID());
            boolean can_call_flag = this.canTenantUseGatewayforAddress(canCallToTenants, toMember.getRoomExtNumber(),
                    fromMember.getTenantID());
            if (can_call_flag) {
                toGUID = this.getVirtualGUIDForLegacyDevice(fromMember, toMember, invite.getPayload(),
                        fromMember.getTenantID());
            } else {
                throw new CannotCallTenantP2PException();
            }
            if (!toGUID.equalsIgnoreCase("")) {
                int endpointID = this.getEndpointIDForGUID(toGUID, toEndpointType);
                VirtualEndpoint ve = this.getVirtualEndpointForEndpointID(endpointID);
                if (toRoom.getTenantName() == null) {
                    toRoom.setTenantName(ve.getTenantName());
                }
                if (toMember.getTenantName() == null) {
                    toMember.setTenantName(ve.getTenantName());
                }
            }
        } else {
            toEndpointType = "D";
            toGUID = this.dao.getGUIDForEndpointID(invite.getToEndpointID(), toEndpointType);
        }

        if (toGUID.equalsIgnoreCase("")) {
            throw new MakeCallException();
        }
        toMember.setEndpointGUID(toGUID);

        String uniqueCallID;
        String confName = fromRoom.getRoomName() + "@" + fromRoom.getTenantName() + "_" + toRoom.getRoomName() + "@"
                + toRoom.getTenantName();

        if (!isEndpointReady(fromEndpointType, fromMember.getEndpointGUID())
                || !isEndpointReady(toEndpointType, toMember.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallID = createConferenceUniqueCallID();
            logger.info("one or both endpoints for p2p not online, queuing invite from: " + fromMember.getEndpointGUID()
                    + ", to: " + toMember.getEndpointGUID());

            if ("V".equals(fromEndpointType)) { // from legacy to portal call
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", toRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            } else {
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        fromMember.getEndpointGUID(), fromEndpointType, "Y");
                this.dao.addEndpointToConferenceRecordQueue(uniqueCallID, confName, "P", fromRoom,
                        toMember.getEndpointGUID(), toEndpointType, "N");
            }
        } else {
            // STEP 1 - create a P2P conference
            try {

                uniqueCallID = createP2PConference(fromRoom, room_group, toRoom);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            }

            // STEP 2 - make a call for 2 endpoints
            try {

                makeCall(uniqueCallID, fromRoom, room_group, toRoom, fromMember, toMember, fromEndpointType,
                        toEndpointType);

            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (EndpointNotExistException e) {
                logger.error(e.getMessage());
                // special case - VirtualEndpoints gone but Online in DB
                String vGUID = e.getMessage();
                updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
                throw new MakeCallException();
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new MakeCallException();
            }
        }

        sendGwServiceEndpointMessageIfAny();

    }

    public void recordTheConference(int memberID, int roomID, String recorderPrefix, int webcast)
            throws OutOfPortsException, JoinConferenceException {
        Member member = this.member.getMember(memberID);
        if (member == null) {
            String errMsg = "recordTheConference failed. Incorect memberID : " + memberID;
            logger.error(errMsg);
            throw new JoinConferenceException(errMsg);
        }
        Room room = this.room.getRoom(roomID);

        String licenseVersion = getLicenseVersion();
        // Recorder gets counted only in version 20 (ports) and 22 (UVL)
        if (licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)
                || licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) {
            int tenantID = room.getTenantID();
            Tenant t = this.tenant.getTenant(tenantID);
            int totalPorts = t.getPorts();
            int usedPorts = this.tenant.getUsedNumOfPorts(tenantID);
            int freePorts = totalPorts - usedPorts;
            if (freePorts <= 0) {
                throw new OutOfPortsException();
            }
        }

        Group room_group = this.group.getGroup(room.getGroupID());

        RecorderEndpoint recorder;

        // check if this tenant can use VidyoRecorder for the prefix
        // List<Tenant> canCallToTenants =
        // this.tenant.canCallToTenants(member.getTenantID());
        // boolean can_call_flag =
        // this.dao.canTenantUseRecorderforPrefix(canCallToTenants,
        // recorderPrefix);
        // if (can_call_flag) {
        recorder = this.dao.getRecorderGUIDForPrefix(recorderPrefix, member, webcast, null);
        // } else {
        // throw new JoinConferenceException();
        // }

        if (recorder == null) {
            throw new JoinConferenceException();
        }

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        // STEP 1 - create a conference
        try {

            createConference(confName, room_group.getRoomMaxUsers());

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException();
        }

        // STEP 2 - add endpoint to the conference
        try {

            inviteRecorderToConference(room, room_group, member, recorder.getEndpointGUID(), recorder.getDescription(),
                    webcast);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            this.dao.updateRecorderEndpointInfo(recorder.getEndpointGUID(), 0, 0);
            throw new JoinConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            this.dao.updateRecorderEndpointInfo(recorder.getEndpointGUID(), 0, 0);
            throw new JoinConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            this.dao.updateRecorderEndpointInfo(recorder.getEndpointGUID(), 0, 0);
            throw new JoinConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            this.dao.updateRecorderEndpointInfo(recorder.getEndpointGUID(), 0, 0);
            throw new JoinConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new JoinConferenceException();
        }
    }

    public List<Control> getControl(int roomID, ControlFilter filter) {
        List<Control> list = this.dao.getControls(roomID, filter);
        List<Control> fedlist = this.dao.getFederationControls(roomID, filter);

        list.addAll(fedlist);

        return list;
    }

    public Control getControlForMember(int memberID, ControlFilter filter) {
        Control list = this.dao.getControlForMember(memberID, filter);
        return list;
    }

    public Long getCountControl(int roomID) {
        Long number = this.dao.getCountControls(roomID);
        return number;
    }

    public void updateEndpointAudio(String GUID, boolean mute) {
        if (mute) {
            this.dao.updateEndpointAudio(GUID, 0);
        } else {
            this.dao.updateEndpointAudio(GUID, 1);
        }
    }

    public void updateEndpointAudioForAll(int roomID, boolean mute) {
        if (mute) {
            this.dao.updateEndpointAudioForAll(roomID, 0);
        } else {
            this.dao.updateEndpointAudioForAll(roomID, 1);
        }
    }

    public void muteAudio(String GUID) throws Exception {
        logger.debug("muteAudio for GUID = " + GUID);

        // update record for GUID in Conference table for new columns - audio,
        // video, share
        this.dao.updateEndpointAudio(GUID, 0);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(requestID);

        MediaControlRequest mute_audio_req = req_message.addNewMediaControl();

        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.AUDIO);
        media_type.setMediaSource(MediaSourceType.LOCAL);

        mute_audio_req.setCommand(MediaControlCommandType.MUTE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("muteAudio for GUID = " + GUID + e.getMessage());
        }
        // This method is invoked to send status notification to tcp events streaming - it wont capture cdr
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);
    }

    public void unmuteAudio(String GUID) throws Exception {
        logger.debug("unmuteAudio for GUID = " + GUID);

        // update record for GUID in Conference table for new columns - audio,
        // video, share
        this.dao.updateEndpointAudio(GUID, 1);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage requestMessage = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        requestMessage.setRequestID(requestID);

        MediaControlRequest mute_audio_req = requestMessage.addNewMediaControl();
        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.AUDIO);
        media_type.setMediaSource(MediaSourceType.LOCAL);
        mute_audio_req.setCommand(MediaControlCommandType.UNMUTE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("unmuteAudio for GUID = " + GUID + e.getMessage());
        }
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);
    }

    /**
     * Send lecture mode on vcap msg.
     *
     * @param GUID
     */
    private void lectureModeInform(int roomID, String GUID) {
        // check for optional presenter
        String presenterRouterParticipantID = this.dao.getPresenterInRoom(roomID);
        this.updateEndpointAudio(GUID, true);
        DataHandler lectureModeMsg = LectureModeServiceImpl.getVCAPLectureModeChange(true,
                presenterRouterParticipantID);
        try {
            sendMessageToEndpoint(GUID, lectureModeMsg);
        } catch (Exception e) {
            logger.error("lectureModeInform for GUID = " + GUID + e.getMessage());
        }
    }

    /**
     * Room state might have changed between being invited/joining and actually
     * being in the conference
     */
    private void lectureModeInformPostJoin(ConferenceRecord conferenceRecord) {
        if (conferenceRecord == null) {
            return;
        }
        if ("P".equals(conferenceRecord.getConferenceType())) {
            return;
        }

        String GUID = conferenceRecord.getGUID();
        int roomId = conferenceRecord.getRoomID();
        Room room = null;
		try {
			room = this.room.getRoom(roomId);
		} catch (Exception e) {
			logger.error("Room not found "+ roomId);
		}
        if (room == null) {
            return;
        }

        boolean mute = room.getRoomMuted() == 1;

        String presenterRouterParticipantID = this.dao.getPresenterInRoom(roomId);

        DataHandler lectureModeMsg;

        if (1 == room.getLectureMode()) {
            if (guidIsPresenting(GUID, presenterRouterParticipantID)) {
                lectureModeMsg = LectureModeServiceImpl.getVCAPLectureModeState(true, presenterRouterParticipantID,
                        false);
            } else {
                lectureModeMsg = LectureModeServiceImpl.getVCAPLectureModeState(true, presenterRouterParticipantID,
                        true);
            }
        } else {
            lectureModeMsg = LectureModeServiceImpl.getVCAPLectureModeState(false, null, mute);
        }
        try {
            sendMessageToEndpoint(GUID, lectureModeMsg);
        } catch (Exception e) {
            logger.error("lectureModeInformPostJoin for GUID = " + GUID + e.getMessage());
        }
    }

    private boolean guidIsPresenting(String GUID, String participantId) {
        if (participantId == null) {
            return false;
        }
        return participantId.equals(this.dao.getParticipantIdForGUID(GUID));
    }

    public void stopVideo(String GUID) throws Exception {
        logger.debug("stopVideo for GUID = " + GUID);

        // update record for GUID in Conference table for new columns - audio,
        // video, share
        this.dao.updateEndpointVideo(GUID, 0);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(requestID);

        MediaControlRequest mute_audio_req = req_message.addNewMediaControl();

        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.VIDEO);
        media_type.setMediaSource(MediaSourceType.LOCAL);

        mute_audio_req.setCommand(MediaControlCommandType.MUTE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("stopVideo for GUID = " + GUID + e.getMessage());
        }
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);

    }

    public void startVideo(String GUID) throws Exception {
        logger.debug("startVideo for GUID = " + GUID);

        // update record for GUID in Conference table for new columns - audio,
        // video, share
        this.dao.updateEndpointVideo(GUID, 1);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(requestID);

        MediaControlRequest mute_audio_req = req_message.addNewMediaControl();

        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.VIDEO);
        media_type.setMediaSource(MediaSourceType.LOCAL);

        mute_audio_req.setCommand(MediaControlCommandType.UNMUTE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("startVideo for GUID = " + GUID + e.getMessage());
        }
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);
    }

    public void startVideoIfStopped(String newPresenterGUID) throws Exception {
        if (this.dao.isVideoStopped(newPresenterGUID)) {
            startVideo(newPresenterGUID);
        }
    }

    public void silenceAudio(String GUID) throws Exception {
        logger.debug("silenceAudio for GUID = " + GUID);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(requestID);

        MediaControlRequest mute_audio_req = req_message.addNewMediaControl();

        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.AUDIO);
        media_type.setMediaSource(MediaSourceType.LOCAL);

        mute_audio_req.setCommand(MediaControlCommandType.SILENCE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("silenceAudio for GUID = " + GUID + e.getMessage());
        }
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);
    }

    public void silenceVideo(String GUID) throws Exception {
        logger.debug("silenceVidio for GUID = " + GUID);

        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(requestID);

        MediaControlRequest mute_audio_req = req_message.addNewMediaControl();

        MediaChannelType media_type = mute_audio_req.addNewMediaChannel();
        media_type.setMediaType(MediaType.VIDEO);
        media_type.setMediaSource(MediaSourceType.LOCAL);

        mute_audio_req.setCommand(MediaControlCommandType.SILENCE);

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler muteAudio = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, muteAudio);
        } catch (Exception e) {
            logger.error("silenceVideo for GUID = " + GUID + e.getMessage());
        }
        cdrCollectionService.collectCdrRecord(GUID, null, -1, -1, null, null,
        		null, false);
    }

    /**
     * VirtualEndpoints may not be ONLINE when a call is requested, therefore,
     * they can be queued in the ConferenceRecord table,
     *
     * @param GUID
     * @param newStatus
     * @param newSequenceNum
     * @return
     */
    private void handleQueuedCallAsync(String GUID, int newStatus, long newSequenceNum, int tenantID) {
        logger.info("VirtualGUID handleQueuedCallAsync: " + GUID + ", newStatus: " + newStatus);
        if (this.gwQueuedCallsTaskExecutor != null) {
            this.gwQueuedCallsTaskExecutor
                    .execute(new HandleQueuedCallTask(GUID, newStatus, newSequenceNum, dao, tenantID));
            if (this.gwQueuedCallsTaskExecutor.getActiveCount() >= 15) {
                logger.warn("gwTaskThreadPool operating at full capacity (all 15 threads busy)");
            }
        } else {
            // true in admin/super/batch contexts
            logger.warn("no gwTaskThreadPool configured, ignoring processing of: " + GUID);
        }
    }

    private class HandleQueuedCallTask implements Runnable {

        private String GUID;
        private int newStatus;
        private long newSequenceNum;
        private IConferenceDao dao;
        private int tenantID;

        public HandleQueuedCallTask(String GUID, int newStatus, long newSequenceNum, IConferenceDao dao, int tenantID) {
            this.GUID = GUID;
            this.newStatus = newStatus;
            this.newSequenceNum = newSequenceNum;
            this.dao = dao;
            this.tenantID = tenantID;
        }

        public void run() {
            logger.info("gwTaskThreadPool task thread started handling: " + GUID);

            TenantContext.setTenantId(tenantID);
            // wait for GatewaySericeImpl(see line 156) thread to create an
            // entry in ConferenceRecord
            /*File lockFile = new File("/home/tomcatnp/VE-" + GUID + ".lock");
            if (!lockFile.exists()) {
                logger.info("Lock file not found: " + lockFile.getName());
            }
            int countLoops = 0;
            try {
                while (lockFile.exists() && countLoops < 40) { // don't wait
                                                                // more than 2
                                                                // seconds
                    logger.info("...sleeping until other thread removes lock....");
                    Thread.sleep(50);
                    countLoops++;
                }
            } catch (InterruptedException ie) {
                logger.error("Interrupted while waiting for lock release: " + ie.getMessage());
            } finally {
                if (lockFile.exists()) {
                    logger.error("Lock file still exists!! Deleting: " + lockFile.getName());
                    FileUtils.deleteQuietly(lockFile);
                }
            }*/

            // update status no matter what
            this.dao.updateVirtualEndpointStatus(GUID, newStatus, newSequenceNum);

            ConferenceRecord conferenceRecord = this.dao.getConferenceRecordForEndpointGUID(GUID);
            if (conferenceRecord != null) {
                String conferenceType = conferenceRecord.getConferenceType();
                String endpointCaller = conferenceRecord.getEndpointCaller();

                logger.info(" VE in queue, found conference record for: " + GUID + " type: " + conferenceType
                        + ", caller:" + endpointCaller);
                if ("Y".equals(endpointCaller)) { // INBOUND CALL FROM LEGACY
                    if ("P".equals(conferenceType)) {
                        try {
                            twoPartyConferenceFromLegacyInQueue(GUID);
                        } catch (MakeCallException e) {
                            logger.warn("could not join p2p VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", make call exception");
                        } catch (OutOfPortsException e) {
                            logger.warn("could not join p2p  VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", out of ports exception");
                        }
                    } else if ("C".equals(conferenceType)) {
                        try {
                            joinTheConferenceInQueue(GUID);
                        } catch (OutOfPortsException e) {
                            logger.warn("could not join conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", out of ports exception");
                        } catch (JoinConferenceException e) {
                            logger.warn("could not join conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", join conference exception");
                        } catch (EndpointNotSupportedException e) {
                            logger.warn("could not join conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", endpoint not supported exception");
                        }
                    }
                } else { // OUTBOUND CALL TO LEGACY (an invite)
                    if ("P".equals(conferenceType)) {
                        try {
                            twoPartyConferenceToLegacyInQueue(GUID);
                        } catch (MakeCallException e) {
                            logger.warn("could not invite p2p VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", make call exception");
                        } catch (OutOfPortsException e) {
                            logger.warn("could not invite p2p  VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", out of ports exception");
                        } catch (EndpointNotExistException e) {
                            logger.warn("could not invite p2p VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", endpoint not found");
                        }
                    } else if ("C".equals(conferenceType)) {
                        try {
                            inviteParticipantToConferenceQueue(GUID);
                        } catch (OutOfPortsException e) {
                            logger.warn("could not invite conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", out of ports exception");
                        } catch (EndpointNotExistException e) {
                            logger.warn("could not invite conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", endpoint not found");
                        } catch (InviteConferenceException e) {
                            logger.warn("could not invite conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", invite conference exception");
                        } catch (EndpointNotSupportedException e) {
                            logger.warn("could not invite conf VE: " + GUID + ", conference: "
                                    + conferenceRecord.getConferenceName() + ", endpoint not supported exception");
                        }
                    }
                }
            } else {
                logger.info(" VE not in queue, no conference record found for: " + GUID);
            }
        }
    }

    /**
     * Updates Endpoint Status in the Portal based on the notification from
     * VidyoManager. IMPORTANT: There are AOP interceptors working on this
     * method flow. Please refer to the spring interceptors xml before changing
     * any business logic in this method
     *
     * @param GUID
     * @param status
     * @param source
     * @param conferenceInfo
     * @param newSequenceNum
     * @param updateReason
     * @param participantId
     */
    public void updateEndpointStatus(String GUID, String status, String source, ConferenceInfo conferenceInfo,
            long newSequenceNum, String updateReason, String participantId) {

        logger.info("updateEndpointStatus: guid=" + GUID + ",newStatus=" + status + ",source=" + source
                + ",updateReason=" + updateReason + ",seqnum=" + newSequenceNum + ",confInfo="
                + (conferenceInfo == null ? "" : conferenceInfo.toString()));

        Endpoint endpoint = endpointService.getEndpointDetail(GUID);

        int oldStatus = 0;
        String endpointType = "D";
        String memberType = "R";
        int memberID = 0;
        if (endpoint != null) {
            oldStatus = endpoint.getStatus();
            endpointType = endpoint.getEndpointType();
            memberType = endpoint.getMemberType();
            memberID = endpoint.getMemberID();
            endpoint.setGuid(GUID);
        }

        int newStatus = convertStatus(status);

        int tenantID = this.dao.getTenantIDForGUID(GUID, endpointType);
        // Thread local TenantId will be overridden by the EndpointGUID's TenantId (Member or Guest)
        TenantContext.setTenantId(tenantID);

        // New CDR collection implementation
        boolean saveUpdateCDR = true; //Flag to notify service to save/update the record
        this.cdrCollectionService.collectCdrRecord(GUID, status, oldStatus, newStatus, conferenceInfo, endpointType,
                updateReason, saveUpdateCDR);

        // if virtual endpoint going online
        if (oldStatus == 0 && newStatus == 1 && this.servicesService.isUseNewGatewayServiceInterface()) { // online
            if (endpoint != null && "V".equalsIgnoreCase(endpointType)) {
                handleQueuedCallAsync(GUID, newStatus, newSequenceNum, tenantID);
                return; // since handled, do not process via code below!
            }
        }

        ConferenceRecord conferenceRecordBeforeJoin = null;
        // create a record in Conferences table before to do a business logic
        if (newStatus == 2) { // Busy
            conferenceRecordBeforeJoin = this.dao.getConferenceRecordForEndpointGUID(GUID);
            this.dao.moveEndpointToConference(GUID, conferenceInfo, participantId);
        }

        // this feature only for BankOfAmerica, and generally will be OFF
        if (VendorUtils.isRoomsLockedByDefault()) {
            if (newStatus == 2 || newStatus == 9) { // busy , busyInOwnRoom
                // if room owner joining own room and rooms are locked by
                // default, unlock room
                dao.unlockRoomIfRoomOwnerJoined(GUID);
            } else if (newStatus == 0 || newStatus == 1) {
                // if online/offline they left conference, so lock room again
                dao.lockOwnerRoomsIfRoomOwnerLeaving(GUID);
            }
        }

        // If insertException is true, caller has to get the memberID again for
        // further processing
        boolean insertException = this.dao.updateEndpointStatus(GUID, newStatus, newSequenceNum, endpointType);

        synchronized (GUID.intern()) {
            // Round-robin for different VG
            if (newStatus == 10 || newStatus == 11) { // 10 - RingFailed, 11 -
                                                        // JoinFailed
                if (endpointType.equalsIgnoreCase("V") && !this.servicesService.isUseNewGatewayServiceInterface()) { // Gateway
                                                                                                                        // endpoints
                    roundRobinGateways(GUID);
                }
                if (endpointType.equalsIgnoreCase("R")) { // Recorder endpoints
                    roundRobinRecorders(GUID);
                }
                // remove Endpoint from any conference in statuses "JoinFailed"
                // and "RingFailed"
                this.dao.removeEndpointFromConference(GUID, endpointType);
            }
        }

        // busy -> online/offline (ie. disconnected or heartbeat timeout)
        if ((oldStatus == 2 || oldStatus == 9) && (newStatus == 0 || newStatus == 1)) {
            // handle lecture mode case: user who disconnected was presenter
            notifyPresenterDisconnect(GUID);
        }

        if ((newStatus == 0 || newStatus == 1) && oldStatus != 0 && oldStatus != 1) {
            // Moving the logic to a method so it can be invoked by linkEndpoint
            // flow
            checkAndDeleteConference(GUID, endpointType);
        }

        // When the endpoint goes offline, reset the authorized flag to "0" as
        // the bind process would set it back to "1"
        if (endpoint != null && newStatus == 0 && ("G".equals(memberType) || ("R".equals(memberType)))) {
            // unauthorize endpoint
            endpoint.setAuthorized(0);
            this.user.setAuthorizedFlagForEndpoint(endpoint);
        }

        // update Virtual Endpoint info in statuses "Offline" and "Online"
        if (newStatus == 0 || newStatus == 1 || newStatus == 10 || newStatus == 11) {
            if (endpointType.equalsIgnoreCase("V") && !this.servicesService.isUseNewGatewayServiceInterface()) {
                this.dao.updateVirtualEndpointInfo(GUID, "", "", 0);
            }
            if (endpointType.equalsIgnoreCase("R")) {
                this.dao.updateRecorderEndpointInfo(GUID, 0, 0);
            }
        }

        // Offline -> Online will bind user again
        if (oldStatus == 0 && newStatus == 1) {
            if (endpointType.equalsIgnoreCase("D")) {
                if (insertException) {
                    // Get the memberID again
                    endpoint = endpointService.getEndpointDetail(GUID);
                    if (endpoint != null) {
                        memberID = endpoint.getMemberID();
                        memberType = endpoint.getEndpointType();
                    }
                }
                if (memberID != 0) {
                    // Auto Login scenario - Endpoint is already associated with
                    // the user
                    if (memberType.equalsIgnoreCase("R")) {
                        // Moved to a different service class, to enable forking
                        // through interception
                        bindEndpointService.bindUserToEndpoint(memberID, GUID);
                    } else if (memberType.equalsIgnoreCase("G")) {
                        bindGuestToEndpoint(memberID, GUID);
                    }
                } else {
                    // Unlink endpoint if the member is not associated - This
                    // code breaks GW - so commented out
                    // if(endpointType.equalsIgnoreCase("D")) {
                    // logger.debug("Sending unbind to Endpoint {}, EndpointType
                    // {}", GUID, endpointType);
                    // user.unlinkEndpointFromUser(0, GUID);
                    // }
                }
            }
        }

        /*
         * Offline to Online status wont be sent from here, it will be handled
         * through bind user. Offline status might get reported here
         * [duplicate], if the endpoint resets EMCP connection before logout or
         * even simultaneously
         */
		if (!(oldStatus == 0 && newStatus == 1)) {
			Alert alert = new Alert(GUID, status, memberID);
			// Recorders & Legacy would show up as 'Busy' when in Conference -
			// but no online status sent
			if (conferenceInfo != null && conferenceInfo.getConferenceID() != null) {
				alert.setConfName(conferenceInfo.getConferenceID());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Status Notification {}", alert);
			}
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setAlert(alert);

			Configuration config = this.system.getConfiguration("EPIC_INTEGRATION_SUPPORT");
			TenantConfiguration tenantConfiguration = tenant.getTenantConfiguration(TenantContext.getTenantId());

			boolean isGlobalEpicEnabled = false;
			boolean isTenantEpicEnabled = false;
			if ((config != null) && org.apache.commons.lang.StringUtils.isNotBlank(config.getConfigurationValue())
					&& config.getConfigurationValue().equalsIgnoreCase("1")) {
				isGlobalEpicEnabled = true;

				if (tenantConfiguration.getExternalIntegrationMode() == 1) {
					isTenantEpicEnabled = true;
				}
			}
			notificationInfo.setTenantId(TenantContext.getTenantId());
			String externalData = endpoint.getExtData(); // encrypted
			try {
				// Epic status notification should be sent only for join and disconnects and not for any other status
				if (isGlobalEpicEnabled && isTenantEpicEnabled && (endpoint != null) 
						&& ((newStatus == 2 || newStatus == 9) || (newStatus == 1 && oldStatus != 0) 
								|| (newStatus == 0 && oldStatus != 1))) {
					CDRinfo2 info = new CDRinfo2();
					String sharedSecret = tenantConfiguration.getExtIntegrationSharedSecret();
					String decryptedExternalData = null;
					// 1st try to decrypt the external data
					try {
						decryptedExternalData = AESEncryptDecryptUtil.decryptAESWithBase64(sharedSecret,
								externalData);
					} catch (Exception e) {
						logger.error("Decryption of external data failed, try with URLDecoder" + externalData + " Error" + e.getMessage());
						externalData = URLDecoder.decode(externalData, StandardCharsets.UTF_8.name());
						externalData = externalData.replaceAll(" ", "+"); // return + back
						decryptedExternalData = AESEncryptDecryptUtil.decryptAESWithBase64(sharedSecret,
								externalData);
						logger.debug("decryptedExternalData 2nd attempt->" +decryptedExternalData);
						// Let the second try fail with exception if the decoded string cannot be decrypted
					}
					int extConnectionStatus = 0;
					if((newStatus == 2 || newStatus == 9)) {
						extConnectionStatus = 1;
					} else {
						extConnectionStatus = 2;
					}
					info.setExtData(decryptedExternalData.concat("&ConnectionStatus="+extConnectionStatus));
					logger.debug("Decrypted external data->"+ info.getExtData());
					info.setExtDataType(
                            validateExtDataType(
                                    endpoint.getExtDataType()));
					notificationInfo.setUserNotification(info);

					notificationInfo.setExternalStatusNotificationUrl(tenantConfiguration.getExternalNotificationUrl());
					notificationInfo.setExternalUsername(tenantConfiguration.getExternalUsername());
					if (StringUtils.isNotBlank(tenantConfiguration.getExternalPassword())) {
						notificationInfo.setExternalPassword(tenantConfiguration.getExternalPassword());
						notificationInfo.setPlainTextExternalPassword(
								VidyoUtil.decrypt(tenantConfiguration.getExternalPassword()));
					} else {
						notificationInfo.setExternalPassword("");
						notificationInfo.setPlainTextExternalPassword("");
					}
				}
			} catch (Exception e) {
				logger.error("error while decrypting epic ext data->" + externalData + " error " + e.getMessage());
			}
			logger.debug("Notification Info after processing Epic " + notificationInfo);

			try {
				if (tenantConfiguration.getVidyoNotificationUrl() != null) {
					notificationInfo.setVidyoStatusNotificationUrl(tenantConfiguration.getVidyoNotificationUrl());
					notificationInfo.setVidyoUsername(tenantConfiguration.getVidyoUsername());
					if (StringUtils.isNotBlank(tenantConfiguration.getVidyoPassword())) {
						notificationInfo.setVidyoPassword(tenantConfiguration.getVidyoPassword());
						notificationInfo
								.setPlainTextVidyoPassword(VidyoUtil.decrypt(tenantConfiguration.getVidyoPassword()));
					}
				}
			} catch (Exception e) {
				logger.error("Exception while decrypting the vidyo notification password -> " + e.getMessage());
			}
			logger.debug("Notification Info before sending to queue " + notificationInfo);
			statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
		}

        // if room owner is joining, check to see if waiting room needs to be
        // turned off
        if ("D".equals(endpointType) && (newStatus == 2 || newStatus == 9)) {
            if (tenantID != 0) {
                // fast read from cached data
                TenantConfiguration tenantConfiguration = this.tenant.getTenantConfiguration(tenantID);
                if (tenantConfiguration.getWaitingRoomsEnabled() == 1
                        && tenantConfiguration.getWaitUntilOwnerJoins() == 1) {
                    checkAndStopWaitingRoomIfNecessary(GUID);
                }
            }
        }

        // do this here, after the room state transitions for lecture
        // mode/waiting room are handled by above code
        if (conferenceRecordBeforeJoin != null) { // will not be null when
                                                    // endpoint is busy (joined
                                                    // a conference)
            lectureModeInformPostJoin(conferenceRecordBeforeJoin);
        }
    }

    private void notifyPresenterDisconnect(String endpointGUID) {
        int roomID = this.dao.getRoomIDByPresenterGuid(endpointGUID);
        if (roomID == 0) {
            return;
        }
        // send VCAP message to all participants that presenter is gone
        List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
        DataHandler vcapMsg = LectureModeServiceImpl.getVCAPLectureModePresenterChange(null);
        for (String guid : endpointGUIDs) {
            if (!guid.equals(endpointGUID)) { // no msg to presenter that
                                                // disconnected
                try {
                    this.sendMessageToEndpoint(guid, vcapMsg);
                } catch (Exception e) {
                    logger.error("Failed to send presenter disconnect/remove msg to guid: " + guid);
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private void checkAndDeleteConference(String endpointGUID, String endpointType) {
        logger.debug("check and delete conference for GUID: " + endpointGUID);
        // remove Endpoint from any conference in statuses "Offline" and
        // "Online"
        Control conference = this.dao.removeEndpointFromConference(endpointGUID, endpointType);

        if (conference != null) {
            // If it was a last record for this conference - delete conference
            // in VM
            try {
                checkAndDeleteTheConference(conference);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void roundRobinGateways(String GUID) {
        int endpointID = this.getEndpointIDForGUID(GUID, "V");
        if (endpointID != 0) {
            VirtualEndpoint ve = getVirtualEndpointForEndpointID(endpointID);
            int roomID = this.dao.getRoomIDForEndpointIDRoundRobin(endpointID);
            if (roomID != 0) {
                Room room = this.room.getRoom(roomID);

                Invite invite = new Invite();
                invite.setFromRoomID(room.getRoomID());
                invite.setFromMemberID(room.getMemberID());
                invite.setSearch(ve.getDisplayName());
                // set to 0 required!!!
                invite.setToMemberID(0);
                invite.setToEndpointID(0);
                invite.setPayload(ve);

                String conferenceType = this.dao.getConferenceTypeForEndpointIDRoundRobin(endpointID);
                if (conferenceType.equalsIgnoreCase("C")) {
                    try {
                        this.inviteToConference(invite);
                    } catch (OutOfPortsException e) {
                        logger.error(e.getMessage());
                    } catch (EndpointNotExistException e) {
                        logger.error(e.getMessage());
                    } catch (InviteConferenceException e) {
                        logger.error(e.getMessage());
                    } catch (EndpointNotSupportedException e) {
                        logger.error(e.getMessage());
                    }
                } else {
                    try {
                        this.twoPartyConference(invite);
                    } catch (EndpointNotExistException e) {
                        logger.error(e.getMessage());
                    } catch (MakeCallException e) {
                        logger.error(e.getMessage());
                    } catch (OutOfPortsException e) {
                        logger.error("OutOfPortsException", e);
                    }
                }
            }
        }
    }

    private void roundRobinRecorders(String GUID) {
        int endpointID = this.getEndpointIDForGUID(GUID, "R");
        if (endpointID != 0) {
            RecorderEndpoint re = getRecorderEndpointForEndpointID(endpointID);
            int roomID = this.dao.getRoomIDForEndpointIDRoundRobin(endpointID);
            if (roomID != 0) {
                try {
                    this.recordTheConference(re.getEntityID(), roomID, re.getPrefix(), re.getWebcast());
                } catch (OutOfPortsException e) {
                    logger.error(e.getMessage());
                } catch (JoinConferenceException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public int getEndpointStatus(int endpointID) {
        // synchronized (this) {
        String GUID = this.dao.getGUIDForEndpointID(endpointID, "D");
        int status = this.dao.getEndpointStatus(GUID);
        return status;
        // }
    }

    public int getEndpointStatus(String GUID) {
        // synchronized (this) {
        int status = this.dao.getEndpointStatus(GUID);
        return status;
        // }
    }

    public String getEndpointIPaddress(String GUID) {
        String vrIP = this.dao.getEndpointIPaddress(GUID);
        return vrIP;
    }

    public List<Control> getParticipants(int roomID, ControlFilter filter) {
        List<Control> list = this.dao.getParticipants(roomID, filter);
        return list;
    }

    public List<Entity> getParticipants(int roomID, EntityFilter filter, User user) {
        List<Entity> list = this.dao.getParticipants(roomID, filter, user);
        return list;
    }

    public List<Entity> getLectureModeParticipants(int roomID, EntityFilter filter) {
        List<Entity> list = this.dao.getLectureModeParticipants(roomID, filter);
        return list;
    }

    public Long getCountParticipants(int roomID) {
        Long number = this.dao.getCountParticipants(roomID);
        return number;
    }

    public int getEndpointIDForMemberID(int memberID) {
        int rc = this.dao.getEndpointIDForMemberID(memberID);
        return rc;
    }

    public int getMemberIDForEndpointID(int endpointID) {
        int rc = this.dao.getMemberIDForEndpointID(endpointID);
        return rc;
    }

    public String getMemberTypeForEndpointID(int endpointID) {
        String rc = this.dao.getMemberTypeForEndpointID(endpointID);
        return rc;
    }

    public int getEndpointIDForGUID(String GUID, String endpointType) {
        int rc = this.dao.getEndpointIDForGUID(GUID, endpointType);
        return rc;
    }

    public String getGUIDForEndpointID(int endpointID, String endpointType) {
        String rc = this.dao.getGUIDForEndpointID(endpointID, endpointType);
        return rc;
    }

    public int getRoomIdForConference(int conferenceId) {
    	int rc = this.dao.getRoomIdForConference(conferenceId);
        return rc;
    }
    
    public int getRoomIDForEndpointID(int endpointID) {
        int rc = this.dao.getRoomIDForEndpointID(endpointID);
        return rc;
    }

    public boolean isEndpointIDinRoomID(int endpointID, int roomID) {
        boolean rc = this.dao.isEndpointIDinRoomID(endpointID, roomID);
        return rc;
    }

    /**
     * Returns the GUID for the EndpointId/Room Id combination
     *
     * @param endpointID
     * @param roomID
     * @return
     */
    public String getGUIDForEndpointIdInConf(int endpointID, int roomID) {
        String guid = null;
        try {
            guid = dao.getGUIDForEndpointIdInConf(endpointID, roomID);
        } catch (DataAccessException dae) {
            logger.warn("Endpoint Id " + endpointID + " is not in Conf " + roomID);
        }
        return guid;
    }

    /**
     * Sends Challenge to the Endpoint through VidyoManager. IMPORTANT: There
     * are AOP interceptors working on this method flow. Please refer to the
     * spring interceptors xml before changing any business logic in this
     * method.
     *
     * @param guestID
     * @param GUID
     */
    public void bindGuestToEndpoint(int guestID, String GUID) {
        // Empty method for interception by line license interceptors
        // IMPORTANT: below call should not be removed - refer to the
        // interceptors xml
        bindEndpointService.bindGuestToEndpoint(guestID, GUID);
        User user = this.user.getUserForGuestID(guestID);
        if (user != null) {
            Guestconf guest_settings = this.system.getGuestConf(user.getTenantID());
            Group group = new Group();
            if (guest_settings.getGroupID() != 0) {
                group = this.group.getGroup(guest_settings.getGroupID());
            } else {
                group.setUserMaxBandWidthOut(2000);
                group.setUserMaxBandWidthIn(2000);
            }

            // send VCAP message BindUser
            RootDocument doc = RootDocument.Factory.newInstance();
            Message message = doc.addNewRoot();
            RequestMessage req_message = message.addNewRequest();
            // Unique RequestID
            long bindUserRequestID = Long.valueOf(PortalUtils.generateNumericKey(9));
            req_message.setRequestID(bindUserRequestID);

            BindUserRequest bind_user_req = req_message.addNewBindUser();

            bind_user_req.setUsername(user.getUsername());
            UserProfileType user_prof_type = bind_user_req.addNewUserProfile();

            user.setMemberName(user.getMemberName());

            user_prof_type.setDisplayName(user.getMemberName());
            Language userLang = this.system.getSystemLang();
            user_prof_type.setLanguage(userLang.getLangCode());

            String challenge = null;
            boolean usePak2 = false;
            if (user.getPak2() != null) {
                challenge = SecureRandomUtils.generateHashKey(47);
                usePak2 = true;
            } else {
                challenge = PortalUtils.generateKey(16);
            }

            this.user.generateSAKforGuest(user.getMemberID(), challenge, bindUserRequestID, usePak2);
            bind_user_req.setChallenge(challenge);

            BirrateLimitSet bit_rate_limits = user_prof_type.addNewBitrateLimits();

            BitrateLimitType bit_rate_limit_up = bit_rate_limits.addNewBitrateLimit();
            MediaChannelType bit_rate_channel_up = bit_rate_limit_up.addNewMediaChannel();
            bit_rate_channel_up.setMediaType(MediaType.NONE);
            bit_rate_channel_up.setMediaSource(MediaSourceType.LOCAL);
            bit_rate_limit_up.setBitrate(1000 * group.getUserMaxBandWidthOut());

            BitrateLimitType bit_rate_limit_down = bit_rate_limits.addNewBitrateLimit();
            MediaChannelType bit_rate_channel_down = bit_rate_limit_down.addNewMediaChannel();
            bit_rate_channel_down.setMediaType(MediaType.NONE);
            bit_rate_channel_down.setMediaSource(MediaSourceType.REMOTE);
            bit_rate_limit_down.setBitrate(1000 * group.getUserMaxBandWidthIn());
            // Add DSCP values
            try {
                DSCPValueSet dscpValueSet = user_prof_type.addNewDSCPValues();
                DSCPValueSet dscpValueSetFromConfig = system.getDSCPConfiguration(user.getMemberName(),
                        user.getTenantID());
                dscpValueSet.setMediaAudio(dscpValueSetFromConfig.getMediaAudio());
                dscpValueSet.setMediaVideo(dscpValueSetFromConfig.getMediaVideo());
                dscpValueSet.setMediaData(dscpValueSetFromConfig.getMediaData());
                dscpValueSet.setSignaling(dscpValueSetFromConfig.getSignaling());
                dscpValueSet.setOAM(dscpValueSetFromConfig.getOAM());
            } catch (Exception e) {
                logger.error("Error while setting DSCP values  ", e);
            }
            XmlOptions opts = new XmlOptions();
            opts.setCharacterEncoding("UTF-8");

            String info = doc.xmlText(opts);

            DataHandler binduser = new DataHandler(info, "text/plain; charset=UTF-8");

            try {
                sendMessageToEndpoint(GUID, binduser);
                // asyncSendMessageToEndpoint(GUID, binduser);
            } catch (Exception e) {
                logger.error("Error sending bind VCAP to guest GUID: " + GUID);
            }
        } else {
            logger.error("Guest user is not valid, bind guest won't succeed for guestId " + guestID + " EndpointGUID "
                    + GUID);
        }
    }

    public void unbindUserFromEndpoint(String GUID, UserUnbindCode reasonCode) {
        logger.debug("unbind user from endpoint GUID: " + GUID);
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage req_message = message.addNewRequest();
        long bindUserRequestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        req_message.setRequestID(bindUserRequestID);

        UnbindUserRequest unbind_user_req = req_message.addNewUnbindUser();
        if (reasonCode == null) {
            reasonCode = UserUnbindCode.UNKNOWN;
        }
        unbind_user_req.setReason(reasonCode.toString());

        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler unbinduser = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(GUID, unbinduser);
        } catch (Exception e) {
            // info log level since often endpoint is offline already so this
            // will fail
            logger.info("exception sending VCAP msg to unbind user from endpoint: GUID - " + GUID + " Error - "
                    + e.getMessage());
        }
    }

    public void sendMessageToEndpoint(String endpointGUID, DataHandler content) throws Exception {
        VidyoManagerServiceStub stub = null;
        try {
            stub = this.system.getVidyoManagerServiceStubWithAUTH();

            // Info for endpoint - go thru VM
            InfoForEndpointRequest info_req = new InfoForEndpointRequest();

            EndpointGUID_type0 to_guid = new EndpointGUID_type0();
            to_guid.setEndpointGUID_type0(endpointGUID);
            info_req.setEndpointGUID(to_guid);

            info_req.setContent(content);

            InfoForEndpointResponse info_resp = stub.infoForEndpoint(info_req);

            if (info_resp != null) {
                DataHandler resp = info_resp.getContent();
            }

        } catch (Exception e) {
            logger.error("exception sending VCAP msg: GUID - " + endpointGUID + " Error - " + e.getMessage());
            throw e;
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void asyncSendMessageToEndpoint(String endpointGUID, DataHandler content) throws Exception {
        VidyoManagerServiceStub stub = null;
        try {
            stub = this.system.getVidyoManagerServiceStubWithAUTH();

            // Info for endpoint - go thru VM
            InfoForEndpointRequest info_req = new InfoForEndpointRequest();

            EndpointGUID_type0 to_guid = new EndpointGUID_type0();
            to_guid.setEndpointGUID_type0(endpointGUID);
            info_req.setEndpointGUID(to_guid);

            info_req.setContent(content);

            VidyoManagerServiceCallbackHandler callback = new VidyoManagerServiceCallbackHandler() {
                @Override
                public void receiveResultinfoForEndpoint(InfoForEndpointResponse resp) {
                    DataHandler content = resp.getContent();
                }
            };

            stub.startinfoForEndpoint(info_req, callback);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void getAffinities() {
        VidyoManagerServiceStub stub = null;
        try {
            stub = this.system.getVidyoManagerServiceStubWithAUTH();

            GetGroupsRequest get_groups_req = new GetGroupsRequest();

            GetGroupsResponse get_groups_resp = stub.getGroups(get_groups_req);

            if (get_groups_resp != null) {
                this.dao.resetRouters(); // set all Routers as inactive
                NEGroupType[] groups = get_groups_resp.getGroups();

                for (NEGroupType neGroupType : groups) {
                    if (!neGroupType.getGroupID().equalsIgnoreCase("DEFAULT")) {
                        this.dao.addRouter(neGroupType.getGroupID(), neGroupType.getGroupName());
                    }
                }
            }

        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public VirtualEndpoint getVirtualEndpointForEndpointID(int endpointID) {
        VirtualEndpoint rc = this.dao.getVirtualEndpointForEndpointID(endpointID);
        return rc;
    }

    public RecorderEndpoint getRecorderEndpointForEndpointID(int endpointID) {
        RecorderEndpoint rc = this.dao.getRecorderEndpointForEndpointID(endpointID);
        return rc;
    }

    public void checkBindUserChallengeResponse(String endpointGUID, String challengeResponse, long bindUserResponseID) {
        // This Endpoint is always Desktop, so use the EndpointService to get
        // all info using a single DB call
        Endpoint endpoint = endpointService.getEndpointDetails(endpointGUID);

        if (endpoint != null && endpoint.getMemberID() != 0) {
            String SAK = "";
            long bindUserRequestID = 0l;
            User user = null;
            if (endpoint.getMemberType().equalsIgnoreCase("R")) {
                user = this.user.getUserForBindChallengeResponse(endpoint.getMemberID());
                SAK = user.getSak();
                bindUserRequestID = user.getBindUserRequestID();
            } else if (endpoint.getMemberType().equalsIgnoreCase("G")) {
                // TODO - change to single query
                SAK = this.user.getSAKforGuest(endpoint.getMemberID());
                bindUserRequestID = this.user.getBindUserRequestIDforGuest(endpoint.getMemberID());
            }

            if (bindUserResponseID == bindUserRequestID) {
                if (challengeResponse.compareTo(SAK) != 0) {
                    // Unbind user from Endpoint
                    logger.debug("Wrong challengeResponse - " + challengeResponse + " from ID - " + endpointGUID);
                    logger.debug("Right challengeResponse - " + SAK);
                    this.user.unlinkEndpointFromUser(endpoint.getMemberID(), endpointGUID,
                            UserUnbindCode.BIND_CHALLENGE_FAILURE);
                    logger.debug("Unbind user with memberID - " + endpoint.getMemberID());
                } else {
                    // challenge response is good - update authorized flag to 1
                    Endpoint updateEndpoint = new Endpoint();
                    updateEndpoint.setGuid(endpointGUID);
                    updateEndpoint.setStatus(1);
                    updateEndpoint.setAuthorized(1);
                    // BY default set consume line to True
                    int consumesLine = 1;
                    List<String> excludedRoles = new ArrayList<String>();
                    String licenseVersion = getLicenseVersion();
                    if (LicensingServiceImpl.LIC_VERSION_20.equals(licenseVersion)
                            || LicensingServiceImpl.LIC_VERSION_21.equals(licenseVersion)) {
                        excludedRoles = excludedRolesv20_21;
                    } else if (LicensingServiceImpl.LIC_VERSION_22.equals(licenseVersion)) {
                        excludedRoles = excludedRolesv22;
                    }
                    if (user != null && excludedRoles.contains(user.getUserRole())) {
                        consumesLine = 0;
                    }
                    updateEndpoint.setConsumesLine(consumesLine);

                    // reset features now, to support downgrades/upgrades of
                    // endpoints like UVD
                    // note, newer endpoints will call setEndpointDetails after
                    // bind
                    // note, we purposely do not reset CDR info at this time
                    logger.debug("Reset endpoint features: " + endpointGUID);
                    EndpointFeatures endpointFeatures = new EndpointFeatures();
                    endpointService.updateEndpointFeatures(endpointGUID, endpointFeatures);

                    logger.debug("Bind endpoint " + endpointGUID + "user with memberID - " + endpoint.getMemberID()
                            + "updateEndpoint ->" + updateEndpoint.getConsumesLine());
                    this.user.setAuthorizedFlagForEndpoint(updateEndpoint);

                    Integer tenantID = TenantContext.getTenantId();
                    if (tenantID == null) {
                        tenantID = 1;
                    }
                    bindEndpointService.sendBindSuccessMessage(endpointGUID, tenantID);

                    Alert alert = new Alert(endpointGUID, "Online", endpoint.getMemberID());
                    NotificationInfo notificationInfo = new NotificationInfo();
                    notificationInfo.setAlert(alert);
                    
                    tenantID = this.dao.getTenantIDForGUID(endpointGUID, endpoint.getEndpointType());
                    TenantConfiguration tenantConfiguration = this.tenant.getTenantConfiguration(tenantID);

	        			try {
	        				if (tenantConfiguration.getVidyoNotificationUrl() != null) {
	        					notificationInfo.setVidyoStatusNotificationUrl(tenantConfiguration.getVidyoNotificationUrl());
	        					notificationInfo.setVidyoUsername(tenantConfiguration.getVidyoUsername());
	        					if (StringUtils.isNotBlank(tenantConfiguration.getVidyoPassword())) {
	        						notificationInfo.setVidyoPassword(tenantConfiguration.getVidyoPassword());
	        						notificationInfo
	        								.setPlainTextVidyoPassword(VidyoUtil.decrypt(tenantConfiguration.getVidyoPassword()));
	        					}
	        				}
	        			} catch (Exception e) {
	        				logger.error("Exception while decrypting the vidyo notification password -> " + e.getMessage());
	        			}
	        			logger.debug("sending notification->" + notificationInfo);
                    statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
                }
            }
        }
    }

    public String getRoomNameForGUID(String guid) {
        logger.debug("Entering getRoomNameForGUID() of ConferenceServiceImpl");
        String roomName = dao.getRoomNameForGUID(guid);
        if (roomName == null) {
            logger.error("RoomName is null for the GUID-" + guid);
        }
        logger.debug("Exiting getRoomNameForGUID() of ConferenceServiceImpl");
        return roomName;
    }

    protected void checkRoleAndValidateFreePorts(Member member) throws OutOfPortsException {

        String licenseVersion = getLicenseVersion();
        boolean consumesLine = false;

        // UVL will consume a line by default
        if (licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) {
            consumesLine = true;
        }

        if (member.getMemberID() != 0) {
            Endpoint endpoint = endpointService.getEndpointStatus(member.getEndpointGUID(), member.getMemberID());
            if (endpoint != null) {
                consumesLine = endpoint.getConsumesLine() == 1;
            }
        }

        if (licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)
                || ((licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_21)
                        || licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) && consumesLine)) {
            // Check for free lines at system level, if the method passes
            // withour any exception, proceed with tenant level check
            checkForAvailableLinesSystemLevel(licenseVersion);
            // Free lines are available at the system level, check if the tenant
            // is provisioned to use it
            int tenantID = member.getTenantID();
            if (tenantID == 0) {
                tenantID = TenantContext.getTenantId();
            }
            Tenant t = this.tenant.getTenant(tenantID);
            int totalPorts = t.getPorts();
            int usedPorts = this.tenant.getUsedNumOfPorts(tenantID);
            int freePorts = totalPorts - usedPorts;
            if (freePorts <= 0) {
                logger.error(
                        "No free line available for the call during tenant level check totalLines {}, usedLines {}",
                        totalPorts, usedPorts);
                throw new OutOfPortsException("No free line available for the call during tenant level check");
            }
        }

    }

    public void checkIfEndpointIsSupported(Room room, String guid) throws EndpointNotSupportedException {
        // if room is in lecture mode, endpoint must support lecture mode
        if (room != null && room.getLectureMode() != null && room.getLectureMode() == 1) {
            TenantConfiguration tenantConfiguration = this.tenant.getTenantConfiguration(room.getTenantID());
            if (tenantConfiguration.getLectureModeStrict() == 1) {
                EndpointFeatures endpointFeatures = endpointService.getEndpointFeaturesForGuid(guid);
                if (endpointFeatures != null && !endpointFeatures.isLectureModeSupported()) {
                    addEndpointUnsupportedToTransactionHistory(room, guid, "LectureMode");
                    throw new LectureModeNotSupportedException();
                }
            }
        }
    }

    /**
     * Checks if line is available system wide for the call. If no line is
     * available will throw OutOfPortsException, This method wont return
     * anything, but should be invoked for side effects.
     *
     * @param licenseVersion
     * @throws OutOfPortsException
     */
    private void checkForAvailableLinesSystemLevel(String licenseVersion) throws OutOfPortsException {
        SystemLicenseFeature licenseFeature = licensingService.getSystemLicenseFeature("Ports");
        // system wide check for available ports/lines
        long usedLines = system.getTotalPortsInUse(licenseVersion);
        String totalLicensedLines = licenseFeature == null ? "0" : licenseFeature.getLicensedValue();
        int totalLines = Integer.parseInt(totalLicensedLines);
        if (totalLines - usedLines <= 0) {
            logger.error("No free line available for the call during system wide check totalLines {}, usedLines {}",
                    totalLines, usedLines);
            throw new OutOfPortsException("No free line available for the call during system wide check");
        }
    }

    /**
     * Validates the available ports for P2P calls
     *
     * @param fromMember
     *            Member from which call starts
     * @param toMember
     *            Member to reach
     * @throws OutOfPortsException
     *             Exception when the lines/ports are exhausted
     */
    protected void checkRoleAndValidateFreePorts(Member fromMember, Member toMember) throws OutOfPortsException {
        // Get License Value
        String licenseVersion = getLicenseVersion();
        // Do not check ports for P2P call with version 20
        if (licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)) {
            return;
        } else if (licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_21)
                || licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_22)) {
            int fromPortCount = 0;

            Endpoint endpoint = null;

            endpoint = endpointService.getEndpointStatus(fromMember.getEndpointGUID(), fromMember.getMemberID());
            if (endpoint != null && endpoint.getConsumesLine() == 1) {
                ++fromPortCount;
            }

            int toPortCount = 0;
            endpoint = null;

            endpoint = endpointService.getEndpointStatus(toMember.getEndpointGUID(), toMember.getMemberID());
            if (endpoint != null && endpoint.getConsumesLine() == 1) {
                ++toPortCount;
            }

            // Same tenant, combine the ports count
            if (fromMember.getTenantID() == toMember.getTenantID()) {
                // Check for free lines at system level, if the method passes
                // withour any exception, proceed with tenant level check
                checkForAvailableLinesSystemLevel(licenseVersion);
                int tenantID = fromMember.getTenantID();
                Tenant t = this.tenant.getTenant(tenantID);
                int totalPorts = t.getPorts();
                int usedPorts = this.tenant.getUsedNumOfPorts(tenantID);
                int freePorts = totalPorts - usedPorts;
                if ((fromPortCount + toPortCount) > freePorts) {
                    logger.error(
                            "No free line available for the P2P call during tenant level check totalLines {}, usedLines {}",
                            totalPorts, usedPorts);
                    throw new OutOfPortsException("No free line available for the P2P call during tenant level check");
                }
                return;
            }

            if (fromPortCount == 1) {
                // Check for free lines at system level, if the method passes
                // withour any exception, proceed with tenant level check
                checkForAvailableLinesSystemLevel(licenseVersion);
                int tenantID = fromMember.getTenantID();
                Tenant t = this.tenant.getTenant(tenantID);
                int totalPorts = t.getPorts();
                int usedPorts = this.tenant.getUsedNumOfPorts(tenantID);
                int freePorts = totalPorts - usedPorts;
                if (fromPortCount > freePorts) {
                    logger.error(
                            "No free line available for the P2P call during tenant level check totalLines {}, usedLines {}",
                            totalPorts, usedPorts);
                    throw new OutOfPortsException("No free line available for the P2P call during tenant level check");
                }
            }

            if (toPortCount == 1) {
                // Check for free lines at system level, if the method passes
                // withour any exception, proceed with tenant level check
                checkForAvailableLinesSystemLevel(licenseVersion);
                int tenantID = toMember.getTenantID();
                Tenant t = this.tenant.getTenant(tenantID);
                int totalPorts = t.getPorts();
                int usedPorts = this.tenant.getUsedNumOfPorts(tenantID);
                int freePorts = totalPorts - usedPorts;
                if (toPortCount > freePorts) {
                    logger.error(
                            "No free line available for the P2P call during tenant level check totalLines {}, usedLines {}",
                            totalPorts, usedPorts);
                    throw new OutOfPortsException("No free line available for the P2P call during tenant level check");
                }
            }

        }
    }

    private boolean isRoomCapacityReached(Room roomToJoin) {
        return roomToJoin.getOccupantsCount() >= roomToJoin.getRoomMaxUsers();
    }

    /**
     * Utility method to get the License Version
     *
     * @return licenseVersion String
     */
    protected String getLicenseVersion() {
        // Default License Value
        String licenseVersion = LicensingServiceImpl.LIC_VERSION_20;
        // Check License Version
        SystemLicenseFeature licenseFeature = null;
        try {
            licenseFeature = licensingService.getSystemLicenseFeature("Version");
        } catch (Exception e) {
            logger.error("Error while getting License Version", e);
        }
        if (licenseFeature != null) {
            licenseVersion = licenseFeature.getLicensedValue();
        }

        return licenseVersion;
    }

    /**
     * This method is likely unused.
     *
     * @param memberID
     * @throws LeaveConferenceException
     */
    public void disconnectMeFromConference(int memberID) throws LeaveConferenceException {
        int endpointID = getEndpointIDForMemberID(memberID);

        if (endpointID <= 0) {
            throw new LeaveConferenceException("Endpoint doesn't exist");
        }

        int roomID = getRoomIDForEndpointID(endpointID);

        if (roomID <= 0) {
            throw new LeaveConferenceException("Active Room doesn't exist");
        }

        leaveTheConference(endpointID, roomID, CallCompletionCode.BY_SELF);

    }

    /**
     * Pauses Recording of the Conference. Sends the Vcap message to the
     * recorder.
     *
     * @param guid:
     *            String
     * @return int
     * @throws Exception
     */
    public void pauseRecording(String guid) throws Exception {
        logger.debug("pauseRecording for GUID = " + guid);
        sendMessageToRecorder(MediaControlCommandType.PAUSE, guid);
        // update record for GUID in Conference table for video = 0
        this.dao.updateEndpointVideo(guid, 0);
    }

    /**
     * Resumes the recording of the Conference. Sends the Vcap message to the
     * recorder.
     *
     * @param guid:
     *            string
     * @return int
     * @throws Exception
     */
    public void resumeRecording(String guid) throws Exception {
        logger.debug("resumeRecording for guid = " + guid);
        sendMessageToRecorder(MediaControlCommandType.UNPAUSE, guid);
        // update record for GUID in Conference table for video = 1
        this.dao.updateEndpointVideo(guid, 1);
    }

    /**
     * Resumes the recording of the Conference. Sends the Vcap message to the
     * recorder.
     *
     * @param commandType:
     *            MediaControlCommandType.Enum
     * @param guid:
     *            string
     */
    protected void sendMessageToRecorder(MediaControlCommandType.Enum commandType, String guid) {
        // send VCAP message BindUser
        RootDocument doc = RootDocument.Factory.newInstance();
        Message message = doc.addNewRoot();
        RequestMessage reqMessage = message.addNewRequest();
        long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
        reqMessage.setRequestID(requestID);

        MediaControlRequest recordingReq = reqMessage.addNewMediaControl();

        MediaChannelType mediaType = recordingReq.addNewMediaChannel();
        mediaType.setMediaType(MediaType.RECORDING);
        mediaType.setMediaSource(MediaSourceType.LOCAL);
        recordingReq.setCommand(commandType);
        XmlOptions opts = new XmlOptions();
        opts.setCharacterEncoding("UTF-8");

        String info = doc.xmlText(opts);

        DataHandler dataHandler = new DataHandler(info, "text/plain; charset=UTF-8");

        try {
            sendMessageToEndpoint(guid, dataHandler);
        } catch (Exception e) {
            logger.error(commandType + "Recording for GUID = " + guid + e.getMessage());
        }
    }

    public boolean leaveTheConference(String GUID, CallCompletionCode callCompletionCode)
            throws LeaveConferenceException {

        logger.debug("internal request to leave conference for GUID: " + GUID);
        String confName = dao.getConfNameByGuid(GUID);
        try {

            if (confName != null) {
                sendLeaveConfRequest(GUID, confName);
                Conference conference = this.dao.getUniqueIDofConference(confName);
                String uniqueCallID = null;
                if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
                    logger.error("No conference found to leave.");
                } else {
                    uniqueCallID = conference.getUniqueCallId();
                    cdrCollectionService.updateEndpointCallCompletionCode(uniqueCallID, GUID, callCompletionCode);
                }
            }

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            // TODO - Check with Yuriy
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new LeaveConferenceException();
        }

        return true;

    }

    /**
     * Sends a request to VidyoManager to remove the EndpointGUID from
     * Conference
     *
     * @param GUID
     * @param confName
     * @return
     * @throws NoVidyoManagerException
     * @throws NotLicensedException
     * @throws EndpointNotExistException
     * @throws ConferenceNotExistException
     * @throws ResourceNotAvailableException
     */
    private boolean sendLeaveConfRequest(String GUID, String confName)
            throws NoVidyoManagerException, NotLicensedException, EndpointNotExistException,
            ConferenceNotExistException, ResourceNotAvailableException {

        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();
        RemoveEndpointRequest remove_ep_req = new RemoveEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        remove_ep_req.setConferenceID(confID);

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(GUID);
        remove_ep_req.setEndpointGUID(guid);

        try {
            RemoveEndpointResponse remove_user_resp = stub.removeEndpoint(remove_ep_req);

            if (remove_user_resp != null && remove_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
                // Will remove in updateEndpointStatus after collect CDR
                // Delete record from Conferences table
                // this.dao.removeEndpointFromConference(member.getEndpointGUID(),
                // endpointType);
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new EndpointNotExistException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        }
        return true;
    }

    /**
     * Checks if the Member has joined the specified Room.
     *
     * @param memberID
     * @param roomID
     * @return
     */
    public boolean isMemberPresentInRoom(int memberID, int roomID) {
        return dao.isMemberPresentInRoom(memberID, roomID);
    }

    /**
     *
     * @param roomToJoin
     * @param inviter
     *            - from Member
     * @param invitee
     *            - to Member
     * @return
     * @throws OutOfPortsException
     * @throws EndpointNotExistException
     * @throws InviteConferenceException
     */
    public String inviteParticipantToConference(Room roomToJoin, Member inviter, Member invitee, String toEndpointType)
            throws OutOfPortsException, EndpointNotExistException, InviteConferenceException,
            EndpointNotSupportedException {
        // Move this to Interceptor
        checkRoleAndValidateFreePorts(invitee);

        if (isRoomCapacityReached(roomToJoin)) {
            String errMsg = "Room capacity is reached.";
            logger.error(errMsg);
            throw new InviteConferenceException(errMsg);
        }

        checkIfEndpointIsSupported(roomToJoin, invitee.getEndpointGUID());

        String confName = generateConferenceName(roomToJoin, tenant.getTenant(roomToJoin.getTenantID()).getTenantURL());

        String uniqueCallId = null;

        if (!this.isEndpointReady(toEndpointType, invitee.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallId = createConferenceUniqueCallID();
            logger.info("endpoint not online, queueing: " + invitee.getEndpointGUID());
            this.dao.addEndpointToConferenceRecordQueue(uniqueCallId, confName, "C", roomToJoin,
                    invitee.getEndpointGUID(), toEndpointType, "N");
        } else {

            try {
                uniqueCallId = createConference(confName, roomToJoin.getRoomMaxUsers());
            } catch (Exception e) {
                logger.error("Exception while creating conference", e.getMessage());
                throw new InviteConferenceException(e.getMessage());
            }
            
            // Before sending vcap for inviting endpoint, check if the extData is present and reset it
            if(StringUtils.isNotBlank(invitee.getExtData())) {
            		int updatedCount = endpointService.resetExtData(invitee.getEndpointGUID());
            		if(logger.isDebugEnabled()) {
            			logger.debug("Updated Endpoint Row count -> " + updatedCount);
            		
            		}
            }

            try {
                inviteEndpointToConference(uniqueCallId, confName, roomToJoin, inviter, invitee, toEndpointType);
            } catch (Exception e) {
                logger.error("Exception while inviting Endpoint", e.getMessage());
                throw new InviteConferenceException(e.getMessage());
            }

            postConferenceHandle(roomToJoin, invitee);
        }
        return uniqueCallId;
    }

    public String inviteParticipantToConferenceQueue(String virtualEndpointGUID) throws OutOfPortsException,
            EndpointNotExistException, InviteConferenceException, EndpointNotSupportedException {
        logger.info(" handling queued CONF TO LEGACY call: " + virtualEndpointGUID);

        VirtualEndpoint ve = this.endpointService.getVirtualEndpointForEndpointGUID(virtualEndpointGUID);

        int roomId = this.dao.getRoomIDForEndpointGUIDConferenceRecord(virtualEndpointGUID);

        Room roomToJoin = this.room.getRoomDetailsForConference(roomId);

        Member inviter = this.member.getMember(roomToJoin.getMemberID());

        // legacy device
        Member invitee = new Member();
        invitee.setMemberName(ve.getDisplayName());
        invitee.setRoomExtNumber(ve.getDisplayExt());
        invitee.setEndpointGUID(virtualEndpointGUID);
        invitee.setRoleName("Legacy");

        return inviteParticipantToConference(roomToJoin, inviter, invitee, "V");
    }

    /**
     *
     * @param roomToJoin
     * @param inviter
     * @param invitee
     * @return
     * @throws OutOfPortsException
     * @throws EndpointNotExistException
     * @throws InviteConferenceException
     */
    public String inviteLegacyToConference(Room roomToJoin, Member inviter, Member invitee)
            throws InviteConferenceException, EndpointNotExistException, OutOfPortsException {
        String toGUID = null;
        String toEndpointType = null;
        if (invitee.getEndpointId() == 0) { // Legacy Device
            // check if this tenant can use VidyoGateway for the prefix
            List<Tenant> canCallToTenants = this.tenant.canCallToTenants(roomToJoin.getTenantID());
            boolean canCall = this.canTenantUseGatewayforAddress(canCallToTenants, invitee.getRoomExtNumber(),
                    roomToJoin.getTenantID());
            if (!canCall) {
                throw new CannotCallTenantInviteException();
            }
            toGUID = this.getVirtualGUIDForLegacyDevice(inviter, invitee, null, roomToJoin.getTenantID());
            toEndpointType = "V";
        }

        if (StringUtils.isBlank(toGUID)) {
            throw new EndpointNotExistException();
        }
        invitee.setEndpointGUID(toGUID);

        String uniqueCallId = null;

        try {
            uniqueCallId = inviteParticipantToConference(roomToJoin, inviter, invitee, toEndpointType);
        } catch (Exception e) {
            this.dao.updateVirtualEndpointInfo(toGUID, "", "", 0);
            throw new InviteConferenceException("Exception while inviting Legacy Endpoint", e);
        }

        sendGwServiceEndpointMessageIfAny();

        return uniqueCallId;
    }

    private void sendGwServiceEndpointMessageIfAny() {
        EndpointMessage endpointMessage = this.gwServiceEndpointMessageHolder.get();
        if (endpointMessage != null) {
            try {
                logger.info(
                        "Sending saved VCAP message to service endpoint: " + endpointMessage.getServiceEndpointGUID());
                this.sendMessageToEndpoint(endpointMessage.getServiceEndpointGUID(),
                        endpointMessage.getJoinToLegacyContent());
            } catch (Exception e) {
                logger.error("Failed to send GW service VCAP message to: " + endpointMessage.getServiceEndpointGUID());
            } finally {
                this.gwServiceEndpointMessageHolder.remove();
            }
        }
    }

    protected void addSpontaneousEndpointToConference(String confName, String endpointGUID, String externalIP)
            throws NoVidyoManagerException, ConferenceNotExistException {
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();
        AddSpontaneousEndpointRequest addSpontaneousEndpointRequest = new AddSpontaneousEndpointRequest();
        addSpontaneousEndpointRequest.setConferenceID(confName);
        SpontaneousEndpointInfo spontaneousEndpointInfo = new SpontaneousEndpointInfo();

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(endpointGUID);
        spontaneousEndpointInfo.setEndpointGUID(guid);

        spontaneousEndpointInfo.setEndpointName("cmapp"); // TODO
        spontaneousEndpointInfo.setExternalAddress(externalIP);
        spontaneousEndpointInfo.setIsSecure(false); // TODO
        spontaneousEndpointInfo.setLocalAddress(externalIP); // TODO
        spontaneousEndpointInfo.setLocationTag("Default"); // TODO
        spontaneousEndpointInfo.setClearance(Clearance_type0.Moderator);
        spontaneousEndpointInfo.setClientType(ClientType_type0.Control);
        addSpontaneousEndpointRequest.setEndpoint(spontaneousEndpointInfo);

        try {
            AddSpontaneousEndpointResponse addSpontaneousEndpointResponse = stub
                    .addSpontaneousEndpoint(addSpontaneousEndpointRequest);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            // TODO fix me
        } catch (ConferenceNotExistFaultException e) {
            try { // hack for VM - if cannot create a conference for given
                    // parameters - delete it!!!
                this.deleteConference(confName);
            } catch (Exception ignored) {
            }
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }

    }

    /**
     *
     * @param confName
     * @param maxUsers
     * @return
     */
    protected String createConference(String confName, int maxUsers) throws NoVidyoManagerException,
            ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException {

        // Do not send WS call to VM in case of conference exist in Portal DB
        Conference conference = this.dao.getUniqueIDofConference(confName);
        String uniqueCallId = null;
        if (conference == null || StringUtils.isBlank(conference.getUniqueCallId())) {
            // continue
        } else {
            if (conference.isConferenceCreatedInVidyoManager()) {
                logger.info("VM conference was created earlier: " + confName);
                return conference.getUniqueCallId();
            }
        }

        // Create conference
        CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);
        createConferenceRequest.setConferenceID(confID);

        createConferenceRequest.setNumParticipants(new BigInteger(String.valueOf(maxUsers)));
        createConferenceRequest.setClassOfConference(ClassOfConference_type0.VGA);

        Duration one_hour_period = new Duration();
        one_hour_period.parseTime("1H0M0S");
        createConferenceRequest.setDuration(one_hour_period);

        // Router details are not needed for create confernece call

        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        try {
            CreateConferenceResponse createConferenceResponse = stub.createConference(createConferenceRequest);

            if (createConferenceResponse != null
                    && createConferenceResponse.getStatus().getValue().equalsIgnoreCase("OK")) {
                // success
                if (conference != null && !conference.isConferenceCreatedInVidyoManager()) {
                    logger.info("VM conference created with confName created earlier: " + confName);
                    uniqueCallId = conference.getUniqueCallId();
                    logger.info("Using existing uniqueCallId: " + uniqueCallId);
                } else {
                    logger.info("VM conference created with new confName: " + confName);
                    uniqueCallId = createConferenceUniqueCallID();
                    logger.info("Using new uniqueCallId: " + uniqueCallId);
                }
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (InvalidArgumentFaultException e) {
            try { // hack for VM - if cannot create a conference for given
                    // parameters - delete it!!!
                this.deleteConference(confName);
            } catch (Exception ignored) {
            }
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        }

        return uniqueCallId;

    }

    private static String createConferenceUniqueCallID() {
        String id = PortalUtils.generateUniqueCallID();
        logger.debug("createConferenceUniqueCallID: " + id);
        return id;
    }

    private boolean inviteEndpointToConference(String uniqueCallId, String confName, Room roomToJoin, Member inviter,
            Member invitee, String endpointType) throws NoVidyoManagerException, NotLicensedException,
            EndpointNotExistException, ConferenceNotExistException, ResourceNotAvailableException {
        boolean endpointinvited = false;

        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();

        InviteEndpointRequest invite_endpoint_req = new InviteEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        // conference room is fromRoomID
        invite_endpoint_req.setConferenceID(confID);

        EndpointCallPref from_endpoint = new EndpointCallPref();

        String username = null;

        if (roomToJoin.getRoomType().equalsIgnoreCase("Public")) {
            username = roomToJoin.getRoomName();
            from_endpoint.setUser(roomToJoin.getRoomName());
        } else {
            // RoomToJoin's Owner Name, not inviter's member name
            username = roomToJoin.getOwnerName();
            from_endpoint.setUser(roomToJoin.getOwnerName());
        }

        String fromEndpointGUID = inviter.getEndpointGUID();
        if (fromEndpointGUID == null) {
            fromEndpointGUID = "0"; // invite from admin
        }
        EndpointGUID_type0 from_guid = new EndpointGUID_type0();
        from_guid.setEndpointGUID_type0(fromEndpointGUID);
        from_endpoint.setEndpointGUID(from_guid);

        // Bandwidth is a minimum between room's group and member's group
        int minBandWidthIn = roomToJoin.getUserMaxBandWidthIn();
        int minBandWidthOut = roomToJoin.getUserMaxBandWidthOut();

        from_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        from_endpoint.setMaxUplinkBw(BigInteger.ZERO);

        from_endpoint.addGroupId("Default");

        invite_endpoint_req.setFromUser(from_endpoint);

        // Set Second Endpoint (who will received invitation)
        EndpointCallPref to_endpoint = new EndpointCallPref();
        to_endpoint.setUser(invitee.getMemberName());

        EndpointGUID_type0 to_guid = new EndpointGUID_type0();
        to_guid.setEndpointGUID_type0(invitee.getEndpointGUID());
        to_endpoint.setEndpointGUID(to_guid);

        // Bandwidth is equals to FromUser
        to_endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        to_endpoint.setMaxUplinkBw(BigInteger.ZERO);
        // Get Router - not in use now, uses Default [will revisit later]
        to_endpoint.addGroupId("Default");

        to_endpoint = populateEndpointCallPref(to_endpoint, roomToJoin, invitee, endpointType);

        if ("V".equalsIgnoreCase(endpointType)) {
            // Pass info for Legacy Device in ApplicationData
        		// Customization - If callFromIdentifier (customization) is specified, use that for from field
			String gwData = getInfoForGateway(
					StringUtils.isNotBlank(inviter.getCallFromIdentifier()) ? inviter.getCallFromIdentifier()
							: inviter.getRoomExtNumber(),
					invitee.getRoomExtNumber(), username);
            DataHandler appData = new DataHandler(gwData, "text/plain; charset=UTF-8");
            to_endpoint.setApplicationData(appData);
        } else if ("D".equalsIgnoreCase(endpointType)) {
            to_endpoint.setApplicationData(getPortalAppData(roomToJoin, inviter));
        }

        invite_endpoint_req.setToEPInfo(to_endpoint);

        invite_endpoint_req.setAlertCaller(false); // ToDo set Alert Caller to
                                                    // true if ready in VD
        invite_endpoint_req.setRingLengthSeconds(new BigInteger("30"));

        try {
            logger.info("endpoint online, inviting: " + invitee.getEndpointGUID());
            // Hold conference record untill status of endpoint will be "Busy"
            this.dao.addEndpointToConferenceRecord(uniqueCallId, confName, "C", roomToJoin, invitee.getEndpointGUID(),
                    endpointType, "N");

            InviteEndpointResponse invite_endpoint_resp = stub.inviteEndpoint(invite_endpoint_req);

            if (invite_endpoint_resp != null && invite_endpoint_resp.getStatus().getValue().equalsIgnoreCase("OK")) {

            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getFaultMessage().getErrorMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }

        return endpointinvited;
    }

    /**
     * Creates the Conference and adds the Member to the Conference
     *
     * @param roomToJoin
     * @param joiningMember
     * @throws OutOfPortsException
     * @throws JoinConferenceException
     */
    public void joinTheConference(Room roomToJoin, Member joiningMember)
            throws OutOfPortsException, JoinConferenceException, EndpointNotSupportedException {
        String endpointType = "D";
        checkRoleAndValidateFreePorts(joiningMember);
        // Step 1 - Create Conference Call to VidyoManager
        // If scheduled room, get the room name from the scheduledRoomConfName
        // property
        String confName = (roomToJoin.getSchRoomConfName() != null ? roomToJoin.getSchRoomConfName()
                : roomToJoin.getRoomName()) + "@" + tenant.getTenant(roomToJoin.getTenantID()).getTenantURL();

        checkIfEndpointIsSupported(roomToJoin, joiningMember.getEndpointGUID());

        String uniqueCallId = null;

        if (!this.isEndpointReady(endpointType, joiningMember.getEndpointGUID())) {
            // add to conferenceRecord
            uniqueCallId = createConferenceUniqueCallID();
            logger.info("endpoint not online, queueing: " + joiningMember.getEndpointGUID());

            this.dao.addEndpointToConferenceRecordQueue(uniqueCallId, confName, "C", roomToJoin,
                    joiningMember.getEndpointGUID(), endpointType, "Y");
        } else {

            try {

                uniqueCallId = createConference(confName, roomToJoin.getRoomMaxUsers());

            } catch (Exception e) {
                logger.error("Error while Creating Conference", e.getMessage());
                throw new JoinConferenceException();
            }

            try {
                addEndpointToConference(uniqueCallId, confName, roomToJoin, joiningMember, endpointType);
            } catch (Exception e) {
                logger.error(
                        "Error adding Endpoint to the Conference - EndpointId - " + joiningMember.getEndpointGUID(),
                        e.getMessage());
                throw new JoinConferenceException();
            }

            postConferenceHandle(roomToJoin, joiningMember);
        }
    }

    /**
     *
     * @param uniqueCallId
     * @param roomToJoin
     * @param joiningMember
     * @throws NoVidyoManagerException
     * @throws ConferenceNotExistException
     * @throws NotLicensedException
     * @throws ResourceNotAvailableException
     * @throws EndpointNotExistException
     */
    private void addEndpointToConference(String uniqueCallId, String confName, Room roomToJoin, Member joiningMember,
            String endpointType) throws NoVidyoManagerException, ConferenceNotExistException, NotLicensedException,
            ResourceNotAvailableException, EndpointNotExistException {

        // Add Endpoint to conference
        AddEndpointRequest add_ep_req = new AddEndpointRequest();

        ConferenceID_type0 confID = new ConferenceID_type0();
        confID.setConferenceID_type0(confName);

        add_ep_req.setConferenceID(confID);
        add_ep_req.setDeleteConference(false);

        EndpointCallPref endpoint = new EndpointCallPref();

        endpoint.setUser(joiningMember.getMemberName());

        EndpointGUID_type0 guid = new EndpointGUID_type0();
        guid.setEndpointGUID_type0(joiningMember.getEndpointGUID());
        endpoint.setEndpointGUID(guid);

        // Bandwidth is a minimum between room's group and member's group
        endpoint.setMaxDownlinkBw(BigInteger.ZERO);
        endpoint.setMaxUplinkBw(BigInteger.ZERO);

        // We dont use GroupId, let it be default
        endpoint.addGroupId("Default");

        populateEndpointCallPref(endpoint, roomToJoin, joiningMember, endpointType);

        if ("D".equalsIgnoreCase(endpointType)) {
            endpoint.setApplicationData(getPortalAppData(roomToJoin, null));
        }
        add_ep_req.setEndpoint(endpoint);
        VidyoManagerServiceStub stub = this.system.getVidyoManagerServiceStubWithAUTH();
        try {

            // Hold conference record untill status of endpoint will be "Busy"
            this.dao.addEndpointToConferenceRecord(uniqueCallId, confName, "C", roomToJoin,
                    joiningMember.getEndpointGUID(), endpointType, "Y");

            AddEndpointResponse add_user_resp = stub.addEndpoint(add_ep_req);

            if (add_user_resp != null && add_user_resp.getStatus().getValue().equalsIgnoreCase("OK")) {
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (GeneralFaultException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException();
        } catch (ConferenceNotExistFaultException e) {
            logger.error(e.getMessage());
            // clean records for this conference in Portal DB
            dao.removeConferenceRecord(confName);
            throw new ConferenceNotExistException();
        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (NotLicensedFaultException e) {
            logger.error(e.getMessage());
            throw new NotLicensedException();
        } catch (EndpointNotExistFaultException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new EndpointNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getMessage());
            throw new ResourceNotAvailableException();
        } catch (DataAccessException dke) {
            logger.warn("Insertion failed for the confernce record {}, {}", confName, dke.getMessage());
            // Insertion failed because of duplicate key (GUID - caused by stuck
            // records) - so check the last status update time for Endpoint
            Endpoint endpointDetails = endpointService.getEndpointDetails(joiningMember.getEndpointGUID());
            if (endpointDetails != null && TimeUnit.SECONDS.toSeconds(
                    Calendar.getInstance().getTime().getTime() - endpointDetails.getUpdateTime().getTime()) > 10) {
                logger.warn(
                        "Endpoint last status update happened before 10 seconds, so deleting the old conference record {}",
                        joiningMember.getEndpointGUID());
                // If the last update happened more than 10 seconds ago, delete
                // the old record and insert the new one
                dao.cleanupConferenceRecord(joiningMember.getEndpointGUID());
                // insert the new record
                dao.addEndpointToConferenceRecord(uniqueCallId, confName, "C", roomToJoin,
                        joiningMember.getEndpointGUID(), endpointType, "Y");
                logger.warn("Adding new record with updated conf info {}, {}, {}", uniqueCallId, confName,
                        joiningMember.getEndpointGUID());
            } else {
                // send command to the VM to remove the endpoint from conference
                removeEndpointFromConference(roomToJoin, joiningMember, endpointType);
                logger.warn("removed endpoint from conf {}, {}, {}", uniqueCallId, confName,
                        joiningMember.getEndpointGUID());
            }
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    /**
     *
     * @param roomToJoin
     * @param joiningMember
     */
    private void postConferenceHandle(Room roomToJoin, Member joiningMember) {

        // lecture mode notification
        if (roomToJoin.getLectureMode() == 1) {

            int tenantID = roomToJoin.getTenantID();
            TenantConfiguration tenantConfiguration = this.tenant.getTenantConfiguration(tenantID);
            if (roomToJoin.getMemberID() == joiningMember.getMemberID()
                    && tenantConfiguration.getWaitingRoomsEnabled() == 1
                    && tenantConfiguration.getWaitUntilOwnerJoins() == 1) {

                // no lecture mode inform for room owner if using waiting rooms

            } else {
                try {
                    this.lectureModeInform(roomToJoin.getRoomID(), joiningMember.getEndpointGUID());
                } catch (Exception ignored) {
                    logger.error("lecture mode inform exception: " + ignored.getMessage());
                }
            }
        } else {
            // STEP 3 - mute endpoint if room is muted NOT done for lecture mode
            // as it is handled above
            if (roomToJoin.getRoomMuted() == 1) {
                try {
                    this.muteAudio(joiningMember.getEndpointGUID());
                } catch (Exception ignored) {
                }
            }
        }

        // STEP 3b - mute endpoint if room is muted
        if (roomToJoin.getRoomVideoMuted() == 1) {
            try {
                this.stopVideo(joiningMember.getEndpointGUID());
            } catch (Exception ignored) {
            }
        }

        // STEP 4 - silence endpoint if room is silenced
        if (roomToJoin.getRoomSilenced() == 1) {
            try {
                this.silenceAudio(joiningMember.getEndpointGUID());
            } catch (Exception ignored) {
            }
        }

        // STEP 4b - silence endpoint if room is silenced
        if (roomToJoin.getRoomVideoSilenced() == 1) {
            try {
                this.silenceVideo(joiningMember.getEndpointGUID());
            } catch (Exception ignored) {
            }
        }

        // STEP 5 - privacy mode based on profile of the room
        if (roomToJoin.getProfileID() != 0) {
            String profileXML = this.dao.getRoomProfile(roomToJoin.getProfileID());
            if (profileXML != null) {
                // parse a VCAP request message
                ProfileDocument xmlDoc = null;
                try {
                    xmlDoc = ProfileDocument.Factory.parse(profileXML, new XmlOptions().setLoadSubstituteNamespaces(
                            Collections.singletonMap("", "http://www.vidyo.com/RoomProfile.xsd")));
                    RoomProfileType profile = xmlDoc.getProfile();
                    // audio
                    if (!profile.getAudioEnabled()) {
                        try {
                            this.silenceAudio(joiningMember.getEndpointGUID());
                        } catch (Exception e) {
                            logger.error("Exception while silencing audio", e);
                        }
                    }
                    // video
                    if (!profile.getVideoEnabled()) {
                        try {
                            this.silenceVideo(joiningMember.getEndpointGUID());
                        } catch (Exception e) {
                            logger.error("Exception while silencing video", e);
                        }
                    }
                } catch (XmlException e) {
                    logger.error("Exception while processing Room Profile", e);
                }
            }
        }

        if(roomToJoin.getRoomSpeakerMuted() == 1) {
            List<String> guids = new ArrayList<>(1);
            guids.add(joiningMember.getEndpointGUID());
            silenceSpeaker(guids, roomToJoin.getRoomSpeakerMuted() ^ 1, roomToJoin.getRoomID(), false);
        }

    }

    public Long getCountParticipants(String conferenceName) {
        return dao.getCountParticipants(conferenceName);
    }

    public int removeExternalLink(ExternalLink externalLink) throws NoVidyoManagerException {
        logger.debug("Entering removeExternalLink(). externalLinkID = " + externalLink.getExternalLinkID());
        // 0 - success & -1 failure
        int respStatus = 0;
        String mySystemID = this.system.getVidyoManagerID();
        VidyoManagerServiceStub stub = null;
        try {
            stub = this.system.getVidyoManagerServiceStubWithAUTH();

            RemoveExternalLinkRequest req = new RemoveExternalLinkRequest();

            if (mySystemID.equalsIgnoreCase(externalLink.getFromSystemID())) {
                // my portal want to join federated conference
                req.setLocalConferenceID(externalLink.getFromConferenceName());
                req.setRemoteConferenceID(externalLink.getToConferenceName());
                req.setRemoteEntityID(externalLink.getToSystemID());
            } else {
                // my portal is host of federated conference
                req.setLocalConferenceID(externalLink.getToConferenceName());
                req.setRemoteConferenceID(externalLink.getFromConferenceName());
                req.setRemoteEntityID(externalLink.getFromSystemID());
            }

            logger.debug("removeExternalLink(). Try stub.removeExternalLink(req)");
            RemoveExternalLinkResponse resp = stub.removeExternalLink(req);

            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (!status.equalsIgnoreCase(Status_type0._OK)) {
                    logger.error("Error while deleting the external link from VidyoManager status {}, ExternalLink {}",
                            status, externalLink);
                    respStatus = -1;
                }
            } else {
                logger.debug("removeExternalLink(). Response of stub.removeExternalLink(req) is null.");
            }

        } catch (InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new NoVidyoManagerException(e.getFaultMessage().getErrorMessage());
        } catch (GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new NoVidyoManagerException(e.getFaultMessage().getErrorMessage());
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new NoVidyoManagerException(e.getMessage());
        } catch (NotLicensedFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new NoVidyoManagerException(e.getFaultMessage().getErrorMessage());
        } catch (ResourceNotAvailableFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new NoVidyoManagerException(e.getFaultMessage().getErrorMessage());
        } catch (EndpointNotExistFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new NoVidyoManagerException(e.getFaultMessage().getErrorMessage());
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }

        logger.debug("Exiting removeExternalLink(). externalLinkID = " + externalLink.getExternalLinkID());
        return respStatus;
    }

    /**
     * Returns ExternalLink by toConferenceName
     *
     * @param toConfName
     * @return
     */
    public ExternalLink getExternalLink(String toConfName) {
        ExternalLink rc = federationDao.getExternalLink(toConfName);
        return rc;
    }

    /**
     * Deletes Guest Endpoints after the call is complete
     *
     * @return
     */
    public int cleanGuestsAndEndpoints() {
        return dao.cleanGuestsAndEndpoints();
    }

    /**
     * Cleans Conference and Conference Record for the user while logging in.
     *
     * @param endpointGuid
     */
    public void cleanConferenceForLinkedUser(String endpointGuid) {
        /*
         * When the user tries to login and if there is a stuck record or a
         * conference, the records have to be cleaned up and delete conf to be
         * sent to VM if the ep is the last participant.
         */
        checkAndDeleteConference(endpointGuid, "D");
    }

    private String getInfoForGateway(String fromExt, String toExt, String fromUsername) {
        // Pass info for VidyoGateway in ApplicationData
        StringBuffer infoForGW = new StringBuffer();
        infoForGW.append("<info>");
        infoForGW.append("<toExtension>").append(toExt).append("</toExtension>");
        infoForGW.append("<fromExtNumber>").append(fromExt).append("</fromExtNumber>");
        infoForGW.append("<fromUserName>").append(fromUsername).append("</fromUserName>");
        infoForGW.append("</info>");
        return infoForGW.toString();
    }

    private DataHandler getPortalAppData(Room roomToJoin, Member inviter) {
        Integer tenantID = null;
        if (roomToJoin != null) {
            tenantID = roomToJoin.getTenantID();
        }
        if (tenantID == null || tenantID == 0) {
            tenantID = TenantContext.getTenantId();
        }

        StringBuilder xmlBlob = new StringBuilder();
        xmlBlob.append("<portalAppData>");

        if (tenantID != null) {
            EndpointSettings es = this.system.getAdminEndpointSettings(tenantID);
            xmlBlob.append("<clientConfig>");
            xmlBlob.append("<minMediaPort>").append(es.getMinMediaPort()).append("</minMediaPort>");
            xmlBlob.append("<maxMediaPort>").append(es.getMaxMediaPort()).append("</maxMediaPort>");
            xmlBlob.append("<vrProxyConfig>").append(es.isAlwaysUseVidyoProxy() ? "ALWAYS" : "AUTO")
                    .append("</vrProxyConfig>");
            xmlBlob.append("</clientConfig>");
        }

        xmlBlob.append("<conference>");

        if (inviter != null) {
            if (inviter.getRoomTypeID() == 1 || inviter.getRoomTypeID() == 3) { // personal
                                                                                // or
                                                                                // legacy
                xmlBlob.append("<inviterEntityID>").append(inviter.getRoomID()).append("</inviterEntityID>");
            } else { // public or scheduled
                xmlBlob.append("<inviterEntityID>").append(this.member.getPersonalRoomId(inviter.getMemberID()))
                        .append("</inviterEntityID>");
            }
        }
        if (roomToJoin != null) {
            xmlBlob.append("<conferenceId>").append(roomToJoin.getRoomID()).append("</conferenceId>");
            xmlBlob.append("<recurring>").append(roomToJoin.getRecurring()).append("</recurring>");
        }

        xmlBlob.append("</conference>");

        xmlBlob.append("</portalAppData>");
        DataHandler appData = new DataHandler(xmlBlob.toString(), "text/plain; charset=UTF-8");
        return appData;
    }

    /**
     * Utility method to create the conference name based on the Room Type
     *
     * @param room
     * @param tenantUrl
     * @return
     */
    public String generateConferenceName(Room room, String tenantUrl) {
        String confName = null;
        ScheduledRoomResponse scheduledRoomResponse = null;
		// Starting 18.1.0 - Scheduled rooms follow Tenant prefix
		// Handle New Scheduled Scheme which has Tenant Prefix instead of dedicated Scheduled Room Prefix 
        Configuration configuration = system.getConfiguration(RoomServiceImpl.SCHEDULED_ROOM_PREFIX);
        String dedciatedSchRoomPrefix = null;
        if(configuration != null && configuration.getConfigurationValue() != null) {
        		dedciatedSchRoomPrefix = configuration.getConfigurationValue();
        }
        // If the room is having dedicated scheduled room prefix
		if (room.getRoomType().equalsIgnoreCase("Scheduled") && room.getRoomPIN() != null
				&& (StringUtils.isNotBlank(dedciatedSchRoomPrefix)
						&& room.getRoomExtNumber() != null && room.getRoomExtNumber().startsWith(dedciatedSchRoomPrefix))) {
            scheduledRoomResponse = this.room.generateSchRoomExtPin(room.getMemberID(),
                    Integer.parseInt(room.getRoomPIN().trim()));
            if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
                logger.debug("Scheduled room - {}, PIN - {}", scheduledRoomResponse.getRoomExtn(),
                        scheduledRoomResponse.getPin());
                confName = scheduledRoomResponse.getRoomExtn();
                if (room.getRoomPIN() != null) {
                    confName += scheduledRoomResponse.getPin();
                }

            }
        } else {
            confName = room.getRoomName();
        }

        return confName + "@" + tenantUrl;
    }

    /**
     * Adds a TransactionHistory record for the call disconnect operation.
     *
     * @param tenantName
     * @param uniqueCallId
     * @param disconnectedUser
     * @return
     */
    protected int recordCallDisconnectDetails(String tenantName, int tenantId, String endpointGUID,
            String uniqueCallId) {
        if (uniqueCallId == null || uniqueCallId.trim().isEmpty()) {
            return 0;
        }
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionName("Call Disconnect");
        transactionHistory.setTenantName(tenantName);
        transactionHistory.setTransactionResult("SUCCESS");
        StringBuilder transactionParams = new StringBuilder();
        Endpoint endpoint = null;
        if (endpointGUID != null) {
            endpoint = endpointService.getEndpointDetail(endpointGUID);
        } else {
            transactionParams.append("Disconnected User: All Users");
        }
        if (endpoint != null) {
            transactionParams.append("Disconnected User:");
            if (endpoint.getEndpointType().equals("D") && endpoint.getMemberID() != 0) {
                if (endpoint.getMemberType().equalsIgnoreCase("G")) {
                    Guest guest = guestService.getGuest(endpoint.getMemberID(), tenantId);
                    if (guest != null) {
                        transactionParams.append(guest.getGuestName());
                    }
                } else {
                    Member memb = this.member.getMember(endpoint.getMemberID());
                    String membName = "Unknown";
                    if (memb != null) {
                        membName = memb.getMemberName();
                    }
                    transactionParams.append(membName);
                }

            } else if (endpoint.getEndpointType().equals("V")) {
                transactionParams.append("Legacy");
            } else if (endpoint.getEndpointType().equals("R")) {
                transactionParams.append("Recorder");
            }
        }
        transactionParams.append(";UniqueCallID:").append(uniqueCallId);
        if (endpointGUID != null) {
            transactionParams.append(";EndpointGUID:").append(endpointGUID);
        }
        transactionHistory.setTransactionParams(transactionParams.toString());
        return transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
    }

    public int addEndpointUnsupportedToTransactionHistory(Room room, String endpointGUID, String feature) {
        if (room == null || endpointGUID == null || endpointGUID.trim().isEmpty()) {
            return 0;
        }
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionName("Endpoint Feature");
        transactionHistory.setTenantName(room.getTenantName());
        transactionHistory.setTransactionResult("FAILURE");
        StringBuilder transactionParams = new StringBuilder();
        transactionParams.append("Feature:" + feature);
        Endpoint endpoint = endpointService.getEndpointDetail(endpointGUID);
        if (endpoint != null) {
            transactionParams.append(";User:");
            if (endpoint.getEndpointType().equals("D") && endpoint.getMemberID() != 0) {
                if (endpoint.getMemberType().equalsIgnoreCase("G")) {
                    Guest guest = guestService.getGuest(endpoint.getMemberID(), room.getTenantID());
                    if (guest != null) {
                        transactionParams.append(guest.getGuestName());
                    }
                } else {
                    Member memb = this.member.getMember(endpoint.getMemberID());
                    String membName = "Unknown";
                    if (memb != null) {
                        membName = memb.getMemberName();
                    }
                    transactionParams.append(membName);
                }

            } else if (endpoint.getEndpointType().equals("V")) {
                transactionParams.append("Legacy");
            } else if (endpoint.getEndpointType().equals("R")) {
                transactionParams.append("Recorder");
            }
        }
        transactionParams.append(";EndpointGUID:").append(endpointGUID);
        transactionHistory.setTransactionParams(transactionParams.toString());
        return transactionService.addTransactionHistoryWithUserLookup(transactionHistory);

    }

    /**
     *
     * @param endpointID
     * @param roomToDisconnect
     * @param callCompletionCode
     */
    public void disconnectEndpointFromConference(int endpointID, Room roomToDisconnect,
            CallCompletionCode callCompletionCode) throws LeaveConferenceException {
        String endpointType = "D";
        String GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
        if (GUID == null || GUID.trim().equalsIgnoreCase("")) {
            endpointType = "V";
            GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
            if (GUID == null || GUID.trim().equalsIgnoreCase("")) {
                endpointType = "R";
                GUID = this.dao.getGUIDForEndpointID(endpointID, endpointType);
            }
        }
        if (StringUtils.isEmpty(GUID)) {
            logger.error("Not a valid GUID and cannot be sent to VidyoManager for disconnect -" + GUID);
            throw new LeaveConferenceException("Invalid Participant");
        }
        // Convert into fake Member object
        Member member = new Member();
        member.setEndpointGUID(GUID);
        String uniqueCallID = null;
        try {
            removeEndpointFromConference(roomToDisconnect, member, endpointType);
            uniqueCallID = dao.getUniqueCallIDByGuid(GUID);
            cdrCollectionService.updateEndpointCallCompletionCode(uniqueCallID, GUID, callCompletionCode);
        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new LeaveConferenceException();
        }
        recordCallDisconnectDetails(roomToDisconnect.getTenantName(),
                tenant.getTenant(roomToDisconnect.getTenantName()).getTenantID(), GUID, uniqueCallID);
    }

    public void disconnectEndpointFromConference(String GUID, Room roomToDisconnect,
            CallCompletionCode callCompletionCode) throws LeaveConferenceException {

        // Convert into fake Member object
        Member member = new Member();
        member.setEndpointGUID(GUID);
        String uniqueCallID = null;
        try {
            removeEndpointFromConference(roomToDisconnect, member);
            uniqueCallID = dao.getUniqueCallIDByGuid(GUID);
            cdrCollectionService.updateEndpointCallCompletionCode(uniqueCallID, GUID, callCompletionCode);
        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
        } catch (EndpointNotExistException e) {
            logger.error(e.getMessage());
            // special case - VirtualEndpoints gone but Online in DB
            String vGUID = e.getMessage();
            updateEndpointStatus(vGUID, "Offline", "portal", null, -1l, null, null);
            throw new LeaveConferenceException();
        }
        recordCallDisconnectDetails(roomToDisconnect.getTenantName(),
                tenant.getTenant(roomToDisconnect.getTenantName()).getTenantID(), GUID, uniqueCallID);
    }

    public void disconnectAllConferencesForTenant(int tenantId) throws NoVidyoManagerException,
            ConferenceNotExistException, NotLicensedException, ResourceNotAvailableException {
        List<String> conferenceNames = this.dao.getConferencesForTenant(tenantId);
        if (conferenceNames != null && conferenceNames.size() > 0) {
            for (String conferenceName : conferenceNames) {
                this.disconnectAll(conferenceName);
            }
        }
    }

    private void checkAndStopWaitingRoomIfNecessary(String joiningGUID) {

        int roomID = this.dao.getRoomIDForWaitingRoomStop(joiningGUID);

        if (roomID != 0) {

            lectureModeDao.setLectureModeFlag(roomID, false);

            try {
                this.room.unmuteRoom(roomID);
                this.updateEndpointAudioForAll(roomID, false);
            } catch (Exception e) {
                logger.error("Error unmuting roomID: " + roomID + ", message: " + e.getMessage());
            }

            List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
            DataHandler vcapMsg = LectureModeServiceImpl.getVCAPLectureModeChange(false, null);
            for (String guid : endpointGUIDs) {
                try {
                    if (!guid.equals(joiningGUID)) { // don't send to room owner
                        this.sendMessageToEndpoint(guid, vcapMsg);
                    }
                } catch (Exception e) {
                    logger.error("Failed to send lecture mode OFF to guid: " + guid);
                    logger.error(e.getMessage());
                }
            }

            // remove raised hands and presenter
            lectureModeDao.clearLectureModeState(roomID);

            // audit
            Room roomToJoin = this.room.getRoomDetailsForConference(roomID);
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setTransactionName("Waiting Room");
            transactionHistory.setTenantName(roomToJoin.getTenantName());
            transactionHistory.setTransactionResult("SUCCESS");
            StringBuilder transactionParams = new StringBuilder();
            transactionParams.append("Room: " + roomToJoin.getRoomName());
            transactionParams.append(";Ended by room owner join");
            transactionHistory.setTransactionParams(transactionParams.toString());
            this.transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
        }

    }

    public LectureModeDao getLectureModeDao() {
        return lectureModeDao;
    }

    /**
     * Deletes conference records created by gateway to/from legacy calls that
     * end up being queued but the virtual endpoint then is never brought online
     * by the gateway (very rare, if ever!)
     *
     * @return
     */
    public int clearStaleConferenceRecords() {
        return this.dao.clearStaleConferenceRecords();
    }

    /**
     * @param componentsService
     *            the componentsService to set
     */
    public void setComponentsService(ComponentsService componentsService) {
        this.componentsService = componentsService;
    }

    /**
     * Returns a flag indicating if the requesting user can control the room or
     * not. Following scenarios will return true: 1. If the user is the owner of
     * the room 2. If the user is ADMIN/OPERATOR and the room also belongs to
     * the same tenant 3. If the user is a particpant in the conference and
     * knows moderator PIN.
     *
     * @param user
     * @param room
     * @param moderatorPIN
     * @return
     */
    protected boolean canControlConference(User user, Room room, String moderatorPIN) {

        // Owner can control his room
        if (user.getMemberID() == room.getMemberID()) {
            return true;
        }

        // ADMIN or OPERATOR user type in the same tenant can control anyone
        // else's room
        if ((user.getUserRole().equalsIgnoreCase(MemberRoleEnum.ADMIN.getMemberRole())
                || user.getUserRole().equalsIgnoreCase(MemberRoleEnum.OPERATOR.getMemberRole()))
                && user.getTenantID() == room.getTenantID()) {
            return true;
        }

		// Participating user knows the moderator PIN and can control the
		// conference
		if (isMemberPresentInRoom(user.getMemberID(), room.getRoomID())
				&& (moderatorPIN != null && moderatorPIN.equalsIgnoreCase(room.getRoomModeratorPIN()))) {
			return true;
		}

        // Return false (cannot control) if all condition checks fail.
        return false;
    }

    /**
     * Sends VCAP message for silencing endpoint's speaker.
     *
     * @param guid
     */
    public void silenceSpeaker(List<String> guids, int flag, int roomId, boolean allEndpoints) {
        // Update all or specific endpoints as per allEndpoints flag.
        if(allEndpoints) {
            dao.updateEndpointSpeakerStatusAll(roomId, flag);
        } else {
            dao.updateEndpointSpeakerStatus(guids.get(0), flag);
        }
        guids.forEach(guid -> {
            logger.debug("Silence Speaker for guid = " + guid);
            // send VCAP message for silencing speaker
            RootDocument doc = RootDocument.Factory.newInstance();
            Message message = doc.addNewRoot();
            RequestMessage reqMessage = message.addNewRequest();
            long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
            reqMessage.setRequestID(requestID);
            MediaControlRequest silenceSpeakerRequest = reqMessage.addNewMediaControl();
            MediaChannelType mediaChannelType = silenceSpeakerRequest.addNewMediaChannel();
            mediaChannelType.setMediaType(MediaType.SPEAKER);
            mediaChannelType.setMediaSource(MediaSourceType.LOCAL);
            silenceSpeakerRequest.setCommand(MediaControlCommandType.UNMUTE);
            // Silence the Endpoint's speaker if the flag is 1
            if (flag == 0) {
                silenceSpeakerRequest.setCommand(MediaControlCommandType.MUTE);
            }
            XmlOptions opts = new XmlOptions();
            opts.setCharacterEncoding("UTF-8");
            String info = doc.xmlText(opts);
            DataHandler silenceSpeaker = new DataHandler(info, "text/plain; charset=UTF-8");
            try {
                sendMessageToEndpoint(guid, silenceSpeaker);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Silence Speaker for GUID Failed -" + guid + e.getMessage());
            }
        });
    }

}