package io.abdul.dynamic_programming.dp_on_subsequences.problem4;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// NOTE: Learn Modulo Arithmetic
public class CountSubsetWithSumK {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input with K = 10
        int[] arr1 = {2, 3, 5, 16, 8, 10};
        int K1 = 10;
        assertEquals(3, solution.perfectSum(arr1, K1), "Number of subsets with sum 10 should be 3");

        // Test Case 2: Example input with K = 5
        int[] arr2 = {1, 2, 3, 4, 5};
        int K2 = 5;
        assertEquals(3, solution.perfectSum(arr2, K2), "Number of subsets with sum 5 should be 3");

        // Test Case 3: Example input with K = 4
        int[] arr3 = {2, 2, 2, 2};
        int K3 = 4;
        assertEquals(6, solution.perfectSum(arr3, K3), "Number of subsets with sum 4 should be 6");

        // Test Case 5: Edge case with single element equal to K
        int[] arr5 = {5};
        int K5 = 5;
        assertEquals(1, solution.perfectSum(arr5, K5), "Number of subsets with sum 5 should be 1");

        // Test Case 6: Edge case with single element not equal to K
        int[] arr6 = {5};
        int K6 = 10;
        assertEquals(0, solution.perfectSum(arr6, K6), "Number of subsets with sum 10 should be 0");

        // Test Case 7: Large input with increasing sequence
        int[] arr7 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int K7 = 15;
        assertEquals(20, solution.perfectSum(arr7, K7), "Number of subsets with sum 15 should be 6");

        // Test Case 8: Large input with all elements equal
        int[] arr8 = {3, 3, 3, 3, 3};
        int K8 = 6;
        assertEquals(10, solution.perfectSum(arr8, K8), "Number of subsets with sum 6 should be 10");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - Stack

2 2 2 2 and target=4
If we check target == arr[i], at pos 1, when 1 is included, we've two options. 1,2 and 1,3
But as soon as we check for 2, we exit. We need to include and exclude pos 2. We miss the exclude option.
 */
class Solution {
    private final static int MAX = 1_000_000_007;

    public int perfectSum(int[] arr, int K) {
        return countSubsetSumEqualsK(arr, 0, K);
    }

    private int countSubsetSumEqualsK(int[] arr, int i, int target) {
        if (i == arr.length) {
            return target == 0 ? 1 : 0;
        }

        int withoutCurrent = countSubsetSumEqualsK(arr, i + 1, target);
        int withCurrent = 0;
        if (arr[i] <= target) {
            withCurrent = countSubsetSumEqualsK(arr, i + 1, target - arr[i]);
        }

        return (withCurrent + withoutCurrent) % MAX;
    }
}

/*
Step 2 - Memoization

T - O(n*target)
S - O(n*target) - stack + dp

 */
class Solution2 {
    private final static int MAX = 1_000_000_007;

    public int perfectSum(int[] arr, int K) {
        int[][] dp = new int[arr.length][K + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return countSubsetSumEqualsK(arr, 0, K, dp);
    }

    private int countSubsetSumEqualsK(int[] arr, int i, int target, int[][] dp) {
        if (i == arr.length) {
            return target == 0 ? 1 : 0;
        }

        if (dp[i][target] != -1) {
            return dp[i][target];
        }

        int withoutCurrent = countSubsetSumEqualsK(arr, i + 1, target, dp);
        int withCurrent = 0;
        if (arr[i] <= target) {
            withCurrent = countSubsetSumEqualsK(arr, i + 1, target - arr[i], dp);
        }

        int result = (withCurrent + withoutCurrent) % MAX;
        dp[i][target] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*target)
S - O(n*target) - dp

Known solutions:
At index 0, if target is zero OR target is arr[0] itself, we can find a match

Recursive solutions:
At index 1, fill table for all possible target values from 0 to target
    With 1 - dp[i-1][t - arr[1]]
    Without 1 - dp[i-1][t]
    dp[1][t] = with + without
 */
class Solution3 {
    private final static int MAX = 1_000_000_007;

    public int perfectSum(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[n][K + 1];

        // Known solutions
        dp[0][0] = 1;
        if (arr[0] <= K) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= K; j++) {
                int withoutCurrent = dp[i - 1][j];
                int withCurrent = 0;
                if (j >= arr[i]) {
                    withCurrent = dp[i - 1][j - arr[i]];
                }
                dp[i][j] = (withCurrent + withoutCurrent) % MAX;
            }
        }

        return dp[n - 1][K];
    }
}

/*
Step 4 - Space Optimization

T - O(n*target)
S - O(target)

 */
class Solution4 {
    private final static int MAX = 1_000_000_007;

    public int perfectSum(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[2][K + 1];

        // Known solutions
        dp[0][0] = 1;
        if (arr[0] <= K) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= K; j++) {
                int withoutCurrent = dp[0][j];
                int withCurrent = 0;
                if (j >= arr[i]) {
                    withCurrent = dp[0][j - arr[i]];
                }
                dp[1][j] = (withCurrent + withoutCurrent) % MAX;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, K + 1);
        }

        return dp[0][K];
    }
}