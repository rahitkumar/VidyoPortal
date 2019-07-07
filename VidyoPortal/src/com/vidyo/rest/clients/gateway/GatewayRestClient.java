package com.vidyo.rest.clients.gateway;

import com.vidyo.bo.Service;
import com.vidyo.rest.gateway.JoinToLegacyRequest;
import com.vidyo.rest.gateway.JoinToLegacyResponse;
import com.vidyo.service.IServiceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class GatewayRestClient {

	protected final Logger logger = LoggerFactory.getLogger(GatewayRestClient.class.getName());

	private IServiceService servicesService;

	private RestTemplate restTemplate; // thread safe!

	public IServiceService getServicesService() {
		return servicesService;
	}

	public void setServicesService(IServiceService servicesService) {
		this.servicesService = servicesService;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getVirtualGUIDForJoinToLegacy(int gwServiceId,
												String gwPrefix,
												String fromUsername,
												String fromExt,
												String toExt) {

		Service gwService = this.servicesService.getVG(gwServiceId);
		String url = gwService.getUrl();
		String token = gwService.getToken();

		JoinToLegacyRequest request = new JoinToLegacyRequest();
		request.setPrefix(gwPrefix);
		request.setFromUserName(fromUsername);
		request.setFromExtNumber(fromExt);
		request.setToExtension(toExt);
		request.setToken(token);
		try {
			JoinToLegacyResponse response = this.restTemplate.postForObject(url, request, JoinToLegacyResponse.class);
			return StringUtils.trimToEmpty(response.getEndpointGuid());
		} catch (RestClientException rce) {
			logger.warn("error contacting gw: " + url + ", error: " + rce.getMessage());
		}

		return "";
	}

}
