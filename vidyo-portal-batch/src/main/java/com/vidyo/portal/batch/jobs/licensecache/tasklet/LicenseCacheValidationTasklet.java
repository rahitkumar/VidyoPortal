/**
 * 
 */
package com.vidyo.portal.batch.jobs.licensecache.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.portal.batch.jobs.licensecheck.tasklet.LicenseExpiryReminderTasklet;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.system.SystemService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author ysakurikar
 *
 */
public class LicenseCacheValidationTasklet implements Tasklet, InitializingBean {

	/**
	 * 
	 */
	protected final Logger logger = LoggerFactory.getLogger(LicenseExpiryReminderTasklet.class);

	/**
	 * 
	 */
	private LicensingService licensingService;
	
	/**
	 * @param licensingService
	 */
	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(licensingService, "LicensingService cannot be null");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Executing LicenseCacheValidationTasklet...");
		if (logger.isDebugEnabled()){
			logger.debug("Loading Cache by calling the license dao.");
		}
		
		boolean removedCache = this.licensingService.removeLicenseCacheData();
		
		if (removedCache){
			if (logger.isDebugEnabled()){
				logger.debug("Removed all elements from licenseDataCache.");
			}
		}
        logger.info("Execution for LicenseCacheValidationTasklet completed.");
		return RepeatStatus.FINISHED;
	}

}
