package com.vidyo.service.gateway.request;

import com.vidyo.rest.gateway.EndpointFeature;
import com.vidyo.rest.gateway.Prefix;

import java.util.Set;

public class RegisterPrefixesServiceRequest {

	private String gatewayUserAccount;
	private String gatewayId;
	private String serviceEndpointGuid;
	private String serviceToken;
	private Set<Prefix> prefixes;
    private Set<EndpointFeature> endpointFeatures;

	public String getGatewayUserAccount() {
		return gatewayUserAccount;
	}

	public void setGatewayUserAccount(String gatewayUserAccount) {
		this.gatewayUserAccount = gatewayUserAccount;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getServiceEndpointGuid() {
		return serviceEndpointGuid;
	}

	public void setServiceEndpointGuid(String serviceEndpointGuid) {
		this.serviceEndpointGuid = serviceEndpointGuid;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public Set<Prefix> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(Set<Prefix> prefixes) {
		this.prefixes = prefixes;
	}

    public Set<EndpointFeature> getEndpointFeatures() {
        return endpointFeatures;
    }

    public void setEndpointFeatures(Set<EndpointFeature> endpointFeatures) {
        this.endpointFeatures = endpointFeatures;
    }
}
