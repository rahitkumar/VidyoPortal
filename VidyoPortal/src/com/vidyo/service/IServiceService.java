package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.bo.gateway.GatewayPrefixFilter;
import com.vidyo.bo.networkconfig.IpAddressMap;
import com.vidyo.bo.networkconfig.RouterPool;
import com.vidyo.bo.tenantservice.TenantServiceMap;
import com.vidyo.rest.gateway.Prefix;

import java.util.List;
import java.util.Set;

public interface IServiceService {
    public List<IpAddressMap> getVPAddnlAddrMaps();
    public int addVPAddnlAddrMap(IpAddressMap ipAddressMap);
    public int updateVPAddnlAddrMap(IpAddressMap ipAddressMap);
    public int deleteVPAddnlAddrMap(int ipAddressMapsID);
    public Boolean getTLSProxyConfiguration();
    public List<Service> getServices(ServiceFilter filter);
    public List<Service> getServices(ServiceFilter filter, String defaultServerNameforLocalHost);
    public Long getCountServices(ServiceFilter filter);
    public Service getServiceByUserName(String serviceUserName, String serviceRole);
    public int getTenantIDforServiceID(int serviceID);

    public Service getVG(int serviceID);
    public int updateVG(int serviceID, Service service);
    public int insertVG(Service service);
    public int deleteVG(int serviceID);
	public void resetGatewayPrefixes(int serviceID, String gatewayID, Set<Prefix> prefixes);
    public void updateGatewayPrefixesTimestamp(int serviceID);
    public int clearStaleGatewayPrefixesOlderThan(int seconds);
	public boolean isUseNewGatewayServiceInterface();

    public Service getVM(int serviceID);
    public int updateVM(int serviceID, Service service);
    public int insertVM(Service service);
    public int deleteVM(int serviceID);

    public Service getVP(int serviceID);
    public int updateVP(int serviceID, Service service);
    public int insertVP(Service service);
    public int deleteVP(int serviceID);
    public String getProxyCSVList(int memberID);

    public List<VirtualEndpoint> getVirtualEndpoints(int serviceID, VirtualEndpointFilter filter);
    public Long getCountVirtualEndpoints(int serviceID);
    public int registerVirtualEndpoint(int serviceID, VirtualEndpoint ve);

	public List<GatewayPrefix> getGatewayPrefixes(int serviceID, GatewayPrefixFilter filter);
	public Long getCountGatewayPrefixes(int serviceID);

	public boolean isVGExistsWithServiceName(String serviceName);
    public boolean isVGExistsWithLoginName(String loginName, int serviceID);

    public List<NEConfiguration> getNEConfigurations(String wildcard, String type);
    public int isAllInOne();
    public List<NEConfiguration> getSingleNEConfiguration(String id, String type);
    public Long getCountNEConfigurations();
    public boolean updateNEConfiguration(NEConfiguration nec);
    public boolean updateNEConfiguration(NEConfiguration nec, boolean increaseVersion);
    public boolean deleteNEConfiguration(String id);
    public boolean enableNEConfiguration(String id, boolean enable);
    public NEConfiguration getNetworkConfig(String status, int version);
    public boolean replaceSystmID(String newID, String type, String ip, String replaceTriggerInYear);
    public boolean setFactoryDefaultNEConfiguration(NEConfiguration nec, String vmIp, String vmId, String docVer);
    public String getFactoryDefaultConfig(String type, String displayName, String vmIp, String vmId,  String docVer);
    public void reconfigVmVrNetworkConfigForAllInOne(String ipAddress, String fqdn, String portalUriFqdn) throws Exception;
    public void reconfigVmVrConfigForNotAllInOne(String ipAddress, String fqdn, String portalUriFqdn) throws Exception;
    public void resetVmconfigPort(int port) throws Exception;
    public String checkVProxyIp();
    public void removeBuildInVR();

    public boolean updateInProgressNetworkConfig(NEConfiguration nec);
    public boolean activateNetworkConfig();
    public boolean discardInProgressNetworkConfig();
    public Service getVGByName(String name);
    public Service getVRecByName(String name);
    public Service getVPByName(String name);
    public Service getVMByName(String name);
    public Service getSuperVM();
    public int insertNetworkElementConfig(NEConfiguration nec);
    public int touchNetworkElementConfig(NEConfiguration nec);

    public boolean isServiceUsed(String type, String serviceName);
    public boolean replaceNetworkComponent(String toBeDeleteServiceName, String replacementServiceName, int type);

    public Service getRec(int serviceID);
    public int updateRec(int serviceID, Service service);
    public int insertRec(Service service);
    public int deleteRec(int serviceID);
    public boolean isRecExistsWithServiceName(String serviceName);
    public boolean isRecExistsWithLoginName(String loginName, int serviceID);

    public List<RecorderEndpoint> getRecorderEndpoints(int serviceID, RecorderEndpointFilter filter);
    public Long getCountRecorderEndpoints(int serviceID);
    public int registerRecorderEndpoint(int serviceID, RecorderEndpoint ve);
    public int clearRegisterRecorderEndpoint(int serviceID);

    public Service getReplay(int serviceID);
    public int updateReplay(int serviceID, Service service);
    public int insertReplay(Service service);
    public int deleteReplay(int serviceID);
    public boolean isReplayExistsWithServiceName(String serviceName);
    public boolean isReplayExistsWithLoginName(String loginName, int serviceID);

    public List<Location> getLocations(ServiceFilter filter);
    public Long getCountLocations(ServiceFilter filter);
    public Location getLocation(int locationID);
    public int updateLocation(int locationID, Location location);
    public int insertLocation(Location location);
    public int deleteLocation(int locationID);
    public boolean isLocationExistsWithLocationTag(String locationTag, int locationID);
    public String getLocationTagForMember(int memberID);
	public int getLocationIdByLocationTag(String locationTag);
	
	/**
	 * 
	 * @param ipAddress
	 * @param fqdn
	 * @throws Exception
	 */
    public void reconfigVmVrNetworkConfig(String ipAddress, String fqdn) throws Exception;
    
    /**
     * 
     * @throws Exception
     */
	public void addVmIdToConnectVMUri() throws Exception;
	
	/**
	 * Returns the available RouterPool Names/Ids
	 * @return
	 */
	public List<RouterPool> getRouterPoolNames();

	/**
	 * Parses the updated NetworkConfig XML and returns the updated list of
	 * RouterPool Ids
	 * 
	 * @param configData
	 * @return
	 */
	public List<String> getRouterPoolIds(String configData);
	
	/**
	 * Deletes the Service Entry Records from the Services table based on the
	 * Ids passed
	 * 
	 * @param serviceIds
	 * @return
	 */
	public int deleteServices(List<Integer> serviceIds);
	
	/**
	 * Returns the TenantIds mapped to the ServiceIds
	 * @param serviceIds
	 * @return
	 */
	public List<Integer> getTenantIdsByServiceIds(List<Integer> serviceIds);
	
	/**
	 * Deletes the Tenant Service [TenantXservice] Mapping by ServiceIds
	 * 
	 * @param serviceIds
	 * @return
	 */
	public int deleteTenantServiceMapping(List<Integer> serviceIds);
	
	/**
	 * Inserts Tenant to Service mapping in to TenantXservice table
	 * 
	 * @param tenantServiceMaps
	 */
	public void saveTenantServiceMappings(
			List<TenantServiceMap> tenantServiceMaps);
	
	/**
	 * Returns the list of router ids in the Router pool
	 * 
	 * @return
	 */
	public List<String> getRouterIdsInPool();
	public List<Location> getSelectedLocationTags(ServiceFilter serviceFilter,
			int tenantId);
	public Long getCountLocations(ServiceFilter serviceFilter, int tenantID);
	
    public boolean replaceNetworkComponent(String toBeDeleteServiceName, String replacementServiceName, String type);
    
    public Integer getCountServiceByUserName(String serviceUserName, String serviceRole, int compID);
}