package com.vidyo.portal.batch.jobs.gateway.tasklet;

import com.vidyo.service.IConferenceService;
import com.vidyo.service.endpoints.EndpointService;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class CleanUpGatewayEndpointsAndConferenceRecordsTasklet implements Tasklet, InitializingBean {

    protected static final Logger logger = Logger.getLogger(CleanUpGatewayEndpointsAndConferenceRecordsTasklet.class);


    private IConferenceService conferenceService;
    private EndpointService endpointService;

    public void setConferenceService(IConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }


    public void setEndpointService(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(conferenceService, "ConferenceService cannot be null");
        Assert.notNull(endpointService, "EndpointService cannot be null");
    }

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {

        int deleted = endpointService.clearRegisterVirtualEndpoint();
        logger.debug("Deleted Offline Virtual Endpoints Count -> " + deleted);

        deleted = conferenceService.clearStaleConferenceRecords();
        logger.debug("Deleted Stale Conference Records Count -> " + deleted);

        return RepeatStatus.FINISHED;
    }
}
