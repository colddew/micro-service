package edu.ustc.server.mq.kafka;

import edu.ustc.server.config.KafkaProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.producer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	private Producer<String, String> producer;
	private Boolean isAsync = false;
	
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

	@Scheduled(cron = "0/2 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void sendMessagge() throws Exception {
		sendMessagge("hello world...");
	}
	
	public void sendMessagge(String message) throws Exception {

		String key = RandomStringUtils.randomAlphanumeric(10);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(kafkaProperties.getTopic(), key, message);

		long startTime = System.currentTimeMillis();
		if(isAsync) {

			producer.send(producerRecord, (metadata, exception) -> {
				long elapsedTime = System.currentTimeMillis() - startTime;
				if (metadata != null) {
					System.out.println(String.format("message( %s, %s ) sent to partition( %d ), offset( %d ) in %d ms",
							key, message, metadata.partition(), metadata.offset(), elapsedTime);
				} else {
					exception.printStackTrace();
				}
			});
		} else {
			RecordMetadata metadata = producer.send(producerRecord).get();
			long elapsedTime = System.currentTimeMillis() - startTime;
			if (metadata != null) {
				System.out.println(String.format("message( %s, %s ) sent to partition( %d ), offset( %d ) in %d ms",
						key, message, metadata.partition(), metadata.offset(), elapsedTime);
			}
		}
	}

	@PreDestroy
	private void destory() {
		if(null != producer) {
			producer.close();
		}
	}
}
