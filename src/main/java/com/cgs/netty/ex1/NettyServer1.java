package com.cgs.netty.ex1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer1 {

	public static void main(String[] args) throws Exception{
		
		//時間循環組是死循環
		//bossGroup是用於接收請求并把請求转交给workerGroup进行处理
		EventLoopGroup bossEventLoop = new NioEventLoopGroup();
		//通常绑定到handler方法上
		EventLoopGroup workerEventLoop = new NioEventLoopGroup();

		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 这里使用了一种编程风格叫做method chain
			serverBootstrap.group(bossEventLoop, workerEventLoop).channel(NioServerSocketChannel.class)
					.childHandler(new TestServerInitializer());//child是针对bossGroup而言的

			//綁定端口并完成服務端的啟動
			//channelFuture用于执行异步的操作 
			//通常异步的处理不需要业务代码执行完成 开始进行下一步操作
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			//netty的优雅的关闭
			//超时 新的连接到达 可以很好的达到优雅关闭的效果
			bossEventLoop.shutdownGracefully();
			workerEventLoop.shutdownGracefully();
		}
	}
}
