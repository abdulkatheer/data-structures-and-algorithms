package io.abdul.sliding_window.longest_smallest_window.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

/*
AABABBA k=1
At pos 3, A=3, B=1
It means, current character occurs 3 times, total so far is 4, so the other characters count should be 4-3=1
If it's within the limit, we count max, otherwise stop.

So we keep track of the max frequency of a character. If we just check against current char, we may not get result.
CCACE, k=3
At pos 4, 5-1=4, which is > k
But if we check agains max frequency (c=3), 5-3=2, which is <= k
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Basic given cases
    assertEquals(6, solution.characterReplacement("BAABAABBBAAA", 2));
    assertEquals(4, solution.characterReplacement("AABABBA", 1));
    assertEquals(2, solution.characterReplacement("ABCDEF", 1));

    // Edge cases
    assertEquals(0, solution.characterReplacement("", 2)); // empty string
    assertEquals(1, solution.characterReplacement("A", 0)); // single char
    assertEquals(2, solution.characterReplacement("AAB", 0)); // no change allowed, already valid
    assertEquals(4, solution.characterReplacement("ABAB", 2)); // change 2 Bs to A or vice versa

    // All same characters
    assertEquals(6, solution.characterReplacement("AAAAAA", 2));

    // k >= length
    assertEquals(6, solution.characterReplacement("ABCDEF", 10)); // all can be changed to same

    // Large repeating pattern
    assertEquals(8, solution.characterReplacement("AABBAABBBA", 3));

    // Alternating characters
    assertEquals(5, solution.characterReplacement("ABABABABA", 2));

    // Stress cases
    assertEquals(100000, solution.characterReplacement("A".repeat(100000), 0));
    assertEquals(100000,
        solution.characterReplacement("AB".repeat(50000), 50000)); // can make all same

    assertEquals(5, solution.characterReplacement("CCACE", 3));
  }
}

/*
Step 1 - Brute-force
Explore all possibilities

T - O(n^2)
S - O(1) - 26 fixed size
 */
class Solution {

  public int characterReplacement(String s, int k) {
    int[] charFrequency = new int[26];
    int n = s.length();
    int max = 0;
    int maxFrequency = 0;

    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        int pos = s.charAt(j) - 'A';
        charFrequency[pos]++; // count the current char
        maxFrequency = Math.max(maxFrequency, charFrequency[pos]);

        int visitedSoFar = j - i + 1;
        int changes = visitedSoFar - maxFrequency;

        if (changes > k) {
          break;
        }

        max = Math.max(max, visitedSoFar);
      }
      Arrays.fill(charFrequency, 0);
    }

    return max;
  }
}

/*
Step 2 - Better
Sliding window

T - O(n) - 2n; right moves from 0 to n-1, left moves from 0 to n-1-k
S - O(1)

Shrink left to meet the condition
 */
class Solution2 {

  public int characterReplacement(String s, int k) {
    int[] charFrequency = new int[26];
    int n = s.length();
    int max = 0, maxFrequency = 0;
    int left = 0, right = 0;

    while (right < n) {
      int rightPos = s.charAt(right) - 'A';
      charFrequency[rightPos]++;
      maxFrequency = Math.max(maxFrequency, charFrequency[rightPos]);

      while ((right - left + 1 - maxFrequency) > k) {
        charFrequency[s.charAt(left) - 'A']--;

        // Now maxFrequency might have changed, so find the max
        maxFrequency = 0;
        for (int i = 0; i < 26; i++) {
          maxFrequency = Math.max(maxFrequency, charFrequency[i]);
        }

        left++;
      }

      // Above while loop will make left == right in the worst case (k=0)
      max = Math.max(max, right - left + 1);
      right++;
    }

    return max;
  }
}

/*
Step 3 - Optimal
Sliding window

T - O(n) - n; right moves from 0 to n-1, left moves along with right when needed
S - O(1)

Shrink left only once when the condition is invalid to avoid more unnecessary iterations
 */
class Solution3 {

  public int characterReplacement(String s, int k) {
    int[] charFrequency = new int[26];
    int n = s.length();
    int max = 0, maxFrequency = 0;
    int left = 0, right = 0;

    while (right < n) {
      int rightPos = s.charAt(right) - 'A';
      charFrequency[rightPos]++;
      maxFrequency = Math.max(maxFrequency, charFrequency[rightPos]);

      // Shrink just once, not until
      if ((right - left + 1 - maxFrequency) > k) {
        charFrequency[s.charAt(left) - 'A']--;

        // Now maxFrequency might have changed, so find the max
        maxFrequency = 0;
        for (int i = 0; i < 26; i++) {
          maxFrequency = Math.max(maxFrequency, charFrequency[i]);
        }

        left++;
      }

      if ((right - left + 1 - maxFrequency) <= k) { // do only if valid
        max = Math.max(max, right - left + 1);
      }
      right++;
    }

    return max;
  }
}