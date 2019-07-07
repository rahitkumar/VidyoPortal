package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.conference.Conference;
import com.vidyo.service.exceptions.*;
import com.vidyo.service.interportal.InterPortalConferenceService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.ws.federation.*;
import com.vidyo.ws.manager.*;
import com.vidyo.ws.manager.GeneralFaultException;
import com.vidyo.ws.manager.InvalidArgumentFaultException;
import com.vidyo.ws.manager.Status_type0;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.rmi.RemoteException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FederationConferenceServiceImpl extends ConferenceServiceImpl implements IFederationConferenceService {

    /**
     * Logger for this class and subclasses
     */
    protected final Logger logger = LoggerFactory.getLogger(FederationConferenceServiceImpl.class.getName());

    private InterPortalConferenceService interPortalConferenceService;

	public void setInterPortalConferenceService(InterPortalConferenceService interPortalConferenceService) {
		this.interPortalConferenceService = interPortalConferenceService;
	}

	/**
	 * Supports only Vidyo Endpoints and legacy endpoints won't work for IPC.
	 * 
	 */
    public void joinRemoteConference(int memberID, String remoteUserName, String remoteTenantHost, String pin, boolean secure)
            throws RemoteException, OutOfPortsException, JoinConferenceException, FederationNotAllowedException, UserNotFoundException, WrongPinException, ConferenceLockedException, ResourceNotAvailableException
    {
    	logger.debug("Entering joinRemoteConf memberId - {}, remoteUserName - {}, remoteTenantHost - {}, pin - {}", memberID, remoteUserName, remoteTenantHost, pin);
		// Check for router pool configuration
		Configuration configuration = system.getConfiguration("IPC_ROUTER_POOL");
		/*if (configuration == null) {
			throw new ResourceNotAvailableException( "RouterPool not Configured/Assigned");
		}*/

        Member member = this.member.getMember(memberID);
        if(member == null) {
        	String errMsg = "joinRemoteConference failed. Incorrect memberID : " + memberID;
        	logger.error(errMsg);
        	throw new JoinConferenceException(errMsg);
        }
        
        int tenantID = member.getTenantID();
        Tenant tenant = this.tenant.getTenant(tenantID);
		boolean isIpcAllowed = interPortalConferenceService.isInterPortalConferencingAllowed(tenant.getTenantID(), remoteTenantHost, true);
		if (!isIpcAllowed) {
			logger.warn("IPC not allowed for this Tenant " + tenantID + " for RemoteHost -" + remoteTenantHost);
			throw new FederationNotAllowedException();
		}

        // Check for free ports		
        String endpointType = "D";
        checkRoleAndValidateFreePorts(member);

        Federation federation = new Federation();
        ExternalLink externalLink = new ExternalLink();

        String conferenceName = "";
        Group room_group = new Group();
        boolean extLinkCreated = false;
        // STEP 1 - send WS call to remote host
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            JoinRemoteConferenceRequest req = new JoinRemoteConferenceRequest();
            // Request id is generated for each participant who is joining, it'll be overwritten by the requestId in the JoinRemoteConfResponse
            String requestID = PortalUtils.generateNumericKey(10);
            // we don't know what is the conferenceName on the RemoteHost or Remote ToSystemId
            req.setRequestID(requestID);
                federation.setRequestID(requestID);
                externalLink.setRequestID(requestID);
            // FromSystemId, ToSystemId, ToConferenceName - unique conference.
            req.setFromUserName(member.getUsername());
                federation.setFromUser(member.getUsername());
            req.setFromTenantHost(tenant.getTenantURL());
                externalLink.setFromTenantHost(tenant.getTenantURL());
            req.setToUserName(remoteUserName);
                federation.setToUser(remoteUserName);
            req.setToTenantHost(remoteTenantHost);
                externalLink.setToTenantHost(remoteTenantHost);
            String fromSystemID = this.system.getVidyoManagerID();
            req.setFromSystemID(fromSystemID);
                externalLink.setFromSystemID(fromSystemID);
            if (!pin.equalsIgnoreCase("")) {
                req.setPin(pin);
            }
            
            JoinRemoteConferenceResponse resp = stub.joinRemoteConference(req);
            // Throw error if the conference cannot be created in host portal
            if (resp != null && resp.getStatus().equals(com.vidyo.ws.federation.Status_type0.NO)) {
                throw new JoinConferenceException("Conference cannot be created");
            }
            
            if (resp != null) {
                String toSystemID = resp.getToSystemID();
                    externalLink.setToSystemID(toSystemID);
                conferenceName = resp.getToConferenceName();
                    externalLink.setToConferenceName(conferenceName);
                    externalLink.setFromConferenceName(conferenceName);

                room_group.setRoomMaxUsers(Integer.valueOf(resp.getRoomMaxUsers()));
                room_group.setUserMaxBandWidthIn(Integer.valueOf(resp.getUserMaxBandWidthIn()));
                room_group.setUserMaxBandWidthOut(Integer.valueOf(resp.getUserMaxBandWidthOut()));

                externalLink.setRequestID(resp.getRequestID());
                if(secure) {
                	externalLink.setSecure("Y");
                }else{
                	externalLink.setSecure("N");
                }
                extLinkCreated = getFederationDao().updateFromSystemIpc(federation, externalLink);
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new JoinConferenceException(e.getFaultMessage().getErrorMessage());
        } catch (TenantNotFoundFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new JoinConferenceException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new JoinConferenceException(e.getFaultMessage().getErrorMessage());
        } catch (UserNotFoundFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new UserNotFoundException(e.getFaultMessage().getErrorMessage());
        } catch (WrongPinFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new WrongPinException(e.getFaultMessage().getErrorMessage());
        } catch (FederationNotAllowedFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new FederationNotAllowedException(e.getFaultMessage().getErrorMessage());
        } catch (ConferenceLockedFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new ConferenceLockedException(e.getFaultMessage().getErrorMessage());
        } finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }

        // STEP 2 - create local conference with the same name as on remote host
        String uniqueCallId = null;
        if (!conferenceName.equalsIgnoreCase("")) {
            try {
            	uniqueCallId = createConferenceForFederation(conferenceName, room_group);
            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException(e.getMessage());
            } catch (ConferenceNotExistException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException(e.getMessage());
            } catch (NotLicensedException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException(e.getMessage());
            } catch (ResourceNotAvailableException e) {
                logger.error(e.getMessage());
                throw new JoinConferenceException(e.getMessage());
            }
        }
        
        externalLink.setUniqueCallID(uniqueCallId);
        
		// STEP 3 - If external link is inserted in the prev call, instruct VM
		// to create ExternalLink
        logger.debug("External Link Created - {}", extLinkCreated);
		if (extLinkCreated) {
			getFederationDao().updateExternalLinkUniqueId(
					externalLink.getToSystemID(),
					externalLink.getToConferenceName(), uniqueCallId);
			try {
				createExternalLinkFromPortal(externalLink);
			} catch (NoVidyoManagerException e) {
				logger.error(e.getMessage());
				getFederationDao().deleteFederation(externalLink);
				throw new JoinConferenceException(e.getMessage());
			}
		}

        // STEP 4 - add local endpoint to the federated conference
        try {
        	//TODO - use the addEndpointToConference API - refactoring required.
            this.addEndpointToConferenceForFederation(conferenceName, room_group, member, endpointType);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        } catch (EndpointNotExistException e) {
            updateEndpointStatus(member.getEndpointGUID(), "Offline", "portal", null, -1l, null, null);
            logger.error(e.getMessage());
            throw new JoinConferenceException(e.getMessage());
        }
    }

    public Conference createConferenceForFederation(Room room, Tenant tenant, boolean checkFreePorts)
            throws OutOfPortsException
    {
        /*if (checkFreePorts) { // check free ports
		    int totalPorts = tenant.getPorts();
		    int usedPorts = this.tenant.getUsedNumOfPorts(tenant.getTenantID());
		    int freePorts = totalPorts - usedPorts;
		    if (freePorts == 0) {
		        throw new OutOfPortsException();
		    }
        }*/

        Group room_group = this.group.getGroup(room.getGroupID());
        String uniqueCallId = null;

        String tenantUrl = this.tenant.getTenant(room.getTenantName()).getTenantURL();
        String confName = generateConferenceName(room, tenantUrl);

        try {
        	uniqueCallId = createConference(confName, room_group.getRoomMaxUsers());
        } catch (NoVidyoManagerException e) {
            throw new OutOfPortsException();
        } catch (ConferenceNotExistException e) {
            throw new OutOfPortsException();
        } catch (NotLicensedException e) {
            throw new OutOfPortsException();
        } catch (ResourceNotAvailableException e) {
            throw new OutOfPortsException();
        }

	    Conference conference = new Conference();
	    conference.setConferenceName(confName);
	    conference.setUniqueCallId(uniqueCallId);
	    return conference;
    }

    public void createExternalLink(ExternalLink externalLink)
            throws NoVidyoManagerException
    {
        String mySystemID = this.system.getVidyoManagerID();
        VidyoManagerServiceStub stub = null;
        try {
            stub = this.system.getVidyoManagerServiceStubWithAUTH();

            CreateExternalLinkRequest req = new CreateExternalLinkRequest();

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

			req.setRemoteMediaAddress(externalLink.getRemoteMediaAddress());
			req.setRemoteMediaAdditionalInfo(externalLink.getRemoteMediaAdditionalInfo());
			
			Configuration config = system.getConfiguration("IPC_ROUTER_POOL");
			if(config != null) {
				req.setRouterPool(config.getConfigurationValue());
			}
			logger.debug("Create External Link Request to VM {}", ReflectionToStringBuilder.toString(req, ToStringStyle.MULTI_LINE_STYLE));
            CreateExternalLinkResponse resp =  stub.createExternalLink(req);

            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (!status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
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
    }

    
	public void createExternalLinkFromPortal(ExternalLink externalLink)
			throws NoVidyoManagerException {
		 logger.debug("Entering createExternalLinkFromPortal  ExternalLink - {}", ReflectionToStringBuilder.toString(externalLink, ToStringStyle.MULTI_LINE_STYLE));
        VidyoManagerServiceStub stub = null;
		try {
            stub = this.system
					.getVidyoManagerServiceStubWithAUTH();

			CreateExternalLinkRequest req = new CreateExternalLinkRequest();
			// my portal want to join federated conference
			req.setLocalConferenceID(externalLink.getFromConferenceName());
			req.setRemoteConferenceID(externalLink.getToConferenceName());
			req.setRemoteEntityID(externalLink.getToSystemID());

			Configuration config = system.getConfiguration("IPC_ROUTER_POOL");
			if (config != null) {
				req.setRouterPool(config.getConfigurationValue());
			}
			logger.debug("Create External Link FromPortal Request to VM {}", ReflectionToStringBuilder.toString(req, ToStringStyle.MULTI_LINE_STYLE));
			
			CreateExternalLinkResponse resp = stub.createExternalLink(req);

			if (resp != null) {
				String status = resp.getStatus().getValue();
				if (!status.equalsIgnoreCase(Status_type0._OK)) {
					logger.error("ExternalLink not created in VidyoManager - ", status);
				}
			}

		} catch (InvalidArgumentFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new NoVidyoManagerException(e.getFaultMessage()
					.getErrorMessage());
		} catch (GeneralFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new NoVidyoManagerException(e.getFaultMessage()
					.getErrorMessage());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			throw new NoVidyoManagerException(e.getMessage());
		} catch (NotLicensedFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new NoVidyoManagerException(e.getFaultMessage()
					.getErrorMessage());
		} catch (ResourceNotAvailableFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new NoVidyoManagerException(e.getFaultMessage()
					.getErrorMessage());
		} catch (EndpointNotExistFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new NoVidyoManagerException(e.getFaultMessage()
					.getErrorMessage());
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
    
        public void allowExternalLink(ExternalLink externalLink)
                throws NoVidyoManagerException
        {
            VidyoManagerServiceStub stub = null;
            try {
                stub = this.system.getVidyoManagerServiceStubWithAUTH();

                AllowExternalLinkRequest req = new AllowExternalLinkRequest();
                req.setLocalConferenceID(externalLink.getToConferenceName());
                req.setRemoteConferenceID(externalLink.getFromConferenceName());
                req.setRemoteEntityID(externalLink.getFromSystemID());
                // MediaInfo contain info about remote party
                req.setRemoteMediaAddress(externalLink.getRemoteMediaAddress());
                req.setRemoteMediaAdditionalInfo(externalLink.getRemoteMediaAdditionalInfo());
                
                logger.debug("Allow External Link Request to VM {}", ReflectionToStringBuilder.toString(req, ToStringStyle.MULTI_LINE_STYLE));

                AllowExternalLinkResponse resp =  stub.allowExternalLink(req);

                if (resp != null) {
                    String status = resp.getStatus().getValue();
                    if (!status.equalsIgnoreCase(Status_type0._OK)) {
                        // ignore for now
                    }
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
        }

        private void connectExternalLink(ExternalLink externalLink)
                throws NoVidyoManagerException
        {
            VidyoManagerServiceStub stub = null;
            try {
                stub = this.system.getVidyoManagerServiceStubWithAUTH();

                ConnectExternalLinkRequest req = new ConnectExternalLinkRequest();
                req.setLocalConferenceID(externalLink.getFromConferenceName());
                req.setRemoteConferenceID(externalLink.getToConferenceName());
                req.setRemoteEntityID(externalLink.getToSystemID());
                // MediaInfo contain info about remote party
                req.setRemoteMediaAddress(externalLink.getRemoteMediaAddress());
                req.setRemoteMediaAdditionalInfo(externalLink.getRemoteMediaAdditionalInfo());

                ConnectExternalLinkResponse resp =  stub.connectExternalLink(req);

                if (resp != null) {
                    String status = resp.getStatus().getValue();
                    if (!status.equalsIgnoreCase(Status_type0._OK)) {
                        // ignore for now
                    }
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
        }



    public ExternalLink checkAndRemoveExternalLink(FederationConferenceRecord federationConferenceRecord)
    {
    	logger.debug("Entering checkAndRemoveExternalLink(). ConfName = " + federationConferenceRecord.getConferenceName());
    	ExternalLink externalLink = null;

        Long count = this.dao.getCountParticipants(federationConferenceRecord.getConferenceName());

        if (count == 1l) { // Last Endpoint in conference
            logger.debug("checkAndRemoveExternalLink(). Last Endpoint in conference");
            externalLink = getFederationDao().getExternalLinkByConferenceName(federationConferenceRecord.getConferenceName());
            try {
            	logger.debug("checkAndRemoveExternalLink(). removeExternalLink");
                removeExternalLink(externalLink);
            } catch (Exception ignored) {
            	logger.debug("checkAndRemoveExternalLink(). Ignore failing  removeExternalLink: " + ignored.getMessage());
            }
            getFederationDao().deleteFederation(externalLink);
            getFederationDao().deleteFederationConferenceRecord(federationConferenceRecord.getEndpointGUID());
        }
        
        logger.debug("Exiting checkAndRemoveExternalLink(). ConfName = " + federationConferenceRecord.getConferenceName());

        return externalLink;
    }

    public void sendMediaInfoToRemote(ExternalLink externalLink, boolean secure)
            throws RemoteException, ConferenceNotExistException
    {
    	logger.debug("Entering sendMediaInfoToRemote(). externalLinkID = " + externalLink.getExternalLinkID() + "; secure = " + secure);
        Assert.notNull(externalLink.getLocalMediaAddress(), "localMediaAddress must be set");
        Assert.notNull(externalLink.getLocalMediaAdditionalInfo(), "localMediaAdditionalInfo must be set");
        logger.debug("externalLink.getLocalMediaAddress() = " + externalLink.getLocalMediaAddress());
        logger.debug("externalLink.getLocalMediaAdditionalInfo() = " + externalLink.getLocalMediaAdditionalInfo());

        String mySystemID = this.system.getVidyoManagerID();

        String remoteTenantHost = "";
        if (mySystemID.equalsIgnoreCase(externalLink.getFromSystemID())) {
            // my portal want to join federated conference - so remote portal url is toTenantHost
            remoteTenantHost = externalLink.getToTenantHost();
        } else {
            // my portal is host of federated conference - so remote portal url is FromTenantHost
            remoteTenantHost = externalLink.getFromTenantHost();
        }

        // send MediaInfo to remote side
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            ExchangeMediaInfoRequest req = new ExchangeMediaInfoRequest();

            req.setRequestID(externalLink.getRequestID());
            req.setFromConferenceName(externalLink.getFromConferenceName());
            req.setToConferenceName(externalLink.getToConferenceName());
            req.setMediaAddress(externalLink.getLocalMediaAddress());
            req.setMediaAdditionalInfo(externalLink.getLocalMediaAdditionalInfo());

            logger.debug("sendMediaInfoToRemote(). exchangeMediaInfo");
            ExchangeMediaInfoResponse resp = stub.exchangeMediaInfo(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                    logger.debug("sendMediaInfoToRemote(). exchangeMediaInfo = OK");
                } else {
                	//toPortal clean up handling
                	if(!mySystemID.equalsIgnoreCase(externalLink.getFromSystemID())) {
                		logger.debug("sendMediaInfoToRemote(). toPortal clean up handling");
                    	try {
    						removeExternalLink(externalLink);
    					} catch (NoVidyoManagerException e) {
    						throw new RemoteException(e.getMessage());
    					}
                    	
                    	logger.debug("sendMediaInfoToRemote(). deleteFederation externalLinkID = " + externalLink.getExternalLinkID());
    					getFederationDao().deleteFederation(externalLink);
    					// Delete the conference if this is the last one
    					long count = getCountParticipants(externalLink.getToConferenceName());
    					
    					logger.debug("sendMediaInfoToRemote(). count = " + count);
    					if(count == 0) {
    						deleteConference(externalLink.getToConferenceName());
    					}
                	}

                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new ConferenceNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new ConferenceNotExistException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoManagerException e) {
        	logger.error(e.getMessage());
		} catch (NotLicensedException e) {
			logger.error(e.getMessage());
		} catch (ResourceNotAvailableException e) {
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


    public void linkMediaWithRemote(ExternalLink externalLink)
            throws ConferenceNotExistException
    {
        String mySystemID = this.system.getVidyoManagerID();

        // Depending on portal systemID do connect or allow
        if (mySystemID.equalsIgnoreCase(externalLink.getFromSystemID())) {
            // my portal want to join federated conference - so call ConnectExternalLink
            try {
                connectExternalLink(externalLink);
            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                String msg = "Cannot connect External Link for externalLinkID=" + externalLink.getExternalLinkID();
                logger.error(msg);
                throw new ConferenceNotExistException(msg);
            }
        } else {
            // my portal is host of federated conference - instruct VM to CreateExternalLink with remote media info
            try {
                createExternalLink(externalLink);
            } catch (NoVidyoManagerException e) {
                logger.error(e.getMessage());
                String msg = "Cannot create External Link on host portal for externalLinkID=" + externalLink.getExternalLinkID();
                logger.error(msg);
                throw new ConferenceNotExistException(msg);
            }
        }
    }

    public void unlinkMediaWithRemote(ExternalLink externalLink, boolean secure)
            throws RemoteException, ConferenceNotExistException
    {
        String mySystemID = this.system.getVidyoManagerID();

        String remoteTenantHost = "";
        if (mySystemID.equalsIgnoreCase(externalLink.getFromSystemID())) {
            // my portal want to join federated conference - so remote portal url is toTenantHost
            remoteTenantHost = externalLink.getToTenantHost();
        } else {
            // my portal is host of federated conference - so remote portal url is FromTenantHost
            remoteTenantHost = externalLink.getFromTenantHost();
        }
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            DropRemoteConferenceRequest req = new DropRemoteConferenceRequest();

            req.setFromSystemID(externalLink.getFromSystemID());
            req.setFromConferenceName(externalLink.getFromConferenceName());
            req.setToSystemID(externalLink.getToSystemID());
            req.setToConferenceName(externalLink.getToConferenceName());

            DropRemoteConferenceResponse resp = stub.dropRemoteConference(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                    this.removeRecordForFederation(externalLink);
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new ConferenceNotExistException();
*/
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



    public List<Control> getParticipantsForFederation(String conferenceName, ControlFilter filter) {
        List<Control> list = getFederationDao().getParticipantsForFederation(conferenceName, filter);
        return list;
    }

    public void leaveTheConferenceForFederation(String GUID, String conferenceName)
            throws LeaveConferenceException
    {
        // Convert into fake Member object
        Member member = new Member();
        member.setEndpointGUID(GUID);

        try {

            this.removeEndpointFromConferenceForFederation(conferenceName, member);

        } catch (NoVidyoManagerException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        } catch (ConferenceNotExistException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        } catch (NotLicensedException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        } catch (ResourceNotAvailableException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        } catch (EndpointNotExistException e) {
            updateEndpointStatus(member.getEndpointGUID(), "Offline", "portal", null, -1l, null, null);
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        }
    }

    // Service methods
    public String createRecordForFederation(Federation federation, ExternalLink externalLink) {
        return getFederationDao().updateToSystemIpc(federation, externalLink);
    }

    public void removeRecordForFederation(ExternalLink externalLink) {
        String conferenceName = externalLink.getFromConferenceName();

        List<Control> list = this.getParticipantsForFederation(conferenceName, null);
        for (Control p : list) {
            try {
                this.leaveTheConferenceForFederation(p.getEndpointGUID(), conferenceName);
                getFederationDao().deleteFederationConferenceRecord(p.getEndpointGUID());
            } catch (LeaveConferenceException e) {
                logger.error(e.getMessage());
            }
        }

        Control control = new Control();
        control.setConferenceName(conferenceName);

        try {
            checkAndDeleteTheConference(control);
        } catch (DeleteConferenceException e) {
            logger.error(e.getMessage());
        }

        getFederationDao().deleteFederation(externalLink);
    }

    public ExternalLink getExternalLinkForUpdateStatus(String remoteSystemID, String fromConferenceName, String toConferenceName) {
        ExternalLink rc = getFederationDao().getExternalLinkForUpdateStatus(remoteSystemID, fromConferenceName, toConferenceName);
        return rc;
    }

    public ExternalLink getExternalLinkByRequestID(String requestID) {
        ExternalLink rc = getFederationDao().getExternalLinkByRequestID(requestID);
        return rc;
    }
    
    public ExternalLink getExternalLink(String requestID, String fromConfName, String toConfName) {
        ExternalLink rc = getFederationDao().getExternalLink(requestID, fromConfName, toConfName);
        return rc;
    }    

    public ExternalLink getExternalLinkByRemoteMedia(String remoteMediaAddress, String remoteMediaAdditionalInfo) {
        ExternalLink rc = getFederationDao().getExternalLinkByRemoteMedia(remoteMediaAddress, remoteMediaAdditionalInfo);
        return rc;
    }

    public int updateExternalLinkForMediaInfo(int externalLinkID, String requestID, String mediaAddress, String mediaAdditionalInfo) {
        return getFederationDao().updateExternalLinkForMediaInfo(externalLinkID, requestID, mediaAddress, mediaAdditionalInfo);
    }

    public int updateExternalLinkForLocalMediaInfo(int externalLinkID, String mediaAddress, String mediaAdditionalInfo) {
        return getFederationDao().updateExternalLinkForLocalMediaInfo(externalLinkID, mediaAddress, mediaAdditionalInfo);
    }

    public int updateExternalLinkForStatus(int externalLinkID, int status) {
        return getFederationDao().updateExternalLinkForStatus(externalLinkID, status);
    }

    public int insertFederationConferenceRecord(FederationConferenceRecord federationConferenceRecord) {
        return getFederationDao().insertFederationConferenceRecord(federationConferenceRecord);
    }

    public int deleteFederationConferenceRecord(String endpointGUID) {
        return getFederationDao().deleteFederationConferenceRecord(endpointGUID);
    }

    public void sendDisconnectParticipantFromFederation(int endpointID, int roomID, boolean secure)
        throws RemoteException, LeaveConferenceException
    {
        String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

        String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        String conferenceName = getFederationDao().getFederationConferenceName(roomID);
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            DisconnectEndpointFromHostRequest req = new DisconnectEndpointFromHostRequest();
            req.setConferenceName(conferenceName);
            req.setEndpointID(String.valueOf(endpointID));
            req.setEndpointGUID(endpointGUID);

            DisconnectEndpointFromHostResponse resp = stub.disconnectEndpointFromHost(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new LeaveConferenceException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new LeaveConferenceException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new LeaveConferenceException(e.getFaultMessage().getErrorMessage());
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

    public void receiveDisconnectParticipantFromFederation(String endpointGUID, String conferenceName)
            throws LeaveConferenceException
    {
        this.leaveTheConferenceForFederation(endpointGUID, conferenceName);
    }

    public void sendMuteAudioForParticipantInFederation(int endpointID, boolean secure)
        throws RemoteException, MuteAudioException
    {
        String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

        String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            MuteAudioRequest req = new MuteAudioRequest();
            req.setEndpointGUID(endpointGUID);

            MuteAudioResponse resp = stub.muteAudio(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new MuteAudioException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new MuteAudioException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new MuteAudioException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new MuteAudioException(e.getFaultMessage().getErrorMessage());
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

    public void receiveMuteAudioForParticipantInFederation(String endpointGUID)
            throws MuteAudioException
    {
        try {
            this.muteAudio(endpointGUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new MuteAudioException(e.getMessage());
        }
    }

    public void sendUnmuteAudioForParticipantInFederation(int endpointID, boolean secure)
        throws RemoteException, UnmuteAudioException
    {
        String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

        String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            UnmuteAudioRequest req = new UnmuteAudioRequest();
            req.setEndpointGUID(endpointGUID);

            UnmuteAudioResponse resp = stub.unmuteAudio(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new UnmuteAudioException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new UnmuteAudioException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new UnmuteAudioException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new UnmuteAudioException(e.getFaultMessage().getErrorMessage());
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

    public void receiveUnmuteAudioForParticipantInFederation(String endpointGUID)
            throws UnmuteAudioException
    {
        try {
            this.unmuteAudio(endpointGUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UnmuteAudioException(e.getMessage());
        }
    }

    public void sendStopVideoForParticipantInFederation(int endpointID, boolean secure)
        throws RemoteException, StopVideoException
    {
        String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

        String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            StopVideoRequest req = new StopVideoRequest();
            req.setEndpointGUID(endpointGUID);

            StopVideoResponse resp = stub.stopVideo(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new StopVideoException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new StopVideoException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new StopVideoException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new StopVideoException(e.getFaultMessage().getErrorMessage());
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

    public void receiveStopVideoForParticipantInFederation(String endpointGUID)
            throws StopVideoException
    {
        try {
            this.stopVideo(endpointGUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new StopVideoException(e.getMessage());
        }
    }

    public void sendStartVideoForParticipantInFederation(int endpointID, boolean secure)
        throws RemoteException, StartVideoException
    {
        String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

        String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
        try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

            StartVideoRequest req = new StartVideoRequest();
            req.setEndpointGUID(endpointGUID);

            StartVideoResponse resp = stub.startVideo(req);
            if (resp != null) {
                String status = resp.getStatus().getValue();
                if (status.equalsIgnoreCase(Status_type0._OK)) {
                    // ignore for now
                }
            }

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new StartVideoException();
*/
        } catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new StartVideoException(e.getFaultMessage().getErrorMessage());
        } catch (NoVidyoFederationException e) {
            logger.error(e.getMessage());
            throw new StartVideoException(e.getMessage());
        } catch (com.vidyo.ws.federation.GeneralFaultException e) {
            logger.error(e.getFaultMessage().getErrorMessage());
            throw new StartVideoException(e.getFaultMessage().getErrorMessage());
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

    public void receiveStartVideoForParticipantInFederation(String endpointGUID)
            throws StartVideoException
    {
        try {
            this.startVideo(endpointGUID);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new StartVideoException(e.getMessage());
        }
    }

	public void sendSilenceAudioForParticipantInFederation(int endpointID, boolean secure)
			throws RemoteException, SilenceAudioException
	{
		String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

		String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
		try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

			SilenceAudioRequest req = new SilenceAudioRequest();
			req.setEndpointGUID(endpointGUID);

			SilenceAudioResponse resp = stub.silenceAudio(req);
			if (resp != null) {
				String status = resp.getStatus().getValue();
				if (status.equalsIgnoreCase(Status_type0._OK)) {
					// ignore for now
				}
			}

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new MuteAudioException();
*/
		} catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new SilenceAudioException(e.getFaultMessage().getErrorMessage());
		} catch (NoVidyoFederationException e) {
			logger.error(e.getMessage());
			throw new SilenceAudioException(e.getMessage());
		} catch (com.vidyo.ws.federation.GeneralFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new SilenceAudioException(e.getFaultMessage().getErrorMessage());
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

	public void receiveSilenceAudioForParticipantInFederation(String endpointGUID)
			throws SilenceAudioException
	{
		try {
			this.silenceAudio(endpointGUID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SilenceAudioException(e.getMessage());
		}
	}
	
	public void sendSilenceVideoForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, SilenceVideoException {
		
		String remoteTenantHost = getFederationDao().getFederationFromTenantHostForEndpointID(endpointID);

		String endpointGUID = getFederationDao().getFederationEndpointGUID(endpointID);
        VidyoFederationServiceStub stub = null;
		try {
            stub = this.system.getVidyoFederationServiceStubWithAUTH(remoteTenantHost, secure);

			SilenceVideoRequest req = new SilenceVideoRequest();
			req.setEndpointGUID(endpointGUID);

			SilenceVideoResponse resp = stub.silenceVideo(req);
			if (resp != null) {
				String status = resp.getStatus().getValue();
				if (status.equalsIgnoreCase(Status_type0._OK)) {
					// ignore for now
				}
			}

/*
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new MuteAudioException();
*/
		} catch (com.vidyo.ws.federation.InvalidArgumentFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new SilenceVideoException(e.getFaultMessage().getErrorMessage());
		} catch (NoVidyoFederationException e) {
			logger.error(e.getMessage());
			throw new SilenceVideoException(e.getMessage());
		} catch (com.vidyo.ws.federation.GeneralFaultException e) {
			logger.error(e.getFaultMessage().getErrorMessage());
			throw new SilenceVideoException(e.getFaultMessage().getErrorMessage());
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
	
	public void receiveSilenceVideoForParticipantInFederation(String endpointGUID) throws SilenceVideoException {
		try {
			this.silenceVideo(endpointGUID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SilenceVideoException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param externalLink
	 * @return
	 */
    public int deleteFederation(ExternalLink externalLink) {
    	logger.debug("Entering/exiting deleteFederation(). externalLinkID = " + externalLink.getExternalLinkID());
    	return getFederationDao().deleteFederation(externalLink);
    }
    
	/**
	 * 
	 * @param externalLink
	 */
	public void dropRemoteConference(ExternalLink externalLink) {
		logger.debug("Entering dropRemoteConference(). externalLinkID = " + externalLink.getExternalLinkID());
		// Delete the table entries
		deleteFederation(externalLink);
		try {
			removeExternalLink(externalLink);
		} catch (NoVidyoManagerException e) {
			logger.error(e.getMessage());
		}
		// Delete the conference if this is the last one
		long count = getCountParticipants(externalLink.getToConferenceName());
		logger.debug("dropRemoteConference(). ParticipantsCount = " + count);

		if (count == 0) {
			try {
				deleteConference(externalLink.getToConferenceName());
			} catch (Exception e) {
				logger.error("Exception while dropping remote conference ->"
						+ externalLink.getToConferenceName(), e.getMessage());
			}
		}
		logger.debug("Exiting dropRemoteConference(). externalLinkID = " + externalLink.getExternalLinkID());
	}
	
	/**
	 * Returns the list of ExternalLinks remaining with specific status for a
	 * specific period of time
	 * 
	 * @param timeLapsed
	 * @return
	 */
	public List<ExternalLink> getDisconnectedExternalLinks(int status,
			int timeLapsed) {
		List<ExternalLink> externalLinks = getFederationDao()
				.getDisconnectedExternalLinks(status, timeLapsed);

		return externalLinks;
	}
	
	/**
	 * Returns ExternalLinks count by toConferenceName
	 * 
	 * @param toConfName
	 * @return
	 */
	public int getExtLinksCountByToConfName(String toConfName) {
		int count = getFederationDao().getExtLinksCountByToConfName(toConfName);
		return count;
	}
	
	/**
	 * Deletes the Conference in VidyoManager.
	 * 
	 * @param confName
	 * @return
	 */
	public int deleteConfInVidyoManager(String confName) {
		int status = -1;
		try {
			status = deleteConference(confName);
		} catch (Exception e) {
			logger.error(
					"Excepton while deleting the Conference in VidyoManager - ",
					e.getMessage());
		}
		return status;
	}

}
