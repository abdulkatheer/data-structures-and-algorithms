package io.abdul.dynamic_programming.dp_on_strings.problem4;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Palindromes can't be built linearly as we can't verify part of the string is palindrome.
But we can build it by checking both ends or comparing mid, then mid+1,mid-1, then mid+2,mid-2 etc.,
 */
public class MinInsertionsForPalindrome {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        SolutionV2_1 solution = new SolutionV2_1();
//        SolutionV2_2 solution = new SolutionV2_2();
//        SolutionV2_3 solution = new SolutionV2_3();
        SolutionV2_4 solution = new SolutionV2_4();

        // Test Case 1: Example input with 2 insertions needed
        String s1 = "abcaa";
        assertEquals(2, solution.minInsertion(s1),
                "The minimum insertions for \"abcaa\" to become a palindrome should be 2");

        // Test Case 2: Example input with 1 insertion needed
        String s2 = "ba";
        assertEquals(1, solution.minInsertion(s2),
                "The minimum insertions for \"ba\" to become a palindrome should be 1");

        // Test Case 3: Example input with no insertions needed
        String s3 = "madam";
        assertEquals(0, solution.minInsertion(s3),
                "The minimum insertions for \"madam\" to become a palindrome should be 0");

        // Test Case 4: Edge case with single character
        String s4 = "a";
        assertEquals(0, solution.minInsertion(s4),
                "The minimum insertions for \"a\" to become a palindrome should be 0");

        // Test Case 5: Edge case with already a palindrome
        String s5 = "racecar";
        assertEquals(0, solution.minInsertion(s5),
                "The minimum insertions for \"racecar\" to become a palindrome should be 0");

        // Test Case 6: Edge case with no repeating characters
        String s6 = "abcd";
        assertEquals(3, solution.minInsertion(s6),
                "The minimum insertions for \"abcd\" to become a palindrome should be 3");

        // Test Case 7: Large input with mixed characters
        String s7 = "aebcbda";
        assertEquals(2, solution.minInsertion(s7),
                "The minimum insertions for \"aebcbda\" to become a palindrome should be 2");

        // Test Case 8: Edge case with two characters
        String s8 = "aa";
        assertEquals(0, solution.minInsertion(s8),
                "The minimum insertions for \"aa\" to become a palindrome should be 0");

        // Test Case 9: Edge case with two different characters
        String s9 = "ab";
        assertEquals(1, solution.minInsertion(s9),
                "The minimum insertions for \"ab\" to become a palindrome should be 1");

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

Ex: abcaa
First look at 0 and 4th position. They're matching and can be part of a palindrome.
then look at 1 and 3rd position. Not matching. What are the possibilities? you can either insert 1st element after 3rd OR 3rd element before 1st.
Either way, we need one insertion. And the result depends on which insertion is carried out.
For ex, if 1st is inserted after 3rd, we'll balance the remaining as palindrome (i+1, j) (i is balanced)
if 3rd is inserted before 1st, we'll balance i,j-1 (j is balanced)
 */
class Solution {
    public int minInsertion(String s) {
        return minInsertion(s, 0, s.length() - 1);
    }

    public int minInsertion(String s, int i, int j) {
        if (i >= j) { // single mid or crossed each other
            return 0;
        }

        /*
        Why if-else and why not take min of all three calls (taken and skipped)?
        Bcz there can be only one optimal solution (palindrome) exist. So if both are matching, it'll be the final palindrome.
        Even if we try other options, it'll be at least 1 greater than taken.

        Similar to Longest Common Subsequence!
         */
        if (s.charAt(i) == s.charAt(j)) {
            return minInsertion(s, i + 1, j - 1); // both i and j are balanced, so balance the remaining
        } else {
            return 1 + Math.min(minInsertion(s, i, j - 1), minInsertion(s, i + 1, j));
        }
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

 */
class Solution2 {
    public int minInsertion(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return minInsertion(s, 0, s.length() - 1, dp);
    }

    public int minInsertion(String s, int i, int j, int[][] dp) {
        if (i >= j) { // single mid or crossed each other
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        /*
        Why if-else and why not take min of all three calls (taken and skipped)?
        Bcz there can be only one optimal solution (palindrome) exist. So if both are matching, it'll be the final palindrome.
        Even if we try other options, it'll be at least 1 greater than taken.

        Similar to Longest Common Subsequence!
         */
        int result;
        if (s.charAt(i) == s.charAt(j)) {
            result = minInsertion(s, i + 1, j - 1, dp); // both i and j are balanced, so balance the remaining
        } else {
            result = 1 + Math.min(minInsertion(s, i, j - 1, dp), minInsertion(s, i + 1, j, dp));
        }
        dp[i][j] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions
Hard to define it! Refer v2 of this solution
 */

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(n) - stack

We know the relation between LCS and LPS.
LCS of two strings says that, when two string is merged like (s1 + revers(s2)), then it forms a palindromic subsequence of length LCS*2

To fins LPS of a string, we find LCS of (s, reverse(s)).

Now to make the whole string as palindrome, we can find LPS of the string and the remaining characters has to be balanced. That's it!
 */
class SolutionV2_1 {
    public int minInsertion(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    private int longestPalindromicSubsequence(String s) {
        return longestCommonSubsequence(s, new StringBuilder(s).reverse().toString());
    }

    private int longestCommonSubsequence(String s1, String s2) {
        return longestCommonSubsequence(s1, s2, 0, 0);
    }

    private int longestCommonSubsequence(String s1, String s2, int i1, int i2) {
        if (i1 == s1.length() || i2 == s2.length()) {
            return 0;
        }

        if (s1.charAt(i1) == s2.charAt(i2)) {
            return 1 + longestCommonSubsequence(s1, s2, i1 + 1, i2 + 1);
        } else {
            return Math.max(longestCommonSubsequence(s1, s2, i1, i2 + 1), longestCommonSubsequence(s1, s2, i1 + 1, i2));
        }
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp + copy

 */
class SolutionV2_2 {
    public int minInsertion(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    private int longestPalindromicSubsequence(String s) {
        return longestCommonSubsequence(s, new StringBuilder(s).reverse().toString());
    }

    private int longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[s1.length()][s2.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return longestCommonSubsequence(s1, s2, 0, 0, dp);
    }

    private int longestCommonSubsequence(String s1, String s2, int i1, int i2, int[][] dp) {
        if (i1 == s1.length() || i2 == s2.length()) {
            return 0;
        }

        if (dp[i1][i2] != -1) {
            return dp[i1][i2];
        }

        int result;
        if (s1.charAt(i1) == s2.charAt(i2)) {
            result = 1 + longestCommonSubsequence(s1, s2, i1 + 1, i2 + 1, dp);
        } else {
            result = Math.max(longestCommonSubsequence(s1, s2, i1, i2 + 1, dp), longestCommonSubsequence(s1, s2, i1 + 1, i2, dp));
        }

        dp[i1][i2] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n^2) - dp + copy

1 - based indexing

Known solutions
When i and j is out of bounds, LCS is zero

 */
class SolutionV2_3 {
    public int minInsertion(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    private int longestPalindromicSubsequence(String s) {
        return longestCommonSubsequence(s, new StringBuilder(s).reverse().toString());
    }

    private int longestCommonSubsequence(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
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
S - O(n) - dp + copy

 */
class SolutionV2_4 {
    public int minInsertion(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    private int longestPalindromicSubsequence(String s) {
        return longestCommonSubsequence(s, new StringBuilder(s).reverse().toString());
    }

    private int longestCommonSubsequence(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[2][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
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