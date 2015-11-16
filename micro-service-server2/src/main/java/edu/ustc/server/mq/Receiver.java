package edu.ustc.server.mq;

public class Receiver {
	
	public void receiveMessage(String message) {
		System.out.println("receive message is : " + message);
	}
}