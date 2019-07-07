package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.vidyo.bo.Router;

public class RouterDaoJdbcImpl extends JdbcDaoSupport implements IRouterDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(RouterDaoJdbcImpl.class.getName());

    public List<Router> getRouters(int tenant) {
        logger.debug("Get records from Routers");

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  routerID," +
            "  routerName," +
            "  description," +
            "  active" +
            " FROM" +
            "  Routers" +
            " WHERE" +
            "  routerID IN (SELECT routerID FROM TenantXrouter WHERE tenantID = ?)" +
            " ORDER BY routerName"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Router.class), tenant);
    }

    public Long getCountRouters(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Routers" +
            " WHERE" +
            "  routerID IN (SELECT routerID FROM TenantXrouter WHERE tenantID = ?)"
        );

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

    public Router getRouter(int tenant, int routerID) {
        logger.debug("Get record from Routers for routerID = " + routerID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  routerID," +
            "  routerName," +
            "  description," +
            "  active" +
            " FROM" +
            "  Routers r" +
            " WHERE" +
            "  r.routerID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Router.class), routerID);
    }

    public Router getRouterByName(int tenant, String routerName) {
        logger.debug("Get record from Routers for routerName = " + routerName);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  routerID," +
            "  routerName," +
            "  description," +
            "  active" +
            " FROM" +
            "  Routers r" +
            " WHERE" +
            "  r.routerID IN (SELECT routerID FROM TenantXrouter WHERE tenantID = ?)" +
            " AND" +
            "  r.routerName = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Router.class), tenant, routerName);
    }

    public boolean isRouterExistForRouterName(int tenant, String routerName, int routerID) {
        NamedParameterJdbcTemplate sqlTemplate = new NamedParameterJdbcTemplate(this.getDataSource());

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Routers r" +
            " WHERE" +
            "  r.routerID IN (SELECT routerID FROM TenantXrouter WHERE tenantID = :tenantID)" +
            " AND" +
            "  r.routerName = :routerName"
        );
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (routerID != 0) {
            sqlstm.append(" AND r.routerID <> :routerId");
            params.put("routerId", routerID);
        }

        params.put("tenantID", tenant);
        params.put("routerName", routerName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = sqlTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean replaceRouter(int tenant, String toBeDeleteServiceName, String replacementServiceName){
        logger.debug("Replace router " + toBeDeleteServiceName + " with " + replacementServiceName);

        NamedParameterJdbcTemplate updateTemplate = new NamedParameterJdbcTemplate(this.getDataSource());

        HashMap<String, String> params = new HashMap<String, String>();

        //delete from table : TenantXrouter
        StringBuffer sqlstm = new StringBuffer(
                " DELETE" +
                " FROM" +
                "  TenantXrouter" +
                " WHERE" +
                "  routerID = (SELECT r.routerID FROM Routers r WHERE r.routerName = :replacementServiceName)" +
                " AND" +
                "  tenantID NOT IN " +
                "	(SELECT DISTINCT outertxr.tenantID FROM (SELECT * FROM TenantXrouter) AS outertxr " +
                "		WHERE EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXrouter) AS innertxr WHERE innertxr.tenantID = outertxr.tenantID AND innertxr.routerID = (SELECT r.routerID FROM Routers r WHERE r.routerName = :replacementServiceName))" +
                "		AND NOT EXISTS " +
                "			(SELECT * FROM (SELECT * FROM TenantXrouter) AS innertxr WHERE innertxr.tenantID = outertxr.tenantID AND innertxr.routerID = (SELECT r.routerID FROM Routers r WHERE r.routerName = :toBeDeleteServiceName))" +
                "	)"
        );

        params.put("toBeDeleteServiceName", toBeDeleteServiceName);
        params.put("replacementServiceName", replacementServiceName);
        params.put("tenant", String.valueOf(tenant));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int affected = updateTemplate.update(sqlstm.toString(), namedParameters);

        logger.debug("Delete router component - Rows affected(could be 1): " + affected);


        sqlstm = new StringBuffer(
            " UPDATE" +
            "  TenantXrouter txr" +
            " SET" +
            "  txr.routerID = (SELECT r.routerID FROM Routers r WHERE r.routerName = :replacementServiceName) " +
            " WHERE" +
            "  txr.routerID = (SELECT r.routerID FROM Routers r WHERE r.routerName = :toBeDeleteServiceName)"
        );
        logger.debug("SQL: "+sqlstm);

        affected = updateTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Replace router - Rows affected(should be 1): " + affected);

        return (affected==1)? true : false;      //should be 1 and only 1 record been updated.
    }
}
