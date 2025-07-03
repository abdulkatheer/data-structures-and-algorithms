package io.abdul.sliding_window.counting_subarrays_and_substrings.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

/*
1 2 1 1 2 2 1 1 2 2 1, k =2
0 1 2 3 4 5 6 7 8 9 10

0 1 2
1 2 3
2 3
3 4 5 6
2 3 1 1
1 2 2 1
 */
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    Solution2 solution = new Solution2();

    // Basic examples
    assertEquals(2, solution.numberOfOddSubarrays(new int[]{1, 1, 2, 1, 1}, 3));
    assertEquals(0, solution.numberOfOddSubarrays(new int[]{4, 8, 2}, 1));
    assertEquals(2, solution.numberOfOddSubarrays(new int[]{41, 3, 5}, 2));

    // Single element
    assertEquals(1, solution.numberOfOddSubarrays(new int[]{1}, 1));
    assertEquals(0, solution.numberOfOddSubarrays(new int[]{2}, 1));

    // All odds
    assertEquals(2, solution.numberOfOddSubarrays(new int[]{1, 3, 5}, 2)); // [1,3], [3,5], [1,3,5]

    // All evens
    assertEquals(0, solution.numberOfOddSubarrays(new int[]{2, 4, 6, 8}, 1));

    // Alternating odd-even
    assertEquals(4,
        solution.numberOfOddSubarrays(new int[]{1, 2, 1, 2, 1}, 2)); // [1,2,1], [2,1,2,1], etc.

    // k equals total number of odds
    assertEquals(2, solution.numberOfOddSubarrays(new int[]{2, 1, 2, 1, 2, 1}, 3));

    // k = 1, count all subarrays with single odd
    assertEquals(8,
        solution.numberOfOddSubarrays(new int[]{1, 2, 1, 2, 1}, 1)); // [1], [1], [1], [1,2], etc.

    // Large input with no odds
    int[] evens = new int[50000];
    Arrays.fill(evens, 2);
    assertEquals(0, solution.numberOfOddSubarrays(evens, 1));

    // Large input with alternating odds
    int[] large = new int[10000];
    for (int i = 0; i < large.length; i++) {
      large[i] = (i % 2 == 0) ? 1 : 2;
    }
    assertTrue(
        solution.numberOfOddSubarrays(large, 3) > 0); // At least some nice subarrays should exist
  }
}

/*
Step 1 - Brute-force

T - O(n^2)
S - O(1)

 */
class Solution {

  public int numberOfOddSubarrays(int[] nums, int k) {
    int n = nums.length;
    int result = 0;

    for (int i = 0; i < n; i++) {
      int count = 0;
      for (int j = i; j < n; j++) {
        if ((nums[j] & 1) == 1) {
          count++;
        }
        if (count > k) {
          break;
        }
        if (count == k) {
          result++;
        }
      }
    }

    return result;
  }
}

/*
Step 2 - Optimal

T - O(n) - 2 * 2n
S - O(1)

Similar to problem2, it's difficult to find exact count. Defining when to shrink and when to expand is complex here.
Easy to find subarrays with condition like less than or equal to. So we'll find less than or equal to k and k-1 and subtract,.
 */
class Solution2 {

  public int numberOfOddSubarrays(int[] nums, int k) {
    return numberOfOddSubarraysLessThanOrEqualTo(nums, k) - numberOfOddSubarraysLessThanOrEqualTo(
        nums, k - 1);
  }

  public int numberOfOddSubarraysLessThanOrEqualTo(int[] nums, int k) {
    if (k < 0) {
      return 0;
    }
    int n = nums.length;

    int left = 0, right = 0, count = 0, result = 0;
    while (right < n) {
      if ((nums[right] & 1) == 1) {
        count++;
      }

      while (count > k) {
        if ((nums[left] & 1) == 1) {
          count--;
        }
        left++;
      }

      // means subarrays starting at left and ending at right has count <= k
      // if the length is 4, there are 4 subarrays ending at right
      result = result + right - left + 1; // number of subarrays ending at right
      right++;
    }

    return result;
  }
}