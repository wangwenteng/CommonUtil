package com.wwt.commonUtil.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件操作工具类
 */

public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

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
     * 删除文件夹里面的所有文件
     *
     * @param path String  文件夹路径  如  c:/fqf
     */
    public void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath String  文件夹路径及名称  如c:/fqf
     * @return boolean
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);  //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();  //删除空文件夹

        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

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


    /**
     * 递归遍历
     *
     * @param file
     */
    public static void getAllFiles(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                getAllFiles(f);
            } else {
                if (f.getName().endsWith(".java")) {
                    System.out.println(f.getName());
                }

            }
        }
    }

    /**
     * 单个文件移动、重命名
     * 目标文件的上级文件夹必须存在否则会移动失败
     *
     * @param sourceFileFullName 源文件 文件全路径名称
     * @param targetFileFullName 目标文件 文件全路径名称
     * @return
     */
    public static boolean moveFile(String sourceFileFullName, String targetFileFullName) {
        //源文件
        boolean flag = false;
        File sourceFile = new File(sourceFileFullName);
        if (sourceFile.exists() && sourceFile.isFile()) {
            File file = new File(targetFileFullName);
            Path path = Paths.get(targetFileFullName);
            path.isAbsolute();
            //源文件移动至目标文件目录
            if (sourceFile.renameTo(file)) {
                System.out.println("File is moved successful!");//输出移动成功
                flag = true;
            } else {
                System.out.println("File is failed to move !");//输出移动失败
            }
        }
        return flag;
    }
    /**
     * 文件夹或文件移动、重命名
     *
     * @param sourceFilePath
     * @param targetFilePath
     * @return
     */
    public static boolean moveDirectory(String sourceFilePath, String targetFilePath) {
//        if(sourceFilePath)
        return false;
    }



    public static void main(String[] args) {
        System.out.println(moveFile("D:/测试照片/moveFile.jpg", "D:/测试照片/moveFile/moveFile.jpg"));

    }
}
