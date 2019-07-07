package com.vidyo.web;

import com.vidyo.bo.ApplicationConfiguration;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.security.CRTObject;
import com.vidyo.bo.security.CSRObject;
import com.vidyo.bo.security.ConfigProperty;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.ISecurityService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.SecurityServiceImpl;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.exceptions.SecurityServiceException;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.superapp.components.service.ComponentsService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SecurityController {
	protected final Logger logger = LoggerFactory.getLogger(SecurityController.class.getName());

	@Autowired
	private IUserService userService;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private ISystemService systemService;

	@Autowired
	private CookieLocaleResolver localeResolver;

	@Autowired
	private MessageSource messages;

	private Set<String> excludeApps = Collections.unmodifiableSet(
			new HashSet<>(Arrays.asList(
					"ROOT",
					"upload",
					"manager",
					"host-manager",
					"media",
					"portal",
					"web",
					"installer"))
	);

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private ComponentsService componentsService;

	private String tmpDirectory = "/opt/vidyo/temp/tomcat/";

	private String getRequestParameter(String name, HttpServletRequest request){
		String ret = request.getParameter(name);
		if(ret == null){ ret = "";}
		ret = ret.trim();

		return ret;
	}

	@RequestMapping(value = "/security.html", method = GET)
	public ModelAndView getSecurityHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "settings");
		userService.setLoginUser(model, response);
		String guideLoc = systemService.getGuideLocation(localeResolver.resolveLocale(request).getLanguage(), "admin");
		model.put("guideLoc", guideLoc);
		model.put("sslEnabledFlag", securityService.isSSLEnabled() ? "1" : "0");
		model.put("httpsOnlyFlag", securityService.isForcedHTTPSEnabled() ? "1" : "0");
		model.put("httpsOnlyFlagNoRedirect", securityService.isForcedHTTPSEnabledNoRedirect() ? "1" : "0");
		model.put("useDefaultRootCerts", securityService.isUseDefaultRootCerts() ? "true" : "false");

		// ocsp settigns
		List<ConfigProperty> ocspConfigProps            = securityService.getOCSPConfigProperties();
		ConfigProperty SSLOCSPEnableProp                = securityService.getOCSPConfigProperty("SSLOCSPEnable", ocspConfigProps);
		ConfigProperty SSLOCSPOverrideResponderProp     = securityService.getOCSPConfigProperty("SSLOCSPOverrideResponder", ocspConfigProps);
		ConfigProperty SSLOCSPDefaultResponderProp      = securityService.getOCSPConfigProperty("SSLOCSPDefaultResponder", ocspConfigProps);

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
		//removed as per vptl 3545
		//model.put("apacheVersion", apacheVersion);
		model.put("ocspSupportFlag", apacheOCSPSupport);
		Configuration config = systemService.getConfiguration("COMPONENTS_ENCRYPTION");
		if(config != null && config.getConfigurationValue() != null) {
			model.put("encryptionEnabledFlag", Boolean.valueOf(config.getConfigurationValue()));
		} else {
			model.put("encryptionEnabledFlag", false);
		}

		File privModeFileHandle = new File(SecurityServiceImpl.SSL_HOME + "private/PRIVILEGED_MODE");
		model.put("privilegedMode", (privModeFileHandle != null && privModeFileHandle.exists()));

		return new ModelAndView("super/security_html", "model", model);
	}

	@RequestMapping(value = "/security/security_key.ajax", method = GET)
	public ModelAndView getKeyAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("bits", securityService.getPrivateKeyBits());
		String privateKeyHash = "";
		try {
			privateKeyHash = securityService.getKeyHash();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SecurityServiceException sse) {
			sse.printStackTrace();
		}
		model.put("keyHash", privateKeyHash);
		return new ModelAndView("ajax/security_key_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_csr.ajax", method = GET)
	public ModelAndView getCsrAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		CSRObject csr = securityService.parseCSR();
		model.put("csrMatchesKey", securityService.isCSRMatchToKey(SecurityServiceImpl.FILE_SIGNING_REQUEST));
		model.put("country", csr.getCountryName());
		model.put("state", csr.getStateOrProvinceName());
		model.put("city", csr.getLocalityName());
		model.put("company", csr.getOrganizationName());
		model.put("division", csr.getOrganizationalUnitName());
		model.put("domain", csr.getCommonName());
		model.put("email", csr.getEmailAddress());
		model.put("csr", securityService.getCSRContent());
		return new ModelAndView("ajax/security_csr_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_server_cert.ajax", method = GET)
	public ModelAndView getServerCertAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		CRTObject crt = securityService.parseCert(SecurityServiceImpl.FILE_SERVER_CERT);
		model.put("crtMatchesKey", securityService.isCertMatchToKey(SecurityServiceImpl.FILE_SERVER_CERT));
		model.put("crtMatchesFQDN", securityService.isCertMatchToFQDN(crt, componentsService.getManagerFQDN()));
		model.put("version", crt.getVersion());
		model.put("fingerprint", crt.getFingerprint());
		model.put("serial", crt.getSerial());
		model.put("notBefore", crt.getNotBefore());
		model.put("notValidYet", crt.getNotYetValid() ? "1" : "0" );
		model.put("notAfter", crt.getNotAfter());
		model.put("expired", crt.getExpired() ? "1" : "0");
		model.put("issuerCountry", crt.getIssuerC());
		model.put("issuerState", crt.getIssuerST());
		model.put("issuerCity", crt.getIssuerL());
		model.put("issuerCompany", crt.getIssuerO());
		model.put("issuerDivision", crt.getIssuerOU());
		model.put("issuerDomain", crt.getIssuerCN());
		model.put("issuerEmail", crt.getIssuerEMAIL());
		model.put("subjectCountry", crt.getSubjectC());
		model.put("subjectState", crt.getSubjectST());
		model.put("subjectCity", crt.getSubjectL());
		model.put("subjectCompany", crt.getSubjectO());
		model.put("subjectDivision", crt.getSubjectOU());
		model.put("subjectDomain", crt.getSubjectCN());
		model.put("subjectEmail", crt.getSubjectEMAIL());
		model.put("san", StringUtils.join(crt.getSubjectAltNames().toArray(), ", "));
		model.put("cert", securityService.getCertContent());
		model.put("fqdn", componentsService.getManagerFQDN());
		return new ModelAndView("ajax/security_server_cert_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_server_ca_cert.ajax", method = GET)
	public ModelAndView getServerCACertAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();

		List<CRTObject> certIntBundleList = new ArrayList<CRTObject>();
		List<String> tempCertFileNames  =  securityService.parseCertBundle(SecurityServiceImpl.FILE_SERVER_INTERMEDIATE_CERT);
		for(int i=0; i < tempCertFileNames.size(); i++){
			String tempCertFileName = tempCertFileNames.get(i);
			if(!securityService.isCertInvalid(tempCertFileName)){
				CRTObject crt = securityService.parseCert(tempCertFileName);
				certIntBundleList.add(crt);
			}
			File tempCertFile = new File(tempCertFileName);
			tempCertFile.delete();
		}
		model.put("caCerts", certIntBundleList);
		model.put("caCert", securityService.getCertBundleContent());
		return new ModelAndView("ajax/security_server_ca_cert_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_generate_key.ajax", method = POST)
	public ModelAndView generatePrivateKeyAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		String bits =  getRequestParameter("bits", request);
		try {
			securityService.generateKey(Integer.valueOf(bits));
			model.put("success", Boolean.TRUE);
		} catch (NumberFormatException nfe) {
			logger.error("provided value for bits is not a number: " + bits);
			model.put("success", Boolean.FALSE);
		} catch (SecurityServiceException sse) {
			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_generate_cert.ajax", method = POST)
	public ModelAndView generateSelfSignedCert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String result = "";
		if (StringUtils.isEmpty(securityService.getCSRContent())) {
			result = messages.getMessage("super.security.ssl.you.must.fill.out.the.csr.section.first.in.the.preceding.tab", null, LocaleContextHolder.getLocale());
		} else if (!securityService.isCSRMatchToKey(SecurityServiceImpl.FILE_SIGNING_REQUEST)) {
			result =  messages.getMessage("super.security.ssl.manage.csr.current.key.mismatch.error", null, LocaleContextHolder.getLocale());
		} else {
			result = securityService.generateCert();
		}

		if ("OK".equals(result)) {
			model.put("success", Boolean.TRUE);
			if (securityService.isSSLEnabled()) {
				securityService.applyApacheChanges();
			}
		} else {
			FieldError fe = new FieldError("cert", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), result);
			errors.add(fe);
			model.put("success", Boolean.FALSE);
		}

		if (errors.size()!=0) {
			model.put("fields", errors);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	@RequestMapping(value = "/security/security_key_update.ajax", method = POST)
	public ModelAndView updatePrivateKeyAjax(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String key = getRequestParameter("key", request);
		String result ="OK";

		String tempKeyFile = getTmpDirectory() + securityService.generateRandomString(10);
		File tempKey = new File(tempKeyFile);
		FileUtils.writeStringToFile(tempKey, key);
		String sslKeyStatus = securityService.checkKeyStatus(tempKeyFile);
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Update Private Key");
		transactionHistory.setTransactionParams("Update Private Key");
		if(sslKeyStatus.equals("OK")){
			if(!securityService.updatePrivateKey(tempKeyFile)) {
				result = "Failed to write/process private key";
			}
		} else {
			result = messages.getMessage("super.security.ssl.manage.key.update.error", null, LocaleContextHolder.getLocale());
			FieldError fe = new FieldError("key", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), result);
			errors.add(fe);
		}
		tempKey.delete();
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		String tenantName = null;
		if(tenant == null) {
			tenantName = "Default";
		} else {
			tenantName = tenant.getTenantName();
		}
		transactionHistory.setTenantName(tenantName);
		if ("OK".equals(result)) {
			model.put("success", Boolean.TRUE);
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			if (errors.size()!=0) {
				model.put("fields", errors);

			}
			model.put("success", Boolean.FALSE);
			transactionHistory.setTransactionResult("FAILURE");
		}
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_generate_csr.ajax", method = POST)
	public ModelAndView generateCSRAjax(HttpServletRequest request, HttpServletResponse response)  {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean success = true;
		List<FieldError> errors = new ArrayList<FieldError>();

		String countryName             = getRequestParameter("country", request);
		String stateOrProvinceName    = getRequestParameter("state", request);
		String localityName           = getRequestParameter("city", request);
		String organizationName       = getRequestParameter("company", request);
		String organizationalUnitName = getRequestParameter("division", request);
		String commonName             = getRequestParameter("domain", request);
		String emailAddress           = getRequestParameter("email", request);

		CSRObject csr = new CSRObject();
		csr.setCountryName(countryName);
		csr.setStateOrProvinceName(stateOrProvinceName);
		csr.setLocalityName(localityName);
		csr.setOrganizationName(organizationName);
		csr.setOrganizationalUnitName(organizationalUnitName);
		csr.setCommonName(commonName);
		csr.setEmailAddress(emailAddress);

		String ret = securityService.generateCSR(csr);
		if ("OK".equals(ret)) {
			model.put("success", Boolean.TRUE);
		} else {
			FieldError fe = new FieldError("csr", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	@RequestMapping(value = "/security/security_server_cert_update.ajax", method = POST)
	public ModelAndView updateServerCertAjax(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean success = true;
		List<FieldError> errors = new ArrayList<FieldError>();

		String ret="OK";

		String cert = getRequestParameter("cert", request);
		String tempCertFile = getTmpDirectory() + securityService.generateRandomString(10);
		File tempCert = new File(tempCertFile);
		FileUtils.writeStringToFile(tempCert, cert);
		if(securityService.isCertInvalid(tempCertFile)){
			ret = messages.getMessage("super.security.ssl.manage.cert.update.error", null, LocaleContextHolder.getLocale());
		} else if(!securityService.isCertMatchToKey(tempCertFile)){
			// Validate if the cert matches key - copied from uploadCert API
			ret = messages.getMessage("super.security.ssl.manage.cert.update.key.mismatch.error", null, LocaleContextHolder.getLocale());
        } else {
			try {
				securityService.uploadDomainCert(tempCertFile);
			} catch (SecurityServiceException sse) {
				ret = "Failed to write server cert to disk.";
			}
		}

		tempCert.delete();
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Update Server Certificate");
		transactionHistory.setTransactionParams("Update Server Certificate");
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		String tenantName = null;
		if(tenant == null) {
			tenantName = "Default";
		} else {
			tenantName = tenant.getTenantName();
		}
		transactionHistory.setTenantName(tenantName);
		if ("OK".equals(ret)) {
			if (securityService.isSSLEnabled()) {
				securityService.applyApacheChanges();
			}
			model.put("success", Boolean.TRUE);
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			FieldError fe = new FieldError("cert", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			transactionHistory.setTransactionResult("FAILURE");
		}
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_export_key.ajax", method = POST)
    public ModelAndView exportPrivateKeyAjax(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        String password = getRequestParameter("keyPassword", request);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionName("Export Private Key");
        Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
        String tenantName = null;
        if(tenant == null) {
            tenantName = "Default";
        } else {
            tenantName = tenant.getTenantName();
        }
        transactionHistory.setTenantName(tenantName);

        String tmpFilePath = getTmpDirectory() + securityService.generateRandomString(10) + ".enc.key";
        File tmpFile = new File(tmpFilePath);

        if (securityService.encryptPrivateKey(tmpFilePath, password)) {
            if(!tmpFile.exists()) {
                return null;
            }
            long fileSize = tmpFile.length();

            response.reset();

            response.setHeader("Pragma", "public");
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "private.key"  + "\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            //response.setContentLength((int)fileSize);
            response.addHeader("Content-Length", Long.toString(fileSize));

            response.setContentType("application/octet-stream");

            ServletOutputStream outPut = response.getOutputStream();

            byte[] bbuf = new byte[4096];
            int length = -1;
            DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
            while (((length = in.read(bbuf)) != -1)) {
                outPut.write(bbuf,0,length);
            }
            in.close();
            outPut.flush();
            outPut.close();

            FileUtils.deleteQuietly(tmpFile);

            transactionHistory.setTransactionParams("password entered");
            transactionHistory.setTransactionResult("SUCCESS");
            transactionService.addTransactionHistoryWithUserLookup(transactionHistory);

            return null;
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            String errorMessage = messages.getMessage("failed.to.encrypt.and.export.the.private.key", null, "",  LocaleContextHolder.getLocale());
            if ("".equals(password.trim())) {
                errorMessage = messages.getMessage("password.cannot.be.empty.or.consist.of.only.whitespace", null, "", LocaleContextHolder.getLocale());
            }
            FieldError fe = new FieldError("cert", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), errorMessage);
            List<FieldError> errors = new ArrayList<FieldError>();
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);

            transactionHistory.setTransactionResult("FAILURE");
            transactionHistory.setTransactionParams(errorMessage);
            transactionService.addTransactionHistoryWithUserLookup(transactionHistory);

            return new ModelAndView("ajax/result_ajax", "model", model);
        }
    }

	@RequestMapping(value = "/security/security_upload_file.ajax", method = POST)
	public ModelAndView uploadFileAjax(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean success = true;
		List<FieldError> errors = new ArrayList<FieldError>();

		String ret="";


		String sslUploadType = getRequestParameter("uploadType", request);

		boolean uploadSuccess = false;
		String uploadMessage = "";
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionName("Security File Upload");

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile file = multipartRequest.getFile("uploadFile");
        if (file.getSize() < 10485760l) { // 10MB = 1024 * 1024 * 10 = 10485760 bytes
            String lines_concat = "";
            List<String> lines = new ArrayList<String>();
            try{
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(file.getInputStream()));
                String strLine;
                String linesAccum = "";
                while ((strLine = stdInput.readLine()) != null)   {
                    lines.add(strLine);
                    lines_concat = lines_concat + strLine;
                    linesAccum   = linesAccum +strLine.trim() + "\n";
                }
                stdInput.close();

                //////////////// PRIVATE KEY
                if(sslUploadType.equals("key")){
                    transactionHistory.setTransactionName("Upload Private Key");
                    String tempKeyFile = getTmpDirectory() + securityService.generateRandomString(10);
                    File tempKey = new File(tempKeyFile);
                    FileUtils.writeLines(tempKey, lines);

                    String password = getRequestParameter("keyPassword", request);

                    String tempDecryptedKeyFile = getTmpDirectory() + securityService.generateRandomString(10);
                    if (securityService.decryptPrivateKey(tempKeyFile, tempDecryptedKeyFile, password)) {
                        File unencryptedFile = new File(tempDecryptedKeyFile);
                        FileUtils.deleteQuietly(unencryptedFile);
                        securityService.mergeKeyAndCert();
                        uploadMessage = messages.getMessage("super.security.ssl.key.upload.success", null, LocaleContextHolder.getLocale());
                        uploadSuccess = true;
                    } else {
                        uploadMessage = messages.getMessage("failed.to.decrypt.private.key", null, "", LocaleContextHolder.getLocale());
                    }

                    FileUtils.deleteQuietly(tempKey);

                }

                //////////////// SERVER CERT

                if(sslUploadType.equals("cert")){
                    String tempCertFile = getTmpDirectory() + securityService.generateRandomString(10);
                    File tempCert = new File(tempCertFile);
                    file.transferTo(tempCert);
                    transactionHistory.setTransactionName("Upload Server Certificate(s)");
                    if (securityService.isCertFormatDER(tempCertFile)) {
                        String tempCertPEMFile = getTmpDirectory() + securityService.generateRandomString(10);
                        if (!securityService.convertDERtoPEM(tempCertFile, tempCertPEMFile)) {
                            uploadMessage = messages.getMessage("super.security.ssl.cert.upload.error", null, LocaleContextHolder.getLocale());
                        }
                        tempCert.delete();
                        tempCertFile = tempCertPEMFile;
                        tempCert = new File(tempCertPEMFile);
                    }

                    if((securityService.isCertInvalid(tempCertFile)) || lines_concat.trim().equals("")){
                        uploadMessage = messages.getMessage("super.security.ssl.cert.upload.error", null, LocaleContextHolder.getLocale());
                    } else if(!securityService.isCertMatchToKey(tempCertFile)){
                        uploadMessage = messages.getMessage("super.security.ssl.manage.cert.update.key.mismatch.error", null, LocaleContextHolder.getLocale());
                    } else {
                        try {
                        	securityService.uploadDomainCert(tempCertFile);
                            uploadMessage = messages.getMessage("super.security.ssl.cert.upload.success", null, LocaleContextHolder.getLocale());
                            uploadSuccess = true;
                        } catch(SecurityServiceException sse) {
                            uploadMessage = messages.getMessage("super.security.ssl.cert.upload.error", null, LocaleContextHolder.getLocale());
                        }
                    }
                    tempCert.delete();
                }

                //////////////// SERVER INTERMEDIATES

                if(sslUploadType.equals("certInt")){
                    boolean foundErrors = false;
                    String tempCertIntBundleFile = getTmpDirectory() + securityService.generateRandomString(10);
                    File tempCertBundle = new File(tempCertIntBundleFile);
                    file.transferTo(tempCertBundle);
                    transactionHistory.setTransactionName("Upload Server Certificate Intermediates");
                    if(securityService.isCertFormatDER(tempCertIntBundleFile)) {
                        String tempCertIntPEMFile = getTmpDirectory() + securityService.generateRandomString(10);
                        if (!securityService.convertDERtoPEM(tempCertIntBundleFile, tempCertIntPEMFile)) {
                            uploadMessage = messages.getMessage("super.security.ssl.cert.upload.error", null, LocaleContextHolder.getLocale());
                        }
                        tempCertBundle.delete();
                        tempCertIntBundleFile = tempCertIntPEMFile;
                        tempCertBundle = new File(tempCertIntPEMFile);
                    }

                    if(securityService.isCertBundleValid(tempCertIntBundleFile)){
                        List<String> tempCertFileNames  =  securityService.parseCertBundle(tempCertIntBundleFile);
                        for(int i=0; i < tempCertFileNames.size(); i++){
                            String tempCertFileName = tempCertFileNames.get(i);
                            if(securityService.isCertInvalid(tempCertFileName)){
                                foundErrors = true;
                            }
                            File tempCertFile = new File(tempCertFileName);
                            tempCertFile.delete();
                        }
                    }else{
                        foundErrors = true;
                    }

                    if(foundErrors || lines_concat.trim().equals("")){
                        uploadMessage = messages.getMessage("super.security.ssl.certint.upload.error", null, LocaleContextHolder.getLocale());
                    } else {
                        try {
							securityService.uploadCertInt(tempCertIntBundleFile);
	                        uploadMessage = messages.getMessage("super.security.ssl.certint.upload.success", null, LocaleContextHolder.getLocale());
	                        uploadSuccess = true;
						} catch (Exception e) {
	                        uploadMessage = messages.getMessage("super.security.ssl.certint.upload.error", null, LocaleContextHolder.getLocale());
	                        uploadSuccess = false;
						}
                    }
                    tempCertBundle.delete();
                }

            } catch (IOException e){
                e.printStackTrace();
            }
		} else {
            uploadMessage = "File size must be less than 10MB.";
        }

		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		String tenantName = null;
		if(tenant == null) {
			tenantName = "Default";
		} else {
			tenantName = tenant.getTenantName();
		}
		transactionHistory.setTenantName(tenantName);
		if (uploadSuccess) {
			model.put("success", Boolean.TRUE);
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			FieldError fe = new FieldError("cert", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), uploadMessage);
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			transactionHistory.setTransactionResult("FAILURE");
		}
		if(file != null && file.getOriginalFilename() != null) {
			transactionHistory.setTransactionParams(file.getOriginalFilename());
		}
		transactionService.addTransactionHistoryWithUserLookup(transactionHistory);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_command.ajax", method = POST)
	public ModelAndView handleSecurityCommand(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String command = getRequestParameter("command", request);

		String ret="";
		if(command.equals("enable")){
			ret = securityService.enableSSL();
		}
		if(command.equals("disable")){
			ret = securityService.disableSSL();
		}
		if(command.equals("forcedEnable")){
			ret = securityService.enableForcedHttps();
		}
		if(command.equals("forcedEnableNoRedirect")){
			ret = securityService.enableForcedHttpsNoRedirect();
		}
		if(command.equals("forcedDisable")){
			ret = securityService.disableForcedHttps();
		}
		if(command.equals("componentsEncryption")) {
			boolean encryption = ServletRequestUtils.getBooleanParameter(request, "encryption", false);
			//Check license
			try {
				int updated = componentsService.enableDisableComponentsEncryption(Boolean.valueOf(encryption));
				if(updated > 0) {
					ret = "OK";
				}
			} catch (Exception e) {
				logger.error("Error while enabling components encryption", e);
			}
		}

		if ("OK".equals(ret)) {
			model.put("success", Boolean.TRUE);
		} else {
			FieldError fe = new FieldError("ssl", messages.getMessage("error", null, "", LocaleContextHolder.getLocale()), ret);
			errors.add(fe);
			model.put("fields", errors);

			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	@RequestMapping(value = "/security/security_applications_get.ajax", method = GET)
	public ModelAndView getApplicationsInfo(HttpServletRequest request,
	                                        HttpServletResponse response) {
		logger.debug("Entering getApplicationsInfo() of SuperSSLApplicationsController");
		Map<String, Object> model = new HashMap<String, Object>();
		// Get the list of deployed applications from the directory
		String appServerHome = System.getProperty("catalina.home");
		if (appServerHome == null) {
			// TODO inject it through the spring config
			appServerHome = "/usr/local/tomcat";
		}
		File appServerDir = new File(appServerHome.concat("/webapps"));
		String[] webapps = appServerDir.list(DirectoryFileFilter.INSTANCE);
		List<String> appNames = new ArrayList<String>();
		if ((Arrays.asList(webapps).contains("super"))) {
			// this is a portal, not a standalone vr
			appNames.add("admin");
		}
		for (String appName : webapps) {
			if (!excludeApps.contains(appName)) {
				appNames.add(appName);
			}
		}
		List<ApplicationConfiguration> applicationConfigurations = securityService
				.getAppsPortInfo(appNames);
		model.put("appConfigs", applicationConfigurations);
		logger.debug("Exiting getApplicationsInfo() of SuperSSLApplicationsController");
		return new ModelAndView("ajax/appconfiginfo_ajax", "model", model);
	}

	private List<String> getListOfUnavailablePorts() {
		try {
			String[] cmd = new String[] {"sudo", "-n", SecurityServiceImpl.SCRIPT_USED_TCP_PORT_BY_PROCESS, "apache2"};
			ShellCapture output = ShellExecutor.execute(cmd);
			if (!StringUtils.isEmpty(output.getStdErr())) {
				return Collections.<String>emptyList();
			}
			return output.getStdOutLines();
		} catch (ShellExecutorException see) {
			return Collections.<String>emptyList();
		}
	}

	@RequestMapping(value = "/security/security_applications_update.ajax", method = POST)
	public ModelAndView updateAppSettings(HttpServletRequest request,
	                                      HttpServletResponse response) {
		logger.debug("Entering updateAppSettings() of SuperSSLApplicationsController");
		String appConfigParam = ServletRequestUtils.getStringParameter(request,
				"appconfig", null);
		Map<String, Object> model = new HashMap<String, Object>();
		if (appConfigParam == null || appConfigParam == "") {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}
		// TODO - Validate this input
		String[] appConfigs = appConfigParam.split("\\|");
		List<FieldError> errors = new ArrayList<FieldError>();
		for (String appConfigVal : appConfigs) {
			String[] tokens = appConfigVal.split(":");
			String securePort = null;
			String unsecurePort = null;
			String ocsp = null;
			if (tokens != null && tokens.length == 5) {
				unsecurePort = tokens[2];
				securePort = tokens[3];
				ocsp = tokens[4];
			} else {
				securePort = tokens[2];
				ocsp = tokens[3];
			}
			List<String> usedPorts = getListOfUnavailablePorts();
			if (usedPorts.contains(unsecurePort)) {
				FieldError fe = new FieldError("unsecurePort", tokens[0],
						"Application \"" + tokens[0] + "\" HTTP port " + securePort + " " +
								messages.getMessage("super.security.ssl.already.being.used.please.choose.another", null, LocaleContextHolder.getLocale()));
				errors.add(fe);
			} else if (usedPorts.contains(securePort)) {
				FieldError fe = new FieldError("securePort", tokens[0],
						"Application \"" + tokens[0] + "\" HTTPS port " + securePort + " " +
								messages.getMessage("super.security.ssl.already.being.used.please.choose.another", null, LocaleContextHolder.getLocale()));
				errors.add(fe);
			} else {
				boolean result = securityService.updateAppAccessConfiguration(tokens[0],
						tokens[1], unsecurePort, securePort, ocsp);
				if (!result) {
					FieldError fe = new FieldError("Update Application Access",
							"Error while updating access", "Error while updating access - " + tokens[0]);
					errors.add(fe);
				}
			}
		}
		boolean restarted = false;
		model.put("success", Boolean.TRUE);
		if(errors.isEmpty()) {
			restarted = "OK".equals(securityService.applyApacheChanges());
		} else {
			model.put("success", Boolean.FALSE);
		}

		if(!restarted) {
			model.put("success", Boolean.FALSE);
		}
		if(!errors.isEmpty()) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		}
		logger.debug("Exiting updateAppSettings() of SuperSSLApplicationsController");
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_applications_network_interfaces.ajax", method = GET)
	public ModelAndView getNetworkInterfaces(HttpServletRequest request,
	                                         HttpServletResponse response) {
		logger.debug("Entering getNetworkInterfaces() of SuperSSLApplicationsController");
		Map<String, Object> model = new HashMap<String, Object>();
		// Get the available interfaces
		List<String> interfaces = securityService.getNetworkInterfaces("interfaces");
		model.put("interfaces", interfaces);
		logger.debug("Exiting getNetworkInterfaces() of SuperSSLApplicationsController");
		return new ModelAndView("ajax/networkinterfaces_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_advanced_ocsp.ajax", method = POST)
	public ModelAndView saveOcspSettings(HttpServletRequest request,
	                                     HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String ocspEnable     = getRequestParameter("ocspEnable", request);
        boolean ocspEnableFlag = "on".equals(ocspEnable);

		String overrideResponder     = getRequestParameter("overrideResponder", request);
        boolean overrideResponderFlag = "on".equals(overrideResponder);

		String responderURL     = getRequestParameter("defaultResponder", request);
		if (StringUtils.isBlank(responderURL)) {
			overrideResponderFlag = false;
		}

		model.put("success", Boolean.FALSE);

		int result = securityService.setOCSPConfigProperties(ocspEnableFlag, overrideResponderFlag, responderURL);
		if (result >= 0) {
			securityService.applyApacheChanges();
			model.put("success", Boolean.TRUE);
		} else {

			FieldError fe = new FieldError("ocspEnable",
					"ocspEnable","Error occurred saving OCSP settings.");
			errors.add(fe);
			model.put("fields", errors);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_advanced_export_bundle.ajax", method = GET)
	public ModelAndView handleExportBundle(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String password = getRequestParameter("bundlePassword", request);

		String fn = securityService.exportVidyoSecurityBundle(password);
		if((fn==null) || (fn.length()==0)) {
			return null;
		}

		File downloadFile = new File(getTmpDirectory()+fn);
		if(!downloadFile.exists()) {
			return null;
		}
		long fileSize = downloadFile.length();

		response.reset();

		response.setHeader("Pragma", "public");
		response.setHeader("Expires","0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fn  + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//response.setContentLength((int)fileSize);
		response.addHeader("Content-Length", Long.toString(fileSize));

		if(fn.endsWith(".tgz")) {
			response.setContentType("application/x-compressed-tar");
		}
		else {
			response.setContentType("application/octet-stream");
		}
		ServletOutputStream outPut = response.getOutputStream();

		byte[] bbuf = new byte[4096];
		int length = -1;
		DataInputStream in = new DataInputStream(new FileInputStream(downloadFile));
		while (((length = in.read(bbuf)) != -1)) {
			outPut.write(bbuf,0,length);
		}
		in.close();
		outPut.flush();
		outPut.close();
		FileUtils.deleteQuietly(downloadFile);
		return null;
	}

	@RequestMapping(value = "/security/security_use_default_root.ajax", method = POST)
	public ModelAndView useDefaultRootCerts(HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		String useDefault = getRequestParameter("useDefault", request);
		if ("on".equals(useDefault)) {
			success = securityService.setUseDefaultRootCerts(true);
		} else if ("off".equals(useDefault)) {
			success = securityService.setUseDefaultRootCerts(false);
		} else {
			// error
		}
		if (!success) {
			errors.add(new FieldError("useDefault", "useDefault", "Failed to update configuration."));
			model.put("success", Boolean.FALSE);
			model.put("fields", errors);
		} else {
			model.put("success", Boolean.TRUE);
		}
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_advanced_upload_root.ajax", method = POST)
	public ModelAndView handleRootUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile file = multipartRequest.getFile("rootCertsFile");
		if (file==null || file.getSize()==0||file.getSize() > 10485760l) { // 10MB = 1024 * 1024 * 10 = 10485760 bytes
			errors.add(new FieldError("upload", "upload", "Invalid client CA certificates file."));
			model.put("success", Boolean.FALSE);
			model.put("fields", errors);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}
		String uploadedFile = getTmpDirectory() + securityService.generateRandomString(10);
		file.transferTo(new File(uploadedFile));

		String actionType = getRequestParameter("actionType", request);

		if (!securityService.importRootCACert(uploadedFile, "append".equals(actionType))) {
			errors.add(new FieldError("upload", "upload", "Invalid client CA certificates file."));
			model.put("success", Boolean.FALSE);
			model.put("fields", errors);
		} else {
			model.put("success", Boolean.TRUE);
		}
		FileUtils.deleteQuietly(new File(uploadedFile));
		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	@RequestMapping(value = "/security/security_advanced_factory_reset.ajax", method = POST)
	public ModelAndView handleFactoryReset(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
		boolean success = securityService.resetSecuritySettings();
		if (!success) {
            errors.add(new FieldError("reset", "reset", "Reset failed."));
			model.put("success", Boolean.FALSE);
            model.put("fields", errors);
		} else {
			model.put("success", Boolean.TRUE);
		}
		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/security/security_advanced_upload_bundle.ajax", method = POST)
	public ModelAndView handleBundleUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		String password = getRequestParameter("bundlePassword", request);

		boolean uploadSuccess = false;
		String uploadMessage = "";

		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile file = multipartRequest.getFile("certBundleFile");
		String fileName = file.getOriginalFilename();

        // Validate file name before proceeding.
        if(!fileName.matches(SystemServiceImpl.UPLOADED_FILE_NAME_REGEX)) {
        	 FieldError fe = new FieldError("Maintenance", messages.getMessage("incorrect.file.upload.params", null, "", LocaleContextHolder.getLocale()), "Invalid Filename");
             errors.add(fe);
        	 model.put("success", Boolean.FALSE);
        	 model.put("fields", errors);
        	 return new ModelAndView("ajax/result_ajax", "model", model);
        }

		if(file == null || file.getBytes().length == 0){
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.empty.error", null, LocaleContextHolder.getLocale());
		} else if (fileName.toLowerCase().endsWith("p7b")) {
			String p7bFileName = getTmpDirectory() + "tmpSslBundleFile.p7b";
			File p7bFile = new File(p7bFileName);
			file.transferTo(p7bFile);
			if (!securityService.importP7bCertificateBundle(p7bFileName)) {
				uploadMessage = messages.getMessage("super.security.ssl.uploaded.p7b.bundle.is.invalid.or.does.not.match.ssl.private.key", null, LocaleContextHolder.getLocale());
			} else {
				uploadSuccess = true;
			}
			FileUtils.deleteQuietly(p7bFile);
		} else if (fileName.toLowerCase().endsWith("pfx") || fileName.toLowerCase().endsWith("p12")) {
			String pfxFileName = getTmpDirectory() + "tmpSslBundleFile.pfx";
			File pfxFile = new File(pfxFileName);
			file.transferTo(pfxFile);
			if (!securityService.importPfxCertificateBundle(pfxFileName, password)) {
				uploadMessage = messages.getMessage("super.security.ssl.uploaded.pfx.bundle.is.invalid.and.or.the.password.is.incorrect", null, LocaleContextHolder.getLocale());
			} else {
				uploadSuccess = true;
			}
			FileUtils.deleteQuietly(pfxFile);
		} else  if(fileName.toLowerCase().endsWith(".vidyo")){

			boolean uploadOK = false;
			boolean cryptTarOK = false;
			String file_path = getTmpDirectory() + "tmpSslImportFile.vidyo";
			File f = new File(file_path);
			if(f.exists()){
				f.delete();
			}
			String import_path_dir = getTmpDirectory() + "opt/vidyo/portal2/security";
			File importDir = new File(import_path_dir);
			try{
				if(importDir.exists()){
					FileUtils.deleteDirectory(importDir);
				}
				FileUtils.writeByteArrayToFile(f, file.getBytes());
				uploadOK = true;
			}catch (IOException e){
				logger.debug("Exception001 while tmpSslImportFile.vidyo file uploading " + e.getMessage());
			}

			File importTempDir = new File(getTmpDirectory() + "mmm");
			try{
				if(importTempDir.exists()){
					FileUtils.deleteDirectory(importTempDir);
				}
				importTempDir.mkdir();
			}catch (IOException e){
				e.printStackTrace();
			}

			if(uploadOK){

				String[] cmd = new String[] {"sudo", "-n", SecurityServiceImpl.SCRIPT_VIDYO_EXPORT_IMPORT, "DECRYPT", f.getName()};

				try {
					ShellExecutor.execute(cmd);
				} catch (ShellExecutorException see) {
					// handled below when it checks to see if tar exists
					logger.error("Failed to decrypt the .vidyo bundle"+ see.getMessage());
				}

				File outputFromCryptTar = new File(f.getAbsolutePath() + ".decrypt" );
				if(outputFromCryptTar.exists()){
					cryptTarOK = true;
				}
				if(cryptTarOK){
					cmd = new String[] {"/bin/tar", "-xzvmf", outputFromCryptTar.getAbsolutePath(), "-C", getTmpDirectory()};
					try {
						ShellExecutor.execute(cmd);
					} catch (ShellExecutorException see) {
						// handled below when it checks to see if uncompressed files exist
						logger.error("Cannot untar the file "+ see.getMessage());
					}

					boolean isTomcatBundle = (new File(getTmpDirectory() + "opt/vidyo/portal2/security/portal2.root")).exists();
					boolean isApacheBundle = (new File(getTmpDirectoryNoTrailingSlash() + SecurityServiceImpl.SSL_HOME + "private/vidyo-cacert.root")).exists();

					if (isTomcatBundle) {
						uploadMessage = processTomcatBundle();
						if ("".equals(uploadMessage)) { // no error
							uploadSuccess = true;
						}
					} else if (isApacheBundle) {
						uploadMessage = securityService.importVidyoSecurityBundle(getTmpDirectory() + "tmpSslImportFile.vidyo", password);
						if ("".equals(uploadMessage)) { // no error
							uploadSuccess = true;
						}
					}  else {
						uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.invalid.file.error", null, LocaleContextHolder.getLocale());
					}

				}else{
					uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.invalid.file.error", null, LocaleContextHolder.getLocale());
				}
			}

			try{
				if(importTempDir.exists()){
					FileUtils.deleteDirectory(importTempDir);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			FileUtils.deleteQuietly(new File(f.getAbsolutePath()));
			FileUtils.deleteQuietly(new File(getTmpDirectory() + "opt/"));
		} else {
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.fileformat.error", null, LocaleContextHolder.getLocale());
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setTransactionName("Import Certificate Bundle");
			transactionHistory.setTransactionParams(fileName + "; Invalid File Format");
			Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
			String tenantName = null;
			if(tenant == null) {
				tenantName = "Default";
			} else {
				tenantName = tenant.getTenantName();
			}
			transactionHistory.setTenantName(tenantName);
			transactionHistory.setTransactionResult("FAILURE");
			transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
		}

		if (!uploadSuccess) {
			errors.add(new FieldError("upload", uploadMessage, uploadMessage));
			model.put("fields", errors);
		}

		model.put("success", Boolean.valueOf(uploadSuccess));
		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	private String processTomcatBundle() {

		String uploadMessage = "";

		String importBundlePath = getConfigParameter("domainName", getTmpDirectory() + "opt/vidyo/portal2/security/data").getValue();
		String importCN         = getConfigParameter("CN", getTmpDirectory() + "opt/vidyo/portal2/security/data").getValue();
		String importKeyName    = getConfigParameter("keyName", getTmpDirectory() + "opt/vidyo/portal2/security/data").getValue();
		String csrName          = getConfigParameter("csrName", getTmpDirectory() + "opt/vidyo/portal2/security/data").getValue();

		boolean foundErrors = false;
		boolean certOK = false;
		boolean certBundleOK = false;
		boolean keyOK   = false;
		boolean csrOK   = false;

		List<String> tempCertFileNames  =  securityService.parseCertBundle(getTmpDirectoryNoTrailingSlash() + importBundlePath);
		StringBuilder bundleAccum = new StringBuilder();

		try{
			for(int i=0; i < tempCertFileNames.size(); i++){
				String tempCertFileName = tempCertFileNames.get(i);
				if(securityService.isCertInvalid(tempCertFileName)){
					foundErrors = true;
				} else {
					CRTObject cert = securityService.parseCert(tempCertFileName);
					String cnName = cert.getSubjectCN();
					if(cnName.equalsIgnoreCase(importCN)){
						try{
							FileUtils.copyFile(new File(tempCertFileName), new File(getTmpDirectory() + "mmm/domain-server.crt"));
							certOK = true;
						}catch (IOException e) {
						}
					}else{
						bundleAccum.append(securityService.readFile(tempCertFileName));
					}
				}
				File tempCertFile = new File(tempCertFileName);
				tempCertFile.delete();
			}

			FileUtils.writeStringToFile(new File(getTmpDirectory() + "mmm/domain-server-bundle.crt"), bundleAccum.toString());
			certBundleOK = true;
		}catch (IOException e) {
			e.printStackTrace();
		}


		String keyContent = parseKeyFromImportBundle(getTmpDirectoryNoTrailingSlash() + importKeyName);
		try{
			FileUtils.writeStringToFile(new File(getTmpDirectory() + "mmm/domain-server.key"), keyContent);
			securityService.removeKeyPassword(getTmpDirectory() + "mmm/domain-server.key");

			String sslKeyStatus = securityService.checkKeyStatus(getTmpDirectory() + "mmm/domain-server.key");
			if(sslKeyStatus.equals("OK")){
				keyOK = true;
			}

		}catch (IOException e) {
			e.printStackTrace();
		}

		csrOK = securityService.isCSRValid(getTmpDirectoryNoTrailingSlash() + csrName);

		if(keyOK && certOK &&certBundleOK && csrOK){
			try{
				FileUtils.copyFile(new File(getTmpDirectory() + "mmm/domain-server.key"), new File(SecurityServiceImpl.FILE_PRIVATE_KEY));

			}catch (IOException e) {
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.key.general.error", null, LocaleContextHolder.getLocale());
			}
			try{
				FileUtils.copyFile(new File(getTmpDirectoryNoTrailingSlash() + csrName), new File(SecurityServiceImpl.FILE_SIGNING_REQUEST));

			}catch (IOException e) {
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.csr.general.error", null, LocaleContextHolder.getLocale());
			}
			try{
				FileUtils.copyFile(new File(getTmpDirectory() + "mmm/domain-server.crt"), new File(SecurityServiceImpl.FILE_SERVER_CERT));
				securityService.mergeKeyAndCert();
			}catch (Exception e) {
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.cert.general.error", null, LocaleContextHolder.getLocale());
			}
			try{
				FileUtils.copyFile(new File(getTmpDirectory() + "mmm/domain-server-bundle.crt"), new File(SecurityServiceImpl.FILE_SERVER_INTERMEDIATE_CERT));

			}catch (IOException e) {
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.cint.general.error", null, LocaleContextHolder.getLocale());
			}
			try {
				FileUtils.copyFile(new File(getTmpDirectory() + "opt/vidyo/portal2/security/portal2.root"), new File(SecurityServiceImpl.FILE_SSL_CA_CERTS));
			} catch (IOException e) {
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.general.error", null, LocaleContextHolder.getLocale());
			}
            try {
                securityService.importRootCACertNSS();
            } catch (ShellExecutorException e) {
                uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.general.error", null, LocaleContextHolder.getLocale());
            }


			if(foundErrors){
				uploadMessage += "<br>" + messages.getMessage("super.security.ssl.manage.cert.import.upload.bundle.some.errors", null, LocaleContextHolder.getLocale());
			}


		} else if(!keyOK){
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.key.error", null, LocaleContextHolder.getLocale());
		} else if(!csrOK){
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.csr.error", null, LocaleContextHolder.getLocale());
		} else if(!certOK){
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.cert.error", null, LocaleContextHolder.getLocale());
		} else if(!certBundleOK){
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.cert.bundle.error", null, LocaleContextHolder.getLocale());
		} else {
			uploadMessage = messages.getMessage("super.security.ssl.manage.cert.import.upload.general.error", null, LocaleContextHolder.getLocale());
		}

		return uploadMessage;
	}

	private List<ConfigProperty> getConfigParameters(String paramFile){
		List<ConfigProperty> ret = new ArrayList<ConfigProperty>();

		try{
			FileInputStream fis = new FileInputStream(paramFile);
			InputStreamReader in = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				int pipeIndex = line.indexOf("=");
				if(pipeIndex > -1){
					ConfigProperty conf = new ConfigProperty();
					String name = line.substring(0,pipeIndex);
					String value = line.substring(pipeIndex+1, line.length());

					conf.setName(name);
					conf.setValue(value);

					ret.add(conf);
				}
			}

			br.close();

		}catch(IOException ex){
			ex.printStackTrace();
		}

		return ret;
	}


	private ConfigProperty getConfigParameter(String name, String paramFile){
		ConfigProperty ret = new ConfigProperty();

		List<ConfigProperty> configs = this.getConfigParameters(paramFile);

		for (ConfigProperty config : configs) {
			if (name.equals(config.getName())) {
				ret = config;
			}
		}

		return ret;
	}

	private String parseKeyFromImportBundle(String filePath){
		String accum = "";
		File file = new File(filePath);
		try {
			boolean startKey = false;
			boolean endKey   = false;
			LineIterator it = FileUtils.lineIterator(file);
			while (it.hasNext()) {
				String line = it.nextLine().trim();
				if(line.equals("-----BEGIN PRIVATE KEY-----")){
					startKey = true;
				}
				if(line.equals("-----END PRIVATE KEY-----")){
					accum = accum + line;
					endKey = true;
				}
				if(!line.equals("")){
					if(startKey && !endKey){
						accum = accum + line + "\n";
					}
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}

		return accum;
	}

	private String getTmpDirectory() {
		return tmpDirectory;
	}

	private String getTmpDirectoryNoTrailingSlash() {
		return this.getTmpDirectory().substring(0,getTmpDirectory().length()-1);
	}
}
