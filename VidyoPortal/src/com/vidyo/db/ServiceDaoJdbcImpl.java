package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.Location;
import com.vidyo.bo.NEConfiguration;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.RecorderEndpoint;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.Service;
import com.vidyo.bo.ServiceFilter;
import com.vidyo.bo.VirtualEndpoint;
import com.vidyo.bo.VirtualEndpointFilter;
import com.vidyo.bo.gateway.GatewayPrefix;
import com.vidyo.bo.gateway.GatewayPrefixFilter;
import com.vidyo.bo.networkconfig.IpAddressMap;
import com.vidyo.bo.tenantservice.TenantServiceMap;
import com.vidyo.framework.security.utils.VidyoUtil;
import com.vidyo.rest.gateway.Prefix;

public class ServiceDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements IServiceDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(ServiceDaoJdbcImpl.class.getName());

    private int getServicesRoleID (String roleName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  roleID " +
            " FROM" +
            "  ServicesRole" +
            " WHERE" +
            "  roleName = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                },
                roleName
        );

        return li.size() > 0 ? li.get(0) : 0;
    }

    public List<Service> getServices(ServiceFilter filter, String defaultServerNameForLocalhost) {
        logger.debug("Get records from Services");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user, s.password," +
            "  REPLACE(s.url, 'localhost', '"+defaultServerNameForLocalhost+"') as 'url'," +
            "  s.serviceName," +
			"  s.token" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE" +
            "  1"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
            sqlstm.append(" AND s.serviceName LIKE :serviceName");
            namedParamsMap.put("serviceName", filter.getServiceName()+"%");
            if (!filter.getType().equalsIgnoreCase("All")) {
                sqlstm.append(" AND sr.roleName LIKE :roleName");
                namedParamsMap.put("roleName", filter.getType()+"%");
            }
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        return getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap ,BeanPropertyRowMapper.newInstance(Service.class));
    }

    public List<Service> getServices(ServiceFilter filter) {
        return getServices(filter, "localhost");
    }

    public Long getCountServices(ServiceFilter filter) {
        logger.debug("Get count of records from Services");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0) " +
            " FROM" +
            "  Services s" +
             " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE 1 "
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();

        if (filter != null) {
            if (!filter.getType().equalsIgnoreCase("All")) {
                sqlstm.append(" AND sr.roleName LIKE :roleName");
                namedParamsMap.put("roleName", filter.getType()+"%");
            }
        }

        logger.debug(sqlstm.toString());

		List<Long> ll = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, new RowMapper<Long>() {
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Service getServiceByUserName(String serviceUserName, String serviceRole) {
        logger.debug("Get record from Service for user = " + serviceUserName);
        StringBuffer sqlstm = null;
        if(serviceRole.equalsIgnoreCase("VidyoGateway")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  comp.id as serviceID," +
                    "  ct.id as roleID," +
                    "  ct.name as roleName," +
                    "  vgc.username as user," +
        			"  vgc.service_endpoint_guid as serviceEndpointGuid, " +
                    "  comp.name as serviceName," +
        			"  vgc.token," +
        			"  vgc.service_ref as serviceRef" +
                    " FROM" +
                    " vidyo_gateway_config vgc, components comp, components_type ct " +
                    " where vgc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vgc.username = ?"
                );
        } else if(serviceRole.equalsIgnoreCase("VidyoRecorder")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  comp.id as serviceID," +
                    "  ct.id as roleID," +
                    "  ct.name as roleName," +
                    "  vrec.username as user," +
                    "  comp.name as serviceName," +
        			"  vrec.password as password" +
                    " FROM" +
                    " vidyo_recorder_config vrec, components comp, components_type ct " +
                    " where vrec.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vrec.username = ?"
                );
        } else if(serviceRole.equalsIgnoreCase("VidyoReplay")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  comp.id as serviceID," +
                    "  ct.id as roleID," +
                    "  ct.name as roleName," +
                    "  vrpc.username as user," +
                    "  comp.name as serviceName," +
        			"  vrpc.password as password" +
                    " FROM" +
                    " vidyo_replay_config vrpc, components comp, components_type ct " +
                    " where vrpc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vrpc.username = ?"
                );
        }

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceRole, serviceUserName);
    }

    public Integer getCountServiceByUserName(String serviceUserName, String serviceRole, int compID) {
        logger.debug("Get record from Service for user = " + serviceUserName);
        StringBuffer sqlstm = null;
        if(serviceRole.equalsIgnoreCase("VidyoGateway")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  COUNT(1) " +
                    " FROM" +
                    " vidyo_gateway_config vgc, components comp, components_type ct " +
                    " where vgc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vgc.username = ?  and vgc.comp_id != ?"
                );
        } else if(serviceRole.equalsIgnoreCase("VidyoRecorder")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  COUNT(1)" +
                    " FROM" +
                    " vidyo_recorder_config vrec, components comp, components_type ct " +
                    " where vrec.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vrec.username = ? and vrec.comp_id != ?"
                );
        } else if(serviceRole.equalsIgnoreCase("VidyoReplay")) {
            sqlstm = new StringBuffer(
                    " SELECT " +
                    "  COUNT(1)" +
                    " FROM" +
                    " vidyo_replay_config vrpc, components comp, components_type ct " +
                    " where vrpc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = ? and " +
                    " vrpc.username = ? and vrpc.comp_id != ?"
                );
        }

        List<Integer> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, serviceRole, serviceUserName, compID
        );

        return (ll != null && ll.size() > 0) ? ll.get(0):0;
    }

    public Service getServiceByServiceName(String serviceName, int serviceRole) {
        logger.debug("Get record from Service for service name = " + serviceName);

        if(serviceRole > 0){ // not router
	        StringBuffer sqlstm = new StringBuffer(
	            " SELECT " +
	            "  s.serviceID," +
	            "  sr.roleID," +
	            "  sr.roleName," +
	            "  s.user," +
	            "  s.url," +
				"  s.serviceEndpointGuid, " +
	            "  s.serviceName," +
				"  s.token," +
				"  s.serviceRef" +
	            " FROM" +
	            "  Services s" +
	            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID AND s.roleID = ?)" +
	            " WHERE" +
	            "  s.serviceName = ?"
	        );

	        return getJdbcTemplate().queryForObject(sqlstm.toString(),
	                BeanPropertyRowMapper.newInstance(Service.class), serviceRole, serviceName);
        }
        else{
	        return null;
	    }
    }

	@Cacheable(cacheName="servicesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Service getVG(int serviceID) {
        logger.debug("Get record from Service for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  comp.id as serviceID," +
            "  ct.id as roleID," +
            "  ct.name as roleName," +
            "  vgc.username as user," +
    		"  vgc.service_endpoint_guid as serviceEndpointGuid, " +
            "  comp.name as serviceName," +
    		"  vgc.token," +
    		"  vgc.service_ref as serviceRef" +
            " FROM" +
            " vidyo_gateway_config vgc, components comp, components_type ct " +
            " where vgc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = 'VidyoGateway' and " +
            " comp.id = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceID);
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int updateVG(int serviceID, Service service) {
        logger.debug("Update record in Services for serviceID = " + serviceID);

        service.setServiceID(serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  vidyo_gateway_config vgc" +
            " SET" +
			"  vgc.service_endpoint_guid = :serviceEndpointGuid," +
			"  vgc.token = :token," +
			"  vgc.service_ref = :serviceRef" +
            " WHERE" +
            " vgc.comp_id = :serviceID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(service);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int insertVG(Service service) {
        logger.debug("Add record into Services");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Services")
                        .usingGeneratedKeyColumns("serviceID");

        service.setRoleID(getServicesRoleID("VidyoGateway"));
        service.setPassword(VidyoUtil.encrypt(service.getPassword()));
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(service);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New serviceID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int deleteVG(int serviceID) {
        logger.debug("Remove record from Services for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Services" +
            " WHERE" +
            "  serviceID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), serviceID);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

	public static final String DELETE_PREFIXES_FOR_SERVICE = "DELETE FROM GatewayPrefixes WHERE serviceID = ?";

	public void resetGatewayPrefixes(int serviceID, String gatewayID, Set<Prefix> prefixes, int tenantID) {
		getJdbcTemplate().update(DELETE_PREFIXES_FOR_SERVICE, serviceID);

		if (prefixes != null && prefixes.size() > 0) {
            for (Prefix prefix : prefixes) {
                GatewayPrefix gwPrefix = new GatewayPrefix();
                gwPrefix.setServiceID(serviceID);
                gwPrefix.setGatewayID(gatewayID);
                gwPrefix.setPrefix(prefix.getName());
                gwPrefix.setDirection(prefix.getDirection().getIntValue());
                gwPrefix.setTenantID(tenantID);
                createGatewayPrefix(gwPrefix);
            }
        }
    }

    public static final String UPDATE_PREFIXES_FOR_SERVICE = "UPDATE GatewayPrefixes SET updateTime = NOW() WHERE serviceID = ?";

    public void updateGatewayPrefixesTimestamp(int serviceID) {
        getJdbcTemplate().update(UPDATE_PREFIXES_FOR_SERVICE, serviceID);
    }

    public static final String CLEAR_STALE_GATEWAY_PREFIXES = "DELETE FROM GatewayPrefixes " +
            " WHERE TIMESTAMPDIFF(SECOND, `updateTime`, CURRENT_TIMESTAMP) > :ageInSeconds";

    public int clearStaleGatewayPrefixesOlderThan(int seconds) {
        logger.debug("Remove record from GatewayPrefixes older than 5 minutes");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ageInSeconds", seconds);
        int affected = getNamedParameterJdbcTemplate().update(CLEAR_STALE_GATEWAY_PREFIXES, paramMap);
        logger.debug("Rows affected: " + affected);

        return affected;
    }


    private void createGatewayPrefix(GatewayPrefix gwPrefix) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("GatewayPrefixes")
				.usingGeneratedKeyColumns("prefixID");

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(gwPrefix);
		insert.executeAndReturnKey(parameters);
	}

    public Service getVM(int serviceID) {
        logger.debug("Get record from Service serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID AND sr.roleName='VidyoManager')" +
            " WHERE" +
            "  s.serviceID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceID);
    }

    @TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache", "componentsUserDataCache"}, removeAll=true)
    public int updateVM(int serviceID, Service service) {
        logger.debug("Update record in Services for serviceID = " + serviceID);

        service.setServiceID(serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Services s" +
            " SET" +
            "  s.serviceName = :serviceName," +
            "  s.user = :user,"
        );
        if (service.getPassword() != null) {
            service.setPassword(VidyoUtil.encrypt(service.getPassword()));
            sqlstm.append("  s.password = :password,");
        }
        sqlstm.append(
            "  s.url = :url" +
            " WHERE" +
            "  (s.serviceID = :serviceID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(service);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache", "componentsUserDataCache"}, removeAll=true)
    public int insertVM(Service service) {
        logger.debug("Add record into Services");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Services")
                        .usingGeneratedKeyColumns("serviceID");

        service.setRoleID(getServicesRoleID("VidyoManager"));
        service.setPassword(VidyoUtil.encrypt(service.getPassword()));
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(service);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New serviceID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName={"vidyoManagerServiceCache", "vidyoManagerServiceStubCache", "componentsUserDataCache"}, removeAll=true)
    public int deleteVM(int serviceID) {
        logger.debug("Remove record from Services for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Services" +
            " WHERE" +
            "  serviceID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), serviceID);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public Service getVP(int serviceID) {
        logger.debug("Get record from Service for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
    		" SELECT " +
			" comp.id as serviceID," +
			" ct.id as roleID," +
			" ct.name as roleName," +
			" vrc.SCIP_FQDN as url," +
			" comp.name as serviceName" +
			" FROM" +
			" vidyo_router_config vrc, components comp, components_type ct " +
			" where vrc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = 'VidyoRouter' and " +
			" comp.id = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceID);
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int updateVP(int serviceID, Service service) {
        logger.debug("Update record in Services for serviceID = " + serviceID);

        service.setServiceID(serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Services s" +
            " SET" +
            "  s.serviceName = :serviceName," +
            "  s.user = :user,"
        );
        if (service.getPassword() != null) {
            service.setPassword(VidyoUtil.encrypt(service.getPassword()));
            sqlstm.append("  s.password = :password,");
        }
        sqlstm.append(
            "  s.url = :url" +
            " WHERE" +
            "  (s.serviceID = :serviceID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(service);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    public int insertVP(Service service) {
        logger.debug("Add record into Services");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Services")
                        .usingGeneratedKeyColumns("serviceID");

        service.setRoleID(getServicesRoleID("VidyoProxy"));
        service.setPassword(VidyoUtil.encrypt(service.getPassword()));
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(service);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New serviceID = " + newID.intValue());

        return newID.intValue();
    }


    public int deleteVP(int serviceID) {
        logger.debug("Remove record from Services for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Services" +
            " WHERE" +
            "  serviceID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), serviceID);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    private static final String GET_PROXY_FOR_MEMBER = "SELECT  vrc.SCIP_FQDN as url FROM vidyo_router_config vrc, components comp, components_type ct, Member m  where vrc.comp_id = comp.id and comp.comp_type_id = ct.id and ct.name = 'VidyoRouter' and m.proxyID = comp.id and m.memberID = ?";

    public String getProxyCSVList(int memberID) {

        List<String> ls = getJdbcTemplate().query(GET_PROXY_FOR_MEMBER.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, memberID
        );

        return ls.size() > 0 ? ls.get(0) : null;
    }

    public List<VirtualEndpoint> getVirtualEndpoints(int serviceID, VirtualEndpointFilter filter) {
        List<VirtualEndpoint> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  ve.endpointID," +
            "  ve.serviceID," +
            "  ve.gatewayID," +
            "  ve.displayName," +
            "  ve.prefix," +
            "  ve.endpointGUID," +
            "  ve.status," +
            "  ve.direction," +
            "  ve.updateTime" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " WHERE" +
            "  ve.serviceID = :serviceID" +
            " AND" +
            " status != 0"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("serviceID", serviceID);

        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(VirtualEndpoint.class));
        return rc;
    }

    public Long getCountVirtualEndpoints(int serviceID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  VirtualEndpoints ve" +
            " WHERE" +
            "  ve.serviceID = ?" +
            " AND" +
            " status != 0"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, serviceID
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

	public List<GatewayPrefix> getGatewayPrefixes(int serviceID, GatewayPrefixFilter filter) {
		List<GatewayPrefix> rc;
		StringBuffer sqlstm = new StringBuffer(
				" SELECT" +
						"  gf.prefixID," +
						"  gf.serviceID," +
						"  gf.gatewayID," +
						"  gf.prefix," +
						"  gf.direction," +
						"  gf.tenantID" +
						" FROM" +
						"  GatewayPrefixes gf" +
						" WHERE" +
						"  gf.serviceID = :serviceID"
		);

		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("serviceID", serviceID);

		if (filter != null) {
			sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
			sqlstm.append(" LIMIT :start, :limit");
			namedParamsMap.put("start", filter.getStart());
			namedParamsMap.put("limit", filter.getLimit());
		}

		rc = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(GatewayPrefix.class));
		return rc;
	}

	public Long getCountGatewayPrefixes(int serviceID) {
		StringBuffer sqlstm = new StringBuffer(
				" SELECT" +
						"  COUNT(0)" +
						" FROM" +
						"  GatewayPrefixes gf" +
						" WHERE" +
						"  gf.serviceID = ?"
		);

		List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
				new RowMapper<Long>() {
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong(1);
					}
				}, serviceID
		);

		return ll.size() > 0 ? ll.get(0) : 0l;
	}

    public int registerVirtualEndpoint(int serviceID, VirtualEndpoint ve, int tenant) {
        logger.debug("Add record into VirtualEndpoints for serviceID = " + serviceID);

        ve.setServiceID(serviceID);
        ve.setTenantID(tenant);
		ve.setSequenceNum(0l);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("VirtualEndpoints")
                        .usingGeneratedKeyColumns("endpointID");

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(ve);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New endpointID = " + newID.intValue());

        return newID.intValue();
    }

    public int getTenantIDforServiceID(int serviceID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  tenantID" +
            " FROM" +
            "  TenantXservice" +
            " WHERE" +
            "  serviceID = ?"
        );

        List<Integer> li = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt(1);
                    }
                }, serviceID
        );

        return li.size() > 0 ? li.get(0) : 1; // Default tenant by default
    }

    public boolean isVGExistsWithServiceName(String serviceName) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.serviceName = :serviceName" +
            " AND" +
            "  s.roleID = 2"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("serviceName", serviceName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isVGExistsWithLoginName(String loginName, int serviceID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.user = :loginName" +
            " AND" +
            "  s.roleID = 2"
        );
        HashMap<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (serviceID != 0) {
            sqlstm.append(" AND s.serviceID <> :serviceID");
            namedParamsMap.put("serviceID", serviceID);
        }

        namedParamsMap.put("loginName", loginName);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParamsMap, Integer.class);
        return num > 0;
    }

    public List<NEConfiguration> getNEConfigurations(String wildcard, String type) {
        logger.debug("Get records from NetworkElementConfiguration");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.Identifier," +
            "  s.DisplayName," +
            "  s.ComponentType," +
            "  s.IpAddress," +
            "  s.WebApplicationURL," +
            "  s.Data," +
            "  s.RunningVersion," +
            "  s.Version," +
            "  s.ExpirySeconds," +
            "  s.Alarm," +
            "  s.SwVer," +
            "  s.Status," +
            "  s.LastModified," +
            "  s.Heartbeat" +
            " FROM" +
            "  NetworkElementConfiguration s" +
            " WHERE" +
            //"  s.ComponentType!='VidyoProxy'"+
            //" AND "+
            "  s.DisplayName like :wildcard"
        );
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("wildcard", wildcard);

        if(!type.equalsIgnoreCase("All"))
            sqlstm.append(" AND s.ComponentType = :componentType");
        	namedParamsMap.put("componentType", type);

        if(logger.isDebugEnabled()) {
        	logger.debug("SQL: "+sqlstm);
        }

        return getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(NEConfiguration.class));
    }

    public List<NEConfiguration> getSingleNEConfiguration(String id, String type){
        logger.debug("Get single record from NetworkElementConfiguration id="+id);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.Identifier," +
            "  s.DisplayName," +
            "  s.ComponentType," +
            "  s.IpAddress," +
            "  s.Data," +
            "  s.RunningVersion," +
            "  s.Version," +
            "  s.ExpirySeconds," +
            "  s.Alarm," +
            "  s.SwVer," +
            "  s.Status," +
            "  s.LastModified," +
            "  s.Heartbeat" +
            " FROM" +
            "  NetworkElementConfiguration s" +
            " WHERE" +
            "  s.ComponentType = ?"+
            " AND "+
            "  s.Identifier = ?"
        );

        logger.debug("SQL: "+sqlstm);

        return getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), type, id);
    }

    public Long getCountNEConfigurations() {
        logger.debug("Get count of records from NetworkElementConfiguration");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0) " +
            " FROM" +
            "  NetworkElementConfiguration s"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public boolean updateNEConfiguration(NEConfiguration nec, boolean setNew, boolean increaseVersion){
        logger.debug("Update NetworkElementConfiguration id="+nec.getIdentifier());

        String identifier = nec.getIdentifier();
        nec.setLastModified(new java.util.Date());

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  NetworkElementConfiguration s" +
            " SET" +
            "  s.Data = :data, "+
            "  s.DisplayName = :displayName, "+
            "  s.LastModified = :lastModified ");

        if(increaseVersion) {
            sqlstm.append(", s.Version = s.Version+1 ");
        }

        if(setNew)
            sqlstm.append(", s.Status = 'NEW' ");
        else if(nec.getStatus().equals("NEW"))
            sqlstm.append(", s.Status = 'ACTIVE' ");

        sqlstm.append(" WHERE  (s.Identifier = :identifier)"
        );

        logger.debug("SQL: "+sqlstm);

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(nec);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Update NetworkElementConfiguration - Rows affected(should be 1): " + affected);

        return (affected==1)? true : false;      //should be 1 and only 1 record been updated.
    }

    public boolean deleteNEConfiguration(String id){
        logger.debug("Delete NetworkElementConfiguration id="+id);

        //query name
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  DisplayName, "+
            "  ComponentType, "+
            "  Status "+
            " FROM" +
            "  NetworkElementConfiguration" +
            " WHERE" +
            "  Identifier = ?"
        );

        List<NEConfiguration> ll = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), id);
        String servicename = (ll.size() > 0) ? ll.get(0).getDisplayName() : null;
        String status = (ll.size() > 0) ? ll.get(0).getStatus() : null;
        String type = (ll.size() > 0) ? ll.get(0).getComponentType() : null;

        //delete from table : NetworkElementConfiguration
        sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  NetworkElementConfiguration" +
            " WHERE" +
            "  Identifier = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), id);
        logger.debug("Delete NetworkElementConfiguration: Rows affected: " + affected);

        if(type!=null && type.equals("VidyoRouter"))
            return true;
        else if(affected != 1)
            return false;
        else if(status!=null &&  status.equals("NEW"))
            return true;
        else if(servicename == null || servicename.equals("")) //then no need to delete from Service table
            return true;

        //delete from table : Service
        sqlstm = new StringBuffer(
            " DELETE " +
            " FROM " +
            "  Services " +
            " WHERE " +
            "  BINARY serviceName = ?"
        );
        affected = getJdbcTemplate().update(sqlstm.toString(), servicename);
        logger.debug("Delete Service - [serviceName="+servicename+"]: Rows affected: " + affected);

        return (affected==1)? true : false;
    }

    public boolean enableNEConfiguration(String id, boolean enable) {
        logger.debug("Enable/Disable NetworkElementConfiguration: id="+id+" enable="+enable);
        String status = enable? "ACTIVE" : "INACTIVE";
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  NetworkElementConfiguration s" +
            " SET" +
            "  s.LastModified = :lastModified, "+
            "  s.Status = :status" +
            " WHERE "+
            "  (s.Identifier = :id)"
        );

        logger.debug("SQL: "+sqlstm);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("lastModified", new java.util.Date());
        params.put("id", id);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), params);
        logger.debug("Update NetworkElementConfiguration - Rows affected(should be 1): " + affected);

        return (affected==1)? true : false;
    }

    @Override
    @Cacheable(cacheName = "networkConfigDataCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public NEConfiguration getNetworkConfig(String status, int ver){
        logger.debug("Get NetworkConfig");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.Identifier," +
            "  s.Data," +
            "  s.Version," +
            "  s.Status," +
            "  s.LastModified" +
            " FROM" +
            "  NetworkConfig s"  +
            " WHERE" +
            "  s.Status = ?" +
            " ORDER BY"+
            " s.Version"+
            " ASC"
        );

        List<NEConfiguration> list;
        if(status.equals("INACTIVE") && (ver != -1)) {
            sqlstm.append(
                " AND" +
                "  s.Version = ?"
            );
            list = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), status, ver);
        }
        else {
            list = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), status);
        }

        logger.debug("SQL: "+sqlstm);

        if(list.size() < 1)
            return null;
        else {
            NEConfiguration nec = (NEConfiguration)list.get(0);
            return nec;
        }

    }


	@Override
	@TriggersRemove(cacheName = { "networkConfigDataCache",
			"routerPoolNamesCache", "routerPoolNamesCache", "memberLocationCache" }, removeAll = true)
    public void clearNetworkConfig() {
    	logger.warn("Empty interceptor to clear NetworkConfig Data Cache");
    }

	@Override
	@TriggersRemove(cacheName = { "networkConfigDataCache",
			"routerPoolNamesCache", "routerPoolNamesCache" }, removeAll = true)
    public boolean updateInProgressNetworkConfig(NEConfiguration nec) {
        logger.debug("Update NetworkConfig");

        List<NEConfiguration> actlist, inprolist;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  *" +
            " FROM" +
            "  NetworkConfig s" +
            " WHERE" +
            "  s.Status = ? " +
            " ORDER BY"+
            " s.Version"+
            " ASC"
        );
        NEConfiguration newActNec = new  NEConfiguration();
        actlist = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), "ACTIVE");
        if(actlist.size() == 0){   //this branch should NEVER be reach
            logger.error("!! Missing ACTIVE NetworkConfig!! ");
            //in case this happen, insert a ACTIVE NetworkConfig in the database

            newActNec.setIdentifier("NC_0001");
            newActNec.setData("<NetworkConfig><DocumentVersion>0</DocumentVersion><NetworkElement/><Categories/> <LocationCandidates><Candidate>EID</Candidate><Candidate>LocalIP</Candidate><Candidate>ExternalIP</Candidate><Candidate>LocationTag</Candidate></LocationCandidates><BandwidthMap/></NetworkConfig>");
            newActNec.setVersion(0);
            java.util.Date t = new java.util.Date();
            t.setTime(t.getTime()-2000);      // minus 2 seconds
            newActNec.setLastModified(t);
            newActNec.setStatus("ACTIVE");

            SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("NetworkConfig");
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(newActNec);
            insert.execute(parameters);
        }

        inprolist = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), "INPROGRESS");
        if(inprolist.size() == 0) { // insert INPROGRESS at first time by duplicate the ACTIVE one
            NEConfiguration nec1 = (actlist.size() != 0)? (NEConfiguration)actlist.get(0) : newActNec;
            nec1.setStatus("INPROGRESS");
            nec1.setVersion(nec1.getVersion()+1);

            SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                            .withTableName("NetworkConfig");
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(nec1);
            insert.execute(parameters);
        }

        String identifier = nec.getIdentifier();
        nec.setLastModified(new java.util.Date());
        nec.setStatus("INPROGRESS");

        sqlstm = new StringBuffer(
            " UPDATE" +
            "  NetworkConfig s" +
            " SET" +
            "  s.Data = :data, "+
            "  s.LastModified = :lastModified "+
            //"  s.Version = s.Version+1 " +
            " WHERE  (s.Status = :status)"
        );

        logger.debug("SQL: "+sqlstm);

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(nec);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Update NetworkElementConfiguration - Rows affected(should be 1): " + affected);

        return (affected==1)? true : false;      //should be 1 and only 1 record been updated.
    }

    public boolean activateNetworkConfig(){
        logger.debug("Activate NetworkConfig 'INPROGRESS'->'ACTIVE'");
        try {
            StringBuffer sqlstm = new StringBuffer(
                " UPDATE" +
                "  NetworkConfig s" +
                " SET" +
                "  s.Status = 'ACTIVE' "+
                " WHERE  (s.Status = 'INPROGRESS')"
            );

            logger.debug("SQL: "+sqlstm);

            getJdbcTemplate().update(sqlstm.toString());
            logger.debug("Active NetworkConfig - change INPROGRESS into ACTIVE");

            sqlstm = new StringBuffer (
                    " SELECT" +
                    "   UNIX_TIMESTAMP(MAX(s.LastModified))" +
                    " FROM" +
                    "   NetworkConfig s" +
                    " WHERE" +
                    "   s.Status='ACTIVE'"
            );

            long timestamp = getJdbcTemplate().queryForObject(sqlstm.toString(), Long.class);
            sqlstm = new StringBuffer(
                " UPDATE" +
                "  NetworkConfig s" +
                " SET" +
                "  s.Status = 'INACTIVE' "+
                " WHERE  (s.LastModified != FROM_UNIXTIME( ? ))"
            );
            getJdbcTemplate().update(sqlstm.toString(), timestamp);
        }
        catch(Exception anyEx){
            logger.error("Failed to do: change INPROGRESS into ACTIVE / change old ACTIVE into INACTIVE. "+anyEx.getMessage());
            return false;
        }
        return true;
    }

    public boolean discardInProgressNetworkConfig() {
        logger.debug("Delete NetworkConfig 'INPROGRESS'");
        try {
            StringBuffer sqlstm = new StringBuffer(
                " DELETE FROM" +
                "  NetworkConfig" +
                " WHERE"+
                "  Status = ?"
            );

            logger.debug("SQL: "+sqlstm);

            int affected = getJdbcTemplate().update(sqlstm.toString(), "INPROGRESS");
            logger.debug("Rows affected: " + affected);
        }
        catch(Exception anyEx) {
            logger.info("Failed to delete INPROGRESS row from NetworkConfig table "+anyEx.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Find out if component's system ID is changed, if changed then replace it.
     *
     * When following condition is satisified, renew old component ideitifier with newID:
     *     - IpAddress is same
     *     - ComponentType is same
     *     - Heartbeat year equal to [triggerYear], which is 1999
     *     - newID is not present, which means this component is new
     * @param newID
     * @param type
     * @param ip
     * @param replaceTriggerInYear
     * @return
     */
    public boolean replaceSystmID(String newID, String type, String ip, String replaceTriggerInYear){
        logger.debug("Attempt to renew SystemID ");
        try {
            StringBuffer sqlstm = new StringBuffer(
                    " SELECT " +
                    "  s.Identifier " +
                    " FROM " +
                    "  NetworkElementConfiguration s "  +
                    " WHERE " +
                    "  s.Identifier != ? " +
                    " AND " +
                    "  s.ComponentType = ? " +
                    " AND " +
                    "  s.IpAddress = ? " +
                    " AND " +
                    "  YEAR(s.Heartbeat) = ? "
            );
            logger.debug("SQL: "+sqlstm);

            List<NEConfiguration> list;
            list = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class),
                                                    newID, type, ip, replaceTriggerInYear);

            if(list.size() == 0) {
                logger.debug(" NO network element was replaced because SELECT components of old systemID from NEC table returns 0 records.");
                return false;
            }

            NEConfiguration nec = (NEConfiguration)list.get(0);
            String oldID = nec.getIdentifier();
            java.util.Date now = new java.util.Date();

            //In NetworkElementConfiguration, replace old identifier with new system id.
            sqlstm = new StringBuffer(
                    " UPDATE " +
                    "    NetworkElementConfiguration s "+
                    " SET " +
                    "    s.Identifier = ?,  " +
                    "    s.LastModified = ?, "+
                    "    s.Heartbeat = ? " +
                    " WHERE " +
                    "    s.Identifier = ?"
            );
            logger.debug("SQL: "+sqlstm);

            int affected = getJdbcTemplate().update(sqlstm.toString(), newID, now, now, oldID);
            if(affected < 1) {
                logger.warn(" NO network element was replaced because UPDATE systemID in NEC table affect 0 records");
                return false;
            }

            //In Cloud NetworkConfig, replace old VidyoRouter's NE id with new system id.
            if(type.equalsIgnoreCase("VidyoManager")) {
                sqlstm = new StringBuffer(
                    " SELECT " +
                    "  *" +
                    " FROM" +
                    "  NetworkConfig s" +
                    " WHERE" +
                    "  s.Status in (:status) "
                );
                logger.warn("SQL: "+sqlstm + "");

                Map<String, Object> params = new HashMap<String, Object>();
                List<String> statusList = new ArrayList<String>(2);
                statusList.add("ACTIVE");
                statusList.add("INPROGRESS");
                params.put("status", statusList);

                list = getJdbcTemplate().query(sqlstm.toString(), BeanPropertyRowMapper.newInstance(NEConfiguration.class), params);
                if(list.size() > 0) {   // PROCESS ACTIVE & INPROGRESS
                	for(NEConfiguration networkConfig : list) {
                        String newNetworkConfigXml;
                        String oldNetworkConfigXml = networkConfig.getData();

                        //count how many apperance of oldID
                        int count = 0;
                        int idx = 0;
                        String oldIDSub = oldID.substring(0, 48);
                        while ((idx = oldNetworkConfigXml.indexOf("<Identifier>"+oldIDSub, idx)) != -1) {
                            idx++;
                            count++;
                        }


                        if(count==0) {
                            logger.warn("No replacement required in NetworkConfig xml because old VidyoRouter ID wasn't used in old NetworkConfig xml");
                            //return true;
                        }
                        newID = newID.substring(0, 48);
                        //TODO - check the logic
                        if (count <= 2) {
                            logger.debug("Replace old System ID with new System ID, Done!   Count of old ID: "+count);
                            newNetworkConfigXml = oldNetworkConfigXml.replaceAll(oldIDSub, newID);
                        }
                        else { //If it was used by multiple Pools, remove this VidyoRouter from VR Pools
                            logger.debug("Replace first old System ID with new System ID only. Remove all other old System ID from pools. Done!   Count of old ID: "+count);
                            newNetworkConfigXml = oldNetworkConfigXml.replaceFirst(oldIDSub, newID)
                                                                     .replaceAll("<Identifier>"+oldID+"</Identifier>", "");
                        }
                        networkConfig.setData(newNetworkConfigXml);

                        StringBuffer updateStatement = new StringBuffer(
                                " UPDATE" +
                                "  NetworkConfig s" +
                                " SET" +
                                "  s.Data = :data, "+
                                "  s.LastModified = :lastModified "+
                                " WHERE  (s.Version = :version)"
                            );
                            logger.debug("SQL: "+updateStatement);

                            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(networkConfig);
                            getNamedParameterJdbcTemplate().update(updateStatement.toString(), namedParameters);
                	}
                }
            }
            return true;
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public Service getVGByName(String name) {
        logger.debug("Get record from Service for serviceName = " + name);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName," +
			"  s.token" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE" +
            "  s.serviceName = ? " +
            " AND sr.roleID = 2 " +              //1-VM, 2-VG, 3-VP
            " ORDER BY s.serviceID LIMIT 1"
        );

        try{
            return getJdbcTemplate().queryForObject(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Service.class), name);
        }
        catch(Exception e) {
            return null;
        }
    }

    public Service getVRecByName(String name) {
        logger.debug("Get record from Service for serviceName = " + name);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE" +
            "  s.serviceName = ? " +
            " AND sr.roleID = 4 " +              //1-VM, 2-VG, 3-VP, 4-ReplayRecorder
            " ORDER BY s.serviceID LIMIT 1"
        );

        try{
            return getJdbcTemplate().queryForObject(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Service.class), name);
        }
        catch(Exception e) {
            return null;
        }
    }

    public Service getVPByName(String name) {
        logger.debug("Get record from Service for serviceName = " + name);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE" +
            "  s.serviceName = ? " +
            " AND sr.roleID = 3 " +                //1-VM, 2-VG, 3-VP
            "  ORDER BY s.serviceID LIMIT 1"
        );

        try{
            return getJdbcTemplate().queryForObject(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Service.class), name);
        }
        catch(Exception e){
            return null;
        }
    }

    public Service getVMByName(String name) {
        logger.debug("Get record from Service for serviceName = " + name);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " WHERE" +
            "  s.serviceName = ? " +
            " AND sr.roleID = 1 " +                //1-VM, 2-VG, 3-VP
            "  ORDER BY s.serviceID LIMIT 1"
        );

        try{
            return getJdbcTemplate().queryForObject(sqlstm.toString(), BeanPropertyRowMapper.newInstance(Service.class), name);
        }
        catch(Exception e){
            return null;
        }
    }


    public Service getSuperVM() {
        logger.debug("Get record from Service: find which VM is used by Super");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.url," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID)" +
            " INNER JOIN TenantXservice ts ON (s.serviceID=ts.serviceID)" +
            " WHERE" +
            "  ts.tenantID = ? " +
            " AND sr.roleID = 1 "           //1-VM, 2-VG, 3-VP
        );

        try {
            return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), 1);  //1-Default Super
        }
        catch(Exception e){
            return null;
        }
    }

    public int insertNetworkElementConfig(NEConfiguration nec){
        logger.debug("Add record into NetworkElementConfiguration");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("NetworkElementConfiguration");


        SqlParameterSource parameters = new BeanPropertySqlParameterSource(nec);
        Number newID = insert.execute(parameters);

        logger.debug("New NetworkElementConfiguration internalid = " + newID.intValue());

        return newID.intValue();

    }

    public int touchNetworkElementConfig(NEConfiguration nec) {
        logger.debug("Touch NetworkElementConfig with current timestamp");

        String identifier = nec.getIdentifier();
        nec.setHeartbeat(new java.util.Date());

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  NetworkElementConfiguration s" +
            " SET");
        //if(nec.getComponentType().equals("VidyoRouter")) {
        //  sqlstm.append(" s.DisplayName = :displayName, ");
        //}
        sqlstm.append(
            "  s.Heartbeat = :heartbeat, "+
            "  s.IpAddress = :ipAddress, "+
            "  s.WebApplicationURL  = :webApplicationURL , "+
            "  s.Data  = :data , "+
            "  s.Alarm = :alarm, "+
            "  s.SwVer = :swVer, "+
            "  s.RunningVersion = :runningVersion "+
            " WHERE  (s.Identifier = :identifier)"
        );

        logger.debug("SQL: "+sqlstm);

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(nec);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Update NetworkElementConfiguration - Rows affected(should be 1): " + affected);

        return affected;
    }

    public boolean isServiceUsed(int tenant, String type, String serviceName){
        logger.debug("Is Service used? " + serviceName + "; type: " + type);

        HashMap<String, String> params = new HashMap<String, String>();
        StringBuffer sqlstm = new StringBuffer();

        //if(type > 0){ //not router
	        sqlstm.append(
	            " SELECT" +
	            "  COUNT(0)" +
	            " FROM" +
	            "  components s" +
	            " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME =:type)" +
	            " INNER JOIN TenantXservice txs ON (txs.serviceID = s.ID)" +
	            " WHERE" +
	            "  s.NAME = :serviceName"
	        );
	        params.put("type", String.valueOf(type));
        /*}
        else{
	        sqlstm.append(
		            " SELECT" +
		            "  COUNT(0)" +
		            " FROM" +
		            "  Routers r" +
		            " INNER JOIN TenantXrouter txr ON (txr.routerID = r.routerID)" +
		            " WHERE" +
		            "  r.routerName = :serviceName"
		        );
        }*/

        params.put("serviceName", serviceName);
        params.put("type", type);
        params.put("tenant", String.valueOf(tenant));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean replaceNetworkComponent(int tenant, int type, String toBeDeleteServiceName, String replacementServiceName){
        logger.debug("Replace network component " + toBeDeleteServiceName + " with " + replacementServiceName);

        HashMap<String, String> params = new HashMap<String, String>();

        //delete from table : TenantXservice
        StringBuffer sqlstm = new StringBuffer(
                " DELETE" +
                " FROM" +
                "  TenantXservice" +
                " WHERE" +
                "  serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type)" +
                " AND" +
                "  tenantID NOT IN " +
                "	(SELECT DISTINCT outertxs.tenantID FROM (SELECT * FROM TenantXservice) AS outertxs " +
                "		WHERE EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXservice) AS innertxs WHERE innertxs.tenantID = outertxs.tenantID AND innertxs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type))" +
                "		AND NOT EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXservice) AS innertxs WHERE innertxs.tenantID = outertxs.tenantID AND innertxs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type))" +
                "	)"
        );

        params.put("toBeDeleteServiceName", toBeDeleteServiceName);
        params.put("replacementServiceName", replacementServiceName);
        params.put("type", String.valueOf(type));
        params.put("tenant", String.valueOf(tenant));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int affected;
        try {
            affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        }
        catch(Exception anyex){
            logger.error("Failed to execute query: "+ sqlstm.toString());
            return false;
        }

        logger.debug("Delete network component - Rows affected(could be 1): " + affected);

        sqlstm = new StringBuffer(
            " UPDATE" +
            "  TenantXservice txs" +
            " SET" +
            "  txs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
            " WHERE" +
            "  txs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)"
        );

        logger.debug("SQL: "+sqlstm);

        try {
            affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        }
        catch(Exception anyEx){
            logger.error("Failed to execute query: "+ sqlstm.toString());
            return false;
        }

        logger.debug("Replace network component - Rows affected(should be 1): " + affected);

        if(type == 3){ //VidyoProxy
        	logger.debug("Replace VidyoProxy " + toBeDeleteServiceName + " with " + replacementServiceName);

            sqlstm = new StringBuffer(
                " UPDATE" +
                "  Member m" +
                " SET" +
                "  m.proxyID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
                " WHERE" +
                "  m.proxyID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)"
            );

            logger.debug("SQL: "+sqlstm);

            try{
                affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
            }
            catch(Exception anyEx){
                logger.error("Failed to execute query: "+ sqlstm.toString());
                return false;
            }

            sqlstm = new StringBuffer(
                " UPDATE" +
                "  Configuration c" +
                " SET" +
                "  c.configurationValue = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
                " WHERE" +
                "  c.configurationValue = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)" +
                " AND" +
                "  c.configurationName = 'GuestProxyID'"
            );

            logger.debug("SQL: "+sqlstm);
            try{
                affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
            }
            catch(Exception anyEx){
                logger.error("Failed to execute query: "+ sqlstm.toString());
                return false;
            }
        }

        return true;
    }

    public boolean replaceNetworkComponent(int tenant, String type, String toBeDeleteServiceName, String replacementServiceName){
        logger.debug("Replace network component " + toBeDeleteServiceName + " with " + replacementServiceName);

        HashMap<String, String> params = new HashMap<String, String>();

        //delete from table : TenantXservice
        StringBuffer sqlstm = new StringBuffer(
                " DELETE" +
                " FROM" +
                "  TenantXservice" +
                " WHERE" +
                "  serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type)" +
                " AND" +
                "  tenantID NOT IN " +
                "	(SELECT DISTINCT outertxs.tenantID FROM (SELECT * FROM TenantXservice) AS outertxs " +
                "		WHERE EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXservice) AS innertxs WHERE innertxs.tenantID = outertxs.tenantID AND innertxs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type))" +
                "		AND NOT EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXservice) AS innertxs WHERE innertxs.tenantID = outertxs.tenantID AND innertxs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type))" +
                "	)"
        );

        params.put("toBeDeleteServiceName", toBeDeleteServiceName);
        params.put("replacementServiceName", replacementServiceName);
        params.put("type", String.valueOf(type));
        params.put("tenant", String.valueOf(tenant));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int affected;
        try {
            affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        }
        catch(Exception anyex){
            logger.error("Failed to execute query: "+ sqlstm.toString());
            return false;
        }

        logger.debug("Delete network component - Rows affected(could be 1): " + affected);

        sqlstm = new StringBuffer(
            " UPDATE" +
            "  TenantXservice txs" +
            " SET" +
            "  txs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
            " WHERE" +
            "  txs.serviceID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)"
        );

        logger.debug("SQL: "+sqlstm);

        try {
            affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        }
        catch(Exception anyEx){
            logger.error("Failed to execute query: "+ sqlstm.toString());
            return false;
        }

        logger.debug("Replace network component - Rows affected(should be 1): " + affected);

        if(type.equalsIgnoreCase("VidyoRouter")){ //VidyoProxy
        	logger.debug("Replace VidyoProxy " + toBeDeleteServiceName + " with " + replacementServiceName);

            sqlstm = new StringBuffer(
                " UPDATE" +
                "  Member m" +
                " SET" +
                "  m.proxyID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
                " WHERE" +
                "  m.proxyID = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)"
            );

            logger.debug("SQL: "+sqlstm);

            try{
                affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
            }
            catch(Exception anyEx){
                logger.error("Failed to execute query: "+ sqlstm.toString());
                return false;
            }

            sqlstm = new StringBuffer(
                " UPDATE" +
                "  Configuration c" +
                " SET" +
                "  c.configurationValue = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :replacementServiceName AND c.comp_type_id = ct.id and ct.name =:type) " +
                " WHERE" +
                "  c.configurationValue = (SELECT c.id as serviceID FROM components c, components_type ct WHERE c.name = :toBeDeleteServiceName AND c.comp_type_id = ct.id and ct.name =:type)" +
                " AND" +
                "  c.configurationName = 'GuestProxyID'"
            );

            logger.debug("SQL: "+sqlstm);
            try{
                affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
            }
            catch(Exception anyEx){
                logger.error("Failed to execute query: "+ sqlstm.toString());
                return false;
            }
        }

        return true;
    }

    @Cacheable(cacheName="servicesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Service getRec(int serviceID) {
        logger.debug("Get record from Service for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID AND sr.roleName='VidyoRecorder')" +
            " WHERE" +
            "  s.serviceID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceID);
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int updateRec(int serviceID, Service service) {
        logger.debug("Update record in Services for serviceID = " + serviceID);

        service.setServiceID(serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Services s" +
            " SET" +
            "  s.user = :user,"
        );
        if (service.getPassword() != null) {
        	service.setPassword(VidyoUtil.encrypt(service.getPassword()));
            sqlstm.append("  s.password = :password,");
        }
        sqlstm.append(
            "  s.serviceName = :serviceName" +
            " WHERE" +
            "  (s.serviceID = :serviceID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(service);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int insertRec(Service service) {
        logger.debug("Add record into Services");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Services")
                        .usingGeneratedKeyColumns("serviceID");

        service.setRoleID(getServicesRoleID("VidyoRecorder"));
        service.setPassword(VidyoUtil.encrypt(service.getPassword()));
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(service);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New serviceID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int deleteRec(int serviceID) {
        logger.debug("Remove record from Services for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Services" +
            " WHERE" +
            "  serviceID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), serviceID);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    public boolean isRecExistsWithServiceName(String serviceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.serviceName = :serviceName" +
            " AND" +
            "  s.roleID = 4" // VidyoRecorder
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("serviceName", serviceName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isRecExistsWithLoginName(String loginName, int serviceID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.user = :loginName" +
            " AND" +
            "  s.roleID = 4" // VidyoRecorder
        );

        HashMap<String, Object> params = new HashMap<String, Object>();
        if (serviceID != 0) {
            sqlstm.append(" AND s.serviceID <> :serviceID ");
            params.put("serviceID", serviceID);
        }

        params.put("loginName", loginName);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), params, Integer.class);
        return num > 0;
    }

    public List<RecorderEndpoint> getRecorderEndpoints(int serviceID, RecorderEndpointFilter filter) {
        List<RecorderEndpoint> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  endpointID," +
            "  serviceID," +
            "  recID," +
            "  description," +
            "  prefix," +
            "  endpointGUID," +
            "  status," +
            "  updateTime" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  serviceID = :serviceID" +
            " AND" +
            "  status != 0"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("serviceID", serviceID);

        if (filter != null) {
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        rc = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(RecorderEndpoint.class));
        return rc;
    }

    public Long getCountRecorderEndpoints(int serviceID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  serviceID = ?" +
            " AND" +
            "  status != 0"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }, serviceID
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public int registerRecorderEndpoint(int serviceID, RecorderEndpoint re) {
        logger.debug("Add record into RecorderEndpoints for serviceID = " + serviceID);

        re.setServiceID(serviceID);
		re.setSequenceNum(0l);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("RecorderEndpoints")
                        .usingGeneratedKeyColumns("endpointID");

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(re);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New endpointID = " + newID.intValue());

        return newID.intValue();
    }

    public int clearRegisterRecorderEndpoint(int serviceID) {
        logger.debug("Remove record from RecorderEndpoints for serviceID = " + serviceID + " with Status=Offline more then 5 minutes");

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  RecorderEndpoints" +
            " WHERE" +
            "  status = 0" +
            " AND" +
            "  TIMESTAMPDIFF(MINUTE, `updateTime`, CURRENT_TIMESTAMP) > 5 "
        );

        int affected = getJdbcTemplate().update(sqlstm.toString());
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @Cacheable(cacheName="servicesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Service getReplay(int serviceID) {
        logger.debug("Get record from Service for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.serviceID," +
            "  sr.roleID," +
            "  sr.roleName," +
            "  s.user," +
            "  s.serviceName" +
            " FROM" +
            "  Services s" +
            " INNER JOIN ServicesRole sr ON (sr.roleID=s.roleID AND sr.roleName='VidyoReplay')" +
            " WHERE" +
            "  s.serviceID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Service.class), serviceID);
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int updateReplay(int serviceID, Service service) {
        logger.debug("Update record in Services for serviceID = " + serviceID);

        service.setServiceID(serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Services s" +
            " SET" +
            "  s.user = :user,"
        );
        if (service.getPassword() != null) {
            service.setPassword(VidyoUtil.encrypt(service.getPassword()));
            sqlstm.append("  s.password = :password,");
        }
        sqlstm.append(
            "  s.serviceName = :serviceName" +
            " WHERE" +
            "  (s.serviceID = :serviceID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(service);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int insertReplay(Service service) {
        logger.debug("Add record into Services");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Services")
                        .usingGeneratedKeyColumns("serviceID");

        service.setRoleID(getServicesRoleID("VidyoReplay"));
        service.setPassword(VidyoUtil.encrypt(service.getPassword()));
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(service);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New serviceID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName={"servicesCache", "componentsUserDataCache"}, removeAll=true)
    public int deleteReplay(int serviceID) {
        logger.debug("Remove record from Services for serviceID = " + serviceID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Services" +
            " WHERE" +
            "  serviceID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), serviceID);
        logger.debug("Rows affected: " + affected);

        return serviceID;
    }

    public boolean isReplayExistsWithServiceName(String serviceName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.serviceName = :serviceName" +
            " AND" +
            "  s.roleID = 5" // VidyoReplay
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("serviceName", serviceName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isReplayExistsWithLoginName(String loginName, int serviceID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Services s" +
            " WHERE" +
            "  s.user = :loginName" +
            " AND" +
            "  s.roleID = 5" // VidyoReplay
        );

        if (serviceID != 0) {
            sqlstm.append(" AND s.serviceID <> ").append(String.valueOf(serviceID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("loginName", loginName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public List<Location> getLocations(ServiceFilter filter) {
        logger.debug("Get records from Locations");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.locationID," +
            "  l.locationTag" +
            " FROM" +
            "  Locations l" +
            " WHERE" +
            "  1"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
            sqlstm.append(" AND l.locationTag LIKE :locationTag");
            namedParamsMap.put("locationTag", filter.getServiceName()+"%");
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        return getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Location.class));
    }

    public List<Location> getSelectedLocationTags(ServiceFilter filter,int tenantId) {
        logger.debug("Get records from Locations for the selected ");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.locationID," +
            "  l.locationTag" +
            " FROM" +
            "  Locations l" +
            " WHERE"
        );
        sqlstm.append(" l.locationID IN (SELECT locationID FROM TenantXlocation WHERE tenantID = :tenantId)");
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
            sqlstm.append(" AND l.locationTag LIKE :locationTag");
            namedParamsMap.put("locationTag", filter.getServiceName()+"%");
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
            namedParamsMap.put("tenantId", tenantId);
        }

        return getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Location.class));
    }

    public Long getCountLocations(ServiceFilter filter) {
        logger.debug("Get count of records from Locations");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0) " +
            " FROM" +
            "  Locations l" +
            " WHERE 1 "
        );
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
        	sqlstm.append(" AND l.locationTag LIKE :locationTag");
            namedParamsMap.put("locationTag", filter.getServiceName()+"%");
        }

        logger.debug(sqlstm.toString());

		List<Long> ll = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, new RowMapper<Long>() {
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});

        return ll.size() > 0 ? ll.get(0) : 0l;
    }
    public Long getCountLocations(ServiceFilter filter,int tenantId) {
        logger.debug("Get count of records from Locations");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0) " +
            " FROM" +
            "  Locations l" +
            " WHERE  "
        );
        sqlstm.append(" l.locationID IN (SELECT locationID FROM TenantXlocation WHERE tenantID = :tenantId)");
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        if (filter != null) {
        	sqlstm.append(" AND l.locationTag LIKE :locationTag");
            namedParamsMap.put("locationTag", filter.getServiceName()+"%");
            namedParamsMap.put("tenantId", tenantId);
        }

        logger.debug(sqlstm.toString());

		List<Long> ll = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, new RowMapper<Long>() {
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Location getLocation(int locationID) {
        logger.debug("Get record from Locations for locationID = " + locationID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.locationID," +
            "  l.locationTag" +
            " FROM" +
            "  Locations l" +
            " WHERE" +
            "  l.locationID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Location.class), locationID);
    }

    @TriggersRemove(cacheName="memberLocationCache", removeAll=true)
    public int updateLocation(int locationID, Location location) {
        logger.debug("Update record in Locations for locationID = " + locationID);

        location.setLocationID(locationID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Locations l" +
            " SET" +
            "  l.locationTag = :locationTag" +
            " WHERE" +
            "  (l.locationID = :locationID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(location);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return locationID;
    }

    @TriggersRemove(cacheName="memberLocationCache", removeAll=true)
    public int insertLocation(Location location) {
        logger.debug("Add record into Locations");

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Locations")
                        .usingGeneratedKeyColumns("locationID");

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(location);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New locationID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName="memberLocationCache", removeAll=true)
    public int deleteLocation(int locationID) {
        logger.debug("Update records back to Default location in Member for locationID = " + locationID);

		StringBuffer sqlstmUpdate = new StringBuffer(
			" UPDATE" +
			"  Member m" +
			" SET" +
			"  m.locationID = 1" +
			" WHERE" +
			"  (m.locationID = :locationID)"
		);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("locationID", locationID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstmUpdate.toString(), paramMap);
		logger.debug("Rows affected: " + affected);

		logger.debug("Remove record in Locations for locationID = " + locationID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Locations" +
            " WHERE" +
            "  locationID = ?"
        );

        affected = getJdbcTemplate().update(sqlstm.toString(), locationID);
        logger.debug("Rows affected: " + affected);

        logger.debug("Remove records in TenantXlocation for locationID = " + locationID);

        sqlstm.delete(0, sqlstm.length());
        sqlstm.append(
            " DELETE" +
            " FROM" +
            "  TenantXlocation" +
            " WHERE" +
            "  locationID = ?"
        );

        affected = getJdbcTemplate().update(sqlstm.toString(), locationID);
        logger.debug("Rows affected: " + affected);

        return locationID;
    }

    public boolean isLocationExistsWithLocationTag(String locationTag, int locationID) {

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Locations l" +
            " WHERE" +
            "  l.locationTag = :locationTag"
        );

        HashMap<String, Object> params = new HashMap<String, Object>();
        if (locationID != 0) {
            sqlstm.append(" AND l.locationID <> :locationID");
            params.put("locationID", locationID);
        }

        params.put("locationTag", locationTag);

        int num = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), params, Integer.class);
        return num > 0;
    }

    @Cacheable(cacheName="memberLocationCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public String getLocationTagForMember(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.locationTag" +
            " FROM" +
            "  Locations l" +
            " INNER JOIN Member m ON (l.locationID=m.locationID AND m.memberID = ?)"
        );

        List<String> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, memberID
        );

        return ls.size() > 0 ? ls.get(0) : "";
    }

	public int getLocationIdByLocationTag(String locationTag) {

		StringBuffer sqlstm = new StringBuffer(
				" SELECT locationID FROM Locations l WHERE l.locationTag = :locationTag");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("locationTag", locationTag);
		List<Integer> list = getNamedParameterJdbcTemplate().queryForList(sqlstm.toString(), paramMap, Integer.class);
		return list.size() == 1 ? list.get(0) : 0;
	}

	/**
	 * Gets the Active NetworkElementConfigs by Id and Type
	 *
	 * @param ids
	 * @param type
	 * @return
	 */
	public List<NEConfiguration> getActiveNEConfigurations(List<String> ids,
			String type) {
		String GET_ACTIVE_ELEMENTS = "SELECT  s.Identifier, s.DisplayName, s.ComponentType, s.Status FROM   NetworkElementConfiguration s  WHERE s.ComponentType = :type  AND s.Identifier in (:ids) and s.Status =:status";

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("ids", ids);
		args.put("type", type);
		args.put("status", "ACTIVE");
		return getNamedParameterJdbcTemplate().query(
				GET_ACTIVE_ELEMENTS, args,
				BeanPropertyRowMapper
				.newInstance(NEConfiguration.class));

	}

	/**
	 *
	 */
	private static final String DELETE_SERVICE_RECORDS_BY_IDS = "DELETE FROM Services where serviceID in (:serviceIds)";

	/**
	 * Deletes the Service Entry Records from the Services table based on the
	 * Ids passed
	 *
	 * @param serviceIds
	 * @return
	 */
	@TriggersRemove(cacheName = {"componentsUserDataCache"}, removeAll = true)
	public int deleteServices(List<Integer> serviceIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serviceIds", serviceIds);
		return getNamedParameterJdbcTemplate().update(DELETE_SERVICE_RECORDS_BY_IDS,
				params);
	}

	/**
	 *
	 */
	private static final String TENANTIDS_BY_SERVICE_IDS = "SELECT DISTINCT tenantID from TenantXservice where serviceID in (:serviceIds)";

	/**
	 * Returns the TenantIds mapped to the ServiceIds
	 * @param serviceIds
	 * @return
	 */
	public List<Integer> getTenantIdsByServiceIds(List<Integer> serviceIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serviceIds", serviceIds);
		return getNamedParameterJdbcTemplate().query(TENANTIDS_BY_SERVICE_IDS, params,
				new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt(1);
			}
		});
	}

	/**
	 *
	 */
	private static final String DELETE_TENANT_SERVICE_RECORDS_BY_SERVICEIDS = "DELETE FROM TenantXservice where serviceID in (:serviceIds)";

	/**
	 * Deletes the Tenant Service [TenantXservice] Mapping by ServiceIds
	 *
	 * @param serviceIds
	 * @return
	 */
	public int deleteTenantServiceMapping(List<Integer> serviceIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serviceIds", serviceIds);
		return getNamedParameterJdbcTemplate().update(
				DELETE_TENANT_SERVICE_RECORDS_BY_SERVICEIDS, params);
	}

	/**
	 * Inserts Tenant to Service mapping in to TenantXservice table
	 *
	 * @param tenantServiceMaps
	 */
	public void saveTenantServiceMappings(
			List<TenantServiceMap> tenantServiceMaps) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("TenantXservice");

		for (TenantServiceMap tenantServiceMap : tenantServiceMaps) {
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("tenantID", tenantServiceMap.getTenantId())
					.addValue("serviceID", tenantServiceMap.getServiceId());

			insert.execute(namedParameters);

		}
	}

    /*
    * START VPIpAddress Map
     */

public List<IpAddressMap> getVPAddnlAddrMaps() {
        logger.debug("Get records from VidyoProxyAddressMap");
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  addrMapID," +
            "  localAddr," +
            "  remoteAddr" +
            " FROM" +
            "  VidyoProxyAddressMap ORDER BY addrMapID Desc"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        List<IpAddressMap> list = getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParamsMap, new RowMapper<IpAddressMap>() {
			@Override
			public IpAddressMap mapRow(ResultSet resultset, int rowNum) throws SQLException {
                IpAddressMap apAddr=new IpAddressMap();
                apAddr.setAddrMapID(resultset.getInt("addrMapID"));
                apAddr.setLocalAddr(resultset.getString("localAddr"));
                apAddr.setRemoteAddr(resultset.getString("remoteAddr"));

                return apAddr;
            }
		});
		return list;
    }


    public int addVPAddnlAddrMap(IpAddressMap ipAddressMap){
        logger.debug("Add record into VidyoProxyAddressMap");
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("VidyoProxyAddressMap")
                        .usingGeneratedKeyColumns("addrMapID");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(ipAddressMap);
        Number newID = insert.executeAndReturnKey(parameters);
        logger.debug("New locationID = " + newID.intValue());
        bumpVersionNumberNEConfigTable();
        return newID.intValue();
    }

    public int updateVPAddnlAddrMap(IpAddressMap ipAddressMap){
        logger.debug("Update 1 record in VidyoProxyAddressMap against ID=" + ipAddressMap.getAddrMapID() );
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE VidyoProxyAddressMap " +
            " SET" +
            "  localAddr = :localaddr ," +
            "  remoteAddr = :remoteaddr " +
            " WHERE addrMapID = :addrmapid"
        );
        Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("localaddr", ipAddressMap.getLocalAddr());
        paramMap.put("remoteaddr", ipAddressMap.getRemoteAddr());
        paramMap.put("addrmapid", ipAddressMap.getAddrMapID());
         int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), paramMap);
        logger.debug("Rows affected: " + affected);
        bumpVersionNumberNEConfigTable();
        return affected;
    }

    public int deleteVPAddnlAddrMap(int ipAddressMapsID){
        logger.debug("Remove 1 record in VidyoProxyAddressMap" + ipAddressMapsID );
        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  VidyoProxyAddressMap" +
            " WHERE addrMapID = :addressmapid"
        );
        Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addressmapid", ipAddressMapsID);
         int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), paramMap);
        logger.debug("Rows affected: " + affected);
        bumpVersionNumberNEConfigTable();
        return affected;
    }

    private int bumpVersionNumberNEConfigTable(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("vp", "VidyoProxy");
        logger.debug("Bump up version number in NEConfiguration table against ComponentType=VP");
        return getNamedParameterJdbcTemplate().update("Update NetworkElementConfiguration set Version = Version+1 where ComponentType =:vp",params);
    }

	/**
	 * Empty implementation which clears cache [VidyoManagerData, LicenseData,
	 * VMID] through annotation. The param key is always a hardcoded value.
	 *
	 * @param key
	 */
    @Override
	@TriggersRemove(cacheName = { "vidyoManagerIdCache",
			"vmConnectAddressCache", "vidyoManagerServiceCache",
			"vidyoManagerServiceStubCache",
			"licenseFeatureDataCache", "licenseDataCache" }, removeAll = true)
	public void clearCache(String key) {
		// Empty implementation just has to be proxied through AOP.
	}

	/**
	 * Empty implementation which clears Components UserId cache.
	 * This method is implemented because the autowired classes triggers remove annoatation are not working.
	 *
	 * @param key
	 */
	@Override
	@TriggersRemove(cacheName = { "componentsUserDataCache", "vidyoManagerIdCache", "vmConnectAddressCache",
			"vidyoManagerServiceCache", "vidyoManagerServiceStubCache", "licenseFeatureDataCache",
			"licenseDataCache" }, removeAll = true)
    public void clearComponentsUserCache() {}

    public List<Proxy> getProxies(int tenant) {
    	StringBuffer sqlstm = new StringBuffer(
        " SELECT " +
        " s.ID as proxyID," +
        " s.NAME as proxyName" +
        " FROM" +
        " components s" +
        " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME = 'VidyoRouter')" +
        " WHERE s.ID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)");
        return getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Proxy.class), tenant);
    }

    public Long getCountProxies(int tenant) {
        logger.debug("Get count of records from Services for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            "SELECT" +
            " COUNT(0) " +
            " FROM" +
            " components s" +
            " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME = 'VidyoRouter')" +
            " WHERE s.ID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)");

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

    public Proxy getProxyByName(int tenant, String proxyName) {
        logger.debug("Get record from Services for tenant = " + tenant + " and proxyName = " + proxyName);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  s.ID as proxyID," +
            "  s.NAME as proxyName" +
            " FROM" +
            "  components s" +
            " INNER JOIN components_type sr ON (sr.ID = s.COMP_TYPE_ID AND sr.NAME = 'VidyoRouter')" +
            "  WHERE s.ID IN (SELECT serviceID FROM TenantXservice WHERE tenantID = ?)" +
            " AND" +
            "  s.NAME = ?"
        );

        List<Proxy> lp =  getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Proxy.class), tenant, proxyName);

        return lp.size() > 0 ? lp.get(0) : null;
    }

}

