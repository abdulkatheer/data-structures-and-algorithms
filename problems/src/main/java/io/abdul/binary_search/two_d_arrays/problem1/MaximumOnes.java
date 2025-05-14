package io.abdul.binary_search.two_d_arrays.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumOnes {
    public static void main(String[] args) {
        Solution solution = new Solution();
        // Test case 1: Row 0 has the maximum number of 1s
        assertEquals(0, solution.rowWithMax1s(new int[][]{{1, 1, 1}, {0, 0, 1}, {0, 0, 0}}));

        // Test case 2: No row contains any 1
        assertEquals(-1, solution.rowWithMax1s(new int[][]{{0, 0}, {0, 0}}));

        // Test case 3: Row 1 has the maximum number of 1s
        assertEquals(1, solution.rowWithMax1s(new int[][]{{0, 0, 0}, {1, 1, 1}, {0, 1, 1}}));

        // Test case 4: Row 2 has the maximum number of 1s
        assertEquals(2, solution.rowWithMax1s(new int[][]{{0, 0, 0}, {0, 0, 1}, {1, 1, 1}}));

        // Test case 5: All rows have the same number of 1s, return the smallest index
        assertEquals(0, solution.rowWithMax1s(new int[][]{{1, 1}, {1, 1}, {1, 1}}));
    }
}

/*
Optimal - Modified Binary Search
T - O(n * logm)
S - O(1)
 */
class Solution {
    public int rowWithMax1s(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int maxRow = -1;
        int maxOnes = 0;
        for (int i = 0; i < n; i++) {
            int low = 0, high = m - 1;
            int answer = -1;
            while (low <= high) {
                int mid = (low + high) / 2;

                if (mat[i][mid] == 0) {
                    low = mid + 1;
                } else {
                    answer = mid;
                    high = mid - 1;
                }
            }
            if (answer != -1) {
                int ones = m - answer;
                if (ones > maxOnes) {
                    maxOnes = ones;
                    maxRow = i;
                }
            }
        }

        return maxRow;
    }
}