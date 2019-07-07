package com.vidyo.db;

import com.googlecode.ehcache.annotations.*;
import com.vidyo.bo.*;
import com.vidyo.bo.member.MemberEntity;
import com.vidyo.bo.member.MemberMini;
import com.vidyo.bo.passwordhistory.MemberPasswordHistory;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.utils.room.RoomUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class MemberDaoJdbcImpl extends JdbcDaoSupport implements IMemberDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(MemberDaoJdbcImpl.class.getName());

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

	public List<Member> getMembers(int tenant, MemberFilter filter) {
        List<Member> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  m.memberID," +
            "  m.roleID," +
            "  mr.roleName," +
            "  m.tenantID," +
            "  g.groupID," +
            "  g.groupName," +
            "  m.langID," +
	        "  lg.langCode," +
            "  m.profileID," +
            "  m.username," +
            "  m.password," +
            "  m.memberName," +
            "  m.active," +
            "  m.emailAddress," +
            "  m.memberCreated," +
            "  m.location," +
            "  m.description," +
            "  m.phone1," +
            "  m.phone2," +
            "  m.phone3," +
            "  m.title," +
            "  m.department," +
            "  m.thumbnailUpdateTime," +
            "  m.instantMessagerID," +
            "  r.roomID," +
            "  rt.roomTypeID," +
            "  rt.roomType," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            "  CASE WHEN m.proxyID IS NULL THEN 0 ELSE m.proxyID END as proxyID," +
            "  CASE WHEN m.proxyID IS NULL THEN '' WHEN m.proxyID = 0 THEN 'No Proxy' ELSE c.NAME END as proxyName," +
            "  CASE WHEN m.locationID IS NULL THEN 1 ELSE m.locationID END as locationID," +
            "  CASE WHEN m.locationID IS NULL THEN '' ELSE l.locationTag END as locationTag," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " LEFT JOIN components c " +
            " INNER JOIN components_type ct ON (c.comp_type_id = ct.id and ct.name = 'VidyoRouter') " +
            " ON (m.proxyID=c.ID)" +
            " LEFT JOIN Locations l ON (m.locationID=l.locationID)" +
            " LEFT JOIN Language lg ON (m.langID=lg.langID)" +
            " WHERE" +
            "  rt.roomTypeID IN (1,3)" +
            " AND" +
            "  m.tenantID = :tenantId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);

        if (filter != null) {
			if (filter.getUserStatus().equalsIgnoreCase("Enabled")) {
				sqlstm.append(" AND m.active = 1");
			} else if (filter.getUserStatus().equalsIgnoreCase("Disabled")) {
				sqlstm.append(" AND m.active = 0");
			}
            if (!filter.getQuery().equalsIgnoreCase("")) {
	        		if (StringUtils.isNotBlank(filter.getQuery())) {
	        			sqlstm.append(" AND MATCH(memberName, username) AGAINST(:query in boolean mode)");
	        			namedParamsMap.put("query", RoomUtils.buildSearchQuery(filter.getQuery()));	        		}
            } else {
	        		if (StringUtils.isNotBlank(filter.getMemberName())) {
	        			sqlstm.append(" AND MATCH(memberName, username) AGAINST(:memberName in boolean mode)");
	        			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getMemberName()));
	        		}

	        		if (StringUtils.isNotBlank(filter.getExt())) {
	        			sqlstm.append(" AND MATCH(roomName,roomExtNumber, displayName) AGAINST(:roomExtNumber in boolean mode)");
	        			namedParamsMap.put("roomExtNumber", RoomUtils.buildSearchQuery(filter.getExt()));
	        		}
	        		if (StringUtils.isNotBlank(filter.getGroupName())) {
	        			sqlstm.append(" AND g.groupName like :groupName");
	        			namedParamsMap.put("groupName", filter.getGroupName() + "%");
	        		}

                if (!filter.getType().equalsIgnoreCase("All")) {
                    sqlstm.append(" AND mr.roleName = :type");
                    namedParamsMap.put("type", filter.getType());
                }
            }

            if (!filter.getSort().equalsIgnoreCase("")) {
                sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }

        if (logger.isDebugEnabled()) {
        	logger.debug("getMembers SQL Query -" + sqlstm.toString());
        }
        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Member.class));
        if (logger.isDebugEnabled()) {
        	logger.debug("Get Members Result Set Size " + rc.size());
        }
        return rc;
    }

    public Long getCountInstalls(String tenant) {
        StringBuffer sqlstm = new StringBuffer(
			" SELECT SUM(num) FROM (" +
			" SELECT COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations ci" +
			" WHERE" +
			"  ci.tenantName = ?" +
			" UNION ALL" +
			" SELECT COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations2 ci2" +
			" WHERE" +
			"  ci2.tenantName = ?" +
			" ) as num"
        );

        logger.debug("getCountInstalls. SQL - " + sqlstm.toString());

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            },
            tenant,
		    tenant
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Long getCountAllInstalls() {
        StringBuffer sqlstm = new StringBuffer(
			" SELECT SUM(num) FROM (" +
			" SELECT  COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations ci" +
			" UNION ALL" +
			" SELECT COUNT(0) as num" +
			" FROM" +
			"  ClientInstallations2 ci2" +
			" ) as num"
        );

        logger.debug("getCountAllInstalls. SQL - " + sqlstm.toString());

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Long getCountSeats(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(memberID)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.roleID in (1,2,3)"+
            " AND"+
            "  m.active = 1"+
            " AND"+
            "  m.allowedToParticipate = 1"+
            " AND"+
            "  m.tenantID = ?"
        );

        logger.debug("getCountSeats. SQL - " + sqlstm.toString());

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

    public Long getCountAllSeats() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(memberID)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.roleID in (1,2,3)"+
            " AND"+
            "  m.active = 1"+
            " AND"+
            "  m.allowedToParticipate = 1"
        );

        logger.debug("getCountSeats. SQL - " + sqlstm.toString());

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Long getCountMembers(int tenant, MemberFilter filter) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " WHERE" +
            "  rt.roomTypeID IN (1,3)" +
            " AND" +
            "  m.tenantID = :tenantId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);

        if (filter != null) {
			if (filter.getUserStatus().equalsIgnoreCase("Enabled")) {
				sqlstm.append(" AND m.active = 1");
			} else if (filter.getUserStatus().equalsIgnoreCase("Disabled")) {
				sqlstm.append(" AND m.active = 0");
			}

            if (!filter.getQuery().equalsIgnoreCase("")) {
	        		if (StringUtils.isNotBlank(filter.getQuery())) {
	        			sqlstm.append(" AND MATCH(memberName, username) AGAINST(:query in boolean mode)");
	        			namedParamsMap.put("query", RoomUtils.buildSearchQuery(filter.getQuery()));	        		}
            } else {
	        		if (StringUtils.isNotBlank(filter.getMemberName())) {
	        			sqlstm.append(" AND MATCH(memberName, username) AGAINST(:memberName in boolean mode)");
	        			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getMemberName()));
	        		}

	        		if (StringUtils.isNotBlank(filter.getExt())) {
	        			sqlstm.append(" AND MATCH(roomName,roomExtNumber, displayName) AGAINST(:roomExtNumber in boolean mode)");
	        			namedParamsMap.put("roomExtNumber", RoomUtils.buildSearchQuery(filter.getExt()));
	        		}
	        		if (StringUtils.isNotBlank(filter.getGroupName())) {
	        			sqlstm.append(" AND g.groupName like :groupName");
	        			namedParamsMap.put("groupName", filter.getGroupName() + "%");
	        		}

                if (!filter.getType().equalsIgnoreCase("All")) {
                    sqlstm.append(" AND mr.roleName = :type");
                    namedParamsMap.put("type", filter.getType());
                }
            }

        }

        logger.debug("getCountMembers. SQL - " + sqlstm.toString());
        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(),
            namedParamsMap,
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );
        if(logger.isDebugEnabled()) {
        	logger.debug("getCountMembers -" + ll.size());
        }
        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public Member getMember(int tenant, int memberID) {
        logger.debug("Get record from Member for tenant = " + tenant + " and memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  m.memberID," +
                "  m.roleID," +
                    "  mr.roleName," +
                "  m.tenantID," +
                    "  g.groupID," +
                    "  g.groupName," +
                "  m.langID," +
                "  m.profileID," +
                "  m.username," +
                "  m.password," +
                "  m.memberName," +
                "  m.active," +
                "  m.allowedToParticipate," +
                "  m.emailAddress," +
                "  m.memberCreated," +
                "  m.location," +
                "  m.description," +
                "  m.phone1," +
                "  m.phone2," +
                "  m.phone3," +
                "  m.title," +
                "  m.department," +
                "  m.thumbnailUpdateTime," +
                "  m.userImageUploaded," +
                "  m.userImageAllowed," +
                "  m.neoRoomPermanentPairingDeviceUser," +
                "  m.instantMessagerID," +
                    "  r.roomID," +
                    "  rt.roomTypeID," +
                    "  rt.roomType," +
                    "  r.roomName," +
                    "  r.roomExtNumber," +
                "  t.tenantPrefix," +
                "  t.tenantID," +
                "  t.tenantName," +
                "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
                "  e.endpointGUID," +
                " e.endpointId, " +
                "  m.defaultEndpointGUID," +
                "  CASE WHEN m.proxyID IS NULL THEN 0 ELSE m.proxyID END as proxyID," +
                "  CASE WHEN m.proxyID IS NULL THEN '' WHEN m.proxyID = 0 THEN 'No Proxy' ELSE c.NAME END as proxyName," +
                "  CASE WHEN m.locationID IS NULL THEN 1 ELSE m.locationID END as locationID," +
                "  CASE WHEN m.locationID IS NULL THEN '' ELSE l.locationTag END as locationTag," +
                "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
                " FROM" +
                "  Member m" +
                " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
                " INNER JOIN Room r ON (m.memberID=r.memberID)" +
                " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
                " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
                " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
                " LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:memberType AND e.status!=:status)" +
                " LEFT JOIN components c " +
                " INNER JOIN components_type ct ON (c.comp_type_id = ct.id and ct.name = 'VidyoRouter') " +
                " ON (m.proxyID=c.ID)"+
                " LEFT JOIN Locations l ON (m.locationID=l.locationID)" +
                " WHERE" +
                "  rt.roomTypeID IN (:personal, :legacy)" +
                " AND" +
                "  m.memberID = :memberId"
            );

            Map<String, Object> namedParamsMap = new HashMap<String, Object>();
            namedParamsMap.put("memberType", "R");
            namedParamsMap.put("status", 0);
            namedParamsMap.put("roleId", 3);
            namedParamsMap.put("personal", 1);
            namedParamsMap.put("legacy", 3);
            namedParamsMap.put("memberId", memberID);
            BeanPropertyRowMapper<Member> propertyRowMapper = BeanPropertyRowMapper.newInstance(Member.class);
            propertyRowMapper.setPrimitivesDefaultedForNullValue(true);
            return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, propertyRowMapper);
    }

	public Member getMemberByRoom(int tenant, int roomID) {
        logger.debug("Get record from Member for tenant = " + tenant + " and memberID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
                "SELECT m.memberID  FROM Member m" +
                        "INNER JOIN Room r ON (m.memberID=r.memberID)" +
                        "INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
                        "LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType='R' AND e.status!=0)" +
                        "WHERE rt.roomTypeID IN (1,3) AND r.roomID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Member.class), roomID);
	}

    public Member getMemberNoRoom(int tenant, int memberID) {
        logger.debug("Get record from Member for tenant = " + tenant + " and memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
                "SELECT m.memberID, m.memberName, 0 as roomID, 0 as roomTypeID,'' as roomType,'' as roomName,0 as roomExtNumber, e.endpointGUID " +
                        "FROM Member m LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:memberType) " +
                        "WHERE m.memberID =:memberId"
            );

            Map<String, Object> namedParamsMap = new HashMap<String, Object>();
            namedParamsMap.put("memberType", "R");
            namedParamsMap.put("memberId", memberID);
            return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Member.class));
    }

    public Member getMemberByName(int tenant, String userName) {
        logger.debug("Get record from Member for tenant = " + tenant + " and userName = " + userName);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  m.memberID," +
            "  m.roleID," +
                "  mr.roleName," +
            "  m.tenantID," +
                "  g.groupID," +
                "  g.groupName," +
            "  m.langID," +
            "  m.profileID," +
            "  m.username," +
            "  m.password," +
            "  m.memberName," +
            "  m.active," +
            "  m.allowedToParticipate," +
            "  m.emailAddress," +
            "  m.memberCreated," +
            "  m.location," +
            "  m.description," +
            "  m.userImageUploaded," +
                "  r.roomID," +
                "  rt.roomTypeID," +
                "  rt.roomType," +
                "  r.roomName," +
                "  r.roomExtNumber," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            "  e.endpointGUID," +
            "  m.defaultEndpointGUID," +
            "  CASE WHEN m.proxyID IS NULL THEN 0 ELSE m.proxyID END as proxyID," +
            "  CASE WHEN m.proxyID IS NULL THEN '' WHEN m.proxyID = 0 THEN 'No Proxy' ELSE c.NAME END as proxyName," +
            "  CASE WHEN m.locationID IS NULL THEN 1 ELSE m.locationID END as locationID," +
            "  CASE WHEN m.locationID IS NULL THEN '' ELSE l.locationTag END as locationTag," +
            "  CASE WHEN m.importedUsed IS NULL THEN 0 ELSE m.importedUsed END as importedUsed" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType='R')" +
            " LEFT JOIN components c " +
            " INNER JOIN components_type ct ON (c.comp_type_id = ct.id and ct.name = 'VidyoRouter')" +
            " ON (m.proxyID=c.ID)" +
            " LEFT JOIN Locations l ON (m.locationID=l.locationID)" +
            " WHERE" +
            "  rt.roomTypeID IN (1,3)" +
            " AND" +
            "  m.tenantID = ?" +
            " AND" +
            "  m.username = ?"
        );
        Member member = null;
        try {
        	member = getJdbcTemplate().queryForObject(sqlstm.toString(),
                    BeanPropertyRowMapper.newInstance(Member.class), tenant, userName);
        } catch (EmptyResultDataAccessException e) {
        	// Member does not exists so keeing mem as null
        }
        return member;
    }

    public int updateSuper(Member member){
        logger.debug("Update Super Account in Member");

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.username = :username," +
            "  m.memberName = :memberName," +
            "  m.emailAddress = :emailAddress," +
            "  m.langID = :langID," +
            "  m.description = :description," +
            "  m.active = :active"
        );
        if(member.getActive() == 1) {
            member.setLoginFailureCount(0);
            sqlstm.append(", m.loginFailureCount =:loginFailureCount");
        }
        if (member.getPassword() != null) {
            sqlstm.append(",  m.password = :password");
        }

        sqlstm.append(
			" WHERE" +
			"  m.memberID = :memberID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

	@Cacheable(cacheName = "memberLangCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public Language getMemberLang(int tenant, @PartialCacheKey int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  l.langID," +
            "  l.langCode," +
            "  l.langName" +
            " FROM" +
            "  Member m" +
            " INNER JOIN Language l ON m.langID = l.langID" +
            " WHERE" +
            "  m.memberID = ?"
        );

        Language lang = getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Language.class), memberID);

        if(lang.getLangName().equalsIgnoreCase("System Language")){
        	sqlstm = new StringBuffer(
                " SELECT" +
                "  l.langID," +
                "  c.configurationValue AS 'langCode'," +
                "  l.langName" +
                " FROM" +
                "  Language l," +
                "  Configuration c" +
                " WHERE " +
                "  l.langName = 'System Language'" +
                " AND" +
                "  c.configurationName = 'SystemLanguage'" +
                " AND" +
                "  c.tenantID = ?"
            );

        	lang = getJdbcTemplate().queryForObject(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(Language.class), tenant);
        }

        logger.error("Get Member Lang ->" + lang);

        return lang;
    }

	@TriggersRemove(cacheName = {"memberLangCache", "servicesCache", "memberLocationCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int updateMember(int tenant, @PartialCacheKey int memberID, Member member) {
        logger.debug("Update record in Member for tenant = " + tenant + " and memberID = " + memberID);
        member.setTenantID(tenant);
        member.setMemberID(memberID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.roleID = :roleID," +
            "  m.tenantID = :tenantID," +
            "  m.langID = :langID," +
            "  m.profileID = :profileID," +
            "  m.username = :username," +
        	"  m.memberName = :memberName,"
        );
        if(member.getActive() == 1) {
        	member.setLoginFailureCount(0);
        	sqlstm.append(" m.loginFailureCount =:loginFailureCount,");
        }

        if (member.getPassword() != null) {
            sqlstm.append("  m.password = :password,");
			member.setPassword(member.getPassword());
        }
        if (member.getLastModifiedDateExternal() != null) {
            sqlstm.append("  m.lastModifiedDateExternal = :lastModifiedDateExternal,");
        }

        sqlstm.append(
            "  m.active = :active," +
            "  m.allowedToParticipate = :allowedToParticipate," +
            "  m.emailAddress = :emailAddress," +
            "  m.location = :location," +
            "  m.description = :description," +
            "  m.proxyID = :proxyID," +
            "  m.locationID = :locationID," +
            "  m.importedUsed = :importedUsed," +
            "  m.phone1 = :phone1," +
            "  m.phone2 = :phone2," +
            "  m.phone3 = :phone3," +
            "  m.department = :department," +
            "  m.instantMessagerID = :instantMessagerID," +
            "  m.title = :title," +
            "  m.userImageAllowed = :userImageAllowed," +
            "  m.neoRoomPermanentPairingDeviceUser = :neoRoomPermanentPairingDeviceUser" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected for Member: " + affected);

        sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.groupID = :groupID," +
            "  r.roomName = :username," +
            "  r.roomExtNumber = :roomExtNumber, " +
            "  r.displayName = :memberName" +
            " WHERE" +
            "  r.memberID = :memberID" +
            " AND" +
            "  r.roomTypeID = 1"
        );

        affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected for Room: " + affected);

        return memberID;
    }

    public int updateUserImageAlwdFlag(int tenantID, int memberID, int userImageAllowed) {
    	logger.debug("updateUserImageAlwdFlag for tenant = " + tenantID + " and memberID = " + memberID + " and enable = " + userImageAllowed);
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.userImageAllowed = :userImageAllowed"
        );


        sqlstm.append(
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userImageAllowed", Integer.toString(userImageAllowed));
        paramMap.put("loginFailureCount", "0");
        paramMap.put("tenantID", Integer.toString(tenantID));
        paramMap.put("memberID", Integer.toString(memberID));

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);

        logger.debug("Rows affected for Member: " + affected);

        return affected;
    }

    public int updateUserImageUploadedFlags(int tenantID, int memberID, int userImageUploaded) {
    	logger.debug("updateUserImageUploadedFlags for tenant = " + tenantID + " and memberID = " + memberID + " and enable = " + userImageUploaded);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.userImageUploaded = :userImageUploaded,"+
            "  m.lastModifiedDateExternal = :lastModifiedDateExternal"
        );


        sqlstm.append(
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userImageUploaded", Integer.toString(userImageUploaded));
        //updating the flag,so that external (ldap/saml)data will be retrieved
        paramMap.put("lastModifiedDateExternal", null);
        paramMap.put("tenantID", Integer.toString(tenantID));
        paramMap.put("memberID", Integer.toString(memberID));

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);

        logger.debug("Rows affected for Member: " + affected);

        return affected;
    }
    public int updateMemberUserImageUploaded(int tenantID, int memberID, int userImageUploaded) {
    	logger.debug("updateMemberUserImageUploaded for tenant = " + tenantID + " and memberID = " + memberID + " and enable = " + userImageUploaded);
        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.userImageUploaded = :userImageUploaded"

        );

        sqlstm.append(
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userImageUploaded", Integer.toString(userImageUploaded));
        paramMap.put("tenantID", Integer.toString(tenantID));
        paramMap.put("memberID", Integer.toString(memberID));

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);

        logger.debug("Rows affected for Member: " + affected);

        return affected;
    }
    public int updateAllMembersLastModifiedDateExt(int tenantID,  String dateStr) {
    	logger.debug("updateAllMembersLastModifiedDateExt for tenant = " + tenantID + " and dateStr = " + dateStr);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.lastModifiedDateExternal = :lastModifiedDateExternal"

        );


        sqlstm.append(
            " WHERE" +
            "  m.tenantID = :tenantID"
        );

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("lastModifiedDateExternal",dateStr);
        paramMap.put("tenantID", Integer.toString(tenantID));
        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);

        logger.debug("Rows affected for Member: " + affected);

        return affected;
    }
    public int enableUser(int tenantID, int memberID, int enable) {
    	logger.debug("Enable Member for tenant = " + tenantID + " and memberID = " + memberID + " and enable = " + enable);

    	StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.active = :active"
        );

        if(enable == 1) {
            sqlstm.append(", m.loginFailureCount = :loginFailureCount");
        }


        sqlstm.append(
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("active", Integer.toString(enable));
        paramMap.put("loginFailureCount", "0");
        paramMap.put("tenantID", Integer.toString(tenantID));
        paramMap.put("memberID", Integer.toString(memberID));

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), paramMap);

        logger.debug("Rows affected for Member: " + affected);

        return affected;
    }

    public int insertMember(int tenant, Member member) {
        logger.debug("Add record into Member for tenant = " + tenant);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("Member")
            .usingGeneratedKeyColumns("memberID");

        member.setTenantID(tenant);
        member.setMemberCreated((int)(new Date().getTime() * .001));
        member.setModeID(1);
        member.setLoginFailureCount(0);

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
        Number newID = insert.executeAndReturnKey(parameters);

        logger.debug("New memberID = " + newID.intValue());

        return newID.intValue();
    }

    @Transactional
    @TriggersRemove(cacheName={"memberLangCache"}, keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
    public int deleteMember(int tenant, @PartialCacheKey int memberID) {
        logger.debug("Remove record from Member for memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  tenantID = ?" +
            " AND " +
            "  memberID = ?"
        );

        String detachGroupFromMemberQuery = "delete from member_user_group where member_id=?";
        int dereferenced = getJdbcTemplate().update(detachGroupFromMemberQuery, memberID);
        logger.info("Dereferenced all usergroups from memberId :"+memberID+" No. of records deleted :" +dereferenced);
        int affected = getJdbcTemplate().update(sqlstm.toString(), tenant, memberID);
        logger.debug("Rows affected: " + affected);

        return memberID;
    }

	/**
	 * Empty method to clear userDetailCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [MemberDaoJdbcImpl] else cache wont get cleared.
	 *
	 * @param tenantId
	 * @param userName
	 * @return
	 */
	@TriggersRemove(cacheName = { "userDataCache" }, keyGenerator = @KeyGenerator(name = "StringCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int deleteMember(int tenantId, String userName) {
		return 0;
	}

	/**
	 * Empty method to clear userDetailCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [MemberDaoJdbcImpl] else cache wont get cleared.
	 *
	 * @param tenantId
	 * @param userName
	 * @return
	 */
	@TriggersRemove(cacheName = { "userDataCache" }, keyGenerator = @KeyGenerator(name = "StringCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateMember(int tenantId, String userName) {
		return 0;
	}

    @Cacheable(cacheName="memberRolesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public List<MemberRoles> getMemberRoles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName NOT IN ('Super', 'Legacy')"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(MemberRoles.class));
    }

    @Cacheable(cacheName="allMemberRolesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public List<MemberRoles> getAllMemberRoles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(MemberRoles.class));
    }

    public Long getCountMemberRoles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName NOT IN ('Super', 'Legacy')"
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

    @Cacheable(cacheName="samlMemberRolesCache", keyGeneratorName="HashCodeCacheKeyGenerator")
    public List<MemberRoles> getSamlMemberRoles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName IN ('Normal', 'Executive')"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(MemberRoles.class));
    }

    public Long getCountSamlMemberRoles() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE " +
            "  mr.roleName IN ('Normal', 'Executive')"
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

    public MemberRoles getMemberRoleByName(int tenant, String roleName) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  mr.roleID," +
            "  mr.roleName," +
            "  mr.roleDescription" +
            " FROM" +
            "  MemberRole mr" +
            " WHERE" +
            "  mr.roleName = ?" +
            " AND" +
            "  mr.roleName NOT IN ('Super', 'Legacy')"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(MemberRoles.class), roleName);
    }

    public boolean isMemberExistForUserName(int tenant, String userName, int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND" +
            "  m.username = :username"
        );

        if (memberID != 0) {
            sqlstm.append(" AND m.memberID <> ").append(String.valueOf(memberID));
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tenantID", String.valueOf(tenant));
        params.put("username", userName);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isMemberActive(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.memberID = :memberID" +
            " AND" +
            "  m.active = 1"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("memberID", String.valueOf(memberID));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public boolean isMemberAllowedToParticipate(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.memberID = :memberID" +
            " AND" +
            "  m.allowedToParticipate = 1"
        );

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("memberID", String.valueOf(memberID));

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);

        int num = namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParameters, Integer.class);
        return num > 0;
    }

    public List<SpeedDial> getSpeedDial(int tenant, int memberID, SpeedDialFilter filter) {
        List<SpeedDial> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  CASE WHEN rt.roomTypeID = 1 OR rt.roomTypeID = 3 THEN sdm.memberName ELSE r.roomName END as name," +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  rt.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomName," +
            "  r.roomExtNumber," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = 0 THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType='C') = 0 THEN 0 ELSE 1 END as roomStatus," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            "  sdm.memberID" +
            " FROM" +
            "  SpeedDial sd" +
            " INNER JOIN Member m ON (sd.memberID=m.memberID)" +
            " INNER JOIN Room r ON (sd.roomID=r.roomID)" +
            " INNER JOIN Member sdm ON (sdm.memberID=r.memberID AND sdm.active=1 AND sdm.allowedToParticipate=1)" +
            " INNER JOIN Tenant t ON (t.tenantID=sdm.tenantID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " LEFT JOIN Endpoints e ON (e.memberID=sdm.memberID AND e.memberType='R')" +
            " WHERE" +
            "  m.tenantID = :tenantId" +
            " AND" +
            "  sd.memberID = :memberId"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", memberID);
        if (filter != null) {
            String memberName = filter.getNameext();
            sqlstm.append(" AND (CASE WHEN rt.roomTypeID = :roomTypeID1 OR rt.roomTypeID = :roomTypeID2");
            namedParamsMap.put("roomTypeID1", 1);
            namedParamsMap.put("roomTypeID2", 3);
            sqlstm.append(" THEN (sdm.memberName LIKE :memberName");
            namedParamsMap.put("memberName", memberName+"%");
            StringTokenizer tokenizer = new StringTokenizer(memberName, " ");
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                String name = (String)tokenizer.nextElement();
                if (i == 0) {
                    sqlstm.append(" OR sdm.memberName LIKE :name0");
                    namedParamsMap.put("name0", name+"%");
                } else {
                	String namedParam = "name"+i;
                    sqlstm.append(" OR sdm.memberName LIKE :").append(namedParam);
                    namedParamsMap.put(namedParam, "%"+name+"%");
                }
                i++;
            }
            if (i == 1) {
                sqlstm.append(" OR sdm.memberName LIKE :name11");
                namedParamsMap.put("name11", "% "+memberName+"%");
            }
            sqlstm.append(")");

            sqlstm.append(" ELSE r.roomName LIKE :ext");
            namedParamsMap.put("ext", filter.getNameext()+"%");
            sqlstm.append(" AND r.roomEnabled = :roomEnabled");
            namedParamsMap.put("roomEnabled", 1);
            sqlstm.append(" END OR r.roomExtNumber LIKE :ext )");
            sqlstm.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());

        }
        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(SpeedDial.class));
        return rc;
    }

    public Long getCountSpeedDial(int tenant, int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(DISTINCT sdm.memberID)" +
            " FROM" +
            "  SpeedDial sd" +
            " INNER JOIN Member m ON (sd.memberID=m.memberID)" +
            " INNER JOIN Room r ON (sd.roomID=r.roomID)" +
            " INNER JOIN Member sdm ON (sdm.memberID=r.memberID AND sdm.active=1 AND sdm.allowedToParticipate=1)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " WHERE" +
            "  sdm.memberID = ? " +
            " OR" +
            "  m.tenantID = ?" +
            " AND" +
            "  sd.memberID = ?"
        );

        List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            },
            memberID,
		    tenant,
		    memberID
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public int addSpeedDialEntry(int memberID, int roomID) {
        logger.debug("Add record into SpeedDial for memberID = " + memberID + " and roomID = " + roomID);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
            .withTableName("SpeedDial")
            .usingGeneratedKeyColumns("speedDialID");

        HashMap<String, Integer> params = new HashMap<String, Integer>();
        params.put("memberID", memberID);
        params.put("roomID", roomID);

        SqlParameterSource namedParameters = new MapSqlParameterSource(params);
        Number newID = insert.executeAndReturnKey(namedParameters);

        logger.debug("New speedDialID = " + newID.intValue());

        return newID.intValue();
    }

    public int removeSpeedDialEntry(int memberID, int roomID) {
        logger.debug("Remove record from SpeedDial for memberID = " + memberID + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  SpeedDial" +
            " WHERE" +
            "  memberID = ?" +
            " AND" +
            "  roomID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), memberID, roomID);
        logger.debug("Rows affected: " + affected);

        return roomID;
    }

    public boolean isInSpeedDialEntry(int memberID, int roomID) {
        logger.debug("Check if record exist SpeedDial for memberID = " + memberID + " and roomID = " + roomID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0)" +
            " FROM" +
            "  SpeedDial" +
            " WHERE" +
            "  memberID = ?" +
            " AND" +
            "  roomID = ?"
        );

        int num = getJdbcTemplate().queryForObject(sqlstm.toString(), Integer.class, memberID, roomID);
        return num > 0;
    }

    public int updateMemberPassword(int tenant, Member member) {
        logger.debug("Update record in Member for tenant = " + tenant + " and memberID = " + member.getMemberID());

        member.setTenantID(tenant);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.password = :password" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public int updateMemberLanguage(int tenant, Member member) {
        logger.debug("Update record in Member for tenant = " + tenant + " and memberID = " + member.getMemberID());

        member.setTenantID(tenant);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.langID = :langID" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return member.getMemberID();
    }

    public int updateMemberDefaultEndpointGUID(int tenant, Member member) {
        logger.debug("Update record in Member for tenant = " + tenant + " and memberID = " + member.getMemberID());

        member.setTenantID(tenant);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.defaultEndpointGUID = :defaultEndpointGUID" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return member.getMemberID();
    }

    public int updateLegacy(int tenant, int memberID, Member member) {
        logger.debug("Update record in Member for tenant = " + tenant + " and memberID = " + memberID);

        member.setTenantID(tenant);
        member.setMemberID(memberID);

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(member);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.username = :username," +
            "  m.memberName = :username" +
            " WHERE" +
            "  m.tenantID = :tenantID" +
            " AND " +
            "  m.memberID = :memberID"
        );

        int affected1 = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected for Member: " + affected1);

        sqlstm = new StringBuffer(
            " UPDATE" +
            "  Room r" +
            " SET" +
            "  r.roomExtNumber = :roomExtNumber" +
            " WHERE" +
            "  r.memberID = :memberID"
        );

        int affected2 = namedParamJdbcTemplate.update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected for Room: " + affected2);

        return affected1 + affected2;
    }

    public int updateMemberMode(int memberID, int modeID) {
        logger.debug("Update record in Member for memberID = " + memberID + " set modeID = " + modeID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.modeID = ?" +
            " WHERE" +
            "  m.memberID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), modeID, memberID);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    /*public List<Entity> getContactsOld(int tenant, int memberID, EntityFilter filter, List<Integer> canCallTenantIds) {
    	logger.debug("Entering getContacts() of MemberDaoJdbcImpl");
        List<Entity> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT DISTINCT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  sdm.memberID," +
            "  sdm.username," +
			"  m.emailAddress, " +
            "  sdmd.modeID," +
            "  sdmd.modeName," +
            "  r.roomName," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN sdm.memberName ELSE r.displayName END as name," +
            "  r.roomExtNumber as ext," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN sdm.description ELSE r.roomDescription END as description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType= :confType) = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType= :confType) = :zero THEN 0 ELSE 1 END as roomStatus," +
            "  CASE WHEN lg.langName <> :sysLang THEN lg.langCode " +
            "	ELSE (SELECT c.configurationValue FROM Configuration c WHERE c.configurationName= :systemLang AND c.tenantID = :tenantId) END as langCode," +
            "  lg.langID, "+
            "  lg.langName," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video," +
            "  m.phone1 as phone1," +
            "  m.phone2 as phone2," +
            "  m.phone3 as phone3," +
            "  m.department as department," +
            "  m.title as title," +
            "  m.instantMessagerID as instantMessagerID," +
            "  m.thumbnailUpdateTime" +
            " FROM" +
            "  SpeedDial sd" +
            //" INNER JOIN Member m ON (sd.memberID=m.memberID)" +
            " INNER JOIN Room r ON (sd.roomID=r.roomID)" +
            " INNER JOIN Member m ON (r.memberID=m.memberID)" +
            " INNER JOIN Groups g ON (g.groupID=r.groupID)" +
            " INNER JOIN Member sdm ON (sdm.memberID=r.memberID AND sdm.active=:active AND sdm.allowedToParticipate=:allow)" +
            " INNER JOIN MemberMode sdmd ON (sdmd.modeID=sdm.modeID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID"
		);

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", memberID);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("zero", 0);
        namedParamsMap.put("sysLang", "System Language");
        namedParamsMap.put("systemLang", "SystemLanguage");
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");

		if (filter != null) {
			if (filter.getEntityType().equalsIgnoreCase("All")) {
				sqlstm.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
			} else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
				sqlstm.append(" AND (r.roomTypeID = :personal))");
			} else if (filter.getEntityType().equalsIgnoreCase("Public")) {
				sqlstm.append(" AND (r.roomTypeID = :public))");
			} else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
				sqlstm.append(" AND (r.roomTypeID = :legacy))");
			}
		} else {
			sqlstm.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
		}

        sqlstm.append(" LEFT JOIN Endpoints e ON (e.memberID=sdm.memberID AND e.memberType= :memberType)" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID)" +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=sdm.tenantID)" +
            " WHERE" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)" +
            " AND" +
            "  sd.memberID = :memberId"
        );

        sqlstm.append(" AND r.roomId NOT IN (").append(IRoomDao.INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", memberID);

        if (filter != null) {
			String memberName = filter.getQuery().trim().replaceAll("\\b\\s{2,}\\b", " ").replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			if (!memberName.contains(" ")) {
				sqlstm.append(" AND (sdm.memberName LIKE :memberName");
				namedParamsMap.put("memberName", memberName+"%");
			} else {
				memberName = memberName.replace(" ", ".*\\\\ ") ;
				sqlstm.append(" AND (sdm.memberName REGEXP '^").append(memberName).append("'");
			}

			sqlstm.append(" OR r.roomName LIKE :roomName");
			namedParamsMap.put("roomName", filter.getQuery().replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_")+"%");
			sqlstm.append(" OR r.roomExtNumber LIKE :roomExtNumber)");
			namedParamsMap.put("roomExtNumber", filter.getQuery().replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_")+"%");

            if (!filter.getSortBy().equalsIgnoreCase("")) {
                sqlstm.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
            }
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }
        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
        logger.debug("Exiting getContacts() of MemberDaoJdbcImpl");
        return rc;
    }*/
    
    public List<Entity> getContacts(int tenant, int memberID, EntityFilter filter, List<Integer> canCallTenantIds) {
    	logger.debug("Entering getContacts() of MemberDaoJdbcImpl");
        List<Entity> rc;
        StringBuffer sqlstm = new StringBuffer(
            " SELECT DISTINCT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  m.memberID," +
            "  m.username," +
			"  m.emailAddress, " +
            "  r.roomName," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.displayName END as name," +
            "  r.roomExtNumber as ext," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.description ELSE r.roomDescription END as description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = :memberId THEN 1 ELSE 0 END as roomOwner," +
            "  count(c.conferenceID) as occupantsCount, " +
            "  CASE WHEN count(c.conferenceID) = g.roomMaxUsers THEN 2" +
            "       WHEN count(c.conferenceID) = :zero THEN 0 ELSE 1 END as roomStatus," +            
            "  CASE WHEN lg.langName <> :sysLang THEN lg.langCode " +
            "  ELSE (SELECT c.configurationValue FROM Configuration c WHERE c.configurationName= :systemLang AND c.tenantID = :tenantId) END as langCode," +
            "  lg.langID, "+
            "  lg.langName," +
            "  m.tenantID," +   
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video," +
            "  m.phone1 as phone1," +
            "  m.phone2 as phone2," +
            "  m.phone3 as phone3," +
            "  m.department as department," +
            "  m.title as title," +
            "  m.instantMessagerID as instantMessagerID," +
            "  m.thumbnailUpdateTime" +
            " FROM" +
            " SpeedDial sd" +
            " INNER JOIN Room r ON sd.roomID = r.roomID" +
            " INNER JOIN Member m ON r.memberID = m.memberID AND m.active =:active AND m.allowedToParticipate =:allow" +
            " INNER JOIN Groups g ON g.groupID = r.groupID" +
            " INNER JOIN RoomType rt ON r.roomTypeID = rt.roomTypeID"
		);

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantId", tenant);
        namedParamsMap.put("memberId", memberID);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("scheduled", 4);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("zero", 0);
        namedParamsMap.put("sysLang", "System Language");
        namedParamsMap.put("systemLang", "SystemLanguage");
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        namedParamsMap.put("canCallToTenantIds", canCallTenantIds);

		if (filter != null) {
			if (filter.getEntityType().equalsIgnoreCase("All")) {
				sqlstm.append(" AND r.roomTypeID != :scheduled ");
			} else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
				sqlstm.append(" AND r.roomTypeID = :personal ");
			} else if (filter.getEntityType().equalsIgnoreCase("Public")) {
				sqlstm.append(" AND r.roomTypeID = :public ");
			} else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
				sqlstm.append(" AND r.roomTypeID = :legacy ");
			}
		} else {
			sqlstm.append(" AND r.roomTypeID != :scheduled ");
		}

        sqlstm.append(" LEFT JOIN Conferences c ON r.roomID = c.roomID " +
            " LEFT JOIN Endpoints e ON e.memberID = m.memberID AND e.memberType= :memberType" +
            " INNER JOIN Language lg ON (m.langID = lg.langID)" +
            " WHERE sd.memberID = :memberId" +
            " AND m.tenantID in (:canCallToTenantIds)"
        );
		if (filter != null && StringUtils.isNotBlank(filter.getQuery())) {
			sqlstm.append(" AND ( MATCH(m.memberName) AGAINST(:memberName in boolean mode)");
			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getQuery())); 
            // OR clause to maintain backward compatibility
			sqlstm.append(" OR MATCH(r.roomName,r.roomExtNumber,r.displayName) AGAINST(:roomNameOrExt in boolean mode))");
            namedParamsMap.put("roomNameOrExt", RoomUtils.buildSearchQuery(filter.getQuery()));
		}

        sqlstm.append(" AND r.roomId NOT IN (").append(IRoomDao.INACCESSIBLE_ROOMS_FOR_USER).append(")");
        sqlstm.append(" group by r.roomID");
        
        if (filter != null && StringUtils.isNotBlank(filter.getSortBy())) {
            sqlstm.append(" ORDER BY ").append(filter.getSortBy()).append(" ").append(filter.getDir());
        }
        
        if(filter != null) {
            sqlstm.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
            namedParamsMap.put("userId", memberID);
        }
        
        rc = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
        logger.debug("Exiting getContacts() of MemberDaoJdbcImpl");
        return rc;
    }

    public Long getCountContactsOld(int tenant, int memberID, EntityFilter filter) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(DISTINCT r.roomID)" +
            " FROM" +
            "  SpeedDial sd" +
            " INNER JOIN Member m ON (sd.memberID=m.memberID)" +
            " INNER JOIN Room r ON (sd.roomID=r.roomID)" +
            " INNER JOIN Member sdm ON (sdm.memberID=r.memberID AND sdm.active=:active AND sdm.allowedToParticipate=:allow)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID"
		);

		if (filter != null) {
			if (filter.getEntityType().equalsIgnoreCase("All")) {
				sqlstm.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
			} else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
				sqlstm.append(" AND (r.roomTypeID = :personal))");
			} else if (filter.getEntityType().equalsIgnoreCase("Public")) {
				sqlstm.append(" AND (r.roomTypeID = :public))");
			} else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
				sqlstm.append(" AND (r.roomTypeID = :legacy))");
			}
		} else {
			sqlstm.append(" AND (r.roomTypeID = :personal OR r.roomTypeID = :public OR r.roomTypeID = :legacy))");
		}

        sqlstm.append(
	        " INNER JOIN Tenant mt ON (mt.tenantID=sdm.tenantID)" +
            " WHERE" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantId)" +
            " AND" +
            "  sd.memberID = :memberId"
        );

		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantId", tenant);
		namedParamsMap.put("memberId", memberID);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);

		if (filter != null) {
			String memberName = filter.getQuery().trim().replaceAll("\\b\\s{2,}\\b", " ").replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			if (!memberName.contains(" ")) {
				sqlstm.append(" AND (sdm.memberName LIKE :memberName");
				namedParamsMap.put("memberName", memberName+"%");
			} else {
				memberName = memberName.replace(" ", ".*\\\\ ") ;
				sqlstm.append(" AND (sdm.memberName REGEXP '^").append(memberName).append("'");
			}

			sqlstm.append(" OR r.roomName LIKE :roomName");
			namedParamsMap.put("roomName", filter.getQuery().replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_")+"%");
			sqlstm.append(" OR r.roomExtNumber LIKE :roomExtNumber)");
			namedParamsMap.put("roomExtNumber", filter.getQuery().replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_")+"%");
		}

        sqlstm.append(" AND r.roomId NOT IN (").append(IRoomDao.INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", memberID);

        List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
            new RowMapper<Long>() {
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong(1);
                }
            }
        );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }
    
    public static final String SEARCH_MY_CONTACTS_COUNT = "SELECT COUNT(DISTINCT r.roomID) FROM SpeedDial sd INNER JOIN Room r ON sd.roomID = r.roomID INNER JOIN RoomType rt ON rt.roomTypeID = r.roomTypeID ";
    
    public int getCountContacts(int tenant, int memberID, EntityFilter filter, List<Integer> canCallTenantIds) {

    	StringBuilder query = new StringBuilder(SEARCH_MY_CONTACTS_COUNT);
    	
		if (filter != null) {
			if (filter.getEntityType().equalsIgnoreCase("All")) {
				query.append(" AND r.roomTypeID != :scheduled");
			} else if (filter.getEntityType().equalsIgnoreCase("Personal")) {
				query.append(" AND r.roomTypeID = :personal");
			} else if (filter.getEntityType().equalsIgnoreCase("Public")) {
				query.append(" AND r.roomTypeID = :public");
			} else if (filter.getEntityType().equalsIgnoreCase("Legacy")) {
				query.append(" AND r.roomTypeID = :legacy");
			}
		} else {
			query.append(" AND r.roomTypeID != :scheduled");
		}

		query.append(
				" INNER JOIN Member m ON r.memberID = m.memberID AND sd.memberID = :memberId and m.tenantID in (:canCallTenantIds)"
        );

		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantId", tenant);
		namedParamsMap.put("memberId", memberID);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("scheduled", 4);
        namedParamsMap.put("canCallTenantIds", canCallTenantIds);
		if (filter != null && StringUtils.isNotBlank(filter.getQuery())) {
			query.append(" AND ( MATCH(m.memberName) AGAINST(:memberName in boolean mode)");
			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getQuery())); 
            // OR clause to maintain backward compatibility
			query.append(" OR MATCH(r.roomName,r.roomExtNumber,r.displayName) AGAINST(:roomNameOrExt in boolean mode))");
            namedParamsMap.put("roomNameOrExt", RoomUtils.buildSearchQuery(filter.getQuery()));
		}

		query.append(" AND r.roomId NOT IN (").append(IRoomDao.INACCESSIBLE_ROOMS_FOR_USER).append(")");
        namedParamsMap.put("userId", memberID);

        List<Integer> countList = namedParamJdbcTemplate.query(query.toString(), namedParamsMap,
            new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            }
        );

        return countList.size() > 0 ? countList.get(0) : 0;
    }

    public Entity getContact(int tenant, int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT DISTINCT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointGUID END as endpointGUID," +
            "  m.memberID," +
            "  m.username," +
            "  m.emailAddress," +
            "  md.modeID," +
            "  md.modeName," +
            "  r.roomName," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  m.description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = :memberID THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType) = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType) = :zero THEN 0 ELSE 1 END as roomStatus," +
            "  CASE WHEN lg.langName <> :sysLang THEN lg.langCode " +
            "	ELSE (SELECT c.configurationValue FROM Configuration c WHERE c.configurationName=:systemLang AND c.tenantID = :tenantID) END as langCode," +
            "  lg.langID," +
            "  lg.langName," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:memberType)" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = :memberID)" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) " +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN MemberMode md ON (md.modeID=m.modeID)" +
            " WHERE" +
            "  rt.roomTypeID IN (:personal, :legacy)" +
            " AND" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantID)" +
            " AND" +
            "  m.memberID = :memberID" +
            " AND" +
            "  m.active = :active" +
            " AND" +
            "  m.allowedToParticipate = :allow"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("zero", 0);
		namedParamsMap.put("tenantID", tenant);
		namedParamsMap.put("memberID", memberID);
        namedParamsMap.put("sysLang", "System Language");
        namedParamsMap.put("systemLang", "SystemLanguage");
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        List<Entity> entities = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
       //VPTL-8200 - Return the first row if there is more than one matching row in Endpoints table
        if(entities != null && !entities.isEmpty()) {
        	return entities.get(0);
        }
        //VPTL-8200 - Return null if no entity matching the memberId.
        return null;
        // Returning null is similar to the commented out code below which would also return null if no match
        //return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
    }

    public Entity getContactNew(int tenant, int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT DISTINCT" +
            "  r.roomID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID END as endpointID," +
            "  CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointGUID END as endpointGUID," +
            "  m.memberID," +
            "  m.username," +
            "  md.modeID," +
            "  md.modeName," +
            "  r.roomName," +
            "  CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy THEN m.memberName ELSE r.roomName END as name," +
            "  r.roomExtNumber as ext," +
            "  m.description," +
            "  CASE WHEN r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned," +
            "  r.roomPIN," +
            "  CASE WHEN r.roomModeratorPIN IS NULL THEN 0 ELSE 1 END as roomModeratorPinned," +
            "  r.roomModeratorPIN," +
            "  r.roomKey," +
            "  r.roomLocked," +
            "  r.roomEnabled," +
            "  r.roomTypeID," +
            "  rt.roomType," +
            "  CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized THEN 0 ELSE e.status END END as memberStatus," +
            "  CASE WHEN sd.speedDialID IS NULL THEN 0 ELSE sd.speedDialID END as speedDialID," +
            "  CASE WHEN r.memberID = :memberID THEN 1 ELSE 0 END as roomOwner," +
            "  CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType) = g.roomMaxUsers THEN 2" +
            "       WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND c.conferenceType=:confType) = :zero THEN 0 ELSE 1 END as roomStatus," +
            "  CASE WHEN lg.langName <> :sysLang THEN lg.langCode " +
            "	ELSE (SELECT c.configurationValue FROM Configuration c WHERE c.configurationName=:systemLang AND c.tenantID = :tenantID) END as langCode," +
            "  lg.langName," +
            "  mt.tenantID," +
            "  mt.tenantName," +
            "  mt.tenantUrl," +
            "  CASE WHEN mt.tenantDialIn IS NULL THEN '' ELSE mt.tenantDialIn END as dialIn," +
            "  g.roomMaxUsers," +
            "  g.userMaxBandWidthIn," +
            "  g.userMaxBandWidthOut," +
            "  g.allowRecording," +
            "  CASE WHEN c.audio IS NULL THEN 0 ELSE c.audio END as audio," +
            "  CASE WHEN c.video IS NULL THEN 0 ELSE c.video END as video" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:memberType)" +
            " LEFT JOIN SpeedDial sd ON (r.roomID=sd.roomID AND sd.memberID = :memberID)" +
            " LEFT JOIN Conferences c ON (c.endpointID=e.endpointID) " +
            " INNER JOIN Language lg ON (m.langID=lg.langID)" +
            " INNER JOIN Tenant mt ON (mt.tenantID=m.tenantID)" +
            " INNER JOIN MemberMode md ON (md.modeID=m.modeID)" +
            " WHERE" +
            "  rt.roomTypeID IN (:personal, :legacy)" +
            " AND" +
            "  m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE x.tenantID = :tenantID)" +
            " AND" +
            "  m.memberID = :memberID" +
            " AND" +
            "  m.active = :active" +
            " AND" +
            "  m.allowedToParticipate = :allow"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("zero", 0);
		namedParamsMap.put("tenantID", tenant);
		namedParamsMap.put("memberID", memberID);
        namedParamsMap.put("sysLang", "System Language");
        namedParamsMap.put("systemLang", "SystemLanguage");
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        return namedParamJdbcTemplate.queryForObject(sqlstm.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
    }


	/*
	* Get the conference room ID, member has joined  from Conferences table
	* Conferences table does have MemberID so make a INNER JOIN with Endpoints table on endpointID
	*/
	public int getJoinedConferenceRoomId(int memberID) {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  c.roomID" +
			" FROM" +
			"  Conferences c " +
			" INNER JOIN Endpoints e on c.endpointID = e.endpointID" +
			" WHERE" +
			"  c.conferenceType = 'C' AND e.memberID = ?"
		);

		List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
			new RowMapper<Long>() {
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		            return rs.getLong(1);
				}
			},
			memberID
		);

		Long lngRoomID = ll.size() > 0 ? ll.get(0) : 0l;
		return lngRoomID.intValue();
	}

	/*
	* Get the conference room ID, and Conference Type member has joined  from Conferences table
	* Conferences table does have MemberID so make a INNER JOIN with Endpoints table on endpointID
	*/
	public RoomIdConferenceType getJoinedConferenceRoomIdConferenceType(int memberID) {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  c.roomID," +
			"  c.conferenceType" +
			" FROM" +
			"  Conferences c" +
			" INNER JOIN Endpoints e on c.endpointID = e.endpointID" +
			" WHERE" +
			"  e.memberID = ?"
		);

		return getJdbcTemplate().queryForObject(sqlstm.toString(),
			BeanPropertyRowMapper.newInstance(RoomIdConferenceType.class), memberID);
	}

    public Guest getGuest(int tenant, int guestID) {
        logger.debug("Get record from Guests for tenant = " + tenant + " and guestID = " + guestID);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  g.guestID," +
            "  g.guestName," +
            "  e.endpointGUID" +
            " FROM" +
            "  Guests g" +
            " LEFT JOIN Endpoints e ON (g.guestID=e.memberID AND e.memberType='G')" +
            " WHERE" +
            "  g.tenantID = ?" +
            " AND" +
            "  g.guestID = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Guest.class), tenant, guestID);
    }

    public List<InviteList> getOnlineMembers(int tenant, InviteListFilter filter) {
        List<InviteList> rc;
        StringBuffer sqlstm_member = new StringBuffer(
                " SELECT" +
                "  m.memberID," +
                "  e.endpointID," +
                "  m.memberName as name," +
                "  r.roomExtNumber as ext," +
                "  t.tenantID," +
                "  t.tenantName" +
                " FROM" +
                "  Endpoints e" +
                " INNER JOIN Member m ON (e.memberID=m.memberID AND m.active=:active AND m.allowedToParticipate=:allow)" +
                " INNER JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=:personal)" +
                " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
                " WHERE" +
                "  e.status = :status" +
                " AND" +
                "  m.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = :tenantId)"
            );

            StringBuffer sqlstm_legacy = new StringBuffer(
                " SELECT" +
                "  m.memberID," +
                "  0 as endpointID," +
                "  m.memberName as name," +
                "  r.roomExtNumber as ext," +
                "  t.tenantID," +
                "  t.tenantName" +
                " FROM" +
                "  Member m" +
                " INNER JOIN Room r ON (r.memberID=m.memberID AND r.roomTypeID=:legacy)" +
                " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
                " WHERE " +
                "  m.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = :tenantId)" +
                " AND" +
                "  m.active = 1" +
                " AND" +
                "  m.allowedToParticipate = 1"
            );

            Map<String, Object> namedParamsMap = new HashMap<String, Object>();
            namedParamsMap.put("tenantId", tenant);
            namedParamsMap.put("active", 1);
            namedParamsMap.put("allow", 1);
            namedParamsMap.put("status", 1);
            namedParamsMap.put("personal", 1);
            namedParamsMap.put("legacy", 3);

        if (filter != null) {
            sqlstm_member.append(" AND (m.memberName LIKE :ext");
            sqlstm_member.append(" OR r.roomName LIKE :ext");
            sqlstm_member.append(" OR r.roomExtNumber LIKE :ext)");
            sqlstm_legacy.append(" AND (m.memberName LIKE :ext");
            sqlstm_legacy.append(" OR r.roomName LIKE :ext");
            sqlstm_legacy.append(" OR r.roomExtNumber LIKE :ext)");
            namedParamsMap.put("ext", filter.getNameext()+"%");
        }

        sqlstm_member.append(" UNION ").append(sqlstm_legacy);

        if (filter != null) {
            sqlstm_member.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir());
            sqlstm_member.append(" LIMIT :start, :limit");
            namedParamsMap.put("start", filter.getStart());
            namedParamsMap.put("limit", filter.getLimit());
        }
        rc = namedParamJdbcTemplate.query(sqlstm_member.toString(), namedParamsMap, BeanPropertyRowMapper.newInstance(InviteList.class));
        return rc;
    }

    public Long getCountOnlineMembers(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  COUNT(0)" +
                " FROM" +
                "  Endpoints e" +
                " INNER JOIN Member m ON (e.memberID=m.memberID AND m.active=:active AND m.allowedToParticipate=:allow)" +
                " WHERE" +
                " e.status = :status" +
                " AND" +
                "  m.tenantID IN (SELECT callTo FROM TenantXcall WHERE tenantID = :tenantId)"
            );

            Map<String, Object> namedParamsMap = new HashMap<String, Object>();
            namedParamsMap.put("tenantId", tenant);
            namedParamsMap.put("active", 1);
            namedParamsMap.put("allow", 1);
            namedParamsMap.put("status", 1);
            List<Long> ll = namedParamJdbcTemplate.query(sqlstm.toString(), namedParamsMap,
                new RowMapper<Long>() {
                    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getLong(1);
                    }
                }
            );

        return ll.size() > 0 ? ll.get(0) : 0l;
    }

    public List<Location> getLocationTags(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  locationID," +
            "  locationTag" +
            " FROM" +
            "  Locations" +
            " WHERE" +
            "  locationID IN (SELECT locationID FROM TenantXlocation WHERE tenantID = ?)"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Location.class), tenant);
    }

    public Long getCountLocationTags(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(0) " +
            " FROM" +
            "  Locations" +
            " WHERE" +
            "  locationID IN (SELECT locationID FROM TenantXlocation WHERE tenantID = ?)"
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

     public Location getLocationInTenantByTag(int tenant, String locationTag) {
         StringBuffer sqlstm = new StringBuffer(
             " SELECT "+
             "   l1.* "+
             " FROM "+
             "     Locations as l1 "+
             "   INNER JOIN "+
             "     TenantXlocation as t1 "+
             "   ON "+
             "     l1.locationID = t1.locationID "+
             " WHERE "+
             "  t1.tenantID = ? "+
             " AND "+
             "  l1.locationTag = ?"
         );

         return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Location.class), tenant, locationTag);

     }

    public Long getCountExecutives(int tenant) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(memberID)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.roleID in (7)"+
            " AND"+
            "  m.active = 1"+
            " AND"+
            "  m.allowedToParticipate = 1"+
            " AND"+
            "  m.tenantID = ?"
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

    public Long getCountAllExecutives() {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  COUNT(memberID)" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.roleID in (7)"+
            " AND"+
            "  m.active = 1"+
            " AND"+
            "  m.allowedToParticipate = 1"
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

	public Long getCountPanoramas(int tenant) {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  COUNT(memberID)" +
			" FROM" +
			"  Member m" +
			" WHERE" +
			"  m.roleID in (8)"+
			" AND"+
			"  m.active = 1"+
			" AND"+
			"  m.allowedToParticipate = 1"+
			" AND"+
			"  m.tenantID = ?"
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

	public Long getCountAllPanoramas() {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  COUNT(memberID)" +
			" FROM" +
			"  Member m" +
			" WHERE" +
			"  m.roleID in (8)"+
			" AND"+
			"  m.active = 1"+
			" AND"+
			"  m.allowedToParticipate = 1"
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

	public String getLocationTag(int locationTagID) {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT locationTag FROM Locations WHERE locationID = ?");
		List<Location> locations = getJdbcTemplate().query(
			sqlstm.toString(),
			BeanPropertyRowMapper.newInstance(Location.class),
			locationTagID);
		return locations.size() == 1 ? locations.get(0).getLocationTag() : null;
	}

	private static final String GET_INVITEE_DETAILS = "SELECT CASE WHEN e.endpointID IS NULL THEN 0 ELSE "
			+ "e.endpointID END as endpointID, e.endpointGUID, e.extData, m.memberID, m.memberName, mr.roleName, r.roomID, r.roomExtNumber, m.tenantID FROM Member m "
			+ "LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:regular AND e.status!=:offline), "
			+ "Room r, MemberRole mr, Tenant t WHERE r.roomID =:inviteeRoomId AND "
			+ "r.memberID = m.memberID AND m.active =:active AND "
			+ "m.allowedToParticipate =:allowed AND m.roleID = mr.roleID AND "
			+ "m.tenantID = t.tenantID AND t.tenantID IN "
			+ "(SELECT x.callTo FROM TenantXcall x WHERE x.tenantID =:tenantId)";

	/**
	 * Returns MemberId and EndpointId of the Invited User
	 *
	 * @param inviteeRoomId
	 * @param tenantId
	 * @return
	 */
	public Member getInviteeDetails(int inviteeRoomId, int tenantId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviteeRoomId", inviteeRoomId);
		params.put("active", 1);
		params.put("allowed", 1);
		params.put("regular", "R");
		params.put("tenantId", tenantId);
		params.put("offline", 0);
		List<Member> members = namedParamJdbcTemplate.query(
				GET_INVITEE_DETAILS,
				params,
				BeanPropertyRowMapper.newInstance(Member.class));
		if (members.isEmpty()) {
			return null;
		}

		return members.get(0);

	}

	private static final String GET_INVITER_DETAILS = "SELECT e.endpointGUID, m.memberID, m.memberName, r.roomID, r.roomName, r.roomExtNumber, m.tenantID, mr.roleName FROM Member m "
			+ "LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType=:regular AND e.status!=:offline), "
			+ "Room r, MemberRole mr, RoomType rt WHERE m.memberID =:inviterMemberId AND "
			+ "m.memberID = r.memberID AND m.active =:active AND "
			+ "m.allowedToParticipate =:allowed AND m.roleID = mr.roleID AND r.roomTypeID=rt.roomTypeID AND "
			+ "rt.roomTypeID IN (:personal, :legacy)";

	/**
	 * Returns MemberName and EndpointGUID of the Inviter
	 *
	 * @param inviterMemberId
	 * @param tenantId
	 * @return
	 */
	public Member getInviterDetails(int inviterMemberId, int tenantId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviterMemberId", inviterMemberId);
		params.put("active", 1);
		params.put("allowed", 1);
		params.put("regular", "R");
		params.put("personal", 1);
		params.put("legacy", 3);
		params.put("offline", 0);
		List<Member> members = namedParamJdbcTemplate.query(
				GET_INVITER_DETAILS,
				params,
				BeanPropertyRowMapper.newInstance(Member.class));
		if (members.isEmpty()) {
			return null;
		}

		return members.get(0);

	}

	private static final String GET_MEMBER_DETAILS =
			"SELECT m.memberName, m.tenantID, m.userName, mr.roleName, m.pak, m.pak2, g.userMaxBandWidthIn, g.userMaxBandWidthOut, l.langCode " +
			"FROM Member m, Language l, MemberRole mr, Room r, Groups g, Tenant t " +
			"WHERE m.memberID =:memberId AND m.roleID = mr.roleID AND m.tenantID = t.tenantID AND m.memberID=r.memberID AND " +
			"r.groupID=g.groupID AND t.tenantID = g.tenantID AND m.langID = l.langID AND r.roomTypeID IN (:personal, :legacy)";

	/**
	 *
	 * @param memberId
	 * @return
	 */
	public MemberEntity getMemberEntity(int memberId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("personal", 1);
		params.put("legacy", 3);
		List<MemberEntity> memberEntities = namedParamJdbcTemplate.query(
				GET_MEMBER_DETAILS,
				params,
				BeanPropertyRowMapper
						.newInstance(MemberEntity.class));
		if (memberEntities.isEmpty()) {
			return null;
		}
		return memberEntities.get(0);
	}

	private String GET_OWN_ROOM_DETAILS = "SELECT DISTINCT r.roomID, CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointID"
			+ " END as endpointID, CASE WHEN e.endpointID IS NULL THEN 0 ELSE e.endpointGUID END as endpointGUID, "
			+ "m.memberID, m.username, r.roomName, CASE WHEN rt.roomTypeID = :personal OR rt.roomTypeID = :legacy "
			+ "THEN m.memberName ELSE r.roomName END as name, r.roomExtNumber as ext, m.description, CASE WHEN "
			+ "r.roomPIN IS NULL THEN 0 ELSE 1 END as roomPinned, r.roomPIN, CASE WHEN r.roomModeratorPIN IS NULL "
			+ "THEN 0 ELSE 1 END as roomModeratorPinned, r.roomModeratorPIN, r.roomKey, r.roomLocked, r.roomEnabled, "
			+ "r.roomTypeID, rt.roomType, CASE WHEN e.status IS NULL THEN 0 ELSE CASE WHEN e.authorized = :authorized "
			+ "THEN 0 ELSE e.status END END as memberStatus, CASE WHEN r.memberID = :memberID THEN 1 ELSE 0 END as "
			+ "roomOwner, CASE WHEN (SELECT COUNT(0) FROM Conferences c WHERE c.roomID=r.roomID AND "
			+ "c.conferenceType=:confType) = g.roomMaxUsers THEN 2 WHEN (SELECT COUNT(0) FROM Conferences c WHERE "
			+ "c.roomID=r.roomID AND c.conferenceType=:confType) = :zero THEN 0 ELSE 1 END as roomStatus FROM Member m "
			+ "INNER JOIN MemberRole mr ON (m.roleID=mr.roleID) INNER JOIN Room r ON (m.memberID=r.memberID) INNER JOIN "
			+ "RoomType rt ON (r.roomTypeID=rt.roomTypeID) INNER JOIN Groups g ON (r.groupID=g.groupID) LEFT JOIN "
			+ "Endpoints e ON (m.memberID=e.memberID AND e.memberType=:memberType) WHERE rt.roomTypeID IN "
			+ "(:personal, :legacy) AND m.tenantID IN (SELECT x.callTo FROM TenantXcall x WHERE "
			+ "x.tenantID = :tenantID) AND m.memberID = :memberID AND m.active = :active AND "
			+ "m.allowedToParticipate = :allow ";

    public Entity getOwnRoomDetails(int tenant, int memberID) {

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("personal", 1);
        namedParamsMap.put("public", 2);
        namedParamsMap.put("legacy", 3);
        namedParamsMap.put("authorized", 0);
        namedParamsMap.put("confType", "C");
        namedParamsMap.put("zero", 0);
		namedParamsMap.put("tenantID", tenant);
		namedParamsMap.put("memberID", memberID);
        namedParamsMap.put("active", 1);
        namedParamsMap.put("allow", 1);
        namedParamsMap.put("memberType", "R");
        return namedParamJdbcTemplate.queryForObject(GET_OWN_ROOM_DETAILS,
            namedParamsMap, BeanPropertyRowMapper.newInstance(Entity.class));
    }

    /**
     * Returns the list of super accounts based on the filter
     * @param filter
     * @return
     */
	public List<Member> getSupers(MemberFilter filter) {
		List<Member> rc;
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  m.memberID," +
			"  m.username," +
			"  m.memberName," +
			"  m.emailAddress," +
			"  m.langID," +
			"  m.description," +
			"  m.memberCreated," +
			"  m.active" +
			" FROM" +
			"  Member m" +
			" WHERE" +
			"  m.roleID = 5"
		);

		if (filter != null) {
			String memberName = filter.getMemberName().trim().replaceAll("\\b\\s{2,}\\b", " ").replaceAll("\\\\", "\\\\\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_").replaceAll("'", "\\\\'");
			if (!memberName.contains(" ")) {
				sqlstm.append(" AND (m.memberName LIKE '").append(memberName).append("%' OR m.username LIKE '").append(memberName).append("%')");
			} else {
				memberName = memberName.replace(" ", ".*\\\\ ") ;
				sqlstm.append(" AND ((m.memberName REGEXP '^").append(memberName).append("' OR m.username REGEXP '^").append(memberName).append("')");
			}

        	if(filter.getUserStatus().equalsIgnoreCase("Enabled")) {
                sqlstm.append(" AND m.active = 1");
            } else if(filter.getUserStatus().equalsIgnoreCase("Disabled")) {
            	sqlstm.append(" AND m.active = 0");
            }

			if(filter.getSort() != null) {
				sqlstm.append(" ORDER BY "+ filter.getSort() + " " + filter.getDir());
			}

            sqlstm.append(" LIMIT " + filter.getStart() + "," + filter.getLimit());
		}

		rc = getJdbcTemplate().query(sqlstm.toString(),
			BeanPropertyRowMapper.newInstance(Member.class));
		return rc;
	}

	/**
	 * Returns the count of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public Long getCountSupers(MemberFilter filter) {
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  COUNT(0)" +
			" FROM" +
			"  Member m" +
			" WHERE" +
			"  m.roleID = 5"
		);

		if (filter != null) {
			String memberName = filter.getMemberName().trim().replaceAll("\\b\\s{2,}\\b", " ").replaceAll("\\\\", "\\\\\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_").replaceAll("'", "\\\\'");
			if (!memberName.contains(" ")) {
				sqlstm.append(" AND (m.memberName LIKE '").append(memberName).append("%' OR m.username LIKE '").append(memberName).append("%')");
			} else {
				memberName = memberName.replace(" ", ".*\\\\ ") ;
				sqlstm.append(" AND (m.memberName REGEXP '^").append(memberName).append("' OR m.username REGEXP '^").append(memberName).append("')");
			}

        	if(filter.getUserStatus().equalsIgnoreCase("Enabled")) {
                sqlstm.append(" AND m.active = 1");
            } else if(filter.getUserStatus().equalsIgnoreCase("Disabled")) {
            	sqlstm.append(" AND m.active = 0");
            }
		}

		if(logger.isDebugEnabled()) {
			logger.debug("getCountSupers. SQL - " + sqlstm.toString());
		}

		List<Long> ll = getJdbcTemplate().query(sqlstm.toString(),
			new RowMapper<Long>() {
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			}
		);

		return ll.size() > 0 ? ll.get(0) : 0l;
	}

	/**
	 *
	 * @param memberID
	 * @return
	 */
    public Member getSuper(int memberID){
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  m.memberID," +
            "  m.username," +
            "  m.memberName," +
            "  m.emailAddress," +
            "  m.langID," +
            "  m.description," +
			"  m.memberCreated," +
			"  m.active, m.tenantID" +
            " FROM" +
            "  Member m" +
            " WHERE" +
            "  m.memberID = ?"
        );
        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Member.class), memberID);
    }

	/**
	 *
	 * @param member
	 * @return
	 */
	public int insertSuper(Member member) {
		logger.debug("Add record into Member for super = "
				+ member.getUsername());

		SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("Member").usingGeneratedKeyColumns("memberID");

		member.setTenantID(1);
		member.setRoleID(5);
		member.setMemberCreated((int) (new Date().getTime() * .001));
		member.setModeID(1);
		member.setImportedUsed(0);
        member.setLoginFailureCount(0);

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				member);
		Number newID = insert.executeAndReturnKey(parameters);

		logger.debug("New memberID = " + newID.intValue());

		return newID.intValue();
	}

	/**
	 *
	 * @param memberID
	 * @return
	 */
	public int deleteSuper(int memberID) {
		logger.debug("Remove record from Member for memberID = " + memberID);

		StringBuffer sqlstm = new StringBuffer(
			" DELETE" +
			" FROM" +
			"  Member" +
			" WHERE" +
			"  memberID = ?"
		);

		int affected = getJdbcTemplate().update(sqlstm.toString(), memberID);
		logger.debug("Rows affected: " + affected);

		return memberID;
	}

  //Not Used
	/*public Member getMemberByNameAndGatewayController(String username, String gatewayControllerDns) {
        logger.debug("Get record from Member for userName = " + username + " and gatewayControllerDns = " + gatewayControllerDns);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  m.memberID," +
            "  m.roleID," +
                "  mr.roleName," +
            "  m.tenantID," +
                "  g.groupID," +
                "  g.groupName," +
            "  m.langID," +
            "  m.profileID," +
            "  m.username," +
            "  m.password," +
            "  m.memberName," +
            "  m.active," +
            "  m.allowedToParticipate," +
            "  m.emailAddress," +
            "  m.memberCreated," +
            "  m.location," +
            "  m.description," +
                "  r.roomID," +
                "  rt.roomTypeID," +
                "  rt.roomType," +
                "  r.roomName," +
                "  r.roomExtNumber," +
            "  t.tenantPrefix," +
            "  t.tenantName," +
            "  CASE WHEN t.tenantDialIn IS NULL THEN '' ELSE t.tenantDialIn END as dialIn," +
            "  e.endpointGUID," +
            "  m.defaultEndpointGUID," +
            "  CASE WHEN m.proxyID IS NULL THEN 0 ELSE m.proxyID END as proxyID," +
            "  CASE WHEN m.proxyID IS NULL THEN '' WHEN m.proxyID = 0 THEN 'No Proxy' ELSE c.NAME END as proxyName," +
            "  CASE WHEN m.locationID IS NULL THEN 1 ELSE m.locationID END as locationID," +
            "  CASE WHEN m.locationID IS NULL THEN '' ELSE l.locationTag END as locationTag" +
            " FROM" +
            "  Member m" +
            " INNER JOIN MemberRole mr ON (m.roleID=mr.roleID)" +
            " INNER JOIN Room r ON (m.memberID=r.memberID)" +
            " INNER JOIN Groups g ON (r.groupID=g.groupID)" +
            " INNER JOIN RoomType rt ON (r.roomTypeID=rt.roomTypeID)" +
            " INNER JOIN Tenant t ON (t.tenantID=m.tenantID)" +
            " LEFT JOIN Endpoints e ON (m.memberID=e.memberID AND e.memberType='R')" +
            " LEFT JOIN components c ON (m.proxyID=c.ID)" +
            " LEFT JOIN Locations l ON (m.locationID=l.locationID)" +
            " WHERE" +
            "  rt.roomTypeID IN (1,3)" +
            " AND" +
            "  m.username = ?" +
            " AND" +
            "  t.vidyoGatewayControllerDns = ?"
        );

        return getJdbcTemplate().queryForObject(sqlstm.toString(),
            BeanPropertyRowMapper.newInstance(Member.class), username, gatewayControllerDns);
	}*/

      /**
     *
     * @param memberID
     * @return
     */
	public String getMemberPassword(int memberID) {
		String GET_MEMBER_PASSWORD = "SELECT password from Member where memberID = ?";
		String password = getJdbcTemplate().queryForObject(
				GET_MEMBER_PASSWORD, String.class, memberID);
		return password;
	}

	/**
	 *
	 */
	private static final String GET_MEMBER_DETAIL_BY_ID = "select tenantId from Member m where m.memberID = ? and m.tenantId = ?";

	/**
	 * Returns Member based on MemberId and TenantId
	 *
	 * @param memberId
	 * @param tenantId
	 * @return
	 */
	public Member getMemberDetail(int memberId, int tenantId) {
		List<Member> members = getJdbcTemplate().query(GET_MEMBER_DETAIL_BY_ID,
				BeanPropertyRowMapper.newInstance(Member.class), memberId, tenantId);
		return members.size() == 1 ? members.get(0) : null;
	}

	/**
	 * Returns Member from only Member table based on MemberId and TenantId
	 * @param memberId
	 * @param tenantId
	 * @return
	 */
	public Member getMemberObj(int memberId, int tenantId) {
		final String GET_MEMBER_OBJ = "select memberID, roleID, tenantID, username from Member m where m.memberID = ? and m.tenantId = ?";

		List<Member> members = getJdbcTemplate().query(GET_MEMBER_OBJ,
				BeanPropertyRowMapper.newInstance(Member.class), memberId, tenantId);
		return members.size() == 1 ? members.get(0) : null;
	}


     //************ LOGIN HISTORY ****************************
    /**
	 *
	 * @param memberID
	 * @return
	 */
	private static final String RESET_FAILURE_COUNT = "UPDATE Member set loginFailureCount =:count where memberID =:memberID";

	private static final String GET_FAILURE_COUNT = "SELECT loginFailureCount from Member where memberID =:memberID";

	@Override
	public int resetLoginFailureCount(int memberID) {
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberID", memberID);
		int loginFailureCount =  namedParamJdbcTemplate.queryForObject(GET_FAILURE_COUNT, namedParamsMap, Integer.class);
		int updatedCount = 0;
		if(loginFailureCount > 0) {
			namedParamsMap.put("count", 0);
			updatedCount = namedParamJdbcTemplate.update(RESET_FAILURE_COUNT,
					namedParamsMap);
		}
		return updatedCount;
	}

    /**
     *
     * @param memberID
     * @return
     */
	//@Override
	public int incrementLoginFailureCount(int memberID) {
		String RESET_FAILURE_COUNT = "UPDATE Member set loginFailureCount = loginFailureCount+1 where memberID = ?";
		int updatedCount = getJdbcTemplate().update(RESET_FAILURE_COUNT,
				memberID);
		return updatedCount;
	}

    /**
     *
     * @param userName
     * @param tenantID
     * @param
     * @return
     */
	public int getMemberID(String userName, int tenantID) {
		String GET_MEMBER_ID = "SELECT memberID from Member where username =:username AND tenantID =:tenantID";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantID", tenantID);

		namedParamsMap.put("username", userName);
		int memberID =  namedParamJdbcTemplate.queryForObject(GET_MEMBER_ID, namedParamsMap, Integer.class);
		return memberID;
	}

    /**
     *
     * @param userName
     * @param tenantID
     * @return
     */
	//@Override
	public int getMemberLoginFailureCount(String userName, int tenantID) {
		String GET_LOGIN_FAILURE_COUNT = "SELECT loginFailureCount from Member where tenantID =:tenantID and username =:username";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantID", tenantID);
		namedParamsMap.put("username", userName);
		int loginFailureCount =  namedParamJdbcTemplate.queryForObject(GET_LOGIN_FAILURE_COUNT, namedParamsMap, Integer.class);
		return loginFailureCount;
	}

    /**
     *
     * @param memberID
     * @return
     */
	//@Override
	public int updateUserAsLocked(int memberID) {
		String UPDATE_USER_A_LOCKED = "UPDATE Member set active = 0 where memberID = ?";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberID", memberID);
		int updateCount = getJdbcTemplate().update(
				UPDATE_USER_A_LOCKED, memberID);
		return updateCount;
	}

	/**
	 *
	 * @param inactiveDaysLimit
	 * @return
	 */
	//@Override
	public int markMembersAsInactive(int inactiveDaysLimit) {
		String UPDATE_MEMBERS_INACTIVE = "UPDATE Member set active =:inactiveFlag where memberID in "
				+ "(SELECT memberID from (SELECT m.memberID from Member m LEFT OUTER JOIN MemberLoginHistory mlh "
				+ "ON m.memberID = mlh.memberID AND mlh.transactionResult =:transactionResult GROUP BY m.memberID, m.memberCreated HAVING IFNULL(max(mlh.transactionTime), FROM_UNIXTIME(m.memberCreated))"
				+ " <= ADDDATE(CURDATE(), interval - :inactiveDaysLimit day)) as Y)";
		int updateCount = getJdbcTemplate().update(
				UPDATE_MEMBERS_INACTIVE, 0, "SUCCESS", inactiveDaysLimit);
		return updateCount;
	}




	/**
	 *
	 * @param passwordExpiryDaysCount
	 * @return
	 */
	public int lockUserPasswordExpiry(int passwordExpiryDaysCount) {
		String UPDATE_MEMBERS_INACTIVE = "UPDATE Member set active =:inactiveFlag where memberID in "
				+ "(SELECT memberID from (SELECT m.memberID from Member m INNER JOIN MemberPasswordHistory mph "
				+ "ON m.memberID = mph.memberID GROUP BY m.memberID HAVING IFNULL(max(mph.changeTime), 0)"
				+ " <= ADDDATE(CURDATE(), interval - :passwordExpiryDaysCount day)) as Y)";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("inactiveFlag", 0);
		namedParamsMap.put("passwordExpiryDaysCount", passwordExpiryDaysCount);
		int updateCount = getJdbcTemplate().update(
				UPDATE_MEMBERS_INACTIVE, 0, passwordExpiryDaysCount);
		return updateCount;
	}

	/**
	 * Returns the username for the member based on memberId.
	 * This doesn't restrict the access to member based on Tenant.
	 * @param memberID
	 * @return
	 */
	public String getUsername(int memberID) {
		String GET_MEMBER_NAME = "SELECT username from Member where memberID = ?";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberID", memberID);
		String username = getJdbcTemplate().queryForObject(
				GET_MEMBER_NAME, String.class, memberID);
		return username;
	}

	 public long getMemberCreationTime(int memberID, int tenantID) {
		String GET_MEMBER_CREATED_TIME = "SELECT memberCreated from Member where memberID =:memberID and tenantID =:tenantID";
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberID", memberID);
		namedParamsMap.put("tenantID", tenantID);
		long createdTime =  namedParamJdbcTemplate.queryForObject(GET_MEMBER_CREATED_TIME, namedParamsMap, Long.class);
		return createdTime;
	}

    public List<MemberPasswordHistory> getMemberPasswordHistory(int memberID) {
        StringBuffer sqlstm = new StringBuffer(
            " SELECT" +
            "  password, changeTime" +
            " FROM" +
            "  MemberPasswordHistory" +
            " WHERE" +
            "  memberID = ?" +
            " ORDER BY changeTime DESC LIMIT 0, 10"
        );

        List<MemberPasswordHistory> ls = getJdbcTemplate().query(sqlstm.toString(),
            new RowMapper<MemberPasswordHistory>() {
                public MemberPasswordHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                	MemberPasswordHistory memberPasswordHistory = new MemberPasswordHistory();
                	memberPasswordHistory.setPassword(rs.getString("password"));
                	memberPasswordHistory.setChangeTime(rs.getTimestamp("changeTime"));
                    return memberPasswordHistory;
                }
            }, memberID
        );

        return ls;
    }

     public void deleteMemberAllPasswordHistory(int memberID) {
        logger.debug("Delete records from MemberPasswordHistory for memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " DELETE" +
            " FROM" +
            "  MemberPasswordHistory" +
            " WHERE" +
            "  memberID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), memberID);
        logger.debug("Rows affected: " + affected);
    }

     public void saveMemberPasswordHistory(int memberID) {
        logger.debug("Save record into MemberPasswordHistory for memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " INSERT" +
            "  INTO MemberPasswordHistory (memberID, password)" +
            " SELECT" +
            "  memberID," +
            "  password" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  memberID = ?"
        );

        int affected = getJdbcTemplate().update(sqlstm.toString(), memberID);
        logger.debug("Rows affected: " + affected);

        deleteMemberObsoletePasswordHistory(memberID);
    }

    private void deleteMemberObsoletePasswordHistory(int memberID) {
            StringBuffer sqlstm = new StringBuffer(
                " SELECT" +
                "  changeTime" +
                " FROM" +
                "  MemberPasswordHistory" +
                " WHERE" +
                "  memberID = ?" +
                " ORDER BY changeTime DESC LIMIT 0, 10"
            );

            List<Timestamp> ls = getJdbcTemplate().query(sqlstm.toString(),
                new RowMapper<Timestamp>() {
                    public Timestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getTimestamp(1);
                    }
                }, memberID
            );

            if (ls.size() == 10) {
                Timestamp ts = ls.get(9);
                sqlstm = new StringBuffer(
                    " DELETE" +
                    " FROM" +
                    "  MemberPasswordHistory" +
                    " WHERE" +
                    "  memberID = ?" +
                    " AND" +
                    "  changeTime < ?"
                );

                int affected = getJdbcTemplate().update(sqlstm.toString(), memberID, ts);
                logger.debug("Rows affected: " + affected);
            }
        }

    public static final String GET_PERSONAL_ROOM_IDS = "select r.memberId, r.roomId from Room r where r.memberId in (:memberIds) and r.roomTypeId =:personal";

	public Map<Integer, Integer> getPersonalRoomIds(List<Integer> memberIds) {
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("memberIds", memberIds);
		namedParamsMap.put("personal", RoomTypes.PERSONAL.getId());
		List<Member> members = namedParamJdbcTemplate.query(
				GET_PERSONAL_ROOM_IDS, namedParamsMap, new RowMapper<Member>() {
					public Member mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Member member = new Member();
						member.setMemberID(rs.getInt("memberId"));
						member.setRoomID(rs.getInt("roomId"));
						return member;
					}
				});
		Map<Integer, Integer> memberRoomMap = new HashMap<Integer, Integer>();
		for (Member member : members) {
			memberRoomMap.put(member.getMemberID(), member.getRoomID());
		}
		return memberRoomMap;
	}

    public static final String GET_PERSONAL_ROOM_ID = "select r.roomId from Room r where r.memberId = :memberId and r.roomTypeId =:personal";

    public Integer getPersonalRoomId(Integer memberId) {
        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("memberId", memberId);
        namedParamsMap.put("personal", RoomTypes.PERSONAL.getId());
		return namedParamJdbcTemplate.queryForObject(GET_PERSONAL_ROOM_ID, namedParamsMap, Integer.class);
    }

    private static String FIND_MEMBERS_BY_NAME_COUNT = "SELECT count(*) AS total FROM Member m WHERE roleID IN (:memberTypes) AND tenantID IN (:tenantIds) AND MATCH(m.memberName) AGAINST(:name in boolean mode)";
    private static String FIND_MEMBERS_BY_EMAIL_COUNT = "SELECT count(*) AS total FROM Member m WHERE roleID IN (:memberTypes) AND tenantID IN (:tenantIds) AND m.emailAddress LIKE :emailEx";
    private static String FIND_MEMBERS_BY_NAME_EMAIL_COUNT = "SELECT count(*) AS total FROM Member m WHERE roleID IN (:memberTypes) AND tenantID IN (:tenantIds) AND MATCH(m.memberName, m.emailAddress) AGAINST(:name in boolean mode)";

    @Override
    public int findMembersCount(String query, String queryField, List<Integer> memberTypes, List<Integer> allowedTenantIds) {

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantIds", allowedTenantIds);
        namedParamsMap.put("memberTypes", memberTypes);

        String sql = FIND_MEMBERS_BY_NAME_EMAIL_COUNT;
        if ("emailAddress".equals(queryField)) {
            sql = FIND_MEMBERS_BY_EMAIL_COUNT;
            namedParamsMap.put("emailEx", prepForLikeSearch(query));
        } else {
        	if ("memberName".equals(queryField)) {
        		sql = FIND_MEMBERS_BY_NAME_COUNT;
        	}
            namedParamsMap.put("name", prepForFullTextSearch(query));
        }
		return namedParamJdbcTemplate.queryForObject(sql, namedParamsMap, Integer.class);
    }

    private static String FIND_MEMBERS_BY_NAME = "SELECT r.roomID as entityID, m.memberName as memberName," +
            " m.description as description, m.emailAddress as emailAddress," +
            " m.active as active, COALESCE(e.status, 0) as status, r.roomTypeID, COALESCE(s.memberID, 0) as myContact," +
            " m.phone1 as phone1, m.phone2 as phone2, m.phone3 as phone3, m.department as department, m.title as title, m.instantMessagerID as instantMessagerID, m.thumbnailUpdateTime as thumbnailUpdateTime" +
            " FROM Member m" +
            " LEFT JOIN Endpoints e ON m.memberID = e.memberID" +
            " LEFT JOIN Room r ON r.memberID = m.memberID " +
            " LEFT JOIN SpeedDial s ON (s.memberID = :thisUserMemberID AND r.roomID = s.roomID)" +
            " WHERE m.roleID IN (:memberTypes) AND m.tenantID IN (:tenantIds) AND" +
            " MATCH(m.memberName) AGAINST(:name in boolean mode) AND" +
            " (e.memberType = 'R' || e.memberType IS NULL) AND" +
            " r.roomTypeID IN (1,3)";

    private static String FIND_MEMBERS_BY_EMAIL = "SELECT r.roomID as entityID, m.memberName as memberName," +
            " m.description as description, m.emailAddress as emailAddress," +
            " m.active as active, COALESCE(e.status, 0) as status, r.roomTypeID, COALESCE(s.memberID, 0) as myContact," +
            " m.phone1 as phone1, m.phone2 as phone2, m.phone3 as phone3, m.department as department, m.title as title, m.instantMessagerID as instantMessagerID, m.thumbnailUpdateTime as thumbnailUpdateTime" +
            " FROM Member m" +
            " LEFT JOIN Endpoints e ON m.memberID = e.memberID" +
            " LEFT JOIN Room r ON r.memberID = m.memberID " +
            " LEFT JOIN SpeedDial s ON (s.memberID = :thisUserMemberID AND r.roomID = s.roomID)" +
            " WHERE m.roleID IN (:memberTypes) AND m.tenantID IN (:tenantIds) AND" +
            " m.emailAddress LIKE :emailEx AND" +
            " (e.memberType = 'R' || e.memberType IS NULL) AND" +
            " r.roomTypeID IN (1,3)";
    
    private static String FIND_MEMBERS_BY_NAME_EMAIL = "SELECT r.roomID as entityID, m.memberName as memberName," +
            " m.description as description, m.emailAddress as emailAddress," +
            " m.active as active, COALESCE(e.status, 0) as status, r.roomTypeID, COALESCE(s.memberID, 0) as myContact," +
            " m.phone1 as phone1, m.phone2 as phone2, m.phone3 as phone3, m.department as department, m.title as title, m.instantMessagerID as instantMessagerID, m.thumbnailUpdateTime as thumbnailUpdateTime" +
            " FROM Member m" +
            " LEFT JOIN Endpoints e ON m.memberID = e.memberID" +
            " LEFT JOIN Room r ON r.memberID = m.memberID " +
            " LEFT JOIN SpeedDial s ON (s.memberID = :thisUserMemberID AND r.roomID = s.roomID)" +
            " WHERE m.roleID IN (:memberTypes) AND m.tenantID IN (:tenantIds) AND" +
            " MATCH(m.memberName, m.emailAddress) AGAINST(:name in boolean mode) AND" +
            " (e.memberType = 'R' || e.memberType IS NULL) AND" +
            " r.roomTypeID IN (1,3)";

    @Override
    public List<MemberMini> findMembers(int thisUserMemberID, String query, String queryField, List<Integer> memberTypes, List<Integer> allowedTenantIds,
                                        int start, int limit, String sortBy, String sortDir) {


        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("tenantIds", allowedTenantIds);
        namedParamsMap.put("memberTypes", memberTypes);
        namedParamsMap.put("thisUserMemberID", thisUserMemberID);
        String sql = FIND_MEMBERS_BY_NAME_EMAIL;
        if ("emailAddress".equals(queryField)) {
            namedParamsMap.put("emailEx", prepForLikeSearch(query));
            sql = FIND_MEMBERS_BY_EMAIL;
            if ("DESC".equals(sortDir)) {
                sql = sql + " ORDER BY memberName DESC LIMIT " + start + "," + limit;
            } else {
                sql = sql + " ORDER BY memberName ASC LIMIT " + start + "," + limit;
            }
        } else {
        	if ("memberName".equals(queryField)) {
                sql = FIND_MEMBERS_BY_NAME;
        	}
            namedParamsMap.put("name", prepForFullTextSearch(query));
            if ("DESC".equals(sortDir)) {
                sql = sql + " ORDER BY memberName DESC LIMIT " + start + "," + limit;
            } else {
                sql = sql + " ORDER BY memberName ASC LIMIT " + start + "," + limit;
            }
        }

        List<MemberMini> members = namedParamJdbcTemplate.query(sql, namedParamsMap,
                new RowMapper<MemberMini>() {
                    public MemberMini mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        MemberMini member = new MemberMini();
                        member.setMemberID(rs.getLong("entityID"));
                        member.setName(rs.getString("memberName"));
                        member.setDescription(StringUtils.trimToEmpty(rs.getString("description")));
                        member.setEmail(StringUtils.trimToEmpty(rs.getString("emailAddress")));
                        member.setEnabled(rs.getInt("active") == 1);
                        member.setStatus(rs.getInt("status"));
                        if (rs.getInt("roomTypeID") == 1) {
                            member.setType("member");
                        } else if (rs.getInt("roomTypeID") == 3) {
                            member.setType("legacy");
                        }
                        if (rs.getInt("myContact") == 0) {
                            member.setInContacts(false);
                        } else {
                            member.setInContacts(true);
                        }
                        member.setPhone1(rs.getString("phone1"));
                        member.setPhone2(rs.getString("phone2"));
                        member.setPhone3(rs.getString("phone3"));
                        member.setDepartment(rs.getString("department"));
                        member.setTitle(rs.getString("title"));
                        member.setThumbnailUpdateTime(rs.getTimestamp("thumbnailUpdateTime"));
                        return member;
                    }
                });
        return members;
    }

    private String prepForLikeSearch(String query) {
        // do not allow percentage and remove all whitespaces anywhere
        String queryInput =  StringUtils.deleteWhitespace(query.replaceAll("%", ""));
        return queryInput + "%";
    }

    private String prepForFullTextSearch(String query) {
        // replace multiple spaces with single space
        query = query.replaceAll("\\b\\s{2,}\\b", " ");
        char[] chArr = query.toCharArray();
        //Special character filter: any character that is not a letter, number, hyphen or underscore becomes a space (note that this must account for all languages)
        for (int i = 0; i < chArr.length; i++) {
            if (!(Character.isLetterOrDigit(chArr[i]) || chArr[i] == '_' || chArr[i] == '-' || chArr[i] == '.')) {
                chArr[i] = ' ';
            }
        }
        String charProcessedQuery = String.valueOf(chArr);
        String queryInput = "";
        for (String queryInputStr : charProcessedQuery.split(" ")) {
            if (!StringUtils.isEmpty(queryInputStr)) {
                queryInput += " +" + queryInputStr + "*";
            }
        }
        // Handle "hyphen" as part of the word and enclose it with double quotes
        if (queryInput.contains("-") || queryInput.contains(".")) {
            queryInput = "\"" + queryInput + "\"";
        }
        return queryInput;
    }

    private static final String SEARCH_MEMBERS = "SELECT m.memberid, m.memberName, m.username, m.active, r.roomExtNumber, m.emailaddress, m.langid, m.creationtime, m.memberCreated, m.updatetime as modificationTime, "
    		+ "m.importedUsed, lo.locationTag, mr.roleName, g.groupName, rt.roomType, m.phone1, m.phone2, m.phone3, m.department, m.title, m.location, m.instantMessagerID, m.thumbnailUpdateTime FROM Member m, Room r, RoomType rt, MemberRole mr, Groups g, Locations lo WHERE m.tenantId =:tenantId ";

	public List<Member> searchMembers(int tenantId, MemberFilter filter) {
		StringBuilder queryBuilder = new StringBuilder(SEARCH_MEMBERS);
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantId", tenantId);
		if (StringUtils.isNotBlank(filter.getMemberName())) {
			queryBuilder.append(" AND MATCH(memberName, username) AGAINST(:memberName in boolean mode)");
			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getMemberName()));
		}
		if (StringUtils.isNotBlank(filter.getUserStatus()) && !filter.getUserStatus().equalsIgnoreCase("All")) {
			queryBuilder.append(" AND m.active =:active");
			namedParamsMap.put("active", filter.getUserStatus().equalsIgnoreCase("Enabled") ? 1 : 0);
		}
		if (StringUtils.isNotBlank(filter.getExt())) {
			queryBuilder.append(" AND MATCH(roomName,roomExtNumber, displayName) AGAINST(:roomExtNumber in boolean mode)");
			namedParamsMap.put("roomExtNumber", RoomUtils.buildSearchQuery(filter.getExt()));
		}
		queryBuilder.append(
				" AND m.memberid = r.memberid and r.roomTypeId in (1,3) AND r.roomTypeID = rt.roomTypeID AND m.roleid = mr.roleid and r.groupid = g.groupid and m.locationid = lo.locationid");
		if (StringUtils.isNotBlank(filter.getGroupName())) {
			queryBuilder.append(" AND g.groupName like :groupName");
			namedParamsMap.put("groupName", filter.getGroupName() + "%");
		}
		if (StringUtils.isNotBlank(filter.getType()) && !filter.getType().equalsIgnoreCase("All")) {
			queryBuilder.append(" AND mr.roleName =:roleName");
			namedParamsMap.put("roleName", filter.getType());
		}
		queryBuilder.append(" ORDER BY ").append(filter.getSort()).append(" ").append(filter.getDir()).append(" LIMIT ")
				.append(filter.getStart()).append(",").append(filter.getLimit());
		List<Member> members = namedParamJdbcTemplate.query(queryBuilder.toString(), namedParamsMap,
				BeanPropertyRowMapper.newInstance(Member.class));
		return members;
	}

    private static final String COUNT_MEMBERS = "SELECT count(*) FROM Member m, Room r, MemberRole mr, Groups g, Locations lo WHERE m.tenantId =:tenantId ";

	public int getMembersCount(int tenantId, MemberFilter filter) {
		StringBuilder queryBuilder = new StringBuilder(COUNT_MEMBERS);
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("tenantId", tenantId);
		if (StringUtils.isNotBlank(filter.getMemberName())) {
			queryBuilder.append(" AND MATCH(memberName, username) AGAINST(:memberName in boolean mode)");
			namedParamsMap.put("memberName", RoomUtils.buildSearchQuery(filter.getMemberName()));
		}
		if (StringUtils.isNotBlank(filter.getUserStatus()) && !filter.getUserStatus().equalsIgnoreCase("All")) {
			queryBuilder.append(" AND m.active =:active");
			namedParamsMap.put("active", filter.getUserStatus().equalsIgnoreCase("Enabled") ? 1 : 0);
		}
		if (StringUtils.isNotBlank(filter.getExt())) {
			queryBuilder.append(" AND MATCH(roomName,roomExtNumber, displayName) AGAINST(:roomExtNumber in boolean mode)");
			namedParamsMap.put("roomExtNumber", RoomUtils.buildSearchQuery(filter.getExt()));
		}
		queryBuilder.append(
				" AND m.memberid = r.memberid and r.roomTypeId in (1,3) AND m.roleid = mr.roleid and r.groupid = g.groupid and m.locationid = lo.locationid");
		if (StringUtils.isNotBlank(filter.getGroupName())) {
			queryBuilder.append(" AND g.groupName like :groupName");
			namedParamsMap.put("groupName", filter.getGroupName() + "%");
		}
		if (StringUtils.isNotBlank(filter.getType()) && !filter.getType().equalsIgnoreCase("All")) {
			queryBuilder.append(" AND mr.roleName =:roleName");
			namedParamsMap.put("roleName", filter.getType());
		}
		return namedParamJdbcTemplate.queryForObject(queryBuilder.toString(), namedParamsMap, Integer.class);
	}

	@Override
    public int updateMemberThumbnailTimeStamp(int memberID, Date thumbnailUpdateTime) {
        logger.debug("Update record in Member for memberID = " + memberID + " set thumbnailUpdateTime = " + thumbnailUpdateTime);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member m" +
            " SET" +
            "  m.thumbnailUpdateTime = :thumbnailUpdateTime" +
            " WHERE" +
            "  m.memberID = :memberID"
        );

        Map<String, Object> namedParamsMap = new HashMap<String, Object>();
        namedParamsMap.put("thumbnailUpdateTime", thumbnailUpdateTime);
        namedParamsMap.put("memberID", memberID);
        int affected = namedParamJdbcTemplate.update(sqlstm.toString(), namedParamsMap);
        logger.debug("Rows affected: " + affected);

        return affected;
    }


}
