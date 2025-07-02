package io.abdul.sliding_window.longest_smallest_window.problem3;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//        Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Example 1
    assertEquals(3, solution.totalFruits(new int[]{1, 2, 1}));

    // Example 2
    assertEquals(4, solution.totalFruits(new int[]{1, 2, 3, 2, 2}));

    // Example 3
    assertEquals(2, solution.totalFruits(new int[]{1, 2, 3, 4, 5}));

    // Edge case: all same fruit
    assertEquals(5, solution.totalFruits(new int[]{2, 2, 2, 2, 2}));

    // Edge case: only two types, alternating
    assertEquals(6, solution.totalFruits(new int[]{1, 2, 1, 2, 1, 2}));

    // Edge case: single tree
    assertEquals(1, solution.totalFruits(new int[]{7}));

    // Edge case: two trees, different fruits
    assertEquals(2, solution.totalFruits(new int[]{3, 4}));

    // Edge case: two trees, same fruit
    assertEquals(2, solution.totalFruits(new int[]{5, 5}));

    // Edge case: three types, longest at end
    assertEquals(2, solution.totalFruits(new int[]{1, 2, 3}));

    // Edge case: three types, longest in middle
    assertEquals(4, solution.totalFruits(new int[]{0, 1, 2, 2, 1}));

    assertEquals(73, solution.totalFruits(
        new int[]{44, 44, 22, 22, 44, 22, 44, 22, 22, 22, 22, 44, 22, 22, 44, 22, 22, 44, 22, 22,
            22, 22, 44, 44, 44, 44, 44, 44, 22, 22, 22, 44, 44, 22, 44, 22, 22, 22, 22, 44, 44, 44,
            44, 22, 22, 44, 44, 22, 22, 44, 22, 22, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 22, 22,
            44, 44, 44, 22, 22, 22, 22, 44, 44}));

  }
}

/*
Step 1 - Brute-force
Explore all possible subarrays

T - O(n^2)
S - O(1)

 */
class Solution {

  public int totalFruits(int[] fruits) {
    int n = fruits.length;
    if (n <= 2) {
      return n;
    }

    HashSet<Integer> fruitsHarvested = new HashSet<>(2);
    int max = 0;

    for (int i = 0; i < n - 1; i++) {
      for (int j = i; j < n; j++) {
        fruitsHarvested.add(fruits[j]);
        if (fruitsHarvested.size() > 2) { // after adding a third type fruit, we need to stop!
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

T - O(n) -> 2n; right moves from 0 to n-1; left moves from 0 to n-3 when all are distinct fruits
S - O(1)

Expand if types are within limits
Shrink until types of within limits
 */
class Solution2 {

  public int totalFruits(int[] fruits) {
    int n = fruits.length;
    if (n <= 2) {
      return n;
    }

    HashMap<Integer, Integer> fruitsHarvested = new HashMap<>(2);
    int max = 0, left = 0, right = 0;

    while (right < n) {
      fruitsHarvested.put(fruits[right], fruitsHarvested.getOrDefault(fruits[right], 0) + 1);

      /*
      Shrink until - size becomes 2

      right holds new type, so left must hold another type
      1 2 1 3
      l     r
      So l has to moved to 2 position to keep only two types
      Removed fruits from left until we end up having only two types
       */
      while (fruitsHarvested.size() > 2) {
        fruitsHarvested.put(fruits[left], fruitsHarvested.get(fruits[left]) - 1);
        // one type is completely removed, end up having just two types now
        if (fruitsHarvested.get(fruits[left]) == 0) {
          fruitsHarvested.remove(fruits[left]);
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

T - O(n) -> 2n; right moves from 0 to n-1; left moves along with right if more than 2 types available
S - O(1)

Expand if types are within limits
Shrink until types of within limits
 */
class Solution3 {

  public int totalFruits(int[] fruits) {
    int n = fruits.length;
    if (n <= 2) {
      return n;
    }

    HashMap<Integer, Integer> fruitsHarvested = new HashMap<>(2);
    int max = 0, left = 0, right = 0;

    while (right < n) {
      fruitsHarvested.put(fruits[right], fruitsHarvested.getOrDefault(fruits[right], 0) + 1);

      /*
      Shrink just once, not until!

      right holds new type, so left must hold another type
      1 2 1 3
      l     r
      So l has to moved to 2 position to keep only two types
      Removed fruits from left until we end up having only two types
       */
      if (fruitsHarvested.size() > 2) { // We'll do only once, reducing more will cost more.
        fruitsHarvested.put(fruits[left], fruitsHarvested.get(fruits[left]) - 1);
        // one type is completely removed, end up having just two types now
        if (fruitsHarvested.get(fruits[left]) == 0) {
          fruitsHarvested.remove(fruits[left]);
        }
        left++;
      }

      if (fruitsHarvested.size() <= 2) { // do only if valid
        max = Math.max(max, right - left + 1);
      }

      right++;
    }

    return max;
  }
}