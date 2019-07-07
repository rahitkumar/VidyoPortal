package com.vidyo.portal.batch.jobs.gateway.tasklet;

import com.vidyo.service.IServiceService;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class PurgeStaleGatewayPrefixesTasklet implements Tasklet, InitializingBean {

    protected static final Logger logger = Logger.getLogger(PurgeStaleGatewayPrefixesTasklet.class);

    private IServiceService serviceService;
    private int maxAgeInSeconds = 300; // 5 min default

    public void setServiceService(IServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public void setMaxAgeInSeconds(int maxAgeInSeconds) {
        this.maxAgeInSeconds = maxAgeInSeconds;
    }

    public int getMaxAgeInSeconds() {
        return maxAgeInSeconds;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(serviceService, "ServiceService cannot be null");
    }

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {

        int deleted = serviceService.clearStaleGatewayPrefixesOlderThan(getMaxAgeInSeconds());
        logger.debug("Deleted stale Gateway Prefixes [older than " + getMaxAgeInSeconds() + "s] -> " + deleted);

        return RepeatStatus.FINISHED;
    }
}
