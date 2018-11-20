package com.cgs.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 服务端
 */
public class NioTest5 {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        //样板式代码
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//创建serverSocketChannel对象
        serverSocketChannel.configureBlocking(false);//配置非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket();//获取serverSocket对象
        serverSocket.bind(new InetSocketAddress(8899));//监听8899端口号

        Selector selector = Selector.open();//创建Selector对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//将ServerSocketChannel注册到selector上并关注Accept事件

        //服务器监听
        while (true) {//事件处理
            try {
                int numbers = selector.select();//阻塞方法 只有当关注的事件发生 numbers表示关注的事件数量
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();//获取所有的事件集合

                selectionKeySet.forEach(selectionKey -> {

                    final SocketChannel client;
                    //判断事件类型
                    try {
                        //表示客户端向服务端发送连接
                        if (selectionKey.isAcceptable()) {
                            //因为注册的时候只是将ServerSocketChannel注册上去的 因此可以进行向下进行类型转换
                            ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();//获取Channel对象
                            client = serverChannel.accept();//接收向ServerSocketChannel发送的一个连接
                            client.configureBlocking(false);//配置成非阻塞
                            client.register(selector, SelectionKey.OP_READ);//注册事件 关注读取事件

                            //记录客户端的信息 为了保证下面的消息分发
                            String key = "[" + UUID.randomUUID().toString() + "]";//生成key值
                            clientMap.put(key, client);

                            //将key移除
//                            selectionKey.cancel();
                        } else if (selectionKey.isReadable()) {
                            //因为只对SockerChannel注册Read事件
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            if (count > 0) {//表示存在数据读入
                                readBuffer.flip();//为了向客户端写数据
                                Charset charset = Charset.forName("UTF-8");//定义编码
                                String receiveMsg = String.copyValueOf(charset.decode(readBuffer).array());//将数据解码并将其转换成char[]
                                System.out.println(client + ":" + receiveMsg);


                                String sendKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    //通过value反推得到发送消息的那个key
                                    if (client == entry.getValue()) {
                                        sendKey = entry.getKey();//获取到发送消息的key
                                        continue;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {

                                    //不需要向发送者返回它发送的消息
                                    if (entry.getKey().equalsIgnoreCase(sendKey)) {
                                        continue;
                                    }
                                    SocketChannel receiver = entry.getValue();
                                    ByteBuffer writerBuffer = ByteBuffer.allocate(1024);
                                    writerBuffer.put((sendKey + ":" + receiveMsg).getBytes());
                                    writerBuffer.flip();
                                    receiver.write(writerBuffer);
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                selectionKeySet.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
