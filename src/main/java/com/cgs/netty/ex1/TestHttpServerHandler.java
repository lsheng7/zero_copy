package com.cgs.netty.ex1;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

//入栈 Inbound 數據流入
//SimpleChannelInboundHandler提供了很多的回調函數
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	// ChannelHandlerContext ctx, HttpObject msg
	// 上下文和请求对象
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

		// 打印msg的Class类型
		System.out.println(msg.getClass());

		// 打印连接的地址
		System.out.println(ctx.channel().remoteAddress());

		if (msg instanceof HttpRequest) {

			HttpRequest httpRequest = (HttpRequest) msg;
			System.out.println("请求方法名:" + httpRequest.method().name());

			URI uri = new URI(httpRequest.uri());

			if ("/favicon.ico".equals(uri.getPath())) {

				System.out.println("請求favicon.ico");
				return;
			}

			// 底层使用ByteBuffer实现 性能比較高
			ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
					content);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

			ctx.writeAndFlush(response);
			ctx.channel().close();

		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channel active");
		super.channelActive(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channel register");
		super.channelRegistered(ctx);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

		System.out.println("handler add");
		super.handlerAdded(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channel inactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

		System.out.println("channel unregister");
		super.channelUnregistered(ctx);
	}

}
