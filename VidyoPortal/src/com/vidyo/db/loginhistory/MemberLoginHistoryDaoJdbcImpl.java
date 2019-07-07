/**
 *
 */
package com.vidyo.db.loginhistory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.googlecode.ehcache.annotations.Cacheable;
import com.vidyo.bo.loginhistory.MemberLoginHistory;

/**
 * @author Ganesh
 *
 */
public class MemberLoginHistoryDaoJdbcImpl extends NamedParameterJdbcDaoSupport
		implements IMemberLoginHistoryDao {

	/** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(MemberLoginHistoryDaoJdbcImpl.class.getName());
    /**
	 *
	 */
	private static final String INSERT_TRANSACTION_HISTORY = "INSERT INTO MemberLoginHistory (memberID, transactionName, transactionResult, sourceIP, transactionTime) "
			+ "VALUES (:memberID, :transactionName, :transactionResult, :sourceIP, :transactionTime)";

	/**
	 *
	 */
	private static final String DELETE_EXTRA_LOGIN__HISTORY_ROWS = "DELETE MemberLoginHistory FROM MemberLoginHistory LEFT JOIN (SELECT ID FROM MemberLoginHistory "
			+ "WHERE memberID =:memberID ORDER BY transactionTime DESC "
			+ "LIMIT :start, :limit ) AS NOT_TO_DELETE on MemberLoginHistory.ID = NOT_TO_DELETE.ID WHERE NOT_TO_DELETE.ID IS NULL and MemberLoginHistory.memberID =:memberID;";

	/**
	 *
	 * @param memberLoginHistory
	 * @return
	 */
	@Override
	public int addMemberLoginHistory(MemberLoginHistory memberLoginHistory) {

		// Before Insert, keep only last 5 rows and delete the rest
		int start = 0;
		int limit = getMemberLoginHistoryLimit(1); //5;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberID", memberLoginHistory.getMemberID());
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		int deletedCount = getNamedParameterJdbcTemplate().update(
				DELETE_EXTRA_LOGIN__HISTORY_ROWS, paramMap);

		SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("memberID", memberLoginHistory.getMemberID())
				.addValue("transactionName",
						memberLoginHistory.getTransactionName())
				.addValue("transactionTime",
						memberLoginHistory.getTransactionTime())
				.addValue("sourceIP", memberLoginHistory.getSourceIP())
				.addValue("transactionResult",
						memberLoginHistory.getTransactionResult());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(INSERT_TRANSACTION_HISTORY, paramSource,
				generatedKeyHolder);

		return generatedKeyHolder.getKey().intValue();
	}

	private final String GET_LOGIN_HISTORY = "SELECT mlh.* FROM MemberLoginHistory as mlh INNER JOIN Member as m on (mlh.memberID = m.memberID) " +
			"WHERE mlh.memberID = :memberID and m.tenantID = :tenantID and transactionName = 'Login' ORDER BY ID DESC LIMIT :start, :limit";

	/**
	 *
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	@Override
	public List<MemberLoginHistory> getLoginHistoryDetails(int memberID,
			int tenantID) {
		int start = 0;
		int limit = 5;

		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("memberID", memberID);
		paramMap.put("tenantID", tenantID);
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		return getNamedParameterJdbcTemplate().query(
				GET_LOGIN_HISTORY,
				paramMap,
				BeanPropertyRowMapper
						.newInstance(MemberLoginHistory.class));
	}

	/**
	 *
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	@Override
	public List<MemberLoginHistory> getFailedLoginDetails(int memberID,
			int tenantID) {
		String GET_FAILED_LOGIN_HISTORY = "select * from MemberLoginHistory where memberID =:memberID and transactionResult =:transactionResult order by ID desc";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberID", memberID);
		paramMap.put("transactionResult", "FAILURE");
		return getNamedParameterJdbcTemplate().query(
				GET_FAILED_LOGIN_HISTORY,
				paramMap,
				BeanPropertyRowMapper
						.newInstance(MemberLoginHistory.class));
	}

	/**
	 *
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	@Override
	public Date getLastLoginTime(int memberID, int tenantID) {
		String GET_SUCCESSFUL_LAST_LOGIN_TIME = "select * from MemberLoginHistory where memberID =:memberID and transactionResult =:transactionResult order by ID desc limit 1,1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberID", memberID);
		paramMap.put("transactionResult", "SUCCESS");
		List<MemberLoginHistory> successLogins = getNamedParameterJdbcTemplate()
				.query(GET_SUCCESSFUL_LAST_LOGIN_TIME,
						paramMap,
						BeanPropertyRowMapper
								.newInstance(MemberLoginHistory.class));
		if (successLogins == null || successLogins.isEmpty()) {
			return null;
		}
		return successLogins.get(0).getTransactionTime();
	}

    @Cacheable(cacheName = "memberLoginHistoryLimitCache", keyGeneratorName = "HashCodeCacheKeyGenerator")
    private int getMemberLoginHistoryLimit(int tenant) {
		logger.debug("Get LOGIN_HISTORY_COUNT from Configuration");
		StringBuffer sqlstm = new StringBuffer(
			" SELECT" +
			"  configurationValue" +
			" FROM" +
			"  Configuration" +
			" WHERE" +
			"  configurationName = 'LOGIN_HISTORY_COUNT'" +
			" AND" +
			"  tenantID = " + tenant
		);

		int intVal = getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sqlstm.toString(), Integer.class);

        return intVal;
    }

}
