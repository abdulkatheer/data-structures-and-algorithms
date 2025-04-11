package io.abdul.array.fundamentals.problem5;

import java.util.Arrays;

public class LeftRotateByOne {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {1};
        solution.rotateArrayByOne(nums);
        System.out.println(Arrays.toString(nums));
        nums = new int[]{1, 2, 3, 4, 5};
        solution.rotateArrayByOne(nums);
        System.out.println(Arrays.toString(nums));
        nums = new int[]{1, 2};
        solution.rotateArrayByOne(nums);
        System.out.println(Arrays.toString(nums));
        nums = new int[]{1, 2, 3, 4};
        solution.rotateArrayByOne(nums);
        System.out.println(Arrays.toString(nums));
    }
}

class Solution {
    public void rotateArrayByOne(int[] nums) {
        int temp2;
        int temp = nums[nums.length - 1];
        for (int i = nums.length - 1; i >= 0; i--) {
            int swapPos = i - 1;
            swapPos = swapPos < 0 ? nums.length + swapPos : swapPos;
            temp2 = nums[swapPos];
            nums[swapPos] = temp;
            temp = temp2;
        }
    }
}