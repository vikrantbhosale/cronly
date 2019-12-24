package com.cronly.app.jobs;

import com.cronly.app.service.CronService;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Component
public class CronJob {
//        implements Job{

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);

    @Autowired
    CronService cronService;



    @Scheduled(cron = "* * * ? * *")
    @Transactional
    public void cronMonitor() throws JSONException {

        logger.info("**CRONLY Job STARTED**");
        cronService.findAllFailingCrons();
        logger.info("**CRONLY Job ENDED**");
    }


//    @Transactional
//    public void execute(JobExecutionContext context) throws JobExecutionException{
//        logger.info("**CRONLY Job STARTED**");
//        try {
//            cronService.findAllFailingCrons() ;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        logger.info("**CRONLY Job ENDED**");
//    }
}
