package com.wwt.commonUtil.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
class Solution {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     *
     * 示例:
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] resultArr = new int[2];
        for (int i = 0 ; i < nums.length ; i++){
            log.info("nums[i]:{}",nums[i]);
            for (int j = i+1 ; j < nums.length ; j++){
                log.info("nums[j]:{}",nums[j]);
                if ( nums[i]+nums[j]==target){
                    resultArr[0] = i;
                    resultArr[1] = j;
                    log.info(Arrays.toString(resultArr));
                    return resultArr;
                }
            }
        }
        return null;
    }

    public static int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            System.out.println("pop:"+pop);
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
            System.out.println(rev);
        }
        return rev;
    }

    public static void main(String[] args) {
//        nums = [2, 7, 11, 15], target = 9
        int [] nums = new int[]{2, 7, 11, 15};
        int target = 13;
        twoSum(nums,target);
        System.out.println(reverse(Integer.MAX_VALUE));
        System.out.println(-123%10);
        System.out.println(123%10);
    }

}