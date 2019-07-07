/**
 * 
 */
package com.vidyo.db.license;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;

/**
 * @author ganesh
 * 
 */
public class LicenseDaoSudoImpl implements LicenseDao {

	protected static final Logger logger = LoggerFactory.getLogger(LicenseDaoSudoImpl.class);

	private String licenseScript;
	
	private String updateLicenseCache;

	private String licenseTextFilePath;

	public String getLicenseTextFilePath() {
		return licenseTextFilePath;
	}

	public void setLicenseTextFilePath(String licenseTextFilePath) {
		this.licenseTextFilePath = licenseTextFilePath;
	}

	@Override
	@Cacheable(cacheName = "licenseDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public Map<String, String> getLicenseFeatures() {
		logger.debug("Entering getLicenseFeatures(int) of LicenseDaoSudoImpl");
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --query");
		} catch (ShellExecutorException e) {
			logger.error("System License Decryption failed. Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0 && shellCapture.getExitCode() != 7) {
			logger.error("Querying license failed. Script returned error code: {}",
					shellCapture != null ? shellCapture.getExitCode() : "No ErrorCode received - Should not happen");
			throw new RuntimeException("Cannot retrieve license data");
		}
		if(shellCapture.getExitCode() == 7) {
			logger.error("System license is expired");
		}

		String licenseData = shellCapture.getStdOut().trim();
		logger.debug("Data from ShellCapture {}", licenseData);

		StringTokenizer licenseTokenizer = new StringTokenizer(licenseData, "\t\n\r\f");
		Map<String, String> licenseDataMap = new HashMap<String, String>();
		while (licenseTokenizer.hasMoreTokens()) {
			String token = licenseTokenizer.nextToken();
			String[] tokens = token.split("=");
			if (tokens != null && tokens.length == 2) {
				if(!tokens[1].trim().isEmpty()) {
					licenseDataMap.put(tokens[0].trim(), tokens[1].trim());	
				}				
			}
		}
		if (licenseDataMap.isEmpty()) {
			logger.error("Cannot retrieve license data, empty license data. Querying the License Server was successfull.");
			throw new RuntimeException("Cannot retrieve license data, empty license data");
			// Throw exception to avoid caching null data
		}
		copyLicenseData(licenseData);
		logger.debug("Exiting getLicenseFeatures(int) of LicenseDaoSudoImpl");
		return licenseDataMap;
	}

	@Override
	public Map<String, String> getLicenseDataFromFile(String fileName) {
		logger.debug("Entering getLicenseDataFromFile() of LicenseDaoSudoImpl {}", fileName);
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --decrypt=" + fileName);
		} catch (ShellExecutorException e) {
			logger.error("System License Decryption failed. Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("System License Decryption failed. {}", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received - Should not happen");
			return null;
		}

		StringTokenizer licenseTokenizer = new StringTokenizer(shellCapture.getStdOut().trim());
		Map<String, String> licenseDataMap = new HashMap<String, String>();
		while (licenseTokenizer.hasMoreTokens()) {
			String token = licenseTokenizer.nextToken();
			String[] tokens = token.split("=");
			if (tokens != null && tokens.length == 2) {
				licenseDataMap.put(tokens[0].trim(), tokens[1].trim());
			}
		}
		logger.debug("Exiting getLicenseFeatureDataFromFile() of LicenseDaoSudoImpl");
		return licenseDataMap;
	}

	/**
	 * 
	 * @param sysFileName
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "vidyoManagerServiceCache", "vidyoManagerServiceStubCache", "vidyoManagerIdCache",
			"vmConnectAddressCache", "licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public boolean applySystemLicense(String sysFileName) {
		logger.debug("Entering applySystemLicense() of LicenseDaoSudoImpl {}", sysFileName);
		boolean isLicenseApplied = false;
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --write=" + sysFileName);
		} catch (ShellExecutorException e) {
			logger.error("Updating System/Event License failed Error Detail -", e.getMessage());
		}
		if (shellCapture != null && shellCapture.getExitCode() == 0) {
			isLicenseApplied = true;
		} else {
			logger.error("Updating System/Event License failed {}", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
		}
		if (isLicenseApplied) {
			queryAndCopyLicenseData();
		}
		logger.debug("Exiting applySystemLicense() of LicenseDaoSudoImpl");
		return isLicenseApplied;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName = { "vidyoManagerIdCache", "vmConnectAddressCache", "vidyoManagerServiceCache",
			"vidyoManagerServiceStubCache", "licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public boolean applyVMLicense(String fileName) {
		logger.debug("Entering applyVMLicense() of LicenseDaoSudoImpl {}", fileName);
		boolean isVMLicenseUploaded = false;
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --setvmlicense=" + fileName);
		} catch (ShellExecutorException e) {
			logger.error("Updating VM License failed Error Detail -", e.getMessage());
		}
		if (shellCapture != null && shellCapture.getExitCode() == 0) {
			isVMLicenseUploaded = true;
		} else {
			logger.error("Updating VM License failed {}", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
		}
		queryAndCopyLicenseData();
		logger.debug("Exiting applyVMLicense() of LicenseDaoSudoImpl");
		return isVMLicenseUploaded;
	}

	/**
	 * @param licenseScript
	 *            the licenseScript to set
	 */
	public void setLicenseScript(String licenseScript) {
		this.licenseScript = licenseScript;
	}

	/**
	 * Clears the System and VM license from the portal
	 * @return boolean indicates success if true
	 */
	@Override
	@TriggersRemove(cacheName = { "vidyoManagerIdCache", "vmConnectAddressCache", "vidyoManagerServiceCache",
			"vidyoManagerServiceStubCache", "licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public boolean clearLicense() {
		logger.debug("Entering clearLicense() of LicenseDaoSudoImpl");
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --invalidate");
		} catch (ShellExecutorException e) {
			logger.error("Updating System/Event License failed Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("Clearing License from the System Failed", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
			return false;
		}
		queryAndCopyLicenseData();
		logger.debug("Exiting clearLicense() of LicenseDaoSudoImpl");
		return true;
	}

	/**
	 * Reverts back to old license.
	 * @return
	 */	
	@Override
	@TriggersRemove(cacheName = { "vidyoManagerIdCache", "vmConnectAddressCache", "vidyoManagerServiceCache",
			"vidyoManagerServiceStubCache", "licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)	
	public boolean revertLicense() {
		logger.debug("Entering revertLicense() of LicenseDaoSudoImpl");
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --rollback");
		} catch (ShellExecutorException e) {
			logger.error("Revert back to old License failed - Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("Revert back to old License failed ", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
			return false;
		}
		queryAndCopyLicenseData();
		logger.debug("Exiting revertLicense() of LicenseDaoSudoImpl");
		return true;
	}


	private void queryAndCopyLicenseData() {
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + licenseScript + " --query");
			copyLicenseData(shellCapture.getStdOut());
		} catch (ShellExecutorException e) {
			logger.error("System License Decryption failed. Error Detail -", e.getMessage());
		}
	}

	private void copyLicenseData(String licenseData) {
		try {
			FileUtils.writeStringToFile(new File(getLicenseTextFilePath()), licenseData, "UTF-8");

			Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
			filePerms.add(PosixFilePermission.OWNER_READ);
			filePerms.add(PosixFilePermission.OWNER_WRITE);
			filePerms.add(PosixFilePermission.GROUP_READ);
			Files.setPosixFilePermissions(Paths.get(getLicenseTextFilePath()), filePerms);

			// set group to 'webapps'
			UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
			GroupPrincipal group = lookupService.lookupPrincipalByGroupName("webapps");
			Files.getFileAttributeView(Paths.get(getLicenseTextFilePath()), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setGroup(group);

		} catch (IOException ioe) {
			logger.error("IOException writing to file: " + getLicenseTextFilePath());
		}

	}
	
	@Override
	@TriggersRemove(cacheName = { "licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)	
	public boolean removeLicenseCacheData() {
		logger.debug("Entering removeLicenseCacheData() of LicenseDaoSudoImpl");
		ShellCapture shellCapture = null;
		try {
			shellCapture = ShellExecutor.execute("sudo -n " + updateLicenseCache );
		} catch (ShellExecutorException e) {
			logger.error("Removal of License data cache failed. Error Detail -", e.getMessage());
		}
		if (shellCapture == null || shellCapture.getExitCode() != 0) {
			logger.error("Removal of License data cache failed with code - ", shellCapture != null ? shellCapture.getExitCode()
					: "No ErrorCode received");
			return false;
		}
		
		logger.debug("Exiting removeLicenseCacheData() of LicenseDaoSudoImpl");
		return true;
	}

	public void setUpdateLicenseCache(String updateLicenseCache) {
		this.updateLicenseCache = updateLicenseCache;
	}
	
	
}
