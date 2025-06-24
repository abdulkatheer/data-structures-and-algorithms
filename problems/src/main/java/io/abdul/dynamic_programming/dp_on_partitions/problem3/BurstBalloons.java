package io.abdul.dynamic_programming.dp_on_partitions.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BurstBalloons {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Example 1
        assertEquals(167, solution.maxCoins(new int[]{3, 1, 5, 8}),
                "The maximum coins for [3, 1, 5, 8] should be 167");

        // Example 2
        assertEquals(40, solution.maxCoins(new int[]{1, 2, 3, 4}),
                "The maximum coins for [1, 2, 3, 4] should be 40");

        // Example 3
        assertEquals(10, solution.maxCoins(new int[]{1, 5}),
                "The maximum coins for [1, 5] should be 10");

        // Single balloon
        assertEquals(7, solution.maxCoins(new int[]{7}),
                "The maximum coins for [7] should be 7");

        // All ones
        assertEquals(4, solution.maxCoins(new int[]{1, 1, 1, 1}),
                "The maximum coins for [1, 1, 1, 1] should be 4");

        // Increasing order
        assertEquals(110, solution.maxCoins(new int[]{1, 2, 3, 4, 5}),
                "The maximum coins for [1, 2, 3, 4, 5] should be 110");

        // Decreasing order
        assertEquals(110, solution.maxCoins(new int[]{5, 4, 3, 2, 1}),
                "The maximum coins for [5, 4, 3, 2, 1] should be 110");

    }
}

/*
Step 1 - Top-down recursive solution

First instinct - Choose first balloon to be burst. Here, for the further recursive calls, we need to remember which one is burst, so that we can skip it for calculation.
So additional DS which supports constant time insert and delete is needed. Complex!
Deep instinct - Choose the last balloon to be burst. Here for sure we known last ballon has to be multiplied with default 1 and 1
then based on the given index

1 3 5 8
i=1, i=4, the last balloon to be burst
_1_ _3_ _5_ _8_

Option (1)
_1_ -> the second last balloon can be 3, 5, or 8
i=2, j=4, prev element is always 1 and next element is always default (1) and only k changes (3, 5, or 8)
How does't look at the second last position - 1 3 _ _ | 1 _ 5 _ | 1 _ _ 8

Option (2)
_3_ -> the second last balloon can be 1, 5, or 8
i=1 j=1, prev element is always default (1) and next element will always be 3
1 3 _ _

i=3 j=4, prev element is always 3 and next element is always default (1)
_ 3 5 _ | _ 3 _ 8

Option (3)
_5_ -> the second last balloon can be 1, 3, or 8
i=1 j=2, prev element is always default (1) and next element will always be 5
1 _ 5 _ | _ 3 5 _

i=4 j=4, prev element is always 5 and next element is always default (1)
_ _ 5 8

Option (4)
_8_ -> the second last balloon can be 1, 3, or 5
i=1 j=3, prev element is always default (1) and next element will always be 8
1 _ _ 8 | _ 3 _ 8 | _ _ 5 8
 */
class Solution {
    public int maxCoins(int[] nums) {
        int b = nums.length;
        int[] numsArr = new int[b + 2];
        numsArr[0] = 1;
        numsArr[numsArr.length - 1] = 1;
        System.arraycopy(nums, 0, numsArr, 1, b);
        return maxCoins(numsArr, 1, b);
    }

    private int maxCoins(int[] nums, int i, int j) {
        if (i > j) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int coinsToBurst = nums[i - 1] * nums[j + 1];
        for (int k = i; k <= j; k++) {
            max = Math.max(max, (coinsToBurst * nums[k]) + maxCoins(nums, i, k - 1) + maxCoins(nums, k + 1, j));
        }

        return max;
    }
}

/*
Step 2 - Memoization

 */
class Solution2 {
    public int maxCoins(int[] nums) {
        int b = nums.length;
        int[] numsArr = new int[b + 2];
        numsArr[0] = 1;
        numsArr[numsArr.length - 1] = 1;
        System.arraycopy(nums, 0, numsArr, 1, b);
        int[][] dp = new int[b + 1][b + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return maxCoins(numsArr, 1, b, dp);
    }

    private int maxCoins(int[] nums, int i, int j, int[][] dp) {
        if (i > j) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int max = Integer.MIN_VALUE;
        int coinsToBurst = nums[i - 1] * nums[j + 1];
        for (int k = i; k <= j; k++) {
            max = Math.max(max, (coinsToBurst * nums[k]) + maxCoins(nums, i, k - 1, dp) + maxCoins(nums, k + 1, j, dp));
        }

        dp[i][j] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solution
when i>j, result is 0
 */
class Solution3 {
    public int maxCoins(int[] nums) {
        int b = nums.length;
        int[] numsArr = new int[b + 2];
        numsArr[0] = 1;
        numsArr[numsArr.length - 1] = 1;
        System.arraycopy(nums, 0, numsArr, 1, b);
        int[][] dp = new int[b + 1][b + 1];
        // dp[i][j] stores max coins to burst balloons from i to j
        /*
        Assume b = 4 (1 to 4)
        dp[4][4] stores max coins to burst 4th
        dp[3][3] burst 3rd
        dp[3][4] burst 3rd and 4th
        dp[2][2] burst 2nd
        dp[2][3] 2nd and 3rd
        dp[2][4] 2nd, 3rd and 4th
        dp[1][4] 1st, 2nd, 3rd and 4th

         */

        for (int i = b; i >= 1; i--) {
            for (int j = 1; j <= b; j++) {
                if (i > j) { // base case
                    continue;
                }

                int max = Integer.MIN_VALUE;
                int coinsToBurst = numsArr[i - 1] * numsArr[j + 1];
                for (int k = i; k <= j; k++) {
                    int leftHalf = k - 1 < 0 ? 0 : dp[i][k - 1];
                    int rightHalf = k + 1 > b ? 0 : dp[k + 1][j];
                    max = Math.max(max, (coinsToBurst * numsArr[k]) + leftHalf + rightHalf);
                }
                dp[i][j] = max;
            }
        }

        return dp[1][b];
    }
}

/*
Step 4 - Space Optimization
Can't be done as result depends on entire dp table
 */