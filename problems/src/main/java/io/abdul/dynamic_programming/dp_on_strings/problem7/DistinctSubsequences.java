package io.abdul.dynamic_programming.dp_on_strings.problem7;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistinctSubsequences {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with 2 distinct subsequences
        String s1 = "axbxax";
        String t1 = "axa";
        assertEquals(2, solution.distinctSubsequences(s1, t1),
                "The number of distinct subsequences of \"axbxax\" that equal \"axa\" should be 2");

        // Test Case 2: Example input with 5 distinct subsequences
        String s2 = "babgbag";
        String t2 = "bag";
        assertEquals(5, solution.distinctSubsequences(s2, t2),
                "The number of distinct subsequences of \"babgbag\" that equal \"bag\" should be 5");

        // Test Case 3: Example input with 1 distinct subsequence
        String s3 = "abcde";
        String t3 = "ace";
        assertEquals(1, solution.distinctSubsequences(s3, t3),
                "The number of distinct subsequences of \"abcde\" that equal \"ace\" should be 1");

        // Test Case 4: Edge case with no matching subsequences
        String s4 = "abc";
        String t4 = "xyz";
        assertEquals(0, solution.distinctSubsequences(s4, t4),
                "The number of distinct subsequences of \"abc\" that equal \"xyz\" should be 0");

        // Test Case 5: Edge case with identical strings
        String s5 = "abc";
        String t5 = "abc";
        assertEquals(1, solution.distinctSubsequences(s5, t5),
                "The number of distinct subsequences of \"abc\" that equal \"abc\" should be 1");

        // Test Case 6: Edge case with empty target string
        String s6 = "abc";
        String t6 = "";
        assertEquals(1, solution.distinctSubsequences(s6, t6),
                "The number of distinct subsequences of \"abc\" that equal an empty string should be 1");

        // Test Case 7: Edge case with empty source string
        String s7 = "";
        String t7 = "abc";
        assertEquals(0, solution.distinctSubsequences(s7, t7),
                "The number of distinct subsequences of an empty string that equal \"abc\" should be 0");

        // Test Case 8: Large input with partial match
        String s8 = "aabbcc";
        String t8 = "abc";
        assertEquals(8, solution.distinctSubsequences(s8, t8),
                "The number of distinct subsequences of \"aabbcc\" that equal \"abc\" should be 8");

        // Test Case 9: Edge case with repeated characters
        String s9 = "aaaa";
        String t9 = "aa";
        assertEquals(6, solution.distinctSubsequences(s9, t9),
                "The number of distinct subsequences of \"aaaa\" that equal \"aa\" should be 6");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n)

Each pos has three options

If matches, taken
Skip s
Skip t
 */
class Solution {
    private static final int MAX = (int) 1e9 + 7;

    public int distinctSubsequences(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }

        return distinctSubsequences(s, t, 0, 0);
    }

    public int distinctSubsequences(String s, String t, int si, int ti) {
        if (ti == t.length()) { // t is exhausted
            return 1;
        }
        if (si == s.length()) { // t is not exhausted, but s exhausted
            return 0;
        }
        int taken = 0;
        if (s.charAt(si) == t.charAt(ti)) {
            taken = distinctSubsequences(s, t, si + 1, ti + 1);
        }

        int skip = distinctSubsequences(s, t, si + 1, ti);

        return (taken + skip) % MAX;
    }
}

/*
Step 2 - Memoization

T - O(n*t)
S - O(n*t) stack + dp
 */
class Solution2 {
    private static final int MAX = (int) 1e9 + 7;

    public int distinctSubsequences(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }

        int[][] dp = new int[s.length()][t.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }

        return distinctSubsequences(s, t, 0, 0, dp);
    }

    public int distinctSubsequences(String s, String t, int si, int ti, int[][] dp) {
        if (ti == t.length()) { // t is exhausted
            return 1;
        }
        if (si == s.length()) { // t is not exhausted, but s exhausted
            return 0;
        }

        if (dp[si][ti] != -1) {
            return dp[si][ti];
        }
        int taken = 0;
        if (s.charAt(si) == t.charAt(ti)) {
            taken = distinctSubsequences(s, t, si + 1, ti + 1, dp);
        }

        int skip = distinctSubsequences(s, t, si + 1, ti, dp);

        int result = (taken + skip) % MAX;
        dp[si][ti] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*t)
S - O(n*t) dp

1-based indexing

Known solutions
for any empty t and non-empty s, result is 1. As t is exhausted
for any empty s and non-empty t, result is 0. As t is not exhausted
row - s
column - t
dp[i][0] = 1
dp[0][i] = 0 // default

axbxax
axa

 */
class Solution3 {
    private static final int MAX = (int) 1e9 + 7;

    public int distinctSubsequences(String s, String t) {
        int n = s.length();
        int m = t.length();
        if (n < m) {
            return 0;
        }

        int[][] dp = new int[s.length() + 1][t.length() + 1];
        // dp[n][i] stores the max distinct subsequences for string s with t with length i+1

        // Known solutions
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        // dp[0][i] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int taken = 0;
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    taken = dp[i - 1][j - 1];
                }
                int skip = dp[i - 1][j];
                dp[i][j] = (taken + skip) % MAX;
            }
        }

        return dp[n][m];
    }
}

/*
Step 4 - Space Optimization

T - O(n*t)
S - O(t) dp

 */
class Solution4 {
    private static final int MAX = (int) 1e9 + 7;

    public int distinctSubsequences(String s, String t) {
        int n = s.length();
        int m = t.length();
        if (n < m) {
            return 0;
        }

        int[][] dp = new int[2][t.length() + 1];
        // dp[n][i] stores the max distinct subsequences for string s with t with length i+1

        // Known solutions
        for (int i = 0; i < 2; i++) {
            dp[i][0] = 1;
        }
        // dp[0][i] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int taken = 0;
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    taken = dp[0][j - 1];
                }
                int skip = dp[0][j];
                dp[1][j] = (taken + skip) % MAX;
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m + 1);
        }

        return dp[0][m];
    }
}