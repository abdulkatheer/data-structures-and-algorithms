package io.abdul.array.subarray_problems.problem1;

import java.util.stream.IntStream;

// https://leetcode.com/problems/maximum-subarray/description/
public class MaximumSubSubarray {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.maxSubArray(new int[]{-6, -5, -4, -1, -2, -3, -6}));
        System.out.println(solution.maxSubArray(new int[]{-1, 5, -2, 1, -10, 0, -2, 0, 18, -1}));
        System.out.println(solution.maxSubArray(new int[]{1, 2, -3, 4, 5, -1, 2}));
        System.out.println(solution.maxSubArray(new int[]{2, 5, 7, 8, 10}));
        System.out.println(solution.maxSubArray(new int[]{-3, -5, -2, -8, -1}));
        System.out.println(solution.maxSubArray(new int[]{-2, -3, 4, -1, -2, 1, 5, -3}));
        System.out.println(solution.maxSubArray(new int[]{100000, -1, 200000, -5, 300000}));
        System.out.println(solution.maxSubArray(new int[]{42}));
        System.out.println(solution.maxSubArray(new int[]{0, -1, 3, 0, -2, 5, -3}));
        System.out.println(solution.maxSubArray(new int[]{10, -100, 20, -50, 30, -10, 40}));

        // A large array of 100,000 elements, alternating between 100 and -1
        int n = 1_00_000;
        int[] arr = IntStream.range(0, n)
                .map(i -> (i % 2 == 0) ? 100 : -1)
                .toArray();

        System.out.println(solution.maxSubArray(arr));
        System.out.println(solution.maxSubArray(new int[]{0, 0, 0, 0, 0}));
    }
}

class Solution {
    // O(n) - Kadane's Algo (DP type)
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int maxSumSoFar = Integer.MIN_VALUE;
        int currentSum = 0;
        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            if (currentSum > maxSumSoFar) {
                maxSumSoFar = currentSum;
            }
            if (currentSum < 0) { // Leaving me in debt
                currentSum = 0; // Start over
            }
        }

        return maxSumSoFar;
    }
}

// O(n^2) - Brute Force
class Solution2 {
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int maxSumSoFar = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                if (currentSum > maxSumSoFar) {
                    maxSumSoFar = currentSum;
                }
            }
        }

        return maxSumSoFar;
    }
}
