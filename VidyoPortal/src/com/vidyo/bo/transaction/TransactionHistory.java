/**
 * 
 */
package com.vidyo.bo.transaction;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;

/**
 * Business Object holding the Transaction History Data
 * 
 * @author Ganesh
 * 
 */
public class TransactionHistory implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Auto Generated Transaction ID from Database
	 */
	private int transactionID;

	/**
	 * UserID performing the Transaction
	 */
	private String userID = "Unknown";

	/**
	 * Tenant to which the user belongs
	 */
	private String tenantName = "Unknown";

	/**
	 * Unique Name identifying the Transaction
	 */
	private String transactionName;

	/**
	 * Time of the Transaction
	 */
	private Date transactionTime;

	/**
	 * IP Address from which Transaction is performed
	 */
	private String sourceIP = "Unknown";

	/**
	 * Request Parameters of the Transaction
	 */
	private String transactionParams;
	
	/**
	 * Result of the Transaction
	 */
	private String transactionResult;


	@Override
	public String toString() {
		return getTransactionID()
			+ "," + getUserID()
			+ "," + getTenantName()
			+ "," + getTransactionName()
			+ "," + getTransactionResult()
			+ "," + getTransactionTime()
			+ "," + getSourceIP()
			+ "," + "\"" + getTransactionParams() + "\""
			+ "," + "\n";
	}

    public Map<String, String> toMap() {
        Map<String, String> historyMap = new HashMap<String, String>();
        historyMap.put("timestamp", getTransactionTime().toString());
        historyMap.put("tenant", getTenantName());
        historyMap.put("ip" , getSourceIP());
        historyMap.put("user", getUserID());
        historyMap.put("action", getTransactionName());
        historyMap.put("result", getTransactionResult());
        historyMap.put("params", getTransactionParams());
        return historyMap;
    }
	/**
	 * @return the transactionID
	 */
	public int getTransactionID() {
		return transactionID;
	}

	/**
	 * @param transactionID
	 *            the transactionID to set
	 */
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		String sanitizedUserId = this.userID != null ? this.userID.trim() : this.userID;
		sanitizedUserId = sanitizedUserId != null
				? sanitizedUserId.replace('\n', '_').replace('\r', '_').replace('\t', '_')
				: sanitizedUserId;
		if (StringUtils.isNotBlank(sanitizedUserId)) {
			sanitizedUserId = ESAPI.encoder().encodeForHTML(sanitizedUserId);
		}
		return sanitizedUserId;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName != null ? tenantName.trim().replace('\n', '_').replace('\r', '_').replace('\t', '_')
				: tenantName;
	}

	/**
	 * @param tenantName
	 *            the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	/**
	 * @return the transactionName
	 */
	public String getTransactionName() {
		return transactionName != null ? transactionName.trim().replace('\n', '_').replace('\r', '_').replace('\t', '_')
				: transactionName;
	}

	/**
	 * @param transactionName
	 *            the transactionName to set
	 */
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	/**
	 * @return the transactionTime
	 */
	public Date getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime
	 *            the transactionTime to set
	 */
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	/**
	 * @return the sourceIP
	 */
	public String getSourceIP() {
		return sourceIP != null ? sourceIP.trim().replace('\n', '_').replace('\r', '_').replace('\t', '_') : sourceIP;
	}

	/**
	 * @param sourceIP
	 *            the sourceIP to set
	 */
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	/**
	 * @return the transactionParams
	 */
	public String getTransactionParams() {
		return this.transactionParams;
	}

	/**
	 * @param transactionParams
	 *            the transactionParams to set
	 */
	public void setTransactionParams(String transactionParams) {
		this.transactionParams = transactionParams;
	}

	/**
	 * @return the transactionResult
	 */
	public String getTransactionResult() {
		return transactionResult != null ? transactionResult.trim() : transactionResult;
	}

	/**
	 * @param transactionResult the transactionResult to set
	 */
	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
	}

}
