package io.abdul.dynamic_programming.dp_on_subsequences.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Two equal subsets will have two equal sum
S = S1 + S2
Here both S1 and S2 are same
S = S1+S1
S = 2S1
S1 = S/2; if S is odd we can't split
And if we're able to find S1 sum, there'll definitely be another subset with sum S1 exist
 */
public class PartitionEqualSubsetSum {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Array can be partitioned into equal subsets
        int[] arr1 = {1, 10, 21, 10};
        assertTrue(solution.equalPartition(arr1.length, arr1), "Array [1, 10, 21, 10] should be partitionable into equal subsets");

        // Test Case 2: Array cannot be partitioned into equal subsets
        int[] arr2 = {1, 2, 3, 5};
        assertFalse(solution.equalPartition(arr2.length, arr2), "Array [1, 2, 3, 5] should not be partitionable into equal subsets");

        // Test Case 3: Array can be partitioned into equal subsets
        int[] arr3 = {2, 2, 1, 1};
        assertTrue(solution.equalPartition(arr3.length, arr3), "Array [2, 2, 1, 1] should be partitionable into equal subsets");

        // Test Case 5: Edge case with single element
        int[] arr5 = {5};
        assertFalse(solution.equalPartition(arr5.length, arr5), "Array [5] should not be partitionable into equal subsets");

        // Test Case 6: Array with all elements equal
        int[] arr6 = {4, 4, 4, 4};
        assertTrue(solution.equalPartition(arr6.length, arr6), "Array [4, 4, 4, 4] should be partitionable into equal subsets");

        // Test Case 7: Large input with no partition possible
        int[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertFalse(solution.equalPartition(arr7.length, arr7), "Array [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] should not be partitionable into equal subsets");
    }
}

/*
Step 1 - Top-down recursive approach

T - O(2^n)
S - O(n) - stack

Similar to Subset Sum Equals Target, but we need to search for Sum/2
If total is 50, and if we're able to find a subset whose sum equals 25, then there must exist another subset whose sum equals 25
 */
class Solution {
    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false; // Odd sum can't split into two equals halves
        }

        return equalPartition(arr, 0, sum / 2);
    }

    private boolean equalPartition(int[] arr, int i, int target) {
        if (i == arr.length) {
            return target == 0;
        }

        boolean withoutCurrent = equalPartition(arr, i + 1, target);
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = equalPartition(arr, i + 1, target - arr[i]);
        }

        return withCurrent || withoutCurrent;
    }
}

/*
Step 2 - Memoization

T - O(n*sum/2)
S - O(n*sum/2) - dp + stack

 */
class Solution2 {
    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false; // Odd sum can't split into two equals halves
        }

        int target = sum / 2;
        int[][] dp = new int[n][target + 1];

        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return equalPartition(arr, 0, target, dp);
    }

    private boolean equalPartition(int[] arr, int i, int target, int[][] dp) {
        if (i == arr.length) {
            return target == 0;
        }

        if (dp[i][target] != -1) {
            return dp[i][target] == 1;
        }

        boolean withoutCurrent = equalPartition(arr, i + 1, target, dp);
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = equalPartition(arr, i + 1, target - arr[i], dp);
        }

        boolean result = withCurrent || withoutCurrent;
        dp[i][target] = result ? 1 : 0;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*sum/2)
S - O(n*sum/2) - dp

Known solutions:
At 0th position, we either skip it or take it. means when target is zero OR when target is same as 0th element, we meet the solution
 */
class Solution3 {
    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[][] dp = new boolean[n][target + 1];
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[i - 1][j];
                boolean withCurrent = false;
                if (j >= arr[i]) {
                    withCurrent = dp[i - 1][j - arr[i]];
                }

                dp[i][j] = withCurrent || withoutCurrent;
            }
        }

        return dp[n - 1][target];
    }
}

/*
Step 4 - Space Optimization

T - O(n*sum/2)
S - O(sum/2)

 */
class Solution4 {
    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[][] dp = new boolean[2][target + 1];
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[0][j];
                boolean withCurrent = false;
                if (j >= arr[i]) {
                    withCurrent = dp[0][j - arr[i]];
                }

                dp[1][j] = withCurrent || withoutCurrent;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, target + 1);
        }

        return dp[0][target];
    }
}