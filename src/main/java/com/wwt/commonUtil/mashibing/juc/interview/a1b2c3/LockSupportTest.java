package com.wwt.commonUtil.mashibing.juc.interview.a1b2c3;

import java.util.concurrent.locks.LockSupport;
/**
 *  两个线程交叉打印，一个打印数字，一个打印小写字母，结果为a1b2...y25z26
 *
 */
public class LockSupportTest {
    static Thread t1 = null,t2 =null;

    public static void main(String[] args) {
        t1 =new Thread(() ->{
            for (int i = 1;i<=26;i++){
                System.out.print(i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t2 = new Thread(()->{
            for (int i = 1;i<=26;i++){
                LockSupport.park();
                System.out.print((char)(i+64));
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }
}
