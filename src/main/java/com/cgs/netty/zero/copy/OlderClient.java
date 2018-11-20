package com.cgs.netty.zero.copy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OlderClient {
	
	public static void main(String[] args) throws Exception{
		
		Socket socket=new Socket("localhost", 8899);
		
		DataOutputStream os=new DataOutputStream(socket.getOutputStream());
		String filePath="D:\\GitHub\\package\\GitHubDesktopSetup.exe";

		InputStream fileInputStream=new FileInputStream(filePath);
		
		byte[] buffer=new byte[4096];
		int total=0;
		int readCount;
	    //开始时间
		long begin=System.currentTimeMillis();
		while(-1!=(readCount=fileInputStream.read(buffer))) {
			total+=readCount;
			os.write(buffer);
		}
		
		/**
		 * 发送总共的字节数据是:80626648
         * 消耗时间:1400ms
		 */
		System.out.println("发送总共的字节数据是:"+total);
		System.out.println("消耗时间:"+(System.currentTimeMillis()-begin)+"ms");
	
	    fileInputStream.close();
	    os.close();
	    socket.close();
	}
}
