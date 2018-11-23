package com.cgs.netty.zero.copy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * 零拷貝
 * @author harry
 *
 */
public class NioServer {

	
	public static void main(String[] args) throws Exception {
		
		InetSocketAddress inetAddr=new InetSocketAddress(7809);
		ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
		// Retrieves a server socket associated with this channel.
		ServerSocket serverSocket=serverSocketChannel.socket();
		
		//Enable/disable the {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}
//		 * When a TCP connection is closed the connection may remain
//	     * in a timeout state for a period of time after the connection
//	     * is closed (typically known as the {@code TIME_WAIT} state
//	     * or {@code 2MSL} wait state).
//		   连接断开socket属于超时的状态 通过设置这个属性可以使得其他应用可以使用
//		   需要在bind方法绑定之前调用
//	     * For applications using a well known socket address or port
//	     * it may not be possible to bind a socket to the required
//	     * {@code SocketAddress} if there is a connection in the
//	     * timeout state involving the socket address or port.
		serverSocket.setReuseAddress(true);
		serverSocket.bind(inetAddr);
		
		ByteBuffer buffer=ByteBuffer.allocate(4096);
        
		while(true) {
			//代表连接的通道
			//获取到的SocketChannel一定是阻塞模式的
			//值得深究
			SocketChannel socketChannel=serverSocketChannel.accept();
            socketChannel.configureBlocking(true);//默认就是阻塞的
			int readCount=0;
			
			while(-1!=readCount) {
				try {
                 readCount=socketChannel.read(buffer);					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
//				buffer.clear();//清空 防止不必要的数据被写入
				buffer.rewind();//标记归位 倒带到初始状态
			}
		}
	}
}
