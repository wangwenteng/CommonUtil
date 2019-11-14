package com.wwt.commonUtil.util.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by lifan on 2019/8/5.
 */
public class FileTest {
    public static void main(String[] args) throws Exception{
//        File file = new File("D:/测试照片/成龙.jpg");
//        System.out.println("file.exists()>>>>"+file.exists());
//        System.out.println("file.isDirectory()>>>>"+file.isDirectory());
//        System.out.println("file.isFile()>>>>"+file.isFile());
        Path move = Files.move(Paths.get("D:/测试照片/moveFile.jpg"), Paths.get("D:/测试照片/moveFile/成龙.jpg"), StandardCopyOption.ATOMIC_MOVE);
        System.out.println(move.toUri());
    }
}
