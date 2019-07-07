package com.vidyo.web;

import com.vidyo.bo.DiagnosticReport;
import com.vidyo.bo.InstallationReport;
import com.vidyo.bo.Network;
import com.vidyo.bo.ConfigLogFile;
import com.vidyo.bo.ConfigProperty;
import com.vidyo.service.IRouterConfigService;
import com.vidyo.framework.security.RouterConfigAuthenticationProvider;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.diagnostics.IDiagnosticsService;
import com.vidyo.service.installation.InstallationService;
import com.vidyo.service.INetworkService;
import com.vidyo.service.ISecurityService;
import com.vidyo.service.SecurityServiceImpl;
import com.vidyo.utils.CompressionUtils;
import com.vidyo.utils.VendorUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RouterConfigController {

    protected final static Logger logger = LoggerFactory.getLogger(RouterConfigController.class.getName());
    private INetworkService network;
    private ISecurityService securityService;
    private IRouterConfigService routerConfigService;
    private MessageSource messages;
    private IDiagnosticsService diagnosticsService;
    private InstallationService installationService;
    private String tmpDirectory;

    public void setMessages(MessageSource messages) {
        this.messages = messages;
    }

    public void setNetwork(INetworkService network) {
        this.network = network;
    }

    public void setSecurityService(ISecurityService securityService) {
        this.securityService = securityService;
    }

    public void setRouterConfigService(IRouterConfigService routerConfigService) {
        this.routerConfigService = routerConfigService;
    }
    
    public void setInstallationService(InstallationService installationService) {
    	this.installationService = installationService;
    }

    public void setDiagnosticsService(IDiagnosticsService dService) {
        this.diagnosticsService = dService;
    }

    @RequestMapping(value = "/menu_content.html", method = RequestMethod.GET)
    public ModelAndView getMenuContentHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("standaloneRouter", routerConfigService.isStandaloneRouter());
        return new ModelAndView("menu_content_html", "model", model);
    }

    @RequestMapping(value = "/security.html", method = RequestMethod.GET)
    public ModelAndView getSecurityHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        model.put("sslEnabledFlag", securityService.isSSLEnabled() ? "1" : "0");
        model.put("httpsOnlyFlag", securityService.isForcedHTTPSEnabled() ? "1" : "0");
        model.put("httpsOnlyFlagNoRedirect", securityService.isForcedHTTPSEnabledNoRedirect() ? "1" : "0");
		model.put("useDefaultRootCerts", securityService.isUseDefaultRootCerts() ? "true" : "false");

        // ocsp settigns
        List<com.vidyo.bo.security.ConfigProperty> ocspConfigProps            = securityService.getOCSPConfigProperties();
        com.vidyo.bo.security.ConfigProperty SSLOCSPEnableProp                = securityService.getOCSPConfigProperty("SSLOCSPEnable", ocspConfigProps);
        com.vidyo.bo.security.ConfigProperty SSLOCSPOverrideResponderProp     = securityService.getOCSPConfigProperty("SSLOCSPOverrideResponder", ocspConfigProps);
        com.vidyo.bo.security.ConfigProperty SSLOCSPDefaultResponderProp      = securityService.getOCSPConfigProperty("SSLOCSPDefaultResponder", ocspConfigProps);

        if(SSLOCSPEnableProp.getName().equals("")){
            SSLOCSPEnableProp.setValue("off");
        }
        if(SSLOCSPOverrideResponderProp.getName().equals("")){
            SSLOCSPOverrideResponderProp.setValue("off");
        }

        String apacheVersion = securityService.getApacheVersion();
        boolean apacheOCSPSupport = true;
        if(apacheVersion.compareTo("2.4") < 0){
            apacheOCSPSupport = false;
        }

        model.put("ocspEnabledFlag", "on".equals(SSLOCSPEnableProp.getValue()));
        model.put("ocspOverrideFlag", "on".equals(SSLOCSPOverrideResponderProp.getValue()));
        model.put("ocspDefaultResponder", SSLOCSPDefaultResponderProp.getValue());
        //cant send back apache  version. quix fix
        model.put("apacheVersion", "");
        model.put("ocspSupportFlag", apacheOCSPSupport);
        String user = "unknown";
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            user = authentication.getName();
        }
        model.put("userName", user );
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", true);
        ModelAndView modelAndView = new ModelAndView("security_html", "model", model);
        return modelAndView;
    }

    @RequestMapping(value = "/maintenance.html", method = RequestMethod.GET)
    public ModelAndView getMaintenanceHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        List<ConfigProperty> props         = routerConfigService.getRouterConfigProperties();
        ConfigProperty ConfigServerAddress = routerConfigService.getRouterConfigProperty("ConfigServerAddress", props);

        model.put("portalversion", routerConfigService.getRouterVersion());
        model.put("configServerAddress", ConfigServerAddress.getValue());

        ConfigProperty LogLevel            = routerConfigService.getRouterConfigProperty("LogLevel", props);
        ConfigProperty LogFileName         = routerConfigService.getRouterConfigProperty("LogFileName", props);
        ConfigProperty MaxLogFileSizeKB    = routerConfigService.getRouterConfigProperty("MaxLogFileSizeKB", props);

        model.put("logLevel", LogLevel.getValue());
        model.put("logFileName", LogFileName.getValue());
        model.put("logFileMaxSize", MaxLogFileSizeKB.getValue());
        String vidyoRouterType = routerConfigService.getRouterType();
        model.put("vidyoRouterType", vidyoRouterType);
		model.put("standaloneRouter", routerConfigService.isStandaloneRouter());

        String user = "unknown";
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            user = authentication.getName();
        }
        model.put("userName", user );
		model.put("betaFeatureEnabled", VendorUtils.isBetaFeatureEnabled());
		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", true);
        ModelAndView modelAndView = new ModelAndView("maintenance_html", "model", model);
        return modelAndView;
    }

    @RequestMapping(value = "/securedmaint/update_config_server.ajax", method = RequestMethod.POST)
    public ModelAndView updateConfigServerAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = "ConfigServerAddress";
        String value = routerConfigService.getRequestParameter("configServerAddress", request);

        String ret="";

        if(value == null || value.trim().equals("")){
            ret = "Configuration server cannot be empty.";
        } else {
            ret = routerConfigService.updateRouterConfigProperty(name, value.trim());
            restartVC2();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if ("OK".equals(ret)) {
            model.put("success", Boolean.TRUE);
            routerConfigService.writeAuditHistory("Save configuration server success: " + value);
        } else {
            routerConfigService.writeAuditHistory("Save configuration server failure: " + ret);
            FieldError fe = new FieldError("configServerAddress", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);

    }

    @RequestMapping(value = "/securedmaint/maintenance_system_reset.ajax", method = RequestMethod.POST)
    public ModelAndView systemResetAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        String ret="";
        String username = routerConfigService.getRequestParameter("username", request);
        String password = routerConfigService.getRequestParameter("password", request);

        boolean passCheck = RouterConfigAuthenticationProvider.checkCredentials(username, password);
        if (passCheck) {

            String command = routerConfigService.getRequestParameter("command", request);
            if ("restart".equals(command)) {
                routerConfigService.restart();
                ret = "OK";
            } else if ("shutdown".equals(command)) {
                routerConfigService.shutdown();
                ret = "OK";
            }
        } else {
            ret = "Username/password incorrect.";
            routerConfigService.writeAuditHistory("Failed to reboot/shutdown: incorrect username/password");
        }

        if ("OK".equals(ret)) {
            model.put("success", Boolean.TRUE);
        } else {
            FieldError fe = new FieldError("none", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_system_upgrade_upload.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceSystemUpgradeUploadAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        
        //uploading
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("PORTALarchive");

        String originalFileName = multipartFile.getOriginalFilename();
        originalFileName = originalFileName.replace("..", "").replace("/","");
        File uploadedFile = new File("/opt/vidyo/temp/tomcat/" + originalFileName);
        multipartFile.transferTo(uploadedFile);
        
        model.put("success", Boolean.TRUE);

        return new ModelAndView("ajax/result_ajax", "model", model);
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_system_upgrade.ajax", method = RequestMethod.POST)
    public ModelAndView systemUpgradeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        
        String fileName = ServletRequestUtils.getStringParameter(request, "PORTALarchive", "");
        if (fileName == null || fileName.isEmpty()) {
            routerConfigService.writeAuditHistory("Upgrade upload fail: empty file name");
        	FieldError fe = new FieldError("Maintenance", messages.getMessage("update.failed", null, "", LocaleContextHolder.getLocale()), 
        			messages.getMessage("file.upload.failed", null, "", LocaleContextHolder.getLocale()));
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
        }
        fileName = fileName.replace("..", "").replace("/","");
        File uploadedFile = new File("/opt/vidyo/temp/tomcat/" + fileName);
        String uploadedFileAbsolutePath = uploadedFile.getAbsolutePath();

        String uploadMessage = "";
        boolean processSuccess = false;

        if (uploadedFileAbsolutePath.endsWith(".vidyo")){

            if (uploadedFile.length() == 0) {
                uploadMessage = messages.getMessage("cannot.update.router.software.from.the.uploaded.file", null, LocaleContextHolder.getLocale());
                routerConfigService.writeAuditHistory("Upgrade upload fail: " + uploadMessage);
            } else {
                String command = "sudo -n /opt/vidyo/bin/update_portal.sh " + uploadedFileAbsolutePath;
                boolean errorFound = false;
                try{
                    logger.debug("process update router " + uploadedFileAbsolutePath);
                    ShellCapture capture = ShellExecutor.execute(command);
                    routerConfigService.writeAuditHistory("Upgrading with installer: " + fileName);
                    String stdOut = capture.getStdOut();
                    String stdErr = capture.getStdErr();

                    if(stdErr.contains("UPDATEFAILED")){
                        errorFound = true;
                    }
                    if(stdOut.contains("UPDATEFAILED")){
                        errorFound = true;
                    }

                    if(errorFound){
                        uploadMessage = messages.getMessage("cannot.update.router.software.from.the.uploaded.file", null, LocaleContextHolder.getLocale());
                        routerConfigService.writeAuditHistory("Upgrade installer fail: installer returned error");
                    }else{
                        processSuccess = true;
                    }

                } catch (ShellExecutorException e) {
                        logger.warn("Exception while processing update portal/router: " + e.getMessage());
                        logger.warn("Ignoring exception while processing update portal/router, as upgrade script probably causing a graceful tomcat shutdown.");
                        processSuccess = true;
                }
            }
        } else {
            uploadMessage = messages.getMessage("type.of.server.software.file.should.be.a.b.tag.vidyo.b", null, LocaleContextHolder.getLocale());
            routerConfigService.writeAuditHistory("Upgrade installer fail: invalid installer uploaded");
        }

        if (processSuccess) {
            model.put("success", Boolean.TRUE);
            routerConfigService.writeAuditHistory("Upgrade installer success");
        } else {
            FieldError fe;
            File failureMarker = new File("/opt/vidyo/temp/root/UPDATE_FAILED");
            if (failureMarker.exists()) {
                String msg = FileUtils.readFileToString(failureMarker);
                fe = new FieldError("Maintenance",  messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), msg);
            } else {
                fe = new FieldError("upload", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), uploadMessage);
            }
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        }


        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/update_log_settings.ajax", method = RequestMethod.POST)
    public ModelAndView systemUpdateLogSettingsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logLevel = routerConfigService.getRequestParameter("logLevel", request);
        logLevel = routerConfigService.stripBadCharacters(logLevel);

        String logMaxSize = routerConfigService.getRequestParameter("logMaxFileSize", request);
        logMaxSize = routerConfigService.stripBadCharacters(logMaxSize);

        String ret ="";
        if(logLevel.equals("")) {
            ret = "Log level cannot be blank.";
        } else if(logMaxSize.equals("")) {
            ret = "Max log file size cannot be blank.";
        } else {
            ret = routerConfigService.updateRouterConfigProperty("LogLevel", logLevel);
            if(ret.equals("OK")){
                try {
                    int size = Integer.parseInt(logMaxSize);
                    ret = routerConfigService.updateRouterConfigProperty("MaxLogFileSizeKB", ""+size);
                    routerConfigService.writeAuditHistory("Log Settings save success: logLevel=" + logLevel + " / logMaxSize=" + size);
                } catch (NumberFormatException nfe) {
                    // ignore
                }
            }
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if ("OK".equals(ret)) {
            model.put("success", Boolean.TRUE);
        } else {
            FieldError fe = new FieldError("logSettings", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            routerConfigService.writeAuditHistory("Log Settings save fail: logLevel=" + logLevel + " / logMaxSize=" + logMaxSize);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);

    }

    private boolean restartVC2() {
        try {
            ShellCapture capture = ShellExecutor.execute("sudo -n /opt/vidyo/bin/vidyo_server.sh START_VC2");
            return capture.isSuccessExitCode();
        } catch (ShellExecutorException e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/logs_download.html", method = RequestMethod.GET)
    public ModelAndView systemLogsDownloadAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileNames = routerConfigService.getRequestParameter("fileNames", request);
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (StringUtils.isNotBlank(fileNames)) {
            fileNames = fileNames.replace("..", "").replace("/", "");
            String[] selectedFiles = fileNames.split(" ");
            if (selectedFiles.length > 0) {
                List<String> files = new ArrayList<String>();
                for (String file : selectedFiles) {
                    files.add("/opt/vidyo/vidyorouter2/" + file);
                }
                try {
                    CompressionUtils.targzip(files.toArray(new String[files.size()]), getTmpDirectory() + "vr2logexport.tar.gz", true);
                } catch (Exception e) {
                    logger.debug("Exception routerLogDownloadController: " + e.getMessage());
                }
            }


            File ff = new File(getTmpDirectory() + "vr2logexport.tar.gz");

            response.setHeader("Pragma", "public");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Content-Description", "File Transfer");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"vr2logexport.tar.gz\"");
            response.setContentLength((int) ff.length());

            try (FileInputStream fin = new FileInputStream(ff);
                 ServletOutputStream outPut = response.getOutputStream()) {

                IOUtils.copy(fin, outPut);

            } catch (IOException e) {
                logger.debug("log download error: " + e.getMessage());
            } finally {
                FileUtils.deleteQuietly(ff);
            }
            routerConfigService.writeAuditHistory(loggedName, request.getRemoteHost(), "Downloaded log files");
        }

        return null;
    }

    @RequestMapping(value = "/audit_logs_download.html", method = RequestMethod.GET)
    public ModelAndView systemDownloadAuditLogs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();

        List<String> logFiles = new ArrayList<String>();

        File adminConsoleLogsDir = new File("/opt/vidyo/logs");
        if (adminConsoleLogsDir.exists() && adminConsoleLogsDir.isDirectory()) {
            File[] files = adminConsoleLogsDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("adminconsole.");
                }
            });
            if (files != null && files.length > 0) {
                for (File file : files) {
                    logFiles.add(file.getAbsolutePath());
                }
            }
        }

        File vrHistoryLogsDir = new File("/var/log/vidyo/");
        if (vrHistoryLogsDir.exists() && vrHistoryLogsDir.isDirectory()) {
            File[] files = vrHistoryLogsDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("vr_history");
                }
            });
            if (files != null && files.length > 0) {
                for (File file : files) {
                    logFiles.add(file.getAbsolutePath());
                }
            }
        }

		try {
            CompressionUtils.targzip(logFiles.toArray(new String[logFiles.size()]), getTmpDirectory() + "vr2history.tar.gz", true);
		} catch (Exception e) {
			logger.error("Failed systemDownloadAuditLogs: " + e.getMessage());
		}

        File ff = new File(getTmpDirectory() + "vr2history.tar.gz");
        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"vr2history.tar.gz\"");
        response.setContentLength((int) ff.length());

        try (FileInputStream fin = new FileInputStream(ff);
             ServletOutputStream outPut = response.getOutputStream()){

            IOUtils.copy(fin, outPut);

        } catch (IOException e){
            logger.debug("log history download error: " + e.getMessage());
        } finally {
            FileUtils.deleteQuietly(ff);
        }

        routerConfigService.writeAuditHistory(loggedName, request.getRemoteHost(), "Downloaded audit history log files");

        return null;
    }

    @RequestMapping(value = "/securedmaint/maintenance_logs.ajax", method = RequestMethod.POST)
    public ModelAndView systemLogsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ConfigLogFile > configLogFiles = routerConfigService.getRouterConfigLogFiles();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("num", configLogFiles.size());
        model.put("logFiles", configLogFiles);
        return new ModelAndView("ajax/logs_ajax", "model", model);
    }

    @RequestMapping(value = "/network.html", method = RequestMethod.GET)
    public ModelAndView getNetworkHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        String user = "unknown";
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            user = authentication.getName();
        }
        model.put("userName", user );
        return new ModelAndView("network_html", "model", model);
    }

    @RequestMapping(value = "/network.ajax", method = RequestMethod.POST)
    public ModelAndView getNetworkAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Network network = this.network.getNetworkSettings();
        model.put("network", network);
        return new ModelAndView("ajax/network_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_system_restart.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceSystemRestart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FieldError> errors = new ArrayList<FieldError>();
        Map<String, Object> model = new HashMap<String, Object>();

        if(!routerConfigService.restart()){
            FieldError fe = new FieldError("Maintenance", messages.getMessage("internal.error", null, "",  LocaleContextHolder.getLocale()), messages.getMessage("failed.to.reboot.the.system", null, "",  LocaleContextHolder.getLocale()));
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

    @RequestMapping(value = "/serverstartedtime.ajax", method = RequestMethod.POST)
    public ModelAndView getServerStartedAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        long timestamp = getServerRestartedTimestamp();
        model.put("serverStartedTimestamp", ""+timestamp);

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(timestamp);

        String serverStarted = (cal.get(Calendar.MONTH)+1)+"/"+
                cal.get(Calendar.DATE)+"/"+
                cal.get(Calendar.YEAR)+" "+
                cal.get(Calendar.HOUR_OF_DAY)+":"+
                cal.get(Calendar.MINUTE)+":"+
                cal.get(Calendar.SECOND)+":"+
                cal.get(Calendar.MILLISECOND);
        model.put("serverStarted", serverStarted);

        return new ModelAndView("ajax/server_started_time_ajax", "model", model);
    }

    public ModelAndView getVidyoRouterType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String vidyoRouterType = routerConfigService.getRouterType();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("vidyoRouterType", vidyoRouterType);
        return new ModelAndView("ajax/vidyoroutertype_ajax", "model", model);
    }

    public static long getServerRestartedTimestamp() {

        File dmesgLog = new File("/var/log/dmesg");
        if (dmesgLog.exists()) {
            return dmesgLog.lastModified();
        }

        String[] cmd = {"/bin/bash", "-c", "who -b"};
        try {
            ShellCapture capture = ShellExecutor.execute(cmd);
            String output = capture.getStdOut();
            if (!StringUtils.isBlank(output)) {
                logger.warn("who -b returned: " + output);
                output = output.replace("system boot", "");
                output = output.trim();
                if (output.startsWith("20")) {
                    // system boot 2013-04-22 09:24
                    logger.warn(" date: " + output);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date dateObj = dateFormat.parse(output);
                    return dateObj.getTime();
                } else {
                    // system boot  Oct  6 13:50
                    logger.warn(" date: " + output);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm");
                    Date dateObj = dateFormat.parse(output);
                    return dateObj.getTime();
                }
            }
        } catch (ShellExecutorException se) {
            se.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return System.currentTimeMillis() - 5 * 60 * 1000; // -5 min
    }

    @RequestMapping(value = "/securedmaint/maintenance_diagnostics_run.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceDiagnosticsRun(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        if (!diagnosticsService.runDiagnostics()) {
            List<FieldError> errors = new ArrayList<FieldError>();
            FieldError fe = new FieldError("Run", "Diagnostics", "Error running diagnostics.");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", false);
            routerConfigService.writeAuditHistory("Diagnostics run fail");
        } else {
            routerConfigService.writeAuditHistory("Diagnostics run success");
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/securedmaint/maintenance_diagnostics_list.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceDiagnosticsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<DiagnosticReport> reports =  diagnosticsService.getDiagnosticReports();
        model.put("num", reports.size());
        model.put("reports", reports);
        return new ModelAndView("ajax/diagnostics_list_ajax", "model", model);
    }

    @RequestMapping(value = "/maintenance_diagnostics_view.html", method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsView(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String file = request.getParameter(("f"));
        String contents = diagnosticsService.getDiagnosticReport(file);
        if ("".equals(contents)) {
            routerConfigService.writeAuditHistory("Diagnostics view fail: " + file);
        } else {
            routerConfigService.writeAuditHistory("Diagnostics view success: " + file);
        }
        model.put("contents", contents.replace("\n", "<br />").
                replace("STATUS=ERROR", "<b style='color: red'>STATUS=ERROR</b>").
                replace("STATUS=FAIL", "<b style='color: red'>STATUS=FAIL</b>" ).
                replace("STATUS=WARNING", "<b style='color: red'>STATUS=WARNING</b>" ).
                replace("STATUS=PASS", "<b style='color: green'>STATUS=PASS</b>" )
        );
        return new ModelAndView("ajax/diagnostics_report_ajax", "model", model);
    }

    @RequestMapping(value = "/maintenance_diagnostics_download.html", method = RequestMethod.GET)
    public ModelAndView maintenanceDiagnosticsDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String file = request.getParameter(("f"));
        String contents = diagnosticsService.getDiagnosticReport(file);

        if ("".equals(contents)) {
            routerConfigService.writeAuditHistory("Diagnostics download fail: " + file);
        } else {
            routerConfigService.writeAuditHistory("Diagnostics download success: " + file);
        }
        // send download
        response.reset();

        response.setHeader("Pragma", "public");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file  + "\"");
        response.setHeader("Content-Transfer-Encoding", "quoted-printable");
        response.addHeader("Content-Length", ""+contents.length());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        IOUtils.copy(IOUtils.toInputStream(contents), out);

        out.flush();

        return null;
    }
    
    @RequestMapping(value = "/securedmaint/maintenance_install_log_list.ajax", method = RequestMethod.POST)
    public ModelAndView maintenanceInstallationLogList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<InstallationReport> reports = installationService.getInstallationReportList();
        model.put("num", reports.size());
        model.put("reports", reports);
        return new ModelAndView("ajax/installations_list_ajax", "model", model);
    }

    @RequestMapping(value = "/maintenance_download_install_log.html", method = RequestMethod.GET)
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
    
    @RequestMapping(value = "/maintenance_installed_patches.html", method = RequestMethod.GET)
    public ModelAndView maintenanceInstalledPatches(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<InstallationReport> patches = installationService.getInstalledPatches(); 
        model.put("patches", patches);
        model.put("num", patches.size());
        return new ModelAndView("ajax/installed_patches_ajax", "model", model);
    }

    public void setTmpDirectory(String tmpDirectory) {
        this.tmpDirectory = tmpDirectory;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }
}
