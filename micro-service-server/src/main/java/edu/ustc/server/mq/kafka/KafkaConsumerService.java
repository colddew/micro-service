package edu.ustc.server.mq.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.ustc.server.config.KafkaProperties;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@Service
public class KafkaConsumerService {
	
	private static final Integer CONSUMER_THREAD_QUANTITY = 3;
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0/10 * *  * * ? ")
//	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void consumeMessage() throws Exception {
		
		ExecutorService executor = Executors.newFixedThreadPool(CONSUMER_THREAD_QUANTITY);
		
		Properties props = new Properties();
		props.put("zookeeper.connect", kafkaProperties.getZookeeperConnect());
		props.put("group.id", kafkaProperties.getGroupId());
		props.put("zookeeper.session.timeout.ms", kafkaProperties.getZookeeperSessionTimeout());
		props.put("zookeeper.sync.time.ms", kafkaProperties.getZookeeperSyncTime());
		props.put("auto.commit.interval.ms", kafkaProperties.getAutoCommitInterval());
		
		ConsumerConfig config = new ConsumerConfig(props);
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(kafkaProperties.getTopic(), CONSUMER_THREAD_QUANTITY);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(kafkaProperties.getTopic());
		for (final KafkaStream stream : streams) {
			executor.submit(new Runnable() {
				@SuppressWarnings("unchecked")
				public void run() {
					ConsumerIterator<byte[], byte[]> it = stream.iterator();
					while (it.hasNext()) {
						System.out.println("#####" + Thread.currentThread() + ":" + new String(it.next().message()) + "#####");
					}
				}
			});
		}
		
		if(null != executor) {
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
		
		if (null != consumer) {
			consumer.shutdown();
		}
	}
}
