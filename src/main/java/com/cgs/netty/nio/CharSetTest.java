package com.cgs.netty.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 1.为什么使用utf-8及iso-8859-1 正常
 * 2.深入理解utf-8 unicode utf-16 utf-16Le utf-16Be
 */
public class CharSetTest {

    public static void main(String[] args) throws Exception {
        String input = "input.txt";
        String output = "outpu.txt";


        RandomAccessFile inputRandom = new RandomAccessFile(input, "r");
        RandomAccessFile outputRandom = new RandomAccessFile(output, "rw");


        long inputLen = new File(input).length();
        FileChannel inputChannel = inputRandom.getChannel();
        FileChannel outputChannel = outputRandom.getChannel();
        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLen);


        System.out.println("---------------------");
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
        System.out.println("---------------------");

        Charset charSet = Charset.forName("iso-8859-1");//utf-8、iso-8859-1仍然可以正常显示中文
        CharsetDecoder decoder = charSet.newDecoder();//字节数组转换成字符串
        CharsetEncoder encoder = charSet.newEncoder();//字符串转换成字节数组
        CharBuffer charBuffer = decoder.decode(inputData);

        System.out.println(charBuffer.get(0));

        ByteBuffer outputData = encoder.encode(charBuffer);


        outputChannel.write(outputData);
        inputRandom.close();
        outputRandom.close();


    }
}
