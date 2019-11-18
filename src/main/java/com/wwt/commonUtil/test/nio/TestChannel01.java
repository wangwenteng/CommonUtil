package com.wwt.commonUtil.test.nio;


import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannel01 {
    public static void main(String[] args) throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);//JVM分配
        FileChannel fileChannel = FileChannel.open(Paths.get("README.md"), StandardOpenOption.READ);
//        FileChannel fileChannel = FileChannel.open(Paths.get("E:/three.txt"), StandardOpenOption.READ);
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        byte[] array = byteBuffer.array();
        System.out.println(new String(array));
        fileChannel.close();
    }

    private static void doPoint(int position, int limit, int capacity) {
        System.out.println("position:"+position);
        System.out.println("limit:"+limit);
        System.out.println("capacity:"+capacity);
    }
}
