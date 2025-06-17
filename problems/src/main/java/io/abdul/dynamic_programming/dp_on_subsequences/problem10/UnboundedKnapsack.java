package io.abdul.dynamic_programming.dp_on_subsequences.problem10;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnboundedKnapsack {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with maximum value 27
        int[] val1 = {5, 11, 13};
        int[] wt1 = {2, 4, 6};
        int W1 = 10;
        assertEquals(27, solution.unboundedKnapsack(wt1, val1, val1.length, W1),
                "Maximum value for weights [2, 4, 6] and values [5, 11, 13] with capacity 10 should be 27");

        // Test Case 2: Example input with maximum value 110
        int[] val2 = {10, 40, 50, 70};
        int[] wt2 = {1, 3, 4, 5};
        int W2 = 8;
        assertEquals(110, solution.unboundedKnapsack(wt2, val2, val2.length, W2),
                "Maximum value for weights [1, 3, 4, 5] and values [10, 40, 50, 70] with capacity 8 should be 110");

        // Test Case 3: Example input with maximum value 240
        int[] val3 = {60, 100, 120};
        int[] wt3 = {10, 20, 30};
        int W3 = 60;
        assertEquals(360, solution.unboundedKnapsack(wt3, val3, val3.length, W3),
                "Maximum value for weights [10, 20, 30] and values [60, 100, 120] with capacity 60 should be 240");

        // Test Case 4: Edge case with no capacity
        int[] val4 = {10, 20, 30};
        int[] wt4 = {1, 2, 3};
        int W4 = 0;
        assertEquals(0, solution.unboundedKnapsack(wt4, val4, val4.length, W4),
                "Maximum value for weights [1, 2, 3] and values [10, 20, 30] with capacity 0 should be 0");

        // Test Case 5: Edge case with single item
        int[] val5 = {15};
        int[] wt5 = {3};
        int W5 = 9;
        assertEquals(45, solution.unboundedKnapsack(wt5, val5, val5.length, W5),
                "Maximum value for weights [3] and values [15] with capacity 9 should be 45");

        // Test Case 6: Large input with multiple items
        int[] val6 = {10, 20, 30, 40, 50};
        int[] wt6 = {1, 2, 3, 4, 5};
        int W6 = 15;
        assertEquals(150, solution.unboundedKnapsack(wt6, val6, val6.length, W6),
                "Maximum value for weights [1, 2, 3, 4, 5] and values [10, 20, 30, 40, 50] with capacity 15 should be 150");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

We either skip or take as much as possible. Similar to coin change problem, but we need to maximize the values
 */
class Solution {
    public int unboundedKnapsack(int[] wt, int[] val, int n, int W) {
        return maximizeValue(wt, val, 0, W);
    }

    public int maximizeValue(int[] wt, int[] val, int i, int W) {
        if (i == wt.length - 1) { // we can just check for wt.length and recursion will take care of this case. But we are building base case for DP.
            // take as much as possible
            int value = 0;
            int weight = wt[i];
            while (weight <= W) {
                value += val[i];
                weight += wt[i];
            }

            return value;
        }

        int skipped = maximizeValue(wt, val, i + 1, W);
        int taken = 0;
        if (W >= wt[i]) {
            taken = val[i] + maximizeValue(wt, val, i, W - wt[i]);
        }

        return Math.max(skipped, taken);
    }
}

/*
Step 2 - Memoization

T - O(n*W)
S - O(n*W) - stack + dp
 */
class Solution2 {
    public int unboundedKnapsack(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[n][W + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return maximizeValue(wt, val, 0, W, dp);
    }

    public int maximizeValue(int[] wt, int[] val, int i, int W, int[][] dp) {
        if (i == wt.length - 1) { // we can just check for wt.length and recursion will take care of this case. But we are building base case for DP.
            // take as much as possible
            int value = 0;
            int weight = wt[i];
            while (weight <= W) {
                value += val[i];
                weight += wt[i];
            }

            return value;
        }

        if (dp[i][W] != -1) {
            return dp[i][W];
        }

        int skipped = maximizeValue(wt, val, i + 1, W, dp);
        int taken = 0;
        if (W >= wt[i]) {
            taken = val[i] + maximizeValue(wt, val, i, W - wt[i], dp);
        }

        int max = Math.max(skipped, taken);
        dp[i][W] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*W)
S - O(n*W) - dp

Known solutions:
At wt 0, as long as remaining weight is less than wt[0], val[0] can be considered
Let's say val[0] is 2 and max W is 20
0 - 0
1 - 0
2 - 2 (x1)
3 - 2
4 - 4 (x2)
5 - 4
6 - 6 (x3)
7 - 6
8 - 8 (x4)
9 - 8
10 - 10 (x5)
 */
class Solution3 {
    public int unboundedKnapsack(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[n][W + 1];

        // Known solutions
        for (int i = 0; i <= W; i++) {
            dp[0][i] = (i / wt[0]) * val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                int skipped = dp[i - 1][j];
                int taken = 0;
                if (j >= wt[i]) {
                    taken = val[i] + dp[i][j - wt[i]]; // taking current and trying to fill remain with same weight or any coins in the past
                }

                dp[i][j] = Math.max(skipped, taken);
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
    public int unboundedKnapsack(int[] wt, int[] val, int n, int W) {
        int[][] dp = new int[2][W + 1];

        // Known solutions
        for (int i = 0; i <= W; i++) {
            dp[0][i] = (i / wt[0]) * val[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                int skipped = dp[0][j];
                int taken = 0;
                if (j >= wt[i]) {
                    taken = val[i] + dp[1][j - wt[i]]; // taking current and trying to fill remain with same weight or any coins in the past
                }

                dp[1][j] = Math.max(skipped, taken);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, W + 1);
        }

        return dp[0][W];
    }
}