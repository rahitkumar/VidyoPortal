package com.vidyo.rest.gateway;

import com.vidyo.rest.base.RestRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegisterPrefixesRequest extends RestRequest implements Serializable {

	private static final long serialVersionUID = 5131850944850061998L;

	@NotNull
	@Length(min=1, max=40)
	private String gatewayId;

	@NotNull
	@Length(max=64)
	private String serviceEndpointGuid;

	@NotNull
	@Length(min=1, max=64)
	private String serviceToken;

	@Size(max=1000)
	private Prefix[] prefixes = {};

    @Size(max=100)
    private EndpointFeature[] endpointFeatures = {};

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

	public Prefix[] getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(Prefix[] prefixes) {
		this.prefixes = prefixes;
	}

    public EndpointFeature[] getEndpointFeatures() {
        return endpointFeatures;
    }

    public void setEndpointFeatures(EndpointFeature[] endpointFeatures) {
        this.endpointFeatures = endpointFeatures;
    }

	public String toString() {
		return "gatewayId=[" + StringUtils.trimToEmpty(getGatewayId()) + "]" +
				",serviceEndpointGuid=[" + StringUtils.trimToEmpty(getServiceEndpointGuid()) + "]";
	}
}
