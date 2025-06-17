package io.abdul.dynamic_programming.lis.problem4;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestStringChain {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
//
//        // Test Case 1: Example input with chain length 5
//        String[] words1 = {"a", "ab", "abc", "abcd", "abcde"};
//        assertEquals(5, solution.longestStringChain(words1),
//                "Longest string chain for [\"a\", \"ab\", \"abc\", \"abcd\", \"abcde\"] should be 5");
//
//        // Test Case 2: Example input with chain length 4
//        String[] words2 = {"dog", "dogs", "dots", "dot", "d", "do"};
//        assertEquals(4, solution.longestStringChain(words2),
//                "Longest string chain for [\"dog\", \"dogs\", \"dots\", \"dot\", \"d\", \"do\"] should be 4");
//
//        // Test Case 3: Example input with chain length 4
//        String[] words3 = {"a", "aa", "aaa", "aaaa", "b", "bb", "bbb"};
//        assertEquals(4, solution.longestStringChain(words3),
//                "Longest string chain for [\"a\", \"aa\", \"aaa\", \"aaaa\", \"b\", \"bb\", \"bbb\"] should be 4");
//
//        // Test Case 4: Edge case with single word
//        String[] words4 = {"word"};
//        assertEquals(1, solution.longestStringChain(words4),
//                "Longest string chain for [\"word\"] should be 1");
//
//        // Test Case 5: Edge case with no chainable words
//        String[] words5 = {"cat", "dog", "fish"};
//        assertEquals(1, solution.longestStringChain(words5),
//                "Longest string chain for [\"cat\", \"dog\", \"fish\"] should be 1");
//
//        // Test Case 6: Large input with mixed chainable and non-chainable words
//        String[] words6 = {"a", "b", "ba", "bca", "bda", "bdca"};
//        assertEquals(4, solution.longestStringChain(words6),
//                "Longest string chain for [\"a\", \"b\", \"ba\", \"bca\", \"bda\", \"bdca\"] should be 4");
//
//        // Test Case 7: Edge case with duplicate words
//        String[] words7 = {"a", "a", "ab", "abc"};
//        assertEquals(3, solution.longestStringChain(words7),
//                "Longest string chain for [\"a\", \"a\", \"ab\", \"abc\"] should be 3");
//
//        String[] words8 = {"rrrbirbhm", "rjrbirbhm", "rrbpirbhm", "rrrbilrbhm", "rrbirbqhm", "rrcbirbhm", "rrsbirbhm", "rrbirdbhm", "rrbirtbhm", "rrbirbhm"};
//        assertEquals(3, solution.longestStringChain(words8));

        String[] words9 = {"w","work","wor","worl","world"};
        assertEquals(3, solution.longestStringChain(words9));

    }
}

/*
Step 1 - Top-down recursive solution

Similar to Longest Divisible Subset. If we sort words by length, we only need to compare current with previous.

 */
class Solution {
    public int longestStringChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));

        return longestStringChain(words, 0, -1);
    }

    public int longestStringChain(String[] words, int i, int prevPos) {
        if (i == words.length - 1) {
            if (prevPos < 0 || match(words[i], words[prevPos])) {
                return 1; // take
            } else {
                return 0; // skip
            }
        }

        // skip
        int skip = longestStringChain(words, i + 1, prevPos);

        int take = 0;
        if (prevPos < 0 || match(words[i], words[prevPos])) {
            // take
            take = 1 + longestStringChain(words, i + 1, i);
        }

        return Math.max(skip, take);
    }

    // Match ordered, but having just 1 extra element in a
    private boolean match(String a, String b) {
        if (a.length() - 1 != b.length()) {
            return false;
        }
        int i = 0;
        int j = 0;

        while (i < a.length()) {
            if (j < b.length() && a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                i++; // in right case, this should happen only once and for that one extra char only
                // in bad case, this will happen multiple times. When it happens multiple times, j will not have visited all characters and j < b.length
            }
        }

        return j == b.length();
    }
}

/*
Step 2 - Memoization

 */
class Solution2 {
    public int longestStringChain(String[] words) {
        Arrays.sort(words, Comparator.comparing(String::length));
        int[][] dp = new int[words.length][words.length];

        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }

        return longestStringChain(words, 0, -1, dp);
    }

    public int longestStringChain(String[] words, int i, int prevPos, int[][] dp) {
        if (i == words.length - 1) {
            if (prevPos < 0 || match(words[i], words[prevPos])) {
                return 1; // take
            } else {
                return 0; // skip
            }
        }

        if (prevPos >= 0 && dp[i][prevPos] != -1) {
            return dp[i][prevPos];
        }

        // skip
        int skip = longestStringChain(words, i + 1, prevPos, dp);

        int take = 0;
        if (prevPos < 0 || match(words[i], words[prevPos])) {
            // take
            take = 1 + longestStringChain(words, i + 1, i, dp);
        }

        int max = Math.max(skip, take);
        if (prevPos >= 0) {
            dp[i][prevPos] = max;
        }
        return max;
    }

    // Match ordered, but having just 1 extra element in a
    private boolean match(String a, String b) {
        if (a.length() - 1 != b.length()) {
            return false;
        }
        int i = 0;
        int j = 0;

        while (i < a.length()) {
            if (j < b.length() && a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                i++; // in right case, this should happen only once and for that one extra char only
                // in bad case, this will happen multiple times. When it happens multiple times, j will not have visited all characters and j < b.length
            }
        }

        return j == b.length();
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions:
At pos 0, max words if considered is 1.

Recursive solutions:
Go through all prev dp, and match the word and take the max of all
 */
class Solution3 {
    public int longestStringChain(String[] words) {
        Arrays.sort(words, Comparator.comparing(String::length));
        int[] dp = new int[words.length]; // At every position, what's the longest string chain upto that array

        // Known solutions
        dp[0] = 1;

        int maxOfAll = 1;
        for (int i = 1; i < words.length; i++) {
            int max = 1; // default if skipped
            for (int j = 0; j < i; j++) {
                if (match(words[i], words[j])) {
                    max = Math.max(max, 1 + dp[j]);
                }
            }

            dp[i] = max;
            maxOfAll = Math.max(max, maxOfAll);
        }

        return maxOfAll;
    }

    // Match ordered, but having just 1 extra element in a
    private boolean match(String a, String b) {
        if (a.length() - 1 != b.length()) {
            return false;
        }
        int i = 0;
        int j = 0;

        while (i < a.length()) {
            if (j < b.length() && a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                i++; // in right case, this should happen only once and for that one extra char only
                // in bad case, this will happen multiple times. When it happens multiple times, j will not have visited all characters and j < b.length
            }
        }

        return j == b.length();
    }
}

/*
Step 4 - Space Optimization

Can't be done as we need entire do table till the end
 */