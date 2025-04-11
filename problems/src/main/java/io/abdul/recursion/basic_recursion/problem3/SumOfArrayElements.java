package io.abdul.recursion.basic_recursion.problem3;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-recursion/sum-of-array-elements
public class SumOfArrayElements {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.arraySum(new int[]{1}));
        System.out.println(solution.arraySum(new int[]{1, 2}));
        System.out.println(solution.arraySum(new int[]{1, 2, 3}));
        System.out.println(solution.arraySum(new int[]{1, 2, 3, 4}));
    }
}

class Solution {
    public int arraySum(int[] nums) {
        return arraySum(nums, 0);
    }

    private int arraySum(int[] nums, int i) {
        if (i >= nums.length) {
            return 0;
        }
        return nums[i] + arraySum(nums, i + 1);
    }
}