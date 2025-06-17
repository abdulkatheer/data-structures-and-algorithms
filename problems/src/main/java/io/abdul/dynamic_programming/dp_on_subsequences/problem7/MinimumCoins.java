package io.abdul.dynamic_programming.dp_on_subsequences.problem7;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumCoins {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Example 1: coins = [1, 2, 5], amount = 11 -> 3
        assertEquals(3, solution.MinimumCoins(new int[]{1, 2, 5}, 11));

        // Example 2: coins = [2, 5], amount = 3 -> -1
        assertEquals(-1, solution.MinimumCoins(new int[]{2, 5}, 3));

        // Example 3: coins = [1], amount = 0 -> 0
        assertEquals(0, solution.MinimumCoins(new int[]{1}, 0));

        // Edge: coins = [2], amount = 4 -> 2
        assertEquals(2, solution.MinimumCoins(new int[]{2}, 4));

        // Edge: coins = [2], amount = 3 -> -1
        assertEquals(-1, solution.MinimumCoins(new int[]{2}, 3));

        // Edge: coins = [1, 3, 4], amount = 6 -> 2 (3+3 or 4+1+1)
        assertEquals(2, solution.MinimumCoins(new int[]{1, 3, 4}, 6));

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n)

We take or skip
When we take, we take the same coin as much as possible and when not we move to next coin
 */
class Solution {
    public int MinimumCoins(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int result = MinimumCoins(coins, 0, amount);
        return result >= (int) 1e9 ? -1 : result;
    }

    public int MinimumCoins(int[] coins, int i, int amount) {
        if (amount == 0) {
            return 0;
        }

        if (i == coins.length - 1) { // We need min, so when invalid result we return the opposite max. We take 10^9, bcz if we return Int.MAX, it'll overflow when we add
            if (amount % coins[i] == 0) { // last coin value is in multiple of required amount, so we just take as much as we need
                return amount / coins[i];
            } else {
                return (int) 1e9;
            }
        }

        int withoutCurrent = MinimumCoins(coins, i + 1, amount);
        int withCurrent = (int) 1e9;
        if (coins[i] <= amount) {
            withCurrent = 1 + MinimumCoins(coins, i, amount - coins[i]);
        }

        return Math.min(withCurrent, withoutCurrent);
    }
}

/*
Step 2 - Memoization

T - O(n*amount)
S - O(n*amount) - stack + dp

 */
class Solution2 {
    public int MinimumCoins(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int[][] dp = new int[coins.length][amount + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, (int) 1e9);
        }
        int result = MinimumCoins(coins, 0, amount, dp);
        return result >= (int) 1e9 ? -1 : result;
    }

    public int MinimumCoins(int[] coins, int i, int amount, int[][] dp) {
        if (amount == 0) {
            return 0;
        }

        if (i == coins.length - 1) {
            if (amount % coins[i] == 0) { // last coin value is in multiple of required amount, so we just take as much as we need
                return amount / coins[i];
            } else {
                return (int) 1e9;
            }
        }

        if (dp[i][amount] != (int) 1e9) {
            return dp[i][amount];
        }

        int withoutCurrent = MinimumCoins(coins, i + 1, amount, dp);
        int withCurrent = (int) 1e9;
        if (coins[i] <= amount) {
            withCurrent = 1 + MinimumCoins(coins, i, amount - coins[i], dp);
        }

        int min = Math.min(withCurrent, withoutCurrent);
        dp[i][amount] = min;
        return min;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions:
When coin 0 alone exists, if amount required are in multiples of coin0, we take it
Ex: coin 0 is 3 and amount is 27
We start filling 3-1, 6-2, 9-3, 12-4, 15-5, 18-6, 21-7, 24-8
and everything else is impossible, so 1e9
 */
class Solution3 {
    public int MinimumCoins(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        // Known solutions
        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) {
                dp[0][i] = i / coins[0];
            } else {
                dp[0][i] = (int) 1e9;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                int withoutCurrent = dp[i - 1][j]; // select all from prev coin(s)
                int withCurrent = (int) 1e9;
                if (j >= coins[i]) {
                    withCurrent = 1 + dp[i][j - coins[i]]; // select as much as possible from current coin
                }
                dp[i][j] = Math.min(withCurrent, withoutCurrent);
            }

        }
        int ans = dp[n - 1][amount];
        return ans >= (int) 1e9 ? -1 : ans;
    }
}

/*
Step 4 - Space Optimization

T - O(n*amount)
S - O(amount)
 */
class Solution4 {
    public int MinimumCoins(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int n = coins.length;
        int[][] dp = new int[2][amount + 1];

        // Known solutions
        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) {
                dp[0][i] = i / coins[0];
            } else {
                dp[0][i] = (int) 1e9;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                int withoutCurrent = dp[0][j]; // select all from prev coin(s)
                int withCurrent = (int) 1e9;
                if (j >= coins[i]) {
                    withCurrent = 1 + dp[1][j - coins[i]]; // select as much as possible from current coin
                }
                dp[1][j] = Math.min(withCurrent, withoutCurrent);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, amount + 1);
        }
        int ans = dp[0][amount];
        return ans >= (int) 1e9 ? -1 : ans;
    }
}
