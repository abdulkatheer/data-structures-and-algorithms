package io.abdul.binary_search.two_d_arrays.problem2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
n = 3, m = 4, total = 12
low=0, high=11
1  2  3  4  5  6  7  8  9  10 11 12
00 01 02 03 10 11 12 13 20 21 22 23
0  1  2  3  4  5  6  7  8  9  10 11
lowR=0, highR=2, lowC=0, lowC=3, midR=1 midC=1 (5th)
lowR=
11 => 11/4=2 row, 11%4=3 column
10 => 11/4=2, 10%4=2

n=1 m=1
0/4, 0%4

n=1, m=2 00 01 10 11
3/2 3%2
 */
public class SearchIn2D {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1: Target exists in the middle of the matrix
        assertTrue(solution.searchMatrix(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}, 8));

        // Test case 2: Target does not exist in the matrix
        assertFalse(solution.searchMatrix(new int[][]{{1, 2, 4}, {6, 7, 8}, {9, 10, 34}}, 78));

        // Test case 3: Target is the smallest element in the matrix
        assertTrue(solution.searchMatrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 1));

        // Test case 4: Target is the largest element in the matrix
        assertTrue(solution.searchMatrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 9));

        // Test case 5: Matrix with a single row, target exists
        assertTrue(solution.searchMatrix(new int[][]{{1, 3, 5, 7}}, 5));

        // Test case 6: Matrix with a single row, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{{1, 3, 5, 7}}, 6));

        // Test case 7: Matrix with a single column, target exists
        assertTrue(solution.searchMatrix(new int[][]{{1}, {3}, {5}, {7}}, 5));

        // Test case 8: Matrix with a single column, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{{1}, {3}, {5}, {7}}, 6));

        // Test case 9: Matrix with a single element, target exists
        assertTrue(solution.searchMatrix(new int[][]{{42}}, 42));

        // Test case 10: Matrix with a single element, target does not exist
        assertFalse(solution.searchMatrix(new int[][]{{42}}, 43));

        // Test case 13: Target is in the first row
        assertTrue(solution.searchMatrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 3));

        // Test case 14: Target is in the last row
        assertTrue(solution.searchMatrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 7));

    }
}

class Solution {
    public boolean searchMatrix(int[][] mat, int target) {
        int n = mat.length;
        int m = mat[0].length;

        int low = 0, high = (n * m) - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int r = mid / m;
            int c = mid % m;
            int element = mat[r][c];
            if (element == target) {
                return true;
            } else if (target < element) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return false;
    }
}

