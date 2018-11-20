package com.cgs.netty.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {

    public static void main(String[] args) throws Exception{

        FileInputStream inputStream=new FileInputStream("NioTest2.txt");
        FileChannel channel = inputStream.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(512);
        //向buffer中写入数据
        channel.read(buffer);

        buffer.flip();


        while(buffer.hasRemaining()){
            System.out.println((char) buffer.get());
        }

    }
}
