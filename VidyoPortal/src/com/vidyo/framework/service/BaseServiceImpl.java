/**
 * 
 */
package com.vidyo.framework.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.service.transaction.TransactionService;

/**
 * @author ganesh
 *
 */
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	private TransactionService transactionService;
	
	/**
	 * index 0 - TransactionName
	 * index 2 - TransactionResult
	 * index 3 - Transaction Params 
	 * @param transactionDetails
	 */
	protected void createTransactionHistory(String... transactionDetails) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName(transactionDetails[0]);
		transactionHistory.setTransactionResult(transactionDetails[1]);
		transactionHistory.setTransactionParams(transactionDetails[2]);
		transactionService.addTransactionHistoryWithUserAndTenantLookup(transactionHistory);
	}	

}
