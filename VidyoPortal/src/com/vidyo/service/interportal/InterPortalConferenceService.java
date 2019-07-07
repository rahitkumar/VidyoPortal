/**
 * 
 */
package com.vidyo.service.interportal;

/**
 * @author Ganesh
 * 
 */
public interface InterPortalConferenceService {

	/**
	 * 
	 * @param tenantId
	 * @param hostName
	 * @return
	 */
	boolean isInterPortalConferencingAllowed(int tenantId, String hostName,
			boolean outbound);

}
