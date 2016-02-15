package edu.ustc.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import edu.ustc.server.mq.rabbitmq.Receiver;
import edu.ustc.server.mq.rabbitmq.Sender;

@Configuration
@EnableScheduling
public class RabbitMQConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
	
	@Value("${classes.exchange.name}")
	private String exchangeName;
	
	@Value("${classes.queue.name}")
	private String queueName;
	
	@Bean()
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMultiplier(10.0);
		backOffPolicy.setMaxInterval(10000);
		
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(backOffPolicy);
		
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setRetryTemplate(retryTemplate);
		template.setChannelTransacted(false);
		
		template.setConfirmCallback(new ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if(ack) {
					logger.info("发送消息成功, correlationId={}", correlationData.getId());
				} else {
					logger.info("发送消息失败, correlationId={}, cause={}", correlationData.getId(), cause);
				}
			}
		});
		
		return template;
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setPublisherConfirms(true);
		
		return connectionFactory;
	}
	
	public AmqpAdmin amqpAdmin() {
		
		RabbitAdmin admin = new RabbitAdmin(connectionFactory());
		admin.setAutoStartup(false);
		
		return admin;
	}
	
	@Bean
	public Queue classesQueue() {
		return new Queue(queueName, true, false, false);
	}
	
	@Bean
	public FanoutExchange classesExchange() {
		return new FanoutExchange(exchangeName, true, false);
	}
	
	@Bean
	public Binding classesBinding() {
		return BindingBuilder.bind(classesQueue()).to(classesExchange());
	}
	
	@Bean
	Receiver receiver() {
		return new Receiver();
	}
	
	@Bean
	Sender sender() {
		return new Sender();
	}
}
