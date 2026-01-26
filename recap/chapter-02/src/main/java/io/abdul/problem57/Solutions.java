package io.abdul.problem57;

import java.util.Arrays;

// https://leetcode.com/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/
// tag:math tag:recursion tag:dynamic_programming
// Stirling number - first kind s1(n k) = s1(n-1, k-1) + n-1 * s1(n-1, k)
public class Solutions {

  public static void main(String[] args) {
    Solution4 solution = new Solution4();
  }
}

/*
ERROR: TLE

n=5 k=3

We need to pick 3 from the 5. Order also matters.
So it's nPr only. -> Number of ways to choose and arrange 3 items out of 5 distinct items
But we need to remove invalid arrangements as well.

Let's do recursion first.

An item may take part in visible items or hide somewhere
Where an item can hide? Anywhere in the remaining position.

Placed -> we need to place k-1 items from remaining n-1 items
Not Placed -> we need to place k items from remaining n-1 items
Item 1 can be placed at position 1 be visible
or any of n-1 positions to get hidden
f(5, 3) = f(4,2) 1 at 1st position + 4 * f(4,3) 1 hides anywhere in 2,3,4,5 positions

Item 2 can be placed at position 1 or 2 to be visible
or any of n-2 positions to get hidden
f(4,2) = f(3,1) 2 at 2nd position + 3 * f(3,2) 2 hides anywhere in 3,4,5 positions
f(4,3) = f(3,2) 2 at 1st position + 3 * f(3,3) 2 hides anywhere in 3,4,5 positions

*/
class Solution {
  private static final int MOD = ((int) 1e9) + 7;

  public int rearrangeSticks(int n, int k) {
    return (int) rearrangeSticksRec(n, k);
  }

  private long rearrangeSticksRec(int n, int k) {
    // n=5 means 1st stick, n=4 means 2nd stick, n=1 means 5th stick
    if (n == k) { // remaining sticks has to be placed for sure
      return 1;
    }

    if (k > n) { // Not enough sticks to pick
      return 0;
    }

    if (k < 0) { // More sticks picked than needed
      return 0;
    }

    long placed = rearrangeSticksRec(n-1, k-1);
    long notPlaced = ((n-1) * rearrangeSticksRec(n-1, k)) % MOD;

    return (placed + notPlaced) % MOD;
  }
}

class Solution2 {
  private static final int MOD = ((int) 1e9) + 7;

  public int rearrangeSticks(int n, int k) {
    long[][] dp = new long[n+1][k+1];
    return (int) rearrangeSticksRec(n, k, dp);
  }

  private long rearrangeSticksRec(int n, int k, long[][] dp) {
    // n=5 means 1st stick, n=4 means 2nd stick, n=1 means 5th stick
    if (n == k) { // remaining sticks has to be placed for sure
      return 1;
    }

    if (k > n) { // Not enough sticks to pick
      return 0;
    }

    if (k < 0) { // More sticks picked than needed
      return 0;
    }

    if (dp[n][k] != 0) {
      return dp[n][k];
    }

    long placed = rearrangeSticksRec(n-1, k-1, dp);
    long notPlaced = ((n-1) * rearrangeSticksRec(n-1, k, dp)) % MOD;

    dp[n][k] = (placed + notPlaced) % MOD;
    return dp[n][k];
  }
}

class Solution3 {
  private static final int MOD = ((int) 1e9) + 7;

  public int rearrangeSticks(int n, int k) {
    long[][] dp = new long[n+1][k+1]; // arrangements for given n and k

    // Known solutions
    for (int i = 0; i <= k; i++) {
      dp[i][i] = 1;
    }

    // Other case is already 0 only

        /*
        n=3 k=2
        00 01 02 03 1 0 0 0
        10 11 12 13 0 1 0 0
        20 21 22 23 0 0 1 0
        30 31 32 33 0 0 0 1
        */
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= k; j++) {
        long placed = dp[i-1][j-1];
        long notPlaced = ((i-1) * dp[i-1][j]) % MOD;
        dp[i][j] = (placed + notPlaced) % MOD;
      }
    }

    return (int) dp[n][k];
  }
}

class Solution4 {
  private static final int MOD = ((int) 1e9) + 7;

  public int rearrangeSticks(int n, int k) {
    long[][] dp = new long[2][k+1]; // arrangements for given n and k

    // Known solutions
    dp[0][0] = 1;
    dp[1][1] = 1;

    // Other case is already 0 only

        /*
        n=3 k=2
        00 01 02 03 1 0 0 0
        10 11 12 13 0 1 0 0
        20 21 22 23 0 0 1 0
        30 31 32 33 0 0 0 1
        */
    for (int i = 1; i <= n; i++) {
      Arrays.fill(dp[i % 2], 0);
      for (int j = 1; j <= k; j++) {
        long placed = dp[(i-1) % 2][j-1];
        long notPlaced = ((i-1) * dp[(i-1) % 2][j]) % MOD;
        dp[i%2][j] = (placed + notPlaced) % MOD;
      }
    }

    return (int) dp[n%2][k];
  }
}

class Solution5 {
  private static final int MOD = 1_000_000_007;

  public int rearrangeSticks(int n, int k) {
    long[] dp = new long[k + 1];
    dp[0] = 1; // base: 0 sticks, 0 visible

        /*
        We only need previous copies of k
        If we go from left to right, because we need j and j-1
        the values will be overridden for current i

        if we go from right to left, j and all prev values will be of i-1
        */
    for (int i = 1; i <= n; i++) {
      for (int j = k; j >= 1; j--) {
        dp[j] = (dp[j - 1] + (long)(i - 1) * dp[j]) % MOD;
      }
      if (i==1) {
        dp[0] = 0; // dp[i][0] = 0 for i > 0
        // we need to do only once after i=1, bcz only that needs dp[0][0] as 1
      }
    }

    return (int) dp[k];
  }
}
