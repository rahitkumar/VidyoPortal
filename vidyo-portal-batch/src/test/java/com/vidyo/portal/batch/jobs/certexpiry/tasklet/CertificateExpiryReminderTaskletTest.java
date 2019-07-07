/**
 * 
 */
package com.vidyo.portal.batch.jobs.certexpiry.tasklet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

/**
 * @author Ganesh
 * 
 */

//@ContextConfiguration(locations = { "classpath:vidyo-portal-batch-job-test-context.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
public class CertificateExpiryReminderTaskletTest {

	private final static Logger logger = LoggerFactory.getLogger(CertificateExpiryReminderTaskletTest.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	private JobParameters jobParameters = new JobParameters();

	/**
	 * @param jobLauncher
	 *            the jobLauncher to set
	 */
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	//@Test
	public void testLaunchJob() throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		System.out.println(job);
		System.out.println(jobLauncher);
		// JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		System.out.println(jobExecution.getStatus());
		sw.stop();
		logger.info(">>> TIME ELAPSED:" + sw.prettyPrint());
	}
}
