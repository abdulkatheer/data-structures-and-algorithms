package io.abdul.dynamic_programming.dp_1d.problem10;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/1d-dp/house-robber
// Same as MaximumSumNonAdjacent, just the array is circular
public class HouseRobber {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input money = [2, 1, 4, 9]
        int[] money1 = {2, 1, 4, 9};
        assertEquals(10, solution.houseRobber(money1), "Maximum loot for money [2, 1, 4, 9] should be 10");

        // Test Case 2: Example input money = [1, 5, 2, 1, 6]
        int[] money2 = {1, 5, 2, 1, 6};
        assertEquals(11, solution.houseRobber(money2), "Maximum loot for money [1, 5, 2, 1, 6] should be 11");

        // Test Case 3: Example input money = [9, 4, 1, 8]
        int[] money3 = {9, 4, 1, 8};
        assertEquals(12, solution.houseRobber(money3), "Maximum loot for money [9, 4, 1, 8] should be 12");

        // Test Case 4: Single house money = [5]
        int[] money4 = {5};
        assertEquals(5, solution.houseRobber(money4), "Maximum loot for money [5] should be 5");

        // Test Case 5: Two houses money = [3, 7]
        int[] money5 = {3, 7};
        assertEquals(7, solution.houseRobber(money5), "Maximum loot for money [3, 7] should be 7");

        // Test Case 6: Large input money = [10, 20, 30, 40, 50]
        int[] money6 = {10, 20, 30, 40, 50};
        assertEquals(80, solution.houseRobber(money6), "Maximum loot for money [10, 20, 30, 40, 50] should be 90");

        // Test Case 7: Edge case money = []
        int[] money7 = {1};
        assertEquals(1, solution.houseRobber(money7), "Maximum loot for empty money array should be 0");

        // Test Case 8: Edge case money = [0, 0, 0]
        int[] money8 = {0, 0, 0};
        assertEquals(0, solution.houseRobber(money8), "Maximum loot for money [0, 0, 0] should be 0");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n) - 2 * 2^n
S - O(n) - stack
 */
class Solution {
    public int houseRobber(int[] money) {
        int n = money.length;
        if (n == 1) {
            return money[0];
        }
        return Math.max(houseRobber(money, 0, n - 2), houseRobber(money, 1, n - 1));
    }

    private int houseRobber(int[] money, int pos, int lastPos) {
        if (pos > lastPos) { // No other options here
            return 0;
        }

        if (pos == lastPos) { // Only valid option is itself
            return money[lastPos];
        }

        // With pos
        int withPos = money[pos] + houseRobber(money, pos + 2, lastPos);

        int withoutPos = houseRobber(money, pos + 1, lastPos);

        return Math.max(withPos, withoutPos);
    }
}

/*
Step 2: Memoization

T - O(n) - 2n
S - O(n) - stack + dp

 */
class Solution2 {
    public int houseRobber(int[] money) {
        int n = money.length;
        if (n == 1) {
            return money[0];
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        int excludingLastElement = houseRobber(money, 0, n - 2, dp);
        Arrays.fill(dp, -1); // resetting cache
        int excludingFirstElement = houseRobber(money, 1, n - 1, dp);
        return Math.max(excludingLastElement, excludingFirstElement);
    }

    private int houseRobber(int[] money, int pos, int lastPos, int[] dp) {
        if (pos > lastPos) { // No other options here
            return 0;
        }

        if (dp[pos] != -1) {
            return dp[pos];
        }

        if (pos == lastPos) { // Only valid option is itself
            dp[pos] = money[pos];
            return money[pos];
        }

        // With pos
        int withPos = money[pos] + houseRobber(money, pos + 2, lastPos, dp);

        int withoutPos = houseRobber(money, pos + 1, lastPos, dp);

        int max = Math.max(withPos, withoutPos);
        dp[pos] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n) - 4n
T - O(n) - dp
 */
class Solution3 {
    public int houseRobber(int[] money) {
        int n = money.length;
        if (n == 1) {
            return money[0];
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        int excludingLastElement = houseRobber(money, 0, n - 2, dp);
        Arrays.fill(dp, -1);
        int excludingFirstElement = houseRobber(money, 1, n - 1, dp);
        return Math.max(excludingFirstElement, excludingLastElement);
    }

    private int houseRobber(int[] money, int firstPos, int lastPos, int[] dp) {
        if (firstPos == lastPos) {
            return money[firstPos];
        }

        // Known solutions, max at lastPos is last itself
        dp[lastPos] = money[lastPos];
        dp[lastPos - 1] = Math.max(money[lastPos], money[lastPos - 1]);

        for (int i = lastPos - 2; i >= firstPos; i--) {
            dp[i] = Math.max(money[i] + dp[i + 2], dp[i + 1]);
        }

        return dp[firstPos];
    }
}

/*
Step 4 - Space Optimization

T - O(n) - 2n
S - O(1)

 */
class Solution4 {
    public int houseRobber(int[] money) {
        int n = money.length;
        if (n == 1) {
            return money[0];
        }
        int[] dp = new int[2];
        Arrays.fill(dp, -1);
        int excludingLastElement = houseRobber(money, 0, n - 2, dp);
        Arrays.fill(dp, -1);
        int excludingFirstElement = houseRobber(money, 1, n - 1, dp);
        return Math.max(excludingFirstElement, excludingLastElement);
    }

    private int houseRobber(int[] money, int firstPos, int lastPos, int[] dp) {
        if (firstPos == lastPos) {
            return money[firstPos];
        }

        // Known solutions, max at lastPos is last itself
        dp[0] = money[lastPos];
        dp[1] = Math.max(money[lastPos], money[lastPos - 1]);

        for (int i = lastPos - 2; i >= firstPos; i--) {
            int max = Math.max(money[i] + dp[0], dp[1]);
            dp[0] = dp[1];
            dp[1] = max;
        }

        return dp[1];
    }
}