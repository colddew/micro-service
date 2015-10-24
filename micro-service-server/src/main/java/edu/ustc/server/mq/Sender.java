package edu.ustc.server.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {
	
	private static final String QUEUE_NAME = "spring-boot";
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
//	@Scheduled(cron = "0/2 * *  * * ? ")
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void send() {
		rabbitTemplate.convertAndSend(QUEUE_NAME, "hello rabbitmq...");
	}
}