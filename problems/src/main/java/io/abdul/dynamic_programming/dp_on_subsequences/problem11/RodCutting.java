package io.abdul.dynamic_programming.dp_on_subsequences.problem11;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RodCutting {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input with maximum value 25
        int[] price1 = {1, 6, 8, 9, 10, 19, 7, 20};
        int N1 = 8;
        assertEquals(25, solution.RodCutting(price1, N1),
                "Maximum value for rod of length 8 with prices [1, 6, 8, 9, 10, 19, 7, 20] should be 25");

        // Test Case 2: Example input with maximum value 10
        int[] price2 = {1, 5, 8, 9};
        int N2 = 4;
        assertEquals(10, solution.RodCutting(price2, N2),
                "Maximum value for rod of length 4 with prices [1, 5, 8, 9] should be 10");

        // Test Case 3: Example input with maximum value 22
        int[] price3 = {5, 5, 8, 9, 10, 17, 17, 20};
        int N3 = 8;
        assertEquals(40, solution.RodCutting(price3, N3),
                "Maximum value for rod of length 8 with prices [5, 5, 8, 9, 10, 17, 17, 20] should be 22");

        // Test Case 5: Edge case with single price
        int[] price5 = {10};
        int N5 = 1;
        assertEquals(10, solution.RodCutting(price5, N5),
                "Maximum value for rod of length 1 with prices [10] should be 10");

        // Test Case 6: Large input with increasing prices
        int[] price6 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int N6 = 10;
        assertEquals(10, solution.RodCutting(price6, N6),
                "Maximum value for rod of length 10 with prices [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] should be 10");

        // Test Case 7: Large input with high prices
        int[] price7 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int N7 = 10;
        assertEquals(100, solution.RodCutting(price7, N7),
                "Maximum value for rod of length 10 with prices [10, 20, 30, 40, 50, 60, 70, 80, 90, 100] should be 100");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

We either skip cut or cut with given inch i as much as possible
 */
class Solution {
    public int RodCutting(int price[], int n) {
        return rodCutting(price, 1, n);
    }

    private int rodCutting(int[] prices, int i, int N) {
        if (i == prices.length) { // At last iteration, I've no option than picking the entire remaining rod (if exists) uncut
            return N == 0 ? 0 : prices[N - 1]; // N will not be negative as we handle that below
        }

        int noCut = rodCutting(prices, i + 1, N);
        int cut = 0;
        if (i <= N) {
            cut = prices[i - 1] + rodCutting(prices, i, N - i);
        }

        return Math.max(noCut, cut);
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

 */
class Solution2 {
    public int RodCutting(int price[], int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return rodCutting(price, 1, n, dp);
    }

    private int rodCutting(int[] prices, int i, int N, int[][] dp) {
        if (i == prices.length) { // At last iteration, I've no option than picking the entire remaining rod (if exists) uncut
            return N == 0 ? 0 : prices[N - 1]; // N will not be negative as we handle that below
        }

        if (dp[i][N] != -1) {
            return dp[i][N];
        }

        int noCut = rodCutting(prices, i + 1, N, dp);
        int cut = 0;
        if (i <= N) {
            cut = prices[i - 1] + rodCutting(prices, i, N - i, dp);
        }

        int max = Math.max(noCut, cut);
        dp[i][N] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp

Known solutions
At inch 1, we cut all into one inches
Let's say Max length is 8 and price[0] is 2
0 - 0
1 - 2
2 - 4 (x2)
3 - 6 (x3)
.
.
8 - 16 (x8)
 */
class Solution3 {
    public int RodCutting(int price[], int n) {
        int[][] dp = new int[n + 1][n + 1]; // 0th row and column is unused
        /*
        dp[i][j] stores the max price possible with prices available upto i and for a rod of length j
        dp[n][n] stores the max price possible with all prices available and for a full length rod
         */

        // Know solutions
        // if cut can be done in only 1 unit, and we've rod length from 1 to n
        for (int i = 1; i <= n; i++) {
            dp[1][i] = i * price[0];
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int noCut = dp[i - 1][j];
                int cut = 0;
                if (j >= i) {
                    cut = price[i - 1] + dp[i][j - i];
                }

                dp[i][j] = Math.max(cut, noCut);
            }
        }

        return dp[n][n];
    }
}

/*
Step 4 - Space Optimization

 */
class Solution4 {
    public int RodCutting(int price[], int n) {
        int[][] dp = new int[2][n + 1]; // 0th row and column is unused

        // Know solutions
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i * price[0];
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int noCut = dp[0][j];
                int cut = 0;
                if (j >= i) {
                    cut = price[i - 1] + dp[1][j - i];
                }

                dp[1][j] = Math.max(cut, noCut);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n + 1);
        }

        return dp[0][n];
    }
}