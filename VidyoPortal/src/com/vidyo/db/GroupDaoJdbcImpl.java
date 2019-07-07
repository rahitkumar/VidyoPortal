package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vidyo.bo.Group;
import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Room;

public class GroupDaoJdbcImpl extends JdbcDaoSupport implements IGroupDao {

    /**
     * Template for handling SQLs that support named params
     */
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    /**
     *
     * @param namedParamJdbcTemplate
     */
    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
		this.namedParamJdbcTemplate = namedParamJdbcTemplate;
	}

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(GroupDaoJdbcImpl.class.getName());

    public List<Group> getGroups(int tenant, GroupFilter filter) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  g.groupID," +
            "  g.tenantID," +
            "  g.routerID," +
            "  g.secondaryRouterID," +
            "  g.groupName," +
            "  g.groupDescription," +
            "  g.defaultFlag," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  r.routerName," +
            "  r.active as routerActive," +
            "  sr.routerName as secondaryRouterName," +
            "  sr.active as secondaryRouterActive," +
            "  g.allowRecording" +
            " FROM" +
            "  Groups g" +
            " INNER JOIN Routers r ON (g.routerID = r.routerID)" +
            " INNER JOIN Routers sr ON (g.secondaryRouterID = sr.routerID)" +
            " WHERE" +
            "  g.tenantID = :tenantId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        if (filter != null) {
        	if(logger.isDebugEnabled()) {
        		logger.debug("Query -"+filter.getQuery());
        		logger.debug("GrpName -"+filter.getGroupName());
        		logger.debug("Sort -"+filter.getSort());
        		logger.debug("Dir -"+filter.getDir());
        		logger.debug("Start -"+filter.getStart());
        		logger.debug("Limit -"+filter.getLimit());
        	}
        	String grpName = filter.getGroupName();
            if (!filter.getQuery().equalsIgnoreCase("")) {
            	grpName = filter.getQuery();
            }

            grpName = grpName.replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");

            sqlstm.append(" AND g.groupName LIKE :grpName");
            namedParamsMap.put("grpName", grpName.concat("%"));
            //Order By and Sort by can be appended as they are validated by the webservice
            if (!filter.getSort().equalsIgnoreCase("")) {
            	sqlstm.append(" ORDER BY ").append(filter.getSort());
                sqlstm.append(" ").append(filter.getDir());
            }
            sqlstm.append(" LIMIT :start,:limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

		return namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Group.class));
    }

    public Long getCountGroups(int tenant, GroupFilter filter) {
        logger.debug("Get count of records from Groups for tenant = " + tenant);

        StringBuffer sqlstm = new StringBuffer(
            "SELECT COUNT(0) " +
            "FROM" +
            " Groups g" +
            " WHERE" +
            "  (g.tenantID = :tenantId)"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);

        if (filter != null) {
        	if(logger.isDebugEnabled()) {
        		logger.debug("Query -"+filter.getQuery());
        		logger.debug("GrpName -"+filter.getGroupName());
        		logger.debug("Sort -"+filter.getSort());
        		logger.debug("Dir -"+filter.getDir());
        		logger.debug("Start -"+filter.getStart());
        		logger.debug("Limit -"+filter.getLimit());
        	}

        	String grpName = filter.getGroupName();
            if (!filter.getQuery().equalsIgnoreCase("")) {
            	grpName = filter.getQuery();
            }

            grpName = grpName.replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");

            sqlstm.append(" AND g.groupName LIKE :grpName");
            namedParamsMap.put("grpName", grpName.concat("%"));
        }

        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                });

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    @Cacheable(cacheName="groupDataCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Group getGroup(int tenant, int groupID) {
        logger.debug("Get record from Groups for tenant = " + tenant + " and groupID = " + groupID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  g.groupID," +
            "  g.tenantID," +
            "  g.routerID," +
            "  g.secondaryRouterID," +
            "  g.groupName," +
            "  g.groupDescription," +
            "  g.defaultFlag," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  r.routerName," +
            "  r.active as routerActive," +
            "  sr.routerName as secondaryRouterName," +
            "  sr.active as secondaryRouterActive," +
            "  g.allowRecording" +
            " FROM" +
            "  Groups g" +
            " INNER JOIN Routers r ON (g.routerID = r.routerID)" +
            " INNER JOIN Routers sr ON (g.secondaryRouterID = sr.routerID)" +
            " WHERE" +
            "  g.groupID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Group.class), groupID);
    }

    @Cacheable(cacheName="groupDataCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Group getDefaultGroup(int tenant) {
        logger.debug("Get record from Groups for tenant = " + tenant);

        Group group = null;

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  g.groupID," +
            "  g.tenantID," +
            "  g.routerID," +
            "  g.secondaryRouterID," +
            "  g.groupName," +
            "  g.groupDescription," +
            "  g.defaultFlag," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  r.routerName," +
            "  r.active as routerActive," +
            "  sr.routerName as secondaryRouterName," +
            "  sr.active as secondaryRouterActive," +
            "  g.allowRecording" +
            " FROM" +
            "  Groups g" +
            " INNER JOIN Routers r ON (g.routerID = r.routerID)" +
            " INNER JOIN Routers sr ON (g.secondaryRouterID = sr.routerID)" +
            " WHERE" +
            "  g.defaultFlag = 1" +
            " AND" +
            "  g.tenantID = ?"
        );

        try{
        	group = getJdbcTemplate().queryForObject(sqlstm.toString(),
        			BeanPropertyRowMapper.newInstance(Group.class), tenant);
        }
        catch(Exception ignored){}

        return group;
    }

    @Cacheable(cacheName="groupDataCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public Group getGroupByName(int tenant, String groupName) {
        logger.debug("Get record from Groups for tenant = " + tenant + " and groupName = " + groupName);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  g.groupID," +
            "  g.tenantID," +
            "  g.routerID," +
            "  g.secondaryRouterID," +
            "  g.groupName," +
            "  g.groupDescription," +
            "  g.defaultFlag," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  r.routerName," +
            "  r.active as routerActive," +
            "  sr.routerName as secondaryRouterName," +
            "  sr.active as secondaryRouterActive," +
            "  g.allowRecording" +
            " FROM" +
            "  Groups g" +
            " INNER JOIN Routers r ON (g.routerID = r.routerID)" +
            " INNER JOIN Routers sr ON (g.secondaryRouterID = sr.routerID)" +
            " WHERE" +
            "  (g.tenantID = ?)" +
            " AND" +
            "  (g.groupName = ?)"
        );

        Group g = null;

        g = getJdbcTemplate().queryForObject(sqlstm.toString(),
        		BeanPropertyRowMapper.newInstance(Group.class), tenant, groupName);

        return g;
    }

    @TriggersRemove(cacheName="groupDataCache", removeAll=true)
    public int updateGroup(int tenant, int groupID, Group group) {
        logger.debug("Update record in Groups for tenant = " + tenant + " and groupID = " + groupID);

        group.setTenantID(tenant);
        group.setGroupID(groupID);

	    if (group.getGroupName().length() > 128) {
		    group.setGroupName(group.getGroupName().substring(0,128));
	    }

	    if (group.getGroupDescription() != null && group.getGroupDescription().length() > 256) {
		    group.setGroupDescription(group.getGroupDescription().substring(0,256));
	    }

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Groups g" +
            " SET" +
            "  g.groupName = :groupName," +
            "  g.groupDescription = :groupDescription," +
            //"  g.defaultFlag = :defaultFlag," +
            "  g.roomMaxUsers = :roomMaxUsers," +
            "  g.userMaxBandWidthIn = :userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut = :userMaxBandWidthOut," +
            //"  g.routerID = :routerID," +
            //"  g.secondaryRouterID = :secondaryRouterID," +
            "  g.allowRecording = :allowRecording" +
            " WHERE" +
            "  (g.tenantID = :tenantID)" +
            " AND " +
            "  (g.groupID = :groupID)"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(group);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return groupID;
    }

    @TriggersRemove(cacheName="groupDataCache", removeAll=true)
    public int insertGroup(int tenant, Group group) {
        logger.debug("Add record into Groups for tenant = " + tenant);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
                        .withTableName("Groups")
                        .usingGeneratedKeyColumns("groupID");

        group.setTenantID(tenant);
        //TODO - Hardcoded RouterId, to be removed once the Routers table is dropped
        group.setRouterID(1);
        group.setSecondaryRouterID(1);

	    if (group.getGroupName().length() > 128) {
		    group.setGroupName(group.getGroupName().substring(0,128));
	    }

	    if (group.getGroupDescription() != null && group.getGroupDescription().length() > 256) {
		    group.setGroupDescription(group.getGroupDescription().substring(0,256));
	    }

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(group);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New groupID = " + newID.intValue());

        return newID.intValue();
    }

    @TriggersRemove(cacheName="groupDataCache", removeAll=true)
    public int deleteGroup(int tenant, int groupID) {
        logger.debug("Remove record from Groups for groupID = " + groupID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Groups" +
            " WHERE" +
            "  (tenantID = ?)" +
            " AND " +
            "  (groupID = ?)"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), tenant, groupID);
        logger.debug("Rows affected: " + affected);

        return groupID;
    }

    public boolean isGroupExistForGroupName(int tenant, String groupName, int groupID) {

    	StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Groups g" +
            " WHERE" +
            "  g.tenantID = :tenantID" +
            " AND" +
            "  g.groupName = :groupName"
        );

        if (groupID != 0) {
            sqlstm.append(" AND g.groupID <> :groupID");
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenant));
        params.put("groupName", groupName);
        params.put("groupID", String.valueOf(groupID));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;

    }

    public List<Room> getRoomsForGroup(int tenant, int groupID){
        logger.debug("Get record from Room for tenant = " + tenant + " and groupID = " + groupID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  r.roomID," +
            "  r.roomTypeID," +
            "  r.memberID," +
            "  r.groupID," +
            "  g.groupName," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  r.roomDescription," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomKey," +
            "  m.tenantID," +
            "  rt.roomType," +
            "  m.username as ownerName," +
            "  m.memberName as displayName," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  'leave' as pinSetting," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            " (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') as numParticipants," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  r.roomMuted" +
            " FROM" +
            "  Room r" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID AND t.tenantID = ?)" +
            " WHERE" +
            "  r.groupID = ?"
        );

        return getJdbcTemplate().query(sqlstm.toString(), new BeanPropertyRowMapper<Room>(), tenant, groupID);
    }
}