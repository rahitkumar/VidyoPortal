package com.vidyo.db.statusnotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.vidyo.bo.statusnotify.StatusNotificationInfo;

public class StatusNotificationDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements IStatusNotificationDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(StatusNotificationDaoJdbcImpl.class.getName());

	public static final String NOTIFICATION_INFO =
		" SELECT" +
		"  CASE WHEN e.memberType = :rMemberType THEN" +
		"   m.username" +
		"  ELSE" +
		"   CONCAT(g.guestName, '(', g.username, ')') " +
		"  END as UserName," +
		"  CASE WHEN e.memberType = :rMemberType THEN" +
		"   tm.tenantName" +
		"  ELSE" +
		"   tg.tenantName" +
		"  END as TenantName," +
		"  CASE WHEN e.memberType = :rMemberType THEN" +
		"   'M'" +
		"  ELSE" +
		"   'G'" +
		"  END as UserType" +
		" FROM" +
		"  Endpoints e" +
		" LEFT JOIN Member m ON m.memberID = e.memberID AND e.memberType = :rMemberType" +
		" LEFT JOIN Tenant tm ON tm.tenantID = m.tenantID AND e.memberType = :rMemberType" +
		" LEFT JOIN Guests g ON g.guestID = e.memberID AND e.memberType = :gMemberType" +
		" LEFT JOIN Tenant tg ON tg.tenantID = g.tenantID AND e.memberType = :gMemberType" +
		" WHERE" +
		"  e.endpointGUID = :endpointGUID" +

		" UNION" +

		" SELECT" +
		"  e.displayName as UserName," +
		"  t.tenantName as TenantName," +
		"  'L' as UserType" +
		" FROM" +
		"  VirtualEndpoints e" +
		" LEFT JOIN Tenant t ON t.tenantID = e.tenantID" +
		" WHERE" +
		"  e.endpointGUID = :endpointGUID" +

		" UNION" +

		" SELECT" +
		"  e.description as UserName," +
		"  t.tenantName as TenantName," +
		"  'R' as UserType" +
		" FROM" +
		"  RecorderEndpoints e" +
		" LEFT JOIN Tenant t ON t.tenantID = :tenantID" +
		" WHERE" +
		"  e.endpointGUID = :endpointGUID";

	@Override
    public StatusNotificationInfo getInfoForStatusNotification(String GUID) {
		if (logger.isDebugEnabled()) {
            logger.debug("Collect member notificationInfo for endpoint - " + GUID);
		}

        StatusNotificationInfo notificationInfo = null;

	    SqlParameterSource namedParams = new MapSqlParameterSource()
		    .addValue("endpointGUID", GUID)
		    .addValue("rMemberType", "R")
		    .addValue("gMemberType", "G")
		    .addValue("tenantID", 1);

        try {
	        notificationInfo = getNamedParameterJdbcTemplate().queryForObject(NOTIFICATION_INFO, namedParams,
	            BeanPropertyRowMapper.newInstance(StatusNotificationInfo.class));
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }

        return notificationInfo;
    }

}