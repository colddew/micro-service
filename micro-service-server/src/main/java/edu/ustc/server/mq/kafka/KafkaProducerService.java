package edu.ustc.server.mq.kafka;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.ustc.server.config.KafkaProperties;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	private Producer<String, String> producer;
	
	@PostConstruct
	private void init() {
		
		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBrokerList());
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());
		props.setProperty(ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcks());
		props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProperties.getRequestTimeout());
		
		producer = new KafkaProducer<>(props);
	}
	
	@PreDestroy
	private void destory() {
		if(null != producer) {
			producer.close();
		}
	}
	
	@Scheduled(cron = "0/2 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void sendMessagge() {
		sendMessagge("hello world...");
	}
	
	public void sendMessagge(String message) {
		ProducerRecord<String, String> data = new ProducerRecord<>(kafkaProperties.getTopic(), message);
		producer.send(data);
	}
}
