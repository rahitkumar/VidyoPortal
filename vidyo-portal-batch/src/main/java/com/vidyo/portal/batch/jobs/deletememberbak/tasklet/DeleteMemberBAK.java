package com.vidyo.portal.batch.jobs.deletememberbak.tasklet;

import com.vidyo.bo.Configuration;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Date;

public class DeleteMemberBAK implements Tasklet {

    protected static final Logger logger = Logger
            .getLogger(DeleteMemberBAK.class);


    private IMemberService memberService;

    private ISystemService systemService;

    private static final String MEMBER_BAK_INACTIVE_LIMIT_IN_MINS = "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS";


    public IMemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(IMemberService memberService) {
        this.memberService = memberService;
    }

    public ISystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Configuration configuration = systemService.getConfiguration(MEMBER_BAK_INACTIVE_LIMIT_IN_MINS);

        int inactiveTimeInMins = 360;

        if(configuration != null && StringUtils.isNotBlank(configuration.getConfigurationValue()) && StringUtils.isNumeric(configuration.getConfigurationValue().trim())) {
            inactiveTimeInMins = Integer.parseInt(configuration.getConfigurationValue().trim());
        }

        int totalDeleted = memberService.deleteByCreationTimeBefore(DateUtils.addMinutes(new Date(), - inactiveTimeInMins));


        logger.info("Completed DeleteMemberBAK. Number of BAKs deleted - " + totalDeleted);

        return RepeatStatus.FINISHED;
    }

}
