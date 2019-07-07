package com.vidyo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.http.ResponseEntity;

import com.vidyo.bo.Banners;
import com.vidyo.bo.CdrAccess;
import com.vidyo.bo.CdrFilter;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.DBProperties;
import com.vidyo.bo.DbBackupFileInfo;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.RecorderPrefix;
import com.vidyo.bo.Service;
import com.vidyo.bo.StatusNotification;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.WsAuthentication;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.bo.email.InviteEmailContentForInviteRoom;
import com.vidyo.bo.eventsnotify.EventsNotificationServers;
import com.vidyo.recordings.webcast.RecordingWebcastServiceStub;
import com.vidyo.replay.update.ReplayUpdateParamServiceStub;
import com.vidyo.service.exceptions.NoVidyoFederationException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NoVidyoReplayException;
import com.vidyo.vcap20.DSCPValueSet;
import com.vidyo.ws.authentication.AuthenticationServiceStub;
import com.vidyo.ws.federation.VidyoFederationServiceStub;
import com.vidyo.ws.manager.VidyoManagerServiceStub;
import com.vidyo.ws.notification.StatusNotificationServiceStub;

public interface ISystemService {
    public Boolean getTLSProxyConfiguration();
    public int updateTlsProxyConfiguration(Boolean enabled);
	public static final String BACKUP_DIR = "/opt/vidyo/backup";
    public Configuration getUserLockoutTimeLimitinSecConfig();
    public Configuration getInactiveDaysLimitConfig();
    public int updateInactiveDaysLimitConfig(String configValue);
    public Configuration getUserPasswordRule();
    public int updateUserPasswordRule(String configValue);
    public Configuration getLoginFalureCountConfig();
    public int updateLoginFalureCountConfig(String configValue);
    public Configuration getPasswordValidityDaysConfig();
    public Configuration getForcePasswordChangeConfig();
    public int updateForcePasswordChangeConfig(String configValue);
    public int updatePasswordValidityDaysConfig(String configValue);
    public DSCPValueSet getDSCPConfiguration(String userName, int tenentID);
    public EndpointSettings getSuperEndpointSettings();
    public boolean saveSuperEndpointSettings(EndpointSettings es);
    public EndpointSettings getAdminEndpointSettings(int tenantID);
    public boolean saveAdminEndpointSettings(EndpointSettings es, int tenantID);
    public Boolean showLoginBanner();
    public Boolean showWelcomeBanner();
    public String getLoginBanner();
    public String getWelcomeBanner();
    public Banners getBannersInfo();
    public Boolean saveBanners(Banners banners);
    public List<Language> getLanguages();
    public Long getCountLanguages();
    public List<Language> getLanguages(Boolean displaySystemLanguage);
    public Long getCountLanguages(Boolean displaySystemLanguage);
    public Language getSystemLang();
    public Language getSystemLang(int tenant);
    public int saveSystemLang(String langCode);
    public int saveSystemLang(int tenant,String langCode);
    public int getLangIDforLangCode(String langCode);
    public Service getVidyoManagerService();
    public Service getVidyoManagerService(int tenantid);
    //public Service getVidyoManagerService(boolean superScope);
    public Guestconf getGuestConf(int tenantID);
    public Guestconf getGuestConf();
    public int saveGuestConf(Guestconf conf);
    public String getTenantAboutInfo();
    public int saveTenantAboutInfo(String aboutInfo);
    public String getTenantContactInfo();
    public int saveTenantContactInfo(String contactInfo);
    public String getTenantInvitationEmailContent();
    public int saveTenantInvitationEmailContent(String emailContent);
    public String getTenantVoiceOnlyContent();
    public int saveTenantVoiceOnlyContent(String emailContent);
    public String getTenantWebcastContent();
    public int saveTenantWebcastContent(String emailContent);
    public String getTenantInvitationEmailSubject();
    public int saveTenantInvitationEmailSubject(String emailSubject);

    public int getTenantDefaultLocationTagID();
    public int saveTenantDefaultLocationTagID(int locationID);
    public boolean assignLocationToGroups(int locationID, String groupIDs);

    public boolean upgradeSystem(String fileName);
    public boolean shutdownSystem();
    public boolean rebootSystem();
    public boolean restartWebServer();
    public List<DbBackupFileInfo> getBackupFileInfo();
    public boolean backupDatabase(String fileName,int includeThumbNail);
    public boolean deleteBackupFile(String fileName);
    public String getDbBackupPath();
    public boolean restoreDatabaseWithBackupFile(String fileName);
    public boolean restoreDatabaseToFactoryDefaults();

	public boolean doSystemBackup();
	public List<DbBackupFileInfo> getSystemBackupFileInfo();
	public boolean deleteSystemBackupFile(String filename);
	public boolean restoreSystemWithBackupFile(String filename, String type);
	public boolean restoreSystemToFactoryDefaults();
	public boolean downloadSystemBackup(String path, boolean result);
	public boolean uploadSystemBackup(String path, boolean result);

    public String getNotificationFromEmailAddress();
    public String getNotificationToEmailAddress();
    public boolean getEnableNewAccountNotification();
    public int saveNotificationFromEmailAddress(String addr);
    public int saveNotificationToEmailAddress(String addr);
    public int saveEnableNewAccountNotification(boolean enable);

    public String getSuperNotificationFromEmailAddress();
    public String getSuperNotificationToEmailAddress();
    public int saveSuperNotificationFromEmailAddress(String addr);
    public int saveSuperNotificationToEmailAddress(String addr);

    public String getSuperAboutInfo();
    public int saveSuperAboutInfo(String aboutInfo);
    public String getSuperContactInfo();
    public int saveSuperContactInfo(String contactInfo);
    public String getSuperInvitationEmailContent();
    public int saveSuperInvitationEmailContent(String emailContent);
    public String getSuperVoiceOnlyContent();
    public int saveSuperVoiceOnlyContent(String emailContent);
    public String getSuperWebcastContent();
    public int saveSuperWebcastContent(String emailContent);
    public String getSuperInvitationEmailSubject();
    public int saveSuperInvitationEmailSubject(String emailSubject);

    public String getDbVersion();
    public void setLocale(Locale l);

    public String getPortalVersion();

    public CdrAccess readCDR();
	public boolean enableCDR(CdrAccess ca);
    public boolean grantCDR(CdrAccess ca);
    public boolean denyCDR();
	public String getCDREnabled();
	public String getCDRformat();
	public int purgeCDR(CdrFilter cf);
    public void exportCDRBatched(CdrFilter cf, String csvFile);
	public int getExportCDRCount(CdrFilter cf);

    public long getTotalInstallsInUse(String cdrFormat);
    public long getTotalSeatsInUse();
    public long getTotalPortsInUse(String licVersion);

    public int getTenantId();

    //following methods are used by each tenant to set/get customized user portal logo (*.swf)
    public String getCustomizedLogoName();
	public String getCustomizedLogoName(int tenantID);
    public int saveCustomizedLogo(String filename);
    public String removeCustomizedLogo();

	public String getCustomizedImageLogoName();
	public String getCustomizedImageLogoName(int tenantID);
	public int saveCustomizedImageLogo(String filename);
	public String removeCustomizedImageLogo();


	//following methods are used by Super to set/get customized super portal logo (*.gif, *.jpg, *.png)
    public String getCustomizedSuperPortalLogoName();
    public int saveCustomizedSuperPortalLogo(String filename);
    public String removeCustomizedSuperPortalLogo();

    //following methods are used by each Super to set/get customized default user portal logo (*.swf)
    public String getCustomizedDefaultUserPortalLogoName();
    public int saveCustomizedDefaultUserPortalLogo(String filename);
    public String removeCustomizedDefaultUserPortalLogo();

	// image shown in vidyodesktop download page, as customized by super
	public String getCustomizedSuperPortalImageLogoName();
	public int saveCustomizedSuperPortalImageLogo(String filename);
	public String removeCustomizedSuperPortalImageLogo();

    public String getSystemId();

    public StatusNotification getStatusNotificationService();
    public int saveStatusNotificationService(StatusNotification service);

    public AuthenticationConfig getAuthenticationConfig(int tenantId);
    public int saveAuthenticationConfig(int tenantId, AuthenticationConfig config,String baseUrl);

    public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH() throws NoVidyoManagerException;
    public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH(int tenantId) throws NoVidyoManagerException;
    public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH(String username, String password) throws NoVidyoManagerException;
    //public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH(boolean superScope) throws NoVidyoManagerException;
    public StatusNotificationServiceStub getStatusNotificationServiceStubWithAUTH(StatusNotification service) throws Exception;
    public AuthenticationServiceStub getAuthenticationServiceStubWithAUTH(WsAuthentication service) throws Exception;
    /**
     * 
     * @param auth 
     * @param service
     * @return boolean -true if authentication successful
     * @throws Exception
     */
	public boolean restAuthenticationService(WsRestAuthentication auth, String username, String presentedPassword) throws Exception;
	public ResponseEntity<String> restTytoCareAuthentication(String uri, String username, String password);

    public int testLDAPAuthenticationService(LdapAuthentication service);
    public int testLDAPUserAuthenticationService(LdapAuthentication service);
	public Member testLDAPUserMappingService(int tenantID, LdapAuthentication service);
    public List<MemberRoles> getFromRoles();
    public List<MemberRoles> getToRoles();
    public int testWSUserAuthenticationService(WsAuthentication service, String testUser, String testPassword);

    public List<Control> getCurrentCalls(ControlFilter filter);
    public Long getCountCurrentCalls();
    public List<Control> getCurrentTenantCalls(ControlFilter filter);
    public Long getCountCurrentTenantCalls();

    public DBProperties getDBProperties(ServletContext ctx);
    public int setDBProperties(ServletContext ctx, DBProperties db);
    public int testDBProperties(ServletContext ctx, DBProperties db);
    public void switchDBProperties(ServletContext ctx, DBProperties db);

    public RecordingWebcastServiceStub getRecordingWebcastServiceStubWithAUTH(int tenantID) throws NoVidyoReplayException;
	public ReplayUpdateParamServiceStub getReplayUpdateParamServiceWithAUTH(int tenantID) throws NoVidyoReplayException;

    /*public List<RecorderEndpoint> getAvailableRecorders(RecorderEndpointFilter filter);
    public Long getCountAvailableRecorders();*/

	public List<RecorderPrefix> getAvailableRecorderPrefixes(RecorderEndpointFilter filter);
	public Long getCountAvailableRecorderPrefixes();

    public VidyoFederationServiceStub getVidyoFederationServiceStubWithAUTH(String host, boolean secure) throws NoVidyoFederationException;
    public String getVidyoManagerID();

	public int getIpcAllowDomainsFlag();

	public int updateIpcAllowDomainsFlag(int allowFlag);

	public boolean isIpcSuperManaged();

	public int updateIpcAdmin(String ipcAdmin);

    public boolean downloadDatabase(String path, boolean result);

    public boolean uploadDatabase(String path, boolean result);
    public boolean moveDatabase(String path);

    public String getGuideLocation(String langCode, String guideType) throws IOException;

    public int saveSystemConfig(String configName, String configValue, int tenantID);

    public int updateSystemConfig(String configName, String configValue);

    public Configuration getConfiguration(String configName);

    /*public boolean generateStartVC2(boolean allInOne,
                                    String vmIp, int vmPort,
                                    String vrIp, int vrPort,
                                    String vpIp, int vpPort);*/

    /*public int saveVidyoProxyIpAndPort(String ip, int port);
    public int saveVidyoProxyDnsHostNameAndPort(String hostname, int port);*/
    public boolean getEnableVidyoCloud();
    public int saveEnableVidyoCloud(boolean show);

    /**
     * Invokes the shell script to setup the database synchrnoization frequency
     * @param startTime
     * @param endTime
     * @param frequency
     * @return
     */
    public int setupDbSyncSchedule(int startTime, int endTime, int frequency);

    /**
     * Invokes the shell script to sync database immediately
     * @param
     * @return
     */
    public int syncDatabase(String param);

    /**
     * Invokes the shell script to switch portal to Maintenance mode
     * @param param
     * @return
     */
    public int switchPortalToMaintenanceMode(String param);

    /**
     *
     * @param param
     * @return
     */
    public int forcePortalToStandbyMode(String param);

    /**
     *
     * @param param
     * @return
     */
    public ClusterInfo getClusterInfo(String param);

	/**
	 * Saves the HotStandby Security key in the respective table
	 *
	 * @param key
	 * @return
	 */
	public int saveHotStandbySecurityKey(String key);

	/**
	 *
	 * @param param
	 * @return
	 */
	public int isPortalOnMaintenance(String param);
	
	/**
	 * Returns status code to let UI layer know if it has to display HS menu or not.
	 * 0 - HS enabled
	 * 1 - HS not enabled/licensed
	 * 2 - HS enabled, but in maintenance mode
	 */
	public int isHotStandbyEnabled();

	/**
	 * Deletes the Configuration Entry by Name and returns the delete count
	 *
	 * @param configName
	 * @return
	 */
	public int deleteConfiguration(String configName);

	/**
	 *
	 * @param type
	 * @return
	 */
	public String getDesktopUserGuide(String type, String langCode) throws IOException;

	/**
	 * Returns the list of Configurations with the ConfigName
	 * @param configNames
	 * @return
	 */
	public List<Configuration> getConfigurations(List<String> configNames);

	public int saveVidyoWebAvailable(boolean flag);

	public boolean isVidyoWebAvailable();

    public int saveVidyoWebEnabledForSuper(boolean flag);

    public boolean isVidyoWebEnabledBySuper();

    public int saveVidyoWebEnabledForAdmin(int tenantId, boolean flag);

    public boolean isVidyoWebEnabledByAdmin(int tenantId);

    public boolean isVidyoWebEnabled(int tenantId);

    public boolean isWebRTCEnabledForGuests(int tenantId);

	public int saveZincSettings(int tenantId, String zincServer, boolean zincEnabled);

	public boolean isShowDisabledRooms();
	public int saveShowDisabledRoomsForSuper(boolean flag);

	/**
	 * Creates backup of specific tables
	 *
	 * @param tableNames
	 *            Tables to be backed up
	 * @return boolean indicating if backup is successful or not
	 */
	public boolean createTablesBackup(List<String> tableNames);
  	/**
	 * Restores the database from the partial db backup created before tenant
	 * update
	 *
	 * @return boolean indicating if restore is successful or not
	 */
	public boolean restoreFromPartialBackup();

	/**
	 * Deletes the partial database backup created before tenant update
	 *
	 * @return boolean indicating if deletion is successful or not
	 */
	public boolean deletePartialBackup();
    public Map<String, String> getSyslogConfigDetails(String param);

    /**
     * Invokes the Syslog configuration shell script to save the Syslog Config
     * values
     *
     * @param syslogConfig
     * @param useStunnel
     * @param ipAddress
     * @param port
     * @return
     */
    public int saveSyslogConfig(String syslogConfig, String useStunnel,
                                String ipAddress, String port);


	public int getManageLocationTag();

	/**
	 * Clears License, VidyoManager and VMId cache across apps and nodes.
	 *
	 * @param tenantId
	 */
	public void clearVidyoManagerAndLicenseCache(int tenantId);

	/**
	 * Save chat available flag for super.
	 * @param flag
	 * @return
	 */
	public int saveChatAvailable(boolean flag);

	/**
	 * Is chat available at super level.
	 * @return
	 */
	public boolean isChatAvailable();

	/**
	 * Saves portal chat on super level. If portalChat.isChatAvailable() == false, then default public and private chats will be saved
	 * as disabled without taking into account their values in portalChat object
	 *
	 * @param portalChat
	 */
	public void savePortalChat(PortalChat portalChat);

	public PortalChat getPortalChat();

	public void saveTiles16Available(boolean isTiles16Available);

	/**
	 * Utility method to create Email Invite Content for Invite Room
	 * @param inviteEmailContentForInviteRoom
	 * @return
	 */
	public String constructEmailInviteContentForInviteRoom(InviteEmailContentForInviteRoom inviteEmailContentForInviteRoom, String content, boolean encodeForHtml);

	/**
	 * Utility method to create Email Invite Subject for Invite Room
	 * @param loc
	 * @return
	 */
	public String constructEmailInviteSubjectForInviteRoom(Locale loc);

    public boolean isAuthenticationLocalForMemberRole(int tenantId, int roleId);

    public int updateTenantGuestSettingsLocationTag(int deletedLocationID, int defaultLocationTagID);

    public boolean isSuperPasswordRecoveryDisabled();

    public int enableSuperPasswordRecovery();

    public int disableSuperPasswordRecovery();

    public int setMinimumPINLengthSuper(int min);
    public int getMinimumPINLengthSuper();
    public int setMinimumPINLengthForTenant(int tenantId, int min);
    public int getMinPINLengthForTenant(int tenantId);
    public boolean isPINLengthAcceptable(int tenantId, String pin);

    public int updateSessionExpirationPeriodConfig(int configValue);

    public int updateGuestProxyId(int deletedProxyId, int defaultProxyId);

    public Configuration getDbSyncSchedule();

    public int saveOrUpdateConfiguration(int tenant, String configName, String configValue);

    public String getConfigValue(int tenantID, String configurationName);

    public boolean isTiles16Available();

    public boolean isDefaultPrivateChatEnabled();

    public boolean isDefaultPublicChatEnabled();

    public String getUserDefaultPassword();

    public boolean getLDAPAuthenticationAttributeMappingFlag(int tenant);

    public boolean getLDAPAuthenticationFlag(int tenant);

    public String getLDAPAuthenticationFilter(int tenant);

    public String getLDAPAuthenticationBase(int tenant);

    public String getLDAPAuthenticationScope(int tenant);

    public String getAuthenticationURL(int tenant);

    public String getAuthenticationPassword(int tenant);

    public boolean getAuthenticationFlag(int tenant);

    public String getLDAPAuthenticationUsername(int tenant);

    public int getGuestConfLocationID(int tenant);

    public String getSuperFromEmailAddress();

    public String getSuperToEmailAddress();
	public void setUserDefaultPassword(String userDefaultPassword);
	public int saveDefaultPublicChatEnabled(boolean isDefaultPublicChatEnabled);
	public int saveDefaultPrivateChatEnabled(boolean isDefaultPrivateChatEnabled);
	public int updateTenantGuestSettingsLocationTag(int tenant, int deletedLocationID, int defaultLocationTagID);
	public int updateGuestProxyId(int tenant, int deletedProxyId, int defaultProxyId);
	public int saveVidyoWebAvailable(int tenant, boolean en);
	public int saveVidyoWebEnabled(int tenant, boolean en);
	public int saveShowDisabledRooms(int tenant, boolean en);
	public int saveVidyoProxyIpAndPort(String ip, int port);
	public int saveVidyoProxyDnsHostNameAndPort(String dnsname, int port);

	/**
	 * Returns the Status notification server details.
	 * @param tenantId
	 * @return
	 */
	public StatusNotification getStatusNotificationDetails(int tenantId);
	int updateCACCfgFile(int integer, String baseURL);
	public File getCACCertificate(boolean stageFlag);
	public boolean getApacheRestartStatus(int tenantId);
	/**
	 * Returns Authentication types allowed in the system.
	 *
	 * @return map key value paris
	 */
	public Map<String, Object> getAuthenticationParam();
	public String getCACUserNameExtractType(int tenantId);
	public List<Tenant> getWebServerRestartRequest() throws IOException;
	public List<Tenant> getPKICertificateForReviewList() throws IOException;
	public boolean approveTenantCert(String tenantId) throws IOException;
	boolean getPKICertReviewStatus(int tenantId);

	public boolean isZincEnabledForTenant(int tenantId);
	public String getZincServerForTenant(int tenantId);
	public int saveDefaultGuestLocationTag(int tenantId,String locationId);
	public int saveDefaultGuestProxy(int tenantId,String proxyId);
	
	/**
	 * Add audit log to the db.
	 * 
	 * this is without tenant details, basically for super functionality uses
	 * This method would use SecurityContextHolder to get the user<br>
	 * who is performing the operation.
	 */
	public void addTransactionHistoryWithUserLookup(String tnName, String tnParam, String tnResult);

	/**
	 * Returns the super configured events notification server details
	 *
	 * @return
	 */
	public EventsNotificationServers getEventNotificationServers();

	/**
	 * Saves the event notification enable/disable flag and primary/secondary
	 * server details
	 *
	 * @param eventsNotificationServers
	 * @return
	 */
	public void saveEventNotificationServers(EventsNotificationServers eventsNotificationServers);
	
	
	/**
	 * Method to add audit log with tenant details. 
	 * @param tnName audit log message
	 * @param tnParam parameters
	 * @param tnResult success/failure
	 */
	public void auditLogTransaction(String tnName, String tnParam, String tnResult);

	/**
   * Method to add audit log with tenant details.
   * @param tnName audit log message
   * @param tnParam parameters
   * @param username parameter
   * @param tnResult success/failure
   */

   public void auditLogTransaction(String tnName, String tnParam, String username, String tnResult);

	public List<Configuration> getConfigurationsByTenantAndConfigNames(int tenantId, List<String> configNames);

	public int updateConfigurations(int tenantId, List<Configuration> configs);

}
