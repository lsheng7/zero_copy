package com.cgs.netty.ex5;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 长连接的例子
 * @author harry
 *
 */
public class MyServer5 {

	
	public static void main(String[] args) throws Exception {
		
		EventLoopGroup boosGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		
		
		try {

			ServerBootstrap serverBootstrap=new ServerBootstrap();
			serverBootstrap.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new WebSocketChannelInitilizer());
			
			
			ChannelFuture channelFuture=serverBootstrap.bind(new InetSocketAddress(8899)).sync();
			channelFuture.channel().closeFuture().sync();
		}finally {
			boosGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
