package edu.ustc.server.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;

import edu.ustc.server.pojo.Classes;

public class Sender {
	
	@Value("${classes.exchange.name}")
	private String exchangeName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Scheduled(cron = "0/2 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void send() {
		
		Classes classes = new Classes();
		classes.setId(1);
		classes.setGrade("一年级");
		
		rabbitTemplate.convertAndSend(exchangeName, null, JSON.toJSONString(classes));
	}
}