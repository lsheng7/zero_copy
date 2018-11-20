package com.cgs.netty.zero.copy;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的服务方式
 * @author harry
 *
 */
public class OlderServer {

	public static void main(String[] args) throws Exception{
     
		ServerSocket serverSocket=new ServerSocket(8899);
		
		while(true) {
			//Listens for a connection to be made to this socket and accepts
		    // it. The method blocks until a connection is made.
			Socket socket=serverSocket.accept();	
			
			//2进制数据
			DataInputStream is=new DataInputStream(socket.getInputStream());
			try {
				byte[] buffer=new byte[4096];

				while(true) {
				    //实际读取到的字节数据
					// If no byte is available because the stream is at end of
				    // file, the value <code>-1</code> is returned
					int len=is.read(buffer, 0, buffer.length);
				    //数据不进行处理
					if(len==-1) {
				    	break;
				    }
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
