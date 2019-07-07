
package com.vidyo.service.status;

import java.util.Map;

import com.vidyo.bo.clusterinfo.ClusterInfo;

public interface VidyoPortalStatusService {
    public ClusterInfo getHotStandByStatus();

	public Map<String, String> clusterRegistration(String mapKeys);

	Map<String, String> clusterRegistration(Map<String, Object> mapKeys);
}