package edu.ustc.server.mq.rabbitmq;

public class Receiver {
	
	public void receiveMessage(String message) {
		System.out.println("receive message is : " + message);
	}
}