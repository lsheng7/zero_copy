package com.cgs.netty.ex1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline=ch.pipeline();

		//添加编解码器
		pipeline.addLast("httpServerCodec",new HttpServerCodec());
		pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
		
	}
}
