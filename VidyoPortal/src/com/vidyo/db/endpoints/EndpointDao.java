/**
 * 
 */
package com.vidyo.db.endpoints;

import java.util.List;
import java.util.Map;

import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointFilter;
import com.vidyo.service.endpoints.EndpointService;

/**
 * @author Ganesh
 * 
 */
public interface EndpointDao {

	/**
	 * 
	 * @param endpointFilter
	 * @param tenantID
	 * @return
	 */
	public List<Endpoint> getVidyoRooms(EndpointFilter endpointFilter, int tenantID);

	/**
	 * 
	 * @param endpointFilter
	 * @param tenantID
	 * @return
	 */
	public int getVidyoRoomsCount(EndpointFilter endpointFilter, int tenantID);

	/**
	 * Returns the Endpoint Details for GUID
	 * 
	 * @param endpointGUID
	 * @return
	 */
	public Endpoint getEndpointDetails(String endpointGUID);

	/**
	 * Return the Endpoint/VirtualEndpoint/RecordeEndpoint status & type
	 * 
	 * @param endpointGUID
	 * @return
	 */
	public Endpoint getEndpointDetail(String endpointGUID);

	/**
	 * Returns Endpoint's status. Value will be returned only if the user is bound to EP.
	 * 
	 * @param guid
	 * @param tenantId
	 * @return
	 */
	public Endpoint getEndpointStatus(String guid, int memberId);

	/**
	 * Updates Line Consumption Flag for Endpoint. This API has to be invoked only from {@link EndpointService}
	 * updateLineConsumption method
	 * 
	 * @param guid
	 * @param memberId
	 * @param consumesLine
	 * @return
	 */
	public int updateLineConsumption(String guid, int memberId, boolean consumesLine);


	public void deleteRegularEndpoint(String guid);

	public int updateGatewayEndpointCDR(String gwEndpointGuid, String deviceModel, String endpointPublicIpAddress);

	public VirtualEndpoint getVirtualEndpointForEndpointGUID(String guid);

	public int clearRegisterVirtualEndpoint();

	public int updateVirtualEndpointStatus(String guid, int status);

    public EndpointFeatures getEndpointFeaturesForGuid(String guid);

    public int updateEndpointFeatures(String guid, EndpointFeatures features);

    public int updateEndpointFeaturesForGuest(int guestID, String guid, EndpointFeatures features);
    
    /**
     * Resets the external data associated with the previous call which endpoint was on.
     * @param endpointGUID
     * @return
     */
    public int resetExtData(String endpointGUID);

    /**
     * 
     * @param tenantId
     * @param endpointGUIDs
     * @return
     */
	public List<Map<String, Object>> findPublicIpAndMemberId (int tenantId, List<String> endpointGUIDs);
	
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