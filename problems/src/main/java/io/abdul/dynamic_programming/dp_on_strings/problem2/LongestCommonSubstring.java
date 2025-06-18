package io.abdul.dynamic_programming.dp_on_strings.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestCommonSubstring {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();

        // Test Case 1: Example input with LCS length 2
        String str1 = "abcde";
        String str2 = "abfce";
        assertEquals(2, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"abcde\" and \"abfce\" should be 2");

        // Test Case 2: Example input with LCS length 4
        str1 = "abcdxyz";
        str2 = "xyzabcd";
        assertEquals(4, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"abcdxyz\" and \"xyzabcd\" should be 4");

        // Test Case 3: Example input with no common substring
        str1 = "abcdef";
        str2 = "ghijkl";
        assertEquals(0, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"abcdef\" and \"ghijkl\" should be 0");

        // Test Case 4: Edge case with one empty string
        str1 = "";
        str2 = "abc";
        assertEquals(0, solution.longestCommonSubstr(str1, str2),
                "The LCS of an empty string and \"abc\" should be 0");

        // Test Case 5: Edge case with identical strings
        str1 = "abcdef";
        str2 = "abcdef";
        assertEquals(6, solution.longestCommonSubstr(str1, str2),
                "The LCS of identical strings \"abcdef\" and \"abcdef\" should be 6");

        // Test Case 6: Edge case with single character match
        str1 = "a";
        str2 = "a";
        assertEquals(1, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"a\" and \"a\" should be 1");

        // Test Case 7: Large input with partial match
        str1 = "abcdefghij";
        str2 = "xyzabcde";
        assertEquals(5, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"abcdefghij\" and \"xyzabcde\" should be 4");

        // Test Case 8: Edge case with no overlapping characters
        str1 = "mnop";
        str2 = "qrst";
        assertEquals(0, solution.longestCommonSubstr(str1, str2),
                "The LCS of \"mnop\" and \"qrst\" should be 0");

        str1 = "viwsavfam";
        str2 = "gpptvromlyjnbuwoicrzjaiwkpoifq";
        assertEquals(2, solution.longestCommonSubstr(str1, str2));

        str1 = "viwsavfam";
        str2 = "iwkpoifq";
        assertEquals(2, solution.longestCommonSubstr(str1, str2));

        str1 = "ckbmtybeaomdcbqyypfrtpmgmfqxlmfsmljpiwohdrmqwylci";
        str2 = "drtppboqqwdlpbyuyb";
        assertEquals(3, solution.longestCommonSubstr(str1, str2));
    }
}

/*
Step 1 - Top-down recursive solution

T - O(3^(n+m))
S - O(n)

In LongestCommonSubsequence, we take if equals and skip if not. But here we skip and reset count to 0.

Also, here we pass the count in recursion to allow reset. We send increased count in param and cumulative count will be returned back after making one full recursion.
 */
class Solution {
    public int longestCommonSubstr(String str1, String str2) {
        return longestCommonSubstr(str1, str2, 0, 0, 0);
    }

    public int longestCommonSubstr(String str1, String str2, int i1, int i2, int count) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return count;
        }

        int taken = 0;
        if (str1.charAt(i1) == str2.charAt(i2)) {
            taken = longestCommonSubstr(str1, str2, i1 + 1, i2 + 1, count + 1);
        }
        // reset count as we gonna look for new substring now
        // count - max so far
        // new substring length for i1, i2+1
        // new substring length for i1+1, i2
        int skipped = Math.max(longestCommonSubstr(str1, str2, i1, i2 + 1, 0), longestCommonSubstr(str1, str2, i1 + 1, i2, 0));
        return Math.max(taken, Math.max(skipped, count));
    }
}

/*
Step 2 - Memoization

T - O(n*m * Min(n,m))
S - O(n*m * Min(n,m))

⚠️ Why Not Memoize Easily?
Head recursion and sends modified count from top.
This solution tracks count as a state, so memoizing it naively with (i1, i2) won't help. To properly memoize, you’d need a 3D table or a better tabulated DP solution.
 */
class Solution2 {
    public int longestCommonSubstr(String str1, String str2) {
        int[][][] dp = new int[str1.length()][str2.length()][Math.min(str1.length(), str2.length())];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }
        return longestCommonSubstr(str1, str2, 0, 0, 0, dp);
    }

    public int longestCommonSubstr(String str1, String str2, int i1, int i2, int count, int[][][] dp) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return count;
        }

        if (dp[i1][i2][count] != -1) {
            return dp[i1][i2][count];
        }

        int taken = 0;
        if (str1.charAt(i1) == str2.charAt(i2)) {
            taken = longestCommonSubstr(str1, str2, i1 + 1, i2 + 1, count + 1, dp);
        }
        // reset count as we gonna look for new substring now
        // count - max so far
        // new substring length for i1, i2+1
        // new substring length for i1+1, i2
        int skipped = Math.max(longestCommonSubstr(str1, str2, i1, i2 + 1, 0, dp), longestCommonSubstr(str1, str2, i1 + 1, i2, 0, dp));
        int max = Math.max(taken, Math.max(skipped, count));
        dp[i1][i2][count] = max;
        return max;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*m)
S - O(n*m)

Known solutions
Base-case with 1st element is also recursive and little complex.
So we'll go with 1-based indexing
Any empty str1 and non-empty str2 -> 0 dp[0][i] = 0
Any non-empty str1 and empty str2 -> 0 dp[i][0] = 0
 */
class Solution3 {
    public int longestCommonSubstr(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] means the LCSubstring ending at str1 of length i (from start) and str2 of length j (from start)
        // that means dp[n][m] will not give LCSubstring of entire strings, rather only when LCS ends at i for str1 and j for str2. So we need to find the max of all rows.

        // Known solutions
        // dp[0][i] = 0
        // dp[i][0] = 0

        int maxOfAll = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxOfAll = Math.max(maxOfAll, dp[i][j]);
                }
                // else count will be 0, and we need to find a new match later
            }
        }

        return maxOfAll;
    }
}

/*
Step 4 - Space Optimization

T - O(n*m)
S - O(m)

 */
class Solution4 {
    public int longestCommonSubstr(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[2][m + 1];

        // Known solutions
        // dp[0][i] = 0
        // dp[i][0] = 0

        int maxOfAll = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[1][j] = 1 + dp[0][j - 1];
                    maxOfAll = Math.max(maxOfAll, dp[1][j]);
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m + 1);
            Arrays.fill(dp[1], 0);
        }

        return maxOfAll;
    }
}