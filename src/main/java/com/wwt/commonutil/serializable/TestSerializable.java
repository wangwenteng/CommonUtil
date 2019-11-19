package com.wwt.commonutil.serializable;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
class Point implements Serializable {

    private static final long serialVersionUID = 1289091345401755939L;
    private int x;
    /**
     * transient 当少量属性不需要序列化是，使用次关键字修饰
     */
    private transient int y;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * 进行加密操作
     * @param out
     */
    private void writeObject(ObjectOutputStream out)throws Exception{
        x = x +2;
        out.defaultWriteObject();
        log.info("writeObject");
    }

    /**
     * 解密操作
     * @param in
     * @throws Exception
     */
    private void readObject(ObjectInputStream in)throws Exception{
        in.defaultReadObject();
        x = x -2;
        log.info("readObject");
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
public class TestSerializable {

    public static void main(String[] args) throws Exception {
        Point p = new Point();
        p.setX(10);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("a1.txt"));
        out.writeObject(p);
        out.close();

        ObjectInputStream in =new ObjectInputStream(new FileInputStream("a1.txt"));
        Object o = in.readObject();
        System.out.println(o);
        in.close();

    }
}

