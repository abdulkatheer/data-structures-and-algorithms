package io.abdul.problem56;

// https://leetcode.com/problems/unique-binary-search-trees/
// tag:math tag:recursion tag:dynamic_programming tag:catalan_numbers
public class Solutions {

}

/*
1 2 3
1 3 2
2 1 3 -
2 3 1 -
3 1 2
3 2 1
*/
class Solution {
  public int numTrees(int n) {
    return numTreesRec(1, n);
  }

  private int numTreesRec(int start, int end) {
    if (start > end) {
      return 1;
    }

    if (start == end) {
      return 1;
    }

    int total = 0;
        /*
        1 & 5
        1-0 1-5
        1-1 2-5
        1-2 3-5
        1-3 4-5
        1-4 5-5
        */
    for (int i = start; i <= end; i++) {
      total = total + numTreesRec(start, i - 1) * numTreesRec(i + 1, end);
    }

    return total;
  }
}

/*
1 2 3
1 3 2
2 1 3 -
2 3 1 -
3 1 2
3 2 1
*/
class Solution2 {
  public int numTrees(int n) {
    int[][] dp = new int[n+1][n+1];
    return numTreesRec(1, n, dp);
  }

  private int numTreesRec(int start, int end, int[][] dp) {
    if (start > end) {
      return 1;
    }

    if (start == end) {
      return 1;
    }

    if (dp[start][end] != 0) {
      return dp[start][end];
    }

    int total = 0;
        /*
        1 & 5
        1-0 1-5
        1-1 2-5
        1-2 3-5
        1-3 4-5
        1-4 5-5
        */
    for (int root = start; root <= end; root++) {
      total = total + numTreesRec(start, root - 1, dp) * numTreesRec(root + 1, end, dp);
    }

    dp[start][end] = total;

    return total;
  }
}

class Solution3 {
  public int numTrees(int n) {
    int[][] dp = new int[n+1][n+1];

    // Known solution
    for (int i = 0; i <= n; i++) {
      dp[i][i] = 1;
    }

        /*
        len = 2
        dp[1][2] = [root 1] no left * dp[2][2] + [root 2] dp[1][1] * no right
        root 2
        dp[2][3] = [root 2] no left * dp[3][3] + [root 3] dp[2][2] * no right

        len=3
        dp[1][3] = [root 1] no left * dp[2][3] + [root 2] dp[1][1] * dp[3][3] + [root 3] dp[1][2] * no right
        */

    for (int len = 2; len <= n; len++) {
      for (int start = 1; start + len - 1 <= n; start++) { // for n=3 and len=2, start will be 1 and 2
        int end = start + len - 1;
        int total = 0;
        for (int root = start; root <= end; root++) {
          int left = root - 1 < start  ? 1 : dp[start][root-1];
          int right = root + 1 > end ? 1 : dp[root + 1][end];
          total += left * right;
        }

        dp[start][end] = total;
      }
    }

    return dp[1][n];
  }
}

/*
count(start,end) mean number of unique BSTs formed with exact nodes from start to end -> start, start+1 ... end
But count shapes doesn't really depend on exact values, but the number of values

Ex: count(1,3) and count(10,12)
1 2 3
1 3 2
2 1 3 / 2 3 1
3 1 2
3 2 1

10 11 12
10 12 11
11 10 12 / 11 12 10
12 10 11
12 11 10

0 - 1
1 - 1
2 - 2
1 as root - rec(0) * rec(1) - 1
2 as root - rec(1) * rec(0) - 1

3 - 5
1 as root - rec(0) * rec(2) - 2
2 as root - rec(1) * rec(1) - 1
3 as root - rec(2) * rec(0) - 2

*/
class Solution4 {
  public int numTrees(int n) {
    return numTreesRec(n);
  }

  private int numTreesRec(int n) {
    if (n == 0 || n == 1) {
      return 1;
    }

        /*
        n=3
        0 2
        1 1
        2 0
        */
    int total = 0;
    for (int i = 1; i <= n; i++) {
      total = total + numTreesRec(i-1) * numTreesRec(n-i);
    }

    return total;
  }
}

/*
count(start,end) mean number of unique BSTs formed with exact nodes from start to end -> start, start+1 ... end
But count shapes doesn't really depend on exact values, but the number of values

Ex: count(1,3) and count(10,12)
1 2 3
1 3 2
2 1 3 / 2 3 1
3 1 2
3 2 1

10 11 12
10 12 11
11 10 12 / 11 12 10
12 10 11
12 11 10

0 - 1
1 - 1
2 - 2
1 as root - rec(0) * rec(1) - 1
2 as root - rec(1) * rec(0) - 1

3 - 5
1 as root - rec(0) * rec(2) - 2
2 as root - rec(1) * rec(1) - 1
3 as root - rec(2) * rec(0) - 2

*/
class Solution5 {
  public int numTrees(int n) {
    int[] dp = new int[n+1];
    return numTreesRec(n, dp);
  }

  private int numTreesRec(int n, int[] dp) {
    if (n == 0 || n == 1) {
      return 1;
    }

    if (dp[n] != 0) {
      return dp[n];
    }

        /*
        n=3
        0 2
        1 1
        2 0
        */
    int total = 0;
    for (int i = 1; i <= n; i++) {
      total = total + numTreesRec(i-1, dp) * numTreesRec(n-i, dp);
    }

    dp[n] = total;
    return total;
  }
}

class Solution6 {
  public int numTrees(int n) {
    int[] dp = new int[n+1];
    dp[0] = dp[1] = 1;

        /*
        len = 2
        dp[2] = [root 1] no left * dp[1] + [root 2] dp[1] * no right

        len = 3
        dp[3] = [root 1] no left * dp[2] + [root 2] dp[1] * dp[1] + [root 3] dp[2] * no right
        */
    for (int len = 2; len <= n; len++) {
      for (int root = 1; root <= len; root++) {
        dp[len] += dp[root-1] * dp[len-root];
      }
    }

    return dp[n];
  }
}

/*
Catalan Numbers
This is in form C_k * C_n-k for k=1 to n

Binomial Coefficient formulat
C_n = 2n C n / (n+1)
2n Choosen n divided by n+1

2n C n = 2n!/n! * (2n-n)! = 2n!/n!n!
2n! = 2n * 2n-1 * 2n-2 * 2n-3 ... * 2n-n+1 (=n+1) * 2n-n (=n) * n-1 * n-2 * n-3 * ... 1
2n-n (=n) * n-1 * n-2 * n-3 * ... 1 = n!
So 2n! = 2n * 2n-1 * 2n-2 * 2n-3 ... * n+1 * n!

2n!/n!n! = 2n * 2n-1 * 2n-2 * 2n-3 ... * 2n-n-1 * n! / n!n!
n! cancels
=2n * 2n-1 * 2n-2 * 2n-3 ... * n+1 / n!

2n C n= 2n/1 * 2n-1/2 * 2n-2/3 * ... n+1/n

*/
class Solution7 {
  public int numTrees(int n) {
    // Find C(2n, n)
    long result = 1;
    for (int i = 0; i < n; i++) {
      result = result * (2 * n - i) / (i+1);
    }

    // C(2n, n) /
    return (int) (result / (n+1));
  }
}