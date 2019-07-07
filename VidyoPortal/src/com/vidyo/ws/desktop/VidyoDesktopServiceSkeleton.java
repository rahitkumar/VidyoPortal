/**
 * VidyoDesktopServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
package com.vidyo.ws.desktop;

import org.apache.axis2.context.MessageContext;

import com.vidyo.service.*;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.security.token.PersistentTokenService;
import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;
import com.vidyo.bo.*;
import com.vidyo.bo.Entity;
import com.vidyo.bo.memberbak.MemberBAKType;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoDesktopServiceSkeleton java skeleton for the axisService
 */
public class VidyoDesktopServiceSkeleton implements VidyoDesktopServiceSkeletonInterface {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(VidyoDesktopServiceSkeleton.class.getName());

    private IUserService user;
    private IMemberService member;
    private IRoomService room;
    private IConferenceService conference;
    private ITenantService tenantService;
    
    private ISystemService systemService;
    
    /**
     * 
     */
    private ConferenceAppService conferenceAppService;
    
    /**
     * 
     */
    private PersistentTokenService persistentTokenService;
    

	/**
	 * @param systemService the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setUser(IUserService user) {
        this.user = user;
    }

    public void setMember(IMemberService member) {
        this.member = member;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setConference(IConferenceService conference) {
        this.conference = conference;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }
    
    /**
	 * @param conferenceAppService the conferenceAppService to set
	 */
	public void setConferenceAppService(ConferenceAppService conferenceAppService) {
		this.conferenceAppService = conferenceAppService;
	}

	/**
	 * @param persistentTokenService the persistentTokenService to set
	 */
	public void setPersistentTokenService(PersistentTokenService persistentTokenService) {
		this.persistentTokenService = persistentTokenService;
	}

	/**
     * Auto generated method signature
     *
     * @param createMyRoomURLRequest         :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.CreateMyRoomURLResponse createMyRoomURL
            (
                    com.vidyo.ws.desktop.CreateMyRoomURLRequest createMyRoomURLRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        CreateMyRoomURLResponse resp = new CreateMyRoomURLResponse();
        User user = this.user.getLoginUser();

        Member member = this.member.getMember(user.getMemberID());
        Room room = this.room.getRoom(member.getRoomID());

        if (room != null) {
            if (room.getMemberID() != user.getMemberID()) {
                throw new InvalidArgumentFaultException("You are not an owner of room for roomID = " + room.getRoomID());
            }
            this.room.generateRoomKey(room);
        }

        resp.setStatus(Status_type1.OK);
        return resp;
    }


    /**
     * Auto generated method signature
     *
     * @param startMyConferenceRequest       :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.StartMyConferenceResponse startMyConference
            (
                    com.vidyo.ws.desktop.StartMyConferenceRequest startMyConferenceRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException, RoomDisabledFaultException
    {
        StartMyConferenceResponse resp = new StartMyConferenceResponse();
        User user = this.user.getLoginUser();

        int memberID = user.getMemberID();
		Member joiningMember = member.getInviterDetails(memberID);
		JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();
		joinConferenceRequest.setJoiningMember(joiningMember);
		joinConferenceRequest.setJoiningMemberId(memberID);
		joinConferenceRequest.setRoomId(joiningMember.getRoomID());
		
		com.vidyo.service.conference.response.JoinConferenceResponse joinConferenceResponse = conferenceAppService.joinConferenceInRoom(joinConferenceRequest);
		
		if(joinConferenceResponse.getStatus() == com.vidyo.service.conference.response.JoinConferenceResponse.ROOM_DISABLED) {
			throw new RoomDisabledFaultException();
		} else if(joinConferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault generalFault = new GeneralFault();
			generalFault.setErrorMessage(joinConferenceResponse.getMessage());
			GeneralFaultException generalFaultException = new GeneralFaultException();
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		}

		String referenceNumber = startMyConferenceRequest.getReferenceNumber();
		String guid = this.user.getLinkedEndpointGUID(user.getMemberID());
		this.user.updateEndpointReferenceNumber(guid, referenceNumber);

		resp.setStatus(Status_type1.OK);
        return resp;
    }

    
    /**
     * Auto generated method signature
     *
     * @param joinConferenceRequest          :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.JoinConferenceResponse joinConference
            (
                    com.vidyo.ws.desktop.JoinConferenceRequest joinConferenceRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        JoinConferenceResponse resp = new JoinConferenceResponse();
        User user = this.user.getLoginUser();

        String toUserName = joinConferenceRequest.getUsername();
        String toTenant = joinConferenceRequest.getTenant();

        Member toMember = this.member.getMemberByName(toUserName, toTenant);
        
        int roomIdToJoin = toMember.getRoomID();
        
		JoinConferenceRequest conferenceRequest = new JoinConferenceRequest();
		conferenceRequest.setJoiningMemberId(user.getMemberID());
		conferenceRequest.setRoomId(roomIdToJoin);
		com.vidyo.service.conference.response.JoinConferenceResponse conferenceResponse = conferenceAppService.joinConferenceInRoom(conferenceRequest);
		
		if (conferenceResponse.getStatus() != com.vidyo.service.conference.response.JoinConferenceResponse.SUCCESS) {
			GeneralFault generalFault = new GeneralFault();
			generalFault.setErrorMessage(conferenceResponse.getMessage());
			GeneralFaultException generalFaultException = new GeneralFaultException();
			generalFaultException.setFaultMessage(generalFault);
			throw generalFaultException;
		}

		String referenceNumber = joinConferenceRequest.getReferenceNumber();
		String guid = this.user.getLinkedEndpointGUID(user.getMemberID());
		this.user.updateEndpointReferenceNumber(guid, referenceNumber);

		resp.setStatus(Status_type1.OK);
        return resp;
    }


    /**
     * Auto generated method signature
     *
     * @param inviteToConferenceRequest      :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.InviteToConferenceResponse inviteToConference
            (
                    com.vidyo.ws.desktop.InviteToConferenceRequest inviteToConferenceRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        InviteToConferenceResponse resp = new InviteToConferenceResponse();
        User user = this.user.getLoginUser();

        Member fromMember = this.member.getMember(user.getMemberID());

        Invite invite = new Invite();
        invite.setFromMemberID(fromMember.getMemberID());
        invite.setFromRoomID(fromMember.getRoomID());

        String toUserName = inviteToConferenceRequest.getUsername();
        String toTenant = inviteToConferenceRequest.getTenant();

        Member toMember = this.member.getMemberByName(toUserName, toTenant);

        invite.setToMemberID(toMember.getMemberID());

        int toEndpointID = this.conference.getEndpointIDForMemberID(toMember.getMemberID());
        invite.setToEndpointID(toEndpointID);

        // check if status of Endpoint is Online
        int status = this.conference.getEndpointStatus(toEndpointID);
        if (status != 1) {
            throw new InvalidArgumentFaultException("Status of invited member is not Online.");
        }

        try {
            this.conference.inviteToConference(invite);
        } catch (Exception e) {
            throw new GeneralFaultException(e.getMessage());
        }

        resp.setStatus(Status_type1.OK);
        return resp;
    }


    /**
     * Auto generated method signature
     *
     * @param getUserDataRequest             :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.GetUserDataResponse getUserData
            (
                    com.vidyo.ws.desktop.GetUserDataRequest getUserDataRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        GetUserDataResponse resp = new GetUserDataResponse();
        User user = this.user.getLoginUser();

        String EID = getUserDataRequest.getEID().getEID_type0();
        if (EID.equalsIgnoreCase("")) {
            throw new InvalidArgumentFaultException("Bad EID = " + EID);
        }

        Member member = this.member.getMember(user.getMemberID());
        if (!member.getEndpointGUID().equalsIgnoreCase(EID)) {
            throw new InvalidArgumentFaultException("User '" + user.getUsername() + "' is not linked to EID '" + EID + "'");
        }

        try {
            com.vidyo.bo.Entity entity = this.member.getContact(user.getMemberID());
            Entity_type0 ws_entity = new Entity_type0();
            EntityID id = new EntityID();
            id.setEntityID(entity.getRoomID());
            ws_entity.setEntityID(id);
            ws_entity.setEntityType(EntityType_type1.Member);
            ws_entity.setDisplayName(entity.getName());
            ws_entity.setExtension(entity.getExt());
            ws_entity.setDescription(entity.getDescription());
            ws_entity.setTenant(entity.getTenantName());

            if (user.getLangID() == 1) {
                ws_entity.setLanguage(Language_type1.en);
            } else if (user.getLangID() == 2) {
                ws_entity.setLanguage(Language_type1.fr);
            } else if (user.getLangID() == 3) {
                ws_entity.setLanguage(Language_type1.ja);
            } else if (user.getLangID() == 4) {
                ws_entity.setLanguage(Language_type1.zh_CN);
            } else if (user.getLangID() == 5) {
                ws_entity.setLanguage(Language_type1.es);
            } else if (user.getLangID() == 6) {
                ws_entity.setLanguage(Language_type1.it);
            } else if (user.getLangID() == 7) {
                ws_entity.setLanguage(Language_type1.de);
            } else if (user.getLangID() == 8) {
                ws_entity.setLanguage(Language_type1.ko);
            } else if (user.getLangID() == 9) {
                ws_entity.setLanguage(Language_type1.pt);
            } else if (user.getLangID() == 10) {
            	ws_entity.setLanguage(Language_type1.Factory
						.fromValue(systemService
								.getSystemLang(user.getTenantID())
								.getLangCode()));
            } else if (user.getLangID() == 11) {
            	ws_entity.setLanguage(Language_type1.fi);
            } else if (user.getLangID() == 12) {
            	ws_entity.setLanguage(Language_type1.pl);
            } else if(user.getLangID() ==  13) {
            	ws_entity.setLanguage(Language_type1.zh_TW);
            } else if(user.getLangID() ==  13) {
            	ws_entity.setLanguage(Language_type1.th);
            }

            // ToDo need to have in DB
            ws_entity.setMemberMode(MemberMode_type0.Available);

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
            }

            RoomMode_type0 roomMode = new RoomMode_type0();
            roomMode.setIsLocked(entity.getRoomLocked() == 1);
            roomMode.setHasPin(entity.getRoomPinned() == 1);
            if (entity.getRoomPIN() != null) {
                roomMode.setRoomPIN(entity.getRoomPIN());
            }
            if (entity.getRoomKey() != null) {
            	String transportName = MessageContext.getCurrentMessageContext().getIncomingTransportName();
            	Tenant tenant = this.tenantService.getTenant(entity.getTenantID());
                StringBuffer path = new StringBuffer();
                /*context.getTo().getAddress().substring(0, context.getTo().getAddress().indexOf("/ser")));
                path.append("/flex.html?roomdirect.html&key=");
                path.append(entity.getRoomKey());*/
                
                String joinURL = this.room.getRoomURL(systemService, transportName, 
        				tenant.getTenantURL(), entity.getRoomKey());

        		path.append(joinURL);
                if(tenantService.isTenantNotAllowingGuests()){
            		path.append("&noguest");
            	}
                
                roomMode.setRoomURL(path.toString());
            }
            ws_entity.setRoomMode(roomMode);

            if (entity.getRoomStatus() == 0) {
                ws_entity.setRoomStatus(RoomStatus_type0.Empty);
            } else if (entity.getRoomStatus() == 1) {
                ws_entity.setRoomStatus(RoomStatus_type0.Occupied);
            } else if (entity.getRoomStatus() == 2) {
                ws_entity.setRoomStatus(RoomStatus_type0.Full);
            }

            ws_entity.setVideo(true);
            ws_entity.setAudio(true);
            ws_entity.setAppshare(true);

            resp.setEntity(ws_entity);

            return resp;

        } catch (Exception e) {
            throw new GeneralFaultException(e.getMessage());
        }
    }


    /**
     * Auto generated method signature
     *
     * @param getUserStatusRequest           :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.GetUserStatusResponse getUserStatus
            (
                    com.vidyo.ws.desktop.GetUserStatusRequest getUserStatusRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        GetUserStatusResponse resp = new GetUserStatusResponse();
        User user = this.user.getLoginUser();

        String userName = getUserStatusRequest.getUsername();
        String tenant = getUserStatusRequest.getTenant();

        if (tenant != null && !tenant.equalsIgnoreCase("")) {
            Member member = this.member.getMemberByName(userName, tenant);
            com.vidyo.bo.Entity entity = this.member.getContact(member.getMemberID());

            UserStatus_type0 userStatus = new UserStatus_type0();
            userStatus.setUsername(entity.getUsername());
            userStatus.setTenant(entity.getTenantName());
            if (entity.getMemberStatus() == 0) {
                userStatus.setMemberStatus(MemberStatus_type0.Offline);
            } else if (entity.getMemberStatus() == 1) {
                userStatus.setMemberStatus(MemberStatus_type0.Online);
            } else if (entity.getMemberStatus() == 2) {
                userStatus.setMemberStatus(MemberStatus_type0.Busy);
            } else if (entity.getMemberStatus() == 3) {
                userStatus.setMemberStatus(MemberStatus_type0.Ringing);
            } else if (entity.getMemberStatus() == 4) {
                userStatus.setMemberStatus(MemberStatus_type0.RingAccepted);
            } else if (entity.getMemberStatus() == 5) {
                userStatus.setMemberStatus(MemberStatus_type0.RingRejected);
            } else if (entity.getMemberStatus() == 6) {
                userStatus.setMemberStatus(MemberStatus_type0.RingNoAnswer);
            } else if (entity.getMemberStatus() == 7) {
                userStatus.setMemberStatus(MemberStatus_type0.Alerting);
            } else if (entity.getMemberStatus() == 8) {
                userStatus.setMemberStatus(MemberStatus_type0.AlertCancelled);
            } else if (entity.getMemberStatus() == 9) {
                userStatus.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
            }

            resp.addUserStatus(userStatus);
        } else {
            EntityFilter filter = new EntityFilter();
            filter.setLimit(Integer.MAX_VALUE);
            filter.setQuery(userName);

            List<Entity> users = this.room.getEntity(filter, user);

            for (Entity entity : users) {
                UserStatus_type0 userStatus = new UserStatus_type0();
                userStatus.setUsername(entity.getUsername());
                userStatus.setTenant(entity.getTenantName());
                if (entity.getMemberStatus() == 0) {
                    userStatus.setMemberStatus(MemberStatus_type0.Offline);
                } else if (entity.getMemberStatus() == 1) {
                    userStatus.setMemberStatus(MemberStatus_type0.Online);
                } else if (entity.getMemberStatus() == 2) {
                    userStatus.setMemberStatus(MemberStatus_type0.Busy);
                } else if (entity.getMemberStatus() == 3) {
                    userStatus.setMemberStatus(MemberStatus_type0.Ringing);
                } else if (entity.getMemberStatus() == 4) {
                    userStatus.setMemberStatus(MemberStatus_type0.RingAccepted);
                } else if (entity.getMemberStatus() == 5) {
                    userStatus.setMemberStatus(MemberStatus_type0.RingRejected);
                } else if (entity.getMemberStatus() == 6) {
                    userStatus.setMemberStatus(MemberStatus_type0.RingNoAnswer);
                } else if (entity.getMemberStatus() == 7) {
                    userStatus.setMemberStatus(MemberStatus_type0.Alerting);
                } else if (entity.getMemberStatus() == 8) {
                    userStatus.setMemberStatus(MemberStatus_type0.AlertCancelled);
                } else if (entity.getMemberStatus() == 9) {
                    userStatus.setMemberStatus(MemberStatus_type0.BusyInOwnRoom);
                }

                resp.addUserStatus(userStatus);
            }
        }
        return resp;
    }


    /**
     * Auto generated method signature
     * get Browser Access Key
     *
     * @param browserAccessKeyRequest        :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.desktop.BrowserAccessKeyResponse getBrowserAccessKey
            (
                    com.vidyo.ws.desktop.BrowserAccessKeyRequest browserAccessKeyRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        BrowserAccessKeyResponse resp = new BrowserAccessKeyResponse();
        User user = this.user.getLoginUser();

        // Adding the legacy BAK type to maintain backward compatibility of the API.
        // The bak parameter in flex.html isn't supported any more.
        String BAK = this.user.generateBAKforMember(user.getMemberID(), MemberBAKType.Legacy);

        MessageContext context = MessageContext.getCurrentMessageContext();
        StringBuffer path = new StringBuffer(context.getTo().getAddress().substring(0, context.getTo().getAddress().indexOf("/ser")));
        path.append("/flex.html?bak=");
        path.append(BAK);

        resp.setBrowserAccessKey(path.toString());
        return resp;
    }

	/**
	 * Generates Auth Token for the VidyoDesktop/UVD. This API uses userid/SAK for authentication.
	 * 
	 * @param generateAuthTokenRequest
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */
	public GenerateAuthTokenResponse generateAuthToken(GenerateAuthTokenRequest generateAuthTokenRequest)
			throws EndpointNotBoundFaultException, InvalidArgumentFaultException, GeneralFaultException {

		User user = this.user.getLoginUser();

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
		tokenCreationRequest.setValidityTime(generateAuthTokenRequest.getValidityTime().getValidityTime_type0());

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

		return authTokenResponse;

	}

}
    
