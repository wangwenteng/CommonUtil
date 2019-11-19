package com.wwt.commonutil.test.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestSocketChannel01 {
    public static void main(String[] args) throws Exception{
        testServer();
        testClient();
    }
    /**
     * 创建服务端
     */
    public static void  testServer()throws Exception{
        //创建ServerSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //让对象在指定端口监听
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        //构建buffer对象用于存储对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //等待客户端的连接
        while (true){
            SocketChannel accept = serverSocketChannel.accept();
            accept.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("server:"+new String(byteBuffer.array()));
            accept.close();
            serverSocketChannel.close();
        }
    }

    /**
     * 创建客户端
     */
    public static void  testClient()throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
        String newData ="new String to write to file ....";
        System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(newData.getBytes());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }
        socketChannel.close();
    }
}
