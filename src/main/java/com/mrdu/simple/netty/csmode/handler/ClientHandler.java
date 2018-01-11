package com.mrdu.simple.netty.csmode.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ClientHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
        byte [] req = new byte[buf.readableBytes()];  
          
        buf.readBytes(req);  
          
        String message = new String(req,"UTF-8");  
          
        System.out.println("Netty-Client:Receive Message,"+ message);  
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte [] req = "Client Connect Successfully...".getBytes();  
        ByteBuf clientMessage = Unpooled.buffer(req.length);  
        clientMessage.writeBytes(req);  
		ctx.writeAndFlush(clientMessage);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.close();
	}

	
}
