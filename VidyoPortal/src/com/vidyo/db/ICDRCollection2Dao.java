package com.vidyo.db;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.conference.CallCompletionCode;

public interface ICDRCollection2Dao {
	
	public static final String INSERT_INTO_CDR_QUERY = "INSERT INTO ConferenceCall2 (UniqueCallID, ConferenceName, TenantName, ConferenceType, "
			+ "EndpointType, CallerID, CallerName, JoinTime, CallState, Direction, RouterID, GWID, GWPrefix, ReferenceNumber, "
			+ "ApplicationName, ApplicationVersion, ApplicationOs, DeviceModel, EndpointPublicIPAddress, Extension, "
			+ "EndpointGUID, AccessType, RoomType, RoomOwner) "
			+ "VALUES(:UniqueCallID, :ConferenceName, :TenantName, :ConferenceType, :EndpointType, :CallerID, :CallerName, :JoinTime, :CallState, "
			+ ":Direction, :RouterID, :GWID, :GWPrefix, :ReferenceNumber, :ApplicationName, :ApplicationVersion, :ApplicationOS, :DeviceModel, "
			+ ":EndpointPublicIPAddress, :Extension, :EndpointGUID, :AccessType, :RoomType, :RoomOwner)";
	
    public CDRinfo2 getCDRFromConferenceRecordTable(String GUID);
	public CDRinfo2 getCDRFromConferencesTable(String GUID);

    public void insertCDRtoConferenceCall2(CDRinfo2 info);
    public int updateCDRinConferenceCall2(CDRinfo2 info);

	public void completeCDRinConferenceCall2(CDRinfo2 info);
	
	/**
	 * Inserts the Server Restart event in to the CDR2 table
	 * 
	 * @param info
	 */
	public void createServerRestartRecord(CDRinfo2 info);
	
    /**
     * Returns the total cdr(v2) count
     *  
     * @return total count
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