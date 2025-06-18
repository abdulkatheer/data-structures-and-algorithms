package io.abdul.dynamic_programming.dp_on_lis.problem1;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Subsequence != Subarray

A subsequence is a sequence derived from an array by deleting some or no elements without changing the order of the remaining elements.
For example, [3, 6, 2, 7] is a subsequence of [0, 3, 1, 6, 2, 2, 7].
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with LIS length 4
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        assertEquals(4, solution.LIS(nums1), "LIS length for [10, 9, 2, 5, 3, 7, 101, 18] should be 4");

        // Test Case 2: Example input with LIS length 4
        int[] nums2 = {0, 1, 0, 3, 2, 3};
        assertEquals(4, solution.LIS(nums2), "LIS length for [0, 1, 0, 3, 2, 3] should be 4");

        // Test Case 3: Example input with LIS length 1
        int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
        assertEquals(1, solution.LIS(nums3), "LIS length for [7, 7, 7, 7, 7, 7, 7] should be 1");

        // Test Case 4: Edge case with single element
        int[] nums4 = {5};
        assertEquals(1, solution.LIS(nums4), "LIS length for [5] should be 1");

        // Test Case 5: Edge case with strictly increasing sequence
        int[] nums5 = {1, 2, 3, 4, 5};
        assertEquals(5, solution.LIS(nums5), "LIS length for [1, 2, 3, 4, 5] should be 5");

        // Test Case 6: Edge case with strictly decreasing sequence
        int[] nums6 = {5, 4, 3, 2, 1};
        assertEquals(1, solution.LIS(nums6), "LIS length for [5, 4, 3, 2, 1] should be 1");

        // Test Case 7: Large input with mixed values
        int[] nums7 = {3, 10, 2, 1, 20};
        assertEquals(3, solution.LIS(nums7), "LIS length for [3, 10, 2, 1, 20] should be 3");

        // Test Case 8: Large input with duplicates
        int[] nums8 = {1, 3, 5, 4, 7, 4, 8};
        assertEquals(5, solution.LIS(nums8), "LIS length for [1, 3, 5, 4, 7, 4, 8] should be 5");

        int[] nums9 = {-39, -14, 94, 34, -19, -70, -1, -62, -64, 28, 99, -24, 74, -71, 13, 78, -85, 51, -70, -70, -63, 51, 42, 71, 31, -70, 100, -47};
        assertEquals(7, solution.LIS(nums9));

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

We skip or take an element to find all subsequences in an array.
But when we take if we make sure that it's greater than previously taken element, then we'll only get subsequence with increasing numbers
 */
class Solution {
    public int LIS(int[] nums) {
        return lis(nums, 0, -1);
    }

    private int lis(int[] nums, int i, int prevTakenPos) {
        // We could just check nums.length and this could be handled in recursive case. But we did to find base case for DP
        if (i == nums.length - 1) {
            if (prevTakenPos < 0 || nums[i] > nums[prevTakenPos]) { // no element taken before or this element greater than prev taken
                return 1; // take
            } else {
                return 0; // skip
            }
        }

        int skip = lis(nums, i + 1, prevTakenPos);

        int take = 0;
        if (prevTakenPos == -1) { // no element taken before or this element greater than prev taken
            take = 1 + lis(nums, i + 1, i);
        } else if (nums[i] > nums[prevTakenPos]) {
            take = 1 + lis(nums, i + 1, i);
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
    public int LIS(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return lis(nums, 0, -1, dp);
    }

    private int lis(int[] nums, int i, int prevTakenPos, int[][] dp) {
        // We could just check nums.length and this could be handled in recursive case. But we did to find base case for DP
        if (i == nums.length - 1) {
            if (prevTakenPos < 0 || nums[i] > nums[prevTakenPos]) { // no element taken before or this element greater than prev taken
                return 1; // take
            } else {
                return 0; // skip
            }
        }

        if (prevTakenPos >= 0 && dp[i][prevTakenPos] != -1) {
            return dp[i][prevTakenPos];
        }

        int skip = lis(nums, i + 1, prevTakenPos, dp);

        int take = 0;
        if (prevTakenPos == -1) { // no element taken before or this element greater than prev taken
            take = 1 + lis(nums, i + 1, i, dp);
        } else if (nums[i] > nums[prevTakenPos]) {
            take = 1 + lis(nums, i + 1, i, dp);
        }

        int max = Math.max(skip, take);
        if (prevTakenPos >= 0) {
            dp[i][prevTakenPos] = max;
        }
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution
NOTE: This is correct, but still TLE... OOOPS..

T - O(n^2)
S - O(n) - dp

Known solutions:
At pos 0, we can consider it as no other elements before it

Recursive solutions:
At pos 1, we'll add length of pos 0, if it's greater than pos 0, otherwise go backwards and see if any other smaller element exists. It'll take max of all.
 */
class Solution3 {
    public int LIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        // Known solutions:
        dp[0] = 1; // if pos 0 is included, 1 is the max

        int maxOfAll = 1;
        for (int i = 1; i < n; i++) {
            int max = 1; // skip all
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) { // take
                    max = Math.max(max, 1 + dp[j]);
                }
            }
            dp[i] = max; // if pos i is included, max is the max
            maxOfAll = Math.max(maxOfAll, max);
        }

        return maxOfAll;
    }
}

// Step 4 - Space Optimization can't be done as current value depends on all previous dp data

/*
Greedy Solution: Binary Search - Lower Bound type

T - O(n logn)
S - O(n)

We maintain a temporary array to keep LIS. If we find a greater num than last, we append, otherwise we replace it with lowerBound.
We'll not end up with the correct LIS, but the length.
 */
class Solution4 {
    public int LIS(int[] nums) {
        ArrayList<Integer> lis = new ArrayList<>();
        lis.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int pos = lowerBound(lis, nums[i]);
            if (pos == lis.size()) {
                lis.add(nums[i]);
            } else {
                lis.set(pos, nums[i]);
            }
        }

        return lis.size();
    }

    private int lowerBound(ArrayList<Integer> lis, int num) {
        int low = 0;
        int high = lis.size() - 1;

        int pos = lis.size();
        while (low <= high) {
            int mid = (low + high) / 2;
            if (lis.get(mid) >= num) {
                pos = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return pos;
    }
}