package io.abdul.dynamic_programming.dp_on_subsequences.problem9;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinChangeII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with multiple combinations
        int[] coins1 = {2, 4, 10};
        int amount1 = 10;
        assertEquals(4, solution.count(coins1, coins1.length, amount1), "Number of combinations for amount 10 with coins [2, 4, 10] should be 4");

        // Test Case 2: Example input with a single coin
        int[] coins2 = {5};
        int amount2 = 5;
        assertEquals(1, solution.count(coins2, coins2.length, amount2), "Number of combinations for amount 5 with coins [5] should be 1");

        // Test Case 3: Example input with multiple coins
        int[] coins3 = {1, 2, 3, 5};
        int amount3 = 5;
        assertEquals(6, solution.count(coins3, coins3.length, amount3), "Number of combinations for amount 5 with coins [1, 2, 3, 5] should be 6");

        // Test Case 5: Edge case with amount 0
        int[] coins5 = {1, 2, 3};
        int amount5 = 0;
        assertEquals(1, solution.count(coins5, coins5.length, amount5), "Number of combinations for amount 0 should be 1 (empty subset)");

        // Test Case 6: Edge case with no valid combinations
        int[] coins6 = {3, 7};
        int amount6 = 5;
        assertEquals(0, solution.count(coins6, coins6.length, amount6), "Number of combinations for amount 5 with coins [3, 7] should be 0");

        // Test Case 7: Large input with multiple combinations
        int[] coins7 = {1, 2, 5};
        int amount7 = 10;
        assertEquals(10, solution.count(coins7, coins7.length, amount7), "Number of combinations for amount 10 with coins [1, 2, 5] should be 10");

        // Test Case 8: Large input with a single coin
        int[] coins8 = {1};
        int amount8 = 1000;
        assertEquals(1, solution.count(coins8, coins8.length, amount8), "Number of combinations for amount 1000 with coins [1] should be 1");

    }
}

/*
Step 1 - Top-down recursion

T - O(2^n)
S - O(n) - stack

Similar to CoinChange problem, here we just count number of possibilities instead of min num of coins required
 */
class Solution {
    private static final int MAX = (int) 1e9 + 7;

    public int count(int[] coins, int N, int amount) {
        return countPossibilities(coins, 0, amount);
    }

    private int countPossibilities(int[] coins, int i, int amount) {
        if (i == coins.length - 1) { // the last coin
            if (amount % coins[i] == 0) { // As its last coin, we've to fulfill the balance by take 0 or 1 or any num of last coin. Otherwise we can't fulfill.
                return 1;
            } else {
                return 0;
            }
        }

        int skipped = countPossibilities(coins, i + 1, amount);
        int taken = 0;
        if (amount >= coins[i]) {
            taken = countPossibilities(coins, i, amount - coins[i]); // take same coin as much as possible
        }

        return (skipped + taken) % MAX;
    }
}

/*
Step 2 - Memoization

T - O(n*amount)
S - O(n*amount) - stack + dp
 */
class Solution2 {
    private static final int MAX = (int) 1e9 + 7;

    public int count(int[] coins, int N, int amount) {
        int[][] dp = new int[N][amount + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return countPossibilities(coins, 0, amount, dp);
    }

    private int countPossibilities(int[] coins, int i, int amount, int[][] dp) {
        if (i == coins.length - 1) { // the last coin
            if (amount % coins[i] == 0) { // As its last coin, we've to fulfill the balance by take 0 or 1 or any num of last coin. Otherwise we can't fulfill.
                return 1;
            } else {
                return 0;
            }
        }

        if (dp[i][amount] != -1) {
            return dp[i][amount];
        }

        int skipped = countPossibilities(coins, i + 1, amount, dp);
        int taken = 0;
        if (amount >= coins[i]) {
            taken = countPossibilities(coins, i, amount - coins[i], dp); // take same coin as much as possible
        }

        int result = (skipped + taken) % MAX;
        dp[i][amount] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*amount)
S - O(n*amount) - dp

Known solutions:
At pos 0, only when the required amount is zero or in multiples of coins[0]
 */
class Solution3 {
    private static final int MAX = (int) 1e9 + 7;

    public int count(int[] coins, int N, int amount) {
        int[][] dp = new int[N][amount + 1];

        // Known solutions
        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) {
                dp[0][i] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= amount; j++) {
                int skipped = dp[i - 1][j];
                int taken = 0;
                if (j >= coins[i]) {
                    taken = dp[i][j - coins[i]];
                }

                dp[i][j] = (skipped + taken) % MAX;
            }
        }

        return dp[N - 1][amount];
    }
}

/*
Step 4 - Space Optimization


 */
class Solution4 {
    private static final int MAX = (int) 1e9 + 7;

    public int count(int[] coins, int N, int amount) {
        int[][] dp = new int[2][amount + 1];

        // Known solutions
        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) {
                dp[0][i] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= amount; j++) {
                int skipped = dp[0][j];
                int taken = 0;
                if (j >= coins[i]) {
                    taken = dp[1][j - coins[i]];
                }

                dp[1][j] = (skipped + taken) % MAX;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, amount + 1);
        }

        return dp[0][amount];
    }
}
