package io.abdul.dynamic_programming.dp_on_subsequences.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartitionSubsetSumII {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();
//         Test Case 1: Example input with minimum absolute difference of 1
        int[] arr1 = {1, 7, 14, 5};
        assertEquals(1, solution.minDifference(arr1, arr1.length), "Minimum absolute difference for [1, 7, 14, 5] should be 1");

        // Test Case 2: Example input with minimum absolute difference of 0
        int[] arr2 = {3, 1, 6, 2, 2};
        assertEquals(0, solution.minDifference(arr2, arr2.length), "Minimum absolute difference for [3, 1, 6, 2, 2] should be 0");

        // Test Case 3: Example input with minimum absolute difference of 3
        int[] arr3 = {2, 2, 2, 9};
        assertEquals(3, solution.minDifference(arr3, arr3.length), "Minimum absolute difference for [2, 2, 2, 9] should be 3");

        // Test Case 4: Edge case with single element
        int[] arr4 = {10};
        assertEquals(10, solution.minDifference(arr4, arr4.length), "Minimum absolute difference for [10] should be 10");

        // Test Case 5: Edge case with all elements equal
        int[] arr5 = {4, 4, 4, 4};
        assertEquals(0, solution.minDifference(arr5, arr5.length), "Minimum absolute difference for [4, 4, 4, 4] should be 0");

        // Test Case 6: Large input with increasing sequence
        int[] arr6 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertEquals(1, solution.minDifference(arr6, arr6.length), "Minimum absolute difference for [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] should be 1");

        // Test Case 7: Large input with random values
        int[] arr7 = {15, 5, 10, 20, 25};
        assertEquals(5, solution.minDifference(arr7, arr7.length), "Minimum absolute difference for [15, 5, 10, 20, 25] should be 5");

    }
}

/*
Step 1 - Top-down Recursive solution

T - O(sum/2 * 2^n)
S - O(n) - stack

When we get two subsets with equal sum, diff is 0 and that's the best option
if not sum/2 -1
if not sum/2 -2
..
if not 1
if not 0 (Bcz 0 also allowed in values)

 */
class Solution {
    public int minDifference(int[] arr, int n) {

        if (arr.length == 1) {
            return arr[0];
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int half = sum / 2;
        for (int i = half; i >= 0; i--) {
            if (subsetSumEqualsTarget(arr, 0, i)) {
                return sum - i - i;
            }
        }

        return -1; // Dummy
    }

    private boolean subsetSumEqualsTarget(int[] arr, int i, int target) {

        if (i == arr.length) {
            return target == 0;
        }

        boolean withoutCurrent = subsetSumEqualsTarget(arr, i + 1, target);
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = subsetSumEqualsTarget(arr, i + 1, target - arr[i]);
        }

        return withCurrent || withoutCurrent;
    }
}

/*
Step 2 - Memoization

T - O(sum/2 * n*sum/2)
S - O(n*sum/2) - dp + stack
 */
class Solution2 {
    public int minDifference(int[] arr, int n) {

        if (arr.length == 1) {
            return arr[0];
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int half = sum / 2;
        int[][] dp = new int[n][half + 1];
        for (int i = half; i >= 0; i--) {
            for (int[] ints : dp) {
                Arrays.fill(ints, -1);
            }
            if (subsetSumEqualsTarget(arr, 0, i, dp)) {
                return sum - i - i;
            }
        }

        return -1; // Dummy
    }

    private boolean subsetSumEqualsTarget(int[] arr, int i, int target, int[][] dp) {
        if (i == arr.length) {
            return target == 0;
        }

        if (dp[i][target] != -1) {
            return dp[i][target] == 1;
        }

        boolean withoutCurrent = subsetSumEqualsTarget(arr, i + 1, target, dp);
        boolean withCurrent = false;
        if (!withoutCurrent && target >= arr[i]) {
            withCurrent = subsetSumEqualsTarget(arr, i + 1, target - arr[i], dp);
        }

        boolean result = withCurrent || withoutCurrent;
        dp[i][target] = result ? 1 : 0;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*sum/2)
S - O(n*sum/2)
 */
class Solution3 {
    public int minDifference(int[] arr, int n) {
        if (arr.length == 1) {
            return arr[0];
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int half = sum / 2;
        boolean[][] dp = new boolean[n][half + 1];
//        for (int i = half; i >= 0; i--) {
//            for (boolean[] ints : dp) {
//                Arrays.fill(ints, false);
//            }
//            if (subsetSumEqualsTarget(arr, i, dp)) {
//                return sum - i - i;
//            }
//        }

        // NOTE: Unlike recursive approach, when we look for target, we fill in all possible targets from 0 to target. So a single iteration is enough
        // After single iteration, DP will contain true/false to say whether a subset with that sum exists or not
        for (boolean[] ints : dp) {
            Arrays.fill(ints, false);
        }
        subsetSumEqualsTarget(arr, half, dp);

        for (int i = dp[n - 1].length - 1; i >= 0; i--) {
            if (dp[n - 1][i]) {
                return sum - i - i;
            }
        }

        return -1; // Dummy
    }

    private boolean subsetSumEqualsTarget(int[] arr, int target, boolean[][] dp) {
        // Known solutions
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[i - 1][j];

                boolean withCurrent = false;
                if (j >= arr[i]) {
                    withCurrent = dp[i - 1][j - arr[i]];
                }

                dp[i][j] = withCurrent || withoutCurrent;
            }
        }

        return dp[arr.length - 1][target];
    }
}

/*
Step 4 - Space Optimization

T - O(n*sum/2)
S - O(sum/2)
 */
class Solution4 {
    public int minDifference(int[] arr, int n) {
        if (arr.length == 1) {
            return arr[0];
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int half = sum / 2;
        boolean[][] dp = new boolean[2][half + 1];
//        for (int i = half; i >= 0; i--) {
//            for (boolean[] ints : dp) {
//                Arrays.fill(ints, false);
//            }
//            if (subsetSumEqualsTarget(arr, i, dp)) {
//                return sum - i - i;
//            }
//        }


        for (boolean[] ints : dp) {
            Arrays.fill(ints, false);
        }
        subsetSumEqualsTarget(arr, half, dp);

        for (int i = dp[0].length - 1; i >= 0; i--) {
            if (dp[0][i]) {
                return sum - i - i;
            }
        }

        return -1; // Dummy
    }

    private boolean subsetSumEqualsTarget(int[] arr, int target, boolean[][] dp) {
        // Known solutions
        dp[0][0] = true;
        if (arr[0] <= target) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                boolean withoutCurrent = dp[0][j];

                boolean withCurrent = false;
                if (j >= arr[i]) {
                    withCurrent = dp[0][j - arr[i]];
                }

                dp[1][j] = withCurrent || withoutCurrent;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, target + 1);
        }

        return dp[0][target];
    }
}
