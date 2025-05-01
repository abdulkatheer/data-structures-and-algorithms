package io.abdul.array.faq_medium.problem8;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/rotate-matrix-by-90-degrees
public class RotateMatrix90Degree {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        solution.rotateMatrix(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}

/*
00 01 02 03
10 11 12 13
20 21 22 23
30 31 32 33

30 20 10 00
31 21 11 01
32 22 12 02
33 23 13 03
 */
/*
Brute - Additional space
T - O(n^2)
S - O(n^2)
 */
class Solution {
    public void rotateMatrix(int[][] matrix) {
        int n = matrix.length;

        int[][] copy = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], n);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = copy[n - j - 1][i];
            }
        }
    }
}

/*
90-degree rotation = Transpose + Column Reversal
T - O(n^2)
S - O(1)

Original
00 01 02 03
10 11 12 13
20 21 22 23
30 31 32 33

Transpose
00 10 20 30
01 11 21 31
02 12 22 32
03 13 23 33
We only need to iterate either upper and downside of diagonal. Diagonal doesn't change too.

Reversal
30 20 10 00
31 21 11 01
32 22 12 02
33 23 12 03
 */
class Solution2 {
    public void rotateMatrix(int[][] matrix) {
        int n = matrix.length;

        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Reversal
        int mid = n / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < mid; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = temp;
            }
        }
    }
}
