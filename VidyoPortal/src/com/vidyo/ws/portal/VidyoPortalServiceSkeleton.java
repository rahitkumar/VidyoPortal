/**
 * VidyoPortalServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.ws.portal;

import com.vidyo.bo.ExternalLink;
import com.vidyo.bo.ConferenceInfo;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IFederationConferenceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.exceptions.ConferenceNotExistException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.vcap20.*;
import org.apache.axis2.databinding.types.UnsignedLong;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import javax.activation.DataHandler;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoPortalServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalServiceSkeleton implements VidyoPortalServiceSkeletonInterface {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(VidyoPortalServiceSkeleton.class.getName());

    private IConferenceService conference;
    private IFederationConferenceService federation;
    
    private ISystemService systemService;

    public void setConference(IConferenceService conference) {
        this.conference = conference;
    }

    public void setFederation(IFederationConferenceService federation) {
        this.federation = federation;
    }

    /**
	 * @param systemService the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	/**
     * Auto generated method signature
     * Event about starting of VidyoManager
     *
     * @param startVidyoManagerRequest:
     * @throws GeneralFaultException :
     */
    public com.vidyo.ws.portal.StartVidyoManagerResponse startVidyoManager
            (
                    com.vidyo.ws.portal.StartVidyoManagerRequest startVidyoManagerRequest
            )
            throws GeneralFaultException
    {
        StartVidyoManagerResponse resp = new StartVidyoManagerResponse();

        resp.setStatus(Status_type1.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Request from Endpoint to Vidyo Portal using VCAProtocol
     *
     * @param infoFromEndpointRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.portal.InfoFromEndpointResponse infoFromEndpoint
            (
                    com.vidyo.ws.portal.InfoFromEndpointRequest infoFromEndpointRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        InfoFromEndpointResponse resp = new InfoFromEndpointResponse();

        String fromEndpointGUID = infoFromEndpointRequest.getEndpointGUID().getEndpointGUID_type0();
        DataHandler req_msg = infoFromEndpointRequest.getContent();

		UnsignedLong sequenceNum =  infoFromEndpointRequest.getSequenceNum();
		long newSequenceNum = 0l;
		if (sequenceNum != null) {
			newSequenceNum = sequenceNum.longValue();
		}

        // parse a VCAP request message
        RootDocument req_doc = null;
        try {
            req_doc = RootDocument.Factory.parse(req_msg.getInputStream(), new XmlOptions().setLoadSubstituteNamespaces(Collections.singletonMap("","http://www.vidyo.com/VCAP20.xsd")));

            Message mess = req_doc.getRoot();
            ResponseMessage rmess = mess.getResponse();

            // ResponseCode
            String respCode = rmess.getResponseCode().toString();
            if (respCode.equalsIgnoreCase("OK")) {
                logger.debug("VCAP ResponseCode -> " + respCode);
            } else {
                logger.debug("VCAP ResponseCode -> " + respCode);
            }

            // ReasonCode
            if (rmess.isSetReasonCode()) {
                String reasonCode = rmess.getReasonCode();
                logger.debug("VCAP ReasonCode -> " + respCode);
            }

            // ReasonText
            if (rmess.isSetReasonText()) {
                String reasonText = rmess.getReasonText();
                logger.debug("VCAP ReasonText -> " + reasonText);
            }

            // BindUserResponse
            if (rmess.isSetBindUser()) {
                BindUserResponse bindUser = rmess.getBindUser();
                long bindUserResponseID = rmess.getRequestID();
                String challengeResponse = bindUser.getChallengeResponse();
                this.conference.checkBindUserChallengeResponse(fromEndpointGUID, challengeResponse, bindUserResponseID);
                logger.debug("VCAP BindUserResponse -> challengeResponse -> " + challengeResponse + " RequestID -> " + bindUserResponseID);
            }

            // UnbindUserResponse
            if (rmess.isSetUnbindUser()) {
                UnbindUserResponse unbindUser = rmess.getUnbindUser();
                logger.debug("VCAP UnbindUserResponse");
            }

            // MediaControlResponse
            MediaControlResponse mediaControl = rmess.getMediaControl();
            if (mediaControl != null) {
                logger.debug("VCAP MediaControlResponse");
            }

        } catch (XmlException e) {
            throw new GeneralFaultException(e.getMessage());
        } catch (IOException e) {
            throw new GeneralFaultException(e.getMessage());
        }

        // create a VCAP response message
/*
        RootDocument resp_doc = RootDocument.Factory.newInstance();
        Message message = resp_doc.addNewRoot();
        ResponseMessage resp_message = message.addNewResponse();
        resp_message.setResponseCode(ResponseCodeType.OK);

        String resp_content = resp_doc.xmlText();

        DataHandler resp_msg = new DataHandler(resp_content, "text/plain");
*/
        DataHandler resp_msg = new DataHandler("", "text/plain");

        resp.setContent(resp_msg);
        return resp;
    }


    /**
     * Auto generated method signature
     * Updates the endpoint status to Vidyo Portal
     *
     * @param updateEndpointStatusRequest:
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.portal.UpdateEndpointStatusResponse updateEndpointStatus
            (
                    com.vidyo.ws.portal.UpdateEndpointStatusRequest updateEndpointStatusRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        UpdateEndpointStatusResponse resp = new UpdateEndpointStatusResponse();

        EndpointGUID_type0 GUID = updateEndpointStatusRequest.getEndpointGUID();
        State_type1 status = updateEndpointStatusRequest.getState();
        ConferenceInfoType conferenceInfoType = updateEndpointStatusRequest.getConferenceInfo();
		UpdateReason_type1 updateReason = updateEndpointStatusRequest.getUpdateReason();
		String participantID = updateEndpointStatusRequest.getParticipantID();

        ConferenceInfo conferenceInfo = null;
        if(conferenceInfoType != null) {
            conferenceInfo = new ConferenceInfo();
            conferenceInfo.setConferenceID(conferenceInfoType.getConferenceID());
            conferenceInfo.setGroupID(conferenceInfoType.getGroupID());
            conferenceInfo.setGroupName(conferenceInfoType.getGroupName());
            conferenceInfo.setVRID(conferenceInfoType.getVRID());
            conferenceInfo.setVRName(conferenceInfoType.getVRName());
        }

		UnsignedLong sequenceNum =  updateEndpointStatusRequest.getSequenceNum();
		long newSequenceNum = 0l;
		if (sequenceNum != null) {
			newSequenceNum = sequenceNum.longValue();
		}

		logger.info("GUID Status Reason {} {} {}", GUID.getEndpointGUID_type0(), status.getValue(), updateReason.getValue());
        this.conference.updateEndpointStatus(GUID.getEndpointGUID_type0(), status.getValue(), "VM", conferenceInfo, newSequenceNum, updateReason.getValue(), participantID);

        resp.setStatus(Status_type1.OK);
        return resp;
    }

    /**
     * Auto generated method signature
     * Updates the external link status to Vidyo Portal
     *
     * @param updateExternalLinkStatusRequest:
     *
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.portal.UpdateExternalLinkStatusResponse updateExternalLinkStatus
            (
                    com.vidyo.ws.portal.UpdateExternalLinkStatusRequest updateExternalLinkStatusRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        UpdateExternalLinkStatusResponse resp = new UpdateExternalLinkStatusResponse();

        String state = updateExternalLinkStatusRequest.getRemoteState().getValue();

        String remoteSystemID = updateExternalLinkStatusRequest.getRemoteEntityID();
        String fromConferenceName = updateExternalLinkStatusRequest.getRemoteConferenceID();
        String toConferenceName = updateExternalLinkStatusRequest.getLocalConferenceID();
        
        String mySystemID = systemService.getVidyoManagerID();

        // Step 1 - retrive record from Federations DB table
        ExternalLink externalLink = this.federation.getExternalLinkForUpdateStatus(remoteSystemID, fromConferenceName, toConferenceName);
	    if (externalLink != null) {

	        // On "Disconnected" status - update Media Info and initiate Exchange process
	        if (state.equalsIgnoreCase(RemoteState_type1._Disconnected)) {
	            String localMediaAddress = updateExternalLinkStatusRequest.getLocalMediaAddress();
	            externalLink.setLocalMediaAddress(localMediaAddress);
	            String localMediaAdditionalInfo = updateExternalLinkStatusRequest.getLocalMediaAdditionalInfo();
	            externalLink.setLocalMediaAdditionalInfo(localMediaAdditionalInfo);

	            this.federation.updateExternalLinkForStatus(externalLink.getExternalLinkID(), 1); // status "Disconnected" = 1
	            if (externalLink.getStatus() == 0) { // status "Created" = 0
		            //TODO - make 1 DB call
		            this.federation.updateExternalLinkForLocalMediaInfo(externalLink.getExternalLinkID(), localMediaAddress, localMediaAdditionalInfo);	            	
	                try {                	
	                    // Remote/From Portal sending media info to the host portal
	                	if(mySystemID.equals(externalLink.getFromSystemID())) {
	                		//Remote sending media info to host and knows http scheme from JoinRemoteConference call
	                		logger.debug("Remote sending media info to Host portal {}", ReflectionToStringBuilder.toString(externalLink, ToStringStyle.MULTI_LINE_STYLE));
	                		this.federation.sendMediaInfoToRemote(externalLink, externalLink.getSecure() != null && externalLink.getSecure().equalsIgnoreCase("Y"));
	                	} else {
	                        // allow external link - fix to make only one call
	                        try {
	                        	logger.debug("Host sending allow external link to VM {}", ReflectionToStringBuilder.toString(externalLink, ToStringStyle.MULTI_LINE_STYLE));
	                            federation.allowExternalLink(externalLink);
	                        } catch (NoVidyoManagerException e) {
	                            logger.error("Allow External Link Failed - ", e.getMessage());
	                            
	                            
	                        }	                		
	                		//Host sending MediaInfo to Remote - 1st message, so doesn't know the scheme
		                    try {
		                    	logger.debug("Host sending media info to Remote portal {}", ReflectionToStringBuilder.toString(externalLink, ToStringStyle.MULTI_LINE_STYLE));
								this.federation.sendMediaInfoToRemote(externalLink, false);
							} catch (RemoteException e) {
								this.federation.sendMediaInfoToRemote(externalLink, true);
								logger.warn("Sending the message over secure protocol - ", e.getMessage());
							}	                		
	                	}
					} catch (RemoteException e) {
						logger.error("Error while sending media info - ", e.getMessage());
	                } catch (ConferenceNotExistException e) {
	                	logger.error("Error while sending media info - ", e.getMessage());
	                }
	            } else {
	            	logger.debug("ExternalLink getStatus non zero case {}", ReflectionToStringBuilder.toString(externalLink, ToStringStyle.MULTI_LINE_STYLE));
	            }
	        }

	        // On "Connected" status - add Endpoint to conference in future
	        if (state.equalsIgnoreCase(RemoteState_type1._Connected)) {
	            this.federation.updateExternalLinkForStatus(externalLink.getExternalLinkID(), 2); // status "Connected" = 2
	        }

        } else {
		    logger.error("Cannot find record in ExternalLinks for parameters - " + remoteSystemID + " " + fromConferenceName + " " + toConferenceName);
        }

        resp.setStatus(Status_type1.OK);
        return resp;
    }

	/**
	 * Auto generated method signature
	 * Event about adding a spontaneous endpoint
	 * @param addSpontaneousEndpointRequest4
	 * @return addSpontaneousEndpointResponse5
	 * @throws InvalidArgumentFaultException
	 * @throws GeneralFaultException
	 */

	public com.vidyo.ws.portal.AddSpontaneousEndpointResponse addSpontaneousEndpoint
	(
			com.vidyo.ws.portal.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest4
	)
			throws InvalidArgumentFaultException,GeneralFaultException{


		if (logger.isDebugEnabled()) {
			logger.debug("RECEIVED CALL TO : addSpontaneousEndpoint");
			logger.debug(" CFID:" + addSpontaneousEndpointRequest4.getConferenceID());
			logger.debug(" GUID:" + addSpontaneousEndpointRequest4.getEndpointID());
			logger.debug(" PTID:" + addSpontaneousEndpointRequest4.getParticipantID());
			logger.debug(" RURI:" + addSpontaneousEndpointRequest4.getRouterAddressUri());
			logger.debug(" R ID:" + addSpontaneousEndpointRequest4.getRouterID());
		}

		AddSpontaneousEndpointResponse resp = new AddSpontaneousEndpointResponse();

		resp.setStatus(Status_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * Updates the VidyoRouter endpoint status to Vidyo Portal
	 * @param updateRouterEndpointStatusRequest:
	 * @return updateRouterEndpointStatusResponse:
	 * @throws InvalidArgumentFaultException:
	 * @throws GeneralFaultException:
	 */
	public com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse updateRouterEndpointStatus
		(
				com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest updateRouterEndpointStatusRequest
		)
			throws InvalidArgumentFaultException,GeneralFaultException
	{
		UpdateRouterEndpointStatusResponse resp = new UpdateRouterEndpointStatusResponse();

		if (logger.isDebugEnabled()) {
			logger.debug("RECEIVED CALL TO : updateRouterEndpointStatus");

			logger.debug(" VRID: " + updateRouterEndpointStatusRequest.getVRID());
			logger.debug(" CFID: " + updateRouterEndpointStatusRequest.getConferenceID());
			logger.debug(" GUID: " + updateRouterEndpointStatusRequest.getEndpointGUID());
			logger.debug(" JNDF: " + updateRouterEndpointStatusRequest.getJoined());
		}

		resp.setStatus(Status_type1.OK);
		return resp;
	}

}
    
