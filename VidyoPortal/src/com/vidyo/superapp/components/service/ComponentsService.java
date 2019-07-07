package com.vidyo.superapp.components.service;

import java.util.List;

import com.vidyo.framework.service.ServiceException;
import com.vidyo.parser.xsd.networkconfig.NetworkConfigType;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.ComponentType;
import com.vidyo.superapp.components.bo.RecorderEndpoints;
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.bo.VidyoRouter;

public interface ComponentsService {

	public List<Component> findAllComponents(String name, String type,
			String status, int isAlarm) throws ServiceException;

	public List<ComponentType> findAllComponentsTypes()
			throws ServiceException;

	public List<VidyoManager> findManagerByCompID(int compID) throws ServiceException;

	public List<VidyoRouter> findRouterByCompID(int compID)
			throws ServiceException;

	public void deleteSelectedComponents(List<Integer> componentIDs)
			throws ServiceException;
	
	public void deleteVidyoRouter(List<Integer> deleteIds)
			throws ServiceException;
	
	public void deletRouterMediaAddressMap(List<Integer> componentIDs)
			throws ServiceException;
	
	public void deleteRouterPoolMap(List<Integer> componentIDs)
			throws ServiceException;

	public void deleteVidyoManager(List<Integer> deleteIds)
			throws ServiceException;

	public Component findComponentByID(int componentID)
			throws ServiceException;

	public VidyoManager saveVidyoManager(VidyoManager vidyoManager)
			throws ServiceException;

	public VidyoRouter saveVidyoRouter(VidyoRouter vidyoRouter)
			throws ServiceException;

	public Component saveComponent(Component component)
			throws ServiceException;

	public boolean saveComponentStatus(int id, String status)
			throws ServiceException;

	public Component findByCompIDAndCompType(String compId, String CompType)
			throws ServiceException;

	public List<ComponentType> findByComponentTypeByName(String compTypeName)
			throws ServiceException;

	public VidyoManager getVidyoManagerById(int vidyoMangerId) throws ServiceException;
	
	public VidyoRouter getVidyoRouterById(int vidyoMangerId) throws ServiceException;

	public boolean updateVidyoManager(VidyoManager updatedVidyoManager) throws ServiceException;
	
	public boolean addVidyoManager(VidyoManager addedVidyoManager) throws ServiceException;
	
	public boolean updateVidyoRouter(VidyoRouter updatedVidyoRouter) throws ServiceException;
	
	public boolean addVidyoRouter(VidyoRouter addedVidyoRouter) throws ServiceException;

	public Component updateComponent(Component updatedComponents) throws ServiceException;
	
	public List<VidyoRouter> getAvailableRouters() throws ServiceException;
	
	public List<Component> getComponentsByType(String type) throws ServiceException;

	public List<Component> getAllComponents();
	public List<Component> getAllComponentsByType(String compTypeName);
	public List<Integer> getComponentIDsAssociatedWithPools(List<Integer> deleteIds) throws ServiceException;
	
	public String getManagerFQDN() throws ServiceException;
	
	public VidyoGateway updateVidyoGateway(VidyoGateway updatedVidyoGateway) throws ServiceException;
	
	public VidyoReplay updateVidyoReplay(VidyoReplay updatedVidyoReplay) throws ServiceException;
	
	public VidyoRecorder addVidyoRecorder(VidyoRecorder recorder) throws ServiceException;
	
	public VidyoRecorder updateVidyoRecorder(VidyoRecorder recorder) throws ServiceException;
	
	public void deleteInternalRouter(String ipAddress) throws ServiceException;
	
	public VidyoRecorder getRecorderByCompId(int id) throws ServiceException;
	
	public VidyoReplay getReplayByCompId(int id) throws ServiceException;
	
	public void deleteVidyoGateway(List<Integer> componentIDs) throws ServiceException;
	
	public void deleteVidyoReplay(List<Integer> componentIDs) throws ServiceException;
	
	public void deleteVidyoRecorder(List<Integer> componentIDs) throws ServiceException;
	
	public VidyoReplay addVidyoReplay(VidyoReplay replay) throws ServiceException;
	
	public VidyoGateway addVidyoGateway(VidyoGateway gateway) throws ServiceException;
	
	public Component setFactoryDefaultConfiguration(String componentId)throws ServiceException;
	
	public NetworkConfigType getActiveNetworkConfig();
	
	public int enableDisableComponentsEncryption(boolean value);
	
	public int updateRouterProxyConfig(boolean enabled);
	
	public VidyoReplay updateVidyoReplay(VidyoRecorder replayRecorder, String ipAddress) throws ServiceException;
	
	public VidyoReplay getReplayByIP(String ipAddress);

	/**
	 * Increment cloud config version of the active cloud and clear the cache. </p>
	 * This event will trigger VM to call the portal for new networkconfig.xml 
	 */
	public void triggerNetworkConfigXmlUpdate();
	
	public List<VidyoRouter> getAllActiveRouters();
	
	public Component updateComponentData(Component updatedComponent, String componentTypeData);
	
	public List<RecorderEndpoints> getRecorderEndpointsByRecorderId(int recorderId);
	
	public String getVidyoManagerConnectAddress();

	public List<Integer> deleteComponents(List<Integer> componentIDsToDelete, String toBeReplaceWith, String confirmation);

	/**
	 * To fetch the recorderEndpoint using description 
	 * @param description
	 * @return
	 */
	public List<RecorderEndpoints> getRecorderEndpointsByDescription(String description);
}
