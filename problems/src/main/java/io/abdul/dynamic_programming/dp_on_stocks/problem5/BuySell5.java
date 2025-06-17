package io.abdul.dynamic_programming.dp_on_stocks.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuySell5 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with fee = 1
        int[] arr1 = {1, 3, 4, 0, 2};
        int fee1 = 1;
        assertEquals(3, solution.stockBuySell(arr1, arr1.length, fee1), "Maximum profit for arr [1, 3, 4, 0, 2] with fee = 1 should be 3");

        // Test Case 2: Example input with fee = 2
        int[] arr2 = {1, 3, 2, 8, 4, 9};
        int fee2 = 2;
        assertEquals(8, solution.stockBuySell(arr2, arr2.length, fee2), "Maximum profit for arr [1, 3, 2, 8, 4, 9] with fee = 2 should be 8");

        // Test Case 3: Example input with fee = 3
        int[] arr3 = {10, 3, 7, 5, 1, 3};
        int fee3 = 3;
        assertEquals(1, solution.stockBuySell(arr3, arr3.length, fee3), "Maximum profit for arr [10, 3, 7, 5, 1, 3] with fee = 3 should be 0");

        // Test Case 4: Edge case with no transaction fee
        int[] arr4 = {1, 2, 3, 4, 5};
        int fee4 = 0;
        assertEquals(4, solution.stockBuySell(arr4, arr4.length, fee4), "Maximum profit for arr [1, 2, 3, 4, 5] with fee = 0 should be 4");

        // Test Case 5: Edge case with single day (no transaction possible)
        int[] arr5 = {10};
        int fee5 = 2;
        assertEquals(0, solution.stockBuySell(arr5, arr5.length, fee5), "Maximum profit for arr [10] with fee = 2 should be 0");

        // Test Case 6: Edge case with all prices the same
        int[] arr6 = {7, 7, 7, 7, 7};
        int fee6 = 1;
        assertEquals(0, solution.stockBuySell(arr6, arr6.length, fee6), "Maximum profit for arr [7, 7, 7, 7, 7] with fee = 1 should be 0");

        // Test Case 7: Large input with increasing prices
        int[] arr7 = new int[10000];
        for (int i = 0; i < arr7.length; i++) {
            arr7[i] = i;
        }
        int fee7 = 1;
        assertEquals(9998, solution.stockBuySell(arr7, arr7.length, fee7), "Maximum profit for large increasing prices with fee = 1 should be 998");

        // Test Case 8: Large input with decreasing prices
        int[] arr8 = new int[10000];
        for (int i = 0; i < arr8.length; i++) {
            arr8[i] = 10000 - i;
        }
        int fee8 = 1;
        assertEquals(0, solution.stockBuySell(arr8, arr8.length, fee8), "Maximum profit for large decreasing prices with fee = 1 should be 0");

    }
}

/*
Step 1 - Top-down recursive solution

T- O(2^n)
S - O(n) - stack

 */
class Solution {
    public int stockBuySell(int[] arr, int n, int fee) {
        return stockBuySell(arr, 0, 1, fee);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int fee) {
        if (i == arr.length) {
            return 0;
        }

        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, fee);
            int skipBuy = stockBuySell(arr, i + 1, 1, fee);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] - fee + stockBuySell(arr, i + 1, 1, fee);
            int skipSell = stockBuySell(arr, i + 1, 0, fee);
            profit = Math.max(sell, skipSell);
        }

        return profit;
    }
}

/*
Step 2 - Memoization

T- O(n)
S - O(n) - stack + dp

 */
class Solution2 {
    public int stockBuySell(int[] arr, int n, int fee) {
        int[][] dp = new int[n][2];
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MIN_VALUE);
        }
        return stockBuySell(arr, 0, 1, fee, dp);
    }

    private int stockBuySell(int[] arr, int i, int canBuy, int fee, int[][] dp) {
        if (i == arr.length) {
            return 0;
        }

        if (dp[i][canBuy] != Integer.MIN_VALUE) {
            return dp[i][canBuy];
        }

        int profit;
        if (canBuy == 1) {
            int buy = -arr[i] + stockBuySell(arr, i + 1, 0, fee, dp);
            int skipBuy = stockBuySell(arr, i + 1, 1, fee, dp);
            profit = Math.max(buy, skipBuy);
        } else {
            int sell = arr[i] - fee + stockBuySell(arr, i + 1, 1, fee, dp);
            int skipSell = stockBuySell(arr, i + 1, 0, fee, dp);
            profit = Math.max(sell, skipSell);
        }

        dp[i][canBuy] = profit;

        return profit;
    }
}

/*
Step 3 - Bottom-up iterative solution

T- O(n)
S - O(n) - dp

 */
class Solution3 {
    public int stockBuySell(int[] arr, int n, int fee) {
        int[][] dp = new int[n + 1][2];

        for (int i = n - 1; i >= 0; i--) {
            int buy = -arr[i] + dp[i + 1][0];
            int skipBuy = dp[i + 1][1];
            dp[i][1] = Math.max(buy, skipBuy);

            int sell = arr[i] - fee + dp[i + 1][1];
            int skipSell = dp[i + 1][0];
            dp[i][0] = Math.max(sell, skipSell);
        }

        return dp[0][1];
    }
}

/*
Step 4 - Space Optimization

T- O(n)
S - O(1) - dp

 */
class Solution4 {
    public int stockBuySell(int[] arr, int n, int fee) {
        int[][] dp = new int[2][2];

        for (int i = n - 1; i >= 0; i--) {
            int buy = -arr[i] + dp[1][0];
            int skipBuy = dp[1][1];
            dp[0][1] = Math.max(buy, skipBuy);

            int sell = arr[i] - fee + dp[1][1];
            int skipSell = dp[1][0];
            dp[0][0] = Math.max(sell, skipSell);

            System.arraycopy(dp[0], 0, dp[1], 0, 2);
        }

        return dp[0][1];
    }
}