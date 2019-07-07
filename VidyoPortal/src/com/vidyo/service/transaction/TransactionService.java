/**
 * 
 */
package com.vidyo.service.transaction;

import com.vidyo.bo.transaction.TransactionFilter;
import com.vidyo.bo.transaction.TransactionHistory;

import java.util.List;

/**
 * @author Ganesh
 * 
 */
public interface TransactionService {

	/**
	 * Inserts a single Transaction History record in to the TransactionHistory
	 * table
	 * 
	 * @param transactionHistory
	 *            Business Object holding the transaction data
	 * @return transactionID auto-generated Identifier from the Database
	 */
	public int addTransactionHistory(TransactionHistory transactionHistory);


	public int getTransactionHistoryCountForPeriod(TransactionFilter filter);

	/**
	 * Returns the Transaction History Records based on the filter provided
	 * 
	 * @param filter
	 * @return
	 */
	public List<TransactionHistory> getTransactionHistoryForPeriod(TransactionFilter filter);

	/**
	 * Deletes the Transaction History records for the specified period.
	 * 
	 * @return
	 */
	public long deleteTransactionHistoryForPeriod();

	/**
     * This method would use SecurityContextHolder to get the user<br>
	 * who is performing the operation.
	 * 
	 * @param transactionHistory
	 * @return generated id of the record
	 */
	public int addTransactionHistoryWithUserLookup(TransactionHistory transactionHistory);
	
	/**
     * This method would use TenantContext to get the Tenant Details<br>
	 * and SecurityConextHolder to get the user performing the operation.
	 * 
	 * @param transactionHistory
	 * @return generated id of the record
	 */
	public int addTransactionHistoryWithUserAndTenantLookup(TransactionHistory transactionHistory);

	/**
	 * This method would use TenantContext to get the Tenant Details<br>
	 * and SecurityConextHolder to get the user performing the operation.
	 *
	 * @param transactionHistory
	 * @return generated id of the record
	 */
	public int addTransactionHistoryWithUserAndTenantLookup(TransactionHistory transactionHistory, String username);
}
