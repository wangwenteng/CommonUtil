package com.wwt.commonutil.mashibing.juc.interview.a1b2c3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        new Thread(() ->{
            for (int i = 1;i<=26;i++){
                System.out.print(i);
                try {
                    q1.put("ok");
                    q2.take();
                }catch (Exception e){

                }
            }
        }).start();
        new Thread(()->{
            for (int i = 1;i<=26;i++){
                try {
                    q1.take();
                }catch (Exception e){

                }
                System.out.print((char)(i+64));
                try {
                    q2.put("ok");
                }catch (Exception e){

                }
            }
        }).start();
    }
}
