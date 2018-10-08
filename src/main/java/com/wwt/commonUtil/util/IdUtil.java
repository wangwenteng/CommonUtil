package com.wwt.commonUtil.util;

import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.util.Random;

/**
 * id生成工具
 * @Author th
 * @Date 2018年07月09日 下午5:26
 */
public final class IdUtil {

    /**
     * 序号
     */
    private static Integer SERIAL_NUMBER = new Integer(0);

    /**
     * 序号16进制最大值
     */
    private final static int SERIAL_NUMBER_MAX = new BigInteger("FFFF",16).intValue();

    /**
     * 类型编码
     */
   private final static Integer TYPE_CODING = new Integer(6);

    /**
     * 服务号
     */
   private final static Integer SERVICE_NO = new Integer(1000);


    /**
     * 获取序号
     * @return
     */
   private static String getSerial(){
       SERIAL_NUMBER++;
       if (SERIAL_NUMBER>SERIAL_NUMBER_MAX){
           Random random = new Random();
           SERIAL_NUMBER = new Integer( random.nextInt(10));
       }
       String str =  new BigInteger(String.valueOf(SERIAL_NUMBER),10).toString(16);
       str =  fill(str,4);
      return str;

   }

    /**
     * 获取时间戳
     * @return
     */
   private static String getTime(){
       long time = System.currentTimeMillis()/1000;
       String time16 = new BigInteger(String.valueOf(time),10).toString(16);
       return time16;
   }

    /**
     * 获取进程号
     * @return
     */
   private static String getProcess(){
       String name = ManagementFactory.getRuntimeMXBean().getName();
       String pid = name.split("@")[0];
       String pid16 = new BigInteger(pid,10).toString(16);
       pid16 =  fill(pid16,4);
       return pid16;
   }


    /**
     * 获取服务号
     * @return
     */
   private static String getServerID(){
       String serverId = new BigInteger(String.valueOf(SERVICE_NO),10).toString(16);
       serverId =  fill(serverId,4);
    return serverId;
   }


    /**
     * 自动补位
     * 转换16进制,位数不够自动补位,默认是4位
     * @param value
     * @return
     */
   private static String fill(String value,int digits){
       if (value.length()<digits){
           StringBuffer stringBuffer = new StringBuffer();
           for (int i = 0,length = digits-value.length();i<length;i++){
               stringBuffer.append("0");
           }
           stringBuffer.append(value);
           value = stringBuffer.toString();
       }
     return value;
   }


    /**
     * 获取类型编码
     * @return
     */
   private static String getTypeCoding(){
       String typeCoding = new BigInteger(String.valueOf(TYPE_CODING),10).toString(16);
       typeCoding =  fill(typeCoding,4);
      return typeCoding;
   }


    /**
     * 获取一个ID
     * @return
     */
    public static String getId() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(getTime()).append("-")
                .append(getSerial()).append("-")
                .append(getTypeCoding()).append("-")
                .append(getServerID()).append(getProcess());
     return stringBuffer.toString();
    }

}
