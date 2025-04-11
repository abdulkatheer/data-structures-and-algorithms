package io.abdul.array.matrix_representation.problem2;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/spiral-matrix/
public class SpiralMatrix {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3, 4, 5, 6}, {20, 21, 22, 23, 24, 7}, {19, 32, 33, 34, 25, 8}, {18, 31, 36, 35, 26, 9}, {17, 30, 29, 28, 27, 10}, {16, 15, 14, 13, 12, 11}};
        Solution solution = new Solution();
//        System.out.println(solution.spiralOrder(matrix));
        matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(solution.spiralOrder(matrix));
    }
}

/**
 * <pre>
 * m=1, n=5
 * 00 01 02 03 04
 *
 * m=2, n=3
 * 00 01 02 12 11 10
 *
 * m=3, n=4
 * 00 01 02 03 | 13 23 | 22 21 20 | 10 | 11 12
 * row           column     row   column  row
 * </pre>
 * When to change row or column iteration?
 * <p>
 * row -> 0; column -> 0 1 2 3, 4 !< 4
 * row -> 1 2, 3 !< 3; column -> 3
 * row -> 2; column -> 2 1 0, -1 !> 0
 * row -> 1; column -> 0
 * row -> 1; column -> 1 2, 3 (already visited)
 * row -> 0 (already visited)
 * <p>
 * Only one solution exists for this problem
 * We'll have four pointers
 * left, right, top and bottom
 * left and top will be at 0 initially
 * right and bottom will be at n-1 initially
 * <p>
 * We move like below iteratively until we meet the dead-end
 * left to right with top as constant, and top++
 * top to bottom with right as constant, and right--
 * right to left with bottom as constant, and bottom--
 * bottom to top with left as constant, and left++
 * <p>
 * Constraints:
 * Array is m x n
 * m will be at least 1
 * n will be at least m and less than or equals 10
 */
/*
Only optimal solution
Time - O(m x n)
Space - O(1)
where m is the size of row and n is the size of column
 */
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;


        /*
        m=6, n=6
        left=0, right=5, top=0, bottom=5

        Itr 1:
        00 01 02 03 04 05 (6)
        top=1

        15 25 35 45 55 (5)
        right=4

        54 53 52 51 50 (5)
        bottom=4

        40 30 20 10 (4)
        left=1

        Itr 2:
        11 12 13 14 (4)
        top=2

        24 34 44 (3)
        right=3

        43 42 41 (3)
        bottom=3

        31 21 (2)
        left=2

        Itr 3:
        22 23 (2)
        top=3

        33 (1)
        right=2

        32 (1)
        left=3

        left > right
        bottom=3, top=3
         */
        int left = 0, right = n - 1, top = 0, bottom = m - 1;
        while (left <= right && top <= bottom) {// update end condition
            // left to right with top as constant
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;

            if (top > bottom) {
                break;
            }
            // top to bottom with right as constant
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            // right to left with bottom as constant
            if (right < left) {
                break;
            }
            for (int i = right; i >= left; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;

            // bottom to top with left as constant
            if (bottom < top) {
                break;
            }
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
        }
        return result;
    }
}
