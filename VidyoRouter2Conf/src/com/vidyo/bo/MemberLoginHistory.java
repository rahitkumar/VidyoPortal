/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

public class MemberLoginHistory implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int ID;

	/**
	 * 
	 */
	private int memberID;

	/**
	 * 
	 */
	private String transactionName;

	/**
	 * 
	 */
	private String transactionResult;

	/**
	 * 
	 */
	private String sourceIP = "Unknown";

	/**
	 * 
	 */
	private String transactionTime;

	
	
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the memberID
	 */
	public int getMemberID() {
		return memberID;
	}

	/**
	 * @param memberID
	 *            the memberID to set
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	/**
	 * @return the transactionResult
	 */
	public String getTransactionResult() {
		return transactionResult;
	}

	/**
	 * @param transactionResult
	 *            the transactionResult to set
	 */
	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
	}

	/**
	 * @return the sourceIP
	 */
	public String getSourceIP() {
		return sourceIP;
	}

	/**
	 * @param sourceIP
	 *            the sourceIP to set
	 */
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	/**
	 * @return the transactionTime
	 */
	public String getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime
	 *            the transactionTime to set
	 */
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	/**
	 * @return the transactionName
	 */
	public String getTransactionName() {
		return transactionName;
	}

	/**
	 * @param transactionName
	 *            the transactionName to set
	 */
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

}
