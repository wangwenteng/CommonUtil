package com.wwt.commonUtil.util;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具类
 */
public class FileUtils {
    /**
     * 创建目录和文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File makefile(String path) throws IOException {

        if (path == null || "".equals(path.trim())) {
            return null;
        }
        //本地使用
        //String dirPath = path.substring(0, path.lastIndexOf("\\"));
        //服务器使用
        String dirPath = path.substring(0, path.lastIndexOf("/"));
        int index = path.lastIndexOf(".");
        if (index > 0) { // 全路径，保存文件后缀
            File dir = new File(dirPath);
            if (!dir.exists()) { //先建目录
                dir.mkdirs();
                dir = null;
            }

            File file = new File(path);
            if (!file.exists()) {//再建文件
                file.createNewFile();
            }
            return file;
        } else {
            File dir = new File(dirPath); //直接建目录
            if (!dir.exists()) {
                dir.mkdirs();
                dir = null;
            }
            return dir;
        }

    }

    /**
     * 获取文件或目录的大小
     *
     * @param file
     */
    public static long sizeOf(File file) {
        if (!file.exists()) {
            String message = file + "不存在";
            throw new IllegalArgumentException(message);
        }
        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        } else {
            return file.length();
        }
    }

    /**
     * 获取目录的大小
     *
     * @param directory
     */
    public static long sizeOfDirectory(File directory) {
        if (!directory.exists()) {
            String message = directory + " 不存在";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " 不是目录";
            throw new IllegalArgumentException(message);
        }
        long size = 0;
        File[] files = directory.listFiles();
        if (files == null) {  // 空文件夹
            return 0L;
        }
        for (File file : files) {
            size += sizeOf(file);
        }

        return size;
    }
}
