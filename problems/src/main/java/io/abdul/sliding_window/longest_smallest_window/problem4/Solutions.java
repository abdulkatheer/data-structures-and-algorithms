package io.abdul.sliding_window.longest_smallest_window.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

/*
Similar to fruits and baskets problem, but with configurable distinct items
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    assertEquals(6, solution.kDistinctChar("aababbcaacc", 2));
    assertEquals(4, solution.kDistinctChar("abcddefg", 3));
    assertEquals(6, solution.kDistinctChar("abccab", 4));
    assertEquals(3, solution.kDistinctChar("aaabbb", 1));
    assertEquals(0, solution.kDistinctChar("abc", 0));
    assertEquals(7, solution.kDistinctChar("aaaaaaa", 1));
    assertEquals(9, solution.kDistinctChar("abcabcabc", 10));
    assertEquals(1, solution.kDistinctChar("a", 1));
    assertEquals(1, solution.kDistinctChar("ab", 1));
    assertEquals(12, solution.kDistinctChar("abcabcabcabc", 3));
    assertEquals(4, solution.kDistinctChar("xyzaabbccdde", 2));
    assertEquals(100000, solution.kDistinctChar("a".repeat(100000), 1));
    assertEquals(100000, solution.kDistinctChar("ab".repeat(50000), 2));
    assertEquals(3, solution.kDistinctChar("abcdefghijklmnopqrstuvwxyz", 3));
  }
}

/*
Step 1 - Brute-force
Explore all possibilities

T - O(n^2)
S - O(1) - max 26 chars size

 */
class Solution {

  public int kDistinctChar(String s, int k) {
    int n = s.length();
    if (n <= k) {
      return n;
    }

    HashSet<Character> fruitsHarvested = new HashSet<>(2);
    int max = 0;

    for (int i = 0; i < n - 1; i++) {
      for (int j = i; j < n; j++) {
        fruitsHarvested.add(s.charAt(j));
        if (fruitsHarvested.size() > k) { // after adding a third type fruit, we need to stop!
          break;
        }
        max = Math.max(max, j - i + 1);
      }
      fruitsHarvested.clear();
    }

    return max;
  }
}

/*
Step 2 - Better
Sliding window

T - O(n) - 2n; right moves from 0 to n-1; left moves from 0 to n-1-k
S - O(n)

Shrink left to meet k condition
 */
class Solution2 {

  public int kDistinctChar(String s, int k) {
    int n = s.length();
    if (n <= k) {
      return n;
    }

    HashMap<Character, Integer> fruitsHarvested = new HashMap<>(2);
    int max = 0, left = 0, right = 0;

    while (right < n) {
      fruitsHarvested.put(s.charAt(right), fruitsHarvested.getOrDefault(s.charAt(right), 0) + 1);

      /*
      Shrink until - size becomes 2

      right holds new type, so left must hold another type
      1 2 1 3
      l     r
      So l has to moved to 2 position to keep only two types
      Removed fruits from left until we end up having only two types
       */
      while (fruitsHarvested.size() > k) {
        char leftChar = s.charAt(left);
        fruitsHarvested.put(leftChar, fruitsHarvested.get(leftChar) - 1);
        // one type is completely removed, end up having just two types now
        if (fruitsHarvested.get(leftChar) == 0) {
          fruitsHarvested.remove(leftChar);
        }
        left++;
      }

      max = Math.max(max, right - left + 1);
      right++;
    }

    return max;
  }
}

/*
Step 3 - Optimal
Sliding window

T - O(n) - n; right moves from 0 to n-1; left moves along when needed
S - O(1)

Shrink only one step and update result only on valid condition. Shrinking much will require us to expand much to meet better answer.
 */
class Solution3 {

  public int kDistinctChar(String s, int k) {
    int n = s.length();
    if (n <= k) {
      return n;
    }

    HashMap<Character, Integer> fruitsHarvested = new HashMap<>(2);
    int max = 0, left = 0, right = 0;

    while (right < n) {
      fruitsHarvested.put(s.charAt(right), fruitsHarvested.getOrDefault(s.charAt(right), 0) + 1);

      /*
      Shrink just once, not until!

      right holds new type, so left must hold another type
      1 2 1 3
      l     r
      So l has to moved to 2 position to keep only two types
      Removed fruits from left until we end up having only two types
       */
      if (fruitsHarvested.size() > k) {
        char leftChar = s.charAt(left);
        fruitsHarvested.put(leftChar, fruitsHarvested.get(leftChar) - 1);
        // one type is completely removed, end up having just two types now
        if (fruitsHarvested.get(leftChar) == 0) {
          fruitsHarvested.remove(leftChar);
        }
        left++;
      }

      if (fruitsHarvested.size() <= k) { // do only if valid
        max = Math.max(max, right - left + 1);
      }
      right++;
    }

    return max;
  }
}