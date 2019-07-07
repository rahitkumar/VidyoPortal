package com.vidyo.service;

import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.vidyo.bo.*;
import com.vidyo.service.tenant.TenantUpdateResponse;

import java.util.List;
import java.util.Set;

public interface ITenantService {
    public List<Tenant> getTenants(TenantFilter filter);
    public Long getCountTenants(TenantFilter filter);
	public Tenant getTenantForRequest();
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

    public TenantUpdateResponse updateTenant(int tenantID, Tenant oldTenant, Tenant tenant, Member adminUser);
    public int insertTenant(Tenant tenant, Member adminUser) throws Exception ;
    public int deleteTenant(int tenantID);

    public int getMaxNumOfInstalls(int tenantID);
    public int getInstallsInUse(int tenantID);
    public int getMaxNumOfSeats(int tenantID);
    public int getSeatsInUse(int tenantID);
    public int getMaxNumOfPorts(int tenantID);
    public int getMaxNumOfExecutives(int tenantID);
    public int getExecutivesInUse(int tenantID);
	public int getMaxNumOfPanoramas(int tenantID);
	public int getPanoramasInUse(int tenantID);
    public int getUsedNumOfPorts(int tenantID);
    public boolean isMultiTenant();
    public int updateRegularLicense(int installs, int seats, int ports, int executives);
    public List<Tenant> canCallToTenants(int tenantID);
    public int getTenantIDforVirtualEndpoint(String GUID);

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
    /**
     * Added for validating the location id against the assigned locations for a tenant.
     * @param tenantID
     * @param locationId
     * @return
     */
    public boolean isLocationExistTenantLevel(int tenantID,int locationId);
    /**
     * Added for validating the vidyo proxy(service) id against the assigned services for a tenant.
     * @param tenantID
     * @param locationId
     * @return
     */
    public boolean isVidyoProxyExistTenantLevel(int tenantID,int serviceId);
    public String getLicenseVersion();

    public boolean isTenantNotAllowingGuests(int tenantID);
    public boolean isTenantNotAllowingGuests();
    
    /**
     * 
     * @return
     */
    public boolean isIpcSuperManaged();
    
    /**
     * 
     * @param ipcAdmin
     * @return
     */
    public int updateIpcAdmin(String ipcAdmin);
    
	/**
	 * 
	 * @param tenantID
	 * @return
	 */
	public List<TenantIpc> getTenantIpcDetails(int tenantID);
	
	/**
	 * 
	 * @param tenantId
	 * @param flag
	 * @return
	 */
	public int updateIpcWhitelistFlag(int tenantId, int flag);
	
	/**
	 * 
	 * @param tenantId
	 * @param deletedIds
	 * @return
	 */
	public int deleteTenantIpcDomains(int tenantId, List<Integer> deletedIds);
	
	/**
	 * 
	 * @param ipcId
	 * @param ipcDomains
	 * @param flag
	 */
	public void addTenantIpcDomains(int ipcId, List<String> ipcDomains, int flag);
	
	/**
	 * 
	 * @param mobileAccess
	 * @return
	 */
	public int updateTenantMobileAccess(int mobileAccess);
	
	/**
	 * Returns the aggregated list of VidyoMobileAccess indicators
	 * 
	 * @return mobileAcessDetail aggregated list of Indicators
	 */
	public List<Integer> getMobileAccessDetail();
	
	/**
	 * Returns the list of all TenantIds
	 * 
	 * @return
	 */
	public List<Integer> getAllTenantIds();
	
	/**
	 * Check if provided VidyoGatewayControllerDns exist, except for tenantID
	 * 
	 * @param vidyoGatewayControllerDns
	 * @param tenantID
	 * @return
	 */
	public boolean isVidyoGatewayControllerDnsExist(String vidyoGatewayControllerDns, int tenantID);
	
	/**
	 * Enables/Disables Tenant's Scheduled Room Feature
	 * 
	 * @param schRoomAccess
	 * @param tenantId
	 * @return
	 */
	public int updateTenantScheduledRoomFeature(int schRoomDisabled,
			int tenantId);
	
	/**
	 * Returns only the callTo tenant ids 
	 * 
	 * @param tenantId
	 * @return
	 */
    public List<Integer> canCallToTenantIds(int tenantId);
    
	/**
	 * Utility to clear cache by invoking AOP intercepted methods
	 * 
	 * @param tenantId
	 */
	public void clearCache(int tenantId);
	
	public TenantConfiguration getTenantConfiguration(int tenantId);
	public int updateTenantConfiguration(TenantConfiguration config);
	
	/**
	 * Enables / Disables Tenant's endpointPrivateChat and endpointPublicChat
	 * @param tenantId
	 * @param endpointPrivateChat
	 * @param endpointPublicChat
	 * @return
	 */
	public int updateEndpointChatsStatuses(int tenantId, int endpointPrivateChat, int endpointPublicChat);
	
	public String getTenantReplayURL(int tenantId);

    public void setTenantRoomsLectureModeState(int tenantId, boolean flag);
    
    public int setTenantRoomAttributes(int tenantID, boolean lectureModeAllowed, boolean waitingRoomsEnabled, boolean waitUntilOwnerJoins, boolean lectureModeStrict);
    
    /**
     * 
     * @param tenantId
     * @param sessionExpPeriod
     * @return
     */
    public int updateSessionExpirationConfig(int tenantId, int sessionExpPeriod);
	public int resetTenantConfigPblcRoomSttngs(String publicRoomEnabledGlobal, String publicRoomMaxRoomNoPerUser);

	public boolean isTenantPrefixExistForTenantPrefixLike(String tenantPrefix);
	int updateTenantCnfUserAttributes(String enableUserImage, String enableUserImageUpload);
	int updateTenantAllLogAggregation(int logAggregation);
	int updateTenantAllCustomRole(int customRoleFlag);
	int updateTenantAllEpicIntegration(int integrationFlag);
	int updateTenantAllTytoCareIntegration(int integrationFlag);
	
    public TenantUpdateResponse updateTenantAPI(int tenantID, Tenant oldTenant, Tenant tenant, Member adminUser);

}