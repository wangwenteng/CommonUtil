package com.wwt.commonUtil.util;

import com.wwt.commonUtil.constant.Redis;

/**
 * redis工具
 * @Author th
 * @Date 2018年07月09日 下午5:26
 */
public final class RedisUtil {
	
	public static String getKey(Redis prefix,String ... values){
		StringBuffer stringBuffer = new StringBuffer(prefix.getKey());
        for (String value : values) {
            stringBuffer.append(".");
            if (value !=null) {
            stringBuffer.append(value);
            }else{
                stringBuffer.append("null");
            }
        }
        return stringBuffer.toString();

	}

}
