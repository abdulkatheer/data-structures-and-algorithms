package io.abdul.binary_search.two_d_arrays.problem4;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindPeak {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // Test case 1: Peak in the middle of the matrix
        int[][] mat1 = {{10, 20, 15}, {21, 30, 14}, {7, 16, 32}};
        int[] result1 = solution.findPeakGrid(mat1);
        assertTrue(isPeak(mat1, result1));

        // Test case 2: Peak at the corner of the matrix
        int[][] mat2 = {{10, 7}, {11, 17}};
        int[] result2 = solution.findPeakGrid(mat2);
        assertTrue(isPeak(mat2, result2));

        // Test case 3: Peak in a strictly increasing matrix
        int[][] mat3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] result3 = solution.findPeakGrid(mat3);
        assertTrue(isPeak(mat3, result3));

        // Test case 4: Single row matrix
        int[][] mat4 = {{1, 3, 2, 5, 4}};
        int[] result4 = solution.findPeakGrid(mat4);
        assertTrue(isPeak(mat4, result4));

        // Test case 5: Single column matrix
        int[][] mat5 = {{1}, {3}, {2}, {5}, {4}};
        int[] result5 = solution.findPeakGrid(mat5);
        assertTrue(isPeak(mat5, result5));

        // Test case 6: Large matrix with random values
        int[][] mat6 = {
                {10, 20, 15, 14, 13},
                {21, 30, 14, 12, 11},
                {7, 16, 32, 10, 9},
                {6, 5, 4, 3, 2},
                {1, 0, -1, -2, -3}
        };
        int[] result6 = solution.findPeakGrid(mat6);
        assertTrue(isPeak(mat6, result6));

        // Test case 7: Matrix with the smallest size (1x1)
        int[][] mat7 = {{42}};
        int[] result7 = solution.findPeakGrid(mat7);
        assertTrue(isPeak(mat7, result7));
    }

    private static boolean isPeak(int[][] mat, int[] peak) {
        int i = peak[0];
        int j = peak[1];
        int value = mat[i][j];
        int n = mat.length;
        int m = mat[0].length;

        int up = i > 0 ? mat[i - 1][j] : -1;
        int down = i < n - 1 ? mat[i + 1][j] : -1;
        int left = j > 0 ? mat[i][j - 1] : -1;
        int right = j < m - 1 ? mat[i][j + 1] : -1;

        return value > up && value > down && value > left && value > right;
    }
}

/*


This is similar to the 1D peak intuition.
We always look for larger element, which will always give a solution. There could be better peaks on the other side as well, but we just need any peak.
If we're on a slop, if we go towards higher slop, it'll always either end up in a deadend or go downwards. Both are peak for us!
Here we start with a mid-element. If upper cell is larger, means answer can be in any of the upper part. Assume element is and left l, right r, up u and down d.
If u > e,l,r,d, we go upwards. We'll not come downwards again, bcz all will be smaller. Draw a 2D table and observe!
 */
class Solution {
    public int[] findPeakGrid(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int low = 0, high = n - 1; // traversing via row, we can do via column as well
        int column = (m - 1) / 2; // mid
        while (low <= high) {
            int row = (low + high) / 2;

            int e = mat[row][column];
            int u = row - 1 >= 0 ? mat[row - 1][column] : -1;
            int d = row + 1 < n ? mat[row + 1][column] : -1;
            int l = column - 1 >= 0 ? mat[row][column - 1] : -1;
            int r = column + 1 < m ? mat[row][column + 1] : -1;

            if (e > u && e > d && e > l && e > r) {
                return new int[]{row, column};
            } else if (u > d && u > l && u > r) {
                high = row - 1;
            } else if (d > u && d > l && d > r) {
                low = row + 1;
            } else if (l > u && l > d && l > r) {
                column--;
            } else {
                column++;
            }
        }

        return new int[]{-1, -1};
    }
}