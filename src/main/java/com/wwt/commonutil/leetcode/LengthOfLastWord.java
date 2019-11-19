package com.wwt.commonutil.leetcode;

public class LengthOfLastWord {

    public int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        while(end >= 0 && s.charAt(end) == ' ') end--;
        if(end < 0) return 0;
        int start = end;
        while(start >= 0 && s.charAt(start) != ' ') start--;
        return end - start;
    }
    public int lengthOfLastWord1(String s) {
        String[] s1 = s.split(" ");
        if(s1.length==0){
            return 0;
        }
        return s1[s1.length-1].length();
    }

}
