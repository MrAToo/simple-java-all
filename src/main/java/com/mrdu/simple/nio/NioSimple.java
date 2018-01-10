package com.mrdu.simple.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 一.缓冲区
 * Buffer 参数信息
 * 1.capacity:缓冲区大小
 * 2.limit:缓冲区可读写限制,最大等于capacity
 * 3.position:当前位置,如:读数据时,标记读到哪个位置,最大可以为capacity-1
 * 4.mark:标记,记录position的文字
 * 以上四个变量始终遵循如下规则
 * 0 <= mark <= position <= limit <= capacity
 * 
 * 二.通道
 * java.nio.channels.Channel 接口
 * 
 * 	1.获取通道的三种方式
 * 		1).getChannel()方法.
 * 			本地IO
 * 			FIleInputSteam/FileOutputSteam,RandomAccessFile
 * 			网络IO
 * 			Socket,ServerSocket,DataGramSocket
 * 		2).JDK1.7中的NIO2,针对各个通道提供一个静态方法open()
 * 		3).JDK1.7中的NIO2,Files工具类的newByteChannel()
 * 
 * 三.通道之间的数据传输
 * 	1.transferFrom()
 * 	2.transferTo()
 * 
 * 四.分散与聚集
 * 	1.分散读取:将一个通道中的数据分散到多个缓冲区中(依次按顺序填满)
 * 	2.聚集写入:将多个缓冲区中的数据聚集到一个通道中(依次按顺序读取)
 */
public class NioSimple {
	
	/**
	 * 缓冲区示例
	 */
	@Test
	public void test1(){
		//定义一个1204字节长度的缓冲区(非直接缓冲区)
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//直接缓冲区
		//ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		//判断是否直接缓冲区
		System.out.println(buffer.isDirect());
		//打印字节信息
		System.out.println("------------------------------");
		System.out.println("capacity:"+buffer.capacity());
		System.out.println("limit:"+buffer.limit());
		System.out.println("position:"+buffer.position());
		//写入数据
		buffer.put("DATA".getBytes());
		//打印字节信息
		System.out.println("------------------------------");
		System.out.println("capacity:"+buffer.capacity());
		System.out.println("limit:"+buffer.limit());
		System.out.println("position:"+buffer.position());
		//切换到读模式
		buffer.flip();
		//打印字节信息
		System.out.println("------------------------------");
		System.out.println("capacity:"+buffer.capacity());
		System.out.println("limit:"+buffer.limit());
		System.out.println("position:"+buffer.position());
		//读出数据
		byte[] bs = new byte[buffer.limit()];
		buffer.get(bs);
		System.out.println(new String(bs));
		//打印字节信息
		System.out.println("------------------------------");
		System.out.println("capacity:"+buffer.capacity());
		System.out.println("limit:"+buffer.limit());
		System.out.println("position:"+buffer.position());
	}
	
	
	/**
	 * 利用通道实现读取数据
	 */
	@Test
	public void test2() throws IOException{
		RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\workspace\\nio\\src\\com\\mrdu\\nio\\file.txt", "rw");
		//获取通道
		FileChannel channel = randomAccessFile.getChannel();
		//创建一个缓冲区(大小为48字节)
		ByteBuffer buffer = ByteBuffer.allocate(48);
		//从channel通道中读出数据写入到buffer缓冲区中
		int read = channel.read(buffer);
		System.out.println(read);
		while (read!=-1) {
			//切换到读模式
			buffer.flip();
			while (buffer.hasRemaining()) {
				System.out.println((char)buffer.get());
			}
			//打印字节信息
			System.out.println("------------------------------");
			System.out.println("capacity:"+buffer.capacity());
			System.out.println("limit:"+buffer.limit());
			System.out.println("position:"+buffer.position());
			//清空缓冲区,只是清空记录(limit和position),回到最初状态,clear()或compact()都可以清空,clear清空整个缓冲区,compact清空已经读了的缓冲区
			buffer.clear();
			//打印字节信息
			System.out.println("------------------------------");
			System.out.println("capacity:"+buffer.capacity());
			System.out.println("limit:"+buffer.limit());
			System.out.println("position:"+buffer.position());
			read = channel.read(buffer);
		}
		randomAccessFile.close();
	}
	
	
	/**
	 * 
	 * 利用通道实现文件的复制(使用非直接缓冲区)
	 * @throws IOException 
	 */
	@Test
	public void test3() throws IOException{
		//创建输入输出流
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\ly\\Desktop\\1.png"));
		FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\ly\\Desktop\\2.png"));
		//获取对应的通道
		FileChannel inChannel = fileInputStream.getChannel();
		FileChannel outChannel = fileOutputStream.getChannel();
		//创建一个用于数据存储的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		/**
		 * 文件复制大体步骤
		 * 1,将输入通道中的数据读入到缓冲区
		 * 2,将缓冲区中的数据写入到输出通道
		 */
		while (inChannel.read(buffer)!=-1) {
			//将缓冲区切换到读模式
			buffer.flip();
			//将缓冲区中的数据写入到输出通道中
			outChannel.write(buffer);
			//清空缓冲区,便于下次继续读取
			buffer.clear();
		}
		//关闭流和通道
		if(fileInputStream!=null){
			fileInputStream.close();
		}
		if(fileOutputStream!=null){
			fileOutputStream.close();
		}
		if(inChannel!=null){
			inChannel.close();
		}
		if(outChannel!=null){
			outChannel.close();
		}
	}
	
	
	/**
	 * 利用通道实现文件的复制(使用直接缓冲区)
	 * 直接缓冲区效率高于非直接缓冲区,但没有非直接缓冲区稳定
	 * 注意:直接缓冲区只有ByteBuffer支持
	 * @throws IOException 
	 */
	@Test
	public void test4() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\1.png"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\2.png"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		//使用内存映射文件(此时缓冲区在物理内存中)
		MappedByteBuffer inmap = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outmap = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		//直接操作缓冲区中的数据,不需要通道
		byte[] bs = new byte[inmap.limit()];
		inmap.get(bs);
		outmap.put(bs);
		//关闭通道
		inChannel.close();
		outChannel.close();
	}

	/**
	 * 利用通道实现文件的复制(通道之间的数据传输)
	 */
	@Test
	public void test5() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\1.png"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\ly\\Desktop\\2.png"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		
		//inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		//关闭通道
		inChannel.close();
		outChannel.close();
	}
}
