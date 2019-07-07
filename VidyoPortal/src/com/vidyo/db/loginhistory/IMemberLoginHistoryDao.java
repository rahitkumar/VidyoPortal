/**
 * 
 */
package com.vidyo.db.loginhistory;

import com.vidyo.bo.loginhistory.MemberLoginHistory;

import java.util.Date;
import java.util.List;

/**
 * @author Ganesh
 * 
 */
public interface IMemberLoginHistoryDao {

	/**
	 * 
	 * @param memberLoginHistory
	 * @return
	 */
	public int addMemberLoginHistory(MemberLoginHistory memberLoginHistory);

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	public List<MemberLoginHistory> getLoginHistoryDetails(int memberID,
                                                           int tenantID);

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	public List<MemberLoginHistory> getFailedLoginDetails(int memberID,
                                                          int tenantID);

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	public Date getLastLoginTime(int memberID, int tenantID);

}
