package com.wwt.commonutil.test;


import java.util.ArrayList;
import java.util.List;

public class TestOOM {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            List<byte[]> list = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                list.add(new byte[1024 * 1024]);
            }
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("oom" + (end - start));
        }

    }
}
