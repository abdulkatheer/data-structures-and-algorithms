package io.abdul.dynamic_programming.dp_on_stocks.problem4;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuySell4 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();
//        Solution3a solution = new Solution3a();

        // Test Case 1: Example input with k = 2
        int[] arr1 = {3, 2, 6, 5, 0, 3};
        int k1 = 2;
        assertEquals(7, solution.stockBuySell(arr1, arr1.length, k1), "Maximum profit for arr [3, 2, 6, 5, 0, 3] with k = 2 should be 7");

        // Test Case 2: Example input with k = 3
        int[] arr2 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        int k2 = 3;
        assertEquals(15, solution.stockBuySell(arr2, arr2.length, k2), "Maximum profit for arr [1, 2, 4, 2, 5, 7, 2, 4, 9, 0] with k = 3 should be 15");

        // Test Case 3: Example input with k = 2
        int[] arr3 = {1, 3, 2, 8, 4, 9};
        int k3 = 2;
        assertEquals(12, solution.stockBuySell(arr3, arr3.length, k3), "Maximum profit for arr [1, 3, 2, 8, 4, 9] with k = 2 should be 12");

        // Test Case 4: Edge case with k = 0 (no transactions allowed)
        int[] arr4 = {3, 2, 6, 5, 0, 3};
        int k4 = 0;
        assertEquals(0, solution.stockBuySell(arr4, arr4.length, k4), "Maximum profit for arr [3, 2, 6, 5, 0, 3] with k = 0 should be 0");

        // Test Case 5: Edge case with single day (no transaction possible)
        int[] arr5 = {10};
        int k5 = 2;
        assertEquals(0, solution.stockBuySell(arr5, arr5.length, k5), "Maximum profit for arr [10] with k = 2 should be 0");

        // Test Case 6: Edge case with all prices the same
        int[] arr6 = {7, 7, 7, 7, 7};
        int k6 = 3;
        assertEquals(0, solution.stockBuySell(arr6, arr6.length, k6), "Maximum profit for arr [7, 7, 7, 7, 7] with k = 3 should be 0");

        // 19,45,22,18,1,24,23,15,19,32,69,85,13,3,33,31,58,96,83,43,25
        int[] arr9 = {19, 45, 22, 18, 1, 24, 23, 15, 19, 32, 69, 85, 13, 3, 33, 31, 58, 96, 83, 43, 25};
        int k9 = 2;
        assertEquals(177, solution.stockBuySell(arr9, arr9.length, k9), "Maximum profit for arr [7, 7, 7, 7, 7] with k = 3 should be 0");

        // Test Case 7: Large input with increasing prices
        int[] arr7 = new int[1000];
        for (int i = 0; i < arr7.length; i++) {
            arr7[i] = i;
        }
        int k7 = 100;
        assertEquals(999, solution.stockBuySell(arr7, arr7.length, k7), "Maximum profit for large increasing prices with k = 100 should be 999");

        // Test Case 8: Large input with decreasing prices
        int[] arr8 = new int[1000];
        for (int i = 0; i < arr8.length; i++) {
            arr8[i] = 1000 - i;
        }
        int k8 = 100;
        assertEquals(0, solution.stockBuySell(arr8, arr8.length, k8), "Maximum profit for large decreasing prices with k = 100 should be 0");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

 */
class Solution {
    public int stockBuySell(int[] arr, int n, int k) {
        return stockBuySell(arr, 0, 1, k);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int transactionsLeft) {
        if (i == arr.length) {
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

/*
Step 2 - Memoization

T - O(n*k)
S - O(n*k) - stack + dp

 */
class Solution2 {
    public int stockBuySell(int[] arr, int n, int k) {
        int[][][] dp = new int[n][2][k + 1];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, Integer.MIN_VALUE);
            }
        }
        return stockBuySell(arr, 0, 1, k, dp);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int transactionsLeft, int[][][] dp) {
        if (i == arr.length) {
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
            int sell = arr[i] + stockBuySell(arr, i + 1, 1, transactionsLeft - 1, dp); // reduce only after selling - day n-1 to day 0
            int skipSell = stockBuySell(arr, i + 1, 0, transactionsLeft, dp);
            profit = Math.max(sell, skipSell);
        }

        dp[i][canBuy][transactionsLeft] = profit;
        return profit;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*k)
S - O(n*k) - dp

Recursive as-is to Iterative
 */
class Solution3 {
    public int stockBuySell(int[] arr, int n, int k) {
        int[][][] dp = new int[n + 1][2][k + 1];

        // Known solutions:
        // Last day starts with sell

        // Capacity reduces from k -> .. -> 1 -> 0
        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 1; cap <= k; cap++) {
                int buy = -arr[i] + dp[i + 1][0][cap]; // transactionsLeft is counted during sell
                int skipBuy = dp[i + 1][1][cap];

                int sell = arr[i] + dp[i + 1][1][cap - 1];
                int skipSell = dp[i + 1][0][cap];

                dp[i][1][cap] = Math.max(buy, skipBuy);
                dp[i][0][cap] = Math.max(sell, skipSell);
            }
        }

        return dp[0][1][k];
    }
}

/*
Step 4  - Space Optimization

T - O(n*k)
S - O(1)

 */
class Solution4 {
    public int stockBuySell(int[] arr, int n, int k) {
        int[][][] dp = new int[2][2][k + 1];

        // Known solutions:
        // Last day starts with sell

        // Capacity reduces from k -> .. -> 1 -> 0
        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 1; cap <= k; cap++) {
                int buy = -arr[i] + dp[1][0][cap]; // transactionsLeft is counted during sell
                int skipBuy = dp[1][1][cap];
                dp[0][1][cap] = Math.max(buy, skipBuy);

                int sell = arr[i] + dp[1][1][cap - 1];
                int skipSell = dp[1][0][cap];
                dp[0][0][cap] = Math.max(sell, skipSell);
            }
            System.arraycopy(dp[0][0], 0, dp[1][0], 0, k + 1);
            System.arraycopy(dp[0][1], 0, dp[1][1], 0, k + 1);
        }

        return dp[0][1][k];
    }
}

/*
Step 3 - Traditional base case thinking
 */
class Solution3a {
    public int stockBuySell(int[] arr, int n, int k) {
        int[][][] dp = new int[n][2][k + 1]; // we gonna use cap from 0 to k

        // Known solutions
        // At day 0, we can only buy
        // We can reduce transactionsLeft at sell
        // So it reduces from 1 to k
        for (int i = 1; i <= k; i++) {
            dp[0][1][i] = -arr[0];
        }

        // from day 1 to n-1
        for (int i = 1; i < n; i++) {
            for (int cap = 1; cap <= k; cap++) {
                int buy = -arr[i] + dp[i - 1][0][cap - 1]; // looking for sell in past with previous capacity
                int skipBuy = dp[i - 1][1][cap]; // looking for buy in past with same capacity

                int sell = arr[i] + dp[i - 1][1][cap]; // looking for buy in past with same capacity
                int skipSell = dp[i - 1][0][cap]; // looking for sell in past with same capacity

                dp[i][0][cap] = Math.max(sell, skipSell);
                dp[i][1][cap] = Math.max(buy, skipBuy);
            }
        }

        return dp[n - 1][0][k]; // best is sell at last day and k transactions consumed
    }
}