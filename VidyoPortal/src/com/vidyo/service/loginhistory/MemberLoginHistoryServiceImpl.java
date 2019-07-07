/**
 * 
 */
package com.vidyo.service.loginhistory;

import com.vidyo.bo.loginhistory.MemberLoginHistory;
import com.vidyo.db.loginhistory.IMemberLoginHistoryDao;

import java.util.Date;
import java.util.List;

/**
 * @author Ganesh
 * 
 */
public class MemberLoginHistoryServiceImpl implements MemberLoginHistoryService {

	/**
	 * 
	 */
	private IMemberLoginHistoryDao memberLoginHistoryDao;

	/**
	 * @param memberLoginHistoryDao
	 *            the IMemberLoginHistoryDao to set
	 */
	public void setMemberLoginHistoryDao(
            IMemberLoginHistoryDao memberLoginHistoryDao) {
		this.memberLoginHistoryDao = memberLoginHistoryDao;
	}

	/**
	 * 
	 * @param memberLoginHistory
	 * @return
	 */
	//@Override
	public int addMemberLoginHistory(MemberLoginHistory memberLoginHistory) {
		return memberLoginHistoryDao.addMemberLoginHistory(memberLoginHistory);
	}

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	//@Override
	public List<MemberLoginHistory> getLoginHistoryDetails(int memberID,
			int tenantID) {
		return memberLoginHistoryDao.getLoginHistoryDetails(memberID, tenantID);
	}

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	//@Override
	public List<MemberLoginHistory> getFailedLoginDetails(int memberID,
			int tenantID) {
		List<MemberLoginHistory> loginHistories = memberLoginHistoryDao
				.getFailedLoginDetails(memberID, tenantID);
		if (loginHistories.isEmpty()) {
			loginHistories = memberLoginHistoryDao.getFailedLoginDetails(
					memberID, 1);
		}
		return loginHistories;
	}

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	//@Override
	public Date getLastLoginTime(int memberID, int tenantID) {
		return memberLoginHistoryDao.getLastLoginTime(memberID, tenantID);
	}

}
