package com.mrdu.simple.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;


/**
 * 
 * 使用阻塞式NIO实现网络通信
 */
public class BlockNetSimple {

	
	/**
	 * 服务端
	 */
	@Test
	public void server() throws IOException{
		//得到ServerSocketChannel通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//绑定端口
		serverSocketChannel.bind(new InetSocketAddress(8888));
		//得到客户端通道
		SocketChannel socketChannel = serverSocketChannel.accept();
		//读取数据
		FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\2.png"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//循环将客户端通道中的数据读取到缓冲区
		while (socketChannel.read(buffer)!=-1) {
			//将缓冲区设置为读模式
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		}
		//关闭通道
		serverSocketChannel.close();
		socketChannel.close();
	}
	
	
	/**
	 * 客户端
	 */
	@Test
	public void client() throws IOException{
		//获取客户端和服务器端链接通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
		FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\1.png"), StandardOpenOption.READ);
		//创建一个1204字节的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//写入数据到缓冲区
		while(inChannel.read(buffer)!=-1){
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		//获取当前时间
		//LocalDateTime.now();
		//关闭链接
		inChannel.close();
		socketChannel.close();
	}
}
