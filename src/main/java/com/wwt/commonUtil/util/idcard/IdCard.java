package com.wwt.commonUtil.util.idcard;

import com.wwt.commonUtil.util.stringExt.StringUtil;

public class IdCard {
    /**
     * 用户身份证号码的打码隐藏加星号加*
     * <p>18位和非18位身份证处理均可成功处理</p>
     * <p>参数异常直接返回null</p>
     *
     * @param idCardNum 身份证号码
     * @param front     需要显示前几位
     * @param end       需要显示末几位
     * @return 处理完成的身份证
     */
    public static String idMask(String idCardNum, int front, int end) {
        //身份证不能为空
        if (StringUtil.isEmpty(idCardNum)) {
            return null;
        }
        //需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return null;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return null;
        }
        //计算*的数量
        int asteriskCount = idCardNum.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        for (int i = 0; i < asteriskCount; i++) {
            asteriskStr.append("*");
        }
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        System.out.println(regex);
        return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
    }

    //生成很多个*号
    public static String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }
    /**
     * 用户姓名的打码隐藏加星号加*
     *  汉族显示名，不显示姓，姓用*代替
     *  少数民族显示，如：买买提•阿不来提显示“买买提•****”
     * @param name 姓名
     * @return 处理完成的身份证
     */
    public static String nameMask(String name) {
        String result ;
        if (name.length() <= 1) {
            result = "*";
        } else {
            int replaceLength = 0;
            if (name.contains("•") || name.contains("·")) {
                if (name.contains("·")) {
                    replaceLength = name.length() - 1 - name.indexOf("·");
                }
                if (name.contains("•")) {
                    replaceLength = name.length() - 1 - name.indexOf("•");
                }
                result = name.replaceAll("([\\u4e00-\\u9fa5]+[·•])(.*)", "$1" + createAsterisk(replaceLength));
            } else {
                result = name.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(name.length() - 1));
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(idMask("3622011984082436",4,2));
        System.out.println(nameMask("张三"));
        System.out.println(nameMask("张三丰"));
        System.out.println(nameMask("买买提•阿不来提"));
        System.out.println(nameMask("爱新觉罗·赵老大"));
    }
}
