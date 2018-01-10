package com.mrdu.simple.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorSimple {

	private static final String CONN = "47.94.194.27:2181";
	private static final String PATH = "/test";

	
	public static void main(String[] args) throws Exception {
		//建立连接
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONN,new RetryNTimes(10, 5000));
		//开始
		curatorFramework.start();
		//判断是否存在指定节点,如果不存在,则创建,存在则修改
		if(curatorFramework.checkExists().forPath(PATH)==null){
			curatorFramework.create().creatingParentsIfNeeded().forPath(PATH, "Hello World!".getBytes());
		}else{
			curatorFramework.setData().forPath(PATH, "Hello Curator".getBytes());
		}
		//读取指定节点数据
		byte[] bs = curatorFramework.getData().forPath(PATH);
		System.out.println(new String(bs));
		//删除指定节点
		curatorFramework.delete().forPath(PATH);
	}
}
