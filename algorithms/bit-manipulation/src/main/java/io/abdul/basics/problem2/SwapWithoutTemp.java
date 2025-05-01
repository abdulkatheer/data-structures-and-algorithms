package io.abdul.basics.problem2;

import java.util.Arrays;

public class SwapWithoutTemp {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums;

        nums = new int[]{1, 2};
        solution.swap(nums);
        System.out.println(Arrays.toString(nums));
    }
}

/*
Using self-inverse, commutative and associative property
 */
class Solution {
    public void swap(int[] nums) {
        nums[0] = nums[0] ^ nums[1]; // a = a^b
        nums[1] = nums[0] ^ nums[1]; // b = a^b = a^b^b = a
        nums[0] = nums[0] ^ nums[1]; // a = a^b = a^b^a = b
    }
}
