package com.vidyo.rest.clients.gateway;

import com.vidyo.bo.Member;
import com.vidyo.bo.Service;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.rest.gateway.JoinToLegacyRequest;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IServiceService;
import com.vidyo.vcap20.Message;
import com.vidyo.vcap20.RootDocument;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class GatewayVcapClient {
	protected final Logger logger = LoggerFactory.getLogger(GatewayVcapClient.class.getName());

	private IServiceService servicesService;

	public IServiceService getServicesService() {
		return servicesService;
	}

	public void setServicesService(IServiceService servicesService) {
		this.servicesService = servicesService;
	}

	public String getVirtualGUIDForJoinToLegacy(GatewayPrefix gatewayPrefix, int tenantID, Member toMember) {

		String virtualEndpointGUID = UUID.randomUUID().toString();

		// save to db
		VirtualEndpoint ve = new VirtualEndpoint();
		ve.setEndpointGUID(virtualEndpointGUID);
		ve.setServiceID(gatewayPrefix.getServiceID());
		ve.setGatewayID(gatewayPrefix.getGatewayID());
		ve.setTenantID(tenantID);
		ve.setPrefix(gatewayPrefix.getPrefix());
		ve.setDisplayName(toMember.getMemberName());
		ve.setDisplayExt(toMember.getRoomExtNumber());
		ve.setEntityID(toMember.getRoomID());
		ve.setDirection(1); // toLegacy

		logger.info("adding new virtual endpoint: " + virtualEndpointGUID );
		this.servicesService.registerVirtualEndpoint(gatewayPrefix.getServiceID(), ve);

		return virtualEndpointGUID;
	}
}
