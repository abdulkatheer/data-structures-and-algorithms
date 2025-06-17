package io.abdul.dynamic_programming.dp_on_strings.problem9;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WildcardMatch {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: Pattern matches the string
        String str1 = "xaylmz";
        String pat1 = "x?y*z";
        assertTrue(solution.wildCard(str1, pat1),
                "The pattern \"x?y*z\" should match the string \"xaylmz\"");

        // Test Case 2: Pattern does not match the string
        String str2 = "xyza";
        String pat2 = "x*z";
        assertFalse(solution.wildCard(str2, pat2),
                "The pattern \"x*z\" should not match the string \"xyza\"");

        // Test Case 3: Pattern matches the string with '?' wildcard
        String str3 = "abc";
        String pat3 = "a?c";
        assertTrue(solution.wildCard(str3, pat3),
                "The pattern \"a?c\" should match the string \"abc\"");

        // Test Case 4: Edge case with empty string and empty pattern
        String str4 = "";
        String pat4 = "";
        assertTrue(solution.wildCard(str4, pat4),
                "An empty pattern should match an empty string");

        // Test Case 5: Edge case with empty string and non-empty pattern
        String str5 = "";
        String pat5 = "*";
        assertTrue(solution.wildCard(str5, pat5),
                "The pattern \"*\" should match an empty string");

        // Test Case 6: Edge case with non-empty string and empty pattern
        String str6 = "abc";
        String pat6 = "";
        assertFalse(solution.wildCard(str6, pat6),
                "An empty pattern should not match a non-empty string");

        // Test Case 7: Pattern matches the string with '*' wildcard
        String str7 = "abcdef";
        String pat7 = "a*f";
        assertTrue(solution.wildCard(str7, pat7),
                "The pattern \"a*f\" should match the string \"abcdef\"");

        // Test Case 8: Pattern does not match the string with extra characters
        String str8 = "abcd";
        String pat8 = "a*c";
        assertFalse(solution.wildCard(str8, pat8),
                "The pattern \"a*c\" should not match the string \"abcd\"");

        // Test Case 9: Pattern matches the string with multiple wildcards
        String str9 = "mississippi";
        String pat9 = "m*iss*ppi";
        assertTrue(solution.wildCard(str9, pat9),
                "The pattern \"m*iss*ppi\" should match the string \"mississippi\"");

        // Test Case 10: Edge case with '?' and '*' wildcards
        String str10 = "xyz";
        String pat10 = "?*z";
        assertTrue(solution.wildCard(str10, pat10),
                "The pattern \"?*z\" should match the string \"xyz\"");

        String str11 = "yuyaxtkkbv";
        String pat11 = "**";
        assertTrue(solution.wildCard(str11, pat11));

    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^(m+n))
S - O(m+n) - stack

When character - exact match and move to next only when match
When ?, skip and move to next
When *, try moving to multiple chars (from i to n-1) for string and next char for patter
 */
class Solution {
    public boolean wildCard(String str, String pat) {
        return wildCard(str, pat, 0, 0);
    }

    private boolean wildCard(String str, String pat, int i, int j) {
        if (i == str.length() && j == pat.length()) { // Both has drained
            return true;
        }

        if (j == pat.length()) { // pat drained, but more chars left match
            return false;
        }

        if (i == str.length()) { // chars drained, but pat left unmatched
            // when last characters of pattern are *, we won't be able to check till end as i will go out of bounds early
            /*
            abcde **
            here 1st * can match abcd and second * can only e
            But when second star tries to match e, i=4 and j=1
            Now both i and j has to move to next position to formally end
            But here we've only two options, take it or skip it
            i+1,j or 1,j+1
            4,2 OR 5,1
            In both cases, we end up in false
            To handle this corner has, when it goes to 5,1, we check if all remaining characters are just * consider them as no-match
             */
            while (j < pat.length()) {
                if (pat.charAt(j) != '*') {
                    return false;
                }
                j++;
            }
            return true; // all remaining pat are * only
        }

        if (pat.charAt(j) == '?') {
            return wildCard(str, pat, i + 1, j + 1);
        } else if (pat.charAt(j) == '*') {
            return wildCard(str, pat, i + 1, j) || wildCard(str, pat, i, j + 1); // match current char and any in future OR skip matching and look for next pattern
        } else {
            if (str.charAt(i) == pat.charAt(j)) {
                return wildCard(str, pat, i + 1, j + 1);
            } else {
                return false; // no match
            }
        }
    }

}

/*
Step 2 - Memoization

T - O((m+n)^2)
S - O((m+n)^2) - stack + dp

 */
class Solution2 {
    public boolean wildCard(String str, String pat) {
        int[][] dp = new int[str.length()][pat.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return wildCard(str, pat, 0, 0, dp);
    }

    private boolean wildCard(String str, String pat, int i, int j, int[][] dp) {
        if (i == str.length() && j == pat.length()) { // Both has drained
            return true;
        }

        if (j == pat.length()) { // pat drained, but more chars left match
            return false;
        }

        if (i == str.length()) { // chars drained, but pat left unmatched
            // when last characters of pattern are *, we won't be able to check till end as i will go out of bounds early
            /*
            abcde **
            here 1st * can match abcd and second * can only e
            But when second star tries to match e, i=4 and j=1
            Now both i and j has to move to next position to formally end
            But here we've only two options, take it or skip it
            i+1,j or 1,j+1
            4,2 OR 5,1
            In both cases, we end up in false
            To handle this corner has, when it goes to 5,1, we check if all remaining characters are just * consider them as no-match
             */
            while (j < pat.length()) {
                if (pat.charAt(j) != '*') {
                    return false;
                }
                j++;
            }
            return true; // all remaining pat are * only
        }

        if (dp[i][j] != -1) {
            return dp[i][j] == 1;
        }

        boolean result;
        if (pat.charAt(j) == '?') {
            result = wildCard(str, pat, i + 1, j + 1, dp);
        } else if (pat.charAt(j) == '*') {
            result = wildCard(str, pat, i + 1, j, dp) || wildCard(str, pat, i, j + 1, dp); // match current char and any in future OR skip matching and look for next pattern
        } else {
            if (str.charAt(i) == pat.charAt(j)) {
                result = wildCard(str, pat, i + 1, j + 1, dp);
            } else {
                result = false; // no match
            }
        }
        dp[i][j] = result ? 1 : 0;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O((m+n)^2)
S - O((m+n)^2) - dp

Known solutions
Any empty string and empty pattern is a match
Any non-empty string and empty pattern is a no-match
Any empty string and (non-star) non-empty pattern is a no-match
Any empty string and All star non-empty pattern is a match

1-based indexing
 */
class Solution3 {
    public boolean wildCard(String str, String pat) {
        int n = str.length();
        int m = pat.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        // Known solutions
        // empty string and empty pattern is a match
        dp[0][0] = true;
        // empty string and All star non-empty pattern is a match
        /*
         * - true
         **  true true
         *?* true false false
         */
        int k = 1;
        while (k <= m && pat.charAt(k - 1) == '*') {
            dp[0][k] = true;
            k++;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (pat.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1]; // any match, so carries prev result
                } else if (pat.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1]; // match [i-1,j] or skip [i,j-1]
                } else {
                    dp[i][j] = str.charAt(i - 1) == pat.charAt(j - 1); // should be exact match and prev character should be matched
                }
            }
        }

        return dp[n][m];
    }
}

/*
Step 4 - Space Optimization

T - O((m+n)^2)
S - O((m+n)) - dp

 */
class Solution4 {
    public boolean wildCard(String str, String pat) {
        int n = str.length();
        int m = pat.length();
        boolean[][] dp = new boolean[2][m + 1];

        // Known solutions
        // empty string and empty pattern is a match
        dp[0][0] = true;
        // empty string and All star non-empty pattern is a match
        /*
         * - true
         **  true true
         *?* true false false
         */
        int k = 1;
        while (k <= m && pat.charAt(k - 1) == '*') {
            dp[0][k] = true;
            k++;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (pat.charAt(j - 1) == '?') {
                    dp[1][j] = dp[0][j - 1]; // any match, so carries prev result
                } else if (pat.charAt(j - 1) == '*') {
                    dp[1][j] = dp[0][j] || dp[1][j - 1]; // match [i-1,j] or skip [i,j-1]
                } else {
                    dp[1][j] = str.charAt(i - 1) == pat.charAt(j - 1); // should be exact match and prev character should be matched
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m + 1);
        }

        return dp[0][m];
    }
}
