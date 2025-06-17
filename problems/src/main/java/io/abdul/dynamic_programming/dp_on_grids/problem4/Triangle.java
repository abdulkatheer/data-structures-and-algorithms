package io.abdul.dynamic_programming.dp_on_grids.problem4;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

//https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-grids/triangle
public class Triangle {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input triangle = [[1], [1, 2], [1, 2, 4]]
        int[][] triangle1 = {
                {1},
                {1, 2},
                {1, 2, 4}
        };
        assertEquals(3, solution.minTriangleSum(triangle1), "Minimum falling path sum for triangle [[1], [1, 2], [1, 2, 4]] should be 3");

        // Test Case 2: Example input triangle = [[1], [4, 7], [4, 10, 50], [-50, 5, 6, -100]]
        int[][] triangle2 = {
                {1},
                {4, 7},
                {4, 10, 50},
                {-50, 5, 6, -100}
        };
        assertEquals(-42, solution.minTriangleSum(triangle2), "Minimum falling path sum for triangle [[1], [4, 7], [4, 10, 50], [-50, 5, 6, -100]] should be -42");

        // Test Case 3: Example input triangle = [[3], [-1, 3], [-3, 2, 4], [8, 8, 1, -4]]
        int[][] triangle3 = {
                {3},
                {-1, 3},
                {-3, 2, 4},
                {8, 8, 1, -4}
        };
        assertEquals(5, solution.minTriangleSum(triangle3), "Minimum falling path sum for triangle [[3], [-1, 3], [-3, 2, 4], [8, 8, 1, -4]] should be 1");

        // Test Case 4: Single row triangle = [[5]]
        int[][] triangle4 = {
                {5}
        };
        assertEquals(5, solution.minTriangleSum(triangle4), "Minimum falling path sum for triangle [[5]] should be 5");

        // Test Case 5: Large triangle = [[1], [2, 3], [4, 5, 6], [7, 8, 9, 10]]
        int[][] triangle5 = {
                {1},
                {2, 3},
                {4, 5, 6},
                {7, 8, 9, 10}
        };
        assertEquals(14, solution.minTriangleSum(triangle5), "Minimum falling path sum for triangle [[1], [2, 3], [4, 5, 6], [7, 8, 9, 10]] should be 14");

        // Test Case 7: Edge case triangle = [[0], [0, 0], [0, 0, 0]]
        int[][] triangle7 = {
                {0},
                {0, 0},
                {0, 0, 0}
        };
        assertEquals(0, solution.minTriangleSum(triangle7), "Minimum falling path sum for triangle [[0], [0, 0], [0, 0, 0]] should be 0");

    }
}

/*
Step 1: Top-down recursive solution

T - O(2^n)
S - O(n) - stack

 */
class Solution {
    public int minTriangleSum(int[][] triangle) {
        return minTriangleSum(triangle, 0, 0);
    }

    private int minTriangleSum(int[][] triangle, int r, int c) {
        if (c > r) { // max column in a row is r itself. row 1 has 1 cell, 2 has 2, 3 has 3
            return (int) 1e9; // this is max result as per problem. Int.MAX will lead to overflow.
        }
        if (r == triangle.length - 1) { // reached last row
            return triangle[r][c];
        }

        int bottom = triangle[r][c] + minTriangleSum(triangle, r + 1, c);
        int bottomRight = triangle[r][c] + minTriangleSum(triangle, r + 1, c + 1);

        return Math.min(bottom, bottomRight);
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

for same r and c, calc are repeated
 */
class Solution2 {
    public int minTriangleSum(int[][] triangle) {
        int[][] dp = new int[triangle.length][];
        for (int i = 1; i <= dp.length; i++) {
            dp[i - 1] = new int[i];
            Arrays.fill(dp[i - 1], (int) 1e9);
        }
        return minTriangleSum(triangle, 0, 0, dp);
    }

    private int minTriangleSum(int[][] triangle, int r, int c, int[][] dp) {
        if (c > r) { // max column in a row is r itself. row 1 has 1 cell, 2 has 2, 3 has 3
            return (int) 1e9; // this is max result as per problem. Int.MAX will lead to overflow.
        }
        if (r == triangle.length - 1) { // reached last row
            return triangle[r][c];
        }
        if (dp[r][c] != (int) 1e9) {
            return dp[r][c];
        }

        int bottom = triangle[r][c] + minTriangleSum(triangle, r + 1, c, dp);
        int bottomRight = triangle[r][c] + minTriangleSum(triangle, r + 1, c + 1, dp);

        int min = Math.min(bottom, bottomRight);
        dp[r][c] = min;
        return min;
    }
}

/*
Step 3 - Bottom-up iterative approach

Known solutions:
At row 0, min is the min of row itself
At row 1, min is Min (cell + up, cell + topLeft)

...
Result is row n-1 min
 */
class Solution3 {
    public int minTriangleSum(int[][] triangle) {
        int n = triangle.length;
        int[][] dp = new int[n][];
        for (int i = 1; i <= dp.length; i++) {
            dp[i - 1] = new int[i];
            Arrays.fill(dp[i - 1], (int) 1e9);
        }
        dp[0][0] = triangle[0][0];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                int up = (int) 1e9;
                if (j < i) { // last cell in row, will not have up
                    up = triangle[i][j] + dp[i - 1][j];
                }
                int topLeft = (int) 1e9;
                if (j - 1 >= 0) { // first cell will not have top left
                    topLeft = triangle[i][j] + dp[i - 1][j - 1];
                }
                dp[i][j] = Math.min(up, topLeft);
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            min = Math.min(dp[n - 1][i], min);
        }

        return min;
    }
}

/*
Step 4 - Space optimization

We only need 2 rows to keep current and last
 */
class Solution4 {
    public int minTriangleSum(int[][] triangle) {
        int n = triangle.length;
        int[][] dp = new int[2][n];
        dp[0][0] = triangle[0][0];
        Arrays.fill(dp[1], (int) 1e9);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                int up = (int) 1e9;
                if (j < i) { // last cell in row, will not have up
                    up = triangle[i][j] + dp[0][j];
                }
                int topLeft = (int) 1e9;
                if (j - 1 >= 0) { // first cell will not have top left
                    topLeft = triangle[i][j] + dp[0][j - 1];
                }
                dp[1][j] = Math.min(up, topLeft);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n);
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            min = Math.min(dp[0][i], min);
        }

        return min;
    }
}