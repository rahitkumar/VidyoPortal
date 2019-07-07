/**
 * 
 */
package com.vidyo.service.endpoints;

import java.util.List;

import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointFilter;
import com.vidyo.db.endpoints.EndpointFeatures;
import com.vidyo.service.endpoints.response.UpdateEndpointResponse;

/**
 * @author Ganesh
 * 
 */
public interface EndpointService {

	/**
	 * 
	 * @param endpointFilter
	 * @return
	 */
	public List<Endpoint> getVidyoRooms(EndpointFilter endpointFilter);
	
	/**
	 * 
	 * @param endpointFilter
	 * @return
	 */
	public int getVidyoRoomsCount(EndpointFilter endpointFilter);	

	/**
	 * Returns the Endpoint Details for GUID
	 * @param endpointGUID
	 * @return
	 */
	public Endpoint getEndpointDetails(String endpointGUID);
	
	/**
	 * Return the Endpoint/VirtualEndpoint/RecordeEndpoint status & type
	 * @param endpointGUID
	 * @return
	 */
	public Endpoint getEndpointDetail(String endpointGUID);
	
	/**
	 * 
	 * @param guid
	 * @param memberId
	 * @return
	 */
	public Endpoint getEndpointStatus(String guid, int memberId);

	/**
	 * 
	 * @param guid
	 * @param memberId
	 * @param consumesLine
	 * @return
	 */
	public UpdateEndpointResponse updateLineConsumption(String guid, int memberId, boolean consumesLine);

	public void deleteRegularEndpoint(String guid);

	public int updateGatewayEndpointCDR(String gwEndpointGuid, String deviceModel, String endpointPublicIpAddress);

	public VirtualEndpoint getVirtualEndpointForEndpointGUID(String guid);

	/**
	 * Deletes inactive virtual endpoints
	 * @return
	 */
	public int clearRegisterVirtualEndpoint();

	public int updateVirtualEndpointStatus(String guid, int status);

    public EndpointFeatures getEndpointFeaturesForGuid(String guid);

    public int updateEndpointFeatures(String guid, EndpointFeatures features);

    public int updateEndpointFeaturesForGuest(int guestID, String endpointGUID, EndpointFeatures endpointFeatures);
    
    /**
     * Resets the external data associated with the previous call which endpoint was on
     * @param endpointGUID
     * @return
     */
    public int resetExtData(String endpointGUID);
    
	/**
	 * Updates the Endpoint's public address column with the provided latest remote
	 * address
	 * 
	 * @param endpointGUID
	 *            endpoint whose ip address to be updated
	 * @param publicIpAddress
	 *            ip value of the endpoint
	 * @return updated rows count
	 */
	public int updatePublicIPAddress(String endpointGUID, String publicIPAddress);
}