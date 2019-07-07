/**
 * 
 */
package com.vidyo.service.system;

import java.util.Arrays;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.log4j.Logger;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Service;
import com.vidyo.db.ISystemDao;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.ws.manager.VidyoManagerServiceStub;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author Ganesh
 * 
 */
public class SystemServiceImpl implements SystemService, DisposableBean {

	/**
	 * 
	 */
	private static final Logger logger = Logger
			.getLogger(SystemServiceImpl.class);

	/**
	 * 
	 */
	private ISystemDao systemDao;

	/**
	 * 
	 */
	private HttpConnectionManager httpConnectionManager;
	private ConfigurationContext axisConfigContext = null;

	/**
	 * @param httpConnectionManager
	 *            the httpConnectionManager to set
	 */
	public void setHttpConnectionManager(
			HttpConnectionManager httpConnectionManager) {
		this.httpConnectionManager = httpConnectionManager;
	}

	/**
	 * @param systemDao
	 *            the systemDao to set
	 */
	public void setSystemDao(ISystemDao systemDao) {
		this.systemDao = systemDao;
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

	/**
	 * 
	 * @return
	 * @throws NoVidyoManagerException
	 */
	@Override
	// stubs are not thread safe
	//@Cacheable(cacheName = "vidyoManagerServiceStubCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public VidyoManagerServiceStub getVidyoManagerServiceStubWithAuth(
			int tenantID) throws NoVidyoManagerException {
		Service service = getVidyoManagerService(tenantID);

		if (service == null) {
			logger.error("Cannot find VidyoManager for tenant with tenantID = "
					+ tenantID);
			throw new NoVidyoManagerException();
		}

		String vidyoManagerServiceAddress = service.getUrl();
		String vidyoManagerServiceUser = service.getUser();
		String vidyoManagerServicePassword = service.getPassword();

		try {
			VidyoManagerServiceStub stub = new VidyoManagerServiceStub(axisConfigContext,
					vidyoManagerServiceAddress);

			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(vidyoManagerServiceUser);
			auth.setPassword(vidyoManagerServicePassword);
			auth.setPreemptiveAuthentication(true);

			HttpClient httpClient = new HttpClient(httpConnectionManager);
			Options opt = stub._getServiceClient().getOptions();
			
			opt.setProperty(HTTPConstants.AUTHENTICATE, auth);

			// Set HTTP 1.0 protocol for auto close connection after response -
			// JUST IN CASE IF NEEDED
			// opt.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
			// HTTPConstants.HEADER_PROTOCOL_10);

			// Add HTTP header into each requests
			// Connection: close
			opt.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
			opt.setProperty(HTTPConstants.HEADER_CONNECTION,
					HTTPConstants.HEADER_CONNECTION_CLOSE);
			opt.setProperty(HTTPConstants.MULTITHREAD_HTTP_CONNECTION_MANAGER,
					httpConnectionManager);
			opt.setCallTransportCleanup(true);
			stub._getServiceClient().getServiceContext().getConfigurationContext().setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
			stub._getServiceClient().getServiceContext().getConfigurationContext().setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
			//stub._getServiceClient().getServiceContext().getConfigurationContext().setProperty(HTTPConstants.AUTO_RELEASE_CONNECTION, true);
			stub._getServiceClient().setOptions(opt);
			return stub;
		} catch (AxisFault e) {
			logger.error(e.getMessage());
			throw new NoVidyoManagerException();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public Service getVidyoManagerService(int tenantID) {
		Service service = null;

		service = systemDao.getVidyoManagerService(tenantID);

		if (service != null) {
			// change URL to Web Service path
			StringBuffer sb = new StringBuffer();

			String url = service.getUrl();
			sb.append(url);
			boolean overrideUrl = false;
			if (url.indexOf("http") == -1) {
				sb.insert(0, "http://");
				//sb.append("http://").append(url);
				overrideUrl = true;
			}
			if (!url.contains("/services/VidyoManagerService")) {
				sb.append("/services/VidyoManagerService");
				overrideUrl = true;
			}

			if (overrideUrl) {
				service.setUrl(sb.toString());
			}
		}

		return service;
	}
	
	/**
	 * 
	 * @param configName
	 * @return
	 */
	@Override
	public Configuration getConfiguration(String configName) {
		Configuration configuration = systemDao.getConfiguration(0, configName);
		return configuration;
	}
	
	/**
	 * Returns true if FIPS mode is enabled
	 * 
	 * @return
	 */
	public boolean isFipsModeEnabled() {
		boolean isFips = false;
		String trustStoreProvider = System.getProperty("javax.net.ssl.trustStoreProvider");
		String keyStoreProvider = System.getProperty("javax.net.ssl.keyStoreProvider");
		if (trustStoreProvider.contains("SunPKCS11-NSS") && keyStoreProvider.contains("SunPKCS11-NSS")) {
			return true;
		}
		return isFips;
	}
	
	/**
	 * Utility method to clear vidyo manager cache. Implementation will specify which cache
	 * has to be cleared.
	 * 
	 */
	@TriggersRemove(cacheName = { "vidyoManagerIdCache",
			"vmConnectAddressCache", 
			"vidyoManagerServiceCache", "vidyoManagerServiceStubCache",
			"licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	@Override
	public void clearVidyoManagerCache() {
		logger.debug("Utility to clear vidyo manager cache");
	}

	@Override
	public int updateVidyoManagerCredentials() {
				
		// Get the license token from VidyoManager and update it in the DB for
		// all VidyoManager entries
		ShellCapture shellCapture = null;
		String[] cmd = {"sudo", "-n", "/opt/vidyo/bin/get_license_info.sh", "VMSoapUser", "VMSoapPass"};
		try {			
			shellCapture = ShellExecutor.execute(cmd);
		} catch (ShellExecutorException e) {
			logger.error("Error while executing script -" + Arrays.toString(cmd) + "-", e);
			throw new RuntimeException("Cannot update VM credentials after license update, System won't function properly");
		}
		List<String> stdOutLines = null;
		if (shellCapture != null) {
			stdOutLines = shellCapture.getStdOutLines();
		}
		int count = 0;
		if (stdOutLines != null && !stdOutLines.isEmpty()) {
			// Parse the userid and password
			String userId = null;
			String password = null;
			for (String outLine : stdOutLines) {
				if (outLine.contains("VMSoapUser")) {
					String[] outLineVal = outLine.split("=");
					if (outLineVal.length == 2) {
						userId = outLineVal[1];
					}
				}
				if (outLine.contains("VMSoapPass")) {
					String[] outLineVal = outLine.split("=");
					if (outLineVal.length == 2) {
						password = outLineVal[1];
					}
				}
			}

			if (userId != null && password != null) {
				count = systemDao.updateServiceCredentials(userId, password, "VidyoManager");
				if (count == 0) {
					logger.error("VidyoManager credentials not updated, Error condition - Portal DB issue");
					throw new RuntimeException("Cannot update VM credentials after license update, System won't function properly");
				}
			} else {
				logger.error("Cannot get credentials from VidyoManager - license info");
				throw new RuntimeException("Cannot update VM credentials after license update, System won't function properly");
			}
		} else {
			logger.error("Cannot get credentials from VidyoManager - license info");
			throw new RuntimeException("Cannot update VM credentials after license update, System won't function properly");
		}		
		return count;
	}
	
	@Override
	@TriggersRemove(cacheName = { "vidyoManagerServiceCache", "vidyoManagerServiceStubCache" }, removeAll = true)
	public int updateComponentsEncryption(boolean value) {
		Configuration configuration = getConfiguration("COMPONENTS_ENCRYPTION");
		// Update only if the value is changed
		if (configuration == null
				|| configuration.getConfigurationValue() == null
				|| Boolean.valueOf(configuration.getConfigurationValue()) != value) {
			return systemDao.updateConfiguration(0,
					"COMPONENTS_ENCRYPTION", String.valueOf(value));
		}
		return 0;
	}

}
