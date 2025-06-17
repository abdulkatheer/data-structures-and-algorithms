package io.abdul.dynamic_programming.dp_1d.problem7;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrogJump {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input heights = [2, 1, 3, 5, 4]
        int[] heights1 = {2, 1, 3, 5, 4};
        assertEquals(2, solution.frogJump(heights1), "Minimum energy for heights [2, 1, 3, 5, 4] should be 2");

        // Test Case 2: Example input heights = [7, 5, 1, 2, 6]
        int[] heights2 = {7, 5, 1, 2, 6};
        assertEquals(9, solution.frogJump(heights2), "Minimum energy for heights [7, 5, 1, 2, 6] should be 9");

        // Test Case 3: Example input heights = [3, 10, 3, 11, 3]
        int[] heights3 = {3, 10, 3, 11, 3};
        assertEquals(0, solution.frogJump(heights3), "Minimum energy for heights [3, 10, 3, 11, 3] should be 0");

        // Test Case 4: Single step heights = [5]
        int[] heights4 = {5};
        assertEquals(0, solution.frogJump(heights4), "Minimum energy for heights [5] should be 0");

        // Test Case 5: Two steps heights = [1, 100]
        int[] heights5 = {1, 100};
        assertEquals(99, solution.frogJump(heights5), "Minimum energy for heights [1, 100] should be 99");

        // Test Case 6: Large input heights = [10, 20, 30, 10]
        int[] heights6 = {10, 20, 30, 10};
        assertEquals(20, solution.frogJump(heights6), "Minimum energy for heights [10, 20, 30, 10] should be 20");

        // Test Case 7: Edge case heights = []
        int[] heights7 = {1};
        assertEquals(0, solution.frogJump(heights7), "Minimum energy for empty heights array should be 0");

    }
}

/*
Step 1: Top down recursive solution

T - O(2^n)
S - O(n) -> stack
 */
class Solution {
    public int frogJump(int[] heights) {
        return frogJump(heights, 0, 0);
    }

    private int frogJump(int[] heights, int step, int energy) {
        if (step == heights.length - 1) { // Possible
            return energy;
        }

        if (step == heights.length) { // Impossible
            return Integer.MAX_VALUE; // to make Math.min work
        }

        int energyFor1Step = step + 1 < heights.length ? frogJump(heights, step + 1, energy + Math.abs(heights[step] - heights[step + 1])) : Integer.MAX_VALUE;
        int energyFor2Step = step + 2 < heights.length ? frogJump(heights, step + 2, energy + Math.abs(heights[step] - heights[step + 2])) : Integer.MAX_VALUE;
        return Math.min(energyFor1Step, energyFor2Step);
    }
}

/*
Step 2 - Memoization

Why not go from step 0 to n-1?
From step 0, value at at step n may change if we take a different step. So if we refer a stored value, we may miss a better value.
Why from step n-1 to 0?
We explore all solutions from back and store the min. It'll not change based on the decisions made in the front.
 */
class Solution2 {
    public int frogJump(int[] heights) {
        int n = heights.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return frogJump(heights, n - 1, dp);
    }

    private int frogJump(int[] heights, int step, int[] dp) {
        if (step == 0) { // Possible
            return 0;
        }

        if (dp[step] != -1) {
            return dp[step];
        }

        int energyForStep1 = frogJump(heights, step - 1, dp) + Math.abs(heights[step] - heights[step - 1]);
        int energyForStep2;
        if (step < 2) { // Base case
            energyForStep2 = Integer.MAX_VALUE;
        } else {
            energyForStep2 = frogJump(heights, step - 2, dp) + Math.abs(heights[step] - heights[step - 2]);
        }

        int min = Math.min(energyForStep1, energyForStep2);
        dp[step] = min;
        return min;
    }
}

/*
Step 3 - Bottom up iterative approach

Known base case:
Step 0 needs 0 energy
Step 1 needs Step[1]-Step[0] energy
Step 2 needs Min(Step[2] - Step[1] + energyFor 1, Step[2] - Step[0] + energyFor 0)
 */
class Solution3 {
    public int frogJump(int[] heights) {
        int n = heights.length;
        if (n <= 1) {
            return 0;
        }

        int[] dp = new int[n];
        // Known solutions
        dp[0] = 0;
        dp[1] = Math.abs(heights[1] - heights[0]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.min(Math.abs(heights[i] - heights[i - 2]) + dp[i - 2], Math.abs(heights[i] - heights[i - 1]) + dp[i - 1]);
        }

        return dp[n - 1];
    }
}

/*
Step 4 - Space optimization

 */
class Solution4 {
    public int frogJump(int[] heights) {
        int n = heights.length;
        if (n <= 1) {
            return 0;
        }

        int[] dp = new int[2];
        // Known solutions
        dp[0] = 0;
        dp[1] = Math.abs(heights[1] - heights[0]);

        for (int i = 2; i < n; i++) {
            int minEnergy = Math.min(Math.abs(heights[i] - heights[i - 2]) + dp[0], Math.abs(heights[i] - heights[i - 1]) + dp[1]);
            dp[0] = dp[1];
            dp[1] = minEnergy;
        }

        return dp[1];
    }
}