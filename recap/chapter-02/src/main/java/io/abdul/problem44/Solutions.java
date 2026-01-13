package io.abdul.problem44;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

// https://leetcode.com/problems/perfect-squares/
// tag:math tag:recursion tag:dynamic_programming
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
    assertEquals(3, solution.numSquares(12));
  }
}

/*
Brute - Recursive

 */
class Solution {

  public int numSquares(int n) {
    return findNumSquares(n);
  }

  private int findNumSquares(int n) {
    if (n == 0) {
      return 0;
    }

    // Take as much as possible, skip if not
    int min = Integer.MAX_VALUE;
    for (int i = 1; i * i <= n; i++) {
      min = Math.min(min, 1 + findNumSquares(n - i * i));
    }
    return min == Integer.MAX_VALUE ? -1 : min;
  }
}

/*
Recursion + Memoization
 */
class Solution2 {

  public int numSquares(int n) {
    return findNumSquares(n, new int[n + 1]);
  }

  private int findNumSquares(int n, int[] dp) {
    if (n == 0) {
      return 0;
    }

    if (dp[n] != 0) {
      return dp[n];
    }

    // Take as much as possible, skip if not
    int min = Integer.MAX_VALUE;
    for (int i = 1; i * i <= n; i++) {
      min = Math.min(min, 1 + findNumSquares(n - i * i, dp));
    }
    int result = min == Integer.MAX_VALUE ? -1 : min;
    dp[n] = result;
    return result;
  }
}

/*
DP
 */
class Solution3 {

  public int numSquares(int n) {
    return findNumSquares(n);
  }

  private int findNumSquares(int n) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, (int) 1e5);

    dp[0] = 0; // known solution

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j * j <= i; j++) {
        // min(existing solution, taking j^2 (1 op) + ops to get to i-j^2)
        dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
      }
    }

    return dp[n];
  }
}

/*
Optimal
Math trick
Lagrange's (luh-graynj) Four-Square theorem & Legendre’s (luh-ZHON-druh) Three-Square Theorem

Is n a perfect square?
    → yes → 1

Can n be written as sum of two squares?
    → yes → 2

Is n of form 4^a(8b+7)?
    → yes → 4

Otherwise
    → 3

 */
class Solution4 {

  private boolean isPerfectSquare(int n) {
    int sqrtN = (int) (Math.sqrt(n));
    return (sqrtN * sqrtN == n);
  }

  // Based on Lagrange's Four Square theorem, there are only 4 possible results: 1, 2, 3, 4.
  public int numSquares(int n) {
    // Case 1: If n is a perfect square, return 1.
    if (isPerfectSquare(n)) {
      return 1;
    }

    // Case 2: Check whether 2 is the result.
    int sqrtN = (int) (Math.sqrt(n));
    for (int i = 1; i <= sqrtN; i++) {
      if (isPerfectSquare(n - i * i)) {
        return 2;
      }
    }

    // Case 3 & 4:
    // The result is 4 if and only if n can be written in the form of 4^a*(8*b + 7).
    // Please refer to Legendre's three-square theorem.
    while ((n & 3) == 0) // n%4 == 0 ; dropping 4^a
    {
      n >>= 2; // n / 2^2 = n/4
    }
    if ((n & 7) == 7) // n%8 == 7
    {
      return 4;
    }

    return 3;
  }
}