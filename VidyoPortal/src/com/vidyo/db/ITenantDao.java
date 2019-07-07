package com.vidyo.db;

import java.util.List;
import java.util.Set;

import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.vidyo.bo.Location;
import com.vidyo.bo.Router;
import com.vidyo.bo.Service;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantFilter;
import com.vidyo.bo.TenantIpc;
import com.vidyo.components.ComponentType;

public interface ITenantDao {
    public List<Tenant> getTenants(TenantFilter filter);
    public Long getCountTenants(TenantFilter filter);
    public Tenant getTenant(int tenantID);
    public Tenant getTenant(String tenantName);
    public Tenant getTenantByURL(String url);
    public Tenant getTenantByReplayURL(String url);

    public List<Tenant> getFromTenants(int tenantID);
    public List<Tenant> getToTenants(int tenantID);

    public List<Router> getFromRouters(int tenantID);
    public List<Router> getToRouters(int tenantID);

    public List<Service> getFromVMs(int tenantID);
    public List<Service> getToVMs(int tenantID);

    public List<Service> getFromVPs(int tenantID);
    public List<Service> getToVPs(int tenantID);

    public List<Service> getFromVGs(int tenantID);
    public List<Service> getToVGs(int tenantID);

    public int updateTenant(int tenantID, Tenant tenant);
    public int insertTenant(Tenant tenant);
    public int deleteTenant(int tenantID);

    public int insertTenantXCall(int fromTenantID, int toTenantID);
    public int deleteTenantXCall(int tenantID);
    public int deleteAllTenantXCall(int tenantID);

    public int insertTenantXrouter(int tenantID, int routerID);
    public int deleteTenantXrouter(int tenantID);

    public int insertTenantXservice(int tenantID, int serviceID);
    public int deleteTenantXservice(int tenantID);

    public int deleteGroupsForTenant(int tenantID);
    public int deleteMembersForTenant(int tenantID);
    public int deleteEndpointsForTenant(int tenantID);
    public int deleteUserGroupsForTenant(int tenantID);
    public int deleteRoomsForTenant(int tenantID);
    public int deleteConfigurationForTenant(int tenantID);
    public int deleteServicesForTenant(int tenantID);

    public void updateTenantOfVirtualEndpoints(int serviceID, int tenantID);
    public void updateTenantOfGatewayPrefixes(int serviceID, int tenantID);
    
    public int getInstalls(int tenantID);
    public int setInstalls(Tenant tenat, int tenantID);

    public int getSeats(int tenantID);
    public int setSeats(Tenant tenat, int tenantID);

    public int getPorts(int tenantID);
    public int setPorts(Tenant tenat, int tenantID);

    public int getGuestLogin(int tenantID);
    public int setGuestLogin(Tenant tenat, int tenantID);

    public int getExecutives(int tenantID);
    public int setExecutives(Tenant tenat, int tenantID);

	public int getPanoramas(int tenantID);
	public int setPanoramas(Tenant tenat, int tenantID);

    public int getOtherTenantsInstalls(int tenantID);
    public int getOtherTenantsSeats(int tenantID);
    public int getOtherTenantsPorts(int tenantID);
    public int getUsedNumOfPorts(int tenantID, String lic_version);
    public int getOtherTenantsExecutives(int tenantID);
	public int getOtherTenantsPanoramas(int tenantID);

    public List<Tenant> canCallToTenants(int tenantID);
    public int getTenantIDforVirtualEndpoint(String GUID);
    public int setRegularLicense(int installs, int seats, int ports, int executives);

    public boolean isTenantExistForTenantName(String tenantName, int tenantID);
    public boolean isTenantUrlExistForTenantUrl(String tenantUrl, int tenantID);
    public boolean isTenantReplayUrlExistForTenantReplayUrl(String tenantReplayUrl, int tenantID);
    public boolean isTenantPrefixExistForTenantPrefix(String tenantPrefix, int tenantID);
    public boolean hasTenantReplayComponent(int tenantID);

    public boolean isTenantAuthenticatedWithLDAP(int tenant);
    
    public List<Service> getFromRecs(int tenantID);
    public List<Service> getToRecs(int tenantID);

    public List<Service> getFromReplays(int tenantID);
    public List<Service> getToReplays(int tenantID);

    public List<Location> getFromLocations(int tenantID);
    public List<Location> getToLocations(int tenantID);
    public boolean isLocationExistTenantLevel(int tenantID,int locationId);
    public boolean isVidyoProxyExistTenantLevel(int tenantID,int serviceId);

    public int insertTenantXlocation(int tenantID, int locationID);
    public int deleteTenantXlocation(int locationID);

    public boolean isTenantNotAllowingGuests(int tenantID);
    
    public boolean isIpcSuperManaged();

	public int updateIpcAdmin(int ipcAdmin);

	public List<TenantIpc> getIpcDetails(int tenantId);

	public int updateWhiteListFlag(int tenantId, int flag);

	public int deleteTenantIpcDomains(int tenantId, List<Integer> deletedIds);

	public void addTenantIpcDomains(int ipcId, List<String> ipcDomains, int flag);
	
	public int updateTenantMobileAccess(int mobileAccess);

	public void updateTenantInClientInstallations(String newTenantName, String oldTenantName);
	
	public List<Integer> getMobileAccessDetail();
	
	/**
	 * Returns the list of all TenantIds
	 * 
	 * @return
	 */
	public List<Integer> getAllTenantIds();
	
	public boolean isVidyoGatewayControllerDnsExist(String vidyoGatewayControllerDns, int tenantID);
	
	/**
	 * Enables/Disables Tenant's Scheduled Room Feature
	 * @param schRoomAccess
	 * @param tenantId
	 * @return
	 */
	public int updateTenantScheduledRoomFeature(int schRoomDisabled, int tenantId);
	
	/**
	 * Returns only the TenantIds which can be called
	 * 
	 * @param tenantID
	 * @return
	 */
    public List<Integer> canCallToTenantIds(int tenantId);
    
    /**
     * 
     * @param tenantName
     * @return
     */
    public int updateTenantByName(String tenantName);
    
    /**
     * 
     * @param tenantUrl
     * @return
     */
    public int updateTenantByUrl(String tenantUrl);
    
    /**
     * 
     * @param replayUrl
     * @return
     */
    public int updateTenantByReplayUrl(String replayUrl);
    
	/**
	 * Empty method to trigger cache removal.
	 * 
	 * @param tenantId
	 * @return
	 */	
	public int updateTenantById(int tenantId);
	
	public int updateMembersForTenant(int tenantID, String ids, boolean isProxy);
	
	public int updateGuetsForTenant(int tenantID, String ids, boolean isProxy);
	
	public int deleteTenantXserviceNonVM(int tenantID);

	public boolean isTenantPrefixExistForTenantPrefixLike(String tenantPrefix);

	public void clearTenantCache(int tenantId);
	
	/**
	 * Inserts can call to tenant ids
	 * @param fromTenantID
	 * @param canCallTenantIds
	 * @return
	 */
    public void insertTenantXCall(@ PartialCacheKey int fromTenantID, Set<Integer> canCallTenantIds);
    
	/**
	 * Deletes can call to tenant ids
	 * @param fromTenantID
	 * @param deleteTenantIds
	 * @return
	 */
    public int deleteTenantXCall(@ PartialCacheKey int fromTenantID, Set<Integer> deleteTenantIds);
    
    /**
     * Deletes all TenantXcall mappings except self mapping
     * @param tenantID
     * @return
     */    
	public int deleteTenantXCallMappingExceptSelf(int tenantID);
	
	/**
	 * Deletes Tenant and Services mapping
	 * @param fromTenantID
	 * @param removedServiceIds
	 * @return
	 */
    public int deleteTenantXServiceMapping(@PartialCacheKey int tenantId, Set<Integer> removedServiceIds);
    
    /**
     * 
     * @param tenantId
     * @param serviceIds
     */
    public void insertTenantXServiceMapping(@PartialCacheKey int tenantId, Set<Integer> serviceIds);
    
    /**
     * Deletes a specific service mapping for a Tenant
     * @param tenantId
     * @param componentType
     * @return
     */
	public int deleteTenantServiceMappingByType(@PartialCacheKey int tenantId, ComponentType componentType);
	
	/**
	 * Deletes Tenant Location mapping by tenantId and LocationIds
	 * @param tenantID
	 * @param locationIds
	 * @return
	 */
    public int deleteTenantXlocation(int tenantID, Set<Integer> locationIds);
    
    /**
     * Batch inserts Tenant Location mapping
     * @param tenantId
     * @param locationIds
     */
    public void insertTenantXlocations(int tenantId, Set<Integer> locationIds);

    /**
     * Updates the Proxy to default for the Proxies removed
     * @param tenantId
     * @param proxyIds
     * @return
     */
	public int updateProxyIdToDefaultForMembers(int tenantId, Set<Integer> proxyIds);
	
	/**
	 * Updates the Guest Proxy Id configuration for the Tenant
	 * @param tenantId
	 * @param proxyIds
	 * @return
	 */
	public int updateProxyConfigForGuests(int tenantId, Set<Integer> proxyIds);
	
    /**
     * Updates the LocationTagId to default for the Locations removed
     * @param tenantId
     * @param locationIds
     * @return
     */
	public int updateLocationIdToDefaultForMembers(int tenantId, Set<Integer> locationIds);
	
	/**
	 * Updates the Guest Location Id configuration for the Tenant
	 * @param tenantId
	 * @param locationIds
	 * @return
	 */
	public int updateLocationConfigForGuests(int tenantId, Set<Integer> locationIds); 

}