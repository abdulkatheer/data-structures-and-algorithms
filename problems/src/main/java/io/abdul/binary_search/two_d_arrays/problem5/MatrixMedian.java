package io.abdul.binary_search.two_d_arrays.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixMedian {
    public static void main(String[] args) {
//        Solution solution = new Solution();

        Solution2 solution = new Solution2();
        // Test case 1: Median in a small 3x3 m atrix
        int[][] mat1 = {{1, 4, 9}, {2, 5, 6}, {3, 7, 8}};
//        assertEquals(5, solution.findMedian(mat1));

        // Test case 2: Median in a matrix with duplicate values
        int[][] mat2 = {{1, 3, 8}, {2, 3, 4}, {1, 2, 5}};
        assertEquals(3, solution.findMedian(mat2));

        // Test case 3: Median in a matrix with larger values
        int[][] mat3 = {{1, 4, 15}, {2, 5, 6}, {3, 8, 11}};
        assertEquals(5, solution.findMedian(mat3));

        // Test case 4: Single row matrix
        int[][] mat4 = {{1, 2, 3, 4, 5}};
        assertEquals(3, solution.findMedian(mat4));

        // Test case 5: Single column matrix
        int[][] mat5 = {{1}, {2}, {3}, {4}, {5}};
        assertEquals(3, solution.findMedian(mat5));

        // Test case 6: Large matrix with odd number of elements
        int[][] mat6 = {
                {1, 3, 5},
                {7, 9, 11},
                {13, 15, 17}
        };
        assertEquals(9, solution.findMedian(mat6));

        // Test case 7: Matrix with minimum size (1x1)
        int[][] mat7 = {{42}};
        assertEquals(42, solution.findMedian(mat7));
    }
}

/*
Brute - With extra space and sort
T - O(n * m log(n*m))
S - O(n * m)
 */
class Solution {
    public int findMedian(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int x = n * m;

        int[] all = new int[x];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, all, m * i, m);
        }

        Arrays.sort(all);

        return all[x / 2];
    }
}

/*
Optimal - Binary Search on Answers. 
T - O(log(max-min) * n log(m)) -> log(max-min) is the main Binary Search on answer; n log(m) is the Binary Search to find bound
S - O(1)

Find smallest and largest elements and take them as low and high. Now we need to check how many elements are < than mid. 
If it's equals, return.
If higher, reduce mid.
If smaller, increase mid.

smallest - smallest of 1st element of all row
 */
class Solution2 {
    public int findMedian(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        int required = ((n * m) / 2) + 1;

        for (int i = 0; i < n; i++) {
            low = Math.min(matrix[i][0], low);
            high = Math.max(matrix[i][m - 1], high);
        }

        int answer = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int count = countSmaller(matrix, mid);
            if (count < required) {
                low = mid + 1;
            } else {
                answer = mid;
                high = mid - 1;
            }
        }

        return answer;
    }

    private int countSmaller(int[][] matrix, int bound) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            count += upperBound(matrix[i], bound);
        }
        return count;
    }

    private int upperBound(int[] matrix, int bound) {
        int low = 0, high = matrix.length - 1;
        int answer = matrix.length; // when all elements are smaller, entire array is within bound
        while (low <= high) {
            int mid = (low + high) / 2;

            if (matrix[mid] > bound) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return answer;
    }
}