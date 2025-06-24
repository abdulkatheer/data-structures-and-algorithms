package io.abdul.dynamic_programming.dp_on_stocks.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-stocks/best-time-to-buy-and-sell-stock-ii
public class BuySell2 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution1a solution = new Solution1a();

        // Test Case 1: Example input with multiple transactions
        int[] arr1 = {9, 2, 6, 4, 7, 3};
        assertEquals(7, solution.stockBuySell(arr1, arr1.length), "Maximum profit for arr [9, 2, 6, 4, 7, 3] should be 7");

        // Test Case 2: Example input with increasing prices
        int[] arr2 = {2, 3, 4, 5, 6};
        assertEquals(4, solution.stockBuySell(arr2, arr2.length), "Maximum profit for arr [2, 3, 4, 5, 6] should be 4");

        // Test Case 3: Example input with decreasing prices
        int[] arr3 = {8, 6, 5, 4, 3};
        assertEquals(0, solution.stockBuySell(arr3, arr3.length), "Maximum profit for arr [8, 6, 5, 4, 3] should be 0");

        // Test Case 4: Single day (no transaction possible)
        int[] arr4 = {10};
        assertEquals(0, solution.stockBuySell(arr4, arr4.length), "Maximum profit for arr [10] should be 0");

        // Test Case 5: All prices are the same
        int[] arr5 = {7, 7, 7, 7, 7};
        assertEquals(0, solution.stockBuySell(arr5, arr5.length), "Maximum profit for arr [7, 7, 7, 7, 7] should be 0");

        // Test Case 6: Large input with alternating prices
        int[] arr6 = {1, 2, 1, 2, 1, 2};
        assertEquals(3, solution.stockBuySell(arr6, arr6.length), "Maximum profit for arr [1, 2, 1, 2, 1, 2] should be 3");

        // Test Case 7: Large input with increasing prices
        int[] arr7 = new int[100000];
        for (int i = 0; i < arr7.length; i++) {
            arr7[i] = i;
        }
        assertEquals(99999, solution.stockBuySell(arr7, arr7.length), "Maximum profit for large increasing prices should be 99999");

        // Test Case 8: Large input with decreasing prices
        int[] arr8 = new int[100000];
        for (int i = 0; i < arr8.length; i++) {
            arr8[i] = 100000 - i;
        }
        assertEquals(0, solution.stockBuySell(arr8, arr8.length), "Maximum profit for large decreasing prices should be 0");
    }
}

/*
Step 1 - Top-down recursion

T - O(2^n)
O - O(n) - stack

It's similar to power-set problem. Like take or don't take. 2^n solution.

But we've 4 options. 2 for buy. 2 for sell.
At any day, if we're allowed to buy, we can opt to buy that day or next day
At any day, if we're allowed to sell, we can opt to sell that day or next day

Assume the smallest problem is 1,6
At day 0, 2 options. Buy 1 or Don't buy
1
-

At day 1, 2 options. Sell or don't sell
6
-

When we sell, we have to do (sell - buy)
So when we buy, we do -buyPrice
and when we sell, we do +sellPrice
 */
class Solution {
    public int stockBuySell(int[] arr, int n) {
        return stockBuySell(arr, 0, true);
    }

    private int stockBuySell(int[] arr, int i, boolean canBuy) {
        if (i == arr.length) {
            return 0; // can't buy or sell
        }

        int profit;
        if (canBuy) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, false);
            int skipBuy = stockBuySell(arr, i + 1, true);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, true);
            int skipSell = stockBuySell(arr, i + 1, false);
            profit = Math.max(sell, skipSell);
        }

        return profit;
    }
}

class Solution1a {
    public int stockBuySell(int[] arr, int n) {
        return stockBuySell(arr, 0, true);
    }

    private int stockBuySell(int[] arr, int i, boolean canBuy) {
        if (i == arr.length - 1) {
            // skip buy - increases profit
            // skip sell - decreases profit
            // you can only sell on last day
            return canBuy ? 0 : arr[i];
        }

        int profit;
        if (canBuy) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, false);
            int skipBuy = stockBuySell(arr, i + 1, true);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, true);
            int skipSell = stockBuySell(arr, i + 1, false);
            profit = Math.max(sell, skipSell);
        }

        return profit;
    }
}

/*
Step 2 - Memoization

T - O(n)
S - O(n) - stack + dp

For same i and canBuy, calc is repeated
 */
class Solution2 {
    public int stockBuySell(int[] arr, int n) {
        int[][] dp = new int[n][2];
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MIN_VALUE);
        }
        return stockBuySell(arr, 0, 1, dp);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int[][] dp) {
        if (i == arr.length) {
            return 0; // can't buy or sell
        }
        if (dp[i][canBuy] != Integer.MIN_VALUE) {
            return dp[i][canBuy];
        }
        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, dp);
            int skipBuy = stockBuySell(arr, i + 1, 1, dp);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] + stockBuySell(arr, i + 1, 1, dp);
            int skipSell = stockBuySell(arr, i + 1, 0, dp);
            profit = Math.max(sell, skipSell);
        }

        dp[i][canBuy] = profit;
        return profit;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n)
S - O(n) - dp

Ex: 9, 2, 6, 4, 7, 3
Known solutions:
At day 0, 00 - 0; 01 - -9

 */
class Solution3 {
    public int stockBuySell(int[] arr, int n) {
        int[][] dp = new int[n][2];

        // Known solutions
        dp[0][0] = 0; // Nothing to sell
        dp[0][1] = -arr[0]; // Something to buy

        for (int i = 1; i < n; i++) {
            int buy = -arr[i] + dp[i - 1][0]; // If we opt to buy i, -i + sell of prev
            int skipBuy = dp[i - 1][1]; // If we opt to skip buy i, buy of prev

            int sell = arr[i] + dp[i - 1][1]; // If we opt to sell i, i + buy of prev
            int skipSell = dp[i - 1][0]; // If we opt to skip sell i, sell of prev

            dp[i][0] = Math.max(sell, skipSell); // Which is best, selling or skipSelling
            dp[i][1] = Math.max(buy, skipBuy); // buying or skipBuying
        }

        return dp[n - 1][0]; // Last step should be sell as we can't hold any end of the day, buy will be less than sell for sure as we minus
    }
}

/*
Step 4 - Space optimization

T - O(n)
S - O(1)

We only need last and current days data
 */
class Solution4 {
    public int stockBuySell(int[] arr, int n) {
        int[][] dp = new int[2][2];

        // Known solutions
        dp[0][0] = 0; // Nothing to sell
        dp[0][1] = -arr[0]; // Something to buy

        for (int i = 1; i < n; i++) {
            int buy = -arr[i] + dp[0][0]; // If we opt to buy i, -i + sell of prev
            int skipBuy = dp[0][1]; // If we opt to skip buy i, buy of prev

            int sell = arr[i] + dp[0][1]; // If we opt to sell i, i + buy of prev
            int skipSell = dp[0][0]; // If we opt to skipp sell i, sell of prev

            dp[1][0] = Math.max(sell, skipSell); // Which is best, selling or skipSelling
            dp[1][1] = Math.max(buy, skipBuy); // buying or skipBuying
            System.arraycopy(dp[1], 0, dp[0], 0, 2);
        }

        return dp[0][0]; // Last step should be sell as we can't hold any end of the day
    }
}