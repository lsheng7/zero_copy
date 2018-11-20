package com.cgs.netty.ex5;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitilizer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipline=ch.pipeline();
		
		
		pipline.addLast(new HttpServerCodec());
		pipline.addLast(new ChunkedWriteHandler());
		pipline.addLast(new HttpObjectAggregator(8192));
        //webcsocket是一种双攻的长连接 
		//html5开始支持
		//发送http请求并upgrade成一个websocket连接
		pipline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipline.addLast(new TextWebSocketFrameHandler());

	}

}
