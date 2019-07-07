package com.vidyo.db.security.token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import com.vidyo.db.security.token.VidyoPersistentRememberMeToken;



/**
 * JDBC based persistent login token repository implementation.
 * 
 * @author Luke Taylor
 * @version $Id: VidyoTokenRepositoryImpl.java,v 1.1 2013/01/04 18:56:29 ganesh
 *          Exp $
 * @since 2.0
 */
public class VidyoTokenRepositoryImpl extends JdbcDaoSupport implements VidyoPersistentTokenRepository {
	// ~ Static fields/initializers
	// =====================================================================================

	/** Default SQL for creating the database table to store the tokens */
	/*
	 * public static final String CREATE_TABLE_SQL =
	 * "create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, "
	 * +
	 * "token varchar(64) not null, last_used timestamp not null, tenant_id int not null)"
	 * ;
	 */
	/** The default SQL used by the <tt>getTokenBySeries</tt> query */
	public static final String DEF_TOKEN_BY_SERIES_SQL =
			"select username,series,token,last_used,tenant_id,validity_secs,creation_time,endpoint_guid from persistent_logins where series = ? and tenant_id = ? and endpoint_guid = ?";
	/** The default SQL used by the <tt>getTokenBySeries</tt> query */
	public static final String DEF_TOKEN_BY_SERIES_WITHOUTEGUID_SQL =
			"select username,series,token,last_used,tenant_id,validity_secs,creation_time,endpoint_guid from persistent_logins where series = ? and tenant_id = ? and endpoint_guid IS NULL";
	
	/** The default SQL used by <tt>createNewToken</tt> */
	public static final String DEF_INSERT_TOKEN_SQL =
			"insert into persistent_logins (username, series, token, last_used, tenant_id, validity_secs, creation_time, endpoint_guid) values(?,?,?,?,?,?,?,?)";
	/** The default SQL used by <tt>updateToken</tt> */
	public static final String DEF_UPDATE_TOKEN_SQL =
			"update persistent_logins set last_used = ? where series = ? and tenant_id = ? and endpoint_guid = ?";
	/** The default SQL used by <tt>removeUserTokens</tt> */
	public static final String DEF_REMOVE_USER_TOKENS_SQL =
			"delete from persistent_logins where username = ? and tenant_id = ? and endpoint_guid = ?";

	/** The default SQL used by <tt>removeUserTokens</tt> */
	public static final String DEF_REMOVE_USER_TOKENS_BY_USERID_SQL =
			"delete from persistent_logins where username = ? and tenant_id = ?";

	public static final String DEF_TOKEN_BY_USER_EID_SQL =
			"select count(1) from persistent_logins where username = ? and tenant_id = ? and endpoint_guid != ?";
	
	public static final String DEF_TOKEN_BY_USER_SQL =
		"select count(1) from persistent_logins where username = ? and tenant_id = ?";
	
	/** The default SQL used by <tt>removeAllUserTokensExceptCurrentEndpoint</tt> */
	public static final String DEF_REMOVE_ALL_TOKENS_SQL_EXCEPT_CUR_EID =
			"delete from persistent_logins where username = ? and tenant_id = ? and endpoint_guid != ?";
	
	/** The default SQL used by <tt></tt> */
	public static final String DEF_DELETE_INACTIVE_TOKENS = "delete from persistent_logins where TIMESTAMPDIFF(SECOND, last_used, CURRENT_TIMESTAMP) >= ?";
	
	/** The default SQL used by <tt></tt> */
	public static final String DEF_DELETE_TOKENS_BY_TENANT_ID = "delete from persistent_logins where tenant_id = ?";
	
	/** The default SQL used by <tt></tt> */
	public static final String DEF_DELETE_TOKENS_BY_SERIES_TOKEN_TENANT_ID = "delete from persistent_logins where series = ? and token = ? and username = ? and tenant_id = ?";

	// ~ Instance fields
	// ================================================================================================

	private String tokensBySeriesSql = DEF_TOKEN_BY_SERIES_SQL;
	private String tokensBySeriesWithoutEGUIDSql = DEF_TOKEN_BY_SERIES_WITHOUTEGUID_SQL;
	private String insertTokenSql = DEF_INSERT_TOKEN_SQL;
	private String updateTokenSql = DEF_UPDATE_TOKEN_SQL;
	private String removeUserTokensSql = DEF_REMOVE_USER_TOKENS_SQL;
	private String removeUserTokensByNameSql = DEF_REMOVE_USER_TOKENS_BY_USERID_SQL;
	private String tokenCountByUserEidSql = DEF_TOKEN_BY_USER_EID_SQL;
	private String tokenCountByUserSql = DEF_TOKEN_BY_USER_SQL;
	private String removeAllUserTokensExceptEidSql = DEF_REMOVE_ALL_TOKENS_SQL_EXCEPT_CUR_EID;
	private String removeInactiveTokensSql = DEF_DELETE_INACTIVE_TOKENS;
	private String removeUserTokensByTenantSql = DEF_DELETE_TOKENS_BY_TENANT_ID;
	private String removeCurrentUserTokenSql = DEF_DELETE_TOKENS_BY_SERIES_TOKEN_TENANT_ID;
	private boolean createTableOnStartup = false;

	protected MappingSqlQuery<VidyoPersistentRememberMeToken> tokensBySeriesMapping;
	protected MappingSqlQuery<VidyoPersistentRememberMeToken> tokensBySeriesWithoutEGUIDMapping;
	protected SqlUpdate insertToken;
	protected SqlUpdate updateToken;
	protected SqlUpdate removeUserTokens;
	protected SqlUpdate removeUserTokensByName;
	protected MappingSqlQuery<Integer> countByUserEidMapping;
	protected MappingSqlQuery<Integer> countByUserMapping;
	protected SqlUpdate removeAllUserTokensExceptEid;
	protected SqlUpdate removeInactiveTokens;
	protected SqlUpdate removeUserTokensByTenant;
	protected SqlUpdate removeCurrentToken;
	
	protected void initDao() {
		tokensBySeriesMapping = new TokensBySeriesMapping(getDataSource());
		tokensBySeriesWithoutEGUIDMapping = new TokensBySeriesWithoutGUIDMapping(getDataSource());
		insertToken = new InsertToken(getDataSource());
		updateToken = new UpdateToken(getDataSource());
		removeUserTokens = new RemoveUserTokens(getDataSource());
		removeUserTokensByName = new RemoveUserTokensByName(getDataSource());
		countByUserEidMapping = new CountByUserEidMapping(getDataSource());
		countByUserMapping = new CountByUserMapping(getDataSource());
		removeAllUserTokensExceptEid = new RemoveAllTokensExceptCurrentEnpoint(getDataSource());
		removeInactiveTokens = new RemoveInactiveTokens(getDataSource());
		removeUserTokensByTenant = new RemoveUserTokensByTenant(getDataSource());
		removeCurrentToken = new RemoveCurrentToken(getDataSource());
		/*
		 * if (createTableOnStartup) {
		 * getJdbcTemplate().execute(CREATE_TABLE_SQL); }
		 */
	}

	@Override
	public void createNewToken(VidyoPersistentRememberMeToken token) {
		Date creationDate = Calendar.getInstance().getTime();
		insertToken
				.update(
				new Object[] { token.getUsername(), token.getSeries(), token.getTokenValue(), creationDate,
						token.getTenantId(), token.getValiditySecs(), creationDate, token.getEndpointGUID() });
	}

	@Override
	public void updateToken(String series, Date lastUsed, int tenantId, String endpointGUID) {
		updateToken.update(new Object[] { new Date(), series, tenantId, endpointGUID });
	}

	/**
	 * Loads the token data for the supplied series identifier.
	 * 
	 * If an error occurs, it will be reported and null will be returned (since
	 * the result should just be a failed persistent login).
	 * 
	 * @param seriesId
	 * @return the token matching the series, or null if no match found or an
	 *         exception occurred.
	 */
	@Override
	public VidyoPersistentRememberMeToken getTokenForSeries(String seriesId, int tenantId, String endpointGUID) {
		try {
			if (endpointGUID != null)
				return (VidyoPersistentRememberMeToken) tokensBySeriesMapping.findObject(new Object[] { seriesId, tenantId,
					endpointGUID });
			
			return (VidyoPersistentRememberMeToken) tokensBySeriesWithoutEGUIDMapping.findObject(new Object[] { seriesId, tenantId });
		} catch (IncorrectResultSizeDataAccessException moreThanOne) {
			logger.error("Querying token for series '" + seriesId + "' returned more than one value. Series" +
					"should be unique");
		} catch (DataAccessException e) {
			logger.error("Failed to load token for series " + seriesId, e);
		}

		return null;
	}

	@Override
	public void removeUserTokens(String username, int tenantId, String endpointGUID) {
		if(endpointGUID != null) {
			removeUserTokens.update(new Object[] { username, tenantId, endpointGUID });
			return;
		}
		removeUserTokens(username, tenantId);
	}

	/**
	 * Deletes all tokens associated with the user.
	 * 
	 * @param username
	 * @param tenantId
	 */
	public int removeUserTokens(String username, int tenantId) {
		return removeUserTokensByName.update(new Object[] { username, tenantId });
	}

	/**
	 * Returns the count of active tokens excluding the endpointid from which
	 * the request is made.
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 * @return
	 */
	public int getCountByUserEid(String username, int tenantId, String endpointId) {
		if(endpointId != null) {
			return countByUserEidMapping.findObject(username, tenantId, endpointId);	
		}
		return getCountByUsername(username, tenantId);		
	}
	
	/**
	 * Returns the count of active tokens for the user
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 * @return
	 */
	private int getCountByUsername(String username, int tenantId) {
		return countByUserMapping.findObject(username, tenantId);
	}
	
	/**
	 * Deletes all auth tokens of the user except for the Eid.
	 * 
	 * @param username
	 * @param tenantId
	 * @param endpointId
	 */
	public int removeAllUserTokensExceptCurrentEndpoint(String username, int tenantId, String endpointId) {
		return removeAllUserTokensExceptEid.update(new Object[]{username, tenantId, endpointId});
	}
	
	/**
	 * Deletes tokens which are inactive for the specified time.
	 * 
	 * @param inactiveDaysCount
	 */	
	@Override
	public int removeUserTokens(int inactiveDaysCount) {
		return removeInactiveTokens.update(inactiveDaysCount);
	}
	
	/**
	 * 
	 */
	@Override
	public int removeUserTokensByTenant(int tenantId) {
		return removeUserTokensByTenant.update(tenantId);
	}
	
	@Override
	public int removeCurrentToken(String username, String series, String token, int tenantId) {
		return removeCurrentToken.update(series, token, username, tenantId);
	}	
	
	/**
	 * Intended for convenience in debugging. Will create the persistent_tokens
	 * database table when the class is initialized during the initDao method.
	 * 
	 * @param createTableOnStartup
	 *            set to true to execute the
	 */
	public void setCreateTableOnStartup(boolean createTableOnStartup) {
		this.createTableOnStartup = createTableOnStartup;
	}

	// ~ Inner Classes
	// ==================================================================================================

	protected class TokensBySeriesMapping extends MappingSqlQuery<VidyoPersistentRememberMeToken> {
		protected TokensBySeriesMapping(DataSource ds) {
			super(ds, tokensBySeriesSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected VidyoPersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
			VidyoPersistentRememberMeToken token =
					new VidyoPersistentRememberMeToken(rs.getString(1), rs.getInt(5), rs.getString(2), rs.getString(3),
							rs.getTimestamp(4), rs.getTimestamp("creation_time"), rs.getInt("validity_secs"),
							rs.getString("endpoint_guid"));

			return token;
		}
	}
	
	protected class TokensBySeriesWithoutGUIDMapping extends MappingSqlQuery<VidyoPersistentRememberMeToken> {
		protected TokensBySeriesWithoutGUIDMapping(DataSource ds) {
			super(ds, tokensBySeriesWithoutEGUIDSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected VidyoPersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
			VidyoPersistentRememberMeToken token =
					new VidyoPersistentRememberMeToken(rs.getString(1), rs.getInt(5), rs.getString(2), rs.getString(3),
							rs.getTimestamp(4), rs.getTimestamp("creation_time"), rs.getInt("validity_secs"),
							rs.getString("endpoint_guid"));

			return token;
		}
	}

	protected class UpdateToken extends SqlUpdate {

		public UpdateToken(DataSource ds) {
			super(ds, updateTokenSql);
			setMaxRowsAffected(1);
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class InsertToken extends SqlUpdate {

		public InsertToken(DataSource ds) {
			super(ds, insertTokenSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class RemoveUserTokens extends SqlUpdate {
		public RemoveUserTokens(DataSource ds) {
			super(ds, removeUserTokensSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class RemoveUserTokensByName extends SqlUpdate {
		public RemoveUserTokensByName(DataSource ds) {
			super(ds, removeUserTokensByNameSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}
	
	protected class RemoveAllTokensExceptCurrentEnpoint extends SqlUpdate {
		public RemoveAllTokensExceptCurrentEnpoint(DataSource ds) {
			super(ds, removeAllUserTokensExceptEidSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}	

	protected class CountByUserEidMapping extends MappingSqlQuery<Integer> {
		protected CountByUserEidMapping(DataSource ds) {
			super(ds, tokenCountByUserEidSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt(1);
		}
	}
	
	protected class CountByUserMapping extends MappingSqlQuery<Integer> {
		protected CountByUserMapping(DataSource ds) {
			super(ds, tokenCountByUserSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt(1);
		}
	}

	protected class RemoveInactiveTokens extends SqlUpdate {
		public RemoveInactiveTokens(DataSource ds) {
			super(ds, removeInactiveTokensSql);
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}	
	
	protected class RemoveUserTokensByTenant extends SqlUpdate {
		public RemoveUserTokensByTenant(DataSource ds) {
			super(ds, removeUserTokensByTenantSql);
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}
	
	protected class RemoveCurrentToken extends SqlUpdate {
		public RemoveCurrentToken(DataSource ds) {
			super(ds, removeCurrentUserTokenSql);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
		}
	}

}
