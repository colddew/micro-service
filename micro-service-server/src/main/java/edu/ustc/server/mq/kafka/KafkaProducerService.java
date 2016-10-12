package edu.ustc.server.mq.kafka;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.ustc.server.config.KafkaProperties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	private Producer<String, String> producer;
	
	@PostConstruct
	private void init() {
		
		Properties props = new Properties();
		props.setProperty("metadata.broker.list", kafkaProperties.getServerAddress());
		props.setProperty("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(props);
		producer = new Producer<String, String>(config);
	}
	
	@PreDestroy
	private void destory() {
		if(null != producer) {
			producer.close();
		}
	}
	
//	@Scheduled(cron = "0/2 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void sendMessagge() {
		sendMessagge("hello world...");
	}
	
	public void sendMessagge(String message) {
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(kafkaProperties.getTopic(), message);
		producer.send(data);
	}
}
