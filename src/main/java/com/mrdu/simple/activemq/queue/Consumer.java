package com.mrdu.simple.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mrdu.simple.activemq.ActiveMQConfig;

public class Consumer {
	
	public static void main(String[] args) {
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConfig.URL);
		//连接句柄
		Connection connection = null;
		//会话句柄
		Session session = null;
		try {
			//创建连接
			connection = connectionFactory.createConnection(ActiveMQConfig.USER, ActiveMQConfig.PASSWORD);
			//开启连接
			connection.start();
			//创建会话
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//创建目标
			Destination destination = session.createQueue(ActiveMQConfig.QUEUE_NAME);
			//创建发送者
			MessageConsumer consumer = session.createConsumer(destination);
			//设置消息监听
			consumer.setMessageListener((message) -> {
				TextMessage textMessage = (TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
