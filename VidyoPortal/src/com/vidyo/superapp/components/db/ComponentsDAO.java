package com.vidyo.superapp.components.db;

import java.util.List;

import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.ComponentType;
import com.vidyo.superapp.components.bo.RecorderEndpoints;
import com.vidyo.superapp.components.bo.RouterMediaAddrMap;
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.bo.VidyoRouter;

public interface ComponentsDAO {
	
	public List<Component> findAllComponents(String name, String type,
			String status,int isAlarm);

	public List<ComponentType> findAllComponentsTypes();

	public VidyoManager findManagerByCompID(int compID);

	public VidyoRouter findRouterByCompID(int compID);

	public void deleteSelectedComponents(List<Integer> componentIDs);
	
	public void deleteVidyoRouter(List<Integer> deleteIds);
	
	public void deletRouterMediaAddressMap(List<Integer> componentIDs);
	
	public void deleteRouterPoolMap(List<Integer> componentIDs);

	public void deleteVidyoManager(List<Integer> deleteIds);

	public Component findComponentByID(int componentID);

	public VidyoManager saveVidyoManager(VidyoManager vidyoManager);

	public VidyoRouter saveVidyoRouter(VidyoRouter vidyoRouter);

	public Component saveComponent(Component component);

	public void deleteMediaAddressMap(List<Integer> deleteID);

	public List<RouterMediaAddrMap> saveRouterMediaAddressMap(
			List<RouterMediaAddrMap> mediaAddressMap);

	public boolean saveComponentStatus(int id, String status);

	public Component findByCompIDAndCompType(String compId, String CompType);

	public List<ComponentType> findByComponentTypeName(String compTypeName);

	public List<Component> getAllComponents();
	public List<Component> getAllComponentsByType(String compTypeName);
	public VidyoManager getVidyoManagerById(int vidyoMangerId);
	
	public VidyoRouter getVidyoRouterById(int vidyoMangerId);

	public VidyoManager updateVidyoManager(VidyoManager vidyoManager);
	
	public VidyoRouter updateVidyoRouter(VidyoRouter vidyoRouter);
	
	public List<VidyoRouter> getAvailableRouters();
	
	public List<Component> getComponentsByType(String type);
	
	public List<Integer> getComponentIDsAssociatedWithPools(List<Integer> deleteIds);
	
	public String getManagerFQDN();
	
	public VidyoGateway updateVidyoGateway(VidyoGateway updatedVidyoGateway);
	
	public VidyoReplay updateVidyoReplay(VidyoReplay updatedVidyoReplay);
	
	public VidyoRecorder updateVidyoRecorder(VidyoRecorder updatedVidyoRecorder);
	
	public List<Integer> getComponentIDForInternalRouter(String ipAddress);
	
	public VidyoRecorder getRecorderByCompId(int id);
	
	public VidyoReplay getReplayByCompId(int id);
	
	public void deleteVidyoGateway(List<Integer> componentIDs);
	
	public void deleteVidyoReplay(List<Integer> componentIDs);
	
	public void deleteVidyoRecorder(List<Integer> componentIDs);
	
	public VidyoRecorder addVidyoRecorder(VidyoRecorder recorder);
	
	public VidyoReplay addVidyoReplay(VidyoReplay replay);
	
	public VidyoGateway addVidyoGateway(VidyoGateway gateway);
	
	public long getRoutersCount();
	
	public VidyoRouter getRouterByIP(String ipAddress);
	
	public Iterable<Component> findAll();
	
	public void updateComponents(Iterable<Component> comps);
	
	public int updateRouterProxyConfig(boolean enabled);
	
	public VidyoReplay getReplayByIP(String ipAddress);
	
	public List<VidyoRouter> getAllActiveRouters();
	
	public List<RecorderEndpoints> getRecorderEndpointsByRecorderId(int recorderId);
	
	public void deleteMediaAddressMapByCompId(List<Integer> deleteIDs);
	
	public void deleteMediaAddressMapNotIn(List<Integer> deleteIDs);
	
	public void deleteMediaAddressMapNotIn(List<Integer> deleteIDs, int routerId);

	/**
	 * Fetch the RecorderEndpoints for the description
	 * @param description
	 * @return
	 */
	public List<RecorderEndpoints> getRecorderEndpointsByDescription(String description);

}
