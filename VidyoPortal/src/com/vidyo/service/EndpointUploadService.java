package com.vidyo.service;

import com.vidyo.bo.EndpointUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface EndpointUploadService {
    public List<EndpointUpload> getEndpointUploadsBySuper(int start, int limit);
    public Long getCountEndpointUploadBySuper();

    public List<EndpointUpload> getEndpointUploads(int start, int limit);
    public Long getCountEndpointUpload();

    public List<EndpointUpload> getActiveEndpointUploads(int start, int limit);
    public Long getCountActiveEndpointUpload();
    
    public EndpointUpload getEndpointUpload(int endpointUploadID);

    public int insertEndpointUpload(EndpointUpload client);
    public int insertEndpointUploadBySuper(int tenant, EndpointUpload client);

    public int deleteEndpointUpload(int endpointUploadID, int tenantId);

    public int setActiveUpload(int tenant, EndpointUpload client);

    public EndpointUpload getActiveEndpointForType(HttpServletRequest request);
    public EndpointUpload getActiveEndpointForType(String endpointUploadType);
    public Long getRefNumberForEndpointFileName(EndpointUpload client);
    public int deleteEndpointUploadFileName(EndpointUpload client);
    public EndpointUpload getEndpointUploadForFileName(int tenant, String filename, String endpointUploadType,
			String endpointUploadVersion);
    public int deleteAllEndpointUploads();

    public boolean setEndpointUploadType(EndpointUpload client);
    
	/**
	 * Upload Endpoint software for individual Tenants. Also acts as a wrapper
	 * method not to be intercepted by the Audit Interceptor.
	 * 
	 * @param tenant
	 * @param client
	 * @return
	 */
	public int insertEndpointUploadBySuperForTenants(int tenant,
			EndpointUpload client);
	public boolean deactivateEndpoints(int tenantId);
	public Map<String, String> getClientVersion(String type, String uploadPath, String contextAddress);
	public int addEndpointUploadSuper(EndpointUpload endpoint, boolean isSuperExternal, boolean setActive);
	public int addEndpointUpload(int tenantId, EndpointUpload endpoint, boolean setActive);
	public String getCDNInstallerURLWithParam(String installerURL, String scheme, String host, String roomKey);
	public boolean isUploadModeExternal(int tenantId);
	
	/**
	 * 
	 * @param tenantIds
	 * @param client
	 * @return
	 */
    public int[] batchInsertEndpointUploadBySuper(List<Integer> tenantIds, EndpointUpload client);
}
