package io.abdul.dynamic_programming.dp_on_strings.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinInsertionsToConvertAtoB {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Example input with 5 operations needed
        String str1 = "kitten";
        String str2 = "sitting";
        assertEquals(5, solution.minOperations(str1, str2),
                "The minimum operations to convert \"kitten\" to \"sitting\" should be 5");

        // Test Case 2: Example input with 2 operations needed
        str1 = "flaw";
        str2 = "lawn";
        assertEquals(2, solution.minOperations(str1, str2),
                "The minimum operations to convert \"flaw\" to \"lawn\" should be 2");

        // Test Case 3: Example input with 12 operations needed
        str1 = "abcdef";
        str2 = "ghijkl";
        assertEquals(12, solution.minOperations(str1, str2),
                "The minimum operations to convert \"abcdef\" to \"ghijkl\" should be 12");

        // Test Case 4: Edge case with identical strings
        str1 = "abc";
        str2 = "abc";
        assertEquals(0, solution.minOperations(str1, str2),
                "The minimum operations to convert \"abc\" to \"abc\" should be 0");

        // Test Case 5: Edge case with one empty string
        str1 = "";
        str2 = "abc";
        assertEquals(3, solution.minOperations(str1, str2),
                "The minimum operations to convert an empty string to \"abc\" should be 3");

        // Test Case 6: Edge case with no common characters
        str1 = "xyz";
        str2 = "abc";
        assertEquals(6, solution.minOperations(str1, str2),
                "The minimum operations to convert \"xyz\" to \"abc\" should be 6");

        // Test Case 7: Large input with partial match
        str1 = "abcdefghij";
        str2 = "acegikmoqs";
        assertEquals(10, solution.minOperations(str1, str2),
                "The minimum operations to convert \"abcdefghij\" to \"acegikmoqs\" should be 10");

        // Test Case 8: Edge case with one string being a subsequence of the other
        str1 = "abc";
        str2 = "abcd";
        assertEquals(1, solution.minOperations(str1, str2),
                "The minimum operations to convert \"abc\" to \"abcd\" should be 1");
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

Ex: kitten, sitting
LCS - ittn
string1 has ke as extra
string2 has sng as extra
ke has to be removed
sng has to be added
So total 5 operations
 */
class Solution {
    public int minOperations(String str1, String str2) {
        int a = str1.length();
        int b = str2.length();
        int lcs = longestCommonSubsequence(str1, str2);
        return (a - lcs) + (b - lcs);
    }

    private int longestCommonSubsequence(String str1, String str2) {
        return longestCommonSubsequence(str1, str2, 0, 0);
    }

    private int longestCommonSubsequence(String str1, String str2, int i1, int i2) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

        if (str1.charAt(i1) == str2.charAt(i2)) {
            return 1 + longestCommonSubsequence(str1, str2, i1 + 1, i2 + 1);
        } else {
            return Math.max(longestCommonSubsequence(str1, str2, i1, i2 + 1), longestCommonSubsequence(str1, str2, i1 + 1, i2));
        }
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

 */
class Solution2 {
    public int minOperations(String str1, String str2) {
        int a = str1.length();
        int b = str2.length();
        int lcs = longestCommonSubsequence(str1, str2);
        return (a - lcs) + (b - lcs);
    }

    private int longestCommonSubsequence(String str1, String str2) {
        int[][] dp = new int[str1.length()][str2.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return longestCommonSubsequence(str1, str2, 0, 0, dp);
    }

    private int longestCommonSubsequence(String str1, String str2, int i1, int i2, int[][] dp) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

        if (dp[i1][i2] != -1) {
            return dp[i1][i2];
        }

        int result;
        if (str1.charAt(i1) == str2.charAt(i2)) {
            result = 1 + longestCommonSubsequence(str1, str2, i1 + 1, i2 + 1, dp);
        } else {
            result = Math.max(longestCommonSubsequence(str1, str2, i1, i2 + 1, dp), longestCommonSubsequence(str1, str2, i1 + 1, i2, dp));
        }
        dp[i1][i2] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp

Known solutions
When i and j are out of bounds, then LCS is 0
we use 1-based indexing
 */
class Solution3 {
    public int minOperations(String str1, String str2) {
        int a = str1.length();
        int b = str2.length();
        int lcs = longestCommonSubsequence(str1, str2);
        return (a - lcs) + (b - lcs);
    }

    private int longestCommonSubsequence(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
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
Step 4 - Space Optimization

T - O(n^2)
S - O(n) - dp

 */
class Solution4 {
    public int minOperations(String str1, String str2) {
        int a = str1.length();
        int b = str2.length();
        int lcs = longestCommonSubsequence(str1, str2);
        return (a - lcs) + (b - lcs);
    }

    private int longestCommonSubsequence(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[2][m + 1];

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
