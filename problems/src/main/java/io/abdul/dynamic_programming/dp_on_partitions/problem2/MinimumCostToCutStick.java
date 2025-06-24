package io.abdul.dynamic_programming.dp_on_partitions.problem2;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumCostToCutStick {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: Example input with multiple cuts
        int n1 = 7;
        assertEquals(16, solution.minCost(n1, Arrays.asList(1, 3, 4, 5)),
                "The minimum cost to cut the stick of length 7 with cuts [1, 3, 4, 5] should be 16");

        // Test Case 2: Example input with fewer cuts
        int n2 = 7;
        assertEquals(14, solution.minCost(n2, Arrays.asList(1, 3, 6)),
                "The minimum cost to cut the stick of length 7 with cuts [1, 3, 6] should be 14");

        // Test Case 4: Edge case with one cut
        int n4 = 8;
        assertEquals(8, solution.minCost(n4, List.of(4)),
                "The minimum cost to cut the stick of length 8 with one cut at position 4 should be 8");

        // Test Case 5: Large input with multiple cuts
        int n5 = 20;
        assertEquals(47, solution.minCost(n5, Arrays.asList(2, 8, 10, 15)),
                "The minimum cost to cut the stick of length 20 with cuts [2, 8, 10, 15] should be 42");

        // Test Case 6: Edge case with cuts at consecutive positions
        int n6 = 10;
        assertEquals(18, solution.minCost(n6, Arrays.asList(1, 2, 3, 4)),
                "The minimum cost to cut the stick of length 10 with cuts [1, 2, 3, 4] should be 20");

        // Test Case 7: Edge case with cuts at extreme positions
        int n7 = 15;
        assertEquals(29, solution.minCost(n7, Arrays.asList(1, 14)),
                "The minimum cost to cut the stick of length 15 with cuts [1, 14] should be 15");

        int n8 = 6;
        assertEquals(12, solution.minCost(n8, Arrays.asList(1, 2, 5)));

    }
}

/*
Step 1 - Top-down recursive solution

n=7, cuts=1 3 4 5
We've 4! arrangements of cuts and we need to take the min of all.
1st cut can be at 1 or 3 or 4 or 5 and all will just cost 7 (7-0)
Let's say we cut at 1, we left with 3 4 5
2nd cut can be at 3 or 4 or 5 and all will cost 6 (7-1)
Let's say we cut at 3, we left with 4 5
3rd cut can be cut at 4 or 5, and all will cost  4 (7-3)
Let's say we cut at 4, we left with 5
4th cut can be cut at 5, and will cost 3 (7-4)
Total cost = 20

NOTE: if cuts are not in ascending order, then when we make a partition, the smaller number may go in other half. So sort it.

1 4 2 5
1 or 4 or 2 or 5 -> 7-0 = 7
cut at 3
1 4 | 5
Cut at 4 -> 7-2 = 5, but actually 7-4=3
So length finding works considering the cuts are in ascending order

 */
class Solution {
    public int minCost(int n, List<Integer> cuts) {
        int[] cutsArr = new int[cuts.size() + 2];
        cutsArr[0] = 0;
        cutsArr[cutsArr.length - 1] = n;
        System.arraycopy(cuts.stream().mapToInt(value -> value).toArray(), 0, cutsArr, 1, cuts.size());
        Arrays.sort(cutsArr);
        return minCost(n, cutsArr, 1, cuts.size());
    }

    private int minCost(int n, int[] cuts, int i, int j) {
        if (i > j) {
            return 0; // No cuts needed
        }

        int min = Integer.MAX_VALUE;
        int right = cuts[j + 1];
        int left = cuts[i - 1];
        int costToCut = right - left;
        // if i=1, j=4 we can cut at 1 or 2 or 3 or 4
        // if i=3, j=3 we can cut at 3 only, minCost(3,2) = 0, minCost(4,3) = 0
        for (int k = i; k <= j; k++) {
            min = Math.min(min, costToCut + minCost(n, cuts, i, k - 1) + minCost(n, cuts, k + 1, j));
        }

        return min;
    }
}

/*
Step 2 - Memoization

 */
class Solution2 {
    public int minCost(int n, List<Integer> cuts) {
        int m = cuts.size();
        int[] cutsArr = new int[m + 2];
        cutsArr[0] = 0;
        cutsArr[cutsArr.length - 1] = n;
        System.arraycopy(cuts.stream().mapToInt(value -> value).toArray(), 0, cutsArr, 1, m);
        Arrays.sort(cutsArr);
        int[][] dp = new int[m + 1][m + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return minCost(n, cutsArr, 1, m, dp);
    }

    private int minCost(int n, int[] cuts, int i, int j, int[][] dp) {
        if (i > j) {
            return 0; // No cuts needed
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int min = Integer.MAX_VALUE;
        int right = cuts[j + 1];
        int left = i - 1 < 0 ? 0 : cuts[i - 1];
        int costToCut = right - left;
        // if i=1, j=4 we can cut at 1 or 2 or 3 or 4
        // if i=3, j=3 we can cut at 3 only, minCost(3,2) = 0, minCost(4,3) = 0
        for (int k = i; k <= j; k++) {
            min = Math.min(min, costToCut + minCost(n, cuts, i, k - 1, dp) + minCost(n, cuts, k + 1, j, dp));
        }

        dp[i][j] = min;
        return min;
    }
}

/*
Step 3 - Bottom-up iterative solution

As is conversion from recursive solution!

Known solution
Incorrect i and j values gives 0
Handled within core loop

1 3 5 8, n=10
i=3 j=3 Min ( 10-5=5 , 0 , 0 )
i=2 j=2 Min ( 8-3=5
i=2 j=3 10-
 */
class Solution3 {
    public int minCost(int n, List<Integer> cuts) {
        int m = cuts.size();
        int[] cutsArr = new int[m + 2];
        cutsArr[0] = 0;
        cutsArr[cutsArr.length - 1] = n;
        System.arraycopy(cuts.stream().mapToInt(value -> value).toArray(), 0, cutsArr, 1, m);
        Arrays.sort(cutsArr);

        int[][] dp = new int[m + 1][m + 1];
        // dp[i][j] stores the minimum cost to cut the rod from i to j
        /*
        Assume cuts length = 4
        dp[3][3] = considering only one cut 3
        dp[2][2] = one cut 2
        dp[2][3] = two cust 2 and 3
        dp[1][1] = one cut 1
        dp[1][2] = two cuts 1 and 2
        dp[1][3] = three cuts 1, 2, and 3
        dp[0][0] = only one cut 0
        dp[0][1] = two cuts 0 and 1
        dp[0][2] = three cuts 0, 1 and 2
        dp[0][3] = four cuts 0, 1, 2, and 3
         */

        for (int i = m; i >= 1; i--) {
            for (int j = 1; j <= m; j++) {
                if (i > j) {
                    continue; // 0 is the default value in array
                }

                int right = cutsArr[j + 1];
                int left = cutsArr[i - 1];
                int costToCut = right - left;

                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    int costForFront = k - 1 < 0 ? 0 : dp[i][k - 1];
                    int costForBack = k + 1 > m ? 0 : dp[k + 1][j];
                    min = Math.min(min, costToCut + costForFront + costForBack);
                }
                dp[i][j] = min;
            }
        }

        return dp[1][m];
    }
}