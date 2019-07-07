package com.vidyo.db;

import java.util.List;

import com.vidyo.bo.*;

public interface ISystemDao {
    public List<Language> getLanguages();
    public Long getCountLanguages();
    public List<Language> getLanguages(Boolean displaySystemLanguage, int tenant);
    public Long getCountLanguages(Boolean displaySystemLanguage);
    public Language getSystemLang(int tenant);
    public int saveSystemLang(int tenant, String langCode);
    public int getLangIDforLangCode(String langCode);
    public Service getVidyoManagerService(int tenant);

    public boolean assignLocationToGroups(int locationID, String groupIDs);

    public CdrAccess readCDR(String ip, String cdrEnabled, String cdrFormat, String cdrAllowDeny, String cdrAccessPassword, String cdrAllowToDelete);
	public boolean grantCDR(CdrAccess ca);
    public boolean denyCDR(String cdrAccessFromHost);
	public int purgeCDR(CdrFilter cf);
    public List<CdrRecord> exportCDRBatched(CdrFilter cf, int limit, int offset);
	public int getExportCDRCount(CdrFilter cf);

    public long getTotalInstallsInUse(String cdrFormat);
    public long getTotalSeatsInUse();
    public long getTotalPortsInUse(String licVersion);

/*    //following methods are used by each tenant to set/get customized user portal logo (*.swf)
    public String getCustomizedLogoName(int tenant);
    public int saveCustomizedLogo(int tenant, String filename);
    public String removeCustomizedLogo(int tenant);
	public String getCustomizedImageLogoName(int tenant);
	public int saveCustomizedImageLogo(int tenant, String filename);
	public String removeCustomizedImageLogo(int tenant);
*/

/*	//following methods are used by Super to set/get customized super portal logo (*.gif, *.jpg, *.png)
    public String getCustomizedSuperPortalLogoName();
    public int saveCustomizedSuperPortalLogo(String filename);
    public String removeCustomizedSuperPortalLogo();
*/
/*    //following methods are used by each Super to set/get customized default user portal logo (*.swf)
    public String getCustomizedDefaultUserPortalLogoName(String configName);
    public int saveCustomizedDefaultUserPortalLogo(String configName, String filename);
    public String removeCustomizedDefaultUserPortalLogo(String configName);
*/

	public List<MemberRoles> getFromRoles(int tenant);
    public List<MemberRoles> getToRoles(int tenant);

    public int insertTenantXauthRole(int tenantID, int roleID);
    public int deleteTenantXauthRole(int tenantID);

    public List<Control> getCurrentCalls(ControlFilter filter);
    public Long getCountCurrentCalls();
    public List<Control> getCurrentTenantCalls(int tenantID, ControlFilter filter);
    public Long getCountCurrentTenantCalls(int tenantID);

    public Service getVidyoReplayService(int tenant);

    //public List<RecorderEndpoint> getAvailableRecorders(int tenant, RecorderEndpointFilter filter);
    //public Long getCountAvailableRecorders(int tenant);

	public List<RecorderPrefix> getAvailableRecorderPrefixes(int tenant, RecorderEndpointFilter filter);
	public Long getCountAvailableRecorderPrefixes(int tenant);

    
    /**
     * Saves the HotStandby Security key in the respective table
     * @param key
     * @return
     */
    public int saveHotStandbySecurityKey(String key);
    
	/**
	 * Empty implementation which clears cache [VidyoManagerData, LicenseData,
	 * VMID] through annotation.
	 * 
	 * @param tenantId
	 */
	public void clearVidyoManagerAndLicenseCache(int tenantId);
	
	/**
	 * Updates the credentials of a service based on rolename
	 * 
	 * @param userId
	 * @param password
	 * @param roleName
	 * @return
	 */
	public int updateServiceCredentials(String userId, String password, String roleName);
	
	/**
	 * Updates the GuestProxyId of the Tenant
	 * @param deletedProxyId
	 * @param defaultProxyId
	 * @return
	 */
    
    public int insertConfiguration(int tenantID, String configName, String configValue);
	 
	 public int deleteConfiguration(int tenantID, String configurationName);
	 
	 public int updateConfiguration(int tenantID, String configName, String configValue);
	 
	 public Configuration getConfiguration(int tenantID, String configurationName);
	 
	 public int updateConfigurations(int tenantID, String configName, String configValue);
	 
	 public int replaceConfigurationValue(int tenantID, String configName, String oldConfigValue, String newConfigValue);

	public List<Configuration> getConfigurationsByTenantAndConfigNames(int tenantID, List<String> configNames);

	public void clearConfigurationCache(int tenantId, String configName);

	public int updateConfigurations(int tenantId, List<Configuration> configurations);

}