package com.cgs.netty.ex2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer2 {
	public static void main(String[] args) throws Exception {

		// 時間循環組是死循環
		// bossGroup是用於接收客户端的請求并把請求转交给workerGroup进行处理
		//请求转发
		//new NioEventLoopGroup()分析后发现完成的工作是
		//完成变量的赋值
		//在开发中通过含参数的构造函数设置为1即设置线程数是1
		EventLoopGroup bossEventLoop = new NioEventLoopGroup();
		// 通常绑定到handler方法上
		// 死循环
		EventLoopGroup workerEventLoop = new NioEventLoopGroup();

		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 这里使用了一种编程风格叫做method chain
			//group方法完成成员变量的赋值
			//channel方法接受一个Class对象
			//一般和反射相关
			/**
			 *
			 * 底层会调用下面的方法
			 *
			 * {@link io.netty.channel.ChannelFactory} which is used to create {@link Channel} instances from
			 * when calling {@link #bind()}. This method is usually only used if {@link #channel(Class)}
			 * is not working for you because of some more complex needs. If your {@link Channel} implementation
			 * has a no-args constructor, its highly recommend to just use {@link #channel(Class)} for
			 * simplify your code.
			 */
//			@SuppressWarnings({ "unchecked", "deprecation" })
//			public B channelFactory(io.netty.channel.ChannelFactory<? extends C> channelFactory) {
//				return channelFactory((ChannelFactory<C>) channelFactory);
//			}
			serverBootstrap.group(bossEventLoop, workerEventLoop).channel(NioServerSocketChannel.class)

					///**
					//服务于Channel的请求
					//     * Set the {@link ChannelHandler} which is used to serve the request for the {@link Channel}'s.
					//     */
					//    public ServerBootstrap childHandler(ChannelHandler childHandler) {
					//        if (childHandler == null) {
					//            throw new NullPointerException("childHandler");
					//        }
					//        this.childHandler = childHandler;
					//        return this;
					//    }
					.childHandler(new MyServerInitializer());// child是针对bossGroup而言的

			// 綁定端口并完成服務端的啟動
			// channelFuture用于执行异步的操作
			// 通常异步的处理不需要业务代码执行完成 开始进行下一步操作
//			/**
//			 * Create a new {@link Channel} and bind it.
//			 */
//			public ChannelFuture bind(SocketAddress localAddress) {
//				validate();
//				if (localAddress == null) {
//					throw new NullPointerException("localAddress");
//				}
//				return doBind(localAddress);
//			}
//
//			/**
//			 * Validate all the parameters. Sub-classes may override this, but should
//			 * call the super method in that case.
//			 */
//			@SuppressWarnings("unchecked")
//			public B validate() {
//				if (group == null) { 这个对象是boosGroup group方法设置的

//					throw new IllegalStateException("group not set");
//				}
//				if (channelFactory == null) {//channel方法设置的
//					throw new IllegalStateException("channel or channelFactory not set");
//				}
//				return (B) this;
//			}

//			Netty规范do开头的方法都是私有的
//			内部使用
//			private ChannelFuture doBind(final SocketAddress localAddress) {
//				final ChannelFuture regFuture = initAndRegister();
//				final Channel channel = regFuture.channel();
//				if (regFuture.cause() != null) {
//					return regFuture;
//				}
//
//				if (regFuture.isDone()) {
//					// At this point we know that the registration was complete and successful.
//					ChannelPromise promise = channel.newPromise();
//					doBind0(regFuture, channel, localAddress, promise);
//					return promise;
//				} else {
//					// Registration future is almost always fulfilled already, but just in case it's not.
//					final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
//					regFuture.addListener(new ChannelFutureListener() {
//						@Override
//						public void operationComplete(ChannelFuture future) throws Exception {
//							Throwable cause = future.cause();
//							if (cause != null) {
//								// Registration on the EventLoop failed so fail the ChannelPromise directly to not cause an
//								// IllegalStateException once we try to access the EventLoop of the Channel.
//								promise.setFailure(cause);
//							} else {
//								// Registration was successful, so set the correct executor to use.
//								// See https://github.com/netty/netty/issues/2586
//								promise.registered();
//
//								doBind0(regFuture, channel, localAddress, promise);
//							}
//						}
//					});
//					return promise;
//				}
//			}

			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			// netty的优雅的关闭
			// 超时 新的连接到达 可以很好的达到优雅关闭的效果
			bossEventLoop.shutdownGracefully();
			workerEventLoop.shutdownGracefully();
		}
	}
}
