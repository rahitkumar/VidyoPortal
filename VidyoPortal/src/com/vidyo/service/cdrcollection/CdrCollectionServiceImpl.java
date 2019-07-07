/**
 *
 */
package com.vidyo.service.cdrcollection;

import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.IUserService;
import com.vidyo.service.statusnotify.StatusNotificationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.CDRinfo;
import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.ConferenceInfo;
import com.vidyo.bo.Configuration;
import com.vidyo.db.ICDRCollection2Dao;
import com.vidyo.db.ICDRCollectionDao;
import com.vidyo.service.ISystemService;

/**
 * @author Ganesh
 *
 */
public class CdrCollectionServiceImpl implements CdrCollectionService {

	/**
	 *
	 */
	private Logger logger = LoggerFactory
			.getLogger(CdrCollectionServiceImpl.class);

	/**
	 *
	 */
	private ICDRCollectionDao cdrCollectionVersion1Dao;

	/**
	 *
	 */
	private ICDRCollection2Dao cdrCollectionVersion2Dao;

	/**
	 *
	 */
	private ISystemService systemService;

	private IUserService userService;

	private StatusNotificationService statusNotificationService;

	/**
	 * @param statusNotificationService the statusNotificationService to set
	 */
	public void setStatusNotificationService(StatusNotificationService statusNotificationService) {
		this.statusNotificationService = statusNotificationService;
	}

	/**
	 * @param cdrCollectionVersion1Dao
	 *            the cdrCollectionVersion1Dao to set
	 */
	public void setCdrCollectionVersion1Dao(
			ICDRCollectionDao cdrCollectionVersion1Dao) {
		this.cdrCollectionVersion1Dao = cdrCollectionVersion1Dao;
	}

	/**
	 * @param cdrCollectionVersion2Dao
	 *            the cdrCollectionVersion2Dao to set
	 */
	public void setCdrCollectionVersion2Dao(
			ICDRCollection2Dao cdrCollectionVersion2Dao) {
		this.cdrCollectionVersion2Dao = cdrCollectionVersion2Dao;
	}

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 *
	 * @param GUID
	 * @param status
	 * @param oldStatus
	 * @param newStatus
	 * @param conferenceInfo
	 * @param endpointType
	 * @param statusUpdateReason
	 */
	@Override
	public void collectCdrRecord(String GUID, String status, int oldStatus,
								 int newStatus, ConferenceInfo conferenceInfo, String endpointType, String statusUpdateReason, boolean saveUpdateRecord) {
		// Check if CDR collection is enabled
		if (systemService.getCDREnabled().equalsIgnoreCase("0")) {
			logger.info("CDR Collection not enabled");
			return;
		}
		// check CDR Format "0" - old, "1" - new
		if (systemService.getCDRformat().equalsIgnoreCase("0")) {
			collectCdrVersion1(GUID, status, oldStatus, newStatus,
					conferenceInfo, endpointType);
		} else if (systemService.getCDRformat().equalsIgnoreCase("1")) {
			collectCdrVersion2(GUID, status, oldStatus, newStatus,
					conferenceInfo, endpointType, statusUpdateReason, saveUpdateRecord);
		} else {
			logger.error("NO CDR Format is available, not collecting CDRs");
		}

	}

	/**
	 * Version 1 CDR Collection. This does not support IPC record collection on
	 * remote portal.
	 *
	 * @param GUID
	 * @param status
	 * @param oldStatus
	 * @param newStatus
	 * @param conferenceInfo
	 * @param endpointType
	 */
	private void collectCdrVersion1(String GUID, String status, int oldStatus,
			int newStatus, ConferenceInfo conferenceInfo, String endpointType) {
		logger.debug(
				"CDR Collection Version 1 GUID - {}, Status - {}, OldStatus - {}, NewStatus - {}, ConferenceInfo - {}, EndpointType - {}",
				GUID, status, oldStatus, newStatus, conferenceInfo,
				endpointType);
		CDRinfo info = null;

		try {
			// query from ConferenceRecord table
			info = cdrCollectionVersion1Dao.getCDRinfo(GUID);
		} catch (Exception ignored) {
			logger.info(ignored.getMessage());
		}

		// If CDR Info is null, query from Conferences table
		if (info == null) {
			info = cdrCollectionVersion1Dao.getCDRinfoFromConference(GUID);
		}

		if (info != null) {
			// Hide the user identity [specifically guests] by setting empty
			// value for caller name
			Configuration config = systemService.getConfiguration("HIDE_USER_IDENTITY");
			if (config != null && StringUtils.isNotBlank(config.getConfigurationValue())
					&& config.getConfigurationValue().equals("1")) {
				info.setCallerName("");
			}
			logger.debug("CDRInfo {}", info);
			// Initialization
			// ALERTING
			// Online -> Alerting
			if (oldStatus == 1 && newStatus == 7) {
				if (info.getConferenceType().equalsIgnoreCase("P")) {
					// P2P direct call
					// USE "RINGING" for consistency
					info.setCallState("RINGING");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.insertCDRtoPointToPointCall(info);
					}
				}
			}
			// RINGING
			// Online -> Ringing
			if (oldStatus == 1 && newStatus == 3) {
				if (info.getConferenceType().equalsIgnoreCase("P")) {
					// P2P direct call
					info.setCallState("RINGING");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.insertCDRtoPointToPointCall(info);
					}
				}
			}
			// Waiting for answer
			// RING NO ANSWER
			// Ringing -> No Answer
			if (oldStatus == 3 && newStatus == 6) {
				if (info.getConferenceType().equalsIgnoreCase("P")) {
					// P2P direct call
					info.setCallState("NO ANSWER");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.updateCDRinPointToPointCallForCallee(info);
					}
				}
			}
			// RING REJECTED
			// Ringing -> Rejected
			if (oldStatus == 3 && newStatus == 5) {
				if (info.getConferenceType().equalsIgnoreCase("P")) {

					info.setCallState("REJECTED");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.updateCDRinPointToPointCallForCallee(info);
					}
				}
			}
			// RING CANCELED
			// Alerting -> Online
			if (oldStatus == 3 && newStatus == 1) {
				// P2P direct call
				if (info.getConferenceType().equalsIgnoreCase("P")) {
					info.setCallState("CANCELED");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.updateCDRinPointToPointCallForCallee(info);
					}
				}
			}
			// Waiting for answer
			// In conference
			// IN PROGRESS
			if (newStatus == 2) { // any status -> Busy
				if (info.getConferenceType().equalsIgnoreCase("C")) {

					info.setCallState("IN PROGRESS");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.insertCDRtoConferenceCall(info);
					}
				} else if (info.getConferenceType().equalsIgnoreCase("P")) {
					// P2P direct call
					info.setCallState("IN PROGRESS");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.insertCDRtoPointToPointCall(info);
					}
				}
			}
			// In conference
			// Finish
			// COMPLETED
			if (oldStatus == 2 || oldStatus == 9) {
				// Busy | Busy in own room
				// -> any status
				if (info.getConferenceType().equalsIgnoreCase("C")) {
					// regular conference
					info.setCallState("COMPLETED");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.updateCDRinConferenceCall(info);
					}
				} else if (info.getConferenceType().equalsIgnoreCase("P")) {
					// P2P direct call
					info.setCallState("COMPLETED");
					synchronized (this) {
						cdrCollectionVersion1Dao
								.updateCDRinPointToPointCall(info);
					}
				}
			}
			// Finish
		}
	}

	/**
	 *
	 * @param GUID
	 * @param status
	 * @param oldStatus
	 * @param newStatus
	 * @param conferenceInfo
	 * @param endpointType
	 * @param statusUpdateReason
	 */
	private void collectCdrVersion2(String GUID, String status, int oldStatus,
									int newStatus, ConferenceInfo conferenceInfo, String endpointType, String statusUpdateReason, boolean saveUpdateRecord) {
		logger.debug(
                "CDR Collection Version 2 GUID - {}, Status - {}, OldStatus - {}, NewStatus - {}, ConferenceInfo - {}, EndpointType - {}",
                GUID, status, oldStatus, newStatus, conferenceInfo, endpointType);


        CDRinfo2 info = cdrCollectionVersion2Dao.getCDRFromConferenceRecordTable(GUID);
        if (info == null) {
            info = cdrCollectionVersion2Dao.getCDRFromConferencesTable(GUID);
        }

		if (info != null) {
			if (conferenceInfo != null) {
				info.setRouterID(conferenceInfo.getVRID());
			}

			// Hide the user identity [specifically guests] by setting empty
			// value for caller name
			Configuration config = systemService.getConfiguration("HIDE_USER_IDENTITY");
			if (config != null && StringUtils.isNotBlank(config.getConfigurationValue())
					&& config.getConfigurationValue().equals("1")) {
				info.setCallerName("");
				info.setEndpointPublicIPAddress("");
				info.setCallerID("");
			}

			// P2P init
			// RINGING
			// Online -> Ringing
			if (oldStatus == 1 && newStatus == 3) {
				info.setCallState("RINGING");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.insertCDRtoConferenceCall2(info);
					}

				}
			}
			// ALERTING
			// Online -> Alerting
			if (oldStatus == 1 && newStatus == 7) {
				info.setCallState("ALERTING");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.insertCDRtoConferenceCall2(info);
					}
				}
			}
			// P2P init
			// P2P Ring Accepted
			// RING ACCEPTED
			// Ringing -> RingAccepted
			if (oldStatus == 3 && newStatus == 4) {
				info.setCallState("RING ACCEPTED");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// P2P Ring Accepted
			// P2P Ring Rejected
			// RING REJECTED
			// Ringing -> RingRejected
			if (oldStatus == 3 && newStatus == 5) {
				info.setCallState("RING REJECTED");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// Alerting -> Online
			if (oldStatus == 7 && newStatus == 1) {
				info.setCallState("ALERT CANCELLED");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// P2P Ring Rejected
			// P2P Ring No Answer
			// RING NO ANSWER
			// Ringing -> RingNoAnswer
			if (oldStatus == 3 && newStatus == 6) {
				info.setCallState("RING NO ANSWER");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// P2P Ring No Answer
			// P2P Alert Cancelled
			// Alert Cancelled
			// Alerting -> AlertCancelled
			if (oldStatus == 7 && newStatus == 8) {
				info.setCallState("ALERT CANCELLED");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// Ringing -> Online
			if (oldStatus == 3 && newStatus == 1) {
				info.setCallState("RING CANCELLED");
				synchronized (this) {
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.updateCDRinConferenceCall2(info);
					}
				}
			}
			// P2P Alert Cancelled
			// In conference
			// IN PROGRESS
			// Online | WaitJoinConfirm -> Busy | Busy in own room
			String confType = info.getConferenceType();
			if ((oldStatus == 1 || oldStatus == 12)
					&& (newStatus == 2 || newStatus == 9)) {
				info.setCallState("IN PROGRESS");
				// Conference Call
				if (confType.equalsIgnoreCase("C")
						|| confType.equalsIgnoreCase("IC")) {
					synchronized (this) {
						if(saveUpdateRecord) {
							cdrCollectionVersion2Dao
								.insertCDRtoConferenceCall2(info);
						}
					}
				}
				// P2P Call
				if (confType.equalsIgnoreCase("D")
						|| confType.equalsIgnoreCase("ID")) {
					synchronized (this) {
						if(saveUpdateRecord) {
							cdrCollectionVersion2Dao
								.updateCDRinConferenceCall2(info);
						}
					}
				}
			}
			// In conference
			// Finish
			// COMPLETED
			if ((oldStatus == 2 || oldStatus == 9)
					&& (newStatus == 1 || newStatus == 0)) {
				// Busy | Busy in own room -> Online | Offline
				info.setCallState("COMPLETED");
				synchronized (this) {
					info.setCallCompletionCode(getCallCompletionCodeFromReason(statusUpdateReason).toString());
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.completeCDRinConferenceCall2(info);
						userService.updateEndpointReferenceNumber(info.getEndpointGUID(), "");
					}
				}
			}
			/*
			 * RING CANCELLED - special cover for Gateway. Usually go from
			 * Online to RingFailed | JoinFailed. Or from WaitJoinConfirm to
			 * Offline
			 */
			if ((oldStatus == 12 || oldStatus == 1)
					&& (newStatus == 1 || newStatus == 0 || newStatus == 10 || newStatus == 11)) {
				// WaitJoinConfirm -> Online | Offline | RingFailed | JoinFailed
				info.setCallState("RING CANCELLED");
				synchronized (this) {
					info.setCallCompletionCode(getCallCompletionCodeFromReason(statusUpdateReason).toString());
					if(saveUpdateRecord) {
						cdrCollectionVersion2Dao.completeCDRinConferenceCall2(info);
						userService.updateEndpointReferenceNumber(info.getEndpointGUID(), "");
					}
				}
			}
			/*
			 * Do not use this update - just keep previous state before go
			 * "Online" or "Offline" else if ((old_status == 3 || old_status ==
			 * 5 || old_status == 6 || old_status == 7 || old_status == 8) &&
			 * (new_status == 1 || new_status == 0)) { // Ringing | RingRejected
			 * | RingNoAnswer | Alerting | AlertCancelled -> Online | Offline
			 * info.setCallState("CANCELED"); synchronized (this) {
			 * this.dao.completeCDRinConferenceCall2(info); } }
			 */
			// ------------------------------- Finish
		} else {
            logger.debug("Not found in ConferenceRecord or Conferences table: " + GUID);
            info = new CDRinfo2();
            info.setEndpointGUID(GUID);
            info.setCallState(status);
            info.setEndpointType(endpointType);
        }
		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setCreationTimestamp(System.nanoTime());
		notificationInfo.setUserNotification(info);
		statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
	}

	private CallCompletionCode getCallCompletionCodeFromReason(String updateStatusReason) {
		if ("EpLost".equals(updateStatusReason)) {
			return CallCompletionCode.ERROR;
		} else if ("EpLeftConf".equals(updateStatusReason)) {
			return CallCompletionCode.BY_SELF;
		} else if ("EpLeaveConfOk".equals(updateStatusReason)) {
			return CallCompletionCode.BY_SOMEONE_ELSE; // other person in P2P call ended the call
		}
		return CallCompletionCode.UNKNOWN;
	}

	/**
	 * Returns the total count of CDRs. Checks the version of the enabled CDR
	 * and returns the appropriate count. If CDR is not enabled, always returns
	 * zero.
	 *
	 * @return total count of CDRs
	 */
	@Override
	public int getTotalCdrCount() {
		int totalCount = 0;
		// Check if CDR collection is enabled
		if (systemService.getCDREnabled().equalsIgnoreCase("0")) {
			logger.info("CDR Collection not enabled");
			return totalCount;
		}
		// check CDR Format "0" - old, "1" - new
		if (systemService.getCDRformat().equalsIgnoreCase("0")) {
			totalCount = cdrCollectionVersion1Dao.getTotalCdrCount();
		} else if (systemService.getCDRformat().equalsIgnoreCase("1")) {
			totalCount = cdrCollectionVersion2Dao.getTotalCdrCount();
		} else {
			logger.error("NO CDR Format is available, not collecting CDRs");
		}
		return totalCount;
	}

	@Override
	public int deleteCdr(int limit) {
		int deleteCount = 0;
		// check CDR Format "0" - old, "1" - new
		if (systemService.getCDRformat().equalsIgnoreCase("0")) {
			deleteCount = cdrCollectionVersion1Dao.deleteCdr(limit);
		} else if (systemService.getCDRformat().equalsIgnoreCase("1")) {
			deleteCount = cdrCollectionVersion2Dao.deleteCdr(limit);
		}
		return deleteCount;
	}

	public int updateConferenceCallCompletionCode(String uniqueCallId, CallCompletionCode code) {
		// find conference by uniqueCallId and update all endpoints
		return cdrCollectionVersion2Dao.updateConferenceCallCompletionCode(uniqueCallId, code);
	}

	public int updateEndpointCallCompletionCode(String uniqueCallId, String endpointGUID, CallCompletionCode code) {
		// find by uniqueCallId and endpointGUID and update single endpoint
		return cdrCollectionVersion2Dao.updateEndpointCallCompletionCode(uniqueCallId, endpointGUID, code);
	}

}
