package io.abdul.problem50;

// https://leetcode.com/problems/climbing-stairs/
// tag:math tag:recursion tag:dynamic_programming
public class Solutions {

}

class Solution {
  public int climbStairs(int n) {
    return climbStairsRec(n);
  }

  private int climbStairsRec(int n) {
    if (n == 1) {
      return 1; // go to 0 by 1
    }

    if (n == 2) {
      return 2; // go to 0 by 2 or 1 & 1
    }

    return climbStairsRec(n-1) + climbStairsRec(n-2);
  }
}

class Solution2 {
  public int climbStairs(int n) {
    return climbStairsRec(n, new int[n + 1]);
  }

  private int climbStairsRec(int n, int[] dp) {
    if (n == 1) {
      return 1; // go to 0 by 1
    }

    if (n == 2) {
      return 2; // go to 0 by 2 or 1 & 1
    }

    if (dp[n] != 0) {
      return dp[n];
    }

    int ways = climbStairsRec(n - 1, dp) + climbStairsRec(n - 2, dp);
    dp[n] = ways;
    return ways;
  }
}

class Solution3 {
  public int climbStairs(int n) {
    if (n <= 2) {
      return n;
    }

    int[] dp = new int[n+1];

    // Known solutions
    dp[1] = 1;
    dp[2] = 2;

    for (int i = 3; i <= n; i++) {
      dp[i] = dp[i-1] + dp[i-2]; // combinations
    }

    return dp[n];
  }
}

// Fibonacci series
class Solution4 {
  public int climbStairs(int n) {
    if (n <= 2) {
      return n;
    }

    int[] dp = new int[2];

    // Known solutions
    dp[0] = 1;
    dp[1] = 2;

    for (int i = 3; i <= n; i++) {
      int temp = dp[1];
      dp[1] = dp[0] + dp[1]; // combinations
      dp[0] = temp;
    }

    return dp[1];
  }
}