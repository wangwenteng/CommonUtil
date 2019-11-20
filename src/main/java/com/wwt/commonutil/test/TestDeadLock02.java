package com.wwt.commonutil.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestDeadLock02 {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(2,4,6,8,10));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1,3,7,9,11));

        Thread thread1 = new Thread(new SyncTask02(list1,list2,2));
        Thread thread2 = new Thread(new SyncTask02(list2,list1,9));
        thread1.start();
        thread2.start();
    }
}
