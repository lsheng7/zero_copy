package com.cgs.netty.ex4;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline channelPipeline=ch.pipeline();
		//IdleStateHandler是netty自帶的handler用於處理讀寫空閒的情況
		//判斷對應的時間來處理對應操作
		//通常用于心跳检测
		channelPipeline.addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS));
		channelPipeline.addLast(new MyServerHandler());
		
		
	}

}
