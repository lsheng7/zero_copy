package com.cgs.netty.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * 服务器端监听5个端口号
 * 都是存在一个客户端与之连接
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {

        int[] ports=new int[5];
        ports[0]=5000;
        ports[1]=5001;
        ports[2]=5002;
        ports[3]=5003;
        ports[4]=5004;

        //构建Selector
        Selector selector=Selector.open();











    }
}
