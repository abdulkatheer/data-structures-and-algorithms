package io.abdul.dynamic_programming.dp_on_grids.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/dp-on-grids/cherry-pickup-ii
/*
Two robots
Possibilities:
At each row, robA and robB can move make any of below possibilities
robA - bottom, robB - bottom
robA - bottom, robB - bottomLeft
robA - bottom, robB - bottomRight
robA - bottomLeft, robB - bottom
robA - bottomLeft, robB - bottomLeft
robA - bottomLeft, robB - bottomRight
robA - bottomRight, robB - bottom
robA - bottomRight, robB - bottomLeft
robA - bottomRight, robB - bottomRight

During this, if both meet at same location, just consider cherries once
 */
public class CherryPickupII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input matrix = [[2, 1, 3], [4, 2, 5], [1, 6, 2], [7, 2, 8]]
        int[][] matrix1 = {
                {2, 1, 3},
                {4, 2, 5},
                {1, 6, 2},
                {7, 2, 8}
        };
        assertEquals(37, solution.cherryPickup(matrix1), "Maximum cherries for matrix [[2, 1, 3], [4, 2, 5], [1, 6, 2], [7, 2, 8]] should be 37");

        // Test Case 2: Example input matrix = [[1, 4, 4, 1], [1, 2, 2, 1], [5, 6, 10, 11], [8, 1, 1, 1]]
        int[][] matrix2 = {
                {1, 4, 4, 1},
                {1, 2, 2, 1},
                {5, 6, 10, 11},
                {8, 1, 1, 1}
        };
        assertEquals(32, solution.cherryPickup(matrix2), "Maximum cherries for matrix [[1, 4, 4, 1], [1, 2, 2, 1], [5, 6, 10, 11], [8, 1, 1, 1]] should be 32");

        // Test Case 3: Example input matrix = [[1, 2, 3], [5, 4, 6], [4, 4, 1]]
        int[][] matrix3 = {
                {1, 2, 3},
                {5, 4, 6},
                {4, 4, 1}
        };
        assertEquals(23, solution.cherryPickup(matrix3), "Maximum cherries for matrix [[1, 2, 3], [5, 4, 6], [4, 4, 1]] should be 28");

        // Test Case 4: Single row matrix = [[1, 2, 3]]
        int[][] matrix4 = {
                {1, 2, 3}
        };
        assertEquals(4, solution.cherryPickup(matrix4), "Maximum cherries for single row matrix [[1, 2, 3]] should be 4");

        // Test Case 5: Single column matrix = [[1], [2], [3]]
        int[][] matrix5 = {
                {1, 1},
                {2, 2},
                {3, 3}
        };
        assertEquals(12, solution.cherryPickup(matrix5), "Maximum cherries for single column matrix [[1], [2], [3]] should be 6");

        // Test Case 6: Large input matrix with all zeros
        int[][] matrix6 = new int[100][100];
        assertEquals(0, solution.cherryPickup(matrix6), "Maximum cherries for large matrix with all zeros should be 0");

        // Test Case 7: Large input matrix with less rows
        int[][] matrix7 = new int[2][10000];
        matrix7[0][0] = 1;
        matrix7[0][9999] = 2;
        matrix7[1][0] = 3;
        matrix7[1][1] = 4;
        matrix7[1][9998] = 5;
        matrix7[1][9999] = 6;
        assertEquals(13, solution.cherryPickup(matrix7), "Maximum cherries for large matrix with all zeros should be 0");
    }
}

/*
Step 1 - Top-down recursive solution

T - O(9^n)
S - O(n) - stack

 */
class Solution {
    public int cherryPickup(int[][] matrix) {
        int m = matrix[0].length;
        int n = matrix.length;
        return cherryPickup(matrix, 0, 0, m - 1, n - 1, m - 1);
    }

    private int cherryPickup(int[][] matrix, int r, int cA, int cB, int n, int m) {
        if (cA < 0 || cA > m || cB < 0 || cB > m) { // Invalid move
            return (int) (-1e9);
        }

        if (r == n) { // reached last row
            if (cA == cB) {
                return matrix[r][cA];
            } else {
                return matrix[r][cA] + matrix[r][cB];
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int picked;
                if (cA == cB) {
                    picked = matrix[r][cA] + cherryPickup(matrix, r + 1, cA + i, cB + j, n, m);
                } else {
                    picked = matrix[r][cA] + matrix[r][cB] + cherryPickup(matrix, r + 1, cA + i, cB + j, n, m);
                }
                max = Math.max(max, picked);
            }
        }

        return max;
    }
}

/*
Step 2  - Memoization

T - O(n*m*m)
S - T(n*m*m) - stack + dp

 */
class Solution2 {
    public int cherryPickup(int[][] matrix) {
        int m = matrix[0].length;
        int n = matrix.length;
        int[][][] dp = new int[matrix.length][matrix[0].length][matrix[0].length];
        for (int[][] ints : dp) {
            for (int[] ints2 : ints) {
                Arrays.fill(ints2, (int) -1e9);
            }
        }

        return cherryPickup(matrix, 0, 0, m - 1, n - 1, m - 1, dp);
    }

    private int cherryPickup(int[][] matrix, int r, int cA, int cB, int n, int m, int[][][] dp) {
        if (cA < 0 || cA > m || cB < 0 || cB > m) { // Invalid move
            return (int) (-1e9);
        }

        if (dp[r][cA][cB] != (int) -1e9) {
            return dp[r][cA][cB];
        }
        if (r == n) { // reached last row
            int res;
            if (cA == cB) {
                res = matrix[r][cA];
            } else {
                res = matrix[r][cA] + matrix[r][cB];
            }
            dp[r][cA][cB] = res;
            return res;
        }

        int max = Integer.MIN_VALUE;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int picked;
                if (cA == cB) {
                    picked = matrix[r][cA] + cherryPickup(matrix, r + 1, cA + i, cB + j, n, m, dp);
                } else {
                    picked = matrix[r][cA] + matrix[r][cB] + cherryPickup(matrix, r + 1, cA + i, cB + j, n, m, dp);
                }
                max = Math.max(max, picked);
            }
        }

        dp[r][cA][cB] = max;
        return max;
    }
}

/*
Step 3: Bottom-up iterative solution

T - O(n*m*m)
S - O(n*m*m)

Known solutions:
At row 0, robA and roB can be at 0 and m-1 only. So max is max[ (0, 0, m-1), -Inf,... , (0,m-1,0) ]

At row 1, only 0 and 1, m-2 and m-1 are considered. Robot can't move to any other cells in row 1.
But we'll iterate through all anyways, as they won't clash with answer as they have negative max as initial value.

At row n-2, only 0 to n-2 and m-n-2-1 to m-1 are considered
At row n-1, only 0 to n-1
 */
class Solution3 {
    public int cherryPickup(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][][] dp = new int[n][m][m];

        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, Integer.MIN_VALUE);
            }
        }

        // Known solution; m is at least 2, so same num is not possible
        dp[0][0][m - 1] = dp[0][m - 1][0] = matrix[0][0] + matrix[0][m - 1];

        for (int i = 1; i < n; i++) {
            for (int robotA = 0; robotA < m && robotA <= i; robotA++) {
                for (int robotB = m - 1; robotB >= 0 && robotB >= m - 1 - i; robotB--) {
                    /* We could try 0 to m-1 for both robot loops. We've init with -Inf, so its fine. But to optimize, we've looked up only required fields
                    What if we've array like matrix[2][10000]. We only have to explore 4 options. 1,0 1,9999; 1,0 1, 9998; 1,1 1,19999; 1,1 1,9998
                    If we do all, we'll visit 1 x 10000 x 10000 iterations
                     */

                    // Now robotA and robotB could have come in 9 possible ways
                    int max = Integer.MIN_VALUE;
                    for (int j = -1; j <= 1; j++) {
                        for (int k = -1; k <= 1; k++) {
                            int res;
                            boolean outOfBounds = robotA + j < 0 || robotA + j > m - 1 || robotB + k < 0 || robotB + k > m - 1;
                            if (outOfBounds) {
                                res = Integer.MIN_VALUE;
                            } else {
                                if (robotA == robotB) {
                                    res = matrix[i][robotA] + dp[i - 1][robotA + j][robotB + k];
                                } else {
                                    res = matrix[i][robotA] + matrix[i][robotB] + dp[i - 1][robotA + j][robotB + k];
                                }
                            }

                            max = Math.max(max, res);
                        }
                    }

                    dp[i][robotA][robotB] = max;
                }
            }
        }

        int result = Integer.MIN_VALUE;
        for (int[] rows : dp[n - 1]) {
            for (int robotA : rows) {
                result = Math.max(result, robotA);
            }
        }
        return result;
    }
}

/*
Step 4: Space optimization

T - O(n*m*m)
S - O(m*m)

We only need last and current row results
 */
class Solution4 {
    public int cherryPickup(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][][] dp = new int[2][m][m]; // 0 for prev, 1 for prev

        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, Integer.MIN_VALUE);
            }
        }

        // Known solution; m is at least 2, so same num is not possible
        dp[0][0][m - 1] = dp[0][m - 1][0] = matrix[0][0] + matrix[0][m - 1];

        for (int i = 1; i < n; i++) {
            for (int robotA = 0; robotA < m && robotA <= i; robotA++) {
                for (int robotB = m - 1; robotB >= 0 && robotB >= m - 1 - i; robotB--) {
                    /* We could try 0 to m-1 for both robot loops. We've init with -Inf, so its fine. But to optimize, we've looked up only required fields
                    What if we've array like matrix[2][10000]. We only have to explore 4 options. 1,0 1,9999; 1,0 1, 9998; 1,1 1,19999; 1,1 1,9998
                    If we do all, we'll visit 1 x 10000 x 10000 iterations
                     */

                    // Now robotA and robotB could have come in 9 possible ways
                    int max = Integer.MIN_VALUE;
                    for (int j = -1; j <= 1; j++) {
                        for (int k = -1; k <= 1; k++) {
                            int res;
                            boolean outOfBounds = robotA + j < 0 || robotA + j > m - 1 || robotB + k < 0 || robotB + k > m - 1;
                            if (outOfBounds) {
                                res = Integer.MIN_VALUE;
                            } else {
                                if (robotA == robotB) {
                                    res = matrix[i][robotA] + dp[0][robotA + j][robotB + k];
                                } else {
                                    res = matrix[i][robotA] + matrix[i][robotB] + dp[0][robotA + j][robotB + k];
                                }
                            }

                            max = Math.max(max, res);
                        }
                    }

                    dp[1][robotA][robotB] = max;
                }
            }
            /*
            Don't do arr copy, bcz it copied the objects and after 1st iteration, both dp[0] and dp[1] will be the same object
             */
            for (int x = 0; x < dp[1].length; x++) {
                System.arraycopy(dp[1][x], 0, dp[0][x], 0, m);
            }
        }

        int result = Integer.MIN_VALUE;
        for (int[] rows : dp[0]) {
            for (int robotA : rows) {
                result = Math.max(result, robotA);
            }
        }
        return result;
    }
}
