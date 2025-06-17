package io.abdul.dynamic_programming.dp_on_subsequences.problem6;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Knapsack01 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Example 1
        int[] val1 = {60, 100, 120};
        int[] wt1 = {10, 20, 30};
        int W1 = 50;
        assertEquals(220, solution.knapsack01(wt1, val1, val1.length, W1));

        // Example 2
        int[] val2 = {10, 40, 30, 50};
        int[] wt2 = {5, 4, 6, 3};
        int W2 = 10;
        assertEquals(90, solution.knapsack01(wt2, val2, val2.length, W2));

        // Example 3
        int[] val3 = {20, 5, 10, 40, 15, 25};
        int[] wt3 = {1, 2, 3, 8, 7, 4};
        int W3 = 10;
        assertEquals(60, solution.knapsack01(wt3, val3, val3.length, W3));

        // Zero capacity
        int[] val4 = {10, 20, 30};
        int[] wt4 = {1, 1, 1};
        int W4 = 0;
        assertEquals(0, solution.knapsack01(wt4, val4, val4.length, W4));
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack
 */
class Solution {
    public int knapsack01(int[] wt, int[] val, int n, int W) {
        return knapsack01(wt, val, n, W, 0);
    }

    private int knapsack01(int[] wt, int[] val, int n, int W, int i) {
        if (i == n) { // Explored all possibilities
            return 0;
        }

        int notToken = knapsack01(wt, val, n, W, i + 1);
        int taken = 0;
        if (W >= wt[i]) { // Make sure Knapsack doesn't overflow
            taken = val[i] + knapsack01(wt, val, n, W - wt[i], i + 1);
        }

        return Math.max(notToken, taken);
    }
}

/*
Step 2 - Memoization

T - O(n*W)
S - O(n*W) - stack + dp

 */
class Solution2 {
    public int knapsack01(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[n][W + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return knapsack01(wt, val, n, W, 0, dp);
    }

    private int knapsack01(int[] wt, int[] val, int n, int W, int i, int[][] dp) {
        if (i == n) { // Explored all possibilities
            return 0;
        }

        if (dp[i][W] != -1) {
            return dp[i][W];
        }
        int notToken = knapsack01(wt, val, n, W, i + 1, dp);
        int taken = 0;
        if (W >= wt[i]) { // Make sure Knapsack doesn't overflow
            taken = val[i] + knapsack01(wt, val, n, W - wt[i], i + 1, dp);
        }

        int max = Math.max(notToken, taken);
        dp[i][W] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*W)
S - O(n*W) - dp

Known solutions:
At 0, only one element is considered. If wt[0] is 30, we can consider it if remaining capacity is 30,31,32 t.... W
 */
class Solution3 {
    public int knapsack01(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[n][W + 1];

        // Known solutions
        for (int i = wt[0]; i <= W; i++) {
            dp[0][i] = val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                int notTaken = dp[i - 1][j];
                int taken = 0;
                if (j >= wt[i]) {
                    taken = val[i] + dp[i - 1][j - wt[i]];
                }
                dp[i][j] = Math.max(taken, notTaken);
            }
        }

        return dp[n - 1][W];
    }
}

/*
Step 4 - Space Optimization

T - O(n*W)
S - O(W) - dp
 */
class Solution4 {
    public int knapsack01(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[2][W + 1];

        // Known solutions
        for (int i = wt[0]; i <= W; i++) {
            dp[0][i] = val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                int notTaken = dp[0][j];
                int taken = 0;
                if (j >= wt[i]) {
                    taken = val[i] + dp[0][j - wt[i]];
                }
                dp[1][j] = Math.max(taken, notTaken);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, W + 1);
        }

        return dp[0][W];
    }
}