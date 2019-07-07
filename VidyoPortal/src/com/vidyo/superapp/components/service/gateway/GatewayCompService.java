/**
 * 
 */
package com.vidyo.superapp.components.service.gateway;

import java.util.List;

import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.gateway.GatewayPrefix;

/**
 * @author ganesh
 *
 */
public interface GatewayCompService {

	/**
	 * Returns VidyoGateway details by ComponentId
	 * 
	 * @param compId
	 * @return
	 */
	public VidyoGateway getGatewayByCompId(int compId);

	
	/**
	 * 
	 * @param vidyoGateway
	 * @return
	 */
	public VidyoGateway saveGateway(VidyoGateway vidyoGateway);
	
	/**
	 * 
	 * @param gatewayId
	 * @return
	 */
	public List<GatewayPrefix> getGatewayPrefixesById(int gatewayId);

}
