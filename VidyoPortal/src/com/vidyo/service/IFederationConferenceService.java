package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.conference.Conference;
import com.vidyo.service.exceptions.*;

import java.rmi.RemoteException;
import java.util.List;

public interface IFederationConferenceService extends IConferenceService {
    public void joinRemoteConference(int memberID, String remoteUserName, String remoteTenantHost, String pin, boolean secure) throws RemoteException, OutOfPortsException, JoinConferenceException, FederationNotAllowedException, UserNotFoundException, WrongPinException, ConferenceLockedException, ResourceNotAvailableException;
    public void sendMediaInfoToRemote(ExternalLink externalLink, boolean secure) throws RemoteException, ConferenceNotExistException;

    public void linkMediaWithRemote(ExternalLink externalLink) throws ConferenceNotExistException;
    public void unlinkMediaWithRemote(ExternalLink externalLink, boolean secure) throws RemoteException,ConferenceNotExistException;

    public Conference createConferenceForFederation(Room room, Tenant tenant, boolean checkFreePorts) throws OutOfPortsException;

    public void createExternalLink(ExternalLink externalLink) throws NoVidyoManagerException;
    public int removeExternalLink(ExternalLink externalLink) throws NoVidyoManagerException;

    public ExternalLink checkAndRemoveExternalLink(FederationConferenceRecord federationConferenceRecord);
    
    public String createRecordForFederation(Federation federation, ExternalLink externalLink);
    public void removeRecordForFederation(ExternalLink externalLink);

    public ExternalLink getExternalLinkForUpdateStatus(String remoteSystemID, String fromConferenceName, String toConferenceName);

    public ExternalLink getExternalLinkByRequestID(String requestID);
    
    public ExternalLink getExternalLinkByRemoteMedia(String remoteMediaAddress, String remoteMediaAdditionalInfo);

    public int updateExternalLinkForMediaInfo(int externalLinkID, String requestID, String mediaAddress, String mediaAdditionalInfo);
    public int updateExternalLinkForLocalMediaInfo(int externalLinkID, String mediaAddress, String mediaAdditionalInfo);
    public int updateExternalLinkForStatus(int externalLinkID, int status);

    public int insertFederationConferenceRecord(FederationConferenceRecord federationConferenceRecord);
    public int deleteFederationConferenceRecord(String endpointGUID);

    public void sendDisconnectParticipantFromFederation(int endpointID, int roomID, boolean secure) throws RemoteException, LeaveConferenceException;
    public void receiveDisconnectParticipantFromFederation(String endpointGUID, String conferenceName) throws LeaveConferenceException;

    public void sendMuteAudioForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, MuteAudioException;
    public void receiveMuteAudioForParticipantInFederation(String endpointGUID) throws MuteAudioException;

    public void sendUnmuteAudioForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, UnmuteAudioException;
    public void receiveUnmuteAudioForParticipantInFederation(String endpointGUID) throws UnmuteAudioException;

    public void sendStopVideoForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, StopVideoException;
    public void receiveStopVideoForParticipantInFederation(String endpointGUID) throws StopVideoException;

    public void sendStartVideoForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, StartVideoException;
    public void receiveStartVideoForParticipantInFederation(String endpointGUID) throws StartVideoException;

	public void sendSilenceAudioForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, SilenceAudioException;
	public void receiveSilenceAudioForParticipantInFederation(String endpointGUID) throws SilenceAudioException;
	
	public void sendSilenceVideoForParticipantInFederation(int endpointID, boolean secure) throws RemoteException, SilenceVideoException;
	public void receiveSilenceVideoForParticipantInFederation(String endpointGUID) throws SilenceVideoException;
	
	public void createExternalLinkFromPortal(ExternalLink externalLink)
			throws NoVidyoManagerException;
	
    public ExternalLink getExternalLink(String requestID, String fromConfName, String toConfName);
    
	/**
	 * 
	 * @param externalLink
	 * @return
	 */
    public int deleteFederation(ExternalLink externalLink);
    
    /**
     * 
     * @param externalLink
     */
    public void dropRemoteConference(ExternalLink externalLink);
    
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
	 * 
	 * @param toConfName
	 * @return
	 */
	public int getExtLinksCountByToConfName(String toConfName);
	
	/**
	 * Deletes the Conference in VidyoManager.
	 * @param confName
	 * @return
	 */
	public int deleteConfInVidyoManager(String confName);
	
	/**
	 * 
	 * @param externalLink
	 * @throws NoVidyoManagerException
	 */
	public void allowExternalLink(ExternalLink externalLink)
			throws NoVidyoManagerException;
}