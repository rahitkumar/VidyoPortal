package com.vidyo.db;

import com.vidyo.bo.*;

import java.util.List;

public interface IFederationDao {
    public String updateToSystemIpc(Federation federation, ExternalLink externalLink);
    
	/**
	 * Returns true if ExternalLink is inserted. This will only happen when the
	 * 1st participant from this portal makes an IPC call to join a given room.
	 * 
	 * @param federation
	 * @param externalLink
	 * @return
	 */
	public boolean updateFromSystemIpc(Federation federation,
			ExternalLink externalLink);
    
    public int deleteFederation(ExternalLink externalLink);

    public ExternalLink getExternalLinkByRequestID(String requestID);
    public ExternalLink getExternalLinkByRemoteMedia(String remoteMediaAddress, String remoteMediaAdditionalInfo);
    public ExternalLink getExternalLinkByUniqueKey(String fromSystemID, String fromConferenceName, String toSystemID, String toConferenceName);
    public ExternalLink getExternalLinkForUpdateStatus(String remoteSystemID, String fromConferenceName, String toConferenceName);
    public ExternalLink getExternalLinkByConferenceName(String conferenceName);

    public int updateExternalLinkForMediaInfo(int externalLinkID, String requestID, String mediaAddress, String mediaAdditionalInfo);
    public int updateExternalLinkForLocalMediaInfo(int externalLinkID, String mediaAddress, String mediaAdditionalInfo);
    public int updateExternalLinkForStatus(int externalLinkID, int status);

    public List<Control> getParticipantsForFederation(String conferenceName, ControlFilter filter);

    public int insertFederationConferenceRecord(FederationConferenceRecord federationConferenceRecord);
    public int deleteFederationConferenceRecord(String endpointGUID);
    public int deleteAllFederationConferenceRecords(String conferenceName);

    public FederationConferenceRecord getFederationConferenceRecord(String endpointGUID);
    public String getFederationEndpointGUID(int endpointID);
    public String getFederationConferenceName(int roomID);
    public String getFederationFromTenantHostForEndpointID(int endpointID);
    
    public ExternalLink getExternalLink(String requestID, String fromConfName, String toConfName);
    
    public ExternalLink getExternalLink(String toConfName);
    
	/**
	 * Updates the UniqueCallId of the conference in ExternalLinks table
	 * @param toSystemId
	 * @param toConfName
	 * @param uniqueCallId
	 * @return
	 */
	public int updateExternalLinkUniqueId(String toSystemId, String toConfName,
			String uniqueCallId);
	
	/**
	 * Returns the list of ExternalLinks remaining with specific status for a
	 * specific period of time
	 * 
	 * @param timeLapsed
	 * @return
	 */
	public List<ExternalLink> getDisconnectedExternalLinks(int status,
			int timeLapsed);
	
	/**
	 * Returns ExternalLinks count by toConferenceName
	 * @param toConfName
	 * @return
	 */
	public int getExtLinksCountByToConfName(String toConfName);
	
}
