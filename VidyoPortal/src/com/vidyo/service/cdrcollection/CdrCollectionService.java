/**
 *
 */
package com.vidyo.service.cdrcollection;

import com.vidyo.bo.ConferenceInfo;
import com.vidyo.bo.conference.CallCompletionCode;

/**
 * Interface definition for CDR Collection
 *
 * @author Ganesh
 *
 */
public interface CdrCollectionService {

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
	public void collectCdrRecord(String GUID, String status, int oldStatus, int newStatus,
								 ConferenceInfo conferenceInfo, String endpointType, String statusUpdateReason, boolean saveUpdateRecord);

	/**
	 * Returns the total count of CDRs. Checks the version of the enabled CDR
	 * and returns the appropriate count. If CDR is not enabled, always returns
	 * zero.
	 *
	 * @return total count of CDRs
	 */
	public int getTotalCdrCount();

	/**
	 * Deletes the oldest CDR records based on the limit passed. The limit
	 * presents the number of records to be deleted.
	 *
	 *
	 * @param limit
	 * @return
	 */
	public int deleteCdr(int limit);

	public int updateConferenceCallCompletionCode(String uniqueCallId, CallCompletionCode code);

	public int updateEndpointCallCompletionCode(String uniqueCallId, String endpointGUID, CallCompletionCode code);

	}
