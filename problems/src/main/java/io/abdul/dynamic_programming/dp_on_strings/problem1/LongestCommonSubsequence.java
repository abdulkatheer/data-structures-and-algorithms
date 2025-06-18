package io.abdul.dynamic_programming.dp_on_strings.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestCommonSubsequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution3a solution = new Solution3a();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input with LCS length 3
        String str1 = "bdefg";
        String str2 = "bfg";
        assertEquals(3, solution.lcs(str1, str2),
                "The LCS of \"bdefg\" and \"bfg\" should be 3");

        // Test Case 2: Example input with LCS length 2
        str1 = "mnop";
        str2 = "mnq";
        assertEquals(2, solution.lcs(str1, str2),
                "The LCS of \"mnop\" and \"mnq\" should be 2");

        // Test Case 3: Example input with LCS length 2
        str1 = "abc";
        str2 = "dafb";
        assertEquals(2, solution.lcs(str1, str2),
                "The LCS of \"abc\" and \"dafb\" should be 2");

        // Test Case 5: Edge case with no common subsequence
        str1 = "xyz";
        str2 = "abc";
        assertEquals(0, solution.lcs(str1, str2),
                "The LCS of \"xyz\" and \"abc\" should be 0");

        // Test Case 6: Edge case with identical strings
        str1 = "abcdef";
        str2 = "abcdef";
        assertEquals(6, solution.lcs(str1, str2),
                "The LCS of identical strings \"abcdef\" and \"abcdef\" should be 6");

        // Test Case 7: Large input with alternating characters
        str1 = "abcdefghijklmnopqrstuvwxyz";
        str2 = "acegikmoqsuwy";
        assertEquals(13, solution.lcs(str1, str2),
                "The LCS of \"abcdefghijklmnopqrstuvwxyz\" and \"acegikmoqsuwy\" should be 13");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(3^(m+n))
S - O(n)

At each pos of i and j, if both match, add 1 and proceed to next
If don't match, try i and j+1, i+1 and j, i+1 and j+1

Return max of all

If i or j crossed their lengths, stop
 */
class Solution {
    public int lcs(String str1, String str2) {
        return lcs(str1, str2, 0, 0);
    }

    public int lcs(String str1, String str2, int i1, int i2) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

//        int taken = 0;
//        if (str1.charAt(i1) == str2.charAt(i2)) {
//            taken = 1 + lcs(str1, str2, i1 + 1, i2 + 1);
//        }
//        int skipped = Math.max(lcs(str1, str2, i1, i2 + 1), lcs(str1, str2, i1 + 1, i2));
//
//        return Math.max(taken, skipped);

        if (str1.charAt(i1) == str2.charAt(i2)) {
            return 1 + lcs(str1, str2, i1 + 1, i2 + 1); // skip can never have higher value than this. So no point in calculating them.
        } else {
            return Math.max(lcs(str1, str2, i1, i2 + 1), lcs(str1, str2, i1 + 1, i2));
        }
    }
}

/*
Step 2 - Memoization

T - O(n*m)
S - O(n*m) stack + dp
 */
class Solution2 {
    public int lcs(String str1, String str2) {
        int[][] dp = new int[str1.length()][str2.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return lcs(str1, str2, 0, 0, dp);
    }

    public int lcs(String str1, String str2, int i1, int i2, int[][] dp) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

        if (dp[i1][i2] != -1) {
            return dp[i1][i2];
        }

//        int taken = 0;
//        if (str1.charAt(i1) == str2.charAt(i2)) {
//            taken = 1 + lcs(str1, str2, i1 + 1, i2 + 1, dp);
//        }
//        int skipped = Math.max(lcs(str1, str2, i1, i2 + 1, dp), lcs(str1, str2, i1 + 1, i2, dp));
//        dp[i1][i2] = Math.max(taken, skipped);
//        return dp[i1][i2];

        int result;
        if (str1.charAt(i1) == str2.charAt(i2)) {
            result = 1 + lcs(str1, str2, i1 + 1, i2 + 1, dp); // skip can never have higher value than this. So no point in calculating them.
        } else {
            result = Math.max(lcs(str1, str2, i1, i2 + 1, dp), lcs(str1, str2, i1 + 1, i2, dp));
        }

        dp[i1][i2] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution [Index shifting to match base case]

T - O(n*m)
S - O(n*m)

As the base case is checking n and m, we need a dp[n+1][m+1]
and we'll 1-based indexing
 */
class Solution3 {
    public int lcs(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] stores the LCS length considering str1 of length i (from start) and str2 of length j (from start)
        // dp[n][m] stores the total LCS length considering str1 and str2 of full length

        // Known solutions
        // dp[i][0] = 0
        // dp[0][j] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
//                int taken = 0;
//                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
//                    taken = 1 + dp[i - 1][j - 1];
//                }
//                int skipped = Math.max(dp[i][j - 1], dp[i - 1][j]);
//                dp[i][j] = Math.max(taken, skipped);
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[n][m];
    }
}

/*
Known solutions:
When str1 has only one char and str2 has 1 or more
When str2 has only one char and str1 has 1 or more
 */
class Solution3a {
    public int lcs(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n][m];

        // Known solutions
        boolean firstCharMatched = str1.charAt(0) == str2.charAt(0);
        dp[0][0] = firstCharMatched ? 1 : 0;
        for (int i = 1; i < m; i++) {
            if (str1.charAt(0) == str2.charAt(i)) {
                dp[0][i] = 1;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }
        for (int i = 1; i < n; i++) {
            if (str2.charAt(0) == str1.charAt(i)) {
                dp[i][0] = 1;
            } else {
                dp[i][0] = dp[i - 1][0];
            }
        }


        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {

                int taken = 0;
                if (str1.charAt(i) == str2.charAt(j)) {
                    taken = 1 + dp[i - 1][j - 1];
                }
                int skipped = Math.max(dp[i][j - 1], dp[i - 1][j]);
                dp[i][j] = Math.max(taken, skipped);
            }
        }

        return dp[n - 1][m - 1];
    }
}

/*
Step 4 - Space Optimization

T - O(n*m)
S - O(m)

 */
class Solution4 {
    public int lcs(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[2][m + 1];

        // Known solutions
        // dp[i][0] = 0
        // dp[0][j] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[1][j] = 1 + dp[0][j - 1];
                } else {
                    dp[1][j] = Math.max(dp[1][j - 1], dp[0][j]);
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m + 1);
        }

        return dp[0][m];
    }
}