package io.abdul.array.faq_medium.problem3;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/print-the-matrix-in-spiral-manner
public class SpiralClockwise {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        System.out.println(solution.spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}}));
        System.out.println(solution.spiralOrder(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}));
        System.out.println(solution.spiralOrder(new int[][]{{-17, -22, -42, -43, 13, 10, -38, 42, 36, -41}, {28, 21, -5, 20, -34, 48, 38, 8, 30, 7}, {-40, -12, -18, 26, 24, 1, -12, 28, 35, -9}, {16, -5, 21, 13, -49, -3, 30, 35, 25, 22}, {-4, 42, -19, -37, 14, 40, 43, 3, 18, 4}, {-10, 4, -39, -6, 40, -24, -14, 17, -6, 16}, {-30, -8, 12, -27, -12, 36, 29, -11, 30, -16}}));
    }
}

// TODO finish this
class Solution {
    /*
    top - 0, bottom - row-1
    left - 0, right - column-1
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int left = 0, right = matrix[0].length - 1;
        int top = 0, bottom = matrix.length - 1;

        List<Integer> result = new ArrayList<>(matrix.length * matrix[0].length);
        while (top <= bottom && left <= right) {
            // left to right
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;

            // top to bottom
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom) { // due to top++
                // right to left
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if (left <= right) { // due to right--
                // bottom to top
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }

        return result;
    }
}
