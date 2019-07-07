package com.vidyo.service.gateway;

import com.vidyo.service.gateway.request.JoinFromLegacyServiceRequest;
import com.vidyo.service.gateway.request.RegisterPrefixesServiceRequest;
import com.vidyo.service.gateway.request.SetCdrDataServiceRequest;
import com.vidyo.service.gateway.response.GetVMConnectInfoServiceResponse;
import com.vidyo.service.gateway.response.JoinFromLegacyServiceResponse;
import com.vidyo.service.gateway.response.RegisterPrefixesServiceResponse;
import com.vidyo.service.gateway.response.SetCdrDataServiceResponse;

public interface GatewayService {

	public RegisterPrefixesServiceResponse  registerPrefixes(RegisterPrefixesServiceRequest request);

	public GetVMConnectInfoServiceResponse getVmConnect();

	public JoinFromLegacyServiceResponse joinFromLegacy(JoinFromLegacyServiceRequest request);

	public SetCdrDataServiceResponse setCdrData(SetCdrDataServiceRequest request);

}
