package io.abdul.sliding_window.counting_subarrays_and_substrings.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Basic examples
    assertEquals(5, solution.numberOfSubstrings("abcba"));
    assertEquals(8, solution.numberOfSubstrings("ccabcc"));
    assertEquals(7, solution.numberOfSubstrings("abccba"));

    // Minimal inputs
    assertEquals(0, solution.numberOfSubstrings("a"));
    assertEquals(0, solution.numberOfSubstrings("ab"));
    assertEquals(1, solution.numberOfSubstrings("abc"));

    // All characters same
    assertEquals(0, solution.numberOfSubstrings("aaaaa"));
    assertEquals(0, solution.numberOfSubstrings("ccccc"));

    // Repeating patterns
    assertEquals(10,
        solution.numberOfSubstrings(
            "abcabc")); // all 10 substrings from index 0 to 5 with all 3 chars

    // Only one valid combination at end
    assertEquals(5, solution.numberOfSubstrings("aaaaabc"));

    // Substrings with overlapping patterns
    assertEquals(3, solution.numberOfSubstrings("abca")); // "abc", "abca", "bca"

    // Stress case
    String large = "a".repeat(10000) + "b".repeat(10000) + "c".repeat(10000);
    assertEquals(10000 * 10000,
        solution.numberOfSubstrings(large)); // Only starts with a and includes b and c later

  }
}

/*
Step 1 - Brute-force

T - O(n^2)
S - O(1)

abcba
a - no
ab - no
abc - yes
if abc has a,b,c, then all possible substrings containing that will also have a,b,c
abc
abcb
abcba
So add 3 and break that inner loop and look for next i
 */
class Solution {

  public int numberOfSubstrings(String s) {
    int n = s.length();

    int result = 0;

    int[] charFrequency = new int[3];
    for (int i = 0; i < n; i++) {
      Arrays.fill(charFrequency, 1); // count up for a,b,c
      int count = 0;
      for (int j = i; j < n; j++) {
        if (charFrequency[s.charAt(j) - 'a'] == 1) {
          count++;
        }
        charFrequency[s.charAt(j) - 'a']--; // count down for occurrence

        if (count == 3) {
          result = result + n - j;
          break;
        }
      }
    }

    return result;
  }
}

/*
Step 2 - Better
Sliding window

T - O(n) - 2n
S - O(1)

Try to find a match, once found all remaining substring starting with same left will have a match. So add all.
Then shrink only once and repeat.
 */
class Solution2 {

  public int numberOfSubstrings(String s) {
    int n = s.length();

    int result = 0;

    int[] charFrequency = new int[3];
    Arrays.fill(charFrequency, 1);
    int left = 0, right = 0;
    int count = 0;
    while (right < n) {
      int rightPos = s.charAt(right) - 'a';
      if (charFrequency[rightPos] == 1) {
        count++;
      }
      charFrequency[rightPos]--;

      // Until we can make a result with same right and different left, count all of them
      while (count == 3) {
        result = result + n - right; // all substrings starting with same left will have a,b,c

        int leftPos = s.charAt(left) - 'a';
        charFrequency[leftPos]++; // shrink
        // if char at left removes one of a,b,c completely
        if (charFrequency[leftPos] == 1) {
          count--;
        }
        left++;
      }

      right++;
    }

    return result;
  }
}

/*
Step 3 - Optimal

T - O(n) - n
S - O(1)

Similar to Step 2, but we count the front substrings.
If we look for match from front to back, as we shrink, we need to expand to find a match.
If we look for match from back to front, we don't have to expand as all the remaining will be a match for sure.

b b a c a b
at pos 3, we find a match. It starts at pos 1, so we've two match 0 and 1
at pos 4, we find a match. It starts at pos 1, so we've two match 0 and 1
at pos 5, we find a match. It starts at pos 3, so we've 4 matches 0, 1, 2 and 3
 */
class Solution3 {

  public int numberOfSubstrings(String s) {
    int n = s.length();

    int result = 0;

    int[] charLastSeen = new int[3];
    Arrays.fill(charLastSeen, -1);

    for (int i = 0; i < n; i++) {
      charLastSeen[s.charAt(i) - 'a'] = i;

      // found a match
      if (charLastSeen[0] != -1 && charLastSeen[1] != -1 && charLastSeen[2] != -1) {
        int startsAt = Math.min(charLastSeen[0], Math.min(charLastSeen[1], charLastSeen[2]));
        result = result + startsAt + 1;
      }
    }

    return result;
  }
}