package edu.ustc.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import edu.ustc.server.mq.Receiver;
import edu.ustc.server.mq.Sender;

@Configuration
@EnableScheduling
public class RabbitMQConfig {
	
	@Value("${classes.exchange.name}")
	private String exchangeName;
	
	@Value("${classes.queue.name}")
	private String queueName;
	
	@Bean
	public Queue classesQueue() {
		return new org.springframework.amqp.core.Queue(queueName, true, false, false);
	}
	
	@Bean
	public FanoutExchange classesExchange() {
		return new FanoutExchange(exchangeName, true, false);
	}
	
	@Bean
	public Binding personBinding() {
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
