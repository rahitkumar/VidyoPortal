/**
 * 
 */
package com.vidyo.portal.batch.jobs.inactiveuser.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.vidyo.service.IMemberService;

/**
 * @author Ganesh
 * 
 */
public class UserInactiveMarkingTasklet implements Tasklet, InitializingBean {
	
	/**
	 * 
	 */
	protected static final Logger logger = Logger
			.getLogger(UserInactiveMarkingTasklet.class);	

	/**
	 * 
	 */
	private IMemberService memberService;

	/**
	 * 
	 */
	private int inactiveDaysLimit;

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param inactiveDaysLimit
	 *            the inactiveDaysLimit to set
	 */
	public void setInactiveDaysLimit(int inactiveDaysLimit) {
		this.inactiveDaysLimit = inactiveDaysLimit;
	}

	/**
	 * 
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(memberService, "MemberService cannot be null");
		Assert.notNull(inactiveDaysLimit,
				"Inactive Days Limit value cannot be null");
	}

	/**
	 * 
	 */
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {		
		int updateCount = 0; //memberService
				//.markMembersAsInactive(inactiveDaysLimit);
		if(logger.isDebugEnabled()) {
			logger.debug("Number of Users marked inactive ->"+ updateCount);
		}
		return RepeatStatus.FINISHED;
	}

}
