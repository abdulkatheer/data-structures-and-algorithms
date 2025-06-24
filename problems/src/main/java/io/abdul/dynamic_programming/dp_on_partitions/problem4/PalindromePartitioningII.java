package io.abdul.dynamic_programming.dp_on_partitions.problem4;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromePartitioningII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        SolutionV2_1 solution = new SolutionV2_1();
//        SolutionV2_2 solution = new SolutionV2_2();
        SolutionV2_3 solution = new SolutionV2_3();

        // Example 1: "aab" -> 1 cut ("aa", "b")
        assertEquals(1, solution.minCut("aab"));

        // Example 2: "abaaba" -> 0 cuts (whole string is palindrome)
        assertEquals(0, solution.minCut("abaaba"));

        // Example 3: "abcd" -> 3 cuts ("a","b","c","d")
        assertEquals(3, solution.minCut("abcd"));

        // Edge case: single character, no cuts needed
        assertEquals(0, solution.minCut("a"));

        // Edge case: all same characters, no cuts needed
        assertEquals(0, solution.minCut("aaaa"));

        // Edge case: two characters, not a palindrome
        assertEquals(1, solution.minCut("ab"));

        // Edge case: already palindrome
        assertEquals(0, solution.minCut("racecar"));

        // Edge case: palindrome with odd length
        assertEquals(0, solution.minCut("madam"));

        // Edge case: palindrome with even length
        assertEquals(0, solution.minCut("noon"));
    }
}

/*
Step 1 - Top-down recursive solution

T - O(n 2^n)
S - O(n) - stack

Base case:
If i-j is a palindrome, return 0

abcd
i=1, j=4 -> Not a palindrome, so 3 options to cut i=1,j=1 & i=2,j=4 | i=1,j=2 & i=3,j=4 | i=1,j=3 & i=4,j=4
 */
class Solution {
    public int minCut(String s) {
        return minCut(s, 0, s.length() - 1);
    }

    public int minCut(String s, int i, int j) {
        if (isPalindrome(s, i, j)) {
            return 0;
        }

        // Many ways to split now
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            min = Math.min(min, 1 + minCut(s, i, k) + minCut(s, k + 1, j));
        }

        return min;
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= i; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}

/*
Step 2 - Memoization

T - O(n^3)
S - O(n^2) - stack + dp
 */
class Solution2 {
    public int minCut(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return minCut(s, 0, n - 1, dp);
    }

    public int minCut(String s, int i, int j, int[][] dp) {
        if (isPalindrome(s, i, j)) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        // Many ways to split now
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            min = Math.min(min, 1 + minCut(s, i, k, dp) + minCut(s, k + 1, j, dp));
        }

        dp[i][j] = min;
        return min;
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= i; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^3)
S - O(n^2) - dp

Known solution
if i-j is palindrome, no cost
 */
class Solution3 {
    public int minCut(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j <= n - 1; j++) {
                if (isPalindrome(s, i, j)) {
                    continue;
                }

                int min = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    min = Math.min(min, 1 + dp[i][k] + dp[k + 1][j]);
                }

                dp[i][j] = min;
            }
        }

        return dp[0][n - 1];
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= mid; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}

/*
Step 1 - Top-down recursive solution
Front partitioning approach

T - O(n^3)
S - O(n) - stack
abcabc

The max partition will be n-1
Min is 0

We start partitioning from front. We add up one by one to make a palindrome. One we make a palindrome, we can add cost of partitioning + cost to check the remaining
a | bcabc
abc | abc - mind

aba
a | ba
aba - min
 */
class SolutionV2_1 {
    public int minCut(String s) {
        // Result gives number of palindromes, so cuts will be one less than that
        return minPartitions(s, 0) - 1;
    }

    private int minPartitions(String s, int i) {
        if (i == s.length()) { // no more chars
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int j = i; j < s.length(); j++) {
            if (isPalindrome(s, i, j)) {
                min = Math.min(min, 1 + minPartitions(s, j + 1));
            }
        }

        return min;
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= mid; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n) - stack + dp

 */
class SolutionV2_2 {
    public int minCut(String s) {
        int[] dp = new int[s.length()];
        Arrays.fill(dp, -1);
        // Result gives number of palindromes, so cuts will be one less than that
        return minPartitions(s, 0, dp) - 1;
    }

    private int minPartitions(String s, int i, int[] dp) {
        if (i == s.length()) { // no more chars
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int min = Integer.MAX_VALUE;
        for (int j = i; j < s.length(); j++) {
            if (isPalindrome(s, i, j)) {
                min = Math.min(min, 1 + minPartitions(s, j + 1, dp));
            }
        }

        dp[i] = min;
        return min;
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= mid; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2)
S - O(n) - dp

Known solution
For empty str, 0 partitions
abcabc = 0

Recursive solutions
a bcabc = 1
ab cabc = 2
abc abc = 1
abca bc = 2
abcab c = 3
abc abc = 2
 */
class SolutionV2_3 {
    public int minCut(String s) {
        int[] dp = new int[s.length() + 1];

        // Known solution
        dp[0] = 0;

        for (int i = 1; i <= s.length(); i++) {
            int min = Integer.MAX_VALUE;
            for (int j = i; j > 0; j--) {
                if (isPalindrome(s, j - 1, i - 1)) {
                    min = Math.min(min, 1 + dp[j - 1]);
                }
            }
            dp[i] = min;
        }

        // cuts are 1 less than partitions
        return dp[s.length()] - 1;
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i == j) {
            return true;
        }

        int mid = (j - i) / 2;
        for (int k = 0; k <= mid; k++) {
            if (s.charAt(i + k) != s.charAt(j - k)) {
                return false;
            }
        }

        return true;
    }
}