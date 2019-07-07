/**
 *
 */
package com.vidyo.db.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.VirtualEndpoint;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointFilter;
import com.vidyo.service.endpoints.EndpointService;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * @author Ganesh
 *
 */
public class EndpointDaoImpl extends NamedParameterJdbcDaoSupport implements EndpointDao {

	/**
	 *
	 */
	private static final String ENDPOINT_BY_GUID = "SELECT endpointID, 'D' as endpointType, memberID, memberType, updateTime, endpointUploadType, authorized, status, extData, extDataType from Endpoints where endpointGUID =:guid";

	/**
	 *
	 */
	private static final String ENDPOINT_STATUS_TYPE = "SELECT 'D' as endpointType, status, endpointID, memberID, memberType, endpointUploadType, extDataType, extData FROM Endpoints"
			+ " WHERE endpointGUID =:guid UNION SELECT 'V' as endpointType, status, endpointID, 0 as memberID, '' as memberType, NULL as endpointUploadType, 0 as extDataType, NULL as extData FROM VirtualEndpoints WHERE "
			+ "endpointGUID =:guid UNION SELECT 'R' as endpointType, status, endpointID, 0 as memberID, '' as memberType, NULL as endpointUploadType, 0 as extDataType, NULL as extData FROM RecorderEndpoints WHERE endpointGUID =:guid";

	private static final String MEMBER_INFO_BY_ENDPOINTGUID = "select e.endpointGUID, e.endpointPublicIPAddress, e.memberType, m.memberID from Endpoints e \n" +
			" left join Member m on e.memberID = m.memberID " +
			" left join Guests g on e.memberID = g.guestID  " +
			" where e.endpointGUID  in  (:endpointGUID)  " +
			" and  (m.tenantID = :tenantID or g.tenantID = :tenantID) ";

	/**
	 *
	 * @param endpointFilter
	 * @param tenantID
	 * @return
	 */
	@Override
	public List<Endpoint> getVidyoRooms(EndpointFilter endpointFilter, int tenantID) {
		// TODO - check for the member role - vidyorooms
		StringBuffer ONLINE_VIDYO_ROOMS = new StringBuffer(
				"select * from (select eps.endpointID, 'UP' as statusVal, eps.status, eps.ipAddress, m.memberName "
						+ "from Endpoints eps, Member m where eps.ipAddress is not null and eps.ipaddress != '' and eps.status !=:status "
						+ "and eps.memberID = m.memberID and m.tenantID =:tenantId ");

		StringBuffer OFFLINE_VIDYO_ROOMS = new StringBuffer(
				" UNION select eps.endpointID, 'DOWN' as statusVal, eps.status, eps.ipAddress, m.memberName "
						+ "from Endpoints eps, Member m where eps.ipAddress is not null and eps.ipaddress != '' "
						+ "and eps.status =:status and eps.memberID = m.memberID and m.tenantID =:tenantId "
						+ "and eps.updateTime >= date_sub(curdate(),INTERVAL 5 DAY)");
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("status", 0);
		namedParamsMap.put("tenantId", tenantID);
		if (endpointFilter != null) {
			if (endpointFilter.getMemberName() != "") {
				ONLINE_VIDYO_ROOMS.append(" AND m.memberName LIKE :memberName ");
				OFFLINE_VIDYO_ROOMS.append(" AND m.memberName LIKE :memberName ");
				namedParamsMap.put("memberName", endpointFilter.getMemberName() + "%");
			}
			ONLINE_VIDYO_ROOMS.append(OFFLINE_VIDYO_ROOMS).append(") as aliasName");

			if (!endpointFilter.getSort().equalsIgnoreCase("")) {
				ONLINE_VIDYO_ROOMS.append(" ORDER BY ").append(endpointFilter.getSort()).append(" ")
						.append(endpointFilter.getDir());
			}
			ONLINE_VIDYO_ROOMS.append(" LIMIT :start, :limit");
			namedParamsMap.put("start", endpointFilter.getStart());
			namedParamsMap.put("limit", endpointFilter.getLimit());
		} else {
			ONLINE_VIDYO_ROOMS.append(OFFLINE_VIDYO_ROOMS).append(") as aliasName");
		}
		return getNamedParameterJdbcTemplate().query(ONLINE_VIDYO_ROOMS.toString(), namedParamsMap,
				BeanPropertyRowMapper.newInstance(Endpoint.class));
	}

	/**
	 *
	 * @param endpointFilter
	 * @param tenantID
	 * @return
	 */
	public int getVidyoRoomsCount(EndpointFilter endpointFilter, int tenantID) {
		StringBuffer COUNT_ONLINE_VIDYO_ROOMS = new StringBuffer("select count(0) from (select eps.endpointID "
				+ "from Endpoints eps, Member m where eps.ipAddress is not null and eps.ipaddress != '' and eps.status !=:status "
				+ "and eps.memberID = m.memberID and m.tenantID =:tenantId ");
		StringBuffer COUNT_OFFLINE_VIDYO_ROOMS = new StringBuffer(" UNION select eps.endpointID "
				+ "from Endpoints eps, Member m where eps.ipAddress is not null and eps.ipaddress != '' "
				+ "and eps.status =:status and eps.memberID = m.memberID and m.tenantID =:tenantId "
				+ "and eps.updateTime >= date_sub(curdate(),INTERVAL 5 DAY)");

		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("status", 0);
		namedParamsMap.put("tenantId", tenantID);
		if (endpointFilter != null) {
			if (endpointFilter.getMemberName() != "") {
				COUNT_ONLINE_VIDYO_ROOMS.append(" AND m.memberName LIKE :memberName ");
				COUNT_OFFLINE_VIDYO_ROOMS.append(" AND m.memberName LIKE :memberName ");
				namedParamsMap.put("memberName", endpointFilter.getMemberName() + "%");
			}
			COUNT_ONLINE_VIDYO_ROOMS.append(COUNT_OFFLINE_VIDYO_ROOMS).append(") as aliasName");

		} else {
			COUNT_ONLINE_VIDYO_ROOMS.append(COUNT_OFFLINE_VIDYO_ROOMS).append(") as aliasName");
		}

		return getNamedParameterJdbcTemplate().queryForObject(COUNT_ONLINE_VIDYO_ROOMS.toString(), namedParamsMap, Integer.class);
	}

	/**
	 * Returns the Endpoint Details for GUID
	 *
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public Endpoint getEndpointDetails(String endpointGUID) {
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("guid", endpointGUID);
		List<Endpoint> endpoints = getNamedParameterJdbcTemplate().query(ENDPOINT_BY_GUID, namedParamsMap,
				BeanPropertyRowMapper.newInstance(Endpoint.class));
		if (endpoints.isEmpty()) {
			return null;
		}
		return endpoints.get(0);
	}

	/**
	 * Return the Endpoint/VirtualEndpoint/RecordeEndpoint status & type
	 *
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public Endpoint getEndpointDetail(String endpointGUID) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", endpointGUID);
		List<Endpoint> endpoints = getNamedParameterJdbcTemplate().query(ENDPOINT_STATUS_TYPE, paramMap,
				BeanPropertyRowMapper.newInstance(Endpoint.class));
		if (endpoints.isEmpty()) {
			return null;
		}
		return endpoints.get(0);
	}

	/**
	 *
	 */
	private static final String GET_ENDPOINT_STATUS = "SELECT e.status, e.consumesLine FROM Endpoints e WHERE e.memberId =:memberId and e.endpointGUID =:guid";

	/**
	 * Returns Endpoint's status. Value will be returned only if the user is bound to EP.
	 *
	 * @param guid
	 * @param tenantId
	 * @return
	 */
	public Endpoint getEndpointStatus(String guid, int memberId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", guid);
		paramMap.put("memberId", memberId);
		List<Endpoint> endpoints = getNamedParameterJdbcTemplate().query(GET_ENDPOINT_STATUS, paramMap,
				BeanPropertyRowMapper.newInstance(Endpoint.class));
		if (endpoints.isEmpty()) {
			return null;
		}

		return endpoints.get(0);
	}

	private static final String UPDATE_LINE_CONSUMPTION = "update Endpoints e set e.consumesLine =:consumesLine where e.memberID =:memberId and e.endpointGUID =:guid";

	/**
	 * Updates Line Consumption Flag for Endpoint. This API has to be invoked only from {@link EndpointService}
	 * updateLineConsumption method
	 *
	 * @param guid
	 *            Endpoint Identifier
	 * @param memberId
	 *            Member the Endpoint is bound to
	 * @param consumesLine
	 *            flag to indicate line consumption
	 * @return
	 */
	@Override
	public int updateLineConsumption(String guid, int memberId, boolean consumesLine) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", guid);
		paramMap.put("memberId", memberId);
		paramMap.put("consumesLine", consumesLine ? 1 : 0);
		int count = getNamedParameterJdbcTemplate().update(UPDATE_LINE_CONSUMPTION, paramMap);
		return count;
	}


	public static final String DELETE_ENDPOINT = "DELETE FROM Endpoints WHERE endpointGUID = :guid";

	@Override
	public void deleteRegularEndpoint(String guid){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", guid);
		getNamedParameterJdbcTemplate().update(DELETE_ENDPOINT, paramMap);
	}

	public static final String UPDATE_VE_CDR = "UPDATE VirtualEndpoints SET deviceModel = :deviceModel, endpointPublicIpAddress = :endpointPublicIpAddress WHERE endpointGUID = :guid";

	@Override
	public int updateGatewayEndpointCDR(String gwEndpointGuid, String deviceModel, String endpointPublicIpAddress) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", gwEndpointGuid);
		paramMap.put("deviceModel", cleanForCDR(deviceModel, 50));
		paramMap.put("endpointPublicIpAddress", cleanForCDR(endpointPublicIpAddress, 48));
		return getNamedParameterJdbcTemplate().update(UPDATE_VE_CDR, paramMap);
	}

	private String cleanForCDR(String str, int maxSize) {
		String cleanString = StringUtils.trimToEmpty(str);
		if (cleanString.length() > maxSize) {
			return cleanString.substring(0, maxSize);
		}
		return cleanString;
	}

	public static final String GET_VIRTUAL_ENDPOINT = " SELECT" +
			"  ve.endpointID," +
			"  ve.serviceID," +
			"  ve.gatewayID," +
			"  ve.displayName," +
			"  ve.displayExt," +
            "  ve.entityID, " +
			"  ve.prefix," +
			"  ve.endpointGUID," +
			"  ve.status," +
			"  ve.direction," +
			"  ve.tenantID," +
			"  t.tenantName," +
			"  ve.updateTime" +
			" FROM" +
			"  VirtualEndpoints ve" +
			" INNER JOIN Tenant t ON t.tenantID = ve.tenantID" +
			" WHERE" +
			"  ve.endpointGUID = :guid";

	public VirtualEndpoint getVirtualEndpointForEndpointGUID(String guid) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", guid);

		List<VirtualEndpoint> endpoints = getNamedParameterJdbcTemplate().query(GET_VIRTUAL_ENDPOINT, paramMap,
				BeanPropertyRowMapper.newInstance(VirtualEndpoint.class));
		if (endpoints.isEmpty()) {
			return null;
		}

		return endpoints.get(0);

	}

	public static final String DELETE_STALE_OFFLINE_VIRTUAL_ENDPOINTS = " DELETE" +
			" FROM" +
			"  VirtualEndpoints" +
			" WHERE" +
			"  status = 0" +
			" AND" +
			"  TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5 ";

	public int clearRegisterVirtualEndpoint() {
		logger.debug("Remove record from VirtualEndpoints with Status=Offline more than 5 minutes");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		int affected = getNamedParameterJdbcTemplate().update(DELETE_STALE_OFFLINE_VIRTUAL_ENDPOINTS, paramMap);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

	public static final String UPDATE_VIRTUAL_ENDPOINT_STATUS = "UPDATE VirtualEndpoints SET status = :status WHERE endpointGUID = :guid";

	public int updateVirtualEndpointStatus(String guid, int status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guid", guid);
		paramMap.put("status", status);
		int affected = getNamedParameterJdbcTemplate().update(UPDATE_VIRTUAL_ENDPOINT_STATUS, paramMap);
		return affected;
	}

    public static final String GET_FEATURES_FOR_GUID = "SELECT lectureModeSupport FROM Endpoints WHERE endpointGUID = :guid UNION " +
            "SELECT 1 as lectureModeSupport FROM VirtualEndpoints WHERE endpointGUID = :guid UNION " +
            "SELECT 1 as lectureModeSupport FROM RecorderEndpoints WHERE endpointGUID = :guid";

    @Override
    @Cacheable(cacheName="endpointFeaturesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public EndpointFeatures getEndpointFeaturesForGuid(String guid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("guid", guid);

        List<EndpointFeatures> featuresList = getNamedParameterJdbcTemplate().query(GET_FEATURES_FOR_GUID, paramMap,
                BeanPropertyRowMapper.newInstance(EndpointFeatures.class));
        if (featuresList.isEmpty()) {
            return null;
        }

        return featuresList.get(0);
    }

    // change query as appropriate when new features are added (that may affect all types of endpoints)
    public static final String SET_FEATURES_FOR_GUID = "UPDATE Endpoints SET lectureModeSupport = :lectureModeSupport WHERE endpointGUID = :guid";

    @Override
    @TriggersRemove(cacheName="endpointFeaturesCache", removeAll=true)
    public int updateEndpointFeatures(String guid, EndpointFeatures features) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lectureModeSupport", features.isLectureModeSupported());
        paramMap.put("guid", guid);

		logger.debug("updating endpoint feature lectureMode: " + features.isLectureModeSupported());
        return getNamedParameterJdbcTemplate().update(SET_FEATURES_FOR_GUID, paramMap);
    }


    // change query as appropriate when new features are added (that may affect all types of endpoints)
    public static final String SET_FEATURES_FOR_GUID_GUEST = "UPDATE Endpoints SET lectureModeSupport = :lectureModeSupport WHERE " +
            "endpointGUID = :guid AND memberType = 'G' AND memberID = :guestID AND authorized = 1 AND status = 1";

    @Override
    public int updateEndpointFeaturesForGuest(int guestID, String guid, EndpointFeatures features) { 
    		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lectureModeSupport", features.isLectureModeSupported());
        paramMap.put("guestID", guestID);
        paramMap.put("guid", guid);

        return getNamedParameterJdbcTemplate().update(SET_FEATURES_FOR_GUID_GUEST, paramMap);
    }
    
    public static final String RESET_EXT_DATA_BY_GUID = "UPDATE Endpoints SET extData =:extData, extDataType =:extDataType WHERE endpointGUID =:endpointGUID";

	/**
	 * Resets the external data associated with the previous call which endpoint was
	 * on.
	 * 
	 * @param endpointGUID
	 * @return
	 */
	@Override
	public int resetExtData(String endpointGUID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering resetExtData(String endpointGUID) of EndpointServiceImpl >>>>>>>>>");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("endpointGUID", endpointGUID);
		paramMap.put("extData", null);
		paramMap.put("extDataType", Integer.valueOf(0));
		if (logger.isDebugEnabled()) {
			logger.debug("Exiting resetExtData(String endpointGUID) of EndpointServiceImpl <<<<<<<<<<");
		}
		return getNamedParameterJdbcTemplate().update(RESET_EXT_DATA_BY_GUID, paramMap);
	}


	public List<Map<String, Object>> findPublicIpAndMemberId (int tenantId, List<String> endpointGUIDs) {
		SqlParameterSource findPublicIpAddress = new MapSqlParameterSource()
				.addValue("tenantID", tenantId)
				.addValue("endpointGUID", endpointGUIDs);
		return getNamedParameterJdbcTemplate().queryForList(MEMBER_INFO_BY_ENDPOINTGUID, findPublicIpAddress);
	}
	
	public static final String UPDATE_PUBLIC_IP_BY_GUID = "UPDATE Endpoints SET endpointPublicIPAddress =:endpointPublicIPAddress WHERE endpointGUID =:endpointGUID";

	/**
	 * Updates the Endpoint's public address column with the provided latest remote
	 * address
	 * 
	 * @param endpointGUID
	 *            endpoint whose ip address to be updated
	 * @param publicIpAddress
	 *            ip value of the endpoint
	 * @return updated rows count
	 */
	@Override
	public int updatePublicIPAddress(String endpointGUID, String publicIPAddress) {
		SqlParameterSource ipAddressUpdateParams = new MapSqlParameterSource()
				.addValue("endpointPublicIPAddress", publicIPAddress).addValue("endpointGUID", endpointGUID);
		return getNamedParameterJdbcTemplate().update(UPDATE_PUBLIC_IP_BY_GUID, ipAddressUpdateParams);
	}

}