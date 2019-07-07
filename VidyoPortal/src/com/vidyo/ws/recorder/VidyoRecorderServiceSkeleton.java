/**
 * VidyoRecorderServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package com.vidyo.ws.recorder;

import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.Service;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IServiceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoRecorderServiceSkeleton java skeleton for the axisService
 */
public class VidyoRecorderServiceSkeleton implements VidyoRecorderServiceSkeletonInterface {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(VidyoRecorderServiceSkeleton.class.getName());

    private IConferenceService conference;
    private IServiceService service;

    public void setConference(IConferenceService conference) {
        this.conference = conference;
    }

    public void setService(IServiceService service) {
        this.service = service;
    }

    /**
     * Auto generated method signature
     * Bring the Recorder Endpoint to Vidyo Portal
     *
     * @param registerRecorderEndpointRequest:
     *
     * @throws InvalidArgumentFaultException :
     * @throws GeneralFaultException         :
     */
    public com.vidyo.ws.recorder.RegisterRecorderEndpointResponse registerRecorderEndpoint
            (
                    com.vidyo.ws.recorder.RegisterRecorderEndpointRequest registerRecorderEndpointRequest
            )
            throws InvalidArgumentFaultException, GeneralFaultException
    {
        RegisterRecorderEndpointResponse resp = new RegisterRecorderEndpointResponse();

        String endpointGUID = registerRecorderEndpointRequest.getRecorderEndpointGUID().getRecorderEndpointGUID_type0();
        if (endpointGUID == null || endpointGUID.equalsIgnoreCase("")) {
            throw new InvalidArgumentFaultException("RecorderEndpointGUID is empty");
        }

        String recID = registerRecorderEndpointRequest.getRecID();
        if (recID == null || recID.equalsIgnoreCase("")) {
            throw new InvalidArgumentFaultException("RecID is empty");
        }

        String prefix = registerRecorderEndpointRequest.getPrefix();
        if (prefix == null) {
            prefix = "";
        }

        String desc = registerRecorderEndpointRequest.getDescription();
        if (desc == null) {
            desc = "Unknown recorder";
        }

        // step 1 - check VM is alive
        try {
            String server = this.conference.getVMConnectAddress();
            resp.setVMconnect(server);
        } catch (Exception e) {
            throw new GeneralFaultException(e.getMessage());
        }

        // step 2 - add RecorderEndpoint to portal
        try {
            RecorderEndpoint re = new RecorderEndpoint();
            re.setEndpointGUID(endpointGUID);
            re.setRecID(recID);
            re.setPrefix(prefix);
            re.setDescription(desc);
			re.setSequenceNum(0l);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Service service = this.service.getServiceByUserName(auth.getName(), "VidyoRecorder");

            // Clear old records (means Offline more then 5 minutes)
            int num_rec = this.service.clearRegisterRecorderEndpoint(service.getServiceID());
            logger.debug("Clear Register RecorderEndpoint in status 'Offline'. Removed number of records - " + num_rec);

            this.service.registerRecorderEndpoint(service.getServiceID(), re);

        } catch (Exception e) {
            throw new GeneralFaultException(e.getMessage());
        }

        return resp;
    }

}
    
