package com.vidyo.service;

import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.bo.Tenant;
import com.vidyo.db.license.LicenseDao;
import com.vidyo.service.license.response.LicenseResponse;
import com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest;
import com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

public class LicensingServiceImpl implements LicensingService {
	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(LicensingServiceImpl.class.getName());

	public static final String LIC_VERSION_20 = "20"; // ports
	public static final String LIC_VERSION_21 = "21"; // lines (some things count)
	public static final String LIC_VERSION_22 = "22"; // UVL lines (everything counts except executive)
	public static final String LIC_ENCRIPTION_NONE = "none";
	public static final String LIC_VERSION = "Version";
	public static final String LIC_START_DATE = "Start Date";
	public static final String LIC_EXPIRY_DATE = "Expiry Date";
	public static final String LIC_SEAT_START_DATE = "SeatStartDate";
	public static final String LIC_SEAT_EXIRY_DATE = "SeatExpiry";
	public static final String LIC_EVENT_LICENSE_EXPIRY = "EventLicenseExpiry";
	public static final String LIC_EVENT_LICENSE_PORTS = "EventLicensePorts";
	public static final String LIC_VM_SOAP_USER = "VMSoapUser";
	public static final String LIC_VM_SOAP_PASS = "VMSoapPass";
	public static final String LIC_SEATS = "Seats";
	public static final String LIC_PORTS = "Ports";
	public static final String LIC_INSTALLS = "Installs";
	public static final String LIC_LIMIT_TYPE_EXECUTIVE_SYSTEM = "LimitTypeExecutiveSystem";
	public static final String LIC_LIMIT_TYPE_PANORAMA_SYSTEM = "LimitTypePanoramaSystem";
	public static final String LIC_ALLOW_USER_APIS = "AllowUserAPIs";
	public static final String LIC_ALLOW_PORTAL_APIS = "AllowPortalAPIs";
	public static final String LIC_ALLOW_EXT_DB = "AllowExtDB";
	public static final String LIC_ENCRIPTION = "Encryption";
	public static final String LIC_MULTITENANT = "MultiTenant";
	public static final String LIC_ALLOW_OCS = "AllowOCS";
	public static final String LIC_SN = "SN";
	public static final String LIC_LICENSEE_EMAIL = "LicenseeEmail";

	// IoC instance
	private ISystemService system;
	private IMemberService member;
	private ITenantService tenant;

	private LicenseDao licenseDao;

	/**
	 * @param licenseDao
	 *            the licenseDao to set
	 */
	public void setLicenseDao(LicenseDao licenseDao) {
		this.licenseDao = licenseDao;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

	public void setTenant(ITenantService tenant) {
		this.tenant = tenant;
	}

	public List<SystemLicenseFeature> getTenantLicense(int tenantId) {
		List<SystemLicenseFeature> fList = null;

		Map<String, String> sysLicenseDataMap = licenseDao.getLicenseFeatures();
		fList = processLicenseFeatureData(tenantId, sysLicenseDataMap);

		return fList;
	}

	private List<SystemLicenseFeature> processLicenseFeatureData(Integer tenantId, Map<String, String> sysLicenseDataMap) {
		List<SystemLicenseFeature> fList = new ArrayList<SystemLicenseFeature>();
		if (sysLicenseDataMap == null || sysLicenseDataMap.isEmpty()) {
			return fList;
		}

		// Iterate the Map and process the data
		for (Map.Entry<String, String> entry : sysLicenseDataMap.entrySet()) {
			int licensed = 0;
			long current = 0;
			String licensedValue = entry.getValue().trim();
			// Seats
			if (entry.getKey().equalsIgnoreCase("Seats")) {
				licensed = member.getLicensedSeats(tenantId);
				licensedValue = String.valueOf(licensed);
				current = member.getCountSeats(tenantId);
			}
			// Ports
			if (entry.getKey().equalsIgnoreCase("Ports")) {
				licensed = member.getLicensedPorts(tenantId);
				licensedValue = String.valueOf(licensed);
				current = tenant.getUsedNumOfPorts(tenantId);
			}

			// Installs
			if (entry.getKey().equalsIgnoreCase("Installs")) {
				licensed = member.getLicensedInstalls(tenantId);
				licensedValue = String.valueOf(licensed);
				current = member.getCountInstalls(tenantId);
			}

			// LimitTypeExecutiveSystem
			if (entry.getKey().equalsIgnoreCase("LimitTypeExecutiveSystem")) {
				licensed = member.getLicensedExecutive(tenantId);
				licensedValue = String.valueOf(licensed);
				current = member.getCountExecutives(tenantId);
			}

			// LimitTypePanoramaSystem
			if (entry.getKey().equalsIgnoreCase("LimitTypePanoramaSystem")) {
				licensed = member.getLicensedPanorama(tenantId);
				licensedValue = String.valueOf(licensed);
				current = member.getCountPanoramas(tenantId);
			}

			SystemLicenseFeature feature = new SystemLicenseFeature(entry.getKey(), licensedValue,
					String.valueOf(current));
			fList.add(feature);
		}

		// forward compatibility - old license with new code
		if (sysLicenseDataMap.get("LimitTypeExecutiveSystem") == null) {
			SystemLicenseFeature executiveFeature = new SystemLicenseFeature("LimitTypeExecutiveSystem", "0", "0");
			fList.add(executiveFeature);
		}

		if (sysLicenseDataMap.get("LimitTypePanoramaSystem") == null) {
			SystemLicenseFeature executiveFeature = new SystemLicenseFeature("LimitTypePanoramaSystem", "0", "0");
			fList.add(executiveFeature);
		}
		return fList;
	}

	/**
	 * Set system license via Standalone program.
	 * 
	 * @param sysFileName
	 *            - name of the file which has Base64 data of system license
	 * @return true if successful false if failed
	 */
	public boolean setSystemLicense(String sysFileName) {
		logger.debug("Entering setSystemLicense() of LicensingServiceImpl {}", sysFileName);
		boolean isLicenseApplied = licenseDao.applySystemLicense(sysFileName);
		logger.debug("Exiting setSystemLicense() of LicensingServiceImpl");
		return isLicenseApplied;
	}

	/**
	 * Set VM license via Standalone program.
	 * 
	 * @param sysFileName
	 *            : name of the file which has Base64 data of VM license
	 * @return true if successful false if failed
	 */
	public boolean setVMLicense(String sysFileName) {
		logger.debug("Entering setVMLicense() of LicensingServiceImpl {}", sysFileName);
		boolean isVMLicenseUploaded = licenseDao.applyVMLicense(sysFileName);
		logger.debug("Exiting setVMLicense() of LicensingServiceImpl");
		return isVMLicenseUploaded;
	}

	@Cacheable(cacheName = "licenseFeatureDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public SystemLicenseFeature getSystemLicenseFeature(String featureName) {
		SystemLicenseFeature feature = null;
		Map<String, String> sysLicenseDataMap = licenseDao.getLicenseFeatures();
		String val = sysLicenseDataMap.get(featureName);
		if (val != null) {
			feature = new SystemLicenseFeature(featureName, val, null);
		}
		return feature;
	}

	public LicenseResponse testVMKeyToken(String username, String password) {
		LicenseResponse licenseResponse = new LicenseResponse();

		VidyoManagerServiceStub ws = null;
		try {
			ws = this.system.getVidyoManagerServiceStubWithAUTH(username, password);
			
			GetVidyoManagerSystemIDResponse sysIDRes = ws.getVidyoManagerSystemID(new GetVidyoManagerSystemIDRequest());
			licenseResponse.setStatus(200);
			// Token works, no need to change the user supplied values
		} catch (Exception e) {
			if (e.getMessage().contains("Unauthorized")) {
				// The password supplied is invalid, no need to change the value
				// in db
				logger.error("Testing key/token: Incorrect key/token to access VidyoManager - " + e.getMessage());
				licenseResponse.setStatus(401);
			} else if (e instanceof ConnectException) {
				// VM is down, can't validate the token, allow the user to save
				// the values - catch22 (deadlock scenario)
				// VM is not coming up as portal password is wrong in db, portal
				// is not able to talk to VM, as it is down
				logger.error("Testing key/token: Cannot access VidyoManager - " + e.getMessage());
				licenseResponse.setStatus(500);
			}
		} finally {
			if (ws != null) {
				try {
					ws.cleanup();
				} catch (AxisFault af) {
					// ignore
				}
			}
		}

		return licenseResponse;
	}

	public Map<String, String> getLicenseFeatureDataFromFile(String sysFileName) {
		logger.debug("Entering getLicenseFeatureDataFromFile() of LicensingServiceImpl {}", sysFileName);
		Map<String, String> licenseDataMap = licenseDao.getLicenseDataFromFile(sysFileName);
		logger.debug("Exiting getLicenseFeatureDataFromFile() of LicensingServiceImpl");
		return licenseDataMap;
	}

	public boolean seatLicenseExpired() {
		// assume it uses long format: "April 5, 2010"		
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG); 
		Date expiryDate;
		try {
			expiryDate = df.parse(this.getSystemLicenseFeature("SeatExpiry").getLicensedValue());
			logger.warn("First try SeatExpiry date: " + expiryDate);
		} catch (Exception pe) {
			expiryDate = null;
			logger.warn("First try SeatExpiry date failed.");
		}
		// try again
		if (expiryDate == null) { // possible format: "022510" for Feb 25, 2010
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
			try {
				expiryDate = sdf.parse(this.getSystemLicenseFeature("SeatExpiry").getLicensedValue());
				logger.warn("Second try SeatExpiry date: " + expiryDate);
			} catch (Exception pe) {
				logger.warn("Second try SeatExpiry date failed.");
				return false;
			}
		}

		Calendar expiryDateCal = GregorianCalendar.getInstance();
		expiryDateCal.setTime(expiryDate);

		Calendar now = GregorianCalendar.getInstance();

		return now.after(expiryDateCal);
	}
	
	public boolean lineLicenseExpired() {
		// assume it uses long format: "April 5, 2010"		
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG); 
		Date expiryDate;
		try {
			expiryDate = df.parse(this.getSystemLicenseFeature("Expiry Date").getLicensedValue());
			logger.debug("First try Expiry Date: " + expiryDate);
		} catch (Exception pe) {
			expiryDate = null;
			logger.debug("First try Expiry Date failed.");
		}
		// try again
		if (expiryDate == null) { // possible format: "022510" for Feb 25, 2010
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
			try {
				expiryDate = sdf.parse(this.getSystemLicenseFeature("Expiry Date").getLicensedValue());
				logger.debug("Second try Expiry Date: " + expiryDate);
			} catch (Exception pe) {
				logger.debug("Second try Expiry Date failed.");
				return false;
			}
		}

		Calendar expiryDateCal = GregorianCalendar.getInstance();
		expiryDateCal.setTime(expiryDate);

		Calendar now = GregorianCalendar.getInstance();

		return now.after(expiryDateCal);
	}

	public long getAllowedSeats() {
		long numOfSeats = this.member.getLicensedSeats();
		long currentSeatsNum = this.member.getCountSeats();
		return numOfSeats - currentSeatsNum;
	}

	public long getAllowedExecutives() {
		return this.member.getLicensedExecutive() - this.member.getCountExecutives();
	}

	public long getAllowedPanoramas() {
		return this.member.getLicensedPanorama() - this.member.getCountPanoramas();
	}

	public String checkLicenseToBeUploaded(Map<String, String> licenseDataMap) {
		try {
			// Seats
			long licenseSeats = 0;
			String seatsVal = licenseDataMap.get("Seats");
			if (seatsVal != null && !seatsVal.isEmpty()) {
				long countSeats = member.getCountAllSeats();
				try {
					licenseSeats = Long.valueOf(seatsVal);
				} catch (NumberFormatException nfe) {
					logger.warn("Seats Value is defined as non number in license file - ", seatsVal);
				}
				if (countSeats > licenseSeats) {
					return "cannot.upload.license.seat.limits.too.low.actual";
				}
			}

			// Executives
			String execVal = licenseDataMap.get("LimitTypeExecutiveSystem");
			long licenseExecutives = 0;
			if (execVal != null && !execVal.isEmpty()) {
				long countExecutives = member.getCountAllExecutives();
				try {
					licenseExecutives = Long.valueOf(execVal);
				} catch (NumberFormatException nfe) {
					logger.warn("LimitTypeExecutiveSystem Value is defined as non number in license file", execVal);
				}
				if (countExecutives > licenseExecutives) {
					return "cannot.upload.license.executive.limits.too.low.actual";
				}
			}

			// Panoramas
			String panoramaVal = licenseDataMap.get("LimitTypePanoramaSystem");
			long licensePanoramas = 0;
			if (panoramaVal != null && !panoramaVal.isEmpty()) {
				long countPanoramas = this.member.getCountAllPanoramas();
				try {
					licensePanoramas = Long.valueOf(panoramaVal);
				} catch (NumberFormatException nfe) {
					logger.warn("LimitTypePanoramaSystem Value is defined as non number in license file - ",
							panoramaVal);
				}
				if (countPanoramas > licensePanoramas) {
					return "cannot.upload.license.panorama.limits.too.low.actual";
				}
			}

			// Ports
			String portsVal = licenseDataMap.get("Ports");
			long licensePorts = 0;
			if (portsVal != null && !portsVal.isEmpty()) {
				try {
					licensePorts = Long.valueOf(portsVal);
				} catch (NumberFormatException nfe) {
					logger.warn("licensePort is defined as non number in license file - ", portsVal);
				}
				// In case of exception just skip it, count licensePorts as 0
			}

			String installsVal = licenseDataMap.get("Installs");
			long licenseInstalls = 0;
			if (installsVal != null && !installsVal.isEmpty()) {
				try {
					licenseInstalls = Long.valueOf(installsVal);
				} catch (NumberFormatException nfe) {
					logger.warn("licenseInstalls is defined as non number in license file - ", installsVal);
				}
			}

			long totalAllocatedSeats = 0L;
			long totalAllocatedInstalls = 0L;
			long totalAllocatedExecutives = 0L;
			long totalAllocatedPanoramas = 0L;

			boolean multiTenant = Boolean.valueOf(licenseDataMap.get("MultiTenant"));

			List<Integer> tenantIds = this.tenant.getAllTenantIds();

			if (!multiTenant && tenantIds.size() > 1) {
				return "cannot.upload.not.multi.tenant";
			}

			for (Integer tenantId : tenantIds) {
				Tenant tenantData = this.tenant.getTenant(tenantId);
				if (tenantData == null) {
					continue;
				}
				totalAllocatedSeats += tenantData.getSeats();
				totalAllocatedInstalls += tenantData.getInstalls();
				totalAllocatedExecutives += tenantData.getExecutives();
				totalAllocatedPanoramas += tenantData.getPanoramas();

				if (this.member.getCountSeats(tenantId) > licenseSeats) {
					return "cannot.upload.license.seat.limit.too.low.tenant.actual|" + tenantData.getTenantName();
				}
				if (tenantData.getSeats() > licenseSeats) {
					return "cannot.upload.license.seat.limit.too.low.tenant.allocated|" + tenantData.getTenantName();
				}
				if (tenantData.getPorts() > licensePorts) {
					SystemLicenseFeature feature = getSystemLicenseFeature("Version");
					if (feature != null && (feature.getLicensedValue().equals(LicensingServiceImpl.LIC_VERSION_21) ||
							feature.getLicensedValue().equals(LicensingServiceImpl.LIC_VERSION_22) )) {
						return "cannot.upload.license.line.limit.too.low.tenant.allocated|"
								+ tenantData.getTenantName();
					} else if (feature != null && feature.getLicensedValue().equals(LicensingServiceImpl.LIC_VERSION_20)) {
						return "cannot.upload.license.port.limit.too.low.tenant.allocated|"
								+ tenantData.getTenantName();
					}
				}
				if (tenantData.getInstalls() > licenseInstalls) {
					return "cannot.upload.license.install.limit.too.low.tenant.allocated|" + tenantData.getTenantName();
				}
				if (this.member.getCountExecutives(tenantId) > licenseExecutives) {
					return "cannot.upload.license.executive.limit.too.low.tenant.actual|" + tenantData.getTenantName();
				}
				if (tenantData.getExecutives() > licenseExecutives) {
					return "cannot.upload.license.executive.limit.too.low.tenant.allocated|"
							+ tenantData.getTenantName();
				}
				if (this.member.getCountPanoramas(tenantId) > licensePanoramas) {
					return "cannot.upload.license.panorama.limit.too.low.tenant.actual|" + tenantData.getTenantName();
				}
				if (tenantData.getPanoramas() > licensePanoramas) {
					return "cannot.upload.license.panorama.limit.too.low.tenant.allocated|"
							+ tenantData.getTenantName();
				}
			}

			if (totalAllocatedSeats > licenseSeats) {
				return "cannot.upload.license.seat.limits.too.low.allocated";
			}
			if (totalAllocatedInstalls > licenseInstalls) {
				return "cannot.upload.license.install.limits.too.low.allocated";
			}
			if (totalAllocatedExecutives > licenseExecutives) {
				return "cannot.upload.license.executive.limits.too.low.allocated";
			}
			if (totalAllocatedPanoramas > licensePanoramas) {
				return "cannot.upload.license.panoramas.limits.too.low.allocated";
			}

			return null;

		} catch (Exception anyEx) {
			return "SuperSystemLicense.failed.to.set.to.VM";
		}
	}

	public boolean isPortalAPIAccessEnabled() {
		String value = this.getSystemLicenseFeature("AllowPortalAPIs").getLicensedValue();
		return Boolean.valueOf(value);
	}

	/**
	 * Validates the license file signature uploaded by endpoint.<br>
	 * Returns the response with status code and consumes line flag.
	 * 
	 * @param data
	 *            data from endpoint
	 * @return license response with status code and consumes line flag
	 */
	@Override
	public LicenseResponse validateLicenseFileSignature(String data) {
		LicenseResponse licenseResponse = new LicenseResponse();
		licenseResponse.setConsumesLine(true);
		return licenseResponse;
	}

	/**
	 * Returns the license details for the entire system.
	 * 
	 * @return list of system license features
	 */
	@Override
	public List<SystemLicenseFeature> getSystemLicenseDetails() {
		List<SystemLicenseFeature> fList = new ArrayList<SystemLicenseFeature>();
		Map<String, String> sysLicenseDataMap = null;

		try {
			// There is only one VidyoManager and all tenants point to same VM
			sysLicenseDataMap = licenseDao.getLicenseFeatures();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		for (Map.Entry<String, String> entry : sysLicenseDataMap.entrySet()) {
			long inuse = 0;
			String currentValue = null;
			if (entry.getKey().equalsIgnoreCase("Seats")) {
				inuse = system.getTotalSeatsInUse();
				currentValue = String.valueOf(inuse);
			}
			if (entry.getKey().equalsIgnoreCase("Ports")) {
				inuse = system.getTotalPortsInUse(sysLicenseDataMap.get("Version"));
				currentValue = String.valueOf(inuse);
			}
			if (entry.getKey().equalsIgnoreCase("Installs")) {
				inuse = system.getTotalInstallsInUse(system.getCDRformat());
				currentValue = String.valueOf(inuse);
			}
			if (entry.getKey().equalsIgnoreCase("LimitTypeExecutiveSystem")) {
				inuse = member.getCountAllExecutives();
				currentValue = String.valueOf(inuse);
			}
			if (entry.getKey().equalsIgnoreCase("LimitTypePanoramaSystem")) {
				inuse = member.getCountAllPanoramas();
				currentValue = String.valueOf(inuse);
			}
			SystemLicenseFeature feature = new SystemLicenseFeature(entry.getKey(), entry.getValue(), currentValue);
			fList.add(feature);
		}

		// forward compatibility - old license with new code
		if (sysLicenseDataMap.get("LimitTypeExecutiveSystem") == null) {
			SystemLicenseFeature executiveFeature = new SystemLicenseFeature("LimitTypeExecutiveSystem", "0", "0");
			fList.add(executiveFeature);
		}

		if (sysLicenseDataMap.get("LimitTypePanoramaSystem") == null) {
			SystemLicenseFeature executiveFeature = new SystemLicenseFeature("LimitTypePanoramaSystem", "0", "0");
			fList.add(executiveFeature);
		}

		return fList;
	}
	
	/**
	 * Clears the System and VM license from the portal
	 * @return boolean indicates success if true
	 */
	public boolean clearLicense() {
		return licenseDao.clearLicense();
	}

	/**
	 * Reverts back to old license.
	 * @return
	 */	
	@Override
	public boolean revertLicense() {		
		return licenseDao.revertLicense();
	}
	
	@Override
	public boolean removeLicenseCacheData(){
		return licenseDao.removeLicenseCacheData();
	}

}
