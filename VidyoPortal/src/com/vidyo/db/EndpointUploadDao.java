package com.vidyo.db;

import com.vidyo.bo.EndpointUpload;

import java.util.List;

public interface EndpointUploadDao {
    public List<EndpointUpload> getEndpointUploadsBySuper(int tenant, int start, int limit);
    public Long getCountEndpointUploadBySuper(int tenant);

    public List<EndpointUpload> getEndpointUploads(int tenant, int start, int limit);
    public Long getCountEndpointUpload(int tenant);

    public List<EndpointUpload> getActiveEndpointUploads(int tenant, int start, int limit);
    public Long getCountActiveEndpointUpload(int tenant);
    
    public int setActiveUpload(int tenant, EndpointUpload client);

    public EndpointUpload getEndpointUpload(int endpointUploadID);

    public int insertEndpointUpload(int tenant, EndpointUpload client, String who);
    public int deleteEndpointUpload(int endpointUploadID, int tenantId);
    public int deleteEndpointUploads(int tenantId);
    
    public EndpointUpload getActiveEndpointForType(int tenant, String endpointUploadType);
    public Long getRefNumberForEndpointFileName(EndpointUpload client);
    public int deleteEndpointUploadFileName(EndpointUpload client);
    public EndpointUpload getEndpointUploadForFileName(int tenant, String filename, String endpointUploadType,
			String endpointUploadVersion);
    public int deleteAllEndpointUploads();
    
    public int copyEndpointUploadsFromDefaultTenantToNew(int newTenantID);
	public boolean deactivateEndpoints(int tenantId);
	
	/**
	 * 
	 * @param tenantIds
	 * @param client
	 * @param who
	 * @return
	 */
	public int[] insertEndpointUpload(List<Integer> tenantIds, EndpointUpload client, String who);
	
	/**
	 * 
	 * @param tenantIds
	 * @param endpointType
	 * @return
	 */
	public boolean deactivateEndpoints(List<Integer> tenantIds, String endpointType);
	
	/**
	 * 
	 * @param tenantIds
	 * @param client
	 * @return
	 */
	public int activateUploadedEndpoint(List<Integer> tenantIds, String endpointType, String endpointFileName, String endpointFileVersion);
}