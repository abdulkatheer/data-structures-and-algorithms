package io.abdul.dynamic_programming.dp_on_strings.problem8;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditDistance {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        SolutionV2_1 solution = new SolutionV2_1();
//        SolutionV2_2 solution = new SolutionV2_2();
//        SolutionV2_3 solution = new SolutionV2_3();
        SolutionV2_4 solution = new SolutionV2_4();

        // Test Case 1: Example input with 2 operations needed
        String start1 = "planet";
        String target1 = "plan";
        assertEquals(2, solution.editDistance(start1, target1),
                "The edit distance between \"planet\" and \"plan\" should be 2");

        // Test Case 2: Example input with 4 operations needed
        String start2 = "abcdefg";
        String target2 = "azced";
        assertEquals(4, solution.editDistance(start2, target2),
                "The edit distance between \"abcdefg\" and \"azced\" should be 4");

        // Test Case 3: Example input with 3 operations needed
        String start3 = "saturday";
        String target3 = "sunday";
        assertEquals(3, solution.editDistance(start3, target3),
                "The edit distance between \"saturday\" and \"sunday\" should be 3");

        // Test Case 4: Edge case with identical strings
        String start4 = "abc";
        String target4 = "abc";
        assertEquals(0, solution.editDistance(start4, target4),
                "The edit distance between identical strings \"abc\" and \"abc\" should be 0");

        // Test Case 5: Edge case with one empty string
        String start5 = "";
        String target5 = "abc";
        assertEquals(3, solution.editDistance(start5, target5),
                "The edit distance between an empty string and \"abc\" should be 3");

        // Test Case 6: Edge case with no common characters
        String start6 = "xyz";
        String target6 = "abc";
        assertEquals(3, solution.editDistance(start6, target6),
                "The edit distance between \"xyz\" and \"abc\" should be 3");

        // Test Case 7: Large input with partial match
        String start7 = "abcdefghij";
        String target7 = "acegikmoqs";
        // a c e g i
        assertEquals(9, solution.editDistance(start7, target7),
                "The edit distance between \"abcdefghij\" and \"acegikmoqs\" should be 10");

        // Test Case 8: Edge case with one string being a subsequence of the other
        String start8 = "abc";
        String target8 = "abcd";
        assertEquals(1, solution.editDistance(start8, target8),
                "The edit distance between \"abc\" and \"abcd\" should be 1");

        String start9 = "ibxmqhhtyorfvp";
        String target9 = "ygxmdzcfffppkmfldfihonjcige";
        // x m f p
        assertEquals(24, solution.editDistance(start9, target9));

        String start10 = "sbresu";
        String target10 = "hsmmr";
        assertEquals(6, solution.editDistance(start10, target10));

    }
}

/*
Step 1 - Top-down recursive solution

Similar to Min Insertions to Convert A to B. But here replace is a single operation

NOTE : WRONG solution, LCS looked right for few cases. But it's not
 */
class Solution {
    public int editDistance(String start, String target) {
        int lcs = lcs(start, target);
        return Math.max(start.length() - lcs, target.length() - lcs);
    }

    public int lcs(String str1, String str2) {
        return lcs(str1, str2, 0, 0);
    }

    private int lcs(String str1, String str2, int i1, int i2) {
        if (i1 == str1.length() || i2 == str2.length()) {
            return 0;
        }

        if (str1.charAt(i1) == str2.charAt(i2)) {
            return 1 + lcs(str1, str2, i1 + 1, i2 + 1);
        } else {
            return Math.max(lcs(str1, str2, i1, i2 + 1), lcs(str1, str2, i1 + 1, i2));
        }
    }
}

/*
Step 1 - Top-down recursive solution

T - O(3^(n+m))
S - O(n+m) - stack

When character at i and j matches, no operation required. We can proceed to next.
When they don't match, 1 operation required (replace/delete/insert).
And three options, replace (move to next), delete (move only s to next), insert (move only t to next). We can the min of three.
 */
class SolutionV2_1 {
    public int editDistance(String start, String target) {
        return editDistance(start, target, 0, 0);
    }

    public int editDistance(String str1, String str2, int i1, int i2) {
        if (i2 == str2.length()) { // entire string matched, now remaining chars in str1 has to be removed
            return str1.length() - i1; // for str1 == length, it'll return zero
        }

        if (i1 == str1.length()) { // str2 not found completely, now needed characters to be inserted
            return str2.length() - i2;
        }

        if (str1.charAt(i1) == str2.charAt(i2)) {
            return editDistance(str1, str2, i1 + 1, i2 + 1); // when matched, this will be the smallest result. so no need to compare with other options
        } else {
            int replace = editDistance(str1, str2, i1 + 1, i2 + 1);
            int insert = editDistance(str1, str2, i1, i2 + 1);
            int delete = editDistance(str1, str2, i1 + 1, i2);
            return 1 + Math.min(replace, Math.min(insert, delete));
        }
    }
}

/*
Step 2 - Memoization

T - O(n*m)
S - O(n*m) - stack + dp

 */
class SolutionV2_2 {
    public int editDistance(String start, String target) {
        int[][] dp = new int[start.length()][target.length()];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return editDistance(start, target, 0, 0, dp);
    }

    public int editDistance(String str1, String str2, int i1, int i2, int[][] dp) {
        if (i2 == str2.length()) { // entire string matched, now remaining chars in str1 has to be removed
            return str1.length() - i1; // for str1 == length, it'll return zero
        }

        if (i1 == str1.length()) { // str2 not found completely, now needed characters to be inserted
            return str2.length() - i2;
        }

        if (dp[i1][i2] != -1) {
            return dp[i1][i2];
        }

        int result;
        if (str1.charAt(i1) == str2.charAt(i2)) {
            result = editDistance(str1, str2, i1 + 1, i2 + 1, dp); // when matched, this will be the smallest result. so no need to compare with other options
        } else {
            int replace = editDistance(str1, str2, i1 + 1, i2 + 1, dp);
            int insert = editDistance(str1, str2, i1, i2 + 1, dp);
            int delete = editDistance(str1, str2, i1 + 1, i2, dp);
            result = 1 + Math.min(replace, Math.min(insert, delete));
        }
        dp[i1][i2] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n*m)
S - O(n*m) - dp

Known solutions
For any empty target, entire start chars has to be removed
For any empty start, entire target chars has to be inserted

row - start
column - target
 */
class SolutionV2_3 {
    public int editDistance(String start, String target) {
        int n = start.length();
        int m = target.length();
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] stores the max operations required to convert from start of length i (from start) to target of length j (from start)

        // Known solutions
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i; // i chars has to be removed
        }
        for (int i = 0; i <= m; i++) {
            dp[0][i] = i; // i chars has to be inserted
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (start.charAt(i - 1) == target.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int replace = dp[i - 1][j - 1];
                    int insert = dp[i - 1][j];
                    int delete = dp[i][j - 1];
                    dp[i][j] = 1 + Math.min(replace, Math.min(delete, insert));
                }
            }
        }

        return dp[n][m];
    }
}

/*
Step 4 - Space Optimization

T - O(n*m)
S - O(m) - dp

 */
class SolutionV2_4 {
    public int editDistance(String start, String target) {
        int n = start.length();
        int m = target.length();
        int[][] dp = new int[2][m + 1];
        // dp[i][j] stores the max operations required to convert from start of length i (from start) to target of length j (from start)

        // Known solutions
        // Below set in main loop
//        for (int i = 0; i <= 1; i++) {
//            dp[i][0] = i; // i chars has to be removed
//        }
        for (int i = 0; i <= m; i++) {
            dp[0][i] = i; // i chars has to be inserted
        }

        for (int i = 1; i <= n; i++) {
            dp[1][0] = i; // As dp[1] is reused, the base case is overwritten. So set it every time.
            for (int j = 1; j <= m; j++) {
                if (start.charAt(i - 1) == target.charAt(j - 1)) {
                    dp[1][j] = dp[0][j - 1];
                } else {
                    int replace = dp[0][j - 1];
                    int insert = dp[0][j];
                    int delete = dp[1][j - 1];
                    dp[1][j] = 1 + Math.min(replace, Math.min(delete, insert));
                }
            }
            System.arraycopy(dp[1], 0, dp[0], 0, m + 1);
        }

        return dp[0][m];
    }
}