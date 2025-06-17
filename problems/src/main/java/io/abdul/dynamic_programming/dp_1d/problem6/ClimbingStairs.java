package io.abdul.dynamic_programming.dp_1d.problem6;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClimbingStairs {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: n = 1
        assertEquals(1, solution.climbStairs(1), "There should be 1 way to climb 1 step");

        // Test Case 2: n = 2
        assertEquals(2, solution.climbStairs(2), "There should be 2 ways to climb 2 steps");

        // Test Case 3: n = 3
        assertEquals(3, solution.climbStairs(3), "There should be 3 ways to climb 3 steps");

        // Test Case 4: n = 4
        assertEquals(5, solution.climbStairs(4), "There should be 5 ways to climb 4 steps");

        // Test Case 5: n = 5
        assertEquals(8, solution.climbStairs(5), "There should be 8 ways to climb 5 steps");

        // Test Case 6: n = 0
        assertEquals(1, solution.climbStairs(1), "There should be 1 way to climb 0 steps");

        // Test Case 7: Large input n = 10
        assertEquals(89, solution.climbStairs(10), "There should be 89 ways to climb 10 steps");
    }
}

/*
Step 1: Top-down recursive solution
T - O(2^n)
S - O(n) -> stack
 */
class Solution {
    public int climbStairs(int n) {
        return climbStairsInt(n);
    }

    private int climbStairsInt(int n) {
        if (n == 0) { // Possible
            return 1;
        }

        if (n < 0) { // Impossible
            return 0;
        }

        return climbStairsInt(n - 1) + climbStairsInt(n - 2);
    }
}

/*
Step 2: Add memoization
T - O(n)
S - O(n) - 2n; n - stack; n - dp

Similar to fibonacci
 */
class Solution2 {

    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return climbStairsInt(n, dp);
    }

    private int climbStairsInt(int n, int[] dp) {
        if (n == 0) { // Possible
            return 1;
        }

        if (n < 0) { // Impossible
            return 0;
        }

        if (dp[n] != -1) {
            return dp[n];
        }

        int paths = climbStairsInt(n - 1, dp) + climbStairsInt(n - 2, dp);
        dp[n] = paths;
        return paths;
    }
}

/*
Step 3: Bottom up iterative solution

Known solutions:
At pos 2, we've two ways. 1, 1 and 2
At pos 1, we've one way. 1
 */
class Solution3 {

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}

/*
Step 4: Space optimization

We only need last two results for calc
 */
class Solution4 {

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[2];
        dp[0] = 1;
        dp[1] = 2;

        for (int i = 3; i <= n; i++) {
            int ways = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = ways;
        }

        return dp[1];
    }
}