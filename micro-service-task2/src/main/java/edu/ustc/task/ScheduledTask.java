package edu.ustc.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ScheduledTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(ScheduledTask.class, args);
		ScheduledTask task = context.getBean(ScheduledTask.class);
		task.run();
	}
	
	public void run() throws Exception {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		System.out.println("scheduled task running...");
		logger.info("scheduled task running...");
		
		executor.shutdown(); 
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
	}
}
