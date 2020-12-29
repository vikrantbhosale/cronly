package com.cronly.app;

import com.cronly.app.jobs.CronJob;
import com.cronly.app.service.HelloJob;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.net.URISyntaxException;
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
	public static final String ACCOUNT_SID = "ACf6033e80f9c14edebb49fcc4afcae76e";
	public static final String AUTH_TOKEN = "7753f3148159ebbdbb865c064a87e82c";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CronlyAppApplication.class, args);

//
//		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//		Call call = Call.creator(new com.twilio.type.PhoneNumber("+918888196669"),
//				new com.twilio.type.PhoneNumber("+15855750088"),
//				new com.twilio.type.Twiml("<Response>\n" +
//						"<Dial>\n" +
//						"  <Sip>\n" +
//						"vikrant@iomeister.sip.us1.twilio.com\n" +
//						"  </Sip>\n" +
//						"</Dial>\n" +
//						"</Response>"))
//				.create();
//
//		System.out.println(call.getSid());
	}

}
