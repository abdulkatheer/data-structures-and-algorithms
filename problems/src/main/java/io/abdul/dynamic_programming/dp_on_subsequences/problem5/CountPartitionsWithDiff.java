package io.abdul.dynamic_programming.dp_on_subsequences.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Similar to count partitions with sum K, but here diff is given.
If sum of arr is S, and diff is D. There is only one possibility that the arr could be split into two subsets.

S - total sum
d - diff
S-d = sum of two equal subsets
If S-d is odd, we can't split into two equals subsets
If S-d is even, (S-d)/2 is S2

2x + diff = S
2x = S - diff
2x = Y
x = Y/2; if Y is odd we can't split
 */
public class CountPartitionsWithDiff {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Example 1
        int[] arr1 = {1, 1, 2, 3};
        int diff1 = 1;
        assertEquals(3, solution.countPartitions(arr1.length, diff1, arr1));

        // Example 2
        int[] arr2 = {1, 2, 3, 4};
        int diff2 = 2;
        assertEquals(2, solution.countPartitions(arr2.length, diff2, arr2));

        // Example 3
        int[] arr3 = {5, 2, 6, 4};
        int diff3 = 3;
        assertEquals(1, solution.countPartitions(arr3.length, diff3, arr3));

        // All zeros
        int[] arr4 = {0, 0, 0, 0};
        int diff4 = 0;
        assertEquals(16, solution.countPartitions(arr4.length, diff4, arr4));

        // No possible partition
        int[] arr5 = {1, 2, 5};
        int diff5 = 10;
        assertEquals(0, solution.countPartitions(arr5.length, diff5, arr5));

        int[] arr6 = {3, 18};
        int diff6 = 20;
        assertEquals(0, solution.countPartitions(arr6.length, diff6, arr6));

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n)

 */
class Solution {
    private final static int MAX = 1_000_000_007;

    public int countPartitions(int n, int diff, int[] arr) {
        int S = 0;
        for (int num : arr) {
            S += num;
        }

        if (diff > S) { // Edge case
            return 0;
        }

        if ((S - diff & 1) == 1) {
            return 0;
        }

        int target = (S - diff) / 2;
        return countSubsetSumK(arr, 0, target);
    }

    private int countSubsetSumK(int[] arr, int i, int k) {
        if (i == arr.length) { // Go till end every time. This is how we generally find all subsets. If we exit early, we may miss all combinations.
            return k == 0 ? 1 : 0;
        }

        int withoutCurrent = countSubsetSumK(arr, i + 1, k);
        int withCurrent = 0;
        if (k >= arr[i]) {
            withCurrent = countSubsetSumK(arr, i + 1, k - arr[i]);
        }
        return (withCurrent + withoutCurrent) % MAX;
    }
}

/*
Step 2 - Memoization

T - O(n*target)
S - O(n*target) - dp + stack

 */
class Solution2 {
    private final static int MAX = 1_000_000_007;

    public int countPartitions(int n, int diff, int[] arr) {
        int S = 0;
        for (int num : arr) {
            S += num;
        }

        if (diff > S) { // Edge case
            return 0;
        }

        if ((S - diff & 1) == 1) {
            return 0;
        }

        int target = (S - diff) / 2;
        int[][] dp = new int[n][target + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return countSubsetSumK(arr, 0, target, dp);
    }

    private int countSubsetSumK(int[] arr, int i, int k, int[][] dp) {
        if (i == arr.length) { // Go till end every time. This is how we generally find all subsets. If we exit early, we may miss all combinations.
            return k == 0 ? 1 : 0;
        }

        if (dp[i][k] != -1) {
            return dp[i][k];
        }

        int withoutCurrent = countSubsetSumK(arr, i + 1, k, dp);
        int withCurrent = 0;
        if (k >= arr[i]) {
            withCurrent = countSubsetSumK(arr, i + 1, k - arr[i], dp);
        }
        int result = (withCurrent + withoutCurrent) % MAX;
        dp[i][k] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*target)
S - O(n*target) - dp

Known solutions
At pos 0, when target is 0 or when target is arr[0] itself, we've a solution

Recursive solutions
At pos 1, we fill table for all possible targets from 0 to target
dp[1][t] = d[i-1][t] + (if t>=arr[1]) dp[i-1][t-arr[1]]
 */
class Solution3 {
    private final static int MAX = 1_000_000_007;

    public int countPartitions(int n, int diff, int[] arr) {
        int S = 0;
        for (int num : arr) {
            S += num;
        }

        if (diff > S) { // Edge case
            return 0;
        }

        if ((S - diff & 1) == 1) {
            return 0;
        }

        int target = (S - diff) / 2;
        int[][] dp = new int[n][target + 1];
        // Known solutions
        dp[0][0] = 1;
        if (arr[0] <= target) {
            dp[0][arr[0]]++; // for zeros, same number repeats
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int withoutCurrent = dp[i - 1][j];
                int withCurrent = 0;
                if (j >= arr[i]) {
                    withCurrent = dp[i - 1][j - arr[i]];
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
    private final static int MAX = 1_000_000_007;

    public int countPartitions(int n, int diff, int[] arr) {
        int S = 0;
        for (int num : arr) {
            S += num;
        }

        if (diff > S) { // Edge case
            return 0;
        }

        if ((S - diff & 1) == 1) {
            return 0;
        }

        int target = (S - diff) / 2;
        int[][] dp = new int[2][target + 1];
        // Known solutions
        dp[0][0] = 1;
        if (arr[0] <= target) {
            dp[0][arr[0]]++; // for zeros, same number repeats
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                int withoutCurrent = dp[0][j];
                int withCurrent = 0;
                if (j >= arr[i]) {
                    withCurrent = dp[0][j - arr[i]];
                }

                dp[1][j] = (withCurrent + withoutCurrent) % MAX;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, target + 1);
            Arrays.fill(dp[1], 0); // As we add to existing values, we clean up the table for fresh calc
        }

        return dp[0][target];
    }
}