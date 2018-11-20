package com.cgs.netty.ex3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		Channel channel = ctx.channel();

		for (Channel ch : channelGroup) {

			if (ch != channel) {

				ch.writeAndFlush(channel.remoteAddress() + "发送的消息:" + msg + "\n");
			} else {
				ch.writeAndFlush("[自己的消息:]" + msg + "\n");
			}
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("服務器-》" + channel.remoteAddress() + "加入\n");
		channelGroup.add(channel);
	}

	// channelGroup会自动移除channel对象 不需要手动进行移除操作
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("服務器-》" + channel.remoteAddress() + "離開\n");
		System.out.println(channelGroup.size());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + "上線！");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() + "下线！");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

}
