package com.wwt.commonUtil.test;



import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TestStringPlus {
    /**
     * 效率比较
     *
     *
     *
     * 既然有这么多种字符串拼接的方法，那么到底哪一种效率最高呢？我们来简单对比一下。
     *
     * long t1 = System.currentTimeMillis();
     * //这里是初始字符串定义
     * for (int i = 0; i &lt; 50000; i++) {
     *    //这里是字符串拼接代码
     * }
     * long t2 = System.currentTimeMillis();
     * System.out.println("cost:" + (t2 - t1));
     * 我们使用形如以上形式的代码，分别测试下五种字符串拼接代码的运行时间。得到结果如下：
     *
     * + cost:5119
     * StringBuilder cost:3
     * StringBuffer cost:4
     * concat cost:3623
     * StringUtils.join cost:25726
     * 从结果可以看出，用时从短到长的对比是：
     *
     * StringBuilder < StringBuffer < concat < + < StringUtils.join
     * @param args
     */
    public static void main(String[] args) {
//        String str = "start ";
//        for (int i = 0 ; i < 5;i++){
//            str +=i + "hello";
//            System.out.println(str);
//        }
//
//        String wechat = "Hollis";
//        String introduce = "每日更新Java相关技术文章";
//        String hollis = wechat + "," + introduce;
//        System.out.println(hollis);

        /**
         * +                cost:   5119
         * StringBuilder    cost:   3
         * StringBuffer     cost:   4
         * concat           cost:   3623
         * StringUtils.join cost:   25726
         */
        long t1 = System.currentTimeMillis();
        //这里是初始字符串定义
        String str = "start ";
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            str += "hello";
        }
        long t2 = System.currentTimeMillis();
        System.out.println("+ cost:" + (t2 - t1));
        long stringBuilderT1 = System.currentTimeMillis();
        //这里是初始字符串定义
        StringBuilder stringBuilder = new StringBuilder("start");
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            stringBuilder.append("hello");
        }
        long stringBuilderT2 = System.currentTimeMillis();
        System.out.println("stringBuilder cost:" + (stringBuilderT2 - stringBuilderT1));
        long stringBufferT1 = System.currentTimeMillis();
        //这里是初始字符串定义
        StringBuffer stringBuffer = new StringBuffer("start");
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            stringBuffer.append("hello");
        }
        long stringBufferT2 = System.currentTimeMillis();
        System.out.println("stringBuffer cost:" + (stringBufferT2 - stringBufferT1));
        long concatT1 = System.currentTimeMillis();
        //这里是初始字符串定义
        String concatStr = "start ";
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            concatStr.contains("hello");
        }
        long concatT2 = System.currentTimeMillis();
        System.out.println("concat cost:" + (concatT2 - concatT1));
        long stringUtilsT1 = System.currentTimeMillis();
        //这里是初始字符串定义
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            StringUtils.join(list,"hello");
        }
        long stringUtilsT2 = System.currentTimeMillis();
        System.out.println("stringUtils cost:" + (stringUtilsT2 - stringUtilsT1));
        /**
         * + cost:15184
         * stringBuilder cost:4
         * stringBuffer cost:4
         * concat cost:7
         * stringUtils cost:11
         */
    }
}
