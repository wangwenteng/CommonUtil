package com.wwt.commonUtil.mashibing.juc.interview.a1b2c3;

public class SyncWaitNotifyTest {
    public static void main(String[] args) {
        final Object o = new Object();

        new Thread(()->{
            synchronized (o){
                for (int i =1 ; i<=26 ;i++){
                    System.out.print(i);
                    try {
                        o.notify();
                        o.wait();
                    }catch (Exception e){

                    }
                }
                o.notify();
            }
        }).start();
        new Thread(()->{
            synchronized (o){
                for (int i =1 ; i<=26 ;i++){
                    System.out.print((char)(i+64));
                    try {
                        o.notify();
                        o.wait();
                    }catch (Exception e){

                    }
                }
                o.notify();
            }
        }).start();
    }
}
