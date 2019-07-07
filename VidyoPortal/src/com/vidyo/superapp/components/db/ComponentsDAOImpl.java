package com.vidyo.superapp.components.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.ComponentType;
import com.vidyo.superapp.components.bo.RecorderEndpoints;
import com.vidyo.superapp.components.bo.RouterMediaAddrMap;
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRecorder;
import com.vidyo.superapp.components.bo.VidyoReplay;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.repository.ComponentsRepository;
import com.vidyo.superapp.components.repository.ComponentsTypeRepository;
import com.vidyo.superapp.components.repository.RecorderEndpointsRepository;
import com.vidyo.superapp.components.repository.RouterMediaAddrMapRepository;
import com.vidyo.superapp.components.repository.VidyoManagerRepository;
import com.vidyo.superapp.components.repository.VidyoRecorderRepository;
import com.vidyo.superapp.components.repository.VidyoReplayRepository;
import com.vidyo.superapp.components.repository.VidyoRouterRepository;
import com.vidyo.superapp.components.repository.VirtualEndpointsRepository;
import com.vidyo.superapp.components.repository.gateway.GatewayRepository;
import com.vidyo.superapp.routerpools.repository.RouterPoolMapRepository;
import com.vidyo.ws.configuration.NetworkElementType;

@Service(value = "ComponentsDAO")
public class ComponentsDAOImpl  implements ComponentsDAO {
	
	@Autowired
	ComponentsRepository compRepository;
	
	@Autowired
	ComponentsTypeRepository compTypeRepository;
	
	@Autowired
	VidyoManagerRepository vidyoManagerRepository;
	
	@Autowired
	VidyoRouterRepository vidyoRouterRepository;
	
	@Autowired
	RouterMediaAddrMapRepository routerMediaAddrMapRepository;
	
	@Autowired
	RecorderEndpointsRepository recorderEndpointRepository;
	
	@Autowired
	VirtualEndpointsRepository virtualEndpointRepository;
	
	@Autowired
	RouterPoolMapRepository routerPoolMapRepository;
	
	@Autowired
	GatewayRepository gatewayRepository;
	
	@Autowired
	VidyoReplayRepository vidyoReplayRepository;
	
	@Autowired
	VidyoRecorderRepository vidyoRecorderRepository;
	
	@Override
	public List<Component> findAllComponents(String name, String type, String status, int isAlarm) {
		List<Component> components = null;
		
		if(isAlarm == 0){
			 components = compRepository.getComponentsByStatus(name, type, status);
		} else {
			 components = compRepository.getComponentsByAlarm(name, type);
		}
		
		return components;
	}

	@Override
	public List<ComponentType> findAllComponentsTypes() {
		
		Iterable<ComponentType> componentsType = compTypeRepository.findAll();
		
		return (List<ComponentType>)componentsType;
	}
	
	@Override
	public VidyoManager findManagerByCompID(int compID){
			
			VidyoManager vidyoManager = vidyoManagerRepository.findManagerByCompID(compID);
			
			return vidyoManager;
			
	}

	@Override
	public VidyoRouter findRouterByCompID(int compID){
		
		VidyoRouter vidyoRouter = vidyoRouterRepository.findRouterByCompID(compID);
		
		return vidyoRouter;
		
	}
	
	@Override
	public void deleteSelectedComponents(List<Integer> componentIDs) {
		
		compRepository.deleteSelectedComponents(componentIDs);
	}
	
	@Override
	public Component findComponentByID(int componentID) {
		
		Component component = compRepository.findOne(componentID);
		
		return component;
	}

	@Override
	@TriggersRemove(cacheName = {"componentsUserDataCache", "vidyoManagerServiceCache"}, removeAll = true)	
	public VidyoManager saveVidyoManager(VidyoManager vidyoManager) {
		
		VidyoManager vVidyoManager = vidyoManagerRepository.save(vidyoManager);
		
		return vVidyoManager;
	}
	
	@Override
	public VidyoRouter saveVidyoRouter(VidyoRouter vidyoRouter) {
		
		VidyoRouter vVidyoRouter = vidyoRouterRepository.save(vidyoRouter);
		
		return vVidyoRouter;
	}

	@Override
	public Component saveComponent(Component component) {
		
		Date now = Calendar.getInstance().getTime();
		
		component.setUpdated(now);
		
		Component vComponent = compRepository.save(component);
		
		return vComponent;
	}

	@Override
	public void deleteMediaAddressMap(List<Integer> deleteIDs) {
		
		routerMediaAddrMapRepository.deleteMediaAddressMap(deleteIDs);
	}
	
	@Override
	public void deleteMediaAddressMapNotIn(List<Integer> deleteIDs) {
		
		routerMediaAddrMapRepository.deleteMediaAddressMapNotIn(deleteIDs);
	}
	
	@Override
	public void deleteMediaAddressMapNotIn(List<Integer> deleteIDs, int routerId) {
		
		routerMediaAddrMapRepository.deleteMediaAddressMapNotIn(deleteIDs, routerId);
	}
	
	@Override
	public void deleteMediaAddressMapByCompId(List<Integer> deleteIDs) {
		
		routerMediaAddrMapRepository.deleteMediaAddressMapByCompID(deleteIDs);
	}

	@Override
	public List<RouterMediaAddrMap> saveRouterMediaAddressMap(
			List<RouterMediaAddrMap> routerMediaAddrMap) {
		
		List<RouterMediaAddrMap> routerMediaAddrMapList = (List<RouterMediaAddrMap>) routerMediaAddrMapRepository.save(routerMediaAddrMap);
		
		return routerMediaAddrMapList;
	}

	@Override
	public boolean saveComponentStatus(int id, String status) {

		Integer vIsUpdated = compRepository.saveComponentStatus(id, status);
		boolean success = false;
		
		if(vIsUpdated == 1){
			success = true;
		}
		return success;
	}

	@Override
	public Component findByCompIDAndCompType(String compId,
			String compType) {
		
		Component component  = compRepository.findByCompIDAndCompType(compId, compType);

    	return component;
	}

	@Override
	public List<ComponentType> findByComponentTypeName(String compTypeName) {
		List<ComponentType> list  = compTypeRepository.findByName(compTypeName);
    	return list;
	}

	@Override
	public VidyoManager getVidyoManagerById(int vidyoMangerId) {
		return vidyoManagerRepository.findById(vidyoMangerId);
	}
	
	@Override
	public VidyoRouter getVidyoRouterById(int vidyoRouterId) {
		return vidyoRouterRepository.findById(vidyoRouterId);
	}
	
	@Override
	@TriggersRemove(cacheName = {"componentsUserDataCache", "vidyoManagerServiceCache"}, removeAll = true)
	public VidyoManager updateVidyoManager(VidyoManager vidyoManager) {
		return vidyoManagerRepository.saveAndFlush(vidyoManager);
	}

	@Override
	public VidyoRouter updateVidyoRouter(VidyoRouter vidyoRouter) {
		return vidyoRouterRepository.saveAndFlush(vidyoRouter);
	}
	
	@Override
	public List<VidyoRouter> getAvailableRouters() {
		return vidyoRouterRepository.getAvailableRouters();		
	}

	@Override
	public List<Component> getComponentsByType(String type) {
		return compRepository.getComponentsByCompType(type);
	}

	@Override
	public void deleteVidyoRouter(List<Integer> deleteIds) {
		vidyoRouterRepository.deleteVidyoRouterByCompID(deleteIds);
		
	}

	@Override
	public void deleteVidyoManager(List<Integer> deleteIds) {
		vidyoManagerRepository.deleteVidyoManagersByCompIDs(deleteIds);
		
	}

	@Override
	public List<Integer> getComponentIDsAssociatedWithPools(
			List<Integer> deleteIds) {
		List<Integer> compIDs = vidyoRouterRepository.getComponentIDsAssociatedWithPools(deleteIds);
		return compIDs;
	}

	@Override
	public void deletRouterMediaAddressMap(List<Integer> deleteIDs) {
		routerMediaAddrMapRepository.deleteMediaAddressMapByCompID(deleteIDs);
		
	}

	@Override
	public void deleteRouterPoolMap(List<Integer> deleteIDs) {
		routerPoolMapRepository.deleteRouterPoolMapByCompID(deleteIDs);
		
	}

	@Override
	public String getManagerFQDN() {
		
		List<String> fqdnList =  vidyoManagerRepository.getManagerFQDN();
		
		return fqdnList.get(0);
	}

	@Override
	@TriggersRemove(cacheName = {"componentsUserDataCache"}, removeAll = true)	
	public VidyoGateway updateVidyoGateway(VidyoGateway updatedVidyoGateway) {
		return (VidyoGateway)gatewayRepository.save(updatedVidyoGateway);
	}

	@Override
	@TriggersRemove(cacheName = {"componentsUserDataCache"}, removeAll = true)	
	public VidyoReplay updateVidyoReplay(VidyoReplay updatedVidyoReplay) {
		return (VidyoReplay)vidyoReplayRepository.save(updatedVidyoReplay);
	}

	@Override
	@TriggersRemove(cacheName = {"componentsUserDataCache"}, removeAll = true)
	public VidyoRecorder updateVidyoRecorder(VidyoRecorder updatedVidyoRecorder) {
		return (VidyoRecorder)vidyoRecorderRepository.save(updatedVidyoRecorder);
	}

	@Override
	public List<Integer> getComponentIDForInternalRouter(String ipAddress) {
		return compRepository.findByLocalIPAndCompType(ipAddress, NetworkElementType.VidyoRouter.getValue());
	}
	
	@Override
	public VidyoRecorder getRecorderByCompId(int id) {
		
		return vidyoRecorderRepository.findRecorderByCompId(id);
	}

	@Override
	public VidyoReplay getReplayByCompId(int id) {
		
		return vidyoReplayRepository.findReplayByCompId(id);
	}

	@Override
	public void deleteVidyoGateway(List<Integer> componentIDs) {
		
		gatewayRepository.deleteVidyoGatewayByCompID(componentIDs);
	}

	@Override
	public void deleteVidyoReplay(List<Integer> componentIDs) {
		
		vidyoReplayRepository.deleteVidyoReplayByCompID(componentIDs);
	}

	@Override
	public void deleteVidyoRecorder(List<Integer> componentIDs) {
		
		vidyoRecorderRepository.deleteVidyoRecorderByCompID(componentIDs);
	}
	
	@Override
	public VidyoRecorder addVidyoRecorder(VidyoRecorder recorder) {
		return vidyoRecorderRepository.save(recorder);
	}

	@Override
	public VidyoReplay addVidyoReplay(VidyoReplay replay) {
		return vidyoReplayRepository.save(replay);
	}
	
	@Override
	public VidyoGateway addVidyoGateway(VidyoGateway gateway) {
		return gatewayRepository.save(gateway);
	}

	@Override
	public long getRoutersCount() {
		return vidyoRouterRepository.count();
	}
	
	public VidyoRouter getRouterByIP(String ipAddress) {
		return vidyoRouterRepository.getRouterByIpAddress(ipAddress);
	}

	@Override
	public Iterable<Component> findAll() {		
		return compRepository.findAll();
	}

	@Override
	public void updateComponents(Iterable<Component> comps) {
		compRepository.save(comps);
	}
	
	@Override
	public int updateRouterProxyConfig(boolean enabled) {
		List<VidyoRouter> routers = vidyoRouterRepository.findAll();
		List<Component> components = new ArrayList<Component>(routers.size());
		for (VidyoRouter router : routers) {
			router.setProxyUseTls(enabled ? 1 : 0);
			// Increment the components config version for these routers
			Component component = router.getComponents();
			component.setConfigVersion(component.getConfigVersion() + 1);
			components.add(component);
		}
		routers = vidyoRouterRepository.save(routers);
		compRepository.save(components);
		return routers.size();
	}
	
	@Override
	public VidyoReplay getReplayByIP(String ipAddress) {
		return vidyoReplayRepository.getReplayByIpAddress(ipAddress);
	}
	
	@Override
	public List<VidyoRouter> getAllActiveRouters() {
		return vidyoRouterRepository.getAllActiveRouters();
	}
	@Override
	public List<Component> getAllComponents(){
	
		List<Component> components = null;
	
	
		 components = compRepository.getAllComponents();
	
	
	return components;
	}
	@Override
	public List<Component> getAllComponentsByType(String compTypeName){
	
		List<Component> components = null;
	
	
		 components = compRepository.getAllComponentsByCompType(compTypeName);
	
	
	return components;
	}
	
	public List<RecorderEndpoints> getRecorderEndpointsByRecorderId(int recorderId) {
		return recorderEndpointRepository.findByVidyoRecorderAndStatus(recorderId, 1);
	}
	
	/**
	 * Fetch the RecorderEndpoints for the description
	 * @param description
	 * @return
	 */
	@Override
	public List<RecorderEndpoints> getRecorderEndpointsByDescription(String description) {
		return recorderEndpointRepository.findByDescription(description);
	}
}
