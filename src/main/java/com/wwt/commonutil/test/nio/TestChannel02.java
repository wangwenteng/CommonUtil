package com.wwt.commonutil.test.nio;


import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannel02 {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);//JVM分配
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/three.txt"), StandardOpenOption.READ);
        int len = -1;
        do {
            len = fileChannel.read(byteBuffer);
//            System.out.println("切换读写模式");
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                char b = (char) byteBuffer.get();
                System.out.print(b);
            }
            byteBuffer.compact();
        } while (len != -1);
        fileChannel.close();
    }

    private static void doPoint(int position, int limit, int capacity) {
        System.out.println("position:" + position);
        System.out.println("limit:" + limit);
        System.out.println("capacity:" + capacity);
    }
}
