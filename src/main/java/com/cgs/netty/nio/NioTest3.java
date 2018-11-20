package com.cgs.netty.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest3<psvm> {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        socketChannel.socket().bind(address);

        ByteBuffer[] buffers = new ByteBuffer[3];
        int messageLenth = 2 + 3 + 4;
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);
        SocketChannel socket = socketChannel.accept();
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLenth) {
                long r = socket.read(buffers);
                byteRead += r;
                System.out.println("byteRead:" + byteRead);
                Arrays.asList(buffers).stream().map(buffer -> "position:" + buffer.position() + "limit:" + buffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(buffers).stream().forEach(buffer -> buffer.flip());
            int byteWritten = 0;
            while (byteWritten < messageLenth) {
                long r = socket.write(buffers);
                byteWritten += r;
            }
            Arrays.asList(buffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead:" + byteRead + "byteWritten:" + byteWritten + "messageLen:" + messageLenth);
        }
    }
}
