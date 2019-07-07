package com.vidyo.service.gateway;

import com.vidyo.bo.*;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.rest.gateway.EndpointFeature;
import com.vidyo.rest.gateway.Prefix;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.*;
import com.vidyo.service.gateway.request.JoinFromLegacyServiceRequest;
import com.vidyo.service.gateway.request.RegisterPrefixesServiceRequest;
import com.vidyo.service.gateway.request.SetCdrDataServiceRequest;
import com.vidyo.service.gateway.response.*;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.utils.PortalUtils;
import com.vidyo.vcap20.Message;
import com.vidyo.vcap20.RequestMessage;
import com.vidyo.vcap20.RootDocument;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GatewayServiceImpl implements GatewayService {
	protected final Logger logger = LoggerFactory.getLogger(GatewayServiceImpl.class.getName());

	private IServiceService servicesService;
	private IConferenceService conferenceService;
	private IMemberService memberService;
	private ITenantService tenantService;
	private IRoomService roomService;
	private ConferenceAppService conferenceAppService;
	private EndpointService endpointService;

	private Map<Integer, Integer> gatewayPrefixHistory = new ConcurrentHashMap<Integer, Integer>();

	public void setServicesService(IServiceService servicesService) {
		this.servicesService = servicesService;
	}

	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public void setConferenceAppService(ConferenceAppService conferenceAppService) {
		this.conferenceAppService = conferenceAppService;
	}

	public void setEndpointService(EndpointService endpointService) {
		this.endpointService = endpointService;
	}

	@Override
	public RegisterPrefixesServiceResponse registerPrefixes(RegisterPrefixesServiceRequest request) {

		RegisterPrefixesServiceResponse response = new RegisterPrefixesServiceResponse();

		if (!servicesService.isUseNewGatewayServiceInterface()) {
			response.setStatus(BaseGatewayServiceResponse.FEATURE_NOT_RELEASED);
			return response;
		}

		String gwUser = request.getGatewayUserAccount();
		String gatewayId = request.getGatewayId();
		Set<Prefix> prefixSet = request.getPrefixes();
        Set<EndpointFeature> endpointFeatures = request.getEndpointFeatures(); // TODO: future use
		String serviceToken = request.getServiceToken();
		String serviceEndpointGuid = request.getServiceEndpointGuid();

		Service gwService = getGatewayServiceFromServiceUser(gwUser);
		gwService.setServiceRef(gatewayId);
		gwService.setToken(serviceToken);
		gwService.setServiceEndpointGuid(serviceEndpointGuid);

		int serviceId = gwService.getServiceID();
		this.servicesService.updateVG(serviceId, gwService);

		Integer existingPrefixHash = gatewayPrefixHistory.get(serviceId);
		Integer incomingPrefixHash = prefixSet.hashCode();
		if (existingPrefixHash == null) {
			logger.info("Updating prefixes since no history for serviceID: " + serviceId + " / gatewayID: " + gatewayId);
			gatewayPrefixHistory.put(serviceId, incomingPrefixHash);
			this.servicesService.resetGatewayPrefixes(serviceId, gatewayId, prefixSet);
		} else if (existingPrefixHash.intValue() == incomingPrefixHash.intValue()) {
			if (this.servicesService.getCountGatewayPrefixes(serviceId) == 0) {
				logger.info("Adding missing prefixes for serviceID: " + serviceId + " / gatewayID: " + gatewayId);
				gatewayPrefixHistory.put(serviceId, incomingPrefixHash);
				this.servicesService.resetGatewayPrefixes(serviceId, gatewayId, prefixSet);
			} else {
				logger.info("No changes in prefixes for serviceID: " + serviceId + " / gatewayID: " + gatewayId + " - updating timestamp so batch job does not delete them.");
				this.servicesService.updateGatewayPrefixesTimestamp(serviceId);
			}
		} else {
			logger.info("Updating prefixes since they changed for serviceID: " + serviceId + " / gatewayID: " + gatewayId);
			gatewayPrefixHistory.put(serviceId, incomingPrefixHash);
			this.servicesService.resetGatewayPrefixes(serviceId, gatewayId, prefixSet);
		}

		String vmConnect = "";
		try {
			vmConnect = this.conferenceService.getVMConnectAddress();
			response.setVmConnect(vmConnect);
			response.setStatus(RegisterPrefixesServiceResponse.SUCCESS);
		} catch (NoVidyoManagerException ne) {
			logger.warn("Error communicating with VidyoManager: " + ne.getMessage());
			response.setStatus(RegisterPrefixesServiceResponse.VIDYO_MANAGER_UNAVAILABLE);
		}
		return response;
	}

	@Override
	public GetVMConnectInfoServiceResponse getVmConnect() {

		GetVMConnectInfoServiceResponse response = new GetVMConnectInfoServiceResponse();

		String vmConnect = "";
		try {
			vmConnect = this.conferenceService.getVMConnectAddress();
			response.setVmConnect(vmConnect);
			response.setStatus(GetVMConnectInfoServiceResponse.SUCCESS);
		} catch (NoVidyoManagerException ne) {
			logger.warn("Error communicating with VidyoManager: " + ne.getMessage());
			response.setStatus(GetVMConnectInfoServiceResponse.VIDYO_MANAGER_UNAVAILABLE);
		}
		return response;
	}

	@Override
	public JoinFromLegacyServiceResponse joinFromLegacy(JoinFromLegacyServiceRequest request) {

		JoinFromLegacyServiceResponse response = new JoinFromLegacyServiceResponse();

		if (!servicesService.isUseNewGatewayServiceInterface()) {
			response.setStatus(BaseGatewayServiceResponse.FEATURE_NOT_RELEASED);
			return response;
		}

		String gwUser = request.getGatewayUserAccount();
		String userTenantName = request.getUserTenantName(); // gw will probably never send this
		String gwEndpointGuid = request.getGwEndpointGuid();
		String fromDisplayName = request.getFromDisplayName();
		String dialString = request.getDialString();
		String pin = request.getPin();
		boolean directCallFlag = request.isDirectCallFlag();
		String prefix = request.getPrefix();
		String deviceModel = request.getDeviceModel();
		String endpointPublicIpAddress = request.getEndpointPublicIpAddress();

        // let this thread do its work before updateEndpointStatus thread by setting a file system lock
        // so it is visible across multiple tomcats (db locking doesn't work due to connection pooling)
        /*File lockFile = new File("/home/tomcatnp/VE-" + gwEndpointGuid + ".lock");
        try {
            FileUtils.writeStringToFile(lockFile, "1");
            logger.info("wrote lock file: " + lockFile.getName());
        } catch (IOException ioe) {
            logger.error("Could not create lock file: " + lockFile);
        }*/

		Service gwService = getGatewayServiceFromServiceUser(gwUser);
        int gwServiceTenantId = this.servicesService.getTenantIDforServiceID(gwService.getServiceID());

		int userTenantId = gwServiceTenantId;
		if (userTenantName != null) {
			Tenant tenant = this.tenantService.getTenant(userTenantName);
			if (tenant != null) {
				userTenantId = tenant.getTenantID();
			}
		}

		response.setJoinStatus("FAIL");

        String toExtName = dialString;
        String gwControllerHost = null;
        Room toRoom = null;
        Member toMember = null;

        String[] dialStringParts = dialString.split("@");

        if (dialStringParts.length == 1) {
            // nameOrExtension
            toExtName = dialStringParts[0];

        } else if (dialStringParts.length == 2) {
            //  nameOrExtension@domain
            toExtName = dialStringParts[0];
            gwControllerHost = dialStringParts[1];

        } else if (dialStringParts.length > 2) {
            // d'oh two or more @ chars, ie 3 parts or more
            int lastPart = dialStringParts.length - 1;
            StringBuilder toExtNameBuilder = new StringBuilder();
            for (int i = 0; i < dialStringParts.length - 1; i++) {
                toExtNameBuilder.append(dialStringParts[i]);
                if (i != dialStringParts.length - 2) {
                    toExtNameBuilder.append("@");
                }
            }
            toExtName = toExtNameBuilder.toString();
            gwControllerHost = dialStringParts[lastPart];

        }

        registerVirtualEndpoint(gwEndpointGuid, gwService, fromDisplayName, prefix, gwServiceTenantId);
        logger.info("registration complete for virtual endpoint");
        try {

            if (gwControllerHost == null) {
                // look up name in given tenant OR extension in *every* tenant,extension is unique across all tenants
                toRoom = lookupRoomByNameOrExtension(toExtName, userTenantId, pin);
            } else {
                // look up until you find something, do most specific search first
                toRoom = lookupRoomByNameOrExtensionGwControllerHost(toExtName, pin, gwControllerHost);
                logger.info("looking for toExtName: " + toExtName + " with configured gw controller host: " + gwControllerHost);
                if (toRoom == null) {
                    logger.info("looking for toExtName: " + dialString);
                    // try again with full input (usecase: test@notadomain, lookup "test@notadomain")
                    toRoom = lookupRoomByNameOrExtension(dialString, userTenantId, pin);
                }
                if (toRoom == null) {
                    logger.info("looking for toExtName: " + toExtName);
                    // try again with just the toExtName (usecase: test@domain, lookup "test")
                    toRoom = lookupRoomByNameOrExtension(toExtName, userTenantId, pin);
                }
            }

            // if we don't have a personal room, public room or scheduled room, we have nothing to connect
            if (toRoom == null) {
                logger.info("not found dialString: " + dialString);
                response.setStatus(JoinFromLegacyServiceResponse.INVALID_TO_NAME_EXT);
                return response;
            }

			TenantContext.setTenantId(toRoom.getTenantID());

            // only personal rooms can do P2P, if the user is online
            if (toRoom.getRoomType().equals("Personal") && directCallFlag) {
                toMember = this.memberService.getMember(toRoom.getMemberID());
                String memberEndpointGUID = toMember.getEndpointGUID();
                int status = 0; // offline
                if (!StringUtils.isBlank(memberEndpointGUID)) {
                    status = this.conferenceService.getEndpointStatus(memberEndpointGUID);
                }
                if (status == 1) { // online
                    directCall(gwEndpointGuid, fromDisplayName, toExtName, toRoom.getTenantName());
                    updateGatewayEndpointCDR(gwEndpointGuid, deviceModel, endpointPublicIpAddress);
                    response.setJoinStatus("OK");
                    return response;
                } else {
					if (status == 0) {
						response.setStatus(JoinFromLegacyServiceResponse.P2P_OFFLINE);
					} else {
						response.setStatus(JoinFromLegacyServiceResponse.P2P_BUSY);
					}
                    response.setJoinStatus(getJoinStatusAsString(status));
                    return response;
                }
            }

            // everything else means joining a room
            if (toRoom.getRoomLocked() == 1) {
                response.setStatus(JoinFromLegacyServiceResponse.ROOM_LOCKED);
                return response;
            }
            if (toRoom.getRoomEnabled() != 1) {
                response.setStatus(JoinFromLegacyServiceResponse.ROOM_DISABLED);
                return response;
            }
            if (toRoom.getRoomPIN() != null) {
                if (StringUtils.isBlank(pin)) {
                    response.setStatus(JoinFromLegacyServiceResponse.ROOM_PIN_REQUIRED);
                    return response;
                } else {
                	// Scheduled Room PIN is already validated
                    if (!pin.equalsIgnoreCase(toRoom.getRoomPIN()) && !RoomTypes.SCHEDULED.getType().equalsIgnoreCase(toRoom.getRoomType())) {
                        response.setStatus(JoinFromLegacyServiceResponse.ROOM_PIN_INVALID);
                        return response;
                    }
                }
            }
            logger.info("Joining Room....");
            joinRoom(gwEndpointGuid, fromDisplayName, toRoom);
            updateGatewayEndpointCDR(gwEndpointGuid, deviceModel, endpointPublicIpAddress);
            response.setJoinStatus("OK");
            return response;


        } catch (WrongPinException wpe) {
            response.setStatus(JoinFromLegacyServiceResponse.ROOM_PIN_REQUIRED); // to match prev service return value
            return response;
        } catch (CannotCallTenantP2PException cctp) {
            logger.info("Error connecting incoming legacy call [p2p]. Not permitted to call this tenant.");
            logger.info(ExceptionUtils.getStackTrace(cctp));
            response.setStatus(JoinFromLegacyServiceResponse.CANNOT_CALL_TENANT);
        } catch (CannotCallTenantJoinException cctj) {
            logger.info("Error connecting incoming legacy call [conference]. Not permitted to call this tenant.");
            logger.info(ExceptionUtils.getStackTrace(cctj));
            response.setStatus(JoinFromLegacyServiceResponse.CANNOT_CALL_TENANT);
        } catch (MakeCallException exc) {
            logger.info("Error connecting incoming legacy call [p2p].");
            logger.info(ExceptionUtils.getStackTrace(exc));
            response.setStatus(JoinFromLegacyServiceResponse.FAILED_TO_CONNECT_CALL);
        } catch (RoomFullJoinException rfje) {
            logger.info("Error connecting incoming legacy call [conference]. Room full.");
            logger.info(ExceptionUtils.getStackTrace(rfje));
            response.setStatus(JoinFromLegacyServiceResponse.ROOM_FULL);
        } catch (JoinConferenceException exc) {
            logger.info("Error connecting incoming legacy call [conference].");
            logger.info(ExceptionUtils.getStackTrace(exc));
            response.setStatus(JoinFromLegacyServiceResponse.FAILED_TO_CONNECT_CALL);
        } catch (OutOfPortsException exc) {
            logger.info("Error connecting incoming legacy call [outOfPorts].");
            logger.info(ExceptionUtils.getStackTrace(exc));
            response.setStatus(JoinFromLegacyServiceResponse.NO_LINES_AVAILABLE);
        }  catch (Exception exc) {
            logger.warn("Error connecting incoming legacy call [internal error].");
            logger.warn("joinFromLegacy / GW: [" + gwService.getUser() + "] " + request.toString());
            logger.warn(ExceptionUtils.getStackTrace(exc));
            response.setStatus(JoinFromLegacyServiceResponse.FAILED_TO_CONNECT_CALL);
        } /*finally {
            FileUtils.deleteQuietly(lockFile);
            logger.info("removed lock file: " + lockFile.getName());
        }*/

		return response;
	}

	@Override
	public SetCdrDataServiceResponse setCdrData(SetCdrDataServiceRequest request) {

		SetCdrDataServiceResponse response = new SetCdrDataServiceResponse();

		if (!servicesService.isUseNewGatewayServiceInterface()) {
			response.setStatus(BaseGatewayServiceResponse.FEATURE_NOT_RELEASED);
			return response;
		}

		String gwEndpointGuid = request.getEndpointGuid();
		String deviceModel = request.getDeviceModel();
		String endpointPublicIpAddress = request.getEndpointPublicIpAddress();

		int rowsUpdated = updateGatewayEndpointCDR(gwEndpointGuid, deviceModel, endpointPublicIpAddress);

		response.setRowsUpdated(rowsUpdated);
		response.setStatus(SetCdrDataServiceResponse.SUCCESS);
		return response;
	}

	/**
	 * Get service based on service user account
	 * @param username
	 * @return
	 */
	private Service getGatewayServiceFromServiceUser(String username) {
		Service service = this.servicesService.getServiceByUserName(username,"VidyoGateway");
		return service;
	}


	/**
	 * Checks the Endpoints table to see if endpointGuid is there, and if so, moves it to the
	 * VirtualEndpoints table, since via the gw call we know the type of endpoint. If not found in
	 * any endpoints table, it adds it to the VirtualEndpoints table.
	 *
	 */
	private void registerVirtualEndpoint(String gwEndpointGuid, Service gwService, String fromDisplayName,
										 String prefix, int tenantId) {

		VirtualEndpoint ve = new VirtualEndpoint();
		ve.setEndpointGUID(gwEndpointGuid);
		ve.setServiceID(gwService.getServiceID());
		ve.setGatewayID(gwService.getServiceRef());
		ve.setTenantID(tenantId);
		ve.setPrefix(prefix);
		ve.setDisplayName(fromDisplayName);
		ve.setDirection(0); // fromLegacy

		Endpoint endpoint = this.endpointService.getEndpointDetail(gwEndpointGuid);
		if (endpoint == null) {
			
			//this.servicesService.registerVirtualEndpoint(gwService.getServiceID(), ve);
			// because of other threads we check for existence of GUID in other endpoint tables
			// unnecessary if all endpoints in one table!
			int countLoops =0;
			try {
				logger.info("Checking the loop for Endpoint wait");
				 while ((endpoint = this.endpointService.getEndpointDetail(gwEndpointGuid)) == null 
						 && countLoops < 50) { 	  // don't wait
								                 // more than 1
								                 // seconds
					logger.info("...sleeping until endpoint is not there in DB...." + countLoops);
					Thread.sleep(20);
					countLoops++;
			}
			} catch (InterruptedException e) {
				logger.error("ERROR:"+ e.getMessage());
			}
			//endpoint = this.endpointService.getEndpointDetail(gwEndpointGuid); // check again!
			if (endpoint == null) {
				logger.error("Endpoint not online after waiting for 1 second." + gwEndpointGuid );
			} else if (endpoint != null && "D".equals(endpoint.getEndpointType())) {
                logger.info(" Expecting a VE but found a desktop endpoint: " + endpoint);
				ve.setSequenceNum(endpoint.getSequenceNum());
				ve.setStatus(endpoint.getStatus());
				logger.info("moving endpoint from Endpoints to VirtualEndpoints table: " + gwEndpointGuid );
	            this.servicesService.registerVirtualEndpoint(gwService.getServiceID(), ve);
				this.endpointService.deleteRegularEndpoint(gwEndpointGuid);
			} else {
				logger.error("Endpoint is present and is not of type D" + endpoint.getEndpointType() + " guid-" + gwEndpointGuid);
			}
		} else  if ("D".equals(endpoint.getEndpointType())) {
			logger.info("moving endpoint from Endpoints to VirtualEndpoints table: " + gwEndpointGuid);
			ve.setSequenceNum(endpoint.getSequenceNum());
			ve.setStatus(endpoint.getStatus());
            this.servicesService.registerVirtualEndpoint(gwService.getServiceID(), ve);
			this.endpointService.deleteRegularEndpoint(gwEndpointGuid);
		}
	}

	/**
	 * Provide human readable endpoint status.
	 *
	 */
	private String getJoinStatusAsString(int status) {
		switch (status) {
			case 0 : return "Offline";
			case 1 : return "Online";
			case 2 : return "Busy";
			case 3 : return "Busy"; // Ringing
			case 4 : return "Busy"; // RingAccepted
			case 5 : return "Busy"; // RingRejected
			case 6 : return "Busy"; // RingNoAnswer
			case 7 : return "Busy"; // Alerting
			case 8 : return "Busy"; // AlertCancelled
			case 9 : return "Busy"; // BusyInOwnRoom
			case 10 : return "Busy"; // RingFailed
			case 11 : return "Busy"; // JoinFailed
			case 12 : return "Busy"; // WaitForConfirm
			default: return "Offline";
		}
	}

	/**
	 * Finds the room by name or extension (regular & scheduled rooms).
	 *
	 */
	private Room lookupRoomByNameOrExtension(String roomNameExt, int tenantID, String pin) throws WrongPinException {
		Room room = null;

		ScheduledRoomResponse scheduledRoomResponse = this.conferenceAppService.validateScheduledRoom(roomNameExt, pin);
		if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN) {
			throw new WrongPinException("PIN is empty");
		}

		if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
			room = scheduledRoomResponse.getRoom();
			// override the pin in the request if PIN is applicable
			if (scheduledRoomResponse.getPin() != 0) {
				//room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
			}
		} else {
			room = roomService.getRoomDetailsForConference(roomNameExt, tenantID);
		}

		return room;
	}

    private Room lookupRoomByNameOrExtensionGwControllerHost(String roomName, String pin, String gwControllerHost) throws WrongPinException {
        Room room = null;

        ScheduledRoomResponse scheduledRoomResponse = this.conferenceAppService.validateScheduledRoom(roomName, pin);
        if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN) {
            throw new WrongPinException("PIN is empty");
        }

        if (scheduledRoomResponse.getStatus() == ScheduledRoomResponse.SUCCESS) {
            room = scheduledRoomResponse.getRoom();
            // override the pin in the request if PIN is applicable
            if (scheduledRoomResponse.getPin() != 0) {
                //room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
            }
        } else {
            room = this.roomService.getRoomDetailsForConference(roomName, gwControllerHost);
        }

        return room;
    }

	/**
	 * Do a P2P call.
	 *
	 */
	private void directCall(String gwEndpointGuid, String from, String to, String tenantName)
			throws MakeCallException, OutOfPortsException {

        Endpoint endpoint = this.endpointService.getEndpointDetail(gwEndpointGuid);
        if ("D".equals(endpoint.getEndpointType())) {
            logger.info(" Expecting a VE but found a desktop endpoint: " + endpoint);
            this.endpointService.updateVirtualEndpointStatus(gwEndpointGuid, endpoint.getStatus());
            this.endpointService.deleteRegularEndpoint(gwEndpointGuid);
        }

		this.conferenceService.twoPartyConference(gwEndpointGuid, to, tenantName, from);
	}

	/**
	 * Do a conference call.
	 *
	 */
	private void joinRoom(String gwEndpointGuid, String from, Room room)
			throws JoinConferenceException, OutOfPortsException, EndpointNotSupportedException {

        Endpoint endpoint = this.endpointService.getEndpointDetail(gwEndpointGuid);
        if ("D".equals(endpoint.getEndpointType())) {
            logger.info(" Expecting a VE but found a desktop endpoint: " + endpoint);
            this.endpointService.updateVirtualEndpointStatus(gwEndpointGuid, endpoint.getStatus());
            this.endpointService.deleteRegularEndpoint(gwEndpointGuid);
        }
		this.conferenceService.joinTheConference(room, gwEndpointGuid, from); // move to conferenceAppService in the future
	}

	/**
	 * Update the CDR data for a legacy call.
	 *
	 */
	private int updateGatewayEndpointCDR(String gwEndpointGuid, String deviceModel, String endpointPublicIpAddress) {

		return this.endpointService.updateGatewayEndpointCDR(gwEndpointGuid, deviceModel, endpointPublicIpAddress);
	}

	public static DataHandler getTransferParticipantVCAPMsg(String endopintGUID, String toExtension, String roomPIN) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		com.vidyo.vcap20.TransferParticipantRequest transferParticipantRequest = requestMessage.addNewTransferParticipant();
		transferParticipantRequest.setEndpointGuid(endopintGUID);
		transferParticipantRequest.setToExtension(toExtension);
		if (StringUtils.isNotBlank(roomPIN)) {
			transferParticipantRequest.setRoomPIN(roomPIN);
		}

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}

	public static DataHandler getSetLayoutVCAPMsg(String endpointGUID, String layout, BigInteger maxParticipants) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		com.vidyo.vcap20.SetLayoutRequest setLayoutRequest = requestMessage.addNewSetLayout();
		setLayoutRequest.setEndpointGuid(endpointGUID);
		if (StringUtils.isNotBlank(layout)) {
			setLayoutRequest.setLayout(layout);
		}
		if (maxParticipants != null) {
			setLayoutRequest.setMaxParticipants(maxParticipants.intValue());
		}
		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}
}
