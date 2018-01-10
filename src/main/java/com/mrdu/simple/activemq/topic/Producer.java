package com.mrdu.simple.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mrdu.simple.activemq.ActiveMQConfig;

public class Producer {
	
	
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
			Destination destination = session.createTopic(ActiveMQConfig.TOPIC_NAME);
			//创建发送者
			MessageProducer producer = session.createProducer(destination);
			//创建消息
			TextMessage message = session.createTextMessage(ActiveMQConfig.MESSAGE);
			//发送消息
			producer.send(message);
			//提交
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			try {
				if(session!=null){
					session.close();
				}
				if(connection!=null){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
