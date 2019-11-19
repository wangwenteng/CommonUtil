package com.wwt.commonutil.mashibing.juc.interview.a1b2c3;

public class CasTest {

    enum ReadyToRun {T1,T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(start);
        new Thread(()->{

            for (int i = 1;i<=26;i++){
                while (r!=ReadyToRun.T1){}
                System.out.print(i);
                r = ReadyToRun.T2;
            }
        }).start();
        new Thread(()->{

            for (int i = 1;i<=26;i++){
                while (r!=ReadyToRun.T2){}
                System.out.print((char)(i+64));
                r = ReadyToRun.T1;
            }
        }).start();
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println(end - start);
    }
}
