package com.mrdu.simple.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQConfig {
	
	public static final String URL = "tcp://47.94.194.27:61616";
	public static final String QUEUE_NAME = "queue";
	public static final String TOPIC_NAME = "topic";
	public static final String USER = ActiveMQConnectionFactory.DEFAULT_USER;
	public static final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
	public static final String MESSAGE = "Hello World！";

}
