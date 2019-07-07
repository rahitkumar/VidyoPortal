package com.vidyo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.*;
import java.util.*;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.vidyo.bo.ApplicationConfiguration;
import com.vidyo.bo.security.CRTObject;
import com.vidyo.bo.security.CSRObject;
import com.vidyo.bo.security.ConfigProperty;
import com.vidyo.bo.security.X500PrincipalHelper;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.exceptions.SecurityServiceException;



public class SecurityServiceImpl implements ISecurityService{
	public static final String SSL_HOME = "/opt/vidyo/etc/ssl/"; //"/etc/ssl/";

	public static final String FILE_PRIVATE_KEY = SSL_HOME + "private/domain-server.key";
	public static final String FILE_SIGNING_REQUEST = SSL_HOME + "private/domain-server.csr";
	public static final String FILE_SERVER_CERT = SSL_HOME + "certs/domain-server.crt";
	public static final String FILE_SERVER_INTERMEDIATE_CERT = SSL_HOME + "certs/domain-server-bundle.crt";
	public static final String FILE_PRIVATE_KEY_SERVER_CERT_COMBO = SSL_HOME + "certs/domain-server.crt-key";
	
	public static final String FILE_CLIENT_CERTS = SSL_HOME + "private/client-cacert.root";
	public static final String FILE_DEFAULT_CLIENT_CERTS = SSL_HOME + "private/vidyo-cacert.root";
	public static final String FILE_SSL_CA_CERTS = SSL_HOME + "certs/cacert.root";

	public static final String FILE_SSL_ENABLED = SSL_HOME + "private/ssl-enabled";
	public static final String FILE_SSL_FORCED = SSL_HOME + "private/ssl-forced";
	public static final String FILE_SSL_FORCED_NO_REDIRECT = SSL_HOME + "private/ssl-forced-no-redirect";

	public static final String FILE_OCSP_INFO = SSL_HOME + "private/ocsp.info";

	public static final String SCRIPT_HOME = "/opt/vidyo/bin/";

	public static final String SCRIPT_SSL_FUNCTIONS = SCRIPT_HOME + "ssl_functions.pl";
	public static final String SCRIPT_PACK_SECURITY = SCRIPT_HOME + "export_pfx.sh";
	public static final String SCRIPT_UNPACK_SECURITY = SCRIPT_HOME + "unpack_security.sh";
	public static final String SCRIPT_RESET_VIDYOHOST = SCRIPT_HOME + "reset_vidyohost.sh";
	public static final String SCRIPT_OCSP_CONFIG = SCRIPT_HOME + "ocsp_config.sh";
    public static final String SCRIPT_NSS_UTILS = SCRIPT_HOME + "nss_utils.sh";
	public static final String SCRIPT_USE_DEFAULT_ROOT_CERTS = SCRIPT_HOME + "use_vidyo_cacert.sh";
	public static final String SCRIPT_VERIFY_CHAIN = SCRIPT_HOME + "ssl_validate_chain.sh";
	
	public static final String SCRIPT_PFX_FUNCTIONS = SCRIPT_HOME + "vidyo_pfx.sh";//old
	public static final String SCRIPT_PFX_IMPORT = SCRIPT_HOME + "import_pfx.sh";

	public static final String PEM_BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
	public static final String PEM_END_CERTIFICATE = "-----END CERTIFICATE-----";
	public static final String SCRIPT_APPS_PORT = SCRIPT_HOME + "apps_port.sh";
	public static final String SCRIPT_GET_INTERFACE = SCRIPT_HOME + "get_interface.sh";
	public static final String SCRIPT_VIDYO_SSL = SCRIPT_HOME + "vidyo_ssl.sh";
	public static final String SCRIPT_VIDYO_UPDATE_SECURITY = SCRIPT_HOME + "vidyo_update_security.sh";
	public static final String SCRIPT_USED_TCP_PORT_BY_PROCESS = SCRIPT_HOME + "vidyo_used_tcpport.sh";
	public static final String SCRIPT_IS_PORT_AVAILABLE = SCRIPT_HOME + "is_port_available.sh";
	public static final String SCRIPT_VIDYO_EXPORT_IMPORT = SCRIPT_HOME + "vidyo_import_export.sh";

	public static final int[] KEY_VALID_BIT_SIZES = {1024, 2048, 4096};
	public static final int KEY_DEFAULT_BIT_SIZE = 2048;
	
	public static final int DEFAULT_SSL_PORT = 443;

	public static final String[] CSR_VALID_COUNTRIES = {
			"AF","AX", "AL", "DZ", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU", "AT",
			"AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BQ", "BA", "BW", "BV", "BR",
			"IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO",
			"KM", "CG", "CD", "CK", "CR", "CI", "HR", "CU", "CW", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG",
			"SV", "GQ", "ER", "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE",
			"GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK",
			"HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP", "JE", "JO", "KZ", "KE", "KI",
			"KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MK", "MG", "MW",
			"MY", "MV", "ML", "MT", "MH", "MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA",
			"MZ", "MM", "NA", "NR", "NP", "NL", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK",
			"PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "RE", "RO", "RU", "RW", "BL",
			"SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SX", "SK",
			"SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD", "SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ",
			"TZ", "TH", "TL", "TG", "TK", "TO", "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "US",
			"UM", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE", "ZM", "ZW"};

	protected final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class.getName());
	
	private String tmpDirectory = "/opt/vidyo/temp/tomcat/";

	private MessageSource messages;

	public String getTmpDirectory() {
		return tmpDirectory;
	}

	public void setTmpDirectory(String tmpDirectory) {
		this.tmpDirectory = tmpDirectory;
	}

	public void setMessages(MessageSource messages) {
		this.messages = messages;
	}

	public void generateKey(int bits) throws SecurityServiceException {
		if (!ArrayUtils.contains(KEY_VALID_BIT_SIZES, bits)) {
			throw new SecurityServiceException("Private key must be 1024, 2048 or 4096 bits.");
		}

		String[] command = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "generateSSLKey", String.valueOf(bits)};
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture.isStdErrOutput()) {
				throw new SecurityServiceException("Command to generate private key returned error. " + capture.getStdErr());
			}
		} catch (ShellExecutorException e) {
			throw new SecurityServiceException("Shell exception while generating new private key", e);
		}
	}

    public String getKeyHash() throws IOException, SecurityServiceException {
    	String[] cmd = {"sudo", "-n", SCRIPT_VIDYO_SSL, "GET_DOMAIN_KEY_HASH"};
    	String out = null;
		try {
			ShellCapture capture = ShellExecutor.execute(cmd);
			if (capture == null || capture.getExitCode() != 0) {
				throw new SecurityServiceException("Command to get private key returned error. " + capture.getStdErr());
			}
			out = capture.getStdOut() != null ? capture.getStdOut().trim() : null;
	        if (org.springframework.util.StringUtils.isEmpty(out)) {
	            generateKey(KEY_DEFAULT_BIT_SIZE);
	            // recursive function
	            return getKeyHash();
	        } else {
	        	return out;
	        }			
		} catch (ShellExecutorException e) {
			throw new SecurityServiceException("Shell exception while generating new private key", e);
		}
    }

    public boolean encryptPrivateKey(String filePathEncrypted, String password) throws IOException {
        if (password == null) {
            return false;
        }
        String passwordFile = getTmpDirectory() + generateRandomString(10) + ".pwd";
        File passwordFileHandle = new File(passwordFile);
        FileUtils.writeStringToFile(passwordFileHandle, password);

        String[] encryptCommand = { "sudo", "-n", SCRIPT_VIDYO_SSL, "ENCRYPT_DOMAIN_KEY", filePathEncrypted, passwordFile };
        try {
            ShellCapture capture = ShellExecutor.execute(encryptCommand);
            if (capture.isErrorExitCode()) {
                logger.error(capture.getStdErr());
            }
            return capture.isSuccessExitCode();
        } catch (ShellExecutorException see) {
            logger.error("Error encrypting private key: " + see.getMessage());
        } finally {
            FileUtils.deleteQuietly(passwordFileHandle);
        }

        return false;
    }

    public boolean decryptPrivateKey(String filePathEncrypted, String filePathPlain, String password) throws IOException {
        if (password == null) {
            return false;
        }
        String passwordFile = getTmpDirectory() + generateRandomString(10) + ".pwd";
        File passwordFileHandle = new File(passwordFile);
        FileUtils.writeStringToFile(passwordFileHandle, password);

        String[] encryptCommand = { "sudo", "-n", SCRIPT_VIDYO_SSL, "DECRYPT_NEW_DOMAIN_KEY", filePathEncrypted, passwordFile, filePathPlain };
        try {
            ShellCapture capture = ShellExecutor.execute(encryptCommand);
            if (capture.isErrorExitCode()) {
                logger.error("Error while decrypting Privaye Key " + capture.getStdErr());
            }
            return capture.isSuccessExitCode();
        } catch (ShellExecutorException see) {
            logger.error("Error decrypting private key: " + see.getMessage());
        } finally {
            FileUtils.deleteQuietly(passwordFileHandle);
        }

        return false;
    }

	public String readFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists()){
			return FileUtils.readFileToString(file);
		} else {
			return "";
		}
	}
	
	public void writeFile(String filePath, String fileContent) throws IOException {
		File file = new File(filePath);
		FileUtils.writeStringToFile(file, fileContent);
	}

	public String getCSRContent() throws IOException {
		return readFile(FILE_SIGNING_REQUEST);
	}
	
	public String getCertContent() throws IOException {
		return readFile(FILE_SERVER_CERT);
	}
	
	public String getCertBundleContent() throws IOException {
		return readFile(FILE_SERVER_INTERMEDIATE_CERT);
	}

	public CSRObject parseCSR(){
		CSRObject csr = new CSRObject();
		
		String[] command = {"/bin/bash", "-c", "openssl req -subject -noout -in " + FILE_SIGNING_REQUEST };
		try {
			ShellCapture capture = ShellExecutor.execute(command);
            if (!capture.isErrorExitCode()) {
                String line = capture.getStdOut();
                if (line.startsWith("subject=")) {
                    String[] parts = line.split("/");
                    String name = "";
                    String value = "";
                    for (int i = 0; i < parts.length; i++) {
                        String[] subparts = parts[i].split("=");

                        if (subparts != null && subparts.length > 0) {
                            name = subparts[0];
                        } else {
                            name = "";
                        }
                        if (subparts != null && subparts.length > 1) {
                            value = subparts[1];
                        } else {
                            value = "";
                        }

                        if ("C".equals(name)) {
                            csr.setCountryName(value);
                        } else if ("ST".equals(name)) {
                            csr.setStateOrProvinceName(value);
                        } else if ("L".equals(name)) {
                            csr.setLocalityName(value);
                        } else if ("O".equals(name)) {
                            csr.setOrganizationName(value);
                        } else if ("CN".equals(name)) {
                            csr.setCommonName(value);
                        } else if ("OU".equals(name)) {
                            csr.setOrganizationalUnitName(value);
                        } else if ("emailAddress".equals(name)) {
                            csr.setEmailAddress(value);
                        }
                    }
                }

            }
		} catch (ShellExecutorException see) {
			see.printStackTrace();
		}
		
		return csr;
	}
	
	
	public String generateCSR(CSRObject csr){
		String ret = "OK";
		
		String countryName            = sslStripCharacters(csr.getCountryName());
		String stateOrProvinceName    = sslStripCharacters(csr.getStateOrProvinceName());
		String localityName           = sslStripCharacters(csr.getLocalityName());
		String organizationName       = sslStripCharacters(csr.getOrganizationName());
		String organizationalUnitName = sslStripCharacters(csr.getOrganizationalUnitName());
		String commonName             = sslStripCharacters(csr.getCommonName());
		String emailAddress           = sslStripCharacters(csr.getEmailAddress());
		
		if(countryName.equals("")){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.country1", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(countryName.length() != 2){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.country2", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(!this.isCountryCodeValid(countryName)){
			ret = messages.getMessage("super.security.ssl.certificate.country.code.invalid", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(stateOrProvinceName.equals("")){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.state", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(localityName.equals("")){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.locality", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(organizationName.equals("")){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.organization", null, LocaleContextHolder.getLocale());
			return ret;
		}
		
		if(commonName.equals("")){
			ret = messages.getMessage("super.security.ssl.manage.csr.error.common", null, LocaleContextHolder.getLocale());
			return ret;
		}

		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
				"generateCSR", countryName.toUpperCase(),
				stateOrProvinceName, localityName,
				organizationName,
				organizationalUnitName, commonName,
				emailAddress};

		try {
			ShellCapture capture = ShellExecutor.execute(cmd);
			logger.debug("CSR output " + capture.getStdOut());

			if(capture == null ||  capture.getExitCode() != 0) {
				if(StringUtils.isNotEmpty(capture.getStdErr())) {
					ret = "Exception while generating CSR: " + capture.getStdErr();
					logger.error(capture.getStdErr());
				}
				return "Error while generating CSR";
			}

			// If CSR generation is success, invoke the permission changing script
			cmd = new String[] {"sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "UPDATE_PERMS"};
			capture = ShellExecutor.execute(cmd);
			if(capture == null ||  capture.getExitCode() != 0) {
				ret = "Exception while changing CSR permission: " + capture.getStdErr();
			}

		} catch (ShellExecutorException e) {
			logger.error("Exception parseCSR(): " + e.getMessage());
			ret = "Exception while generating CSR: " + e.getMessage();
		}

		return ret;
	}
	
	public String checkKeyStatus(String filePath){
		String ret = "";
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "checkKeyStatus",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
			
			String s = output.getStdOut();
			String error = output.getStdErr();

			if (s.contains("key ok")){
				ret = "OK";
			} else {
				ret = "Invalid";
			}

			if(!error.equals("")) {
				logger.debug("Exception while checkKeyStatus: " + error);
				ret = "Invalid";
			}

		} catch (ShellExecutorException e) {
			logger.debug("Exception checkKeyStatus(): " + e.getMessage());
			ret = "Invalid";
		}
		
		return ret;
	}

	private String extractDigits(String field){
		String ret = "";
		if(field == null){
			field = "";
		}
		field = field.trim();
		for(int i=0; i < field.length();i++){
			if(  (field.charAt(i) == '0')||
					(field.charAt(i) == '1')||
					(field.charAt(i) == '2')||
					(field.charAt(i) == '3')||
					(field.charAt(i) == '4')||
					(field.charAt(i) == '5')||
					(field.charAt(i) == '6')||
					(field.charAt(i) == '7')||
					(field.charAt(i) == '8')||
					(field.charAt(i) == '9'))
			{
				ret += field.charAt(i);
			}
		}
		return ret;
	}

	public String getPrivateKeyBits(){
		String ret = "";
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
				"extractKeyText"
		};

		try {
			ShellCapture output = ShellExecutor.execute(sargs);

			List<String> outLines = output.getStdOutLines();
			String error = output.getStdErr();
			
			if (outLines.size() != 0) {
				String s = outLines.get(0);
				if (s.contains("Private-Key:")) {
					int startPos = 12;
					if (startPos < s.length()) {
						String bitPart = s.substring(startPos, s.length()).trim();
						ret = extractDigits(bitPart);
					}
				}
			}

			if (!error.equals("")) {
				logger.debug("Exception while getPrivateKeyBits: " + error);
			}

		} catch (ShellExecutorException e) {
			logger.debug("Exception getPrivateKeyBits(): " + e.getMessage());
		}
		
		return ret;
	}
	
	
	private String parseCertFingerprint(String filePath){
		String ret = "";
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "extractCrtFingerprint",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
			
			List<String> lines = output.getStdOutLines();
			String error = output.getStdErr();
			for (String s : lines) {
				if (s.contains("SHA1 Fingerprint=")) {
					int startPos = 17;
					if (startPos < s.length()) {
					    ret = s.substring(startPos, s.length()).trim();
					}
				}
			}
          
			if (!error.equals("")) {
				logger.debug("Exception while parseCertFingerprint: " + error);
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception parseCertFingerprint(): " + e.getMessage());
		}
		
		return ret;
	}
	
	private String parseCertSerialNumber(String filePath){
		
		String ret1 = "";
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "extractCrtSerial",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
			
			List<String> lines = output.getStdOutLines();
			String error = output.getStdErr();
			
			for (String s : lines) {
				if (s.contains("serial=")) {
					int startPos = 7;
					if (startPos < s.length()) {
						String ret = s.substring(startPos, s.length()).trim();
					    if (ret.length() % 2 != 0) {
					    	ret = "0" + ret;
					    }
					    for (int i=0; i < ret.length(); i+=2) {
					    	if (i == 0) {
					    	   ret1 = ret1 + ret.charAt(i) + ret.charAt(i + 1);
					    	} else {
					    		ret1 = ret1 + ":" + ret.charAt(i) + ret.charAt(i + 1);
					    	}
					    }
					}
				}
			}
			
			if (!error.equals("")) {
				logger.debug("Exception while parseCertFingerprint: " + error);
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception parseCertFingerprint(): " + e.getMessage());
		}	
		return ret1;
	}
	
	
	public CRTObject parseCert(String filePath){
		CRTObject ret = new CRTObject();
		try {
		    InputStream inStream = new FileInputStream(filePath);
		    CertificateFactory cf = CertificateFactory.getInstance("X.509");

		    X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
		    inStream.close();
		    ret.setFingerprint(parseCertFingerprint(filePath));
		    ret.setVersion(cert.getVersion() + "");
		    ret.setSerial(parseCertSerialNumber(filePath));
		    ret.setNotBefore(cert.getNotBefore());
		    ret.setNotAfter(cert.getNotAfter());
		    try {
		    	cert.checkValidity();
		    } catch(CertificateExpiredException e) {
		    	ret.setExpired(true);
		    } catch(CertificateNotYetValidException e) {
		    	ret.setNotYetValid(true);
		    }
		    
		    // ret.setValid();
		    
		    X500Principal x500IssuerPrincipal = cert.getIssuerX500Principal();
			X500PrincipalHelper prHelp = new X500PrincipalHelper();
			prHelp.setPrincipal(x500IssuerPrincipal);
			ret.setIssuerCN(prHelp.getCN());
			ret.setIssuerOU(prHelp.getOU());
			ret.setIssuerO(prHelp.getO());
			ret.setIssuerL(prHelp.getL());
			ret.setIssuerST(prHelp.getST());
			ret.setIssuerSTREET(prHelp.getSTREET());
			ret.setIssuerEMAIL(prHelp.getEMAILADDRESS());
			ret.setIssuerC(prHelp.getC());
			
			X500Principal x500SubjectPrincipal = cert.getSubjectX500Principal();
			prHelp.setPrincipal(x500SubjectPrincipal);
			ret.setSubjectCN(prHelp.getCN());
			ret.setSubjectOU(prHelp.getOU());
			ret.setSubjectO(prHelp.getO());
			ret.setSubjectL(prHelp.getL());
			ret.setSubjectST(prHelp.getST());
			ret.setSubjectSTREET(prHelp.getSTREET());
			ret.setSubjectEMAIL(prHelp.getEMAILADDRESS());
			ret.setSubjectC(prHelp.getC());

			List<String> sans = getDNSSubjectAltNames(cert);
			ret.setSubjectAltNames(sans);
		} catch (Exception e) {
			logger.debug("Exception parseCert(): " + e.getMessage());
		}	
		
		return ret;
	}

	private List<String> getDNSSubjectAltNames(X509Certificate cert) throws CertificateParsingException {
		final List<String> subjectAltList = new LinkedList<String>();
		Collection<List<?>> sans = cert.getSubjectAlternativeNames();
		if (sans != null) {
			for (List<?> list : sans) {
				int type = (Integer) list.get(0);
				if (type == 2) { // DNS type
					String hostname = (String) list.get(1);
					subjectAltList.add(hostname);
				}
			}
		}
		if (!subjectAltList.isEmpty()) {
			return subjectAltList;
		} else {
			return Collections.emptyList();
		}
	}
	
    public boolean isCSRValid(String filePath) {
	    
	    String command = "openssl req -in " + filePath + " -noout -text";
	    boolean unableFound = false;
	    try {
	    	ShellCapture output = ShellExecutor.execute(command);

			String error = output.getStdErr();
			for (String s : output.getStdOutLines()) {
				if(s.contains("unable to load")){
					unableFound = true;
				}
			}
			for (String s : output.getStdErrLines()) {
            	if(s.contains("unable to load")){
					unableFound = true;
				}
			}
			
			if(!error.equals("")) {
				logger.debug("Exception while parseCertFingerprint: " + error);
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception parseCertFingerprint(): " + e.getMessage());
		}

	    return !unableFound;
	    
    }
	
    public boolean isCSRMatchToKey(String filePath){
		
		boolean ret = false;
		
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "checkCSRMatchToKey",
                filePath
        };
		
		try {
			ShellCapture capture = ShellExecutor.execute(sargs);
			
			for (String s : capture.getStdOutLines()) {
				if(s.contains("csrmatch")){
                    ret = true;
				}
			}
			
			if(!capture.getStdErr().equals("")) {
				logger.debug("Exception while isCSRMatchToKey: " + capture.getStdErr());
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception isCSRMatchToKey(): " + e.getMessage());
		}	
		
	    return ret;
	}
	
    public boolean isCertMatchToKey(String filePath){
		
		boolean ret = false;
		
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "checkCRTMatchToKey",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
		
			for (String s : output.getStdOutLines()) {
				if(s.contains("crtmatch")){
                    ret = true;
				}
			}
            
			if(!output.getStdErr().equals("")) {
				logger.debug("Exception while isCertMatchToKey: " + output.getStdErr());
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception isCertMatchToKey(): " + e.getMessage());
		}	
	
	    return ret;
	}

	public boolean isCertMatchToFQDN(CRTObject crt, String fqdn) {
		if (fqdn == null) {
			return false;
		}
		fqdn = fqdn.toLowerCase();

		String commonName = crt.getSubjectCN();
		if (commonName != null) {
			commonName = commonName.toLowerCase();
		}

		List<String> san = crt.getSubjectAltNames();
		// make a list of all domains
		List<String> all = new ArrayList<String>(san.size() + 1);
		all.add(commonName);
		all.addAll(san);
		for (String domain : all) {
			domain = domain.toLowerCase();
			if (domain.startsWith("*.")) {
				String domainWithoutWildcard = domain.replace("*", "");
				String domainWithoutWildcardFull = domain.replace("*.", "");
				if (fqdn.endsWith(domainWithoutWildcard)) {
					String fqdnSuffix = fqdn.replace(domainWithoutWildcard, "");
					if (!fqdnSuffix.contains(".")) {
						return true; // x.domain.com matches *.domain.com
					} else {
						// ignore (x.y.domain.com does not match *.domain.com)
					}
				} else if (fqdn.equals(domainWithoutWildcardFull)) {
					return true; // domain.com matches *.domain.com (debatable)
				} else {
					// ignore and continue
				}
			} else if (fqdn.matches(domain)) {
				return true;
			}
		}
		return false;
	}
    
    @SuppressWarnings("unchecked")
	public boolean isCertBundleValid(String filePath){
    	File file = new File(filePath);
    	int beginCount = 0;
		int endCount   = 0;
		List<String> lines = new ArrayList<String>();
    	try{
    		List<String> tlines =  FileUtils.readLines(file);

		    for (String tline : tlines) {
			    String line = tline.trim();

			    if (line.equals(PEM_BEGIN_CERTIFICATE)) {
				    beginCount++;
			    }
			    if (line.equals(PEM_END_CERTIFICATE)) {
				    endCount++;
			    }

			    if (!line.equals("")) {
				    lines.add(line);
			    }
		    }
    		
    	}catch(IOException ex){
			ex.printStackTrace();
		}
    	
    	if(lines.size() == 0){
    		return true;
    	}
    	
    	String firstElement = lines.get(0).trim();
    	if(!firstElement.equals(PEM_BEGIN_CERTIFICATE)){
    		return false;
    	}
    	
    	String lastElement = lines.get(lines.size() - 1).trim();
    	if(!lastElement.equals(PEM_END_CERTIFICATE)){
    		return false;
    	}

	    return beginCount == endCount;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> parseCertBundle(String filePath){
		File file = new File(filePath);
		List<String> tempCertFileNames = new ArrayList<String>();

	    if (!file.exists()) {
		    return tempCertFileNames;
	    }

		try{
			List<String> lines =  FileUtils.readLines(file);			
			String tempCertFile = "";
			StringBuffer accum  = new StringBuffer();
			int certCount = 0;

			for (String line1 : lines) {
				String line = line1.trim();
				if (line.equals(PEM_BEGIN_CERTIFICATE)) {
					if (certCount > 0) {
						File f = new File(tempCertFile);
						FileUtils.writeStringToFile(f, accum.toString());
					}
					certCount++;
					tempCertFile = getTmpDirectory() + "cint_" + generateRandomString(10);
					tempCertFileNames.add(tempCertFile);
					accum = new StringBuffer();
				}
				if (!line.equals("")) {
					accum = accum.append(line).append("\n");
				}
			}
		    File f = new File(tempCertFile);
    	    FileUtils.writeStringToFile(f, accum.toString());
	
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		return tempCertFileNames;
	}

	public boolean isSSLEnabled(){
		
		File sslEnabled = new File(FILE_SSL_ENABLED);
		String fContent = "";
		if(!sslEnabled.exists()){
			return false;
		} else {
			try{
				fContent = FileUtils.readFileToString(sslEnabled);
			} catch (IOException e) {
				logger.error("Exception while reading isSSLEnabled(): " + e.getMessage());
			}	
		}
		fContent = fContent.trim();
		return "Y".equals(fContent);
	}
	
    private boolean isPortal() {
        List<String> appNames = new ArrayList<String>();
        appNames.add("super"); // super app only on a portal
        List<ApplicationConfiguration> appConfig = getAppsPortInfo(appNames);
        return (appConfig != null && appConfig.size() > 0);
    }

    private boolean isProxyInterfering() {
        if (isPortal()) {
            // portal can only run on 443 for HTTPS, but only if proxy is not running on that port
        	return isPortInUse(DEFAULT_SSL_PORT);
        } else {
            // standalone router can run on any port for HTTPS, even 443, as long as it is available
            List<String> appNames = new ArrayList<String>();
            appNames.add("vr2conf");
            List<ApplicationConfiguration> appConfig = getAppsPortInfo(appNames);
            if (appConfig != null && appConfig.size() > 0) {
                ApplicationConfiguration routerConfig = appConfig.get(0);
                int securePort = routerConfig.getSecurePort();
                if (securePort == DEFAULT_SSL_PORT) {
                    return isPortInUse(DEFAULT_SSL_PORT);
                } else {
                    return false; // proxy only runs on 443
                }
            }
        }
        return false;
    }
    
    private boolean isPortInUse(int port) {
    	String[] command = new String[] {SCRIPT_IS_PORT_AVAILABLE, String.valueOf(port)};
        try {
            ShellCapture capture = ShellExecutor.execute(command);
            if (capture == null || capture.isErrorExitCode()) {
                logger.error(capture.getStdErr());
            }
            // Returns 0 when the port is available
            return !capture.isSuccessExitCode();
        } catch (ShellExecutorException see) {
            logger.error("Error while executing is port available " + see.getMessage());
        }
        return true;
    }

	public String enableSSL(){

		String ret = "OK";
		
		if (isProxyInterfering()) {
		    logger.error("Port is in use - {}", DEFAULT_SSL_PORT);
		    ret = messages.getMessage("super.security.ssl.port.in.use", null, LocaleContextHolder.getLocale());
		    return ret;			
		}
		
		File cert = new File(FILE_SERVER_CERT);
		if(!cert.exists()){
			ret = "Error, there is no suitable SSL certificate";
			return ret;
		}
		
		//Check if certificate belongs to current key.
		List<String> validCert = new ArrayList<String>();
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "checkCert"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);
		
		    for (String s : output.getStdOutLines()) {
			    validCert.add(s);
		    }
		    for (String s : output.getStdErrLines()) {
			    validCert.add(s);
		    }
		    
	    } catch (ShellExecutorException e) {
		    logger.debug("Exception002 while validating SSL Certificate: " + e.getMessage());
		    ret = messages.getMessage("super.security.ssl.manage.cert.error.loading", null, LocaleContextHolder.getLocale());
		    return ret;
        }	
	    
	    if(validCert.size() > 1){
	    	String firstLine = validCert.get(0);
	    	if(firstLine.contains("unable")){
	    		ret = messages.getMessage("super.security.ssl.manage.cert.error.loading", null, LocaleContextHolder.getLocale());
			    return ret;
	    	}
	    }
	    
		if(validCert.size() != 1){
			logger.debug("Exception while certificate validation. Certificate key does not match to current server key");
			ret = messages.getMessage("super.security.ssl.manage.cert.error.non.match", null, LocaleContextHolder.getLocale());
			return ret;
		}

		// check if server cert expired
		CRTObject serverCert = parseCert(SecurityServiceImpl.FILE_SERVER_CERT);
		if (serverCert.getNotYetValid()) {
			ret = messages.getMessage("server.certificate.is.not.valid.yet.please.verify.date.on.server.and.or.obtain.a.new.server.certificate", null, LocaleContextHolder.getLocale());
			return ret;
		}
		if (serverCert.getExpired()) {
			ret = messages.getMessage("server.certificate.has.expired.please.verify.date.on.server.and.or.obtain.a.new.server.certificate", null, LocaleContextHolder.getLocale());
			return ret;
		}

		// check certificate chain
		if (!isCertificateChainComplete()) {
			ret = messages.getMessage("certificate.chain.is.incomplete.please.verify.and.upload.necessary.intermediate.and.or.root.certificates", null, LocaleContextHolder.getLocale());
			return ret;
		}

		cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "enableSSL"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);

			logger.debug(output.getStdOut());
			logger.debug(output.getStdErr());

		} catch (ShellExecutorException e) {
			logger.debug("Exception005 while enabling SSL: " + e.getMessage());
		}	
		return ret;
	}

	private boolean isCertificateChainComplete() {

		// ignore check if script not found
		if (!(new File(SCRIPT_VERIFY_CHAIN)).exists()) {
			logger.warn("missing chain verification script: " + SCRIPT_VERIFY_CHAIN);
			logger.warn(" - skipping chain verification");
			return true;
		}

		String[] command = { SCRIPT_VERIFY_CHAIN, FILE_SERVER_CERT };

		try {
			ShellCapture output = ShellExecutor.execute(command);

			logger.debug(output.getStdOut());
			logger.debug(output.getStdErr());

			boolean successFlag = output.isSuccessExitCode();

			if (!successFlag) {
				logger.warn("chain verification failed: " + output.getStdOut());
			}

			return successFlag;

		} catch (ShellExecutorException e) {
			logger.debug("Exception while verifying certificate chain: " + e.getMessage());
		}

		return false;
	}
	
	public String disableSSL(){
		String ret = "OK";
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "disableSSL"};
		try{
			ShellCapture output = ShellExecutor.execute(cmd);
			if(output == null || output.getExitCode() != 0) {
				ret = "Failed to disable SSL";
				return ret;
			}

		} catch (ShellExecutorException e) {
		    logger.debug("Exception001 while disabling SSL: " + e.getMessage());
        }	
	
	    return ret;	
	}
	
	public boolean isCertInvalid(String filePath){
		
		File cert = new File(filePath);
		if(!cert.exists()){
			return false;
		} else {
			String fContent = "";
			try {   
				fContent = FileUtils.readFileToString(cert);
			} catch (IOException e) {
				logger.debug("Exception while reading isCertInvalid(): " + e.getMessage());
		    }	
			fContent = fContent.trim();
			if(fContent.equals("")){
				return false;
			}
			    
		}
		
		//Check if certificate belongs to current key.
		List<String> validCert = new ArrayList<String>();
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "checkCertErrors",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
	    
		    for (String s : output.getStdOutLines()) {
			    validCert.add(s);
		    }
		    for (String s : output.getStdErrLines()) {
			    validCert.add(s);
		    }
		    
	    } catch (ShellExecutorException e) {
		    logger.debug("Exception002 while validating SSL Certificate: " + e.getMessage());
		    e.printStackTrace();
		    return false;
        }	
	    
	    if(validCert.size() > 1){
	    	String firstLine = validCert.get(0);
	    	if(firstLine.contains("unable")){
			    return true;
	    	}
	    }
		
		return false;
	}
	
	public boolean isCertFormatDER(String filePath){
		
		List<String> scriptOutput = new ArrayList<String>();
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "checkCertFormatDER",
                filePath
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
	    
		    for (String s : output.getStdOutLines()) {
		    	scriptOutput.add(s);
		    }
		    for (String s: output.getStdErrLines()) {
		    	scriptOutput.add(s);
		    }  
		} catch (ShellExecutorException e) {
		    logger.debug("Error while checking cert format DER: " + e.getMessage());
		    return false;
        }	
	    
	    if (scriptOutput.size() > 1) {
	    	String firstLine = scriptOutput.get(0);
	    	if (!firstLine.contains("unable")) {
			    return true;
	    	}
	    }
		
		return false;
	}
	
	public boolean convertDERtoPEM(String tempCertFile,String tempCertPEMFile) {
		List<String> scriptOutput = new ArrayList<String>();
		String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
                "convertDERtoPEM",
                tempCertFile,
                tempCertPEMFile
        };
		
		try {
			ShellCapture output = ShellExecutor.execute(sargs);
		    
		    for (String s : output.getStdOutLines()) {
		    	scriptOutput.add(s);
		    }
		    for (String s: output.getStdErrLines()) {
		    	scriptOutput.add(s);
		    }  
		} catch (ShellExecutorException e) {
		    logger.debug("Error while converting DER to PEM: " + e.getMessage());
		    return false;
        }	
	    
	    if (scriptOutput.size() > 1) {
	    	String firstLine = scriptOutput.get(0);
	    	if (!firstLine.contains("unable")) {
			    return true;
	    	}
	    }
		
		return false;
	}
	
	public String generateCert(){
		String ret = "OK";
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "generateSelfSigned"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);

			logger.debug("generateCert: " + output.getStdOut());
			logger.debug("generateCert: " + output.getStdErr());
	    } catch (ShellExecutorException e) {
		    logger.debug("Exception002 while generationg SSL Certificate: " + e.getMessage());
		    return "Exception002 while generationg SSL Certificate ";
        }

		try {
			mergeKeyAndCert();
		} catch (Exception e) {
			logger.debug("IOException merging key and cert");
		}

		// copy self-signed cert to client ca root
		importRootCACert(FILE_SERVER_CERT, true);

		// change the permissions
		cmd = new String[] {"sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "UPDATE_PERMS"};
		try {
			ShellCapture capture = ShellExecutor.execute(cmd);
			if(capture == null ||  capture.getExitCode() != 0) {
				ret = "Exception while changing Cert permission: " + capture.getStdErr();
			}
	    } catch (ShellExecutorException e) {
		    logger.debug("Exception while changing Cert permissions: " + e.getMessage());
		    ret = "Exception while changing Cert permission: " + e.getMessage();
        }
		return ret;
	}


	public boolean isForcedHTTPSEnabled(){

		File sslForcedEnabled = new File(FILE_SSL_FORCED);
		String fContent = "";
		if(!sslForcedEnabled.exists()){
			return false;
		} else {
			try {
				fContent = FileUtils.readFileToString(sslForcedEnabled);
			} catch (IOException e) {
				logger.debug("Exception while reading isForcedHTTPSEnabled(): " + e.getMessage());
			}	
		}
		fContent = fContent.trim();
		return fContent.equals("Y");
	}
	
	public boolean isForcedHTTPSEnabledNoRedirect(){

		File sslForcedEnabled = new File(FILE_SSL_FORCED_NO_REDIRECT);
		String fContent = "";
		if(!sslForcedEnabled.exists()){
			return false;
		} else {
			try {
				fContent = FileUtils.readFileToString(sslForcedEnabled);
			} catch (IOException e) {
				logger.debug("Exception while reading isForcedHTTPSEnabledNoRedirect(): " + e.getMessage());
			}
		}
		fContent = fContent.trim();
		return fContent.equals("Y");
	}

	public String enableForcedHttps(){
		String ret = "OK";
		
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "enableForcedHttps"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			if(output == null || output.getExitCode() != 0) {
				ret = "Failed to Enable SSL";
				return ret;
			}
			
		} catch (ShellExecutorException e) {
			logger.debug("Exception003 while enabling forced SSL: " + e.getMessage());
		}	
		
		return ret;
	}

	public String enableForcedHttpsNoRedirect(){
		String ret = "OK";

		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "enableForcedHttpsNoRedirect"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			if(output == null || output.getExitCode() != 0) {
				ret = "Failed to Enable SSL No Redirect";
				return ret;
			}

		} catch (ShellExecutorException e) {
			logger.debug("Exception003 while enabling forced SSL No Redirect: " + e.getMessage());
		}

		return ret;
	}
	
	public String disableForcedHttps(){
		String ret = "OK";
		
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "disableForcedHttps"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			if(output == null || output.getExitCode() != 0) {
				ret = "Failed to Disable SSL";
				return ret;
			}
		} catch (ShellExecutorException e) {
		    logger.debug("Exception003 while disabling forced SSL: " + e.getMessage());
        }	
		
		return ret;
	}
	
	public String applyApacheChanges(){
		String ret = "OK";
		String[] cmd = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS, "init"};
		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			logger.debug(output.getStdOut());
			logger.warn(output.getStdErr());
		} catch (ShellExecutorException e) {
		    logger.error("Exception006 while applyApacheChanges(): " + e.getMessage());
        }	
		reloadApacheConfig();
		return ret;
	}

	public void reloadApacheConfig(){
		String command = "sudo /etc/init.d/apache2 reload";
		try {
			logger.debug("reload apache config");
			ShellCapture output = ShellExecutor.execute(command);
			logger.debug(output.getStdOut());
			logger.warn(output.getStdErr());
		} catch (ShellExecutorException e) {
			logger.error("Exception while reloadApacheConfig(): " + e.getMessage());
		}
	}
	
	private String sslStripCharacters(String field){
		String ret = "";
		if(field == null){
			field = "";
		}
		field = field.trim();
		for(int i=0; i < field.length();i++){
			if(     (field.charAt(i) != '<')&&
					(field.charAt(i) != '>')&&
					(field.charAt(i) != '~')&&
					(field.charAt(i) != '#')&&
					(field.charAt(i) != '$')&&
					(field.charAt(i) != '%')&&
					(field.charAt(i) != '^')&&
					(field.charAt(i) != '/')&&
					(field.charAt(i) != '\\')&&
					(field.charAt(i) != '(')&&
					(field.charAt(i) != ')')&&
					(field.charAt(i) != '?')&&
                    (field.charAt(i) != '`')&&
					(field.charAt(i) != '"')){
				ret += field.charAt(i);
			}
		}
		return ret;
	}
	
	public String generateRandomString(int len){
		String str= "QAa0bcLdUK2eHfJgTP8XhiFj61DOklNm9nBoI5pGqYVrs3CtSuMZvwWx4yE7zR";
		StringBuilder sb=new StringBuilder();
	 	Random r = new Random();
	 	int te=0;
	 	for(int i=1;i<=len;i++){
	 		te=r.nextInt(62);
	 		sb.append(str.charAt(te));
	 	}
        return sb.toString();
	}
	
	
	public List<ConfigProperty> getOCSPConfigProperties(){
		List<ConfigProperty> ret = new ArrayList<ConfigProperty>();
		File ocspFile = new File(FILE_OCSP_INFO);
		if(!ocspFile.exists()){
			return ret;
		}
		String[] command = new String[]{"sudo", "-n", SCRIPT_OCSP_CONFIG};
		try{
		    Process p = Runtime.getRuntime().exec(command);
		    BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
			String s = null;
			
			while ((s = stdError.readLine()) != null) {
				logger.debug(s);
				
			}
			
			while ((s = stdInput.readLine()) != null) {
				String line = s.trim();
				int pipeIndex = line.indexOf("=");
			     if(pipeIndex > -1){
			    	 ConfigProperty prop = new ConfigProperty();
			    	 String name = line.substring(0,pipeIndex);
			    	 String value = line.substring(pipeIndex+1, line.length());
			    	 
			    	 prop.setName(name);
			    	 prop.setValue(value);
			    	 ret.add(prop);
			     }
			}

			stdInput.close();
			stdError.close();
			
		}catch (IOException e) {
			logger.debug("Exception while getOCSPConfigProperties: " + e.getMessage());
	    }	

		return ret;
	}
	
	public ConfigProperty getOCSPConfigProperty(String name, List<ConfigProperty> props){
		ConfigProperty ret = new ConfigProperty();
		if(name == null) name = "";

		for (ConfigProperty prop : props) {
			if (name.equalsIgnoreCase(prop.getName())) {
				ret = prop;
			}
		}
		if(ret.getName().equals("")){
			ret.setName(name);
		}
		
		return ret;
	}
	
	public int setOCSPConfigProperties(boolean ocspConfigFlag, boolean ocspUrlOverrideFlag, String ocspUrl) {
		int ret = -1;
		if(ocspUrl == null)         ocspUrl = "";

        String ocspConfig = "off";
        if (ocspConfigFlag) {
            ocspConfig = "on";
        }
        String ocspUrlOverride = "off";
        if (ocspUrlOverrideFlag) {
            ocspUrlOverride = "on";
        }

        String[] cmd = { "sudo", "-n", SCRIPT_OCSP_CONFIG, ocspConfig.toLowerCase(),  ocspUrlOverride.toLowerCase(), ocspUrl.toLowerCase() };

		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			ret = output.getExitCode();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			logger.debug("Exception while setOCSPConfigProperties: " + e.getMessage());
		}

		return ret;
	}
	
	
	public String getApacheVersion(){
		String ret = "0.0.0";
		String command = "/usr/sbin/apache2 -v";
		
		try{
		    Process p = Runtime.getRuntime().exec(command);
		    BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
			String s = null;
			
			while ((s = stdError.readLine()) != null) {
				logger.debug(s);
				
			}
			
			while ((s = stdInput.readLine()) != null) {
				if(s.contains("Server version:")){
					String sVersion = s.substring(23, s.length()).trim();
					int bIndex = sVersion.indexOf(" ");
					if(bIndex > -1){
						sVersion = sVersion.substring(0, bIndex).trim();
					}
					ret = sVersion;
				}
				logger.debug(s);
			}
			
			
			stdInput.close();
			stdError.close();
			
		}catch (IOException e) {
			logger.debug("Exception while getApacheVersion: " + e.getMessage());
	    }	
		
		
		return ret;
	}
	
	
	private boolean isCountryCodeValid(String code){
		if (StringUtils.isEmpty(code)) {
			return false;
		}
		return ArrayUtils.contains(CSR_VALID_COUNTRIES, code.toUpperCase());
	}

	/**
	 * APPS_NAME=super,HTTPS_IFC=eth0,HTTPS_PORT=443,HTTP_IFC=eth0,HTTP_PORT=80,
	 * OCSP=Y
	 *
	 * @param appNames
	 * @return
	 */

	public List<ApplicationConfiguration> getAppsPortInfo(List<String> appNames) {
		String cmd = SCRIPT_APPS_PORT;

		List<ApplicationConfiguration> appConfigs = new ArrayList<ApplicationConfiguration>();
		String args = StringUtils.join(appNames, " ");

		List<String> stdOutLines;

		try {
			ShellCapture capture = ShellExecutor.execute(cmd + " " + args);
			stdOutLines = capture.getStdOutLines();
			if (capture.isErrorExitCode()) {
				return null;
			}
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return null;
		}

		for (String line : stdOutLines ) {
			String[] tokens = line.split(",");
			ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
			for (String config : tokens) {
				String[] configTokens = config.split("=");
				if (configTokens[0].equalsIgnoreCase("APPS_NAME")) {
					applicationConfiguration.setAppName(configTokens[1]);
				}
				if (configTokens[0].equalsIgnoreCase("HTTPS_IFC")) {
					String networkInterface = (configTokens[1].trim()
							.equalsIgnoreCase("eth0")
							|| configTokens[1].trim().equalsIgnoreCase("eth0") || configTokens[1]
							.trim().equalsIgnoreCase("eth0")) ? "PRODUCTION"
							: "MANAGEMENT";
					applicationConfiguration
							.setNetworkInterface(networkInterface);
				}
				if (configTokens[0].equalsIgnoreCase("HTTPS_PORT")) {
					applicationConfiguration.setSecurePort(Integer
							.parseInt(configTokens[1].trim()));
				}
				if (configTokens[0].equalsIgnoreCase("HTTP_PORT")) {
					applicationConfiguration.setUnsecurePort(Integer
							.parseInt(configTokens[1].trim()));
				}
				if (configTokens[0].equalsIgnoreCase("OCSP")) {
					boolean ocsp = configTokens[1].trim().equalsIgnoreCase("Y");
					applicationConfiguration.setOcsp(ocsp);
				}

			}
			appConfigs.add(applicationConfiguration);
		}

		return appConfigs;
	}

	public boolean updateAppAccessConfiguration(String appName,
			String networkInterface, String unsecurePort, String securePort,
			String ocsp) {
		networkInterface = networkInterface.equalsIgnoreCase("PRODUCTION") ? "eth0"
				: "eth1";
		ocsp = ocsp.equalsIgnoreCase("true") ? "Y" : "N";

		securePort = (StringUtils.isEmpty(securePort)) ? "0"
				: securePort;
		unsecurePort = (StringUtils.isEmpty(unsecurePort)) ? "0"
				: unsecurePort;
		File tempFile = null;
		try {
			tempFile = new File(getTmpDirectory() + appName + ".vidyohost");
			tempFile.createNewFile();
		} catch (IOException e) {
			logger.error("Exception while creating file", e);
			return false;
		}
		if (tempFile != null && tempFile.exists()) {
			try {
				if (!securePort.equalsIgnoreCase("0")) {
					FileUtils.writeStringToFile(tempFile, appName + "|" + networkInterface
							+ "|" + securePort + "|" + "Y" + "|" + ocsp);
				}
				if (!unsecurePort.equalsIgnoreCase("0")) {
					FileUtils.writeStringToFile(tempFile, "\n", true);
					FileUtils.writeStringToFile(tempFile, appName + "|" + networkInterface
							+ "|" + unsecurePort + "|" + "N" + "|" + ocsp, true);
				}
				// invoke script to copy the temp file to original location
				String[] cmd = { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "VIDYO_HOST", tempFile.getAbsolutePath() };
				try {
					ShellCapture capture = ShellExecutor.execute(cmd);
					if(capture == null || capture.getExitCode() != 0) {
						logger.error("Error while updating the .vidyohost file" + appName);
						return false;
					}
				} catch (ShellExecutorException see) {
					logger.error("ShellExecutorException while updating the .vidyohost file" + appName);
					return false;					
				}
                if ("super".equals(appName)) {
                    // copy super to temp installer file
                    File tempInstallerFile = new File(getTmpDirectory() + "installer.vidyohost");

                    if (!securePort.equalsIgnoreCase("0")) {
                        FileUtils.writeStringToFile(tempInstallerFile, "installer" + "|" + networkInterface
                                + "|" + securePort + "|" + "Y" + "|" + ocsp);
                    }
                    if (!unsecurePort.equalsIgnoreCase("0")) {
                        FileUtils.writeStringToFile(tempInstallerFile, "\n", true);
                        FileUtils.writeStringToFile(tempInstallerFile, "installer" + "|" + networkInterface
                                + "|" + unsecurePort + "|" + "N" + "|" + ocsp, true);
                    }
                    // invoke script to copy the temp file to original location
                    cmd = new String[] { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "VIDYO_HOST", tempInstallerFile.getAbsolutePath() };
    				try {
    					ShellCapture capture = ShellExecutor.execute(cmd);
    					if(capture == null || capture.getExitCode() != 0) {
    						logger.error("Error while updating the installer.vidyohost file");
    						return false;
    					}
    				} catch (ShellExecutorException see) {
    					logger.error("ShellExecutorException while updating the installer.vidyohost file" + appName);
    					return false;					
    				}                    
                }
			} catch (IOException e) {
				logger.error("Exception while creating file", e);
				return false;
			}
			return true;
		}
		return false;
	}


	public List<String> getNetworkInterfaces(String param) {
		String cmd = SCRIPT_GET_INTERFACE;

		List<String> interfaces = new ArrayList<String>();
		Map<String, String> interfaceMap = new HashMap<String, String>();
		interfaceMap.put("eth0", "PRODUCTION");
		interfaceMap.put("eth1", "MANAGEMENT");

		List<String> stdOutLines;
		try {
			ShellCapture capture = ShellExecutor.execute(cmd);
			if (capture.isErrorExitCode()) {
				return interfaces;
			}
			stdOutLines = capture.getStdOutLines();
		} catch (ShellExecutorException e) {
			e.printStackTrace();
			return interfaces;
		}
		for (String line : stdOutLines) {
			String[] interPort = line.trim().split("\\|");
			interfaces.add(interfaceMap.get(interPort[0]));
		}
		return interfaces;
	}

	private void deleteFiles(String...files) {
		for (String file : files) {
			FileUtils.deleteQuietly(new File(file));
		}
	}
	
	public boolean importP7bCertificateBundle(String p7bFile) {
		 	String tmpDomainCertFile = getTmpDirectory() + generateRandomString(10);
		 	String tmpDomainBundleFile = getTmpDirectory() + generateRandomString(10);
		
			List<String> scriptOutput = new ArrayList<String>();
			String[] sargs = new String[] {"sudo", "-n", SCRIPT_SSL_FUNCTIONS,
	                "convertP7B",
	                p7bFile,
	                tmpDomainCertFile,
	                tmpDomainBundleFile
	        };
			
			try {
				ShellCapture output = ShellExecutor.execute(sargs);
			    scriptOutput.addAll(output.getStdOutLines());
			    scriptOutput.addAll(output.getStdErrLines());
		    } catch (ShellExecutorException e) {
			    logger.debug("Error while importing P7B: " + e.getMessage());
			    deleteFiles(tmpDomainCertFile, tmpDomainBundleFile);
			    return false;
	        }	

			if(scriptOutput.size() == 0){
				if(isCertInvalid(tmpDomainCertFile)) {
					logger.error("error: uploaded cert in p7b is invalid");
					return false;
				}
				if (!isCertMatchToKey(tmpDomainCertFile)) {
					logger.error("error: uploaded cert in p7b does not match private key");
					return false;
				}
				if (!isCertBundleValid(tmpDomainBundleFile)) {
					logger.error("error: uploaded intermediate cert in p7b is invalid");
					return false;
				}
				try {
					// invoke script to update domain-server.crt
					String[] cmd = {"sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "DOMAIN_CERT", tmpDomainCertFile};
					try {
						ShellCapture capture = ShellExecutor.execute(cmd);
						if(capture == null || capture.getExitCode() != 0) {
							logger.error("Error while updating the domain server cert file");
							return false;
						}
					} catch (ShellExecutorException see) {
						logger.error("ShellExecutorException while updating the domain server cert file");
						return false;					
					}
					cmd = new String[] {"sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "BUNDLE_FILE", tmpDomainBundleFile};
					try {
						ShellCapture capture = ShellExecutor.execute(cmd);
						if(capture == null || capture.getExitCode() != 0) {
							logger.error("Error while updating the domain server cert file");
							return false;
						}
					} catch (ShellExecutorException see) {
						logger.error("ShellExecutorException while updating the domain server cert file");
						return false;					
					}

                    if ((new File(tmpDomainBundleFile)).length() != 0) {
                        importRootCACert(SecurityServiceImpl.FILE_SERVER_INTERMEDIATE_CERT, true);
                    }
	    		    deleteFiles(tmpDomainCertFile, tmpDomainBundleFile);
					mergeKeyAndCert();
	    			return true;
	    		} catch (Exception ioe) {
			    	logger.error("error: IO exception copying files");
	    			ioe.printStackTrace();
	    		}
		    } else {
		    	logger.error("error: " + scriptOutput.get(0));
		    }

		    deleteFiles(tmpDomainCertFile, tmpDomainBundleFile);
			return false;
	}

	public boolean importPfxCertificateBundle(String pfxFile, String password) {

		String passwordFile = getTmpDirectory() + generateRandomString(10) + ".import.pwd";

		try {
			FileUtils.writeStringToFile(new File(passwordFile), password);

			String[] cmd = new String[] {"sudo", "-n", SCRIPT_PFX_IMPORT, passwordFile, pfxFile};
			ShellCapture output = ShellExecutor.execute(cmd);
			String s = output.getStdErr();
			if (output.isErrorExitCode()) {
				logger.error("[" + s + "] is returned from the import_pfx.sh script ");
				return false;
			}
			return true;

		} catch (ShellExecutorException e) {
			logger.debug("importPfxSecurityBundle() failed: " + e.getMessage());
		} catch (IOException ioe) {
			logger.debug("IOException: " + ioe.getMessage());
		} finally {
			FileUtils.deleteQuietly(new File(passwordFile));
		}

		return false;
	}

	
	public String exportVidyoSecurityBundle(String password) {
		String fn = "security_bundle.pfx";

		File pwdFile = new File(getTmpDirectory() + System.currentTimeMillis() + ".export.pwd");
		try {
			FileUtils.writeStringToFile(pwdFile, password);
		} catch (IOException ioe) {
			logger.error("Unable to write export pwd file: " + pwdFile.getAbsolutePath());
			return null;
		}

        try {
			String[] cmd ={"sudo", "-n", SCRIPT_PACK_SECURITY, pwdFile.getCanonicalPath(), fn};
	        ShellCapture output = ShellExecutor.execute(cmd);
            String s = output.getStdErr();
            if (output.isErrorExitCode()) {
                logger.error("["+s+"] is returned from the export_pfx.sh script ");
                return null;
            }
            return fn;
        } catch(ShellExecutorException e) {
            logger.debug("exportVidyoSecurityBundle() failed: "+e.getMessage());
        } catch(IOException ioe) {
			logger.debug("IOException: " + ioe.getMessage());
		} finally {
			FileUtils.deleteQuietly(pwdFile);
		}
		return null;
	}

	public String importVidyoSecurityBundle(String fileLocation, String password) {

		File pwdFile = new File(getTmpDirectory() + System.currentTimeMillis() + ".import.pwd");
		try {
			FileUtils.writeStringToFile(pwdFile, password);
		} catch (IOException ioe) {
			logger.error("Unable to write import pwd file: " + pwdFile.getAbsolutePath());
			return "error checking password";
		}

		String[] cmd ={"sudo", "-n", SCRIPT_UNPACK_SECURITY, fileLocation, pwdFile.getName() };

		try {
			ShellCapture output = ShellExecutor.execute(cmd);
			String s = output.getStdOut();
			if(s!=null && s.startsWith("UNPACKSECFAILED")) {
				logger.error("["+s+"] is returned from the unpack_security.sh script ");
				return "error uncompressing upload";
			}
		}
		catch(ShellExecutorException e) {
			logger.debug("prepareSecurityBundle() failed: "+e.getMessage());
			return "error uncompressing upload";
		} finally {
			FileUtils.deleteQuietly(pwdFile);
		}
		return "";
	}
	
	public boolean importRootCACert(String rootFile, boolean append) {
		File tmpRootFile = new File(rootFile);
		
		boolean foundErrors = false;
		if (isCertBundleValid(rootFile)){
		    List<String> tempCertFileNames  =  parseCertBundle(rootFile);
			for (String tempCertFileName : tempCertFileNames) {
				if (isCertInvalid(tempCertFileName)) {
					foundErrors = true;
				}
				File tempCertFile = new File(tempCertFileName);
				tempCertFile.delete();
			}
		} else {
			foundErrors = true;
		}
		
		if (foundErrors) {
			logger.error("client ca root certs file is invalid");
			return false;
		}
		
		File clientRoot = new File(FILE_CLIENT_CERTS);
		String[] command = null;
		if (append && clientRoot.exists()) {
			command = new String[] { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "APPEND_CLIENT_CACERT", tmpRootFile.getAbsolutePath() };
		} else {
			command = new String[] { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "REPLACE_CLIENT_CACERT", tmpRootFile.getAbsolutePath() };
		}			
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Shell Exception - "+ command + " "+ capture.getStdErr());
				return false;
			}	
		} catch (ShellExecutorException e) {
			logger.error("Shell Exception - " + command + " " + e.getMessage());
			return false;
		}

		File defaultRoot = new File(FILE_DEFAULT_CLIENT_CERTS);
		if (defaultRoot.exists() && isUseDefaultRootCerts()) {
			command = new String[] { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "MERGE_VIDYO_CLIENT_CACERT", tmpRootFile.getAbsolutePath() };
		} else {
			command = new String[] { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "USE_CLIENT_CACERT", tmpRootFile.getAbsolutePath()};
		}
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Shell Exception - "+ command + " "+ capture.getStdErr());
				return false;
			}	
		} catch (ShellExecutorException e) {
			logger.error("Shell Exception - " + command + " " + e.getMessage());
			return false;
		}

        try {
            importRootCACertNSS();
        } catch (ShellExecutorException e) {
            logger.error("failure import caroot via NSS: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
	}

    public void importRootCACertNSS() throws ShellExecutorException {
        File nssScript = new File(SCRIPT_NSS_UTILS);
        if (!nssScript.exists()) {
            return; // hack: no such script on standalone vidyorouter
        }
        String[] cmd ={"sudo", "-n", SCRIPT_NSS_UTILS, " --import_caroot" };
        ShellCapture capture = ShellExecutor.execute(cmd);
        if (capture.isErrorExitCode()) {
            throw new ShellExecutorException("error exit code from nss_utils script");
        }
    }
	
	public boolean resetSecuritySettings() {
		String[] command = { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "RESET_SECURITY" };
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Shell Exception - "+ command + " "+ capture.getStdErr());
				return false;
			}	
		} catch (ShellExecutorException e) {
			logger.error("Shell Exception - " + command + " " + e.getMessage());
			return false;
		}		
		
        try {
            importRootCACertNSS();
        } catch (ShellExecutorException e) {
            logger.error("failure import caroot via NSS: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

		return true;
	}

	public void removeKeyPassword(String filePath){
		String command = "/usr/bin/openssl rsa -in " + filePath + " -out " + filePath;
		try {
			ShellExecutor.execute(command);
		} catch (ShellExecutorException see) {
			see.printStackTrace();
		}	 
	}

	public void mergeKeyAndCert() throws Exception {
		// only bundle if they go together
		if (isCertMatchToKey(FILE_SERVER_CERT)) {
			String[] cmd = { "sudo", "-n", SCRIPT_VIDYO_SSL, "MERGE_DOMAIN_CERT_KEY" };
			try {
				ShellCapture capture = ShellExecutor.execute(cmd);
				if (capture == null || capture.getExitCode() != 0) {
					throw new SecurityServiceException("Command to merge domain cert key returned error " + capture.getStdErr());
				}								
			} catch (ShellExecutorException e) {
				throw new SecurityServiceException("Command to merge domain cert key returned error", e);
			}			
		}
	}

	public boolean setUseDefaultRootCerts(boolean useDefaultFlag) {
		String arg = "off";
		if (useDefaultFlag) {
			arg = "on";
		}
		try {
			ShellCapture capture = ShellExecutor
					.execute(new String[] {"sudo", "-n", SCRIPT_USE_DEFAULT_ROOT_CERTS, arg});
			if(capture == null || capture.getExitCode() != 0) {
				logger.error("Error updating the cacert.root " + capture.getExitCode());
				return false;
			}
			try {
				importRootCACertNSS();
			} catch (ShellExecutorException e) {
				logger.error("failure import caroot via NSS: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		} catch (ShellExecutorException see) {
			logger.error("Error configuring use of default root CA certs: "
					+ see.getMessage());
		}
		return false;
	}

	public boolean isUseDefaultRootCerts() {
		try {
			ShellCapture capture = ShellExecutor.execute(new String[] { "sudo", "-n", SCRIPT_USE_DEFAULT_ROOT_CERTS });
			if (capture != null && capture.getStdOut() != null) {
				return "USE_VIDYO_CACERT=on".equals(StringUtils.trim(capture.getStdOut()));
			}
		} catch (ShellExecutorException see) {
			logger.error("Error determining use of default root CA certs: " + see.getMessage());
		}
		return true;
	}

	@Override
	public void uploadDomainCert(String tempFilePath) throws SecurityServiceException{
		String[] command = { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "DOMAIN_CERT", tempFilePath };
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				throw new SecurityServiceException("Upload Domain Cert - Shell Exception " + capture.getStdErr());
			}	
		} catch (ShellExecutorException e) {
			throw new SecurityServiceException("Upload Domain Cert - Shell Exception", e);
		}		
        try {
			mergeKeyAndCert();
		} catch (Exception e) {
			logger.error("Upload Cert - Error while merging key and cert");
			throw new SecurityServiceException("Upload Cert - Error while merging key and cert");
		}
        if (isSSLEnabled()) {
            applyApacheChanges();
        }		
	}

	@Override
	public void uploadCertInt(String tempFileCertIntPath)
			throws SecurityServiceException {
		String[] command = { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "BUNDLE_FILE", tempFileCertIntPath };
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				throw new SecurityServiceException("Upload Domain Cert Intermediates - Shell Exception " + capture.getStdErr());
			}	
		} catch (ShellExecutorException e) {
			throw new SecurityServiceException("Upload Domain Cert Intermediates - Shell Exception", e);
		}	        
        importRootCACert(SecurityServiceImpl.FILE_SERVER_INTERMEDIATE_CERT, true);
        if (isSSLEnabled()) {
            applyApacheChanges();
        }
		
	}
	
	public boolean updatePrivateKey(String tempKeyFilePath) {
		String[] command = { "sudo", "-n", SCRIPT_VIDYO_UPDATE_SECURITY, "PRIVATE_KEY", tempKeyFilePath };
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture == null || capture.getExitCode() != 0) {
				logger.error("Update Private Key - Shell Exception " + capture.getStdErr());
				return false;
			}	
		} catch (ShellExecutorException e) {
			logger.error("Update Private Key - Shell Exception", e);
			return false;
		}		
		
		try {
			mergeKeyAndCert();
		} catch (Exception e) {
			logger.error("Exception while merging key and cert ", e.getMessage());
			return false;
		}
		// Success if it reaches here
		return true;
	}
	
	/**
	 * CAUTION: Please do not change any method's signature without checking the SecurityTransactionInterceptor code.
	 */


}

