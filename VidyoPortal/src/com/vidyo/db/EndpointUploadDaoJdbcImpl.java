package com.vidyo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.TenantConfiguration;


public class EndpointUploadDaoJdbcImpl extends JdbcDaoSupport implements EndpointUploadDao {
	
	@Autowired
	ISystemDao systemDao;
	
	@Autowired
	TenantConfigurationDao tenantConfigDao;
	
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;
	
	
	public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
		this.namedParamJdbcTemplate = namedParamJdbcTemplate;
	}

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(EndpointUploadDaoJdbcImpl.class.getName());

    public List<EndpointUpload> getEndpointUploadsBySuper(int tenant, int start, int limit) {
        List<EndpointUpload> rc;
        logger.debug("Get records from EndpointUpload for tenant = " + tenant + " from = " + start + " and limit = " + limit);
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  eu.endpointUploadID," +
            "  eu.tenantID," +
            "  eu.endpointUploadFile," +
            "  eu.endpointUploadTime," +
            "  eu.endpointUploadType," +
            "  eu.endpointUploadActive," +
            "  eu.endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = :tenantID" +
            " AND" +
            "  eu.whoUploadFile = 'S'"
        );
        
        sqlstm.append(getExternalCheck());
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenant);
        if (limit != 0) {
            sqlstm.append(" LIMIT :start,:limit");
            paramSource.addValue("start", start);
            paramSource.addValue("limit", limit);
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), paramSource, BeanPropertyRowMapper.newInstance(EndpointUpload.class));
        return rc;
    }

    private String getExternalCheck(){
    	return getExternalCheck(0, false);
    }
    
    private String getExternalCheck(int tenantID, boolean forTenant){
    	if (!isExternal(tenantID, forTenant)){
        	return " AND eu.endpointUploadVersion IS NULL";
        } else {
        	return " AND eu.endpointUploadVersion IS NOT NULL";
        }
    }
    
    private boolean isExternal(){
    	return isExternal(0, false);
    }
    
	private boolean isExternal(int tenantID, boolean forTenant){
		if (forTenant) {
			TenantConfiguration tenantConfig = tenantConfigDao.getTenantConfiguration(tenantID);
			if (tenantConfig.getEndpointUploadMode() != null){
				return tenantConfig.getEndpointUploadMode().equalsIgnoreCase("External");
			} 
		} 
		Configuration endpointUploadModeConfiguration = systemDao.getConfiguration(0, "MANAGE_ENDPOINT_UPLOAD_MODE");
    	return (endpointUploadModeConfiguration != null && endpointUploadModeConfiguration.getConfigurationValue() != null 
			&& endpointUploadModeConfiguration.getConfigurationValue().equalsIgnoreCase("External"));
	}
	
    public Long getCountEndpointUploadBySuper(int tenant) {
        logger.debug("Get count of records from EndpointUpload for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT COUNT(0) " +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = ?" +
            " AND" +
            "  eu.whoUploadFile = 'S'"
        );
        
        sqlstm.append(getExternalCheck());

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                },
                tenant
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public List<EndpointUpload> getEndpointUploads(int tenant, int start, int limit) {
        List<EndpointUpload> rc;
        logger.debug("Get records from EndpointUpload for tenant = " + tenant + " from = " + start + " and limit = " + limit);
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  eu.endpointUploadID," +
            "  eu.tenantID," +
            "  eu.endpointUploadFile," +
            "  eu.endpointUploadTime," +
            "  eu.endpointUploadType," +
            "  eu.endpointUploadActive," +
            "  eu.endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = :tenantID"
        );
        
        sqlstm.append(getExternalCheck(tenant, tenant != 0));
        
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenant);
        if (limit != 0) {
            sqlstm.append(" LIMIT :start,:limit");
            paramSource.addValue("start", start);
            paramSource.addValue("limit", limit);
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), paramSource, BeanPropertyRowMapper.newInstance(EndpointUpload.class));
        
        return rc;
    }

    public List<EndpointUpload> getActiveEndpointUploads(int tenant, int start, int limit) {
        List<EndpointUpload> rc;
        logger.debug("Get records from EndpointUpload for tenant = " + tenant + " from = " + start + " and limit = " + limit);
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  eu.endpointUploadID," +
            "  eu.tenantID," +
            "  eu.endpointUploadFile," +
            "  eu.endpointUploadTime," +
            "  eu.endpointUploadType," +
            "  eu.endpointUploadActive," +
            "  eu.endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = :tenantID" +
            " AND" +
            "  eu.endpointUploadActive = 1"
        );

        String additionalCheck = getExternalCheck(tenant, tenant!=0);
        sqlstm.append(additionalCheck);
        
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenant);
        if (limit != 0) {
            sqlstm.append(" LIMIT :start,:limit");
            paramSource.addValue("start", start);
            paramSource.addValue("limit", limit);
        }

        rc = namedParamJdbcTemplate.query(sqlstm.toString(), paramSource, BeanPropertyRowMapper.newInstance(EndpointUpload.class));
        
        return rc;
    }

    public Long getCountEndpointUpload(int tenant) {
        logger.debug("Get count of records from EndpointUpload for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT COUNT(0) " +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = ?"
        );

        sqlstm.append(getExternalCheck(tenant, tenant != 0));
        
        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                },
                tenant
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Long getCountActiveEndpointUpload(int tenant) {
        logger.debug("Get count of records from EndpointUpload for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT COUNT(0) " +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = ?" +
            " AND" +
            "  eu.endpointUploadActive = 1"
        );

        String additionalCheck = getExternalCheck(tenant, tenant!=0);
        sqlstm.append(additionalCheck);
        
        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                },
                tenant
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public EndpointUpload getEndpointUpload(int endpointUploadID) {
        logger.debug("Get record from EndpointUpload for endpointUploadID = " + endpointUploadID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  eu.endpointUploadID," +
            "  eu.tenantID," +
            "  eu.endpointUploadFile," +
            "  eu.endpointUploadTime," +
            "  eu.endpointUploadType," +
            "  eu.endpointUploadActive," +
            "  eu.endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.endpointUploadID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(EndpointUpload.class), endpointUploadID);
    }

    @Cacheable(cacheName="endpointCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public EndpointUpload getActiveEndpointForType(int tenant, String endpointUploadType) {
        logger.debug("Get record from EndpointUpload for tenant = " + tenant + " and endpointUploadType = " + endpointUploadType);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  eu.endpointUploadID," +
            "  eu.tenantID," +
            "  eu.endpointUploadFile," +
            "  eu.endpointUploadTime," +
            "  eu.endpointUploadType," +
            "  eu.endpointUploadActive," +
            "  eu.endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.tenantID = ?" +
            " AND" +
            "  eu.endpointUploadActive = ?" +
            " AND" +
            "  eu.endpointUploadType = ?"
        );

        String additionalCheck = getExternalCheck(tenant, tenant!=0);
        sqlstm.append(additionalCheck);
        
        EndpointUpload rc = null;
        try {
            rc = getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(EndpointUpload.class), tenant, 1, endpointUploadType);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return rc;
    }
    
    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int insertEndpointUpload(int tenant, EndpointUpload client, String who) {
        logger.debug("Add record into EndpointUpload for tenant = " + tenant);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("EndpointUpload")
                        .usingGeneratedKeyColumns("endpointUploadID");

        client.setTenantID(tenant);
        client.setEndpointUploadTime((int)(new Date().getTime() * .001));
        client.setWhoUploadFile(who);

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New endpointUploadID = " + newID.intValue());

        return newID.intValue();
    }
    
    public static final String INSERT_ENDPOINT_UPLOAD = "INSERT INTO EndpointUpload(tenantID, endpointUploadFile, endpointUploadTime, endpointUploadType,  endpointUploadActive, whoUploadFile, endpointUploadVersion) values (?, ?, ?, ?, ?, ?, ?)";
    
    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int[] insertEndpointUpload(List<Integer> tenantIds, EndpointUpload client, String who) {
    	final int time = (int)(new Date().getTime() * .001);
    	int[]ids = getJdbcTemplate().batchUpdate(INSERT_ENDPOINT_UPLOAD, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, tenantIds.get(i));
				ps.setString(2, client.getEndpointUploadFile());
				ps.setInt(3, time);
				ps.setString(4, client.getEndpointUploadType());
				ps.setInt(5, client.getEndpointUploadActive());
				ps.setString(6, client.getWhoUploadFile());
				ps.setString(7, client.getEndpointUploadVersion());
			}
			
			@Override
			public int getBatchSize() {
				return tenantIds.size();
			}
		});
    	return ids;
    }

    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int deleteEndpointUpload(int endpointUploadID, int tenantId) {
        logger.debug("Remove record from EndpointUpload for endpointUploadID = " + endpointUploadID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  EndpointUpload" +
            " WHERE" +
            "  endpointUploadID = ? AND tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), endpointUploadID, tenantId);
        logger.debug("Rows affected: " + affected);

        return endpointUploadID;
    }

    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int deleteEndpointUploads(int tenantId) {
        logger.debug("Remove records from EndpointUpload for tenantId = " + tenantId);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE FROM EndpointUpload WHERE tenantID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantId);
        logger.debug("Rows affected: " + affected);

        return tenantId;
    }
    
	@TriggersRemove(cacheName="endpointCache", removeAll=true)
	public int setActiveUpload(int tenant, EndpointUpload client) {
        logger.debug("Update record in EndpointUpload for tenant = " + tenant + " and endpointUploadID = " + client.getEndpointUploadID());

        // remove active flag from other endpoints in the same tenant and type
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  EndpointUpload eu" +
            " SET" +
            "  endpointUploadActive = 0" +
            " WHERE" +
            "  (tenantID = ?)" +
            " AND " +
            "  endpointUploadType = ?"
        );
        
        String additionalCheck = getExternalCheck(tenant, tenant!=0);
        sqlstm.append(additionalCheck);

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenant, client.getEndpointUploadType());
        logger.debug("Rows affected: " + affected);

        StringBuffer set_sqlstm = new StringBuffer(
            " UPDATE" +
            "  EndpointUpload eu" +
            " SET" +
            "  endpointUploadActive = 1" +
            " WHERE" +
            "  tenantID = ?" +
            " AND " +
            "  endpointUploadID = ?"
        );

        set_sqlstm.append(additionalCheck);
        
        affected = getJdbcTemplate().update(set_sqlstm.toString(), tenant, client.getEndpointUploadID());
        logger.debug("Rows affected: " + affected);

        return client.getEndpointUploadID();
    }
	
	/**
	 * 
	 * @param tenantIds
	 * @param endpointType
	 * @return
	 */
	public boolean deactivateEndpoints(List<Integer> tenantIds, String endpointType) {
        // remove active flag from other endpoints in the same tenant and type
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            " EndpointUpload" +
            " SET" +
            " endpointUploadActive = 0" +
            " WHERE" +
            " tenantID in (:tenantIds) " +
            " AND endpointUploadType =:endpointUploadType "
         );
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tenantIds", tenantIds);
        paramMap.put("endpointUploadType", endpointType);
        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);
		return affected > 0;
	}
	
	@TriggersRemove(cacheName="endpointCache", removeAll=true)
	public int activateUploadedEndpoint(List<Integer> tenantIds, String endpointType, String endpointFileName, String endpointFileVersion) {
        logger.debug("Update record in EndpointUpload for tenants = " + tenantIds + " and endpoints = ");
        
        StringBuffer set_sqlstm = new StringBuffer(
            " UPDATE" +
            "  EndpointUpload eu" +
            " SET" +
            "  endpointUploadActive = 1" +
            " WHERE" +
            "  tenantID IN (:tenantIds)" +
            " AND endpointUploadType =:endpointUploadType " + 
            " AND endpointUploadFile =:endpointFileName "
        );
        //Endpoint File Version value will be null for the VidyoPortal mode, 
        //so do not use it for update statement if value is null or empty
        if(StringUtils.isNotBlank(endpointFileVersion)) {
        	set_sqlstm.append(" AND endpointUploadVersion =:endpointFileVersion ");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tenantIds", tenantIds);
        paramMap.put("endpointUploadType", endpointType);
        paramMap.put("endpointFileVersion", endpointFileVersion);     
        paramMap.put("endpointFileName", endpointFileName);     
        int affected = namedParamJdbcTemplate.update(set_sqlstm.toString(), paramMap);
        logger.debug("Rows affected: " + affected);
        return affected;
    }	

    public Long getRefNumberForEndpointFileName(EndpointUpload client) {
        logger.debug("Get count of records from EndpointUpload for filename = " + client.getEndpointUploadFile());

        StringBuffer sqlstm = new StringBuffer(
            " SELECT COUNT(0) " +
            " FROM" +
            "  EndpointUpload eu" +
            " WHERE" +
            "  eu.endpointUploadFile = ?"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            },
            client.getEndpointUploadFile()
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int deleteEndpointUploadFileName(EndpointUpload client) {
        logger.debug("Remove record from EndpointUpload for endpointUploadFile = " + client.getEndpointUploadFile());

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  EndpointUpload" +
            " WHERE" +
            "  endpointUploadFile = :endpointUploadFile "
        );

        int affected = 0;
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("endpointUploadFile", client.getEndpointUploadFile());
        if (isExternal()){
        	sqlstm.append(" AND endpointUploadType=:endpointUploadType AND endpointUploadVersion=:endpointUploadVersion");
        	paramSource.addValue("endpointUploadType", client.getEndpointUploadType());
        	paramSource.addValue("endpointUploadVersion", client.getEndpointUploadVersion());
        }
       	affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramSource);
       	
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int deleteAllEndpointUploads() {
       logger.debug("Remove all records from EndpointUpload");

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  EndpointUpload"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    @TriggersRemove(cacheName="endpointCache", removeAll=true)
    public int copyEndpointUploadsFromDefaultTenantToNew(int newTenantID) {
        logger.debug("copy records for uploaded clients from default tenant to new");

        StringBuffer copyUploads = new StringBuffer(
                " INSERT INTO " +
                "  EndpointUpload (" +
                "  tenantID," +
                "  endpointUploadFile," +
                "  endpointUploadTime," +
                "  endpointUploadType," +
                "  endpointUploadActive," +
                "  endpointUploadVersion)" +
                " SELECT" +
                "  ? as tenantID," +
                "  endpointUploadFile," +
                "  endpointUploadTime," +
                "  endpointUploadType," +
                "  endpointUploadActive," +
                "  endpointUploadVersion" +
                " FROM" +
                "  EndpointUpload" +
                " WHERE" +
                " tenantID = 0"
            );

        int affected = getJdbcTemplate().update(copyUploads.toString(), newTenantID);
        logger.debug("Rows copied to EndpointUpload: " + affected);

        return affected;
    }

	@Override
	@TriggersRemove(cacheName="endpointCache", removeAll=true)
	public boolean deactivateEndpoints(int tenantId) {
        // remove active flag from other endpoints in the same tenant and type
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  EndpointUpload" +
            " SET" +
            "  endpointUploadActive = 0" +
            " WHERE" +
            "  tenantID = ?"
         );

        sqlstm.append(!isExternal(tenantId, tenantId!=0) ?  " AND " +
            "  endpointUploadVersion IS NOT NULL":" AND " +
            "  endpointUploadVersion IS NULL");
        
        int affected = getJdbcTemplate().update(sqlstm.toString(), tenantId);
		return affected > 0;
	}

	@Override
	public EndpointUpload getEndpointUploadForFileName (int tenantId, String endpointUploadFile, String endpointUploadType,
			String endpointUploadVersion) {
		logger.debug("Get record from EndpointUpload for tenantId = " + tenantId + " endpointUploadFile="+ " endpointUploadType="+endpointUploadType
				+ "endpointUploadVersion ="+endpointUploadVersion);
		
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  endpointUploadID," +
            "  tenantID," +
            "  endpointUploadFile," +
            "  endpointUploadTime," +
            "  endpointUploadType," +
            "  endpointUploadActive," +
            "  endpointUploadVersion" +
            " FROM" +
            "  EndpointUpload" +
            " WHERE" +
            "  tenantID=:tenantID AND endpointUploadFile = :endpointUploadFile"
        );

        MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("tenantID", tenantId)
			    .addValue("endpointUploadFile", endpointUploadFile);
        if (org.apache.commons.lang.StringUtils.isNotBlank(endpointUploadType) && org.apache.commons.lang.StringUtils.isNotBlank(endpointUploadVersion)){
        	sqlstm.append(" AND endpointUploadType = :endpointUploadType AND endpointUploadVersion = :endpointUploadVersion");
        	paramSource.addValue("endpointUploadType", endpointUploadType);
        	paramSource.addValue("endpointUploadVersion", endpointUploadVersion);
        }
       	
       	EndpointUpload endpoint = null;
       	List<EndpointUpload> endpointList = null;
       	endpointList = namedParamJdbcTemplate.query(sqlstm.toString(), paramSource, BeanPropertyRowMapper.newInstance(EndpointUpload.class));
       	if (endpointList != null && endpointList.size() > 0){
       		endpoint = endpointList.get(0);
       	}
        return endpoint;
	}

}