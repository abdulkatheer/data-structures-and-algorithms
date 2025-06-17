package io.abdul.dynamic_programming.dp_on_stocks.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-stocks/best-time-to-buy-and-sell-stock
public class BuySell {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: Example input with profit
        int[] arr1 = {10, 7, 5, 8, 11, 9};
        assertEquals(6, solution.stockBuySell(arr1, arr1.length), "Maximum profit for arr [10, 7, 5, 8, 11, 9] should be 6");

        // Test Case 2: Example input with no profit
        int[] arr2 = {5, 4, 3, 2, 1};
        assertEquals(0, solution.stockBuySell(arr2, arr2.length), "Maximum profit for arr [5, 4, 3, 2, 1] should be 0");

        // Test Case 3: Example input with mixed values
        int[] arr3 = {3, 8, 1, 4, 6, 2};
        assertEquals(5, solution.stockBuySell(arr3, arr3.length), "Maximum profit for arr [3, 8, 1, 4, 6, 2] should be 5");

        // Test Case 4: Single day (no transaction possible)
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
Step 1: Recursive Solution

T - O(n)
S - O(n) - stack

Head recursion

If I sell it on the best-selling day from last, what's the best day to buy
 */
class Solution {
    public int stockBuySell(int[] arr, int n) {
        int[] maxProfit = new int[1];
        stockBuySell(arr, 0, maxProfit);
        return Math.max(maxProfit[0], 0);
    }

    private int stockBuySell(int[] arr, int i, int[] max) {
        if (i == arr.length - 1) { // Buy and sell on last day makes zero profit
            max[0] = 0;
            return arr[i]; // Max is last element itself
        }

        int bestSellingPrice = stockBuySell(arr, i + 1, max);
        max[0] = Math.max(max[0], bestSellingPrice - arr[i]);
        return Math.max(bestSellingPrice, arr[i]);
    }
}

/*
Step 2: Recursive Solution

T - O(n)
S - O(n) - stack

Tail recursion

If I buy it on best-buying day, what's the best day to sell
 */
class Solution2 {
    public int stockBuySell(int[] arr, int n) {
        int[] maxProfit = new int[1];
        stockBuySell(arr, arr[0], 0, maxProfit);
        return Math.max(maxProfit[0], 0);
    }

    private int stockBuySell(int[] arr, int bestBuyingPrice, int i, int[] max) {
        if (i == arr.length - 1) { // Buy and sell on first day makes zero profit
            max[0] = Math.max(arr[i] - bestBuyingPrice, max[0]);
            return Math.min(bestBuyingPrice, arr[i]); // Min is last element itself
        }

        max[0] = Math.max(arr[i] - bestBuyingPrice, max[0]);
        return stockBuySell(arr, Math.min(bestBuyingPrice, arr[i]), i + 1, max);
    }
}

/*
Step 3: Iterative solution

T - O(n)
S - O(1)

 */
class Solution3 {
    public int stockBuySell(int[] arr, int n) {
        int maxProfit = 0;
        int bestBuyingPrice = arr[0];

        for (int i = 1; i < n; i++) {
            maxProfit = Math.max(arr[i] - bestBuyingPrice, maxProfit);
            bestBuyingPrice = Math.min(bestBuyingPrice, arr[i]);
        }
        return maxProfit;
    }
}