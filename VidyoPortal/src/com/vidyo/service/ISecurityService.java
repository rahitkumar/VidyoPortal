package com.vidyo.service;


import com.vidyo.bo.ApplicationConfiguration;
import com.vidyo.bo.security.CRTObject;
import com.vidyo.bo.security.CSRObject;
import com.vidyo.bo.security.ConfigProperty;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.exceptions.SecurityServiceException;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface ISecurityService {

	// private key methods
    public String getKeyHash() throws IOException, SecurityServiceException;
	public void generateKey(int bits) throws SecurityServiceException;
    public boolean encryptPrivateKey(String filePathEncrypted, String password) throws IOException;
    public boolean decryptPrivateKey(String filePathEncrypted, String filePathPlain, String password) throws IOException;
	public String checkKeyStatus(String filePath);
	public String getPrivateKeyBits();
	public void removeKeyPassword(String filePath);

	// csr methods
	public String getCSRContent() throws IOException;
	public CSRObject parseCSR();
	public String generateCSR(CSRObject csr);
	public boolean isCSRValid(String filePath);
	public boolean isCSRMatchToKey(String filePath);

	// cert methods
	public String getCertContent() throws IOException;
	public String generateCert();
	public CRTObject parseCert(String filePath);
	public boolean isCertMatchToKey(String filePath);
	public boolean isCertMatchToFQDN(CRTObject crt, String domain);
	public boolean isCertInvalid(String filePath);
	public boolean isCertFormatDER(String filePath);
	public boolean convertDERtoPEM(String tempCertFile,String tempCertPEMFile);

	public void mergeKeyAndCert() throws Exception;

	// int cert methods
	public String getCertBundleContent() throws IOException;
	public boolean isCertBundleValid(String filePath);
	public List<String> parseCertBundle(String filePath);

	// generic methods
	public String generateRandomString(int len);
	public String readFile(String filePath) throws IOException;
	public void writeFile(String filePath, String fileContent) throws IOException;


	// ssl commands
	public boolean isSSLEnabled();
	public String enableSSL();
	public String disableSSL();
	public boolean isForcedHTTPSEnabled();
	public String enableForcedHttps();
	public String enableForcedHttpsNoRedirect();
	public boolean isForcedHTTPSEnabledNoRedirect();
	public String disableForcedHttps();
	public String applyApacheChanges();
	public void reloadApacheConfig();

	// ocsp methods
	public List<ConfigProperty> getOCSPConfigProperties();
	public ConfigProperty getOCSPConfigProperty(String name, List<ConfigProperty> props);
	public int setOCSPConfigProperties(boolean ocspConfig, boolean ocspUrlOverride,String ocspUrl);
	public String getApacheVersion();

	// apps methods
	public List<ApplicationConfiguration> getAppsPortInfo(List<String> appNames);
	public List<String> getNetworkInterfaces(String param);
	public boolean updateAppAccessConfiguration(String appName, String networkInterface,
	                                            String securePort, String unsecurePort, String ocsp);

	// advanced  methods
	public boolean importP7bCertificateBundle(String p7bFile);
	public boolean importPfxCertificateBundle(String pfxFile, String password);
	public String importVidyoSecurityBundle(String fileLocation, String password);
	public String exportVidyoSecurityBundle(String password);
	public boolean importRootCACert(String rootFile, boolean append);
    public void importRootCACertNSS() throws ShellExecutorException;
	public boolean resetSecuritySettings();
	public boolean setUseDefaultRootCerts(boolean useDefaultFlag);
	public boolean isUseDefaultRootCerts();
	
	public void uploadDomainCert(String tempFilePath) throws SecurityServiceException;
	
	public void uploadCertInt(String tempFilePath) throws SecurityServiceException;
	
	public boolean updatePrivateKey(String tempKeyFilePath);
	public boolean uploadCACCertificates(String uploadTempDir, File tempFile, boolean equalsIgnoreCase) throws IOException;
	public List<CRTObject> extractCertificate(boolean stageFlag);

}
