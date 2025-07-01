package io.abdul.sliding_window.longest_smallest_window.problem1;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Example 1: "abcddabac" -> 4 ("abcd")
        assertEquals(4, solution.longestNonRepeatingSubstring("abcddabac"));

        // Example 2: "aaabbbccc" -> 2 ("ab", "bc")
        assertEquals(2, solution.longestNonRepeatingSubstring("aaabbbccc"));

        // Example 3: "aaaa" -> 1 ("a")
        assertEquals(1, solution.longestNonRepeatingSubstring("aaaa"));

        // Edge case: single character
        assertEquals(1, solution.longestNonRepeatingSubstring("a"));

        // Edge case: all unique
        assertEquals(9, solution.longestNonRepeatingSubstring("abcdefghi"));

        // Edge case: alternating characters
        assertEquals(2, solution.longestNonRepeatingSubstring("abababab"));

        // Edge case: empty string
        assertEquals(0, solution.longestNonRepeatingSubstring(""));

        // Edge case: two different characters
        assertEquals(2, solution.longestNonRepeatingSubstring("ab"));

        // Edge case: palindrome
        assertEquals(3, solution.longestNonRepeatingSubstring("abccba"));

    }
}

/*
Step 1 - Brute-force solution
Explore all possible solutions (Recursion / DP / Iteration)

T - O(n^2)
S - O(n)

 */
class Solution {
    public int longestNonRepeatingSubstring(String s) {
        int n = s.length();
        HashSet<Character> substring = new HashSet<>();

        int max = 0;
        for (int i = 0; i < n; i++) {
            substring.clear();
            for (int j = i; j < n; j++) {
                if (substring.contains(s.charAt(j))) {
                    break;
                }
                substring.add(s.charAt(j));
            }
            max = Math.max(max, substring.size());
        }

        return max;
    }
}

/*
Step 2 - Optimal solution
Sliding window

T - O(n)
S - O(1) - just 26 chars

When current char doesn't repeat, expand and set max
When it repeats, set left to last repeating position +1
 */
class Solution2 {
    public int longestNonRepeatingSubstring(String s) {
        int left = 0, right = 0, maxLength = 0;
        int n = s.length();

        int[] charPosition = new int[n];
        Arrays.fill(charPosition, -1);

        while (right < n) {
            char currentChar = s.charAt(right);
            int pos = ((int) currentChar) % 97;
            if (charPosition[pos] != -1 && charPosition[pos] >= left) { // doesn't repeat in the current window
                left = charPosition[pos] + 1; // shrink window excluding the repeating character
            }
            charPosition[pos] = right; // store last position
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }

        return maxLength;
    }
}
