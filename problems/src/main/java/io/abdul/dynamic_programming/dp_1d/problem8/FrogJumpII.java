package io.abdul.dynamic_programming.dp_1d.problem8;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrogJumpII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input heights = [10, 5, 20, 0, 15], k = 2
        int[] heights1 = {10, 5, 20, 0, 15};
        int k1 = 2;
        assertEquals(15, solution.frogJump(heights1, k1), "Minimum energy for heights [10, 5, 20, 0, 15] with k = 2 should be 15");

        // Test Case 2: Example input heights = [15, 4, 1, 14, 15], k = 3
        int[] heights2 = {15, 4, 1, 14, 15};
        int k2 = 3;
        assertEquals(2, solution.frogJump(heights2, k2), "Minimum energy for heights [15, 4, 1, 14, 15] with k = 3 should be 2");

        // Test Case 3: Example input heights = [15, 4, 1, 14, 15], k = 4
        int[] heights3 = {15, 4, 1, 14, 15};
        int k3 = 4;
        assertEquals(0, solution.frogJump(heights3, k3), "Minimum energy for heights [15, 4, 1, 14, 15] with k = 4 should be 0");

        // Test Case 4: Single step heights = [5], k = 1
        int[] heights4 = {5};
        int k4 = 1;
        assertEquals(0, solution.frogJump(heights4, k4), "Minimum energy for heights [5] with k = 1 should be 0");

        // Test Case 5: Two steps heights = [1, 100], k = 1
        int[] heights5 = {1, 100};
        int k5 = 1;
        assertEquals(99, solution.frogJump(heights5, k5), "Minimum energy for heights [1, 100] with k = 1 should be 99");

        // Test Case 6: Large input heights = [10, 20, 30, 10], k = 2
        int[] heights6 = {10, 20, 30, 10};
        int k6 = 2;
        assertEquals(20, solution.frogJump(heights6, k6), "Minimum energy for heights [10, 20, 30, 10] with k = 2 should be 20");

        // Test Case 7: Edge case heights = [], k = 1
        int[] heights7 = {1};
        int k7 = 10;
        assertEquals(0, solution.frogJump(heights7, k7), "Minimum energy for empty heights array with k = 1 should be 0");

    }
}

/*
Step 1: Top-down recursive approach

T - O(k^n)
S - O(n) - stack
 */
class Solution {
    public int frogJump(int[] heights, int k) {
        return frogJump(heights, 0, k, 0);
    }

    private int frogJump(int[] heights, int step, int k, int energy) {
        if (step == heights.length - 1) {
            return energy;
        }

        int minEnergy = Integer.MAX_VALUE;
        for (int i = step + 1; i <= step + k && i < heights.length; i++) { // 1 to k steps max
            minEnergy = Math.min(frogJump(heights, i, k, energy + Math.abs(heights[step] - heights[i])), minEnergy);
        }

        return minEnergy;
    }
}

/*
Step 2: Memoization

T - O(nk)
S - O(n) - stack

We can't go from step 0 to n-1, as we may loose better values when backtracking and trying different steps

0 1 2 3 4 5
Calc min energy required to reach 0 from start
Calc min energy required to reach 1 from start based on 0
Calc min energy required to reach 2 from start based on 0,1
...
Calc min energy required to reach 5 from start based on 0,1,2,3,4
 */
class Solution2 {
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return frogJump(heights, n - 1, k, 0, dp);
    }

    private int frogJump(int[] heights, int step, int k, int energy, int[] dp) {
        if (dp[step] != -1) {
            return dp[step];
        }

        if (step == 0) {
            return energy;
        }

        int minEnergy = Integer.MAX_VALUE;

        for (int i = step - 1; i >= step - k && i >= 0; i--) {
            minEnergy = Math.min(Math.abs(heights[step] - heights[i]) + frogJump(heights, i, k, energy, dp), minEnergy);
        }

        dp[step] = minEnergy;
        return minEnergy;
    }
}

/*
Step 3: Bottom up iterative solution

T - O(nk)
S - O(n)

Known solutions
Step 0 needs 0 energy
Step 1 needs heights[1] - heights[0] energy
Step 2 needs min[ abs(heights[2]-heights[1]) + energy_at_1 , abs(heights[2]-heights[0]) + energy_at_0 ]
Step 3 needs min[ abs(heights[3]-heights[2]) + energy_at_2 , abs(heights[3]-heights[1]) + energy_at_1 , abs(heights[3]-heights[0]) + energy_at_0 ]
Step 4 needs min[ abs(heights[4]-heights[3]) + energy_at_2 , abs(heights[4]-heights[2]) + energy_at_1 , abs(heights[4]-heights[1]) + energy_at_1 , abs(heights[4]-heights[0]) + energy_at_0 ]
 */
class Solution3 {
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        if (n <= 1) {
            return 0;
        }
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            int minEnergy = Integer.MAX_VALUE;
            for (int j = i - 1; j >= 0 && j >= i - k; j--) {
                // min (energy to come from j to i + min energy to come to j
                minEnergy = Math.min(Math.abs(heights[i] - heights[j]) + dp[j], minEnergy);
            }
            dp[i] = minEnergy;
        }
        return dp[heights.length - 1];
    }
}

/*
Step 4: Space optimization

T - O(nk)
S - O(k)
We only need last k values
let's say n=10, k=2
0 -> 0
1 -> 1
2 -> 0
3 -> 1
.
.
.
10 -> 0
 */
class Solution4 {
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        if (n <= 1) {
            return 0;
        }
        int[] dp = new int[k];
        Arrays.fill(dp, -1);
        dp[0] = 0; // 0 % k = 0

        for (int i = 1; i < n; i++) {
            int minEnergy = Integer.MAX_VALUE;
            for (int j = i - 1; j >= 0 && j >= i - k; j--) {
                // min (energy to come from j to i + min energy to come to j
                minEnergy = Math.min(Math.abs(heights[i] - heights[j]) + dp[j % k], minEnergy);
            }
            dp[i % k] = minEnergy;
        }
        return dp[(n - 1) % k];
    }
}
