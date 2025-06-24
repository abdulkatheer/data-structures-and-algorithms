package io.abdul.dynamic_programming.dp_on_grids.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-grids/minimum-falling-path-sum
public class MinimumSumPath {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input matrix = [[1, 2, 10, 4], [100, 3, 2, 1], [1, 1, 20, 2], [1, 2, 2, 1]]
        int[][] matrix1 = {
                {1, 2, 10, 4},
                {100, 3, 2, 1},
                {1, 1, 20, 2},
                {1, 2, 2, 1}
        };
        assertEquals(6, solution.minFallingPathSum(matrix1), "Minimum falling path sum for matrix [[1, 2, 10, 4], [100, 3, 2, 1], [1, 1, 20, 2], [1, 2, 2, 1]] should be 6");

        // Test Case 2: Example input matrix = [[1, 4, 3, 1], [2, 3, -1, -1], [1, 1, -1, 8]]
        int[][] matrix2 = {
                {1, 4, 3, 1},
                {2, 3, -1, -1},
                {1, 1, -1, 8}
        };
        assertEquals(-1, solution.minFallingPathSum(matrix2), "Minimum falling path sum for matrix [[1, 4, 3, 1], [2, 3, -1, -1], [1, 1, -1, 8]] should be -1");

        // Test Case 3: Single row matrix = [[1, 2, 3]]
        int[][] matrix3 = {
                {1, 2, 3}
        };
        assertEquals(1, solution.minFallingPathSum(matrix3), "Minimum falling path sum for matrix [[1, 2, 3]] should be 1");

        // Test Case 4: Single column matrix = [[1], [2], [3]]
        int[][] matrix4 = {
                {1},
                {2},
                {3}
        };
        assertEquals(6, solution.minFallingPathSum(matrix4), "Minimum falling path sum for matrix [[1], [2], [3]] should be 6");

        // Test Case 5: Large input matrix = [[10, 20, 30], [40, 50, 60], [70, 80, 90], [100, 110, 120]]
        int[][] matrix5 = {
                {10, 20, 30},
                {40, 50, 60},
                {70, 80, 90},
                {100, 110, 120}
        };
        assertEquals(220, solution.minFallingPathSum(matrix5), "Minimum falling path sum for matrix [[10, 20, 30], [40, 50, 60], [70, 80, 90], [100, 110, 120]] should be 260");

        // Test Case 7: Edge case matrix = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]
        int[][] matrix7 = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertEquals(0, solution.minFallingPathSum(matrix7), "Minimum falling path sum for matrix [[0, 0, 0], [0, 0, 0], [0, 0, 0]] should be 0");

    }
}

/*
Step 1: Top-down recursive solution

T - O(m * 3^n) - m columns, n - rows
S - O(n) - stack
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[0].length; i++) {
            min = Math.min(min, minFallingPathSum(matrix, 0, i)); // for all cells from row 0
        }
        return min;
    }

    private int minFallingPathSum(int[][] matrix, int r, int c) {
        if (r < 0 || c < 0 || r >= matrix.length || c >= matrix[0].length) {
            // 1_000_000_000
            /* Why not Int MAX?
            Bcz problem states that answer will not go beyond 10^9
            If we take int max, due to addition, it'll overflow and become negative and influence wrong result.
             */
            return (int) 1e9;  // Bcz we want to skip this path, and should not include to calculate minimum path
        }

        if (r == matrix.length - 1) { // reached destination, last row
            return matrix[r][c];
        }

        int min = Integer.MAX_VALUE;
        int bottom = matrix[r][c] + minFallingPathSum(matrix, r + 1, c);
        min = Math.min(min, bottom);
        int bottomLeft = matrix[r][c] + minFallingPathSum(matrix, r + 1, c - 1);
        min = Math.min(min, bottomLeft);
        int bottomRight = matrix[r][c] + minFallingPathSum(matrix, r + 1, c + 1);
        min = Math.min(min, bottomRight);

        return min;
    }
}

/*
Step 2: Memoization

T - O(n^2)
S - O(n^2) - stack + dp

For same r and c, calc is repeated
 */
class Solution2 {
    public int minFallingPathSum(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int[] ints : dp) {
                Arrays.fill(ints, (int) 1e9);
            }
            min = Math.min(min, minFallingPathSum(matrix, 0, i, dp)); // for all cells from row 0
        }
        return min;
    }

    private int minFallingPathSum(int[][] matrix, int r, int c, int[][] dp) {
        if (c < 0 || c >= matrix[0].length) {
            // 1_000_000_000
            /* Why not Int MAX?
            Bcz problem states that answer will not go beyond 10^9
            If we take int max, due to addition, it'll overflow and become negative and influence wrong result.
             */
            return (int) 1e9;  // Bcz we want to skip this path, and should not include to calculate minimum path
        }

        if (r == matrix.length - 1) { // reached destination, last row
            return matrix[r][c];
        }

        if (dp[r][c] < (int) 1e9) {
            return dp[r][c];
        }

        int min = Integer.MAX_VALUE;
        int bottom = matrix[r][c] + minFallingPathSum(matrix, r + 1, c, dp);
        min = Math.min(min, bottom);
        int bottomLeft = matrix[r][c] + minFallingPathSum(matrix, r + 1, c - 1, dp);
        min = Math.min(min, bottomLeft);
        int bottomRight = matrix[r][c] + minFallingPathSum(matrix, r + 1, c + 1, dp);
        min = Math.min(min, bottomRight);

        dp[r][c] = min;
        return min;
    }
}

/*
Step 3: Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp

Known solution:
At row 0 - min of them is the answer
At row 1, cost to reach each column is MIN (column + up, column + topLeft, column + topRight)
At row n-1, cost to reach each column is MIN (column + up, column + topLeft, column + topRight)

Result is min of row n-1
 */
class Solution3 {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];

        // Known solution; row 0 has no additional cost
        System.arraycopy(matrix[0], 0, dp[0], 0, m);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int up = matrix[i][j] + dp[i - 1][j];
                int topLeft = Integer.MAX_VALUE;
                if (j - 1 >= 0) {
                    topLeft = matrix[i][j] + dp[i - 1][j - 1];
                }
                int topRight = Integer.MAX_VALUE;
                if (j + 1 < m) {
                    topRight = matrix[i][j] + dp[i - 1][j + 1];
                }
                dp[i][j] = Math.min(up, Math.min(topLeft, topRight));
            }
        }

        // Min of last row
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            min = Math.min(dp[n - 1][i], min);
        }

        return min;
    }
}

/*
Step 4 - Space optimization

We only need the current and last row to keep results
 */
class Solution4 {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[2][m];

        // Known solution; row 0 has no additional cost
        System.arraycopy(matrix[0], 0, dp[0], 0, m);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int up = matrix[i][j] + dp[0][j];
                int topLeft = Integer.MAX_VALUE;
                if (j - 1 >= 0) {
                    topLeft = matrix[i][j] + dp[0][j - 1];
                }
                int topRight = Integer.MAX_VALUE;
                if (j + 1 < m) {
                    topRight = matrix[i][j] + dp[0][j + 1];
                }
                dp[1][j] = Math.min(up, Math.min(topLeft, topRight));
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m);
        }

        // Min of last row
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            min = Math.min(dp[0][i], min);
        }

        return min;
    }
}