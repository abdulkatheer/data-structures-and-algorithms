package io.abdul.dynamic_programming.dp_on_subsequences.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubsetSumEqualsTarget {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Subset exists with sum equal to target
        int[] arr1 = {1, 2, 7, 3};
        int target1 = 6;
        assertTrue(solution.isSubsetSum(arr1, target1), "Subset with sum 6 should exist in [1, 2, 7, 3]");

        // Test Case 2: No subset exists with sum equal to target
        int[] arr2 = {2, 3, 5};
        int target2 = 6;
        assertFalse(solution.isSubsetSum(arr2, target2), "Subset with sum 6 should not exist in [2, 3, 5]");

        // Test Case 3: Subset exists with sum equal to target
        int[] arr3 = {7, 54, 4, 12, 15, 5};
        int target3 = 9;
        assertTrue(solution.isSubsetSum(arr3, target3), "Subset with sum 9 should exist in [7, 54, 4, 12, 15, 5]");

        // Test Case 5: Edge case with single element equal to target
        int[] arr5 = {5};
        int target5 = 5;
        assertTrue(solution.isSubsetSum(arr5, target5), "Subset with sum 5 should exist in [5]");

        // Test Case 6: Edge case with single element not equal to target
        int[] arr6 = {5};
        int target6 = 10;
        assertFalse(solution.isSubsetSum(arr6, target6), "Subset with sum 10 should not exist in [5]");

        // Test Case 7: Large input with no subset matching target
        int[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target7 = 100;
        assertFalse(solution.isSubsetSum(arr7, target7), "Subset with sum 100 should not exist in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

 */
class Solution {
    public boolean isSubsetSum(int[] arr, int target) {
        return isSubsetSum(arr, 0, target);
    }

    public boolean isSubsetSum(int[] arr, int i, int target) {

        if (i == arr.length) { // visited all elements
            return target == 0;
        }

        // Excluding current element
        boolean withoutCurrent = isSubsetSum(arr, i + 1, target);
        // Including current element
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = isSubsetSum(arr, i + 1, target - arr[i]);
        }
        return withCurrent || withoutCurrent;
    }
}

/*
Step 2 - Memoization

T - O(n*target)
S - O(n*target) - stack + dp

 */
class Solution2 {
    public boolean isSubsetSum(int[] arr, int target) {
        int[][] dp = new int[arr.length][target + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        boolean subsetSum = isSubsetSum(arr, 0, target, dp);
        return subsetSum;
    }

    public boolean isSubsetSum(int[] arr, int i, int target, int[][] dp) {

        if (i == arr.length) { // visited all elements
            return target == 0;
        }

        if (dp[i][target] != -1) {
            return dp[i][target] == 1;
        }

        // Excluding current element
        boolean withoutCurrent = isSubsetSum(arr, i + 1, target, dp);
        // Including current element
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = isSubsetSum(arr, i + 1, target - arr[i], dp);
        }

        boolean result = withCurrent || withoutCurrent;
        dp[i][target] = result ? 1 : 0;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*target)
S - O(n*target)

Known solutions:
At 0th position, we either skip it or take it. means when target is zero OR when target is same as 0th element, we meet the solution

Recursive case:
For each i, we populate results of all possible target values
dp[i][target] = dp[i-1][target], when current element is skipped
dp[i][target] = dp[i-1][target-arr[i]], when current element is considered
whichever is true

At each position i, we populate dp for all possible target values.
Meaning at i, if target (from 1 to target) is left, will it lead to the solution or not

1 2 7 3 and target=6
dp[0][0] = true, dp[0][1] = true
dp[1][1] = true, dp[1][2] = true, dp[1][3] = true, dp[1][4] = false, dp[1][5] = false, dp[1][6] = false
dp[2][1] = true, dp[2][2] = true, dp[2][3] = true, dp[2][4] = false, dp[2][5] = false, dp[2][6] = false
dp[3][1] = true, dp[3][2] = true, dp[3][3] = true, dp[3][4] = true,  dp[3][5] = true,  dp[3][6] = true
 */
class Solution3 {
    public boolean isSubsetSum(int[] arr, int target) {
        boolean[][] dp = new boolean[arr.length][target + 1];

        // Known solutions - Either taken or skip first element
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[i - 1][j];
                boolean withCurrent = false;
                if (!withoutCurrent) {
                    if (arr[i] <= j) {
                        withCurrent = dp[i - 1][j - arr[i]];
                    }
                }
                dp[i][j] = withCurrent || withoutCurrent;
            }
        }

        return dp[arr.length - 1][target];
    }
}

/*
Step 4 - Space Optimization

T - O(n*target)
S - O(n)

 */
class Solution4 {
    public boolean isSubsetSum(int[] arr, int target) {
        boolean[][] dp = new boolean[2][target + 1];

        // Known solutions - Either taken or skip first element
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[0][j];
                boolean withCurrent = false;
                if (!withoutCurrent) {
                    if (arr[i] <= j) {
                        withCurrent = dp[0][j - arr[i]];
                    }
                }
                dp[1][j] = withCurrent || withoutCurrent;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, target + 1);
        }

        return dp[0][target];
    }
}