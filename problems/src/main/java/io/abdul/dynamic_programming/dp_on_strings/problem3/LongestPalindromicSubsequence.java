package io.abdul.dynamic_programming.dp_on_strings.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestPalindromicSubsequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution1a solution = new Solution1a();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();
//        Solution3a solution = new Solution3a();
        Solution4a solution = new Solution4a();

        // Test Case 1: Example input with palindromic subsequence length 4
        String s1 = "eeeme";
        assertEquals(4, solution.longestPalinSubseq(s1),
                "The longest palindromic subsequence of \"eeeme\" should be 4");

        // Test Case 2: Example input with palindromic subsequence length 2
        String s2 = "annb";
        assertEquals(2, solution.longestPalinSubseq(s2),
                "The longest palindromic subsequence of \"annb\" should be 2");

        // Test Case 3: Single character string
        String s3 = "s";
        assertEquals(1, solution.longestPalinSubseq(s3),
                "The longest palindromic subsequence of \"s\" should be 1");

        // Test Case 4: Edge case with all identical characters
        String s4 = "aaaa";
        assertEquals(4, solution.longestPalinSubseq(s4),
                "The longest palindromic subsequence of \"aaaa\" should be 4");

        // Test Case 5: Edge case with no repeating characters
        String s5 = "abcd";
        assertEquals(1, solution.longestPalinSubseq(s5),
                "The longest palindromic subsequence of \"abcd\" should be 1");

        // Test Case 6: Palindrome string
        String s6 = "racecar";
        assertEquals(7, solution.longestPalinSubseq(s6),
                "The longest palindromic subsequence of \"racecar\" should be 7");

        // Test Case 7: Large input with mixed characters
        String s7 = "aebcbda";
        assertEquals(5, solution.longestPalinSubseq(s7),
                "The longest palindromic subsequence of \"aebcbda\" should be 5");

        // Test Case 8: Edge case with two characters
        String s8 = "aa";
        assertEquals(2, solution.longestPalinSubseq(s8),
                "The longest palindromic subsequence of \"aa\" should be 2");

        // Test Case 9: Edge case with two different characters
        String s9 = "ab";
        assertEquals(1, solution.longestPalinSubseq(s9),
                "The longest palindromic subsequence of \"ab\" should be 1");
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack + copy

a x d e n f b n g h i a k
k a i h g n b f n e d x a
Optimal
a
n
b
n
a
 */
class Solution {
    public int longestPalinSubseq(String s) {
        return longestPalinSubseq(s, new StringBuilder(s).reverse().toString(), 0, 0);
    }

    public int longestPalinSubseq(String str1, String str2, int i1, int i2) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

        if (str1.charAt(i1) == str2.charAt(i2)) {
            return 1 + longestPalinSubseq(str1, str2, i1 + 1, i2 + 1);
        } else {
            return Math.max(longestPalinSubseq(str1, str2, i1, i2 + 1), longestPalinSubseq(str1, str2, i1 + 1, i2));
        }
    }
}

/*
T - O(2^n)
S - O(n) - stack
 */
class Solution1a {
    public int longestPalinSubseq(String s) {
        return longestPalinSubseq(s, 0, s.length() - 1);
    }

    public int longestPalinSubseq(String str1, int i1, int i2) {
        if (i1 == str1.length() || i2 < 0) {
            return 0;
        }

        if (str1.charAt(i1) == str1.charAt(i2)) {
            return 1 + longestPalinSubseq(str1, i1 + 1, i2 - 1);
        } else {
            return Math.max(longestPalinSubseq(str1, i1, i2 - 1), longestPalinSubseq(str1, i1 + 1, i2));
        }
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp
 */
class Solution2 {
    public int longestPalinSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return longestPalinSubseq(s, 0, s.length() - 1, dp);
    }

    public int longestPalinSubseq(String str1, int i1, int i2, int[][] dp) {
        if (i1 == str1.length() || i2 < 0) {
            return 0;
        }

        if (dp[i1][i2] != -1) {
            return dp[i1][i2];
        }
        if (str1.charAt(i1) == str1.charAt(i2)) {
            return 1 + longestPalinSubseq(str1, i1 + 1, i2 - 1, dp);
        } else {
            return Math.max(longestPalinSubseq(str1, i1, i2 - 1, dp), longestPalinSubseq(str1, i1 + 1, i2, dp));
        }
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp + copy

1 - based indexing as base case itself is recursive/complex

 */
class Solution3 {
    public int longestPalinSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        String s1 = s;
        String s2 = new StringBuilder(s).reverse().toString();

        // Known solutions
        // dp[n][i] = 0
        // dp[i][0] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[n][n];
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp

1 - based indexing as base case itself is recursive/complex

 */
class Solution3a {
    public int longestPalinSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];

        // Known solutions
        // dp[n][i] = 0
        // dp[i][0] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == s.charAt(n - j)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[n][n];
    }
}

/*
Step 4 - Space Optimization

T - O(n^2)
S - O(n) - dp + copy

 */
class Solution4 {
    public int longestPalinSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[2][n + 1];
        String s1 = s;
        String s2 = new StringBuilder(s).reverse().toString();

        // Known solutions
        // dp[n][i] = 0
        // dp[i][0] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[1][j] = 1 + dp[0][j - 1];
                } else {
                    dp[1][j] = Math.max(dp[1][j - 1], dp[0][j]);
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n + 1);
        }

        return dp[0][n];
    }
}

/*
Step 4 - Space Optimization

T - O(n^2)
S - O(n) - dp

 */
class Solution4a {
    public int longestPalinSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[2][n + 1];
        String s1 = s;

        // Known solutions
        // dp[n][i] = 0
        // dp[i][0] = 0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s1.charAt(n - j)) {
                    dp[1][j] = 1 + dp[0][j - 1];
                } else {
                    dp[1][j] = Math.max(dp[1][j - 1], dp[0][j]);
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, n + 1);
        }

        return dp[0][n];
    }
}