/**
 * 
 */
package com.vidyo.portal.batch.jobs.cdr.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.vidyo.service.cdrcollection.CdrCollectionService;

/**
 * Limits the number of records in the CDR table. The number can be configured
 * through the job configuration file.
 * 
 * @author Ganesh
 * 
 */
public class CdrLimitingTasklet implements Tasklet {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(CdrLimitingTasklet.class);

	/**
	 * 
	 */
	private CdrCollectionService cdrCollectionService;

	/**
	 * 
	 */
	private int cdrLimit;

	/**
	 * @param cdrCollectionService
	 *            the cdrCollectionService to set
	 */
	public void setCdrCollectionService(CdrCollectionService cdrCollectionService) {
		this.cdrCollectionService = cdrCollectionService;
	}

	/**
	 * @param cdrLimit
	 *            the cdrLimit to set
	 */
	public void setCdrLimit(int cdrLimit) {
		this.cdrLimit = cdrLimit;
	}

	/**
	 * Retrieves the total count of CDR and calculates the number of oldest
	 * records to be deleted, if the total count exceeds the threshold limit.
	 * 
	 */
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		// Get the total number of CDRs
		int totalCount = cdrCollectionService.getTotalCdrCount();

		if (totalCount <= cdrLimit) {
			logger.info("Total Count {} is less than or equal to the CDR limit {}", totalCount, cdrLimit);
			return RepeatStatus.FINISHED;
		}

		// Calculate the number of rows to be deleted
		int limit = totalCount - cdrLimit;
		if (limit <= 0) {
			logger.info("CDR Limit not reached {}", limit);
			return RepeatStatus.FINISHED;
		}

		int deleteCount = cdrCollectionService.deleteCdr(limit);

		logger.info("Number of CDR records deleted {}", deleteCount);
		return RepeatStatus.FINISHED;
	}

}
