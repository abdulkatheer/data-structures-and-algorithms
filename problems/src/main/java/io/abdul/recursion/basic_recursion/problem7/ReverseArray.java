package io.abdul.recursion.basic_recursion.problem7;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-recursion/reverse-an-array-ii
public class ReverseArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.reverseArray(new int[]{1})));
        System.out.println(Arrays.toString(solution.reverseArray(new int[]{1, 2})));
        System.out.println(Arrays.toString(solution.reverseArray(new int[]{1, 2, 3})));
        System.out.println(Arrays.toString(solution.reverseArray(new int[]{1, 2, 3, 4})));
    }
}

class Solution {
    public int[] reverseArray(int[] nums) {
        return reverseArray(nums, nums.length - 1);
    }

    public int[] reverseArray(int[] nums, int i) {
        if (i < 0) {
            return new int[nums.length];
        }
        int[] reversed = reverseArray(nums, i - 1);
        reversed[nums.length - i - 1] = nums[i];
        return reversed;
    }
}
