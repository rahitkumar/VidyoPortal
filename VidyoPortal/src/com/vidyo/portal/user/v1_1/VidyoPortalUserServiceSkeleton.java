/**
 * VidyoPortalUserServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.user.v1_1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.types.PositiveInteger;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.axis2.transport.http.TransportHeaders;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.vidyo.bo.Banners;
import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Control;
import com.vidyo.bo.EndpointBehavior;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Invite;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.RecorderPrefix;
import com.vidyo.bo.Room;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.UserUnbindCode;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.AuthenticationType;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.WsAuthentication;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.email.InviteEmailContentForInviteRoom;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.loginhistory.MemberLoginHistory;
import com.vidyo.bo.member.MemberMini;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.db.endpoints.EndpointFeatures;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.dao.BaseQueryFilter;
import com.vidyo.model.CallDetails;
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
import com.vidyo.service.RoomServiceImpl;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.TestCallService;
import com.vidyo.service.TestCallServiceImpl;
import com.vidyo.service.PortalService.PortalFeaturesEnum;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.control.ConferenceControlService;
import com.vidyo.service.conference.response.ConferenceControlResponse;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import com.vidyo.service.endpointbehavior.EndpointBehaviorService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.CancelInvitationException;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.EndpointNotExistException;
import com.vidyo.service.exceptions.JoinConferenceException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NoVidyoReplayException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.exceptions.TestCallException;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.service.lecturemode.LectureModeService;
import com.vidyo.service.lecturemode.request.LectureModeControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantsRequest;
import com.vidyo.service.lecturemode.request.MemberHandUpdateRequest;
import com.vidyo.service.lecturemode.response.HandUpdateResponse;
import com.vidyo.service.lecturemode.response.LectureModeControlResponse;
import com.vidyo.service.lecturemode.response.LectureModeParticipantsResponse;
import com.vidyo.service.loginhistory.MemberLoginHistoryService;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.service.passwdhistory.MemberPasswordHistoryService;
import com.vidyo.service.room.PublicRoomCreateRequest;
import com.vidyo.service.room.RoomAccessDetailsResponse;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.security.token.PersistentTokenService;
import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;
import com.vidyo.service.user.AccessKeyResponse;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.utils.ImageUtils;
import com.vidyo.utils.UserAgentUtils;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;
import com.vidyo.ws.configuration.NetworkElementType;

import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.validateExtDataType;

public class VidyoPortalUserServiceSkeleton implements VidyoPortalUserServiceSkeletonInterface {

    protected static final Logger logger = LoggerFactory
            .getLogger(VidyoPortalUserServiceSkeleton.class.getName());

    private IUserService userService;
    private IMemberService memberService;
    private IRoomService roomService;
    private IConferenceService conferenceService;
    private IServiceService serviceService;
    private LicensingService licensingService;
    private ITenantService tenantService;
    private ISystemService systemService;

    @Autowired
    private IReplayService replayService;

    private ReloadableResourceBundleMessageSource ms;
    private VidyoPersistentTokenRepository vidyoPersistentTokenRepository;
    private IpcConfigurationService ipcConfigurationService;
    private ConferenceAppService conferenceAppService;

    @Autowired
    private ConferenceControlService conferenceControlService;

    private PersistentTokenService persistentTokenService;
    private MemberPasswordHistoryService memberPasswordHistoryService;
    private MemberLoginHistoryService memberLoginHistoryService;
    private EndpointService endpointService;
    @Autowired
    private LectureModeService lectureModeService;
    private String thumbNailLocation;
    private TestCallService testCallService;

    @Autowired
    private ComponentsService componentService;
    
    @Autowired
	private ExtIntegrationService extIntegrationService;

    //VPTL-7615 - Integrate test call feature with WebRTC.
    private static final String WEBRTC_TEST_CALL_USERNAME = "WEBRTC_TEST_CALL_USERNAME";
    private static final String DEFAULT_TEST_CALL_USERNAME = "Test-call";
    private static final String WEBRTC_TEST_MEDIA_SERVER ="WEBRTC_TEST_MEDIA_SERVER";
    private static final String SCHEDULED_ROOM_PREFIX = "SCHEDULED_ROOM_PREFIX";

    @Autowired
    private EndpointBehaviorService endpointBehaviorService;

    public String getThumbNailLocation() {
        return thumbNailLocation;
    }

    public void setThumbNailLocation(String thumbNailLocation) {
        this.thumbNailLocation = thumbNailLocation;
    }
    /**
     * @param conferenceAppService the conferenceAppService to set
     */
    public void setConferenceAppService(ConferenceAppService conferenceAppService) {
        this.conferenceAppService = conferenceAppService;
    }

    public void setTestCallService(TestCallService testCallService) {
        this.testCallService = testCallService;
    }

    public TestCallService getTestCallService() {
        return testCallService;
    }


    /**
     * @param vidyoPersistentTokenRepository
     *            the vidyoPersistentTokenRepository to set
     */
    public void setVidyoPersistentTokenRepository(
            VidyoPersistentTokenRepository vidyoPersistentTokenRepository) {
        this.vidyoPersistentTokenRepository = vidyoPersistentTokenRepository;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setMemberService(IMemberService memberService) {
        this.memberService = memberService;
    }

    public void setRoomService(IRoomService roomService) {
        this.roomService = roomService;
    }

    public void setConferenceService(IConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    public void setServiceService(IServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public void setLicensingService(LicensingService licensingService) {
        this.licensingService = licensingService;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

    public void setMs(ReloadableResourceBundleMessageSource ms) {
        this.ms = ms;
    }

    public void setIpcConfigurationService(IpcConfigurationService ipcConfigurationService) {
        this.ipcConfigurationService = ipcConfigurationService;
    }
    /**
     * @param persistentTokenService the persistentTokenService to set
     */
    public void setPersistentTokenService(PersistentTokenService persistentTokenService) {
        this.persistentTokenService = persistentTokenService;
    }

    public void setMemberPasswordHistoryService(MemberPasswordHistoryService memberPasswordHistoryService) {
        this.memberPasswordHistoryService = memberPasswordHistoryService;
    }

    public void setMemberLoginHistoryService(MemberLoginHistoryService memberLoginHistoryService) {
        this.memberLoginHistoryService = memberLoginHistoryService;
    }

    public void setEndpointService(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    public void setLectureModeService(LectureModeService lectureModeService) {
        this.lectureModeService = lectureModeService;
    }

	public void setComponentService(ComponentsService componentService) {
		this.componentService = componentService;
	}

    private void checkLicenseForAllowUserAPIs(MessageContext context)
            throws NotLicensedFaultException {
        TransportHeaders headers = (TransportHeaders) context
                .getProperty(MessageContext.TRANSPORT_HEADERS);
        String user_agent = headers.get("user-agent");
        if (user_agent == null) {
            user_agent = "";
        }

        if (!UserAgentUtils.isVidyoEndpoint(user_agent)) {
            SystemLicenseFeature licensed = licensingService
                    .getSystemLicenseFeature("AllowUserAPIs");
            if (licensed == null
                    || licensed.getLicensedValue().equalsIgnoreCase("false")) {
                NotLicensedFault fault = new NotLicensedFault();
                fault.setErrorMessage("Operation is not licensed");
                NotLicensedFaultException exception = new NotLicensedFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }
    }

    private Entity_type0 getWSEntityFromBOEntity(User user,
            com.vidyo.bo.Entity entity, Map<Integer, Integer> memberIdPersonalRoomIdMap) {
        Entity_type0 ws_entity = new Entity_type0();

        EntityID entityID = new EntityID();
        entityID.setEntityID(String.valueOf(entity.getRoomID()));
        ws_entity.setEntityID(entityID);

        if (entity.getRoomType() != null) {
            if (entity.getRoomType().equalsIgnoreCase("Personal")) {
                ws_entity.setEntityType(EntityType_type0.Member);
                ws_entity.setCanCallDirect(true);
                ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
                EntityID ownerID = new EntityID();
                ownerID.setEntityID(String.valueOf(entity.getRoomID()));
                ws_entity.setOwnerID(ownerID);
            } else if (entity.getRoomType().equalsIgnoreCase("Public")) {
                ws_entity.setEntityType(EntityType_type0.Room);
                ws_entity.setCanCallDirect(false);
                ws_entity.setCanJoinMeeting(entity.getRoomEnabled() == 1);
                EntityID ownerID = new EntityID();
                if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
                    ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
                } else {
                    ownerID.setEntityID(String.valueOf(entity.getRoomID()));
                }
                ws_entity.setOwnerID(ownerID);
            } else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
                ws_entity.setEntityType(EntityType_type0.Legacy);
                ws_entity.setCanCallDirect(true);
                ws_entity.setCanJoinMeeting(false);
                EntityID ownerID = new EntityID();
                ownerID.setEntityID(String.valueOf(entity.getRoomID()));
                ws_entity.setOwnerID(ownerID);
            } else if(entity.getRoomType().equalsIgnoreCase("Scheduled")) {
                ws_entity.setEntityType(EntityType_type0.Room);
                ws_entity.setCanCallDirect(false);
                ws_entity.setCanJoinMeeting(true);
                EntityID ownerID = new EntityID();
                if(memberIdPersonalRoomIdMap != null && memberIdPersonalRoomIdMap.get(entity.getMemberID()) != null) {
                    ownerID.setEntityID(String.valueOf(memberIdPersonalRoomIdMap.get(entity.getMemberID())));
                } else {
                    ownerID.setEntityID(String.valueOf(entity.getRoomID()));
                }
                ws_entity.setOwnerID(ownerID);
            }
        } else {
            ws_entity.setEntityType(EntityType_type0.Member);
            ws_entity.setCanCallDirect(false);
            ws_entity.setCanJoinMeeting(false);
        }

        if(entity.getAllowRecording() == 1) {
            ws_entity.setCanRecordMeeting(true);
        } else {
            ws_entity.setCanRecordMeeting(false);
        }

        if (entity.getMemberID() != 0) {
            ws_entity.setEmailAddress(entity.getEmailAddress());
            Tenant tenantDetail = tenantService.getTenant(entity.getTenantID());
            if(tenantDetail != null && StringUtils.isNotBlank(tenantDetail.getTenantName())) {
            	ws_entity.setTenant(tenantDetail.getTenantName());
            } else {
            	ws_entity.setTenant(entity.getTenantName());
            }

            switch (entity.getLangID()) {
            case 1:
                ws_entity.setLanguage(Language_type0.en);
                break;
            case 2:
                ws_entity.setLanguage(Language_type0.fr);
                break;
            case 3:
                ws_entity.setLanguage(Language_type0.ja);
                break;
            case 4:
                ws_entity.setLanguage(Language_type0.zh_CN);
                break;
            case 5:
                ws_entity.setLanguage(Language_type0.es);
                break;
            case 6:
                ws_entity.setLanguage(Language_type0.it);
                break;
            case 7:
                ws_entity.setLanguage(Language_type0.de);
                break;
            case 8:
                ws_entity.setLanguage(Language_type0.ko);
                break;
            case 9:
                ws_entity.setLanguage(Language_type0.pt);
                break;
            case 10:
                ws_entity.setLanguage(Language_type0.Factory
                        .fromValue(systemService.getSystemLang(
                                entity.getTenantID()).getLangCode()));
                break;
            case 11:
                ws_entity.setLanguage(Language_type0.fi);
                break;
            case 12:
                ws_entity.setLanguage(Language_type0.pl);
                break;
            case 13:
                ws_entity.setLanguage(Language_type0.zh_TW);
                break;
            case 14:
                ws_entity.setLanguage(Language_type0.th);
                break;
            case 15:
                ws_entity.setLanguage(Language_type0.ru);
                break;
            case 16:
                ws_entity.setLanguage(Language_type0.tr);
                break;
            default:
                ws_entity.setLanguage(Language_type0.Factory
                        .fromValue(systemService.getSystemLang(
                                entity.getTenantID()).getLangCode()));
                break;
            }

            if (entity.getModeID() == 1) {
                ws_entity.setMemberMode(MemberMode_type0.Available);
            } else if (entity.getModeID() == 2) {
                ws_entity.setMemberMode(MemberMode_type0.Away);
            } else if (entity.getModeID() == 3) {
                ws_entity.setMemberMode(MemberMode_type0.DoNotDisturb);
            }
        } else {
            EntityID ownerID = new EntityID();
            ownerID.setEntityID(String.valueOf(0));
            ws_entity.setOwnerID(ownerID);
            ws_entity
                    .setLanguage(Language_type0.Factory.fromValue(systemService
                            .getSystemLang(entity.getTenantID()).getLangCode()));
            ws_entity.setMemberMode(MemberMode_type0.Available);
        }

        ws_entity.setDisplayName(StringUtils.isNotBlank(entity.getDisplayName()) ? entity.getDisplayName() : entity.getName());
        ws_entity.setExtension(entity.getExt());
        ws_entity.setDescription(entity
                .getDescription());

        if (entity.getMemberStatus() == 0) {
            ws_entity.setMemberStatus(MemberStatus_type0.Offline);
        } else if (entity.getMemberStatus() == 1) {
            ws_entity.setMemberStatus(MemberStatus_type0.Online);
        } else if (entity.getMemberStatus() == 2) {
            ws_entity.setMemberStatus(MemberStatus_type0.Busy);
        } else if (entity.getMemberStatus() == 3) {
            ws_entity.setMemberStatus(MemberStatus_type0.Ringing);
        } else if (entity.getMemberStatus() == 4) {
            ws_entity.setMemberStatus(MemberStatus_type0.RingAccepted);
        } else if (entity.getMemberStatus() == 5) {
            ws_entity.setMemberStatus(MemberStatus_type0.RingRejected);
        } else if (entity.getMemberStatus() == 6) {
            ws_entity.setMemberStatus(MemberStatus_type0.RingNoAnswer);
        } else if (entity.getMemberStatus() == 7) {
            ws_entity.setMemberStatus(MemberStatus_type0.Alerting);
        } else if (entity.getMemberStatus() == 8) {
            ws_entity.setMemberStatus(MemberStatus_type0.AlertCancelled);
        } else if (entity.getMemberStatus() == 9) {
            ws_entity.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
        } else if (entity.getMemberStatus() == 12) {
            ws_entity.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
        }

        RoomMode_type0 roomMode = new RoomMode_type0();
        // Room URL
        if (entity.getRoomKey() != null && !entity.getRoomKey().isEmpty() && user.getMemberID() == entity.getMemberID()) {
            StringBuilder path = new StringBuilder();
            Tenant tenant = tenantService.getTenant(entity.getTenantID());
            String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            /*path.append(transportName).append("://").append(tenant.getTenantURL());
            path.append("/flex.html?roomdirect.html&key=");
            path.append(entity.getRoomKey());*/

            String joinURL = roomService.getRoomURL(systemService, transportName,
                    tenant.getTenantURL(), entity.getRoomKey());

            path.append(joinURL);
            if (tenantService.isTenantNotAllowingGuests()) {
                path.append("&noguest");
            }

            roomMode.setRoomURL(path.toString());
        }
        // Room Lock
        roomMode.setIsLocked(entity.getRoomLocked() == 1);
        // Room PIN
        roomMode.setHasPIN(entity.getRoomPinned() == 1);
        String roomPIN = entity.getRoomPIN();
        if (roomPIN != null && !roomPIN.equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
            roomMode.setRoomPIN(roomPIN); // roomPIN
        } else {
            // Mask room PIN if the user is not the owner
            roomMode.setRoomPIN("****");
        }
        // Moderator PIN
        roomMode.setHasModeratorPIN(entity.getRoomModeratorPIN() != null);
        String moderatorPIN = entity.getRoomModeratorPIN();
        if (moderatorPIN != null && !moderatorPIN.equalsIgnoreCase("") && user.getMemberID() == entity.getMemberID()) {
            roomMode.setModeratorPIN(moderatorPIN); // moderatorPIN
        } else {
            // Mask moderator PIN if the user is not the owner
            roomMode.setModeratorPIN("****");
        }
        // WebCast related values - only in case of webCastURL is present
/* REMOVED FROM WSDL FILE
        if (entity.getWebCastURL() != null) {
            roomMode.setWebCastURL(entity.getWebCastURL());
            roomMode.setHasWebCastPIN(entity.getWebCastPinned() == 1);
            String webCastPIN = entity.getWebCastPIN();
            if (webCastPIN != null && !webCastPIN.equalsIgnoreCase("")) {
                roomMode.setWebCastPIN(webCastPIN); // webCastPIN
            }
        }
*/

        ws_entity.setRoomMode(roomMode);

        if (entity.getRoomStatus() == 0) {
            ws_entity.setRoomStatus(RoomStatus_type0.Empty);
        } else if (entity.getRoomStatus() == 1) {
            ws_entity.setRoomStatus(RoomStatus_type0.Occupied);
        } else if (entity.getRoomStatus() == 2) {
            ws_entity.setRoomStatus(RoomStatus_type0.Full);
        }

        ws_entity.setVideo(entity.getVideo() == 1);
        ws_entity.setAudio(entity.getAudio() == 1);
        // ToDo - share?
        ws_entity.setAppshare(true);

        if (entity.getRoomOwner() == 1) {
            ws_entity.setCanControl(true);
        } else {
            ws_entity.setCanControl(false);
        }

        if (entity.getSpeedDialID() != 0) {
            ws_entity.setIsInMyContacts(true);
        } else {
            ws_entity.setIsInMyContacts(false);
        }

        if (entity.getRoomType() != null && entity.getRoomType().equalsIgnoreCase("Personal")) {
            if (entity.getPhone1() != null && ! entity.getPhone1().isEmpty()){
                ws_entity.setPhone1(entity.getPhone1());
            }

            if (entity.getPhone2() != null && ! entity.getPhone2().isEmpty()){
                ws_entity.setPhone2(entity.getPhone2());
            }

            if (entity.getPhone3() != null && ! entity.getPhone3().isEmpty()){
                ws_entity.setPhone3(entity.getPhone3());
            }

            if (entity.getDepartment() != null && ! entity.getDepartment().isEmpty()){
                ws_entity.setDepartment(entity.getDepartment());
            }

            if (entity.getTitle() != null && ! entity.getTitle().isEmpty()){
                ws_entity.setTitle(entity.getTitle());
            }

            if (entity.getInstantMessagerID() != null && ! entity.getInstantMessagerID().isEmpty()){
                ws_entity.setInstantMessagerID(entity.getInstantMessagerID());
            }

            if (entity.getLocation() != null && ! entity.getLocation().isEmpty()){
                ws_entity.setLocation(entity.getLocation());
            }

            if (entity.getThumbnailUpdateTime() != null){
                Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
                if (userImageConf != null && StringUtils.isNotBlank(userImageConf.getConfigurationValue()) && userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
                    TenantConfiguration tenantConfiguration = this.tenantService
                        .getTenantConfiguration(TenantContext.getTenantId());
                    if (tenantConfiguration.getUserImage() == 1) {
                        Calendar updateTime = Calendar.getInstance();;
                        updateTime.setTime(entity.getThumbnailUpdateTime());
                        ws_entity.setThumbnailUpdateTime(updateTime);
                    }
                }
            }
        }
        return ws_entity;
    }

    private EntityDetails_type0 getWSEntityDetailsFromBOEntity(com.vidyo.bo.Entity entity,
            Entity_type0 ws_entity,	Configuration userImageConf, TenantConfiguration tenantConfiguration) {
        EntityDetails_type0 ws_entityDetails = new EntityDetails_type0();
        ws_entityDetails.setEntityID(ws_entity.getEntityID());
        ws_entityDetails.setEntityType(ws_entity.getEntityType());
        ws_entityDetails.setCanCallDirect(ws_entity.getCanCallDirect());
        ws_entityDetails.setCanJoinMeeting(ws_entity.getCanJoinMeeting());
        ws_entityDetails.setOwnerID(ws_entity.getOwnerID());
        ws_entityDetails.setCanRecordMeeting(ws_entity.getCanRecordMeeting());
        ws_entityDetails.setEmailAddress(ws_entity.getEmailAddress());
        ws_entityDetails.setTenant(ws_entity.getTenant());
        ws_entityDetails.setLanguage(ws_entity.getLanguage());
        ws_entityDetails.setMemberMode(ws_entity.getMemberMode());
        ws_entityDetails.setDisplayName(ws_entity.getDisplayName());
        ws_entityDetails.setExtension(ws_entity.getExtension());
        ws_entityDetails.setDescription(ws_entity.getDescription());
        ws_entityDetails.setMemberStatus(ws_entity.getMemberStatus());
        ws_entityDetails.setRoomMode(ws_entity.getRoomMode());
        ws_entityDetails.setRoomStatus(ws_entity.getRoomStatus());
        ws_entityDetails.setVideo(ws_entity.getVideo());
        ws_entityDetails.setAudio(ws_entity.getAudio());
        ws_entityDetails.setAppshare(ws_entity.getAppshare());
        ws_entityDetails.setCanControl(ws_entity.getCanControl());
        ws_entityDetails.setIsInMyContacts(ws_entity.getIsInMyContacts());
        //if (entity.getRoomType() != null && entity.getRoomType().equalsIgnoreCase("Personal")) {
        if (ws_entity.getPhone1()  != null && ! ws_entity.getPhone1().isEmpty()) {
            ws_entityDetails.setPhone1(ws_entity.getPhone1());
        }
        if (ws_entity.getPhone2()  != null && ! ws_entity.getPhone2().isEmpty()) {
            ws_entityDetails.setPhone2(ws_entity.getPhone2());
        }
        if (ws_entity.getPhone3()  != null && ! ws_entity.getPhone3().isEmpty()) {
            ws_entityDetails.setPhone3(ws_entity.getPhone3());
        }
        if (ws_entity.getDepartment()  != null && ! ws_entity.getDepartment().isEmpty()) {
            ws_entityDetails.setDepartment(ws_entity.getDepartment());
        }
        if (ws_entity.getTitle()  != null && ! ws_entity.getTitle().isEmpty()) {
            ws_entityDetails.setTitle(ws_entity.getTitle());
        }
        if (ws_entity.getInstantMessagerID()  != null && ! ws_entity.getInstantMessagerID().isEmpty()) {
            ws_entityDetails.setInstantMessagerID(ws_entity.getInstantMessagerID());
        }
        if (ws_entity.getLocation()  != null && ! ws_entity.getLocation().isEmpty()) {
            ws_entityDetails.setLocation(ws_entity.getLocation());
        }
            if (userImageConf != null && StringUtils.isNotBlank(userImageConf.getConfigurationValue()) && userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
                if (tenantConfiguration.getUserImage() == 1) {
                    if (ws_entity.getThumbnailUpdateTime() != null){
                        ws_entityDetails.setThumbnailUpdateTime(ws_entity.getThumbnailUpdateTime());
                        // Set the Thumbnail photo
                        boolean isExists = ImageUtils.checkIfThumbNailImageExists(thumbNailLocation, entity.getTenantID(), entity.getMemberID());
                        if (isExists){
                            DataHandler dHandler = new DataHandler(new FileDataSource(this.getThumbNailLocation() + "/" + entity.getTenantID() + "/" +entity.getMemberID()));
                            ws_entityDetails.setThumbnailPhoto(dHandler);
                        }
                    }
                }
            }
        //}
        return ws_entityDetails;
    }

    private LectureModeParticipant_type0 getLectureModeWSEntityFromBOEntity(com.vidyo.bo.Entity entity) {
        LectureModeParticipant_type0 ws_entity = new LectureModeParticipant_type0();

        EntityID entityID = new EntityID();
        entityID.setEntityID(String.valueOf(entity.getRoomID()));
        ws_entity.setEntityID(entityID);

        if (entity.getRoomType() != null) {
            if (entity.getRoomType().equalsIgnoreCase("Personal")) {
                ws_entity.setEntityType(EntityType_type0.Member);
            } else if (entity.getRoomType().equalsIgnoreCase("Public")) {
                ws_entity.setEntityType(EntityType_type0.Room);
            } else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
                ws_entity.setEntityType(EntityType_type0.Legacy);
            } else if(entity.getRoomType().equalsIgnoreCase("Scheduled")) {
                ws_entity.setEntityType(EntityType_type0.Room);
            }
        } else {
            ws_entity.setEntityType(EntityType_type0.Member);
        }

        ws_entity.setDisplayName(entity.getName());
        ws_entity.setExtension(entity.getExt());
        ws_entity.setVideo(entity.getVideo() == 1);
        ws_entity.setAudio(entity.getAudio() == 1);
        ws_entity.setAppshare(true);

        if (entity.getHandRaised() == 1) {
            ws_entity.setHandRaised(true);
        } else {
            ws_entity.setHandRaised(false);
        }

        if (entity.getPresenter() != 0) {
            ws_entity.setPresenter(true);
        } else {
            ws_entity.setPresenter(false);
        }

        return ws_entity;
    }

    private boolean canMemberControlRoom(int memberID, Room room,
            String moderatorPIN) {
        boolean rc = false;
        // MR Bug 11849 - WS - User portal control meeting related APIs allows
        // control of any meeting to any user
        // when the user is part of the conference which doesn't have moderator
        // PIN
        // and the API request is sent using empty moderator PIN tag
        // step 1 - user is an owner of room
        if (room.getMemberID() == memberID) {
            rc = true;
        } else {
            int endpointID = this.conferenceService
                    .getEndpointIDForMemberID(memberID);
            // step 2 - user has an admin or operator role
            String userRole = this.userService.getLoginUserRole();
            if (userRole.equalsIgnoreCase("ROLE_ADMIN")
                    || userRole.equalsIgnoreCase("ROLE_OPERATOR")) {
                // these kind of users must be participants in this room
                rc = this.conferenceService.isEndpointIDinRoomID(endpointID,
                        room.getRoomID());
            } else {
                // step 3 - user knows moderator PIN and he is a participant
                String roomModeratorPIN = room.getRoomModeratorPIN() != null ? room
                        .getRoomModeratorPIN() : "";
                if (roomModeratorPIN.equalsIgnoreCase(moderatorPIN)) {
                    // these kind of users must be participants in this room
                    rc = this.conferenceService.isEndpointIDinRoomID(
                            endpointID, room.getRoomID());
                }
            }
        }
        return rc;
    }

    private RoomProfile getWSRoomProfileFromBORoomProfile(
            com.vidyo.bo.profile.RoomProfile roomProfile) {
        RoomProfile ws_roomProfile = new RoomProfile();

        ws_roomProfile.setRoomProfileName(roomProfile.getProfileName());
        ws_roomProfile.setDescription(roomProfile.getProfileDescription());

        return ws_roomProfile;
    }

    /**
     * Auto generated method signature
     *
     * @param searchMyContactsRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public SearchMyContactsResponse searchMyContacts(
            SearchMyContactsRequest searchMyContactsRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering searchMyContacts() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        SearchMyContactsResponse resp = new SearchMyContactsResponse();
        User user = this.userService.getLoginUser();

        Filter_type0 input_param = searchMyContactsRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            validateAndSetFilterLimitParam(filter, input_param);
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("roomID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
            if (filter.getQuery() != null
                    && filter.getQuery().equalsIgnoreCase("*")) {
                if (filter.getEntityType().equalsIgnoreCase("Public")
                        || filter.getEntityType().equalsIgnoreCase("Legacy")) {
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                            "Cannot use * with specified Entity Type - "
                                    + filter.getEntityType());
                    exception.setFaultMessage(fault);
                    throw exception;
                }
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
            filter.setEntityType("All");
        }

        // total records in DB
        int total = memberService.getCountContacts(user.getMemberID(), filter);

        if(total > 0) {
            List<com.vidyo.bo.Entity> list = memberService.getContacts(
                    user.getMemberID(), filter);
            List<Integer> memberIds = new ArrayList<Integer>(list.size());
            for(com.vidyo.bo.Entity entity : list) {
                // If the entity is public room, get the personal room id of the member
                if(entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())) {
                    memberIds.add(entity.getMemberID());
                }
            }
            Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
            if(!memberIds.isEmpty()) {
                memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
            }
            // map list of entities to ws_entities
            for (com.vidyo.bo.Entity entity : list) {
                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                resp.addEntity(ws_entity);
            }

        }

        resp.setTotal(total);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting searchMyContacts() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param linkEndpointRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws AccessRestrictedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public LinkEndpointResponse linkEndpoint(
            LinkEndpointRequest linkEndpointRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException,
            AccessRestrictedFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering linkEndpoint() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        LinkEndpointResponse resp = new LinkEndpointResponse();

        String endpointGUID = linkEndpointRequest.getEID();
        if (endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Endpoint ID: " + endpointGUID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();

        if (linkEndpointRequest.getClientType() != null
                && (linkEndpointRequest.getClientType().equalsIgnoreCase("I") || linkEndpointRequest
                        .getClientType().equalsIgnoreCase("A"))) { // Check to
                                                                    // validate
                                                                    // if mobile
                                                                    // login is
                                                                    // allowed
                                                                    // for this
                                                                    // tenant
            Tenant tenant = tenantService.getTenant(user.getTenantID());

            if (tenant == null) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid Tenant ID: "
                        + user.getTenantID());
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            if (tenant.getMobileLogin() == 0) {
                AccessRestrictedFault fault = new AccessRestrictedFault();
                fault.setErrorMessage("Mobile Access Restricted for this User "
                        + user.getUsername());
                AccessRestrictedFaultException exception = new AccessRestrictedFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        com.vidyo.bo.Entity entity = memberService.getContact(user
                .getMemberID());
        // The above call returns the memberId and personal/legacy roomId always
        Map<Integer, Integer> memberIdPersonalRoomIdMap = new HashMap<Integer, Integer>();
        memberIdPersonalRoomIdMap.put(entity.getMemberID(), entity.getRoomID());

        Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);

        // LinkEndpoint Request will inform the portal which prng should be used
        userService.linkEndpointToUser(endpointGUID, linkEndpointRequest.getClientType(), linkEndpointRequest.getPak2());

        String vrIP = linkEndpointRequest.getVrIP();
        if (vrIP != null && !vrIP.equalsIgnoreCase("")) {
            userService.updateEndpointIPaddress(endpointGUID, vrIP);
        }
        // for CDRv3
        CDRinfo2 cdrinfo = new CDRinfo2();
        cdrinfo.setApplicationName(linkEndpointRequest.getApplicationName());
        cdrinfo.setApplicationVersion(linkEndpointRequest.getApplicationVersion());
        cdrinfo.setApplicationOs(linkEndpointRequest.getApplicationOs());
        cdrinfo.setDeviceModel(linkEndpointRequest.getDeviceModel());
         MessageContext msgContext = MessageContext.getCurrentMessageContext();
        cdrinfo.setEndpointPublicIPAddress((String) msgContext.getProperty(MessageContext.REMOTE_ADDR));
        userService.updateEndpointCDRInfo(endpointGUID, cdrinfo);

        resp.setEntity(ws_entity);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting linkEndpoint() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param addToMyContactsRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public AddToMyContactsResponse addToMyContacts(
            AddToMyContactsRequest addToMyContactsRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering addToMyContacts() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        AddToMyContactsResponse resp = new AddToMyContactsResponse();
        User user = userService.getLoginUser();

        int memberID = user.getMemberID();
        int roomID = Integer.valueOf(addToMyContactsRequest.getEntityID()
                .getEntityID());

        if (memberService.isInSpeedDialEntry(memberID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Entity already exists in list of Contacts");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room = roomService.getRoomDetailsForConference(roomID);

        if(room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Entity ID: " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

         memberService.addSpeedDialEntry(memberID, roomID);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting linkEndpoint() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param startVideoRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public StartVideoResponse startVideo(StartVideoRequest startVideoRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering startVideo() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        StartVideoResponse resp = new StartVideoResponse();

        int roomID = Integer.valueOf(startVideoRequest.getConferenceID()
                .getEntityID());
        int endpointID = Integer.valueOf(startVideoRequest.getParticipantID()
                .getEntityID());

        String moderatorPIN = startVideoRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        // check Regilar endpoint
        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "D");
        // not found - check Virtual endpoint
        if (endpointGUID.equalsIgnoreCase("")) {
            endpointGUID = conferenceService.getGUIDForEndpointID(endpointID,
                    "V");
        }

        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.startVideo(endpointGUID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting startVideo() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeRoomURLRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public RemoveRoomURLResponse removeRoomURL(
            RemoveRoomURLRequest removeRoomURLRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeRoomURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        RemoveRoomURLResponse resp = new RemoveRoomURLResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(removeRoomURLRequest.getRoomID()
                .getEntityID());

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid RoomID " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
            roomService.removeRoomKey(room);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeRoomURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param myEndpointStatusRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public MyEndpointStatusResponse myEndpointStatus(
            MyEndpointStatusRequest myEndpointStatusRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering myEndpointStatus() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        MyEndpointStatusResponse resp = new MyEndpointStatusResponse();
        User user = userService.getLoginUser();

        com.vidyo.bo.Entity entity = memberService.getContact(user
                .getMemberID());

        if (entity.getMemberStatus() == 0) {
            resp.setMemberStatus(MemberStatus_type0.Offline);
        } else if (entity.getMemberStatus() == 1) {
            resp.setMemberStatus(MemberStatus_type0.Online);
        } else if (entity.getMemberStatus() == 2) {
            resp.setMemberStatus(MemberStatus_type0.Busy);
        } else if (entity.getMemberStatus() == 3) {
            resp.setMemberStatus(MemberStatus_type0.Ringing);
        } else if (entity.getMemberStatus() == 4) {
            resp.setMemberStatus(MemberStatus_type0.RingAccepted);
        } else if (entity.getMemberStatus() == 5) {
            resp.setMemberStatus(MemberStatus_type0.RingRejected);
        } else if (entity.getMemberStatus() == 6) {
            resp.setMemberStatus(MemberStatus_type0.RingNoAnswer);
        } else if (entity.getMemberStatus() == 7) {
            resp.setMemberStatus(MemberStatus_type0.Alerting);
        } else if (entity.getMemberStatus() == 8) {
            resp.setMemberStatus(MemberStatus_type0.AlertCancelled);
        } else if (entity.getMemberStatus() == 9) {
            resp.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
        } else if (entity.getMemberStatus() == 12) {
            resp.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting myEndpointStatus() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param logOutRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering logOut() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        LogOutResponse resp = new LogOutResponse();
        User user = userService.getLoginUser();

        Member member = memberService.getMember(user.getMemberID());

        // Unbind user from Endpoint
        userService.unlinkEndpointFromUser(user.getMemberID(), member.getEndpointGUID(), UserUnbindCode.LOGOUT_REQUEST);

        if (member.getDefaultEndpointGUID() != null) {
            if (!member.getDefaultEndpointGUID().equalsIgnoreCase("")) {
                Endpoint endpoint = endpointService.getEndpointDetails(member.getDefaultEndpointGUID());
                if(endpoint != null) {
                    userService.linkEndpointToUser(member.getDefaultEndpointGUID(), endpoint.getEndpointUploadType(), false);
                } else {
                    userService.linkEndpointToUser(member.getDefaultEndpointGUID(), null, false);
                }
            }
        }

        // Delete the auth token for the EID if the authentication is using token
        Object authByToken = RequestContextHolder.getRequestAttributes().getAttribute("AUTH_USING_TOKEN",
                RequestAttributes.SCOPE_REQUEST);

        if (authByToken != null && authByToken instanceof Boolean && Boolean.valueOf(authByToken.toString())) {
            String eid = (String)RequestContextHolder.getRequestAttributes().getAttribute("ENDPOINT_ID",
                        RequestAttributes.SCOPE_REQUEST);
            // get the eid if authentication is by token
            if(eid != null) {
            	// Only invoke this API if eid is not null, since we don't want to remove all tokens generated by this user
            	vidyoPersistentTokenRepository.removeUserTokens(user.getUsername(), TenantContext.getTenantId(), eid);
            } else {
            	// If the endpoint id is not present, then remove only the token with which the request is made.
            	String token = (String) RequestContextHolder.getRequestAttributes().getAttribute("TOKEN_PART",
                        RequestAttributes.SCOPE_REQUEST);
            	String series = (String) RequestContextHolder.getRequestAttributes().getAttribute("SERIES_PART",
                        RequestAttributes.SCOPE_REQUEST);
            	vidyoPersistentTokenRepository.removeCurrentToken(user.getUsername(), series, token, TenantContext.getTenantId());
            }
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting logOut() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getEntityDetailsByEntityIDRequest
     * @return getEntityDetailsByEntityIDResponse
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws SeatLicenseExpiredFaultException
     * @throws NotLicensedFaultException
     */
    public com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDResponse getEntityDetailsByEntityID(
        com.vidyo.portal.user.v1_1.GetEntityDetailsByEntityIDRequest getEntityDetailsByEntityIDRequest)
        throws InvalidArgumentFaultException, GeneralFaultException,
            SeatLicenseExpiredFaultException, NotLicensedFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getEntityDetailsByEntityID() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();

        GetEntityDetailsByEntityIDResponse resp = new GetEntityDetailsByEntityIDResponse();

        EntityID[] list = getEntityDetailsByEntityIDRequest.getEntityID();
        // Remove if there are duplicates
        Set<Integer> entityIds = new LinkedHashSet<Integer>();
        for(EntityID id : list) {
            try {
                entityIds.add(Integer.valueOf(id.getEntityID()));
            } catch(NumberFormatException nfe) {
                logger.error("EntityId format is not correct - {}", id.getEntityID());
            }
        }
        com.vidyo.bo.Entity entity = null;
        Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
        TenantConfiguration tenantConfiguration = this.tenantService
                .getTenantConfiguration(TenantContext.getTenantId());

        for (Integer entityId : entityIds) {
            try {
                entity = roomService.getOneEntityDetails(entityId, user);
            } catch (java.lang.Exception ignored) {
                logger.error("Exception while retrieving Entity by entityID = " + entityId + " and user = " + user.getUsername(),
                        ignored.getMessage());
                continue;
            }
            if(entity != null) {
                List<Integer> memberIds = new ArrayList<Integer>(1);
                // If the entity is public room, get the personal room id of the member
                if (entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())
                        || entity.getRoomType().equalsIgnoreCase(
                                RoomTypes.SCHEDULED.getType())) {
                    memberIds.add(entity.getMemberID());
                }

                Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
                if(!memberIds.isEmpty()) {
                    memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
                }
                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                EntityDetails_type0 ws_entityDetails = getWSEntityDetailsFromBOEntity(entity, ws_entity, userImageConf, tenantConfiguration);
                resp.addEntityDetails(ws_entityDetails);

            }

        }
        if(resp.getEntityDetails() != null) {
            resp.setTotal(resp.getEntityDetails().length);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getEntityDetailsByEntityID() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param leaveConferenceRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public LeaveConferenceResponse leaveConference(
            LeaveConferenceRequest leaveConferenceRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering leaveConference() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        LeaveConferenceResponse resp = new LeaveConferenceResponse();

        int roomId = 0;
        try {
            roomId = Integer.valueOf(leaveConferenceRequest.getConferenceID()
                    .getEntityID());
        } catch (NumberFormatException e) {
            logger.warn("Invalid RoomId ", leaveConferenceRequest
                    .getConferenceID().getEntityID());
        }

        if (roomId <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int endpointId = 0;
        try {
            endpointId = Integer.valueOf(leaveConferenceRequest
                    .getParticipantID().getEntityID());
        } catch (NumberFormatException e) {
            logger.warn("Invalid EndpointId ", leaveConferenceRequest
                    .getParticipantID().getEntityID());
        }

        if (endpointId <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EndpointID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPin = leaveConferenceRequest.getModeratorPIN();
        com.vidyo.service.conference.request.LeaveConferenceRequest leaveConferenceRequest2 = new com.vidyo.service.conference.request.LeaveConferenceRequest();
        leaveConferenceRequest2.setRoomId(roomId);
        leaveConferenceRequest2.setModeratorPin(moderatorPin);
        leaveConferenceRequest2.setEndpointId(endpointId);
        com.vidyo.service.conference.response.LeaveConferenceResponse leaveConferenceResponse = conferenceAppService
                .leaveConference(leaveConferenceRequest2);

        if (leaveConferenceResponse.getStatus() == JoinConferenceResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Conference ID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (leaveConferenceResponse.getStatus() == com.vidyo.service.conference.response.LeaveConferenceResponse.INVALID_PARTICIPANT) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Participant Id");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (leaveConferenceResponse.getStatus() == JoinConferenceResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage(leaveConferenceResponse.getDisplayMessage());
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (leaveConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(leaveConferenceResponse.getDisplayMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    leaveConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting leaveConference() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param inviteToConferenceRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public InviteToConferenceResponse inviteToConference(
            InviteToConferenceRequest inviteToConferenceRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering inviteToConference() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        InviteToConferenceResponse resp = new InviteToConferenceResponse();

        // Validate the ConferenceID/RoomID to join
        int roomId = 0;
        try {
            roomId = Integer.valueOf(inviteToConferenceRequest
                    .getConferenceID().getEntityID());
        } catch (NumberFormatException nfe) {
            logger.warn("ConfID is not a valid integer {} ",
                    inviteToConferenceRequest.getConferenceID().getEntityID());
        }

        Room room = null;
        try {
            room = roomService.getRoomDetailsForConference(roomId);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + roomId);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(userService.getLoginUser().getMemberID(),
                room, inviteToConferenceRequest.getModeratorPIN())) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        // Validate Invitee's roomId
        int inviteeEntityId = 0;
        if (inviteToConferenceRequest
                .getInviteToConferenceRequestChoice_type0().getEntityID() != null) {
            try {
                inviteeEntityId = Integer.valueOf(inviteToConferenceRequest
                        .getInviteToConferenceRequestChoice_type0().getEntityID()
                        .getEntityID());
            } catch (NumberFormatException nfe) {
                logger.warn("Invitee EntityId is not a valid integer {} ",
                        inviteToConferenceRequest.getConferenceID().getEntityID());
            }
        }

        String inviteeInput = inviteToConferenceRequest
                .getInviteToConferenceRequestChoice_type0().getInvite();

        inviteeInput = inviteeInput != null ? inviteeInput.trim()
                : inviteeInput;

        com.vidyo.service.conference.request.InviteToConferenceRequest conferenceRequest = new com.vidyo.service.conference.request.InviteToConferenceRequest();
        conferenceRequest.setInviteeId(inviteeEntityId);
        conferenceRequest
                .setInviterId(userService.getLoginUser().getMemberID());
        conferenceRequest.setLegacy(inviteeInput);
        conferenceRequest.setModeratorPin(inviteToConferenceRequest
                .getModeratorPIN());
        conferenceRequest.setRoomId(roomId);
        
        if(inviteToConferenceRequest.isCallFromIdentifierSpecified()) {
        		conferenceRequest.setCallFromIdentifier(inviteToConferenceRequest.getCallFromIdentifier());
        }

        JoinConferenceResponse joinConferenceResponse = conferenceAppService
                .inviteParticipantToConference(conferenceRequest);

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.REQUIRED_PARAMS_NOT_PRESENT) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Conference ID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        /*Invite invite = new Invite();
        invite.setFromMemberID(user.getMemberID());

        int confID = Integer.valueOf(inviteToConferenceRequest
                .getConferenceID().getEntityID());
        invite.setFromRoomID(confID);



        Room room;
        try {
            room = roomService.getRoom(confID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + confID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int toEntityID = 0;
        if (inviteToConferenceRequest
                .getInviteToConferenceRequestChoice_type0().getEntityID() != null) {
            toEntityID = Integer.valueOf(inviteToConferenceRequest
                    .getInviteToConferenceRequestChoice_type0().getEntityID()
                    .getEntityID());
        }

        Room toRoom;
        int status = 1;
        boolean isLegacy = false;
        try {
            toRoom = roomService.getRoom(toEntityID);
            invite.setToMemberID(toRoom.getMemberID());

            Member invitee = memberService.getMember(toRoom.getMemberID());
            if (invitee.getRoleID() == 6) {
                isLegacy = true;
            }

            int toEndpointID;
            if (!isLegacy) {
                toEndpointID = conferenceService
                        .getEndpointIDForMemberID(toRoom.getMemberID());
                invite.setToEndpointID(toEndpointID);
                // check if status of Endpoint is Online
                status = conferenceService.getEndpointStatus(toEndpointID);
            }
        } catch (java.lang.Exception e) {
            invite.setToMemberID(0);
            invite.setToEndpointID(0);
        }

        if (!isLegacy && status != 1) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Status of invited member is not Online.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String search = "";
        if (inviteToConferenceRequest
                .getInviteToConferenceRequestChoice_type0().getInvite() != null) {
            search = inviteToConferenceRequest
                    .getInviteToConferenceRequestChoice_type0().getInvite();
        }
        invite.setSearch(search);

        try {
            conferenceService.inviteToConference(invite);
        } catch (OutOfPortsException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } catch (EndpointNotExistException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } catch (InviteConferenceException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }*/

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting inviteToConference() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param cancelOutboundCallRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ConferenceLockedFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException

     */
    @Override
    public CancelOutboundCallResponse cancelOutboundCall(CancelOutboundCallRequest cancelOutboundCallRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException,
            ControlMeetingFaultException, SeatLicenseExpiredFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering cancelOutboundCall() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        CancelOutboundCallResponse resp = new CancelOutboundCallResponse();
        User user = userService.getLoginUser();

        Invite invite = new Invite();
        invite.setFromMemberID(user.getMemberID());

        int confID = Integer.valueOf(cancelOutboundCallRequest.getConferenceID().getEntityID());
        invite.setFromRoomID(confID);

        String moderatorPIN = cancelOutboundCallRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(confID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + confID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int toEntityID = Integer.valueOf(cancelOutboundCallRequest.getEntityID().getEntityID());

        Room toRoom;
        int status = 3;
        boolean isLegacy = false;
        try {
            toRoom = roomService.getRoom(toEntityID);
            invite.setToMemberID(toRoom.getMemberID());

            Member invitee = memberService.getMember(toRoom.getMemberID());
            if (invitee.getRoleID() == 6) {
                isLegacy = true;
            }

            int toEndpointID;
            if (!isLegacy) {
                toEndpointID = conferenceService.getEndpointIDForMemberID(toRoom.getMemberID());
                invite.setToEndpointID(toEndpointID);
                // check if status of Endpoint is Online or Ringing
                status = conferenceService.getEndpointStatus(toEndpointID);
            }
        } catch (java.lang.Exception e) {
            invite.setToMemberID(0);
            invite.setToEndpointID(0);
        }

        if (!isLegacy && status != 3) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Status of cancelling member is not Ringing.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.cancelInviteToConference(invite);
        } catch (EndpointNotExistException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } catch (CancelInvitationException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting cancelOutboundCall() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public RaiseHandResponse raiseHand(RaiseHandRequest raiseHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering raiseHand() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        RaiseHandResponse response = new RaiseHandResponse();

        User user = this.userService.getLoginUser();

        MemberHandUpdateRequest serviceRequest = new MemberHandUpdateRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setHandRaised(true);

        HandUpdateResponse serviceResponse =  lectureModeService.updateHand(serviceRequest);

        if (serviceResponse.getStatus() == HandUpdateResponse.SUCCESS) {
            response.setOK(OK_type0.OK);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(serviceResponse.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting raiseHand() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param searchByEmailRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public SearchByEmailResponse searchByEmail(
            SearchByEmailRequest searchByEmailRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering searchByEmail() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        SearchByEmailResponse resp = new SearchByEmailResponse();
        User user = userService.getLoginUser();

        String emailAddress = searchByEmailRequest.getEmailAddress();

        Filter_type0 input_param = searchByEmailRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            validateAndSetFilterLimitParam(filter, input_param);
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("roomID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
            if (filter.getQuery() != null
                    && filter.getQuery().equalsIgnoreCase("*")) {
                if (filter.getEntityType().equalsIgnoreCase("Public")
                        || filter.getEntityType().equalsIgnoreCase("Legacy")) {
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    fault.setErrorMessage("Cannot use * with specified Entity Type - "
                            + filter.getEntityType());
                    InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                            fault.getErrorMessage());
                    exception.setFaultMessage(fault);
                    throw exception;
                }
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
            filter.setEntityType("All");
        }

        // total records in DB
        Long total = roomService.getCountEntityByEmail(filter, user,
                emailAddress);
        if (total > 0) {
            List<com.vidyo.bo.Entity> list = roomService.getEntityByEmail(
                    filter, user, emailAddress);

            List<Integer> memberIds = new ArrayList<Integer>(list.size());
            for(com.vidyo.bo.Entity entity : list) {
                // If the entity is public room, get the personal room id of the member
                if(entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())) {
                    memberIds.add(entity.getMemberID());
                }
            }
            Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
            if(!memberIds.isEmpty()) {
                memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
            }
            // map list of entities to ws_entities
            for (com.vidyo.bo.Entity entity : list) {
                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                resp.addEntity(ws_entity);
            }
        }

        resp.setTotal(total.intValue());

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting searchByEmail() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getEntityByEntityIDRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public GetEntityByEntityIDResponse getEntityByEntityID(
            GetEntityByEntityIDRequest getEntityByEntityIDRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getEntityByEntityID() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();

        GetEntityByEntityIDResponse resp = new GetEntityByEntityIDResponse();

        EntityID[] list = getEntityByEntityIDRequest.getEntityID();
        // Remove if there are duplicates
        Set<Integer> entityIds = new LinkedHashSet<Integer>();
        for(EntityID id : list) {
            try {
                entityIds.add(Integer.valueOf(id.getEntityID()));
            } catch(NumberFormatException nfe) {
                logger.error("EntityId format is not correct - {}", id.getEntityID());
            }
        }
        com.vidyo.bo.Entity entity = null;
        for (Integer entityId : entityIds) {
            try {
                entity = roomService.getOneEntity(entityId, user);
            } catch (java.lang.Exception ignored) {
                logger.error("Exception while retrieving Entity by entityID = " + entityId + " and user = " + user.getUsername(),
                        ignored.getMessage());
                continue;
            }
            if(entity != null) {
                List<Integer> memberIds = new ArrayList<Integer>(1);
                // If the entity is public room, get the personal room id of the member
                if (entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())
                        || entity.getRoomType().equalsIgnoreCase(
                                RoomTypes.SCHEDULED.getType())) {
                    memberIds.add(entity.getMemberID());
                }

                Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
                if(!memberIds.isEmpty()) {
                    memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
                }

                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                resp.addEntity(ws_entity);
            }
        }
        if(resp.getEntity() != null) {
            resp.setTotal(resp.getEntity().length);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getEntityByEntityID() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public DeleteScheduledRoomResponse deleteScheduledRoom(DeleteScheduledRoomRequest deleteScheduledRoomRequest) throws NotLicensedFaultException, RoomNotFoundFaultException, InvalidArgumentFaultException, GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering deleteScheduledRoom() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();
        int tenantId = user.getTenantID();

        String extension = deleteScheduledRoomRequest.getExtension().getExtension_type4();
        Pin_type3 pinObj = deleteScheduledRoomRequest.getPin();
        String pin = null;
        if (pinObj != null) {
            pin = pinObj.getPin_type2();
        }

        ScheduledRoomResponse result = roomService.validateScheduledRoom(extension, pin, tenantId);
        if (result.getStatus() != ScheduledRoomResponse.SUCCESS) {
            RoomNotFoundFault fault = new RoomNotFoundFault();
            fault.setErrorMessage("No such scheduled room found in tenant.");
            RoomNotFoundFaultException exception = new RoomNotFoundFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        if (result.getRoom().getMemberID() != user.getMemberID()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Unauthorized, you are not the room owner. ");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int rowsDeleted = roomService.deleteRoom(result.getRoom().getRoomID());
        if (rowsDeleted < 1) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Unable to delete room.");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        DeleteScheduledRoomResponse resp = new DeleteScheduledRoomResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting deleteScheduledRoom() of User API v.1.1");
        }

       return resp;

    }

    /**
     * Auto generated method signature
     *
     * @param stopVideoRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public StopVideoResponse stopVideo(StopVideoRequest stopVideoRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering stopVideo() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        StopVideoResponse resp = new StopVideoResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(stopVideoRequest.getConferenceID()
                .getEntityID());
        int endpointID = Integer.valueOf(stopVideoRequest.getParticipantID()
                .getEntityID());

        String moderatorPIN = stopVideoRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    "Participant not found in Conference");
            exception.setFaultMessage(fault);
            throw exception;
        }

        // check Regilar endpoint
        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "D");
        // not found - check Virtual endpoint
        if (endpointGUID.equalsIgnoreCase("")) {
            endpointGUID = conferenceService.getGUIDForEndpointID(endpointID,
                    "V");
        }

        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.stopVideo(endpointGUID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting stopVideo() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getParticipantsRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public GetParticipantsResponse getParticipants(
            GetParticipantsRequest getParticipantsRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getParticipants() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        GetParticipantsResponse resp = new GetParticipantsResponse();
        User user = userService.getLoginUser();

        int confID = Integer.valueOf(getParticipantsRequest.getConferenceID()
                .getEntityID());

        String moderatorPIN = getParticipantsRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(confID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + confID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid" + confID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Filter_type0 input_param = getParticipantsRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            validateAndSetFilterLimitParam(filter, input_param);
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("endpointID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
            filter.setEntityType("All");
        }

        // total participants except recorder
        Long total = conferenceService.getCountParticipants(confID);
        if(total > 0) {
            List<com.vidyo.bo.Entity> list = conferenceService.getParticipants(
                    confID, filter, user);
            List<Integer> memberIds = new ArrayList<Integer>(list.size());
            for(com.vidyo.bo.Entity entity : list) {
                // If the entity is public room, get the personal room id of the member
                if(entity.getRoomType() != null && (entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType()) || entity.getRoomType().equalsIgnoreCase(
                        RoomTypes.SCHEDULED.getType()))) {
                    memberIds.add(entity.getMemberID());
                }
            }
            Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
            if(!memberIds.isEmpty()) {
                memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
            }

            // map list of entities to ws_entities
            for (com.vidyo.bo.Entity entity : list) {
                // if the type is Recorder, dont add it to Entity list
                if (entity.getRoomType() != null
                        && entity.getRoomType().equalsIgnoreCase("Recorder")) {
                    resp.setRecorderID(entity.getEndpointID());
                    resp.setRecorderName(entity.getName());
                    resp.setWebcast(entity.getWebcast() == 1);
                    resp.setPaused(entity.getVideo() == 0);
                    total--;
                } else {
                    Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);

                    EntityID p_id = new EntityID();
                    // endpointId as participantId
                    p_id.setEntityID(String.valueOf(entity.getEndpointID()));
                    ws_entity.setParticipantID(p_id);

                    resp.addEntity(ws_entity);
                }
            }
        }
        resp.setTotal(total.intValue());

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getParticipants() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param lockRoomRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public LockRoomResponse lockRoom(LockRoomRequest lockRoomRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering lockRoom() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        LockRoomResponse resp = new LockRoomResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(lockRoomRequest.getRoomID().getEntityID());

        String moderatorPIN = lockRoomRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RoomID is invalid" + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RoomID is invalid " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        roomService.lockRoom(roomID);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting lockRoom() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param logInRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public LogInResponse logIn(LogInRequest logInRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering logIn() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        LogInResponse resp = new LogInResponse();
        User user = userService.getLoginUser();

        // Below condition takes for restricting login for mobile clients, if
        // the access is disabled at Tenant Level
        if (logInRequest.getClientType() != null
                && (logInRequest.getClientType().getValue().equalsIgnoreCase("I") || logInRequest.getClientType()
                        .getValue().equalsIgnoreCase("A"))) {
            // Check to validate if mobile login is allowed for this tenant
            Tenant tenant = tenantService.getTenant(user.getTenantID());

            if (tenant == null) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Tenant ID: "
                        + user.getTenantID());
                exception.setFaultMessage(fault);
                throw exception;
            }

            if (tenant.getMobileLogin() == 0) {
                // If Mobile Access is restricted, don't allow login
                GeneralFault fault = new GeneralFault();
                GeneralFaultException exception = new GeneralFaultException("Mobile Access Restricted");
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        PortalAccessKeys portalAccessKeys = userService.generatePAKforMember(user.getMemberID());
        //String PAK = userService.getPAKforMember(user.getMemberID());

        if(portalAccessKeys != null) {
            resp.setPak(portalAccessKeys.getPak());
            //TODO - uncomment the below line to include pak2
            resp.setPak2(portalAccessKeys.getPak2());
        }

        // set VM address
        try {
            String vm = conferenceService.getVMConnectAddress();

            if (vm != null && !vm.trim().equals("")) {
                resp.setVmaddress(vm);
            }
        } catch (NoVidyoManagerException ignored) {
            logger.error("No VidyoManager found, ignoring the Error");
        }

        String proxies = serviceService.getProxyCSVList(userService
                .getLoginUser().getMemberID());
        if (proxies != null && !proxies.trim().equals("")) {
            resp.setProxyaddress(proxies);
        }

        // set Location Tag
        String locTag = serviceService.getLocationTagForMember(userService
                .getLoginUser().getMemberID());
        if (locTag != null && !locTag.equalsIgnoreCase("")) {
            resp.setLoctag(locTag);
        } else {
            resp.setLoctag("Default");
        }

        MessageContext msgContext = MessageContext.getCurrentMessageContext();
        String remoteIpAddress = (String) msgContext.getProperty(MessageContext.REMOTE_ADDR);
        if(remoteIpAddress == null || remoteIpAddress.isEmpty()) {
            resp.setEndpointExternalIPAddress("Unknown");
        } else {
            resp.setEndpointExternalIPAddress(remoteIpAddress);
        }

        EndpointSettings es = this.systemService.getAdminEndpointSettings(TenantContext.getTenantId());
        resp.setMinMediaPort(es.getMinMediaPort());
        resp.setMaxMediaPort(es.getMaxMediaPort());
        resp.setVrProxyConfig(es.isAlwaysUseVidyoProxy() ? "ALWAYS" : "AUTO");

        resp.setMinimumPINLength(systemService.getMinPINLengthForTenant(TenantContext.getTenantId()));
        resp.setMaximumPINLength(SystemServiceImpl.PIN_MAX);

        // Check if the EndpointBehavior is enabled for the Tenant
        if (logInRequest.getReturnEndpointBehavior()
        		&& endpointBehaviorService.isEndpointBehaviorForTenant(TenantContext.getTenantId())) {
        	// If the request has set with the return EndpointBehavior, then only
        	// response will have it.
			// Get the EndpointBehavior for the tenant if exists.
			EndpointBehavior endpointBehavior = null;
	    	 List<EndpointBehavior> endpointBehaviors = endpointBehaviorService.getEndpointBehaviorByTenant(TenantContext.getTenantId());
			   if (endpointBehaviors != null && endpointBehaviors.size() > 0){
				   endpointBehavior = endpointBehaviors.get(0);
			   }

			if (endpointBehavior != null ){
				resp.setEndpointBehavior((EndpointBehaviorDataType)
						endpointBehaviorService.makeEndpointBehavioDataType(endpointBehavior, new EndpointBehaviorDataType()));
			}
        }

        // generate the auth token if it is requested
        if (logInRequest.getReturnAuthToken()) {
        	 TokenCreationRequest tokenCreationRequest = new TokenCreationRequest();
             tokenCreationRequest.setTenantId(user.getTenantID());
             tokenCreationRequest.setMemberId(user.getMemberID());
             tokenCreationRequest.setUsername(user.getUsername());
             tokenCreationRequest.setDeleteOldTokens(false);

             TokenCreationResponse tokenCreationResponse = persistentTokenService
                     .createPersistentToken(tokenCreationRequest);

             resp.setAuthToken(new String(Base64.encodeBase64(tokenCreationResponse.getToken().getBytes())));
        }

        if (logInRequest.getReturnPortalVersion()){
        	String portalVersion = null;
            try {
                portalVersion = systemService.getPortalVersion();
            } catch (java.lang.Exception e) {
                logger.error("Exception while retrieving Portal Version", e);
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("System Error - Unable to process request");
                GeneralFaultException exception = new GeneralFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            resp.setPortalVersion(portalVersion);
        }
        
		if (logInRequest.getReturnServiceAddress()) {

			URI registrationServiceAddress = null;
			try {
				List<Component> registrationServiceComponents = componentService
						.getComponentsByType(NetworkElementType.VidyoRegistrationMicroservice.getValue());
				if (CollectionUtils.isNotEmpty(registrationServiceComponents)) {
					registrationServiceAddress = new URI(registrationServiceComponents.get(0).getMgmtUrl());
				}
			} catch (java.lang.Exception e) {
				logger.error("Exception while retrieving RegistrationService Address", e);
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("System Error - Unable to process request");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			resp.setRegistrationService(registrationServiceAddress);

			URI pairingServiceAddress = null;
			try {
				List<Component> pairingServiceComponents = componentService
						.getComponentsByType(NetworkElementType.VidyoPairingMicroservice.getValue());
				if (CollectionUtils.isNotEmpty(pairingServiceComponents)) {
					pairingServiceAddress = new URI(pairingServiceComponents.get(0).getMgmtUrl());
				}
			} catch (java.lang.Exception e) {
				logger.error("Exception while retrieving PairingService Address", e);
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("System Error - Unable to process request");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
			resp.setPairingService(pairingServiceAddress);
		}

		Member member = null;
		if (logInRequest.getReturnNeoRoomPermanentPairingDeviceUserAttribute()) {
			try {
				member = memberService.getMember(user.getMemberID());
				if (member != null) {
					resp.setNeoRoomPermanentPairingDeviceUser(member.isNeoRoomPermanentPairingDeviceUser());
				}
			} catch (java.lang.Exception e) {
				logger.error("Exception while retrieving Member Details", e);
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("System Error - Unable to process request");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		if (logInRequest.getReturnUserRole()) {
			try {
				if (member == null) {
					member = memberService.getMember(user.getMemberID());
				}
				if (member != null) {
					resp.setUserRole(member.getRoleName());
				}
			} catch (java.lang.Exception e) {
				logger.error("Exception while retrieving Member Details", e);
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("System Error - Unable to process request");
				GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting logIn() of User API v.1.1");
        }

		return resp;
	}

    /**
     * Auto generated method signature
     *
     * @param unmuteAudioRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public UnmuteAudioResponse unmuteAudio(UnmuteAudioRequest unmuteAudioRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering unmuteAudio() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UnmuteAudioResponse resp = new UnmuteAudioResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(unmuteAudioRequest.getConferenceID()
                .getEntityID());
        int endpointID = Integer.valueOf(unmuteAudioRequest.getParticipantID()
                .getEntityID());

        String moderatorPIN = unmuteAudioRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room = null;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            logger.error("Exception while retrieving Room", e);
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "D");
        if (endpointGUID.equalsIgnoreCase("")) {
            endpointGUID = conferenceService.getGUIDForEndpointID(endpointID,
                    "V");
        }

        try {
            conferenceService.unmuteAudio(endpointGUID);
        } catch (java.lang.Exception e) {
            logger.error(e.getMessage(), e);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting unmuteAudio() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param updatePasswordRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public UpdatePasswordResponse updatePassword(
            UpdatePasswordRequest updatePasswordRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering updatePassword() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UpdatePasswordResponse resp = new UpdatePasswordResponse();
        User user = userService.getLoginUser();

        String password = updatePasswordRequest.getPassword();

        if(StringUtils.isEmpty(password) || password.trim().isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Password " + password);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());
        member.setPassword(password);
        int retVal = memberService.updateMemberPassword(member);
        if(retVal== MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS){
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Password does not meet requirements");
            GeneralFaultException exception = new GeneralFaultException("Password does not meet requirements");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED){
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("The password of imported user cannot be updated");
            GeneralFaultException exception = new GeneralFaultException("The password of imported user cannot be updated");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==MemberManagementResponse.PASSWORD_ENCODING_EXCEPTION){
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Could not update member Password");
            GeneralFaultException exception = new GeneralFaultException("Could not update member Password");
            exception.setFaultMessage(fault);
            throw exception;
        }else if (retVal==0){
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Could not update member Password");
            GeneralFaultException exception = new GeneralFaultException("Could not update member Password");
            exception.setFaultMessage(fault);
            throw exception;
        }
        resp.setOK(OK_type0.OK);
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting updatePassword() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param searchRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public SearchResponse search(SearchRequest searchRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering search() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        SearchResponse resp = new SearchResponse();
        User user = userService.getLoginUser();

        Filter_type0 input_param = searchRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            validateAndSetFilterLimitParam(filter, input_param);
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("roomID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null && input_param.getQuery().trim().length() > 80) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid Query string. It can not be greater than 80 characters."
                        + filter.getEntityType());
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
            if (filter.getQuery() != null
                    && filter.getQuery().equalsIgnoreCase("*")) {
                if (filter.getEntityType().equalsIgnoreCase("Public")
                        || filter.getEntityType().equalsIgnoreCase("Legacy")) {
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    fault.setErrorMessage("Cannot use * with specified Entity Type - "
                            + filter.getEntityType());
                    InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                            fault.getErrorMessage());
                    exception.setFaultMessage(fault);
                    throw exception;
                }
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
            filter.setEntityType("All");
        }

        List<RoomIdSearchResult> roomIdSearchResults = roomService.searchRoomIds(filter, user);
        
    		//Check for exact match for scheduled room extension (newer scheme v1.1)
    		// This for addressing the join issue from Room/UVD when a scheduled room extension is typed in
		Room room = null;
		try {
			room = roomService.getRoom(input_param.getQuery());
		} catch (Exception e) {
			logger.info(
					"More than one room matching the extension, extremely unlikely case -" + input_param.getQuery());
		}
		/*
		 *  1. If the input extension is an exact match, retrieve the room.
		 *  2. If the result is a scheduled room add it to the result set.
		 *  The above fix is needed to address the vidyoroom and UVD issue for identifying the new scheduled room extension 
		 */
		if (room != null) {
			final Room scheduledRoom = room;
			// Validate if the Tenant id is in allowed list since the above API can return
			// extension in any other tenant
			List<Integer> canCallToTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
			if(!roomIdSearchResults.isEmpty()) {
				if (canCallToTenantIds != null && canCallToTenantIds.contains(room.getTenantID())
						&& RoomTypes.SCHEDULED.getId() == room.getRoomTypeID() && roomIdSearchResults.stream()
								.noneMatch(result -> result.getRoomId() == scheduledRoom.getRoomID())) {
					RoomIdSearchResult idSearchResult = new RoomIdSearchResult();
					idSearchResult.setRoomId(room.getRoomID());
					roomIdSearchResults.get(0).setTotalCount(roomIdSearchResults.get(0).getTotalCount() + 1);
					roomIdSearchResults.add(idSearchResult);
				}
			} else {
				RoomIdSearchResult idSearchResult = new RoomIdSearchResult();
				idSearchResult.setRoomId(room.getRoomID());
				idSearchResult.setTotalCount(1);
				roomIdSearchResults.add(idSearchResult);
			}
		}


        if(!roomIdSearchResults.isEmpty() && roomIdSearchResults.get(0).getTotalCount() > 0) {
            resp.setTotal(roomIdSearchResults.get(0).getTotalCount());
            List<Integer> roomIds = new ArrayList<Integer>(roomIdSearchResults.size());
            for(RoomIdSearchResult roomIdSearchResult : roomIdSearchResults) {
                roomIds.add(roomIdSearchResult.getRoomId());
            }
            List<com.vidyo.bo.Entity> entities = roomService.getEntities(roomIds, user, filter);
            List<Integer> memberIds = new ArrayList<Integer>(entities.size());
            for(com.vidyo.bo.Entity entity : entities) {
                // If the entity is public room, get the personal room id of the member
                if(entity.getRoomTypeID() == RoomTypes.PUBLIC.getId()) {
                    memberIds.add(entity.getMemberID());
                }
            }
            Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
            if(!memberIds.isEmpty()) {
                memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
            }
            // map list of entities to ws_entities
            for (com.vidyo.bo.Entity entity : entities) {
                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                resp.addEntity(ws_entity);
            }
        } else {
            resp.setTotal(0);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting search() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param deleteRoomRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public DeleteRoomResponse deleteRoom(DeleteRoomRequest deleteRoomRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering deleteRoom() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
        User user = this.userService.getLoginUser();

        DeleteRoomResponse resp = new DeleteRoomResponse();
        String entityID = deleteRoomRequest.getRoomID().getEntityID();
        int roomID;
        Room room;

        try {
            roomID = Integer.parseInt(entityID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RoomID is invalid " + entityID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not found for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not found for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if(room.getMemberID() != user.getMemberID()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room.getRoomType().equalsIgnoreCase("Personal")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Cannot delete Personal type of Room for roomID = "
                    + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        roomService.deleteRoom(room.getRoomID());

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting deleteRoom() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param myAccountRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public MyAccountResponse myAccount(MyAccountRequest myAccountRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering myAccount() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        MyAccountResponse resp = new MyAccountResponse();
        User user = userService.getLoginUser();

        com.vidyo.bo.Entity entity = memberService.getContact(user
                .getMemberID());
        // The above call returns only personal/legacy room type
        Map<Integer, Integer> memberIdPersonalRoomIdMap = new HashMap<Integer, Integer>();
        memberIdPersonalRoomIdMap.put(entity.getMemberID(), entity.getRoomID());

        Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);

        resp.setEntity(ws_entity);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting myAccount() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param joinConferenceRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ConferenceLockedFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     * @throws WrongPINFaultException
     *             :
     */
    @Override
    public com.vidyo.portal.user.v1_1.JoinConferenceResponse joinConference(
            com.vidyo.portal.user.v1_1.JoinConferenceRequest joinConferenceRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ConferenceLockedFaultException,
            SeatLicenseExpiredFaultException, WrongPINFaultException {
        logger.debug("Entering joinConference() of User API v.1.1");

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        com.vidyo.portal.user.v1_1.JoinConferenceResponse resp = new com.vidyo.portal.user.v1_1.JoinConferenceResponse();
        User user = userService.getLoginUser();

        int roomIdToJoin = -1;

        if(joinConferenceRequest.getJoinConferenceRequestChoice_type0().getConferenceID() != null) {
            roomIdToJoin = Integer.valueOf(joinConferenceRequest.getJoinConferenceRequestChoice_type0().getConferenceID().getEntityID());
        }

        String roomNameExt = null;

        if(joinConferenceRequest.getJoinConferenceRequestChoice_type0().getExtension() != null) {
            roomNameExt = joinConferenceRequest.getJoinConferenceRequestChoice_type0().getExtension();
        }

        String pin = joinConferenceRequest.getPIN();
        if (pin == null) {
            pin = "";
        }

        com.vidyo.service.conference.request.JoinConferenceRequest conferenceRequest = new com.vidyo.service.conference.request.JoinConferenceRequest();
        conferenceRequest.setJoiningMemberId(user.getMemberID());
        conferenceRequest.setRoomId(roomIdToJoin);
        conferenceRequest.setPin(pin);
        conferenceRequest.setRoomNameExt(roomNameExt);

        com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService
                .joinConference(conferenceRequest);

        if(joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Conference ID is invalid " + roomIdToJoin);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if(joinConferenceResponse.getStatus() == ScheduledRoomResponse.INVALID_SCHEDULED_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Extension is invalid " + roomNameExt);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.WRONG_PIN
                || joinConferenceResponse.getStatus() == ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN) {
            WrongPINFault fault = new WrongPINFault();
            fault.setErrorMessage("Wrong PIN");
            WrongPINFaultException exception = new WrongPINFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_LOCKED) {
            ConferenceLockedFault fault = new ConferenceLockedFault();
            fault.setErrorMessage("Conference is Locked");
            ConferenceLockedFaultException exception = new ConferenceLockedFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if(joinConferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String referenceNumber = joinConferenceRequest.getReferenceNumber();
        String guid = this.userService.getLinkedEndpointGUID(user.getMemberID());
        this.userService.updateEndpointReferenceNumber(guid, referenceNumber);
        
        String extData = null;
//		if (joinConferenceRequest.isExtDataSpecified() && StringUtils.isNotEmpty(joinConferenceRequest.getExtData())) {
			extData = joinConferenceRequest.getExtData();
			int extDataType = 0;

			if (joinConferenceRequest.isExtDataTypeSpecified()) {
				extDataType = joinConferenceRequest.getExtDataType();
			}
			userService.updateEndpointExtData(guid, extDataType, extData);
//		}
        resp.setOK(OK_type0.OK);

        logger.debug("Exiting joinConference() of User API v.1.1");

        return resp;
    }

    @Override
    public DismissAllRaisedHandResponse dismissAllRaisedHand(DismissAllRaisedHandRequest dismissAllRaisedHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering dismissAllRaisedHand() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = dismissAllRaisedHandRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = dismissAllRaisedHandRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);

        LectureModeControlResponse serviceResponse = lectureModeService.dismissAllRaisedHands(serviceRequest);

        int status =  serviceResponse.getStatus();
        if (status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is not on for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        DismissAllRaisedHandResponse response = new DismissAllRaisedHandResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting dismissAllRaisedHand() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param joinIPCConferenceRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ConferenceLockedFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     * @throws WrongPINFaultException
     *             :
     */
    @Override
    public JoinIPCConferenceResponse joinIPCConference(JoinIPCConferenceRequest joinIPCConferenceRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException,
            ConferenceLockedFaultException, SeatLicenseExpiredFaultException, WrongPINFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering joinIPCConference() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        JoinIPCConferenceResponse resp = new JoinIPCConferenceResponse();
        User user = userService.getLoginUser();

        String roomNameExt = joinIPCConferenceRequest.getLocalPart();
        if (roomNameExt == null || roomNameExt.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + roomNameExt);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String remoteHost = joinIPCConferenceRequest.getDomain();
        if (remoteHost == null || remoteHost.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Host " + remoteHost);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String pin = joinIPCConferenceRequest.getPIN();
        if (pin == null) {
            pin = "";
        }

        com.vidyo.service.conference.request.JoinConferenceRequest conferenceRequest = new com.vidyo.service.conference.request.JoinConferenceRequest();
        conferenceRequest.setTenantUrl(remoteHost);
        conferenceRequest.setPin(pin);
        conferenceRequest.setRoomNameExt(roomNameExt);
        conferenceRequest.setJoiningMemberId(user.getMemberID());
        com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService
                .joinInterPortalRoomConference(conferenceRequest);

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.ROOM_LOCKED) {
            ConferenceLockedFault fault = new ConferenceLockedFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            ConferenceLockedFaultException exception = new ConferenceLockedFaultException(
                    joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.WRONG_PIN
                || joinConferenceResponse.getStatus() == ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN) {
            WrongPINFault fault = new WrongPINFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            WrongPINFaultException exception = new WrongPINFaultException(
                    joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() == JoinConferenceResponse.REMOTE_USER_NOT_FOUND) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (joinConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(joinConferenceResponse.getDisplayMessage());
            GeneralFaultException exception = new GeneralFaultException(joinConferenceResponse.getDisplayMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String referenceNumber = joinIPCConferenceRequest.getReferenceNumber();
        String guid = this.userService.getLinkedEndpointGUID(user.getMemberID());
        this.userService.updateEndpointReferenceNumber(guid, referenceNumber);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting joinIPCConference() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public RemovePresenterResponse removePresenter(RemovePresenterRequest removePresenterRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removePresenter() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = removePresenterRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = removePresenterRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        int endpointID = Integer.valueOf(removePresenterRequest.getParticipantID().getEntityID());

        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);

        LectureModeControlResponse serviceResponse = lectureModeService.removePresenter(serviceRequest);

        int status =  serviceResponse.getStatus();
        if(status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.PARTICIPANT_IS_NOT_PRESENTING) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not prsenting in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is not on for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        RemovePresenterResponse response = new RemovePresenterResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removePresenter() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param unlockRoomRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public UnlockRoomResponse unlockRoom(UnlockRoomRequest unlockRoomRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering unlockRoom() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UnlockRoomResponse resp = new UnlockRoomResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(unlockRoomRequest.getRoomID()
                .getEntityID());

        String moderatorPIN = unlockRoomRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RoomID is invalid " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        roomService.unlockRoom(roomID);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting unlockRoom() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param directCallRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public DirectCallResponse directCall(DirectCallRequest directCallRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering directCall() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        DirectCallResponse resp = new DirectCallResponse();
        User user = userService.getLoginUser();

        Member member = memberService.getMember(user.getMemberID());

        Invite invite = new Invite();
        invite.setFromMemberID(member.getMemberID());
        invite.setFromRoomID(member.getRoomID());

        if (directCallRequest.getDirectCallRequestChoice_type0().getEntityID() != null) {
            int roomID = Integer.valueOf(directCallRequest
                    .getDirectCallRequestChoice_type0().getEntityID()
                    .getEntityID());

            Room toRoom = null;
            int status = 1;
            boolean isLegacy = false;
            try {
                toRoom = roomService.getRoomDetailsForConference(roomID);
                invite.setToMemberID(toRoom.getMemberID());

                Member invitee = memberService.getMember(toRoom.getMemberID());
                if (invitee.getRoleID() == 6) {
                    isLegacy = true;
                }

                int toEndpointID;
                if (!isLegacy) {
                    toEndpointID = conferenceService
                            .getEndpointIDForMemberID(toRoom.getMemberID());
                    invite.setToEndpointID(toEndpointID);
                    // check if status of Endpoint is Online
                    status = conferenceService.getEndpointStatus(toEndpointID);
                }
            } catch (java.lang.Exception e) {
                invite.setToMemberID(0);
                invite.setToEndpointID(0);
            }

            if (toRoom != null && toRoom.getMemberID() == user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Cannot do a direct call to myself.");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            if (!isLegacy && status != 1) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Status of invited member is not Online.");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

        } else if (directCallRequest.getDirectCallRequestChoice_type0()
                .getInvite() != null) {
            String search = directCallRequest
                    .getDirectCallRequestChoice_type0().getInvite();
            invite.setSearch(search);
        }

        try {
            conferenceService.twoPartyConference(invite);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting directCall() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param setMemberModeRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public SetMemberModeResponse setMemberMode(
            SetMemberModeRequest setMemberModeRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering setMemberMode() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();

        SetMemberModeResponse resp = new SetMemberModeResponse();
        MemberMode_type0 mode = setMemberModeRequest.getMemberMode();

        int modeID = 1;
        if (mode.getValue().equalsIgnoreCase(MemberMode_type0._Available)) {
            modeID = 1;
        } else if (mode.getValue().equalsIgnoreCase(MemberMode_type0._Away)) {
            modeID = 2;
        } else if (mode.getValue().equalsIgnoreCase(
                MemberMode_type0._DoNotDisturb)) {
            modeID = 3;
        }

        memberService.updateMemberMode(user.getMemberID(), modeID);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting setMemberMode() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeFromMyContactsRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public RemoveFromMyContactsResponse removeFromMyContacts(
            RemoveFromMyContactsRequest removeFromMyContactsRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeFromMyContacts() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        RemoveFromMyContactsResponse resp = new RemoveFromMyContactsResponse();
        User user = userService.getLoginUser();

        int memberID = user.getMemberID();
        int roomID = Integer.valueOf(removeFromMyContactsRequest.getEntityID()
                .getEntityID());

        if (!memberService.isInSpeedDialEntry(memberID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Entity not found in list of Contacts");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room = roomService.getRoomDetailsForConference(roomID);
        if(room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid entity ID: " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        memberService.removeSpeedDialEntry(memberID, roomID);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeFromMyContacts() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param createRoomPINRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public CreateRoomPINResponse createRoomPIN(
            CreateRoomPINRequest createRoomPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createRoomPIN() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        CreateRoomPINResponse resp = new CreateRoomPINResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(createRoomPINRequest.getRoomID()
                .getEntityID());

        String sPIN = createRoomPINRequest.getPIN();
        if (sPIN == null) {
            sPIN = "";
        }

        Room room;
        try {
            room = roomService.getRoom(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), sPIN)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("PIN should be a " +
                    systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
                    "-" + SystemServiceImpl.PIN_MAX + " digit number");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("You are not an owner of room for roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
            room.setPinSetting("enter");
            room.setRoomPIN(sPIN);
            roomService.updateRoom(roomID, room);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createRoomPIN() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param createRoomURLRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public CreateRoomURLResponse createRoomURL(
            CreateRoomURLRequest createRoomURLRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createRoomURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        CreateRoomURLResponse resp = new CreateRoomURLResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(createRoomURLRequest.getRoomID()
                .getEntityID());

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("You are not an owner of room for roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
            roomService.generateRoomKey(room);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createRoomURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param updateLanguageRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public UpdateLanguageResponse updateLanguage(
            UpdateLanguageRequest updateLanguageRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering updateLanguage() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UpdateLanguageResponse resp = new UpdateLanguageResponse();
        User user = userService.getLoginUser();

        Language_type0 langCode = updateLanguageRequest.getLanguage();
        Member member = memberService.getMember(user.getMemberID());

        if (langCode.getValue().equalsIgnoreCase(Language_type0._en)) {
            member.setLangID(1);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._fr)) {
            member.setLangID(2);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._ja)) {
            member.setLangID(3);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._zh_CN)) {
            member.setLangID(4);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._es)) {
            member.setLangID(5);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._it)) {
            member.setLangID(6);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._de)) {
            member.setLangID(7);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._ko)) {
            member.setLangID(8);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._pt)) {
            member.setLangID(9);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._fi)) {
            member.setLangID(11);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._pl)) {
            member.setLangID(12);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._zh_TW)) {
            member.setLangID(13);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._th)) {
            member.setLangID(14);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._ru)) {
            member.setLangID(15);
        } else if (langCode.getValue().equalsIgnoreCase(Language_type0._tr)) {
            member.setLangID(16);
        }

        memberService.updateMemberLanguage(member);

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting updateLanguage() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public StopLectureModeResponse stopLectureMode(StopLectureModeRequest stopLectureModeRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering stopLectureMode() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = stopLectureModeRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = stopLectureModeRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);

        LectureModeControlResponse serviceResponse = lectureModeService.stopLectureMode(serviceRequest);

        int status =  serviceResponse.getStatus();
        if (status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_ALREADY_STOPPED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is already off for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is already off for conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        StopLectureModeResponse response = new StopLectureModeResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting stopLectureMode() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param muteAudioRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public MuteAudioResponse muteAudio(MuteAudioRequest muteAudioRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering muteAudio() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        MuteAudioResponse resp = new MuteAudioResponse();
        
		String endpointGUID = retrieveGUIDAfterRoomModerationValidation(muteAudioRequest.getConferenceID(),
				muteAudioRequest.getParticipantID(), muteAudioRequest.getModeratorPIN());

        try {
            conferenceService.muteAudio(endpointGUID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting muteAudio() of User API v.1.1");
        }

        return resp;
    }
    
	/**
	 * Common method to validate the Control meeting params and return EndpointGUID
	 * of the participant
	 * 
	 * @param confId
	 * @param participantId
	 * @param modPIN
	 * @return
	 * @throws InvalidArgumentFaultException
	 * @throws ControlMeetingFaultException
	 */
    protected String retrieveGUIDAfterRoomModerationValidation(EntityID confId, EntityID participantId, String modPIN) throws InvalidArgumentFaultException, ControlMeetingFaultException {
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(confId.getEntityID());
        int endpointID = Integer.valueOf(participantId.getEntityID());

        if (modPIN == null) {
        	modPIN = "noModeratorPIN";
        }

        Room room = null;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!canMemberControlRoom(user.getMemberID(), room, modPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "D");
        if (endpointGUID.equalsIgnoreCase("")) {
            endpointGUID = conferenceService.getGUIDForEndpointID(endpointID,
                    "V");
        }
        return endpointGUID;
    }

    @Override
    public SetPresenterResponse setPresenter(SetPresenterRequest setPresenterRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering setPresenter() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = setPresenterRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = setPresenterRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        int endpointID = Integer.valueOf(setPresenterRequest.getParticipantID().getEntityID());

        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);

        LectureModeControlResponse serviceResponse = lectureModeService.setPresenter(serviceRequest);

        int status =  serviceResponse.getStatus();
        if (status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is not on for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        SetPresenterResponse response = new SetPresenterResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting setPresenter() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param createRoomRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public CreateRoomResponse createRoom(CreateRoomRequest createRoomRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createRoom() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();

        CreateRoomResponse resp = new CreateRoomResponse();
        String name = createRoomRequest.getName();
        String ext = createRoomRequest.getExtension();

        if (StringUtils.isEmpty(name) ||
        		!name.matches(ValidationUtils.USERNAME_REGEX)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room Name - " + name);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if(RoomUtils.isNotValidAlphaNumericExtension(ext)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room Extension - " + ext);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomService.isRoomExistForRoomName(name, 0)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room exist for name = " + name);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, ext);
        if (extExists > 0){
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Extension matches Schedule Room prefix. Please choose a different extension and try again. Extension = " + ext);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        boolean isValidExt = RoomUtils.isValidExtensionByPrefix(tenantService, ext);
        if(!isValidExt) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Extension - Extension does not start with Tenant Prefix " + ext);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomService.isRoomExistForRoomExtNumber(ext, 0)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room exist for extension = " + ext);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());

        Room room = new Room();
        room.setRoomEnabled(1); // active by default
        room.setRoomTypeID(2); // public room
        room.setRoomName(name);
        room.setRoomExtNumber(ext);
        room.setPinSetting("leave");
        room.setGroupID(member.getGroupID());
        room.setMemberID(member.getMemberID());

        if (VendorUtils.isRoomsLockedByDefault()) {
            room.setRoomLocked(1);
        }
        int roomID = roomService.insertRoom(room);

        // This API creates a public room always - getMember call returns the personal room id
        Map<Integer, Integer> memberIdPersonalRoomIdMap = new HashMap<Integer, Integer>();
        memberIdPersonalRoomIdMap.put(member.getMemberID(), member.getRoomID());

        if (roomID > 0) {
            com.vidyo.bo.Entity entity = roomService.getOneEntity(roomID, user);
            Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
            resp.setEntity(ws_entity);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createRoom() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param searchByEntityIDRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public SearchByEntityIDResponse searchByEntityID(
            SearchByEntityIDRequest searchByEntityIDRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering searchByEntityID() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        SearchByEntityIDResponse resp = new SearchByEntityIDResponse();
        User user = userService.getLoginUser();

        String sEntityID = searchByEntityIDRequest.getEntityID().getEntityID();
        int entityID;

        try {
            entityID = Integer.parseInt(sEntityID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid entityID = " + sEntityID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        /*Room room;
        try {
            room = roomService.getRoom(entityID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid entityID = " + sEntityID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }*/
        List<Integer> canCallTenantIds = tenantService.canCallToTenantIds(user.getTenantID());
        Room room = roomService.getAccessibleRoomDetails(entityID, canCallTenantIds);

        if (room == null || room.getRoomID() <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid entityID "+ sEntityID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Filter_type0 input_param = searchByEntityIDRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            validateAndSetFilterLimitParam(filter, input_param);
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("roomID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
            if (filter.getQuery() != null
                    && filter.getQuery().equalsIgnoreCase("*")) {
                if (filter.getEntityType().equalsIgnoreCase("Public")
                        || filter.getEntityType().equalsIgnoreCase("Legacy")) {
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    fault.setErrorMessage("Cannot use * with specified Entity Type - "
                            + filter.getEntityType());
                    InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                            fault.getErrorMessage());
                    exception.setFaultMessage(fault);
                    throw exception;
                }
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
            filter.setEntityType("All");
        }

        // total records in DB
        Long total = roomService.getCountEntityByOwnerID(filter, user,
                room.getMemberID());

        //Make data retrieval query only if the count is non zero
        if(total > 0) {
            List<com.vidyo.bo.Entity> list = roomService.getEntityByOwnerID(filter,
                    user, room.getMemberID());
            List<Integer> memberIds = new ArrayList<Integer>(list.size());
            for(com.vidyo.bo.Entity entity : list) {
                // If the entity is public room, get the personal room id of the member
                if(entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())) {
                    memberIds.add(entity.getMemberID());
                }
            }
            Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
            if(!memberIds.isEmpty()) {
                memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
            }
            // map list of entities to ws_entities
            for (com.vidyo.bo.Entity entity : list) {
                Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);
                resp.addEntity(ws_entity);
            }
        }

        resp.setTotal(total.intValue());

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting searchByEntityID() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public DismissRaisedHandResponse dismissRaisedHand(DismissRaisedHandRequest dismissRaisedHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering dismissRaisedHand() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = dismissRaisedHandRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = dismissRaisedHandRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        int endpointID = Integer.valueOf(dismissRaisedHandRequest.getParticipantID().getEntityID());

        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);

        LectureModeControlResponse serviceResponse = lectureModeService.dismissRaisedHand(serviceRequest);

        int status =  serviceResponse.getStatus();
        if (status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Participant not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_NOT_STARTED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is not on for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is not on for conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        DismissRaisedHandResponse response = new DismissRaisedHandResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting dismissRaisedHand() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param removeRoomPINRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public RemoveRoomPINResponse removeRoomPIN(
            RemoveRoomPINRequest removeRoomPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeRoomPIN() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        RemoveRoomPINResponse resp = new RemoveRoomPINResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(removeRoomPINRequest.getRoomID()
                .getEntityID());

        Room room;
        try {
            room = roomService.getRoom(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("You are not an owner of room for roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
            room.setPinSetting("remove");
            roomService.updateRoom(roomID, room);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeRoomPIN() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getRecordingProfilesRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public GetRecordingProfilesResponse getRecordingProfiles(
            GetRecordingProfilesRequest getRecordingProfilesRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getRecordingProfiles() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        List<RecorderPrefix> recorders;
        try {
            recorders = systemService
                    .getAvailableRecorderPrefixes(new RecorderEndpointFilter());
        } catch (java.lang.Exception e) {
            logger.error("Exception while retrieving Recorder Profiles", e);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("System Error - Unable to process request");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetRecordingProfilesResponse resp = new GetRecordingProfilesResponse();

        if (recorders != null) {
            for (RecorderPrefix recorderEndpoint : recorders) {
                Recorder recorder = new Recorder();
                recorder.setDescription(recorderEndpoint.getDescription());
                recorder.setRecorderPrefix(recorderEndpoint.getPrefix());
                resp.addRecorder(recorder);
            }
            resp.setTotal(recorders.size());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getRecordingProfiles() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param startRecordingRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws ResourceNotAvailableFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public StartRecordingResponse startRecording(
            StartRecordingRequest startRecordingRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            ResourceNotAvailableFaultException, GeneralFaultException,
            ControlMeetingFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering recordConference() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = Integer.valueOf(startRecordingRequest.getConferenceID()
                .getEntityID());
        boolean webcast = startRecordingRequest.getWebcast();

        String moderatorPIN = startRecordingRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room.getAllowRecording() == 0) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.recordTheConference(room.getMemberID(), roomID,
                    startRecordingRequest.getRecorderPrefix(), webcast ? 1 : 0);
        } catch (JoinConferenceException e) {
            logger.error("JoinConferenceException" + e.getMessage());
            ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
            fault.setErrorMessage("Resource not available to record the Meeting "
                    + roomID);
            ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } catch (OutOfPortsException e) {
            logger.error("OutOfPortsException" + e.getMessage());
            ResourceNotAvailableFault fault = new ResourceNotAvailableFault();
            fault.setErrorMessage(e.getMessage());
            ResourceNotAvailableFaultException exception = new ResourceNotAvailableFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        StartRecordingResponse resp = new StartRecordingResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting recordConference() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getPortalVersionRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public GetPortalVersionResponse getPortalVersion(
            GetPortalVersionRequest getPortalVersionRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getPortalVersion() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String portalVersion;
        try {
            portalVersion = systemService.getPortalVersion();
        } catch (java.lang.Exception e) {
            logger.error("Exception while retrieving Portal Version", e);
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("System Error - Unable to process request");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetPortalVersionResponse resp = new GetPortalVersionResponse();
        resp.setPortalVersion(portalVersion);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getPortalVersion() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param pauseRecordingRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public PauseRecordingResponse pauseRecording(
            PauseRecordingRequest pauseRecordingRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering pauseRecording() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = Integer.valueOf(pauseRecordingRequest.getConferenceID()
                .getEntityID());
        int endpointID = pauseRecordingRequest.getRecorderID();

        String moderatorPIN = pauseRecordingRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoom(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room.getAllowRecording() == 0) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Recorder not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        // check Recorder endpoint
        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "R");
        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Recorder not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.pauseRecording(endpointGUID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        PauseRecordingResponse resp = new PauseRecordingResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting pauseRecording() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param resumeRecordingRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public ResumeRecordingResponse resumeRecording(
            ResumeRecordingRequest resumeRecordingRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering resumeRecording() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = Integer.valueOf(resumeRecordingRequest.getConferenceID()
                .getEntityID());
        int endpointID = resumeRecordingRequest.getRecorderID();

        String moderatorPIN = resumeRecordingRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoom(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room.getAllowRecording() == 0) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Recorder not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "R");
        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Recorder not found in Conference");
            InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            invalidArgumentFaultException.setFaultMessage(fault);
            throw invalidArgumentFaultException;
        }

        try {
            conferenceService.resumeRecording(endpointGUID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        ResumeRecordingResponse resp = new ResumeRecordingResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting resumeRecording() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public GetLectureModeParticipantsResponse getLectureModeParticipants(GetLectureModeParticipantsRequest getLectureModeParticipantsRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering getLectureModeParticipants() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        GetLectureModeParticipantsResponse resp = new GetLectureModeParticipantsResponse();

        int roomID = Integer.valueOf(getLectureModeParticipantsRequest.getConferenceID().getEntityID());

        String moderatorPIN = getLectureModeParticipantsRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        LectureModeParticipantsRequest serviceRequest = new LectureModeParticipantsRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setModeratorPIN(moderatorPIN);


        Filter_type0 input_param = getLectureModeParticipantsRequest.getFilter();
        EntityFilter filter = new EntityFilter();
        // map ws_filter to entity filter
        if (input_param != null) {
            if (input_param.getStart() >= 0) {
                filter.setStart(input_param.getStart());
            }
            if (input_param.getLimit() >= 0) {
                filter.setLimit(input_param.getLimit());
            }
            if (input_param.getDir() != null) {
                filter.setDir(input_param.getDir().getValue());
            }
            if (input_param.getSortBy() != null) {
                // mapping between WS and SQL
                if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
                    filter.setSortBy("endpointID");
                } else if (input_param.getSortBy().equalsIgnoreCase("name")) {
                    filter.setSortBy("name");
                } else if (input_param.getSortBy()
                        .equalsIgnoreCase("extension")) {
                    filter.setSortBy("ext");
                } else {
                    filter.setSortBy("");
                }
            }
            if (input_param.getEntityType() != null) {
                // mapping between WS and SQL
                if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Member")) {
                    filter.setEntityType("Personal");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Room")) {
                    filter.setEntityType("Public");
                } else if (input_param.getEntityType().getValue()
                        .equalsIgnoreCase("Legacy")) {
                    filter.setEntityType("Legacy");
                }
            } else {
                filter.setEntityType("All");
            }
            if (input_param.getQuery() != null) {
                filter.setQuery(input_param.getQuery());
            }
        }

        serviceRequest.setEntityFilter(filter);

        LectureModeParticipantsResponse serviceResponse = lectureModeService.getLectureModeParticipants(serviceRequest);

        int status =  serviceResponse.getStatus();
        if(status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        // total participants except recorder
        int total = serviceResponse.getTotal();
        List<com.vidyo.bo.Entity> list = serviceResponse.getEntities();
        // map list of entities to ws_entities
        for (com.vidyo.bo.Entity entity : list) {

            LectureModeParticipant_type0 ws_entity = getLectureModeWSEntityFromBOEntity(entity);

            EntityID p_id = new EntityID();
            // endpointId as participantId
            p_id.setEntityID(String.valueOf(entity.getEndpointID()));
            ws_entity.setParticipantID(p_id);

            resp.addLectureModeParticipant(ws_entity);
        }
        resp.setRecorderID(serviceResponse.getRecorderID());
        resp.setRecorderName(serviceResponse.getRecorderName());
        resp.setWebcast(serviceResponse.isWebcast());
        resp.setPaused(serviceResponse.isPaused());
        resp.setLectureMode(serviceResponse.isLectureMode());
        resp.setTotal(total);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getLectureModeParticipants() of User API v.1.1");
        }

        return resp;

    }

    @Override
    public UnraiseHandResponse unraiseHand(UnraiseHandRequest unraiseHandRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering unraiseHand() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UnraiseHandResponse response = new UnraiseHandResponse();

        User user = this.userService.getLoginUser();

        MemberHandUpdateRequest serviceRequest = new MemberHandUpdateRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setHandRaised(false);

        HandUpdateResponse serviceResponse =  lectureModeService.updateHand(serviceRequest);

        if (serviceResponse.getStatus() == HandUpdateResponse.SUCCESS) {
            response.setOK(OK_type0.OK);
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(serviceResponse.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting raiseHand() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param stopRecordingRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public StopRecordingResponse stopRecording(
            StopRecordingRequest stopRecordingRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException,
            SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering stopRecording() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = Integer.valueOf(stopRecordingRequest.getConferenceID()
                .getEntityID());

        int endpointID = stopRecordingRequest.getRecorderID();

        String moderatorPIN = stopRecordingRequest.getModeratorPIN();
        if (moderatorPIN == null) {
            moderatorPIN = "noModeratorPIN";
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("ConferenceID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, moderatorPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (room.getAllowRecording() == 0) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = conferenceService.getGUIDForEndpointID(
                endpointID, "R");
        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RecorderID not found in Conference");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            conferenceService.leaveTheConference(endpointGUID, roomID, CallCompletionCode.BY_SOMEONE_ELSE);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        StopRecordingResponse resp = new StopRecordingResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting stopRecording() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param createWebcastURLRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public CreateWebcastURLResponse createWebcastURL(
            CreateWebcastURLRequest createWebcastURLRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createWebcastURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = createWebcastURLRequest.getRoomID();
        String roomId = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomId == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.parseInt(roomId));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        
        User user = this.userService.getLoginUser();
        if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
        	InvalidArgumentFault fault = new InvalidArgumentFault();
        	fault.setErrorMessage("You are not an owner of room");
        	InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
        	exception.setFaultMessage(fault);
        	throw exception;
        }

        boolean throwGeneralFault = false;
        String generalFaultMessg = null;
        try {
                replayService.generateWebCastURL(room);
        } catch (RemoteException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (NoVidyoReplayException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(e.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (throwGeneralFault) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(generalFaultMessg);
            GeneralFaultException exception = new GeneralFaultException(
                    generalFaultMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        CreateWebcastURLResponse resp = new CreateWebcastURLResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createWebcastURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param createWebcastPINRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public CreateWebcastPINResponse createWebcastPIN(
            CreateWebcastPINRequest createWebcastPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createWebcastPIN() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String erroMessg = null;
        boolean errorCondition = false;
        if (createWebcastPINRequest.getRoomID().getEntityID() == null
                || createWebcastPINRequest.getRoomID().getEntityID()
                        .equalsIgnoreCase("")) {
            errorCondition = true;
            erroMessg = "Invalid RoomID";
        }

        String sPIN = createWebcastPINRequest.getPIN();
        if (sPIN == null) {
            sPIN = "";
        }
        if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), sPIN)) {
            errorCondition = true;
            erroMessg = "Invalid PIN";
        }

        if (errorCondition) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(erroMessg);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    erroMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        boolean invalidRoom = false;
        Room room = null;
        if (createWebcastPINRequest.getRoomID().getEntityID() == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer
                        .parseInt(createWebcastPINRequest.getRoomID()
                                .getEntityID()));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room "
                    + createWebcastPINRequest.getRoomID().getEntityID());
            InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            invalidArgumentFaultException.setFaultMessage(fault);
            throw invalidArgumentFaultException;
        }
        
        User user = this.userService.getLoginUser();
		if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			fault.setErrorMessage("You are not an owner of room");
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

        boolean throwGeneralFault = false;
        String generalFaultMessg = null;
        try {
                replayService.updateWebCastPIN(room, sPIN);
        } catch (RemoteException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (NoVidyoReplayException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(e.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (throwGeneralFault) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(generalFaultMessg);
            GeneralFaultException exception = new GeneralFaultException(
                    generalFaultMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        CreateWebcastPINResponse resp = new CreateWebcastPINResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createWebcastPIN() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeWebcastURLRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public RemoveWebcastURLResponse removeWebcastURL(
            RemoveWebcastURLRequest removeWebcastURLRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeWebcastURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = removeWebcastURLRequest.getRoomID();
        String roomID = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomID == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.parseInt(roomID));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        
        User user = this.userService.getLoginUser();
        if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
        	InvalidArgumentFault fault = new InvalidArgumentFault();
        	fault.setErrorMessage("You are not an owner of room");
        	InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
        	exception.setFaultMessage(fault);
        	throw exception;
        }

        boolean throwGeneralFault = false;
        String generalFaultMessg = null;
        try {
                replayService.deleteWebCastURL(room);
        } catch (RemoteException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (NoVidyoReplayException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(e.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (throwGeneralFault) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(generalFaultMessg);
            GeneralFaultException exception = new GeneralFaultException(
                    generalFaultMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        RemoveWebcastURLResponse resp = new RemoveWebcastURLResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeWebcastURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeWebcastPINRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public RemoveWebcastPINResponse removeWebcastPIN(
            RemoveWebcastPINRequest removeWebcastPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeWebcastURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = removeWebcastPINRequest.getRoomID();
        String roomID = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomID == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.parseInt(roomID));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        
        User user = this.userService.getLoginUser();
        if (!RoomUtils.canMemberAccessRoom(user.getMemberID(), this.userService.getLoginUserRole(), room, "noModeratorPIN")) {
        	InvalidArgumentFault fault = new InvalidArgumentFault();
        	fault.setErrorMessage("You are not an owner of room");
        	InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
        	exception.setFaultMessage(fault);
        	throw exception;
        }

        boolean throwGeneralFault = false;
        String generalFaultMessg = null;
        try {
                replayService.updateWebCastPIN(room, "");
        } catch (RemoteException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (NoVidyoReplayException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(e.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (throwGeneralFault) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(generalFaultMessg);
            GeneralFaultException exception = new GeneralFaultException(
                    generalFaultMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        RemoveWebcastPINResponse resp = new RemoveWebcastPINResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeWebcastURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getWebcastURLRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws SeatLicenseExpiredFaultException
     *             :
     */
    @Override
    public GetWebcastURLResponse getWebcastURL(
            GetWebcastURLRequest getWebcastURLRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getWebcastURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = getWebcastURLRequest.getRoomID();
        String roomID = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomID == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.parseInt(roomID));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            invalidArgumentFaultException.setFaultMessage(fault);
            throw invalidArgumentFaultException;
        }

        boolean throwGeneralFault = false;
        String generalFaultMessg = null;
        try {
                replayService.getWebCastURLandPIN(room);
        } catch (RemoteException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.GeneralFaultException e) {
            throwGeneralFault = true;
            generalFaultMessg = e.getMessage();
        } catch (com.vidyo.recordings.webcast.InvalidArgumentFaultException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage(e.getMessage());
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (throwGeneralFault) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(generalFaultMessg);
            GeneralFaultException exception = new GeneralFaultException(
                    generalFaultMessg);
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetWebcastURLResponse resp = new GetWebcastURLResponse();
        resp.setHasWebCastPIN((room.getWebCastPinned() == 1));
        String webcastUrl = room.getWebCastURL() == null ? "" : room
                .getWebCastURL().trim();
        if (webcastUrl != null
                && webcastUrl.indexOf("=") > 0
                && (webcastUrl.substring(webcastUrl.indexOf("=") + 1)).length() <= 0) {
            webcastUrl = "";
        }
        resp.setWebCastURL(webcastUrl);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getWebcastURL() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public GetPINLengthRangeResponse getPINLengthRange(GetPINLengthRangeRequest getPINLengthRangeRequest) throws GeneralFaultException, NotLicensedFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering getPINLengthRange() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        GetPINLengthRangeResponse resp = new GetPINLengthRangeResponse();
        resp.setMinimumPINLength(systemService.getMinPINLengthForTenant(TenantContext.getTenantId()));
        resp.setMaximumPINLength(SystemServiceImpl.PIN_MAX);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getPINLengthRange() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getUserNameRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public GetUserNameResponse getUserName(GetUserNameRequest getUserNameRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getUserName() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        GetUserNameResponse resp = new GetUserNameResponse();
        resp.setRealUserName(user.getUsername());

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getUserName() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getEntityByRoomKeyRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public GetEntityByRoomKeyResponse getEntityByRoomKey(
            GetEntityByRoomKeyRequest getEntityByRoomKeyRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getEntityByRoomKey() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        String roomKey = getEntityByRoomKeyRequest.getRoomKey();
        if (roomKey == null || roomKey.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Bad roomKey");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room = this.roomService.getRoomDetailsByRoomKey(roomKey, TenantContext.getTenantId());

        if (room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not found for roomKey = " + roomKey);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        com.vidyo.bo.Entity entity = this.roomService.getOneEntity(
                room.getRoomID(), user);

        List<Integer> memberIds = new ArrayList<Integer>(1);
        // If the entity is public room, get the personal room id of the member
        if (entity.getRoomType().equalsIgnoreCase(RoomTypes.PUBLIC.getType())
                || entity.getRoomType().equalsIgnoreCase(
                        RoomTypes.SCHEDULED.getType())) {
            // This is the only API which returns scheduled room by room key
            memberIds.add(entity.getMemberID());
        }

        Map<Integer, Integer> memberIdPersonalRoomIdMap = null;
        if(!memberIds.isEmpty()) {
            memberIdPersonalRoomIdMap = memberService.getPersonalRoomIds(memberIds);
        }

        Entity_type0 ws_entity = getWSEntityFromBOEntity(user, entity, memberIdPersonalRoomIdMap);

        GetEntityByRoomKeyResponse resp = new GetEntityByRoomKeyResponse();
        resp.setEntity(ws_entity);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getEntityByRoomKey() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getInviteContentRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public GetInviteContentResponse getInviteContent(
            GetInviteContentRequest getInviteContentRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getInviteContent() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();
        EntityID entityID = getInviteContentRequest.getRoomID();
        int roomID;
        Room room = null;

        if (entityID != null) {
            try {
                roomID = Integer.parseInt(entityID.getEntityID().trim());
            } catch (Exception e) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid RoomId = " + entityID.getEntityID());
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            try {
                room = this.roomService.getRoom(roomID);
            } catch (Exception e) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid RoomId = " + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }

            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid RoomId = " + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        if(room == null) {
            room = roomService.getPersonalOrLegacyRoomForOwnerID(TenantContext.getTenantId(), user.getMemberID());
            if(room == null) {
                logger.error("There is no Personal Room for logged in user : " + user.getUsername());
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("There is no Personal Room for logged in user");
                GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        Tenant tenant = this.tenantService.getTenant(TenantContext.getTenantId());

        Locale l = Locale.getDefault();
		String content = this.systemService.getTenantInvitationEmailContent();
		if (StringUtils.isBlank(content)) {
			content = this.systemService.getSuperInvitationEmailContent();
			if (StringUtils.isBlank(content)) {
				content = this.ms.getMessage("defaultInvitationEmailContent", null, "", l);
			}
		}


        content = constructEmailInviteContent(room, tenant.getTenantDialIn(), tenant.getGuestLogin() == 1,
                tenant.getTenantURL(), tenant.getVidyoGatewayControllerDns(), true, content, false);

        String subject = systemService.constructEmailInviteSubjectForInviteRoom(Locale.getDefault());

        GetInviteContentResponse resp = new GetInviteContentResponse();
        resp.setContent(content);
        resp.setSubject(subject);

        // Add the HTML invite content to the Response if it is requested to be included
        if (getInviteContentRequest.getReturnHtmlContent()){
        	// Get tenant level html content
        	String emailContentHtml = systemService.getConfigValue(TenantContext.getTenantId(), "InvitationEmailContentHtml");
        	if (StringUtils.isBlank(emailContentHtml)) {
        		// Since tenant does not have if, get it from super
        		emailContentHtml = this.systemService.getConfigValue(1, "SuperInvitationEmailContentHtml");
    			if (StringUtils.isBlank(emailContentHtml)) {
    				emailContentHtml = this.ms.getMessage("defaultInvitationEmailContentHtml", null, "", l);
    			}
    		}
       		// As the html content is configured, contruct the content and replace tags
        	emailContentHtml = constructEmailInviteContent(room, tenant.getTenantDialIn(), tenant.getGuestLogin() == 1,
                    tenant.getTenantURL(), tenant.getVidyoGatewayControllerDns(), false, emailContentHtml, true);
        	resp.setHtmlContent(emailContentHtml);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getInviteContent() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getRoomProfilesRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public GetRoomProfilesResponse getRoomProfiles(
            GetRoomProfilesRequest getRoomProfilesRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getRoomProfiles() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        GetRoomProfilesResponse resp = new GetRoomProfilesResponse();

        List<com.vidyo.bo.profile.RoomProfile> roomProfiles = roomService
                .getRoomProfiles();
        for (com.vidyo.bo.profile.RoomProfile roomProfile : roomProfiles) {
            resp.addRoomProfile(getWSRoomProfileFromBORoomProfile(roomProfile));
        }
        resp.setTotal(roomProfiles.size());

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getRoomProfiles() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param setRoomProfileRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public SetRoomProfileResponse setRoomProfile(
            SetRoomProfileRequest setRoomProfileRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getRoomProfiles() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        int roomID = 0;
        try {
            roomID = Integer.parseInt(setRoomProfileRequest.getRoomID()
                    .getEntityID());
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not exist for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String profileName = setRoomProfileRequest.getRoomProfileName();
        if (profileName == null || profileName.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("profileName not provided");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("You are not an owner of room for roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        roomService.updateRoomProfile(roomID, profileName);

        SetRoomProfileResponse resp = new SetRoomProfileResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getRoomProfiles() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeRoomProfileRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public RemoveRoomProfileResponse removeRoomProfile(
            RemoveRoomProfileRequest removeRoomProfileRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeRoomProfile() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        int roomID = 0;
        try {
            roomID = Integer.parseInt(removeRoomProfileRequest.getRoomID()
                    .getEntityID());
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room;
        try {
            room = roomService.getRoom(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not exist for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("You are not an owner of room for roomID = "
                        + roomID);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        try {
            roomService.removeRoomProfile(roomID);
        } catch (java.lang.Exception e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(
                    e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        RemoveRoomProfileResponse resp = new RemoveRoomProfileResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeRoomProfile() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getRoomProfileRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     */
    @Override
    public GetRoomProfileResponse getRoomProfile(
            GetRoomProfileRequest getRoomProfileRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeRoomProfile() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        int roomID = 0;
        try {
            roomID = Integer.parseInt(getRoomProfileRequest.getRoomID()
                    .getEntityID());
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room = null;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            logger.warn("Accessing Invalid Room Id - {}", roomID);
        }

        if(room == null || room.getMemberID() != user.getMemberID()) {
            if (room != null) {
                logger.warn("Un-Authorized Room access - {} attempt by - {} , roomId - {}, userId - {}",
                        room.getRoomName(), user.getUsername(), roomID, user.getMemberID());
            }
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        com.vidyo.bo.profile.RoomProfile roomProfile = null;

        try {
            roomProfile = roomService.getRoomProfile(roomID);
        } catch (java.lang.Exception ignored) {
            logger.info("Room Profile does not exist for Room Id - {}", roomID);
        }

        GetRoomProfileResponse resp = new GetRoomProfileResponse();
        if (roomProfile != null) {
            RoomProfile ws_roomprofile = this
                    .getWSRoomProfileFromBORoomProfile(roomProfile);
            resp.setRoomProfile(ws_roomprofile);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getRoomProfile() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param createModeratorPINRequest
     *            :
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws InvalidModeratorPINFormatFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     */
    @Override
    public CreateModeratorPINResponse createModeratorPIN(
            CreateModeratorPINRequest createModeratorPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, InvalidModeratorPINFormatFaultException,
            ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering createModeratorPIN() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = 0;
        try {
            roomID = Integer.parseInt(createModeratorPINRequest.getRoomID()
                    .getEntityID());
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    "Invalid roomID = " + roomID);
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not exist for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    "Room not exist for roomID = " + roomID);
            exception.setFaultMessage(fault);
            throw exception;
        }

        String sPIN = createModeratorPINRequest.getPIN();
        if (sPIN == null) {
            sPIN = "";
        }

        if (sPIN.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("PIN not provided");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (!systemService.isPINLengthAcceptable(TenantContext.getTenantId(), sPIN)) {
            InvalidModeratorPINFormatFault fault = new InvalidModeratorPINFormatFault();
            fault.setErrorMessage("PIN should be a " +
                    systemService.getMinPINLengthForTenant(TenantContext.getTenantId()) +
                    "-" + SystemServiceImpl.PIN_MAX + " digit number");
            InvalidModeratorPINFormatFaultException exception = new InvalidModeratorPINFormatFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, sPIN)) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        room.setModeratorPinSetting("enter");
        room.setRoomModeratorPIN(sPIN);
        roomService.updateRoomModeratorPIN(room);

        CreateModeratorPINResponse resp = new CreateModeratorPINResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createModeratorPIN() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeModeratorPINRequest
     * @throws NotLicensedFaultException
     *             :
     * @throws InvalidArgumentFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws ControlMeetingFaultException
     *             :
     */
    @Override
    public RemoveModeratorPINResponse removeModeratorPIN(
            RemoveModeratorPINRequest removeModeratorPINRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, ControlMeetingFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeModeratorPIN() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        int roomID = 0;
        try {
            roomID = Integer.parseInt(removeModeratorPINRequest.getRoomID()
                    .getEntityID());
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Room not exist for roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, "noModeratorPIN")) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        room.setModeratorPinSetting("remove");
        roomService.updateRoomModeratorPIN(room);

        RemoveModeratorPINResponse resp = new RemoveModeratorPINResponse();
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeModeratorPIN() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public SearchMembersResponse searchMembers(SearchMembersRequest searchMembersRequest) throws InvalidArgumentFaultException, GeneralFaultException, SeatLicenseExpiredFaultException, NotLicensedFaultException {

        BigInteger start = searchMembersRequest.getStart();
        if (start == null) {
            start = new BigInteger("0");
        } else if (start.intValue() > 99) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("start must be in between 0-99");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        BigInteger limit =  searchMembersRequest.getLimit();
        if (limit == null) {
            limit = new BigInteger("10");
        } else if (limit.intValue() > 100) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("limit must be in between 0-100");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String memberType = StringUtils.trimToNull(searchMembersRequest.getMemberType());
        List<Integer> memberTypes = new ArrayList<Integer>();
        if (memberType == null) {
            memberTypes.add(1);
            memberTypes.add(2);
            memberTypes.add(3);
            memberTypes.add(4);
            memberTypes.add(5);
            memberTypes.add(7);
            memberTypes.add(8);
        } else if (memberType.contains("|")) { // multiple
            String[] parts = memberType.split("\\|");
            int i = 0;
            for (String part : parts) {
                if (part.equalsIgnoreCase("member")) {
                    memberTypes.add(1);
                    memberTypes.add(2);
                    memberTypes.add(3);
                    memberTypes.add(4);
                    memberTypes.add(5);
                    memberTypes.add(7);
                    memberTypes.add(8);
                } else if (part.equalsIgnoreCase("legacy")) {
                    memberTypes.add(6);
                }
                i++;
                if (i >= 2) {
                    break;
                }
            }
        } else { // single
            if (memberType.equalsIgnoreCase("member")) {
                memberTypes.add(1);
                memberTypes.add(2);
                memberTypes.add(3);
                memberTypes.add(4);
                memberTypes.add(5);
                memberTypes.add(7);
                memberTypes.add(8);
            } else if (memberType.equalsIgnoreCase("legacy")) {
                memberTypes.add(6);
            }
        }
        // if still nothing, use default
        if (memberTypes.size() == 0) {
            memberTypes.add(1);
            memberTypes.add(2);
            memberTypes.add(3);
            memberTypes.add(4);
            memberTypes.add(5);
            memberTypes.add(7);
            memberTypes.add(8);
        }

        String sortBy = StringUtils.trimToNull(searchMembersRequest.getSortBy());
        if (sortBy == null) {
            sortBy = "memberName";
        } else if (sortBy.equalsIgnoreCase("name")) {
            sortBy = "memberName";
        } else if (!sortBy.equalsIgnoreCase("name")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("sortBy value invalid, must be \"name\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        SortDir sortDir = searchMembersRequest.getSortDir();
        if (sortDir == null) {
            sortDir = SortDir.ASC;
        }

        String query = StringUtils.trimToNull(searchMembersRequest.getQuery());
        if (query == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string cannot be empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        if (StringUtils.deleteWhitespace(query).length() < 2) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string must be at least two characters");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String queryField = StringUtils.trimToNull(searchMembersRequest.getQueryField());
        if (queryField == null) {
            queryField = "name";
        } else if (queryField.equalsIgnoreCase("name")) {
            queryField = "memberName";
        } else if (queryField.equalsIgnoreCase("email")) {
            queryField = "emailAddress";
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("queryField must be \"name\" or \"email\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        SearchMembersResponse response = new SearchMembersResponse();

        int total = this.memberService.findMembersCount(query, queryField, memberTypes);
        response.setTotal(total);

        User user = userService.getLoginUser();
        int thisUserMemberID = user.getMemberID();

        Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
        TenantConfiguration tenantConfiguration = this.tenantService
                .getTenantConfiguration(TenantContext.getTenantId());

        if (total > 0 && limit.intValue() > 0) {
            List<MemberMini> members = this.memberService.findMembers(thisUserMemberID,
                    query, queryField, memberTypes,
                    start.intValue(), limit.intValue(),
                    sortBy, sortDir.toString());

            for (MemberMini member : members) {
                Member_type0 m = new Member_type0();
                EntityID entityID = new EntityID();
                entityID.setEntityID("" + member.getMemberID());
                m.setEntityID(entityID);
                m.setName(member.getName());
                m.setDescription(member.getDescription());
                m.setEmail(member.getEmail());
                m.setEnabled(member.isEnabled());
                m.setType(member.getType());

                if (member.getStatus() == 0) {
                    m.setMemberStatus(MemberStatus_type0.Offline);
                } else if (member.getStatus() == 1) {
                    m.setMemberStatus(MemberStatus_type0.Online);
                } else if (member.getStatus() == 2) {
                    m.setMemberStatus(MemberStatus_type0.Busy);
                } else if (member.getStatus() == 3) {
                    m.setMemberStatus(MemberStatus_type0.Ringing);
                } else if (member.getStatus() == 4) {
                    m.setMemberStatus(MemberStatus_type0.RingAccepted);
                } else if (member.getStatus() == 5) {
                    m.setMemberStatus(MemberStatus_type0.RingRejected);
                } else if (member.getStatus() == 6) {
                    m.setMemberStatus(MemberStatus_type0.RingNoAnswer);
                } else if (member.getStatus() == 7) {
                    m.setMemberStatus(MemberStatus_type0.Alerting);
                } else if (member.getStatus() == 8) {
                    m.setMemberStatus(MemberStatus_type0.AlertCancelled);
                } else if (member.getStatus() == 9) {
                    m.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
                } else if (member.getStatus() == 12) {
                    m.setMemberStatus(MemberStatus_type0.Busy); // WaitForConfirm
                } else {
                    // unknown status
                    m.setMemberStatus(MemberStatus_type0.Offline);
                }
                m.setIsInMyContacts(member.isInContacts());
                if (member.getPhone1() != null && StringUtils.isNotBlank(member.getPhone1())){
                    m.setPhone1(member.getPhone1());
                }
                if (member.getPhone2() != null && StringUtils.isNotBlank(member.getPhone2())){
                    m.setPhone2(member.getPhone2());
                }
                if (member.getPhone3() != null && StringUtils.isNotBlank(member.getPhone3())){
                    m.setPhone3(member.getPhone3());
                }
                if (member.getDepartment() != null && StringUtils.isNotBlank(member.getDepartment())){
                    m.setDepartment(member.getDepartment());
                }
                if (member.getTitle() != null && StringUtils.isNotBlank(member.getTitle())){
                    m.setTitle(member.getTitle());
                }
                if (member.getInstantMessagerID() != null && StringUtils.isNotBlank(member.getInstantMessagerID())){
                    m.setInstantMessagerID(member.getInstantMessagerID());
                }
                if (userImageConf != null && StringUtils.isNotBlank(userImageConf.getConfigurationValue()) && userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
                    if (tenantConfiguration.getUserImage() == 1) {
                        if (member.getThumbnailUpdateTime() != null){
                            Calendar updateTime = Calendar.getInstance();;
                            updateTime.setTime(member.getThumbnailUpdateTime());
                            m.setThumbnailUpdateTime(updateTime);
                        }
                    }
                }
                response.addMember(m);
            }
        }

        // convert back
        if (sortBy.equals("memberName")) {
            sortBy = "name";
        } else {
            sortBy = "name"; // expand sortBy possibilities in future possibly
        }

        if (queryField.equals("memberName")) {
            queryField = "name";
        } else if (queryField.equals("emailAddress")) {
            queryField = "email";
        }

        String memberTypeOut = "member";
        if (memberTypes.size() > 0) {
            boolean containsMember =  memberTypes.contains(1) || memberTypes.contains(2)  || memberTypes.contains(3) || memberTypes.contains(4)
                    || memberTypes.contains(5) || memberTypes.contains(7) || memberTypes.contains(8);
            boolean containsLegacy = memberTypes.contains(6);

            if (containsMember && containsLegacy) {
                memberTypeOut = "member|legacy";
            } else if (containsMember) {
                memberTypeOut = "member";
            } else if (containsLegacy) {
                memberTypeOut = "legacy";
            }
         }

        response.setStart(start);
        response.setLimit(limit);
        response.setSortBy(sortBy);
        response.setSortDir(sortDir);
        response.setQuery(query);
        response.setQueryField(queryField);
        response.setMemberType(memberTypeOut);

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param getConferenceIDRequest38
     * @return getConferenceIDResponse39
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */
    @Override
    public GetConferenceIDResponse getConferenceID(GetConferenceIDRequest getConferenceIDRequest38)
            throws NotLicensedFaultException, InPointToPointCallFaultException, GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getConferenceID() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        try {
            User user = userService.getLoginUser();

            GetConferenceIDResponse response = new GetConferenceIDResponse();

            Control control = conferenceService.getControlForMember(user.getMemberID(), null);
            if(control == null) {
                throw new ConferenceNotExistException();
            }
            /*if(control.getConferenceType().equalsIgnoreCase("P")) {
                InPointToPointCallFault fault = new InPointToPointCallFault();
                fault.setErrorMessage("User is in P2P call");
                InPointToPointCallFaultException ex = new InPointToPointCallFaultException(fault.getErrorMessage());
                ex.setFaultMessage(fault);
                throw ex;
            }*/

            EntityID entityID = new EntityID();
            entityID.setEntityID(String.valueOf(control.getRoomID()));

            response.setConferenceID(entityID);

            if (logger.isDebugEnabled()) {
                logger.debug("Exiting getConferenceID() of User API v.1.1");
            }

            return response;
        } catch (ConferenceNotExistException anyEx) {
            String errMsg = "Failed to getConferenceID. User " +
                    userService.getLoginUser().getMemberName() + " is not in the Conference";
            logger.error(errMsg);

            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(errMsg);
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } catch (Exception anyEx) {
            logger.error("Failed to getConferenceID.", anyEx);

            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Failed to getConferenceID.");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

    }

    /**
     * Generates a Persistent token for the user.
     *
     * @param generateAuthTokenRequest
     * @throws EndpointNotBoundFaultException
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws SeatLicenseExpiredFaultException
     */
    @Override
    public com.vidyo.portal.user.v1_1.GenerateAuthTokenResponse generateAuthToken(
            com.vidyo.portal.user.v1_1.GenerateAuthTokenRequest generateAuthTokenRequest)
            throws EndpointNotBoundFaultException, NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {

        logger.debug("Entering generateAuthToken() of VidyoPortalUserServiceSkeleton");

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();
        if (user == null || user.getMemberID() == 0) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (generateAuthTokenRequest.getEndpointId() == null
                || generateAuthTokenRequest.getEndpointId().trim().length() == 0) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid EndpointId");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        TokenCreationRequest tokenCreationRequest = new TokenCreationRequest();
        tokenCreationRequest.setEndpointId(generateAuthTokenRequest.getEndpointId().trim());
        tokenCreationRequest.setTenantId(user.getTenantID());
        tokenCreationRequest.setMemberId(user.getMemberID());
        tokenCreationRequest.setUsername(user.getUsername());
        //tokenCreationRequest.setValidityTime(generateAuthTokenRequest.getValidityTime().getValidityTime_type0());

        TokenCreationResponse tokenCreationResponse = persistentTokenService
                .createPersistentToken(tokenCreationRequest);

        if (tokenCreationResponse.getStatus() != TokenCreationResponse.SUCCESS) {
            if (tokenCreationResponse.getStatus() == TokenCreationResponse.ENDPOINT_NOT_BOUND_TO_USER) {
                EndpointNotBoundFault boundFault = new EndpointNotBoundFault();
                boundFault.setErrorMessage("Endpoint not bound to the user");
                EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException(
                        boundFault.getErrorMessage());
                notBoundFaultException.setFaultMessage(boundFault);
                throw notBoundFaultException;
            }
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(tokenCreationResponse.getMessage());
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GenerateAuthTokenResponse authTokenResponse = new GenerateAuthTokenResponse();
        authTokenResponse.setAuthToken(tokenCreationResponse.getToken());
        logger.debug("Exiting generateAuthToken() of VidyoPortalUserServiceSkeleton");
        return authTokenResponse;
    }

    /**
     *
     */
    @Override
    public CreateScheduledRoomResponse createScheduledRoom(
            CreateScheduledRoomRequest createScheduledRoomRequest)
            throws ScheduledRoomCreationFaultException,
            NotLicensedFaultException, InvalidArgumentFaultException,
            GeneralFaultException, SeatLicenseExpiredFaultException {

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();
        if (user == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());
        if (member == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int recurring = 0;
        if (createScheduledRoomRequest.getRecurring() != null) {
            recurring = createScheduledRoomRequest.getRecurring().getRecurring_type0();
        }

        SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
        schRoomCreationRequest.setTenantId(member.getTenantID());
        schRoomCreationRequest.setMemberId(member.getMemberID());
        schRoomCreationRequest.setGroupId(member.getGroupID());
        schRoomCreationRequest.setMemberName(member.getMemberName());
        schRoomCreationRequest.setRecurring(recurring);
        schRoomCreationRequest.setPinRequired(createScheduledRoomRequest.getSetPIN());
        RoomCreationResponse roomCreationResponse = roomService.createScheduledRoom(schRoomCreationRequest);

        // If the response status is non-zero [failure], check the status code
        // and return fault
        if (roomCreationResponse.getStatus() != 0) {
            ScheduledRoomCreationFault fault = new ScheduledRoomCreationFault();
            fault.setErrorMessage(roomCreationResponse.getMessage());
            ScheduledRoomCreationFaultException exception = new ScheduledRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        CreateScheduledRoomResponse createScheduledRoomResponse = new CreateScheduledRoomResponse();
        Extension_type3 extn = new Extension_type3();
        extn.setExtension_type2(roomCreationResponse.getExtensionValue());
        createScheduledRoomResponse.setExtension(extn);

        // Scheduled Room - values customization
        roomCreationResponse.getRoom().setRoomName(roomCreationResponse.getExtensionValue());

		String inviteContent = this.systemService.getTenantInvitationEmailContent();
		if (StringUtils.isBlank(inviteContent)) {
			inviteContent = this.systemService.getSuperInvitationEmailContent();
			if (StringUtils.isBlank(inviteContent)) {
				inviteContent = this.ms.getMessage("defaultInvitationEmailContent", null, "", Locale.getDefault());
			}
		}
		inviteContent  = constructEmailInviteContent(roomCreationResponse.getRoom(),
                roomCreationResponse.getTenant().getTenantDialIn(),
                (roomCreationResponse.getTenant().getGuestLogin() == 1),
                roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getTenant().getVidyoGatewayControllerDns(), false,
                inviteContent, false);
        createScheduledRoomResponse.setInviteContent(inviteContent);
        String inviteSubject = systemService.constructEmailInviteSubjectForInviteRoom(Locale.getDefault());
        createScheduledRoomResponse.setInviteSubject(inviteSubject);

        URI uri = null;
        try {
            String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            String joinURL = roomService.getRoomURL(systemService, transportName,
                    roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getRoom().getRoomKey());

            uri = new URI(joinURL);
        } catch (MalformedURIException e) {
            logger.error("Error", e);
        }
        createScheduledRoomResponse.setRoomURL(uri);
        if(roomCreationResponse.getRoom().getRoomPIN() != null) {
            Pin_type1 pin = new Pin_type1();
            pin.setPin_type0(roomCreationResponse.getRoom().getRoomPIN());
            createScheduledRoomResponse.setPin(pin);
        }

     // Add the HTML invite content to the Response if it is requested to be included
        if (createScheduledRoomRequest.getReturnHtmlContent()){
        	// Get tenant level html content
        	String emailContentHtml = systemService.getConfigValue(TenantContext.getTenantId(), "InvitationEmailContentHtml");
        	if (StringUtils.isBlank(emailContentHtml)) {
        		// Since tenant does not have if, get it from super
        		emailContentHtml = this.systemService.getConfigValue(1, "SuperInvitationEmailContentHtml");
    			if (StringUtils.isBlank(emailContentHtml)) {
    				emailContentHtml = this.ms.getMessage("defaultInvitationEmailContentHtml", null, "", Locale.getDefault());
    			}
    		}
       		// As the html content is configured, contruct the content and replace tags
        	emailContentHtml = constructEmailInviteContent(roomCreationResponse.getRoom(), roomCreationResponse.getTenant().getTenantDialIn(),
        			(roomCreationResponse.getTenant().getGuestLogin() == 1),
        			roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getTenant().getVidyoGatewayControllerDns(), false, emailContentHtml, true);
        	createScheduledRoomResponse.setHtmlContent(emailContentHtml);
        }

		if (createScheduledRoomRequest.getReturnRoomDetails()) {
			Room createdRoom = roomCreationResponse.getRoom();
			Room_type0 roomDetails = new Room_type0();

			EntityID entityID = new EntityID();
			entityID.setEntityID(String.valueOf(createdRoom.getRoomID()));
			roomDetails.setEntityID(entityID);

			Integer personalRoomId = memberService.getPersonalRoomId(user.getMemberID());
			EntityID ownerEntityID = new EntityID();
			ownerEntityID.setEntityID(personalRoomId.toString());
			roomDetails.setOwnerEntityID(ownerEntityID);

			roomDetails.setExtension(createdRoom.getRoomExtNumber());
			roomDetails.setName(createdRoom.getRoomName());
			roomDetails.setDisplayName(createdRoom.getDisplayName());
			roomDetails.setDescription(createdRoom.getRoomDescription());
			roomDetails.setOwnerName(user.getMemberName());
			roomDetails.setType(createdRoom.getRoomType());
			roomDetails.setPinned(StringUtils.isNotBlank(createdRoom.getRoomPIN()));
			roomDetails.setLocked(createdRoom.getRoomLocked() == 1);
			roomDetails.setEnabled(createdRoom.getRoomEnabled() == 1);
			roomDetails.setIsInMyContacts(false);

			createScheduledRoomResponse.setRoom(roomDetails);
		}

        return createScheduledRoomResponse;
    }

    /**
     * Utility method to create Email Invite Content
     * @param scheduledRoom
     * @param tenantDialIn
     * @param guestsAllowed
     * @param tenantUrl
     * @return
     */
    private String constructEmailInviteContent(Room room,
            String tenantDialIn, boolean guestsAllowed, String tenantUrl, String gateWayDns, boolean overrideScheduledRoomProperties, String content, boolean encodeForHtml) {

        InviteEmailContentForInviteRoom inviteEmailContentForInviteRoom = new InviteEmailContentForInviteRoom();
        inviteEmailContentForInviteRoom.setGateWayDns(gateWayDns);
        inviteEmailContentForInviteRoom.setGuestsAllowed(guestsAllowed);
        inviteEmailContentForInviteRoom.setLocale(Locale.getDefault());
        inviteEmailContentForInviteRoom.setOverrideScheduledRoomProperties(overrideScheduledRoomProperties);
        inviteEmailContentForInviteRoom.setRoom(room);
        inviteEmailContentForInviteRoom.setTenantDialIn(tenantDialIn);
        inviteEmailContentForInviteRoom.setTenantUrl(tenantUrl);
        inviteEmailContentForInviteRoom.setTransportName(MessageContext.getCurrentMessageContext().getIncomingTransportName());

        content = this.systemService.constructEmailInviteContentForInviteRoom(inviteEmailContentForInviteRoom, content, encodeForHtml);

        return content;

    }

    /**
     * NOTE: ADD NEW FEATURES TO THE BOTTOM OF THE LIST (see VPTL-5857)
     *
     * @param getPortalFeaturesRequest
     * @return getPortalFeaturesResponse
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */
    @Override
    public GetPortalFeaturesResponse getPortalFeatures
        (
            GetPortalFeaturesRequest getPortalFeaturesRequest
        )
        throws NotLicensedFaultException,
        GeneralFaultException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getPortalFeatures() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        GetPortalFeaturesResponse resp = new GetPortalFeaturesResponse();

        User user = userService.getLoginUser();
        boolean isVidyoRoom = "ROLE_VIDYOROOM".equalsIgnoreCase(user.getUserRole());
        Tenant tenant = null;
        Integer tenantId =  TenantContext.getTenantId();
        tenant = tenantService.getTenant(tenantId);

        // FEATURE - ScheduledRoom
        boolean scheduledRoomEnabled = false;
        if (tenant != null) {
            scheduledRoomEnabled = tenant.getScheduledRoomEnabled() == 1;
        }
        // ScheduledRoom2 - indicates increased scheduled room capacity starting
        // v3.3.4
        //***************
        // VERY IMPORTANT - SCHEDULEDROOM2 flag should always be the 1st element, otherwise client parser will break.
        //***************
        PortalFeature_type0 scheduledRoom2Feature = new PortalFeature_type0();
        PortalFeatureName scheduledRoom2FeatureName = new PortalFeatureName();
        scheduledRoom2FeatureName
                .setPortalFeatureName("ScheduledRoom2");
        scheduledRoom2Feature.setFeature(scheduledRoom2FeatureName);
        scheduledRoom2Feature.setEnable(scheduledRoomEnabled);
        resp.addPortalFeature(scheduledRoom2Feature);

        PortalFeature_type0 scheduledRoomFeature = new PortalFeature_type0();
        PortalFeatureName scheduledRoomFeatureName = new PortalFeatureName();
        scheduledRoomFeatureName
                .setPortalFeatureName("ScheduledRoom");
        scheduledRoomFeature.setFeature(scheduledRoomFeatureName);
        scheduledRoomFeature.setEnable(scheduledRoomEnabled);
        resp.addPortalFeature(scheduledRoomFeature);

        // FEATURE - Guest login for tenant
        if(!isVidyoRoom) {
            PortalFeature_type0 guestFeature = new PortalFeature_type0();
            PortalFeatureName guestFeatureName = new PortalFeatureName();
            guestFeatureName.setPortalFeatureName("Guest");
            guestFeature.setFeature(guestFeatureName);
            guestFeature.setEnable(!this.tenantService.isTenantNotAllowingGuests());

            resp.addPortalFeature(guestFeature);
        }

        // FEATURE - IPC outbound and IPC inbound for tenant
        boolean IPCoutbound = false;
        boolean IPCinbound = false;

        IpcConfiguration interPortalConference = ipcConfigurationService.getIpcConfiguration(tenantId);
        IPCoutbound = interPortalConference.getOutbound() == 1;
        IPCinbound = interPortalConference.getInbound() == 1;

        PortalFeature_type0 ipcOutboundFeature = new PortalFeature_type0();
        PortalFeatureName ipcOutboundFeatureName = new PortalFeatureName();
        ipcOutboundFeatureName.setPortalFeatureName("IPCoutbound");
        ipcOutboundFeature.setFeature(ipcOutboundFeatureName);
        ipcOutboundFeature.setEnable(IPCoutbound);

        resp.addPortalFeature(ipcOutboundFeature);

        PortalFeature_type0 ipcInboundFeature = new PortalFeature_type0();
        PortalFeatureName ipcInboundFeatureName = new PortalFeatureName();
        ipcInboundFeatureName.setPortalFeatureName("IPCinbound");
        ipcInboundFeature.setFeature(ipcInboundFeatureName);
        ipcInboundFeature.setEnable(IPCinbound);

        resp.addPortalFeature(ipcInboundFeature);

        // Is Moderator URL enabled always?
        PortalFeature_type0 moderatorURLFeature = new PortalFeature_type0();
        PortalFeatureName modePortalFeatureName = new PortalFeatureName();
        modePortalFeatureName.setPortalFeatureName("ModeratorURL");
        moderatorURLFeature.setFeature(modePortalFeatureName);
        moderatorURLFeature.setEnable(true);

        resp.addPortalFeature(moderatorURLFeature);

        // FEATURE - TlsTunneling
        boolean tlsTunneling = systemService.getTLSProxyConfiguration();

        PortalFeature_type0 tlsTunnelingFeature = new PortalFeature_type0();
        PortalFeatureName tlPortalFeatureName = new PortalFeatureName();
        tlPortalFeatureName.setPortalFeatureName("TlsTunneling");
        tlsTunnelingFeature.setFeature(tlPortalFeatureName);
        tlsTunnelingFeature.setEnable(tlsTunneling);

        resp.addPortalFeature(tlsTunnelingFeature);

        // FEATURES - Welcome and Login banners
        Banners banners = systemService.getBannersInfo();

        PortalFeature_type0 loginBannerFeature = new PortalFeature_type0();
        PortalFeatureName loginPortalFeatureName = new PortalFeatureName();
        loginPortalFeatureName.setPortalFeatureName("LoginBanner");
        loginBannerFeature.setFeature(loginPortalFeatureName);
        loginBannerFeature.setEnable(banners.getShowLoginBanner());

        resp.addPortalFeature(loginBannerFeature);

        PortalFeature_type0 welcomeBannerFeature = new PortalFeature_type0();
        PortalFeatureName welBannerPortalFeatureName = new PortalFeatureName();
        welBannerPortalFeatureName.setPortalFeatureName("WelcomeBanner");
        welcomeBannerFeature.setFeature(welBannerPortalFeatureName);
        welcomeBannerFeature.setEnable(banners.getShowWelcomeBanner());

        resp.addPortalFeature(welcomeBannerFeature);
        TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
        if(!isVidyoRoom) {
            // FEATURE - EndpointPrivateChat
            PortalFeature_type0 endpointPrivateChatFeature = new PortalFeature_type0();
            PortalFeatureName endpointPrivateChatFeatureName = new PortalFeatureName();
            endpointPrivateChatFeatureName.setPortalFeatureName("EndpointPrivateChat");
            endpointPrivateChatFeature.setFeature(endpointPrivateChatFeatureName);

            // FEATURE - EndpointPrivateChat
            PortalFeature_type0 endpointPublicChatFeature = new PortalFeature_type0();
            PortalFeatureName endpointPublicChatFeatureName = new PortalFeatureName();
            endpointPublicChatFeatureName.setPortalFeatureName("EndpointPublicChat");
            endpointPublicChatFeature.setFeature(endpointPublicChatFeatureName);

            PortalChat portalChat = systemService.getPortalChat();
            if(portalChat.isChatAvailable()) {
            	if(tenantConfiguration != null) {
                    endpointPrivateChatFeature.setEnable(tenantConfiguration.getEndpointPrivateChat() == 1);
                    endpointPublicChatFeature.setEnable(tenantConfiguration.getEndpointPublicChat() == 1);
            	}

            } else {
                endpointPrivateChatFeature.setEnable(false);

                endpointPublicChatFeature.setEnable(false);
            }

            resp.addPortalFeature(endpointPrivateChatFeature);
            resp.addPortalFeature(endpointPublicChatFeature);
        }

        // FEATURE - CDR2.1
        PortalFeature_type0 cdrEnhancement = new PortalFeature_type0();
        PortalFeatureName cdrEnhancementFeatureName = new PortalFeatureName();
        cdrEnhancementFeatureName.setPortalFeatureName("CDR2_1");
        cdrEnhancement.setFeature(cdrEnhancementFeatureName);
        cdrEnhancement.setEnable(true);

        resp.addPortalFeature(cdrEnhancement);

        // FEATURE - ability send endpoint features to portal
        PortalFeature_type0 endpointDetails = new PortalFeature_type0();
        PortalFeatureName endpointDetailsName = new PortalFeatureName();
        endpointDetailsName.setPortalFeatureName("EndpointDetails");
        endpointDetails.setFeature(endpointDetailsName);
        endpointDetails.setEnable(true);

        resp.addPortalFeature(endpointDetails);


        // FEATURE - Make 16 tiles layout available on your VidyoPortal
        if(!isVidyoRoom) {
            if(systemService.isTiles16Available()) {
                PortalFeature_type0 tiles16Features = new PortalFeature_type0();
                PortalFeatureName tiles16FeaturesName = new PortalFeatureName();
                tiles16FeaturesName.setPortalFeatureName("16TILES");
                tiles16Features.setFeature(tiles16FeaturesName);
                tiles16Features.setEnable(systemService.isTiles16Available());
                resp.addPortalFeature(tiles16Features);
            }

            // FEATURE - Indicate that this version supports the new password change page by reporting "HTMLChangePswd"
            PortalFeature_type0 htmlChangePswd = new PortalFeature_type0();
            PortalFeatureName htmlChangePswdName = new PortalFeatureName();
            htmlChangePswdName.setPortalFeatureName("HTMLChangePswd");
            htmlChangePswd.setFeature(htmlChangePswdName);

			AuthenticationConfig authConfig = systemService.getAuthenticationConfig(tenantId);
			AuthenticationType authType = authConfig.toAuthentication().getAuthenticationType();
			htmlChangePswd.setEnable(authType.equals(AuthenticationType.INTERNAL) ? true : false);

            resp.addPortalFeature(htmlChangePswd);
        }

        // FEATURE - Indicate that the router is passing additional participant information starting in 3.3.
        PortalFeature_type0 routerParticipantInformation = new PortalFeature_type0();
        PortalFeatureName routerParticipantInformationName = new PortalFeatureName();
        routerParticipantInformationName.setPortalFeatureName("RouterParticipantInformation");
        routerParticipantInformation.setFeature(routerParticipantInformationName);

        routerParticipantInformation.setEnable(true);

        resp.addPortalFeature(routerParticipantInformation);

        // bind ack in VP 3.4.3 and above
        PortalFeature_type0 bindAckFeature = new PortalFeature_type0();
        PortalFeatureName bindAckFeatureName = new PortalFeatureName();
        bindAckFeatureName.setPortalFeatureName("BindAck");
        bindAckFeature.setFeature(bindAckFeatureName);
        bindAckFeature.setEnable(true);
        resp.addPortalFeature(bindAckFeature);

        // neo direct call flow supported in VP3.4.4 and above only
        // scheduled room + recurring + conference appData info
        PortalFeature_type0 neoDirectCallSupport = new PortalFeature_type0();
        PortalFeatureName neoDirectCallFeatureName = new PortalFeatureName();
        neoDirectCallFeatureName.setPortalFeatureName("NeoDirectCall");
        neoDirectCallSupport.setFeature(neoDirectCallFeatureName);
        neoDirectCallSupport.setEnable(scheduledRoomEnabled);

        resp.addPortalFeature(neoDirectCallSupport);

        // FEATURE - Public room feature
        Configuration createPublicRoomconfig = systemService.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");

        boolean createPublicRoomEnabled = false;

        if (createPublicRoomconfig == null
                || createPublicRoomconfig.getConfigurationValue() == null
                || createPublicRoomconfig.getConfigurationValue().trim().length() == 0
                || createPublicRoomconfig.getConfigurationValue().trim().equals("0"))
        {
            createPublicRoomEnabled = false;
        } else {
            if (tenant != null) {
                TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenant.getTenantID());
                if (tenantConfig != null) {
                    createPublicRoomEnabled = tenantConfig.getCreatePublicRoomEnable() == 1;
                }
            }
        }

        PortalFeature_type0 createPublicRoomFeature = new PortalFeature_type0();
        PortalFeatureName createPublicRoomFeatureName = new PortalFeatureName();
        createPublicRoomFeatureName.setPortalFeatureName("CreatePublicRoom");
        createPublicRoomFeature.setFeature(createPublicRoomFeatureName);
        createPublicRoomFeature.setEnable(createPublicRoomEnabled);

        resp.addPortalFeature(createPublicRoomFeature);
        
        // TytoCare
     	PortalFeature_type0 tytoCareFeature = new PortalFeature_type0();
     	PortalFeatureName tytoCareFeatureFeatureName = new PortalFeatureName();
     	tytoCareFeatureFeatureName.setPortalFeatureName(PortalFeaturesEnum.TytoCare.name());
     	tytoCareFeature.setFeature(tytoCareFeatureFeatureName);
     	tytoCareFeature.setEnable(extIntegrationService.isTenantTytoCareEnabled());
     	resp.addPortalFeature(tytoCareFeature);

        // FEATURE - Thumbnail photo feature
        Configuration thumbnailConfig = systemService.getConfiguration("USER_IMAGE");

        boolean isUserImageEnabled = false;

        if (thumbnailConfig == null
                || thumbnailConfig.getConfigurationValue() == null
                || thumbnailConfig.getConfigurationValue().trim().length() == 0
                || thumbnailConfig.getConfigurationValue().trim().equals("0"))
        {
            isUserImageEnabled = false;
        } else {
            if (tenant != null) {
                TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenant.getTenantID());
                if (tenantConfig != null) {
                    isUserImageEnabled = tenantConfig.getUserImage() == 1;
                }
            }
        }

        PortalFeature_type0 thumbnailFeature = new PortalFeature_type0();
        PortalFeatureName thumbnailFeatureName = new PortalFeatureName();
        thumbnailFeatureName.setPortalFeatureName("ThumbnailPhoto");
        thumbnailFeature.setFeature(thumbnailFeatureName);
        thumbnailFeature.setEnable(isUserImageEnabled);

        resp.addPortalFeature(thumbnailFeature);

        // Add this feature and send it in response only if the flag is set
        if (VendorUtils.isModeratedConferenceAllowed()) {
            PortalFeature_type0 moderatedConfFeature = new PortalFeature_type0();
            PortalFeatureName moderatedConfFeatureName = new PortalFeatureName();
            moderatedConfFeatureName.setPortalFeatureName("ModeratedConference");
            moderatedConfFeature.setFeature(moderatedConfFeatureName);
            moderatedConfFeature.setEnable(true);
            resp.addPortalFeature(moderatedConfFeature);
        }

		// FEATURE - If SDK 2,2 router feature enabled
		Configuration opusAudio = systemService.getConfiguration("OPUS_AUDIO");

		if (opusAudio != null
				&& StringUtils.isNotBlank(opusAudio.getConfigurationValue()))
		{
			PortalFeature_type0 opusAudioFeature = new PortalFeature_type0();
			PortalFeatureName opusAudioFeatureName = new PortalFeatureName();
			opusAudioFeatureName.setPortalFeatureName("OpusAudio");
			opusAudioFeature.setFeature(opusAudioFeatureName);
			opusAudioFeature.setEnable(opusAudio.getConfigurationValue().equalsIgnoreCase("1"));

			resp.addPortalFeature(opusAudioFeature);
		}

		Configuration sdk220 = systemService.getConfiguration("SDK220");
		if(sdk220 != null && StringUtils.isNotBlank(sdk220.getConfigurationValue())) {
			PortalFeature_type0 sdk220Feature = new PortalFeature_type0();
			PortalFeatureName sdk220FeatureName = new PortalFeatureName();
			sdk220FeatureName.setPortalFeatureName("SDK220");
			sdk220Feature.setFeature(sdk220FeatureName);
			sdk220Feature.setEnable(sdk220.getConfigurationValue().equalsIgnoreCase("1"));
			resp.addPortalFeature(sdk220Feature);
		}

		if (tenantConfiguration != null) {
			PortalFeature_type0 testCallFeature = new PortalFeature_type0();
			PortalFeatureName testCallFeatureName = new PortalFeatureName();
			testCallFeatureName.setPortalFeatureName("TestCall");
			testCallFeature.setFeature(testCallFeatureName);
			testCallFeature.setEnable(tenantConfiguration.getTestCall() == 1);
			resp.addPortalFeature(testCallFeature);

			PortalFeature_type0 personalRoomFeature = new PortalFeature_type0();
			PortalFeatureName personalRoomFeatureName = new PortalFeatureName();
			personalRoomFeatureName.setPortalFeatureName("PersonalRoom");
			personalRoomFeature.setFeature(personalRoomFeatureName);
			personalRoomFeature.setEnable(tenantConfiguration.getPersonalRoom() == 1);
			resp.addPortalFeature(personalRoomFeature);
		}
		
		// FEATURE - If VP9 codec feature enabled - added only if SDK22X is enabled
		Configuration vp9 = systemService.getConfiguration("VP9");
		if (sdk220 != null && StringUtils.isNotBlank(sdk220.getConfigurationValue())
				&& sdk220.getConfigurationValue().equalsIgnoreCase("1") && vp9 != null
				&& StringUtils.isNotBlank(vp9.getConfigurationValue())) {
			PortalFeature_type0 vp9Feature = new PortalFeature_type0();
			PortalFeatureName vp9FeatureName = new PortalFeatureName();
			vp9FeatureName.setPortalFeatureName("VP9");
			vp9Feature.setFeature(vp9FeatureName);
			vp9Feature.setEnable(vp9.getConfigurationValue().equalsIgnoreCase("1"));
			resp.addPortalFeature(vp9Feature);
		}
		if (logger.isDebugEnabled()) {
            logger.debug("Exiting getPortalFeatures() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Returns the Active Sessions/Tokens of the user. If the request is
     * authenticated using token,<br>
     * the count returned will exclude session by current token.<br>
     * If the request is authenticated using userid/password,<br>
     * all the active tokens count will be returned.
     *
     */
    @Override
    public GetActiveSessionsResponse getActiveSessions(GetActiveSessionsRequest getActiveSessionsRequest)
            throws NotLicensedFaultException, GeneralFaultException {
        String username = null;

        // get username from logged in user object
        username = userService.getLoginUser().getUsername();
        if (username == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
        }

        int count = 0;
        int tenantId = 0;
        Integer tenantDetail = TenantContext.getTenantId();
        tenantId = tenantDetail != null ? tenantDetail : 0;

        if (tenantId <= 0) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid Tenant");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
        }

        count = vidyoPersistentTokenRepository.getCountByUserEid(username, tenantId, null);

        GetActiveSessionsResponse activeSessionsResponse = new GetActiveSessionsResponse();
        activeSessionsResponse.setCount(count);
        return activeSessionsResponse;
    }

    /**
     * Deletes all the authentication tokens associated with the user<br>
     * except the token using which the request has been made.<br>
     * If the request is made using userid/password, all tokens would be deleted<br>
     *
     * @param logoutAllOtherSessionsRequest
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */
    @Override
    public LogoutAllOtherSessionsResponse logoutAllOtherSessions(
            LogoutAllOtherSessionsRequest logoutAllOtherSessionsRequest) throws NotLicensedFaultException,
            GeneralFaultException {
        Object authByToken = RequestContextHolder.getRequestAttributes().getAttribute("AUTH_USING_TOKEN",
                RequestAttributes.SCOPE_REQUEST);
        String username = null;
        String endpointId = null;
        // get username from logged in user object
        username = userService.getLoginUser().getUsername();

        if (username == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
        }

        if (authByToken != null && authByToken instanceof Boolean && Boolean.valueOf(authByToken.toString())) {
            Object eid = RequestContextHolder.getRequestAttributes().getAttribute("ENDPOINT_ID",
                        RequestAttributes.SCOPE_REQUEST);
            // get the eid if authentication is by token
            endpointId = (eid != null) ? (String) eid : null;
        }

        int tenantId = 0;
        Integer tenantDetail = TenantContext.getTenantId();

        tenantId = tenantDetail != null ? tenantDetail : 0;

        if (tenantId <= 0) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid Tenant");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
        }
        int deleted = 0;
        if (authByToken != null && authByToken instanceof Boolean && Boolean.valueOf(authByToken.toString())
                && endpointId != null) {
            deleted = vidyoPersistentTokenRepository.removeAllUserTokensExceptCurrentEndpoint(username, tenantId,
                    endpointId);
        } else {
            deleted = vidyoPersistentTokenRepository.removeUserTokens(username, tenantId);
        }
        logger.debug("Number of Tokens deleted - {} for the user - {}, tenantId - {}, endpointId - {}", deleted,
                username, tenantId, endpointId);

        LogoutAllOtherSessionsResponse logoutAllOtherSessionsResponse = new LogoutAllOtherSessionsResponse();
        logoutAllOtherSessionsResponse.setOK(OK_type0.OK);
        return logoutAllOtherSessionsResponse;
    }

    /**
     * Auto generated method signature
     *
     * @param disconnectConferenceAllRequest10
     * @return disconnectConferenceAllResponse11
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public DisconnectConferenceAllResponse disconnectConferenceAll(DisconnectConferenceAllRequest disconnectConferenceAllRequest10)
        throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering disconnetConferenceAll() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = disconnectConferenceAllRequest10.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = disconnectConferenceAllRequest10.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        ConferenceControlResponse ccResponse = conferenceControlService.disconnectConferenceAll(userService.getLoginUser().getMemberID(),
                Integer.valueOf(roomEntityID), moderatorPIN);
        int errStatus = ccResponse.getStatus();
        if(errStatus == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        DisconnectConferenceAllResponse response = new DisconnectConferenceAllResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting disconnetConferenceAll() of User API v.1.1");
        }

        return response;
    }

    @Override
    public StartLectureModeResponse startLectureMode(StartLectureModeRequest startLectureModeRequest) throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering startLectureMode() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = startLectureModeRequest.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = startLectureModeRequest.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
        serviceRequest.setMemberID(userService.getLoginUser().getMemberID());
        serviceRequest.setTenantID(userService.getLoginUser().getTenantID());
        serviceRequest.setRoomID(Integer.valueOf(roomEntityID));
        serviceRequest.setModeratorPIN(moderatorPIN);

        LectureModeControlResponse serviceResponse = lectureModeService.startLectureMode(serviceRequest);

        int status =  serviceResponse.getStatus();
        if (status == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status == LectureModeControlResponse.LECTURE_MODE_ALREADY_STARTED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode is already on for conference");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode is already on for conference");
            exception.setFaultMessage(fault);
            throw exception;
        }  else if (status == LectureModeControlResponse.LECTURE_MODE_FEATURE_NOT_ALLOWED) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Lecture mode feature disabled by tenant admin.");
            GeneralFaultException exception = new GeneralFaultException("Lecture mode feature disabled by tenant admin.");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (status != 0 && status != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        StartLectureModeResponse response = new StartLectureModeResponse();
        response.setOK(OK_type0.OK);


        if (logger.isDebugEnabled()) {
            logger.debug("Exiting startLectureMode() of User API v.1.1");
        }

        return response;

    }

    /**
     * Auto generated method signature
     *
     * @param muteAudioServerAllRequest76
     * @return muteAudioServerAllResponse77
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public MuteAudioServerAllResponse muteAudioServerAll(MuteAudioServerAllRequest muteAudioServerAllRequest76)
        throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering muteAudioServerAll() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = muteAudioServerAllRequest76.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = muteAudioServerAllRequest76.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        boolean muteStae = muteAudioServerAllRequest76.getMuteState();

        ConferenceControlResponse ccResponse;
        if(muteStae) {
            ccResponse = conferenceControlService.muteAudioServerAll(userService.getLoginUser().getMemberID(), Integer.valueOf(roomEntityID), moderatorPIN);
        } else {
            ccResponse = conferenceControlService.unmuteAudioServerAll(userService.getLoginUser().getMemberID(), Integer.valueOf(roomEntityID), moderatorPIN);
        }

        int errStatus = ccResponse.getStatus();
        if(errStatus == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        MuteAudioServerAllResponse response = new MuteAudioServerAllResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting muteAudioServerAll() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param muteAudioClientAllRequest48
     * @return muteAudioClientAllResponse49
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public MuteAudioClientAllResponse muteAudioClientAll(MuteAudioClientAllRequest muteAudioClientAllRequest48)
        throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering muteAudioClientAll() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = muteAudioClientAllRequest48.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = muteAudioClientAllRequest48.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        ConferenceControlResponse ccResponse = conferenceControlService.muteAudioClientAll(userService.getLoginUser().getMemberID(),
                Integer.valueOf(roomEntityID), moderatorPIN);
        int errStatus = ccResponse.getStatus();
        if(errStatus == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        MuteAudioClientAllResponse response = new MuteAudioClientAllResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting muteAudioClientAll() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param muteVideoServerAllRequest10
     * @return muteVideoServerAllResponse11
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */
    public MuteVideoServerAllResponse muteVideoServerAll(MuteVideoServerAllRequest muteVideoServerAllRequest10)
            throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering muteVideoServerAll() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = muteVideoServerAllRequest10.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = muteVideoServerAllRequest10.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        boolean muteStae = muteVideoServerAllRequest10.getMuteState();

        ConferenceControlResponse ccResponse;
        if(muteStae) {
            ccResponse = conferenceControlService.muteVideoServerAll(userService.getLoginUser().getMemberID(), Integer.valueOf(roomEntityID), moderatorPIN);
        } else {
            ccResponse = conferenceControlService.unmuteVideoServerAll(userService.getLoginUser().getMemberID(), Integer.valueOf(roomEntityID), moderatorPIN);
        }

        int errStatus = ccResponse.getStatus();
        if(errStatus == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        MuteVideoServerAllResponse response = new MuteVideoServerAllResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting muteVideoServerAll() of User API v.1.1");
        }

        return response;
    }

    /**
     * Auto generated method signature
     *
     * @param muteVideoClientAllRequest90
     * @return muteVideoClientAllResponse91
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public MuteVideoClientAllResponse muteVideoClientAll(MuteVideoClientAllRequest muteVideoClientAllRequest90)
        throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering muteVideoClientAll() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        String roomEntityID = muteVideoClientAllRequest90.getConferenceID().getEntityID();
        if(roomEntityID == null || roomEntityID.isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("EntityId is empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("EntityId is empty");
            exception.setFaultMessage(fault);
            throw exception;
        }

        String moderatorPIN = muteVideoClientAllRequest90.getModeratorPIN();
        if(moderatorPIN == null || moderatorPIN.isEmpty()) {
            moderatorPIN = "noModeratorPIN";
        }

        ConferenceControlResponse ccResponse = conferenceControlService.muteVideoClientAll(userService.getLoginUser().getMemberID(),
                Integer.valueOf(roomEntityID), moderatorPIN);
        int errStatus = ccResponse.getStatus();
        if(errStatus == ConferenceControlResponse.INVALID_ROOM) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid ConferenceId");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid ConferenceId");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Cannot control conference");
            ControlMeetingFaultException exception = new ControlMeetingFaultException("Cannot control conference");
            exception.setFaultMessage(fault);
            throw exception;
        } else if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Server internal error");
            GeneralFaultException exception = new GeneralFaultException("Server internal error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        MuteVideoClientAllResponse response = new MuteVideoClientAllResponse();
        response.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting muteVideoClientAll() of User API v.1.1");
        }

        return response;
    }

    /**
     * Generates one time access URL for the user making the request<br>
     *
     */
    @Override
    public OnetimeAccessResponse getOnetimeAccessUrl(OnetimeAccessRequest onetimeAccessRequest)
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException {
        logger.debug("Entering getOnetimeAccessUrl() of User API v.1.1");
        /*checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
        User user = userService.getLoginUser();

        AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(user.getTenantID(), user.getMemberID());

        if (accessKeyResponse.getStatus() == AccessKeyResponse.INVALID_TENANT) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid Tenant");
            GeneralFaultException exception = new GeneralFaultException("Invalid Tenant");
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (accessKeyResponse.getStatus() != AccessKeyResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        URI uri = null;
        try {
            uri = new URI(MessageContext.getCurrentMessageContext().getIncomingTransportName(),
                    accessKeyResponse.getTenantUrl(), accessKeyResponse.getPortalPath(),
                    null, null);
            uri.setQueryString("bak="+accessKeyResponse.getAccessKey());
        } catch (MalformedURIException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }*/
        logger.debug("Exiting getOnetimeAccessUrl() of User API v.1.1");

        GeneralFault fault = new GeneralFault();
        fault.setErrorMessage("Not implemented");
        GeneralFaultException exception = new GeneralFaultException("Not implemented");
        exception.setFaultMessage(fault);
        throw exception;

        //OnetimeAccessResponse onetimeAccessResponse = new OnetimeAccessResponse();

        /*onetimeAccessResponse.*/
        //return onetimeAccessResponse;
    }

    /**
     * Returns Room Access Options as Name value pair to the owner of the Room. RoomAccessOptions include roomURL, PIN,
     * Extension and dial in number.
     *
     * @param roomAccessOptionsRequest
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     */
    @Override
    public RoomAccessOptionsResponse getRoomAccessOptions(RoomAccessOptionsRequest roomAccessOptionsRequest)
            throws NotLicensedFaultException, InvalidArgumentFaultException, GeneralFaultException {
        logger.debug("Entering getRoomAccessOptions() of User API v.1.1");
        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
        User user = userService.getLoginUser();

        if (user == null || user.getMemberID() <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid User");
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomAccessOptionsRequest.getRoomID().getEntityID() == null
                || roomAccessOptionsRequest.getRoomID().getEntityID().trim().isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid RoomID");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid RoomID");
            exception.setFaultMessage(fault);
            throw exception;
        }

        int roomId = 0;
        try {
            roomId = Integer.parseInt(roomAccessOptionsRequest.getRoomID().getEntityID().trim());
        } catch (NumberFormatException e) {
            logger.error("Error while parsing the roomId {}", roomAccessOptionsRequest.getRoomID().getEntityID().trim());
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid RoomID");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid RoomID");
            exception.setFaultMessage(fault);
        }

        RoomAccessDetailsResponse roomAccessDetailsResponse = roomService.getRoomAccessOptions(user.getMemberID(),
                roomId, user.getTenantID());

        if (roomAccessDetailsResponse.getStatus() != RoomAccessDetailsResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage(ms.getMessage(roomAccessDetailsResponse.getMessage(), null, "", Locale.getDefault()));
            GeneralFaultException exception = new GeneralFaultException(roomAccessDetailsResponse.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Tenant tenant = tenantService.getTenant(user.getTenantID());

        if (tenant == null || tenant.getTenantURL() == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Tenant");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException("Invalid Tenant");
            exception.setFaultMessage(fault);
            throw exception;
        }

        RoomAccessOptionsResponse roomAccessOptionsResponse = new RoomAccessOptionsResponse();

        if(roomAccessDetailsResponse.getRoomKey() != null && !roomAccessDetailsResponse.getRoomKey().trim().isEmpty()) {
            // Construct room url
            URI uri = null;
            try {
                String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
                String joinURL = roomService.getRoomURL(systemService, transportName,
                        tenant.getTenantURL(), roomAccessDetailsResponse.getRoomKey());

                uri = new URI(joinURL);
            } catch (MalformedURIException e) {
                logger.error("Error", e);
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("Internal Server Error");
                GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
                exception.setFaultMessage(fault);
                throw exception;
            }

            // Returns string equivalent of URI
            String roomUrl = uri.toString();
            RoomAccessOption_type0 roomUrlAccessOption = new RoomAccessOption_type0();
            AccessOptionName urlAccessOptionName = new AccessOptionName("roomURL", true);
            roomUrlAccessOption.setAccessOption(urlAccessOptionName);
            roomUrlAccessOption.setValue(roomUrl);
            roomAccessOptionsResponse.addRoomAccessOption(roomUrlAccessOption);
        }

        // Set Extension
        RoomAccessOption_type0 roomExtAccessOption = new RoomAccessOption_type0();
        AccessOptionName extAccessOptionName = new AccessOptionName("extension", true);
        roomExtAccessOption.setAccessOption(extAccessOptionName);
        roomExtAccessOption.setValue(roomAccessDetailsResponse.getExtension());
        roomAccessOptionsResponse.addRoomAccessOption(roomExtAccessOption);

        // Set PIN
        if(roomAccessDetailsResponse.getRoomPin() != null) {
            RoomAccessOption_type0 pinAccessOption = new RoomAccessOption_type0();
            AccessOptionName pinAccessOptionName = new AccessOptionName("PIN", true);
            pinAccessOption.setAccessOption(pinAccessOptionName);
            pinAccessOption.setValue(roomAccessDetailsResponse.getRoomPin());
            roomAccessOptionsResponse.addRoomAccessOption(pinAccessOption);
        }

        // Set tenant dial in number
        if(tenant.getTenantDialIn() != null) {
            RoomAccessOption_type0 tenantDialinAccessOption = new RoomAccessOption_type0();
            AccessOptionName dialInAccessOptionName = new AccessOptionName("dial-in-Number", true);
            tenantDialinAccessOption.setAccessOption(dialInAccessOptionName);
            tenantDialinAccessOption.setValue(tenant.getTenantDialIn());
            roomAccessOptionsResponse.addRoomAccessOption(tenantDialinAccessOption);
        }
        logger.debug("Exiting getRoomAccessOptions() of User API v.1.1");
        return roomAccessOptionsResponse;
    }

    /**
     * Auto generated method signature
     *
     * @param createModeratorURLRequest
     * @return createModeratorURLResponse123
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public com.vidyo.portal.user.v1_1.CreateModeratorURLResponse createModeratorURL
    (
            com.vidyo.portal.user.v1_1.CreateModeratorURLRequest createModeratorURLRequest
    )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering createModeratorURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        CreateModeratorURLResponse resp = new CreateModeratorURLResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(createModeratorURLRequest.getRoomID()
                .getEntityID());

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (!canMemberControlRoom(user.getMemberID(), room, "noModeratorPIN")) {
                ControlMeetingFault fault = new ControlMeetingFault();
                fault.setErrorMessage("Control of conference not allowed");
                ControlMeetingFaultException exception = new ControlMeetingFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            } else {
                roomService.generateModeratorKey(room);
            }
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting createModeratorURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param removeModeratorURLRequest
     * @return removeModeratorURLResponse59
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public com.vidyo.portal.user.v1_1.RemoveModeratorURLResponse removeModeratorURL
    (
            com.vidyo.portal.user.v1_1.RemoveModeratorURLRequest removeModeratorURLRequest
    )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering removeModeratorURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        RemoveModeratorURLResponse resp = new RemoveModeratorURLResponse();
        User user = userService.getLoginUser();

        int roomID = Integer.valueOf(removeModeratorURLRequest.getRoomID()
                .getEntityID());

        Room room;
        try {
            room = roomService.getRoomDetailsForConference(roomID);
        } catch (java.lang.Exception e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("RoomID is invalid");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomID != 0 && room != null) {
            if (!canMemberControlRoom(user.getMemberID(), room, "noModeratorPIN")) {
                ControlMeetingFault fault = new ControlMeetingFault();
                fault.setErrorMessage("Control of conference not allowed");
                ControlMeetingFaultException exception = new ControlMeetingFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            } else {
                roomService.removeModeratorKey(room);
            }
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid roomID = " + roomID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting removeRoomURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getModeratorURLRequest
     * @return getModeratorURLResponse81
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public com.vidyo.portal.user.v1_1.GetModeratorURLResponse getModeratorURL
    (
            com.vidyo.portal.user.v1_1.GetModeratorURLRequest getModeratorURLRequest
    )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering getModeratorURL() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = getModeratorURLRequest.getRoomID();
        String roomID = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomID == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.valueOf(roomID));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            invalidArgumentFaultException.setFaultMessage(fault);
            throw invalidArgumentFaultException;
        }

        User user = userService.getLoginUser();
        if (!canMemberControlRoom(user.getMemberID(), room, "noModeratorPIN")) {
            ControlMeetingFault fault = new ControlMeetingFault();
            fault.setErrorMessage("Control of conference not allowed");
            ControlMeetingFaultException exception = new ControlMeetingFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetModeratorURLResponse resp = new GetModeratorURLResponse();
        resp.setHasModeratorPIN((room.getRoomModeratorPIN() != null));

        String moderatorKey = room.getRoomModeratorKey();
        String moderatorUrl = "";

        if (moderatorKey != null) {
            StringBuilder path = new StringBuilder();
            Tenant tenant = tenantService.getTenant(room.getTenantID());
            String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            path.append(transportName).append("://").append(tenant.getTenantURL());
            path.append("/controlmeeting.html?key=");
            path.append(moderatorKey);
            moderatorUrl = path.toString();
        }

        resp.setModeratorURL(moderatorUrl);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getModeratorURL() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getModeratorURLWithTokenRequest44
     * @return getModeratorURLWithTokenResponse45
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws ControlMeetingFaultException
     */

    public com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenResponse getModeratorURLWithToken
    (
            com.vidyo.portal.user.v1_1.GetModeratorURLWithTokenRequest getModeratorURLWithTokenRequest
    )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ControlMeetingFaultException{

        if (logger.isDebugEnabled()) {
            logger.debug("Entering getModeratorURLWithToken() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        EntityID entityID = getModeratorURLWithTokenRequest.getRoomID();
        String roomID = entityID.getEntityID();
        boolean invalidRoom = false;
        Room room = null;
        if (roomID == null) {
            invalidRoom = true;
        } else {
            try {
                room = roomService.getRoomDetailsForConference(Integer.parseInt(roomID));
            } catch (java.lang.Exception e) {
                invalidRoom = true;
            }
        }

        if (invalidRoom || room == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room " + entityID.getEntityID());
            InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            invalidArgumentFaultException.setFaultMessage(fault);
            throw invalidArgumentFaultException;
        }

        // check if same tenant

        if (room != null) {
            int roomTenantID = room.getTenantID();

            User user = userService.getLoginUser();
            int userTenantID = user.getTenantID();

            if (userTenantID != roomTenantID) {
                    InvalidArgumentFault fault = new InvalidArgumentFault();
                    fault.setErrorMessage("Invalid room ID - this Tenant not allowed to make this request for this room.");
                    InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                            fault.getErrorMessage());
                    exception.setFaultMessage(fault);
                    throw exception;
            }
        }


        GetModeratorURLWithTokenResponse resp = new GetModeratorURLWithTokenResponse();

        String moderatorKey = room.getRoomModeratorKey();
        if (moderatorKey == null) {
            roomService.generateModeratorKey(room);
            moderatorKey = room.getRoomModeratorKey();
        }

        StringBuilder path = new StringBuilder();

        Tenant tenant = tenantService.getTenant(room.getTenantID());
        String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
        path.append(transportName).append("://").append(tenant.getTenantURL());
        path.append("/controlmeeting.html?key=");
        path.append(moderatorKey);

        // bak
        User user = userService.getLoginUser();
        AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(user.getTenantID(), user.getMemberID(), MemberBAKType.MorderatorURL);

        if (accessKeyResponse.getStatus() == AccessKeyResponse.INVALID_TENANT) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid Tenant");
            GeneralFaultException exception = new GeneralFaultException("Invalid Tenant");
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (accessKeyResponse.getStatus() != AccessKeyResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }
        try {
            path.append("&bak=").append(URLEncoder.encode(accessKeyResponse.getAccessKey(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }
        resp.setModeratorURL(path.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getModeratorURLWithToken() of User API v.1.1");
        }

        return resp;

    }

    /**
     * Returns Portal/Scheduled Room Prefix. If no prefix configured throws fault.
     *
     *
     * @throws NotLicensedFaultException
     *             :
     * @throws GeneralFaultException
     *             :
     * @throws PrefixNotConfiguredFaultException
     *             :
     */
    @Override
    public PortalPrefixResponse getPortalPrefix(PortalPrefixRequest portalPrefixRequest)
            throws NotLicensedFaultException, GeneralFaultException, PrefixNotConfiguredFaultException {
        logger.debug("Entering getPortalPrefix() of v1_1 VidyoPortalUserServiceSkeleton");
        Configuration config = systemService.getConfiguration(RoomServiceImpl.SCHEDULED_ROOM_PREFIX);

        if (config == null || config.getConfigurationValue() == null || config.getConfigurationValue().isEmpty()) {
            PrefixNotConfiguredFault prefixNotConfiguredFault = new PrefixNotConfiguredFault();
            prefixNotConfiguredFault.setErrorMessage("Portal Prefix Not Configured");
            PrefixNotConfiguredFaultException prefixNotConfiguredFaultException = new PrefixNotConfiguredFaultException(
                    "Portal Prefix Not Configured");
            prefixNotConfiguredFaultException.setFaultMessage(prefixNotConfiguredFault);
            throw prefixNotConfiguredFaultException;
        }

        String prefix = null;
        try {
            prefix = config.getConfigurationValue().trim();
        } catch (Exception e) {
            logger.error("This condition should not happen. Invalid integer value for Portal Prefix {}",
                    config.getConfigurationValue());
            PrefixNotConfiguredFault prefixNotConfiguredFault = new PrefixNotConfiguredFault();
            prefixNotConfiguredFault.setErrorMessage("Portal Prefix Not Configured");
            PrefixNotConfiguredFaultException prefixNotConfiguredFaultException = new PrefixNotConfiguredFaultException(
                    "Portal Prefix Not Configured");
            prefixNotConfiguredFaultException.setFaultMessage(prefixNotConfiguredFault);
            throw prefixNotConfiguredFaultException;
        }

        PortalPrefixResponse portalPrefixResponse = new PortalPrefixResponse();
        portalPrefixResponse.setPortalPrefix(prefix);

        logger.debug("Exiting getPortalPrefix() of v1_1 VidyoPortalUserServiceSkeleton");
        return portalPrefixResponse;
    }

    /**
     * Auto generated method signature
     *
     * @param getLoginAndWelcomeBannerRequest46
     * @return getLoginAndWelcomeBannerResponse47
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     * @throws FeatureNotAvailableFaultException
     */

    public GetLoginAndWelcomeBannerResponse getLoginAndWelcomeBanner(GetLoginAndWelcomeBannerRequest getLoginAndWelcomeBannerRequest46)
        throws NotLicensedFaultException,GeneralFaultException,FeatureNotAvailableFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getLoginAndWelcomeBanner() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        Banners banners = systemService.getBannersInfo();

        if(!banners.getShowLoginBanner() && !banners.getShowWelcomeBanner()) {
            FeatureNotAvailableFault fault = new FeatureNotAvailableFault();
            fault.setErrorMessage("Both Login Banner and Welcome Banner are disabled.");
            FeatureNotAvailableFaultException exception = new FeatureNotAvailableFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetLoginAndWelcomeBannerResponse resp = new GetLoginAndWelcomeBannerResponse();
        if(banners.getShowLoginBanner()){
            resp.setLoginBannerText(banners.getLoginBanner());
        }

        if(banners.getShowWelcomeBanner()){
            WelcomeBannerContent_type0 welcomeBannerContent_type0 = new WelcomeBannerContent_type0();
            welcomeBannerContent_type0.setWelcomeBannerText(banners.getWelcomeBanner());

            int tenantID = 0;
            Integer tenantDetail =  TenantContext.getTenantId();
            tenantID = tenantDetail != null ? tenantDetail : 0;

            if (tenantID <= 0) {
                GeneralFault fault = new GeneralFault();
                fault.setErrorMessage("Invalid Tenant");
                GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
            }

            Configuration config = systemService.getPasswordValidityDaysConfig();
            int memberID = userService.getLoginUser().getMemberID();
            if(config != null) {
                Calendar now;
                try {
                    now = memberPasswordHistoryService.getPasswordExpiryDate(config, memberID, tenantID);
                } catch (Exception e) {
                    GeneralFault fault = new GeneralFault();
                    fault.setErrorMessage("Problem with logged in user");
                    GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
                    exception.setFaultMessage(fault);
                    throw exception;
                }
                if(null !=now){
                    welcomeBannerContent_type0.setWelcomeBannerPasswordExpiryDate(now);
                }
            }

            List<MemberLoginHistory> memberLoginHistories = memberLoginHistoryService.getLoginHistoryDetails(memberID, tenantID);
            LoginAttempt_type0 [] loginAttempts = new LoginAttempt_type0 [memberLoginHistories.size()];
            Calendar transDate;
            LoginAttempt_type0 loginAttempt;
            for(int i = 0; i < loginAttempts.length; i++) {
                loginAttempt = new LoginAttempt_type0();
                transDate = Calendar.getInstance();
                transDate.setTime(memberLoginHistories.get(i).getTransactionTime());
                loginAttempt.setAttemptTime(transDate);

                loginAttempt.setResult(memberLoginHistories.get(i).getTransactionResult());
                loginAttempt.setSourceIPAddress(memberLoginHistories.get(i).getSourceIP());

                loginAttempts[i] = loginAttempt;
            }
            welcomeBannerContent_type0.setLoginAttempt(loginAttempts);

            resp.setWelcomeBannerContent(welcomeBannerContent_type0);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getLoginAndWelcomeBanner() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getUserAccountTypeRequest42
     * @return getUserAccountTypeResponse43
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */
    public GetUserAccountTypeResponse getUserAccountType(GetUserAccountTypeRequest getUserAccountTypeRequest42)
        throws NotLicensedFaultException,GeneralFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getUserAccountType() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        GetUserAccountTypeResponse resp = new GetUserAccountTypeResponse();

        String accountType = null;

        if(user.getUserRole() == null || user.getUserRole().isEmpty()) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Problem with logged in user");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_ADMIN")) {
            accountType = MemberRoleEnum.ADMIN.getMemberRole();
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_OPERATOR")) {
            accountType = MemberRoleEnum.OPERATOR.getMemberRole();
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_NORMAL")) {
            accountType = MemberRoleEnum.NORMAL.getMemberRole();
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_VIDYOROOM")) {
            accountType = MemberRoleEnum.VIDYO_ROOM.getMemberRole();
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_EXECUTIVE")) {
            accountType = MemberRoleEnum.EXECUTIVE.getMemberRole();
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_VIDYOPANORAMA")) {
            accountType = MemberRoleEnum.VIDYO_PANORAMA.getMemberRole();
        } else {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Problem with logged in user");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        resp.setUserAccountType(accountType);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getUserAccountType() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getVidyoReplayLibraryRequest
     * @throws VidyoReplayNotAvailableFaultException :
     * @throws NotLicensedFaultException :
     * @throws GeneralFaultException :
     */

    public GetVidyoReplayLibraryResponse getVidyoReplayLibrary(GetVidyoReplayLibraryRequest getVidyoReplayLibraryRequest)
        throws VidyoReplayNotAvailableFaultException,NotLicensedFaultException,GeneralFaultException {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getVidyoReplayLibrary() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = this.userService.getLoginUser();

        String url = tenantService.getTenantReplayURL(TenantContext.getTenantId());

        if(url == null || url.isEmpty()) {
            VidyoReplayNotAvailableFault fault = new VidyoReplayNotAvailableFault();
            fault.setErrorMessage("VidyoReplay is not set up.");
            VidyoReplayNotAvailableFaultException exception = new VidyoReplayNotAvailableFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        url += "/replay/recordingUI.html";
        String BAK = userService.generateBAKforMember(user.getMemberID(), MemberBAKType.VidyoReplayLibrary);

        GetVidyoReplayLibraryResponse response = new GetVidyoReplayLibraryResponse();
        response.setVidyoReplayLibraryUrl(url);
        response.setAuthToken(BAK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getVidyoReplayLibrary() of User API v.1.1");
        }

        return response;
    }

    @Override
    public SearchRoomsResponse searchRooms(SearchRoomsRequest searchRoomsRequest) throws InvalidArgumentFaultException, GeneralFaultException, SeatLicenseExpiredFaultException, NotLicensedFaultException {

        BigInteger start = searchRoomsRequest.getStart();
        if (start == null) {
            start = new BigInteger("0");
        } else if (start.intValue() > 99) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("start must be in between 0-99");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        BigInteger limit =  searchRoomsRequest.getLimit();
        if (limit == null) {
            limit = new BigInteger("10");
        } else if (limit.intValue() > 100) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("limit must be in between 0-100");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String sortBy = StringUtils.trimToNull(searchRoomsRequest.getSortBy());
        if (sortBy == null) {
            sortBy = "roomName";
        } else if (sortBy.equalsIgnoreCase("roomName")) {
            sortBy = "roomName";
        } else if (sortBy.equalsIgnoreCase("extension")) {
            sortBy = "roomExtNumber";
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("sortBy value invalid, must be \"roomName\" or \"extension\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String roomType = StringUtils.trimToNull(searchRoomsRequest.getRoomType());
        List<Integer> roomTypes = new ArrayList<Integer>();
        if (roomType == null) {
            roomTypes.add(2);
        } else if (roomType.contains("|")) { // multiple
            String[] parts = roomType.split("\\|");
            int i = 0;
            for (String part : parts) {
                if (part.equalsIgnoreCase("personal")) {
                    roomTypes.add(1);
                } else if (part.equalsIgnoreCase("public")) {
                    roomTypes.add(2);
                } else if (part.equalsIgnoreCase("legacy")) {
                    roomTypes.add(3);
                }
                i++;
                if (i >= 3) {
                    break;
                }
             }
        } else { // single
            if (roomType.equalsIgnoreCase("personal")) {
                roomTypes.add(1);
            } else if (roomType.equalsIgnoreCase("public")) {
                roomTypes.add(2);
            } else if (roomType.equalsIgnoreCase("legacy")) {
                roomTypes.add(3);
            }
        }
        if (roomTypes.size() == 0) {
            roomTypes.add(2);
        }

        SortDir sortDir = searchRoomsRequest.getSortDir();
        if (sortDir == null) {
            sortDir = SortDir.ASC;
        }

        String query = StringUtils.trimToNull(searchRoomsRequest.getQuery());
        if (query == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string cannot be empty");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String originalQuery = query;
        boolean checkQueryLength = true;

        String queryField = StringUtils.trimToNull(searchRoomsRequest.getQueryField());
        if (queryField == null) {
            queryField = "roomName";
        } else if (queryField.equalsIgnoreCase("roomNameOrExtension")) {
            queryField = "roomName";
        } else if (queryField.equalsIgnoreCase("ownerName")) {
            queryField = "memberName";
        } else if (queryField.equalsIgnoreCase("ownerEntityID")) {
            queryField = "memberID";
            try {
                int roomID = Integer.parseInt(query);
                int memberID = this.roomService.getMemberIDForRoomIDIfAllowed(roomID); // convert entityID (roomID) to memberID
                query = "" + memberID;
                checkQueryLength = false;
            } catch (NumberFormatException n) {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("query must be consist only of digits when queryField is \"ownerEntityID\"");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        } else {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("queryField must be \"roomNameOrExtension\" or \"ownerName\" or \"ownerEntityID\"");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (checkQueryLength && StringUtils.deleteWhitespace(query).length() < 2) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("query string must be at least two characters");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        SearchRoomsResponse response = new SearchRoomsResponse();

        User user = userService.getLoginUser();
        int thisUserMemberID = user.getMemberID();

        int total = this.roomService.findRoomsCount(thisUserMemberID, query, queryField, roomTypes);
        response.setTotal(total);

        if (total > 0 && limit.intValue() > 0) {
            List<RoomMini> rooms = this.roomService.findRooms(thisUserMemberID,
                    query, queryField, roomTypes,
                    start.intValue(), limit.intValue(),
                    sortBy, sortDir.toString());

            List<Integer> memberIDs = new ArrayList<Integer>();
            for (RoomMini room : rooms) {
                if (!"legacy".equals(room.getType())) {
                    memberIDs.add((int) room.getOwnerID());
                }
            }
            Map<Integer,Integer> personalRooms = memberService.getPersonalRoomIds(memberIDs);

            for (RoomMini room : rooms) {
                Room_type0 r = new Room_type0();

                EntityID entityID = new EntityID();
                entityID.setEntityID("" + room.getRoomID());
                r.setEntityID(entityID);

                EntityID ownerEntityID = new EntityID();
                if ("legacy".equals(room.getType())) {
                    r.setOwnerEntityID(entityID); // no owner for legacy
                } else {
                    ownerEntityID.setEntityID("" + personalRooms.get((int) room.getOwnerID())); // convert member ids to personal room ids
                    r.setOwnerEntityID(ownerEntityID);
                }

                r.setExtension(room.getExtension());
                r.setName(room.getName());
                r.setDisplayName(room.getDisplayName());
                r.setDescription(room.getDescription());
                r.setOwnerName(room.getOwnerName());
                r.setType(room.getType());
                r.setPinned(room.isPinned());
                r.setLocked(room.isLocked());
                r.setEnabled(room.isEnabled());
                r.setIsInMyContacts(room.isInContacts());

                response.addRoom(r);
            }
        }

        // convert back
        if (sortBy.equals("roomName")) {
            sortBy = "roomName";
        } else if (sortBy.equals("roomExtNumber")) {
            sortBy = "extension";
        } else {
            sortBy = "roomName";
        }

        if (queryField.equals("roomName")) {
            queryField = "roomNameOrExtension";
        } else if (queryField.equals("memberName")) {
            queryField = "ownerName";
        } else if (queryField.equals("memberID")) {
            queryField = "ownerEntityID";
        }

        StringBuilder roomTypeOut = new StringBuilder();
        if (roomTypes.size() > 0) {
            int i = 0;
            for (Integer type : roomTypes) {
                if (i >= 1) {
                    roomTypeOut.append("|");
                }
                if (type == 1) {
                    roomTypeOut.append("personal");
                } else if (type == 2) {
                    roomTypeOut.append("public");
                } else if (type == 3) {
                    roomTypeOut.append("legacy");
                }
                i++;
            }
        }

        response.setStart(start);
        response.setLimit(limit);
        response.setSortBy(sortBy);
        response.setSortDir(sortDir);
        response.setQuery(originalQuery);
        response.setQueryField(queryField);
        response.setRoomType(roomTypeOut.toString());

        return response;


    }

    /**
     * Auto generated method signature
     *
     * @param whatIsMyIPAddressRequest154
     * @return whatIsMyIPAddressResponse155
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */

    public WhatIsMyIPAddressResponse whatIsMyIPAddress(WhatIsMyIPAddressRequest whatIsMyIPAddressRequest154)
        throws NotLicensedFaultException,GeneralFaultException {

        if (logger.isDebugEnabled()) {
            logger.debug("Entering whatIsMyIPAddress() of User API v.1.1");
        }

        MessageContext msgContext = MessageContext.getCurrentMessageContext();

        checkLicenseForAllowUserAPIs(msgContext);

        WhatIsMyIPAddressResponse response = new WhatIsMyIPAddressResponse();
        String remoteIpAddress = (String) msgContext.getProperty(MessageContext.REMOTE_ADDR);
        if(remoteIpAddress == null || remoteIpAddress.isEmpty()) {
            response.setEndpointExternalIPAddress("Unknown");
        } else {
            response.setEndpointExternalIPAddress(remoteIpAddress);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting whatIsMyIPAddress() of User API v.1.1");
        }

        return response;
    }

    private void validateAndSetFilterLimitParam(BaseQueryFilter filter, Filter_type0 input_param) throws InvalidArgumentFaultException {
        if (input_param.getLimit() >= 0) {
            if(input_param.getLimit() <= BaseQueryFilter.FILTER_MAX_LIMIT) {
                if(input_param.getLimit() > BaseQueryFilter.FILTER_DEFAULT_LIMIT) {
                    logger.info("ATTENTION!!! Usage of limit equal to " + input_param.getLimit() + " MAY HAVE performance impact");
                }
                filter.setLimit(input_param.getLimit());
            } else {
                String errMsg = "Requested limit exceeds the allowed threshold of " + BaseQueryFilter.FILTER_MAX_LIMIT + " entries. Please try again.";

                logger.error(errMsg);

                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage(errMsg);
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        } else {
            filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
        }
    }

    /**
     * Auto generated method signature
     *
     * @param setEndpointDetailsRequest104
     * @return setEndpointDetailsResponse105
     * @throws NotLicensedFaultException
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     */

    public com.vidyo.portal.user.v1_1.SetEndpointDetailsResponse setEndpointDetails
    (
            com.vidyo.portal.user.v1_1.SetEndpointDetailsRequest setEndpointDetailsRequest
    )
            throws EndpointNotBoundFaultException, NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering setEndpointDetails() of User API v.1.1");
        }

        SetEndpointDetailsResponse resp = new SetEndpointDetailsResponse();

        User user = userService.getLoginUser();
        if (user == null || user.getMemberID() == 0) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String endpointGUID = setEndpointDetailsRequest.getEID();
        if (endpointGUID.equalsIgnoreCase("")) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Endpoint ID: " + endpointGUID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Endpoint endpoint = endpointService.getEndpointDetails(endpointGUID);
        if (endpoint == null) {
            logger.warn("setEndpointDetails rejected of unknown endpoint, GUID: " + endpointGUID);
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Unknown Endpoint ID: " + endpointGUID);
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        if (endpoint.getStatus() == 0) {
            logger.warn("setEndpointDetails of offline endpoint, GUID: " + endpointGUID);
        }
        if (endpoint.getMemberID() != user.getMemberID()) {
            logger.warn("setEndpointDetails rejected of endpoint bound to a different user, GUID: " + endpointGUID);
            EndpointNotBoundFault boundFault = new EndpointNotBoundFault();
            boundFault.setErrorMessage("Endpoint not bound to this user");
            EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException(
                    boundFault.getErrorMessage());
            notBoundFaultException.setFaultMessage(boundFault);
            throw notBoundFaultException;
        }
        if (endpoint.getAuthorized() != 1) {
            logger.info("setEndpointDetails rejected of unauthorized endpoint, GUID: " + endpointGUID);
            EndpointNotBoundFault boundFault = new EndpointNotBoundFault();
            boundFault.setErrorMessage("Endpoint not bound to the user");
            EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException(
                    boundFault.getErrorMessage());
            notBoundFaultException.setFaultMessage(boundFault);
            throw notBoundFaultException;
        }

        logger.info("setEndpointDetails accepted of authorized endpoint, GUID: " + endpointGUID);

        // save endpoint features
        EndpointFeatures endpointFeatures = new EndpointFeatures();
        EndpointFeature_type0[] features = setEndpointDetailsRequest.getEndpointFeature();
        if (features != null && features.length > 0) {
            EndpointFeatureName epfeatureName = null;
            String featureName = "";
            boolean isFeatureEnabled = false;
            for (EndpointFeature_type0 feature : features) {
                epfeatureName = feature.getFeature();
                if (epfeatureName != null) {
                    featureName = epfeatureName.getEndpointFeatureName();
                    isFeatureEnabled = feature.getEnable();
                    // check for features by name
                    if ("LectureMode".equalsIgnoreCase(featureName)) {
                        endpointFeatures.setLectureModeSupported(isFeatureEnabled);
                    }
                }
            }
        }

        endpointService.updateEndpointFeatures(endpointGUID, endpointFeatures);

        // for CDRv3
        CDRinfo2 cdrinfo = new CDRinfo2();
        cdrinfo.setApplicationName(setEndpointDetailsRequest.getApplicationName());
        cdrinfo.setApplicationVersion(setEndpointDetailsRequest.getApplicationVersion());
        cdrinfo.setApplicationOs(setEndpointDetailsRequest.getApplicationOs());
        cdrinfo.setDeviceModel(setEndpointDetailsRequest.getDeviceModel());
        MessageContext msgContext = MessageContext.getCurrentMessageContext();
        cdrinfo.setEndpointPublicIPAddress((String) msgContext.getProperty(MessageContext.REMOTE_ADDR));
        if(setEndpointDetailsRequest.isExtDataSpecified() && StringUtils.isNotEmpty(setEndpointDetailsRequest.getExtData())) {
        		cdrinfo.setExtData(setEndpointDetailsRequest.getExtData());
        }
        if(setEndpointDetailsRequest.isExtDataTypeSpecified()) {
        		cdrinfo.setExtDataType(
                        validateExtDataType(
                                setEndpointDetailsRequest.getExtDataType()));
        }
        userService.updateEndpointCDRInfo(endpointGUID, cdrinfo);
        resp.setOK(OK_type0.OK);

        if (logger.isDebugEnabled()) {
            logger.debug("Exiting setEndpointDetails() of User API v.1.1");
        }

        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param getChangePasswordHtmlUrlWithTokenRequest126
     * @return getChangePasswordHtmlUrlWithTokenResponse127
     * @throws NotLicensedFaultException
     * @throws GeneralFaultException
     */
    public GetChangePasswordHtmlUrlWithTokenResponse getChangePasswordHtmlUrlWithToken(GetChangePasswordHtmlUrlWithTokenRequest getChangePasswordHtmlUrlWithTokenRequest126)
        throws NotLicensedFaultException,GeneralFaultException{
        if (logger.isDebugEnabled()) {
            logger.debug("Entering getChangePasswordHtmlUrlWithToken() of User API v.1.1");
        }

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();

        boolean isPasswordChangeable = true;
        boolean isSamlCandidate = false;

        List<MemberRoles> authForMemberRoles = systemService.getToRoles();
        List<String> authForRoles = new LinkedList<String>();
        for (MemberRoles r : authForMemberRoles) {
            authForRoles.add("ROLE_" + r.getRoleName().toUpperCase());
            if("Normal".equalsIgnoreCase(r.getRoleName()) || "Executive".equalsIgnoreCase(r.getRoleName())) {
                isSamlCandidate = true;
            }
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (authForRoles.contains(role.getAuthority())) {
                isPasswordChangeable = false;
                break;
            }
        }

        com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(TenantContext.getTenantId()).toAuthentication();

        if(!isPasswordChangeable && (authType instanceof WsAuthentication || authType instanceof LdapAuthentication || authType instanceof WsRestAuthentication) ||
                isSamlCandidate && authType instanceof SamlAuthentication) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Password of provisioned user cannot be changed");
            GeneralFaultException exception = new GeneralFaultException("Invalid User");
            exception.setFaultMessage(fault);
            throw exception;
        }

        GetChangePasswordHtmlUrlWithTokenResponse resp = new GetChangePasswordHtmlUrlWithTokenResponse();
        AccessKeyResponse accessKeyResponse = userService.getOnetimeAccessUri(user.getTenantID(), user.getMemberID(), MemberBAKType.ChangePassword);

        if (accessKeyResponse.getStatus() == AccessKeyResponse.INVALID_TENANT) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid Tenant");
            GeneralFaultException exception = new GeneralFaultException("Invalid Tenant");
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (accessKeyResponse.getStatus() != AccessKeyResponse.SUCCESS) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }

        StringBuilder path = new StringBuilder();

        Tenant tenant = tenantService.getTenant(user.getTenantID());
        String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
        path.append(transportName).append("://").append(tenant.getTenantURL());
        path.append("/changepassword.html?bak=");
        try {
            path.append(URLEncoder.encode(accessKeyResponse.getAccessKey(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Internal Server Error");
            GeneralFaultException exception = new GeneralFaultException("Internal Server Error");
            exception.setFaultMessage(fault);
            throw exception;
        }
        resp.setChangePasswordHtmlUrlWithToken(path.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting getChangePasswordHtmlUrlWithToken() of User API v.1.1");
        }

        return resp;
    }

    @Override
    public CreatePublicRoomResponse createPublicRoom(CreatePublicRoomRequest createPublicRoomRequest)
            throws PublicRoomCreationFaultException, InvalidArgumentFaultException, GeneralFaultException,
            NotAllowedToCreateFaultException, NotLicensedFaultException {

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();
        if (user == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());
        if (member == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }


        if (createPublicRoomRequest.getDisplayName() == null || createPublicRoomRequest.getDisplayName().trim().isEmpty()
                || createPublicRoomRequest.getDisplayName().trim().length() > 80) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Display Name (must be 2 - 80 characters).");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        String displayName = createPublicRoomRequest.getDisplayName().trim();

        //Check if the Tenant is allowing to create
        int roomsAllowedToCreate = roomService.getUserCreatePublicRoomAllowed(member.getTenantID());

        if (roomsAllowedToCreate <= 0){
            NotAllowedToCreateFault fault = new NotAllowedToCreateFault();
            fault.setErrorMessage("You are not allowed to create room.");
            NotAllowedToCreateFaultException exception = new NotAllowedToCreateFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int roomsCreated = roomService.getUserCreatePublicRoomCount(member.getTenantID() , member.getMemberID());

        if ((roomsAllowedToCreate - roomsCreated) <= 0){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("You have created all public rooms as per the limit.");
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        long roomRemainingCount = roomService.getTenantCreatePublicRoomRemainCount(member.getTenantID());
        if (roomRemainingCount <= 0){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("You have created all public rooms as per the limit for the Tenant.");
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        long totalPublicRoomsRemain = roomService.getSystemCreatePublicRoomRemainCount();
        if (totalPublicRoomsRemain <= 0){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("You have created all public rooms as per the limit of the System.");
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int displayNameCount = roomService.getDisplayNameCount(displayName,member.getTenantID());
        if (displayNameCount > 0){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("Display name already exists. Please use different display name.");
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        String roomName = displayName.replaceAll(" +", "_").replaceAll("[~`!@#$%^&*\\(\\)+={}\\[\\]\\|\\\\:;\"\'<>,\\?/]", "");
        if (roomName.isEmpty() || roomName.equals("-") || roomName.equals("_") || roomName.length() < 2){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("Display name contains too few alphanumeric characters.");
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomService.isRoomExistForRoomName(roomName, 0)){
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage("Room exist for name = " + roomName);
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();
        publicRoomCreateRequest.setTenantId(member.getTenantID());
        publicRoomCreateRequest.setMemberId(member.getMemberID());
        publicRoomCreateRequest.setGroupId(member.getGroupID());
        publicRoomCreateRequest.setMemberName(member.getMemberName());
        publicRoomCreateRequest.setDisplayName(displayName);
        publicRoomCreateRequest.setRoomName(roomName);
        publicRoomCreateRequest.setInMyContacts(createPublicRoomRequest.getInMyContacts());
        publicRoomCreateRequest.setLocked(createPublicRoomRequest.getLocked());
        publicRoomCreateRequest.setDescription(createPublicRoomRequest.getDescription());

        if (createPublicRoomRequest.getSetPIN() != null && !createPublicRoomRequest.getSetPIN().isEmpty()) {
            if ((createPublicRoomRequest.getSetPIN().replaceAll("[0-9]", "")).length() > 0
                    || !systemService.isPINLengthAcceptable(TenantContext.getTenantId(), createPublicRoomRequest.getSetPIN())){
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid PIN. PIN should be a "
                        + systemService.getMinPINLengthForTenant(TenantContext.getTenantId())
                        + "-" + SystemServiceImpl.PIN_MAX + " digit number");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
            publicRoomCreateRequest.setPinRequired(true);
            publicRoomCreateRequest.setPIN(createPublicRoomRequest.getSetPIN());
        }

        RoomCreationResponse roomCreationResponse = roomService.createPublicRoom(publicRoomCreateRequest);


        // If the response status is non-zero [failure], check the status code
        // and return fault
        if (roomCreationResponse.getStatus() != 0) {
            PublicRoomCreationFault fault = new PublicRoomCreationFault();
            fault.setErrorMessage(roomCreationResponse.getMessage());
            PublicRoomCreationFaultException exception = new PublicRoomCreationFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        CreatePublicRoomResponse createPublicRoomResponse = new CreatePublicRoomResponse();

        EntityID entity_id = new EntityID();
        entity_id.setEntityID(""+roomCreationResponse.getRoom().getRoomID());
        createPublicRoomResponse.setRoomID(entity_id);

        Extension_type1 extn = new Extension_type1();
        extn.setExtension_type0(roomCreationResponse.getExtensionValue());
        createPublicRoomResponse.setExtension(extn);

        // Scheduled Room - values customization
        roomCreationResponse.getRoom().setRoomName(roomCreationResponse.getExtensionValue());


        URI uri = null;
        try {
            String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            String joinURL = roomService.getRoomURL(systemService, transportName,
                    roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getRoom().getRoomKey());

            uri = new URI(joinURL);
        } catch (MalformedURIException e) {
            logger.error("Error", e);
        }
        createPublicRoomResponse.setRoomURL(uri);

        // Add Entry in speed dial if flag is set
        if (publicRoomCreateRequest.isInMyContacts()){
            memberService.addSpeedDialEntry(roomCreationResponse.getRoom().getMemberID(), roomCreationResponse.getRoom().getRoomID());
        }
        createPublicRoomResponse.setInMyContacts(publicRoomCreateRequest.isInMyContacts());



        createPublicRoomResponse.setLocked(roomCreationResponse.getRoom().getRoomLocked() == 0 ? false:true);

        if(roomCreationResponse.getRoom().getRoomPIN() != null) {
            createPublicRoomResponse.setHasPIN(true);
        } else {
            createPublicRoomResponse.setHasPIN(false);
        }

        return createPublicRoomResponse;
    }


    /**
     *
     * @param updatePublicRoomDescriptionRequest
     * @return updatePublicRoomDescriptionResponse
     * @throws InvalidArgumentFaultException
     * @throws GeneralFaultException
     * @throws PublicRoomDescUpdationFaultException
     * @throws NotLicensedFaultException
     */
    public com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionResponse updatePublicRoomDescription(
        com.vidyo.portal.user.v1_1.UpdatePublicRoomDescriptionRequest updatePublicRoomDescriptionRequest)
        throws InvalidArgumentFaultException, GeneralFaultException,
            PublicRoomDescUpdationFaultException, NotLicensedFaultException, RoomNotFoundFaultException {
        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        UpdatePublicRoomDescriptionResponse updatePublicRoomDescriptionResponse = new UpdatePublicRoomDescriptionResponse();

        User user = userService.getLoginUser();
        if (user == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        int roomId = 0;
        try {
            roomId = Integer.parseInt(updatePublicRoomDescriptionRequest.getRoomID().getEntityID());
        } catch (Exception ex){
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room Id.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (roomId <= 0) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Room Id.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        boolean doesRoomExistsForUser=false;
        doesRoomExistsForUser=roomService.isPublicRoomIdExistsForMemberId(roomId, user.getMemberID(), user.getTenantID());
        if (!doesRoomExistsForUser){
            RoomNotFoundFault fault = new RoomNotFoundFault();
            fault.setErrorMessage("Room Id not found.");
            RoomNotFoundFaultException exception = new RoomNotFoundFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }


        if (updatePublicRoomDescriptionRequest.getDescription() == null || updatePublicRoomDescriptionRequest.getDescription().trim().isEmpty()) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid Description.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        roomService.updatePublicRoomDescription(roomId, updatePublicRoomDescriptionRequest.getDescription());

        updatePublicRoomDescriptionResponse.setOK(OK_type0.OK);
        return updatePublicRoomDescriptionResponse;

    }


    /**
     * Allows a user to upload their own thumbnailPhoto
     * @param setThumbnailPhotoRequest
     * @throws FileTooLargeFaultException :
     * @throws NotAllowedThumbnailPhotoFaultException :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException :
     * @throws SeatLicenseExpiredFaultException :
     * @throws NotLicensedFaultException :
     */
    public com.vidyo.portal.user.v1_1.SetThumbnailPhotoResponse setThumbnailPhoto(
        com.vidyo.portal.user.v1_1.SetThumbnailPhotoRequest setThumbnailPhotoRequest)
        throws FileTooLargeFaultException,
            NotAllowedThumbnailPhotoFaultException,
            InvalidArgumentFaultException, GeneralFaultException,
            SeatLicenseExpiredFaultException, NotLicensedFaultException{

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());
        SetThumbnailPhotoResponse setThumbnailPhotoResponse = new SetThumbnailPhotoResponse();

        User user = userService.getLoginUser();
        if (user == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());
        if (member == null) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid User");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Configuration userImageConf = systemService.getConfiguration("USER_IMAGE");
        if (userImageConf == null || StringUtils.isBlank(userImageConf.getConfigurationValue()) || !userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
            NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
            fault.setErrorMessage("Thumbnail image is not enabled by Super.");
            NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        userImageConf = systemService.getConfiguration("UPLOAD_USER_IMAGE");
        if (userImageConf == null || StringUtils.isBlank(userImageConf.getConfigurationValue()) || !userImageConf.getConfigurationValue().equalsIgnoreCase("1")){
            NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
            fault.setErrorMessage("User Thumbnail image upload is not allowed by Super.");
            NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        TenantConfiguration tenantConfiguration = this.tenantService
                .getTenantConfiguration(TenantContext.getTenantId());
        if (tenantConfiguration.getUploadUserImage() != 1) {
            NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
            fault.setErrorMessage("User Thumbnail image upload is not allowed by Admin.");
            NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        if (member.getUserImageAllowed() == 0){
            NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
            fault.setErrorMessage("Member not allowed to upload image.");
            NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        byte[] thumbNail = null;
        try {
            thumbNail = IOUtils.toByteArray(setThumbnailPhotoRequest.getThumbNailPhoto().getInputStream());
        } catch (IOException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid thambnail attached.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        try {
            if (thumbNail != null && ImageUtils.validateThumbNailImage(thumbNail)){

                    if (tenantConfiguration.getUserImage() == 1) {
                        Configuration conf = systemService.getConfiguration("MAX_USER_IMAGE_SIZE_KB");

                        int maxAllowedImageSize = Integer.valueOf(conf.getConfigurationValue());
                        if (thumbNail.length <= (maxAllowedImageSize * 1024)) {
                            try {
                                memberService.uploadUserImage(thumbNail, member.getMemberID(), TenantContext.getTenantId());
                            } catch (Exception ex){
                                NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
                                fault.setErrorMessage("Thumbnail folder configuration missing.");
                                NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                                        fault.getErrorMessage());
                                exception.setFaultMessage(fault);
                                throw exception;
                            }

                        } else {
                            FileTooLargeFault fault = new FileTooLargeFault();
                            fault.setErrorMessage("Thumbnail image size is bigger than what it is allowed by super. Allowed size in KB "
                                    + maxAllowedImageSize);
                            FileTooLargeFaultException exception = new FileTooLargeFaultException(
                                    fault.getErrorMessage());
                            exception.setFaultMessage(fault);
                            throw exception;
                        }

                    } else {
                        NotAllowedThumbnailPhotoFault fault = new NotAllowedThumbnailPhotoFault();
                        fault.setErrorMessage("Thumbnail image is not enabled by Admin");
                        NotAllowedThumbnailPhotoFaultException exception = new NotAllowedThumbnailPhotoFaultException(
                                fault.getErrorMessage());
                        exception.setFaultMessage(fault);
                        throw exception;
                    }
                    setThumbnailPhotoResponse.setOK(OK_type0.OK);

            } else {
                InvalidArgumentFault fault = new InvalidArgumentFault();
                fault.setErrorMessage("Invalid thambnail attached.File format should JPEG or PNG");
                InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                        fault.getErrorMessage());
                exception.setFaultMessage(fault);
                throw exception;
            }
        } catch (IOException e) {
            InvalidArgumentFault fault = new InvalidArgumentFault();
            fault.setErrorMessage("Invalid thambnail attached.");
            InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        return setThumbnailPhotoResponse;
    }

    @Override
    public GetLogAggregationServerResponse getLogAggregationServer(
            GetLogAggregationServerRequest getLogAggregationServerRequest) throws LogAggregationDisabledFaultException {
        GetLogAggregationServerResponse response = new GetLogAggregationServerResponse();

        TenantConfiguration config=tenantService.getTenantConfiguration(TenantContext.getTenantId());
        if(config.getLogAggregation()==0){
        	 logger.error("Log Aggregation is disabled at tenant level");
        	 LogAggregationDisabledFault fault = new LogAggregationDisabledFault();
             fault.setErrorMessage("Log Aggregation is disabled at tenant level");
             LogAggregationDisabledFaultException exception = new LogAggregationDisabledFaultException(fault.getErrorMessage());
             exception.setFaultMessage(fault);
             throw exception;
        }

        String logFqdn = systemService.getConfigValue(0, "LogAggregationServer");

        response.setLogServer(logFqdn);
        return response;
    }

    /**
     * Silences/UnSilences the speaker of specific participant or all
     * participants in conference. If the participantId is not sent or zero,
     * silences all participants
     *
     * @param roomID
     * @param participantId
     * @param moderatorPIN
     * @param silence
     * @return
     */
    @Override
    public SilenceSpeakerServerResponse silenceSpeakerServer(SilenceSpeakerServerRequest silenceSpeakerServerRequest)
            throws InvalidParticipantFaultException, InvalidConferenceFaultException, NotLicensedFaultException {

        String confId = silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getConferenceID().getEntityID();
        if (!silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().isParticipantIDSpecified()) {
            InvalidParticipantFault invalidParticipantFault = new InvalidParticipantFault();
            invalidParticipantFault.setErrorMessage("Invalid participant");
            InvalidParticipantFaultException invalidParticipantFaultException = new InvalidParticipantFaultException(
                    invalidParticipantFault.getErrorMessage());
            invalidParticipantFaultException.setFaultMessage(invalidParticipantFault);
            throw invalidParticipantFaultException;
        }

        int participantId = silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getParticipantID().intValue();
        String moderatorPIN = silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getModeratorPIN();
        silenceSpeaker(confId, participantId, moderatorPIN,
                silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getSilenceState().getSilenceState());
        // If the response is success, return the room state
        SilenceSpeakerServerResponse silenceSpeakerServerResponse = new SilenceSpeakerServerResponse();
        SilenceSpeakerServerType serverType = new SilenceSpeakerServerType();
        serverType.setConferenceID(silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getConferenceID());
        serverType.setSilenceState(silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getSilenceState());
        if (participantId > 0) {
            serverType
                    .setParticipantID(silenceSpeakerServerRequest.getSilenceSpeakerServerRequest().getParticipantID());
        }
        silenceSpeakerServerResponse.setSilenceSpeakerServerResponse(serverType);
        return silenceSpeakerServerResponse;
    }

    @Override
    public SilenceSpeakerServerAllResponse silenceSpeakerServerAll(
            SilenceSpeakerServerAllRequest silenceSpeakerServerAllRequest)
            throws InvalidConferenceFaultException, NotLicensedFaultException {
        SilenceSpeakerServerAllResponse silenceSpeakerServerAllResponse = new SilenceSpeakerServerAllResponse();
        String confId = silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest().getConferenceID().getEntityID();
        String moderatorPIN = silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest().getModeratorPIN();
        int silenceState = silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest().getSilenceState().getSilenceState();
        try {
            silenceSpeaker(confId, -1, moderatorPIN, silenceState);
        } catch (InvalidParticipantFaultException e) {
            logger.error("InvalidParticpant Error should not happen for silenceSpeakerAll");
            InvalidConferenceFault invalidConferenceFault = new InvalidConferenceFault();
            invalidConferenceFault.setErrorMessage("Invalid conference" + confId);
            InvalidConferenceFaultException invalidConferenceFaultException = new InvalidConferenceFaultException(
                    invalidConferenceFault.getErrorMessage());
            throw invalidConferenceFaultException;
        }
        silenceSpeakerServerAllResponse
                .setSilenceSpeakerServerAllResponse(silenceSpeakerServerAllRequest.getSilenceSpeakerServerAllRequest());
        return silenceSpeakerServerAllResponse;
    }

    /**
     *
     * @param confId
     * @param participantId
     * @param moderatorPIN
     * @param silenceState
     * @return
     * @throws InvalidParticipantFaultException
     * @throws InvalidConferenceFaultException
     */
    private ConferenceControlResponse silenceSpeaker(String confId, int participantId, String moderatorPIN,
            int silenceState) throws InvalidParticipantFaultException, InvalidConferenceFaultException {
        ConferenceControlResponse conferenceControlResponse = conferenceControlService.silenceSpeakerServer(
                userService.getLoginUser(), Integer.valueOf(confId), participantId, moderatorPIN, silenceState);

        if (conferenceControlResponse.getStatus() == ConferenceControlResponse.CANNOT_CONTROL_ENDPOINT) {
            InvalidParticipantFault invalidParticipantFault = new InvalidParticipantFault();
            invalidParticipantFault.setErrorMessage("Invalid participant" + participantId);
            InvalidParticipantFaultException invalidParticipantFaultException = new InvalidParticipantFaultException(
                    invalidParticipantFault.getErrorMessage());
            invalidParticipantFaultException.setFaultMessage(invalidParticipantFault);
            throw invalidParticipantFaultException;
        }

        if (conferenceControlResponse.getStatus() == ConferenceControlResponse.INVALID_ROOM) {
            InvalidConferenceFault invalidConferenceFault = new InvalidConferenceFault();
            invalidConferenceFault.setErrorMessage("Invalid conference" + confId);
            InvalidConferenceFaultException invalidConferenceFaultException = new InvalidConferenceFaultException(
                    invalidConferenceFault.getErrorMessage());
            invalidConferenceFaultException.setFaultMessage(invalidConferenceFault);
            throw invalidConferenceFaultException;
        }

        if(conferenceControlResponse.getStatus() == ConferenceControlResponse.CANNOT_CONTROL_ROOM) {
            InvalidConferenceFault invalidConferenceFault = new InvalidConferenceFault();
            invalidConferenceFault.setErrorMessage("Cannot control conference " + confId);
            InvalidConferenceFaultException invalidConferenceFaultException = new InvalidConferenceFaultException(
                    invalidConferenceFault.getErrorMessage());
            invalidConferenceFaultException.setFaultMessage(invalidConferenceFault);
            throw invalidConferenceFaultException;
        }

        return conferenceControlResponse;
    }

    @Override
    public CreateTestcallRoomResponse createTestcallRoom(CreateTestcallRoomRequest createTestcallRoomRequest)
            throws TestcallRoomCreationFaultException, InvalidArgumentFaultException, GeneralFaultException,
            SeatLicenseExpiredFaultException, NotLicensedFaultException {

        checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

        User user = userService.getLoginUser();
        if (user == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

        Member member = memberService.getMember(user.getMemberID());
        if (member == null) {
            GeneralFault fault = new GeneralFault();
            fault.setErrorMessage("Invalid User");
            GeneralFaultException exception = new GeneralFaultException(
                    fault.getErrorMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }

		boolean canCreateTestcallRoom = true;
		String errorMessage = null;
		Tenant tenantDetails = tenantService.getTenant(TenantContext.getTenantId());
        TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
        Configuration systemConfiguration = systemService.getConfiguration(WEBRTC_TEST_MEDIA_SERVER);
        Configuration scheduledRoomPrefixConfiguration = systemService.getConfiguration(SCHEDULED_ROOM_PREFIX);
        String configurationURL = tenantDetails.getTenantWebRTCURL();
        TestCallService.PortConfig portConfig = TestCallService.PortConfig.TENANT;

        boolean schRoomEnabled = (scheduledRoomPrefixConfiguration == null
				|| StringUtils.isEmpty(scheduledRoomPrefixConfiguration.getConfigurationValue())) ? false : true;
		if (!schRoomEnabled) {
			canCreateTestcallRoom = false;
			errorMessage = "Scheduled Room creation is disabled";
		}
		if (canCreateTestcallRoom) {
			if (tenantConfiguration == null || tenantConfiguration.getTestCall() == 0) {
				canCreateTestcallRoom = false;
				errorMessage = "Testcall feature is disabled";
			}
		}
		if (canCreateTestcallRoom) {
			if (tenantDetails == null || tenantDetails.getScheduledRoomEnabled() == 0) {
				canCreateTestcallRoom = false;
				errorMessage = "Scheduled Room creation is disabled";
			}
		}

        //VPTL-7773 - Fallback to system level configuration if the webRTC is not present for tenant.
        if (canCreateTestcallRoom) {
            if (StringUtils.isBlank(configurationURL)) {
                if (systemConfiguration == null  || StringUtils.isBlank(systemConfiguration.getConfigurationValue())){
                    //fallback to system level configuration of WebRTC. But if the url is not configured throw error
                    canCreateTestcallRoom = false;
                    errorMessage = "WebRTC server URL is not configured.";
                } else{
                    // Now safe to fallback to system level configuration of WebRTC
                    configurationURL = systemConfiguration.getConfigurationValue();
                    portConfig = TestCallServiceImpl.PortConfig.SYSTEM;
                    logger.info("WebRTC configuration set to system level setting.");
                }
            }
        }


        if (!canCreateTestcallRoom) {
			logger.error(String.format("Error while creating a testcall room. %s. Tenant Id : %d", errorMessage, TenantContext.getTenantId()));
			TestcallRoomCreationFault faultObj = new TestcallRoomCreationFault();
			faultObj.setErrorMessage(errorMessage);
			TestcallRoomCreationFaultException testcallRoomCreationFaultException = new TestcallRoomCreationFaultException();
			testcallRoomCreationFaultException.setFaultMessage(faultObj);
			throw testcallRoomCreationFaultException;
		}

        ScheduledRoomResponse scheduledRoomResponse = null;
        try {
            scheduledRoomResponse = testCallService.createScheduledRoomForTestCall(member);
        }catch (ScheduledRoomCreationException exceptionObj){
            TestcallRoomCreationFault fault = new TestcallRoomCreationFault();
            fault.setErrorMessage(exceptionObj.getMessage());
            TestcallRoomCreationFaultException exception = new TestcallRoomCreationFaultException();
            exception.setFaultMessage(fault);
            throw exception;
        }

        //VPTL-7615 - Integrate test call feature with WebRTC.
        CallDetails callDetails = new CallDetails();
        Tenant tenant = this.tenantService.getTenant(TenantContext.getTenantId());
        String tenantURL = tenant.getTenantURL();
        //Set the call name from the configuration.
        Configuration testCallUserNameConfig = this.systemService.getConfiguration(WEBRTC_TEST_CALL_USERNAME);
        callDetails.setName(StringUtils.defaultIfBlank(testCallUserNameConfig.getConfigurationValue(), DEFAULT_TEST_CALL_USERNAME));
        callDetails.setPortal(tenantURL);
        String roomKeyStr = scheduledRoomResponse.getRoom().getRoomKey();
        callDetails.setRoomKey(roomKeyStr);

        // rest template and set the json with all properties.
        //Now invoke the testCall service to make the test call.
        try {
            testCallService.testCall(callDetails, configurationURL, portConfig );
        } catch (TestCallException exception){
            //There is an exception while establishing the connection with WebRTC server.
            logger.warn("Deleting the scheduled room entry from DB after test call failed:: TenantID : "+TenantContext.getTenantId()+"roomKey: "+roomKeyStr);
            roomService.deleteScheduledRoomByRoomKey(roomKeyStr);
            TestcallRoomCreationFault faultObj = new TestcallRoomCreationFault();
            faultObj.setErrorMessage(exception.getMessage());
            TestcallRoomCreationFaultException testcallRoomCreationFaultException = new TestcallRoomCreationFaultException();
            testcallRoomCreationFaultException.setFaultMessage(faultObj);
            throw testcallRoomCreationFaultException;
        }
        //If there is no exception return the response.
        long pin = scheduledRoomResponse.getPin();

        Pin_type5 roomPin = new Pin_type5();
        roomPin.setPin_type4(Long.toString(pin));

        CreateTestcallRoomResponse createTestcallRoomResponse = new CreateTestcallRoomResponse();

        String extension = scheduledRoomResponse.getRoomExtn();

        if (extension != null ) {
            Extension_type7 resExtension = new Extension_type7();
            resExtension.setExtension_type6(extension);
            createTestcallRoomResponse.setExtension(resExtension);
        }
        try {
            createTestcallRoomResponse.setRoomURL(new URI(scheduledRoomResponse.getRoom().getRoomURL()));
        } catch (URI.MalformedURIException e) {
            e.printStackTrace();
        }
        createTestcallRoomResponse.setRoomKey(roomKeyStr);

        return createTestcallRoomResponse;
    }

    /**
     * Silences/Local/Soft mutes the audio of the participant in a conference.
     * Returns the media status of the participant after sending vcap message to VidyoManager.
     */
	@Override
	public ParticipantStatusResponse muteAudioLocal(MuteAudioRequest muteAudioRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException,
			SeatLicenseExpiredFaultException, NotLicensedFaultException {

		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		ParticipantStatusResponse participantStatusResponse = new ParticipantStatusResponse();

		String endpointGUID = retrieveGUIDAfterRoomModerationValidation(muteAudioRequest.getConferenceID(),
				muteAudioRequest.getParticipantID(), muteAudioRequest.getModeratorPIN());

		// EndpointGUID will be validate if it reaches here
		try {
			conferenceService.silenceAudio(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Internal Server Error");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// Retrieve the audio/video/appshare status from DB
		ParticipantStatus participantStatus = new ParticipantStatus();
		participantStatus.setConferenceID(muteAudioRequest.getConferenceID());
		PositiveInteger participantId = new PositiveInteger(muteAudioRequest.getParticipantID().getEntityID());
		participantStatus.setParticipantID(participantId);
		participantStatusResponse.setParticipantStatus(participantStatus);
		return participantStatusResponse;
	}

    /**
     * Silences/Local/Soft mutes the video of the participant in a conference.
     * Returns the media status of the participant after sending vcap message to VidyoManager.
     */
	@Override
	public ParticipantStatusResponse muteVideoLocal(MuteVideoRequest muteVideoRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, ControlMeetingFaultException,
			SeatLicenseExpiredFaultException, NotLicensedFaultException {
		checkLicenseForAllowUserAPIs(MessageContext.getCurrentMessageContext());

		ParticipantStatusResponse participantStatusResponse = new ParticipantStatusResponse();

		String endpointGUID = retrieveGUIDAfterRoomModerationValidation(muteVideoRequest.getConferenceID(),
				muteVideoRequest.getParticipantID(), muteVideoRequest.getModeratorPIN());

		// EndpointGUID will be validate if it reaches here
		try {
			conferenceService.silenceVideo(endpointGUID);
		} catch (Exception e) {
			GeneralFault fault = new GeneralFault();
			fault.setErrorMessage("Internal Server Error");
			GeneralFaultException exception = new GeneralFaultException(fault.getErrorMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		// Retrieve the audio/video/appshare status from DB
		ParticipantStatus participantStatus = new ParticipantStatus();
		participantStatus.setConferenceID(muteVideoRequest.getConferenceID());
		PositiveInteger participantId = new PositiveInteger(muteVideoRequest.getParticipantID().getEntityID());
		participantStatus.setParticipantID(participantId);
		participantStatusResponse.setParticipantStatus(participantStatus);
		return participantStatusResponse;
	}

}