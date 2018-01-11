package com.mrdu.simple.netty.csmode.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		
		byte [] req = new byte[buf.readableBytes()];  
        
        buf.readBytes(req);  
          
        String message = new String(req,"UTF-8");  
          
        System.out.println("Netty-Server:"+ message);  
	}
	
}
