package com.mrdu.simple.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisSimple {
	
	public static final String HOST = "47.94.194.27";
	private Jedis jedis;
	private JedisPool pool;
	
	@Test
	public void jedis(){
		setData();
		Object data = getData();
		System.out.println(data);
	}
	
	
	/**
	 * 设置数据
	 */
	public void setData(){
		jedis.set("name", "dujuncheng");
	}
	
	
	/**
	 * 获取数据
	 */
	public Object getData(){
		return jedis.get("name");
	}

	
	/**
	 * 获取连接
	 */
	@Before
	public void getConn(){
		JedisPoolConfig config = new JedisPoolConfig();
		//最大连接
		config.setMaxTotal(30);
		//最大空闲连接
		config.setMaxIdle(10);
		JedisPool pool = new JedisPool(config, HOST);
		jedis = pool.getResource();
	}
	
	
	/**
	 * 关闭连接
	 */
	@After
	public void close(){
		jedis.close();
		pool.close();
	}
}
