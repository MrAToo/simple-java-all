package com.mrdu.simple.zk.nativeapi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

public class ZookeeperSimple {
	
	private ZooKeeper zk;
	final CountDownLatch connectedSignal = new CountDownLatch(1);
	
	public ZooKeeper connect(String host) throws IOException, InterruptedException{
		zk = new ZooKeeper(host, 5000, new Watcher() {
			//监控节点变化
		    public void process(WatchedEvent we) {
		    	EventType type = we.getType();
		    	KeeperState state = we.getState();
		    	System.out.println(type);
		    	System.out.println(state);
		       if (we.getState() == KeeperState.SyncConnected) {
		          connectedSignal.countDown();
		       }
		    }
		});
		connectedSignal.await();
		return zk;
	}
	
	public void close() throws InterruptedException{
		zk.close();
	}
	
	public static void main(String[] args) {
		ZookeeperSimple zk = new ZookeeperSimple();
		try {
			ZooKeeper zooKeeper = zk.connect("47.94.194.27");
			
			String path = "/MyFirstZnode";
			byte[] data = "My first zookeeper app".getBytes();
			//删除,指定版本
			zooKeeper.delete(path, zooKeeper.exists(path, true).getVersion());
			/**
			 * 创建Znode
			 * 1,路径
			 * 2,开放协议
			 * 3,持久化模式
			 */
			zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			//判断是否存在
			Stat exists = zooKeeper.exists(path, true);
			//获取数据
			byte[] bs = zooKeeper.getData(path, true, null);
			System.out.println(new String(bs));
			//删除节点,-1表示跳过版本检查
			zooKeeper.delete(path, -1);
			System.out.println("是否存在："+exists);
			zk.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

}
