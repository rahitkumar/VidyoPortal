package com.vidyo.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.owasp.validator.html.CleanResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vidyo.bo.Banners;
import com.vidyo.bo.CdrAccess;
import com.vidyo.bo.CdrFilter;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Country;
import com.vidyo.bo.DBProperties;
import com.vidyo.bo.DbBackupFileInfo;
import com.vidyo.bo.DiagnosticReport;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.InstallationReport;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.NEConfiguration;
import com.vidyo.bo.Network;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.StatusNotification;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.TenantFilter;
import com.vidyo.bo.User;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.bo.authentication.WsAuthentication;
import com.vidyo.bo.authentication.WsRestAuthentication;
import com.vidyo.bo.clusterinfo.ClusterInfo;
import com.vidyo.bo.email.SmtpConfig;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.bo.ipcdomain.IpcDomain;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.security.CRTObject;
import com.vidyo.bo.transaction.TransactionFilter;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.INetworkService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISecurityService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.IpcDomainService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.SecurityServiceImpl;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;
import com.vidyo.service.configuration.enums.ExternalDataTypeEnum;
import com.vidyo.service.configuration.enums.SystemConfigurationEnum;
import com.vidyo.service.diagnostics.IDiagnosticsService;
import com.vidyo.service.dialin.CountryService;
import com.vidyo.service.dialin.TenantDialInService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.idp.TenantIdpAttributesMapping;
import com.vidyo.service.installation.InstallationService;
import com.vidyo.service.interportal.IpcConfigurationService;
import com.vidyo.service.ldap.ITenantLdapAttributesMapping;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.passwdhistory.MemberPasswordHistoryService;
import com.vidyo.service.system.SystemService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.service.ComponentsService;
import com.vidyo.utils.CompressionUtils;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecurityUtils;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;

import bsh.This;

@Controller
public class SettingsController {

	protected static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	@Autowired
    private INetworkService network;

    @Autowired
	private LicensingService licensing;

    @Autowired
    private EndpointUploadService endpointupload;
    
    @Autowired
	private ExtIntegrationService extIntegrationService;

	@Value( "${upload.dir.super}" )
    private String upload_dir;

	@Value( "${upload.path}" )
    private String upload_path;

	@Value( "${upload.temp.dir}" )
    private String uploadTempDir;

	@Value( "${upload.temp.dir.super}" )
    private String uploadTempDirSuper;

    @Autowired
    private IUserService user;

    @Autowired
    private ISystemService system;

    @Autowired
    private ITenantService tenant;

    @Autowired
    private IMemberService member;

    @Autowired
    private CookieLocaleResolver lr;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;
    private boolean showVidyoReplay = true;
    private boolean showVidyoVoice = true;

    @Autowired
    private TransactionService transaction;

    @Autowired
    private IpcConfigurationService ipcConfigurationService;

    @Autowired
    private IpcDomainService ipcDomainService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ITenantLdapAttributesMapping ldapAttributesMapping;

    @Autowired
    private TenantIdpAttributesMapping idpAttributesMapping;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IDiagnosticsService diagnosticsService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private SamlAuthenticationService samlAuthenticationService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private InstallationService installationService;
	private static final String SCHEDULED_ROOM_PREFIX = "SCHEDULED_ROOM_PREFIX";
	private static final String USER_IMAGE ="USER_IMAGE";
	private static final String ENDPOINTBEHAVIOR ="ENDPOINTBEHAVIOR";
	private static final String UPLOAD_USER_IMAGE ="UPLOAD_USER_IMAGE";
	private static final String MAX_USER_IMAGE_SIZE_KB ="MAX_USER_IMAGE_SIZE_KB";
	private static final String CERTIFICATE_EXT = ".crt";
	private static final String Logs = "VidyoPortalLogs.zip";
	private static final String CREATE_PUBLIC_ROOM_FLAG = "CREATE_PUBLIC_ROOM_FLAG";
	private static final String MAX_CREATE_PUBLIC_ROOM_USER = "MAX_CREATE_PUBLIC_ROOM_USER";

	private static final String SELECTED_MAX_ROOM_EXT_LENGTH = "SELECTED_MAX_ROOM_EXT_LENGTH";

	static final String FAILURE = "FAILURE";
	static final String SUCCESS = "SUCCESS";

	private static final String LogAggregationServer = "LogAggregationServer";

	@Autowired
	private LogoutHandler logoutHandler;

	@Autowired
    private SessionRegistry sessionRegistry;

	@Autowired
    private MemberPasswordHistoryService memberPasswordHistoryService;

	@Autowired
    private JmsTemplate jmsTemplate;

	@Autowired
    private Topic superDeleteMessageMQtopic;

    @Autowired
    private IServiceService serviceService;
    
    @Autowired
    private ComponentsService componentsService;

    @Autowired
    private TenantDialInService tenantDdialInService;

    @Autowired
    private CountryService countryService;

    @Value("${upload.dir.admin}")
    private String uploadDirAdmin;

    @Value("${upload.dir.super}")
    private String uploadDirSuper;

    /**
    	Due to the new ext js framework and the way ajax function is called, browser return 302 error if there is a redirect from
    	server. However ext js is unable to handle the 302 error especially the redirect and
    	it consider as success.this method  is a hack for the issue. we need to revisit this.
    	
    	
    */
    
    public ModelAndView getDummyValueHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("pass", true);
       
        return new ModelAndView("ajax/checkpin_ajax", "model", model);
    }

    @RequestMapping(value = "/guestconf.html", method = RequestMethod.GET)
    public ModelAndView getGuestConfHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("admin/guestconf_html", "model", model);
    }

    @RequestMapping(value = "/guestconf.ajax", method = RequestMethod.GET)
    public ModelAndView getGuestConfAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Guestconf guestconf = this.system.getGuestConf();
        model.put("guestconf", guestconf);

        return new ModelAndView("ajax/guestconf_ajax", "model", model);
    }

    @RequestMapping(value = "/saveguestconf.ajax", method = RequestMethod.POST)
    public ModelAndView saveGuestConf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Guestconf conf = new Guestconf();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(conf);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        int configID = 0;

        Map<String, Object> model = new HashMap<String, Object>();
        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            configID = this.system.saveGuestConf(conf);
        }
        if (configID > 0) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/savenotification.ajax", method = RequestMethod.POST)
    public ModelAndView saveAdminNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        String fromEmail = ServletRequestUtils.getStringParameter(request, "fromEmail");
        String toEmail = ServletRequestUtils.getStringParameter(request, "toEmail");
        boolean enable = ServletRequestUtils.getBooleanParameter(request, "enableNewAccountNotification", false);

        if ((this.system.saveNotificationFromEmailAddress(fromEmail)==-1)
                || (this.system.saveNotificationToEmailAddress(toEmail)==-1)
                || (this.system.saveEnableNewAccountNotification(enable)==-1) ){
            FieldError fe = new FieldError("Maintenance", ms.getMessage("notification", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/statusnotification.ajax", method = RequestMethod.GET)
    public ModelAndView getStatusNotificationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        StatusNotification sn = this.system.getStatusNotificationService();

        if (!StringUtils.isBlank(sn.getPassword())) {
            sn.setPassword(SystemServiceImpl.PASSWORD_UNCHANGED);
        }
        
        if(StringUtils.isEmpty(sn.getUsername()) && StringUtils.isEmpty(sn.getUrl())) {
        	sn.setPassword(null);
        }
        model.put("StatusNotification", sn);

        return new ModelAndView("ajax/status_notification_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/savestatusnotification.ajax", method = RequestMethod.POST)
    public ModelAndView saveStatusNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        StatusNotification sn = new StatusNotification();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(sn);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
        	if (sn.getUsername() != null && (sn.getUsername().isEmpty() || 
        			sn.getUsername().length() > 128 || 
        			!sn.getUsername().matches(ValidationUtils.USERNAME_REGEX))) {   //printabled unicoded username
        		model.put("success", Boolean.FALSE);
        	} else {
	            if (this.system.saveStatusNotificationService(sn) == -1) {
	                FieldError fe = new FieldError("StatusNotification", ms.getMessage("status.notification", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
	                errors.add(fe);
	            }
	
	            if(errors.size() != 0){
	                model.put("fields", errors);
	                model.put("success", Boolean.FALSE);
	            }
	            else {
	                model.put("success", Boolean.TRUE);
	            }
        	}
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/authentication.html", method = RequestMethod.GET)
    public ModelAndView getAuthenticationHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        SystemLicenseFeature feature = this.licensing.getSystemLicenseFeature("AllowPortalAPIs");
        if(feature != null && feature.getLicensedValue().equalsIgnoreCase("true")) {
            model.put("enableAdminAPI", Boolean.TRUE);
        } else {
            model.put("enableAdminAPI", Boolean.FALSE);
        }
        return new ModelAndView("admin/authentication_html", "model", model);
    }
    
    @RequestMapping(value = "/authenticationParam.ajax", method = RequestMethod.GET)
	public ModelAndView getAuthenticationParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();
		Map<String, Object> model = null;
		Map<String, Object> modelLocalised = new HashMap<String, Object>();
		 model = system.getAuthenticationParam();
		 if(model!=null ){
			 for (Map.Entry<String, Object> entry : model.entrySet()) {
				switch (entry.getKey()) {
					case "CAC":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.cacpki", null, "", loc));
						
						break;
					case "LDAP":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.ldap", null, "", loc));
						
						break;
					case "SAML":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.saml", null, "", loc));
						
						break;
					case "NORMAL":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.local", null, "", loc));
						
						break;
					case "WS":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.ws", null, "", loc));
						
						break;
					case "REST":
						modelLocalised.put(entry.getKey(),ms.getMessage("authtype.rest", null, "", loc));
						
						break;
					
					}
				
			 }
			}
		
		return new ModelAndView("ajax/authentication_html", "model", modelLocalised);
	}
	
    @RequestMapping(value = "/authentication.ajax", method = RequestMethod.GET)
    public ModelAndView getAuthenticationAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Integer tenantId = TenantContext.getTenantId();
        AuthenticationConfig auth = (AuthenticationConfig) BeanUtils.cloneBean(this.system.getAuthenticationConfig(tenantId)); // get a copy
        if (!StringUtils.isBlank(auth.getLdappassword())) {
            auth.setLdappassword(SystemServiceImpl.PASSWORD_UNCHANGED);
        }
        if (!StringUtils.isBlank(auth.getWspassword())) {
            auth.setWspassword(SystemServiceImpl.PASSWORD_UNCHANGED);
        }
        if (securityService.isSSLEnabled()) {
			model.put("isSSLEnabled", Boolean.TRUE);
		} else {
			model.put("isSSLEnabled", Boolean.FALSE);
		}

        
        //commenting this out since need to consider the scenario where you have tenants those were pki enabled.
       // if(auth.isCacflag())
      //  {
        	
        model.put("webServerRestartNeeded", this.system.getApacheRestartStatus(tenantId));        	
        model.put("pkiCertReviewPending",this.system.getPKICertReviewStatus(tenantId));
    
       
      //  }else{
        	
       
       // }
        
        model.put("Authentication", auth);

        SystemLicenseFeature feature = this.licensing.getSystemLicenseFeature("AllowPortalAPIs");
        if(feature != null && feature.getLicensedValue().equalsIgnoreCase("true")) {
            model.put("enableAdminAPI", Boolean.TRUE);
        }
        else {
            model.put("enableAdminAPI", Boolean.FALSE);
        }
        TenantConfiguration config = tenant.getTenantConfiguration(tenantId);
        model.put("sessionExpPeriod", config.getSessionExpirationPeriod());
        

        return new ModelAndView("ajax/authentication_ajax", "model", model);
    }
   
    @RequestMapping(value = "/exportcaccertificate.ajax", method = RequestMethod.GET)
    public ModelAndView exportCACCertificate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
    	
    	File downloadFile =null;
    	boolean stageFlag= ServletRequestUtils.getBooleanParameter(request, "stageFlag", false);
		try{
			downloadFile=this.system.getCACCertificate(stageFlag);
		}catch(Exception e){
			logger.error("Error",e);
			return null;
		}
		
	
		long fileSize = downloadFile.length();
		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ "TrustedCAExport" + CERTIFICATE_EXT + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		// response.setContentLength((int)fileSize);
		response.addHeader("Content-Length", Long.toString(fileSize));

		response.setContentType("application/pkix-cert");

		DataInputStream in = new DataInputStream(new FileInputStream(
				downloadFile));
		org.apache.commons.io.IOUtils.copy(in, response.getOutputStream());
		in.close();
		response.flushBuffer();

		return null;
	}

	private String getRequestParameter(String name, HttpServletRequest request) {
		String ret = request.getParameter(name);
		if (ret == null) {
			ret = "";
		}
		ret = ret.trim();

		return ret;
	}
	private boolean validateExtension(MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		logger.info("fileName " + fileName);
		if (fileName != null & fileName.contains(".")) {

			String ext = fileName.substring(fileName.indexOf(".") + 1);

			logger.info("ext " + ext);
			if (ext.equalsIgnoreCase("pem") || ext.equalsIgnoreCase("cer")
					|| ext.equalsIgnoreCase("crt")) {
				return true;
			}

		}
		return false;
	}

	@RequestMapping(value = "/certificateextracter.ajax", method = RequestMethod.GET)
	public ModelAndView certificateExtractor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		AuthenticationConfig config=this.system.getAuthenticationConfig(TenantContext.getTenantId());
		if(config!=null && config.isCacflag()){//causing unnecessary error in the log due to file missing if it is not pki enabled
		List<CRTObject> crtObjs=null;
		boolean stageFlag= ServletRequestUtils.getBooleanParameter(request, "stageFlag", false);
		try{
			crtObjs=securityService.extractCertificate(stageFlag);		
		}catch(Exception e){
			logger.error("error occurred",e);
			model.put("count", 0);		
			return new ModelAndView("ajax/ca_certificate_ajax", "model", model);
		}
	      Long featureCount = (long) crtObjs.size();

	        model.put("count", featureCount);
	   
	        model.put("list", crtObjs);
		}
	        return new ModelAndView("ajax/ca_certificate_ajax", "model", model);
		
	}
	
	@RequestMapping(value = "/uploadcaccertificate.ajax", method = RequestMethod.POST)
    public ModelAndView uploadCACCertificate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		String uploadedFile =null;
		Path tempPath=null;
		File tempFile=null;
		Locale loc = LocaleContextHolder.getLocale();
		String actionType =ServletRequestUtils.getStringParameter(request,"actionType", null); 				
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile multipartFile = multipartRequest.getFile("certificatefilefield");
		if (multipartFile==null ||!validateExtension(multipartFile)) {
			errors.add(new FieldError("upload", "upload",
					"File extension is not supported"));
			 model.put("success", Boolean.FALSE);
			model.put("fields", errors);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}
		if (multipartFile.getSize() < 52428800) { 
			 uploadedFile = RandomStringUtils.randomAlphabetic(16);
			 tempFile= new File(uploadTempDir+uploadedFile);
			multipartFile.transferTo(tempFile);
			
			if (!securityService.uploadCACCertificates(uploadTempDir,
					tempFile, "append".equalsIgnoreCase(actionType))) {
				errors.add(new FieldError("upload", "upload",
						"Invalid client CA certificates file."));
				 model.put("success", Boolean.FALSE);
				model.put("fields", errors);
			} else {
				String baseURL = request.getScheme() + "://"
						+ request.getServerName();
				int status=system.updateCACCfgFile(TenantContext.getTenantId(),baseURL);
				if(status!=0){
					errors.add(new FieldError("upload", "upload",
							"Unable to update configuration changes"));
					model.put("fields", errors);
					model.put("success", Boolean.FALSE);
				}else{
				
				 model.put("success", Boolean.TRUE);
				}
			}
			tempPath = Paths.get(uploadedFile);
			if(Files.exists(tempPath)){
				try{
					logger.warn("going to delete temp file "+uploadedFile);
				Files.delete(tempPath);
				}catch(Exception e){
					logger.error("Deleting tmp file failed. "+e.getMessage());
				}
			}
			//deleting tmp file
			
			

		} else {
			errors.add(new FieldError("upload", "upload",
					"File size must be less than 50 MB."));
			 model.put("success", Boolean.FALSE);
			model.put("fields", errors);
		}
	
		
		
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	@RequestMapping(value = "/saveauthentication.ajax", method = RequestMethod.POST)
    public ModelAndView saveAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        AuthenticationConfig auth = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(auth);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        
        if(request.getScheme().equalsIgnoreCase("https")) {
        	auth.setHttpsRequest(true);
        }
        
        int samlmappingflag = ServletRequestUtils.getIntParameter(request, "samlmappingflag", 1);
        auth.setSamlAuthProvisionType(SamlProvisionType.get(samlmappingflag));
        int sessionExpPeriod = ServletRequestUtils.getIntParameter(request, "sessionExpPeriod", 0);

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
        	String baseURL = request.getScheme() + "://"
					+ request.getServerName();
        	        
            Integer tenantId = TenantContext.getTenantId();
            int resultCode = this.system.saveAuthenticationConfig(tenantId, auth,baseURL);
            FieldError fe = null;
            switch(resultCode) {
                case -1:    fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save", null, "", loc));
                            errors.add(fe);
                            break;
                case -2:    fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("saml.identity.provider.metadata.xml.is.invalid", null, "", loc));
                            errors.add(fe);
                            break;
                case -3:    fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("idp.attribute.for.user.name.is.not.defined", null, "", loc));
                            errors.add(fe);
                            break;
                case -4:    fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("entity.id.is.not.unique", null, "", loc));
                            errors.add(fe);
                            break;
            	case -5:
            		//TODO- add in message.properties
    				fe = new FieldError("Authentication", ms.getMessage(
    						"authentication", null, "", loc), ms.getMessage(
    						"Error in updating OCSP settings", null, "", loc));
    				errors.add(fe);
    				break;
    			
            }
            
            Configuration globalSessionExpPeriod = system.getConfiguration("SESSION_EXP_PERIOD");
            // Update only if the value is less than global setting
            if(sessionExpPeriod > 0 && sessionExpPeriod < Integer.valueOf(globalSessionExpPeriod.getConfigurationValue())) {
            	tenant.updateSessionExpirationConfig(TenantContext.getTenantId(), sessionExpPeriod);	
            }            
            
            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/testldapauthentication.ajax", method = RequestMethod.POST)
    public ModelAndView testLDAPAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

	    Locale loc = LocaleContextHolder.getLocale();

        AuthenticationConfig authConfig = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(authConfig);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            int error_code = this.system.testLDAPAuthenticationService((LdapAuthentication) authConfig.toAuthentication());
            if (error_code == -1) {
                FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("connection.test.failed", null, "", loc));
                errors.add(fe);
            } else if (error_code == 0) {
                FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), "System rebooting...");
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/testrestwsuserauthentication.ajax", method = RequestMethod.POST)
    public ModelAndView testRestWSUserAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        try{
        	
        
       

	  
	    AuthenticationConfig authConfig = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(authConfig);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
        	authConfig.setRestFlag(true);
        	WsRestAuthentication authType=(WsRestAuthentication) authConfig.toAuthentication();
        	String[] schemes = {"http","https"};
        UrlValidator urlValidator=new UrlValidator(schemes);
        	if(!urlValidator.isValid(authType.getRestUrl())){
        		 FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), "Unable to connect to the server.Please check the url");
                 errors.add(fe);
        	}
            if (errors.size() ==0&& (!this.system.restAuthenticationService(authType,authConfig.getUsername(),authConfig.getPassword())) ) {
                FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("user.authentication.failed", null, "", loc));
                errors.add(fe);
            }

           
        }
        }
        catch(Exception e){
        	logger.error(e.getMessage());       	
        	FieldError fe = new FieldError("Rest Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("user.authentication.failed", null, "", loc));
              errors.add(fe);
        }
        if(errors.size() != 0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }
	
	@RequestMapping(value = "/testldapuserauthentication.ajax", method = RequestMethod.POST)
    public ModelAndView testLDAPUserAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

	    Locale loc = LocaleContextHolder.getLocale();

	    AuthenticationConfig authConfig = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(authConfig);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (this.system.testLDAPUserAuthenticationService((LdapAuthentication) authConfig.toAuthentication()) == -1) {
                FieldError fe = new FieldError("LDAPAuthentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("user.authentication.failed", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/testwsuserauthentication.ajax", method = RequestMethod.POST)
	public ModelAndView testWSUserAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        AuthenticationConfig authConfig = new AuthenticationConfig();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(authConfig);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (this.system.testWSUserAuthenticationService((WsAuthentication) authConfig.toAuthentication(), authConfig.getUsername(), authConfig.getPassword()) == -1) {
                FieldError fe = new FieldError("WSAuthentication", "WSAuthentication", "User Authentication failed");
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/ldapmapping.ajax", method = RequestMethod.GET)
	public ModelAndView getLdapMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Integer tenantID = TenantContext.getTenantId();
		List<TenantLdapAttributeMapping> mappingList = this.ldapAttributesMapping.getTenantLdapAttributeMapping(tenantID);
		TenantConfiguration configuration=tenant.getTenantConfiguration(tenantID);
		if(configuration.getUserImage()!=1){
			
			ListIterator<TenantLdapAttributeMapping> iter = mappingList.listIterator();
			while(iter.hasNext()){
			    if(iter.next().getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo")){
			        iter.remove();
			    }
			}
		
		}
		
        for (TenantLdapAttributeMapping item : mappingList) {
        	item.setLdapAttributeName(item.getLdapAttributeName().replaceAll(ValidationUtils.TAG_EXPR,""));
        	item.setDefaultAttributeValue(item.getDefaultAttributeValue().replaceAll(ValidationUtils.TAG_EXPR,"")); 
        }
		
		model.put("list", mappingList);
		model.put("num", mappingList.size());

		return new ModelAndView("ajax/tenant_ldap_mapping_ajax", "model", model);
	}

	@RequestMapping(value = "/saveldapmapping.ajax", method = RequestMethod.POST)
    public ModelAndView saveLdapMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		Locale loc = LocaleContextHolder.getLocale();

		TenantLdapAttributeMapping tenantLdapAttributeMapping = new TenantLdapAttributeMapping();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(tenantLdapAttributeMapping);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();

		if (results.hasErrors()) {
			List<FieldError> fields = results.getFieldErrors();
			model.put("success", Boolean.FALSE);
			model.put("fields", fields);
		} else {
			Integer tenantId = TenantContext.getTenantId();
			try {
				TenantLdapAttributeMapping existingTenantLdapAttributeMapping = 
						ldapAttributesMapping.getTenantLdapAttributeMappingRecord(tenantId, tenantLdapAttributeMapping.getMappingID());
				if(existingTenantLdapAttributeMapping.getVidyoAttributeName().equalsIgnoreCase("EmailAddress") &&
					!tenantLdapAttributeMapping.getDefaultAttributeValue().matches("([\\w_\\-]+([\\.]))+([A-Za-z]{2,4})$")) {
					
					FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("email.address.does.not.match.to.domain.format", null, "", loc));
					errors.add(fe);
				}
			} catch (Exception ignore) {}
			
			tenantLdapAttributeMapping.setLdapAttributeName(
					tenantLdapAttributeMapping.getLdapAttributeName().replaceAll(ValidationUtils.TAG_EXPR,""));
			tenantLdapAttributeMapping.setDefaultAttributeValue(
					tenantLdapAttributeMapping.getDefaultAttributeValue().replaceAll(ValidationUtils.TAG_EXPR,""));   
            
			if (errors.size() == 0 && this.ldapAttributesMapping.updateTenantLdapAttributeMappingRecord(tenantId, tenantLdapAttributeMapping) <= 0) {
				FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
				errors.add(fe);
			}

			if(errors.size() != 0){
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
			}
			else {
				model.put("success", Boolean.TRUE);
			}
		}
		return new ModelAndView("ajax/result_ajax", "model", model);
    }

    public ModelAndView saveSamlValueMappingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();

        TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tenantIdpAttributeValueMapping);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
        	Integer tenantId = TenantContext.getTenantId();
            if (this.idpAttributesMapping.saveTenantIdpAttributeValueMappingRecord(tenantId, tenantIdpAttributeValueMapping) == -1) {
                FieldError fe = new FieldError("Authentication", ms.getMessage("authentication", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/network.html",method = RequestMethod.GET)
    public ModelAndView getSuperNetworkHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("super/network_html", "model", model);
    }

    @RequestMapping(value = "/network.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperNetworkAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Network network = this.network.getNetworkSettings();
        model.put("network", network);

        String systemID = this.system.getSystemId();
        if((systemID == null) || (systemID.length()==0))
            systemID = network.getMACAddress();
        model.put("systemID", systemID);
        TimeZone tz = Calendar.getInstance().getTimeZone();

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

        model.put("systemTimeZone", dateFormat.format(new Date())+ " "+tz.getDisplayName());

        return new ModelAndView("ajax/network_ajax", "model", model);
    }

    //Supports only 1)Single license file starts with v3.license.
    @RequestMapping(value = "/savelicense.ajax",method = RequestMethod.POST)
    public ModelAndView saveSuperLicenseAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("client-path");
        File uploadedFile = new File(uploadTempDirSuper + multipartFile.getOriginalFilename());
        multipartFile.transferTo(uploadedFile);
        String licFileName = uploadedFile.getAbsolutePath();
        String directoryPath = "";
        String sysLicFileName = "";
        String vmLicFileName = "";
        Map<String, String> licenseDataMap = null;

        if(multipartFile.getOriginalFilename().toLowerCase().startsWith("v3.") || multipartFile.getOriginalFilename().toLowerCase().startsWith("v2.")) {
            if (multipartFile.getOriginalFilename().toLowerCase().startsWith("v3.license") && !(licFileName.toLowerCase().contains("eventlicense"))) {
                //single License file: split to vm and sys license
                directoryPath = PortalUtils.splitLicFile(licFileName);
                if(!directoryPath.isEmpty()){ // directoryPath will be empty if splitting the single license file to 2 files fails.
                    vmLicFileName = directoryPath + "vmlicense";
                    sysLicFileName = directoryPath + "syslicense";
                }else {
                    logger.error("Failed to process single license file");
                    FieldError fe = new FieldError("SaveLicense", ms.getMessage("error", null, "", loc), ms.getMessage("v3.SuperSystemLicense.invalid", null, "", loc));
                    errors.add(fe);
                    model.put("fields", errors);
                }
            }
            else if (licFileName.contains("eventlicense")) {
                if (!this.licensing.setSystemLicense(licFileName)) { //  VidyoManager use same WebService operation as handling system license, so call same setSystemLicense() api.
                    logger.error("Failed to set Event license to VidyoManager");
                    FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), ms.getMessage("failed.to.set.event.license.to.vm", null, "", loc));
                    errors.add(fe);
                }
            } else {
                logger.error("Incorrect license file name");
                FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), ms.getMessage("v3.SuperSystemLicense.invalid", null, "", loc));
                errors.add(fe);
            }
        }
        else {
            logger.error("Incorrect license file name");
            FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), ms.getMessage("v3.SuperSystemLicense.invalid", null, "", loc));
            errors.add(fe);
        }

        // Step 1 set VM LICENSE  (send SOAP request setVidyoManagerLicense to VM via licensing service [synchronous])
        if(!vmLicFileName.isEmpty()){
            if (!this.licensing.setVMLicense(vmLicFileName)) {
                logger.error("Failed to set VM license to VidyoManager");
                FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), ms.getMessage("SuperSystemLicense.failed.to.set.vmlic.to.VM", null, "", loc));
                errors.add(fe);
            }
        }
        //Step 2 get License data (send SOAP request getLicenseData to VM via licensing service)
        if(!sysLicFileName.isEmpty()){
            licenseDataMap = licensing.getLicenseFeatureDataFromFile(sysLicFileName);
            if(licenseDataMap != null){
                // Step 3 check license
                String checkLicense = licensing.checkLicenseToBeUploaded(licenseDataMap);
                if (checkLicense != null) {
                    String message = "";
                    if (checkLicense.contains("|")) {
                        String[] checkLicenseWithArgs = checkLicense.split("[|]");
                        checkLicense = checkLicenseWithArgs[0];
                        String arg = checkLicenseWithArgs[1];
                        logger.error("Failed to set system license to VidyoManager: " + checkLicense + " - " + arg);
                        message = MessageFormat.format(ms.getMessage(checkLicense, null, "", loc), arg);
                    } else {
                        message = ms.getMessage(checkLicense, null, "", loc);
                    }
                    FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), message);
                    errors.add(fe);
                    // License Validation with current seats/lines/executives/panorama etc failed
                    // Revert back to old license
                    licensing.revertLicense();
                }
                // is systemID based license and is HA enabled, then revert license
                if (licenseDataMap.get("FQDN") == null && "true".equals(licenseDataMap.get("BackupLic"))) {
                    FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), "SystemID based license with Hot Standby not permitted.");
                    errors.add(fe);
                    licensing.revertLicense();
                }

                //Step 4 check that there is not errors and set Sys License (send SOAP request setLicense to VM via licensing service)
                if (errors.size() == 0 && !this.licensing.setSystemLicense(sysLicFileName)) {
                    logger.error("Failed to set system license to VidyoManager");
                    FieldError fe = new FieldError("SaveSystemLicense", ms.getMessage("error", null, "", loc), ms.getMessage("SuperSystemLicense.failed.to.set.to.VM", null, "", loc));
                    errors.add(fe);
                }
            }
        }

        // Clear VM cache explicitly. This has to be invoked to clear the VidyoManager credentials cache, right after license upload
        systemService.clearVidyoManagerCache();

        if (errors.size()!=0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            // in case of syslicense
            if (!sysLicFileName.isEmpty()) {
                int licenseSeats = 0;
                try {
					licenseSeats = Integer.valueOf(licenseDataMap.get("Seats"));
				} catch (NumberFormatException nfe) {
					logger.warn("Seats is not an integer value {}", licenseDataMap.get("Seats"));
				}
                int licensePorts = 0;
                try {
					licensePorts = Integer.valueOf(licenseDataMap.get("Ports"));
				} catch (NumberFormatException nfe) {
					logger.warn("Ports is not an integer value {}", licenseDataMap.get("Ports"));
				}
                int licenseInstalls = 0;
                try {
					licenseInstalls = Integer.valueOf(licenseDataMap.get("Installs"));
				} catch (NumberFormatException nfe) {
					logger.warn("Installs is not an integer value {}", licenseDataMap.get("Installs"));
				}
                int licenseExecutives = 0;
                try {
					licenseExecutives = Integer.valueOf(licenseDataMap.get("LimitTypeExecutiveSystem"));
				} catch (NumberFormatException nfe) {
					logger.warn("LimitTypeExecutiveSystem is not an integer value {}", licenseDataMap.get("LimitTypeExecutiveSystem"));
				}
                boolean multiTenant = Boolean.valueOf(licenseDataMap.get("MultiTenant"));
                if (!multiTenant) {
                    this.tenant.updateRegularLicense(licenseInstalls, licenseSeats, licensePorts, licenseExecutives);
                }
            }
            model.put("success", Boolean.TRUE);
        }
        system.clearVidyoManagerAndLicenseCache(TenantContext.getTenantId());

        systemService.updateVidyoManagerCredentials();

        FileUtils.deleteQuietly(uploadedFile);
        FileUtils.deleteQuietly(new File(vmLicFileName));
        FileUtils.deleteQuietly(new File(sysLicFileName));

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/superaccount.html",method = RequestMethod.GET)
    public ModelAndView getSuperAccountHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);

		model.put("memberID", memberID);

		if (memberID != 0) {
			Member superAcct = memberService.getSuper(memberID);
			if(superAcct != null) {
				superAcct.setMemberName(superAcct.getMemberName()
						.replace("& #40;".subSequence(0, "& #40;".length()), "(")
						.replace("& #41;".subSequence(0, "& #41;".length()), ")")
						.replace("& lt;".subSequence(0, "& lt;".length()), "<")
						.replace("& gt;".subSequence(0, "& gt;".length()), ">")
						.replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ")
						.replace("& #39;".subSequence(0, "& #39;".length()), "'")
				);
				model.put("memberName", superAcct.getMemberName());
				model.put("memberID", superAcct.getMemberID());
			} else {
				logger.error("Exception while getting super " + memberID);
				model.put("memberName", "");
			}
		} else {
			model.put("memberName", "");
		}
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("super/superaccount_html", "model", model);
    }

    @RequestMapping(value = "/superaccount.ajax",method = RequestMethod.POST)
    public ModelAndView getSuperAccountAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

        Map<String, Object> model = new HashMap<String, Object>();

        Member superAcct;
		if (memberID == 0) {
			superAcct = createEmptySuperAccountObj();
		} else {
			superAcct = memberService.getSuper(memberID);
			if(superAcct != null) {
				superAcct.setMemberName(superAcct.getMemberName()
						.replace("& #40;".subSequence(0, "& #40;".length()), "(")
						.replace("& #41;".subSequence(0, "& #41;".length()), ")")
						.replace("& lt;".subSequence(0, "& lt;".length()), "<")
						.replace("& gt;".subSequence(0, "& gt;".length()), ">")
						.replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ")
						.replace("& #39;".subSequence(0, "& #39;".length()), "'"));
			} else {
				superAcct = memberService.getSuper(memberID);
			}
		}

        model.put("member", superAcct);
        return new ModelAndView("ajax/superaccount_ajax", "model", model);
    }
    
    private Member createEmptySuperAccountObj() {
    	Member superAcct = new Member();
		superAcct.setUsername("");
		superAcct.setMemberName("");
		superAcct.setEmailAddress("");
		superAcct.setLangID(1); // English Language = Default for Super
		superAcct.setEnable("on");
		
		return superAcct;
    }

    @RequestMapping(value = "/checksupername.ajax",method = RequestMethod.GET)
    public ModelAndView checkSuperNameAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> model = new HashMap<String, Object>();

    	 String username = ServletRequestUtils.getStringParameter(request, "username", null);

    	 if (username == null) {
    		 model.put("success", Boolean.FALSE);
    		 return new ModelAndView("ajax/result_ajax", "model", model);
    	 }

    	 boolean memberExists = member.isSuperExistForUserName(username);
    	 model.put("success", memberExists);

    	 return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/savesuperaccount.ajax",method = RequestMethod.POST)
    public ModelAndView saveSuperAccountAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        Locale loc = LocaleContextHolder.getLocale();
        int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
        Member member = new Member();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(member);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
        	// Check if the user name is valid
        	if (member.getUsername() == null || (member.getUsername() != null && member.getUsername().isEmpty() || member.getUsername().length() > 80
        				|| !member.getUsername().matches(ValidationUtils.USERNAME_REGEX))) {   //printable unicoded username
        			model.put("success", Boolean.FALSE);
        			FieldError fe = new FieldError("SuperAccount", ms.getMessage("error", null, "", loc), "invalidusername");
					errors.add(fe);
            } else {
	        	if (memberID == 0) {
					// create a new member
					String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
					if (this.member.isValidMemberPassword(password, memberID)) {
						member.setPassword(PasswordHash.createHash(password));
						memberService.insertSuper(member);
					} else {
						logger.error("Password for Super Account did not pass the requirements check");
						FieldError fe = new FieldError("password1", ms.getMessage("error", null, "", loc), ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));
						errors.add(fe);
					}
	        	} else {
	        		boolean validSuper = true;
	        		Member validMember = memberService.getSuper(memberID);
	        		if(validMember == null) {
	        			logger.error("Failed to update Super Account");
						FieldError fe = new FieldError("UpdateSuperAccount", ms.getMessage("error", null, "", loc), ms.getMessage("SuperAccount.failed.to.update", null, "", loc));
						errors.add(fe);
						validSuper = false;
	        		}
					if(validSuper) {
		        		String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
						if (password.equalsIgnoreCase("do_not_update")) {
							member.setPassword(null);
						} else {
	                        boolean validPasswd = this.member.isValidMemberPassword(password, member.getMemberID());
	                        if (!validPasswd) {
	                            logger.error("Password for Super Account did not pass the requirements check");
	                            FieldError fe = new FieldError("password1", ms.getMessage("error", null, "", loc), ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));
	                            errors.add(fe);
	                            model.put("fields", errors);
	                            model.put("success", Boolean.FALSE);
	                            return new ModelAndView("ajax/result_ajax", "model", model);
	                        }
	   					    member.setPassword(PasswordHash.createHash(password));
						}
						if(validMember != null) {
							member.setTenantID(validMember.getTenantID());
						}
						int updrec = 0;

							updrec = this.member.updateSuper(member);
						if(updrec==-5){
							//removed the exception handler and put manual check. Since there is another aop which look for proper value and when you throw exception from service layer that aop authByTokensDeletingAdvisor gets error and breaking the complete flow.
							//the real fix should do something in aop settings which is dangerous at this time.
							FieldError fe = new FieldError("status", ms.getMessage("error", null, "", loc), ms.getMessage("logged.in.super.cannot.disable.himself", null, "", loc));
	                        errors.add(fe);
	                        model.put("fields", errors);
	                        model.put("success", Boolean.FALSE);
	                        return new ModelAndView("ajax/result_ajax", "model", model);
						}
						if(updrec != 1) { // only 1 line should be affected
							logger.error("Failed to update Super Account");
							FieldError fe = new FieldError("UpdateSuperAccount", ms.getMessage("error", null, "", loc), ms.getMessage("SuperAccount.failed.to.update", null, "", loc));
							errors.add(fe);
						}
					}

	        	}
            }
            if(errors.size()!=0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
				User user = this.user.getLoginUser();
				if (user == null) { // changed username for current logged in user
					SecurityContextHolder.clearContext();
				} else {
					if (memberID == user.getMemberID()) {
						SecurityContextHolder.clearContext();
					}
				}
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/approve_tenant_cert.ajax",method = RequestMethod.POST)
    public ModelAndView approveTenantCert(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String tenantId = ServletRequestUtils.getStringParameter(request, "tenantId",null);
        if(tenantId!=null){
        	boolean approveSuccess=this.system.approveTenantCert(tenantId);
        	if(!approveSuccess){
        		  model.put("success", Boolean.FALSE);
        	}else{
        		   model.put("success", Boolean.TRUE);
        	}
        }else{
        	 model.put("success", Boolean.FALSE);
        }
    
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/webserverrestartrequest.ajax", method = RequestMethod.GET)
    public ModelAndView getWebServerRestartRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

       List<Tenant> tenantList= this.system.getPKICertificateForReviewList();
       model.put("tenants",tenantList);
       model.put("success", Boolean.TRUE);
        return new ModelAndView("ajax/webserver_restart_ajax", "model", model);
    }

    @RequestMapping(value = "/maintenance.html", method = RequestMethod.GET)
    public ModelAndView getMaintenanceHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        Locale loc = LocaleContextHolder.getLocale();
        Configuration config = this.system.getConfiguration("INTERNAL_VERSION");
        if(config != null) {
        	model.put("portalversion", config.getConfigurationValue());
        }

        SystemLicenseFeature feature = null;

        try {
			feature = this.licensing.getSystemLicenseFeature("AllowPortalAPIs");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
        if (feature == null || !feature.getLicensedValue().equalsIgnoreCase("true")) {
            model.put("showStatusNotifyTab", false);
		} else {
            model.put("showStatusNotifyTab", true);
		}

        try {
			feature = this.licensing.getSystemLicenseFeature("AllowExtDB");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}", e.getMessage());
		}
        
        if (feature == null || !feature.getLicensedValue().equalsIgnoreCase("true")) {
            model.put("showExtDbTab", false);
		} else {
            model.put("showExtDbTab", true);
		}
       try {
		//any pki settings waiting for super approval
		    List<Tenant> tenantList= this.system.getPKICertificateForReviewList();
		    if(tenantList!=null && tenantList.size()>0){
		    	 model.put("pkiNotification", ms.getMessage("pki.certificate.pending.message", null, "", loc));
		    	 model.put("pkiCertReviewPending",true);
		    }else{
		    	tenantList= this.system.getWebServerRestartRequest();
		    	 if(tenantList!=null && tenantList.size()>0){
		    		 model.put("pkiNotification", ms.getMessage("web.server.restart.is.pending", null, "", loc));
		    		 model.put("pkiCertReviewPending",false);
		    	 }else{
		    		 model.put("pkiNotification", null);
		    		 model.put("pkiCertReviewPending",false);
		    	 }
		    }
	} catch (Exception e) {
		logger.error(e.getMessage());
		model.put("pkiNotification", null);
		 model.put("pkiCertReviewPending",false);
	}
       

        model.put("backupLic", false);
		String cdrFormat = this.system.getCDRformat();
		model.put("CDRFormat", cdrFormat);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("allowUpgrade", false);
        int maintStatus = system.isPortalOnMaintenance(null);
        if(maintStatus == 0 || maintStatus == 1) {
        	/*
        	 * If the status code is 0 - node is in maint mode,
        	 * If the status code is 1 - hot standby not enabled/not licensed
        	 * If the status code is 2 - hot standby enabled, but not in maintenance mode
        	 */
        	model.put("allowUpgrade", true);
        }
        model.put("peerStatus",1);
        
        if (maintStatus != 1) {
        	 model.put("backupLic", true);
        	 if (maintStatus != 0) {
		        ClusterInfo clusterInfo = system.getClusterInfo("param");
		        if (clusterInfo != null ) {
			        if ((clusterInfo.getMyStatus() != null && !clusterInfo.getMyStatus().equalsIgnoreCase("offline")) 
			        		&& (clusterInfo.getPeerStatus() != null && clusterInfo.getPeerStatus().equalsIgnoreCase("offline")) ){
			        	model.put("peerStatus",0);
			        } else {
			        	model.put("peerStatus",2);
			        }
		        }
        	 }
	    }
		model.put("betaFeatureEnabled", VendorUtils.isBetaFeatureEnabled());
        model.put("guideLoc", guideLoc);

        model.put("showSyslog", true);
        
        String trustStoreProvider = System.getProperty("javax.net.ssl.trustStoreProvider");
        if(trustStoreProvider != null && trustStoreProvider.equalsIgnoreCase("SunPKCS11-NSS")) {
        	model.put("fipsEnabled", true);
        } else {
        	model.put("fipsEnabled", false);
        }
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));

        return new ModelAndView("super/maintenance_html", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_logfqdn.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceGetFQDNLogAggrServer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();      
        String logFqdn=null;
        try{
        	logFqdn=system.getConfigValue(0,LogAggregationServer );
        	  model.put("logfqdn",logFqdn);
              model.put("success", Boolean.TRUE);
        }catch(Exception e){
        	 logger.error(e.getMessage());
        	 model.put("success", Boolean.FALSE);
        }
      
      
        return new ModelAndView("ajax/fqdn_logaggr_server", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_savelogfqdn.ajax", method = RequestMethod.POST)
	public ModelAndView maintenanceSaveFQDNLogAggrServer(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();
		String configValue = ServletRequestUtils.getStringParameter(request, "logfqdn", "");
		boolean enableLogAggrForTenants = ServletRequestUtils.getBooleanParameter(request, "enableLogAggr", false);
		int status = 0;
		status = this.system.saveSystemConfig(LogAggregationServer, configValue, 0);
		if (status == 0) {
			FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc),
					"Failed to save Aggregation server info.");
			errors.add(fe);

		}
		if (errors.size() == 0) {			
		
			

			if (!configValue.isEmpty() && enableLogAggrForTenants == true) {
				status = tenant.updateTenantAllLogAggregation(1);
				if (status == 0) {
					FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc),
							"Log aggregation saved, however, failed to enable/disable log aggregation with all tenants.");
					errors.add(fe);

				}
			}
			//disable..disable all tenants
			if (configValue.isEmpty()) {
				status = tenant.updateTenantAllLogAggregation(0);
				if (status == 0) {
					FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc),
							"Log aggregation saved, however, failed to enable/disable log aggregation with all tenants.");
					errors.add(fe);

				}
			}
			
			
		}
		if (errors.size() != 0) {
	
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			

			model.put("success", Boolean.TRUE);
		}
		system.auditLogTransaction("Log aggregation server is configured", "",errors.size()!= 0? FAILURE:SUCCESS);
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	@RequestMapping(value = "/securedmaint/maintenance_system_shutdown.ajax",method = RequestMethod.POST)
    public ModelAndView maintenanceSystemShutdown(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        if(!this.system.shutdownSystem()){
            FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc), ms.getMessage("failed.to.shutdown.the.system", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/securedmaint/maintenance_system_restart.ajax",method = RequestMethod.POST)
    public ModelAndView maintenanceSystemRestart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        if(!this.system.rebootSystem()){
            FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc), ms.getMessage("failed.to.reboot.the.system", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/securedmaint/maintenance_webserver_restart.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceRestartWebServer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        if(!this.system.restartWebServer()){
            FieldError fe = new FieldError("Maintenance", ms.getMessage("internal.error", null, "", loc), "Failed to restart Web Server.");
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_system_upgrade_upload.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceSystemUpgradeUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        List<FieldError> errors = new ArrayList<FieldError>(1);

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("PORTALarchive");
        String fileName = multipartFile.getOriginalFilename();

        if (!fileName.matches(SystemServiceImpl.UPLOADED_FILE_NAME_REGEX)) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("incorrect.file.upload.params", null, "", loc), "Invalid file format");
            errors.add(fe);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        if (!fileName.endsWith(ms.getMessage("UploadFileExtension", null, "", loc))) {
            logger.error("Upgrade stopped because file not a .vidyo");
            FieldError fe = new FieldError("Maintenance",
                    ms.getMessage("incorrect.file.type", null, "", loc),
                    ms.getMessage("type.of.server.software.file.should.be.a.b.tag.vidyo.b", null, "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // upgrade in progress by another instance or super user, so do not permit another upload now
        File installInProgressMarker =  new File(uploadTempDirSuper + "INSTALL_IN_PROGRESS_STATUS");
        if (installInProgressMarker.exists()) {
            logger.error("Upload was rejected because an upgrade was already in progress");
            FieldError fe = new FieldError("Maintenance",
                    ms.getMessage("update.failed", null, "", loc),
                    "Upgrade already in progress (code 1).");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        File uploadedFile = new File(uploadTempDirSuper + multipartFile.getOriginalFilename());
        if (uploadedFile.exists()) {
            // log this, but ignore due to following use case: upload succeeds and upgrade ajax call never happens
            logger.error("NOTE: uploaded file already exists, indicating existing or failed upgrade, ignoring ...");
        }
        multipartFile.transferTo(uploadedFile);
        
        model.put("success", Boolean.TRUE);

        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_system_upgrade.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceSystemUpgrade(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        File uploadedFile = null;
    	File installInProgressMarker =  new File(uploadTempDirSuper + "INSTALL_IN_PROGRESS_STATUS");

        try {

            String fileName = ServletRequestUtils.getStringParameter(request, "PORTALarchive", "");
            if (fileName.isEmpty()) {
                logger.error("Upgrade stopped because fileName param was empty");
                FieldError fe = new FieldError("Maintenance",
                        ms.getMessage("update.failed", null, "", loc),
                        ms.getMessage("file.upload.failed", null, "", loc));
                errors.add(fe);
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
                return new ModelAndView("ajax/result_ajax", "model", model);
            }

            uploadedFile = new File(uploadTempDirSuper + fileName);
            String uploadFileAbsolutePath = uploadedFile.getAbsolutePath();

            if (!uploadFileAbsolutePath.endsWith(ms.getMessage("UploadFileExtension", null, "", loc))) {
                logger.error("Upgrade stopped because file not a .vidyo");
                FieldError fe = new FieldError("Maintenance",
                        ms.getMessage("incorrect.file.type", null, "", loc),
                        ms.getMessage("type.of.server.software.file.should.be.a.b.tag.vidyo.b", null, "", loc));
                errors.add(fe);
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
                return new ModelAndView("ajax/result_ajax", "model", model);
            }

            if (installInProgressMarker.exists()) {
                logger.error("Upgrade stopped because another upgrade already in progress");
                FieldError fe = new FieldError("Maintenance",
                        ms.getMessage("update.failed", null, "", loc),
                        "Upgrade already in progress (code 2).");
                errors.add(fe);
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
                return new ModelAndView("ajax/result_ajax", "model", model);
            } else {
                logger.debug("create INSTALL_IN_PROGRESS_STATUS marker");
                if (!installInProgressMarker.createNewFile()) {
                    logger.error("failed to create INSTALL_IN_PROGRESS_STATUS marker");
                }
            }

            logger.debug("Upgrade process start");

            if (!this.system.upgradeSystem(uploadFileAbsolutePath)) {
                logger.debug("Upgrade failed, do post-processing");

                FieldError fe = new FieldError("Maintenance",
                        ms.getMessage("update.failed", null, "", loc),
                        ms.getMessage("cannot.update.server.software.from.the.uploaded.file.new", null, "", loc));

                File preInstallMarker = new File(uploadTempDirSuper + "PRE_INSTALL_STATUS");
                if (preInstallMarker.exists()) {
                    String contents = FileUtils.readFileToString(preInstallMarker);
                    if (!StringUtils.isBlank(contents)) {
                        contents = contents.trim();
                    }
                    if ("INSTALLER".equals(contents)) {
                        //generate token and forward user: /installer/install?token=<TOKEN>
                        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                        FileUtils.writeStringToFile(new File(uploadTempDirSuper + "INSTALLER_TOKEN"), token);
                        fe = new FieldError("Maintenance",
                                ms.getMessage("update.failed", null, "", loc),
                                "FORWARD:/installer/install?token=" + token);
                    } else {
                        fe = new FieldError("Maintenance",
                                ms.getMessage("update.failed", null, "", loc),
                                contents);
                    }
                    preInstallMarker.delete();
                } else {
                    File failureMarker = new File("/opt/vidyo/temp/root/UPDATE_FAILED");
                    if (failureMarker.exists()) {
                        String msg = FileUtils.readFileToString(failureMarker, "UTF-8");
                        fe = new FieldError("Maintenance",
                                ms.getMessage("update.failed", null, "", loc),
                                msg);
                    }
                }

                errors.add(fe);
            }

            logger.debug("Upgrade process end");

            if (errors.size() != 0) {
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            } else {
                model.put("success", Boolean.TRUE);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            FileUtils.deleteQuietly(installInProgressMarker);
            FileUtils.deleteQuietly(uploadedFile); // just in case upgrade script did not do it
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_install_log_list.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceInstallationLogList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<InstallationReport> reports = installationService.getInstallationReportList();
        model.put("num", reports.size());
        model.put("reports", reports);
        return new ModelAndView("ajax/installations_list_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_download_install_log.ajax",method = RequestMethod.GET)
    public ModelAndView maintenanceDownloadInstallLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fn = ServletRequestUtils.getStringParameter(request, "f");
        if (fn != null) {
            fn = fn.replace("..", "").replace("/", "");
        }
        String installLogPath = installationService.getInstalltionLogPath();
        String installLogDatedPath = installationService.getInstalltionLogDatedPath();

        Path path = FileSystems.getDefault().getPath(installLogPath + "/" + fn);
        Path path2 = FileSystems.getDefault().getPath(installLogDatedPath + "/" + fn);

        Path finalPath = null;

        if (Files.exists(path)) {
            finalPath = path;
        } else if (Files.exists(path2)) {
            finalPath = path2;
        }

        if(finalPath != null && Files.isRegularFile(finalPath)) {
            response.reset();

            response.setHeader("Pragma", "public");
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fn  + "\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentType("application/octet-stream");
            
            try(
            		ServletOutputStream outPut = response.getOutputStream();
            		InputStream in = Files.newInputStream(finalPath);
            ) {
            	long fileSize = Files.size(finalPath);
            	response.addHeader("Content-Length", Long.toString(fileSize));
            	
                byte[] bbuf = new byte[4096];
                int length = -1;
                while (((length = in.read(bbuf)) != -1)) {
                    outPut.write(bbuf,0,length);
                }
            } catch(IOException ex) {
            	logger.error(ex.getMessage());
            	throw ex;
            }
            
        }
        
        return null;    //donot response anything
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_installed_patches.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceInstalledPatches(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<InstallationReport> patches = installationService.getInstalledPatches(); 
        model.put("patches", patches);
        model.put("num", patches.size());
        return new ModelAndView("ajax/installed_patches_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_db.ajax", method = RequestMethod.GET)
    public ModelAndView getMaintenanceDbAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<DbBackupFileInfo> list = this.system.getBackupFileInfo();
        Collections.sort(list);

        model.put("num", list.size());
        model.put("backups", list);
        return new ModelAndView("ajax/maintenance_list_backups_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_backup_db.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceBackupDb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        String password = ServletRequestUtils.getStringParameter(request, "password", "test");
        boolean includeThumbnail = ServletRequestUtils.getBooleanParameter(request, "thumbnailBCFlag",false);
        Locale loc = LocaleContextHolder.getLocale();

        String dbVer = this.system.getDbVersion();
        dbVer = dbVer.replace(".", "");

        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
        String dirName = "db_"+sdf.format(new Date())+"_v"+dbVer;

        String tmpBackupDirStr = uploadTempDirSuper + dirName;
        File tmpBackupDir = new File(tmpBackupDirStr);
        tmpBackupDir.mkdir();

        if (!this.system.backupDatabase(dirName,includeThumbnail==true?1:0)) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("backup.db", null, "", loc), ms.getMessage("failed.to.backup.the.database", null, "", loc));
            errors.add(fe);
            FileUtils.deleteQuietly(tmpBackupDir);
        }

        if (errors.size() == 0) {
            String tarGzFilePath = tmpBackupDirStr + ".tar.gz";
            try {
                CompressionUtils.targzip(tmpBackupDirStr, tarGzFilePath, true);
                SecurityUtils.createSecureArchive(password, tmpBackupDirStr, tarGzFilePath, tmpBackupDirStr + ".veb");
                system.moveDatabase(dirName + ".veb");
            } catch (Exception e) {
                logger.error("Exception creating secure archive for backup: " + e.getMessage());
                FieldError fe = new FieldError("Maintenance", ms.getMessage("backup.db", null, "", loc), ms.getMessage("failed.to.backup.the.database", null, "", loc));
                errors.add(fe);
            } finally {
                FileUtils.deleteQuietly(tmpBackupDir);
                FileUtils.deleteQuietly(new File(tarGzFilePath));
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_delete_backup.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceDeleteBackup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fn = ServletRequestUtils.getStringParameter(request, "fileName");
        if (fn != null) {
            fn = fn.replace("..", "").replace("/", "");
        }

        Map<String, Object> model = new HashMap<String, Object>();

        boolean success = this.system.deleteBackupFile(fn);
        if (success) {
            model.put("success", Boolean.TRUE);
        } else {
            model.put("success", Boolean.FALSE);

        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_upload_db.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceUploadDb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        boolean isFileUploaded = false;
        Locale loc = LocaleContextHolder.getLocale();
        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("DBarchive");
        String origFileName = multipartFile.getOriginalFilename();
        if (origFileName != null && !origFileName.endsWith(".veb")) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("upload.backup", null, "", loc), ms.getMessage("incorrect.file.type", null, "", loc));
            errors.add(fe);
        } else {
            // Transfer to tmp location
            File uploadedFile = new File(uploadTempDirSuper + multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(uploadedFile);
            } catch (Exception e) {     //IOException, IllegalStateException
                FieldError fe = new FieldError("Maintenance", ms.getMessage("upload.backup", null, "", loc), ms.getMessage("failed.to.upload.the.backup.file.to.server", null, "", loc));
                errors.add(fe);
            }
            isFileUploaded = system.uploadDatabase(uploadedFile.getAbsolutePath(), (errors.size() == 0));
        }
        model.put("fields", errors);
        model.put("success", isFileUploaded);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_download_db.ajax", method = RequestMethod.GET)
    public void maintenanceDownloadDb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fn = ServletRequestUtils.getStringParameter(request, "f", "");
        if (fn != null) {
            fn = fn.replace("..", "").replace("/", "");
        }
        String backupPath = this.system.getDbBackupPath();
        File downloadFile = new File(backupPath+"/"+fn);
        if (!downloadFile.exists()) {
            this.system.downloadDatabase(backupPath+"/"+fn, false); // audit interceptor
            response.setContentType("text/html");
            response.sendError(org.springframework.http.HttpStatus.NOT_FOUND.value());
            return;
        }
        this.system.downloadDatabase(backupPath+"/"+fn, true); // audit interceptor
        long fileSize = downloadFile.length();

        response.reset();

        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fn  + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Content-Length", Long.toString(fileSize));
        response.setContentType("application/octet-stream");

        try (ServletOutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(downloadFile)) {
            IOUtils.copy(in, out);
        }
    }

    @RequestMapping(value = "/backups/*",method = RequestMethod.GET)
    public void downloadBackup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fn = request.getRequestURI();
        if (fn != null) {
            fn = fn.replace("/super/backups/", "" );
            fn = fn.replace("..", "").replace("/", "");
        }
        String backupPath = this.system.getDbBackupPath();
        File downloadFile = new File(backupPath+"/"+fn);
        if (!downloadFile.exists()) {
            if (!"favicon.ico".equals(fn)) { // browsers ask for this unnecessarily, so avoid logging
                this.system.downloadDatabase(backupPath+"/"+fn, false); // audit interceptor
            }
            response.setContentType("text/html");
            response.sendError(org.springframework.http.HttpStatus.NOT_FOUND.value());
            return;
        }
        this.system.downloadDatabase(backupPath+"/"+fn, true); // audit interceptor
        long fileSize = downloadFile.length();

        response.reset();

        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fn  + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Content-Length", Long.toString(fileSize));
        response.setContentType("application/octet-stream");

        try (ServletOutputStream out = response.getOutputStream();
             FileInputStream in = new FileInputStream(downloadFile)) {
            IOUtils.copy(in, out);
        }
    }

    @RequestMapping(value = "/securedmaint/maintenance_restore_db.ajax",method = RequestMethod.POST)
    public ModelAndView maintenanceRestoreDb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        String fn = ServletRequestUtils.getStringParameter(request, "filename");
        String password = ServletRequestUtils.getStringParameter(request, "password", "test");
        fn = fn.replace("..", "").replace("/", "");

        String dbVer = this.system.getDbVersion();
        dbVer = dbVer.replace(".", "");



        if (fn.contains(".vidyo")){
            FieldError fe = new FieldError("Maintenance", ms.getMessage("restore.db", null, "", loc), ".vidyo files are no longer supported");
            errors.add(fe);
        } else if(!fn.contains("_v" + dbVer)) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("restore.db", null, "", loc), ms.getMessage("db.version.mismatched", null, "", loc));
            errors.add(fe);
        } else {
            String dirName = fn.substring(0, fn.lastIndexOf(".veb"));
            File dir = new File(uploadTempDirSuper + dirName);
            dir.mkdir();
            try {
                SecurityUtils.extractSecureArchiveUsingPassword(password, "/var/backup/" + fn, dir.getAbsolutePath());
            } catch (Exception e) {
                logger.error("Error extracting db backup: " + e.getMessage());
                FieldError fe = new FieldError("Maintenance", ms.getMessage("restore.db", null, "", loc), "Invalid backup and/or password.");
                errors.add(fe);
            }

            if (errors.size()==0) {
                if (!this.system.restoreDatabaseWithBackupFile(fn)) {

                    FieldError fe = new FieldError("Maintenance", ms.getMessage("restore.db", null, "", loc), ms.getMessage("failed.to.restore.the.server.database", null, "", loc));
                    errors.add(fe);
                }

                this.endpointupload.deleteAllEndpointUploads();
            }
            FileUtils.deleteQuietly(dir);
        }


        if (errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }
        if((Boolean)model.get("success")) {
            // Logout the user after the defaults as the user may not be in the restored DB
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logoutHandler.logout(request, response, authentication);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_factory_defaults.ajax",method = RequestMethod.POST)
    public ModelAndView maintenanceFactoryDefaults(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
        String password = ServletRequestUtils.getStringParameter(request, "password", "test");
        boolean includeThumbnail = ServletRequestUtils.getBooleanParameter(request, "thumbnailBCFlag",false);
        String dbVer = this.system.getDbVersion();
        dbVer = dbVer.replace(".", "");

        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
        String dirName = "db_"+sdf.format(new Date())+"_v"+dbVer;

        String tmpBackupDirStr = uploadTempDirSuper + dirName;
        File tmpBackupDir = new File(tmpBackupDirStr);
        tmpBackupDir.mkdir();

        if (!this.system.backupDatabase(dirName,includeThumbnail==true?1:0)) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("backup.db", null, "", loc), ms.getMessage("failed.to.backup.the.database", null, "", loc));
            errors.add(fe);
            FileUtils.deleteQuietly(tmpBackupDir);
        }

        if (errors.size() == 0) {
            String tarGzFilePath = tmpBackupDirStr + ".tar.gz";
            try {
                CompressionUtils.targzip(tmpBackupDirStr, tarGzFilePath, true);
                SecurityUtils.createSecureArchive(password, tmpBackupDirStr, tarGzFilePath, tmpBackupDirStr + ".veb");
                system.moveDatabase(dirName + ".veb");
            } catch (Exception e) {
                logger.error("Exception creating secure archive for backup: " + e.getMessage());
                FieldError fe = new FieldError("Maintenance", ms.getMessage("backup.db", null, "", loc), ms.getMessage("failed.to.backup.the.database", null, "", loc));
                errors.add(fe);
            } finally {
                FileUtils.deleteQuietly(tmpBackupDir);
                FileUtils.deleteQuietly(new File(tarGzFilePath));
            }
        }

        if(!this.system.restoreDatabaseToFactoryDefaults()) {
            FieldError fe = new FieldError("Maintenance", ms.getMessage("factory.defaults", null, "", loc), ms.getMessage("failed.to.restore.the.database.to.factory.default.setting", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
            // Logout the user after the defaults as the user may not be in the restored DB
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logoutHandler.logout(request, response, authentication);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_cdr_read.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceReadCDR(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        CdrAccess ca = this.system.readCDR();
		model.put("cdr", ca);
        if (!StringUtils.isBlank(ca.getPassword())) {
            ca.setPassword(SystemServiceImpl.PASSWORD_UNCHANGED);
        }
        String trustStoreProvider = System.getProperty("javax.net.ssl.trustStoreProvider");
        if(trustStoreProvider != null && trustStoreProvider.equalsIgnoreCase("SunPKCS11-NSS")) {
        	model.put("fipsEnabled", true);
        } else {
        	model.put("fipsEnabled", false);
        }
        return new ModelAndView("ajax/cdrinfo_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_cdr_save.ajax", method = RequestMethod.POST)
	public ModelAndView maintenanceSaveCDR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		CdrAccess ca = new CdrAccess();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(ca);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			FieldError fe = new FieldError("Maintenance", ms.getMessage("grant.cdr.access", null, "", loc), ms.getMessage("failed.to.grant.permission", null, "", loc));
			errors.add(fe);
		}
		
		if(!ca.getFormat().equalsIgnoreCase("1")) {
			FieldError fe = new FieldError("Maintenance", ms.getMessage("grant.cdr.access", null, "", loc), ms.getMessage("invalid.cdr.format", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {

			this.system.enableCDR(ca);
			String trustStoreProvider = System.getProperty("javax.net.ssl.trustStoreProvider");
	        if(trustStoreProvider == null || !trustStoreProvider.equalsIgnoreCase("SunPKCS11-NSS")) {
				if (ca.getAllowdeny() == 1) { // CDR access for user 'cdraccess' enable
					if(ca.getId() != null && ca.getId().equalsIgnoreCase("")) {
						FieldError fe = new FieldError("Maintenance", ms.getMessage("username", null, "", loc), ms.getMessage("missing.information", null, "", loc));
						errors.add(fe);
					}
					if(ca.getIp() != null && ca.getIp().equalsIgnoreCase("")) {
						FieldError fe = new FieldError("Maintenance", ms.getMessage("ip.or.hostname", null, "", loc), ms.getMessage("missing.information", null, "", loc));
						errors.add(fe);
					}
					if(ca.getPassword() != null && ca.getPassword().equalsIgnoreCase("")) {
						FieldError fe = new FieldError("Maintenance", ms.getMessage("access.password", null, "", loc), ms.getMessage("please.provide.access.password", null, "", loc));
						errors.add(fe);
					}
					if (ca.getId() != null && ca.getIp() != null && ca.getPassword() != null) {
						if (!ca.getId().equalsIgnoreCase("") && !ca.getIp().equalsIgnoreCase("") && !ca.getPassword().equalsIgnoreCase("")) {
	                        if (SystemServiceImpl.PASSWORD_UNCHANGED.equals(ca.getPassword())) {
	                            CdrAccess caDB = this.system.readCDR();
	                            ca.setPassword(caDB.getPassword());
	                        }
                            if (!ca.getIp().matches("[a-zA-Z0-9\\-\\.\\:\\%]*")) {
                                FieldError fe = new FieldError("Maintenance", ms.getMessage("ip.or.hostname", null, "", loc), "Invalid IP or Hostname");
                                errors.add(fe);
                            } else {
                                if (!this.system.grantCDR(ca)) {
                                    FieldError fe = new FieldError("Maintenance", ms.getMessage("grant.cdr.access", null, "", loc), ms.getMessage("failed.to.grant.permission", null, "", loc));
                                    errors.add(fe);
                                }
                            }
						}
					}
				} else { // CDR access for user 'cdraccess' deny
					if (!this.system.denyCDR()) {
						FieldError fe = new FieldError("Maintenance", ms.getMessage("deny.cdr.access", null, "", loc), ms.getMessage("failed.to.revoke.permission", null, "", loc));
						errors.add(fe);
					}
				}	        	
	        }

			if (errors.size() != 0) {
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
			} else {
				model.put("success", Boolean.TRUE);
			}
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/securedmaint/maintenance_cdr_purge.ajax", method = RequestMethod.POST)
	public ModelAndView maintenancePurgeCDR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		CdrFilter cf = new CdrFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(cf);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			cf = null;
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {

			int affected = this.system.purgeCDR(cf);

			if (errors.size() != 0) {
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
			} else {
				model.put("success", Boolean.TRUE);
			}
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/securedmaint/cdrexport.ajax", method = RequestMethod.GET)
	public ModelAndView adminMaintenanceExportCDR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		CdrFilter cdrFilter = new CdrFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(cdrFilter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			cdrFilter = null;
		} else {
			// restrict to range
			cdrFilter.setDateperiod("range");
			// restrict to this tenant
			cdrFilter.setOneall("one");
			Tenant thisTenant = this.tenant.getTenant(TenantContext.getTenantId());
			if (thisTenant != null) {
				cdrFilter.setTenantName(thisTenant.getTenantName());
			}
		}

        String tmpFileName = System.currentTimeMillis() + ".cdr.csv";
		String tmpCsvFile = uploadTempDir + tmpFileName;
		this.system.exportCDRBatched(cdrFilter, tmpCsvFile);

		File theCsvFile = new File(tmpCsvFile);

		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Expires","0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setHeader("Content-Disposition", "attachment; filename=\"CDRexport.csv\"");
		response.addHeader("Content-Length", Long.toString(theCsvFile.length()));
		response.setContentType("text/csv");

        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new FileInputStream(theCsvFile)) {
            IOUtils.copy(inputStream, outputStream);
        } finally {
            FileUtils.deleteQuietly(theCsvFile);
        }

        return null;
    }

    @RequestMapping(value = "/securedmaint/cdrexportcount.ajax", method = RequestMethod.GET)
	public ModelAndView maintenanceExportCDRCount(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();

		CdrFilter cdrFilter = new CdrFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(cdrFilter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			cdrFilter = null;
		}

		int count = this.system.getExportCDRCount(cdrFilter);

		if (count > 65000) {
			FieldError fe = new FieldError("CDRExport",
					ms.getMessage("export.exceeds.65000.record.limit.please.restrict.selected.range", null, "", loc),
					ms.getMessage("export.exceeds.65000.record.limit.please.restrict.selected.range", null, "", loc));
			errors.add(fe);
		}

		if(errors.size()!=0){
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		}
		else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);

	}

    @RequestMapping(value = "/securedmaint/maintenance_cdr_export.ajax", method = RequestMethod.GET)
	public ModelAndView maintenanceExportCDR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		CdrFilter cdrFilter = new CdrFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(cdrFilter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			cdrFilter = null;
		}

        String tmpFileName = System.currentTimeMillis() + ".cdr.csv";
        String tmpCsvFile = uploadTempDirSuper + tmpFileName;
        this.system.exportCDRBatched(cdrFilter, tmpCsvFile);

        File theCsvFile = new File(tmpCsvFile);

		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Expires","0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setHeader("Content-Disposition", "attachment; filename=\"CDRexport.csv\"");
		response.addHeader("Content-Length", Long.toString(theCsvFile.length()));
		response.setContentType("text/csv");

        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new FileInputStream(theCsvFile)) {
            IOUtils.copy(inputStream, outputStream);
        } finally {
            FileUtils.deleteQuietly(theCsvFile);
        }

		return null;
	}

    @RequestMapping(value = "/fromroles.ajax", method = RequestMethod.GET)
	public ModelAndView getAuthFromRolesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        List<MemberRoles> list = this.system.getFromRoles();
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/from_roles_ajax", "model", model);
    }

    @RequestMapping(value = "/toroles.ajax", method = RequestMethod.GET)
    public ModelAndView getAuthToRolesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        List<MemberRoles> list = this.system.getToRoles();
        model.put("list", list);
        model.put("num", list.size());

        return new ModelAndView("ajax/to_roles_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/savenotification.ajax",method = RequestMethod.POST)
    public ModelAndView saveSuperNotification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
         SmtpConfig smtpConfig = new SmtpConfig();
        String notificationEnable = ServletRequestUtils.getStringParameter(request, "notificationFlag");
         if (!StringUtils.isBlank(notificationEnable) && "on".equals(notificationEnable)) {
            String fromEmail = ServletRequestUtils.getStringParameter(request, "fromEmail");
            String toEmail = ServletRequestUtils.getStringParameter(request, "toEmail");
            if ((this.system.saveSuperNotificationFromEmailAddress(fromEmail)==-1)
                    || (this.system.saveSuperNotificationToEmailAddress(toEmail)==-1)){
                FieldError fe = new FieldError("Maintenance", ms.getMessage("notification", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
                errors.add(fe);
            }
            String smtpHost = ServletRequestUtils.getStringParameter(request, "smtpHost");
            String smtpPort = ServletRequestUtils.getStringParameter(request, "smtpPort");
            String smtpSecurity = ServletRequestUtils.getStringParameter(request, "smtpSecurityValue");
            String smtpUsername = ServletRequestUtils.getStringParameter(request, "smtpUsername");
            String smtpPassword = ServletRequestUtils.getStringParameter(request, "smtpPassword");
            String smtpTrustAllCerts = ServletRequestUtils.getStringParameter(request, "smtpTrustAllCerts");


            smtpConfig.setHost(smtpHost);
            smtpConfig.setPort(Integer.parseInt(smtpPort));
            smtpConfig.setSecurityType(smtpSecurity);
            smtpConfig.setUsername(smtpUsername);
            smtpConfig.setPassword(smtpPassword);
            if (!StringUtils.isBlank(smtpTrustAllCerts) && "on".equals(smtpTrustAllCerts)) {
                smtpConfig.setTrustAllCerts(true);
            }
            smtpConfig.setEmailsOn(true);
        } else {
            smtpConfig.setEmailsOn(false);
        }
        try {
            emailService.setSuperSmtpConfig(smtpConfig);
        } catch (Exception e) {
            e.printStackTrace();
            FieldError fe = new FieldError("Maintenance", ms.getMessage("notification", null, "", loc), ms.getMessage("failed.to.save.notifications.data", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/testsmtpconfig.ajax",method = RequestMethod.POST)
    public ModelAndView testSuperSmtpConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        String fromEmail = ServletRequestUtils.getStringParameter(request, "fromEmail");
        String toEmail = ServletRequestUtils.getStringParameter(request, "toEmail");

        String smtpHost = ServletRequestUtils.getStringParameter(request, "smtpHost");
        String smtpPort = ServletRequestUtils.getStringParameter(request, "smtpPort");
        String smtpSecurity = ServletRequestUtils.getStringParameter(request, "smtpSecurity");
        String smtpUsername = ServletRequestUtils.getStringParameter(request, "smtpUsername");
        String smtpPassword = ServletRequestUtils.getStringParameter(request, "smtpPassword");
        String smtpTrustAllCerts = ServletRequestUtils.getStringParameter(request, "smtpTrustAllCerts");

        SmtpConfig smtpConfig = new SmtpConfig();
        smtpConfig.setHost(smtpHost);
        smtpConfig.setPort(Integer.parseInt(smtpPort));
        smtpConfig.setSecurityType(smtpSecurity);
        smtpConfig.setUsername(smtpUsername);
        smtpConfig.setEmailsOn(true);
        if (!SystemServiceImpl.PASSWORD_UNCHANGED.equals(smtpPassword)) {
            smtpConfig.setPassword(smtpPassword);
        } else {
            SmtpConfig smtpConfigDB = emailService.getSuperSmtpConfig();
            smtpConfig.setPassword(smtpConfigDB.getPassword());
        }
        if (!StringUtils.isBlank(smtpTrustAllCerts) && "on".equals(smtpTrustAllCerts)) {
            smtpConfig.setTrustAllCerts(true);
        }

        try {
            emailService.sendTestEmail(fromEmail, toEmail, smtpConfig);
        } catch (Exception e) {
            logger.error("SMTP server test failed. " + e.getMessage());
            e.printStackTrace();

            String exceptionMessage = e.getMessage();
            String cleanMessage = "";

            if (exceptionMessage.contains("Unknown SMTP host")) {
                cleanMessage = "The SMTP Hostname is invalid.";
            } else if (exceptionMessage.contains("Connection refused")) {
                cleanMessage = "The SMTP Port is invalid.";
            } else if (exceptionMessage.contains("does not support STARTTLS")) {
                cleanMessage = "This SMTP server does not support STARTTLS security on this port.";
            } else if (exceptionMessage.contains("plaintext connection?")) {
                cleanMessage = "This SMTP server does not support SSL/TLS security on this port.";
            } else if (exceptionMessage.contains("unable to find valid certification path")) {
                cleanMessage = "The SMTP server certificate is invalid. Either set to trust all certs or upload client CA cert.";
            } else if (exceptionMessage.contains("must be authenticated")) {
                cleanMessage = "The SMTP server username/password combination is invalid.";
            } else if (exceptionMessage.contains("Authentication failed")) {
                cleanMessage = "The SMTP server username/password combination is invalid.";
            }  else {
                cleanMessage = e.getMessage();
            }

            FieldError fe = new FieldError("Maintenance", ms.getMessage("notification", null, "", loc), cleanMessage);
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/authentication_saml_view.ajax", method = RequestMethod.GET)
    public ModelAndView getSamlSecurityMetadataAjax(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();


        String samlSecurityProfile = request.getParameter("samlSecurityProfile");
        String samlSSLProfile = request.getParameter("samlSSLProfile");
        String samlSignMetadata = request.getParameter("samlSignMetadata");
        
        String samlmappingflag = request.getParameter("samlmappingflag");
        SamlProvisionType samlProvisionType = SamlProvisionType.get(Integer.valueOf(samlmappingflag));
        String idpAttributeForUsername = request.getParameter("idpAttributeForUsername");
        
        String spEntityId = request.getParameter("samlSpEntityId");

        SamlAuthentication samlAuth = new SamlAuthentication();
        samlAuth.setSecurityProfile(samlSecurityProfile);
        samlAuth.setSslTlsProfile(samlSSLProfile);
        samlAuth.setSignMetadata("YES".equals(samlSignMetadata));
        if(request.getScheme().equalsIgnoreCase("https")) {
        	samlAuth.setHttps(true);
        }
        samlAuth.setAuthProvisionType(samlProvisionType);
        samlAuth.setIdpAttributeForUsername(idpAttributeForUsername);
        samlAuth.setSpEntityId(spEntityId);

        if(samlProvisionType == SamlProvisionType.LOCAL && (idpAttributeForUsername == null || idpAttributeForUsername.isEmpty())) {
        	model.put("output", ms.getMessage("please.fill.idp.attribute.for.user.name.field.and.click.view.service.provider.sp.metadata.xml.button", null, "", loc));
        } else {
            try {
                String serviceProviderMetadata = samlAuthenticationService.generateServiceProviderMetadata(TenantContext.getTenantId(), samlAuth);
                model.put("output", serviceProviderMetadata);
            } catch (Exception e) {
                model.put("output", e.getMessage());
                e.printStackTrace();
            }
        }

        return new ModelAndView("ajax/authentication_saml_ajax", "model", model);
    }

    @RequestMapping(value = "/customizedlogo.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperCustomizedSuperPortalLogoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String hostname = request.getServerName();
        String name = this.system.getCustomizedSuperPortalLogoName();
        if((name!=null) && (name.length()!=0)) {
        	if(name.contains("/")) {
        		name = name.substring(name.lastIndexOf("/") + 1);
        	}
            File logoFile = new File(upload_dir+ name);
            if(logoFile.exists()) {
            	model.put("spname", "/super/upload/"+ name);
            }
        }
        return new ModelAndView("ajax/customizedlogo_ajax", "model", model);
    }

    // super upload for super/admin portal logo
    @RequestMapping(value = "/securedmaint/savecustomizedlogo.ajax", method = RequestMethod.POST)
    public ModelAndView saveSuperCustomizedSuperPortalLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("SPLogoArchive");
        String mimeType = multipartFile.getContentType();

        //verify the file extension type
        String fn = multipartFile.getOriginalFilename().toLowerCase();
        if(!PortalUtils.isValidImageFileName(fn)) {
            FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " *.gif, *.jpg, *.png"));
            errors.add(fe);
        } else if (!PortalUtils.isValidImageMimeType(mimeType, fn)) {
            FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " gif, jpg, png"));
            errors.add(fe);
        } else {

            //store the new logo file
            String ext = fn.substring(fn.lastIndexOf('.')+1);
            fn = "superportallogo."+ext;
            File uploadedFileTemp = new File(uploadTempDirSuper + fn);
            multipartFile.transferTo(uploadedFileTemp);

            if (!PortalUtils.isValidImageFile(uploadedFileTemp)) {
                logger.info("Invalid mime-type for image upload.");
                FileUtils.deleteQuietly(uploadedFileTemp);
                FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), " gif, jpg, png"));
                errors.add(fe);
            } else {
                //remove old logo image file
                String oldname = this.system.getCustomizedSuperPortalLogoName();
                if ((oldname != null) && (oldname.length() != 0)) {
                    if (oldname.contains("/")) {
                        oldname = oldname.substring(oldname.lastIndexOf("/") + 1);
                    }
                    // Delete the file if it exists
                    FileUtils.deleteQuietly(new File(upload_dir+oldname));
                }

                PortalUtils.moveWebappsFileSecurely(uploadedFileTemp, new File(upload_dir + uploadedFileTemp.getName()));

                //update the database with new logo filename
                if (-1 == this.system.saveCustomizedSuperPortalLogo(upload_path + fn)) {
                    FieldError fe = new FieldError("CustomizedSuperPortalLogo", ms.getMessage("update.failed", null, "", loc), ms.getMessage("failed.to.save.customized.logo", null, "", loc));
                    errors.add(fe);
                }
            }
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/removecustomizedlogo.ajax", method = RequestMethod.POST)
    public ModelAndView removeSuperCustomizedSuperPortalLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        String removableLogoFileName = this.system.removeCustomizedSuperPortalLogo();
		if((removableLogoFileName!=null) && (removableLogoFileName.length()!=0)) {
    		// Delete the file if it exists
        	if(removableLogoFileName.contains("/")) {
        		removableLogoFileName = removableLogoFileName.substring(removableLogoFileName.lastIndexOf("/") + 1);
        	}
            FileUtils.deleteQuietly(new File(upload_dir+removableLogoFileName));
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/savecustomizeduserportallogo.ajax", method = RequestMethod.POST)
	public ModelAndView saveAdminCustomizedDefaultUserPortalLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();

		//uploading
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile multipartFile = multipartRequest.getFile("UPLogoArchive");

		//verify the file extension type
		String fn = multipartFile.getOriginalFilename().toLowerCase();
        ShellCapture capture = null;
		if(!fn.endsWith(".swf")) {
			FieldError fe = new FieldError("CustomizedDefaultUserPortalLogo", ms.getMessage("incorrect.file.type", null, "", loc), MessageFormat.format(ms.getMessage("logo.image.file.must.be.0", null, "", loc), "*.swf"));
			errors.add(fe);
		} else {
            String oldname = this.system.getCustomizedImageLogoName();
            oldname = (oldname != null ? oldname.trim() : "");

           	if(oldname.contains("/")) {
           		oldname = oldname.substring(oldname.lastIndexOf("/") + 1);
           	}

            FileUtils.deleteQuietly(new File(upload_dir+oldname));
       }

		//store the new logo file
		String tenantId = ""+this.system.getTenantId();
		fn = tenantId+"_"+fn;
        File uploadedTempFile = new File(upload_dir + fn);
        multipartFile.transferTo(uploadedTempFile);

		//update the database with new logo filename
		if(-1 == this.system.saveCustomizedLogo(this.upload_path + fn)){
			FieldError fe = new FieldError("CustomizedDefaultUserPortalLogo", ms.getMessage("update.failed", null, "", loc), ms.getMessage("failed.to.save.customized.logo", null, "", loc));
			errors.add(fe);
		}

		if(errors.size()!=0){
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		}
		else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/securedmaint/removecustomizeduserportallogo.ajax", method = RequestMethod.GET)
	public ModelAndView removeAdminCustomizedDefaultUserPortalLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();

		String removableLogoFileName = this.system.removeCustomizedLogo();
		ShellCapture capture = null;
		if(removableLogoFileName != null) {
        	if(removableLogoFileName.contains("/")) {
        		removableLogoFileName = removableLogoFileName.substring(removableLogoFileName.lastIndexOf("/") + 1);
        	}

            FileUtils.deleteQuietly(new File(upload_dir+removableLogoFileName));
		}

		if(errors.size()!=0){
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		}
		else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/externaldb.html", method = RequestMethod.GET)
    public ModelAndView getExternalDBHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("super/external_db_html", "model", model);
    }

    @RequestMapping(value = "/securedmaint/externaldb.ajax", method = RequestMethod.GET)
    public ModelAndView getExternalDBAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        DBProperties extDB = this.system.getDBProperties(request.getServletContext());

        if (!StringUtils.isBlank(extDB.getPassword())) {
            extDB.setPassword(SystemServiceImpl.PASSWORD_UNCHANGED);
        }

        model.put("DBProperties", extDB);

        return new ModelAndView("ajax/external_db_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/testexternaldb.ajax", method = RequestMethod.POST)
    public ModelAndView testExternalDBAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        DBProperties db = new DBProperties();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(db);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (SystemServiceImpl.PASSWORD_UNCHANGED.equals(db.getPassword())) {
               DBProperties extDBCurrent = this.system.getDBProperties(request.getServletContext());
               db.setPassword(extDBCurrent.getPassword());
            }
            if (this.system.testDBProperties(request.getServletContext(), db) == -1) {
                FieldError fe = new FieldError("DBProperties", ms.getMessage("dbproperties", null, "", loc), ms.getMessage("connection.test.failed", null, "", loc));
                errors.add(fe);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/saveexternaldb.ajax", method = RequestMethod.POST)
    public ModelAndView saveExternalDB(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        DBProperties db = new DBProperties();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(db);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            List<FieldError> fields = results.getFieldErrors();
            model.put("success", Boolean.FALSE);
            model.put("fields", fields);
        } else {
            if (SystemServiceImpl.PASSWORD_UNCHANGED.equals(db.getPassword())) {
                DBProperties extDBCurrent = this.system.getDBProperties(request.getServletContext());
                db.setPassword(extDBCurrent.getPassword());
            }
            if (this.system.setDBProperties(request.getServletContext(), db) == -1) {
                FieldError fe = new FieldError("DBProperties", ms.getMessage("dbproperties", null, "", loc), ms.getMessage("failed.to.save.db.properties", null, "", loc));
                errors.add(fe);
            } else {
                this.system.switchDBProperties(request.getServletContext(), db);
            }

            if(errors.size() != 0){
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);
            }
            else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/vmid.ajax",method = RequestMethod.GET)
    public ModelAndView getVmIdAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        
        String vmId = this.system.getSystemId();
        model.put("vmID", vmId);

        return new ModelAndView("ajax/vmid_ajax", "model", model);
    }
    
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "/ipc.ajax", method = RequestMethod.POST)
	public ModelAndView getInterPortalCommAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		int tenantID = ServletRequestUtils.getIntParameter(request, "tenantID", 0);
		// Get the existing configuration details for IPC

		if (tenantID == 0) {
			model.put("tenantID", 0);
			model.put("inboundLines", 99999);
			model.put("outboundLines", 99999);
			model.put("outbound", true);
			model.put("inbound", true);
			model.put("num", 1);
		} else {
			IpcConfiguration interPortalConference = ipcConfigurationService.getIpcConfiguration(tenantID);
			if(interPortalConference != null) {
				model.put("tenantID", interPortalConference.getTenantID());
				model.put("inboundLines", interPortalConference.getInboundLines());
				model.put("outboundLines", interPortalConference.getOutboundLines());
				model.put("outbound", interPortalConference.getOutbound() == 1);
				model.put("inbound", interPortalConference.getInbound() == 1);
				model.put("num", 1);
			} else {
				model.put("tenantID", 0);
				model.put("inboundLines", 99999);
				model.put("outboundLines", 99999);
				model.put("outbound", true);
				model.put("inbound", true);
				model.put("num", 1);
			}
		}
		return new ModelAndView("ajax/ipc_ajax", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView getInterPortalCommSettingsHtml(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering getInterPortalCommSettingsHtml() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		// Get the IPC Administrator flag from Configuration table
		boolean isIpcSuperManaged = system.isIpcSuperManaged();		
		model.put("superManaged", isIpcSuperManaged);
		if(!isIpcSuperManaged) {
			model.put("adminManaged", true);
		}else {
			model.put("adminManaged", false);
		}
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);        
		logger.debug("Exiting getInterPortalCommSettingsHtml() of SettingsController");
		return new ModelAndView("super/ipc_html", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "/saveipcsettings.ajax", method = RequestMethod.POST)
	public ModelAndView savePortalIpcSettingsAjax(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Entering savePortalIpcSettingsAjax() of SettingsController");
		
		List<FieldError> fields = new ArrayList<FieldError>();
		String ipcAdmin = ServletRequestUtils.getStringParameter(request,
				"accessControlGroup", "admin");
		String allowBlockFlag = ServletRequestUtils.getStringParameter(request,
                "allowBlockGroup", "");
		String prevAllowFlag = ServletRequestUtils.getStringParameter(request,
				"prevAllowFlag", "");
		String routerPoolID = ServletRequestUtils.getStringParameter(request,
                "routerPoolID", null);
		
		if (routerPoolID != null && !routerPoolID.equalsIgnoreCase("")) {
			if(routerPoolID.equalsIgnoreCase("0")) {
				system.deleteConfiguration("IPC_ROUTER_POOL");
			} else {
				// Save IPC RouterPool in Configuration Table
				Configuration config = system.getConfiguration("IPC_ROUTER_POOL");
				if (config == null) {
					system.saveSystemConfig("IPC_ROUTER_POOL", routerPoolID, 0);
				} else {
					if (!config.getConfigurationValue().equalsIgnoreCase(
							routerPoolID)) {
						system.updateSystemConfig("IPC_ROUTER_POOL", routerPoolID);
					}
				}				
			}

		}
		system.updateIpcAdmin(ipcAdmin);
		//Below updates happen only if the ipcAdmin is Super
		if (allowBlockFlag != "" && ipcAdmin.equalsIgnoreCase("super")) {
			int flag = 0;
			if (allowBlockFlag.equalsIgnoreCase("allow")) {
				flag = 1;
			}
			if (prevAllowFlag != "") {
				int prevFlag = -1;
				try {
					prevFlag = Integer.parseInt(prevAllowFlag);
				} catch (NumberFormatException nfe) {
					logger.error("NumberFormatException while parsing PrevAllowFlag", nfe);
				}
				if(prevFlag == -1 || (prevFlag != flag)) {
					//Update the System Configuration Setting for IPCAllowDomainsFlag
					system.updateIpcAllowDomainsFlag(flag);
				}

			}

			// get the deleted list
			String deletedIds = ServletRequestUtils.getStringParameter(request,
					"deleteIds", "");
			if (deletedIds != "") {
				String[] ids = deletedIds.split(",");
				if (ids.length > 0) {
					ipcDomainService.deleteIpcDomains(ids);
				}
			}
			// Process newly added domains
			String addedDomains = ServletRequestUtils.getStringParameter(
					request, "addedDomains", "");
			Locale loc = LocaleContextHolder.getLocale();
			
			if (addedDomains != "") {
				addedDomains = addedDomains.replaceAll("\"", "");
				String[] domainNames = addedDomains.split(",");
				List<String> domainNamesList = new ArrayList<String>();
		        	for (String domainName : domainNames) {
		        		if ((domainName != null) && !domainName.trim().isEmpty() 
		            			&& !ValidationUtils.isValidIPCDomainName(domainName)) {
		            		FieldError fe = new FieldError("addedDomains", "addedDomains", 
		                			ms.getMessage("valid.domain.address", null, "", loc));
		                	fields.add(fe);
		            	} else {
		            		domainNamesList.add(domainName);
		            	}
		        }
		        if (domainNamesList.size() > 0) {
		        	ipcDomainService.addDomains(
		        			domainNamesList.toArray(new String[domainNamesList.size()]), flag);
		        }
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
        if (fields.size() > 0) {
        	model.put("success", Boolean.FALSE);
        	model.put("fields", fields);
        } else {
        	model.put("success", true);
        }
        
		logger.debug("Exiting savePortalIpcSettingsAjax() of SettingsController");
		return new ModelAndView("ajax/savePortalIpcSettings_ajax", "model",
				model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ipcportaldomains.ajax", method = RequestMethod.GET)
	public ModelAndView getIpcPortalDomainsAjax(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("Entering getIpcPortalDomainsAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		List<IpcDomain> ipcDomains = ipcDomainService.getPortalIpcDomains();
		model.put("ipcDomains", ipcDomains);
		model.put("size", ipcDomains.size());
		logger.debug("Exiting getIpcPortalDomainsAjax() of SettingsController");
		return new ModelAndView("ajax/ipcPortalDomains", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syslang.ajax",method = RequestMethod.GET)
	public ModelAndView getSystemLanguagesAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("Entering getSystemLanguagesAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		List<Language> languages = system.getLanguages(false);
		model.put("nav", "settings");
		model.put("list", languages);
		model.put("num", languages.size());
		this.user.setLoginUser(model, response);
		logger.debug("Exiting getSystemLanguagesAjax() of SettingsController");
		return new ModelAndView("ajax/langs_ajax", "model", model);
	}

    @RequestMapping(value = "/guidelocation.ajax",method = RequestMethod.GET)
    public ModelAndView getGuideLocationByLang(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.debug("Entering getGuideLocationByLang() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();
        String langCode = ServletRequestUtils.getStringParameter(request,
                "langCode", "en");
        String guideType = ServletRequestUtils.getStringParameter(request,
                "guideType", "admin");
        String guideURL = system.getGuideLocation(langCode, guideType);
        model.put("nav", "settings");
        model.put("guideURL", guideURL);
        this.user.setLoginUser(model, response);
        logger.debug("Exiting getGuideLocationByLang() of SettingsController");
        return new ModelAndView("ajax/guideloc_ajax", "model", model);
    }

	@RequestMapping(value = "/securedmaint/uploadsupportfile.ajax", method = RequestMethod.POST)
	public ModelAndView uploadSupportFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();

		String langCode = ServletRequestUtils.getStringParameter(request,
				"langCode", null);
		String guideType = ServletRequestUtils.getStringParameter(request,
				"guideType", null);

		if (StringUtils.isBlank(langCode) || StringUtils.isBlank(guideType)) {
			FieldError fe = new FieldError(
					"Maintenance",
					ms.getMessage("incorrect.langcode.guidetype", null, "", loc),
					ms.getMessage("incorrect.file.upload.params", null, "", loc));
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		// uploading
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile multipartFile = multipartRequest.getFile("guideUpload");
		File uploadedTmpFile = new File(uploadTempDirSuper
				+ multipartFile.getOriginalFilename());
        String originalFileName = multipartFile.getOriginalFilename();
        String guideURL = "";
        if (PortalUtils.isValidGuideFileName(originalFileName)) {
            try {
                multipartFile.transferTo(uploadedTmpFile);
                if (PortalUtils.isValidGuideFile(uploadedTmpFile)) {

                    int id = 0;
                    // Save the generated URL to the database
                     guideURL = request
                            .getRequestURL()
                            .toString()
                            .substring(0,
                                    request.getRequestURL().toString().indexOf("super"))
                            + "upload/" + langCode + "/" + URLEncoder.encode(uploadedTmpFile.getName(), "UTF-8").replaceAll("\\+","%20");

                    Configuration configuration = system.getConfiguration(guideType
                            + "guidelocation_" + langCode);
                    if (configuration == null) {
                        id = system.saveSystemConfig(guideType + "guidelocation_"
                                + langCode, guideURL, 0);
                    } else {
                        String fileName = configuration.getConfigurationValue()
                                .substring(
                                        configuration.getConfigurationValue()
                                                .lastIndexOf("/") + 1);
                        // Delete the existing file
                        FileUtils.deleteQuietly(new File(upload_dir+langCode+"/"+fileName));
                    }

                    // Copy the new file
                    PortalUtils.moveWebappsFileSecurely(uploadedTmpFile, new File(upload_dir + langCode + "/" + uploadedTmpFile.getName()));
                    PortalUtils.setWebappsDirectoryOwnerAndPermissions(upload_dir + langCode);

                    id = system.updateSystemConfig(guideType + "guidelocation_"
                            + langCode, guideURL);
                    if (id == 0) {
                        FieldError fe = new FieldError("Maintenance", ms.getMessage(
                                "file.upload.failed", null, "", loc), ms.getMessage(
                                "file.upload.failed.message", null, "", loc));
                        errors.add(fe);
                    }
                } else {
                    FileUtils.deleteQuietly(uploadedTmpFile);
                    logger.info("Invalid mime-type for guide upload");
                    FieldError fe = new FieldError("Maintenance", ms.getMessage(
                            "file.upload.failed", null, "", loc), "Invalid file.");
                    errors.add(fe);
                }
            } catch (Exception e) {
                FileUtils.deleteQuietly(uploadedTmpFile);
                logger.error("File Upload Exception", e);
                FieldError fe = new FieldError("Maintenance", ms.getMessage(
                        "file.upload.failed", null, "", loc), ms.getMessage(
                        "file.upload.failed.message", null, "", loc));
                errors.add(fe);
            }
        } else {
             logger.error("File Upload Exception: Un-supported file extention");
                FieldError fe = new FieldError("Maintenance", ms.getMessage(
                        "file.upload.failed", null, "", loc), ms.getMessage(
                        "file.upload.failed.message.invalid", null, "", loc));
                errors.add(fe);
            model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
        }


		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
			model.put("guideURL", guideURL);
		}
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/securedmaint/updatesupportfileurl.ajax", method = RequestMethod.POST)
	public ModelAndView updateSupportFileURL(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

		String langCode = ServletRequestUtils.getStringParameter(request,
				"langCode", null);
		String guideType = ServletRequestUtils.getStringParameter(request,
                "guideType", null);
		String guideURL = ServletRequestUtils.getStringParameter(request,
				"url", null);
		String urlValidated = ServletRequestUtils.getStringParameter(request,
                "urlValidated", null);

		if (urlValidated.equalsIgnoreCase("n")) {
			HttpClient httpClient = new HttpClient();
			HttpMethod headMethod = new HeadMethod(guideURL);
			int statusCode = 0;
			try {
				statusCode = httpClient.executeMethod(headMethod);
			} catch (IOException e) {
				logger.error("URL cannot be verified", e);
			}
			if (statusCode != HttpStatus.SC_OK) {
				FieldError fe = new FieldError("Maintenance", ms.getMessage(
						"help.file.url", null, "", loc), ms.getMessage(
						"help.file.url.verification.failed", null, "", loc));
				errors.add(fe);
				model.put("fields", errors);
				return new ModelAndView("ajax/result_ajax", "model", model);
			}
		}

		if (guideURL == null || guideType == null || langCode == null) {
			FieldError fe = new FieldError(
					"Maintenance",
					ms.getMessage("incorrect.langcode.guidetype", null, "", loc),
					ms.getMessage("incorrect.file.upload.params", null, "", loc));
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int id = 0;
		try {
			Configuration configuration = system.getConfiguration(guideType
					+ "guidelocation_" + langCode);
			if (configuration == null) {
				id = system.saveSystemConfig(guideType + "guidelocation_"
						+ langCode, guideURL, 0);
			} else {
				String fileName = configuration.getConfigurationValue()
						.substring(
								configuration.getConfigurationValue()
										.lastIndexOf("/"));
				FileUtils.deleteQuietly(new File(
                        upload_dir + langCode + "/"
                                + fileName));
				id = system.updateSystemConfig(guideType + "guidelocation_"
						+ langCode, guideURL);
			}
		} catch (Exception e) {
			FieldError fe = new FieldError("Maintenance", ms.getMessage(
					"url.upload.failed", null, "", loc), ms.getMessage(
					"url.upload.failed.message", null, "", loc));
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		if (id == 0) {
			FieldError fe = new FieldError("Maintenance", ms.getMessage(
					"url.upload.failed", null, "", loc), ms.getMessage(
					"url.upload.failed.message", null, "", loc));
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		model.put("success", Boolean.TRUE);
		model.put("guideURL", guideURL);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/mobiaccess.html", method = RequestMethod.GET)
	public ModelAndView getVidyoMobileAccessHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);
		//List<Integer> mobileAccess = tenant.getMobileAccessDetail();
		Configuration mobiledModeConfiguration = systemService.getConfiguration("MOBILE_LOGIN_MODE");

		if (mobiledModeConfiguration != null
    				&& StringUtils.isNotBlank(mobiledModeConfiguration.getConfigurationValue())) {
			model.put("mobileLogin",Integer.parseInt(mobiledModeConfiguration.getConfigurationValue()));
		}
		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
		return new ModelAndView("super/mobiaccess_html", "model", model);
	}
	
	@RequestMapping(value = "/savemobiaccess.ajax", method = RequestMethod.POST)
	public ModelAndView saveMobileAccessSettingsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering saveMobileAccessSettingsAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		String mobileAccessParam = ServletRequestUtils.getStringParameter(request, "mobileAccessGroup", null);
		if(mobileAccessParam != null) {
			
			int status = system.saveOrUpdateConfiguration(0, "MOBILE_LOGIN_MODE", mobileAccessParam);
			if (status > 0) {
				tenant.updateTenantMobileAccess(Integer.parseInt(mobileAccessParam));
			}
		}
		model.put("success", true);
		logger.debug("Exiting saveMobileAccessSettingsAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model",
				model);				
	}

	@RequestMapping(value = "/securedmaint/maintenance_audit_export_count.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceExportAuditCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        TransactionFilter tf = new TransactionFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(tf);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            tf = null;
        }

        int count = this.transaction.getTransactionHistoryCountForPeriod(tf);

        if (count > 65000) {
            FieldError fe = new FieldError("AuditExport",
                    ms.getMessage("export.exceeds.65000.record.limit.please.restrict.selected.range", null, "", loc),
                    ms.getMessage("export.exceeds.65000.record.limit.please.restrict.selected.range", null, "", loc));
            errors.add(fe);
        }

        if(errors.size()!=0){
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/securedmaint/maintenance_audit_export.ajax",method = RequestMethod.GET)
	public ModelAndView maintenanceExportAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		TransactionFilter tf = new TransactionFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(tf);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			tf = null;
		}

		List<TransactionHistory> list = this.transaction.getTransactionHistoryForPeriod(tf);
		long size = 0;
		for (TransactionHistory rec: list) {
			size += rec.toString().getBytes().length;
		}

		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setHeader("Content-Disposition", "attachment; filename=\"AuditLog.csv\"");
		response.addHeader("Content-Length", Long.toString(size));
		response.setContentType("text/csv");

		ServletOutputStream outPut = response.getOutputStream();

		for (TransactionHistory rec: list) {
			outPut.write(rec.toString().getBytes());
		}

		outPut.flush();
		outPut.close();

		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/highavailability.ajax", method = RequestMethod.GET)
	public ModelAndView getHighAvailabilityHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		//Populate the default values
		model.put("startTime", 0);
		model.put("endTime", 4);
		model.put("frequency", 3);		
		//Get DB Sync Schedule
		Configuration configuration = system.getDbSyncSchedule();
		if(configuration != null) {
			String val = configuration.getConfigurationValue();
			if(val.contains(";")) {
				String[] values = val.split(";");
				if(values.length == 3) {
					model.put("startTime", values[0]);
					model.put("endTime", values[1]);
					model.put("frequency", values[2]);
				}
				
			}
		}
		int maintStatus = system.isPortalOnMaintenance(null);
    	model.put("showRemoveMaintLink", false);
    	model.put("showToMaintLink", true);		
        if(maintStatus == 0) {
        	/*
        	 * If the status code is 0 - node is in maint mode,
        	 * If the status code is 1 - hot standby not enabled/not licensed
        	 * If the status code is 2 - hot standby enabled, but not in maintenance mode
        	 */
        	model.put("showRemoveMaintLink", true);
        	model.put("showToMaintLink", false);
        }
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));        
		this.user.setLoginUser(model, response);
		return new ModelAndView("ajax/highavailability_ajax", "model", model);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/securedmaint/dbsnapshot.ajax", method = RequestMethod.POST)
	public ModelAndView dbSnapshotAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("Entering syncDatabasesAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
		
		List<FieldError> errors = new ArrayList<FieldError>();
		// If Sync Immediately, invoke the backup script
		int statusFlag = 1; // Indicates failure
		statusFlag = system.syncDatabase("dummyparam");
		

		if (statusFlag != 0) {
			FieldError fe = new FieldError("SyncDatabase", "SyncDatabase",
					ms.getMessage("failed.to.sync.database", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0 || statusFlag != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}
		logger.debug("Exiting syncDatabasesAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/securedmaint/switchtomaintmode.ajax", method = RequestMethod.POST)
	public ModelAndView switchToMaintenanceModeAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("Entering switchToMaintenanceModeAjax() of SettingsController");
		String syncnowFlag = ServletRequestUtils.getStringParameter(request,
				"maint", null);		
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		int statusFlag = system.switchPortalToMaintenanceMode(syncnowFlag);
		logger.debug("statusFlag MaintenanceMode-->" + statusFlag);
		if (statusFlag != 0) {
			FieldError fe = new FieldError("SyncDatabase", "SyncDatabase",
					"failed to switch to maintenance mode");
			errors.add(fe);
		}

		if (errors.size() != 0 || statusFlag != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}
		logger.debug("Exiting switchToMaintenanceModeAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/securedmaint/forcestandby.ajax", method = RequestMethod.POST)
	public ModelAndView forcePortalToStandbyModeAjax(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 Locale loc = LocaleContextHolder.getLocale();
		logger.debug("Entering forcePortalTostandbyModeAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		//Check if the Standby is online
		ClusterInfo clusterInfo = system.getClusterInfo("");
		if(clusterInfo == null || !clusterInfo.getPeerStatus().equalsIgnoreCase("ONLINE")) {
			FieldError fe = new FieldError("Force Standby", "Force Standby",
					ms.getMessage("hot.stand.by.peer.offline", null, "", loc));
			errors.add(fe);			
		}else{
			int dbSyncStatus=-1;
			try{
				dbSyncStatus=Integer.parseInt(clusterInfo.getdBSyncStatus());
				logger.debug("Error Code returned from script-"+ clusterInfo.getdBSyncStatus());
			}catch(Exception e ){	
				logger.error("Error returned from script-"+ clusterInfo.getdBSyncStatus());
			}	
			if(!(dbSyncStatus==0 || dbSyncStatus==1)){

				//this mean db is not in sync.we cant do force stand by
				FieldError fe = new FieldError("Force Standby", "Force Standby",
						ms.getMessage("ha.errorcode.database.is.not.in.sync", null, "", loc));
						errors.add(fe);		
				
			}
		}
	
		int statusFlag = -1;
		if(errors.isEmpty()) {
			statusFlag = system.forcePortalToStandbyMode("dummyparam");
			logger.debug("statusFlag force standby-->" + statusFlag);
			if (statusFlag != 0) {
				FieldError fe = new FieldError("Force Standby", "Force Standby",
						"failed to switch to maintenance mode");
				errors.add(fe);
			}			
		}
		
		
		if (errors.size() != 0 || statusFlag != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}
		logger.debug("Exiting forcePortalTostandbyModeAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/securedmaint/importseckey.ajax", method = RequestMethod.POST)
	public ModelAndView saveHotStandBySecKeyAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("Entering saveHotStandBySecKeyAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		String key = ServletRequestUtils.getStringParameter(request, "seckey",
                "");
		if (key.equalsIgnoreCase("")) {
			FieldError fe = new FieldError("Import Security Key",
					"Import Security Key", "Invalid Security Key");
			errors.add(fe);
		}
		int statusCode = 999;
		if (errors.isEmpty()) {
			key = key.trim();
			key = key.replaceAll("\"", "");
			statusCode = system.saveHotStandbySecurityKey(StringUtils
					.chomp(key));
		}
		if (statusCode > 0) {
			FieldError fe = new FieldError("Import Security Key",
					"Import Security Key", "Failed to Generate Auth Key");
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}
		logger.debug("Exiting forcePortalTostandbyModeAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/clusterinfo.ajax",method = RequestMethod.GET)
	public ModelAndView getClusterInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("Entering getClusterInfo() of SettingsController");
		 Locale loc = LocaleContextHolder.getLocale();
		ClusterInfo clusterInfo = system.getClusterInfo("param");
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		if(clusterInfo == null) {
			clusterInfo = new ClusterInfo();
		}

		model.put("role.status", clusterInfo.getMyRole());
		model.put("shared.ip", clusterInfo.getClusterIP());
		model.put("server.ip", clusterInfo.getMyIP());
		model.put("network.status", clusterInfo.getMyRole());
		int maintStatus = system.isPortalOnMaintenance("");
		
        if(maintStatus == 0) {
        	/*
        	 * If the status code is 0 - node is in maint mode,
        	 * If the status code is 1 - hot standby not enabled/not licensed
        	 * If the status code is 2 - hot standby enabled, but not in maintenance mode
        	 */
        	model.put("preferred.primary", "MAINTENANCE");
        } else {
        	model.put("preferred.primary", clusterInfo.isPreferred() ? "Yes" : "No");
        }
		
//		Date lastDBSyncTime = null;
//		if(clusterInfo.getLastDBSync() != null && clusterInfo.getLastDBSync() != 0) {
//			lastDBSyncTime = new Date(clusterInfo.getLastDBSync() * 1000);
//			// Below 2 lines are commented based on bug 9906. As it is planned that they may be used in the future 
//			// they are commented.
////			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss a");
////			model.put("last.dbbackup", dateFormat.format(lastDBSyncTime));
//		}
		//Peer Status
		model.put("peer.status", clusterInfo.getPeerStatus());
		model.put("peer.server.ip", clusterInfo.getPeerIP());
		String syncStatus = null;
		int dbSyncStatus=-1;
		try{
			dbSyncStatus=Integer.parseInt(clusterInfo.getdBSyncStatus());
			logger.debug("Error Code returned from script-"+ clusterInfo.getdBSyncStatus());
		}catch(Exception e ){	
			logger.error("Error returned from script-"+ clusterInfo.getdBSyncStatus());
		}			
		if(dbSyncStatus==0 || dbSyncStatus==1){
				syncStatus=ms.getMessage("ha.errorcode.insync", null, "", loc);
		}
		else if(dbSyncStatus > 1 && dbSyncStatus < 99999){
			syncStatus=ms.getMessage("ha.errorcode.syncinprogress", null, "", loc);
		}
		else if (dbSyncStatus==99999){
				syncStatus=ms.getMessage("ha.statuscode.file.system.sync.in.progress", null, "", loc);
		}
		else if(dbSyncStatus==-1000){
			syncStatus=ms.getMessage("ha.errorcode.newdb.snapshot.required", null, "", loc);
			//for displaying special href link in ui
			syncStatus=dbSyncStatus+":-:"+syncStatus;
		}else if(dbSyncStatus==-1001){
			syncStatus=ms.getMessage("ha.errorcode.snapshot.database.version.mismatch", null, "", loc);
		}else if(dbSyncStatus==-1100){
			syncStatus=ms.getMessage("ha.errorcode.snapshot.failedto.establish.tunnel", null, "", loc);
		}else if(dbSyncStatus==-1101){
			syncStatus=ms.getMessage("ha.errorcode.unableto.connect.peer", null, "", loc);
		}else if(dbSyncStatus==-1200){
			syncStatus=ms.getMessage("ha.errorcode.unableto.sync.with.peer.database", null, "", loc);
		}
		else{
			 syncStatus = ms.getMessage("ha.errorcode.unknown", null, "", loc);
			 logger.error("Error Code returned from script-"+ clusterInfo.getdBSyncStatus());
		}
		
		
		
		model.put("label.dbsync",syncStatus);
		
				
		logger.debug("Exiting getClusterInfo() of SettingsController");
		return new ModelAndView("ajax/clusterinfo_ajax", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/superaccounts.html",method = RequestMethod.GET)
	public ModelAndView getSuperAccountsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);

		return new ModelAndView("super/superaccounts_html", "model", model);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/superaccounts.ajax",method = RequestMethod.POST)
	public ModelAndView getSuperAccountsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberFilter filter = new MemberFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			filter = null;
		}

        // JSON format for filtering - new UI
		if(filter != null) {
	        final String jsonFilter = filter.getFilter();
	        if(jsonFilter != null && jsonFilter.contains("property")) {
	            final ObjectNode[] nodes = new ObjectMapper().readValue(jsonFilter, ObjectNode[].class);
	            for(ObjectNode node : nodes) {
	            	if(node.get("property").asText().equals("memberName") && !StringUtils.isEmpty(node.get("value").asText())) {
	            		filter.setMemberName(node.get("value").asText());
	            	}
	            	if(node.get("property").asText().equals("enable") && !StringUtils.isEmpty(node.get("value").asText())) {
	            		filter.setUserStatus(node.get("value").asText());
	            	}
	            }
	        }
		}

		if(filter != null && filter.getSort() != null && filter.getSort().contains("property")) {
			final ObjectNode[] nodes = new ObjectMapper().readValue(filter.getSort(), ObjectNode[].class);
			for(ObjectNode node : nodes) {
	       		filter.setSort(node.get("property").asText());
	       		filter.setDir(node.get("direction").asText());
			}
		} else {
			String sort = ServletRequestUtils.getStringParameter(request, "sort");
			if(sort != null && sort.contains("property")) {
				final ObjectNode[] nodes = new ObjectMapper().readValue(sort, ObjectNode[].class);
				for(ObjectNode node : nodes) {
		       		filter.setSort(node.get("property").asText());
		       		filter.setDir(node.get("direction").asText());
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<Member> list = memberService.getSupers(filter);
		model.put("list", list);
		Long num = memberService.getCountSupers(filter);
		model.put("num", num);

		for(Member member: list){
			member.setMemberName(member.getMemberName()
				.replace("& #40;".subSequence(0, "& #40;".length()), "(")
				.replace("& #41;".subSequence(0, "& #41;".length()), ")")
				.replace("& lt;".subSequence(0, "& lt;".length()), "<")
				.replace("& gt;".subSequence(0, "& gt;".length()), ">")
				.replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ")
				.replace("& #39;".subSequence(0, "& #39;".length()), "'")
			);
		}

		return new ModelAndView("ajax/superaccounts_ajax", "model", model);
	}
	
	@RequestMapping(value = "/validatesuperpassword.ajax",method = RequestMethod.POST)
    public ModelAndView validateSuperPasswordAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale loc = LocaleContextHolder.getLocale();

        String password = ServletRequestUtils.getStringParameter(request, "value", "");
		//int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
        int memberID = this.user.getLoginUser().getMemberID();
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

		if (!password.equalsIgnoreCase("")) {
			String currentPassword = member.getMemberPassword(memberID);
			// Handle both old password and new password scheme
			if (memberID != 0
					&& !((!currentPassword.contains(":") && currentPassword
							.equalsIgnoreCase(SHA1.enc(password))) || (currentPassword
							.contains(":") && PasswordHash.validatePassword(password, currentPassword)))) {
				FieldError fe = new FieldError("oldpassword", "oldpassword",
						ms.getMessage("incorrect.current.password", null, "", loc));
				errors.add(fe);
			}
		}

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/json_result_ajax", "model", model);
    }
    
	@RequestMapping(value = "/deletesuper.ajax",method = RequestMethod.POST)
	public ModelAndView deleteSuperAccountAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		Map<String, Object> model = new HashMap<String, Object>();
		int removedCount = 0;
		Member deletingSuper = memberService.getSuper(memberID);
		List<SessionInformation> sessionInfoList = null;
		if(deletingSuper != null) {
			List<Object> principals = sessionRegistry.getAllPrincipals();
			for(Object principal : principals) {
				if(((VidyoUserDetails)principal).getUsername().equals(deletingSuper.getUsername())) {
					sessionInfoList = sessionRegistry.getAllSessions(principal, false);
					break;
				}
			}
			try {
				removedCount = memberService.deleteSuper(memberID);
			} catch (AccessRestrictedException e) {
				// It is not necessary to do anything. Error message is already logged in the service method.
			}
		}

		if (removedCount > 0) {
			if(sessionInfoList != null) {
				for(SessionInformation sessionInfo : sessionInfoList) {
					logger.warn("Session " + sessionInfo.getSessionId() + " has beed expired.");
					sessionInfo.expireNow();
				}
			}
			sendSuperDeleteMessageMQtopic(deletingSuper.getUsername());
			model.put("success", Boolean.TRUE);
		} else {
			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	private void sendSuperDeleteMessageMQtopic(final String username) {
		try {
			jmsTemplate.send(superDeleteMessageMQtopic.getTopicName(), new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(username);
				}
			});
			logger.warn("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . Super delete message is fired");
		} catch (Exception e) {
			logger.error("Cannot send super delete: (" + username + ") message to MQ", e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getuserattribute.ajax", method = RequestMethod.GET)
	public ModelAndView getUserAttributeAdmin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Configuration conf= this.system.getConfiguration(UPLOAD_USER_IMAGE);
		boolean enableUserImgeUpldGlobal=false;
		 if(conf!=null){
			if( conf.getConfigurationValue()!=null && "1".equalsIgnoreCase(conf.getConfigurationValue()))
			enableUserImgeUpldGlobal=true;
		 }
			TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(TenantContext.getTenantId());
					Map<String, Object> model = new HashMap<String, Object>();

			model.put("enableUserImage", tenantConfig.getUserImage());
			model.put("enableUserImageUpload", tenantConfig.getUploadUserImage());
			model.put("enableUserImgeUpldGlobal", enableUserImgeUpldGlobal);
	     			
			
			return new ModelAndView("ajax/getuserattribute_ajax", "model", model);
}
	@RequestMapping(value = "/userattribute.html", method = RequestMethod.GET)
	public ModelAndView getUserAttributeHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<String> configNames=new ArrayList<String>();
		configNames.add(USER_IMAGE);
		configNames.add(UPLOAD_USER_IMAGE);
		configNames.add(MAX_USER_IMAGE_SIZE_KB);
		List<Configuration> confList = system.getConfigurations(configNames);
		if(!confList.isEmpty()){
			for(Configuration conf:confList){
				if(conf!=null){
					switch (conf.getConfigurationName()) {
					case USER_IMAGE:
						model.put("enableUserImage", conf.getConfigurationValue());
								
						break;
					case UPLOAD_USER_IMAGE:
						model.put("enableUserImageUpload", conf.getConfigurationValue());
						break;
					case MAX_USER_IMAGE_SIZE_KB:
						model.put("maxImageSize", conf.getConfigurationValue());
						break;
					
				
					}
				}
			}
		}
		return new ModelAndView("super/user_attributes_html", "model", model);
	}
	
	@RequestMapping(value = "/customrole.html", method = RequestMethod.GET)
	public ModelAndView getCustomRoleAttributeHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Configuration conf= this.system.getConfiguration(ENDPOINTBEHAVIOR);
		int customRole=0;
		 
		if(conf!=null){
			if( conf.getConfigurationValue()!=null && "1".equalsIgnoreCase(conf.getConfigurationValue()))
				customRole=1;
		}
			
		model.put("customRole", customRole);
	    
		return new ModelAndView("super/custom_role_attributes_html", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/scheduledroom.html", method = RequestMethod.GET)
	public ModelAndView getScheduledRoomHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<String> configNames=new ArrayList<String>();
		configNames.add(SCHEDULED_ROOM_PREFIX);
		configNames.add(CREATE_PUBLIC_ROOM_FLAG);
		configNames.add(MAX_CREATE_PUBLIC_ROOM_USER);
		configNames.add(SELECTED_MAX_ROOM_EXT_LENGTH);
		List<Configuration> confList = system.getConfigurations(configNames);
		if(!confList.isEmpty()){
			for(Configuration conf:confList){
				if(conf!=null){
					switch (conf.getConfigurationName()) {
					case SCHEDULED_ROOM_PREFIX:
						model.put("prefix", conf.getConfigurationValue());
						//existing logic
						model.put("schRoomEnabled",  conf.getConfigurationValue()==null?false:!conf.getConfigurationValue().isEmpty());
						break;
					case CREATE_PUBLIC_ROOM_FLAG:
						model.put("publicRoomEnabledGlobal", conf.getConfigurationValue());
						break;
					case MAX_CREATE_PUBLIC_ROOM_USER:
						model.put("publicRoomMaxRoomNoPerUser", conf.getConfigurationValue());
						break;
					case SELECTED_MAX_ROOM_EXT_LENGTH:
						model.put("publicRoomMinNoExt", conf.getConfigurationValue());
						break;
				
					}
				}
			}
		}
	
		// Get default tenant prefix
		Tenant defaultTenant = this.tenant.getTenant(1);
		model.put("defaultTenantPrefix", defaultTenant.getTenantPrefix());

		return new ModelAndView("super/scheduledroom_html", "model", model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/scheduledroom.ajax", method = RequestMethod.GET)
	public ModelAndView getScheduledRoomAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Configuration conf = system.getConfiguration(SCHEDULED_ROOM_PREFIX);
		String prefix = "";
		if(conf != null) {
			prefix = conf.getConfigurationValue();
		}

        model.put("prefix", prefix);
        model.put("isglobalscheduleroomenabled", !prefix.isEmpty());
		return new ModelAndView("ajax/scheduledroom_ajax", "model", model);
	}
	
	@RequestMapping(value = "/saveUserAttributesAdmin.ajax", method = RequestMethod.POST)
	public ModelAndView saveUserAttributesAdmin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveUserAttributesAdmin() of SettingsController");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		int enableUserImage = ServletRequestUtils.getIntParameter(request, "enableUserImage", 0);
		int enableUserImageUpload = ServletRequestUtils.getIntParameter(request, "enableUserImageUpload", 0);

		Configuration configuration = system.getConfiguration(USER_IMAGE);

		if (configuration == null || configuration.getConfigurationValue() == null
				|| configuration.getConfigurationValue().trim().isEmpty()
				|| configuration.getConfigurationValue().equalsIgnoreCase("0")) {
			// Don't allow any changes to be done if super disabled scheduled
			// room at system level
			FieldError fe = new FieldError("Thumbnail", "Thumbnail", "Thumbnail functionality not enabled by super");
			errors.add(fe);
			
		} else {
			TenantConfiguration config = tenant.getTenantConfiguration(TenantContext.getTenantId());
			config.setUserImage(enableUserImage);
			config.setUploadUserImage(enableUserImageUpload);
			int status = tenant.updateTenantConfiguration(config);
			if (status == 0) {
				// TODO need to give correct error message
				FieldError fe = new FieldError("UserAttributes", "UserAttributes",
						ms.getMessage("failed.to.save.room.attributes", null, "", loc));
				errors.add(fe);
				system.auditLogTransaction("TENANT LEVEL USER IMAGE ATTRIBUTES UPDATE", "User Image: " + enableUserImage + "User Image Upload" + enableUserImageUpload, FAILURE);
			
			}else{
                system.auditLogTransaction("TENANT LEVEL USER IMAGE ATTRIBUTES UPDATE", "User Image:" + enableUserImage + ",User Image Upload:" + enableUserImageUpload, SUCCESS);
				member.updateAllMembersLastModifiedDateExt(TenantContext.getTenantId(),  null);
			}

			logger.debug("Exiting saveUserAttributesAdmin() of SettingsController");
		}

		// TODO n
		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	@RequestMapping(value = "/savecustomrole.ajax", method = RequestMethod.POST)
	public ModelAndView saveCustomRoleAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveCustomRoleAjax() of SettingsController");
		}
		int status=0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		String customRole = ServletRequestUtils.getStringParameter(request, "customRole", null);
		if(customRole!=null){
			status=system.updateSystemConfig(ENDPOINTBEHAVIOR, customRole);		
			if (status == 0) {			
			FieldError fe = new FieldError("CustomRoles", "CustomRoles",
					ms.getMessage("Failed to save custom role", null, "", loc));
			errors.add(fe);			
			}		
			if("0".equalsIgnoreCase(customRole)){
				status=tenant.updateTenantAllCustomRole(0);
				if (status == 0) {			
					FieldError fe = new FieldError("CustomRoles", "CustomRoles",
					ms.getMessage("Failed to disable all tenant's custom role feature", null, "", loc));
					errors.add(fe);
			
				}	
			}
		}else{
			FieldError fe = new FieldError("CustomRoles", "CustomRoles",
					ms.getMessage("Input Value is null. No action taken", null, "", loc));
					errors.add(fe);
		}
	 if (errors.size() != 0) {
             model.put("fields", errors);
             model.put("success", Boolean.FALSE);
         system.auditLogTransaction("SYSTEM LEVEL Custom Role UPDATE", customRole, FAILURE);
         }else{
      	   model.put("success", Boolean.TRUE);
         system.auditLogTransaction("SYSTEM LEVEL Custom Role UPDATE", customRole, SUCCESS);
         }

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	@RequestMapping(value = "/saveuserattribute.ajax", method = RequestMethod.POST)
	public ModelAndView saveUserAttributeAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveUserAttributeAjax() of SettingsController");
		}
		StringBuffer param=new StringBuffer();
		int status=0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		String enableUserImage = ServletRequestUtils.getStringParameter(request, "enableUserImage", null);
		String enableUserImageUpload = ServletRequestUtils.getStringParameter(request, "enableUserImageUpload", null);
		String maxImageSize = ServletRequestUtils.getStringParameter(request, "maxImageSize",
				null);
		
		if(enableUserImage!=null){
		status=system.updateSystemConfig(USER_IMAGE, enableUserImage.trim());
		param.append("User Image:"+enableUserImage);
		if (status == 0) {
			// TODO need to give correct error message
			FieldError fe = new FieldError("UserAttributes", "UserAttributes",
					ms.getMessage("failed.to.save.user.image.attribute", null, "", loc));
			errors.add(fe);
			
		}
		}
		
		if(enableUserImageUpload!=null){
			param.append(",Upload User Image:"+enableUserImageUpload);
		status=	 system.updateSystemConfig(UPLOAD_USER_IMAGE, enableUserImageUpload.trim());
		 if (status == 0) {
				// TODO need to give correct error message
				FieldError fe = new FieldError("UserAttributes", "UserAttributes",
						ms.getMessage("failed.to.save.user.image.attribute", null, "", loc));
				errors.add(fe);
			
			
		}
		}
		if(!(enableUserImage==null && enableUserImageUpload==null)){
			status=tenant.updateTenantCnfUserAttributes(enableUserImage,enableUserImageUpload);
			if (status == 0) {
					// TODO need to give correct error message
					FieldError fe = new FieldError("UserAttributes", "UserAttributes",
							ms.getMessage("failed.to.reset.all.tenants.attributes", null, "", loc));
					errors.add(fe);
				
				
			}
		}
		if(maxImageSize!=null){
			param.append(",Maximum image size:"+maxImageSize);
			status=system.updateSystemConfig(MAX_USER_IMAGE_SIZE_KB, maxImageSize.trim());
			 if (status == 0) {
					// TODO need to give correct error message
					FieldError fe = new FieldError("UserAttributes", "UserAttributes",
							ms.getMessage("failed.to.save.user.image.attribute", null, "", loc));
					errors.add(fe);
			 }
			 
		}
								
				
		
			 	
		
			
		 
	 if (errors.size() != 0) {
             model.put("fields", errors);
             model.put("success", Boolean.FALSE);
         	system.auditLogTransaction("SYSTEM LEVEL USER IMAGE ATTRIBUTES UPDATE", param.toString(), FAILURE);
         }else{
      	   model.put("success", Boolean.TRUE);


         system.auditLogTransaction("SYSTEM LEVEL USER IMAGE ATTRIBUTES UPDATE", param.toString(), SUCCESS);
			

         }
		
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savescheduledroom.ajax", method = RequestMethod.POST)
	public ModelAndView saveScheduledRoomPrefixAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering saveScheduledRoomAjax() of SettingsController");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		String prefix = ServletRequestUtils.getStringParameter(request, "ScheduledRoomPrefix", null);
		String schRoomEnabled = ServletRequestUtils.getStringParameter(request, "ScheduledRoomEnabled", null);
		String publicRoomEnabledGlobal = ServletRequestUtils.getStringParameter(request, "publicRoomEnabledGlobal",
                null);
		String publicRoomMaxRoomNoPerUser = ServletRequestUtils.getStringParameter(request,
				"publicRoomMaxRoomNoPerUser", null);
		String publicRoomMinNoExt = ServletRequestUtils.getStringParameter(request, "publicRoomMinNoExt", null);

		// in case of any UI bug or something the value not coming from UI to
		// server, it will delete all schroom so adding a null check.
		if (schRoomEnabled != null) {
			if (!schRoomEnabled.equalsIgnoreCase("1")) {
				// Delete the existing rooms and delete the prefix
				int id = updateSchRoomPrefix(null);
				if (id <= 0) {
					FieldError fe = new FieldError("ScheduledRoom",
							ms.getMessage("manage.scheduled.room", null, "", loc),
							ms.getMessage("scheduled.room.update.failed", null, "", loc));
					errors.add(fe);
					
				
				}
				// If there is no old config, just return success from here
				// model.put("success", Boolean.TRUE);
				// return new ModelAndView("ajax/result_ajax", "model", model);
			} else {
				// Scheduled Room enabled case
				boolean invalidPrefix = false;
				if (prefix == null || prefix.trim().length() > 3) {
					invalidPrefix = true;
				} else {
					try {
						Integer.parseInt(prefix.trim());
					} catch (Exception e) {
						invalidPrefix = true;
					}
				}
				if (invalidPrefix) {
					logger.error("Invalid value for Scheduled Room Prefix {}", prefix);
					FieldError fe = new FieldError("ScheduledRoom",
							ms.getMessage("manage.scheduled.room", null, "", loc),
							ms.getMessage("super.scheduled.room.prefix.invalid", null, "", loc));
					errors.add(fe);			
					
				} else {
					// Check if it belongs to any tenant - prevent hacking
					// through Ajax
					// calls
					boolean prefixInUse = tenant.isTenantPrefixExistForTenantPrefixLike(prefix);
					if (!prefix.equalsIgnoreCase("") && prefixInUse) {
						FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat
								.format(ms.getMessage("duplicate.schroom.ext.prefix", null, "", loc), prefix));
						errors.add(fe);		
						
					} else {
						// Check if the prefix exists for default tenant 
						int used = roomService.isPrefixExistingInDefaultTenant(prefix);
						if (used > 0) {
							FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat
									.format(ms.getMessage("duplicate.schroom.ext.prefix", null, "", loc), prefix));
							errors.add(fe);		
							
						} else {
						// Removing the check for the default tenant prefix
						// Get default tenant prefix
						/*String defaultTenantPrefix = this.tenant.getTenant(1).getTenantPrefix();
						
						if (defaultTenantPrefix == null || defaultTenantPrefix.trim().isEmpty()) {
							String message = "scheduled.room.update.failed";
							if (defaultTenantPrefix == null || defaultTenantPrefix.isEmpty()) {
								message = "assign.prefix.to.default.tenant";
							}
							FieldError fe = new FieldError("ScheduledRoom",
									ms.getMessage("manage.scheduled.room", null, "", loc),
									ms.getMessage(message, null, "", loc));
							errors.add(fe);
						
						
						}else{*/
						
							updateSchRoomPrefix(prefix);
								if (logger.isDebugEnabled()) {
								logger.debug("Exiting saveScheduledRoomAjax() of SettingsController");
							}
						}
					}
				}
			}
		}

		updatePublicRoomSettings(publicRoomEnabledGlobal, publicRoomMaxRoomNoPerUser, publicRoomMinNoExt);
		 if (errors.size() != 0) {
               model.put("fields", errors);
               model.put("success", Boolean.FALSE);
           }else{
        	   model.put("success", Boolean.TRUE);
           }
		
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
	
	private void updatePublicRoomSettings(String publicRoomEnabledGlobal, String publicRoomMaxRoomNoPerUser,String publicRoomMinNoExt) {
		boolean updateFlag=false;
		system.updateSystemConfig(CREATE_PUBLIC_ROOM_FLAG, publicRoomEnabledGlobal);
		if(publicRoomEnabledGlobal!=null){
			if(publicRoomEnabledGlobal.equalsIgnoreCase("0")){
				//putting back the defaults value.
				system.updateSystemConfig(MAX_CREATE_PUBLIC_ROOM_USER, "5");
				system.updateSystemConfig(SELECTED_MAX_ROOM_EXT_LENGTH, "12");
				//need to disable all the public-room-creation-by-user option for all tenants
				updateFlag=true;
				
				
				
			}
			
		}
		
		if(publicRoomMaxRoomNoPerUser!=null){
			system.updateSystemConfig(MAX_CREATE_PUBLIC_ROOM_USER, publicRoomMaxRoomNoPerUser);
			updateFlag=true;
		}
		if(publicRoomMinNoExt!=null){
			system.updateSystemConfig(SELECTED_MAX_ROOM_EXT_LENGTH, publicRoomMinNoExt);
			updateFlag=true;
		}
		if(updateFlag){
			int status=tenant.resetTenantConfigPblcRoomSttngs(publicRoomEnabledGlobal,publicRoomMaxRoomNoPerUser);
			logger.debug("Exiting updatePublicRoomSettings() of SettingsController after updatiion with status " + status);
		}
		
		
	}
	
	@RequestMapping(value = "/cdraccess.html", method = RequestMethod.GET)
	public ModelAndView getAdminCdrAccessHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");

		String cdrFormat = this.system.getCDRformat();
		model.put("CDRFormat", cdrFormat);

		Tenant curTenant = tenant.getTenantForRequest();
		model.put("tenantName", curTenant.getTenantName());

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));		

		return new ModelAndView("admin/cdr_access_html", "model", model);
	}

	@RequestMapping(value = "/securedmaint/maintenance_diagnostics_run.ajax",method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsRun(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        if (!diagnosticsService.runDiagnostics()) {
            List<FieldError> errors = new ArrayList<FieldError>();
            FieldError fe = new FieldError("Run", "Diagnostics", "Error running diagnostics.");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", false);
        }
        model.put("success", true);
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_diagnostics_list.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<DiagnosticReport> reports =  diagnosticsService.getDiagnosticReports();
        model.put("num", reports.size());
        model.put("reports", reports);
        return new ModelAndView("ajax/diagnostics_list_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_diagnostics_view.ajax", method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsView(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String file = request.getParameter(("f"));
        String contents = diagnosticsService.getDiagnosticReport(file);
        model.put("contents", contents.replace("\n", "<br />").
                replace("STATUS=ERROR", "<b style='color: red'>STATUS=ERROR</b>").
                replace("STATUS=FAIL", "<b style='color: red'>STATUS=FAIL</b>" ).
                replace("STATUS=WARNING", "<b style='color: red'>STATUS=WARNING</b>" ).
                replace("STATUS=PASS", "<b style='color: green'>STATUS=PASS</b>")
        );
        return new ModelAndView("ajax/diagnostics_report_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_diagnostics_download.ajax",method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String file = request.getParameter(("f"));
        String contents = diagnosticsService.getDiagnosticReport(file);

        // send download
        response.reset();

        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
        response.setHeader("Content-Transfer-Encoding", "quoted-printable");
        response.addHeader("Content-Length", "" + contents.length());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        IOUtils.copy(IOUtils.toInputStream(contents), out);

        out.flush();

        return null;
    }

    @RequestMapping(value = "/vidyoweb.html",method = RequestMethod.GET)
    public ModelAndView getVidyoWebHtml(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);

		model.put("available", system.isVidyoWebAvailable());
        model.put("enabled", system.isVidyoWebEnabledBySuper());

        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);

        return new ModelAndView("super/vidyoweb_html", "model", model);
    }

     /***********************************
     * BANNERS GTEC requiremengts configurable Login and Wecome Banners
     * Section START
     */

    @RequestMapping(value = "/banners.ajax",method = RequestMethod.GET)
    public ModelAndView getBannersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

        if (logger.isDebugEnabled()) {
			logger.debug("getBannersAjax ajax request came in");
		}

		Banners banners = this.system.getBannersInfo();

		model.put("loginBanner", banners.getLoginBanner());
        model.put("welcomeBanner", banners.getWelcomeBanner());
        model.put("showLoginBanner", banners.getShowLoginBanner());
        model.put("showWelcomeBanner", banners.getShowWelcomeBanner());

		return new ModelAndView("ajax/banners_ajax", "model", model);
	}

    @RequestMapping(value = "/savebanners.ajax",method = RequestMethod.POST)
    public ModelAndView saveBannersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean showLoginBanner = ServletRequestUtils.getBooleanParameter(request, "showLoginBanner", false);

        boolean showWelcomeBanner = ServletRequestUtils.getBooleanParameter(request, "showWelcomeBanner", false);

        String loginBanner = ServletRequestUtils.getStringParameter(request, "loginBanner", null);
        CleanResults cleanResults = null;
        if(null != loginBanner){
            cleanResults = AntiSamyHtmlCleaner.cleanHtml(loginBanner);
            loginBanner = cleanResults.getCleanHTML();
        }
        String welcomeBanner = ServletRequestUtils.getStringParameter(request, "welcomeBanner", null);
         if(null != welcomeBanner){
            cleanResults = AntiSamyHtmlCleaner.cleanHtml(welcomeBanner);
            welcomeBanner = cleanResults.getCleanHTML();
         }

        Banners  banners = new Banners();
        banners.setLoginBanner(loginBanner);
        banners.setShowLoginBanner(showLoginBanner);
        banners.setShowWelcomeBanner(showWelcomeBanner);
        banners.setWelcomeBanner(welcomeBanner);

		Map<String, Object> model = new HashMap<String, Object>();

		Boolean status = this.system.saveBanners(banners);

		model.put("success", status);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/securedmaint/getsyslogconfig.ajax", method = RequestMethod.POST)
    public ModelAndView getSyslogConfigAjax(HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        logger.debug("Entering getSyslogConfigAjax() of SettingsController");
        Map<String, String> model = null;
        model = system.getSyslogConfigDetails("syslog");
        logger.debug("Exiting getSyslogConfigAjax() of SettingsController");
        return new ModelAndView("ajax/syslogconfig_ajax", "model", model);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/securedmaint/savesyslogconfig.ajax",method = RequestMethod.POST)
    public ModelAndView saveSyslogConfigAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering saveSyslogConfigAjax() of SettingsController");
        String sysLogConfig = ServletRequestUtils.getStringParameter(request,
                "remote_logging", null);
        String stunnelConfig = ServletRequestUtils.getStringParameter(
                request, "stunnel", null);
        String ipaddress = ServletRequestUtils.getStringParameter(request,
                "ip_address", null);
        String port = ServletRequestUtils.getStringParameter(request,
                "port", null);

        Map<String, Object> model = new HashMap<String, Object>();
        if (sysLogConfig == null || "".equals(sysLogConfig.trim())) {
            sysLogConfig = "off";
        }

        if (stunnelConfig == null || "".equals(stunnelConfig.trim())) {
            stunnelConfig = "off";
        }

        List<FieldError> errors = new ArrayList<FieldError>();
        if(sysLogConfig.equalsIgnoreCase("on") && (ipaddress == null || port == null)) {
            model.put("success", Boolean.FALSE);
            FieldError fe = new FieldError("Sys Log Configuration", "Sys Log Config Fields", "Input Error");
            errors.add(fe);
            model.put("errors", errors);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        int status = system
                .saveSyslogConfig(sysLogConfig, stunnelConfig, ipaddress, port);
        if (status != 0) {
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        logger.debug("Exiting saveSyslogConfigAjax() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/savepasswordconfigvals.ajax",method = RequestMethod.POST)
    public ModelAndView savePasswordConfigVals(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering savePasswordConfigVals() of SettingsController");
        Locale loc = LocaleContextHolder.getLocale();
        String expiryDays = ServletRequestUtils.getStringParameter(request, "expieryDays", null);
        String inactiveDays = ServletRequestUtils.getStringParameter(request, "inactiveDays", null);
        String failCount = ServletRequestUtils.getStringParameter(request,"failCount", null);
        int sessionExpPeriod = ServletRequestUtils.getIntParameter(request, "sessionExpPeriod", 0);
        
        String passwordComplexity = null;
        if(!VendorUtils.isDISA()) {
        	passwordComplexity = ServletRequestUtils.getStringParameter(request, "passwordComplexity", null);
        }
        
        Map<String, Object> model = new HashMap<String, Object>();
        int status = 0;
        if(null != expiryDays && null!= inactiveDays && null != failCount) {
            status = system.updatePasswordValidityDaysConfig(expiryDays);
            status = system.updateInactiveDaysLimitConfig(inactiveDays);
            status = system.updateLoginFalureCountConfig(failCount);
            // invoked by Super
            if(sessionExpPeriod > 0) {
            	status = system.updateSessionExpirationPeriodConfig(sessionExpPeriod);	
            }            
            
            if(!VendorUtils.isDISA()) {
                if(null != passwordComplexity && (passwordComplexity.equalsIgnoreCase("on") || passwordComplexity.equalsIgnoreCase("true"))) {
                    Configuration conf = system.getUserPasswordRule();
                    String passwordRuleName = null;
                    boolean isDisaRule = false;
                    if(null != conf) {
                        passwordRuleName = conf.getConfigurationValue();
                    }
                    if(passwordRuleName != null && passwordRuleName.equalsIgnoreCase("DISA")) {
                        isDisaRule = true;
                    }
                    
                    status = system.updateUserPasswordRule("DISA");
                    
                    // Clear PasswordHistory to force password change for the case password complexity off->on
                    if(!isDisaRule) {
                        if(memberPasswordHistoryService.cleanMemberPasswordHistory()) {
                            status = 1;
                        } else {
                            status = 0;
                        }
                    }
                } else {
                    status = system.updateUserPasswordRule("NO_PASSWORD_RULE");
                }
            }
        }

        boolean isSuperForgetPasswordDisabled = ServletRequestUtils.getBooleanParameter(request, "disableForgetPasswordSuper", false);

        if (isSuperForgetPasswordDisabled) {
            system.disableSuperPasswordRecovery();
        } else {
            system.enableSuperPasswordRecovery();
        }

        List<FieldError> errors = new ArrayList<FieldError>();

        int minPINLength = ServletRequestUtils.getIntParameter(request, "minPINLength", 3);
        if (minPINLength < SystemServiceImpl.PIN_MIN || minPINLength > SystemServiceImpl.PIN_MAX) {
            String[] args = {""+SystemServiceImpl.PIN_MIN, ""+SystemServiceImpl.PIN_MAX};
            FieldError fe = new FieldError("minPINLength", "minPINLength", ms.getMessage("allowed.range.is.between.3.12.please.modify.the.value.and.try.again", args, "", loc));
            errors.add(fe);
            model.put("success", Boolean.FALSE);
            model.put("fields", errors);
            return new ModelAndView("ajax/result_ajax", "model", model);
        } else {
            system.setMinimumPINLengthSuper(minPINLength);
        }

        if (status > 0) {
            model.put("success", true);
        } else {
            model.put("success", false);
        }

        logger.debug("Exiting savePasswordConfigVals() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/getpasswordconfigvals.ajax",method = RequestMethod.GET)
    public ModelAndView getPasswordConfigVals(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String expieryDays = null;
        String inactiveDays = null;
        String failCount = null;
        Configuration conf = system.getPasswordValidityDaysConfig();
        if(null != conf){
           expieryDays = conf.getConfigurationValue();
        }
        if( null != expieryDays){
            model.put("expieryDays", expieryDays);
        }
        conf = system.getInactiveDaysLimitConfig();
        if(null != conf){
           inactiveDays = conf.getConfigurationValue();
        }
        if( null != inactiveDays){
            model.put("inactiveDays", inactiveDays);
        }
        conf = system.getLoginFalureCountConfig();
        if(null != conf){
           failCount = conf.getConfigurationValue();
        }
        if( null != failCount){
            model.put("failCount", failCount);
        }
        if(!VendorUtils.isDISA()) {
        	String passwordRuleName = null;
        	conf = system.getUserPasswordRule();
        	if(null != conf) {
        		passwordRuleName = conf.getConfigurationValue();
        	}
        	if(passwordRuleName != null && passwordRuleName.equalsIgnoreCase("DISA")) {
        		model.put("passwordComplexity", true);
        	} else {
        		model.put("passwordComplexity", false);
        	}
        }
        if (system.isSuperPasswordRecoveryDisabled()) {
            model.put("disableForgetPasswordSuper", true);
        } else {
            model.put("disableForgetPasswordSuper", false);
        }

        model.put("minPINLength", "" + system.getMinimumPINLengthSuper());
		model.put("sessionExpPeriod",
                system.getConfiguration("SESSION_EXP_PERIOD")
                        .getConfigurationValue());

        return new ModelAndView("ajax/password_configs_ajax", "model", model);
    }

    @RequestMapping(value = "/savepasswordconfigvalsadmin.ajax",method = RequestMethod.POST)
    public ModelAndView savePasswordConfigValsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering savePasswordConfigValsAdmin() of SettingsController");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int minPINLength = ServletRequestUtils.getIntParameter(request, "minPINLength", 3);
        if (minPINLength < system.getMinimumPINLengthSuper() || minPINLength > SystemServiceImpl.PIN_MAX) {
            String[] args = {""+ system.getMinimumPINLengthSuper(), ""+SystemServiceImpl.PIN_MAX};
            FieldError fe = new FieldError("minPINLength", "minPINLength", ms.getMessage("allowed.range.is.between.3.12.please.modify.the.value.and.try.again", args, "", loc));
            errors.add(fe);
            model.put("success", Boolean.FALSE);
            model.put("fields", errors);
            return new ModelAndView("ajax/result_ajax", "model", model);
        } else {
            system.setMinimumPINLengthForTenant(TenantContext.getTenantId(), minPINLength);
            model.put("success", true);
        }
        int sessionExpPeriod = ServletRequestUtils.getIntParameter(request,"sessionExpPeriod", 0);
        Configuration globalSessionExpPeriod = system.getConfiguration("SESSION_EXP_PERIOD");
        // Update only if the value is less than global setting
        if(sessionExpPeriod > 0 && sessionExpPeriod <= Integer.valueOf(globalSessionExpPeriod.getConfigurationValue())) {
        	int updateCount = tenant.updateSessionExpirationConfig(TenantContext.getTenantId(), sessionExpPeriod);
        	if(updateCount <= 0) {
        		model.put("success", Boolean.FALSE);
        	}
        }            
        
        logger.debug("Exiting savePasswordConfigValsAdmin() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/getpasswordconfigvalsadmin.ajax",method = RequestMethod.GET)
    public ModelAndView getPasswordConfigValsAdmin(HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("minPINLength", system.getMinPINLengthForTenant(TenantContext.getTenantId()));
        
        TenantConfiguration tenantConfiguration = tenant.getTenantConfiguration(TenantContext.getTenantId());
                
        model.put("sessionExpPeriod", tenantConfiguration.getSessionExpirationPeriod());

        return new ModelAndView("ajax/password_configs_ajax", "model", model);
    }

    @RequestMapping(value = "/misc.html",method = RequestMethod.GET)
    public ModelAndView getMiscHtml (HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);

		// vidyoweb
		File vidyoWebVersionFile = new File("/usr/local/tomcatnp/webapps/web/version.txt");
		String vidyoWebVersion = "-";
		if (vidyoWebVersionFile.exists()) {
			vidyoWebVersion = StringUtils.trimToEmpty(FileUtils.readFileToString(vidyoWebVersionFile));
		}
		model.put("vidyoWebVersion", vidyoWebVersion);
		model.put("vidyoWebAvailable", system.isVidyoWebAvailable());
		model.put("vidyoWebEnabled", system.isVidyoWebEnabledBySuper());

		/*Configuration mobiledModeConfiguration = systemService.getConfiguration("MOBILE_LOGIN_MODE");
		
		if (mobiledModeConfiguration != null 
				&& StringUtils.isNotBlank(mobiledModeConfiguration.getConfigurationValue())) { 
			model.put("mobileLogin",Integer.parseInt(mobiledModeConfiguration.getConfigurationValue()));
		}*/
		List<Integer> mobileAccess = tenant.getMobileAccessDetail();
		if (mobileAccess.size() == 1) {
			if (mobileAccess.get(0) == 1) {
				model.put("mobileLogin", 1);
			} else if(mobileAccess.get(0) == 2){
				model.put("mobileLogin", 2);
			} else {
				model.put("mobileLogin", 0);
			}
		} else {
			model.put("mobileLogin", 3);
		}

		// VidyoDesktop options
		model.put("tiles16Available", system.isTiles16Available());
				
		// search options
		model.put("showDisabledRoomsEnabled", system.isShowDisabledRooms());

		// ipc
		boolean isIpcSuperManaged = system.isIpcSuperManaged();
		model.put("superManaged", isIpcSuperManaged);
		if(!isIpcSuperManaged) {
			model.put("adminManaged", true);
		}else {
			model.put("adminManaged", false);
		}
		
		model.put("accessControlMode", system.getIpcAllowDomainsFlag());
		Configuration config=system.getConfiguration("IPC_ROUTER_POOL");
		if(config!=null){
			model.put("routerPool",config.getConfigurationValue() );
		}
		
	
		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);
        model.put("tlsProxyEnabled",system.getTLSProxyConfiguration());

		// gmail plugin
		File gmailPluginVersionFile = new File("/usr/local/tomcat/webapps/vidyoExtensionForGmail/version.txt");
		String gmailPluginVersion = "-";
		if (gmailPluginVersionFile.exists()) {
			gmailPluginVersion = StringUtils.trimToEmpty(FileUtils.readFileToString(gmailPluginVersionFile));
		}
		model.put("gmailPluginVersion", gmailPluginVersion);
		File gmailInstallation = new File("/usr/local/tomcat/webapps/vidyoExtensionForGmail.war");
		model.put("gmailPluginInstalled", gmailInstallation.exists());
        model.put("betaFeatureEnabled", VendorUtils.isBetaFeatureEnabled());
        model.put("tlsProxyFeatureEnabled", true);
        
        // chat
		PortalChat portalChat = system.getPortalChat();

		model.put("chatAvailable", portalChat.isChatAvailable());
		model.put("dafaultPublicChatEnabled", portalChat.isDefaultPublicChatEnabled());
		model.put("defaultPrivateChatEnabled", portalChat.isDefaultPrivateChatEnabled());
        
		return new ModelAndView("super/misc_html", "model", model);
	}

    @RequestMapping(value = "/savesearchoptions.ajax",method = RequestMethod.POST)
	public ModelAndView saveSearchOptionsSettingsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering saveSearchOptionsSettingsAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		String showDisabledRoomsEnabled = ServletRequestUtils.getStringParameter(request, "showDisabledRoomsEnabled", null);
		if(showDisabledRoomsEnabled != null && (showDisabledRoomsEnabled.equalsIgnoreCase("enabled")  || showDisabledRoomsEnabled.equalsIgnoreCase("on"))) {
			system.saveShowDisabledRoomsForSuper(true);
		} else {
			system.saveShowDisabledRoomsForSuper(false);
		}
		model.put("success", true);
		logger.debug("Exiting saveSearchOptionsSettingsAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}
    
    @RequestMapping(value = "/savetlsproxyconfig.ajax",method = RequestMethod.POST)
    public ModelAndView saveTlsProxySettingsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering saveTlsProxySettingsAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
        String tlsProxyEnabled = ServletRequestUtils.getStringParameter(request, "tlsProxyGroup", null);

        Boolean tlsProxyFlag = false;

        if(tlsProxyEnabled != null) {
            if(tlsProxyEnabled.equalsIgnoreCase("enabled")) {
                system.updateTlsProxyConfiguration(true);
                tlsProxyFlag = true;
            } else {
                system.updateTlsProxyConfiguration(false);
            }
            
            // Update the fields in vidyo_router_config
            componentsService.updateRouterProxyConfig(tlsProxyFlag);
            
            // update network element config
            List<NEConfiguration> proxyConfigs = serviceService.getNEConfigurations("%","VidyoProxy");
            if (proxyConfigs != null && proxyConfigs.size() > 0) {
                for (NEConfiguration proxyConfig : proxyConfigs) {
                    String necData = proxyConfig.getData();
                    if(necData.contains("<TLSProxy>false</TLSProxy>") || necData.contains("<TLSProxy>true</TLSProxy>") ){
                        if(tlsProxyFlag){
                            necData = necData.substring(0, necData.indexOf("<TLSProxy>")) +
                                    "<TLSProxy>" + tlsProxyFlag.toString() +
                                    necData.substring(necData.indexOf("</TLSProxy>"), necData.length());
                        } else  {
                            necData = necData.substring(0, necData.indexOf("<TLSProxy>"))+
                                    necData.substring(necData.indexOf("</Config>"), necData.length());
                        }

                    }else {
                        if(tlsProxyFlag){
                            necData = necData.substring(0, necData.indexOf("</Config>")) +
                                    "<TLSProxy>" + tlsProxyFlag.toString() + "</TLSProxy>" +
                                    necData.substring(necData.indexOf("</Config>"), necData.length());
                        }
                    }
                    proxyConfig.setData(necData);
                    serviceService.updateNEConfiguration(proxyConfig); // increments version
                }
            }
        }
        model.put("success", true);
        logger.debug("Exiting saveTlsProxySettingsAjax() of SettingsController");
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    /**
     * Validation for Scheduled Room Prefix
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validateschroomprefix.ajax",method = RequestMethod.GET)
	public ModelAndView validateScheduledRoomPrefixAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering validateScheduledRoomPrefixAjax() of SettingsController");
        Locale loc = LocaleContextHolder.getLocale();
		String tenantPrefix = ServletRequestUtils.getStringParameter(request, "value", "");
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		boolean prefixInUse = tenant.isTenantPrefixExistForTenantPrefix(tenantPrefix, 0);
		if (!tenantPrefix.equalsIgnoreCase("") && prefixInUse) {
			FieldError fe = new FieldError("tenantPrefix", "tenantPrefix", MessageFormat.format(
					ms.getMessage("duplicate.schroom.ext.prefix", null, "", loc), tenantPrefix));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		logger.debug("Exiting validateScheduledRoomPrefixAjax() of SettingsController");
		return new ModelAndView("ajax/json_result_ajax", "model", model);
	}
	
	protected int updateSchRoomPrefix(String newPrefix) {
		// Change the old prefix config name to "SCHEDULED_ROOM_PREFIX_DELETE"
		Configuration config = system.getConfiguration("SCHEDULED_ROOM_PREFIX");
		int id = -1;
		// Updating the prefix
		if (config != null && !config.getConfigurationValue().equals(newPrefix)) {
			// TODO - make this thread safe
			long timeMilliSecs = System.currentTimeMillis();
			id = system.saveSystemConfig("SCHEDULED_ROOM_PREFIX_DELETE_" + timeMilliSecs,
					config.getConfigurationValue(), 0);
			if (id <= 0) {
				return id;
			}
			// Delete all the scheduled rooms
			int deletedCount = roomService.deleteScheduledRooms(config.getConfigurationValue());
			logger.debug("Scheduled Rooms delete Count - {}", deletedCount);
			// After rooms are successfully deleted, remove the prefix marked
			// for deletion
			system.deleteConfiguration("SCHEDULED_ROOM_PREFIX_DELETE_" + timeMilliSecs);
			system.deleteConfiguration("SCHEDULED_ROOM_PREFIX");
			if (newPrefix != null && !newPrefix.trim().isEmpty()) {
				// Insert the new prefix
				id = system.saveSystemConfig("SCHEDULED_ROOM_PREFIX", newPrefix.trim(), 0);
			}
		} else if (config == null && newPrefix != null && !newPrefix.trim().isEmpty()) {
			id = system.saveSystemConfig("SCHEDULED_ROOM_PREFIX", newPrefix.trim(), 0);
		} else if(config == null && newPrefix == null) {
			// Disabling already disabled feature - hack through ajax
			id = 100000;
		}

		return id;
	}

	@RequestMapping(value = "/deletegmailplugin.ajax",method = RequestMethod.POST)
	public ModelAndView deleteGmailPlugin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		FileUtils.deleteQuietly(new File("/usr/local/tomcat/webapps/vidyoExtensionForGmail.war"));
		FileUtils.deleteQuietly(new File("/usr/local/tomcat/webapps/vidyoExtensionForGmail"));

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		model.put("fields", errors);
		model.put("success", Boolean.TRUE);

		return new ModelAndView("ajax/result_ajax", "model", model);

	}
	
	@RequestMapping(value = "/featuresettings.html", method = RequestMethod.GET)
	public ModelAndView getFeatureSettingsAdminHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);
		
		File vidyoWebVersionFile = new File("/usr/local/tomcatnp/webapps/web/version.txt");
		String vidyoWebVersion = "-";
		if (vidyoWebVersionFile.exists()) {
			vidyoWebVersion = StringUtils.trimToEmpty(FileUtils.readFileToString(vidyoWebVersionFile));
		}
		model.put("vidyoWebVersion", vidyoWebVersion);
		
		Integer tenantId = TenantContext.getTenantId();
		model.put("available", system.isVidyoWebAvailable());
		model.put("enabled", system.isVidyoWebEnabledByAdmin(tenantId));
		
		
		String zincServer = "";
		boolean zincEnabled = false;
		boolean isPublicChatEnabled = false;
		boolean isPrivateChatEnabled = false;
		boolean isChatAvailable = system.isChatAvailable();
        boolean waitingRoomsEnabled = false;
        boolean waitUntilOwnerJoins = false;
        boolean lectureModeStrict = false;

		TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(tenantId);

        zincServer = StringUtils.trimToEmpty(tenantConfig.getZincUrl());
        zincEnabled = tenantConfig.getZincEnabled() == 1;

        if(isChatAvailable) {
            isPrivateChatEnabled = tenantConfig.getEndpointPrivateChat() == 1;
            isPublicChatEnabled = tenantConfig.getEndpointPublicChat() == 1;
        }

        waitingRoomsEnabled = tenantConfig.getWaitingRoomsEnabled() == 1;
        waitUntilOwnerJoins = tenantConfig.getWaitUntilOwnerJoins() == 1;
        lectureModeStrict = tenantConfig.getLectureModeStrict() == 1;
		
		model.put("zincServer", zincServer);
		model.put("zincEnabled", zincEnabled);
		
		model.put("chatAvailable", isChatAvailable);
		model.put("publicChatEnabled", isPublicChatEnabled);
		model.put("privateChatEnabled", isPrivateChatEnabled);

        model.put("waitingRoomsEnabled", waitingRoomsEnabled);
        model.put("waitUntilOwnerJoins", waitUntilOwnerJoins);
        model.put("lectureModeStrict", lectureModeStrict);

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);
		
		return new ModelAndView("admin/featuresettings_html", "model", model);
	}
	
	@RequestMapping(value = "/savechatadmin.ajax", method = RequestMethod.POST)
	public ModelAndView saveChatAdminAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering saveChatAdminAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
		
		List<FieldError> errors = new ArrayList<FieldError>();
		
		if(!system.isChatAvailable()) {
			FieldError fe = new FieldError("saveChatAdmin", "saveChatAdmin", ms.getMessage("chat.feature.is.turned.off", null, "", loc));
			errors.add(fe);
		} else {
			String publicChatEnabled = ServletRequestUtils.getStringParameter(request, "publicChatEnabledGroup", null);
			String privateChatEnabled = ServletRequestUtils.getStringParameter(request, "privateChatEnabledGroup", null);
			
			if(publicChatEnabled == null || publicChatEnabled.isEmpty() || privateChatEnabled == null || privateChatEnabled.isEmpty()) {
				FieldError fe = new FieldError("saveChatAdmin", "saveChatAdmin", ms.getMessage("public.or.private.chat.state.is.not.defined", null, "", loc));
				errors.add(fe);
			} else {
				int publicChatState = 0;
				if(publicChatEnabled.equalsIgnoreCase("enabled")) {
					publicChatState = 1;
				}
				
				int privateChatState = 0;
				if(privateChatEnabled.equalsIgnoreCase("enabled")) {
					privateChatState = 1;
				}
				
				Integer tenantId = TenantContext.getTenantId();
				
				int updateCount = tenant.updateEndpointChatsStatuses(tenantId, privateChatState, publicChatState);
				if(updateCount == 0) {
					FieldError fe = new FieldError("saveChatAdmin", "saveChatAdmin", ms.getMessage("failed.to.save.public.private.chat.states", null, "", loc));
					errors.add(fe);
				}
			}
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		logger.debug("Exiting saveChatAdminAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
		
	}
	
	@RequestMapping(value = "/savechatsuper.ajax",method = RequestMethod.POST)
	public ModelAndView saveChatSuperAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Entering saveChatSuperAjax() of SettingsController");
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<FieldError> errors = new ArrayList<FieldError>();
		
		String chatAvailable = ServletRequestUtils.getStringParameter(request, "chatAvailableGroup", "available");
		String publicChatEnabled = ServletRequestUtils.getStringParameter(request, "publicChatEnabledGroup", "enabled");
		String privateChatEnabled = ServletRequestUtils.getStringParameter(request, "privateChatEnabledGroup", "enabled");
		
		PortalChat portalChat = new PortalChat();
		if(chatAvailable.equalsIgnoreCase("available")) {
			portalChat.setChatAvailable(true);
		} else {
			portalChat.setChatAvailable(false);
		}
		
		if(publicChatEnabled.equalsIgnoreCase("enabled")) {
			portalChat.setDefaultPublicChatEnabled(true);
		} else {
			portalChat.setDefaultPublicChatEnabled(false);
		}
		
		if(privateChatEnabled.equalsIgnoreCase("enabled")) {
			portalChat.setDefaultPrivateChatEnabled(true);
		} else {
			portalChat.setDefaultPrivateChatEnabled(false);
		}
		
		system.savePortalChat(portalChat);

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		logger.debug("Exiting saveChatSuperAjax() of SettingsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
		
	}

	@RequestMapping(value = "/saveRoomAttributes.ajax",method = RequestMethod.POST)
    public ModelAndView saveRoomAttributes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Entering saveRoomAttributes() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        List<FieldError> errors = new ArrayList<FieldError>();

        // when checkboxes not set/disabled, no form param is submitted
        String lectureModeAllowed = ServletRequestUtils.getStringParameter(request, "lectureModeAllowed", "0");
        String waitingRoomsEnabled = ServletRequestUtils.getStringParameter(request, "waitingRoomsEnabled", "disabled");
        String waitUntilOwnerJoins = ServletRequestUtils.getStringParameter(request, "waitUntilOwnerJoins", "disabled");
        String lectureModeStrict = ServletRequestUtils.getStringParameter(request, "lectureModeStrict", "disabled");
        String schRoomEnabledTenantLevel = ServletRequestUtils.getStringParameter(request, "schRoomEnabledTenantLevel", null);
        String publicRoomEnabledTenant = ServletRequestUtils.getStringParameter(request, "publicRoomEnabledTenant", null);
        String publicRoomMaxRoomNoPerUser = ServletRequestUtils.getStringParameter(request, "publicRoomMaxRoomNoPerUser", null);
	       
        int updateCount = tenant.setTenantRoomAttributes(TenantContext.getTenantId(),
                lectureModeAllowed.equalsIgnoreCase("1"),
                waitingRoomsEnabled.equalsIgnoreCase("1"),
        		waitUntilOwnerJoins.equalsIgnoreCase("1"),
                lectureModeStrict.equalsIgnoreCase("1"));
        
        if(updateCount == 0) {
			FieldError fe = new FieldError("RoomAttributes", "RoomAttributes", ms.getMessage("failed.to.save.room.attributes", null, "", loc));
			errors.add(fe);
			model.put("successAttributes", false);
		}
        
       

        logger.debug("Exiting saveRoomAttributes() of SettingsController");
    	
	
       if( schRoomEnabledTenantLevel!=null){
    	   
  
    	   
    	   // get the super config for scheduled room
    	   Configuration config = system.getConfiguration("SCHEDULED_ROOM_PREFIX");
    	   if(config == null || config.getConfigurationValue() == null || config.getConfigurationValue().trim().isEmpty()) {
			// Don't allow any changes to be done if super disabled scheduled room at system level
			FieldError fe = new FieldError("ScheduledRoom", ms.getMessage("manage.scheduled.room", null, "", loc),
					ms.getMessage("scheduled.room.update.failed", null, "", loc));
			errors.add(fe);
			
			model.put("successSchedule", false);
				
    	   }else{
    		   
    		   int updateCountSchdRoom = tenant.updateTenantScheduledRoomFeature(schRoomEnabledTenantLevel.equalsIgnoreCase("1")?1:0, TenantContext.getTenantId());
    		   if (updateCountSchdRoom <= 0) {
    			   FieldError fe = new FieldError("ScheduledRoom", ms.getMessage("manage.scheduled.room", null, "", loc),
    					   ms.getMessage("scheduled.room.update.failed", null, "", loc));
    			   			errors.add(fe);
    			   			model.put("successSchedule", false);
    		   }
    	   }
    	   if (logger.isDebugEnabled()) {
   			logger.debug("Exiting saveScheduledRoomAjax() of SettingsController");
   			}
       }
       //if the UI field is not visible in the UI, UI send null.
      if(!(publicRoomEnabledTenant==null &&publicRoomMaxRoomNoPerUser ==null) ){
    	    
    	 Configuration configuration = system.getConfiguration("CREATE_PUBLIC_ROOM_FLAG");
   	   	 if(configuration == null || configuration.getConfigurationValue() == null || configuration.getConfigurationValue().trim().isEmpty() || configuration.getConfigurationValue().equalsIgnoreCase("0")) {
			// Don't allow any changes to be done if super disabled scheduled room at system level
   	   		 FieldError fe = new FieldError("PublicRoom", "PublicRoom", ms.getMessage("failed.to.save.public.room.attributes", null, "", loc));
			 errors.add(fe);
			
			model.put("successSchedule", false);
				
   	   }else{
    	  
    	  
    	  TenantConfiguration config = tenant.getTenantConfiguration(TenantContext.getTenantId());
    		if (config == null) {
    			config = new TenantConfiguration();
    			config.setTenantId(TenantContext.getTenantId());
    		}
    		 if(publicRoomEnabledTenant!=null ){
    			 int isEnable=Integer.valueOf(publicRoomEnabledTenant);
    			 config.setCreatePublicRoomEnable(isEnable);
    			 if(isEnable==1){
    				 if(publicRoomMaxRoomNoPerUser !=null ){//if null, we dont update that field
    					 config.setMaxCreatePublicRoomUser(Integer.valueOf(publicRoomMaxRoomNoPerUser));
    				 }
    			 }else{
    				 //resetting to 0
    				 config.setMaxCreatePublicRoomUser(0);
    			 }
    			
    		 }
    	
    		int updateStatusPublicRoom=tenant.updateTenantConfiguration(config);
    		 if (updateStatusPublicRoom== 0) {
    			//TODO need to add in property files   			
   			   FieldError fe = new FieldError("PublicRoom", ms.getMessage("manage.scheduled.room", null, "", loc),
   					   ms.getMessage("scheduled.room.update.failed", null, "", loc));
   			   			errors.add(fe);
   			   			model.put("successPublicRoom", false);
   		   }
    		if (logger.isDebugEnabled()) {
    				logger.debug("Exiting saving public room detail () of SettingsController");
    		}
   	   }
      }

		
		if (errors.size() != 0) {
	            model.put("fields", errors);
	            model.put("success", Boolean.FALSE);
	        } else {
	            model.put("success", Boolean.TRUE);
	    }
        return new ModelAndView("ajax/result_ajax", "model", model);

    }
    
    @RequestMapping(value = "/savevidyodesktopoptions.ajax",method = RequestMethod.POST)
    public ModelAndView saveVidyoDesktopOptions(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.debug("Entering saveVidyoDesktopOptions() of SettingsController");
        Map<String, Object> model = new HashMap<String, Object>();

        boolean isTiles16Available = ServletRequestUtils.getBooleanParameter(request, "tiles16Available", false);
        
        system.saveTiles16Available(isTiles16Available);
        
        logger.debug("Exiting saveVidyoDesktopOptions() of SettingsController");
        
        model.put("success", Boolean.TRUE);
        
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/getroomattribute.ajax", method = RequestMethod.GET)
	public ModelAndView getRoomAttributeAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer tenantId = TenantContext.getTenantId();
		TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(tenantId);
		Configuration conf = system.getConfiguration(SCHEDULED_ROOM_PREFIX);
		String prefix = "";
		if (conf != null) {
			prefix = conf.getConfigurationValue();
		}
		model.put("schRoomEnabledSystemLevel", !prefix.isEmpty());
		Tenant curTenant = tenant.getTenant(TenantContext.getTenantId());
		model.put("schRoomEnabledTenantLevel", curTenant.getScheduledRoomEnabled());
        model.put("lectureModeAllowed", tenantConfig.getLectureModeAllowed());
		model.put("waitingRoomsEnabled", tenantConfig.getWaitingRoomsEnabled());
		model.put("waitUntilOwnerJoins", tenantConfig.getWaitUntilOwnerJoins());
		model.put("lectureModeStrict", tenantConfig.getLectureModeStrict());

		
		conf = system.getConfiguration(CREATE_PUBLIC_ROOM_FLAG);
		if (conf != null) {
			model.put("publicRoomEnabledGlobal", conf.getConfigurationValue());
		}else{
			model.put("publicRoomEnabledGlobal", 1);
		}
		
		model.put("publicRoomEnabledTenant", tenantConfig.getCreatePublicRoomEnable());
		model.put("publicRoomMaxRoomNoPerUser", tenantConfig.getMaxCreatePublicRoomUser());
		
		conf = system.getConfiguration(MAX_CREATE_PUBLIC_ROOM_USER);
		if (conf != null) {
			model.put("publicRoomMaxRoomNoPerUserGlb", conf.getConfigurationValue());
		}else{
			model.put("publicRoomMaxRoomNoPerUserGlb", 5);//the maximum global value in case 
		}

		return new ModelAndView("ajax/getroomattribute_ajax", "model", model);
	}
	
    @RequestMapping(value = "/getchatadmin.ajax", method = RequestMethod.GET)
	public ModelAndView getChatAdminAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		
		boolean isPublicChatEnabled = false;
		boolean isPrivateChatEnabled = false;
		boolean isChatAvailable = system.isChatAvailable();
		
		Integer tenantId = TenantContext.getTenantId();
		
		TenantConfiguration tenantConfig = this.tenant
				.getTenantConfiguration(tenantId);
		
		if (isChatAvailable) {
			isPrivateChatEnabled = tenantConfig.getEndpointPrivateChat() == 1;
			isPublicChatEnabled = tenantConfig.getEndpointPublicChat() == 1;
		}
		
		model.put("chatAvailable", isChatAvailable);
		model.put("publicChatEnabled", isPublicChatEnabled);
		model.put("privateChatEnabled", isPrivateChatEnabled);
		return new ModelAndView("ajax/getchatadmin_ajax", "model", model);
	}
	
    @RequestMapping(value = "/getvidyoweb.ajax", method = RequestMethod.GET)
	public ModelAndView getVidyoWebAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);

		File vidyoWebVersionFile = new File(
				"/usr/local/tomcatnp/webapps/web/version.txt");
		String vidyoWebVersion = "-";
		if (vidyoWebVersionFile.exists()) {
			vidyoWebVersion = StringUtils.trimToEmpty(FileUtils
					.readFileToString(vidyoWebVersionFile));
		}
		model.put("vidyoWebVersion", vidyoWebVersion);

		Integer tenantId = TenantContext.getTenantId();
		model.put("available", system.isVidyoWebAvailable());
		model.put("enabled", system.isVidyoWebEnabledByAdmin(tenantId));

		String zincServer = "";
		boolean zincEnabled = false;

		TenantConfiguration tenantConfig = this.tenant
				.getTenantConfiguration(tenantId);

		zincServer = StringUtils.trimToEmpty(tenantConfig.getZincUrl());
		zincEnabled = tenantConfig.getZincEnabled() == 1;
		model.put("zincServer", zincServer);
		model.put("zincEnabled", zincEnabled);
		return new ModelAndView("ajax/getvidyoweb_ajax", "model", model);
	}
	
    @RequestMapping(value = "/getCDRAccessParams.ajax", method = RequestMethod.GET)
	public ModelAndView getAdminCdrAccessParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");

		String cdrFormat = this.system.getCDRformat();
		model.put("CDRFormat", cdrFormat);

		Tenant curTenant = tenant.getTenantForRequest();
		model.put("tenantName", curTenant.getTenantName());

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request)
                .toString(), "admin");
		model.put("guideLoc", guideLoc);

		return new ModelAndView("ajax/cdr_access_html", "model", model);
	}

	@RequestMapping(value = "/getsettingstabmenu.ajax", method = RequestMethod.GET)
	public ModelAndView getSettingsTabMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String prefix = "";
		model.put("nav", "settings");
		this.user.setLoginUser(model, response);

		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME
				+ "private/PRIVILEGED_MODE");
		model.put("privilegedMode",
				(privModeFileHandle != null && privModeFileHandle.exists()));

		SystemLicenseFeature feature = null;

		try {
			feature = this.licensing.getSystemLicenseFeature("AllowPortalAPIs");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}",
					e.getMessage());
		}
		if (feature == null
				|| !feature.getLicensedValue().equalsIgnoreCase("true")) {
			model.put("showStatusNotifyTab", false);
		} else {
			model.put("showStatusNotifyTab", true);
		}

		try {
			feature = this.licensing.getSystemLicenseFeature("AllowExtDB");
		} catch (Exception e) {
			logger.error("Error while accessing Tenant license detail {}",
					e.getMessage());
		}

		if (feature == null
				|| !feature.getLicensedValue().equalsIgnoreCase("true")) {
			model.put("showExtDbTab", false);
		} else {
			model.put("showExtDbTab", true);
		}

		model.put("showSyslog", true);

		File gmailInstallation = new File(
				"/usr/local/tomcat/webapps/vidyoExtensionForGmail.war");
		model.put("gmailPluginInstalled", gmailInstallation.exists());

		List<String> configNames=new ArrayList<String>();
		configNames.add(USER_IMAGE);
		configNames.add("SHOW_CUSTOMIZE_BANNER");
		boolean showConfigBanner = false;
		boolean showUserAttributePage=false;
		List<Configuration> confList = system.getConfigurations(configNames);
		if(!confList.isEmpty()){
			for(Configuration conf:confList){
				if(conf!=null){
					switch (conf.getConfigurationName()) {
					case USER_IMAGE:
					{
						String val = conf.getConfigurationValue();
						if (val.equals("1")) {
							showUserAttributePage = true;
						}
						break;
					}
								
					case "SHOW_CUSTOMIZE_BANNER":
					{
						String val = conf.getConfigurationValue();
						if (val.equals("1")) {
							showConfigBanner = true;
						}
						break;
					
				
					}
				}
			}}
		}
		
		
		Configuration configBanner = system
				.getConfiguration("SHOW_CUSTOMIZE_BANNER");
		if (null != configBanner) {
			String val = configBanner.getConfigurationValue();
			if (val.equals("1")) {
				showConfigBanner = true;
			}
		}
		model.put("showConfigBanner", showConfigBanner);
		boolean isIpcSuperManaged = system.isIpcSuperManaged();
		model.put("isIpcSuperManaged", isIpcSuperManaged);
		model.put("maintMode", false);
		model.put("vidyoWebAvailable", system.isVidyoWebAvailable());
		model.put("vidyoChatAvailable", system.isChatAvailable());
		model.put("showUserAttributePage", showUserAttributePage);
		model.put("showEpicIntegration", extIntegrationService.isGlobalEpicEnabled());
		model.put("showTytoCareIntegration", extIntegrationService.isGlobalTytoCareEnabled());
		
		boolean showVidyoNeoWebRTC=false;
		Configuration vidyoNeoWebRTCGuestConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
 
        Tenant tenantDetails = tenant.getTenant(TenantContext.getTenantId());
        if ( tenantDetails.getTenantWebRTCURL() != null && StringUtils.isNotBlank(tenantDetails.getTenantWebRTCURL())){
	        if (vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
	        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1")){
	        	showVidyoNeoWebRTC = true;
	        } 
	        
	        if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
	        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
	        	showVidyoNeoWebRTC = true;
	        }
        }
		model.put("showVidyoNeoWebRTC", showVidyoNeoWebRTC);
	try{
		Configuration conf = system.getConfiguration(SCHEDULED_ROOM_PREFIX);
		
		if (conf != null) {
			prefix = conf.getConfigurationValue();
		}
    
		} catch (Exception e) {
			
			logger.error("Error while accessing SCHEDULED_ROOM_PREFIX ",
					e.getMessage());
		}
	
		model.put("vidyoSchdRoomAvailable",  !prefix.isEmpty());
	    int maintStatus = system.isPortalOnMaintenance("");
        if(maintStatus == 0 || maintStatus == 2) {
        	/*
        	 * If the status code is 0 - node is in maint mode,
        	 * If the status code is 1 - hot standby not enabled/not licensed
        	 * If the status code is 2 - hot standby enabled, but in maintenance mode
        	 */
        	model.put("maintMode", true);
            ClusterInfo clusterInfo = system.getClusterInfo("param");
            if (clusterInfo != null) {
                if (clusterInfo.getMyState() != null && clusterInfo.getMyState().contains("NOSTANDBY")) {
                    model.put("clusterErrorMessage", "WARNING !!! SYSTEM DEGRADED: NO HOT STANDBY DETECTED");
                }
            }
        }

		return new ModelAndView("ajax/settings_tab_menu", "model", model);
	}
	
	@RequestMapping(value = "/securedmaint/maintenance_portal_logs_export.ajax",method = RequestMethod.GET)
	public ModelAndView maintenanceExportVidyoPortalLogs(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String password  = ServletRequestUtils.getStringParameter(request, "password", "");
		
		String absPathZipFile = diagnosticsService.getZippedAbsFilePath(password);
		if(logger.isDebugEnabled()){
			logger.debug("absPathZipFile " + absPathZipFile);
		}
		if(absPathZipFile!=null){
			

			File downloadFile = new File(absPathZipFile);
			if (!downloadFile.exists()) {
				logger.error("file not exist " +absPathZipFile);
				return null;

			}
			long fileSize = downloadFile.length();
			response.reset();
			response.setHeader("Pragma", "public");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setHeader("Content-Description", "File Transfer");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ Logs + "\"");
			response.setHeader("Content-Transfer-Encoding", "binary");
			// response.setContentLength((int)fileSize);
			response.addHeader("Content-Length", Long.toString(fileSize));

			response.setContentType("application/zip");

			DataInputStream in = new DataInputStream(new FileInputStream(
					downloadFile));
			org.apache.commons.io.IOUtils.copy(in, response.getOutputStream());
			
			
			try{
					
					boolean flag=downloadFile.delete();
					if(!flag){
						logger.warn("File failed to delete  "+absPathZipFile);
				}
			}catch(Exception e){
				logger.error("File failed to delete  "+absPathZipFile +" "+e.getMessage());
				
			}
			
			in.close();			response.flushBuffer();

			return null;
		}

		return null;
	}
	
	/**
	 * Validates if the Session Expiration Period for the Tenant is less than the global settings.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validatesessionexpperiod.ajax",method = RequestMethod.POST)
	public ModelAndView validateSessionExpPeriodAjax(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Locale loc = LocaleContextHolder.getLocale();
		int sessionExpPeriod = ServletRequestUtils.getIntParameter(request,
				"sessionExpPeriod", 0);

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		Configuration config = system.getConfiguration("SESSION_EXP_PERIOD");
		// This configuration cannot be null

		if (sessionExpPeriod < 0
				|| sessionExpPeriod > Integer.valueOf(config
						.getConfigurationValue().trim())) {
			FieldError fe = new FieldError("sessionExpPeriod",
					"sessionExpPeriod", "invalid session exp period");
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			model.put("maxSessionExp", config.getConfigurationValue().trim());
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/json_passwords_result_ajax", "model", model);
	}
    
    @RequestMapping(value = "/roomlinksetting.ajax",method = RequestMethod.GET)
    public ModelAndView roomLinkSettings(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
       

        Configuration roomLinkFormatConfiguration = system.getConfiguration("ROOM_LINK_FORMAT");
        Configuration roomKeyLengthConfiguration = system.getConfiguration("ROOM_KEY_LENGTH");
        

        if (roomKeyLengthConfiguration != null && Integer.parseInt(roomKeyLengthConfiguration.getConfigurationValue()) > 0)  {
        	model.put("roomKeyLength", Integer.parseInt(roomKeyLengthConfiguration.getConfigurationValue()));
        } else {
        	model.put("roomKeyLength", 0);
        }
        
        if (roomLinkFormatConfiguration != null && roomLinkFormatConfiguration.getConfigurationValue() != null) {
        	model.put("roomLinkFormat", roomLinkFormatConfiguration.getConfigurationValue());
        }

        return new ModelAndView("ajax/roomSetting_ajax", "model", model);
    }
    
    @RequestMapping(value = "/saveroomlink.ajax",method = RequestMethod.POST)
    public ModelAndView saveRoomLinkSettings(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String roomLinkFormat = ServletRequestUtils.getStringParameter(request, "roomLinkFormatGroup", null);
        int roomKeyLength = ServletRequestUtils.getIntParameter(request, "roomKeyLength", 0);

        int saved = -1;
        
        if (StringUtils.isNotBlank(roomLinkFormat) ){
        	saved = system.updateSystemConfig("ROOM_LINK_FORMAT", roomLinkFormat);
        }
        
        if(roomKeyLength >= 0){
        	saved = system.updateSystemConfig("ROOM_KEY_LENGTH", roomKeyLength+"");
        }
        
        if (saved >= 0) {
            model.put("success", Boolean.TRUE);
        } else{
        	model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/supersavedesktopcustomization.ajax", method = RequestMethod.POST)
    public ModelAndView superSaveDesktopCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("desktopPackage");
        String mimeType = multipartFile.getContentType();

        String origFinalname = multipartFile.getOriginalFilename().toLowerCase();
        // check if valid zip file
        if (!PortalUtils.isValidZipFileName(origFinalname)) {
            FieldError fe = new FieldError("desktopPackage", "File name must be *.zip", "File name must be *.zip");
            errors.add(fe);
        } else if (!PortalUtils.isValidZipMimeType(mimeType, origFinalname)) {
            FieldError fe = new FieldError("desktopPackage", "File type must be a zip", "File type must be a zip");
            errors.add(fe);
        } else if (multipartFile.getSize() > 50 * 1024 * 1024) {
            FieldError fe = new FieldError("desktopPackage", "File type must be a less than 50MB", "File type must be a less than 50MB");
            errors.add(fe);
        } else {
            String newFilename = "0_desktop.zip";

            // upload to temp directory
            File uploadedTempFile = new File(uploadTempDirSuper + newFilename);
            multipartFile.transferTo(uploadedTempFile);

            // TODO: more validation of Neo package here

            if (!PortalUtils.isValidZipFile(uploadedTempFile)) {
                logger.info("Invalid mime-type for desktop customization upload.");
                FileUtils.deleteQuietly(uploadedTempFile);
                FieldError fe = new FieldError("desktopPackage", "File type must be a zip", "File type must be a zip");
                errors.add(fe);
            } else {
                // delete previous version
                FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/" + newFilename));
                FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/" + newFilename.replace(".zip", ".sha1")));

                String destFile = "/opt/vidyo/portal2/customization/" + uploadedTempFile.getName();
                String destFileSha = "/opt/vidyo/portal2/customization/" + uploadedTempFile.getName().replace(".zip", ".sha1");

                // while copying bytes, calculate hash
                try (
                        FileInputStream is = new FileInputStream(uploadedTempFile);
                        DigestInputStream dis = new DigestInputStream(is, MessageDigest.getInstance("SHA-1"));
                        BufferedInputStream bis = new BufferedInputStream(dis);
                        FileOutputStream fos = new FileOutputStream(destFile);
                        BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    int i = 0;
                    while ((i = bis.read()) != -1) {
                        bos.write(i);
                    }
                    String sha1hash = Hex.encodeHexString(dis.getMessageDigest().digest());
                    FileUtils.writeStringToFile(new File(destFileSha), sha1hash);
                } catch (IOException e) {
                    logger.error("IOException handling customization upload");
                }

                FileUtils.deleteQuietly(uploadedTempFile);
                PortalUtils.setWebappsFileOwnerAndPermissions(destFile);
                PortalUtils.setWebappsFileOwnerAndPermissions(destFileSha);

            }
        }

        if(errors.size()!=0){
            system.auditLogTransaction("SuperDesktopEndpointCustomizationUpload", origFinalname, FAILURE);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            system.auditLogTransaction("SuperDesktopEndpointCustomizationUpload", origFinalname, SUCCESS);
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/downloadSuperDesktopCustomization.html", method = RequestMethod.GET)
    public ModelAndView superDownloadDesktopCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        File superDesktopCustomizationFile = new File("/opt/vidyo/portal2/customization/0_desktop.zip");
        if (superDesktopCustomizationFile.exists()) {
            long fileSize = superDesktopCustomizationFile.length();

            response.reset();

            response.setHeader("Pragma", "public");
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Disposition", "attachment; filename=\"customization.zip\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.addHeader("Content-Length", Long.toString(fileSize));
            response.setContentType("application/zip");

            try (ServletOutputStream out = response.getOutputStream();
                 FileInputStream in = new FileInputStream(superDesktopCustomizationFile)) {
                IOUtils.copy(in, out);
            }
            system.auditLogTransaction("SuperDesktopEndpointCustomizationDownload", "", SUCCESS);
        }
        return null;
    }

    @RequestMapping(value = "/superendpointcustomization.ajax", method = RequestMethod.GET)
    public ModelAndView superEndpointCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        File superDesktopCustomizationFile = new File("/opt/vidyo/portal2/customization/0_desktop.zip");

        model.put("superCustomizeDesktopExists", superDesktopCustomizationFile.exists());

        Configuration override = system.getConfiguration("EndpointCustomizationOverride");
        if (override != null && StringUtils.isNotBlank(override.getConfigurationValue())) {
            if ("true".equals(override.getConfigurationValue())) {
                model.put("allowTenantOverride", "true");
            } else {
                model.put("allowTenantOverride", "false");
            }
        } else {
            model.put("allowTenantOverride", "false");
        }


        return new ModelAndView("ajax/endpoint_customization_ajax", "model", model);
    }

    @RequestMapping(value = "/savedesktopcustomizationoverride.ajax", method = RequestMethod.POST)
    public ModelAndView superSaveEndpointCustomizationOverride(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String allowTenantOverrideStr = ServletRequestUtils.getStringParameter(request, "allowTenantOverride", "false");

        if (StringUtils.isNotBlank(allowTenantOverrideStr) && "true".equals(allowTenantOverrideStr.toLowerCase())) {
            system.saveOrUpdateConfiguration(0, "EndpointCustomizationOverride", "true");
            system.auditLogTransaction("SuperDesktopEndpointCustomizationTenantOverride","true", SUCCESS);
        } else {
            system.saveOrUpdateConfiguration(0, "EndpointCustomizationOverride", "false");
            system.auditLogTransaction("SuperDesktopEndpointCustomizationTenantOverride", "false", SUCCESS);
        }
        model.put("success", Boolean.TRUE);

        return new ModelAndView("ajax/result_ajax", "model", model);
    }


    @RequestMapping(value = "/adminsavedesktopcustomization.ajax",method = RequestMethod.POST)
    public ModelAndView adminSaveDesktopCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("desktopPackage");
        String mimeType = multipartFile.getContentType();

        int tenantId = TenantContext.getTenantId();

        String origFinalname = multipartFile.getOriginalFilename().toLowerCase();
        Configuration tenantOverride = system.getConfiguration("EndpointCustomizationOverride");
        if (tenantOverride != null && !"true".equals(tenantOverride.getConfigurationValue())) {
            FieldError fe = new FieldError("desktopPackage", "Tenant override of customization forbidden.", "Tenant override of customization forbidden.");
            errors.add(fe);
        } else if (!PortalUtils.isValidZipFileName(origFinalname)) {
            FieldError fe = new FieldError("desktopPackage", "File name must be *.zip", "File name must be *.zip");
            errors.add(fe);
        } else if (!PortalUtils.isValidZipMimeType(mimeType, origFinalname)) {
            FieldError fe = new FieldError("desktopPackage", "File type must be a zip", "File type must be a zip");
            errors.add(fe);
        } else if (multipartFile.getSize() == 0) {
            FieldError fe = new FieldError("desktopPackage", "File cannot be empty", "File cannot be empty");
            errors.add(fe);
        }  else if (multipartFile.getSize() > 50 * 1024 * 1024) {
            FieldError fe = new FieldError("desktopPackage", "File type must be a less than 50MB", "File type must be a less than 50MB");
            errors.add(fe);
        } else {
            String newFilename = "" + tenantId + "_desktop.zip";

            // upload to temp directory
            File uploadedTempFile = new File(uploadTempDir + newFilename);
            multipartFile.transferTo(uploadedTempFile);

            // TODO: more validation of Neo package here

            if (!PortalUtils.isValidZipFile(uploadedTempFile)) {
                logger.info("Invalid mime-type for desktop customization upload.");
                FileUtils.deleteQuietly(uploadedTempFile);
                FieldError fe = new FieldError("desktopPackage", "File type must be a zip", "File type must be a zip");
                errors.add(fe);
            } else {
                FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/" + newFilename));
                FileUtils.deleteQuietly(new File("/opt/vidyo/portal2/customization/" + newFilename.replace(".zip", ".sha1")));

                String destFile = "/opt/vidyo/portal2/customization/" + uploadedTempFile.getName();
                String destFileSha = "/opt/vidyo/portal2/customization/" + uploadedTempFile.getName().replace(".zip", ".sha1");

                // while copying bytes, calculate hash
                try (
                        FileInputStream is = new FileInputStream(uploadedTempFile);
                        DigestInputStream dis = new DigestInputStream(is, MessageDigest.getInstance("SHA-1"));
                        BufferedInputStream bis = new BufferedInputStream(dis);
                        FileOutputStream fos = new FileOutputStream(destFile);
                        BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    int i = 0;
                    while ((i = bis.read()) != -1) {
                        bos.write(i);
                    }
                    String sha1hash = Hex.encodeHexString(dis.getMessageDigest().digest());
                    FileUtils.writeStringToFile(new File(destFileSha), sha1hash);
                } catch (IOException e) {
                    logger.error("IOException handling customization upload");
                }

                FileUtils.deleteQuietly(uploadedTempFile);
                PortalUtils.setWebappsFileOwnerAndPermissions(destFile);
                PortalUtils.setWebappsFileOwnerAndPermissions(destFileSha);
            }
        }

        if(errors.size()!=0){
            system.auditLogTransaction("AdminDesktopEndpointCustomizationUpload", origFinalname, FAILURE);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }
        else {
            system.auditLogTransaction("AdminDesktopEndpointCustomizationUpload", origFinalname, SUCCESS);
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/downloadAdminDesktopCustomization.html", method = RequestMethod.GET)
    public ModelAndView adminDownloadDesktopCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int tenantId = TenantContext.getTenantId();
        File superDesktopCustomizationFile = new File("/opt/vidyo/portal2/customization/" +  tenantId + "_desktop.zip");
        if (superDesktopCustomizationFile.exists()) {
            long fileSize = superDesktopCustomizationFile.length();

            response.reset();

            response.setHeader("Pragma", "public");
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Disposition", "attachment; filename=\"customization.zip\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.addHeader("Content-Length", Long.toString(fileSize));
            response.setContentType("application/zip");

            try (ServletOutputStream out = response.getOutputStream();
                 FileInputStream in = new FileInputStream(superDesktopCustomizationFile)) {
                IOUtils.copy(in, out);
            }
            system.auditLogTransaction("AdminDesktopEndpointCustomizationDownload", "", SUCCESS);
        }
        return null;
    }

    @RequestMapping(value = "/adminendpointcustomization.ajax", method = RequestMethod.GET)
    public ModelAndView adminEndpointCustomization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int tenantId = TenantContext.getTenantId();
        File adminDesktopCustomizationFile = new File("/opt/vidyo/portal2/customization/" +  tenantId + "_desktop.zip");

        model.put("adminCustomizeDesktopExists", adminDesktopCustomizationFile.exists());

        Configuration override = system.getConfiguration("EndpointCustomizationOverride");
        if (override != null && StringUtils.isNotBlank(override.getConfigurationValue())) {
            if ("true".equals(override.getConfigurationValue())) {
                model.put("allowTenantOverride", "true");
            } else {
                model.put("allowTenantOverride", "false");
            }
        } else {
            model.put("allowTenantOverride", "false");
        }

        return new ModelAndView("ajax/endpoint_customization_ajax", "model", model);
    }

    @RequestMapping(value = "/vidyoneowebrtcsetting.ajax",method = RequestMethod.GET)
    public ModelAndView getVidyoNeoWebRTCSettings(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
       

        Configuration vidyoNeoWebRTCGuestConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        

        if (vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1")){
        	model.put("enableVidyoNeoWebRTCGuest", 1);
        } else {
        	model.put("enableVidyoNeoWebRTCGuest", 0);
        }
        
        if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
        	model.put("enableVidyoNeoWebRTCUser", 1);
        } else {
        	model.put("enableVidyoNeoWebRTCUser", 0);
        }

        return new ModelAndView("ajax/vidyoNeoWebRTCSetting_ajax", "model", model);
    }
    
    @RequestMapping(value = "/savevidyoneowebrtcsetting.ajax",method = RequestMethod.POST)
    public ModelAndView saveVidyoNeoWebRTCSettings(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        int enableVidyoNeoWebRTCGuest = ServletRequestUtils.getIntParameter(request, "enableVidyoNeoWebRTCGuest", 0);
        int enableVidyoNeoWebRTCUser = ServletRequestUtils.getIntParameter(request, "enableVidyoNeoWebRTCUser", 0);

        int saved = 0;
        
        Configuration vidyoNeoWebRTCGuestConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        boolean isWebRTCGuestChanged = false;
        boolean isWebRTCUserChanged = false;
        
        if (vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
        		&& !vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase(""+enableVidyoNeoWebRTCGuest)) {
        	isWebRTCGuestChanged = true;
        } else if (vidyoNeoWebRTCGuestConfiguration == null && enableVidyoNeoWebRTCGuest > 0) {
        	isWebRTCGuestChanged = true;
        }
        
        if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
        		&& !vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase(""+enableVidyoNeoWebRTCUser)) {
        	isWebRTCUserChanged = true;
        } else if (vidyoNeoWebRTUserConfiguration == null && enableVidyoNeoWebRTCUser > 0) {
        	isWebRTCUserChanged = true;
        }
        
        if (isWebRTCGuestChanged){
        	saved = system.updateSystemConfig("VIDYONEO_FOR_WEBRTC_FOR_GUESTS", enableVidyoNeoWebRTCGuest+"");
        }
        
        if (isWebRTCUserChanged){
        	saved = system.updateSystemConfig("VIDYONEO_FOR_WEBRTC_FOR_USERS", enableVidyoNeoWebRTCUser+"");
        }
        
        if (saved >= 0) {
            model.put("success", Boolean.TRUE);
            if (isWebRTCGuestChanged){
                system.auditLogTransaction(enableVidyoNeoWebRTCGuest == 1 ? "Guest Web RTC enabled- System Wide.":"Guest Web RTC disabled- System Wide.", "", SUCCESS);
            }
            if (isWebRTCUserChanged){
                system.auditLogTransaction(enableVidyoNeoWebRTCUser == 1 ? "User Web RTC enabled- System Wide.":"User Web RTC disabled- System Wide.", "", SUCCESS);
            }
        } else{
        	model.put("success", Boolean.FALSE);
        	if (isWebRTCGuestChanged){
                system.auditLogTransaction(enableVidyoNeoWebRTCGuest == 1 ? "Guest Web RTC enabled- System Wide.":"Guest Web RTC disabled- System Wide.", "", FAILURE);
        	}
        	if (isWebRTCUserChanged){
                system.auditLogTransaction(enableVidyoNeoWebRTCUser == 1 ? "User Web RTC enabled- System Wide.":"User Web RTC disabled- System Wide.", "", FAILURE);
        	}
        }
        
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/vidyoneowebrtcsettingadmin.ajax", method = RequestMethod.GET)
    public ModelAndView getVidyoNeoWebRTCSettingsAdmin(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
       
        Integer tenantId = TenantContext.getTenantId();
        
        Configuration vidyoNeoWebRTCGuestConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        Tenant tenantDetails = tenant.getTenant(tenantId);
        if ( tenantDetails.getTenantWebRTCURL() == null || StringUtils.isBlank(tenantDetails.getTenantWebRTCURL())){
        	//Since the WebRTC URL is not set for the Tenant Yet, it is not yet ready to be configure at Tenant level.
        	model.put("enableVidyoNeoWebRTCGuest", 0);
        	model.put("enableVidyoNeoWebRTCUser", 0);
        	model.put("enableVidyoNeoWebRTCGuestAdmin", 0);
        	model.put("enableVidyoNeoWebRTCUserAdmin", 0);
        } else {
	        TenantConfiguration tenantConfig = tenant.getTenantConfiguration(tenantId);
	
	        if (vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
	        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1")){
	        	model.put("enableVidyoNeoWebRTCGuest", 1);
	         } else {
	        	model.put("enableVidyoNeoWebRTCGuest", 0);
	        }
			model.put("enableVidyoNeoWebRTCGuestAdmin", tenantConfig.getVidyoNeoWebRTCGuestEnabled());
	        if (vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
	        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
	        	model.put("enableVidyoNeoWebRTCUser", 1);
	        } else {
	        	model.put("enableVidyoNeoWebRTCUser", 0);
	        }
	   		model.put("enableVidyoNeoWebRTCUserAdmin", tenantConfig.getVidyoNeoWebRTCUserEnabled());
        }
        return new ModelAndView("ajax/vidyoNeoWebRTCSetting_ajax", "model", model);
    }

    @RequestMapping(value = "/savevidyoneowebrtcsettingadmin.ajax", method = RequestMethod.POST)
    public ModelAndView saveVidyoNeoWebRTCSettingsAdmin(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        
        Configuration vidyoNeoWebRTCGuestConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_GUESTS");
        boolean isSuperWebRTCGuestEnabled = vidyoNeoWebRTCGuestConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTCGuestConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTCGuestConfiguration.getConfigurationValue().equalsIgnoreCase("1");
        Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
        boolean isSuperWebRTCUserEnabled = vidyoNeoWebRTUserConfiguration != null && StringUtils.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
        		&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1");

        int enableVidyoNeoWebRTCGuestAdmin = ServletRequestUtils.getIntParameter(request, "enableVidyoNeoWebRTCGuestAdmin", 0);
        int enableVidyoNeoWebRTCUserAdmin = ServletRequestUtils.getIntParameter(request, "enableVidyoNeoWebRTCUserAdmin", 0);

        Integer tenantId = TenantContext.getTenantId();
        
        int saved = 0;
        TenantConfiguration tenantConfig = tenant.getTenantConfiguration(tenantId);
        boolean isWebRTCGuestAdminChanged = tenantConfig.getVidyoNeoWebRTCGuestEnabled() != enableVidyoNeoWebRTCGuestAdmin;
        boolean isWebRTCUserAdminChanged = tenantConfig.getVidyoNeoWebRTCUserEnabled() != enableVidyoNeoWebRTCUserAdmin;
        
        if (isWebRTCGuestAdminChanged){
        	tenantConfig.setVidyoNeoWebRTCGuestEnabled(enableVidyoNeoWebRTCGuestAdmin);
        }
        if (isWebRTCUserAdminChanged){
        	tenantConfig.setVidyoNeoWebRTCUserEnabled(enableVidyoNeoWebRTCUserAdmin);
    	}
        if (isWebRTCGuestAdminChanged || isWebRTCUserAdminChanged){
        	saved = tenant.updateTenantConfiguration(tenantConfig);
        }
       			
        if (saved >= 0) {
            model.put("success", Boolean.TRUE);
            if (isSuperWebRTCGuestEnabled){
            	if (isWebRTCGuestAdminChanged){
                    system.auditLogTransaction(enableVidyoNeoWebRTCGuestAdmin == 1 ? "Guest Web RTC enabled.":"Guest Web RTC disabled.", "", SUCCESS);
            	}
            }
            if (isSuperWebRTCUserEnabled) {
            	if (isWebRTCUserAdminChanged){
                    system.auditLogTransaction(enableVidyoNeoWebRTCUserAdmin == 1 ? "User Web RTC enabled.":"User Web RTC disabled.", "", SUCCESS);
            	}
            }
        } else{
        	model.put("success", Boolean.FALSE);
        	 if (isSuperWebRTCGuestEnabled){
        		 if (isWebRTCGuestAdminChanged){
                     system.auditLogTransaction(enableVidyoNeoWebRTCGuestAdmin == 1 ? "Guest Web RTC enabled.":"Guest Web RTC disabled.", "", FAILURE);
        		 }
        	 }
        	 if (isSuperWebRTCUserEnabled) {
        		 if (isWebRTCUserAdminChanged){
                     system.auditLogTransaction(enableVidyoNeoWebRTCUserAdmin == 1 ? "User Web RTC enabled.":"User Web RTC disabled.", "", FAILURE);
        		 }
        	 }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    
    @RequestMapping(value = "/saveuploadmode.ajax", method = RequestMethod.POST)
    public ModelAndView saveSuperUploadMode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int saved = 0;
        Locale loc = LocaleContextHolder.getLocale();
        
        String manageEndpointUploadMode = ServletRequestUtils.getStringParameter(request, "fileServerModeGroup", null);
        
        if (manageEndpointUploadMode == null || (!manageEndpointUploadMode.equalsIgnoreCase("External") && !manageEndpointUploadMode.equalsIgnoreCase("VidyoPortal"))){
        	model.put("success", Boolean.FALSE);
            system.auditLogTransaction("Endpoint Upload Mode change failed for invalid value.","EndpointUploadMode: "+manageEndpointUploadMode, FAILURE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Configuration endpointUploadModeConfiguration = system.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
    	if ((endpointUploadModeConfiguration == null ) 
    		|| (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null 
    			&& !endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase(manageEndpointUploadMode))){
    		saved = system.saveOrUpdateConfiguration(0, "MANAGE_ENDPOINT_UPLOAD_MODE", manageEndpointUploadMode);

    		TenantFilter tf = new TenantFilter();
            tf.setLimit(Integer.MAX_VALUE);
            List<Tenant> tenants = this.tenant.getTenants(tf);

    		//this.endpointupload.deactivateEndpoints(0);
    		
            for (Tenant t : tenants) {
        		TenantConfiguration tenantConfig = this.tenant.getTenantConfiguration(t.getTenantID());
        		tenantConfig.setEndpointUploadMode(manageEndpointUploadMode);
        		this.tenant.updateTenantConfiguration(tenantConfig);

        		//this.endpointupload.deactivateEndpoints( t.getTenantID());
            }
    	}
        
    	if (saved==0){
   			model.put("msg",ms.getMessage("save.failed", null, "", loc));
        }
    	
    	model.put("success", saved > 0 ? Boolean.TRUE:Boolean.FALSE);
        system.auditLogTransaction("Endpoint Upload Mode changed.","EndpointUploadMode: "+manageEndpointUploadMode, saved > 0 ? SUCCESS:FAILURE);

        return new ModelAndView("ajax/result_ajax", "model", model);
        
    }

    @RequestMapping(value = "/invtSttngDialInCntyListAjax.ajax", method = RequestMethod.GET)
    public ModelAndView getInvtSttngDialInCntyListAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();    
        List<Country> listCountry=countryService.getAllCountries();
        model.put("list", listCountry);
        return new ModelAndView("ajax/dail_in_country_list_ajax", "model", model);
    }

    @RequestMapping(value = "/adminInvtSttngDialInCntyGridAjax.ajax", method = RequestMethod.GET)
	public ModelAndView getAdminInvtSttngDialInCntyGridAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();
		List<TenantDialInCountry> tenantListCountry = tenantDdialInService.getTenantDialInCounties(TenantContext.getTenantId());
		if(tenantListCountry==null|| tenantListCountry.size()==0){
			//means no teant level is available so we will show the super level.
			tenantListCountry = tenantDdialInService.getTenantDialInCounties(0);
		}
		model.put("list", tenantListCountry);
		return new ModelAndView("ajax/dail_in_country_grid_ajax", "model", model);
	}
	
	@RequestMapping(value = "/superInvtSttngDialInCntyGridAjax.ajax", method = RequestMethod.GET)
	public ModelAndView getSuperInvtSttngDialInCntyGridAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TenantDialInCountry> tenantListCountry;
		Locale loc = LocaleContextHolder.getLocale();
		tenantListCountry = tenantDdialInService.getTenantDialInCounties(0);
		model.put("list", tenantListCountry);
		return new ModelAndView("ajax/dail_in_country_grid_ajax", "model", model);
	}
}
