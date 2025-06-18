package io.abdul.dynamic_programming.dp_on_lis.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestBitonicSequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: Example input with bitonic sequence length 6
        int[] arr1 = {5, 1, 4, 2, 3, 6, 8, 7};
        assertEquals(6, solution.LongestBitonicSequence(arr1),
                "Longest bitonic sequence for [5, 1, 4, 2, 3, 6, 8, 7] should be 6");

        // Test Case 2: Example input with bitonic sequence length 8
        int[] arr2 = {10, 20, 30, 40, 50, 40, 30, 20};
        assertEquals(8, solution.LongestBitonicSequence(arr2),
                "Longest bitonic sequence for [10, 20, 30, 40, 50, 40, 30, 20] should be 8");

        // Test Case 3: Example input with bitonic sequence length 6
        int[] arr3 = {12, 11, 10, 15, 18, 17, 16, 14};
        assertEquals(6, solution.LongestBitonicSequence(arr3),
                "Longest bitonic sequence for [12, 11, 10, 15, 18, 17, 16, 14] should be 6");

        // Test Case 4: Edge case with strictly increasing sequence
        int[] arr4 = {1, 2, 3, 4, 5};
        assertEquals(5, solution.LongestBitonicSequence(arr4),
                "Longest bitonic sequence for [1, 2, 3, 4, 5] should be 5");

        // Test Case 5: Edge case with strictly decreasing sequence
        int[] arr5 = {5, 4, 3, 2, 1};
        assertEquals(5, solution.LongestBitonicSequence(arr5),
                "Longest bitonic sequence for [5, 4, 3, 2, 1] should be 5");

        // Test Case 6: Edge case with single element
        int[] arr6 = {7};
        assertEquals(1, solution.LongestBitonicSequence(arr6),
                "Longest bitonic sequence for [7] should be 1");

        // Test Case 7: Large input with mixed values
        int[] arr7 = {1, 3, 5, 4, 2, 6, 8, 7, 9};
        assertEquals(6, solution.LongestBitonicSequence(arr7),
                "Longest bitonic sequence for [1, 3, 5, 4, 2, 6, 8, 7, 9] should be 7");

        // Test Case 8: Edge case with all elements the same
        int[] arr8 = {4, 4, 4, 4};
        assertEquals(1, solution.LongestBitonicSequence(arr8),
                "Longest bitonic sequence for [4, 4, 4, 4] should be 1");

    }

}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n)

Either skip or take
when you take, if you're looking for bigger, take only if its smaller otherwise skip
next you have two more options. Look for smaller or bigger

if you're looking for smaller, take only if its smaller
next you've only one option look for smaller
 */
class Solution {
    public int LongestBitonicSequence(int[] arr) {
        return longestBitonicSequence(arr, 0, -1, true);
    }

    public int longestBitonicSequence(int[] nums, int i, int prevPos, boolean bigger) {
        if (i == nums.length - 1) {
            if (prevPos < 0) {
                return 1;
            }
            if (bigger && nums[i] > nums[prevPos]) {
                return 1;
            }
            if (!bigger && nums[i] < nums[prevPos]) {
                return 1;
            }
            return 0;
        }

        // skip
        int skip = longestBitonicSequence(nums, i + 1, prevPos, bigger);

        int take = 0;
        if (bigger && (prevPos < 0 || nums[i] > nums[prevPos])) {
            take = 1 + Math.max(longestBitonicSequence(nums, i + 1, i, true), longestBitonicSequence(nums, i + 1, i, false)); // next bigger or smaller
        }
        if (!bigger && (prevPos < 0 || nums[i] < nums[prevPos])) {
            take = 1 + longestBitonicSequence(nums, i + 1, i, false); // next smaller only
        }

        return Math.max(skip, take);
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

 */
class Solution2 {
    public int LongestBitonicSequence(int[] arr) {
        int[][][] dp = new int[arr.length][arr.length][2]; // i, prevPos, bigger
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }
        return longestBitonicSequence(arr, 0, -1, true, dp);
    }

    public int longestBitonicSequence(int[] nums, int i, int prevPos, boolean bigger, int[][][] dp) {
        if (i == nums.length - 1) {
            if (prevPos < 0) {
                return 1;
            }
            if (bigger && nums[i] > nums[prevPos]) {
                return 1;
            }
            if (!bigger && nums[i] < nums[prevPos]) {
                return 1;
            }
            return 0;
        }

        int biggerInt = bigger ? 1 : 0;
        if (prevPos >= 0 && dp[i][prevPos][biggerInt] != -1) {
            return dp[i][prevPos][biggerInt];
        }

        // skip
        int skip = longestBitonicSequence(nums, i + 1, prevPos, bigger, dp);

        int take = 0;
        if (bigger && (prevPos < 0 || nums[i] > nums[prevPos])) {
            take = 1 + Math.max(longestBitonicSequence(nums, i + 1, i, true, dp), longestBitonicSequence(nums, i + 1, i, false, dp)); // next bigger or smaller
        }
        if (!bigger && (prevPos < 0 || nums[i] < nums[prevPos])) {
            take = 1 + longestBitonicSequence(nums, i + 1, i, false, dp); // next smaller only
        }

        int max = Math.max(skip, take);
        if (prevPos >= 0) {
            dp[i][prevPos][biggerInt] = max;
        }
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

It wasn't straightforward to convert recursive as is to iterative. Thought process is different

Diff between LIS and Bitonic is, LIS has only increasing subsequence. But Bitonic LIS + Longest Decreasing Subsequence

So we'll build LIS and LDS, then bitonic based on them.

Bitonic can only have LIS and no LDS.
 */
class Solution3 {
    public int LongestBitonicSequence(int[] arr) {
        int n = arr.length;
        int[] lis = new int[n];
        // Known solution
        lis[0] = 1;
        for (int i = 1; i < n; i++) {
            int max = 1; // default if all are bigger
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    max = Math.max(max, 1 + lis[j]);
                }
            }

            lis[i] = max;
        }

        int[] lds = new int[n];
        // Known solution
        lds[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {
            int max = 1; // default if all are bigger
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    max = Math.max(max, 1 + lds[j]);
                }
            }

            lds[i] = max;
        }

        int[] bitonic = new int[n];
        // bitonic[i] = lis at i + lds at i -1, as the same element will be the end of lis and start of lds
        for (int i = 0; i < n; i++) {
            bitonic[i] = lis[i] + lds[i] - 1;
        }

        int max = 1;
        for (int i : bitonic) {
            max = Math.max(i, max);
        }

        return max;
    }
}