package com.cgs.netty.zero.copy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;


public class NioClient {
	
	public static void main(String[] args) throws Exception{
		SocketChannel socketChannel=SocketChannel.open();
		InetSocketAddress inetAddr=new InetSocketAddress("localhost",8899);
		socketChannel.connect(inetAddr);
		

		socketChannel.configureBlocking(true);
		String filePath="D:\\GitHub\\package\\GitHubDesktopSetup.exe";
		
		//获取文件通道
		FileChannel fileChannel=new FileInputStream(filePath).getChannel();
		
		long begin=System.currentTimeMillis();
		//将文件写入通道 完成数据发送
		/**
		 * * Transfers bytes from this channel's file to the given writable byte
	     * channel.
	     *
	     * <p> An attempt is made to read up to <tt>count</tt> bytes starting at
	     * the given <tt>position</tt> in this channel's file and write them to the
	     * target channel.  An invocation of this method may or may not transfer
	     * all of the requested bytes; whether or not it does so depends upon the
	     * natures and states of the channels.  Fewer than the requested number of
	     * bytes are transferred if this channel's file contains fewer than
	     * <tt>count</tt> bytes starting at the given <tt>position</tt>, or if the
	     * target channel is non-blocking and it has fewer than <tt>count</tt>
	     * bytes free in its output buffer.
	     *
	     * <p> This method does not modify this channel's position.  If the given
	     * position is greater than the file's current size then no bytes are
	     * transferred.  If the target channel has a position then bytes are
	     * written starting at that position and then the position is incremented
	     * by the number of bytes written.
	     *
	     * <p> This method is potentially much more efficient than a simple loop
	     * that reads from this channel and writes to the target channel.  Many
	     * operating systems can transfer bytes directly from the filesystem cache
	     * to the target channel without actually copying them.  </p>
	     *
	     * @param  position
	     *         The position within the file at which the transfer is to begin;
	     *         must be non-negative
	     */
		
		long transferCount=fileChannel.transferTo(0, fileChannel.size(),socketChannel);
		//总共发送的数据长度是:8388608耗时:57ms
		System.out.println("总共发送的数据长度是:"+transferCount+"耗时:"+(System.currentTimeMillis()-begin)+"ms");
		
		fileChannel.close();
		
	}

}
