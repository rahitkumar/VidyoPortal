/**
 * 
 */
package com.vidyo.service.endpoints;

import java.util.List;

import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.db.endpoints.EndpointFeatures;
import com.vidyo.framework.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointFilter;
import com.vidyo.db.endpoints.EndpointDao;
import com.vidyo.service.endpoints.response.UpdateEndpointResponse;

/**
 * @author Ganesh
 * 
 */
public class EndpointServiceImpl implements EndpointService {
	
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(EndpointServiceImpl.class);

	/**
	 * 
	 */
	private EndpointDao endpointDao;


	/**
	 * @param endpointDao
	 *            the endpointDao to set
	 */
	public void setEndpointDao(EndpointDao endpointDao) {
		this.endpointDao = endpointDao;
	}

	/**
	 * 
	 * @param endpointFilter
	 * @return
	 */
	@Override
	public List<Endpoint> getVidyoRooms(EndpointFilter endpointFilter) {
		return endpointDao.getVidyoRooms(endpointFilter, TenantContext.getTenantId());
	}

	/**
	 * 
	 * @param endpointFilter
	 * @return
	 */
	@Override
	public int getVidyoRoomsCount(EndpointFilter endpointFilter) {
		return endpointDao.getVidyoRoomsCount(endpointFilter, TenantContext.getTenantId());
	}

	/**
	 * Returns the Endpoint Details for GUID
	 * 
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public Endpoint getEndpointDetails(String endpointGUID) {
		Endpoint endpoint = endpointDao.getEndpointDetails(endpointGUID);
		return endpoint;
	}

	/**
	 * Return the Endpoint/VirtualEndpoint/RecordeEndpoint status & type
	 * 
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public Endpoint getEndpointDetail(String endpointGUID) {
		Endpoint endpoint = endpointDao.getEndpointDetail(endpointGUID);
		return endpoint;
	}
	
	@Override
	public Endpoint getEndpointStatus(String guid, int memberId) {
		Endpoint endpoint = endpointDao.getEndpointStatus(guid, memberId);
		return endpoint;
	}


	@Override
	public UpdateEndpointResponse updateLineConsumption(String guid, int memberId, boolean consumesLine) {
		Endpoint endpoint = endpointDao.getEndpointStatus(guid, memberId);
		UpdateEndpointResponse updateEndpointResponse = new UpdateEndpointResponse();

		// Return error if user is not bound to Endpoint
		if (endpoint == null) {
			updateEndpointResponse.setStatus(UpdateEndpointResponse.ENDPOINT_NOT_BOUND);
			updateEndpointResponse.setMessageId("endpoint.not.bound");
			return updateEndpointResponse;
		}

		// Return error if Endpoint is offline
		if (endpoint.getStatus() == 0) {
			updateEndpointResponse.setStatus(UpdateEndpointResponse.ENDPOINT_OFFLINE);
			updateEndpointResponse.setMessageId("endpoint.offline");
			return updateEndpointResponse;
		}

		// Validations complete, update the line consumption flag in database
		int count = endpointDao.updateLineConsumption(guid, memberId, consumesLine);
		if (count <= 0) {
			logger.error("Update line consumption did not affect any rows. guid -{}, memberId -{}, consumesLine -{}",
					guid, memberId, consumesLine);
			updateEndpointResponse.setMessageId("update.failed");
			updateEndpointResponse.setStatus(UpdateEndpointResponse.UPDATE_LINE_CONSUMPTION_FAILED);
		}
		return updateEndpointResponse;
	}

	@Override
	public void deleteRegularEndpoint(String guid) {
		endpointDao.deleteRegularEndpoint(guid);
	}

	public int updateGatewayEndpointCDR(String gwEndpointGuid, String deviceModel, String endpointPublicIpAddress) {
		return endpointDao.updateGatewayEndpointCDR(gwEndpointGuid, deviceModel, endpointPublicIpAddress);
	}

	public VirtualEndpoint getVirtualEndpointForEndpointGUID(String guid) {
		return endpointDao.getVirtualEndpointForEndpointGUID(guid);
	}

	public int clearRegisterVirtualEndpoint() {
		int rc = this.endpointDao.clearRegisterVirtualEndpoint();
		return rc;
	}

	public int updateVirtualEndpointStatus(String guid, int status) {
		int rc = this.endpointDao.updateVirtualEndpointStatus(guid, status);
		return rc;
	}

    public EndpointFeatures getEndpointFeaturesForGuid(String guid) {
        return this.endpointDao.getEndpointFeaturesForGuid(guid);
    }

    public int updateEndpointFeatures(String guid, EndpointFeatures features) {
        return this.endpointDao.updateEndpointFeatures(guid, features);
    }

	/**
	 * Resets the external data associated with the previous call which endpoint was
	 * on
	 * 
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public int resetExtData(String endpointGUID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering resetExtData(String endpointGUID) of EndpointServiceImpl");
		}
		int updatedRowCount = endpointDao.resetExtData(endpointGUID);
		if (logger.isDebugEnabled()) {
			logger.debug("Exiting resetExtData(String endpointGUID) of EndpointServiceImpl");
		}
		return updatedRowCount;
	}

	@Override
    public int updateEndpointFeaturesForGuest(int guestID, String guid, EndpointFeatures features) {
        return this.endpointDao.updateEndpointFeaturesForGuest(guestID, guid, features);
    }

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
	@Override
	public int updatePublicIPAddress(String endpointGUID, String publicIPAddress) {
		logger.debug("Entering updatePublicIPAddress(endpointGUID, publicIPAddress) of EndpointServiceImpl");
		int updatedRowCount = endpointDao.updatePublicIPAddress(endpointGUID, publicIPAddress);
		logger.debug("Exiting updatePublicIPAddress(endpointGUID, publicIPAddress) of EndpointServiceImpl");
		return updatedRowCount;
	}

}