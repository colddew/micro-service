package edu.ustc.server.mq.kafka;

import edu.ustc.server.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaConsumerService {
	
	private static final Integer CONSUMER_THREAD_QUANTITY = 3;
	
	@Autowired
	private KafkaProperties kafkaProperties;

	@Scheduled(cron = "0/10 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void multiThreadConsumeMessage() throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(CONSUMER_THREAD_QUANTITY);

		for (int i = 0; i < CONSUMER_THREAD_QUANTITY; i++) {
			executor.submit(() -> consumeMessage());
		}

		if(null != executor) {
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
	}

	public void consumeMessage() {

		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBrokerList());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getEnableAutoCommit());
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getAutoCommitInterval());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(kafkaProperties.getTopic()));

		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
		for (ConsumerRecord<String, String> record : records) {
			System.out.println(String.format("current thread: %s, [ key: %s, value: %s, partition: %d, offset: %d]",
					Thread.currentThread().toString(), record.key(), record.value(), record.partition(), record.offset()));
		}

		if (null != consumer) {
			consumer.close();
		}
	}
}
