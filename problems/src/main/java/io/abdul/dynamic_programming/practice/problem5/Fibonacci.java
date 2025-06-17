package io.abdul.dynamic_programming.practice.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Fibonacci {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        assertEquals(0, solution.fibonacci(0), "Fibonacci of 0 should be 0");
        assertEquals(1, solution.fibonacci(1), "Fibonacci of 1 should be 1");
        assertEquals(1, solution.fibonacci(2), "Fibonacci of 2 should be 1");
        assertEquals(2, solution.fibonacci(3), "Fibonacci of 3 should be 2");
        assertEquals(3, solution.fibonacci(4), "Fibonacci of 4 should be 3");
        assertEquals(5, solution.fibonacci(5), "Fibonacci of 5 should be 5");
    }
}

/*
Step 1: Top-down Recursive solution

T - T(n^2)
S - T(n) - stack
 */
class Solution {
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

/*
Step 2: Add memoization

T - O(n)
S - O(n) -> stack
 */
class Solution2 {
    public int fibonacci(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return fibonacci(n, dp); // to keep 0 to n
    }

    private int fibonacci(int n, int[] dp) {
        if (dp[n] != -1) {
            return dp[n]; // return memoized
        }

        if (n <= 1) {
            dp[n] = n; // return memoized
            return n;
        }


        /*
        Why no cache n-1 and n-2?
        Recursion works in below order 0,1,2,3,4, ... n
        0 and 1 are base
        2 will get 0 and 1 from dp and stores in dp
        3 will get 1 and 1 from dp and stores in dp
        ...
        So n-1 and n-2 dedicated store isn't needed
         */
        int f = fibonacci(n - 1, dp) + fibonacci(n - 2, dp);
        dp[n] = f; // do memoize

        return f;
    }
}

/*
Step 3: Bottom up Iterative solution

T - O(n)
S - O(n)
 */
class Solution3 {
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        // Start with known solutions / base case
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}

/*
Step 4: Optimize space

T - O(n)
S - O(1)
 */
class Solution4 {
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        int[] dp = new int[2]; // calc needs only last two results
        // Start with known solutions / base case
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            int f = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = f;
        }

        return dp[1];
    }
}