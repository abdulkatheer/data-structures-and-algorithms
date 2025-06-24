package io.abdul.dynamic_programming.dp_on_stocks.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-stocks/best-time-to-buy-and-sell-stock-iii
public class BuySell3 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution3a solution = new Solution3a();
//        Solution3b solution = new Solution3b();
//        Solution4 solution = new Solution4();

//        Solution1a solution = new Solution1a();
        Solution3c solution = new Solution3c();

        // Test Case 1: Example input with two transactions
        int[] arr1 = {4, 2, 7, 1, 11, 5};
        assertEquals(15, solution.stockBuySell(arr1, arr1.length), "Maximum profit for arr [4, 2, 7, 1, 11, 5] should be 15");

        // Test Case 2: Example input with two transactions
        int[] arr2 = {1, 3, 2, 8, 4, 9};
        assertEquals(12, solution.stockBuySell(arr2, arr2.length), "Maximum profit for arr [1, 3, 2, 8, 4, 9] should be 12");

        // Test Case 3: Example input with mixed values
        int[] arr3 = {5, 7, 2, 10, 6, 9};
        assertEquals(11, solution.stockBuySell(arr3, arr3.length), "Maximum profit for arr [5, 7, 2, 10, 6, 9] should be 12");

//         Test Case 4: Single day (no transaction possible)
        int[] arr4 = {10};
        assertEquals(0, solution.stockBuySell(arr4, arr4.length), "Maximum profit for arr [10] should be 0");

        // Test Case 5: All prices are the same
        int[] arr5 = {7, 7, 7, 7, 7};
        assertEquals(0, solution.stockBuySell(arr5, arr5.length), "Maximum profit for arr [7, 7, 7, 7, 7] should be 0");

        // Test Case 6: Large input with increasing prices
        int[] arr6 = new int[100000];
        for (int i = 0; i < arr6.length; i++) {
            arr6[i] = i;
        }
        assertEquals(99999, solution.stockBuySell(arr6, arr6.length), "Maximum profit for large increasing prices should be 99999");

        // Test Case 7: Large input with decreasing prices
        int[] arr7 = new int[100000];
        for (int i = 0; i < arr7.length; i++) {
            arr7[i] = 100000 - i;
        }
        assertEquals(0, solution.stockBuySell(arr7, arr7.length), "Maximum profit for large decreasing prices should be 0");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

Same as BuySell2. but has additional transaction limit check
 */
class Solution {
    public int stockBuySell(int[] arr, int n) {
        return stockBuySell(arr, 0, 1, 2);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int transactionsLeft) {
        if (arr.length == i) { // Invalid position
            return 0;
        }

        if (transactionsLeft == 0) {
            return 0;
        }

        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, transactionsLeft);
            int skipBuy = stockBuySell(arr, i + 1, 1, transactionsLeft);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, 1, transactionsLeft - 1);
            int skipSell = stockBuySell(arr, i + 1, 0, transactionsLeft);
            profit = Math.max(sell, skipSell);
        }

        return profit;
    }
}

class Solution1a {
    public int stockBuySell(int[] arr, int n) {
        return stockBuySell(arr, 0, 1, 2);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int transactionsLeft) {
        if (transactionsLeft == 0) {
            return 0;
        }

        // On the last day, I can only sell. Even if we buy it reduces profit. If we skip sell, we reduce profit too.
        if (arr.length - 1 == i) {
            return canBuy == 1 ? 0 : arr[i];
        }

        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, transactionsLeft);
            int skipBuy = stockBuySell(arr, i + 1, 1, transactionsLeft);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, 1, transactionsLeft - 1); // reduce transactionsLeft on sell for guaranteed sell
            int skipSell = stockBuySell(arr, i + 1, 0, transactionsLeft);
            profit = Math.max(sell, skipSell);
        }

        return profit;
    }
}

/*
Step 2 - Memoization

T - O(n)
S - O(n^2) - stack + dp

For same i, canBuy and transactionsLeft, calc are repeated
 */
class Solution2 {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[n][2][3];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, Integer.MIN_VALUE);
            }
        }
        return stockBuySell(arr, 0, 1, 2, dp);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int transactionsLeft, int[][][] dp) {
        if (arr.length == i) { // Invalid position
            return 0;
        }

        if (transactionsLeft == 0) {
            return 0;
        }

        if (dp[i][canBuy][transactionsLeft] != Integer.MIN_VALUE) {
            return dp[i][canBuy][transactionsLeft];
        }

        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, transactionsLeft, dp);
            int skipBuy = stockBuySell(arr, i + 1, 1, transactionsLeft, dp);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, 1, transactionsLeft - 1, dp);
            int skipSell = stockBuySell(arr, i + 1, 0, transactionsLeft, dp);
            profit = Math.max(sell, skipSell);
        }

        dp[i][canBuy][transactionsLeft] = profit;
        return profit;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n)
S - O(n^2) - dp

Known solutions:
At day 0, only option is to buy dp[0][1][0] = -arr[0]; dp[0][1][1] = -arr[0]; dp[0][1][2] = -arr[0] ; dp[0][0][0] = 0; dp[0][0][1] = 0; dp[0][0][2] = 0

NOTE: TransactionsLeft increased on buy
1) When we buy, we look for sell with reduced capacity
buy = -arr[i] + dp[i-1][0][cap-1]
skipBuy = dp[i-1][1][cap]
2) When we sell, we look for buy at same capacity
sell = arr[i] + dp[i-1][1][cap]
skipSell = dp[i-1][0][cap]
3) Best result will be on the last day when we choose to sell and utilizing full capacity 2. dp[n-1][0][2]

At day 1
dp[1][0][2] = Math.max(arr[i] + dp[0][1][1], dp[0][0][2])
dp[1][0][1] = Math.max(arr[i] + dp[0][1][0], dp[0][0][1])

dp[1][1][2] = Math.max(-arr[i] + dp[0][0][1], dp[0][1][2])
dp[1][1][2] = Math.max(-arr[i] + dp[0][0][0], dp[0][1][1])
 */
class Solution3 {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[n][2][3];

        // Known solutions
        // No sell at day 0
        dp[0][0][1] = 0;
        dp[0][0][2] = 0;
        // Only buy at day 0
        dp[0][1][1] = -arr[0];
        dp[0][1][2] = -arr[0];

        for (int i = 1; i < n; i++) {
            for (int cap = 1; cap <= 2; cap++) {
                // Capacity starts from 0 -> 1 -> 2 and not reducing from 2 -> 1 -> 0
                // That's why when a new buy happens, it takes the profit from previous capacity, which will be cap-1
                // If it were other way around, it would have taken from cap+1

                // Buy
                // previous day's sell profit with last capacity - Today's buying price
                // Intuition 1: Why sell? Bcz we need to sell before buying
                // Intuition 2: Why cap-1? Bcz to buy today, something might be sold at previous capacity.
                // Like day 0&1 utilised 1 capacity and its result will be in dp[1][0][1]
                // When we buy at day 2, we take that profit - today's buying price
                int buy = -arr[i] + dp[i - 1][0][cap - 1]; // Buy, if we don't hold anything yesterday. Use one of the remaining transactions.
                /*
                We've opportunity to buy, but if we skip, we just take the profit of yesterday's buy profit at same capacity
                 */
                int skipBuy = dp[i - 1][1][cap]; // same as yesterday

                // Sell
                /*
                yesterday's profit after buying at some capacity + Today's profit
                Intuition 1: Why buy? to sell something, it should have been bought already. So yesterday's profit for buy at same capacity
                Intuition 2: Why cap? Bcz we utilise cap when buy happens. So just the last buy profit at capacity.
                 */
                int sell = arr[i] + dp[i - 1][1][cap]; // Sell, if holding yesterday
                int skipSell = dp[i - 1][0][cap]; // same as yesterday

                dp[i][1][cap] = Math.max(buy, skipBuy);
                dp[i][0][cap] = Math.max(sell, skipSell);
            }
        }

        return dp[n - 1][0][2]; // should have sold all; best is utilising all 2 transaction limit
    }
}

/*
We could convert recursive as-is to iterative.
From the last day to first day
 */
class Solution3a {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[n + 1][2][3];

        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 1; cap <= 2; cap++) {
                // NOTE: Capacity reduces from 2 -> 1 -> 0 from day 0 to n-1

                /*
                Buy
                When we buy, we're looking for sell in the future with same capacity
                When we skip buy, we're looking for buy in the future with same capacity
                Buy today or in the future
                 */
                int buy = -arr[i] + dp[i + 1][0][cap];
                int skipBuy = dp[i + 1][1][cap];

                /*
                Sell
                When we sell, we're looking for next buy in the future with next possible capacity (2 -> 1 -> 0)
                When we sell, we're looking for sell in the future with same capacity
                 */
                int sell = arr[i] + dp[i + 1][1][cap - 1];
                int skipSell = dp[i + 1][0][cap];

                dp[i][1][cap] = Math.max(buy, skipBuy);
                dp[i][0][cap] = Math.max(sell, skipSell);
            }
        }

        return dp[0][1][2];
    }
}

/*
Similar to Solution3, but from last to 1st day

Difference between Solution3 and Solution3b:
When we go from day 0 to n-1, capacity reduces at sell as our first option is buy
1. We first buy, as that's the only option at day 0
2. When we buy, we look for sell in the past with previous capacity (cap+1 or cap-1 depending on how to treat cap 2 -> 1 -> 0 or 0 -> 1 -> 2
3. When we sell, we look for buy in the past with same capacity

When we go from day n-1 to 0, capacity reduces at buy as our first option is sell
1. We first sell, as that's the only option at day n-1
2. When we buy, we look for sell in the future with previous capacity (cap+1 or cap-1 depending on how to treat cap 2 -> 1 -> 0 or 0 -> 1 -> 2
3. When we sell, we look for buy in the past with same capacity
 */
class Solution3b {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[n][2][3];

        // Known solutions:
        // At last day alone, we can only sell
        // For buy, default value is zero
        dp[n - 1][0][1] = arr[n - 1];
        dp[n - 1][0][2] = arr[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            for (int cap = 1; cap <= 2; cap++) {
                // NOTE: Capacity reduces from 2 -> 1 -> 0 from day 0 to n-1

                /*
                Buy
                When we buy, we're looking for sell in the future with same capacity
                When we skip buy, we're looking for buy in the future with same capacity
                Buy today or in the future
                 */
                int buy = -arr[i] + dp[i + 1][0][cap];
                int skipBuy = dp[i + 1][1][cap];

                /*
                Sell
                When we sell, we're looking for next buy in the future with next possible capacity (2 -> 1 -> 0)
                When we sell, we're looking for sell in the future with same capacity
                 */
                int sell = arr[i] + dp[i + 1][1][cap - 1];
                int skipSell = dp[i + 1][0][cap];

                dp[i][1][cap] = Math.max(buy, skipBuy);
                dp[i][0][cap] = Math.max(sell, skipSell);
            }
        }

        return dp[0][1][2];
    }
}

class Solution3c {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[n][2][3];

        // start from day 0 to n-1
        // start from capacity 2 to 0
        // At day 0, we can only buy
        dp[0][1][0] = -arr[0];
        dp[0][1][1] = -arr[0];

        for (int i = 1; i <= n - 1; i++) {
            for (int cap = 0; cap <= 1; cap++) {
                int buy = -arr[i] + dp[i - 1][0][cap + 1];
                int skipBuy = dp[i - 1][1][cap];

                int sell = arr[i] + dp[i - 1][1][cap];
                int skipSell = dp[i - 1][0][cap];

                dp[i][0][cap] = Math.max(sell, skipSell);
                dp[i][1][cap] = Math.max(buy, skipBuy);
            }
        }

        // Best is when cap is fully drained at last day after a sell
        return dp[n - 1][0][0];
    }
}

/*
Step 4: Space Optimization

T - O(n)
S - O(1) - dp

We only need 2 dp compartments to store future day's and current day's profit
 */
class Solution4 {
    public int stockBuySell(int[] arr, int n) {
        int[][][] dp = new int[2][2][3];

        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 1; cap <= 2; cap++) {
                // NOTE: Capacity reduces from 2 -> 1 -> 0 from day 0 to n-1

                /*
                Buy
                When we buy, we're looking for sell in the future with same capacity
                When we skip buy, we're looking for buy in the future with same capacity
                Buy today or in the future
                 */
                int buy = -arr[i] + dp[1][0][cap];
                int skipBuy = dp[1][1][cap];

                /*
                Sell
                When we sell, we're looking for next buy in the future with next possible capacity (2 -> 1 -> 0)
                When we sell, we're looking for sell in the future with same capacity
                 */
                int sell = arr[i] + dp[1][1][cap - 1];
                int skipSell = dp[1][0][cap];

                dp[0][1][cap] = Math.max(buy, skipBuy);
                dp[0][0][cap] = Math.max(sell, skipSell);
            }

            System.arraycopy(dp[0][0], 0, dp[1][0], 0, 3);
            System.arraycopy(dp[0][1], 0, dp[1][1], 0, 3);
        }

        return dp[0][1][2];
    }
}