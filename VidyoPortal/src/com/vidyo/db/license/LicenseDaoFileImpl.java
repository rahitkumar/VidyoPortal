package com.vidyo.db.license;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.TriggersRemove;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class LicenseDaoFileImpl implements LicenseDao {

	protected static final Logger logger = LoggerFactory.getLogger(LicenseDaoFileImpl.class);

	private String licenseFilePath;

	public String getLicenseFilePath() {
		return licenseFilePath;
	}

	public void setLicenseFilePath(String licenseFilePath) {
		this.licenseFilePath = licenseFilePath;
	}

	@Override
	@Cacheable(cacheName = "licenseDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public Map<String, String> getLicenseFeatures() {
		logger.debug("Entering getLicenseFeatures(int) of LicenseDaoFileImpl");

		File licenseFile = new File(licenseFilePath);
		if (!licenseFile.exists()) {
			logger.error("Cannot retrieve license file: " + licenseFilePath);
			// Throw exception to avoid caching null data
			throw new RuntimeException("Cannot retrieve license data, empty license data");
		}

		Map<String, String> licenseDataMap = new HashMap<String, String>();

		try {
			String licenseFeatures = FileUtils.readFileToString(licenseFile, "UTF-8");
			StringTokenizer licenseTokenizer = new StringTokenizer(licenseFeatures, "\t\n\r\f");
			while (licenseTokenizer.hasMoreTokens()) {
				String token = licenseTokenizer.nextToken();
				String[] tokens = token.split("=");
				if (tokens.length == 2) {
					if (!tokens[1].trim().isEmpty()) {
						licenseDataMap.put(tokens[0].trim(), tokens[1].trim());
					}
				}
			}
			if (licenseDataMap.isEmpty()) {
				logger.error("Cannot retrieve license data, empty license data.");
				// Throw exception to avoid caching null data
				throw new RuntimeException("Cannot retrieve license data, empty license data");
			}
		} catch (IOException ioe) {
			logger.error("IOException reading license file: " + licenseFilePath);
			throw new RuntimeException("Cannot retrieve license data, empty license data");
		}

		logger.debug("Exiting getLicenseFeatures(int) of LicenseDaoFileImpl");
		return licenseDataMap;
	}

	// license write methods not allowed for user tomcat: ROOT.war/admin.war
	// license write methods only allowed by super: super.war (using LicenseDaoSudoImpl)

	public Map<String, String> getLicenseDataFromFile(String fileName) {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}

	public boolean applySystemLicense(String sysFileName) {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}

	public boolean applyVMLicense(String fileName) {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}

	public boolean clearLicense() {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}

	public boolean revertLicense() {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}

	public boolean removeLicenseCacheData() {
		throw new RuntimeException("not allowed, use LicenseDaoSudoImpl instead");
	}
}
