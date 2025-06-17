package io.abdul.dynamic_programming.dp_on_grids.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-grids/unique-paths-ii
public class UniquePathsII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        // Test Case 1: Example input matrix = [[0, 0, 0], [0, 1, 0], [0, 0, 0]]
        int[][] matrix1 = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        assertEquals(2, solution.uniquePathsWithObstacles(matrix1), "Unique paths for matrix [[0, 0, 0], [0, 1, 0], [0, 0, 0]] should be 2");

        // Test Case 2: Example input matrix = [[0, 0, 0], [0, 0, 1], [0, 1, 0]]
        int[][] matrix2 = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };
        assertEquals(0, solution.uniquePathsWithObstacles(matrix2), "Unique paths for matrix [[0, 0, 0], [0, 0, 1], [0, 1, 0]] should be 0");

        // Test Case 3: Single cell matrix = [[0]]
        int[][] matrix3 = {
                {0}
        };
        assertEquals(1, solution.uniquePathsWithObstacles(matrix3), "Unique paths for matrix [[0]] should be 1");

        // Test Case 4: Single cell blocked matrix = [[1]]
        int[][] matrix4 = {
                {1}
        };
        assertEquals(0, solution.uniquePathsWithObstacles(matrix4), "Unique paths for matrix [[1]] should be 0");

        // Test Case 5: Large input matrix = [[0, 0, 0, 0], [0, 1, 0, 0], [0, 0, 0, 0], [0, 0, 1, 0]]
        int[][] matrix5 = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0}
        };
        assertEquals(4, solution.uniquePathsWithObstacles(matrix5), "Unique paths for matrix [[0, 0, 0, 0], [0, 1, 0, 0], [0, 0, 0, 0], [0, 0, 1, 0]] should be 4");
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n)

Unlike UniquePathsI, we can take 1 when we're at bottom row and right column. Bcz some cells might be blocked in it. So we need to step in every cell.
We'll traverse only if it's not blocked
 */
class Solution {
    public int uniquePathsWithObstacles(int[][] matrix) {
        return uniquePathsWithObstacles(matrix, 0, 0);
    }

    private int uniquePathsWithObstacles(int[][] matrix, int r, int c) {
        if (r > matrix.length - 1 || c > matrix[0].length - 1) { // Invalid path
            return 0;
        }
        if (matrix[r][c] == 1) { // Blocked, can't go from here
            return 0;
        }
        if (r == matrix.length - 1 && c == matrix[0].length - 1) { // Reached destination
            return 1;
        }
        int uniquePaths = 0;
        if (r < matrix.length - 1) {
            uniquePaths += uniquePathsWithObstacles(matrix, r + 1, c);
        }
        if (c < matrix[0].length - 1) {
            uniquePaths += uniquePathsWithObstacles(matrix, r, c + 1);
        }

        return uniquePaths;
    }
}

/*
Step 2: Memoization

T - O(n^2)
S - O(n^2) - stack + dp

For same r and c, calc is repeating

 */
class Solution2 {
    public int uniquePathsWithObstacles(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return uniquePathsWithObstacles(matrix, 0, 0, dp);
    }

    private int uniquePathsWithObstacles(int[][] matrix, int r, int c, int[][] dp) {
        if (r > matrix.length - 1 || c > matrix[0].length - 1) { // Invalid path
            return 0;
        }
        if (dp[r][c] != -1) {
            return dp[r][c];
        }
        if (matrix[r][c] == 1) { // Blocked, can't go from here
            return 0;
        }
        if (r == matrix.length - 1 && c == matrix[0].length - 1) { // Reached destination
            return 1;
        }
        int uniquePaths = 0;
        if (r < matrix.length - 1) {
            uniquePaths += uniquePathsWithObstacles(matrix, r + 1, c, dp);
        }
        if (c < matrix[0].length - 1) {
            uniquePaths += uniquePathsWithObstacles(matrix, r, c + 1, dp);
        }

        dp[r][c] = uniquePaths;

        return uniquePaths;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp
 */
class Solution3 {
    public int uniquePathsWithObstacles(int[][] matrix) {
        int m = matrix.length - 1;
        int n = matrix[0].length - 1;
        if (matrix[m][n] == 1) {
            return 0; // destination itself is blocked, so no way to go there
        }

        int[][] dp = new int[m + 1][n + 1];
        // Known solutions
        dp[m][n] = 1;

        for (int i = m; i >= 0; i--) {
            boolean pathFound = false;
            for (int j = n; j >= 0; j--) {
                if (i == m && j == n) {
                    pathFound = true;
                    continue;
                }
                if (matrix[i][j] == 0) {
                    pathFound = true;
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
            if (!pathFound) { // entire row is blocked, so no way to go to end
                return 0;
            }
        }

        return dp[0][0];
    }
}

/*
Step 4 - Space optimization

T - O(n^2)
S - O(n) - dp
 */
class Solution4 {
    public int uniquePathsWithObstacles(int[][] matrix) {
        int m = matrix.length - 1;
        int n = matrix[0].length - 1;
        if (matrix[m][n] == 1) {
            return 0; // destination itself is blocked, so no way to go there
        }

        int[][] dp = new int[2][n + 1];
        // Known solutions
        dp[0][n] = 1;

        for (int i = m; i >= 0; i--) {
            Arrays.fill(dp[1], 0);
            boolean pathFound = false;
            for (int j = n; j >= 0; j--) {
                if (matrix[i][j] == 0) {
                    pathFound = true;
                    int down = dp[0][j];
                    int right = 0;
                    if (j < n) {
                        right = dp[1][j + 1];
                    }
                    dp[1][j] = down + right;
                }
            }
            if (!pathFound) { // entire row is blocked, so no way to go to end
                return 0;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n + 1);
        }

        return dp[0][0];
    }
}