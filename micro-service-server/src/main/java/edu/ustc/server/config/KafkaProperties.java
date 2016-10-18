package edu.ustc.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "microservice.kafka")
public class KafkaProperties {

	private String brokerList;
	private String topic;
	private String keySerializer;
	private String valueSerializer;
	private String acks;
	private String requestTimeout;
	private String zookeeperConnect;
	private String groupId;
	private String zookeeperSessionTimeout;
	private String zookeeperSyncTime;
	private String autoCommitInterval;

	public String getBrokerList() {
		return brokerList;
	}

	public void setBrokerList(String brokerList) {
		this.brokerList = brokerList;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}

	public String getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public String getAcks() {
		return acks;
	}

	public void setAcks(String acks) {
		this.acks = acks;
	}

	public String getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(String requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public String getZookeeperConnect() {
		return zookeeperConnect;
	}

	public void setZookeeperConnect(String zookeeperConnect) {
		this.zookeeperConnect = zookeeperConnect;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getZookeeperSessionTimeout() {
		return zookeeperSessionTimeout;
	}

	public void setZookeeperSessionTimeout(String zookeeperSessionTimeout) {
		this.zookeeperSessionTimeout = zookeeperSessionTimeout;
	}

	public String getZookeeperSyncTime() {
		return zookeeperSyncTime;
	}

	public void setZookeeperSyncTime(String zookeeperSyncTime) {
		this.zookeeperSyncTime = zookeeperSyncTime;
	}

	public String getAutoCommitInterval() {
		return autoCommitInterval;
	}

	public void setAutoCommitInterval(String autoCommitInterval) {
		this.autoCommitInterval = autoCommitInterval;
	}
}
