/**
 * 
 */
package com.vidyo.portal.batch.jobs.authtokens.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.vidyo.bo.Configuration;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;
import com.vidyo.service.system.SystemService;

/**
 * Invalidates the auth tokens not used in the last N number of days specified.<br>
 * Inactive tokens are identified using the last used timestamp of the token.<br>
 * 
 * @author Ganesh
 * 
 */
public class AuthTokensInvalidationTasklet implements Tasklet, InitializingBean {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(AuthTokensInvalidationTasklet.class);

	/**
	 * 
	 */
	private static final String AUTH_TOKEN_INACTIVITY_LIMIT = "AUTH_TOKEN_INACTIVITY_LIMIT";

	/**
	 * 
	 */
	private VidyoPersistentTokenRepository persistentTokenRepository;

	/**
	 * 
	 */
	private SystemService systemService;

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 
	 */
	private int inactiveSecondsCount;

	/**
	 * @param inactiveSecondsCount
	 *            the inactiveSecondsCount to set
	 */
	public void setInactiveSecondsCount(int inactiveSecondsCount) {
		this.inactiveSecondsCount = inactiveSecondsCount;
	}

	/**
	 * @param persistentTokenRepository
	 *            the persistentTokenRepository to set
	 */
	public void setPersistentTokenRepository(VidyoPersistentTokenRepository persistentTokenRepository) {
		this.persistentTokenRepository = persistentTokenRepository;
	}

	/**
	 * 
	 */
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		Configuration inactivityConfig = systemService.getConfiguration(AUTH_TOKEN_INACTIVITY_LIMIT);
		if (inactivityConfig != null && inactivityConfig.getConfigurationValue() != null) {
			try {
				inactiveSecondsCount = Integer.valueOf(inactivityConfig.getConfigurationValue().trim());
			} catch (NumberFormatException e) {
				logger.error("Config value for auth token inactivity limit is invalid -"
						+ inactivityConfig.getConfigurationValue() + " Using the default value -"
						+ inactiveSecondsCount);
			}
		}
		if (inactiveSecondsCount <= 0) {
			logger.warn("Inactive days count is not configured - {}", inactiveSecondsCount);
			return RepeatStatus.FINISHED;
		}
		int deletedCount = persistentTokenRepository.removeUserTokens(inactiveSecondsCount);
		logger.debug("Number of inactive auth tokens deleted - {}", deletedCount);

		return RepeatStatus.FINISHED;
	}

	/**
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(persistentTokenRepository, "VidyoPersistentTokenRepository cannot be null");
	}

}
