package io.abdul.binary_search.two_d_arrays.problem3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchIn2DII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test case 1: Target exists in the middle of the matrix
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        }, 5));

        // Test case 2: Target does not exist in the matrix
        assertFalse(solution.searchMatrix(new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        }, 20));

        // Test case 3: Target is the smallest element in the matrix
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 4, 7},
                {8, 10, 12},
                {13, 15, 17}
        }, 1));

        // Test case 4: Target is the largest element in the matrix
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 4, 7},
                {8, 10, 12},
                {13, 15, 17}
        }, 17));

        // Test case 5: Matrix with a single row, target exists
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 3, 5, 7, 9}
        }, 5));

        // Test case 6: Matrix with a single row, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{
                {1, 3, 5, 7, 9}
        }, 6));

        // Test case 7: Matrix with a single column, target exists
        assertTrue(solution.searchMatrix(new int[][]{
                {1}, {3}, {5}, {7}, {9}
        }, 5));

        // Test case 8: Matrix with a single column, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{
                {1}, {3}, {5}, {7}, {9}
        }, 6));

        // Test case 9: Matrix with a single element, target exists
        assertTrue(solution.searchMatrix(new int[][]{
                {42}
        }, 42));

        // Test case 10: Matrix with a single element, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{
                {42}
        }, 43));

        // Test case 11: Target is in the first row
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }, 3));

        // Test case 12: Target is in the last row
        assertTrue(solution.searchMatrix(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }, 7));
    }
}

/*
Better -  Modified Binary Search
T - O(n * logm)
S - O(1)
 */
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        for (int i = 0; i < n; i++) {
            int low = 0, high = m - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int element = matrix[i][mid];
                if (element == target) {
                    return true;
                } else if (target < element) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return false;
    }
}

/*
Optimal - Modified Linear Search
T - O(n + m)
S - O(1)

If we start from 0,0, there're two ways always. Right and down is larger and left and up is larger.
But if we start from right top corner 0,m-1, we can trim it to one option. Left is smaller, down is larger
 */
class Solution2 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int row = 0, column = m - 1;

        while (row < n && column >= 0) {
            int element = matrix[row][column];
            if (element == target) {
                return true;
            } else if (target < element) {
                column--;
            } else {
                row++;
            }
        }

        return false;
    }
}