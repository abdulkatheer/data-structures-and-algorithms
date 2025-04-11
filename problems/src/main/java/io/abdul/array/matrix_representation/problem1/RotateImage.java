package io.abdul.array.matrix_representation.problem1;

import java.util.Arrays;

// https://leetcode.com/problems/rotate-image/
public class RotateImage {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("Before => " + Arrays.deepToString(matrix));
        solution.rotate(matrix);
        System.out.println("After => " + Arrays.deepToString(matrix));
    }
}

/*
Brute-force approach (Additional space)
Time - O(n)
Space - O(n)
where n is the number of elements

OR
Time - O(n^2)
Space - O(n^2)
where n is the length of the matrix
 */
class Solution {
    /*
    00 01 02
    10 11 12
    20 21 22

    20 10 00
    21 11 01
    22 12 02
     */
    public void rotate(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix.length]; // Space - O(n)

        for (int i = 0, ri = matrix.length - 1; i < matrix.length; i++, ri--) { // Time - O(n)
            for (int j = 0; j < matrix[i].length; j++) {
                result[j][ri] = matrix[i][j];
            }
        }

        // Copying result back to original array
        for (int i = 0; i < result.length; i++) { // Time - O(sqrt (n)) as arrayCopy copies by memory location in constant time
            System.arraycopy(result[i], 0, matrix[i], 0, result[i].length);
        }
    }
}

/*
Better solution
Time - 2 x O(n^2) -> O(n^2)
Space - O(1)
 */
class Solution2 {
    /*
    Key idea here is, each row is in the column, but in reverse order.
    A transpose will help us move each row into a column
    A reverse after that will help us reverse each column

       Input:
       1 2 3
       4 5 6
       7 8 9

       Output:
       7 4 1
       8 5 2
       9 6 3

       Step 1 -> Transpose:
       1 4 7
       2 5 8
       3 6 9

       Step 2 -> Reverse each column:
       7 4 1
       8 5 2
       9 6 3

     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        /*
        Transpose
        00 01 02
        10 11 12
        20 21 22

        00 10 20
        01 11 21
        02 12 22

        [i][j] -> [j][i]
        Diagonals stays at same position

        So, what's swapping?
        01 -> 10
        02 -> 20
        12 -> 21
        Elements above the diagonal is swapped with elements below the diagonal

        So, if i is 0, j will be 0+1 to n and if i is 2, j will be 3 to n
        So, j=i+1 to n-1

        Also last row will not be swapping, so i can be n-1
         */

        for (int i = 0; i < n - 1; i++) { // Time - O(n^2)
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        /*
        Reverse
        00 01 02
        10 11 12
        20 21 22

        02 01 00
        12 11 10
        22 21 20
         */

        int mid = n / 2;
        for (int i = 0; i < n; i++) { // Time - O(n^2)
            for (int j = 0; j < mid; j++) {
                int temp = matrix[i][j];
                int swapIndex = n - j - 1;
                matrix[i][j] = matrix[i][swapIndex];
                matrix[i][swapIndex] = temp;
            }
        }
    }
}
