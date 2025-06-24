package io.abdul.dynamic_programming.dp_on_partitions.problem6;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://leetcode.com/problems/partition-array-for-maximum-sum/
public class PartitionArrayForMaximumSum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Example 1
        assertEquals(84, solution.maxSumAfterPartitioning(new int[]{1, 15, 7, 9, 2, 5, 10}, 3));

        // Example 2
        assertEquals(83, solution.maxSumAfterPartitioning(new int[]{1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3}, 4));

        // Example 3
        assertEquals(1, solution.maxSumAfterPartitioning(new int[]{1}, 1));

        // All elements the same
        assertEquals(30, solution.maxSumAfterPartitioning(new int[]{5, 5, 5, 5, 5, 5}, 2));

        // k equals array length
        assertEquals(250, solution.maxSumAfterPartitioning(new int[]{10, 20, 30, 40, 50}, 5));

        // k is 1 (no partitioning)
        assertEquals(15, solution.maxSumAfterPartitioning(new int[]{1, 2, 3, 4, 5}, 1));

        // Array with zeros
        assertEquals(0, solution.maxSumAfterPartitioning(new int[]{0, 0, 0, 0}, 2));

        // Array with increasing values
        assertEquals(24, solution.maxSumAfterPartitioning(new int[]{1, 2, 3, 4, 5, 6}, 2));
    }
}

/*
Step 1 - Top-down recursive solution
Front-partition

T - O(k * n^2)
S - O(n) - stack

take 1 and partition remaining
take 2 and partition remaining
taken n and partition none
when we take find the max and multiply into taken size

We need to return the max of all

Base case
i > j -> 0
i == j -> element itself, can't partition further

 */
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        return maxSum(arr, 0, k);
    }

    private int maxSum(int[] arr, int i, int maxPartitions) {
        if (i == arr.length) {
            return 0;
        }

        int maxForI = Integer.MIN_VALUE;
        int maxAnswer = Integer.MIN_VALUE;
        int length = 0;
        for (int k = i; k < Math.min(i + maxPartitions, arr.length); k++) {
            length++;
            maxForI = Math.max(maxForI, arr[k]);
            int currentSubarraySum = length * maxForI;
            maxAnswer = Math.max(maxAnswer, currentSubarraySum + maxSum(arr, k + 1, maxPartitions));
        }

        return maxAnswer;
    }
}

/*
Step 2 - Memoization

T - O(n*k)
S - O(n) - stack + dp

 */
class Solution2 {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int[] dp = new int[arr.length];
        Arrays.fill(dp, -1);
        return maxSum(arr, 0, k, dp);
    }

    private int maxSum(int[] arr, int i, int maxPartitions, int[] dp) {
        if (i == arr.length) {
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int maxForI = Integer.MIN_VALUE;
        int maxAnswer = Integer.MIN_VALUE;
        int length = 0;
        for (int k = i; k < Math.min(i + maxPartitions, arr.length); k++) {
            length++;
            maxForI = Math.max(maxForI, arr[k]);
            int currentSubarraySum = length * maxForI;
            maxAnswer = Math.max(maxAnswer, currentSubarraySum + maxSum(arr, k + 1, maxPartitions, dp));
        }

        dp[i] = maxAnswer;
        return maxAnswer;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*k)
S - O(n) - dp

Known solutions
Out of bounds, then 0
 */

class Solution3 {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        /*
        dp[i] stores the max sum from i to arr.length
        dp[0] stores max for entire array
         */

        // Known solutions
        // dp[arr.length] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int maxForI = Integer.MIN_VALUE;
            int maxAnswer = Integer.MIN_VALUE;
            int length = 0;
            for (int j = i; j < Math.min(i + k, n); j++) {
                length++;
                maxForI = Math.max(maxForI, arr[j]);
                int currentSubarraySum = length * maxForI;
                maxAnswer = Math.max(maxAnswer, currentSubarraySum + dp[j + 1]);
            }
            dp[i] = maxAnswer;
        }

        return dp[0];
    }
}

/*
Step 4 - Space optimization

T - O(n*k)
S - O(k) - dp

dp[i] depends on last k values
 */

class Solution4 {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[k + 1];
        /*
        dp[i] stores the max sum from i to arr.length
        dp[0] stores max for entire array
         */

        // Known solutions
        // dp[arr.length] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int maxForI = Integer.MIN_VALUE;
            int maxAnswer = Integer.MIN_VALUE;
            int length = 0;
            int dpI = 0;
            for (int j = i; j < Math.min(i + k, n); j++) {
                length++;
                maxForI = Math.max(maxForI, arr[j]);
                int currentSubarraySum = length * maxForI;
                maxAnswer = Math.max(maxAnswer, currentSubarraySum + dp[dpI + 1]);
                dpI++;
            }
            dp[0] = maxAnswer;
            System.arraycopy(dp, 0, dp, 1, k);
        }

        return dp[0];
    }
}