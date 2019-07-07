/**
 * 
 */
package com.vidyo.db.passwdhistory;

import com.vidyo.bo.Member;

import java.util.Date;
import java.util.List;

/**
 * @author Ganesh
 * 
 */
public interface IMemberPasswordHistoryDAO {

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
	
	public void cleanMemberPasswordHistory();

}
