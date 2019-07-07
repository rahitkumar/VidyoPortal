/**
 *
 */
package com.vidyo.db.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.vidyo.bo.transaction.TransactionFilter;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.utils.SyslogUtils;

/**
 * Jdbc Implementaion of the Transaction DAO. Provides implementations for all
 * the CRUD operations involving TransactionHistory Table
 *
 * @author Ganesh
 *
 */
public class TransactionDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements
		TransactionDao {
    protected final Logger logger = LoggerFactory.getLogger(TransactionDaoJdbcImpl.class.getName());

	/**
	 * Inserts a single Transaction History record in to the TransactionHistory
	 * table
	 *
	 * @param transactionHistory
	 *            Business Object holding the transaction data
	 * @return transactionID autogenerated Identifier from the Database
	 */
	@Override
	public int addTransactionHistory(TransactionHistory transactionHistory) {
		transactionHistory.setTransactionTime(Calendar.getInstance().getTime());
		String INSERT_TRANSACTION_HISTORY = "INSERT INTO TransactionHistory (userID, tenantName, transactionName, transactionResult, transactionTime, sourceIP, transactionParams) VALUES (:userID, :tenantName, :transactionName, :transactionResult, :transactionTime, :sourceIP, :transactionParams)";
		SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("userID", transactionHistory.getUserID())
				.addValue("tenantName", transactionHistory.getTenantName())
				.addValue("transactionName",
						transactionHistory.getTransactionName())
				.addValue("transactionTime",
						Calendar.getInstance().getTime())
				.addValue("sourceIP", transactionHistory.getSourceIP())
				.addValue("transactionParams",
						transactionHistory.getTransactionParams())
				.addValue("transactionResult",
						transactionHistory.getTransactionResult());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(INSERT_TRANSACTION_HISTORY, paramSource,
				generatedKeyHolder);
		SyslogUtils.auditLog(transactionHistory.toMap(), "");
		return generatedKeyHolder.getKey().intValue();
	}

	public List<TransactionHistory> getTransactionHistoryForPeriod(
			TransactionFilter filter) {
		List<TransactionHistory> rc = null;

		String days = "30";
		if (filter != null) {
			days = filter.getAuditperiod();
		}

		StringBuffer sqlstm = new StringBuffer(
				" SELECT"
						+ " *"
						+ " FROM"
						+ "  TransactionHistory"
						+ " WHERE"
						+ "  TIMESTAMPDIFF(DAY, `transactionTime`, CURRENT_TIMESTAMP) <= :days");
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();
		namedParamsMap.put("days", days);

		rc = getNamedParameterJdbcTemplate().query(
				sqlstm.toString(), namedParamsMap,
				BeanPropertyRowMapper
						.newInstance(TransactionHistory.class));
		return rc;
	}

	public int getTransactionHistoryCountBetweenDates(
			TransactionFilter filter) {
		String startDate = null;
		String endDate = null;

		if (filter != null) {
			startDate = filter.getStartDate();
			String endDateString =  filter.getEndDate();
			Calendar c = Calendar.getInstance();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date enddate;
			try {
				enddate = dateFormat.parse(endDateString);
				c.setTime(enddate);
				c.add(Calendar.DATE, 1); // Adding 1 day
				enddate = c.getTime();

				dateFormat.setLenient(false);
				endDate = dateFormat.format(enddate);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		StringBuffer sqlstm = new StringBuffer(
				" SELECT"
						+ " COUNT(*)"
						+ " FROM"
						+ "  TransactionHistory"
						+ " WHERE  `transactionTime`"
						+ "  BETWEEN :startDate AND :endDate ");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("startDate", startDate);
		paramsMap.put("endDate", endDate);
		int count = getNamedParameterJdbcTemplate().queryForObject(sqlstm.toString(), paramsMap, Integer.class);

		return count;
	}

	public List<TransactionHistory> getTransactionHistoryBetweenDates(
			TransactionFilter filter) {
		List<TransactionHistory> rc = null;
		String startDate = null;
		String endDate = null;

		if (filter != null) {
				startDate = filter.getStartDate();
				String endDateString =  filter.getEndDate();
				Calendar c = Calendar.getInstance();

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date enddate;
				try {
					enddate = dateFormat.parse(endDateString);
					c.setTime(enddate);
					c.add(Calendar.DATE, 1); // Adding 1 day
					enddate = c.getTime();

					dateFormat.setLenient(false);
				    endDate = dateFormat.format(enddate);

				} catch (ParseException e) {
					e.printStackTrace();
				}
		}

		StringBuffer sqlstm = new StringBuffer(
				" SELECT"
						+ " *"
						+ " FROM"
						+ "  TransactionHistory"
						+ " WHERE  `transactionTime`"
						+ "  BETWEEN :startDate AND :endDate ");
		Map<String, Object> namedParamsMap = new HashMap<String, Object>();

		namedParamsMap.put("startDate", startDate);
		namedParamsMap.put("endDate", endDate);

		rc = getNamedParameterJdbcTemplate().query(
				sqlstm.toString(),
				namedParamsMap, BeanPropertyRowMapper
				.newInstance(TransactionHistory.class));
		return rc;
	}
     public long  deleteTransactionHistoryForPeriod() {
        logger.debug("Remove all records from TransactionHistory except keep history for days = 90");
        StringBuffer sqlstm_delete = new StringBuffer();
        sqlstm_delete.append(
            "DELETE FROM TransactionHistory " +
                    "WHERE transactionTime < DATE_ADD(Now(), INTERVAL -90 DAY);"
        );
        long affected =0; ;
        try{
            affected = getNamedParameterJdbcTemplate().getJdbcOperations().update(sqlstm_delete.toString());
            logger.debug("Remove record from TransactionHistory  - Rows affected: " + affected);
        } catch (Exception exp)  {
            logger.error("ERROR Remove record from TransactionHistory " +exp.getMessage() );
        }
        return affected;

    }

    public long  deleteTransactionHistoryForPeriodArchive(
			int days) {
            String numberOfDays = Integer.toString(days);

        logger.debug("Remove all records from TransactionHistory except keep history for days = " + days);
        StringBuffer sqlstm_archive = new StringBuffer();
        StringBuffer sqlstm_delete = new StringBuffer();
        sqlstm_archive.append(
            "INSERT INTO TransactionHistoryArchive SELECT * FROM TransactionHistory " +
                    "WHERE transactionTime < DATE_ADD(Now(), INTERVAL -" + numberOfDays + " DAY);"
        );
        sqlstm_delete.append(
            "DELETE FROM TransactionHistory " +
                    "WHERE transactionTime < DATE_ADD(Now(), INTERVAL -" + numberOfDays + " DAY);"
        );
        try{
            getJdbcTemplate().update(sqlstm_archive.toString());
            logger.debug("Archive record from TransactionHistory  to TransactionHistoryArchive" );
        }catch (Exception exp)  {
            logger.error("ERROR Archiving record from TransactionHistory  to TransactionHistoryArchive" );
        }
        long affected =0; ;
        try{
            affected = getNamedParameterJdbcTemplate().getJdbcOperations().update(sqlstm_delete.toString());
            logger.debug("Remove record from TransactionHistory  - Rows affected: " + affected);
        } catch (Exception exp)  {
            logger.error("ERROR Remove record from TransactionHistory" );
        }
        return affected;

    }
}
