package com.vidyo.superservice.rest.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.rest.base.RestResponse;
import com.vidyo.rest.base.RestService;
import com.vidyo.rest.base.Status;
import com.vidyo.rest.status.ClusterInfoResponse;
import com.vidyo.rest.status.ClusterStatusInfo;
import com.vidyo.service.status.VidyoPortalStatusService;

@Controller
@RequestMapping(value = "/VidyoPortalStatusService")
public class VidyoPortalStatusController
extends RestService {

    private final Logger logger = LoggerFactory.getLogger((String)VidyoPortalStatusController.class.getName());

    @Autowired
    private VidyoPortalStatusService statusService;

    @RequestMapping(value= "/getPortalStatus", method = RequestMethod.GET,
    		produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public RestResponse getHotStandByStatus() {
        this.logger.warn("inside LoadBalance Rest service-getHotStandByStatus");
        ClusterInfoResponse resp = new ClusterInfoResponse();
        ClusterInfo clusterInfo = this.statusService.getHotStandByStatus();
        if (clusterInfo == null) {
            throw new RuntimeException("Error while excecuting the script ");
        }
        resp.setMyStatus(clusterInfo.getMyStatus());
        resp.setLastDBSync(clusterInfo.getLastDBSync());
        resp.setLastPingOK(clusterInfo.getLastPingOK());
        Status status = new Status();
        status.setCode("200");
        status.setMessage("Success");
        resp.setStatus(status);
        return resp;
    }
    
    @RequestMapping(value = "/clusterRegistration", method = RequestMethod.POST,
    		consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
    				MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public RestResponse clusterRegistration( @RequestBody  Map<String, Object> mapKeys) {
    	
    	// ClusterInfoResponse resp = new ClusterInfoResponse();
         
    	Map<String, String> clusterMap= statusService.clusterRegistration(mapKeys);
    	ClusterStatusInfo clusterStatusInfo=new ClusterStatusInfo();
    	clusterStatusInfo.setClusterMap(clusterMap);
         Status status = new Status();
         status.setCode("200");
         status.setMessage("Success");
         clusterStatusInfo.setStatus(status);
         return clusterStatusInfo;
    }
    
    @RequestMapping(value = "/clusterRegistrationPassThrough", 
    		consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
    				MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    @ResponseBody
    public RestResponse clusterRegistration( @RequestBody  String mapKeys) {
    	
    	// ClusterInfoResponse resp = new ClusterInfoResponse();
         
    	Map<String, String> clusterMap= statusService.clusterRegistration(mapKeys);
    	ClusterStatusInfo clusterStatusInfo=new ClusterStatusInfo();
    	clusterStatusInfo.setClusterMap(clusterMap);
         Status status = new Status();
         status.setCode("200");
         status.setMessage("Success");
         clusterStatusInfo.setStatus(status);
         return clusterStatusInfo;
    }
    
    @RequestMapping(value = "/clusterRegistrationPassThroughString", 
    		consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
    				MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    @ResponseBody
    public String clusterRegistrationPassThroughString( @RequestBody  String mapKeys) {
    	
    	// ClusterInfoResponse resp = new ClusterInfoResponse();
         
    	Map<String, String> clusterMap= statusService.clusterRegistration(mapKeys);
    	ClusterStatusInfo clusterStatusInfo=new ClusterStatusInfo();
    	clusterStatusInfo.setClusterMap(clusterMap);
         Status status = new Status();
         status.setCode("200");
         status.setMessage("Success");
         clusterStatusInfo.setStatus(status);
         return "nodeId=123";
    }
}
