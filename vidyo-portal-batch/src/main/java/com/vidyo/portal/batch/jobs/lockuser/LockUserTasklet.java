/**
 * 
 */
package com.vidyo.portal.batch.jobs.lockuser;

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
public class LockUserTasklet implements Tasklet, InitializingBean {

	/**
	 * 
	 */
	private IMemberService memberService;

	/**
	 * 
	 */
	private int passwordExpiryDaysCount;

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param passwordExpiryDaysCount
	 *            the passwordExpiryDaysCount to set
	 */
	public void setPasswordExpiryDaysCount(int passwordExpiryDaysCount) {
		this.passwordExpiryDaysCount = passwordExpiryDaysCount;
	}

	/**
	 * 
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(memberService, "MemberService cannot be null");
		Assert.notNull(passwordExpiryDaysCount,
				"Password Expiry Days Value cannot be null");
	}

	/**
	 * 
	 */
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		int updateCount = 0; //memberService
				//.lockUserPasswordExpiry(passwordExpiryDaysCount);
		return RepeatStatus.FINISHED;
	}

}
