package com.wwt.commonutil.leetcode;

public class IsValid {
    public boolean isValid(String s) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = s.split("");
        for (int i = split.length-1 ; i>=0; i--){
            stringBuffer.append(split[i]);
        }
        String s1 = stringBuffer.toString();

        System.out.println(s);
        return s1.equals(s);
    }

    public static void main(String[] args) {
        System.out.println(new IsValid().isValid("()"));
    }
}
