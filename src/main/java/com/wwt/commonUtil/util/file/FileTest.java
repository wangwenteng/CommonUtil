package com.wwt.commonUtil.util.file;

import java.io.File;

/**
 * Created by lifan on 2019/8/5.
 */
public class FileTest {
    public static void main(String[] args) {
        File file = new File("D:/测试照片/成龙.jpg");
        System.out.println("file.exists()>>>>"+file.exists());
        System.out.println("file.isDirectory()>>>>"+file.isDirectory());
        System.out.println("file.isFile()>>>>"+file.isFile());

    }
}
