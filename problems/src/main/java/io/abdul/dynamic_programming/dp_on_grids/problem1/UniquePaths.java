package io.abdul.dynamic_programming.dp_on_grids.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-grids/grid-unique-paths
public class UniquePaths {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();
//        Solution3a solution = new Solution3a();
        Solution4a solution = new Solution4a();

        // Test Case 1: Example input m = 3, n = 2
        assertEquals(3, solution.uniquePaths(3, 2), "Unique paths for a 3x2 grid should be 3");

        // Test Case 2: Example input m = 2, n = 4
        assertEquals(4, solution.uniquePaths(2, 4), "Unique paths for a 2x4 grid should be 4");

        // Test Case 3: Single cell grid m = 1, n = 1
        assertEquals(1, solution.uniquePaths(1, 1), "Unique paths for a 1x1 grid should be 1");

        // Test Case 4: Single row grid m = 1, n = 5
        assertEquals(1, solution.uniquePaths(1, 5), "Unique paths for a 1x5 grid should be 1");

        // Test Case 5: Single column grid m = 6, n = 1
        assertEquals(1, solution.uniquePaths(6, 1), "Unique paths for a 6x1 grid should be 1");

        // Test Case 6: Square grid m = 3, n = 3
        assertEquals(6, solution.uniquePaths(3, 3), "Unique paths for a 3x3 grid should be 6");

        // Test Case 7: Large grid m = 5, n = 5
        assertEquals(70, solution.uniquePaths(5, 5), "Unique paths for a 5x5 grid should be 70");
    }
}

/*
Step 1 - Top-down recursive approach

T - O(2^n)
S - O(n) - stack

 */
class Solution {
    public int uniquePaths(int m, int n) {
        return uniquePathsRec(m, n, 1, 1);
    }

    private int uniquePathsRec(int m, int n, int r, int c) {
        if (r > m || c > n) { // Invalid path
            return 0;
        }
        if (r == m && c == n) { // reached end
            return 1;
        }
        if (r == m) { // At last row, now only one path, to go right
            return 1;
        }
        if (c == n) { // At last column, now only one path, to go bottom
            return 1;
        }

        return uniquePathsRec(m, n, r + 1, c) + uniquePathsRec(m, n, r, c + 1);
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

Repeated calc for same r and c

 */
class Solution2 {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return uniquePathsRec(m, n, 1, 1, dp);
    }

    private int uniquePathsRec(int m, int n, int r, int c, int[][] dp) {
        if (r > m || c > n) { // Invalid path
            return 0;
        }
        if (dp[r][c] != -1) {
            return dp[r][c];
        }

        if (r == m && c == n) { // reached end
            return 1;
        }
        if (r == m) { // At last row, now only one path, to go right
            return 1;
        }
        if (c == n) { // At last column, now only one path, to go bottom
            return 1;
        }

        int paths = uniquePathsRec(m, n, r + 1, c, dp) + uniquePathsRec(m, n, r, c + 1, dp);
        dp[r][c] = paths;
        return paths;
    }
}

/*
Step 3 - Bottom-up iterative approach

T - O(n^2)
S - O(n^2)

3 x 3
Known solution
All x 3 - 1
3 x All - 1
Last row and column are full of 1s
6  3  1
3  2  1
1  1  1
 */
class Solution3 {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        // Known solutions
        dp[m][n] = 1;

        for (int i = m; i >= 1; i--) {
            for (int j = n; j >= 1; j--) {
                if (i == m && j == n) { // Known solution
                    continue;
                }

                int down = 0;
                if (i < m) {
                    down = dp[i + 1][j];
                }

                int right = 0;
                if (j < n) {
                    right = dp[i][j + 1];
                }

                dp[i][j] = down + right;
            }
        }

        return dp[1][1];
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(m*n)
S - O(m*n)

Copy base case - dp[m][n] = 1; dp[m][j] = 1; dp[i][n] = 1
Copy iteration parameters - i=m-1 to 1, j=n-1 to 1
Copy recursive case
 */
class Solution3a {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        // Known solutions
        dp[m][n] = 1;
        for (int i = 1; i <= m; i++) {
            dp[i][n] = 1;
        }
        for (int j = 1; j <= n; j++) {
            dp[m][j] = 1;
        }

        // Recursive solutions
        for (int i = m - 1; i >= 1; i--) {
            for (int j = n - 1; j >= 1; j--) {
                dp[i][j] = dp[i][j + 1] + dp[i + 1][j];
            }
        }

        return dp[1][1];
    }
}

/*
Step 4 - Space Optimization

T - O(n^2)
S - O(n)

We only need 2*n arrays to keep current and prev row values for bottom
We only need one space to keep right value

0  0  1 <- right
0  0  0

0  1  1 <- r
0  0  0

1  1  1 <- r
0  0  0

0  0  0 <- r
1  1  1

0  0  1 <- r
1  1  1

0  2  1 <- r
1  1  1

3  2  1 <- r
1  1  1

0  0  0 <- r
3  2  1

0  0  1 <- r
3  2  1

0  3  1 <- r
3  2  1

6  3  1 <- r
3  2  1

Ans: 6
 */
class Solution4 {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[2][n + 1];

        // Known solutions
        dp[0][n] = 1;


        for (int i = m; i >= 1; i--) {
            for (int j = n; j >= 1; j--) {

                int down = dp[0][j];
                int right = 0;
                if (j < n) {
                    right = dp[1][j + 1];
                }

                dp[1][j] = down + right;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n + 1);
        }

        return dp[0][1];
    }
}

/*
Step 4 - Space Optimization

We only need below row and right side cell for any subproblem
 */
class Solution4a {
    public int uniquePaths(int m, int n) {

        int[][] dp = new int[2][n + 1];

        // Known solutions
        dp[0][n] = 1;
        for (int j = 1; j <= n; j++) {
            dp[1][j] = 1;
        }

        // Recursive solutions
        for (int i = m - 1; i >= 1; i--) {
            for (int j = n - 1; j >= 1; j--) {
                dp[0][j] = dp[0][j + 1] + dp[1][j];
            }
            System.arraycopy(dp[0], 0, dp[1], 0, n);
        }

        return dp[1][1];
    }
}