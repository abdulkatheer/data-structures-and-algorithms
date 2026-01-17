package io.abdul.problem48;

// https://leetcode.com/problems/unique-paths/
// tag:math tag:recursion tag:dynamic_programming
import java.util.Arrays;

public class Solutions {

}

// ERROR: TLE
class Solution {
  public int uniquePaths(int m, int n) {
    return uniquePathsRec(0, 0, m, n);
  }

  private int uniquePathsRec(int i, int j, int m, int n) {
    if (i >= m || j >= n) {
      return 0;
    }

    if (i == m-1 || j == n-1) {
      return 1; // single path when we reach last row or column
    }

    int down = uniquePathsRec(i+1, j, m, n);
    int right = uniquePathsRec(i, j+1, m, n);
    return down + right;
  }
}

class Solution2 {
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    for (int[] dpData : dp) {
      Arrays.fill(dpData, -1);
    }
    return uniquePathsRec(0, 0, m, n, dp);
  }

  private int uniquePathsRec(int i, int j, int m, int n, int[][] dp) {
    if (i >= m || j >= n) {
      return 0;
    }

    if (i == m-1 || j == n-1) {
      return 1; // single path when we reach last row or column
    }

    if (dp[i][j] != -1) {
      return dp[i][j];
    }

    int down = uniquePathsRec(i+1, j, m, n, dp);
    int right = uniquePathsRec(i, j+1, m, n, dp);

    int total = down + right;
    dp[i][j] = total;
    return total;
  }
}

class Solution3 {
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    // any pos in last row
    for (int i = 0; i < n; i++) {
      dp[m-1][i] = 1;
    }
    // any pos in last column
    for (int i = 0; i < m; i++) {
      dp[i][n-1] = 1;
    }

    for (int i = m-2; i >= 0; i--) {
      for (int j = n-2; j >= 0; j--) {
        dp[i][j] = dp[i][j+1] + dp[i+1][j];
      }
    }

    return dp[0][0];
  }
}

class Solution4 {
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[2][n];

    // any pos in last row
    for (int i = 0; i < n; i++) {
      dp[(m-1)%2][i] = 1;
    }
    // any pos in last column
    for (int i = 0; i < m; i++) {
      dp[i%2][n-1] = 1;
    }

    for (int i = m-2; i >= 0; i--) {
      for (int j = n-2; j >= 0; j--) {
        dp[i%2][j] = dp[i%2][j+1] + dp[(i+1)%2][j];
      }
    }

    return dp[0][0];
  }
}

class Solution5 {
  public int uniquePaths(int m, int n) {
    int[] dp = new int[n];

    // any pos in last row
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
    }

    // Update to a cell in the row only depends on right and down.
    // Meaning any data stored from current cell towards right side is not needed
    // So we can override the cells with new data
    for (int i = m-2; i >= 0; i--) {
      for (int j = n-2; j >= 0; j--) {
        dp[j] = dp[j+1] + dp[j]; // right + down
      }
    }

    return dp[0];
  }
}

/*
Combinatorics
We've only m-1 + n-1 choices
if m=4 and n=6
we have total choices of D D D R R R R R
All possible arrangements of above is our answer.

Arrangements -> Permutation?

But here we've duplicates. Only D and R exists.
So it boils down to combinatorics.

( (m-1) + (n-1) )!
-------------------
(m-1)! * (n-1)!

Safe multiplication:
let k = Min(m-1, n-1)
and N = m + n -2

So,
 N!
-----
k! * (N-k)*

N * N-1 * N-2 * ... (N-k+1) * (N-k)!
---------------------
k * k-1 * k-2 ... * 1 * (N-k)!

Cancel (N-k)!

So
N * N-1 * N-2 ... * (N-k+1)
---------------------------
k!

N-k+1 * N-k+2 * N-k+3 * N-k+4 ... * N-k+k
-----------------------------------------
1 * 2 * 3 * 4 * ... * k
*/
class Solution6 {
  public int uniquePaths(int m, int n) {
    long res = 1;
    int k = Math.min(m-1, n-1);
    int N = m+n-2;

    for (int i = 1; i <= k; i++) {
      res = res * (N-k+i) / i;
    }

    return (int) res;
  }
}