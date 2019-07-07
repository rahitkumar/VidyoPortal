/**
 * 
 */
package com.vidyo.portal.batch.jobs.transactioncleanup.tasklet;
import com.vidyo.service.ISystemService;
import com.vidyo.service.transaction.TransactionService;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


/**
 * This job cleans TransactionHoistory
 * 
 * @author Gokul
 * 
 */
public class TransactionCleanupTasklet implements Tasklet {

	/**
	 * 
	 */
	protected static final Logger logger = Logger
			.getLogger(TransactionCleanupTasklet.class);


	private int status;

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	private ISystemService systemService;


	public void setSystemService(
			ISystemService service) {
		this.systemService = service;
	}

    private TransactionService transactionService;


	public void setTransactionService(
			TransactionService service) {
		this.transactionService = service;
	}



	/**
	 * 
	 */
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
		// Step 1 - Get the configuration value for Trnsaction
       /* int transactionHistDaysToKeep = 0;
		com.vidyo.bo.Configuration transactionConf = null;
        try {
				transactionConf = systemService.getConfiguration("TransactionHistryToKeepInDays");
			} catch (Exception exe) {
				logger.error("Portal2.Configuration does not have entry for TransactionHistryToKeepInDays" + exe.getMessage());
			}
		if(null != transactionConf) {
             try {
                transactionHistDaysToKeep = Integer.parseInt(transactionConf.getConfigurationValue());
                 logger.debug("Number of days TransactionHistory to keep = " + transactionHistDaysToKeep);
             } catch (Exception exe) {
				logger.error("Portal2.Configuration table have invalid value for COnfigurationName TransactionHistryToKeepInDays" + exe.getMessage());
                transactionHistDaysToKeep = 0;
			}
        }
        if(transactionHistDaysToKeep ==0) {
            transactionHistDaysToKeep =1;
            logger.info("Minimum 1 day TransactionHistory to keep if there is no configuration Value");
        }
        else if(transactionHistDaysToKeep >90) {
            transactionHistDaysToKeep =90;
            logger.info("Maximum 90 days TransactionHistory to keep if there is no configuration Value");
        }*/
		//Step 2 - Clean the TransactionHistory Table from  portal2audit DB
         long status = -1;
			try {
				status = transactionService.deleteTransactionHistoryForPeriod() ;
			} catch (Exception e) {
				logger.error("Error deleting TransactionHistory - " + e.getMessage());
			}
		 if(status <=0) {
             logger.debug("No TransactionHistory deleted ");
         }
        else {
             logger.debug("Number TransactionHistory deleted = " + status);
         }

		return RepeatStatus.FINISHED;
	}



}
