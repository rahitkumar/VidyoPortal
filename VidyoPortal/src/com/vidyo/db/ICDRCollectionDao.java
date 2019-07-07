package com.vidyo.db;

import com.vidyo.bo.*;

public interface ICDRCollectionDao {
    public CDRinfo getCDRinfo(String GUID);

    public void insertCDRtoConferenceCall(CDRinfo info);
    public void updateCDRinConferenceCall(CDRinfo info);

    public void insertCDRtoPointToPointCall(CDRinfo info);
    public void updateCDRinPointToPointCall(CDRinfo info);
    public void updateCDRinPointToPointCallForCallee (CDRinfo info);

    public void updateCDRinProgressConferenceCall();
    public void updateCDRinProgressP2PCall();
	public void updateCDRinProgressConferenceCall2();

    public int cleanConferences();
    public int resetVirtualEndpoints();
    public int resetEndpoints();
    public int resetRecorderEndpoints();
    public int cleanFederations();
    
    /**
     * Returns the CDR Info from the Conferences Table
     * @param GUID
     * @return
     */
    public CDRinfo getCDRinfoFromConference(String GUID);
    
    /**
     * Returns the total cdr(v1) count
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
}