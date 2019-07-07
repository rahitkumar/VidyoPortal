/**
 * VidyoFederationServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.ws.federation;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.ExternalLink;
import com.vidyo.bo.Federation;
import com.vidyo.bo.FederationConferenceRecord;
import com.vidyo.bo.Group;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.conference.Conference;
import com.vidyo.service.IFederationConferenceService;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.MuteAudioException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.SilenceAudioException;
import com.vidyo.service.exceptions.SilenceVideoException;
import com.vidyo.service.exceptions.StartVideoException;
import com.vidyo.service.exceptions.StopVideoException;
import com.vidyo.service.exceptions.UnmuteAudioException;
import com.vidyo.service.interportal.InterPortalConferenceService;
import com.vidyo.service.room.ScheduledRoomResponse;

/**
 * VidyoFederationServiceSkeleton java skeleton for the axisService
 */
public class VidyoFederationServiceSkeleton implements VidyoFederationServiceSkeletonInterface {

    /**
     * Logger for this class and subclasses
     */
    protected final Logger logger = LoggerFactory.getLogger(VidyoFederationServiceSkeleton.class.getName());

    private ISystemService system;
    private ITenantService tenantService;
    private IRoomService room;
    private IGroupService group;
    private IFederationConferenceService federationConferenceService;
    private InterPortalConferenceService interPortalConferenceService;
    
    private ConferenceAppService conferenceAppService;

    /**
	 * @param conferenceAppService the conferenceAppService to set
	 */
	public void setConferenceAppService(ConferenceAppService conferenceAppService) {
		this.conferenceAppService = conferenceAppService;
	}

	public void setSystem(ISystemService system) {
        this.system = system;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setGroup(IGroupService group) {
        this.group = group;
    }

    public void setFederationConferenceService(IFederationConferenceService federationConferenceService) {
        this.federationConferenceService = federationConferenceService;
    }

	public void setInterPortalConferenceService(InterPortalConferenceService interPortalConferenceService) {
		this.interPortalConferenceService = interPortalConferenceService;
	}

    /**
     * Auto generated method signature
     * Join to remote conference on federation of Vidyo Portals
     *
     * @param joinRemoteConferenceRequest:
     * @throws InvalidArgumentFaultException :
     * @throws UserNotFoundFaultException    :
     * @throws GeneralFaultException         :
     * @throws TenantNotFoundFaultException  :
     */
    public com.vidyo.ws.federation.JoinRemoteConferenceResponse joinRemoteConference
    	(
            com.vidyo.ws.federation.JoinRemoteConferenceRequest joinRemoteConferenceRequest
    	)
		throws
		InvalidArgumentFaultException,
		UserNotFoundFaultException,
		GeneralFaultException,
		ConferenceLockedFaultException,
		TenantNotFoundFaultException,
		FederationNotAllowedFaultException,
		WrongPinFaultException
    {
    	//TODO - remove Federations table
        JoinRemoteConferenceResponse resp = new JoinRemoteConferenceResponse();

        Federation federation = new Federation();
        ExternalLink externalLink = new ExternalLink();

        String requestID = joinRemoteConferenceRequest.getRequestID();
        federation.setRequestID(requestID);
        externalLink.setRequestID(requestID);
        String fromSystemID = joinRemoteConferenceRequest.getFromSystemID();
        externalLink.setFromSystemID(fromSystemID);

        String fromUserName = joinRemoteConferenceRequest.getFromUserName();
        if (fromUserName.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			String msg = "Empty fromUserName";
			logger.error(msg);
			fault.setErrorMessage(msg);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }
        federation.setFromUser(fromUserName);

        String fromTenantHost = joinRemoteConferenceRequest.getFromTenantHost();
        if (fromTenantHost.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			String msg = "Empty fromTenantHost";
			logger.error(msg);
			fault.setErrorMessage(msg);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }
        externalLink.setFromTenantHost(fromTenantHost);

        String toUserName = joinRemoteConferenceRequest.getToUserName();
        if (toUserName.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			String msg = "Empty toUserName";
			logger.error(msg);
			fault.setErrorMessage(msg);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }
        federation.setToUser(toUserName);

        String toTenantHost = joinRemoteConferenceRequest.getToTenantHost();
        if (toTenantHost.equalsIgnoreCase("")) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			String msg = "Empty toTenantHost";
			logger.error(msg);
			fault.setErrorMessage(msg);
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }
        externalLink.setToTenantHost(toTenantHost);

		Tenant toTenant;
		try {
			toTenant = this.tenantService.getTenantByURL(toTenantHost);
		} catch (Exception e1) {
			TenantNotFoundFault fault = new TenantNotFoundFault();
			String msg = "Tenant not found = " + toTenantHost;
			logger.error(msg);
			fault.setErrorMessage(msg);
			TenantNotFoundFaultException exception = new TenantNotFoundFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
		}
		if (toTenant == null) {
			TenantNotFoundFault fault = new TenantNotFoundFault();
			String msg = "No tenant for given toTenantHost = " + toTenantHost;
			logger.error(msg);
			fault.setErrorMessage(msg);
			TenantNotFoundFaultException exception = new TenantNotFoundFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
		}

        boolean allowByWL = false;
        allowByWL = interPortalConferenceService.isInterPortalConferencingAllowed(toTenant.getTenantID(), fromTenantHost, false);

        if (!allowByWL) {
			FederationNotAllowedFault fault = new FederationNotAllowedFault();
			String msg = "Inter-Portal Communication not allowed with host = " + toTenantHost;
			logger.error(msg);
			fault.setErrorMessage(msg);
			FederationNotAllowedFaultException exception = new FederationNotAllowedFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }		
		
		Room room;
		try {
			room = this.room.getRoomByNameAndTenantName(toUserName, toTenant.getTenantName());
		} catch (Exception e1) {
			// Set room to null for the Scheduled Room check to proceed
			logger.error("Room not found for roomname/ext - {}, tenant - {}", toUserName, toTenant.getTenantName());
			room = null;
		}
		boolean checkPin = true;
		if (room == null) {
			// Check if it is a scheduled room extension
			ScheduledRoomResponse scheduledRoomResponse = conferenceAppService.validateScheduledRoom(toUserName, joinRemoteConferenceRequest.getPin());
			// Handle scheduled room pin check
			if(scheduledRoomResponse.getStatus() == ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN) {
				WrongPinFault fault = new WrongPinFault();
				String msg = "Wrong PIN - " + joinRemoteConferenceRequest.getPin();
				logger.error(msg);
				fault.setErrorMessage(msg);
				WrongPinFaultException exception = new WrongPinFaultException(msg);
				exception.setFaultMessage(fault);
				throw exception;
			}
			// If room is invalid return error
			if(scheduledRoomResponse.getStatus() != 0) {
				UserNotFoundFault fault = new UserNotFoundFault();
				String msg = "User not found - " + toUserName;
				logger.error(msg);
				fault.setErrorMessage(msg);
				UserNotFoundFaultException exception = new UserNotFoundFaultException(msg);
				exception.setFaultMessage(fault);
				throw exception;
			}
			room = scheduledRoomResponse.getRoom();
			room.setSchRoomConfName(toUserName + joinRemoteConferenceRequest.getPin());
			// If scheduled room, pin is already validated
			checkPin = false;
		}

        if (room.getRoomLocked() == 1) {
			ConferenceLockedFault fault = new ConferenceLockedFault();
			String msg = "Conference locked - " + room.getRoomName();
			logger.error(msg);
			fault.setErrorMessage(msg);
			ConferenceLockedFaultException exception = new ConferenceLockedFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }

        if (checkPin && room.getRoomPinned() == 1) {
            if (!room.getRoomPIN().equalsIgnoreCase("")) {
                String pin = joinRemoteConferenceRequest.getPin();
				if (pin == null) pin = "";
                if (!room.getRoomPIN().equalsIgnoreCase(pin)) {
					WrongPinFault fault = new WrongPinFault();
					String msg = "Wrong PIN - " + pin;
					logger.error(msg);
					fault.setErrorMessage(msg);
					WrongPinFaultException exception = new WrongPinFaultException(msg);
					exception.setFaultMessage(fault);
					throw exception;
                }
            }
        }

        String localSystemID = this.system.getVidyoManagerID();
        externalLink.setToSystemID(localSystemID);

        String conferenceName = "";
        try {
        	//TODO - How to cleanup the conferences when remote host and localhost cannot communicate
            Conference conference = this.federationConferenceService.createConferenceForFederation(room, toTenant, false); // do not check of free ports
            conferenceName = conference.getConferenceName();
            externalLink.setToConferenceName(conferenceName);
            externalLink.setUniqueCallID(conference.getUniqueCallId());
        } catch (OutOfPortsException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
        }

        if (conferenceName.equalsIgnoreCase("")) {
            resp.setToSystemID(localSystemID);
            resp.setRequestID(requestID);
            resp.setStatus(Status_type0.NO);
        } else {
            resp.setToConferenceName(conferenceName);
            resp.setToSystemID(localSystemID);
            resp.setRequestID(requestID);
            resp.setStatus(Status_type0.YES);

            Group group = this.group.getGroup(room.getGroupID());

            resp.setRoomMaxUsers(String.valueOf(group.getRoomMaxUsers()));
            resp.setUserMaxBandWidthIn(String.valueOf(group.getUserMaxBandWidthIn()));
            resp.setUserMaxBandWidthOut(String.valueOf(group.getUserMaxBandWidthOut()));

            // Will keep the same conferense name on remote side
            externalLink.setFromConferenceName(conferenceName);

            // Keep record for this request in DB
            String requestId = federationConferenceService.createRecordForFederation(federation, externalLink);
            resp.setRequestID(requestId);
        }

        return resp;
    }

    /**
     * Auto generated method signature
     * Exchange information about Cascaded Vidyo Routers
     *
     * @param exchangeMediaInfoRequest       :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.ExchangeMediaInfoResponse exchangeMediaInfo
	    (
            com.vidyo.ws.federation.ExchangeMediaInfoRequest exchangeMediaInfoRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        ExchangeMediaInfoResponse resp = new ExchangeMediaInfoResponse();

        // Step 1 - update federation record with info about remote side
        String requestID = exchangeMediaInfoRequest.getRequestID();
        String mediaAddress = exchangeMediaInfoRequest.getMediaAddress();
        String mediaAdditionalInfo = exchangeMediaInfoRequest.getMediaAdditionalInfo();

		// have to search based on confNames
		ExternalLink externalLink = this.federationConferenceService
				.getExternalLink(requestID,
						exchangeMediaInfoRequest.getFromConferenceName(),
						exchangeMediaInfoRequest.getToConferenceName());
        // No externalLink for the requested requestID

        if (externalLink == null) {
			logger.error("This should not happen ->" + requestID + " From->"
					+ exchangeMediaInfoRequest.getFromConferenceName()
					+ " ToConf ->"
					+ exchangeMediaInfoRequest.getToConferenceName());
            //externalLink = this.federationConferenceService.getExternalLinkByRemoteMedia(mediaAddress, mediaAdditionalInfo);
			resp.setStatus(Status_type0.NO);
			return resp;
        }
        this.federationConferenceService.updateExternalLinkForMediaInfo(externalLink.getExternalLinkID(), requestID, mediaAddress, mediaAdditionalInfo);

        // Step 2 - compare transport in local and remote mediaAddresses: both should match - secure/secure or unsecure/unsecure
        // if different - drop the federation
        String remoteTransport = mediaAddress.substring(mediaAddress.lastIndexOf("=") + 1);
        String localMediaAddress = externalLink.getLocalMediaAddress();
        if (localMediaAddress != null) {
            String localTransport = localMediaAddress.substring(localMediaAddress.lastIndexOf("=") + 1);
            if (!remoteTransport.equalsIgnoreCase(localTransport)) {
                // drop call and clean up DB
                try {
                    this.federationConferenceService.unlinkMediaWithRemote(externalLink, false);
                } catch (RemoteException e) {
                    try {
                        this.federationConferenceService.unlinkMediaWithRemote(externalLink, true);
                    } catch (RemoteException ignored) {
                    } catch (ConferenceNotExistException ignored) {}
                } catch (ConferenceNotExistException ignored) {}

                try {
                    this.federationConferenceService.removeExternalLink(externalLink);
                } catch (NoVidyoManagerException ignored) {}

                this.federationConferenceService.removeRecordForFederation(externalLink);

                String msg = "Cannot do IPC call between secured and unsecured routers";
                GeneralFault fault = new GeneralFault();
                logger.error(msg);
                fault.setErrorMessage(msg);
                GeneralFaultException exception = new GeneralFaultException(msg);
                exception.setFaultMessage(fault);
                throw exception;
            }
        }

        /*ExternalLink updatedExternalLink = this.federationConferenceService.getExternalLinkByRequestID(requestID);

        // Step 2.5 - in case of new requestID - host will reuse old externalLink for the same remoteMediaInfo
        if (needSendMediaInfoToRemote) {
            try {
                try {
                    this.federationConferenceService.sendMediaInfoToRemote(updatedExternalLink, false);
                } catch (RemoteException e) {
                    this.federationConferenceService.sendMediaInfoToRemote(updatedExternalLink, true);
                }
            } catch (RemoteException e) {
                logger.error(e.getMessage());
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
            }
        }*/

        // Step 3 - depends on which side portal stays (from or to) do different functionality
        // from - connectExternalLink
        // to - createExternalLink
        try {
        	externalLink.setRemoteMediaAddress(mediaAddress);
        	externalLink.setRemoteMediaAdditionalInfo(mediaAdditionalInfo);
            this.federationConferenceService.linkMediaWithRemote(externalLink);
        } catch (ConferenceNotExistException e) {
            GeneralFault fault = new GeneralFault();
            logger.error(e.getMessage());
            fault.setErrorMessage(e.getMessage());
            GeneralFaultException exception = new GeneralFaultException(e.getMessage());
            exception.setFaultMessage(fault);
            throw exception;
        }
        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Disconnect from remote conference on federation of Vidyo Portals
     *
     * @param dropRemoteConferenceRequest    :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.DropRemoteConferenceResponse dropRemoteConference
	    (
            com.vidyo.ws.federation.DropRemoteConferenceRequest dropRemoteConferenceRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        DropRemoteConferenceResponse resp = new DropRemoteConferenceResponse();
        String fromSystemID = dropRemoteConferenceRequest.getFromSystemID();
        String fromConferenceName = dropRemoteConferenceRequest.getFromConferenceName();
        String toConferenceName = dropRemoteConferenceRequest.getToConferenceName();

        // externalLink - let's remove it from VR
        ExternalLink externalLink = federationConferenceService.getExternalLinkForUpdateStatus(fromSystemID, fromConferenceName, toConferenceName);
        if(externalLink != null) {
        	federationConferenceService.dropRemoteConference(externalLink);
        }

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Send info about remoute Endpoint to Host portal
     *
     * @param sendEndpointInfoToHostRequest  :
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.SendEndpointInfoToHostResponse sendEndpointInfoToHost
	    (
            com.vidyo.ws.federation.SendEndpointInfoToHostRequest sendEndpointInfoToHostRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        SendEndpointInfoToHostResponse resp = new SendEndpointInfoToHostResponse();

        FederationConferenceRecord fr = new FederationConferenceRecord();
        fr.setAudio(sendEndpointInfoToHostRequest.getAudio());
        fr.setConferenceName(sendEndpointInfoToHostRequest.getConferenceName());
        fr.setConferenceType(sendEndpointInfoToHostRequest.getConferenceType());
        fr.setDialIn(sendEndpointInfoToHostRequest.getDialIn());
        fr.setDisplayName(sendEndpointInfoToHostRequest.getDisplayName());
        fr.setEndpointCaller(sendEndpointInfoToHostRequest.getEndpointCaller());
        fr.setEndpointGUID(sendEndpointInfoToHostRequest.getEndpointGUID());
        fr.setEndpointID(Integer.valueOf(sendEndpointInfoToHostRequest.getEndpointID()));
        fr.setEndpointType(sendEndpointInfoToHostRequest.getEndpointType());
        fr.setExtension(sendEndpointInfoToHostRequest.getExtension());
        fr.setUserNameAtTenant(sendEndpointInfoToHostRequest.getUserNameAtTenant());
        fr.setVideo(sendEndpointInfoToHostRequest.getVideo());

        this.federationConferenceService.insertFederationConferenceRecord(fr);

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Disconnect remote participant from the conference
     *
     * @param disconnectEndpointFromHostRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.DisconnectEndpointFromHostResponse disconnectEndpointFromHost
	    (
			com.vidyo.ws.federation.DisconnectEndpointFromHostRequest disconnectEndpointFromHostRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        DisconnectEndpointFromHostResponse resp = new DisconnectEndpointFromHostResponse();

        String endpointGUID = disconnectEndpointFromHostRequest.getEndpointGUID();
        String conferenceName = disconnectEndpointFromHostRequest.getConferenceName();

        try {
            this.federationConferenceService.receiveDisconnectParticipantFromFederation(endpointGUID, conferenceName);
        } catch (LeaveConferenceException e) {
			GeneralFault fault = new GeneralFault();
			String msg = "Cannot disconnect endpoint - " + endpointGUID + " from conference - " + conferenceName;
			logger.error(msg);
			fault.setErrorMessage(msg);
			GeneralFaultException exception = new GeneralFaultException(msg);
			exception.setFaultMessage(fault);
			throw exception;
        }

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Remove info about remote Endpoint from Host portal
     *
     * @param removeEndpointInfoFromHostRequest:
     *
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.RemoveEndpointInfoFromHostResponse removeEndpointInfoFromHost
	    (
            com.vidyo.ws.federation.RemoveEndpointInfoFromHostRequest removeEndpointInfoFromHostRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        RemoveEndpointInfoFromHostResponse resp = new RemoveEndpointInfoFromHostResponse();

        String endpointGUID = removeEndpointInfoFromHostRequest.getEndpointGUID();

        this.federationConferenceService.deleteFederationConferenceRecord(endpointGUID);

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Mute the audio for a given Endpoint GUID in the conference.
     *
     * @param muteAudioRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.MuteAudioResponse muteAudio
	    (
            com.vidyo.ws.federation.MuteAudioRequest muteAudioRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        MuteAudioResponse resp = new MuteAudioResponse();
        String endpointGUID = muteAudioRequest.getEndpointGUID();

        try {
            this.federationConferenceService.receiveMuteAudioForParticipantInFederation(endpointGUID);
        } catch (MuteAudioException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Unmute the audio for a given Endpoint GUID in the conference.
     *
     * @param unmuteAudioRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.UnmuteAudioResponse unmuteAudio
	    (
            com.vidyo.ws.federation.UnmuteAudioRequest unmuteAudioRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        UnmuteAudioResponse resp = new UnmuteAudioResponse();
        String endpointGUID = unmuteAudioRequest.getEndpointGUID();

        try {
            this.federationConferenceService.receiveUnmuteAudioForParticipantInFederation(endpointGUID);
        } catch (UnmuteAudioException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
        }

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Stop the video for a given Endpoint GUID in the conference.
     *
     * @param stopVideoRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.StopVideoResponse stopVideo
	    (
            com.vidyo.ws.federation.StopVideoRequest stopVideoRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        StopVideoResponse resp = new StopVideoResponse();
        String endpointGUID = stopVideoRequest.getEndpointGUID();

        try {
            this.federationConferenceService.receiveStopVideoForParticipantInFederation(endpointGUID);
        } catch (StopVideoException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
        }

        resp.setStatus(Status_type0.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Start the video for a given Endpoint GUID in the conference.
     *
     * @param startVideoRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.federation.StartVideoResponse startVideo
	    (
            com.vidyo.ws.federation.StartVideoRequest startVideoRequest
	    )
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
    {
        StartVideoResponse resp = new StartVideoResponse();
        String endpointGUID = startVideoRequest.getEndpointGUID();

        try {
            this.federationConferenceService.receiveStartVideoForParticipantInFederation(endpointGUID);
        } catch (StartVideoException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
        }

        resp.setStatus(Status_type0.OK);
        return resp;
    }

	/**
	 * Auto generated method signature
	 * Silence the audio for a given Endpoint GUID in the conference.
	 * @param silenceAudioRequest:
	 * @return silenceAudioResponse:
	 * @throws InvalidArgumentFaultException:
	 * @throws GeneralFaultException:
	 */
	public com.vidyo.ws.federation.SilenceAudioResponse silenceAudio
		(
			com.vidyo.ws.federation.SilenceAudioRequest silenceAudioRequest
		)
		throws
		InvalidArgumentFaultException,
		GeneralFaultException
	{
		SilenceAudioResponse resp = new SilenceAudioResponse();
		String endpointGUID = silenceAudioRequest.getEndpointGUID();

		try {
			this.federationConferenceService.receiveSilenceAudioForParticipantInFederation(endpointGUID);
		} catch (SilenceAudioException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setStatus(Status_type0.OK);
		return resp;
	}
	
	/**
	 * Auto generated method signature
	 * Silence the video for a given Endpoint GUID in the conference.
	 * @param silenceVideoRequest
	 * @throws InvalidArgumentFaultException : 
	 * @throws GeneralFaultException : 
	 */
	public SilenceVideoResponse silenceVideo(SilenceVideoRequest silenceVideoRequest)
			throws InvalidArgumentFaultException,GeneralFaultException {
		
		SilenceVideoResponse resp = new SilenceVideoResponse();
		String endpointGUID = silenceVideoRequest.getEndpointGUID();

		try {
			this.federationConferenceService.receiveSilenceVideoForParticipantInFederation(endpointGUID);
		} catch (SilenceVideoException e) {
			GeneralFault fault = new GeneralFault();
			logger.error(e.getMessage());
			fault.setErrorMessage(e.getMessage());
			GeneralFaultException exception = new GeneralFaultException(e.getMessage());
			exception.setFaultMessage(fault);
			throw exception;
		}

		resp.setStatus(Status_type0.OK);
		return resp;

	}


}
    
