/**
 * 
 */
package com.vidyo.portal.batch.jobs.inactiveuser.incrementer;

import java.util.Calendar;
import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;

/**
 * @author Ganesh
 *
 */
public class UserInactiveJobParamsIncrementer implements
		JobParametersIncrementer {

	/**
	 * 
	 */
	public JobParameters getNext(JobParameters parameters) {
		Date date = Calendar.getInstance().getTime();
		//parameters = parameters.getParameters()
		return null;
	}

}
