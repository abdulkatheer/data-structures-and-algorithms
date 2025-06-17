package io.abdul.dynamic_programming.dp_2d.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://takeuforward.org/plus/dsa/dynamic-programming/2d-dp/ninja's-training
public class NinjaTraining {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input matrix = [[10, 40, 70], [20, 50, 80], [30, 60, 90]]
        int[][] matrix1 = {{10, 40, 70}, {20, 50, 80}, {30, 60, 90}};
        assertEquals(210, solution.ninjaTraining(matrix1), "Maximum merit points for matrix [[10, 40, 70], [20, 50, 80], [30, 60, 90]] should be 210");

        // Test Case 2: Example input matrix = [[70, 40, 10], [180, 20, 5], [200, 60, 30]]
        int[][] matrix2 = {{70, 40, 10}, {180, 20, 5}, {200, 60, 30}};
        assertEquals(290, solution.ninjaTraining(matrix2), "Maximum merit points for matrix [[70, 40, 10], [180, 20, 5], [200, 60, 30]] should be 290");

        // Test Case 3: Example input matrix = [[20, 10, 10], [20, 10, 10], [20, 30, 10]]
        int[][] matrix3 = {{20, 10, 10}, {20, 10, 10}, {20, 30, 10}};
        assertEquals(60, solution.ninjaTraining(matrix3), "Maximum merit points for matrix [[20, 10, 10], [20, 10, 10], [20, 30, 10]] should be 60");

        // Test Case 4: Single day matrix = [[10, 20, 30]]
        int[][] matrix4 = {{10, 20, 30}};
        assertEquals(30, solution.ninjaTraining(matrix4), "Maximum merit points for matrix [[10, 20, 30]] should be 30");

        // Test Case 5: Two days matrix = [[10, 20, 30], [40, 50, 60]]
        int[][] matrix5 = {{10, 20, 30}, {40, 50, 60}};
        assertEquals(80, solution.ninjaTraining(matrix5), "Maximum merit points for matrix [[10, 20, 30], [40, 50, 60]] should be 80");

        // Test Case 6: Large input matrix = [[10, 20, 30], [40, 50, 60], [70, 80, 90], [100, 110, 120]]
        int[][] matrix6 = {{10, 20, 30}, {40, 50, 60}, {70, 80, 90}, {100, 110, 120}};
        assertEquals(280, solution.ninjaTraining(matrix6), "Maximum merit points for matrix [[10, 20, 30], [40, 50, 60], [70, 80, 90], [100, 110, 120]] should be 260");
    }
}

/*
Step 1: Top-down Recursive solution

T - O(2^n) - 2 * 2^n
S - O(n) - stack

a   b   c
10, 40, 70
20, 50, 80
30, 60, 90

a1 -> b2 -> c1
a1 -> b2 -> c3
a1 -> b3 -> c1
a1 -> b3 -> c2

a2 -> b1 -> c2
a2 -> b1 -> c3
a2 -> b3 -> c1
a2 -> b3 -> c2

a3 -> b1 -> c2
a3 -> b1 -> c3
a3 -> b2 -> c1
a3 -> b2 -> c3
 */
class Solution {
    public int ninjaTraining(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            max = Math.max(max, ninjaTraining(matrix, 0, i)); // Activity at day 1
        }

        return max;
    }

    private int ninjaTraining(int[][] matrix, int day, int activity) {
        if (day == matrix.length - 1) { // last day, so only one option
            return matrix[day][activity];
        }

        int withOneActivity;
        int withOtherActivity;
        if (activity == 0) {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 1);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 2);
        } else if (activity == 1) {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 0);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 2);
        } else {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 0);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 1);
        }

        return Math.max(withOneActivity, withOtherActivity);
    }
}

/*
Step 2 - Memoization

T - O(n) - 2n
S - O(n) - stack + dp

For same day and activity, multiple times called
 */
class Solution2 {
    public int ninjaTraining(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int n = matrix.length;
        int k = 3;
        int[][] dp = new int[n][k];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < k; i++) {
            max = Math.max(max, ninjaTraining(matrix, 0, i, dp)); // Activity at day 1
        }

        return max;
    }

    private int ninjaTraining(int[][] matrix, int day, int activity, int[][] dp) {
        if (day == matrix.length - 1) { // last day, so only one option
            return matrix[day][activity];
        }

        if (dp[day][activity] != -1) {
            return dp[day][activity];
        }

        int withOneActivity;
        int withOtherActivity;
        if (activity == 0) {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 1, dp);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 2, dp);
        } else if (activity == 1) {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 0, dp);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 2, dp);
        } else {
            withOneActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 0, dp);
            withOtherActivity = matrix[day][activity] + ninjaTraining(matrix, day + 1, 1, dp);
        }

        int max = Math.max(withOneActivity, withOtherActivity);
        dp[day][activity] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative approach

T - O(n)
S - O(n) - n*k

Known solutions:
Considering only one day, max of 3 will be the answer
So we'll check for each choice and take max

Calc max at day 2; 0 - Max (prev_0 + current_1, prev_0 + current_2); 1 - Max (prev_1 + current_0, prev_1 + current_2); 2 - Max (prev_2 + current_1, prev_2 + current_1)
Calc max at day 3; 0 - Max (prev_0 + current_1, prev_0 + current_2); 1 - Max (prev_1 + current_0, prev_1 + current_2); 2 - Max (prev_2 + current_1, prev_2 + current_1)
..
Calc max at day n-1; 0 - Max (prev_0 + current_1, prev_0 + current_2); 1 - Max (prev_1 + current_0, prev_1 + current_2); 2 - Max (prev_2 + current_1, prev_2 + current_1)

Final answer, max of day n-1
 */
class Solution3 {
    public int ninjaTraining(int[][] matrix) {
        int n = matrix.length;
        int k = 3;
        int[][] dp = new int[n][k];
        // Known result; base case
        dp[0][0] = matrix[0][0];
        dp[0][1] = matrix[0][1];
        dp[0][2] = matrix[0][2];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + matrix[i][0], dp[i - 1][2] + matrix[i][0]);
            dp[i][1] = Math.max(dp[i - 1][0] + matrix[i][1], dp[i - 1][2] + matrix[i][1]);
            dp[i][2] = Math.max(dp[i - 1][0] + matrix[i][2], dp[i - 1][1] + matrix[i][2]);
        }

        return Math.max(Math.max(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
    }
}

/*
Step 4 - Space optimization

T - O(n)
S - O(1)

We only need one day's data
 */
class Solution4 {
    public int ninjaTraining(int[][] matrix) {
        int n = matrix.length;
        int[] dp = new int[3];
        // Known result; base case
        dp[0] = matrix[0][0];
        dp[1] = matrix[0][1];
        dp[2] = matrix[0][2];

        for (int i = 1; i < n; i++) {
            int max0 = Math.max(dp[1] + matrix[i][0], dp[2] + matrix[i][0]);
            int max1 = Math.max(dp[0] + matrix[i][1], dp[2] + matrix[i][1]);
            int max2 = Math.max(dp[0] + matrix[i][2], dp[1] + matrix[i][2]);
            dp[0] = max0;
            dp[1] = max1;
            dp[2] = max2;
        }

        return Math.max(Math.max(dp[0], dp[1]), dp[2]);
    }
}