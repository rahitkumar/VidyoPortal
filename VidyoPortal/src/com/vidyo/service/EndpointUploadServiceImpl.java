package com.vidyo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.EndpointUploadDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.utils.UserAgentUtils;

public class EndpointUploadServiceImpl implements EndpointUploadService {
	
    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(EndpointUploadServiceImpl.class.getName());

    private EndpointUploadDao dao;
    
    @Autowired
    private ITenantService tenant;
    
    @Autowired
    private ISystemService system;

    @Autowired
    private PortalService portalService;

    public void setDao(EndpointUploadDao dao) {
        this.dao = dao;
    }

    public List<EndpointUpload> getEndpointUploads(int start, int limit) {
        List<EndpointUpload> list = this.dao.getEndpointUploads(TenantContext.getTenantId(), start, limit);
        return list;
    }

    public List<EndpointUpload> getActiveEndpointUploads(int start, int limit) {
        List<EndpointUpload> list = this.dao.getActiveEndpointUploads(TenantContext.getTenantId(), start, limit);
        return list;
    }
    
    public List<EndpointUpload> getEndpointUploadsBySuper(int start, int limit) {
        List<EndpointUpload> list = this.dao.getEndpointUploadsBySuper(0, start, limit);
        return list;
    }

    public Long getCountEndpointUpload() {
        Long number = this.dao.getCountEndpointUpload(TenantContext.getTenantId());
        return number;
    }

    public Long getCountActiveEndpointUpload() {
        Long number = this.dao.getCountActiveEndpointUpload(TenantContext.getTenantId());
        return number;
    }
    
    public Long getCountEndpointUploadBySuper() {
        Long number = this.dao.getCountEndpointUploadBySuper(0);
        return number;
    }

    public EndpointUpload getEndpointUpload(int endpointUploadID) {
        EndpointUpload rc = this.dao.getEndpointUpload(endpointUploadID);
        return rc;
    }

    public int insertEndpointUpload(EndpointUpload client) {
        int rc = this.dao.insertEndpointUpload(TenantContext.getTenantId(), client, "A");
        return rc;
    }

    public int insertEndpointUploadBySuper(int tenant, EndpointUpload client) {
        int rc = this.dao.insertEndpointUpload(tenant, client, "S");
        return rc;
    }
    
    public int[] batchInsertEndpointUploadBySuper(List<Integer> tenantIds, EndpointUpload client) {
        int[] ids = this.dao.insertEndpointUpload(tenantIds, client, "S");
        return ids;
    }    

    public int deleteEndpointUpload(int endpointUploadID, int tenantId) {
        int rc = this.dao.deleteEndpointUpload(endpointUploadID, tenantId);
        return rc;
    }

    public int setActiveUpload(int tenant,EndpointUpload client) {
        int rc = this.dao.setActiveUpload(tenant, client);
        return rc;
    }

    public EndpointUpload getEndpointUploadForFileName(int tenantID, String filename, String endpointUploadType,
			String endpointUploadVersion) {
        EndpointUpload rc = this.dao.getEndpointUploadForFileName(tenantID, filename, endpointUploadType, endpointUploadVersion);
        return rc;
    }

    public EndpointUpload getActiveEndpointForType(String endpointUploadType) {
        EndpointUpload rc = this.dao.getActiveEndpointForType(TenantContext.getTenantId(), endpointUploadType);
        return rc;
    }

    public EndpointUpload getActiveEndpointForType(HttpServletRequest request) {
        String endpointUploadType = getType(request);
        EndpointUpload endpoint = this.dao.getActiveEndpointForType(TenantContext.getTenantId(), endpointUploadType);

        if (endpoint == null) {
        	//Adding to handle Windows platform case
        	if (endpointUploadType != null && endpointUploadType.equalsIgnoreCase("W64")){
        		// If the EndpointUploadType asked for is W64 active version and is not available, provide option of 32 bit download if available
        		endpoint = this.getActiveEndpointForType("W32");
        	} else {
	            // special case for linux, as detection flaky due to Chrome not reporting Linux distribution info
	            String userAgent = request.getHeader("User-Agent");
	            if (UserAgentUtils.is64bitLinux(userAgent)) {
	                // do we have at least one rpm or deb
	                endpoint = this.getActiveEndpointForType("X"); // deb/64bit
	                if (endpoint == null) {
	                    endpoint = this.getActiveEndpointForType("T"); // rpm64bit
	                }
	            } else if (UserAgentUtils.is32bitLinux(userAgent))  {
	                endpoint = this.getActiveEndpointForType("U"); // deb/32bit
	                if (endpoint == null) {
	                    endpoint = this.getActiveEndpointForType("S"); // rpm32bit
	                }
	            }
	        }
        }

        return endpoint;
    }

    private static String getType(HttpServletRequest request) {    	
    	// VidyoRoom is always provided clientType
        /*if(request.getSession().getAttribute("clientType") != null
    		&& !((String)request.getSession().getAttribute("clientType")).equalsIgnoreCase("")){
    		return (String)request.getSession().getAttribute("clientType");
    	}*/

    	// First time - will determinate OS family from User-Agent
        String rc = "W32";

        String agent = request.getHeader("User-Agent");

        if (agent.contains("Windows")) {
        	// Check if the windows platform is 64 bit
        	// The user agent will contain "WOW64" for FF-32bit on Windows-64bit Or "Win64" for FF-64bit on Windows-64bit 
        	if (agent.contains("WOW64") || agent.contains("Win64")) {
        		rc = "W64";
        	}
        }

        if (agent.contains("Mac OS")) {
            rc = "M";
        }

        if (agent.contains("Linux")) {
            if (agent.contains("Ubuntu")) {
                rc = "U";
                if (agent.contains("x86_64")) {
                    rc = "X";
                }
            } else if (agent.contains("Red Hat")) {
                rc = "S";
                if (agent.contains("x86_64")) {
                    rc = "T";
                }
            } else { // Linux by default - SL5
                rc = "S";
                if (agent.contains("x86_64")) {
                    rc = "T";
                }
            }
        }

        return rc;
    }

    public Long getRefNumberForEndpointFileName(EndpointUpload client) {
        Long rc = this.dao.getRefNumberForEndpointFileName(client);
        return rc;
    }

    public int deleteEndpointUploadFileName(EndpointUpload client) {
        int rc = this.dao.deleteEndpointUploadFileName(client);
        return rc;
    }

    public int deleteAllEndpointUploads() {
        int rc = this.dao.deleteAllEndpointUploads();
        return rc;
    }

    public boolean setEndpointUploadType(EndpointUpload client) {
        String fileName = client.getEndpointUploadFile();

        // Windows
        if (Pattern.matches("([A-Za-z]*)Installer-win32-([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("W32");
            return true;
        }
        if (Pattern.matches("([A-Za-z]*)Installer-win64-([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("W64");
            return true;
        }
        
        // MacOS
        else if (Pattern.matches("([A-Za-z]*)Installer-macosx-([A-Za-z0-9_]*)\\.dmg", fileName)) {
            client.setEndpointUploadType("M");
            return true;
        }
        // SL5 - 32bit and 64bit
        else if (Pattern.matches("([A-Za-z]*)Installer-sl5-([A-Za-z0-9_]*)\\.rpm", fileName)) {
            client.setEndpointUploadType("S");
            return true;
        }
        else if (Pattern.matches("([A-Za-z]*)Installer-sl564-([A-Za-z0-9_]*)\\.rpm", fileName)) {
            client.setEndpointUploadType("T");
            return true;
        }
        // Ubuntu - 32bit and 64bit
        else if (Pattern.matches("([A-Za-z]*)Installer-ubuntu-([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("U");
            return true;
        }
        else if (Pattern.matches("([A-Za-z]*)Installer-ubuntu64-([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("X");
            return true;
        }
        // VidyoRooms - HD200 and HD50/100/150
        else if (Pattern.matches("([A-Za-z]*)RoomInstallerHD200-win32-([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("V");
            return true;
        }
        else if (Pattern.matches("([A-Za-z]*)RoomInstallerA-win32-([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("R");
            return true;
        }
        // VidyoPanorama - VP600 - for now just one type     VidyoPanoramaInstallerVP600-TAG_VP_X_X_X_XXXX.exe 
        else if (Pattern.matches("([A-Za-z]*)InstallerVP600-([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("P");
            return true;
        }
        // VidyoPanorama 600 running Windows 64-bit
        else if (Pattern.matches("VidyoPanoramaInstaller-Win64-TAG_VP_([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("N");
            return true;
        }
        // VidyoPanorama 600 running Linux 32bit
        else if (Pattern.matches("VidyoPanoramaInstaller-Linux32-TAG_VP_([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("E");
            return true;
        }
        // VidyoPanorama 600 running Linux 64bit
        else if (Pattern.matches("VidyoPanoramaInstaller-Linux-TAG_VP_([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("O");
            return true;
        }
        // VidyoRoom running Windows 32-bit
        else if (Pattern.matches("VidyoPanoroomInstaller-Win32-TAG_VP_([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("Q");
            return true;
        }
        // VidyoRoom running Windows 64-bit 
        else if (Pattern.matches("VidyoPanoroomInstaller-Win64-TAG_VP_([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("Y");
            return true;
        }
        // VidyoRoom running Linux 32-bit
        else if (Pattern.matches("VidyoPanoroomInstaller-Linux32-TAG_VP_([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("F");
            return true;
        }
        // VidyoRoom running Linux 64-bit
        else if (Pattern.matches("VidyoPanoroomInstaller-Linux-TAG_VP_([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("Z");
            return true;
        }
        // Softroom running windows 64-bit
        else if (Pattern.matches("VidyoRoomSEInstaller-Win64-TAG_VP_([A-Za-z0-9_]*)\\.exe", fileName)) {
            client.setEndpointUploadType("B");
            return true;
        }
        // Softroom running Ubuntu Linux 64-bit
        else if (Pattern.matches("VidyoRoomSEInstaller-Linux-TAG_VP_([A-Za-z0-9_]*)\\.deb", fileName)) {
            client.setEndpointUploadType("C");
            return true;
        }
        // Softroom running OS X 64-bit
        else if (Pattern.matches("VidyoRoomSEInstaller-MacOSX-TAG_VP_([A-Za-z0-9_]*)\\.dmg", fileName)) {
            client.setEndpointUploadType("D");
            return true;
        }
        // TBD
        else {
            return false;
        }
    }
    
	/**
	 * Upload Endpoint software for individual Tenants. Also acts as a wrapper
	 * method not to be intercepted by the Audit Interceptor.
	 * 
	 * @param tenant
	 * @param client
	 * @return
	 */
	public int insertEndpointUploadBySuperForTenants(int tenant, EndpointUpload client) {
		int rc = insertEndpointUploadBySuper(tenant, client);
		return rc;
	}


	@Override
	public boolean deactivateEndpoints(int tenantId) {
		return this.dao.deactivateEndpoints(tenantId);
	}
	
	@Override
	public Map<String,String> getClientVersion(String type, String uploadPath, String contextAddress){
		Map<String,String> clientVerMap = new HashMap();
		EndpointUpload endpoint = null;
		StringBuffer installer = new StringBuffer();
		if (type != null && type.equalsIgnoreCase("W")){
			// Support back-word compatibility for W 
			endpoint = this.getActiveEndpointForType("W64");
			if (endpoint == null){
				// Windows 64 bit installer not there so fetch Windows 32-bit installer
				endpoint = this.getActiveEndpointForType("W32");
			}
		} else {
			endpoint = this.getActiveEndpointForType(type);
		}
		
		String filename = "";
		String activeVersion = "";
		if (endpoint != null && endpoint.getEndpointUploadVersion() != null) {
			installer.append(endpoint.getEndpointUploadFile() != null ? endpoint.getEndpointUploadFile():"");
			activeVersion =  endpoint.getEndpointUploadVersion();
		} else if (endpoint != null && endpoint.getEndpointUploadVersion() == null){
			filename = endpoint.getEndpointUploadFile();
			String path = contextAddress.substring(0, contextAddress.indexOf("/ser"));
			installer = installer.append(path).append(uploadPath).append(filename);
			
			if (!filename.equalsIgnoreCase("")) {
				int periodInd = filename.indexOf(".");
				if(periodInd == -1) {
					activeVersion = filename.substring(filename.indexOf("TAG"), filename.length());
				} else {
					activeVersion = filename.substring(filename.indexOf("TAG"), periodInd);
				}
			}
		}
		
		clientVerMap.put("CurrentTag", activeVersion);
		clientVerMap.put("InstallerURI", installer.toString());
		return clientVerMap;
	}
	
	@Override
	public int addEndpointUploadSuper(EndpointUpload endpoint, boolean isSuperExternal, boolean setActive) {
		if (endpoint == null){
			return 0;
		}
		//cleaning data in the db
        deleteEndpointUploadFileName(endpoint);
        // special record for super
        int sID = insertEndpointUploadBySuper(0, endpoint);
        if (setActive) {
            endpoint.setEndpointUploadID(sID);
        	setActiveUpload(0, endpoint);
        }
        //Retrieve all tenant ids except zero TenantId (which is super tenant)
        List<Integer> tenantIds = this.tenant.getAllTenantIds();
        // Insert endpoint record for all tenants with active flag set to zero (inactive)
		int[] endpointUploadIds = this.dao.insertEndpointUpload(tenantIds, endpoint, "S");
		List<Integer> filteredTenantIds = tenantIds.stream().filter(id -> !(isSuperExternal ^ isUploadModeExternal(id)))
				.collect(Collectors.toList());
    	// If the tenant and super endpoint upload modes are matching
        // then only activate the endpoint for tenant, otherwise, let it be inactive
        // for the condition- if admin removed one end point in the admin page then later super set as active the same one from the super page/API.
        if(setActive) {
        	logger.debug("Deactivating the Endpoint Type {} for Tenants {} ", endpoint.getEndpointUploadType(), filteredTenantIds);
        	// Deactivate the 
        	dao.deactivateEndpoints(filteredTenantIds, endpoint.getEndpointUploadType());
        	logger.debug("Activating the Endpoint Type {}, File {}, Version{}, for Tenants {} ", endpoint.getEndpointUploadType(), endpoint.getEndpointUploadFile(), endpoint.getEndpointUploadVersion(), filteredTenantIds);
        	dao.activateUploadedEndpoint(filteredTenantIds, endpoint.getEndpointUploadType(), endpoint.getEndpointUploadFile(), endpoint.getEndpointUploadVersion());
        }
        return sID;
	}
	
	@Override
    public boolean isUploadModeExternal(int tenantId){
	    boolean isUploadModeExternal = false;
	    if ( tenantId == 0) {
		    Configuration endpointUploadModeConfiguration = this.system.getConfiguration("MANAGE_ENDPOINT_UPLOAD_MODE");
			if (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null){
				isUploadModeExternal = endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase("External");
			}
	    } else {
	    	TenantConfiguration endpointUploadModeConfiguration = this.tenant.getTenantConfiguration(tenantId);
	    	if (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getEndpointUploadMode() != null ){
	    		isUploadModeExternal = endpointUploadModeConfiguration.getEndpointUploadMode().equalsIgnoreCase("External");
	    	} 
	    }
		return isUploadModeExternal;
    }

    @Override
	public int addEndpointUpload(int tenantId, EndpointUpload endpoint, boolean setActive) {
		if (endpoint == null){
			return 0;
		}
		
		endpoint.setTenantID(tenantId);
		EndpointUpload endpointToDelete = getEndpointUploadForFileName(tenantId, endpoint.getEndpointUploadFile(), 
				endpoint.getEndpointUploadType(), endpoint.getEndpointUploadVersion());
		//cleaning data in the db
		if (endpointToDelete != null){
			deleteEndpointUpload(endpointToDelete.getEndpointUploadID(), tenantId);
		} 
        
        endpoint.setEndpointUploadTime((int)new Date().getTime());
        
        int sID = insertEndpointUpload(endpoint);
        if (setActive) {
            endpoint.setEndpointUploadID(sID);
        	setActiveUpload(tenantId, endpoint);
        }
        
        return sID;
	}
	
	@Override
	public String getCDNInstallerURLWithParam(String installerURL, String scheme, String host, String roomKey){
		Configuration CDNConfiguration = system.getConfiguration("CDN_OPTIONAL_PARAMETER");
		if (CDNConfiguration != null && CDNConfiguration.getConfigurationValue() != null){
			String fileName = installerURL.substring(installerURL.lastIndexOf("/")+1);
			//VPTL-7796 - Embedding the portal features inside the installable.
			String portalFeatures = portalService.getEncodedPortalFeatures();
			if (fileName != null) {
				String[] fileNameParts = new String[2];
				int dotIndex = fileName.lastIndexOf(".");
				// File Name
				fileNameParts[0] = dotIndex > 0 ? fileName.substring(0, dotIndex):fileName;
				
				// Extension
				fileNameParts[1] = dotIndex > 0 ? "."+fileName.substring(dotIndex+1):"";
				
				if (!fileNameParts[0].contains("TAG_VD_")) { // is not UVD - apply params
					fileNameParts[0] += "[p=" + scheme + "&h=" + host+"&f="+portalFeatures;
					if (roomKey != null && !roomKey.equals("")){
						fileNameParts[0] += "&r=" + roomKey;
					}
					fileNameParts[0] += "]";
				}
				
				String encodedUrl;
				try {
					// Trying with UTF-8 in case file name has special characters
					encodedUrl = URLEncoder.encode("attachment; filename="+ fileNameParts[0] 
								+ fileNameParts[1], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// Can not use UTF-8 so falling back to normal
					encodedUrl = URLEncoder.encode("attachment; filename="+ fileNameParts[0] 
							+ fileNameParts[1]);
				}
				// Replacing all the spaces which are replaced as + by encoder
				// This will be needed as %20, and is recognized by AWS, + is not allowed.
				encodedUrl = encodedUrl.replaceAll("\\+","%20");
				installerURL = installerURL + "?" + CDNConfiguration.getConfigurationValue() + "=" + encodedUrl;
				/*if(StringUtils.isNotBlank(roomKey)) {
					installerURL = installerURL.concat("&r=").concat(roomKey);
				}*/
			}
		}
		
		return installerURL;
	}
}