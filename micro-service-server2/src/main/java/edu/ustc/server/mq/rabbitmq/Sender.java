package edu.ustc.server.mq.rabbitmq;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;

import edu.ustc.server.pojo.Classes;

public class Sender {
	
	private static final Logger logger = LoggerFactory.getLogger(Sender.class);
	
	@Value("${classes.exchange.name}")
	private String exchangeName;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Scheduled(cron = "0/5 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void send() {
		
		Classes classes = new Classes();
		classes.setId(1);
		classes.setGrade("一年级");
		
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		logger.info("发送消息, correlationId={}, message={}", correlationData.getId(), JSON.toJSONString(classes));
		
		rabbitTemplate.convertAndSend(exchangeName, null, JSON.toJSONString(classes), correlationData);
	}
}