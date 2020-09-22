package com.example.AAprojectWeb1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.AAprojectWeb1.Job.CouponExpirationDailyJob;
import com.example.AAprojectWeb1.repository.CouponRepository;

@SpringBootApplication
public class AAprojectWeb1Application {

	public static void main(String[] args)  {
		ConfigurableApplicationContext ctx =
				SpringApplication.run(AAprojectWeb1Application.class, args);
		
		//JOB THREAD 
		CouponExpirationDailyJob t1 = ctx.getBean(CouponExpirationDailyJob.class);
		t1.start();
		//END OF JOB THREAD
		
		


	}
}
