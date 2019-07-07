/**
 * 
 */
package com.vidyo.service.passwdhistory;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ganesh
 * 
 */
public interface MemberPasswordHistoryService {

	/**
	 * Returns the number of times password has been changed by the user
	 * 
	 * @param memberID
	 * @return
	 */
	public int getPasswordChangeCount(int memberID);
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
	public Date getLatestPasswordChangeDate(int memberID);
	
	/**
	 * 
	 * @param daysCount
	 * @return
	 */
	public List<Member> getPasswordExpiryingMembers(int daysCount);
	
	public Calendar getPasswordExpiryDate(Configuration config, int memberID, int tenantID) throws Exception;
	
	public boolean cleanMemberPasswordHistory();
}
