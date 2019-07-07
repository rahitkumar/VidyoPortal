/**
 * 
 */
package com.vidyo.service.endpointbehavior;

import java.util.List;

import com.vidyo.bo.EndpointBehavior;
import com.vidyo.framework.service.ServiceException;

/**
 * @author ysakurikar
 *
 */
public interface EndpointBehaviorService {

	public static final String[] onOffList = {"welcomepage", "beautyscreen", "loginmodule", "publicchat", "leftpanel", "incallsearch", "inviteparticipants",
			"contentsharing", "sharedialogonjoin", "displaylabels", "remotecontentaccess", "cameramutecontrol", "mutecameraonentry",
			"audiomutecontrol", "muteaudioonentry", "devicesettings", "pinnedparticipant", "recordconference", "recordingrole", "exitonuserhangup",
			"automaticallyupdate", "lockusername", "enableautoanswer", "participantnotification", "fullscreenvideo"};


	
	/**
	 * Fetch the EndpointBehavior using endpointBehaviorKey. 
	 * @param endpointBehaviorKey
	 * @return
	 * @throws ServiceException
	 */
	public EndpointBehavior getEndpointBehaviorByKey(String endpointBehaviorKey) throws ServiceException;

	/**
	 * Create the EndpointBehavior object and save parameters into database. 
	 * @param endpointBehavior
	 * @return
	 * @throws ServiceException
	 */
	public EndpointBehavior saveEndpointBehavior(EndpointBehavior endpointBehavior) throws ServiceException;

	/**
	 * Delete the EndpointBehavior from database.
	 * @param endpointBehavior
	 * @throws ServiceException
	 */
	public boolean deleteEndpointBehavior(String endpointBehaviorKey) throws ServiceException;

	/**
	 * Get the EndpointBehavior by tenant
	 * @param tenantId
	 * @return
	 * @throws ServiceException
	 */
	public List<EndpointBehavior> getEndpointBehaviorByTenant(int tenantID) throws ServiceException;

	/**
	 * To generate a unique EndpointBehavior Key
	 * @return
	 * @throws ServiceException
	 */
	public String generateEndpointBehaviorKey() throws ServiceException;

	/**
	 * Check if the EndpointBehavior is enabled for the Tenant
	 * @param tenantId
	 * @return
	 */
	public boolean isEndpointBehaviorForTenant(int tenantId);

	/**
	 * Create the copy of the endpointBehavior into the corresponding endpointBehaviorDataType object and return it.
	 * @param endpointBehavior
	 * @param endpointBehaviorDataType
	 * @return
	 */
	public Object makeEndpointBehavioDataType(EndpointBehavior endpointBehavior, Object endpointBehaviorDataType);

	/**
	 * Create the copy of the endpointBehaviorDataType into the corresponding endpointBehavior object and return it.
	 * @param endpointBehaviorDataType
	 * @return
	 */
	public EndpointBehavior makeEndpointBehaviorFromDataType(Object endpointBehaviorDataType, EndpointBehavior endpointBehavior);

	/**
	 * Delete the EndpointBehavior identified by tenantId
	 * @param tenantID
	 * @return
	 */
	public boolean deleteEndpointBehaviorByTenantID(int tenantID);
}
