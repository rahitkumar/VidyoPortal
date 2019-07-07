/**
 * 
 */
package com.vidyo.service.bindendpoint;

/**
 * @author Ganesh
 * 
 */
public interface BindEndpointService {

	/**
	 * Sends Challenge to the Endpoint through VidyoManager. IMPORTANT: There
	 * are AOP interceptors working on this method flow. Please refer to the
	 * spring interceptors xml before changing any business logic in this
	 * method.
	 * 
	 * @param memberID
	 * @param GUID
	 */
	public void bindUserToEndpoint(int memberID, String GUID);

	/**
	 * Empty method implementation for interception.
	 * 
	 * @param guestID
	 * @param GUID
	 */
	public void bindGuestToEndpoint(int guestID, String GUID);

	public void sendBindSuccessMessage(String endpointGUID, int tenantID);
}
