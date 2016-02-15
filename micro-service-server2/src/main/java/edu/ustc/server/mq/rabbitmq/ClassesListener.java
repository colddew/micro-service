package edu.ustc.server.mq.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import edu.ustc.server.pojo.Classes;

@Service
public class ClassesListener {
	
	@Autowired
	private Receiver receiver;
	
	@RabbitListener(queues = "${classes.queue.name}")
	public void handle(Message message) {
		
		Classes classes = JSON.parseObject(message.getBody(), Classes.class);
		
		receiver.receiveMessage(JSON.toJSONString(classes));
	}
}
