package io.abdul.dynamic_programming.dp_1d.problem9;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumSumNonAdjacent {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution =  new Solution4();
        // Test Case 1: Example input nums = [1, 2, 4]
        int[] nums1 = {1, 2, 4};
        assertEquals(5, solution.nonAdjacent(nums1), "Maximum sum for nums [1, 2, 4] should be 5");

        // Test Case 2: Example input nums = [2, 1, 4, 9]
        int[] nums2 = {2, 1, 4, 9};
        assertEquals(11, solution.nonAdjacent(nums2), "Maximum sum for nums [2, 1, 4, 9] should be 11");

        // Test Case 3: Example input nums = [1, 7, 16, 8]
        int[] nums3 = {1, 7, 16, 8};
        assertEquals(17, solution.nonAdjacent(nums3), "Maximum sum for nums [1, 7, 16, 8] should be 17");

        // Test Case 4: Single element nums = [5]
        int[] nums4 = {5};
        assertEquals(5, solution.nonAdjacent(nums4), "Maximum sum for nums [5] should be 5");

        // Test Case 5: Two elements nums = [3, 7]
        int[] nums5 = {3, 7};
        assertEquals(7, solution.nonAdjacent(nums5), "Maximum sum for nums [3, 7] should be 7");

        // Test Case 6: Large input nums = [10, 20, 30, 40, 50]
        int[] nums6 = {10, 20, 30, 40, 50};
        assertEquals(90, solution.nonAdjacent(nums6), "Maximum sum for nums [10, 20, 30, 40, 50] should be 90");

        // Test Case 7: Edge case nums = []
        int[] nums7 = {1};
        assertEquals(1, solution.nonAdjacent(nums7), "Maximum sum for empty nums array should be 0");

        // Test Case 8: Edge case nums = [0, 0, 0]
        int[] nums8 = {0, 0, 0};
        assertEquals(0, solution.nonAdjacent(nums8), "Maximum sum for nums [0, 0, 0] should be 0");

    }
}

/*
Step 1: Top-down recursive solution

T - O(2^n)
S - O(n) -> stack

1 2 3 4
Calc max sum by taking 1 then 3
taking 3 and stop
skip 3 and take 4

skip 1 and take 2
take 4 and stop
 */
class Solution {
    public int nonAdjacent(int[] nums) {
        return maximumSum(nums, 0);
    }

    public int maximumSum(int[] nums, int pos) {
        if (pos == nums.length - 1) { // Max sum at last element is the last element only. If we skip, value will only reduce.
            return nums[pos];
        }

        if (pos >= nums.length) { // No options to try
            return 0;
        }

        // Take pos
        int withPos = nums[pos] + maximumSum(nums, pos + 2); // current num + maxSum by taking next to adjacent

        // Skip pos
        int withoutPos = maximumSum(nums, pos + 1);

        return Math.max(withPos, withoutPos);
    }
}

/*
Step 2: Memoization

T - O(n)
S - O(n) -> stack + dp

1 2 3 4 5
For 1, 3,4,5 are options
For 2, 4,5 are options
For 3 5 is option

So maxSum for 5 is calc thrice, for 4 cal twice, 3 calc twice

So first 5 is calc and stored
then 3
then 4
then 1
then 2
 */
class Solution2 {
    public int nonAdjacent(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return maximumSum(nums, 0, dp);
    }

    public int maximumSum(int[] nums, int pos, int[] dp) {
        if (pos >= nums.length) { // No options to try
            return 0;
        }

        if (dp[pos] != -1) {
            return dp[pos];
        }

        if (pos == nums.length - 1) { // Max sum at last element is the last element only. If we skip, value will only reduce.
            dp[pos] = nums[pos];
            return nums[pos];
        }

        // Take pos
        int withPos = nums[pos] + maximumSum(nums, pos + 2, dp); // current num + maxSum by taking next to adjacent

        // Skip pos
        int withoutPos = maximumSum(nums, pos + 1, dp);

        int max = Math.max(withPos, withoutPos);
        dp[pos] = max;
        return max;
    }
}

/*
Step 3: Bottom-up iterative solution

1 2 3 4 5
Known solution
Max when 5 is considered -> 5

Now,
Max when 4,5 exists, Max (4 + max_at_x, max_at_5) = Max (4, 5) = 5
Max when 3,4,5 exists, Max (3 + max_at_5, max_at_4) = Max (8, 5) = 8
Max when 2,3,4,5 exists, Max (2 + max_at_4, max_at_3) = Max(7, 8) = 8
Max when 1,2,3,4,5 exists, Max (1 + max_at_3, max_at_2) = Max(9, 8) = 9
 */
class Solution3 {
    public int nonAdjacent(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length];
        // Known solution; just 2 element exists, smallest sub-problem
        dp[nums.length - 1] = nums[nums.length - 1];
        dp[nums.length - 2] = Math.max(nums[nums.length - 1], nums[nums.length - 2]);

        for (int i = nums.length - 3; i >= 0; i--) {
            dp[i] = Math.max(nums[i] + dp[i + 2], dp[i + 1]);
        }

        return dp[0];
    }
}

/*
Step 4: Space optimization

We only need n+1 and n+2 pos to calc any
 */
class Solution4 {
    public int nonAdjacent(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[2];
        // Known solution; just 2 element exists, smallest sub-problem
        dp[0] = nums[nums.length - 1];
        dp[1] = Math.max(nums[nums.length - 1], nums[nums.length - 2]);

        for (int i = nums.length - 3; i >= 0; i--) {
            int max = Math.max(nums[i] + dp[0], dp[1]);
            dp[0] = dp[1];
            dp[1] = max;
        }

        return dp[1];
    }
}