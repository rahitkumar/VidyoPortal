package com.vidyo.service.lecturemode;

import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Room;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.lecturemode.LectureModeDao;
import com.vidyo.framework.dao.BaseQueryFilter;
import com.vidyo.framework.service.BaseServiceResponse;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.lecturemode.request.GuestHandUpdateRequest;
import com.vidyo.service.lecturemode.request.HandUpdateRequest;
import com.vidyo.service.lecturemode.request.LectureModeControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantControlRequest;
import com.vidyo.service.lecturemode.request.LectureModeParticipantsRequest;
import com.vidyo.service.lecturemode.request.MemberHandUpdateRequest;
import com.vidyo.service.lecturemode.response.HandUpdateResponse;
import com.vidyo.service.lecturemode.response.LectureModeControlResponse;
import com.vidyo.service.lecturemode.response.LectureModeParticipantsResponse;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.service.utils.UtilService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.vcap20.HandDismissedNotification;
import com.vidyo.vcap20.LectureModeRequest;
import com.vidyo.vcap20.LectureStatusUpdateType;
import com.vidyo.vcap20.Message;
import com.vidyo.vcap20.RequestMessage;
import com.vidyo.vcap20.RootDocument;
import com.vidyo.vcap20.UserStatusUpdateType;

@Service
public class LectureModeServiceImpl implements LectureModeService {
	private final Logger logger = LoggerFactory.getLogger(LectureModeServiceImpl.class.getName());

	@Autowired
	private LectureModeDao lectureModeDao;

	@Autowired
	private IRoomService roomService;

	@Autowired
	private IConferenceService conferenceService;
	@Autowired
	private UtilService utilService;

	@Autowired
    private ITenantService tenantService;

	@Autowired
    private TransactionService transactionService;

	@Override
	public HandUpdateResponse updateHand(HandUpdateRequest request) {
		HandUpdateResponse response = new HandUpdateResponse();

		int rowsUpdated = 0;

		if (request instanceof MemberHandUpdateRequest) {
			if (request.isHandRaised()) {
				rowsUpdated = lectureModeDao.raiseHandForMember(((MemberHandUpdateRequest) request).getMemberID());
			} else {
				rowsUpdated = lectureModeDao.unraiseHandForMember(((MemberHandUpdateRequest) request).getMemberID());
			}
		} else if (request instanceof GuestHandUpdateRequest) {
			if (request.isHandRaised()) {
				rowsUpdated = lectureModeDao.raiseHandForGuest(
						((GuestHandUpdateRequest) request).getGuestID(),
						((GuestHandUpdateRequest) request).getUsername());
			} else {
				rowsUpdated = lectureModeDao.unraiseHandForGuest(
						((GuestHandUpdateRequest) request).getGuestID(),
						((GuestHandUpdateRequest) request).getUsername());
			}
		}

		if (rowsUpdated == 0) {
			response.setStatus(HandUpdateResponse.USER_NOT_FOUND_IN_CONFERENCE);
			response.setMessage("Unable to raise hand, user not found to be in active conference.");
		} else if (rowsUpdated > 1) {
			logger.warn("Raise or unraise hand query updated more rows than expected.");
		} else {
			response.setStatus(BaseServiceResponse.SUCCESS);
		}

		return response;
	}

	@Override
	public LectureModeControlResponse startLectureMode(LectureModeControlRequest request) {

		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();

		LectureModeControlResponse response = canControlRoom(memberID, tenantID, roomID, moderatorPIN);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(tenantID);
		if (tenantConfiguration.getLectureModeAllowed() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_FEATURE_NOT_ALLOWED);
			response.setMessage("Lecture mode feature is off.");
			return response;
		}


		Room room = response.getRoom();
		if (room.getLectureMode() == 1) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_ALREADY_STARTED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		lectureModeDao.setLectureModeFlag(roomID, true);

        // disconnect endpoints that do not support lecture mode
        if (tenantConfiguration.getLectureModeStrict() == 1) {
            List<String> endpointGUIDsNotSupported = lectureModeDao.getEndpointsWithoutLectureModeSupportInRoom(roomID);
            if (endpointGUIDsNotSupported != null && endpointGUIDsNotSupported.size() > 0) {
                for (String guid : endpointGUIDsNotSupported) {
                    try {
                        conferenceService.addEndpointUnsupportedToTransactionHistory(room, guid, "LectureMode");
                        conferenceService.disconnectEndpointFromConference(guid, room, CallCompletionCode.BY_SOMEONE_ELSE);
                    } catch (LeaveConferenceException lce) {
                        logger.error("LeaveConferenceException disconnecting unsupported lecture mode endpoint: " + guid);
                    }
                }
            }
        }

		try {
			roomService.muteRoom(roomID);
			conferenceService.updateEndpointAudioForAll(roomID, true);
		} catch (Exception e) {
			response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
			logger.error("Error muting roomID: "  + roomID + ", message: " + e.getMessage());
		}

		List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
		DataHandler vcapMsg = getVCAPLectureModeChange(true, null);
		for (String guid : endpointGUIDs) {
			try {
				conferenceService.sendMessageToEndpoint(guid, vcapMsg);
			} catch (Exception e) {
				response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
				logger.error("Failed to send lecture mode ON to guid: " + guid);
				logger.error(e.getMessage());
			}
		}

		return response;
	}

	@Override
	public LectureModeControlResponse stopLectureMode(LectureModeControlRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();

		LectureModeControlResponse response = canControlRoom(memberID, tenantID, roomID, moderatorPIN);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		Room room = response.getRoom();
		if (room.getLectureMode() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_ALREADY_STOPPED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		lectureModeDao.setLectureModeFlag(roomID, false);

		try {
			roomService.unmuteRoom(roomID);
			conferenceService.updateEndpointAudioForAll(roomID, false);
		} catch (Exception e) {
			response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
			logger.error("Error unmuting roomID: "  + roomID + ", message: " + e.getMessage());
		}

		List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
		DataHandler vcapMsg = getVCAPLectureModeChange(false, null);
		for (String guid : endpointGUIDs) {
			try {
				conferenceService.sendMessageToEndpoint(guid, vcapMsg);
			} catch (Exception e) {
				response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
				logger.error("Failed to send lecture mode OFF to guid: " + guid);
				logger.error(e.getMessage());
			}
		}

		// remove raised hands and presenter
		lectureModeDao.clearLectureModeState(roomID);

        // log if ending waiting room
        TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(tenantID);
        if (tenantConfiguration.getWaitingRoomsEnabled() == 1) {
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setTransactionName("Waiting Room");
            transactionHistory.setTenantName(room.getTenantName());
            transactionHistory.setTransactionResult("SUCCESS");
            StringBuilder transactionParams = new StringBuilder();
            transactionParams.append("Room: " + room.getRoomName());
            transactionParams.append(";Ended by control meeting moderator");
            transactionHistory.setTransactionParams(transactionParams.toString());
            this.transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
        }

		return response;
	}

	@Override
	public LectureModeControlResponse dismissAllRaisedHands(LectureModeControlRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();

		LectureModeControlResponse response = canControlRoom(memberID, tenantID, roomID, moderatorPIN);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		Room room = response.getRoom();
		if (room.getLectureMode() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_NOT_STARTED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		List<String> handsRaised = lectureModeDao.getEndpointGUIDsWithHandsRaised(roomID);
		lectureModeDao.dismissAllHands(roomID);

		DataHandler vcapMsg = getVCAPHandDismissedNotification();
		for (String guid : handsRaised) {
			try {
				conferenceService.sendMessageToEndpoint(guid, vcapMsg);
			} catch (Exception e) {
				response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
				logger.error("Failed to dismiss hand for guid : " + guid);
				logger.error(e.getMessage());
			}
		}

 		return response;
	}

	@Override
	public LectureModeControlResponse dismissRaisedHand(LectureModeParticipantControlRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();
		int endpointID = request.getEndpointID();
		String endpointType = request.getEndpointType();

		LectureModeControlResponse response = canControlEndpointInRoom(memberID, tenantID, roomID, moderatorPIN, endpointID);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		Room room = response.getRoom();
		if (room.getLectureMode() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_NOT_STARTED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		String handRaisedGUID = null;
		if (StringUtils.isBlank(endpointType)) { // blank for SOAP services, not blank from HTML control meeting
			// ugly hack taken from VidyoPortalUserService.muteAudio/unmuteAudio
			handRaisedGUID = conferenceService.getGUIDForEndpointID( endpointID, "D");
			if (handRaisedGUID.equalsIgnoreCase("")) {
				handRaisedGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
			}
		} else {
			handRaisedGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
		}

		lectureModeDao.dismissHand(handRaisedGUID);

		DataHandler vcapMsg = getVCAPHandDismissedNotification();
		try {
			conferenceService.sendMessageToEndpoint(handRaisedGUID, vcapMsg);
		} catch (Exception e) {
			response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
			logger.error("Failed to dismiss hand for guid : " + handRaisedGUID);
			logger.error(e.getMessage());
		}

		return response;
	}

	@Override
	public LectureModeControlResponse setPresenter(LectureModeParticipantControlRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();
		int endpointID = request.getEndpointID();
		String endpointType = request.getEndpointType();

		LectureModeControlResponse response = canControlEndpointInRoom(memberID, tenantID, roomID, moderatorPIN, endpointID);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		Room room = response.getRoom();
		if (room.getLectureMode() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_NOT_STARTED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		String currentPresenterGUID = lectureModeDao.getCurrentPresenterGUID(roomID);
		if (currentPresenterGUID != null) {
			lectureModeDao.setPresenterFlag(currentPresenterGUID, false);
		}

		String newPresenterGUID = null;
		if (StringUtils.isBlank(endpointType)) { // blank for SOAP services, not blank from HTML control meeting
			// ugly hack taken from VidyoPortalUserService.muteAudio/unmuteAudio
			newPresenterGUID = conferenceService.getGUIDForEndpointID( endpointID, "D");
			if (newPresenterGUID.equalsIgnoreCase("")) {
				newPresenterGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
			}
		} else {
			newPresenterGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
		}

		lectureModeDao.setPresenterFlag(newPresenterGUID, true);

		String routerParticipantID = lectureModeDao.getRouterParticipantIDForGUID(newPresenterGUID);

		if (currentPresenterGUID != null) {
			DataHandler vcapMsgMute = getVCAPLectureModePresenterChange(routerParticipantID, true);
			// to old presenter
			try {
				conferenceService.updateEndpointAudio(currentPresenterGUID, true);
				conferenceService.sendMessageToEndpoint(currentPresenterGUID, vcapMsgMute);
			} catch (Exception e) {
				logger.error("Failed to send lecture mode presenter to prev presenter guid: " + newPresenterGUID);
				logger.error(e.getMessage());
			}
		}

		DataHandler vcapMsgUnMute = getVCAPLectureModePresenterChange(routerParticipantID, false);
		// to new presenter
		try {
			conferenceService.updateEndpointAudio(newPresenterGUID, false);
			conferenceService.sendMessageToEndpoint(newPresenterGUID, vcapMsgUnMute);
            conferenceService.startVideoIfStopped(newPresenterGUID);
		} catch (Exception e) {
			logger.error("Failed to send lecture mode presenter to new presenter guid: " + newPresenterGUID);
			logger.error(e.getMessage());
		}

		// find all participants and send them new presenter info
		List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
		DataHandler vcapMsg = getVCAPLectureModePresenterChange(routerParticipantID);
		for (String guid : endpointGUIDs) {
			if (!(guid.equals(newPresenterGUID) || guid.equals(currentPresenterGUID))) {
				try {
					conferenceService.sendMessageToEndpoint(guid, vcapMsg);
				} catch (Exception e) {
					response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
					logger.error("Failed to send lecture mode presenter to guid: " + guid);
					logger.error(e.getMessage());
				}
			}
		}

		return response;
	}

	@Override
	public LectureModeControlResponse removePresenter(LectureModeParticipantControlRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();
		int endpointID = request.getEndpointID();
		String endpointType = request.getEndpointType();

		LectureModeControlResponse response = canControlEndpointInRoom(memberID, tenantID, roomID, moderatorPIN, endpointID);
		if (response.getStatus() != LectureModeControlResponse.SUCCESS) {
			return response;
		}

		Room room = response.getRoom();
		if (room.getLectureMode() == 0) {
			response.setStatus(LectureModeControlResponse.LECTURE_MODE_NOT_STARTED);
			return response;
		}

		response.setStatus(LectureModeControlResponse.SUCCESS);

		String presenterGUID = null;
		if (StringUtils.isBlank(endpointType)) { // blank for SOAP services, not blank from HTML control meeting
			// ugly hack taken from VidyoPortalUserService.muteAudio/unmuteAudio
			presenterGUID = conferenceService.getGUIDForEndpointID( endpointID, "D");
			if (presenterGUID.equalsIgnoreCase("")) {
				presenterGUID = conferenceService.getGUIDForEndpointID(endpointID, "V");
			}
		} else {
			presenterGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
		}

		// find out current presenter
		String currentPresenterGUID = lectureModeDao.getCurrentPresenterGUID(roomID); // can be null
		if (presenterGUID.equals(currentPresenterGUID)) {
			lectureModeDao.setPresenterFlag(currentPresenterGUID, false);

			DataHandler vcapMsgMute = getVCAPLectureModePresenterChange(null, true);
			// to current presenter
			try {
				conferenceService.updateEndpointAudio(currentPresenterGUID, true);
				conferenceService.sendMessageToEndpoint(currentPresenterGUID, vcapMsgMute);
			} catch (Exception e) {
				response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
				logger.error("Failed to send lecture mode presenter to prev presenter guid: " + currentPresenterGUID);
				logger.error(e.getMessage());
			}


			// find all participants and let them know there is new presenter
			List<String> endpointGUIDs = lectureModeDao.getEndpointGUIDsInConference(roomID);
			DataHandler vcapMsg = getVCAPLectureModePresenterChange(null);
			for (String guid : endpointGUIDs) {
				if (!guid.equals(currentPresenterGUID)) {
					try {
						conferenceService.sendMessageToEndpoint(guid, vcapMsg);
					} catch (Exception e) {
						response.setStatus(LectureModeControlResponse.OK_WITH_ERRORS);
						logger.error("Failed to send presenter removed msg to guid: " + guid);
						logger.error(e.getMessage());
					}
				}
			}

		} else {
			response.setStatus(LectureModeControlResponse.PARTICIPANT_IS_NOT_PRESENTING);
			response.setMessage("Requested participant is not presenter.");
		}
		return response;
	}

	/**
	 * No need to be in lecture mode to use this method.
	 *
	 * @param request
	 * @return
	 */
	@Override
	public LectureModeParticipantsResponse getLectureModeParticipants(LectureModeParticipantsRequest request) {
		int memberID = request.getMemberID();
		int tenantID = request.getTenantID();
		int roomID = request.getRoomID();
		String moderatorPIN = request.getModeratorPIN();
		EntityFilter filter = request.getEntityFilter();

		LectureModeParticipantsResponse response = new LectureModeParticipantsResponse();

		LectureModeControlResponse controlResponse = canControlRoom(memberID, tenantID, roomID, moderatorPIN);
		if (controlResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
			response.setStatus(controlResponse.getStatus());
			response.setMessage(controlResponse.getMessage());
			return response;
		}

		if (filter != null) {
			int limit = filter.getLimit();

			if (limit >= 0) {
				if (limit > BaseQueryFilter.FILTER_MAX_LIMIT) {
					// return error
					response.setStatus(LectureModeParticipantsResponse.SEARCH_EXCEEDS_LIMIT);
					response.setMessage("Requested limit exceeds the allowed threshold of " + BaseQueryFilter.FILTER_MAX_LIMIT + " entries.");
					return response;
				}
				if (limit > BaseQueryFilter.FILTER_DEFAULT_LIMIT) {
					logger.info("ATTENTION!!! Usage of limit equal to " + limit + " MAY HAVE performance impact");
				}
			} else {
				filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
			}

		} else {
			filter = new EntityFilter();
			filter.setLimit(BaseQueryFilter.FILTER_DEFAULT_LIMIT);
			filter.setEntityType("All");
		}

		Long total = conferenceService.getCountParticipants(roomID);
		List<Entity> list = null;
		if (total > 0) {
			list = conferenceService.getLectureModeParticipants(roomID, filter);
		}

		response.setLectureMode(controlResponse.getRoom().getLectureMode() == 1);

		if (list != null) {
			for (Entity entity : list) {
				if ("Recorder".equals(entity.getRoomType())) {
					response.setRecorderID(entity.getEndpointID());
					response.setRecorderName(entity.getName());
					response.setWebcast(entity.getWebcast() == 1);
					response.setPaused(entity.getVideo() == 0);
					total = total - 1;
				} else {
					response.addEntity(entity);
				}
			}
		}

		response.setTotal(total.intValue());

		return response;
	}

    public int getCountEndpointsWithoutLectureModeSupportInRoom(int roomID) {
        return lectureModeDao.getCountEndpointsWithoutLectureModeSupportInRoom(roomID);
    }

	public static DataHandler getVCAPLectureModeChange(boolean lectureModeFlag, String presenterParticipantID) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		LectureModeRequest lectureModeRequest = requestMessage.addNewLectureMode();
		LectureStatusUpdateType lectureStatusUpdateType = lectureModeRequest.addNewLectureStatusUpdate();
		lectureStatusUpdateType.setLecture(lectureModeFlag);
		if (lectureModeFlag) {
			lectureStatusUpdateType.setMuteAudio(true);
		} else {
			lectureStatusUpdateType.setMuteAudio(false);
		}
		if (!StringUtils.isBlank(presenterParticipantID)) {
			lectureStatusUpdateType.setPresenterParticipantId(presenterParticipantID);
		}

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}

	public static DataHandler getVCAPLectureModeState(boolean lectureModeFlag, String presenterParticipantID, boolean mute) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		LectureModeRequest lectureModeRequest = requestMessage.addNewLectureMode();
		LectureStatusUpdateType lectureStatusUpdateType = lectureModeRequest.addNewLectureStatusUpdate();
		lectureStatusUpdateType.setLecture(lectureModeFlag);
		if (mute) {
			lectureStatusUpdateType.setMuteAudio(true);
		} else {
			lectureStatusUpdateType.setMuteAudio(false);
		}
		if (!StringUtils.isBlank(presenterParticipantID)) {
			lectureStatusUpdateType.setPresenterParticipantId(presenterParticipantID);
		}

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}

	public static DataHandler getVCAPLectureModePresenterChange(String presenterParticipantID) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		LectureModeRequest lectureModeRequest = requestMessage.addNewLectureMode();
		LectureStatusUpdateType lectureStatusUpdateType = lectureModeRequest.addNewLectureStatusUpdate();
		lectureStatusUpdateType.setLecture(true);
		if (!StringUtils.isBlank(presenterParticipantID)) {
			lectureStatusUpdateType.setPresenterParticipantId(presenterParticipantID);
		}

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}

	public static DataHandler getVCAPLectureModePresenterChange(String presenterParticipantID, boolean muteFlag) {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		LectureModeRequest lectureModeRequest = requestMessage.addNewLectureMode();
		LectureStatusUpdateType lectureStatusUpdateType = lectureModeRequest.addNewLectureStatusUpdate();
		lectureStatusUpdateType.setLecture(true);
		if (muteFlag) {
			lectureStatusUpdateType.setMuteAudio(true);
		} else {
			lectureStatusUpdateType.setMuteAudio(false);
		}
		if (!StringUtils.isBlank(presenterParticipantID)) {
			lectureStatusUpdateType.setPresenterParticipantId(presenterParticipantID);
		}

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}


	public static DataHandler getVCAPHandDismissedNotification() {
		RootDocument rootDocument = RootDocument.Factory.newInstance();
		Message message = rootDocument.addNewRoot();
		RequestMessage requestMessage = message.addNewRequest();
		long requestID = Long.valueOf(PortalUtils.generateNumericKey(9));
		requestMessage.setRequestID(requestID);

		LectureModeRequest lectureModeRequest = requestMessage.addNewLectureMode();
		UserStatusUpdateType userStatusUpdateType = lectureModeRequest.addNewUserStatusUpdate();
		HandDismissedNotification handDismissedNotification = userStatusUpdateType.addNewHandDismissedNotification();

		XmlOptions opts = new XmlOptions();
		opts.setCharacterEncoding("UTF-8");
		String info = rootDocument.xmlText(opts);
		return new DataHandler(info, "text/plain; charset=UTF-8");
	}

	// private methods

	private LectureModeControlResponse canControlRoom(int memberID, int memberTenantID, int roomID, String moderatorPIN) {

		LectureModeControlResponse response = new LectureModeControlResponse();
		response.setStatus(LectureModeControlResponse.SUCCESS);

		// validate room
		Room room = null;
		try {
			room = roomService.getRoomDetailsForConference(roomID); // less expensive then getRoom()
			response.setRoom(room);
		} catch (Exception ex) {
			// ignore
		}
		if (room == null) {
			response.setStatus(LectureModeControlResponse.INVALID_ROOM);
			response.setMessage("Invalid room");
			return response;
		}
		if (!utilService.canMemberControlRoom(memberID, memberTenantID, room, moderatorPIN)) {
			response.setStatus(LectureModeControlResponse.CANNOT_CONTROL_ROOM);
			response.setMessage("Cannot control room");
			return response;
		}

		return response;
	}

	private LectureModeControlResponse canControlEndpointInRoom(int memberID, int memberTenantID, int roomID, String moderatorPIN, int endpointID) {

		LectureModeControlResponse response = new LectureModeControlResponse();
		response.setStatus(LectureModeControlResponse.SUCCESS);

		// validate room
		Room room = null;
		try {
			room = roomService.getRoomDetailsForConference(roomID); // less expensive then getRoom()
			response.setRoom(room);
		} catch (Exception ex) {
			// ignore
		}
		if (room == null) {
			response.setStatus(LectureModeControlResponse.INVALID_ROOM);
			response.setMessage("Invalid room");
			return response;
		}
		if (!conferenceService.isEndpointIDinRoomID(endpointID, roomID)) {
			response.setStatus(LectureModeControlResponse.PARTICIPANT_NOT_IN_CONFERENCE);
			response.setMessage("Participant is not in this conference");
			return response;
		}
		if (!utilService.canMemberControlRoom(memberID, memberTenantID, room, moderatorPIN)) {
			response.setStatus(LectureModeControlResponse.CANNOT_CONTROL_ROOM);
			response.setMessage("Cannot control room");
			return response;
		}

		return response;
	}


}
