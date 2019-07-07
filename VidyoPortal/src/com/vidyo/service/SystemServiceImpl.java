package com.vidyo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.servlet.ServletContext;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.Banners;
import com.vidyo.bo.CdrAccess;
import com.vidyo.bo.CdrFilter;
import com.vidyo.bo.CdrRecord;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.DBProperties;
import com.vidyo.bo.DbBackupFileInfo;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.Language;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.RecorderPrefix;
import com.vidyo.bo.Room;
import com.vidyo.bo.Service;
import com.vidyo.bo.StatusNotification;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.CACAuthentication;
import com.vidyo.bo.authentication.InternalAuthentication;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlAuthenticationState;
import com.vidyo.bo.authentication.SamlAuthenticationStateChange;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.bo.authentication.WsAuthentication;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.bo.email.InviteEmailContentForInviteRoom;
import com.vidyo.bo.eventsnotify.EventsNotificationServers;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.ISystemDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.framework.propertyconfig.OpenPropertyPlaceholderConfigurer;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.recordings.webcast.RecordingWebcastServiceStub;
import com.vidyo.replay.update.ReplayUpdateParamServiceStub;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;
import com.vidyo.service.cache.CacheAdministrationService;
import com.vidyo.service.exceptions.NoVidyoFederationException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.exceptions.NoVidyoReplayException;
import com.vidyo.service.ldap.ILdapUserToMemberAttributesMapper;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.PropertyUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.vcap20.BindUserRequest;
import com.vidyo.vcap20.DSCPValueSet;
import com.vidyo.vcap20.RequestMessage;
import com.vidyo.vcap20.RootDocument;
import com.vidyo.vcap20.UserProfileType;
import com.vidyo.ws.authentication.AuthenticationRequest;
import com.vidyo.ws.authentication.AuthenticationResponse;
import com.vidyo.ws.authentication.AuthenticationServiceStub;
import com.vidyo.ws.federation.VidyoFederationServiceStub;
import com.vidyo.ws.manager.AddressesPerVM;
import com.vidyo.ws.manager.GetEMCPConfigRequest;
import com.vidyo.ws.manager.GetEMCPConfigResponse;
import com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest;
import com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse;
import com.vidyo.ws.manager.NotLicensedFaultException;
import com.vidyo.ws.manager.ResourceNotAvailableFaultException;
import com.vidyo.ws.manager.VidyoManagerServiceStub;
import com.vidyo.ws.notification.StatusNotificationServiceStub;

public class SystemServiceImpl implements ISystemService, DisposableBean {

	protected final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class.getName());
    public static final String PASSWORD_UNCHANGED = "PASSWORD_UNCHANGED";

	public static final int PIN_MIN = 3;
	public static final int PIN_MAX = 12;

	private ISystemDao dao;

	private LicensingService license;
	//private CacheManager superManager;
	private IMemberService member;
	private String dbBackupPath;   // "/var/backup";
	private ReloadableResourceBundleMessageSource ms;
	private Locale loc;
	private static final String PORTAL_PROPERTIES = "/usr/local/tomcat/repo/vidyoportal/portal.properties";
	private static final String CACAuthenticationFlag = "CACAuthenticationFlag";
	private static final String CACAuthenticationType = "CACAuthenticationType";
	private static final String CACOCSPRevocationCheck = "CACOCSPRevocationCheck";
	private static final String CACOCSPResponderCheck = "CACOCSPResponderCheck";
	private static final String CACOCSPResponderText = "CACOCSPResponderText";
	private static final String CACOCSPNonceCheck = "CACNonceCheck";
	private static final String  CACLDAPAuthenticationFlag="CACLDAPAuthenticationFlag";
	private static final String  CACLDAPAuthenticationUsername="CACLDAPAuthenticationUsername";
	private static final String  CACLDAPAuthenticationPassword="CACLDAPAuthenticationPassword";
	private static final String  CACLDAPAuthenticationBase="CACLDAPAuthenticationBase";
	private static final String  CACLDAPAuthenticationFilter="CACLDAPAuthenticationFilter";
	private static final String  CACLDAPAuthenticationScope="CACLDAPAuthenticationScope";
	private static final String  CACLDAPAuthenticationURL="CACLDAPAuthenticationURL";
	private static final String  CACOCSPDIRTYFLAG="CACOCSPDirtyFlag";
	private String cacCertificateLocationStg;
	private String cacCertificateLocation;
	private static final String CERTIFICATE_EXT = ".crt";

	private static final String SCRIPT_OCSP_UNSAVED=".unsaved";
	private static final String SCRIPT_OCSP_PENDINGRESTART=".pendingrestart";
	private ITenantService tenantService;
	private OpenPropertyPlaceholderConfigurer propertyPlaceholderConfigurer;


	/**
	 * Path variable for executables
	 */
	private String portalExecutablesPath;

	/**
	 *
	 */
	private String jdbcURL;
	private ILdapUserToMemberAttributesMapper attributesMapper;
	private SamlAuthenticationService samlAuthenticationService;

	private HttpConnectionManager connectionManager;

	private RestTemplate restTemplate;

	private JmsTemplate jmsTemplate;
	private Topic samlAuthMessageMQtopic;

	private TransactionService transactionService;

	private IRoomService roomService;

    public static final String UPLOADED_FILE_NAME_REGEX = "^[a-zA-Z0-9-_\\.]+[a-zA-Z0-9]+$";

	private ConfigurationContext axisConfigContext = null;

	private CacheAdministrationService cacheAdministration;
	private String uploadTempDirSuper;

	private String upload_dir;

	/*public CacheManager getSuperManager() {
		return superManager;
	}

	public void setSuperManager(CacheManager superManager) {
		this.superManager = superManager;
	}*/

	/**
	 * @param portalExecutablesPath the portalExecutablesPath to set
	 */
	public void setPortalExecutablesPath(String portalExecutablesPath) {
		this.portalExecutablesPath = portalExecutablesPath;
	}

	public void setUploadTempDirSuper(String uploadTempDirSuper) {
		this.uploadTempDirSuper = uploadTempDirSuper;
	}

	public String getUploadTempDirSuper() {
		return uploadTempDirSuper;
	}

    public Boolean showLoginBanner(){
        Boolean showLoginBanner = false;
        if(getShowLoginBannerInfo().equals("Yes"))  showLoginBanner = true;
		return showLoginBanner;
	}
    public Boolean showWelcomeBanner(){
    	Boolean showWelcomeBanner = false;
        if(getShowWelcomeBannerInfo().equals("Yes"))  showWelcomeBanner = true;
		return showWelcomeBanner;
	}
	public String getCacCertificateLocationStg() {
		return cacCertificateLocationStg;
	}

	public void setCacCertificateLocationStg(String cacCertificateLocationStg) {
		this.cacCertificateLocationStg = cacCertificateLocationStg;
	}

	public String getCacCertificateLocation() {
		return cacCertificateLocation;
	}

	public void setCacCertificateLocation(String cacCertificateLocation) {
		this.cacCertificateLocation = cacCertificateLocation;
	}

	public void setUpload_dir(String upload_dir) {
        this.upload_dir = upload_dir.trim();
    }
    public String getLoginBanner(){
    	return getLoginBannerInfo();
	}
    public String getWelcomeBanner(){
    	return getWelcomeBannerInfo();
	}

     public Banners getBannersInfo() {
  		Banners bnrs = new Banners();
        Boolean showLoginBanner = false;
        Boolean showWelcomeBanner = false;
        String loginBanner = "";
        String welcomeBanner = "";
        if(getShowLoginBannerInfo().equals("Yes"))  showLoginBanner = true;
        if(getShowWelcomeBannerInfo().equals("Yes"))  showWelcomeBanner = true;
        bnrs.setShowLoginBanner(showLoginBanner);
        bnrs.setShowWelcomeBanner(showWelcomeBanner);
        bnrs.setLoginBanner(getLoginBannerInfo());
        bnrs.setWelcomeBanner(getWelcomeBannerInfo());
		return bnrs;
	}
     public Boolean saveBanners(Banners banners){
         Boolean saveBannerStatus = false;
         String showLoginBanner = "No";
         String showWelcomeBanner = "No";
         if(banners.getShowLoginBanner()== true)  showLoginBanner = "Yes";
         if(banners.getShowWelcomeBanner()== true) {
             showWelcomeBanner = "Yes";
             dao.updateConfiguration(1, "LOGIN_HISTORY_COUNT", "5");
         }
         dao.updateConfiguration(1, "ShowLoginBanner", showLoginBanner);
         if( banners.getLoginBanner() != null) {
        	 dao.updateConfiguration(1, "LoginBannerInfo", banners.getLoginBanner());
         }
         dao.updateConfiguration(1, "ShowWelcomeBanner", showWelcomeBanner) ;
         if(  banners.getWelcomeBanner() != null) {
        	 dao.updateConfiguration(1, "WelcomeBannerInfo", banners.getWelcomeBanner());
         }
         saveBannerStatus = true;
         return saveBannerStatus;
	}

	/**
	 * @param jdbcURL the jdbcURL to set
	 */
	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}

	/**
	 * @param propertyPlaceholderConfigurer the propertyPlaceholderConfigurer to set
	 */
	public void setPropertyPlaceholderConfigurer(
			OpenPropertyPlaceholderConfigurer propertyPlaceholderConfigurer) {
		this.propertyPlaceholderConfigurer = propertyPlaceholderConfigurer;
	}

	public void setDao(ISystemDao dao) {
		this.dao = dao;
	}
	/*
	public void setConferenceDao(IConferenceDao conferenceDao) {
		this.conferenceDao = conferenceDao;
	}
*/

	public void setLicense(LicensingService license) {
		this.license = license;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

    public void setSamlAuthenticationService(SamlAuthenticationService samlAuthenticationService) {
        this.samlAuthenticationService = samlAuthenticationService;
    }

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}



	public void setSamlAuthMessageMQtopic(Topic samlAuthMessageMQtopic) {
		this.samlAuthMessageMQtopic = samlAuthMessageMQtopic;
	}

	public void setDbBackupPath(String path){
		this.dbBackupPath = path;
	}

	public void setLocale(Locale l){
		this.loc = l;
	}

	public void setMs(ReloadableResourceBundleMessageSource msgSrc){
		this.ms = msgSrc;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setConnectionManager(HttpConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public CacheAdministrationService getCacheAdministration() {
		return cacheAdministration;
	}

	public void setCacheAdministration(CacheAdministrationService cacheAdministration) {
		this.cacheAdministration = cacheAdministration;
	}

	public void setAttributesMapper(ILdapUserToMemberAttributesMapper attributesMapper) {
		this.attributesMapper = attributesMapper;
	}

	public List<Language> getLanguages() {
		return getLanguages(false);
	}

	public Long getCountLanguages() {
		return getCountLanguages(false);
	}

	public List<Language> getLanguages(Boolean displaySystemLanguage) {
		List<Language> list = this.dao.getLanguages(displaySystemLanguage, TenantContext.getTenantId());
		return list;
	}

	public Long getCountLanguages(Boolean displaySystemLanguage) {
		Long number = this.dao.getCountLanguages(displaySystemLanguage);
		return number;
	}

	public Language getSystemLang() {
		Language rc = this.dao.getSystemLang(TenantContext.getTenantId());
		return rc;
	}

	public Language getSystemLang(int tenant) {
		Language rc = this.dao.getSystemLang(tenant);
		return rc;
	}

	public int saveSystemLang(String langCode) {
		int rc = this.dao.saveSystemLang(TenantContext.getTenantId(), langCode);
		return rc;
	}

	public int saveSystemLang(int tenant, String langCode) {
		int rc = this.dao.saveSystemLang(tenant, langCode);
		return rc;
	}

	public int getLangIDforLangCode(String langCode) {
		int rc = this.dao.getLangIDforLangCode(langCode);
		return rc;
	}

	public SystemServiceImpl() {
		try {
			axisConfigContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
		} catch (AxisFault af) {
			logger.error("unable to create axis config context: " + af.getMessage());
		}
	}

	@Override
	public void destroy() throws Exception {
		if (axisConfigContext != null) {
			axisConfigContext.terminate();
		}
	}

	public Service getVidyoManagerService() {
		Service service = null;

		if (TenantContext.getTenantId() == 0) {
			service = this.dao.getVidyoManagerService(1);
		} else {
			service = this.dao.getVidyoManagerService(TenantContext.getTenantId());
		}

		processVidyoManagerService(service);

		return service;
	}

	public Service getVidyoManagerService(int tenantid) {
		Service service = null;

		if (tenantid == 0) {
			service = this.dao.getVidyoManagerService(1);
		} else {
			service = this.dao.getVidyoManagerService(tenantid);
		}

		processVidyoManagerService(service);

		return service;
	}

	private void processVidyoManagerService(final Service service) {
		if (service != null) {
			// change URL to Web Service path
			StringBuffer sb = new StringBuffer();

	        String url = service.getUrl();
	        sb.append(url);
	        boolean overrideUrl = false;
	       	if(url.indexOf("http") == -1) {
	       		sb.insert(0, "http://");
	       		overrideUrl = true;
	       	}
	       	if(!url.contains("/services/VidyoManagerService")) {
	       		sb.append("/services/VidyoManagerService");
	       		overrideUrl = true;
	       	}

	       	if(overrideUrl) {
	       		service.setUrl(sb.toString());
	       	}
		}
	}

	public Guestconf getGuestConf(int tenantID) {
		Guestconf rc = new Guestconf();
		rc.setGroupID(this.getGuestConfGroupID(tenantID));
		rc.setProxyID(this.getGuestConfProxyID(tenantID));
		rc.setLocationID(this.getGuestConfLocationID(tenantID));
		return rc;
	}

	public Guestconf getGuestConf() {
		Guestconf rc = new Guestconf();
		rc.setGroupID(this.getGuestConfGroupID(TenantContext.getTenantId()));
		rc.setProxyID(this.getGuestConfProxyID(TenantContext.getTenantId()));
		rc.setLocationID(this.getGuestConfLocationID(TenantContext.getTenantId()));
		return rc;
	}

	public int saveGuestConf(Guestconf conf) {
		int rc1 = this.dao.updateConfiguration(TenantContext.getTenantId(), "GuestGroupID", String.valueOf(conf.getGroupID()));
		int rc2 = this.dao.updateConfiguration(TenantContext.getTenantId(), "GuestProxyID", String.valueOf(conf.getProxyID()));
		int rc3 = this.dao.updateConfiguration(TenantContext.getTenantId(), "GuestLocationID", String.valueOf(conf.getLocationID()));
		return 1;
	}

	public String getTenantAboutInfo() {
        logger.debug("Get AboutInfo from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "AboutInfo");
	}

	public int saveTenantAboutInfo(String aboutInfo) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "AboutInfo", aboutInfo);
	}

	public String getSuperAboutInfo() {
        logger.debug("Get SuperAboutInfo from Configuration");
        return getConfigValue(1, "SuperAboutInfo");
	}

	public int saveSuperAboutInfo(String aboutInfo) {
        return dao.updateConfiguration(1, "SuperAboutInfo", aboutInfo);
	}

	public String getTenantContactInfo() {
        logger.debug("Get ContactInfo from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "ContactInfo");
	}

	public int saveTenantContactInfo(String contactInfo) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "ContactInfo", contactInfo);
	}

	public String getSuperContactInfo() {
        logger.debug("Get SuperContactInfo from Configuration");
        return getConfigValue(1, "SuperContactInfo");
	}

	public int saveSuperContactInfo(String contactInfo) {
        return dao.updateConfiguration(1, "SuperContactInfo", contactInfo);
	}

	public String getTenantInvitationEmailContent() {
        logger.debug("Get InvitationEmail from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "InvitationEmailContent");
	}

	public int saveTenantInvitationEmailContent(String email) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "InvitationEmailContent", email);
	}

	public String getSuperInvitationEmailContent() {
        logger.debug("Get SuperInvitationEmail from Configuration");
        return getConfigValue(1, "SuperInvitationEmailContent");
	}

	public int saveSuperInvitationEmailContent(String email) {
        return dao.updateConfiguration(1, "SuperInvitationEmailContent", email);
	}

	public String getTenantVoiceOnlyContent() {
        logger.debug("Get InvitationEmail from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "VoiceOnlyContent");
	}

	public int saveTenantVoiceOnlyContent(String email) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "VoiceOnlyContent", email);
	}

	public String getSuperVoiceOnlyContent() {
        logger.debug("Get SuperVoiceOnly from Configuration");
        return getConfigValue(1, "SuperVoiceOnlyContent");
	}

	public int saveSuperVoiceOnlyContent(String email) {
        return dao.updateConfiguration(1, "SuperVoiceOnlyContent", email);
	}

	public String getTenantWebcastContent() {
        logger.debug("Get InvitationEmail from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "WebcastContent");
	}

	public int saveTenantWebcastContent(String email) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "WebcastContent", email);
	}

	public String getSuperWebcastContent() {
        logger.debug("Get SuperWebcast from Configuration");
        return getConfigValue(1, "SuperWebcastContent");
	}

	public int saveSuperWebcastContent(String email) {
        return dao.updateConfiguration(1, "SuperWebcastContent", email);
	}

	public String getTenantInvitationEmailSubject() {
        logger.debug("Get InvitationEmailSubject from Configuration");
        return getConfigValue(TenantContext.getTenantId(), "InvitationEmailSubject");
	}

	public int saveTenantInvitationEmailSubject(String emailSubject) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "InvitationEmailSubject", emailSubject);
	}

	public String getSuperInvitationEmailSubject() {
        logger.debug("Get SuperInvitationEmailSubject from Configuration");
        return getConfigValue(1, "SuperInvitationEmailSubject");
	}

	public int saveSuperInvitationEmailSubject(String emailSubject) {
        return dao.updateConfiguration(1, "SuperInvitationEmailSubject", emailSubject);
	}

	public int getTenantDefaultLocationTagID() {
        logger.debug("Get Tenant Default Location Tag ID from Configuration");
        String configValue = getConfigValue(TenantContext.getTenantId(), "DefaultLocationTagID");

		return configValue != null && configValue.trim().length() > 0 ? Integer.parseInt(configValue.trim()):1;
	}

	public int saveTenantDefaultLocationTagID(int locationID) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "DefaultLocationTagID", "" + locationID);
	}

	public boolean assignLocationToGroups(int locationID, String groupIDs) {
		return this.dao.assignLocationToGroups(locationID, groupIDs);
	}

	/**
	 * Upgrade system with .vidyo installer file
	 * @param filename An installer file with extension .vidyo
	 * @return
	 */
	public boolean upgradeSystem(String filename) {
		logger.warn("Upgrade System firmware with file [" + filename + "]");

		String cmd = "sudo -n " + SecurityServiceImpl.SCRIPT_HOME + "update_portal.sh";
		try {
			ShellCapture capture = ShellExecutor.execute(cmd + " " + filename);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Upgrade failed - " + filename);
				return false;
			}
		} catch (ShellExecutorException e) {
			logger.error("Upgrade failed due to shell exception ",
					e.getMessage());
			return false;
		}

		File failureMarker = new File("/opt/vidyo/temp/root/UPDATE_FAILED");
		if (failureMarker.exists()) {
			logger.error("Upgrade UI message file, found /opt/vidyo/temp/root/UPDATE_FAILED failure marker file");
			return false;
		}

		return true;
	}

	public boolean shutdownSystem() {
		logger.warn("Entering Shutdown System");
		boolean cmdResult = restartVidyoServer("SHUTDOWN_SYSTEM");
		logger.warn("Exiting Shutdown System");
		return cmdResult;
	}

	public boolean rebootSystem() {
		logger.warn("Entering Reboot System");
		boolean cmdResult = restartVidyoServer("REBOOT_SYSTEM");
		logger.warn("Exiting Reboot System");
		return cmdResult;
	}

	public boolean restartWebServer() {
		logger.warn("Entering Restart Web Server");
		boolean cmdResult = restartVidyoServer("RESTART_WEBSERVER");
		logger.warn("Exiting Restart Web Server");
		return cmdResult;
	}

	private boolean restartVidyoServer(String arg) {
		String command = "sudo -n " + SecurityServiceImpl.SCRIPT_HOME + "vidyo_server.sh"
				+ " " + arg;
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Failed to execute command " + command);
				return false;
			}
		} catch (ShellExecutorException e1) {
			logger.error("");
			return false;
		}
		return true;
	}

	public List<DbBackupFileInfo> getBackupFileInfo(){
		logger.debug("Get a list of DB backup file");
		List<DbBackupFileInfo> list = new ArrayList<DbBackupFileInfo>();

		File backupPath = new File(dbBackupPath);

		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				if(file.isDirectory())
					return false;
				else if(file.getName().startsWith("."))
					return false;
				return true;
			}
		};

		File[] files = backupPath.listFiles(fileFilter);
		for(int i=0; i<files.length; i++){
			File f = (File)files[i];
			DbBackupFileInfo backup = new DbBackupFileInfo(f.getName(), new Date(f.lastModified()));
			list.add(backup);
		}

		return list;
	}

	public boolean backupDatabase(String fName,int includeThumbNail){
		logger.debug("Backup database snapshot");
		String seats = String.valueOf(this.member.getCountAllSeats());
		String ports = this.license.getSystemLicenseFeature("Ports") != null ? this.license.getSystemLicenseFeature("Ports").getLicensedValue() : "2";
		String installs = String.valueOf(this.member.getCountAllInstalls());
		String multiTenant = this.license.getSystemLicenseFeature("MultiTenant") != null ? this.license.getSystemLicenseFeature("MultiTenant").getLicensedValue() : "false";
		//Ex: "mysqldump --host=qa.vidyo.com --port=3306 --user=vidyo --password=v1dy03x -n --database portal2 "
		//Script: mysqldump --host=$1 --port=$2 --user=$3 --password=$4 -n --database portal2 > $5.sql
		String aCommand =  "sudo -n " + SecurityServiceImpl.SCRIPT_HOME + "backup_database.sh " +fName+" "+seats+" "+ports+" "+installs+" "+multiTenant+" "+includeThumbNail;
		logger.debug("Calling script: "+aCommand);

		try {
			ShellCapture capture = ShellExecutor.execute(aCommand);
			if(capture == null || capture.getExitCode() != 0) {
				logger.error("Backup database failed " + aCommand);
				return false;
			}
		} catch (ShellExecutorException e) {
			logger.error("Backup database failed " + aCommand + " Error " + e.getMessage());
			return false;
		}

		return true;
	}

	public boolean deleteBackupFile(String filename) {
		// security
		if (filename != null) {
			filename = filename.replace("..", "").replace("/", "");
		}

		String[] command = new String[] {"sudo", "-n", SecurityServiceImpl.SCRIPT_VIDYO_FILE_UTILS, "DELETE_DB_BACKUP", filename};
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Failed to execute command " + command);
				return false;
			}
		} catch (ShellExecutorException sse) {
			logger.error("Exception while executing command " + command + " Exception -" + sse.getMessage());
			return false;
		}
		return true;

	}

	public String getDbBackupPath() {
		return this.dbBackupPath;
	}

	@TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache"}, removeAll=true)
	public boolean restoreDatabaseWithBackupFile(String filename) {
		String dbFileName =  dbBackupPath+"/"+filename;
		logger.debug("Restore database with file : ["+dbFileName+"]");

		/*String uid    = this.dataSource.getUsername();
		String pwd    = this.dataSource.getPassword();
		String uriStr = this.dataSource.getUrl().toLowerCase();*/
		String uid = null;
		String pwd = null;
		//String uriStr = null;
		try {
			uid = propertyPlaceholderConfigurer.getMergedProperties()
			.getProperty("jdbc.username");
			pwd = propertyPlaceholderConfigurer.getMergedProperties().getProperty("jdbc.password");
			//uriStr = propertyPlaceholderConfigurer.getMergedProperties().getProperty("jdbc.url");
		} catch (IOException e) {
			logger.error("IO Exception while reading portal.properties", e);
		}
		try {
			String scheme = "jdbc";         //  URI = scheme:scheme-part, jdbc here is the scheme
			String dbName = "mariadb";
			Pattern pattern = Pattern.compile( scheme+":"+dbName+"://(.*):(.*)/(.*)" );
			Matcher matcher = pattern.matcher(jdbcURL);
			if(!matcher.matches()) {
				logger.error("DataSource URI pattern matches wrong");
				return false;
			}

			String host = matcher.group(1);
			String port = matcher.group(2);

			String seats = this.license.getSystemLicenseFeature("Seats") != null ? this.license.getSystemLicenseFeature("Seats").getLicensedValue() : "10";
			String ports = this.license.getSystemLicenseFeature("Ports") != null ? this.license.getSystemLicenseFeature("Ports").getLicensedValue() : "2";
			String installs = this.license.getSystemLicenseFeature("Installs") != null ? this.license.getSystemLicenseFeature("Installs").getLicensedValue() : "10";
			String multiTenant = this.license.getSystemLicenseFeature("MultiTenant") != null ? this.license.getSystemLicenseFeature("MultiTenant").getLicensedValue() : "false";

			String dirName = filename.substring(0, filename.lastIndexOf(".veb"));

			String aCommand =  "sudo -n " + SecurityServiceImpl.SCRIPT_HOME + "restore_database.sh "+
								dirName + " "+seats+" "+ports+" "+installs+" "+multiTenant;
			logger.debug("Calling script: "+aCommand);

			try {
				ShellCapture capture = ShellExecutor.execute(aCommand);
				if(capture == null || capture.getExitCode() != 0) {
					logger.error("Restore database failed " + aCommand);
					return false;
				}
			} catch (ShellExecutorException e) {
				logger.error("Restore database failed " + aCommand + " Error " + e.getMessage());
				return false;
			}

		}
		catch(Exception ex){
			logger.error("Restore database process failed: "+ ex.getMessage());
			return false;
		}

		cacheAdministration.clearCaches();
		license.getSystemLicenseDetails();

		return true;
	}

	public boolean restoreDatabaseToFactoryDefaults() {
		logger.debug("Restore Database to Factory Defaults");
		// clear licenses
		boolean isLicenseCleared = license.clearLicense();
		if (!isLicenseCleared) {
			logger.error("Error while clearing license, not proceeding with Factory reset");
			return isLicenseCleared;
		}
		// invoke the create portal db script to reset the database
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + portalExecutablesPath + "portal_factory_default.sh");
		} catch (ShellExecutorException e) {
			logger.error("Error while performing Factory reset of the system");
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("Restoring to Factory Defaults failed", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
			return false;
		}
		cacheAdministration.clearCaches();
		license.getSystemLicenseDetails();

		logger.debug("Restored the system to Factory Defaults");
		return true;
	}

	public List<DbBackupFileInfo> getSystemBackupFileInfo(){
		logger.debug("Get a list of system backup file");
		List<DbBackupFileInfo> list = new ArrayList<DbBackupFileInfo>();

		File backupPath = new File(BACKUP_DIR);

		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				if(file.isDirectory())
					return false;
				else if(file.getName().startsWith("."))
					return false;
				return true;
			}
		};

		File[] files = backupPath.listFiles(fileFilter);
		if (files != null) {
			for(int i=0; i<files.length; i++){
				File f = (File)files[i];
				DbBackupFileInfo backup = new DbBackupFileInfo(f.getName(), new Date(f.lastModified()));
				list.add(backup);
			}
		}

		return list;
	}

	public boolean doSystemBackup() {
		try {
			ShellCapture capture = ShellExecutor.execute("/opt/vidyo/bin/backup_restore_portal.sh --backup");
			return capture.isSuccessExitCode();
		} catch (ShellExecutorException see) {
			logger.error("Shell exception when doing system backup. " + see.getMessage());
		}
		return false;
	}

	public boolean deleteSystemBackupFile(String filename) {
		String dbFileName =  BACKUP_DIR+"/"+filename;
		logger.debug("Delete system backup file: ["+dbFileName+"]");
		File f = new File(dbFileName);
		return f.delete();
	}

	@TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache"}, removeAll=true)
	public boolean restoreSystemWithBackupFile(String filename, String type) {
		String dbFileName =  BACKUP_DIR+"/"+filename;
		logger.debug("Restore system with file : ["+dbFileName+"]");

		try {
			ShellCapture capture = ShellExecutor.execute("/opt/vidyo/bin/backup_restore_portal.sh --restore-" + type + "  " + filename);
			return capture.isSuccessExitCode();
		} catch (ShellExecutorException see) {
			logger.error("Shell exception when restoring system backup." + see.getMessage());
		}

		return false;
	}

	@TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache"}, removeAll=true)
	public boolean restoreSystemToFactoryDefaults() {
		logger.debug("Restore system to Factory Defaults");
		try {
			ShellCapture capture = ShellExecutor.execute("/opt/vidyo/bin/factory_default.sh --keep-network");
			return capture.isSuccessExitCode();
		} catch (ShellExecutorException see) {
			logger.error("Shell exception when restoring system to factory defaults. " + see.getMessage());
		}

		return false;
	}

	public boolean downloadSystemBackup(String path, boolean result) {
		//Dummy method for interception
		return result;
	}

	public boolean uploadSystemBackup(String path, boolean result) {
		//Dummy method for interception
		return result;
	}

	public String getNotificationFromEmailAddress(){
        logger.debug("Get NotificationEmailFrom from Configuration, tenant="+TenantContext.getTenantId());
        return getConfigValue(TenantContext.getTenantId(), "NotificationEmailFrom");

	}

	public String getNotificationToEmailAddress(){
        logger.debug("Get NotificationEmailTo from Configuration, tenant="+TenantContext.getTenantId());
        return getConfigValue(TenantContext.getTenantId(), "NotificationEmailTo");
	}

	public boolean getEnableNewAccountNotification(){
        logger.debug("Get EnableNewAccountNotification from Configuration, tenant="+TenantContext.getTenantId());
        String configValue = getConfigValue(TenantContext.getTenantId(), "EnableNewAccountNotification");

        return configValue != null && configValue.equalsIgnoreCase("true");
	}

	public int saveNotificationFromEmailAddress(String addr) {
		if(addr != null){
			return dao.updateConfiguration(TenantContext.getTenantId(), "NotificationEmailFrom", addr);
		}
		else
			return -1;
	}

	public int saveNotificationToEmailAddress(String addr){
		if(addr != null){
			return dao.updateConfiguration(TenantContext.getTenantId(), "NotificationEmailTo", addr);
		}
		else
			return -1;
	}

	public int saveEnableNewAccountNotification(boolean enable) {
		return this.dao.updateConfiguration(TenantContext.getTenantId(), "EnableNewAccountNotification", Boolean.toString(enable));
	}

	public String getSuperNotificationFromEmailAddress(){
		String email = this.getSuperFromEmailAddress();
		return email;
	}
	public String getSuperNotificationToEmailAddress() {
		String email = this.getSuperToEmailAddress();
		return email;
	}
	public int saveSuperNotificationFromEmailAddress(String addr){
		if(addr !=null)
			return this.dao.updateConfiguration(0, "SuperEmailFrom", addr);
		else
			return -1;
	}
	public int saveSuperNotificationToEmailAddress(String addr) {
		if(addr != null)
			return this.dao.updateConfiguration(0, "SuperEmailTo", addr);
		else
			return -1;
	}

	public String getDbVersion() {
        logger.debug("Get DBVersion from Configuration");
        return getConfigValue(1, "DBVersion");
	}

	public String getPortalVersion() {
		String rc = this.getPortalVersion(1);
		return rc;
	}

	public CdrAccess readCDR(){
		CdrAccess ca = this.dao.readCDR(getCDRAccessFromHost(), getCDREnabled(), getCDRformat(), getCDRAllowDeny(), getCDRAccessPassword(), getCDRAllowDelete());
		return ca;
	}

	public boolean enableCDR(CdrAccess ca) {
		try {
			dao.updateConfiguration(1, "CdrEnabled", String.valueOf(ca.getEnabled()));
			dao.updateConfiguration(1, "CdrFormat", ca.getFormat());
		} catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean grantCDR(CdrAccess ca){
		try {
			ShellExecutor.execute("sudo -n /opt/vidyo/bin/firewallSettings.sh enableCdr " + ca.getIp());
		} catch (ShellExecutorException e) {
			logger.error("Error while enabling CDR access", e);
			return false;
		}
		dao.updateConfiguration(1, "CdrAllowDeny", String.valueOf("0"));

		boolean granted = this.dao.grantCDR(ca);
        dao.updateConfiguration(1, "CdrAccessFromHost", ca.getIp());
        dao.updateConfiguration(1, "CdrAccessPassword", VidyoUtil.encrypt(ca.getPassword()));
        dao.updateConfiguration(1, "CdrAllowDeny", String.valueOf(ca.getAllowdeny()));
        dao.updateConfiguration(1, "CdrAllowDelete", String.valueOf(ca.getAllowdelete()));

        return granted;
	}

	public boolean denyCDR(){
		try {
			ShellExecutor.execute("sudo -n /opt/vidyo/bin/firewallSettings.sh disableCdr");
		} catch (ShellExecutorException e) {
			logger.error("Error while disabling CDR access", e);
			return false;
		}
		dao.updateConfiguration(1, "CdrAllowDeny", String.valueOf("0"));

		return this.dao.denyCDR(getCDRAccessFromHost());
	}

	public String getCDREnabled() {
		logger.debug("Get CdrEnabled from Configuration");
		String configValue = getConfigValue(1, "CdrEnabled");

		return configValue != null && configValue.trim().length() > 0 ? configValue.trim():"0";
	}

	public String getCDRformat() {
		logger.debug("Get CdrEnabled from Configuration");
		String configValue = getConfigValue(1, "CdrFormat");

		return configValue != null && configValue.trim().length() > 0 ? configValue.trim():"1";
	}

	public int purgeCDR(CdrFilter cf) {
		int rc;
		if (cf == null) {
			rc = 0;
		} else {
			rc = this.dao.purgeCDR(cf);
		}
		return rc;
	}

	public void exportCDRBatched(CdrFilter cf, String csvFile) {
		if (cf == null) {
			return;
		}
		List<CdrRecord> records;

		int limit = 5000;
		int offset = 0;
		records = this.dao.exportCDRBatched(cf, limit, offset);

		CsvWriter writer = new CsvWriter(csvFile, ',', StandardCharsets.UTF_8);
		writer.setTextQualifier('"');
		writer.setUseTextQualifier(true);
		writer.setForceQualifier(true);
		writer.setEscapeMode(CsvWriter.ESCAPE_MODE_DOUBLED);

		try {
			writer.writeRecord(CdrRecord.RECORD_HEADER);
			while (!records.isEmpty()) {
				for (CdrRecord record : records) {
					writer.writeRecord(record.toArray());
				}
				writer.flush();
				if (records.size() < limit) {
					break; // we're done sooner
				}
				offset = offset + limit;
				records = this.dao.exportCDRBatched(cf, limit, offset);
			}
		} catch (IOException ioe) {
			logger.error("IOException during cdr export: " + ioe.getMessage());
		} finally {
			writer.flush();
			writer.close();
		}
	}

	public int getExportCDRCount(CdrFilter cf) {
		if (cf == null) {
			return 0;
		}
		return this.dao.getExportCDRCount(cf);
	}

	public long getTotalInstallsInUse(String cdrFormat) {
		return this.dao.getTotalInstallsInUse(cdrFormat);
	}

	public long getTotalSeatsInUse() {
		return this.dao.getTotalSeatsInUse();
	}

	public String getCustomizedLogoName(){
        logger.debug("Get Customized Logo from Configuration, tenant=" + TenantContext.getTenantId());
        return getConfigValue(TenantContext.getTenantId(), "logo");
	}

	public String getCustomizedLogoName(int tenantID){
        logger.debug("Get Customized Logo from Configuration, tenant=" + tenantID);
        return getConfigValue(tenantID, "logo");
	}

	public int saveCustomizedLogo(String filename) {
        return dao.updateConfiguration(TenantContext.getTenantId(), "logo", filename);
	}

	public String removeCustomizedImageLogo() {
		String logoFileName = getConfigValue(TenantContext.getTenantId(), "logoImage");
		if((logoFileName != null) && (logoFileName.length()>0)) {
			dao.updateConfiguration(TenantContext.getTenantId(), "logoImage", "");
			return logoFileName;
		}
		else
			return null;
	}

	public String getCustomizedImageLogoName(){
		String name = getConfigValue(TenantContext.getTenantId(), "logoImage");
		return name;
	}

	public int saveCustomizedImageLogo(String filename) {
		return dao.updateConfiguration(TenantContext.getTenantId(), "logoImage", filename);
	}

	public String removeCustomizedLogo() {
        String logoFileName = getConfigValue(TenantContext.getTenantId(), "logo");
        if((logoFileName != null) && (logoFileName.length()>0)) {
        	dao.updateConfiguration(TenantContext.getTenantId(), "logo", "");
            return logoFileName;
        }
        else
            return null;
	}

	public String getCustomizedSuperPortalLogoName(){
        logger.debug("Get Customized SuperPortalLogo from Configuration");
        return getConfigValue(1, "SuperPortalLogo");
	}

	public int saveCustomizedSuperPortalLogo(String filename) {
        return dao.updateConfiguration(1, "SuperPortalLogo", filename);
	}

	public String removeCustomizedSuperPortalLogo() {
        String logoFileName = getConfigValue(1, "SuperPortalLogo");
        if((logoFileName != null) && (logoFileName.length()>0)) {
            // If SuperPortalLogo is null, not exist record in DB, it means the logo never been uploaded by user
            // If SuperPortalLogo is "", it means the logo was once updated by user followed by being removed by user
            // Above difference is left for future use if needed.
        	dao.updateConfiguration(1, "SuperPortalLogo", "");
            return logoFileName;
        }
        else
            return null;
	}

	public String getCustomizedDefaultUserPortalLogoName(){
        logger.debug("Get Customized DefaultUserPortalLogo from Configuration");
        return getConfigValue(1, "DefaultUserPortalLogo");
	}

	public int saveCustomizedDefaultUserPortalLogo(String filename) {
        return dao.updateConfiguration(1, "DefaultUserPortalLogo", filename);
	}

	public String removeCustomizedDefaultUserPortalLogo() {
        String logoFileName = getConfigValue(1, "DefaultUserPortalLogo");
        if((logoFileName != null) && (logoFileName.length()>0)) {
            // If DefaultUserPortalLogo is null, not exist record in DB, it means the logo never been uploaded by user
            // If DefaultUserPortalLogo is "", it means the logo was once updated by user followed by being removed by user
        	dao.updateConfiguration(1, "DefaultUserPortalLogo", "");
            return logoFileName;
        }
        else
            return null;
	}

	public String getCustomizedSuperPortalImageLogoName(){
		String name = this.getCustomizedImageLogoName(0);
		return name;
	}

	public int saveCustomizedSuperPortalImageLogo(String filename) {
		int rc = this.dao.updateConfiguration(0, "logoImage", filename);
		return rc;
	}

	public String removeCustomizedSuperPortalImageLogo() {
		String logoFileName = getConfigValue(0, "logoImage");
		if((logoFileName != null) && (logoFileName.length()>0)) {
			dao.updateConfiguration(0, "logoImage", "");
			return logoFileName;
		}
		else
			return null;	}

	public int getTenantId(){
		return TenantContext.getTenantId();
	}

	public String getSystemId() {
		logger.debug("Get VidyoManager System ID");
		String systemID = "";
		VidyoManagerServiceStub ws = null;
		try {
			ws = this.getVidyoManagerServiceStubWithAUTH();
			GetVidyoManagerSystemIDRequest getVmIdReq = new GetVidyoManagerSystemIDRequest();
			GetVidyoManagerSystemIDResponse getVmIdResp = ws.getVidyoManagerSystemID(getVmIdReq);
			systemID = getVmIdResp.getVidyoManagerSystemID().toString();

		} catch(Exception e){
			logger.error("Failed to get VidyoManager System ID: "+e.getMessage());
		} finally {
			if (ws != null) {
				try {
					ws.cleanup();
				} catch (AxisFault af) {
					// ignore
				}
			}
		}
		return systemID;
	}

	public StatusNotification getStatusNotificationService() {
		// Hard code tenant id to 1 - as it is system specific
		StatusNotification statusNotificationDetails = getStatusNotificationDetails(1);
		return statusNotificationDetails;
	}

	public int saveStatusNotificationService(StatusNotification service) {
		try {
			this.dao.updateConfiguration(1, "StatusNotificationFlag", service.isFlag() ? "1" : "0"); //TenantContext.get()
			if (!service.isFlag()) return 1; // if flag is disabled - don't change values for url/user/password
		} catch (Exception e) {
			return -1;
		}
		try {
			String url = service.getUrl() != null ? service.getUrl() : "";
			this.dao.updateConfiguration(1, "StatusNotificationURL", url); //TenantContext.get()
		} catch (Exception e) {
			return -1;
		}
		try {
			String username = service.getUsername() != null ? service.getUsername() : "";
			this.dao.updateConfiguration(1, "StatusNotificationUsername", username); //TenantContext.get()
		} catch (Exception e) {
			return -1;
		}
		try {
			String password = (service.getPassword() != null) ? service.getPassword() : "";
            if (!PASSWORD_UNCHANGED.equals(password)) {
            	if (!password.isEmpty()) {
            		password = VidyoUtil.encrypt(password);
            	}
			    this.dao.updateConfiguration(1, "StatusNotificationPassword", password); //TenantContext.get()
            }
            if(password == null) {
            	//Delete the config for password
            }
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	@Cacheable(cacheName="tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public AuthenticationConfig getAuthenticationConfig(int tenantId) {
		logger.debug("AuthConfig Data retrieved from Database");

		AuthenticationConfig authConfig = new AuthenticationConfig();

		boolean wsflag = this.getAuthenticationFlag(tenantId);
        authConfig.setWsflag(wsflag);

		String wsurl = this.getAuthenticationURL(tenantId);
        authConfig.setWsurl(wsurl);

		String wsuser = this.getAuthenticationUsername(tenantId);
        authConfig.setWsusername(wsuser);

		String wspassword = this.getAuthenticationPassword(tenantId);
        authConfig.setWspassword(wspassword);

        //REST WS

        authConfig.setRestFlag(this.getRestAuthenticationFlag(tenantId));


        authConfig.setRestUrl(this.getRestAuthenticationURL(tenantId));


        //CAC

        authConfig.setCacflag(this.getCACAuthenticationFlag(tenantId));
        authConfig.setOcspcheck(this.getCACOCSPRevocationCheck(tenantId));
        authConfig.setUserNameExtractfrom(this.getCACUserExtractFrom(tenantId));
        authConfig.setOcspnonce(this.getCACOSCPNonce(tenantId));
        authConfig.setOcspresponder(this.getCACOCSPResponderText(tenantId));
        authConfig.setOcsprespondercheck(this.getCACOCSPResponderCheck(tenantId));
        authConfig.setCacldapflag(this.getCACLDAPAuthenticationFlag(tenantId));
        if("1".equalsIgnoreCase(authConfig.getCacldapflag())){
        	 authConfig.setLdapbase(this.getCACLDAPAuthenticationBase(tenantId));
             authConfig.setLdapfilter(this.getCACLDAPAuthenticationFilter(tenantId));
             //authConfig.setCacldapmappingflag(this.dao.getCACLDAPmappingflag(tenantId));
             authConfig.setLdappassword(this.getCACLDAPAuthenticationPassword(tenantId));
             authConfig.setLdapscope(this.getCACLDAPAuthenticationScope(tenantId));
             authConfig.setLdapurl(this.getCACLDAPAuthenticationURL(tenantId));
             authConfig.setLdapusername(this.getCACLDAPAuthenticationUsername(tenantId));

        }
		boolean ldapflag = this.getLDAPAuthenticationFlag(tenantId);
        authConfig.setLdapflag(ldapflag);
        //if it is ldap authentication.we need this check or pki ldap will fail...or we have to use another widget for pki ldap
        if(ldapflag){
        	String ldapurl = this.getLDAPAuthenticationURL(tenantId);
        	authConfig.setLdapurl(ldapurl);

        	String ldapuser = this.getLDAPAuthenticationUsername(tenantId);
        	authConfig.setLdapusername(ldapuser);

        	String ldappassword = this.getLDAPAuthenticationPassword(tenantId);
        	authConfig.setLdappassword(ldappassword);

        	String ldapbase = this.getLDAPAuthenticationBase(tenantId);
        	authConfig.setLdapbase(ldapbase);

        	String ldapfilter = this.getLDAPAuthenticationFilter(tenantId);
        	authConfig.setLdapfilter(ldapfilter);

        	String ldapscope = this.getLDAPAuthenticationScope(tenantId);
        	authConfig.setLdapscope(ldapscope);

        	boolean ldapmappingenabled = this.getLDAPAuthenticationAttributeMappingFlag(tenantId);
        	authConfig.setLdapmappingflag(ldapmappingenabled);
        }



        // SAML
        SamlAuthentication samlAuth = samlAuthenticationService.getSamlAuthenticationForTenant(tenantId);
        if (samlAuth != null) {
            authConfig.setSamlflag(samlAuthenticationService.isSAMLAuthenticationEnabled(tenantId));
            authConfig.setSamlIdpMetadata(samlAuth.getIdentityProviderMetadata());
            authConfig.setSamlSpEntityId(samlAuth.getSpEntityId());
            authConfig.setSamlSecurityProfile(samlAuth.getSecurityProfile());
            authConfig.setSamlSSLProfile(samlAuth.getSslTlsProfile());
            authConfig.setSamlSignMetadata(samlAuth.isSignMetadata() ? "YES" : "NO");
            authConfig.setSamlAuthProvisionType(samlAuth.getAuthProvisionType());
            if(authConfig.getSamlAuthProvisionType() == SamlProvisionType.LOCAL) {
                authConfig.setIdpAttributeForUsername(samlAuth.getIdpAttributeForUsername());
            }
        }

        //REST AUTH changes


		return authConfig;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int saveAuthenticationConfig(@PartialCacheKey int tenantId,
			AuthenticationConfig authConfig,String baseURL) {
		AuthenticationConfig oldAuthConfig = getAuthenticationConfig(tenantId);

		try {
			this.dao.updateConfiguration(tenantId, "AuthenticationFlag", authConfig.isWsflag() ? "1" : "0");
		} catch (Exception e) {
			logger.error("Error in saving Auth Flag - "+ e.getMessage());
			return -1;
		}
		if (authConfig.isWsflag()) {
			try {
				this.dao.updateConfiguration(tenantId, "AuthenticationURL", authConfig.getWsurl());
			} catch (Exception e) {
				logger.error("Error in saving URL - "+ e.getMessage());
				return -1;
			}
			try {
				this.dao.updateConfiguration(tenantId, "AuthenticationUsername", authConfig.getWsusername());
			} catch (Exception e) {
				logger.error("Error in saving Auth Username - "+ e.getMessage());
				return -1;
			}
			try {
				if (!PASSWORD_UNCHANGED.equals(authConfig.getWspassword())) {
					this.dao.updateConfiguration(tenantId, "AuthenticationPassword", VidyoUtil.encrypt(authConfig.getWspassword()));
				}
			} catch (Exception e) {
				logger.error("Error in saving Auth Password - "+ e.getMessage());
				return -1;
			}
		}
		try {
			this.dao.updateConfiguration(tenantId, "RestAuthenticationFlag", authConfig.isRestFlag() ? "1" : "0");
		} catch (Exception e) {
			logger.error("Error in saving Auth Flag - "+ e.getMessage());
			return -1;
		}
		if (authConfig.isRestFlag()) {
			try {
				this.dao.updateConfiguration(tenantId, "RestAuthenticationURL", authConfig.getRestUrl());
			} catch (Exception e) {
				logger.error("Error in saving URL - "+ e.getMessage());
				return -1;
			}

		}


		try {
			this.dao.updateConfiguration(tenantId, CACAuthenticationFlag, authConfig.isCacflag() ? "1" : "0");
		} catch (Exception e) {
			logger.error("Error in saving Auth Flag - "+ e.getMessage());
			return -1;
		}



		if (authConfig.isCacflag()) {

			boolean isOCSCPscriptCallNeeded=false;
			try {
				this.dao.updateConfiguration(tenantId, CACAuthenticationType, authConfig.getUserNameExtractfrom()==null?"0":authConfig.getUserNameExtractfrom());

			} catch (Exception e) {
				logger.error("Error in saving URL - "+ e.getMessage());
				return -1;
			}
			try {
				String tmpVal="0";
				if(authConfig.getOcspcheck()!=null &&"on".equalsIgnoreCase(authConfig.getOcspcheck())){
					tmpVal="1";

				}
				if(!tmpVal.equalsIgnoreCase(this.getCACOCSPRevocationCheck(tenantId))){
					isOCSCPscriptCallNeeded=true;

				this.dao.updateConfiguration(tenantId, CACOCSPRevocationCheck, tmpVal);
				}
			} catch (Exception e) {
				logger.error("Error in saving Auth OCSPRevocationCheck - "+ e.getMessage());
				return -1;
			}
			try {
				String tmpVal=authConfig.getOcsprespondercheck()==null?"0":authConfig.getOcsprespondercheck();
				if(isOCSCPscriptCallNeeded ||(!tmpVal.equalsIgnoreCase(this.getCACOCSPResponderCheck(tenantId))) ){
					isOCSCPscriptCallNeeded=true;
					this.dao.updateConfiguration(tenantId, CACOCSPResponderCheck, tmpVal);
				}
			} catch (Exception e) {
				logger.error("Error in saving Auth Username - "+ e.getMessage());
				return -1;
			}
			try {
				String tmpVal=authConfig.getOcspresponder()==null?"":authConfig.getOcspresponder();
				if(isOCSCPscriptCallNeeded ||(!tmpVal.equalsIgnoreCase(this.getCACOCSPResponderText(tenantId))) ){
					isOCSCPscriptCallNeeded=true;
					this.dao.updateConfiguration(tenantId, CACOCSPResponderText, tmpVal);
				}

			} catch (Exception e) {
				logger.error("Error in saving Auth Username - "+ e.getMessage());
				return -1;
			}
			try {
				String tmpVal=authConfig.getOcspnonce()==null?"0":authConfig.getOcspnonce();
				if(isOCSCPscriptCallNeeded ||(!tmpVal.equalsIgnoreCase(this.getCACOSCPNonce(tenantId))) ){
					isOCSCPscriptCallNeeded=true;
					this.dao.updateConfiguration(tenantId, CACOCSPNonceCheck, tmpVal);
				}

			} catch (Exception e) {
				logger.error("Error in saving Auth Username - "+ e.getMessage());
				return -1;
			}



			try {

				this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationFlag, authConfig.getCacldapflag());
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Flag - "+ e.getMessage());
				return -1;
			}
			if ("1".equalsIgnoreCase(authConfig.getCacldapflag())) {
				// for CAC, only admin and operator needed ldap authentication. so if ldap authentication is selected it is for admin and operator
				authConfig.setAuthFor("1,2");

				try {
					this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationURL, authConfig.getLdapurl());

				} catch (Exception e) {
					logger.error("Error in LDAP Auth URL - "+ e.getMessage());
					return -1;
				}
				try {
					this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationUsername, authConfig.getLdapusername());

				} catch (Exception e) {
					logger.error("Error in LDAP Auth Username - "+ e.getMessage());
					return -1;
				}
				try {
	                if (!PASSWORD_UNCHANGED.equals(authConfig.getLdappassword())) {
	                	this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationPassword, VidyoUtil.encrypt(authConfig.getLdappassword()));

					}

				} catch (Exception e) {
					logger.error("Error in LDAP Auth Password - "+ e.getMessage());
					return -1;
				}
				try {
					this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationBase, authConfig.getLdapbase());

				} catch (Exception e) {
					logger.error("Error in LDAP Auth Base - "+ e.getMessage());
					return -1;
				}
				try {
					String subs = "& lt;& gt;"; //"<>";
					String filter = authConfig.getLdapfilter().replace(subs.subSequence(0, subs.length()), "<>");
					this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationFilter, filter);
				} catch (Exception e) {
					logger.error("Error in LDAP Auth Filter - "+ e.getMessage());
					return -1;
				}
				try {
					this.dao.updateConfiguration(tenantId,CACLDAPAuthenticationScope,authConfig.getLdapscope());

				} catch (Exception e) {
					logger.error("Error in LDAP Auth Scope - "+ e.getMessage());
					return -1;
				}

			}
			if(isOCSCPscriptCallNeeded ){
				File certFile = new File( cacCertificateLocationStg+TenantContext.getTenantId() + CERTIFICATE_EXT);
				File permCertFile = new File( cacCertificateLocation+TenantContext.getTenantId() + CERTIFICATE_EXT);
				//if cert file is not uploaded we don't need to call ocsp script.
				if (certFile.exists()) {
						isOCSCPscriptCallNeeded=true;
						int statusCode=ocspSettingCal(tenantId,authConfig,baseURL);

						if(statusCode<0){
							return -5;
						}
				}
				if(permCertFile.exists()){
					// new thing added-store the dirty flag in db so that we know that we need webserver restart
					this.dao.updateConfiguration(tenantId, CACOCSPDIRTYFLAG, "1");
				}

			}


		}else{
			//not doing anything based on latest discussion. vptl 5999

//			if(oldAuthConfig.isCacflag()){
//				Path certFile = Paths.get(cacCertificateLocationStg +tenantId+CERTIFICATE_EXT);
//				Path unSavedFile = Paths.get(cacCertificateLocationStg +tenantId+SCRIPT_OCSP_UNSAVED);
//				if (Files.exists(certFile)) {
//					try {
//						Files.delete(certFile);
//					} catch (IOException e) {
//						logger.error("failed to delete certificate",e);
//					}
//
//				}
//				if (Files.exists(unSavedFile)) {
//					try {
//						Files.delete(unSavedFile);
//					} catch (IOException e) {
//						logger.error("failed to delete certificate",e);
//					}
//
//				}
//
//				//disabling ocsp-no need to catch the error
//				logger.warn("disabling ocsp call");
//				//TODO
//			//	int status=runOCSPScript(tenantId, baseURL, "off","off", "off", "", "");
//				//logger.debug("disabling ocsp call completed with status "+status);
//
//			}
		}


		try {
			this.dao.updateConfiguration(tenantId, "LDAPAuthenticationFlag", authConfig.isLdapflag() ? "1" : "0");
		} catch (Exception e) {
			logger.error("Error in LDAP Auth Flag - "+ e.getMessage());
			return -1;
		}
		if (authConfig.isLdapflag()) {
			try {
				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationURL", authConfig.getLdapurl());
			} catch (Exception e) {
				logger.error("Error in LDAP Auth URL - "+ e.getMessage());
				return -1;
			}
			try {
				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationUsername", authConfig.getLdapusername());
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Username - "+ e.getMessage());
				return -1;
			}
			try {
                if (!PASSWORD_UNCHANGED.equals(authConfig.getLdappassword())) {
					this.dao.updateConfiguration(tenantId, "LDAPAuthenticationPassword", VidyoUtil.encrypt(authConfig.getLdappassword()));
				}
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Password - "+ e.getMessage());
				return -1;
			}
			try {
				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationBase", authConfig.getLdapbase());
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Base - "+ e.getMessage());
				return -1;
			}
			try {
				String subs = "& lt;& gt;"; //"<>";
				String filter = authConfig.getLdapfilter().replace(subs.subSequence(0, subs.length()), "<>");

				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationFilter", filter);
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Filter - "+ e.getMessage());
				return -1;
			}
			try {
				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationScope", authConfig.getLdapscope());
			} catch (Exception e) {
				logger.error("Error in LDAP Auth Scope - "+ e.getMessage());
				return -1;
			}
			try {
				this.dao.updateConfiguration(tenantId, "LDAPAuthenticationAttributeMappingFlag", authConfig.isLdapmappingflag() ? "1" : "0");
			} catch (Exception e) {
				return -1;
			}
		}

        SamlAuthentication samlAuthentication;
        if (authConfig.isSamlflag()) {
            String idpAttributeForUsername = authConfig.getIdpAttributeForUsername();
            if(authConfig.getSamlAuthProvisionType() == SamlProvisionType.LOCAL &&
                    (idpAttributeForUsername == null || idpAttributeForUsername.isEmpty())) {
                return -3;
            }
            String idpXML =  StringUtils.trimToEmpty(authConfig.getSamlIdpMetadata());
            String subs = "& lt;& gt;"; //"<>";
            String filteredIdpXML = idpXML.replace(subs.subSequence(0, subs.length()), "<>");
            authConfig.setSamlIdpMetadata(filteredIdpXML);
            if (!samlAuthenticationService.isValidIdpXML(filteredIdpXML)) {
                return -2;
            }
            if((!oldAuthConfig.isSamlflag() && authConfig.isSamlflag() ||
            		oldAuthConfig.isSamlflag() && authConfig.isSamlflag() &&
            		!((SamlAuthentication)oldAuthConfig.toAuthentication()).getSpEntityId().equals(((SamlAuthentication) authConfig.toAuthentication()).getSpEntityId()))

            		&& samlAuthenticationService.isProviderExisting(authConfig.getSamlSpEntityId())) {
        		return -4;
        	}
            try {
                samlAuthenticationService.enableSAMLAuthentication(tenantId, (SamlAuthentication) authConfig.toAuthentication());
            } catch (Exception e) {
                return -1;
            }
            samlAuthentication = samlAuthenticationService.getSamlAuthenticationForTenant(tenantId);
        } else {
            try {
            	samlAuthentication = samlAuthenticationService.getSamlAuthenticationForTenant(tenantId);
                samlAuthenticationService.disableSAMLAuthentication(tenantId);
            } catch (Exception e) {
                return -1;
            }
        }

        final SamlAuthenticationStateChange samlAuthenticationStateChange = new SamlAuthenticationStateChange();
        samlAuthenticationStateChange.setTenantID(tenantId);
        samlAuthenticationStateChange.setSamlAuthentication(samlAuthentication);
        if(oldAuthConfig.isSamlflag() && !authConfig.isSamlflag()) {
        	// Remove the tenant config from the context
        	samlAuthenticationStateChange.setSamlAuthenticationState(SamlAuthenticationState.REMOVE);
        	sendSamlAuthMessageMQtopic(samlAuthenticationStateChange);
        } else if(!oldAuthConfig.isSamlflag() && authConfig.isSamlflag()) {
        	// Add the tenant config to the context
        	samlAuthenticationStateChange.setSamlAuthenticationState(SamlAuthenticationState.ADD);
        	sendSamlAuthMessageMQtopic(samlAuthenticationStateChange);
        } else if(oldAuthConfig.isSamlflag() && authConfig.isSamlflag()) {
        	SamlAuthentication oldAuth =(SamlAuthentication)oldAuthConfig.toAuthentication();
        	SamlAuthentication auth = (SamlAuthentication) authConfig.toAuthentication();
        	if(!oldAuth.getSecurityProfile().equals(auth.getSecurityProfile()) ||
        			!oldAuth.getSslTlsProfile().equals(auth.getSslTlsProfile()) ||
        			!oldAuth.getIdentityProviderMetadata().equals(auth.getIdentityProviderMetadata()) ||
        			oldAuth.isSignMetadata() != auth.isSignMetadata() ||
        			!oldAuth.getSpEntityId().equals(auth.getSpEntityId())) {
				notifyStateUpdate(samlAuthenticationStateChange, oldAuth, auth);
			} else if( null != auth.getIdentityProviderMetadata()
					&& !auth.getIdentityProviderMetadata().equals(oldAuth.getIdentityProviderMetadata())) {
				notifyStateUpdate(samlAuthenticationStateChange, oldAuth, auth);
			}
        }

		try {
			// Save auth roles
			// remove all auth roles for tenant
			this.dao.deleteTenantXauthRole(tenantId);
			// add new auth roles for tenant
			if (authConfig.getAuthFor() != null && !authConfig.getAuthFor().equalsIgnoreCase("")) {
				String[] roles = authConfig.getAuthFor().split(",");
				for (String roleID : roles) {
					this.dao.insertTenantXauthRole(tenantId, Integer.parseInt(roleID));
				}
			}
		} catch (Exception e) {
			logger.error("Error in saving Tenant Auth Role Mappings - "+ e.getMessage());
			return -1;
		}

		return 1;
	}

	private void notifyStateUpdate(SamlAuthenticationStateChange samlAuthenticationStateChange, SamlAuthentication oldAuth, SamlAuthentication auth) {
		if(!oldAuth.getSpEntityId().equals(auth.getSpEntityId())) {
			samlAuthenticationStateChange.setOldSpEntityId(oldAuth.getSpEntityId());
		}
		// Update the tenant config in the context
		samlAuthenticationStateChange.setSamlAuthenticationState(SamlAuthenticationState.UPDATE);
		sendSamlAuthMessageMQtopic(samlAuthenticationStateChange);
	}

	private int ocspSettingCal(int tenantId, AuthenticationConfig authConfig,String baseURL) {

		int statusCode=-1;
		 String ocspConfig="off";
		String ocspUrlOverride ="off";
		String ocspUrl="";
		String nonce="off";
		String cacflag="on";
		if(authConfig.getOcspcheck()==null || "0".equalsIgnoreCase(authConfig.getOcspcheck())){
			ocspConfig="off";
		}else {
			ocspConfig="on";
		}



       if(ocspConfig.equalsIgnoreCase("on")){
    	  ocspUrl =authConfig.getOcspresponder()==null?"":authConfig.getOcspresponder();
    	  ocspUrlOverride =authConfig.getOcsprespondercheck();
          if("on".equalsIgnoreCase(ocspUrlOverride)||"1".equalsIgnoreCase(ocspUrlOverride)){
        	  ocspUrlOverride="on";
        	  if(ocspUrl.equalsIgnoreCase("")){
        		  //invalid condition..
        		  logger.error("Invalid  condition. if the ocsp override flag is checked, then oscpurl cant be empty ");
        		  return -1;
        	  }
          }else{
        	  ocspUrlOverride="off";
          }

          if(authConfig.getOcspnonce()==null || "0".equalsIgnoreCase(authConfig.getOcspnonce())){
        	  nonce="off";
          }else if("on".equalsIgnoreCase(authConfig.getOcspnonce()) || "1".equalsIgnoreCase(authConfig.getOcspnonce())){
        	  nonce="on";
          }else{
        	  nonce="off";
          }



       }

        statusCode = runOCSPScript(tenantId, baseURL,cacflag,  ocspConfig,
				ocspUrlOverride, ocspUrl, nonce);

		return statusCode;


	}

	private int runOCSPScript(int tenantId, String baseURL, String cacflag,
			String ocspConfig, String ocspUrlOverride, String ocspUrl,
			String nonce) {
			logger.info("calling ocsp script with following param.tenantId:"+tenantId+"  baseURL: "+ baseURL+" cacflag:" +cacflag+"  ocspConfig:"+ocspConfig+" ocspUrlOverride:"+ocspUrl +"nonce: "+ nonce);
			//Map<String, String> ocspMap=new HashMap<String, String>();
			//ocspMap.put("URL", baseURL);
		//	ocspMap.put("OCSP", ocspConfig);
			//ocspMap.put("OCSP_OVERRIDE", ocspUrlOverride);
		//	ocspMap.put("OCSP_URL", ocspUrl);
			//ocspMap.put("OCSP_NONCE", nonce);;

		    List<String> pairs=new ArrayList<String>();
		  	//ocspMap.forEach((k,v) -> pairs.add(k+"="+v));
			try {//commented.eric is going to read it from db..no need of cfg file
				//Files.write(Paths.get(cacCertificateLocationStg +tenantId+".cfg"), pairs, StandardCharsets.UTF_8);
				if(Files.notExists(Paths.get(cacCertificateLocationStg +tenantId+SCRIPT_OCSP_UNSAVED))){
					Files.createFile(Paths.get(cacCertificateLocationStg +tenantId+SCRIPT_OCSP_UNSAVED));
				}

			} catch (Exception e) {
				logger.error("failed in writing to a file",e);
				return -1;
			}
		return 0;
	}
	@Override
	public int updateCACCfgFile(int tenantId,String baseURL){
		Path unSaved=Paths.get(cacCertificateLocationStg +tenantId+SCRIPT_OCSP_UNSAVED);
		if(Files.exists(unSaved)){
			return 0;
			//as we dont need to update the config as it is already dirty
		}else{
			AuthenticationConfig authConfig = getAuthenticationConfig(tenantId);

			if(authConfig.isCacflag()){
				return ocspSettingCal(tenantId,authConfig,baseURL);

			}else{
				logger.error("Invalid condition. this operation only allowed in PKI CAC authentication");
				return -1;
			}
		}


	}
	private void sendSamlAuthMessageMQtopic(final SamlAuthenticationStateChange samlAuthenticationStateChange) {
		try {
			jmsTemplate.send(samlAuthMessageMQtopic.getTopicName(), new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(samlAuthenticationStateChange);
				}
			});
			logger.warn("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . SAML auth message is fired");
		} catch (Exception e) {
			logger.error("Cannot send SamlAuthenticationStateChange (" + samlAuthenticationStateChange.getSamlAuthenticationState() + ") message to MQ", e.getMessage());
		}
	}

	public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH()
			throws NoVidyoManagerException
	{
		Service service = getVidyoManagerService();

		if (service == null) {
			logger.error("Cannot found VidyoManager for tenant with tenantID = " + TenantContext.getTenantId());
			throw new NoVidyoManagerException();
		}

		VidyoManagerServiceStub stub = processVidyoManagerServiceStubWithAUTH(service);

		return stub;
	}

	public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH(int tenantId)
			throws NoVidyoManagerException
	{
		Service service = getVidyoManagerService(tenantId);

		if (service == null) {
			logger.error("Cannot found VidyoManager for tenant with tenantID = " + tenantId);
			throw new NoVidyoManagerException();
		}

		VidyoManagerServiceStub stub = processVidyoManagerServiceStubWithAUTH(service);

		return stub;
	}

	private VidyoManagerServiceStub processVidyoManagerServiceStubWithAUTH(Service service) throws NoVidyoManagerException {
		String vidyoManagerServiceAddress = service.getUrl();
		String vidyoManagerServiceUser = service.getUser();
		String vidyoManagerServicePassword = service.getPassword();

		try {
			VidyoManagerServiceStub stub = new VidyoManagerServiceStub(axisConfigContext, vidyoManagerServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(vidyoManagerServiceUser);
			auth.setPassword(vidyoManagerServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoManagerException();
		}
	}

	/**
	 * Caller can try to get VidyoManagerServiceStub with given username and password, in case they are forced to changed.
	 * @throws NoVidyoManagerException
	 */
	public VidyoManagerServiceStub getVidyoManagerServiceStubWithAUTH(String username, String password)
			throws NoVidyoManagerException
	{
		Service service = getVidyoManagerService();

		if (service == null) {
			logger.error("Cannot found VidyoManager for tenant with tenantID = " + TenantContext.getTenantId());
			throw new NoVidyoManagerException();
		}

		String vidyoManagerServiceAddress = service.getUrl();

		try {
			VidyoManagerServiceStub stub = new VidyoManagerServiceStub(axisConfigContext, vidyoManagerServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(username);
			auth.setPassword(password);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoManagerException();
		}
	}

	public StatusNotificationServiceStub getStatusNotificationServiceStubWithAUTH(StatusNotification service)
			throws Exception
	{
		String statusNotificationServiceAddress = service.getUrl();
		String statusNotificationServiceUser = service.getUsername();
		String statusNotificationServicePassword = service.getPassword();

		try {
			StatusNotificationServiceStub stub = new StatusNotificationServiceStub(axisConfigContext, statusNotificationServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(statusNotificationServiceUser);
			auth.setPassword(statusNotificationServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	 public boolean restAuthenticationService(WsRestAuthentication auth,String username,String password ) throws Exception{


			final String uri = auth.getRestUrl();
			//TODO this has to change. In phase 2 we will let user to define the variable name and we create json dynamically.This user class will go.
			class User{
				private String username;
				private String password;
				public String getUsername() {
					return username;
				}
				public void setUsername(String username) {
					this.username = username;
				}
				public String getPassword() {
					return password;
				}
				public void setPassword(String password) {
					this.password = password;
				}
			}
				// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			User user =new User();
			user.setPassword(password);
			user.setUsername(username);
			HttpEntity<User> entity = new HttpEntity<>(user, headers);
			ResponseEntity<String> response=restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			//Check the response status as well as the response format to be a valid json. -https://jira.vidyo.com/browse/VPTL-7280
	        return (response.getStatusCodeValue()==200 && isValidJSON(response.getBody()));
	 }
	
	/**
	 * Makes a test connection to Tyto Server and returns back the HTTP status code.
	 */
	public ResponseEntity<String> restTytoCareAuthentication(String uri, String username, String password) {

		// set headers
		HttpHeaders headers = new HttpHeaders();
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		headers.add("Authorization", authHeader);

		HttpEntity entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		} catch (HttpClientErrorException e) {
			logger.error("TytoCare Server " + uri + " Test call failed due to an exception. HTTP code - "
					+ e.getStatusCode().value());
			response = ResponseEntity.status(e.getStatusCode()).build();
		} catch (Exception e) {
			// Handle any other exception - Categorize as not reachable - HTTP 408 - Request
			// timeout
			logger.error(
					"TytoCare Server " + uri + " Test call failed due to an exception - Unknown Host/Not Reachable.");
			response = ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
		}
		return response;
	}

	private boolean isValidJSON(final String json) throws IOException {
		boolean valid = true;
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			objectMapper.readTree(json);
		} catch(JsonProcessingException e){
			valid = false;
		}
		return valid;
	}
	public AuthenticationServiceStub getAuthenticationServiceStubWithAUTH(WsAuthentication wsAuth)
			throws Exception
	{
		String authenticationServiceAddress = wsAuth.getURL();
		String authenticationServiceUser = wsAuth.getUsername();
		String authenticationServicePassword = wsAuth.getPassword();

        if (PASSWORD_UNCHANGED.equals(wsAuth.getPassword())) {
        	//TODO - TenantId should be an argument to the method - ThreadLocal is a temporary solution for now
            authenticationServicePassword = getAuthenticationConfig(TenantContext.getTenantId()).getWspassword(); // get from db
        }

		try {
			AuthenticationServiceStub stub = new AuthenticationServiceStub(axisConfigContext, authenticationServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(authenticationServiceUser);
			auth.setPassword(authenticationServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public int testLDAPAuthenticationService(LdapAuthentication ldapAuth) {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapAuth.getLdapurl());
		contextSource.setUserDn(ldapAuth.getLdapusername());
		contextSource.setPassword(ldapAuth.getLdappassword());
		contextSource.setAnonymousReadOnly(true);
		contextSource.setPooled(false);
		contextSource.setCacheEnvironmentProperties(false);

		DirContext ctx = null;
		try {
			contextSource.afterPropertiesSet();
            String password = ldapAuth.getLdappassword();
            if (PASSWORD_UNCHANGED.equals(ldapAuth.getLdappassword())) {
            	//TODO - TenantId should be an argument to the method - ThreadLocal is a temporary solution for now
				password = getAuthenticationConfig(TenantContext.getTenantId()).getLdappassword(); // get from db
				contextSource.setPassword(password);
            }
			ctx = contextSource.getContext(ldapAuth.getLdapusername(), password);
			return 1;
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed - " + e.getMessage());
			return -1;
		} finally {
			// It is imperative that the created DirContext instance is always closed
			LdapUtils.closeContext(ctx);
		}
	}

	public int testLDAPUserAuthenticationService(LdapAuthentication ldapAuth) {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapAuth.getLdapurl());
		contextSource.setUserDn(ldapAuth.getLdapusername());
		contextSource.setPassword(ldapAuth.getLdappassword());
		contextSource.setAnonymousReadOnly(true);
		contextSource.setPooled(false);
		contextSource.setCacheEnvironmentProperties(false);

		DirContext ctx = null;
		DirContext userctx = null;
		try {
			contextSource.afterPropertiesSet();
            String password = ldapAuth.getLdappassword();
            if (PASSWORD_UNCHANGED.equals(ldapAuth.getLdappassword())) {
            	//TODO - TenantId should be an argument to the method - ThreadLocal is a temporary solution for now
				password = getAuthenticationConfig(TenantContext.getTenantId()).getLdappassword(); // get from db
				contextSource.setPassword(password);
            }
			ctx = contextSource.getContext(ldapAuth.getLdapusername(), password);
			// using username and password for testing user's login
			String dn = getDnForUser(ldapAuth.getUsername(), ldapAuth, ctx);
			if (dn.equalsIgnoreCase("")) {
				logger.error("Login failed");
				return -1;
			} else {
				userctx = contextSource.getContext(dn, ldapAuth.getPassword());
			}

			return 1;
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed - " + e.getMessage());
			return -1;
		} finally {
			// It is imperative that the created DirContext instance is always closed
			LdapUtils.closeContext(ctx);
			LdapUtils.closeContext(userctx);
		}
	}

	@Override
	public Member testLDAPUserMappingService(int tenantID, LdapAuthentication ldapAuth) {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapAuth.getLdapurl());
		contextSource.setUserDn(ldapAuth.getLdapusername());
		contextSource.setPassword(ldapAuth.getLdappassword());
		contextSource.setAnonymousReadOnly(true);
		contextSource.setPooled(false);
		contextSource.setCacheEnvironmentProperties(false);

		DirContext ctx = null;
		DirContext userctx = null;
		try {
			contextSource.afterPropertiesSet();
            String password = ldapAuth.getLdappassword();
            if (PASSWORD_UNCHANGED.equals(ldapAuth.getLdappassword())) {
            	//TODO - TenantId should be an argument to the method - ThreadLocal is a temporary solution for now
				password = getAuthenticationConfig(TenantContext.getTenantId()).getLdappassword(); // get from db
				contextSource.setPassword(password);
            }
			ctx = contextSource.getContext(ldapAuth.getLdapusername(), password);

			String dn = getDnForUser(ldapAuth.getUsername(), ldapAuth, ctx);
			if (dn.equalsIgnoreCase("")) {
				throw new BadCredentialsException( "Bad credentials");
			} else {
				userctx = contextSource.getContext(dn, ldapAuth.getPassword());

				String[] returnedAttrs = this.attributesMapper.getReturnedAttributes(tenantID);
				Attributes attrs = getLdapUsersAttributes(ldapAuth.getUsername(), ldapAuth, ctx, returnedAttrs);
				Member ldapMember = this.attributesMapper.getMemberFromAttributes(tenantID, attrs);
				if (ldapMember != null) {
					return ldapMember;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed - " + e.getMessage());
			return null;
		} finally {
			// It is imperative that the created DirContext instance is always closed
			LdapUtils.closeContext(ctx);
			LdapUtils.closeContext(userctx);
		}
	}

	public static String getDnForUser(String name, LdapAuthentication ldapAuth, DirContext ctx) throws Exception {
		LdapName baseName=null;
		if (!ldapAuth.getLdapbase().equalsIgnoreCase("")) {
			baseName=LdapUtils.newLdapName(ldapAuth.getLdapbase());
		}
		// from Request
		String filter = ldapAuth.getLdapfilter();
		if (filter.contains("<>")) {
			filter = filter.replace("<>".subSequence(0, "<>".length()), name);
		}

		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(Integer.valueOf(ldapAuth.getLdapscope()));
		//if we dont set empty array, all the attributes will be returned.In this getDnForUser we dont need all attributes but just cn. Added during thubmnail implementation
		String s[]={};
		ctls.setReturningAttributes(s);
		String dn = "";
		NamingEnumeration<SearchResult> result = ctx.search(baseName, filter, ctls);
		while (result.hasMoreElements()) {
			SearchResult searchResult = result.nextElement();
			dn = searchResult.getNameInNamespace();
		}

		return dn;
	}

	public static Attributes getLdapUsersAttributes(String name, LdapAuthentication ldapAuth, DirContext ctx, String[] returnedAttrs) throws Exception {
		Attributes rc = null;

		LdapName baseName=null;
		if (!ldapAuth.getLdapbase().equalsIgnoreCase("")) {
			baseName=LdapUtils.newLdapName(ldapAuth.getLdapbase());
	}
		String filter = ldapAuth.getLdapfilter();

		if (filter.contains("<>")) {
			filter = filter.replace("<>".subSequence(0, "<>".length()), name);
		}

		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(Integer.valueOf(ldapAuth.getLdapscope()));
		ctls.setReturningAttributes(returnedAttrs);

		NamingEnumeration<SearchResult> result = ctx.search(baseName, filter, ctls);
		while (result.hasMoreElements()) {
			SearchResult searchResult = result.nextElement();
			rc = searchResult.getAttributes();
		}

		return rc;
	}

	public List<MemberRoles> getFromRoles() {
		List<MemberRoles> list = this.dao.getFromRoles(TenantContext.getTenantId());
		return list;
	}

	public List<MemberRoles> getToRoles() {
		List<MemberRoles> list = this.dao.getToRoles(TenantContext.getTenantId());
		return list;
	}

	public int testWSUserAuthenticationService(WsAuthentication wsAuth, String testUser, String testPassword) {
		AuthenticationServiceStub authenticationService = null;
		try {
			authenticationService = getAuthenticationServiceStubWithAUTH(wsAuth);

			AuthenticationRequest req = new AuthenticationRequest();
			req.setUsername(testUser);
			req.setPassword(testPassword);

			AuthenticationResponse resp = authenticationService.authentication(req);
			if (resp != null && !resp.getPassed()) {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		} finally {
			if (authenticationService != null) {
				try {
					authenticationService.cleanup();
				} catch (AxisFault af) {
					// ignore
				}
			}
		}
		return 1;
	}

	public List<Control> getCurrentCalls(ControlFilter filter) {
		List<Control> list = this.dao.getCurrentCalls(filter);
		return list;
	}

	public Long getCountCurrentCalls() {
		Long number = this.dao.getCountCurrentCalls();
		return number;
	}

	public List<Control> getCurrentTenantCalls(ControlFilter filter) {
		List<Control> list = this.dao.getCurrentTenantCalls(TenantContext.getTenantId(), filter);
		return list;
	}

	public Long getCountCurrentTenantCalls() {
		Long number = this.dao.getCountCurrentTenantCalls(TenantContext.getTenantId());
		return number;
	}

	public DBProperties getDBProperties(ServletContext ctx) {
		DBProperties db = new DBProperties();
		FileInputStream is = null;
		try {
			is = new FileInputStream(PORTAL_PROPERTIES);

			Properties prop = new Properties();
			prop.load(is);

			db.setHostport(prop.getProperty("jdbc.hostport"));
			db.setUsername(prop.getProperty("jdbc.username"));
			db.setPassword(prop.getProperty("jdbc.password"));
		} catch (IOException ignored) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}

		return db;
	}

	public int setDBProperties(ServletContext ctx, DBProperties db) {
		FileInputStream is = null;
		FileOutputStream os = null;

		try {
			is = new FileInputStream(PORTAL_PROPERTIES);

			Properties prop = new Properties();
			prop.load(is);

			prop.setProperty("jdbc.hostport", db.getHostport());
			prop.setProperty("jdbc.username", db.getUsername());
			prop.setProperty("jdbc.password", db.getPassword());

			os = new FileOutputStream(PORTAL_PROPERTIES);
			prop.store(os, "edit");

			return 0;
		} catch (IOException ignored) {
			return -1;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public int testDBProperties(ServletContext ctx, DBProperties db) {
		Connection conn = null;
		PreparedStatement stmt = null;
		FileInputStream is = null;

		try {
			is = new FileInputStream(PORTAL_PROPERTIES);

			Properties prop = new Properties();
			prop.load(is);

			prop.setProperty("jdbc.hostport", db.getHostport());
			prop.setProperty("jdbc.username", db.getUsername());
			prop.setProperty("jdbc.password", db.getPassword());

			SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
			dataSource.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
			dataSource.setUrl(PropertyUtils.resolvePlaceholders(prop.getProperty("jdbc.url"), prop));
			dataSource.setUsername(prop.getProperty("jdbc.username"));
			dataSource.setPassword(prop.getProperty("jdbc.password"));

			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT 1");
			stmt.execute();

			return 0;
		} catch (IOException ignored) {
			return -1;
		} catch (SQLException e) {
			return -1;
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException ignored) {
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public void switchDBProperties(ServletContext ctx, DBProperties db) {
		FileInputStream is = null;

		try {
			//this.dataSource.close();

			is = new FileInputStream(PORTAL_PROPERTIES);

			Properties prop = new Properties();
			prop.load(is);

			prop.setProperty("jdbc.hostport", db.getHostport());
			prop.setProperty("jdbc.username", db.getUsername());
			prop.setProperty("jdbc.password", db.getPassword());

			/*this.dataSource.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
			this.dataSource.setUrl(PropertyUtils.resolvePlaceholders(prop.getProperty("jdbc.url"), prop));
			this.dataSource.setUsername(prop.getProperty("jdbc.username"));
			this.dataSource.setPassword(prop.getProperty("jdbc.password"));*/

		} catch (IOException ignored) {
		//} catch (SQLException ignored) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	private Service getVidyoReplayService(int tenantID)
		throws Exception
	{
		boolean secure = false;

		Service service = this.dao.getVidyoReplayService(tenantID);
		if (service != null) {
			// change URL to Web Service path
			StringBuffer rc = new StringBuffer();
			Tenant t = this.tenantService.getTenant(tenantID);
			String replayUrl = t.getTenantReplayURL();

            // there may be URLs without http or https (assume http)
			if (replayUrl != null && !replayUrl.equalsIgnoreCase("")) {
				if (replayUrl.contains("http://")) {
					replayUrl = replayUrl.replaceAll("http://", "");
					secure = false;
				}
				if (replayUrl.contains("https://")) {
					replayUrl = replayUrl.replaceAll("https://", "");
					secure = true;
				}
				String protocol = secure ? "https://" : "http://";
				rc.append(protocol).append(replayUrl).append("/replay/services/RecordingWebcastService");
				service.setUrl(rc.toString());
			} else {
				throw new Exception("No replay URL for the tenant");
			}
		} else {
			throw new Exception("No replay component for the tenant");
		}
		return service;
	}

	public RecordingWebcastServiceStub getRecordingWebcastServiceStubWithAUTH(int tenantID)
			throws NoVidyoReplayException
	{
		try {
			Service service = getVidyoReplayService(tenantID);

			String recordingWebcastServiceAddress = service.getUrl();
			String recordingWebcastServiceUser = service.getUser();
			String recordingWebcastServicePassword = service.getPassword();

			RecordingWebcastServiceStub stub = new RecordingWebcastServiceStub(axisConfigContext, recordingWebcastServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(recordingWebcastServiceUser);
			auth.setPassword(recordingWebcastServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoReplayException();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoVidyoReplayException();
		}
	}

	private Service getVidyoReplayUpdateService(int tenantID)
		throws Exception
	{
		boolean secure = false;

		Service service = this.dao.getVidyoReplayService(tenantID);
		if (service != null) {
			// change URL to Web Service path
			StringBuffer rc = new StringBuffer();
			Tenant t = this.tenantService.getTenant(tenantID);
			String replayUrl = t.getTenantReplayURL();

            // there may be URLs without http or https (assume http)
			if (replayUrl != null && !replayUrl.equalsIgnoreCase("")) {
				if (replayUrl.contains("http://")) {
					replayUrl = replayUrl.replaceAll("http://", "");
					secure = false;
				}
				if (replayUrl.contains("https://")) {
					replayUrl = replayUrl.replaceAll("https://", "");
					secure = true;
				}
				String protocol = secure ? "https://" : "http://";
				rc.append(protocol).append(replayUrl).append("/replay/services/ReplayUpdateParamService");
				service.setUrl(rc.toString());
			} else {
				throw new Exception("No replay URL for the tenant");
			}
		} else {
			throw new Exception("No replay component for the tenant");
		}
		return service;
	}

	public ReplayUpdateParamServiceStub getReplayUpdateParamServiceWithAUTH(int tenantID)
			throws NoVidyoReplayException
	{
		try {
			Service service = getVidyoReplayUpdateService(tenantID);

			String replayUpdateServiceAddress = service.getUrl();
			String replayUpdateServiceUser = service.getUser();
			String replayUpdateServicePassword = service.getPassword();

			ReplayUpdateParamServiceStub stub = new ReplayUpdateParamServiceStub(axisConfigContext, replayUpdateServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(replayUpdateServiceUser);
			auth.setPassword(replayUpdateServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoReplayException();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoVidyoReplayException();
		}
	}

	/*public List<RecorderEndpoint> getAvailableRecorders(RecorderEndpointFilter filter) {
		List<RecorderEndpoint> list = this.dao.getAvailableRecorders(TenantContext.getTenantId(), filter);
		return list;
	}

	public Long getCountAvailableRecorders() {
		Long number = this.dao.getCountAvailableRecorders(TenantContext.getTenantId());
		return number;
	}*/

	public List<RecorderPrefix> getAvailableRecorderPrefixes(RecorderEndpointFilter filter) {
		List<RecorderPrefix> list = this.dao.getAvailableRecorderPrefixes(TenantContext.getTenantId(), filter);
		return list;
	}

	public Long getCountAvailableRecorderPrefixes() {
		Long number = this.dao.getCountAvailableRecorderPrefixes(TenantContext.getTenantId());
		return number;
	}

	public VidyoFederationServiceStub getVidyoFederationServiceStubWithAUTH(String host, boolean secure)
			throws NoVidyoFederationException
	{
		String protocol = secure ? "https://" : "http://";
		String vidyoFederationServiceAddress = protocol + host + "/services/VidyoFederationService";
		String vidyoFederationServiceUser = "ipc";
		String vidyoFederationServicePassword = "ipc";

		try {
			VidyoFederationServiceStub stub = new VidyoFederationServiceStub(axisConfigContext, vidyoFederationServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(vidyoFederationServiceUser);
			auth.setPassword(vidyoFederationServicePassword);
			auth.setPreemptiveAuthentication(true);

			Options opt = stub._getServiceClient().getOptions();

			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response - JUST IN CASE IF NEEDED
			//opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION, HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER, this.connectionManager);

			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoFederationException();
		}
	}

	/**
	 *
	 */
	@Override
	@Cacheable(cacheName = "vidyoManagerIdCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public String getVidyoManagerID() {
		String rc = null;
		VidyoManagerServiceStub stub = null;
		try {
			stub = getVidyoManagerServiceStubWithAUTH();

			GetEMCPConfigRequest VMreq = new GetEMCPConfigRequest();
			GetEMCPConfigResponse VMresp = stub.getEMCPConfig(VMreq);
			AddressesPerVM[] addrs = VMresp.getEMCPListenAddress();

			if (addrs != null && addrs.length > 0) {
				rc = addrs[0].getId();
			}
		} catch (com.vidyo.ws.manager.InvalidArgumentFaultException e) {
			logger.error(e.getMessage());
		} catch (com.vidyo.ws.manager.GeneralFaultException e) {
			logger.error(e.getMessage());
		} catch (NotLicensedFaultException e) {
			logger.error(e.getMessage());
		} catch (ResourceNotAvailableFaultException e) {
			logger.error(e.getMessage());
		} catch (NoVidyoManagerException e) {
			logger.error(e.getMessage());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
		} finally {
            if (stub != null) {
                try {
                    stub.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
	    }

		return rc;
	}

	public int getIpcAllowDomainsFlag() {
		String value = getConfigValue(0,"ipcAllowDomainFlag");
		if (value != "") {
			int allowDomainsFlag = Integer.parseInt(value);
			return allowDomainsFlag;
		}

		logger.error("allowDomainsFlag configuration value is not available in the Configuration Table");
		return 0;
	}

	public int updateIpcAllowDomainsFlag(int allowFlag) {
		return dao.updateConfigurations(0, "ipcAllowDomainFlag", ""+allowFlag);
	}

	public boolean isIpcSuperManaged() {
		String value = getConfigValue(0,"ipcSuperManaged");
		if (value != "") {
			int isIpcSuperManaged = Integer.parseInt(value);
			return (isIpcSuperManaged == 1);
		}
		logger.error("ipcSuperManaged configuration value is not available in the Configuration Table");
		return false;
	}

	public int updateIpcAdmin(String ipcAdmin) {
		int flag = (ipcAdmin.equalsIgnoreCase("super") ? 1 : 0);
		return dao.updateConfiguration(0, "ipcSuperManaged", String.valueOf(flag));
	}

	public boolean downloadDatabase(String path, boolean result) {
		//Dummy method for interception
		return result;
	}
	public boolean uploadDatabase(String path, boolean result) {
		String[] command = new String[] {"sudo", "-n", SecurityServiceImpl.SCRIPT_VIDYO_FILE_UTILS, "COPY_DB_BACKUP", path};
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Failed to execute command " + command);
				return false;
			}
		} catch (ShellExecutorException sse) {
			logger.error("Exception while executing command " + command + " Exception -" + sse.getMessage());
			return false;
		}
		return true;
	}

	public boolean moveDatabase(String path) {
		String[] command = new String[] {"sudo", "-n", SecurityServiceImpl.SCRIPT_VIDYO_FILE_UTILS, "COPY_DB_BACKUP", path};
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Failed to execute command " + command);
				return false;
			}
		} catch (ShellExecutorException sse) {
			logger.error("Exception while executing command " + command + " Exception -" + sse.getMessage());
			return false;
		}
		return true;
	}

	public String getGuideLocation(String langCode, String guideType) throws IOException {
		/*String guideLoc = getConfigValue(0, guideType + "guidelocation_"
				+ langCode);

		if (guideLoc == null || guideLoc.equals("")) {
			// get url from the properties file
			String baseUrl = null;
			String fileName = null;
			String fileExtn = null;
			try {
				baseUrl = propertyPlaceholderConfigurer.getMergedProperties()
						.getProperty("baseUrl", null);
				fileExtn = propertyPlaceholderConfigurer.getMergedProperties()
						.getProperty("guide_extn", null);
				fileName = propertyPlaceholderConfigurer.getMergedProperties()
						.getProperty(guideType, null);
			} catch (IOException e) {
				logger.error("Exception while accessing portal properties", e);
				throw e;
			}

			if(baseUrl != null && fileName != null) {
				String portalVersion = getConfigValue(1, "PortalVersion");
				portalVersion = "v" + portalVersion.substring(portalVersion.indexOf("-")+1, portalVersion.indexOf("("));
				guideLoc = baseUrl + portalVersion + "/" + langCode.toUpperCase() + "/" + fileName + "_" + langCode.toUpperCase() + fileExtn;
			}
		}*/
		String guideLoc = propertyPlaceholderConfigurer.getMergedProperties()
				.getProperty("admin.guide.location", null);
		return guideLoc;
	}

	public int saveSystemConfig(String configName, String configValue,
			int tenantID) {
		int id = 0;
		boolean success = true;
		try {
			id = dao.updateConfiguration(tenantID, configName, configValue);
		} catch (Exception e) {
			success = false;
		}

		success = id > 0;

		if (configName.equalsIgnoreCase("SCHEDULED_ROOM_PREFIX")) {
			logSchRoomChanges("CREATE", configValue, null, success);
		}
		return id;
	}

	public int updateSystemConfig(String configName, String configValue) {
		int updateCount = dao.updateConfiguration(0, configName, configValue);
		return updateCount;

	}

	public Configuration getConfiguration(String configName) {
		/*if (superManager != null){
			boolean ifExists = superManager.cacheExists("configurationCache");
			Cache configurationCache = superManager.getCache("configurationCache");
			long size = configurationCache.getMemoryStoreSize();
			List<Long> keys = configurationCache.getKeys();
			for (Long key:keys){
				if (configurationCache.get(key).getObjectValue() != null)
				System.out.println("Name = "+ configurationCache.get(key).getObjectKey().toString() + "key = "+ key + " value = "+ ((Configuration)configurationCache.get(key).getObjectValue()).getConfigurationValue());
			}
		}*/
		return dao.getConfiguration(0, configName);
	}

	public boolean getEnableVidyoCloud() {
        logger.debug("Get EnableVidyoCloud from Configuration");
        String configurationValue = getConfigValue(1, "EnableVidyoCloud");
        return configurationValue != null && configurationValue.equals("1");
	}

	public int saveEnableVidyoCloud(boolean show) {
		return dao.updateConfiguration(1, "EnableVidyoCloud", (show? "1" : "0"));
	}

	/**
	 * Invokes the shell script to get the synchronization time and frequency
	 *
	 * @return
	 */
	public Configuration getDbSyncSchedule() {
		Configuration config = new Configuration();
		String script = "sudo -n /opt/vidyo/ha/bin/update_cron.sh GET_SETTINGS";
		config.setConfigurationName("");
		String configValue = null;
		try {
			configValue = ShellExecutor.execute(script).getStdOut();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
		}
		if(configValue != null ) {
			config.setConfigurationValue(configValue);

		}
		return config;
	}

	/**
	 * Invokes the shell script to setup the database synchrnoization frequency
	 *
	 * @param startTime
	 * @param endTime
	 * @param frequency
	 * @return
	 */
	public int setupDbSyncSchedule(int startTime, int endTime, int frequency) {
		String script = "sudo -n /opt/vidyo/ha/bin/update_cron.sh";
		script = script + " " + startTime + " " + endTime + " " + frequency;
		int statusCode = -1;
		try {
			statusCode = ShellExecutor.execute(script).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
		}
		/*if(statusCode == 0) {
			//Save the values in Config Table
			String val = startTime+";"+endTime+";"+frequency;
			Configuration config = dao.getConfiguration("dbSyncFreq");
			if(config == null) {
				dao.saveSystemConfig("dbSyncFreq", val, 0);
			} else {
				dao.updateConfigValue("dbSyncFreq", val);
			}

		}*/
		return statusCode;
	}

	/**
	 * Invokes the shell script to sync database immediately
	 *
	 * @return
	 */
	public int syncDatabase(String param) {
		String script = "sudo -n /opt/vidyo/ha/bin/create_backup.sh";
		try {
			return ShellExecutor.execute(script).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Invokes the shell script to switch portal to Maintenance mode
	 *
	 * @param param
	 * @return
	 */
	public int switchPortalToMaintenanceMode(String param) {
		if (param != null && param.equalsIgnoreCase("remove")) {
			try {
				return ShellExecutor.execute("sudo -n /opt/vidyo/ha/bin/maintenance.sh stop").getExitCode();
			} catch (ShellExecutorException e) {
				e.printStackTrace();
				return -1;
			}
		}
		try {
			return ShellExecutor.execute("sudo -n /opt/vidyo/ha/bin/maintenance.sh start").getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 *
	 * @param param
	 * @return
	 */
	public int forcePortalToStandbyMode(String param) {
		String script = "sudo -n /opt/vidyo/ha/bin/force_standby.sh start";
		int statusCode = 0;
		try {
			statusCode = ShellExecutor.execute(script).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
		logger.debug("forcePortalToStandbyMode -->"+statusCode);
		return statusCode;
	}

	/**
	 *
	 * @param param
	 */
	public ClusterInfo getClusterInfo(String param) {
		String script = null;
		//If on maintenance invoke other script
		if(isPortalOnMaintenance(null) == 0) {
			script = "sudo -n /opt/vidyo/ha/bin/peer_status.sh";
		} else {
			script = "sudo -n /opt/vidyo/ha/bin/clusterinfo.sh";
		}

		List<String> stdOutLines;
		try {
			ShellCapture capture = ShellExecutor.execute(script);
			stdOutLines = capture.getStdOutLines();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return null;
		}

		ClusterInfo clusterInfo = null;
		// Parse the putput string
		if (stdOutLines.size() > 0) {
			Map<String, String> tokensMap = new HashMap<String, String>();
			for (String token : stdOutLines) {
				String[] tokens = token.split("=");
				if (tokens.length == 2) {
					tokensMap.put(
							StringUtils.uncapitalize(tokens[0].trim()),
							tokens[1].trim());
				}

				}
			clusterInfo = new ClusterInfo();
			try {
				BeanUtils.populate(clusterInfo, tokensMap);
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException while populating bean", e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException while populating bean", e);
			}
			logger.debug("tokensMap--->" + tokensMap);
			return clusterInfo;
		}
		return clusterInfo;
	}

	/**
	 * Saves the HotStandby Security key in the respective table
	 *
	 * @param key
	 * @return
	 */
	public int saveHotStandbySecurityKey(String key) {
		int id = -1; //dao.saveHotStandbySecurityKey(key);

		try {
			File file = new File("/opt/vidyo/temp/tomcat/" + PortalUtils.generateRandomString(10) + ".sec.key");
			if (!file.exists())
				file.createNewFile();
			file.setReadable(true,false);
			file.setWritable(true);
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
			bWriter.write(key);
			bWriter.close();
			String script = "sudo -n /opt/vidyo/ha/bin/ha-gen-authkeys.sh "+ file.getAbsolutePath();
			id = ShellExecutor.execute(script).getExitCode();
			file.delete();
		} catch (ShellExecutorException | IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 *
	 * @param param
	 * @return
	 */
	public int isPortalOnMaintenance(String param) {
		String script = "sudo -n /opt/vidyo/ha/bin/is_maintenance.sh";
		try {
			return ShellExecutor.execute(script).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Deletes the Configuration Entry by Name and returns the delete count
	 *
	 * @param configName
	 * @return
	 */
	public int deleteConfiguration(String configName) {
		int deleteCount = 0;
		boolean success = true;
		String oldPrefix = null;
		if (configName.equalsIgnoreCase("SCHEDULED_ROOM_PREFIX")) {
			Configuration currentConfig = dao.getConfiguration(0, "SCHEDULED_ROOM_PREFIX");
			oldPrefix = (currentConfig != null ? currentConfig.getConfigurationValue() : null);
		}

		try {
			deleteCount = dao.deleteConfiguration(0, configName);
		} catch (Exception e) {
			success = false;
		}

		if (configName.equalsIgnoreCase("SCHEDULED_ROOM_PREFIX")) {
			logSchRoomChanges("DELETE", null, oldPrefix, success);
		}
		return deleteCount;

	}

	protected void logSchRoomChanges(String oper, String newPrefix,
			String oldPrefix, boolean operSuccess) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTenantName(tenantService.getTenant(
				TenantContext.getTenantId()).getTenantName());
		if (operSuccess) {
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			transactionHistory.setTransactionResult("FAILURE");
		}
		if (oper.equalsIgnoreCase("DELETE")) {
			transactionHistory
					.setTransactionName("Scheduled Room Feature Disabled - System wide");
			transactionHistory
					.setTransactionParams("Scheduled Room Prefix Deleted - "
							+ (oldPrefix != null ? oldPrefix
									: "No Previous value set"));
		}

		if (oper.equalsIgnoreCase("CREATE")) {
			transactionHistory
					.setTransactionName("Scheduled Room Feature Enabled - System wide");
			transactionHistory
					.setTransactionParams("Scheduled Room Prefix Created - "
							+ newPrefix);
		}

		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);

	}

	/**
	 *
	 * @param type
	 * @return
	 */
	public String getDesktopUserGuide(String type, String langCode) throws IOException{
		String guideLoc;
		try {
			guideLoc = propertyPlaceholderConfigurer.getMergedProperties().getProperty(type + "_" + langCode, null);
		} catch (IOException e) {
			logger.error("Exception while accessing portal properties", e);
			throw e;
		}
		return guideLoc;
	}

	/**
	 * Returns the list of Configurations with the ConfigName
	 * @param configNames
	 * @return
	 */
	@Override
	public List<Configuration> getConfigurations(List<String> configNames) {
		List<Configuration> configurations = new ArrayList<>();
		if (configNames != null && configNames.size() > 0) {
			String[] configs = configNames.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			for(String config:configs){
				configurations.add(dao.getConfiguration(0,config));
			}
		}
		return configurations;
	}


	/**
	 * Save vidyoweb available flag for super.
	 * @param flag
	 * @return
	 */
	public int saveVidyoWebAvailable(boolean flag) {
		return saveVidyoWebAvailable(0, flag);
	}

	/**
	 * Is vidyoweb available at super level.
	 * @return
	 */
	public boolean isVidyoWebAvailable() {
		String flag = getConfigValue(0, "VidyoWebAvailable");
		if (flag == null || "".equals(flag.trim())) {
			return true; // enabled by default, if not set
		} else {
			return Boolean.parseBoolean(flag);
		}
	}


	/**
     * Save vidyoweb enabled flag for super.
     * @param flag
     * @return
     */
    public int saveVidyoWebEnabledForSuper(boolean flag) {
        return saveVidyoWebEnabled(0, flag);
    }

    /**
     * Save vidyoweb enabled flag for tenant.
     * @param tenantId
     * @param flag
     * @return
     */
    public int saveVidyoWebEnabledForAdmin(int tenantId, boolean flag) {
        return saveVidyoWebEnabled(tenantId, flag);
    }

    /**
     * Is vidyoweb enbaled at super level.
     * @return
     */
    public boolean isVidyoWebEnabledBySuper() {
        String flag = getConfigValue(0, "VidyoWebEnabled");
        if (flag == null || "".equals(flag.trim())) {
            return false; // disabled by default, if not set
        } else {
            return Boolean.parseBoolean(flag);
        }
    }

    /**
     * Is vidyoweb enabled at admin (tenant) level.
     * @param tenantId
     * @return
     */
    public boolean isVidyoWebEnabledByAdmin(int tenantId) {
    	String flag = getConfigValue(tenantId, "VidyoWebEnabled");
        if (flag == null || "".equals(flag.trim())) {
            return false; // disabled by default, if not set
        } else {
            return Boolean.parseBoolean(flag);
        }
    }

    /**
     * Is vidyoweb enabled combining admin & super configs.
     * @param tenantId
     * @return
     */
    public boolean isVidyoWebEnabled(int tenantId) {
        return isVidyoWebAvailable() && isVidyoWebEnabledByAdmin(tenantId);
    }

	public boolean isWebRTCEnabledForGuests(int tenantId) {
		boolean webRTCForGuests = false;
		Configuration vidyoNeoWebRTUserConfiguration = getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
		if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
				&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
			TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
			webRTCForGuests = tenantConfiguration.getVidyoNeoWebRTCGuestEnabled() == 1;
		}

		Tenant t = tenantService.getTenant(TenantContext.getTenantId());
		String webrtcURL = t.getTenantWebRTCURL();

		if (webRTCForGuests && StringUtils.isNotBlank(webrtcURL)) {
			webRTCForGuests = true;
		} else {
			webRTCForGuests = false;
		}
		return webRTCForGuests;
	}

	public int saveZincSettings(int tenantId, String zincServer, boolean zincEnabled) {
		TenantConfiguration config = tenantService.getTenantConfiguration(tenantId);
		if (config == null) {
			config = new TenantConfiguration();
			config.setTenantId(tenantId);
		}
		config.setZincUrl(zincServer);
		config.setZincEnabled(zincEnabled ? 1 : 0);
		return tenantService.updateTenantConfiguration(config);
	}

	public boolean isZincEnabledForTenant(int tenantId) {
		TenantConfiguration config = tenantService.getTenantConfiguration(tenantId);
		if (config == null) {
			return false;
		}
		return config.getZincEnabled() == 1;
	}

	public String getZincServerForTenant(int tenantId) {
		TenantConfiguration config = tenantService.getTenantConfiguration(tenantId);
		if (config == null) {
			return null;
		}
		return config.getZincUrl();
	}

	/**
	 * Is show disabled rooms set at super level.
	 * @return
	 */
	public boolean isShowDisabledRooms() {
		String flag = getConfigValue(0, "ShowDisabledRooms");
		if (flag == null || "".equals(flag.trim())) {
			return false; // disabled by default, if not set
		} else {
			return Boolean.parseBoolean(flag);
		}
	}


	/**
	 * Save show disabled rooms enabled flag for super.
	 * @param flag
	 * @return
	 */
	public int saveShowDisabledRoomsForSuper(boolean flag) {
		return saveShowDisabledRooms(0, flag);
	}


	/**
	 * Creates backup of specific tables
	 *
	 * @param tableNames
	 *            Tables to be backed up
	 * @return boolean indicating if backup is successful or not
	 */
    @Override
	public boolean createTablesBackup(List<String> tableNames) {
		String cmd = "sudo -n /opt/vidyo/bin/portal_tables.sh EXPORT ";
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute(cmd);
		} catch (ShellExecutorException e) {
			logger.error("Error while executing backup for tables Tenant Room", e);
		}

		if (shellCapture.getExitCode() != 0) {
			return false;
		}

		return true;
	}

	/**
	 * Restores the database from the partial db backup created before tenant
	 * update
	 *
	 * @return boolean indicating if restore is successful or not
	 */
	@Override
	public boolean restoreFromPartialBackup() {
		String cmd = "sudo -n /opt/vidyo/bin/portal_tables.sh IMPORT";
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute(cmd);
		} catch (ShellExecutorException e) {
			e.printStackTrace();
		}

		if (shellCapture.getExitCode() != 0) {
			return false;
		}

		return true;
	}

	/**
	 * Deletes the partial database backup created before tenant update
	 *
	 * @return boolean indicating if deletion is successful or not
	 */
	@Override
	public boolean deletePartialBackup() {
		String cmd = "sudo -n /opt/vidyo/bin/portal_tables.sh DELETE";
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute(cmd);
		} catch (ShellExecutorException e) {
			e.printStackTrace();
		}

		if (shellCapture.getExitCode() != 0) {
			return false;
		}

		return true;
	}

    public DSCPValueSet getDSCPConfiguration(String userName, int tenentID ){
           logger.info("Getting DSCP values from DB Configuration");
            RootDocument doc = RootDocument.Factory.newInstance();
            com.vidyo.vcap20.Message message = doc.addNewRoot();
            RequestMessage req_message = message.addNewRequest();
            long bindUserRequestID = Long.valueOf(PortalUtils.generateNumericKey(9));
            req_message.setRequestID(bindUserRequestID);
            BindUserRequest bind_user_req = req_message.addNewBindUser();
            bind_user_req.setUsername(userName);
            UserProfileType user_prof_type = bind_user_req.addNewUserProfile();
            DSCPValueSet dscpValueSet = user_prof_type.addNewDSCPValues();

			 EndpointSettings es = getAdminEndpointSettings(tenentID);
            dscpValueSet.setMediaAudio(es.getDscpAudio());
            dscpValueSet.setMediaVideo(es.getDscpVideo());
            dscpValueSet.setMediaData(es.getDscpContent());
            dscpValueSet.setSignaling(es.getDscpSignaling());
            dscpValueSet.setOAM(es.getDscpOam());

            logger.info("DSCP values :" + dscpValueSet.toString());

            return   dscpValueSet;

        }


	@Cacheable(cacheName = "endpointSettingsCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "true")))
	public EndpointSettings getSuperEndpointSettings() {

		EndpointSettings es = new EndpointSettings();

		String dscpVideoConfig = getConfigValue(0, "SUPER_DSCP_MEDIA_VIDEO");
		if (StringUtils.isNotBlank(dscpVideoConfig)) {
			try {
				es.setDscpVideo(Integer.parseInt(dscpVideoConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String dscpAudioConfig = getConfigValue(0, "SUPER_DSCP_MEDIA_AUDIO");
		if (StringUtils.isNotBlank(dscpAudioConfig)) {
			try {
				es.setDscpAudio(Integer.parseInt(dscpAudioConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String dscpContentConfig = getConfigValue(0, "SUPER_DSCP_MEDIA_DATA");
		if (StringUtils.isNotBlank(dscpContentConfig)) {
			try {
				es.setDscpContent(Integer.parseInt(dscpContentConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String dscpSignalingConfig = getConfigValue(0, "SUPER_DSCP_SIGNALING");
		if (StringUtils.isNotBlank(dscpSignalingConfig)) {
			try {
				es.setDscpSignaling(Integer.parseInt(dscpSignalingConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String minMediaPortConfig = getConfigValue(0, "SUPER_MIN_MEDIA_PORT");
		if (StringUtils.isNotBlank(minMediaPortConfig)) {
			try {
				es.setMinMediaPort(Integer.parseInt(minMediaPortConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String maxMediaPortConfig = getConfigValue(0, "SUPER_MAX_MEDIA_PORT");
		if (StringUtils.isNotBlank(maxMediaPortConfig)) {
			try {
				es.setMaxMediaPort(Integer.parseInt(maxMediaPortConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		String alwaysUseVidyoProxyConfig = getConfigValue(0, "SUPER_ALWAYS_USE_VIDYO_PROXY");
		if (StringUtils.isNotBlank(alwaysUseVidyoProxyConfig)) {
			try {
				es.setAlwaysUseVidyoProxy("1".equals(alwaysUseVidyoProxyConfig));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		return es;
	}

	@TriggersRemove(cacheName = { "endpointSettingsCache" },  removeAll = true, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public boolean saveSuperEndpointSettings(EndpointSettings es) {
		try {
			dao.updateConfiguration(0, "SUPER_DSCP_MEDIA_VIDEO", ""+es.getDscpVideo());
			dao.updateConfiguration(0, "SUPER_DSCP_MEDIA_AUDIO", ""+es.getDscpAudio());
			dao.updateConfiguration(0, "SUPER_DSCP_MEDIA_DATA", ""+es.getDscpContent());
			dao.updateConfiguration(0, "SUPER_DSCP_SIGNALING", ""+es.getDscpSignaling());
			dao.updateConfiguration(0, "SUPER_MIN_MEDIA_PORT", ""+es.getMinMediaPort());
			dao.updateConfiguration(0, "SUPER_MAX_MEDIA_PORT", ""+es.getMaxMediaPort());
			dao.updateConfiguration(0, "SUPER_ALWAYS_USE_VIDYO_PROXY", (es.isAlwaysUseVidyoProxy() ? "1" : "0"));
		} catch (Exception e) {
			logger.error("Exception during saveSuperEndpointSettings", e);
			return false;
		}

		return true;
	}

	@Cacheable(cacheName = "endpointSettingsCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "true")))
	public EndpointSettings getAdminEndpointSettings(@PartialCacheKey int tenantID) {

		EndpointSettings es = new EndpointSettings();
		EndpointSettings superEndpointSettings = getSuperEndpointSettings();

		String dscpVideoConfig = getConfigValue(tenantID, "DSCP_MEDIA_VIDEO");
		if (StringUtils.isNotBlank(dscpVideoConfig)) {
			try {
				es.setDscpVideo(Integer.parseInt(dscpVideoConfig));
			} catch (NumberFormatException nfe) {
				es.setDscpVideo(superEndpointSettings.getDscpVideo());
			}
		} else {
			es.setDscpVideo(superEndpointSettings.getDscpVideo());
		}

		String dscpAudioConfig = getConfigValue(tenantID, "DSCP_MEDIA_AUDIO");
		if (StringUtils.isNotBlank(dscpAudioConfig)) {
			try {
				es.setDscpAudio(Integer.parseInt(dscpAudioConfig));
			} catch (NumberFormatException nfe) {
				es.setDscpAudio(superEndpointSettings.getDscpAudio());
			}
		} else {
			es.setDscpAudio(superEndpointSettings.getDscpAudio());
		}

		String dscpContentConfig = getConfigValue(tenantID, "DSCP_MEDIA_DATA");
		if (StringUtils.isNotBlank(dscpContentConfig)) {
			try {
				es.setDscpContent(Integer.parseInt(dscpContentConfig));
			} catch (NumberFormatException nfe) {
				es.setDscpContent(superEndpointSettings.getDscpContent());
			}
		} else {
			es.setDscpContent(superEndpointSettings.getDscpContent());
		}

		String dscpSignalingConfig = getConfigValue(tenantID, "DSCP_SIGNALING");
		if (StringUtils.isNotBlank(dscpSignalingConfig)) {
			try {
				es.setDscpSignaling(Integer.parseInt(dscpSignalingConfig));
			} catch (NumberFormatException nfe) {
				es.setDscpSignaling(superEndpointSettings.getDscpSignaling());
			}
		} else {
			es.setDscpSignaling(superEndpointSettings.getDscpSignaling());
		}

		String dscpOam = getConfigValue(tenantID, "DSCP_OAM"); // never set, but read?!
		if (StringUtils.isNotBlank(dscpOam)) {
			try {
				es.setDscpOam(Integer.parseInt(dscpOam));
			} catch (NumberFormatException nfe) {
				es.setDscpOam(superEndpointSettings.getDscpOam());
			}
		} else {
			es.setDscpOam(superEndpointSettings.getDscpOam());
		}

		TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantID);


		es.setMinMediaPort(tenantConfig.getMinMediaPort() == 0 ? superEndpointSettings.getMinMediaPort() : tenantConfig.getMinMediaPort());
		es.setMaxMediaPort(tenantConfig.getMaxMediaPort() == 0 ? superEndpointSettings.getMaxMediaPort() : tenantConfig.getMaxMediaPort());
		if (-1 == tenantConfig.getAlwaysUseProxy()) { // never set by admin, so use super values
			es.setAlwaysUseVidyoProxy(superEndpointSettings.isAlwaysUseVidyoProxy());
		} else {
			es.setAlwaysUseVidyoProxy(1 == tenantConfig.getAlwaysUseProxy());
		}

		return es;
	}

	@TriggersRemove(cacheName = { "endpointSettingsCache" }, removeAll = true,  keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public boolean saveAdminEndpointSettings(EndpointSettings es, int tenantId) {
		try {
			dao.updateConfiguration(tenantId, "DSCP_MEDIA_VIDEO", ""+es.getDscpVideo());
			dao.updateConfiguration(tenantId, "DSCP_MEDIA_AUDIO", ""+es.getDscpAudio());
			dao.updateConfiguration(tenantId, "DSCP_MEDIA_DATA", ""+es.getDscpContent());
			dao.updateConfiguration(tenantId, "DSCP_SIGNALING", ""+es.getDscpSignaling());

			TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
			tenantConfig.setMinMediaPort(es.getMinMediaPort());
			tenantConfig.setMaxMediaPort(es.getMaxMediaPort());
			tenantConfig.setAlwaysUseProxy(es.isAlwaysUseVidyoProxy() ? 1 : 0);
			tenantService.updateTenantConfiguration(tenantConfig);
		} catch (Exception e) {
			logger.error("Exception during saveSuperEndpointSettings", e);
			return false;
		}

		return true;
	}


	@Override
	public int getManageLocationTag() {
		int locationID = getTenantDefaultLocationTagID();

        //if default location is removed from current tenant, set it with "Default" location tag
        boolean found = false;
        List<Location> list = member.getLocationTags();
        for(Location loc : list) {
            if(locationID == loc.getLocationID()) {
                found = true;
            }
        }
        if(!found){
            locationID = 1;
            saveTenantDefaultLocationTagID(locationID);
        }

        return locationID;
	}


    /**
     * Returns the Syslog config details as key value pair
     *
     * @param param
     * @return
     */
    //@Override
    public Map<String, String> getSyslogConfigDetails(String param) {
        String cmd = "sudo -n /opt/vidyo/bin/configureSyslog.sh ";

		List<String> stdOutLines;
		try {
			ShellCapture capture = ShellExecutor.execute(cmd);
			if (capture.isErrorExitCode()) {
				return null;
			}
			stdOutLines = capture.getStdOutLines();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return null;
		}


        Map<String, String> syslogConfig = null;
        for (String line : stdOutLines) {
            String[] configVal = line.trim().split("=");
            if (syslogConfig == null) {
                syslogConfig = new HashMap<String, String>();
            }
            if (configVal != null && configVal.length == 2) {
                syslogConfig.put(configVal[0].toLowerCase(),
                        configVal[1].toLowerCase());
            }
        }
        return syslogConfig;
    }

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
    //@Override
    public int saveSyslogConfig(String syslogConfig, String useStunnel,
                                String ipAddress, String port) {
        String cmd = "sudo -n /opt/vidyo/bin/configureSyslog.sh ";
        if (syslogConfig.equalsIgnoreCase("on")) {
            cmd += "REMOTE_LOGGING=on STUNNEL=" + useStunnel + " IP_ADDRESS="
                    + ipAddress + " PORT=" + port;
        } else {
            cmd += "REMOTE_LOGGING=off";
        }

		try {
			return ShellExecutor.execute(cmd).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
    }

    public int updatePasswordValidityDaysConfig(String configValue) {
       return dao.updateConfiguration(0, "PASSWORD_VALIDITY_DAYS", configValue);
    }

    public Configuration getPasswordValidityDaysConfig() {
		Configuration configuration = dao.getConfiguration(0, "PASSWORD_VALIDITY_DAYS");
		return configuration;
	}

    public int updateLoginFalureCountConfig(String configValue) {
       return dao.updateConfiguration(1, "LOGIN_FAILURE_COUNT", configValue);
    }

    public Configuration getLoginFalureCountConfig() {
		Configuration configuration = dao.getConfiguration(1, "LOGIN_FAILURE_COUNT");
		return configuration;
	}

    public int updateInactiveDaysLimitConfig(String configValue) {
       return dao.updateConfiguration(1, "INACTIVE_DAYS_LIMIT", configValue);
    }

    public Configuration getInactiveDaysLimitConfig() {
		Configuration configuration = dao.getConfiguration(1, "INACTIVE_DAYS_LIMIT");
		return configuration;
	}

    public Configuration getUserPasswordRule() {
    	Configuration configuration = dao.getConfiguration(1, "MEMBER_PASSWORD_RULE");
    	return configuration;
    }

    public int updateUserPasswordRule(String configValue) {
       return dao.updateConfiguration(1, "MEMBER_PASSWORD_RULE", configValue);
    }

    public Configuration getUserLockoutTimeLimitinSecConfig() {
		Configuration configuration = dao.getConfiguration(1, "USER_LOCKOUT_TIME_LIMT_SECS");
		return configuration;
	}

    public Configuration getForcePasswordChangeConfig() {
		Configuration configuration = dao.getConfiguration(1, "FORCE_PASSWORD_CHANGE");
		return configuration;
	}

    public int updateForcePasswordChangeConfig(String configValue) {
       return dao.updateConfiguration(1, "FORCE_PASSWORD_CHANGE", configValue);
    }

    public Boolean getTLSProxyConfiguration(){
        Boolean tlsEnabled = false;
        String tlsValue = getConfigValue(0, "TLS_PROXY_ENABLED") ;
        if(tlsValue.equals("1")){
           tlsEnabled = true;
        }
        return tlsEnabled;
    }

    public int updateTlsProxyConfiguration(Boolean enabled){
        String tlsValue = "0";
        if(enabled)  {
          tlsValue = "1";
        }
        return dao.updateConfiguration(0, "TLS_PROXY_ENABLED", tlsValue) ;
    }

	@Override
	public long getTotalPortsInUse(String licVersion) {
//		return conferenceDao.getTotalPortsInUse(licVersion);
		return dao.getTotalPortsInUse(licVersion);
	}

	/**
	 * Clears License, VidyoManager and VMId cache across apps and nodes.
	 *
	 * @param tenantId
	 */
	public void clearVidyoManagerAndLicenseCache(int tenantId) {
		dao.clearVidyoManagerAndLicenseCache(tenantId);
	}

	/**
	 * Save chat available flag for super.
	 * @param flag
	 * @return
	 */
	@Override
	public int saveChatAvailable(boolean flag) {
		return dao.updateConfiguration(0, "chatAvailableFlag", Boolean.toString(flag));
	}

	/**
	 * Saves portal chat on super level. If portalChat.isChatAvailable() == false, then default public and private chats will be saved
	 * as disabled without taking into account their values in portalChat object
	 *
	 * @param portalChat
	 */
	@Override
	public void savePortalChat(PortalChat portalChat) {
		boolean isChatAvailable = portalChat.isChatAvailable();
		boolean isDefaultPublicChatEnabled = false;
		boolean isDefaultPrivateChatEnabled = false;

		if(isChatAvailable) {
			isDefaultPublicChatEnabled = portalChat.isDefaultPublicChatEnabled();
			isDefaultPrivateChatEnabled = portalChat.isDefaultPrivateChatEnabled();
		}

		saveChatAvailable(isChatAvailable);
		saveDefaultPublicChatEnabled(isDefaultPublicChatEnabled);
		saveDefaultPrivateChatEnabled(isDefaultPrivateChatEnabled);
	}

	@Override
	public PortalChat getPortalChat() {
		boolean isChatAvailable = isChatAvailable();
		boolean isDefaultPublicChatEnabled = false;
		boolean isDefaultPrivateChatEnabled = false;

		if(isChatAvailable) {
			isDefaultPublicChatEnabled = isDefaultPublicChatEnabled();
			isDefaultPrivateChatEnabled = isDefaultPrivateChatEnabled();
		}

		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(isChatAvailable);
		portalChat.setDefaultPublicChatEnabled(isDefaultPublicChatEnabled);
		portalChat.setDefaultPrivateChatEnabled(isDefaultPrivateChatEnabled);

		return portalChat;
	}

	/**
	 * @param transactionService the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public void saveTiles16Available(boolean isTiles16Available) {
 		dao.updateConfiguration(0, "tiles16AvailableFlag", Boolean.toString(isTiles16Available));
	}

	/**
	 * Utility method to create Email Invite Content for Invite Room
	 * @param inviteEmailContentForInviteRoom
	 * @return
	 */
	@Override
	public String constructEmailInviteContentForInviteRoom(InviteEmailContentForInviteRoom inviteEmailContentForInviteRoom,
			String content, boolean encodeForHtml) {

		if (StringUtils.isBlank(content)){
			logger.info("Content is empty / null for tenant ="+ inviteEmailContentForInviteRoom.getRoom().getTenantID());
			return content;
		}

		Locale l = Locale.getDefault();
		if(inviteEmailContentForInviteRoom.getLocale() != null) {
			l = inviteEmailContentForInviteRoom.getLocale();
		}

		Room room = inviteEmailContentForInviteRoom.getRoom();

		if(inviteEmailContentForInviteRoom.isOverrideScheduledRoomProperties() && room.getRoomType().equalsIgnoreCase("Scheduled")) {
			roomService.overrideScheduledRoomProperties(room);
		}

		String dialString = null;
		String pinStr = null;
		// create the roomlink, tenantDailIn+ext, for replacing [ROOMLINK] and
		// [EXTENSION]
		String extension = room.getRoomExtNumber();
		if(extension != null) {
			content = content.replaceAll("\\[EXTENSION_ONLY\\]", extension);
		}
		String dialIn = inviteEmailContentForInviteRoom.getTenantDialIn();

		content = content.replaceAll("\\[DIALIN_NUMBER\\]", dialIn != null ? dialIn : "");

		if (dialIn != null && dialIn.trim().length() > 0) {
			dialString = dialIn.concat("x").concat(extension);
		}

		// VP-5282 Control meeting HTML UI : Invite via email button should check whether links are available before starting email client
		if (room.getRoomKey() == null) {
			roomService.generateRoomKey(room);
		}

		StringBuffer path = new StringBuffer();
		/*String transportName = inviteEmailContentForInviteRoom.getTransportName();
		path.append(transportName).append("://").append(inviteEmailContentForInviteRoom.getTenantUrl());
		path.append("/flex.html?roomdirect.html&key=");
		path.append(room.getRoomKey());

		String url = path.toString();*/

		String joinURL = roomService.getRoomURL(this, inviteEmailContentForInviteRoom.getTransportName(),
				inviteEmailContentForInviteRoom.getTenantUrl(), room.getRoomKey());

		path.append(joinURL);
		if (!inviteEmailContentForInviteRoom.isGuestsAllowed()) {
			path.append("&noguest");
		}

		String pin = "";
		if (inviteEmailContentForInviteRoom.getRoom().getRoomPIN() != null) {
			pin = room.getRoomPIN();
			pinStr = " (Room PIN: " + room.getRoomPIN() + ") ";
			if (dialString != null) {
				dialString = dialString.concat("*").concat(room.getRoomPIN());
			}
			if (extension != null) {
				extension = extension.concat("*").concat(room.getRoomPIN());
			}
		} else {
			path.append(" ");
			pinStr = "";
		}

		if(inviteEmailContentForInviteRoom.getRoom().getRoomPIN() != null) {
			content = content.replaceAll("\\[PIN\\]", pinStr);
			content = content.replaceAll("\\[PIN_ONLY\\]", pin);
		} else {
			content = content.replaceAll("\\[PIN\\]", "");
			content = content.replaceAll("\\[PIN_ONLY\\]", "");
		}

		if (extension != null) {
			content = content.replaceAll("\\[EXTENSION\\]", extension);
		}

		if (dialString != null) {
			content = content.replaceAll("\\[DIALSTRING\\]", dialString);
		}

		// Determine type of room to set DisplayName/RoomName
		if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
			content = content.replaceAll("\\[DISPLAYNAME\\]", Matcher.quoteReplacement(encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName()));
		} else if (room.getRoomType().equalsIgnoreCase("Public")) {
			content = content.replaceAll("\\[DISPLAYNAME\\]", Matcher.quoteReplacement(encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getDisplayName()) : room.getDisplayName()));
		} else {
			content = content.replaceAll("\\[DISPLAYNAME\\]", Matcher.quoteReplacement(encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getOwnerDisplayName()) : room.getOwnerDisplayName()));
		}

		// Room Name is always the room name and not display name
		content = content.replaceAll("\\[ROOMNAME\\]", encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName());

		content = content.replaceAll("\\[TENANTURL\\]", inviteEmailContentForInviteRoom.getTenantUrl());

		content = content.replaceAll("\\[ROOMLINK\\]", path != null ? path.toString() : "");

		// first try
		String windowsGuide = "";
		try {
			windowsGuide = getDesktopUserGuide("windows", l.toString()); // lang_Country
		} catch (IOException ignored) {
			// Log the error
			logger.error("Exception While Accessing portal.properties: {}", ignored.getMessage());
		}
		// second try
		if (windowsGuide == null || windowsGuide == "") {
			try {
				windowsGuide = getDesktopUserGuide("windows", l.getLanguage()); // just a lang
			} catch (IOException ignored) {
				logger.error("Exception While Accessing portal.properties: {}", ignored.getMessage());
			}
		}
		content = content.replaceAll("\\[WINDOWS_GUIDE\\]", windowsGuide != null ? windowsGuide : "");

		// first try
		String macGuide = "";
		try {
			macGuide = getDesktopUserGuide("mac", l.toString()); // lang_Country
		} catch (IOException ignored) {
			logger.error("Exception While Accessing portal.properties: {}", ignored.getMessage());
		}
		// second try
		if (macGuide == null || macGuide == "") {
			try {
				macGuide = getDesktopUserGuide("mac", l.getLanguage()); // just a lang
			} catch (IOException ignored) {
				logger.error("Exception While Accessing portal.properties: {}", ignored.getMessage());
			}
		}
		content = content.replaceAll("\\[MAC_GUIDE\\]", macGuide != null ? macGuide : "");

		String gateWayDns = inviteEmailContentForInviteRoom.getGateWayDns();
		if(gateWayDns != null && !gateWayDns.isEmpty()) {
			if(room.getRoomType().equalsIgnoreCase("Public")) {
				content = content.replaceAll("\\[LEGACY_URI\\]", encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName() + "@" + gateWayDns);
			} else if(room.getRoomType().equalsIgnoreCase("Scheduled")) {
				content = content.replaceAll("\\[LEGACY_URI\\]", encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName() + "@" + gateWayDns + " " + pinStr);
			} else {
				content = content.replaceAll("\\[LEGACY_URI\\]", encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName() + "@" + gateWayDns);
			}
		} else {
			content = content.replaceAll("\\[LEGACY_URI\\]", ms.getMessage("undefined", null, "", l));
		}

		Member user = this.member.getMember(TenantContext.getTenantId(),
				inviteEmailContentForInviteRoom.getRoom().getMemberID());

		// Replace User Title

		content = content.replaceAll("\\[USER_TITLE\\]", user.getTitle() != null ? user.getTitle() : "");

		// Replace user display name
		content = content.replaceAll("\\[USER_DISPLAYNAME\\]", Matcher.quoteReplacement(user.getMemberName() != null ? (encodeForHtml ? ESAPI.encoder().encodeForHTML(user.getMemberName()) : user.getMemberName()): ""));


		// Replace room name
		content = content.replaceAll("\\[ROOMNAME\\]", Matcher.quoteReplacement(encodeForHtml ? ESAPI.encoder().encodeForHTML(room.getRoomName()) : room.getRoomName()));

		// Replace the AVATAR

		String image = this.member.getThumbNailImage(TenantContext.getTenantId(),
				inviteEmailContentForInviteRoom.getRoom().getMemberID());
		if (image == null) {
			//getting the default avatar
			image = Arrays.stream(user.getMemberName().split("\\s+")).map(s -> s.substring(0, 1).toUpperCase()).collect(Collectors.joining());

		}
		content = content.replaceAll("\\[AVATAR\\]", image != null ? image : "");

		// Replace tenant logo
		String name = StringUtils.trimToEmpty(getConfigValue(TenantContext.getTenantId(), "logoImage"));

		if((name!=null) && (name.length()!=0)) {
        	if(name.contains("/")) {
        		name = name.substring(name.lastIndexOf("/") + 1);
        	}
            File logoFile = new File(upload_dir + name);
			content = content.replaceAll("\\[TENANT_LOGO\\]", logoFile.exists() ? inviteEmailContentForInviteRoom.getTransportName() + "://" +
					inviteEmailContentForInviteRoom.getTenantUrl() + "/admin/upload/" + logoFile.getName():"");
		} else {
			content = content.replaceAll("\\[TENANT_LOGO\\]","");
		}

		content = content.replaceAll("\\[INTERNATIONAL_DIALIN\\]", inviteEmailContentForInviteRoom.getTransportName() + "://" +
				inviteEmailContentForInviteRoom.getTenantUrl() + "/dial/"+room.getRoomKey());
		
		// DEFAULT AVATAR
		content = content.replaceAll("\\[DEFAULT_AVATR\\]", inviteEmailContentForInviteRoom.getTransportName() + "://" +
				inviteEmailContentForInviteRoom.getTenantUrl() + "/upload/defaultprofile.png");
		// GLOBE ICON
		content = content.replaceAll("\\[GLOBE_ICON\\]", inviteEmailContentForInviteRoom.getTransportName() + "://" +
				inviteEmailContentForInviteRoom.getTenantUrl() + "/upload/globeicon.png");
		// PHONE ICON
		content = content.replaceAll("\\[PHONE_ICON\\]", inviteEmailContentForInviteRoom.getTransportName() + "://" +
				inviteEmailContentForInviteRoom.getTenantUrl() + "/upload/phoneicon.png");
		// ROOM PIN ICON
		content = content.replaceAll("\\[ROOM_PIN_ICON\\]", inviteEmailContentForInviteRoom.getTransportName() + "://" +
				inviteEmailContentForInviteRoom.getTenantUrl() + "/upload/roompin.png");

		return content;
	}

	/**
	 * Utility method to create Email Invite Subject for Invite Room
	 * @param loc
	 * @return
	 */
	@Override
	public String constructEmailInviteSubjectForInviteRoom(Locale loc) {
		Locale l = Locale.getDefault();
		if(loc != null) {
			l = loc;
		}

		String subject = getTenantInvitationEmailSubject();
		if (subject == null || subject.trim().length() == 0) {
			subject = getSuperInvitationEmailSubject();
			if (subject == null || subject.trim().length() == 0) {
				subject = this.ms.getMessage("defaultInvitationEmailSubject", null, "", l);
			}
		}

		return subject;
	}

    public boolean isAuthenticationLocalForMemberRole(int tenantId, int roleId) {
        if (tenantId <= 0) {
            logger.warn("Auth local for tenantId: " + tenantId + " / roleId: "  + roleId);
            return true;
        }

        Authentication auth = getAuthenticationConfig(tenantId).toAuthentication();
        if (auth instanceof InternalAuthentication) {
            logger.info("Auth [internal] local for tenantId: " + tenantId + " / roleId: "  + roleId);
            return true;
        } else if (auth instanceof SamlAuthentication) {
            // Get the SAML member roles which are hardcoded now in DB (Normal & Executive)
            List<MemberRoles> memberRolesList = member.getSamlMemberRoles();
            for(MemberRoles memberRole : memberRolesList) {
            	if(memberRole.getRoleID() == roleId) {
            		logger.info("Auth [SAML] not local for tenantId: " + tenantId + " / roleId: "  + roleId);
            		return false;
            	}
            }
            logger.info("Auth local (SAML) for tenantId: " + tenantId + " / roleId: "  + roleId);
            return true;
        } else { // WS, LDAP
            List<MemberRoles> list = this.getToRoles();
            if (list != null && list.size() > 0) {
                for (MemberRoles memberRoles : list ) {
                    if (memberRoles.getRoleID() == roleId) {
                        logger.info("Auth [WS or LDAP] not local for tenantId: " + tenantId + " / roleId: "  + roleId);
                        return false;
                    }
                }
            }
            logger.info("Auth local (WS, LDAP) for tenantId: " + tenantId + " / roleId: "  + roleId);
            return true;
        }
    }

	public int updateTenantGuestSettingsLocationTag(int deletedLocationID, int defaultLocationTagID) {
		return updateTenantGuestSettingsLocationTag(TenantContext.getTenantId(), deletedLocationID, defaultLocationTagID);
	}

    public int updateGuestProxyId(int deletedProxyId, int defaultProxyId) {
    	return updateGuestProxyId(TenantContext.getTenantId(), deletedProxyId, defaultProxyId);
    }

	public boolean isSuperPasswordRecoveryDisabled() {
		try {
			Configuration config = this.getConfiguration("DISABLE_PASSWORD_RECOVERY_SUPER");
			return "1".equals(config.getConfigurationValue());
		} catch (Exception ignored) {
			logger.info("Exception looking up config value for: DISABLE_PASSWORD_RECOVERY_SUPER");
		}
		return false;
	}

	public int enableSuperPasswordRecovery() {
		return dao.updateConfiguration(0, "DISABLE_PASSWORD_RECOVERY_SUPER", "0");
	}

	public int disableSuperPasswordRecovery() {
		return dao.updateConfiguration(0, "DISABLE_PASSWORD_RECOVERY_SUPER", "1");
	}

	public int setMinimumPINLengthSuper(int min) {
		if (min < PIN_MIN) {
			min = PIN_MIN;
		}
		if (min > PIN_MAX) {
			min = PIN_MAX;
		}
		return dao.updateConfiguration(0, "MINIMUM_PIN_LENGTH_SUPER", ""+min);

	}

	public int getMinimumPINLengthSuper() {
		return Integer.parseInt(getConfiguration("MINIMUM_PIN_LENGTH_SUPER").getConfigurationValue());
	}

	public int setMinimumPINLengthForTenant(int tenantId, int min) {
		int superMin = getMinimumPINLengthSuper();
		if (min < superMin) {
			min = superMin;
		}
		if (min > PIN_MAX) {
			min = PIN_MAX;
		}

		TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
		tenantConfig.setMinPINLength(min);
		return tenantService.updateTenantConfiguration(tenantConfig);
	}

	public int getMinPINLengthForTenant(int tenantId) {
		TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
		return Math.max(tenantConfig.getMinPINLength(), getMinimumPINLengthSuper()); // super's value overrides admin's
	}

	public boolean isPINLengthAcceptable(int tenantId, String pin) {
		return pin.matches("[0-9]{" + getMinPINLengthForTenant(tenantId) + "," + PIN_MAX + "}");
	}


	/**
	 * Used by Super to set the session expiration period globally
	 */
	@Override
	public int updateSessionExpirationPeriodConfig(int configValue) {
		// Update the Tenants which have higher value
		tenantService.updateSessionExpirationConfig(0, configValue);
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("UPDATE CLIENT SESSION EXPIRY PERIOD");
		transactionHistory.setTransactionResult("SUCCESS");
		transactionHistory.setTransactionParams(configValue + " Hours");
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
		return dao.updateConfiguration(0, "SESSION_EXP_PERIOD", String.valueOf(configValue));
	}


	public int saveOrUpdateConfiguration(int tenant, String configName, String configValue) {
		/*if (superManager != null){
			boolean ifExists = superManager.cacheExists("configurationCache");
			Cache configurationCache = superManager.getCache("configurationCache");
			long size = configurationCache.getMemoryStoreSize();
			List<Long> keys = configurationCache.getKeys();
			for (Long key:keys){
				if (configurationCache.get(key).getObjectValue() != null)
				System.out.println("Name = "+ configurationCache.get(key).getObjectKey().toString() + "key = "+ key + " value = "+ ((Configuration)configurationCache.get(key).getObjectValue()).getConfigurationValue());
			}
		}*/
		return dao.updateConfiguration(tenant, configName, configValue);
	}

	@Override
	public String getConfigValue(int tenantID, String configurationName){
		Configuration config = dao.getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();

	}

	@Override
	public boolean isTiles16Available() {
		boolean isTiles16Available = false;

		String flag = getConfigValue(0, "tiles16AvailableFlag");
		if (flag == null || "".equals(flag.trim())) {
			isTiles16Available = false; // not available by default, if not set
		} else {
			isTiles16Available = Boolean.parseBoolean(flag);
		}

		return isTiles16Available;
	}


	@Override
	public boolean isDefaultPrivateChatEnabled() {
		boolean isDefaultPrivateChatEnabled = false;

		String flag = getConfigValue(0, "privateChatEnabledDefaultFlag");
		if (flag == null || "".equals(flag.trim())) {
			isDefaultPrivateChatEnabled = true; // enabled by default, if not set
		} else {
			isDefaultPrivateChatEnabled = Boolean.parseBoolean(flag);
		}

		return isDefaultPrivateChatEnabled;
	}

	@Override
	public boolean isDefaultPublicChatEnabled() {
		boolean isDefaultPublicChatEnabled = false;

		String flag = getConfigValue(0, "publicChatEnabledDefaultFlag");
		if (flag == null || "".equals(flag.trim())) {
			isDefaultPublicChatEnabled = true; // enabled by default, if not set
		} else {
			isDefaultPublicChatEnabled = Boolean.parseBoolean(flag);
		}

		return isDefaultPublicChatEnabled;
	}

	/**
	 * Is chat available at super level.
	 * @return
	 */
	@Override
	public boolean isChatAvailable() {
		String flag = getConfigValue(0, "chatAvailableFlag");
		if (flag == null || "".equals(flag.trim())) {
			return true; // enabled by default, if not set
		} else {
			return Boolean.parseBoolean(flag);
		}
	}

	@Override
	public String getUserDefaultPassword() {
		return getConfigValue(1, "USER_DEFAULT_PASSWORD");
	}

	 public String getCACLDAPAuthenticationURL(int tenant) {
	        logger.debug("Get LDAPAuthenticationURL from Configuration");
	        return getConfigValue(tenant, "CACLDAPAuthenticationURL");
	    }


	public boolean getLDAPAuthenticationAttributeMappingFlag(int tenant) {
		logger.debug("Get AuthenticationFlag from Configuration, tenant="+tenant);
		String configValue = getConfigValue(tenant, "LDAPAuthenticationAttributeMappingFlag");
		return configValue != null && configValue.equalsIgnoreCase("1");
	}

	//CAC

	 public boolean getCACAuthenticationFlag(int tenant) {
		 logger.debug("Get getCACAuthenticationFlag from Configuration, tenant="+tenant);
	        String configValue = getConfigValue(tenant, CACAuthenticationFlag);
	        return configValue != null && configValue.equalsIgnoreCase("1");
    }
	  public String getCACOCSPRevocationCheck(int tenant) {
	        logger.debug("Get getCACOCSPRevocationCheck from Configuration, tenant="+tenant);
	        String val= getConfigValue(tenant, CACOCSPRevocationCheck);
	        if(val!=null && "on".equalsIgnoreCase(val)){
	        	return "1";
	        }
	        return val==null?"0":val;
	    }
	  public String getCACOSCPNonce(int tenant) {
	        logger.debug("Get getCACOSCPNonce from Configuration, tenant="+tenant);
	        String val= getConfigValue(tenant, CACOCSPNonceCheck);
	        return val==null?"0":val;
	    }
	  public String getCACUserExtractFrom(int tenant) {
	        logger.debug("Get getCACUserExtractFrom from Configuration, tenant="+tenant);
	        String val= getConfigValue(tenant, CACAuthenticationType);
	        return val==null?"0":val;
	    }
	  public String getCACOCSPResponderText(int tenant) {
	        logger.debug("Get getCACOSCPNonce from Configuration, tenant="+tenant);
	        String val= getConfigValue(tenant, CACOCSPResponderText);
	        return val==null?"0":val;
	    }
	  public String getCACOCSPResponderCheck(int tenant) {
	        logger.debug("Get CACOCSPResponderCheck from Configuration, tenant="+tenant);
	        String val= getConfigValue(tenant, CACOCSPResponderCheck);
	        return val==null?"0":val;
	    }
	 public String getCACLDAPAuthenticationPassword(int tenant) {
	        logger.debug("Get AuthenticationPassword from Configuration");
	        String password = getConfigValue(tenant, CACLDAPAuthenticationPassword);
	        return password != null && password.length() > 0 ? VidyoUtil.decrypt(password):"";
	    }

	    public String getCACLDAPAuthenticationFlag(int tenant) {
	        logger.debug("Get CACLDAPAuthenticationFlag from Configuration, tenant="+tenant);
	        String configValue = getConfigValue(tenant, CACLDAPAuthenticationFlag);

	        if(configValue==null){
	        	return "0";
	        	//adding double safty for the upgrade
	        }else if(configValue.isEmpty()||configValue.equalsIgnoreCase("0") ||configValue.equalsIgnoreCase("false") || configValue.equalsIgnoreCase("0")  ){
	        	return "0";
	        }else{
	        	return "1";
	        }

	    }

	    public String getCACLDAPAuthenticationFilter(int tenant) {
	        logger.debug("Get CACLDAPAuthenticationFilter from Configuration, tenant="+tenant);
	        return getConfigValue(tenant, CACLDAPAuthenticationFilter);
	    }

	    public String getCACLDAPAuthenticationBase(int tenant) {
	        logger.debug("Get CACLDAPAuthenticationBase from Configuration, tenant="+tenant);
	        return getConfigValue(tenant, CACLDAPAuthenticationBase);
	    }

	    public String getCACLDAPAuthenticationScope(int tenant) {
	        logger.debug("Get LDAPAuthenticationScope from Configuration, tenant="+tenant);
	        return getConfigValue(tenant, CACLDAPAuthenticationScope);
	    }
	    public String getCACLDAPAuthenticationUsername(int tenant) {
	        logger.debug("Get LDAPAuthenticationUser from Configuration");
	        return getConfigValue(tenant, CACLDAPAuthenticationUsername);
	    }

	    @Override
	    public boolean getPKICertReviewStatus(int tenantId) {
			try{

				File unsaved=new File(cacCertificateLocationStg+tenantId+SCRIPT_OCSP_UNSAVED);
				if(unsaved!=null && unsaved.exists()){
						return  true;

				}
			}catch(Exception e){
				logger.error(e.getMessage());
			}

			return false;
		}
	    @Override
	    public boolean getApacheRestartStatus(int tenantId) {
			try{
				File  apacheRestartFile=new File(cacCertificateLocation+tenantId+SCRIPT_OCSP_PENDINGRESTART);
					if(apacheRestartFile!=null && apacheRestartFile.exists()){
							return  true;

				}else{

					String dirtyFlag=getConfigValue(tenantId,CACOCSPDIRTYFLAG );
					if("1".equalsIgnoreCase(dirtyFlag)){
						return true;
					}else{
						return false;
					}
					}


			}catch(Exception e){
				logger.error(e.getMessage());
			}

			return false;
		}


    public String getLDAPAuthenticationPassword(int tenant) {
        logger.debug("Get AuthenticationPassword from Configuration");
        String password = getConfigValue(tenant, "LDAPAuthenticationPassword");
        return password != null && password.length() > 0 ? VidyoUtil.decrypt(password):"";
    }

    public boolean getLDAPAuthenticationFlag(int tenant) {
        logger.debug("Get AuthenticationFlag from Configuration, tenant="+tenant);
        String configValue = getConfigValue(tenant, "LDAPAuthenticationFlag");
        return configValue != null && configValue.equalsIgnoreCase("1");
    }

    public String getLDAPAuthenticationFilter(int tenant) {
        logger.debug("Get LDAPAuthenticationFilter from Configuration, tenant="+tenant);
        return getConfigValue(tenant, "LDAPAuthenticationFilter");
    }

    public String getLDAPAuthenticationBase(int tenant) {
        logger.debug("Get LDAPAuthenticationBase from Configuration, tenant="+tenant);
        return getConfigValue(tenant, "LDAPAuthenticationBase");
    }

    public String getLDAPAuthenticationScope(int tenant) {
        logger.debug("Get LDAPAuthenticationScope from Configuration, tenant="+tenant);
        return getConfigValue(tenant, "LDAPAuthenticationScope");
    }

	public String getCustomizedImageLogoName(int tenant){
		logger.debug("Get Customized Logo from Configuration, tenant=" + tenant);
		return getConfigValue(tenant, "logoImage");
	}


    public String getAuthenticationURL(int tenant) {
        logger.debug("Get AuthenticationURL from Configuration");
        return getConfigValue(tenant, "AuthenticationURL");
    }

    public String getAuthenticationUsername(int tenant) {
        logger.debug("Get AuthenticationUsername from Configuration");
        return getConfigValue(tenant, "AuthenticationUsername");
    }

    public String getAuthenticationPassword(int tenant) {
        logger.debug("Get AuthenticationPassword from Configuration");
        String password = getConfigValue(tenant, "AuthenticationPassword");
        return password != null && password.length() > 0 ? VidyoUtil.decrypt(password):"";
    }

    public boolean getAuthenticationFlag(int tenant) {
        logger.debug("Get AuthenticationFlag from Configuration, tenant="+tenant);
        String configValue = getConfigValue(tenant, "AuthenticationFlag");
        return configValue != null && configValue.equalsIgnoreCase("1");
    }
    public boolean getRestAuthenticationFlag(int tenant) {
        logger.debug("Get RestAuthenticationFlag from Configuration, tenant="+tenant);
        String configValue = getConfigValue(tenant, "RestAuthenticationFlag");
        return configValue != null && configValue.equalsIgnoreCase("1");
    }
    public String getRestAuthenticationURL(int tenant) {
        logger.debug("Get RestAuthenticationURL from Configuration");
        return getConfigValue(tenant, "RestAuthenticationURL");
    }


    public String getLDAPAuthenticationURL(int tenant) {
        logger.debug("Get LDAPAuthenticationURL from Configuration");
        return getConfigValue(tenant, "LDAPAuthenticationURL");
    }

    public String getLDAPAuthenticationUsername(int tenant) {
        logger.debug("Get LDAPAuthenticationUser from Configuration");
        return getConfigValue(tenant, "LDAPAuthenticationUsername");
    }

    public String getPortalVersion(int tenant) {
        logger.debug("Get PortalVersion from Configuration");
		return getConfigValue(tenant, "PortalVersion");
    }

    private String getCDRAccessFromHost() {
        logger.debug("Get CdrAccessFromHost from Configuration");
        return getConfigValue(1, "CdrAccessFromHost");
    }

	private String getCDRAccessPassword() {
		logger.debug("Get CdrAccessPassword from Configuration");
		String password = getConfigValue(1, "CdrAccessPassword");
        return password != null && password.length() > 0 ? VidyoUtil.decrypt(password):"";
	}

	private String getCDRAllowDeny() {
		logger.debug("Get CdrEnabled from Configuration");
		String configValue = getConfigValue(1, "CdrAllowDeny");

		return configValue != null && configValue.trim().length() > 0 ? configValue.trim():"0";
	}

	private String getCDRAllowDelete() {
		logger.debug("Get CdrAllowDelete from Configuration");
		String configValue = getConfigValue(1, "CdrAllowDelete");

		return configValue != null && configValue.trim().length() > 0 ? configValue.trim():"1";
	}


    public int getGuestConfGroupID(int tenant) {
        logger.debug("Get GuestGroupID from Configuration");
        String configurationValue = getConfigValue(tenant, "GuestGroupID");

        return configurationValue != null && configurationValue.trim().length() > 0 ? Integer.parseInt(configurationValue) : 0;
    }

    public int getGuestConfProxyID(int tenant) {
        logger.debug("Get GuestProxyID from Configuration");
        String configValue = getConfigValue(tenant, "GuestProxyID");

		return configValue != null && configValue.trim().length() > 0 ? Integer.parseInt(configValue.trim()):0;
    }

    public int getGuestConfLocationID(int tenant) {
        logger.debug("Get GuestLocationID from Configuration");
        String configValue = getConfigValue(tenant, "GuestLocationID");

		return configValue != null && configValue.trim().length() > 0 ? Integer.parseInt(configValue.trim()):1;
    }


     public String getSuperToEmailAddress() {
        logger.debug("Get SuperEmailTo from Configuration, tenant=0");
        return getConfigValue(0, "SuperEmailTo");
    }

     public String getSuperFromEmailAddress(){
         logger.debug("Get SuperEmailFrom from Configuration, tenant=0");
         return getConfigValue(0, "SuperEmailFrom");
     }


 	/****
      * Start BANNERS section
      * @return
      */


     private String getShowLoginBannerInfo() {
 		logger.debug("Get LoginBannerInfo from Configuration");
 		return getConfigValue(1, "ShowLoginBanner");
 	}

     private String getLoginBannerInfo() {
 		logger.debug("Get LoginBannerInfo from Configuration");
 		return getConfigValue(1, "LoginBannerInfo");
 	}

     private String getShowWelcomeBannerInfo() {
 		logger.debug("Get WelcomeBannerInfo from Configuration");
 		return getConfigValue(1, "ShowWelcomeBanner");
 	}

     private String getWelcomeBannerInfo() {
 		logger.debug("Get WelcomeBannerInfo from Configuration");
 		return getConfigValue(1, "WelcomeBannerInfo");
 	}


 	@Override
 	public void setUserDefaultPassword(String userDefaultPassword) {
 		dao.updateConfiguration(1, "USER_DEFAULT_PASSWORD", userDefaultPassword);
 	}

 	@Override
 	public int saveDefaultPublicChatEnabled(boolean isDefaultPublicChatEnabled) {
 		return dao.updateConfiguration(0, "publicChatEnabledDefaultFlag", Boolean.toString(isDefaultPublicChatEnabled));
 	}

 	@Override
 	public int saveDefaultPrivateChatEnabled(boolean isDefaultPrivateChatEnabled) {
 		return dao.updateConfiguration(0, "privateChatEnabledDefaultFlag", Boolean.toString(isDefaultPrivateChatEnabled));
 	}

 	/**/

 	@Override
     public int updateTenantGuestSettingsLocationTag(int tenant, int deletedLocationID, int defaultLocationTagID) {
         return dao.replaceConfigurationValue(tenant, "GuestLocationID", ""+deletedLocationID, ""+defaultLocationTagID);
     }

 	@Override
     public int updateGuestProxyId(int tenant, int deletedProxyId, int defaultProxyId) {
         return dao.replaceConfigurationValue(tenant, "GuestLocationID", ""+deletedProxyId, ""+defaultProxyId);
     }

 	@Override
	public int saveVidyoWebAvailable( int tenant, boolean en) {
		return dao.updateConfiguration(tenant, "VidyoWebAvailable", Boolean.toString(en));
	}

 	@Override
    public int saveVidyoWebEnabled( int tenant, boolean en) {
        return dao.updateConfiguration(tenant, "VidyoWebEnabled", Boolean.toString(en));
    }

 	@Override
	public int saveShowDisabledRooms( int tenant, boolean en) {
		return dao.updateConfiguration(tenant, "ShowDisabledRooms", Boolean.toString(en));
	}

 	@Override
 	public int saveVidyoProxyIpAndPort(String ip, int port) {
        if(ip==null || ip.trim().length()==0) {
            return dao.updateConfiguration(1, "VidyoProxyIpAndPort", "");
        }
        else {
            return dao.updateConfiguration(1, "VidyoProxyIpAndPort", ip+":"+port);
        }
    }

 	@Override
    public int saveVidyoProxyDnsHostNameAndPort(String dnsname, int port) {
        if(dnsname==null || dnsname.trim().length()==0) {
            return dao.updateConfiguration(1, "VidyoProxyDNS", "");
        }
        else {
            return dao.updateConfiguration(1, "VidyoProxyDNS", dnsname+":"+port);
        }
    }


	/**
	 * Returns the Status notification server details.
	 *
	 * @param tenantId
	 * @return
	 */
	@Override
	public StatusNotification getStatusNotificationDetails(int tenantId) {
		StatusNotification statusNotification = new StatusNotification();
		statusNotification.setFlag(getStatusNotificationFlag(tenantId));
		statusNotification.setUsername(getStatusNotificationUsername(tenantId));
		statusNotification.setUrl(getStatusNotificationURL(tenantId));
		statusNotification.setPassword(getStatusNotificationPassword(tenantId));
		return statusNotification;
	}

	private String getStatusNotificationURL(int tenant) {
        logger.debug("Get StatusNotificationURL from Configuration");
        Configuration config = dao.getConfiguration(tenant, "StatusNotificationURL");
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();
    }

    private String getStatusNotificationUsername(int tenant) {
        logger.debug("Get StatusNotificationUser from Configuration");
        Configuration config = dao.getConfiguration(tenant, "StatusNotificationUsername");
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();
    }

    private String getStatusNotificationPassword(int tenant) {
        logger.debug("Get StatusNotificationPassword from Configuration");
        Configuration config = dao.getConfiguration(tenant, "StatusNotificationPassword");
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return VidyoUtil.decrypt(config.getConfigurationValue());
    }

    private boolean getStatusNotificationFlag(int tenant) {
        logger.debug("Get StatusNotificationFlag from Configuration, tenant="+tenant);
        Configuration config = dao.getConfiguration(tenant, "StatusNotificationFlag");
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return false;
		}
		return config.getConfigurationValue().equalsIgnoreCase("1");
    }


    public File getCACCertificate(boolean stageFlag) {
    	File downloadFile =null;
    	if(stageFlag){
    		downloadFile = new File(cacCertificateLocationStg
					+ TenantContext.getTenantId() + CERTIFICATE_EXT);
    		if (downloadFile ==null || !downloadFile.exists()) {
    			logger.error("file not exist " + cacCertificateLocationStg
    					+ TenantContext.getTenantId() + CERTIFICATE_EXT);


    			throw new RuntimeException("File doesn't exist");
    		}

		}else{
			downloadFile = new File(cacCertificateLocation
					+ TenantContext.getTenantId() + CERTIFICATE_EXT);
			if (downloadFile ==null || !downloadFile.exists()) {
				logger.error("file not exist " + cacCertificateLocation
						+ TenantContext.getTenantId() + CERTIFICATE_EXT);


				throw new RuntimeException("File doesn't exist");
			}
		}
    	return downloadFile;

    }

	@Override
	public Map<String, Object> getAuthenticationParam() {

		Map<String, Object> model = new HashMap<String, Object>();
		if(VendorUtils.isDISA()){
			model.put("CAC", "PKI Authentication");
		}
		model.put("NORMAL", "Local");
		model.put("LDAP", "LDAP");
		model.put("REST", "REST WS");
		SystemLicenseFeature feature = license.getSystemLicenseFeature("AllowPortalAPIs");
	    if(feature != null && feature.getLicensedValue().equalsIgnoreCase("true")) {
	    		model.put("WS", "Web Service");
        }
		model.put("SAML", "SAML");



		return model;
	}

	@Override
	public String getCACUserNameExtractType(int tenantId) {
		return this.getCACUserExtractFrom(tenantId);
	}
	public static String getDnForUserForCAC(String name, CACAuthentication ldapAuth, DirContext ctx) throws Exception {

		DistinguishedName base = DistinguishedName.EMPTY_PATH;
		if (!ldapAuth.getLdapbase().equalsIgnoreCase("")) {
			base = new DistinguishedName(ldapAuth.getLdapbase());
		}

		// from Request
		String filter = ldapAuth.getLdapfilter();
		/*if (filter.contains("& lt;& gt;")) {
			filter = filter.replace("& lt;& gt;".subSequence(0, "& lt;& gt;".length()), name);
			filter = XssUtils.unescape(filter);
		}*/

		// from DB
		if (filter.contains("<>")) {
			filter = filter.replace("<>".subSequence(0, "<>".length()), name);
		}

		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(Integer.valueOf(ldapAuth.getLdapscope()));

		String dn = "";
		NamingEnumeration<SearchResult> result = ctx.search(base, filter, ctls);
		while (result.hasMoreElements()) {
			SearchResult searchResult = result.nextElement();
			dn = searchResult.getNameInNamespace();
		}

		return dn;
	}

	@Override
	public List<Tenant> getWebServerRestartRequest() throws IOException {
		List<Tenant>tenantList=new ArrayList<Tenant>();

		Object[] paths=Files.list(new File(cacCertificateLocation).toPath())
	     .filter(p -> p.getFileName().toString().contains(".pendingrestart")).toArray();

	    for(Object ob:paths){
	    	Path filePath=(Path)ob;
	    	String fileName=filePath.getName(filePath.getNameCount()-1).toString();
	    	String tenantId=fileName.substring(0, fileName.indexOf(".pendingrestart"));

	    	try{
	    		Tenant tenant=tenantService.getTenant(Integer.parseInt(tenantId));
	    		if(tenant!=null){
	    			tenantList.add(tenant);
	    		}
	    	}catch(NumberFormatException e){
	    		logger.error("Not a tenant id "+tenantId +" -Skipping due to  "+e.getMessage());
	    	}


	    }
		return tenantList;
	}


	@Override
	public List<Tenant> getPKICertificateForReviewList() throws IOException {
		List<Tenant>tenantList=new ArrayList<Tenant>();

		Object[] paths=Files.list(new File(cacCertificateLocationStg).toPath())
	     .filter(p -> p.getFileName().toString().contains(".unsaved")).toArray();

	    for(Object ob:paths){
	    	Path filePath=(Path)ob;
	    	String fileName=filePath.getName(filePath.getNameCount()-1).toString();
	    	String tenantId=fileName.substring(0, fileName.indexOf(".unsaved"));

	    	try{
	    		Tenant tenant=tenantService.getTenant(Integer.parseInt(tenantId));
	    		if(tenant!=null){
	    			tenantList.add(tenant);
	    		}
	    	}catch(NumberFormatException e){
	    		logger.error("Not a tenant id "+tenantId +" -Skipping due to  "+e.getMessage());
	    	}


	    }
		return tenantList;
	}

	@Override
	public boolean approveTenantCert(String tenantId) throws IOException {try {
		return ShellExecutor.execute("sudo -n /opt/vidyo/bin/approve_pki.sh " + tenantId).getExitCode()==0;

	} catch (ShellExecutorException e) {
		logger.error("Error while moving TenantCert", e);
		return false;

	}}

	@Override
	public int saveDefaultGuestLocationTag(int tenantId,String locationId) {

		return this.dao.updateConfiguration(tenantId, "GuestLocationID", locationId);

	}

	@Override
	public int saveDefaultGuestProxy(int tenantId,String proxyId) {

		return this.dao.updateConfiguration(tenantId, "GuestProxyID", proxyId);
	}

	@Override
	public void addTransactionHistoryWithUserLookup(String tnName, String tnParam, String tnResult) {

		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(tnName);
		transactionHistory.setTransactionParams(tnParam);
		transactionHistory.setTransactionResult(tnResult);
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
        }

	@Override
	public EventsNotificationServers getEventNotificationServers() {
		EventsNotificationServers eventsNotificationServers = new EventsNotificationServers();
		Configuration eventsConfiguration = dao.getConfiguration(0, "EVENTS_NOTIFICATION");
		if (eventsConfiguration != null && eventsConfiguration.getConfigurationValue() != null) {
			eventsNotificationServers.setEventsNotificationEnabled(
					eventsConfiguration.getConfigurationValue().equalsIgnoreCase("1") ? "on" : "off");
		}
		Configuration primaryServerConfig = dao.getConfiguration(0, "PRIMARY_EVENTS_SERVER");
		if (primaryServerConfig != null && primaryServerConfig.getConfigurationValue() != null) {
			eventsNotificationServers.setPrimaryServer(primaryServerConfig.getConfigurationValue());
			Configuration primaryServerPortConfig = dao.getConfiguration(0, "PRIMARY_EVENTS_SERVER_PORT");
			if (primaryServerPortConfig != null && primaryServerPortConfig.getConfigurationValue() != null) {
				eventsNotificationServers.setPrimaryServerPort(primaryServerPortConfig.getConfigurationValue());
			}
			Configuration secondaryServerConfig = dao.getConfiguration(0, "SECONDARY_EVENTS_SERVER");
			if (secondaryServerConfig != null && secondaryServerConfig.getConfigurationValue() != null) {
				eventsNotificationServers.setSecondaryServer(secondaryServerConfig.getConfigurationValue());
				Configuration secondaryServerPortConfig = dao.getConfiguration(0, "SECONDARY_EVENTS_SERVER_PORT");
				if (secondaryServerPortConfig != null && secondaryServerPortConfig.getConfigurationValue() != null) {
					eventsNotificationServers.setSecondaryServerPort(secondaryServerPortConfig.getConfigurationValue());
				}
			}
		}
		return eventsNotificationServers;
	}

	@Override
	public void saveEventNotificationServers(EventsNotificationServers eventsNotificationServers) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionResult("SUCCESS");
		try {
			if(eventsNotificationServers.getEventsNotificationEnabled() == null || eventsNotificationServers.getEventsNotificationEnabled().equalsIgnoreCase("on")) {
				transactionHistory.setTransactionName("EVENTS NOTIFICATION ENABLED");
			} else {
				transactionHistory.setTransactionName("EVENTS NOTIFICATION DISABLED");
			}
			dao.updateConfiguration(0, "EVENTS_NOTIFICATION", eventsNotificationServers.getEventsNotificationEnabled().equalsIgnoreCase("on") ? "1" : "0");
			dao.updateConfiguration(0, "PRIMARY_EVENTS_SERVER", eventsNotificationServers.getPrimaryServer());
			dao.updateConfiguration(0, "PRIMARY_EVENTS_SERVER_PORT", eventsNotificationServers.getPrimaryServerPort());
			dao.updateConfiguration(0, "SECONDARY_EVENTS_SERVER", eventsNotificationServers.getSecondaryServer());
			dao.updateConfiguration(0, "SECONDARY_EVENTS_SERVER_PORT", eventsNotificationServers.getSecondaryServerPort());
		} catch(DataAccessException dae) {
			transactionHistory.setTransactionResult("FAILURE");
			logger.error("Data Access Exception while saving events notification configuration");
		}
		// Update the properties file in directory "/opt/vidyo/portal2/batch-config/events-notify-servers.properties"
		Path path = Paths.get("/opt/vidyo/portal2/batch-config/events-notify-servers.properties");
		boolean exists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
		if(!exists) {
			File file = new File("/opt/vidyo/portal2/batch-config/events-notify-servers.properties");
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error("Cannot create new file events-notify-servers.properties", e);
			}
		}

		PropertiesConfiguration config = null;
		try {
			config = new PropertiesConfiguration("/opt/vidyo/portal2/batch-config/events-notify-servers.properties");
		} catch (ConfigurationException e) {
			logger.error("Error while loading properties file");
		}
		if (config != null) {
			config.setProperty("primary.server", eventsNotificationServers.getPrimaryServer().trim());
			config.setProperty("primary.server.port", eventsNotificationServers.getPrimaryServerPort());
			// Set the primary server as secondary if secondary is empty - this prevents batch from having startup errors.
			config.setProperty("secondary.server",
					eventsNotificationServers.getSecondaryServer().trim().isEmpty()
							? eventsNotificationServers.getPrimaryServer().trim()
							: eventsNotificationServers.getSecondaryServer().trim());
			config.setProperty("secondary.server.port",
					eventsNotificationServers.getSecondaryServerPort().trim().isEmpty()
							? eventsNotificationServers.getPrimaryServerPort()
							: eventsNotificationServers.getSecondaryServerPort().trim());
			try {
				config.save();
			} catch (ConfigurationException e) {
				logger.error("Error while saving properties file");
			}
		}
		Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
		filePerms.add(PosixFilePermission.OWNER_READ);
		filePerms.add(PosixFilePermission.OWNER_WRITE);
        filePerms.add(PosixFilePermission.OWNER_EXECUTE);
		filePerms.add(PosixFilePermission.GROUP_READ);
		filePerms.add(PosixFilePermission.OTHERS_READ);
		try {
			Files.setPosixFilePermissions(Paths.get("/opt/vidyo/portal2/batch-config/events-notify-servers.properties"), filePerms);
		} catch (IOException e) {
			logger.error("Cannot change permissions of file events-notify-servers.properties", e);
		}
		// Restart the batch process
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n /opt/vidyo/bin/vidyo_server.sh STOP_PORTAL_BATCH");
		} catch (ShellExecutorException e) {
			logger.error("Restart of batch process failed - Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("Restart of batch process failed ", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
		}
		transactionHistory.setTransactionParams(eventsNotificationServers.toString());
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
	}

	@Override
	public void auditLogTransaction(String tnName, String tnParam, String tnResult) {

		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(tnName);
		transactionHistory.setTransactionParams(tnParam);
		transactionHistory.setTransactionResult(tnResult);
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
	}

	@Override
	public void auditLogTransaction(String tnName, String tnParam, String username, String tnResult) {

		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(tnName);
		transactionHistory.setTransactionParams(tnParam);
		transactionHistory.setTransactionResult(tnResult);
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory, username);
	}
	
	/**
	 * Returns status code to let UI layer know if it has to display HS menu or not.
	 * 0 - HS enabled
	 * 1 - HS not enabled/licensed
	 * 2 - HS enabled, but in maintenance mode
	 */
	@Override
	public int isHotStandbyEnabled() {
		String script = "sudo -n /opt/vidyo/ha/bin/is_hs_enabled.sh";
		try {
			return ShellExecutor.execute(script).getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<Configuration> getConfigurationsByTenantAndConfigNames(int tenantId, List<String> configNames) {
		return dao.getConfigurationsByTenantAndConfigNames(tenantId, configNames);
	}

	@Override
	public int updateConfigurations(int tenantId, List<Configuration> configs) {
		// Clear the config and tenant caches
		if (CollectionUtils.isNotEmpty(configs)) {
			for (Configuration config : configs) {
				dao.clearConfigurationCache(tenantId, config.getConfigurationName());
			}
		}
		tenantService.clearCache(tenantId);

		return dao.updateConfigurations(tenantId, configs);
	}

}
