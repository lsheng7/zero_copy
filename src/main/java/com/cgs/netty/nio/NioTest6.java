package com.cgs.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioTest6 {
    public static void main(String[] args) throws IOException {

        //创建一个SocketChannel对象
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));


        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();

            try {
                for (SelectionKey key : selectionKeySet) {

                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();//完成连接
                            ByteBuffer writerBuffer = ByteBuffer.allocate(1024);
                            writerBuffer.put((LocalDateTime.now() + "连接完成").getBytes());
                            writerBuffer.flip();
                            channel.write(writerBuffer);

                            //新开一个线程监听用户的键盘操作
                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {

                                while (true) {
                                    writerBuffer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    String sendMsg = bufferedReader.readLine();
                                    writerBuffer.put(sendMsg.getBytes());
                                    writerBuffer.flip();
                                    channel.write(writerBuffer);
                                }
                            });
                        }
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = channel.read(byteBuffer);
                        if (count > 0) {
                            String receiveMsg = new String(byteBuffer.array(), 0, count);
                            System.out.println(receiveMsg);
                        }
                    }
                    selectionKeySet.clear();//清除掉selectKeySet
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
