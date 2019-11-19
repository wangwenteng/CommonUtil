package com.wwt.commonutil.test;


public class TestOne {
    public static void main(String[] args) {

        Long time = System.currentTimeMillis();
        System.out.println(time);
        String hexString = Long.toHexString(time);
        System.out.println(hexString);

        System.out.println(Long.valueOf(hexString, 16));
    }
}
