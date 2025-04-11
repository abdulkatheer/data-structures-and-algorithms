package io.abdul.array.dynamic_programming.problem3;

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
public class StockMaxProfit {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        System.out.println(solution.maxProfit(new int[]{1}));
        System.out.println(solution.maxProfit(new int[]{3, 1}));
        System.out.println(solution.maxProfit(new int[]{1, 10}));
        System.out.println(solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }
}

// #tag_recursion
// Step 1: Recursive (Top-down)
class Solution {
    public int maxProfit(int[] prices) {
        return maxProfit(prices, prices.length - 1);
    }

    /*
    7 -> 0
    7 1 -> max(0, 0) -> 0
    7 1 5 -> max(4, 0) -> 4
    7 1 5 3 -> Max(2, 4) -> 4
    7 1 5 3 6 -> Max(5, 4) -> 5
    7 1 5 3 6 4 -> Max(5, 3) -> 5
     */
    private static int maxProfit(int[] prices, int i) {
        if (i == 0) {
            return 0;
        }
        int maxProfitAtI = 0;
        for (int j = 0; j < i; j++) {
            maxProfitAtI = Math.max(prices[i] - prices[j], maxProfitAtI);
        }
        return Math.max(maxProfitAtI, maxProfit(prices, i - 1));
    }
}

// Step 2: Iterative (Bottom-up)
class Solution2 {
    public int maxProfit(int[] prices) {
        int maxProfitSoFar = 0;
        for (int i = 1; i < prices.length; i++) {
            int maxProfitAtI = 0;
            for (int j = 0; j < i; j++) {
                maxProfitAtI = Math.max(prices[i] - prices[j], maxProfitAtI);
            }
            maxProfitSoFar = Math.max(maxProfitAtI, maxProfitSoFar);
        }

        return maxProfitSoFar;
    }
}

// Step 3: Iterative (Bottom-up) + Tabulation
/*
Idea here is, consider 0th as cheapest option
As we move on, if we find cheaper than this, any ways we'll get more profit. So move cheapest to current.
If we find costlier than current, check profit and update with maxSoFar
 */
class Solution3 {
    public int maxProfit(int[] prices) {
        int[] maxProfits = new int[prices.length];
        int cheapest = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < cheapest) {
                cheapest = prices[i];
            } else {
                maxProfits[i] = Math.max(maxProfits[i - 1], prices[i] - cheapest);
            }
        }

        return maxProfits[prices.length - 1];
    }
}

// Step 4: Iterative (Bottom-up) + Tabulation (Space optimized)
// We only need maxProfitAtLastIndex
class Solution4 {
    public int maxProfit(int[] prices) {
        int maxProfitSoFar = 0;
        int cheapest = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < cheapest) {
                cheapest = prices[i];
            } else {
                maxProfitSoFar = Math.max(maxProfitSoFar, prices[i] - cheapest);
            }
        }

        return maxProfitSoFar;
    }
}

