package io.abdul.dynamic_programming.dp_on_subsequences.problem8;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TargetSum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution1a solution = new Solution1a();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Example 1: nums = [1, 2, 7, 1, 5], target = 4 -> 2 ways
        assertEquals(2, solution.targetSum(5, 4, new int[]{1, 2, 7, 1, 5}), "Example 1: 2 ways to reach 4");

        // Example 2: nums = [1], target = 1 -> 1 way
        assertEquals(1, solution.targetSum(1, 1, new int[]{1}), "Example 2: 1 way to reach 1");

        // Example 3: nums = [2, 1, 3, 1, 2], target = 2 -> 0 ways
        assertEquals(0, solution.targetSum(5, 2, new int[]{2, 1, 3, 1, 2}), "Example 3: 3 ways to reach 2");

        // Edge: nums = [1, 1], target = 0 -> 2 ways (+1-1, -1+1)
        assertEquals(2, solution.targetSum(2, 0, new int[]{1, 1}), "Edge: 2 ways to reach 0");

        // Edge: nums = [1, 2, 3], target = 7 -> 0 ways (not possible)
        assertEquals(0, solution.targetSum(3, 7, new int[]{1, 2, 3}), "Edge: 0 ways to reach 7");

        assertEquals(2, solution.targetSum(4, 3, new int[]{1, 2, 3, 1}), "Edge: 0 ways to reach 7");
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack
 */
class Solution {
    private static final int MAX = (int) 1e9 + 7;

    public int targetSum(int n, int target, int[] nums) {
        return targetSum(nums, 0, target);
    }

    private int targetSum(int[] nums, int i, int target) {
        if (i == nums.length) {
            return target == 0 ? 1 : 0;
        }

        int positive = targetSum(nums, i + 1, target + nums[i]);
        int negative = targetSum(nums, i + 1, target - nums[i]);

        return (positive + negative) % MAX;
    }
}

/*
Step 1 - Better recursion

T - O(n*target)
S - O(n) - stack

If we closely examine the question,
[1,2,3,1] and target 3
1) -1 2 3 -1
2) 1 -2 3 1

2 3 | -1 -1
1 3 1 | -2

We're looking for two partitions and the abs diff of them will be our target

So this is same as count with target diff between two partitions subsets

So if target is 3, sum is 7, we're looking for a subset whose sum equals (7-3) / 2
If sum-diff is odd, we can't find any solution

If sum-target < 0, then also we can't find any solution

sum is 20, so max possible target is -20 to 20
So 20-(-20) = 40;  20-20=0, 0 to 40

 */
class Solution1a {
    private static final int MAX = (int) 1e9 + 7;

    public int targetSum(int n, int target, int[] nums) {
        int S = 0;
        for (int num : nums) {
            S += num;
        }
        if (S - target < 0) { // Impossible to achieve
            return 0;
        }
        if ((S - target & 1) == 1) { // We can't find equals sum partition
            return 0;
        }

        return countPartitionsWithSumK(nums, 0, (S - target) / 2);
    }

    private int countPartitionsWithSumK(int[] nums, int i, int K) {
        if (i == nums.length - 1) {
            return K == 0 || K == nums[i] ? 1 : 0; // take or skip last element
        }

        int withoutCurrent = countPartitionsWithSumK(nums, i + 1, K);
        int withCurrent = 0;
        if (K >= nums[i]) {
            withCurrent = countPartitionsWithSumK(nums, i + 1, K - nums[i]);
        }

        return (withCurrent + withoutCurrent) % MAX;
    }
}

/*
Step 2 - Memoization

 */
class Solution2 {
    private static final int MAX = (int) 1e9 + 7;

    public int targetSum(int n, int target, int[] nums) {
        int S = 0;
        for (int num : nums) {
            S += num;
        }
        if (S - target < 0) { // Impossible to achieve
            return 0;
        }
        if ((S - target & 1) == 1) { // We can't find equals sum partition
            return 0;
        }

        int S1 = (S - target) / 2;
        int[][] dp = new int[n][S1 + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return countPartitionsWithSumK(nums, 0, S1, dp);
    }

    private int countPartitionsWithSumK(int[] nums, int i, int K, int[][] dp) {
        if (i == nums.length - 1) {
            return K == 0 || K == nums[i] ? 1 : 0; // take or skip last element
        }

        if (dp[i][K] != -1) {
            return dp[i][K];
        }
        int withoutCurrent = countPartitionsWithSumK(nums, i + 1, K, dp);
        int withCurrent = 0;
        if (K >= nums[i]) {
            withCurrent = countPartitionsWithSumK(nums, i + 1, K - nums[i], dp);
        }

        int result = (withCurrent + withoutCurrent) % MAX;
        dp[i][K] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions:
At pos 0, when expected target is 0 or nums[0] itself, we can take it

 */
class Solution3 {
    private static final int MAX = (int) 1e9 + 7;

    public int targetSum(int n, int target, int[] nums) {
        int S = 0;
        for (int num : nums) {
            S += num;
        }
        if (S - target < 0) { // Impossible to achieve
            return 0;
        }
        if ((S - target & 1) == 1) { // We can't find equals sum partition
            return 0;
        }

        int S1 = (S - target) / 2;
        return countSubsetsWithSumK(n, nums, S1);
    }

    private static int countSubsetsWithSumK(int n, int[] nums, int target) {
        int[][] dp = new int[n][target + 1];

        // Known solutions
        dp[0][0] = 1;
        if (nums[0] <= target) {
            dp[0][nums[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int withoutCurrent = dp[i - 1][j];
                int withCurrent = 0;
                if (j >= nums[i]) {
                    withCurrent = dp[i - 1][j - nums[i]];
                }

                dp[i][j] = (withCurrent + withoutCurrent) % MAX;
            }
        }

        return dp[n - 1][target];
    }
}

/*
Step 4 - Space Optimization

T - O(n*target)
S - O(target)

 */
class Solution4 {
    private static final int MAX = (int) 1e9 + 7;

    public int targetSum(int n, int target, int[] nums) {
        int S = 0;
        for (int num : nums) {
            S += num;
        }

        if (S - target < 0) { // Impossible to achieve
            return 0;
        }
        if ((S - target & 1) == 1) { // We can't find equals sum partition
            return 0;
        }

        int S1 = (S - target) / 2;
        return countSubsetsWithSumK(n, nums, S1);
    }

    private static int countSubsetsWithSumK(int n, int[] nums, int target) {
        int[][] dp = new int[2][target + 1];

        // Known solutions
        dp[0][0] = 1;
        if (nums[0] <= target) {
            dp[0][nums[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int withoutCurrent = dp[0][j];
                int withCurrent = 0;
                if (j >= nums[i]) {
                    withCurrent = dp[0][j - nums[i]];
                }

                dp[1][j] = (withCurrent + withoutCurrent) % MAX;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, target + 1);
        }

        return dp[0][target];
    }
}