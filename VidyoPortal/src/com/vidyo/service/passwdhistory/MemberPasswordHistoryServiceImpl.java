/**
 * 
 */
package com.vidyo.service.passwdhistory;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.db.passwdhistory.IMemberPasswordHistoryDAO;
import com.vidyo.portal.user.v1_1.GeneralFault;
import com.vidyo.portal.user.v1_1.GeneralFaultException;
import com.vidyo.service.IMemberService;
import com.vidyo.service.SystemServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ganesh
 *
 */
public class MemberPasswordHistoryServiceImpl implements MemberPasswordHistoryService {
	
	protected final Logger logger = LoggerFactory.getLogger(MemberPasswordHistoryServiceImpl.class.getName());
	
	private IMemberService memberService;
	
	/**
	 * 
	 */
	private IMemberPasswordHistoryDAO memberPasswordHistoryDAO;
	
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param memberPasswordHistoryDAO the memberPasswordHistoryDAO to set
	 */
	public void setMemberPasswordHistoryDAO(
			IMemberPasswordHistoryDAO memberPasswordHistoryDAO) {
		this.memberPasswordHistoryDAO = memberPasswordHistoryDAO;
	}

	/**
	 * Returns the number of times password has been changed by the user.
	 * Maximum will be 10
	 * 
	 * @param memberID
	 * @return
	 */
	public int getPasswordChangeCount(int memberID) {
		return memberPasswordHistoryDAO.getPasswordChangeCount(memberID);
	}
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */	
	public Date getLatestPasswordChangeDate(int memberID) {
		return memberPasswordHistoryDAO.getLatestPasswordChangeDate(memberID);
	}
	
	/**
	 * 
	 * @param daysCount
	 * @return
	 */	
	//@Override
	public List<Member> getPasswordExpiryingMembers(int daysCount) {
		return memberPasswordHistoryDAO.getPasswordExpiryingMembers(daysCount);
	}

	@Override
	public Calendar getPasswordExpiryDate(Configuration config, int memberID, int tenantID) throws Exception {
		String days = config.getConfigurationValue();
        int daysCount = Integer.parseInt(days.trim());
        if(daysCount ==0) return null;
        Date lastChangeDate = getLatestPasswordChangeDate(memberID);
        Calendar now = Calendar.getInstance();
        if (lastChangeDate != null) {
            now.setTime(lastChangeDate);
            now.add(Calendar.DATE, daysCount);
        } else {
            long creationTime = 0;
            creationTime = memberService.getMemberCreationTime(memberID, tenantID);
            now.setTimeInMillis((long)(creationTime * 1000));
            now.add(Calendar.DATE, daysCount);
        }
        
        return now;
	}
	
	@Override
	public boolean cleanMemberPasswordHistory() {
		boolean retVal = true;
		try {
			memberPasswordHistoryDAO.cleanMemberPasswordHistory();
		} catch (Exception e) {
			logger.error("Error occured during trncating MemberPasswordHistory table: " + e.getMessage());
			retVal = false;
		}
		
		return retVal;
	}
}
