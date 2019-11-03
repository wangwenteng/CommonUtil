package com.wwt.commonUtil.serializable;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 当只有少量属性需要序列化时使用以下方式
 * 1、必须显示提供无参的构造函数，如果提供了有参的构造函数，必须重写无参的构造函数
 */
@Slf4j
class Order implements Externalizable {

    private int code;

    private int totalPrice;

    public Order() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    //根据自己的属性需求序列化
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(code);//必须跟要序列化的字段属性一致
        log.info("writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        code = in.readInt();
        log.info("readExternal");
    }

    @Override
    public String toString() {
        return "Order{" +
                "code=" + code +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

public class TestSerializable02 {
    public static void main(String[] args) throws Exception{
        Order order = new Order();
        order.setCode(100);
        order.setTotalPrice(2000);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("a2.txt"));
        out.writeObject(order);
        out.close();

        ObjectInputStream in =new ObjectInputStream(new FileInputStream("a2.txt"));
        Object o = in.readObject();
        System.out.println(o);
        in.close();
    }
}
