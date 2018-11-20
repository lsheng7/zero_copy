package com.cgs.netty.ex5;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 数据的文本格式
 * TextWebSocketFrame[阵的形式]
 * @author harry
 *
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
	
		//客户端发送的数据
		System.out.println("收到消息:"+msg.text());
		//回显的数据
		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器的时间: "+LocalDateTime.now()));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        	System.out.println("handlerAdded: "+ctx.channel().id().asLongText());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved: "+ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常发生");
     	ctx.close();
	}
}
