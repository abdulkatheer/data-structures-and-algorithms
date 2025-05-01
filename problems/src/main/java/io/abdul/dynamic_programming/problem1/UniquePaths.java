package io.abdul.dynamic_programming.problem1;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/unique-paths/
// #tag_recursion
public class UniquePaths {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
//        System.out.println(solution.uniquePaths(2, 2));
//        System.out.println(solution.uniquePaths(3, 2));
        System.out.println(solution.uniquePaths(3, 7));
    }
}

// Recursion
class Solution {
    public int uniquePaths(int m, int n) {
        return countPaths(0, 0, m, n);
    }

    private static int countPaths(int i, int j, int m, int n) {
//        System.out.println("i=" + i + " j=" + j);
        if (i == m - 1 || j == n - 1) { // Base condition - reached last row or last column
            return 1;// only one path, can move either to right or down alone
        }

        return countPaths(i + 1, j, m, n) + countPaths(i, j + 1, m, n);
    }
}

// Recursion + Memoization
class Solution2 {
    public int uniquePaths(int m, int n) {
        Map<String, Integer> state = new HashMap<>();
        return countPaths(0, 0, m, n, state);
    }

    private static int countPaths(int i, int j, int m, int n, Map<String, Integer> state) {
        if (state.containsKey("" + i + j)) {
            return state.get("" + i + j);
        }
//        System.out.println("i=" + i + " j=" + j);
        if (i == m - 1 || j == n - 1) { // Base condition - reached last row or last column
            return 1;// only one path, can move either to right or down alone
        }
        int c1 = countPaths(i + 1, j, m, n, state);
        String k1 = "" + (i + 1) + j;
        state.put(k1, c1);

        int c2 = countPaths(i, j + 1, m, n, state);
        String k2 = "" + i + (j + 1);
        state.put(k2, c2);

        return c1 + c2;
    }
}

// Iterative + Tabulation
/*
Time - O(m * n)
Space - O(m * n)
 */
class Solution3 {
    public int uniquePaths(int m, int n) {
        // Initialise table of mxn with values as 1
        int[][] resultTable = new int[m][n];

//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                resultTable[i][j] = 1;
//            }
//        }
//
//        // Now path from any one of last column or last row will always be 1
//        // This was our base case in recursive function. So we need to start from back excluding last row and column
//        // m-1 to 0, n-1 to 0 (as index starts from zero, m-2 to 0, n-2 to 0
//
//        for (int i = m - 2; i >= 0; i--) {
//            for (int j = n - 2; j >= 0; j--) {
//                resultTable[i][j] = resultTable[i + 1][j] + resultTable[i][j + 1];
//                // Path at i,j is path from right side + path from downside
//            }
//        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 || j == n - 1) {
                    // Path from any element in the last row/column will always be 1
                    // If it's at last column, it can move only downwards to reach target
                    // If it's at last row, it can move only rightwards to reach target
                    // This was our base case in recursive function
                    resultTable[i][j] = 1;
                } else {
                    resultTable[i][j] = resultTable[i + 1][j] + resultTable[i][j + 1];
                    // Path at i,j is path from right side + path from downside
                }
            }
        }

        return resultTable[0][0];
    }
}

// Iterative + Tabulation [Space Optimized]
/*
Time - O(m * n)
Space - O(n)

Idea is that we always need 2 rows keep the state
1 to keep total so far and 1 to keep current state
row 1 - calculates current total with respect to previous total
row 2 - maintains total so far

To understand this, run Solution3 in debug mode and observe results of each row from bottom up in each iteration
It just shows how the values at each row generated. It all needs the last element of current row's value to be 1 and
previous results stored in previous row
 */
class Solution4 {
    public int uniquePaths(int m, int n) {
        // Initialise table of mxn with values as 1
        int[][] resultTable = new int[2][n];
/*
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                resultTable[i][j] = 1;
            }
        }

        // Now path from any one of last column or last row will always be 1
        // This was our base case in recursive function. So we need to start from back excluding last row and column
        // m-1 to 0, n-1 to 0 (as index starts from zero, m-2 to 0, n-2 to 0

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                resultTable[0][j] = resultTable[1][j] + resultTable[0][j + 1];
                // Path at i,j is path from right side + path from downside
            }
            resultTable[1] = resultTable[0]; // Copy the total so far to row 2,
            // at this point row 1 can be all 1
            // technically we're overriding values, so no need to explicitly set it to 1
            // resultTable[0][j + 1] this might look like we're using previous results, but that's not true
            // At the start of 1 inner iteration, we read t[0][j+1], which will the last element in the row
            // Last element in the row will always be 1, we never update that. So we use value 1 in the first iteration
            // First iteration updates value at t[0][j], so we're overriding previous result
            // Hence we're not relying on previous results stored in row 1, which is copied to row 2
        }
*/
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 || j == n - 1) {
                    // Path from any element in the last row/column will always be 1
                    // If it's at last column, it can move only downwards to reach target
                    // If it's at last row, it can move only rightwards to reach target
                    // This was our base case in recursive function
                    resultTable[0][j] = 1;
                } else {
                    resultTable[0][j] = resultTable[1][j] + resultTable[0][j + 1];
                    // Path at i,j is path from right side + path from downside
                }
            }
            resultTable[1] = resultTable[0]; // Move prev result to row 2 and keep row 1 empty for next calculation
        }

        return resultTable[0][0];
    }
}