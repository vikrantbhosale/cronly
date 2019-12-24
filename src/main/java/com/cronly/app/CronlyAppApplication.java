package com.cronly.app;

import com.cronly.app.service.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CronlyAppApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CronlyAppApplication.class, args);

		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity("dummyJobName", "group1").build();

		//Quartz 1.6.3
		//CronTrigger trigger = new CronTrigger();
		//trigger.setName("dummyTriggerName");
		//trigger.setCronExpression("0/5 * * * * ?");

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName", "group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.build();

		//schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

}
