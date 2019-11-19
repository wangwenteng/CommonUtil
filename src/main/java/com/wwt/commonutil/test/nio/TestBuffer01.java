package com.wwt.commonutil.test.nio;


import java.nio.ByteBuffer;

public class TestBuffer01 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//JVM分配
//        byteBuffer = ByteBuffer.allocateDirect(1024);//os系统分配
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        byteBuffer.put("hello".getBytes());
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        byteBuffer.put("hello".getBytes());
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        byteBuffer.put("hello".getBytes());
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        byteBuffer.flip();//切换为读模式
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        char b = (char)byteBuffer.get();
        System.out.println(b);
        doPoint(byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity());
        byteBuffer.clear();//清除缓存区
        byteBuffer.compact();//清楚已读数据

    }

    private static void doPoint(int position, int limit, int capacity) {
        System.out.println("position:"+position);
        System.out.println("limit:"+limit);
        System.out.println("capacity:"+capacity);
    }
}
