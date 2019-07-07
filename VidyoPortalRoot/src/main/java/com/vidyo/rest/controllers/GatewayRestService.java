package com.vidyo.rest.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.vidyo.rest.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vidyo.rest.base.RestService;
import com.vidyo.service.gateway.GatewayService;
import com.vidyo.service.gateway.request.JoinFromLegacyServiceRequest;
import com.vidyo.service.gateway.request.RegisterPrefixesServiceRequest;
import com.vidyo.service.gateway.request.SetCdrDataServiceRequest;
import com.vidyo.service.gateway.response.BaseGatewayServiceResponse;
import com.vidyo.service.gateway.response.GetVMConnectInfoServiceResponse;
import com.vidyo.service.gateway.response.JoinFromLegacyServiceResponse;
import com.vidyo.service.gateway.response.RegisterPrefixesServiceResponse;
import com.vidyo.service.gateway.response.SetCdrDataServiceResponse;

@Controller
@RequestMapping(value="/gatewayService", consumes="application/json", produces="application/json")
public class GatewayRestService extends RestService {
	protected final Logger logger = LoggerFactory.getLogger(GatewayRestService.class.getName());

	@Autowired
	private GatewayService gatewayService;

	public void setGatewayService(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	// *****************************************************************************************************************
	// REST call: /registerPrefixes.json
	// *****************************************************************************************************************
	@RequestMapping(value="/registerPrefixes", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_VIDYOGATEWAY')")
	public @ResponseBody
	RegisterPrefixesResponse registerPrefixes(@Valid @RequestBody RegisterPrefixesRequest request) {

		String gwUser = getGatewayUserAccount();
		logger.info("registerPrefixes / GW: [" + gwUser + "] " + request.toString());

		String gatewayId = request.getGatewayId();
		String serviceEndpointGuid = request.getServiceEndpointGuid();
		String serviceToken = request.getServiceToken();

		Prefix[] prefixList = request.getPrefixes();
		Set<Prefix> prefixSet = new HashSet<Prefix>();
		if (prefixList != null && prefixList.length > 0) {
			Collections.addAll(prefixSet, prefixList); // purges duplicates
		}

        EndpointFeature[] featuresList = request.getEndpointFeatures();
        Set<EndpointFeature> featuresSet = new HashSet<EndpointFeature>();
        if (featuresList != null && featuresList.length > 0) {
            Collections.addAll(featuresSet, featuresList); // purges duplicates
        }

		RegisterPrefixesServiceRequest serviceRequest = new RegisterPrefixesServiceRequest();
		serviceRequest.setGatewayUserAccount(gwUser);
		serviceRequest.setServiceEndpointGuid(serviceEndpointGuid);
		serviceRequest.setServiceToken(serviceToken);
		serviceRequest.setGatewayId(gatewayId);
		serviceRequest.setPrefixes(prefixSet);
        serviceRequest.setEndpointFeatures(featuresSet);

		RegisterPrefixesResponse response = new RegisterPrefixesResponse();
			RegisterPrefixesServiceResponse serviceResponse = gatewayService.registerPrefixes(serviceRequest);
			String vmConnectInfo = serviceResponse.getVmConnect();
			response.setVmConnect(vmConnectInfo);

		if (serviceResponse.getStatus() == BaseGatewayServiceResponse.FEATURE_NOT_RELEASED) {
			response.setStatus("501", "Feature not available on this portal.");
		} else if (serviceResponse.getStatus() == RegisterPrefixesServiceResponse.VIDYO_MANAGER_UNAVAILABLE) {
			response.setStatus("503","Internal error - Unable to connect to VidyoManager");
		}

		return response;
	}

	// *****************************************************************************************************************
	// REST call: /getVMConnectInfo.json
	// *****************************************************************************************************************
	@RequestMapping(value="/getVMConnectInfo", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_VIDYOGATEWAY')")
	public @ResponseBody
	GetVMConnectInfoResponse getVMConnectInfo() {

		GetVMConnectInfoServiceResponse serviceResponse = gatewayService.getVmConnect();
		GetVMConnectInfoResponse response = new GetVMConnectInfoResponse();
		response.setVmConnect(serviceResponse.getVmConnect());

		if (serviceResponse.getStatus() == BaseGatewayServiceResponse.FEATURE_NOT_RELEASED) {
			response.setStatus("501", "Feature not available on this portal.");
		} else if (serviceResponse.getStatus() == RegisterPrefixesServiceResponse.VIDYO_MANAGER_UNAVAILABLE) {
			response.setStatus("503","Internal error - Unable to connect to VidyoManager");
		}

		return response;
	}

	// *****************************************************************************************************************
	// REST call: /joinFromLegacy.json
	// *****************************************************************************************************************
	@RequestMapping(value="/joinFromLegacy", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_VIDYOGATEWAY')")
	public @ResponseBody
	JoinFromLegacyResponse joinFromLegacy(@Valid @RequestBody JoinFromLegacyRequest request) {

		String gwUser = getGatewayUserAccount();
		logger.info("joinFromLegacy / GW: [" + gwUser + "] " + request.toString());

		String gwEndpointGuid = request.getEndpointGuid();
		String fromDisplayName = request.getFromDisplayName();
		String dialString = request.getDialString();
		String pin = request.getPin();
		String prefix = request.getPrefix();
		String tenantName = request.getTenant();
		boolean directCallFlag = request.isDirectCallFlag();
		String deviceModel = request.getDeviceModel();
		String endpointPublicIpAddress = request.getEndpointPublicIpAddress();

		JoinFromLegacyServiceRequest serviceRequest = new JoinFromLegacyServiceRequest();
		serviceRequest.setGatewayUserAccount(gwUser);
		serviceRequest.setGwEndpointGuid(gwEndpointGuid);
		serviceRequest.setFromDisplayName(fromDisplayName);
		serviceRequest.setDialString(dialString);
		serviceRequest.setPin(pin);
		serviceRequest.setPrefix(prefix);
		serviceRequest.setUserTenantName(tenantName);
		serviceRequest.setDirectCallFlag(directCallFlag);
		serviceRequest.setDeviceModel(deviceModel);
		serviceRequest.setEndpointPublicIpAddress(endpointPublicIpAddress);

		JoinFromLegacyServiceResponse serviceResponse = this.gatewayService.joinFromLegacy(serviceRequest);

		JoinFromLegacyResponse response = new JoinFromLegacyResponse();
		response.setJoinStatus("FAIL");

		if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.SUCCESS) {
			response.setJoinStatus(serviceResponse.getJoinStatus());
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.P2P_OFFLINE) {
			response.setStatus("1000", "Offline");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.P2P_BUSY) {
			response.setStatus("1001", "Busy");
		}  else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.INVALID_TO_NAME_EXT) {
			response.setStatus("404", "Invalid dialString");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.ROOM_LOCKED) {
			response.setStatus("423", "Room is locked");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.ROOM_DISABLED) {
			response.setStatus("427", "Room is disabled");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.ROOM_FULL) {
            response.setStatus("425", "Room capacity is reached");
        } else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.ROOM_PIN_INVALID) {
			response.setStatus("401", "PIN is wrong");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.ROOM_PIN_REQUIRED) {
			response.setStatus("403", "PIN is empty");
        } else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.CANNOT_CALL_TENANT) {
            response.setStatus("500", "Cannot call this tenant");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.FAILED_TO_CONNECT_CALL) {
			response.setStatus("500", "Failed to complete call due to internal error.");
		} else if (serviceResponse.getStatus() == BaseGatewayServiceResponse.FEATURE_NOT_RELEASED) {
			response.setStatus("501", "Feature not available on this portal.");
		} else if (serviceResponse.getStatus() == JoinFromLegacyServiceResponse.NO_LINES_AVAILABLE) {
			response.setStatus("512", "No lines available.");
		}

		return response;
	}

	// *****************************************************************************************************************
	// REST call: /setCdrData.json
	//
	// This is called by gw after portal calls gw (joinToLegacy.json)
	// *****************************************************************************************************************
	@RequestMapping(value="/setCdrData", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_VIDYOGATEWAY')")
	public @ResponseBody
	SetCdrDataResponse setCdrData(@Valid @RequestBody SetCdrDataRequest request) {

		String gwUser = getGatewayUserAccount();
		logger.info("setCdrData / GW: [" + gwUser + "] " + request.toString());

		String gwEndpointGuid = request.getEndpointGuid();
		String deviceModel = request.getDeviceModel();
		String endpointPublicIpAddress = request.getEndpointPublicIpAddress();

		SetCdrDataServiceRequest serviceRequest = new SetCdrDataServiceRequest();
		serviceRequest.setDeviceModel(deviceModel);
		serviceRequest.setEndpointGuid(gwEndpointGuid);
		serviceRequest.setEndpointPublicIpAddress(endpointPublicIpAddress);

		SetCdrDataServiceResponse serviceResponse = this.gatewayService.setCdrData(serviceRequest);

		SetCdrDataResponse response = new SetCdrDataResponse();
		if (serviceResponse.getStatus() == BaseGatewayServiceResponse.FEATURE_NOT_RELEASED) {
			response.setStatus("501", "Feature not available on this portal.");
		} else if (serviceResponse.getRowsUpdated() == 0) {
			response.setStatus("404", "Endpoint not found.");
		}
		return response;
	}

	/**
	 * Returns the gateway user for the authenticated gateway.
	 *
	 */
	private String getGatewayUserAccount() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

}
