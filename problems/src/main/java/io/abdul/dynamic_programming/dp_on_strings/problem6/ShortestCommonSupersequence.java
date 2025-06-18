package io.abdul.dynamic_programming.dp_on_strings.problem6;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortestCommonSupersequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution3 solution = new Solution3();
//
        // Test Case 1: Example input with partial overlap
        String str1 = "mno";
        String str2 = "nop";
        String result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"mno\" and \"nop\" in the same order");

        // Test Case 2: Example input with minimal overlap
        str1 = "dynamic";
        str2 = "program";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"dynamic\" and \"program\" in the same order");

        // Test Case 3: Example input with no overlap
        str1 = "apple";
        str2 = "orange";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"apple\" and \"orange\" in the same order");

        // Test Case 4: Edge case with one empty string
        str1 = "";
        str2 = "abc";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"\" and \"abc\" in the same order");

        // Test Case 5: Edge case with identical strings
        str1 = "abcdef";
        str2 = "abcdef";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"abcdef\" and \"abcdef\" in the same order");

        // Test Case 6: Edge case with one string being a subsequence of the other
        str1 = "abc";
        str2 = "abcd";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"abc\" and \"abcd\" in the same order");

        // Test Case 7: Large input with partial overlap
        str1 = "abcdefghij";
        str2 = "acegikmoqs";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"abcdefghij\" and \"acegikmoqs\" in the same order");

        // Test Case 8: Edge case with no common characters
        str1 = "xyz";
        str2 = "abc";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result),
                "The result should contain characters of \"xyz\" and \"abc\" in the same order");

        str1 = "ggbbgaebfefebgefcaceefbdc";
        str2 = "babcadebe";
        result = solution.shortestCommonSupersequence(str1, str2);
        assertTrue(isSubsequence(str1, result) && isSubsequence(str2, result));
    }

    private static boolean isSubsequence(String str, String result) {
        int i = 0, j = 0;
        while (i < str.length() && j < result.length()) {
            if (str.charAt(i) == result.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == str.length();
    }
}

/*
Step 1 - Top-down iterative solution

T - O(2^n)
S - O(n) - stack

apple and orange
LCS - ae
a & o - a matches LCS, so take o
a & r - a matches LCS, so take r
a & a - full match LCS, so take a
p and n - no match, let's take p
p and n - no match, let's take n
p and g - no match, let's take p
l and g - no match, let's take g
l and e - e matches LCS, so take l
e and e - full match LCS, so take e

o r a p n p g l e = 9

There can be multiple optimal solutions with same length. It's in what we choose when both don't match.

Why just take 1 when no match?

 */
class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        ArrayList<Character> temp = new ArrayList<>();
        ArrayList<Character> lcs = new ArrayList<>();
        longestCommonSubsequence(str1, str2, temp, lcs);

        if (lcs.isEmpty()) {
            return str1 + str2;
        }

        return getShortestCommonSupersequence(str1, str2, lcs);
    }

    private static String getShortestCommonSupersequence(String str1, String str2, ArrayList<Character> lcs) {
        int i = 0;
        int j = 0;
        int k = 0;

        StringBuilder res = new StringBuilder();
        while (k < lcs.size() && i < str1.length() && j < str2.length()) {
            if (str1.charAt(i) == str2.charAt(j) && str1.charAt(i) == lcs.get(k)) { // all match
                res.append(str1.charAt(i));
                i++;
                j++;
                k++;
            } else if (str1.charAt(i) == lcs.get(k)) { // 2 match
                res.append(str2.charAt(j));
                j++;
            } else if (str2.charAt(j) == lcs.get(k)) { // 2 match
                res.append(str1.charAt(i));
                i++;
            } else { // no match
                res.append(str1.charAt(i));
                res.append(str2.charAt(j));
                i++;
                j++;
            }
        }

        while (i < str1.length()) {
            res.append(str1.charAt(i));
            i++;
        }

        while (j < str2.length()) {
            res.append(str2.charAt(j));
            j++;
        }

        return res.toString();
    }

    private int longestCommonSubsequence(String str1, String str2, List<Character> temp, List<Character> result) {
        return longestCommonSubsequence(str1, str2, 0, 0, temp, result);
    }

    private int longestCommonSubsequence(String str1, String str2, int i1, int i2, List<Character> temp, List<Character> result) {
        if (i1 == str1.length() || i2 == str2.length()) {
            if (temp.size() > result.size()) {
                result.clear();
                result.addAll(temp);
            }
            return 0;
        }

        if (str1.charAt(i1) == str2.charAt(i2)) {
            temp.add(str1.charAt(i1)); // no skip case as there's only one optimal solution. Generally we remove and try other options.
            int res = 1 + longestCommonSubsequence(str1, str2, i1 + 1, i2 + 1, temp, result);
            temp.remove(temp.size() - 1);
            return res;
        } else {
            return Math.max(longestCommonSubsequence(str1, str2, i1, i2 + 1, temp, result), longestCommonSubsequence(str1, str2, i1 + 1, i2, temp, result));
        }
    }
}

/*
Step 2 - Memoization
Difficult due to output argument
 */

/*
Step 3 - Bottom-up iterative solution

T - O(n^2) - core logic + m+n
S - O(n^2) - dp

Keep track of previous element to backtrack and build lcs
 */
class Solution3 {
    public String shortestCommonSupersequence(String str1, String str2) {
        char[] lcs = longestCommonSubsequence(str1, str2);

        if (lcs.length == 0) {
            return str1 + str2;
        }

        return getShortestCommonSupersequence(str1, str2, lcs);
    }

    private static String getShortestCommonSupersequence(String str1, String str2, char[] lcs) {
        int i = 0;
        int j = 0;
        int k = 0;

        StringBuilder res = new StringBuilder();
        while (k < lcs.length && i < str1.length() && j < str2.length()) {
            if (str1.charAt(i) == str2.charAt(j) && str1.charAt(i) == lcs[k]) { // all match
                res.append(str1.charAt(i));
                i++;
                j++;
                k++;
            } else if (str1.charAt(i) == lcs[k]) { // 2 match
                res.append(str2.charAt(j));
                j++;
            } else if (str2.charAt(j) == lcs[k]) { // 2 match
                res.append(str1.charAt(i));
                i++;
            } else { // no match
                res.append(str1.charAt(i));
                res.append(str2.charAt(j));
                i++;
                j++;
            }
        }

        while (i < str1.length()) {
            res.append(str1.charAt(i));
            i++;
        }

        while (j < str2.length()) {
            res.append(str2.charAt(j));
            j++;
        }

        return res.toString();
    }

    private char[] longestCommonSubsequence(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        /*
         dp[i][j] is the LCS for str1 ending at i and str2 ending at j
         doesn't mean that str1[i] is part of the LCS!
         So we can't rebuild the LCS by just looking at last row
         dp[n][j] only keeps the LCS considering str1 ending at n-1 and str1 ending at j (0 to m-1)
         But LCS is different at each str1 ending as well, we'll pick the best of it
         We'll just backtrack the dp table the way we've filled it
         Ex:
         ggbbgaebfefebgefcaceefbdc and babcadebe
         dp[n] = 0 1 2 3 4 5 6 6 7 7
         dp[n][6] = 6, meaning LCS of str1 length n and str2 length 6 is 6. the last char will be part of LCS as total length is 6 only
         dp[n][7] = 6, meaning LCS of str1 length n and str2 length 7 is 6. the last char may or may not be part of LCS as length is 7
         Pos 5 and 6 has 6. Means either of them could have part of LCS. We can't decide by just looking at it.
         Both d & e are common in both strings. So commonality check also doesn't work.
         Same goes to Pos 7 and 8.
        */

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        /*
        Backtrack the dp to in the way it's built!
        if s[i] == s[j], part of lcs, move to upper diagonal (i-1, j-1)
        if s[i] != s[j], the length would have come from upper cell or left cell. We can go to whichever is greater or equals than other
         */
        char[] lcs = new char[dp[n][m]];
        int k = dp[n][m] - 1;
        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs[k] = str1.charAt(i - 1);
                k--;
                i = i - 1; // go to upper diagonal
                j = j - 1;
            } else {
                if (dp[i - 1][j] >= dp[i][j - 1]) {
                    i = i - 1; // left
                } else {
                    j = j - 1; // up
                }
            }
        }
        return lcs;
    }
}

/*
Step 4 - Space Optimization

Can't be done, as we need to backtrack the DP to build the LCS

 */
