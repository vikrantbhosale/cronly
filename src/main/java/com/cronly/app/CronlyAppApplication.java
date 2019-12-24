package com.cronly.app;

import com.cronly.app.jobs.CronJob;
import com.cronly.app.service.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.cronly.app")
public class CronlyAppApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CronlyAppApplication.class, args);

//		JobDetail job = JobBuilder.newJob(CronJob.class)
//				.withIdentity("dummyJobName", "group1").build();
//
//
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.withIdentity("dummyTriggerName", "group1")
//				.withSchedule(
//						CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//				.build();
////
////		//schedule it
//		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//		scheduler.start();
//		scheduler.scheduleJob(job, trigger);
	}

}
